/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
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
 *    Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.stereotyped.elements.tests.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.widgets.util.NavigationTarget;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.Abstraction;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * This allows to test the allocation table with dynamic SysML allocation.
 */
@PluginResource("/resources/allocation/modelSysMLRequirement.di")
public class RevealAllocationTableTest extends AbstractPapyrusTest {

	/**
	 * The papyrus fixture.
	 */
	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();

	/** Di Resource. */
	protected Resource di;

	/** Notation Resource. */
	protected Resource notation;

	/** UML Resource. */
	protected Resource uml;

	/** The root model. */
	protected Model rootModel;

	/** The list of elements in model. */
	protected List<Abstraction> elementListTest;

	/** The test package. */
	protected Package packageTest;

	/** The abstraction. */
	protected Abstraction allocateAbstraction;


	/**
	 * Constructor.
	 * This plugin test the ModelExplorerView.
	 */
	public RevealAllocationTableTest() {
		super();
	}

	/**
	 * Initialize tests.
	 * 
	 * @throws CoreException
	 *             The core exception.
	 * @throws IOException
	 *             The input/output file exception.
	 */
	@Before
	public void initTests() throws CoreException, IOException {

		final ResourceSet set = fixture.getResourceSet();

		final List<Resource> resources = set.getResources();

		for (final Resource current : resources) {
			if (current.getURI().lastSegment().equals("modelSysMLRequirement.uml")) { //$NON-NLS-1$
				this.uml = current;
			} else if (current.getURI().lastSegment().equals("modelSysMLRequirement.notation")) { //$NON-NLS-1$
				this.notation = current;
			} else if (current.getURI().lastSegment().equals("modelSysMLRequirement.di")) { //$NON-NLS-1$
				this.di = current;
			}
		}

		this.rootModel = (Model) fixture.getModel();
	}


	/**
	 * Select an element from the model and seek it in the allocation table,
	 * then proceed to check that its row has been correctly selected and that none of the columns are.
	 */
	@Test
	public void test1SelectElement() {
		// select the element
		this.allocateAbstraction = (Abstraction) rootModel.getPackagedElements().get(1);

		// select the page, corresponding to the requirement table used to test the behavior, in the multidiagram view
		fixture.openTable("AllocationTable0"); //$NON-NLS-1$
		fixture.activateTable("AllocationTable0"); //$NON-NLS-1$

		final INattableModelManager manager = fixture.getActiveTableManager();
		// verify that the axis is not inverted
		if (manager.getTable().isInvertAxis()) {
			manager.invertAxis();
		}

		Assert.assertTrue(fixture.getActiveTableEditor() instanceof NavigationTarget);
		// spoofs the behavior when the link with editor button is activated
		((NavigationTarget) fixture.getActiveTableEditor()).revealElement(this.allocateAbstraction);
		final ISelectionService serv = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();
		final ISelection selection = serv.getSelection();

		Assert.assertTrue(selection instanceof IStructuredSelection);
		final Object currentAxisObject = ((IStructuredSelection) selection).getFirstElement();
		final Object currentRealObject = AxisUtils.getRepresentedElement(currentAxisObject);
		// verify that the same element has been selected on both sides
		Assert.assertTrue("failed to match the selection with: " + this.allocateAbstraction, this.allocateAbstraction.equals(currentRealObject)); //$NON-NLS-1$

		final SelectionLayer layer = manager.getBodyLayerStack().getSelectionLayer();
		final int[] rowSelectedPositions = layer.getFullySelectedRowPositions();
		final int[] columnSelectedPositions = layer.getFullySelectedColumnPositions();
		// verify that the line selected is indeed the line corresponding to the element selected in the model explorer
		Assert.assertTrue("index of row does not match: " + rowSelectedPositions[0], rowSelectedPositions[0] == 0); //$NON-NLS-1$
		Assert.assertTrue("number of selected columns does not match: " + columnSelectedPositions.length, columnSelectedPositions.length == 0); //$NON-NLS-1$

	}

	/**
	 * Select an element from the model and seek it in the allocation table that has had its axis inverted,
	 * then proceed to check that its column has been correctly selected and that none of the rows are.
	 */
	@Test
	public void test2SelectElementInvertAxis() {
		this.allocateAbstraction = (Abstraction) rootModel.getPackagedElements().get(1);

		fixture.openTable("AllocationTable0"); //$NON-NLS-1$
		fixture.activateTable("AllocationTable0"); //$NON-NLS-1$

		final INattableModelManager manager = fixture.getActiveTableManager();
		if (!manager.getTable().isInvertAxis()) {
			manager.invertAxis();
		}

		Assert.assertTrue(fixture.getActiveTableEditor() instanceof NavigationTarget);
		((NavigationTarget) fixture.getActiveTableEditor()).revealElement(this.allocateAbstraction);
		final ISelectionService serv = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();
		final ISelection selection = serv.getSelection();

		Assert.assertTrue(selection instanceof IStructuredSelection);
		final Object currentAxisObject = ((IStructuredSelection) selection).getFirstElement();
		final Object currentRealObject = AxisUtils.getRepresentedElement(currentAxisObject);
		Assert.assertTrue("failed to match the selection with: " + this.allocateAbstraction, this.allocateAbstraction.equals(currentRealObject)); //$NON-NLS-1$

		final SelectionLayer layer = manager.getBodyLayerStack().getSelectionLayer();
		final int[] rowSelectedPositions = layer.getFullySelectedRowPositions();
		final int[] columnSelectedPositions = layer.getFullySelectedColumnPositions();
		Assert.assertTrue("index of row does not match: " + columnSelectedPositions[0], columnSelectedPositions[0] == 0); //$NON-NLS-1$
		Assert.assertTrue("number of selected columns does not match: " + rowSelectedPositions.length, rowSelectedPositions.length == 0); //$NON-NLS-1$

	}

