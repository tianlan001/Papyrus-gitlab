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
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.clazz.config.tests.bugs;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.TreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EStructuralFeatureAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.FeatureIdAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.tree.CollapseAndExpandActionsEnum;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.TableUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * This class allows to test the destroy column action.
 */
@PluginResource("resources/bugs/bug492086/DestroyColumn.di") //$NON-NLS-1$
public class DestroyColumnTest extends AbstractPapyrusTest {

	/**
	 * The table name.
	 */
	private static final String TABLE_NAME = "ClassTreeTable0"; //$NON-NLS-1$

	/**
	 * The papyrus fixture.
	 */
	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();

	/**
	 * The current model.
	 */
	public Model model = null;

	/**
	 * The nattable model manager.
	 */
	public INattableModelManager currentManager = null;

	/**
	 * Constructor.
	 */
	public DestroyColumnTest() {
		super();
	}

	/**
	 * Initialize.
	 */
	@Before
	public void init() {
		// Get the model
		model = (Model) fixture.getModel();
		Assert.assertNotNull("RootModel is null", model); //$NON-NLS-1$

		// Get the table and open it
		final Table mainTable = TableUtils.getNotationFirstTable(fixture.getModelSet(), TABLE_NAME);
		fixture.getPageManager().openPage(mainTable);
		fixture.flushDisplayEvents();

		// Get the table and its manager
		final IPageManager pageManager = fixture.getPageManager();
		final List<Object> pages = pageManager.allPages();
		pageManager.openPage(pages.get(0));
		final IEditorPart part = fixture.getEditor().getActiveEditor();
		Assert.assertTrue(part instanceof NatTableEditor);
		final NatTableEditor editor = (NatTableEditor) part;
		currentManager = (INattableModelManager) editor.getAdapter(INattableModelManager.class);
		Assert.assertTrue("The table manage must be a tree table manager", currentManager instanceof TreeNattableModelManager); //$NON-NLS-1$

		((TreeNattableModelManager) currentManager).doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		fixture.flushDisplayEvents();
	}

