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

package org.eclipse.papyrus.infra.ui.internal.emf.readonly;

import java.util.Map;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.resource.IReadOnlyHandler;
import org.eclipse.papyrus.infra.core.resource.IReadOnlyHandler2;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.readonly.ReadOnlyManager;
import org.eclipse.papyrus.infra.emf.readonly.spi.IReadOnlyManagerProcessor;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForResourceSet;
import org.eclipse.papyrus.infra.tools.util.PlatformHelper;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.infra.ui.editor.IReloadableEditor;
import org.eclipse.papyrus.infra.ui.editor.reload.EditorReloadEvent;
import org.eclipse.papyrus.infra.ui.editor.reload.IEditorReloadListener;
import org.eclipse.papyrus.infra.ui.editor.reload.IReloadContextProvider;
import org.eclipse.papyrus.infra.ui.internal.emf.Activator;

import com.google.common.collect.Maps;

/**
 * Implementation of the {@link ReadOnlyManager} processor service that attaches an
 * editor reload listener that lets {@link IReadOnlyHandler}s contribute
 * {@linkplain IReloadContextProvider reload contexts} to the editor re-load procedure.
 */
public class EditorReloadProcessor implements IReadOnlyManagerProcessor {

	public EditorReloadProcessor() {
		super();
	}

	@Override
	public void processReadOnlyManager(ReadOnlyManager readOnlyManager, EditingDomain editingDomain) {
		// Is this editing domain associated with an editor?
		try {
			IMultiDiagramEditor editor = ServiceUtilsForResourceSet.getInstance().getService(IMultiDiagramEditor.class, editingDomain.getResourceSet());
			if (editor != null) {
				IReloadableEditor reloadable = IReloadableEditor.Adapter.getAdapter(editor);
				if (reloadable != null) {
					reloadable.addEditorReloadListener(new ReadOnlyEditorReloadListener(readOnlyManager));
				}
			}
		} catch (ServiceException e) {
			// That's OK. We're not in the context of an editor
		}
	}

	//
	// Nested types
	//

	private static class ReadOnlyEditorReloadListener implements IEditorReloadListener {
		private ReadOnlyManager readOnlyManager;

		ReadOnlyEditorReloadListener(ReadOnlyManager readOnlyManager) {
			super();

			this.readOnlyManager = readOnlyManager;
		}

		//
		// Get and store a reload context from each read-only handler that can provide one
		//
		@Override
		public void editorAboutToReload(EditorReloadEvent event) {
			Map<Class<? extends IReadOnlyHandler2>, Object> reloadContexts = Maps.newHashMap();

			for (Iterable<IReadOnlyHandler2> partition : readOnlyManager.getReadOnlyHandlers().values()) {
				for (IReadOnlyHandler2 next : partition) {
					if (!reloadContexts.containsKey(next.getClass())) {
						IReloadContextProvider provider = PlatformHelper.getAdapter(next, IReloadContextProvider.class);
						if (provider != null) {
							reloadContexts.put(next.getClass(), provider.createReloadContext());
						}
					}
				}
			}

			if (!reloadContexts.isEmpty()) {
				event.putContext(reloadContexts);
			}
		}

		//
		// Ask available read-only handlers to restore the reload contexts that their
		// counterparts in the editor's previous manager had provided
		//
		@Override
		public void editorReloaded(EditorReloadEvent event) {
			// It will have been replaced by a new read-only manager
			try {
				readOnlyManager = (ReadOnlyManager) ReadOnlyManager.getReadOnlyHandler(event.getEditor().getServicesRegistry().getService(TransactionalEditingDomain.class));
			} catch (ServiceException e) {
				Activator.log.error(e);
			}

			Object context = event.getContext();
			if (context instanceof Map<?, ?>) {
				@SuppressWarnings("unchecked")
				Map<Class<? extends IReadOnlyHandler2>, Object> reloadContexts = (Map<Class<? extends IReadOnlyHandler2>, Object>) context;
				for (Iterable<IReadOnlyHandler2> partition : readOnlyManager.getReadOnlyHandlers().values()) {
					for (IReadOnlyHandler2 next : partition) {
						Object reloadContext = reloadContexts.get(next.getClass());
						if (reloadContext != null) {
							IReloadContextProvider provider = PlatformHelper.getAdapter(next, IReloadContextProvider.class);
							if (provider != null) {
								provider.restore(reloadContext);
							}
						}
					}
				}
			}
		}

	}
}