	/**
	 * Select multiple elements from the model and seek them in the allocation table,
	 * then proceed to check that their rows have been correctly selected and that none of the columns are.
	 */
	@Test
	public void test3SelectMultipleElements() {

		this.elementListTest = buildList();

		fixture.openTable("AllocationTable0"); //$NON-NLS-1$
		fixture.activateTable("AllocationTable0"); //$NON-NLS-1$

		final INattableModelManager manager = fixture.getActiveTableManager();
		if (manager.getTable().isInvertAxis()) {
			manager.invertAxis();
		}

		Assert.assertTrue(fixture.getActiveTableEditor() instanceof NavigationTarget);
		((NavigationTarget) fixture.getActiveTableEditor()).revealElement(this.elementListTest);
		final ISelectionService serv = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();
		final ISelection selection = serv.getSelection();

		Assert.assertTrue(selection instanceof IStructuredSelection);
		final List<?> selectedRowElements = ((IStructuredSelection) selection).toList();
		for (int index = 0; index < selectedRowElements.size(); index++) {
			final Object currentAxisObject = selectedRowElements.get(index);
			final Object currentRealObject = AxisUtils.getRepresentedElement(currentAxisObject);
			Assert.assertTrue("failed to match the selection", this.elementListTest.contains(currentRealObject)); //$NON-NLS-1$
		}

		final SelectionLayer layer = manager.getBodyLayerStack().getSelectionLayer();
		final int[] rowSelectedPositions = layer.getFullySelectedRowPositions();
		final int[] columnSelectedPositions = layer.getFullySelectedColumnPositions();

		Assert.assertTrue("index of row does not match", rowSelectedPositions.length == 3); //$NON-NLS-1$
		final List<?> rowSelectedPositionsAsList = Arrays.asList(rowSelectedPositions[0], rowSelectedPositions[1], rowSelectedPositions[2]);
		Assert.assertTrue("index of row does not match", rowSelectedPositionsAsList.contains(0)); //$NON-NLS-1$
		Assert.assertTrue("index of row does not match", rowSelectedPositionsAsList.contains(1)); //$NON-NLS-1$
		Assert.assertTrue("index of row does not match", rowSelectedPositionsAsList.contains(2)); //$NON-NLS-1$
		Assert.assertTrue("number of selected columns does not match: " + columnSelectedPositions.length, columnSelectedPositions.length == 0); //$NON-NLS-1$

	}

	/**
	 * Select multiple elements from the model and seek them in the allocation table that has had its axis inverted,
	 * then proceed to check that their columns have been correctly selected and that none of the rows are.
	 */
	@Test
	public void test4SelectMultipleElementsInvertAxis() {

		this.elementListTest = buildList();

		fixture.openTable("AllocationTable0"); //$NON-NLS-1$
		fixture.activateTable("AllocationTable0"); //$NON-NLS-1$

		final INattableModelManager manager = fixture.getActiveTableManager();
		if (!manager.getTable().isInvertAxis()) {
			manager.invertAxis();
		}

		Assert.assertTrue(fixture.getActiveTableEditor() instanceof NavigationTarget);
		((NavigationTarget) fixture.getActiveTableEditor()).revealElement(this.elementListTest);
		final ISelectionService serv = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();
		final ISelection selection = serv.getSelection();

		Assert.assertTrue(selection instanceof IStructuredSelection);
		final List<?> selectedRowElements = ((IStructuredSelection) selection).toList();
		for (int index = 0; index < selectedRowElements.size(); index++) {
			final Object currentAxisObject = selectedRowElements.get(index);
			final Object currentRealObject = AxisUtils.getRepresentedElement(currentAxisObject);
			Assert.assertTrue("failed to match the selection", this.elementListTest.contains(currentRealObject)); //$NON-NLS-1$
		}

		final SelectionLayer layer = manager.getBodyLayerStack().getSelectionLayer();
		final int[] rowSelectedPositions = layer.getFullySelectedRowPositions();
		final int[] columnSelectedPositions = layer.getFullySelectedColumnPositions();

		Assert.assertTrue("index of row does not match", columnSelectedPositions.length == 3); //$NON-NLS-1$
		final List<?> columnSelectedPositionsAsList = Arrays.asList(columnSelectedPositions[0], columnSelectedPositions[1], columnSelectedPositions[2]);
		Assert.assertTrue("index of row does not match", columnSelectedPositionsAsList.contains(0)); //$NON-NLS-1$
		Assert.assertTrue("index of row does not match", columnSelectedPositionsAsList.contains(1)); //$NON-NLS-1$
		Assert.assertTrue("index of row does not match", columnSelectedPositionsAsList.contains(2)); //$NON-NLS-1$
		Assert.assertTrue("number of selected columns does not match: " + rowSelectedPositions.length, rowSelectedPositions.length == 0); //$NON-NLS-1$

	}

	/**
	 * This allows to build the list of abstractions in model.
	 * 
	 * @return The list of abstractions available in model.
	 */
	protected List<Abstraction> buildList() {
		final List<Abstraction> listAbs = new ArrayList<Abstraction>();
		this.allocateAbstraction = (Abstraction) rootModel.getPackagedElements().get(1);
		listAbs.add(this.allocateAbstraction);
		this.allocateAbstraction = (Abstraction) rootModel.getPackagedElements().get(2);
		listAbs.add(this.allocateAbstraction);
		this.allocateAbstraction = (Abstraction) rootModel.getPackagedElements().get(3);
		listAbs.add(this.allocateAbstraction);

		return listAbs;
	}

}
