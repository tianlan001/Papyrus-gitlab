/*****************************************************************************
 * Copyright (c) 2012, 2015 CEA LIST, Christian W. Damus, and others.
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) Vincent.Lorenzo@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 434983
 *
 *****************************************************************************/
package org.eclipse.papyrus.junit.utils;

import org.eclipse.core.resources.IFile;
import org.eclipse.papyrus.editor.PapyrusMultiDiagramEditor;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.junit.Assert;

/**
 * 
 * useful methods for Editors
 * 
 */
public class EditorUtils {

	private EditorUtils() {
		// to prevent instanciation
	}

	/**
	 * 
	 * @param file
	 *            a file
	 * @return
	 * 		the opened editor for this file
	 * @throws PartInitException
	 */
	public static final IEditorPart openEditor(final IFile file) throws PartInitException {
		return withoutLayoutStoragePopup(() -> {
			GenericUtils.closeIntroPart();
			final IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			IEditorPart editor = null;
			editor = IDE.openEditor(activePage, file);
			Assert.assertNotNull(editor);
			return editor;
		});
	}

	/**
	 * Opens the file with the Papyrus Editor
	 * 
	 * @param file
	 * @return
	 * @throws PartInitException
	 */
	public static final IMultiDiagramEditor openPapyrusEditor(final IFile file) throws PartInitException {
		return withoutLayoutStoragePopup(() -> {
			GenericUtils.closeIntroPart();
			final IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			IEditorPart editor = null;
			editor = IDE.openEditor(activePage, file, PapyrusMultiDiagramEditor.EDITOR_ID);
			Assert.assertNotNull(editor);
			return (IMultiDiagramEditor) editor;
		});
	}

	/**
	 * Opens an editor without the possibility of it showing a prompt dialog to convert
	 * DI-file storage of the page layout to private sash-file storage.
	 */
	@SuppressWarnings("restriction")
	private static <E extends IEditorPart> E withoutLayoutStoragePopup(EditorOpener<E> editorOpener) throws PartInitException {
		E result;
		boolean posted = false;

		org.eclipse.papyrus.infra.ui.internal.preferences.YesNo originalPreference = org.eclipse.papyrus.infra.ui.internal.preferences.EditorPreferences.getInstance().getConvertSharedPageLayoutToPrivate();
		org.eclipse.papyrus.infra.ui.internal.preferences.EditorPreferences.getInstance().setConvertSharedPageLayoutToPrivate(org.eclipse.papyrus.infra.ui.internal.preferences.YesNo.NO);

		try {
			result = editorOpener.openEditor();
			result.getSite().getShell().getDisplay().asyncExec(() -> org.eclipse.papyrus.infra.ui.internal.preferences.EditorPreferences.getInstance().setConvertSharedPageLayoutToPrivate(originalPreference));
			posted = true;
		} finally {
			if (!posted) {
				// Revert now because the editor failed to open and we won't be reverting asynchronously
				org.eclipse.papyrus.infra.ui.internal.preferences.EditorPreferences.getInstance().setConvertSharedPageLayoutToPrivate(originalPreference);
			}
		}

		return result;
	}

	//
	// Nested types
	//

	@FunctionalInterface
	private interface EditorOpener<E extends IEditorPart> {
		E openEditor() throws PartInitException;
	}
}
