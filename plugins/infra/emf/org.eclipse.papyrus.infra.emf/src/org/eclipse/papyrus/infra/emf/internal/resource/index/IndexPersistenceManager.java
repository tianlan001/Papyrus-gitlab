/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.internal.resource.index;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.eclipse.core.resources.ISaveContext;
import org.eclipse.core.resources.ISaveParticipant;
import org.eclipse.core.resources.ISavedState;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrus.infra.emf.Activator;
import org.eclipse.papyrus.infra.emf.resource.index.WorkspaceModelIndex;

import com.google.common.collect.Maps;

/**
 * Persistence manager for {@link WorkspaceModelIndex}es.
 */
public class IndexPersistenceManager {
	private static final IPath INDEX_DIR = new Path("index").addTrailingSeparator(); //$NON-NLS-1$

	private static final String ZIP_ENTRY = "Contents"; //$NON-NLS-1$

	public static final IndexPersistenceManager INSTANCE = new IndexPersistenceManager();

	private final Map<WorkspaceModelIndex<?>, IIndexSaveParticipant> workspaceIndices = Maps.newConcurrentMap();

	// Index file paths relative to the plug-in state location, by index name
	private Map<String, IPath> indexFiles = Collections.emptyMap();

	/**
	 * Not instantiable by clients.
	 */
	private IndexPersistenceManager() {
		super();
	}

	/**
	 * Initializes the persistence manager with the previous Eclipse session's
	 * saved state.
	 * 
	 * @param state
	 *            the previous session's state, or {@code null} if none
	 *            (for example, if this is the first run)
	 * 
	 * @throws CoreException
	 *             on failure to initialize the index persistence manager
	 */
	public void initialize(ISavedState state) throws CoreException {
		indexFiles = Collections.unmodifiableMap(
				Stream.of(state.getFiles())
						.collect(Collectors.toMap(IPath::toString, state::lookup)));
	}

	/**
	 * Registers a persistent model index.
	 * 
	 * @param index
	 *            the index to register
	 * @param saveParticipant
	 *            its workspace-save delegate
	 * 
	 * @return an input stream providing the previous session's index data, or {@code null}
	 *         if none is available, in which case presumably a full indexing is required.
	 *         The caller is required to {@link InputStream#close() close} this stream
	 */
	public InputStream addIndex(WorkspaceModelIndex<?> index, IIndexSaveParticipant saveParticipant) {
		ZipInputStream result = null;

		workspaceIndices.put(index, saveParticipant);

		IPath indexFile = indexFiles.get(index.getName());
		File storeFile = (indexFile != null) ? getStoreFile(indexFile) : null;
		if (storeFile != null) {
			if (storeFile.exists()) {
				try {
					result = new ZipInputStream(new FileInputStream(storeFile));

					// Get the Contents entry
					result.getNextEntry();
				} catch (Exception e) {
					Activator.log.error("Failed to open index file for " + index.getName(), e); //$NON-NLS-1$
				}
			}
		}

		return result;
	}

	/**
	 * Removes an index from the persistence manager.
	 * 
	 * @param index
	 *            the index to remove
	 */
	public void removeIndex(WorkspaceModelIndex<?> index) {
		workspaceIndices.remove(index);
	}

	private IPath getIndexLocation() {
		return Activator.getDefault().getStateLocation().append(INDEX_DIR);
	}

	private File getStoreFile(IPath storePath) {
		return Activator.getDefault().getStateLocation().append(storePath).toFile();
	}

	private IPath getStorePath(WorkspaceModelIndex<?> index, int saveNumber) {
		return INDEX_DIR.append(index.getName()).addFileExtension(String.valueOf(saveNumber));
	}

	private IPath getStoreLocation(WorkspaceModelIndex<?> index, int saveNumber) {
		return Activator.getDefault().getStateLocation().append(getStorePath(index, saveNumber));
	}

	/**
	 * Obtains a workspace save participant to which the bundle's main participant
	 * delegates the index portion of workspace save.
	 * <p>
	 * <b>Note</b> that this delegate must never tell the {@link ISaveContext} that
	 * it needs a {@linkplain ISaveContext#needSaveNumber() save number} or a
	 * {@linkplain ISaveContext#needDelta() delta} as that is the responsibility
	 * of the bundle's save participant. Also, it is only ever invoked on a
	 * full workspace save.
	 * </p>
	 * 
	 * @return the workspace save participant delegate
	 */
	public ISaveParticipant getSaveParticipant() {
		return new ISaveParticipant() {

			private Map<String, IPath> newIndexFiles;

			@Override
			public void prepareToSave(ISaveContext context) throws CoreException {
				// Ensure that our state location index directory exists
				File indexDirectory = getIndexLocation().toFile();
				if (!indexDirectory.exists()) {
					indexDirectory.mkdir();
				}
			}

			@Override
			public void saving(ISaveContext context) throws CoreException {
				// Save our indices
				for (Map.Entry<WorkspaceModelIndex<?>, IIndexSaveParticipant> next : workspaceIndices.entrySet()) {
					WorkspaceModelIndex<?> index = next.getKey();
					IIndexSaveParticipant save = next.getValue();

					if (save != null) {
						File storeFile = getStoreLocation(index, context.getSaveNumber()).toFile();

						try (OutputStream store = createStoreOutput(storeFile)) {
							save.save(index, store);
						} catch (IOException e) {
							storeFile.delete(); // In case there's something there, it can't be trusted
							throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
									"Failed to save index " + index.getName(), e)); //$NON-NLS-1$
						}
					}
				}

				// Compute the new index file mappings
				newIndexFiles = workspaceIndices.keySet().stream()
						.collect(Collectors.toMap(
								WorkspaceModelIndex::getName,
								index -> getStorePath(index, context.getSaveNumber())));

				// Remove old index mappings
				for (String next : indexFiles.keySet()) {
					context.map(new Path(next), null);
				}

				// Add new index mappings
				for (Map.Entry<String, IPath> next : newIndexFiles.entrySet()) {
					context.map(new Path(next.getKey()), next.getValue());
				}
			}

			private OutputStream createStoreOutput(File storeFile) throws IOException {
				ZipOutputStream result = new ZipOutputStream(new FileOutputStream(storeFile));
				ZipEntry entry = new ZipEntry(ZIP_ENTRY);
				result.putNextEntry(entry);
				return result;
			}

			@Override
			public void doneSaving(ISaveContext context) {
				// Delete the old index files
				try {
					indexFiles.values().forEach(p -> getStoreFile(p).delete());
				} catch (Exception e) {
					// This doesn't stop us proceeding
					Activator.log.error("Failed to clean up old index files", e); //$NON-NLS-1$
				}

				// Grab our new index files
				indexFiles = newIndexFiles;
				newIndexFiles = null;
			}

			@Override
			public void rollback(ISaveContext context) {
				try {
					if (newIndexFiles != null) {
						// Delete the new save files and mappings that we created
						newIndexFiles.values().stream()
								.map(IndexPersistenceManager.this::getStoreFile)
								.forEach(File::delete);

						// And the mappings
						newIndexFiles.keySet().stream()
								.map(Path::new)
								.forEach(p -> context.map(p, null));

						newIndexFiles = null;

						// Then restore the old mappings
						indexFiles.forEach((name, location) -> context.map(new Path(name), location));
					}
				} catch (Exception e) {
					Activator.log.error("Failed to roll back model indices.", e); //$NON-NLS-1$
				}

			}
		};
	}

}
