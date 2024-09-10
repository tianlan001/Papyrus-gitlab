/*****************************************************************************
 * Copyright (c) 2016, 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *  Christian W. Damus - bug 512554
 *****************************************************************************/

package org.eclipse.papyrus.uml.profile.index;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.infra.emf.resource.index.IWorkspaceModelIndexListener;
import org.eclipse.papyrus.infra.emf.resource.index.IWorkspaceModelIndexProvider;
import org.eclipse.papyrus.infra.emf.resource.index.WorkspaceModelIndex;
import org.eclipse.papyrus.infra.emf.resource.index.WorkspaceModelIndex.PersistentIndexHandler;
import org.eclipse.papyrus.uml.profile.Activator;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * {@link WorkspaceModelIndex} for {@link Profile} model.
 * 
 * @since 3.0
 *
 */
public class ProfileWorkspaceModelIndex {

	/** The workspace model index */
	private WorkspaceModelIndex<String> index;

	/** The instance. */
	private static final ProfileWorkspaceModelIndex INSTANCE = new ProfileWorkspaceModelIndex();

	String[] profileExtension = { "profile.uml" };//$NON-NLS-1$

	private Set<URI> workspaceProfileURIs = new HashSet<>();

	/**
	 * Constructor.
	 */
	private ProfileWorkspaceModelIndex() {
		index = new WorkspaceModelIndex<String>("PapyrusWorkspaceProfiles", UMLPackage.eCONTENT_TYPE, indexer()); //$NON-NLS-1$
	}

	/**
	 * @return the indexer.
	 */
	protected PersistentIndexHandler<String> indexer() {
		return new PersistentIndexHandler<String>() {

			@Override
			public String index(final IFile file) {
				URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);

				synchronized (workspaceProfileURIs) {
					workspaceProfileURIs.add(uri);
				}

				return uri.toString();
			}

			@Override
			public void unindex(final IFile file) {
				URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);

				synchronized (workspaceProfileURIs) {
					workspaceProfileURIs.remove(uri);
				}
			}

			@Override
			public boolean shouldIndex(IFile file) {
				return file.getName().contains(".profile.uml"); //$NON-NLS-1$
			};

			@Override
			public boolean load(IFile file, String index) {
				URI uri = URI.createPlatformResourceURI(index, true);

				synchronized (workspaceProfileURIs) {
					workspaceProfileURIs.add(uri);
				}

				return true;
			}
		};
	}

	/**
	 * Gets the instance of {@link ProfileWorkspaceModelIndex}.
	 */
	public static ProfileWorkspaceModelIndex getInstance() {
		return INSTANCE;
	}

	/**
	 * Gets all {@link Profile}s available in eclipse workspace.
	 */
	public Collection<URI> getWorkspaceProfilesURIs() {
		Collection<URI> result = new HashSet<>();

		try {
			result = index.afterIndex(() -> {
				return new HashSet<>(workspaceProfileURIs);
			}).get();
		} catch (InterruptedException | ExecutionException e) {
			Activator.log.error(e);
		}

		return result;
	}

	/**
	 * dispose index and loaded resources.
	 */
	public void dispose() {
		index.dispose();
	}

	/**
	 * @return The index.
	 */
	public WorkspaceModelIndex<String> getIndex() {
		return index;
	}

	/**
	 * Adds listener to the index.
	 */
	public void addListener(final IWorkspaceModelIndexListener listener) {
		index.addListener(listener);
	}

	/**
	 * Removes listener to the index.
	 */
	public void removeListener(final IWorkspaceModelIndexListener listener) {
		index.removeListener(listener);
	}

	/**
	 * Index provider on the extension point.
	 */
	public static final class IndexProvider implements IWorkspaceModelIndexProvider {
		@Override
		public WorkspaceModelIndex<?> get() {
			return ProfileWorkspaceModelIndex.INSTANCE.index;
		}
	}
}
