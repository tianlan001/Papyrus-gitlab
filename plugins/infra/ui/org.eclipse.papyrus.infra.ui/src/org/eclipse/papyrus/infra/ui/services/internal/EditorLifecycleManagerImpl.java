/*****************************************************************************
 * Copyright (c) 2013, 2015 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 469188
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.services.internal;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.infra.ui.services.EditorLifecycleEventListener;
import org.eclipse.papyrus.infra.ui.services.EditorLifecycleManager;


public class EditorLifecycleManagerImpl implements EditorLifecycleManager, InternalEditorLifecycleManager {

	private final Set<EditorLifecycleEventListener> listeners = new HashSet<EditorLifecycleEventListener>();

	@Override
	public void init(ServicesRegistry servicesRegistry) throws ServiceException {
		// Nothing
	}

	@Override
	public void startService() throws ServiceException {
		// Nothing
	}

	@Override
	public void disposeService() throws ServiceException {
		listeners.clear();
	}

	@Override
	public void addEditorLifecycleEventsListener(EditorLifecycleEventListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeEditorLifecycleEventsListener(EditorLifecycleEventListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void firePostInit(final IMultiDiagramEditor editor) {
		for (final EditorLifecycleEventListener listener : listeners) {
			SafeRunner.run(new ISafeRunnable() {

				@Override
				public void run() throws Exception {
					listener.postInit(editor);
				}

				@Override
				public void handleException(Throwable exception) {
					// Already logged by the SafeRunner
				}
			});
		}
	}

	@Override
	public void firePreDisplay(final IMultiDiagramEditor editor) {
		for (final EditorLifecycleEventListener listener : listeners) {
			SafeRunner.run(new ISafeRunnable() {

				@Override
				public void run() throws Exception {
					listener.preDisplay(editor);
				}

				@Override
				public void handleException(Throwable exception) {
					// Already logged by the SafeRunner
				}
			});
		}
	}

	@Override
	public void firePostDisplay(final IMultiDiagramEditor editor) {
		for (final EditorLifecycleEventListener listener : listeners) {
			SafeRunner.run(new ISafeRunnable() {

				@Override
				public void run() throws Exception {
					listener.postDisplay(editor);
				}

				@Override
				public void handleException(Throwable exception) {
					// Already logged by the SafeRunner
				}
			});
		}
	}

	@Override
	public void fireBeforeClose(final IMultiDiagramEditor editor) {
		for (final EditorLifecycleEventListener listener : listeners) {
			SafeRunner.run(new ISafeRunnable() {

				@Override
				public void run() throws Exception {
					listener.beforeClose(editor);
				}

				@Override
				public void handleException(Throwable exception) {
					// Already logged by the SafeRunner
				}
			});
		}
	}

}
