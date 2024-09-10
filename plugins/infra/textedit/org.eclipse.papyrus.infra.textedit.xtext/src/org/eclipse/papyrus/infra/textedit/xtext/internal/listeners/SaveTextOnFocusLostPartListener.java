/*****************************************************************************
 * Copyright (c) 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.textedit.xtext.internal.listeners;

import org.eclipse.papyrus.infra.textedit.xtext.nested.editor.PapyrusXTextEditor;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.part.WorkbenchPart;

/**
 * This class is used to be notified on changes on the XTextEditor
 */
public class SaveTextOnFocusLostPartListener implements IPartListener2 {

	/**
	 * the listen editor
	 */
	private final PapyrusXTextEditor editor;

	/**
	 *
	 * Constructor.
	 *
	 * @param editor
	 *            the listen editor
	 */
	public SaveTextOnFocusLostPartListener(final PapyrusXTextEditor editor) {
		this.editor = editor;
	}

	/**
	 *
	 * @see org.eclipse.ui.IPartListener2#partActivated(org.eclipse.ui.IWorkbenchPartReference)
	 *
	 * @param partRef
	 */
	@Override
	public void partActivated(IWorkbenchPartReference partRef) {
		// nothing to do
	}

	/**
	 *
	 * @see org.eclipse.ui.IPartListener2#partBroughtToTop(org.eclipse.ui.IWorkbenchPartReference)
	 *
	 * @param partRef
	 */
	@Override
	public void partBroughtToTop(IWorkbenchPartReference partRef) {
		// nothing to do
	};

	/**
	 *
	 * @see org.eclipse.ui.IPartListener2#partClosed(org.eclipse.ui.IWorkbenchPartReference)
	 *
	 * @param partRef
	 */
	@Override
	public void partClosed(IWorkbenchPartReference partRef) {
		if (isExpectedEditor(partRef)) {
			this.editor.saveTextInEditedModel();
		}
	};

	/**
	 *
	 * @see org.eclipse.ui.IPartListener2#partHidden(org.eclipse.ui.IWorkbenchPartReference)
	 *
	 * @param partRef
	 */
	@Override
	public void partHidden(IWorkbenchPartReference partRef) {
		if (isExpectedEditor(partRef)) {
			this.editor.saveTextInEditedModel();
		}
	};

	/**
	 *
	 * @see org.eclipse.ui.IPartListener2#partDeactivated(org.eclipse.ui.IWorkbenchPartReference)
	 *
	 * @param partRef
	 */
	@Override
	public void partDeactivated(IWorkbenchPartReference partRef) {
		if (isExpectedEditor(partRef)) {
			this.editor.saveTextInEditedModel();
		}
	};

	/**
	 *
	 * @see org.eclipse.ui.IPartListener2#partVisible(org.eclipse.ui.IWorkbenchPartReference)
	 *
	 * @param partRef
	 */
	@Override
	public void partVisible(IWorkbenchPartReference partRef) {
		// nothing to do
	};

	/**
	 *
	 * @see org.eclipse.ui.IPartListener2#partOpened(org.eclipse.ui.IWorkbenchPartReference)
	 *
	 * @param partRef
	 */
	@Override
	public void partOpened(IWorkbenchPartReference partRef) {
		// nothing to do
	};

	/**
	 *
	 * @param part
	 *            a {@link WorkbenchPart}
	 * @return
	 *         <code>true</code> if the current part is the editor to listen
	 */
	protected boolean isExpectedEditor(final IWorkbenchPartReference partRef) {
		final IWorkbenchPart part = partRef.getPart(false);
		if (part instanceof IMultiDiagramEditor) {
			final IEditorPart activeEditor = ((IMultiDiagramEditor) part).getActiveEditor();
			return activeEditor == this.editor;
		}
		return false;
	}
}
