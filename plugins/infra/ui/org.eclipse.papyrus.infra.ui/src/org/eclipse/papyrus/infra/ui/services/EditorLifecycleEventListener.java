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
package org.eclipse.papyrus.infra.ui.services;

import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;

/**
 * Listens to the Lifecycle of an {@link IMultiDiagramEditor}
 *
 * @author Camille Letavernier
 * @since 1.2
 *
 */
public interface EditorLifecycleEventListener {

	/**
	 * The ServicesRegistry is successfully started
	 *
	 * @param editor
	 */
	public void postInit(IMultiDiagramEditor editor);

	/**
	 * All the editors are constructed, but not yet displayed
	 *
	 * @param editor
	 */
	public default void preDisplay(IMultiDiagramEditor editor) {
		// Pass
	}

	/**
	 * All the editors are displayed
	 *
	 * @param editor
	 */
	public void postDisplay(IMultiDiagramEditor editor);

	/**
	 * The editor is about to be closed
	 *
	 * @param editor
	 */
	public void beforeClose(IMultiDiagramEditor editor);

}
