/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Shuai Li (CEAL LIST) <shuai.li@cea.fr>
 *
 *****************************************************************************/
package org.eclipse.papyrus.editor;

import org.eclipse.papyrus.infra.core.sasheditor.editor.IPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IPageChangedListener;

public class PapyrusNavigationHistorySynchronizer implements IPageChangedListener {

	private final PapyrusMultiDiagramEditor myMultiDiagramEditor;

	public PapyrusNavigationHistorySynchronizer(PapyrusMultiDiagramEditor multiEditor) {
		myMultiDiagramEditor = multiEditor;
		//synchronizeNavigationHistory(); // Mark the first page when the editor is opened
	}

	@Override
	public void pageChanged(IPage newPage) {
		synchronizeNavigationHistory();
	}
	
	private void synchronizeNavigationHistory() {
		myMultiDiagramEditor.getSite().getPage().getNavigationHistory().markLocation(myMultiDiagramEditor);
	}
}
