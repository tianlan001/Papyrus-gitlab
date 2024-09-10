/*
 * Copyright (c) 2014 CEA and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus (CEA) - Initial API and implementation
 *
 */
package org.eclipse.papyrus.infra.ui.editor.reload;



/**
 * Convenience superclass for selective implementation of editor reload call-backs.
 * 
 * @since 1.2
 */
public class EditorReloadAdapter implements IEditorReloadListener {

	public EditorReloadAdapter() {
		super();
	}

	@Override
	public void editorAboutToReload(EditorReloadEvent event) {
		// Pass
	}

	@Override
	public void editorReloaded(EditorReloadEvent event) {
		// Pass
	}

}