	/**
	 * This allows to test the destroy axis of a feature on a class tree table with undo/redo.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	@Test
	public void testDestroyFeatureColumn() throws Exception {
		// Get the editing domain
		final TransactionalEditingDomain editingDomain = fixture.getEditingDomain();
		Assert.assertNotNull("The editing domain cannot be null", editingDomain); //$NON-NLS-1$

		for(int repreat=0; repreat<=1; repreat++){
			// Check initial columns
			checkInitialColumns();
			
			// Try to destroy the second column
			List<Integer> axisToDestroy = new ArrayList<Integer>(1);
			axisToDestroy.add(1);
			currentManager.getColumnAxisManager().destroyAxis(axisToDestroy);
			
			// Check the columns after the destroy axis
			fixture.flushDisplayEvents();
			checkWithoutFeatureAxis();
	
			// Check the columns after the undo action
			undo(editingDomain);
			checkInitialColumns();
	
			// Check the columns after the redo destroy axis
			redo(editingDomain);
			checkWithoutFeatureAxis();
			
			// Undo to be like the opened table
			undo(editingDomain);
		}
	}
	
	/**
	 * This allows to test the destroy axis of a stereotype property on a class tree table with undo/redo.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	@Test
	public void testDestroyStereotypePropertyColumn() throws Exception {
		// Get the editing domain
		final TransactionalEditingDomain editingDomain = fixture.getEditingDomain();
		Assert.assertNotNull("The editing domain cannot be null", editingDomain); //$NON-NLS-1$

		for(int repreat=0; repreat<=1; repreat++){
			// Check initial columns
			checkInitialColumns();
			
			// Try to destroy the fourth column
			List<Integer> axisToDestroy = new ArrayList<Integer>(1);
			axisToDestroy.add(3);
			currentManager.getColumnAxisManager().destroyAxis(axisToDestroy);
			
			// Check the columns after the destroy axis
			fixture.flushDisplayEvents();
			checkWithoutStereotypePropertyAxis();
	
			// Check the columns after the undo action
			undo(editingDomain);
			checkInitialColumns();
	
			// Check the columns after the redo destroy axis
			redo(editingDomain);
			checkWithoutStereotypePropertyAxis();
			
			// Undo to be like the opened table
			undo(editingDomain);
		}
	}

	/**
	 * This allows to undo the previous command.
	 * 
	 * @param editingDomain
	 *            The current editing domain.
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void undo(final TransactionalEditingDomain editingDomain) throws Exception {
		editingDomain.getCommandStack().undo();
		((TreeNattableModelManager) currentManager).doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		fixture.flushDisplayEvents();
	}

	/**
	 * This allows to redo the previous command.
	 * 
	 * @param editingDomain
	 *            The current editing domain.
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void redo(final TransactionalEditingDomain editingDomain) throws Exception {
		editingDomain.getCommandStack().redo();
		((TreeNattableModelManager) currentManager).doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		fixture.flushDisplayEvents();
	}

	/**
	 * This allows to test the initial columns of the table (size and column elements).
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void checkInitialColumns() throws Exception {
		final List<Object> columnElements = currentManager.getColumnElementsList();
		Assert.assertEquals("The table content size is not the correct one", 4, columnElements.size()); //$NON-NLS-1$

		checkTableColumns(true, true);
	}

	/**
	 * This allows to test the columns of the table (size and column elements) when the attribute column is destroy.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void checkWithoutFeatureAxis() throws Exception {
		final List<Object> columnElements = currentManager.getColumnElementsList();
		Assert.assertEquals("The table content size is not the correct one", 3, columnElements.size()); //$NON-NLS-1$
		
		checkTableColumns(false, true);
	}
	
	/**
	 * This allows to test the columns of the table (size and column elements) when the stereotype property column is destroy.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void checkWithoutStereotypePropertyAxis() throws Exception {
		final List<Object> columnElements = currentManager.getColumnElementsList();
		Assert.assertEquals("The table content size is not the correct one", 3, columnElements.size()); //$NON-NLS-1$
		
		checkTableColumns(true, false);
	}

	/**
	 * This allows to check the table content with/without attribute column.
	 * 
	 * @param hasAttributeColumn
	 *            Boolean to determinate if the attribute column is available.
	 * @param hasStereotypePropertyColumn Boolean to determinate if the stereotype property is available.
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void checkTableColumns(final boolean hasAttributeColumn, final boolean hasStereotypePropertyColumn) throws Exception {
		int index = 0;

		final List<Object> columnElements = currentManager.getColumnElementsList();

		final IAxis firstColumn = (IAxis) columnElements.get(index++);
		Assert.assertTrue("The first column must be a structural feature axis", firstColumn instanceof EStructuralFeatureAxis); //$NON-NLS-1$
		Assert.assertEquals("The first column element must be the name feature", UMLPackage.eINSTANCE.getNamedElement_Name(),((EStructuralFeatureAxis)firstColumn).getElement()); //$NON-NLS-1$

		if (hasAttributeColumn) {
			final IAxis secondColumn = (IAxis) columnElements.get(index++);
			Assert.assertTrue("The first column must be a structural feature axis", secondColumn instanceof EStructuralFeatureAxis); //$NON-NLS-1$
			Assert.assertEquals("The first column element must be the name feature", UMLPackage.eINSTANCE.getClassifier_Attribute(),((EStructuralFeatureAxis)secondColumn).getElement()); //$NON-NLS-1$
		}
		
		final IAxis thirdColumn = (IAxis) columnElements.get(index++);
		Assert.assertTrue("The first column must be a structural feature axis", thirdColumn instanceof EStructuralFeatureAxis); //$NON-NLS-1$
		Assert.assertEquals("The first column element must be the name feature", UMLPackage.eINSTANCE.getBehavioredClassifier_ClassifierBehavior(),((EStructuralFeatureAxis)thirdColumn).getElement()); //$NON-NLS-1$
		
		if (hasStereotypePropertyColumn) {
			final IAxis fourthColumn = (IAxis) columnElements.get(index++);
			Assert.assertTrue("The first column must be a structural feature axis", fourthColumn instanceof FeatureIdAxis); //$NON-NLS-1$
			Assert.assertEquals("The first column element must be the name feature", "property_of_stereotype:/RootElement::Stereotype1::Attribute1",((FeatureIdAxis)fourthColumn).getElement()); //$NON-NLS-1$ //$NON-NLS-2$
		}

	}
}