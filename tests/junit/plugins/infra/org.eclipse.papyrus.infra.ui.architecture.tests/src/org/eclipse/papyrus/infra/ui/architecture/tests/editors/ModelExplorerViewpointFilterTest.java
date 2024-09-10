/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.architecture.tests.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.ui.architecture.tests.Activator;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.views.modelexplorer.ModelExplorerPageBookView;
import org.eclipse.papyrus.views.modelexplorer.tests.AbstractHandlerTest;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.junit.Assert;
import org.junit.Test;

/**
 * This class allows to test the viewpoint filter in the ModelExplorer.
 * Currently, all the diagrams and tables should be visible (bug 550567).
 */
@SuppressWarnings("nls")
@PluginResource("/resources/ModelExplorerViewpointFilter.di")
public class ModelExplorerViewpointFilterTest extends AbstractHandlerTest {

	/**
	 * Constructor.
	 */
	public ModelExplorerViewpointFilterTest() {
		super(Activator.getDefault().getBundle());
	}

	/**
	 * This allows to test the viewpoint filter for diagrams and tables.
	 * 
	 * @throws ServiceException
	 *             The service Exception.
	 */
	@Test
	public void revealDiagramsAndTables() throws ServiceException {

		// Manage the selection
		final List<Object> selectedElement = new ArrayList<>();
		final List<Diagram> diagrams = getDiagrams();
		final List<Table> tables = getTables();

		// select all the diagrams and tables
		selectedElement.addAll(diagrams);
		selectedElement.addAll(tables);

		getModelExplorerView().revealSemanticElement(selectedElement);
		RunnableWithResult<IWorkbenchPart> activePartRunnable;
		Display.getDefault().syncExec(activePartRunnable = new RunnableWithResult.Impl<IWorkbenchPart>() {

			public void run() {
				IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				IWorkbenchPart activePart = activePage.getActivePart();
				setResult(activePart);
			}
		});

		Assert.assertTrue("The active part is not the ModelExplorer", activePartRunnable.getResult() instanceof ModelExplorerPageBookView);
		final IStructuredSelection currentSelection = getCurrentSelection();
		// There are 4 diagrams and tables, no one should be hidden
		Assert.assertEquals("Diagrams and tables shouln't be hidden", 4, currentSelection.size());
	}
}
