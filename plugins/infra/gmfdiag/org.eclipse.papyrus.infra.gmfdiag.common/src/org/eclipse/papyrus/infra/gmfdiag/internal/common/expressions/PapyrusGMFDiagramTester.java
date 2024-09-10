/*****************************************************************************
 * Copyright (c) 2021 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.internal.common.expressions;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.gmfdiag.common.SynchronizableGmfDiagramEditor;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.infra.ui.util.EditorHelper;
import org.eclipse.ui.IEditorPart;

/**
 * This tester is used to check if the current Editor is a Papyrus GMF diagram (and not a papyrus Sirius Diagram) (see bug 577822)
 */
public class PapyrusGMFDiagramTester extends PropertyTester {

	/**
	 * this property is used to know is the current Editor is a Papyrus GMF diagram (and not a papyrus Sirius Diagram)
	 */
	private static final String IS_PAPYRUS_GMF_DIAGRAM_ACTIVE = "isPapyrusGMFDiagramActive";//$NON-NLS-1$


	/**
	 *
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String, java.lang.Object[], java.lang.Object)
	 *
	 * @param receiver
	 * @param property
	 * @param args
	 * @param expectedValue
	 * @return
	 */
	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (IS_PAPYRUS_GMF_DIAGRAM_ACTIVE.equals(property) && receiver instanceof IStructuredSelection && expectedValue instanceof Boolean) {
			return expectedValue.equals(isPapyrusGMFDiagramActive());
		}
		return false;
	}


	/**
	 *
	 * @return
	 *         <code>true</code> if the current active editor represents a Papyrus GMF Editor
	 */
	private final boolean isPapyrusGMFDiagramActive() {
		IEditorPart editorPart = EditorHelper.getCurrentEditor();
		// this code also should work when the SynchronizableGmfDiagramEditor is not embedded in the IMultiDiagramEditor
		if (editorPart instanceof IMultiDiagramEditor) {
			editorPart = ((IMultiDiagramEditor) editorPart).getActiveEditor();
		}
		return editorPart instanceof SynchronizableGmfDiagramEditor;
	}
}
