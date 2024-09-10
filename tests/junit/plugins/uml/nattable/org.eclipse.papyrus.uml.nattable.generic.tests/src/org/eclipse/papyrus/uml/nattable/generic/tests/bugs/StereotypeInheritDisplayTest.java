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

package org.eclipse.papyrus.uml.nattable.generic.tests.bugs;

import java.util.List;

import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.TableUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.nattable.config.UMLStereotypeSingleBooleanCellEditorConfiguration;
import org.eclipse.papyrus.uml.nattable.config.UMLStereotypeSingleIntegerCellEditorConfiguration;
import org.eclipse.papyrus.uml.nattable.config.UMLStereotypeSingleRealCellEditorConfiguration;
import org.eclipse.papyrus.uml.nattable.config.UMLStereotypeSingleStringCellEditorConfiguration;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.Model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * This allows to test the stereotype inherit properties in the table.
 */
@PluginResource("resources/bugs/bug435417/435417.di")
public class StereotypeInheritDisplayTest extends AbstractPapyrusTest {

	/**
	 * The table name.
	 */
	private static final String TABLE_NAME = "GenericTable0"; //$NON-NLS-1$

	/**
	 * The papyrus fixture.
	 */
	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();

	/**
	 * Constructor.
	 */
	public StereotypeInheritDisplayTest() {
		super();
	}

	/**
	 * This allow to initialize the tests.
	 */
	@Before
	public void init() {
		final Model model = (Model) fixture.getModel();
		Assert.assertNotNull("RootModel is null", model); //$NON-NLS-1$

		// Get the table and open it
		Table mainTable = TableUtils.getNotationFirstTable(fixture.getModelSet(), TABLE_NAME);
		fixture.getPageManager().openPage(mainTable);
		fixture.flushDisplayEvents();
	}

	/**
	 * This allows to test the stereotype inherited properties displayed in the table
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	@Test
	public void testStereotypeProperties() throws Exception {
		// Open the table and get the manager
		final IPageManager pageManager = fixture.getPageManager();
		final List<Object> pages = pageManager.allPages();
		pageManager.openPage(pages.get(0));
		final IEditorPart part = fixture.getEditor().getActiveEditor();
		Assert.assertTrue(part instanceof NatTableEditor);
		final NatTableEditor editor = (NatTableEditor) part;
		final INattableModelManager currentManager = (INattableModelManager) editor.getAdapter(INattableModelManager.class);
		Assert.assertTrue(currentManager instanceof INattableModelManager);

		checkTableContent(currentManager);
	}

	/**
	 * This allows to test the table cell editors and values.
	 * 
	 * @param currentManager
	 *            The current nattable model manager.
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void checkTableContent(final INattableModelManager currentManager) throws Exception {
		// check the rows and columns number
		Assert.assertEquals("The rows number is not the expected one", 1, currentManager.getRowCount()); //$NON-NLS-1$
		Assert.assertEquals("The columns number is not the expected one", 4, currentManager.getColumnCount()); //$NON-NLS-1$

		// Check the cell editors
		final ILayerCell cell0 = currentManager.getBodyLayerStack().getCellByPosition(0, 0);
		Assert.assertNotEquals("The first cell must have config labels", null, cell0.getConfigLabels()); //$NON-NLS-1$
		Assert.assertNotEquals("The first cell must have at least one cell editor configuration", 0, cell0.getConfigLabels().getLabels().size()); //$NON-NLS-1$
		Assert.assertEquals("The first cell editor is not the expected one", UMLStereotypeSingleStringCellEditorConfiguration.ID + "0", cell0.getConfigLabels().getLabels().get(0));//$NON-NLS-1$

		final ILayerCell cell1 = currentManager.getBodyLayerStack().getCellByPosition(1, 0);
		Assert.assertNotEquals("The second cell must have config labels", null, cell1.getConfigLabels()); //$NON-NLS-1$
		Assert.assertNotEquals("The second cell must have at least one cell editor configuration", 0, cell1.getConfigLabels().getLabels().size()); //$NON-NLS-1$
		Assert.assertEquals("The second cell editor is not the expected one", UMLStereotypeSingleBooleanCellEditorConfiguration.ID + "1", cell1.getConfigLabels().getLabels().get(0));//$NON-NLS-1$

		final ILayerCell cell2 = currentManager.getBodyLayerStack().getCellByPosition(2, 0);
		Assert.assertNotEquals("The third cell must have config labels", null, cell2.getConfigLabels()); //$NON-NLS-1$
		Assert.assertNotEquals("The third cell must have at least one cell editor configuration", 0, cell2.getConfigLabels().getLabels().size()); //$NON-NLS-1$
		Assert.assertEquals("The third cell editor is not the expected one", UMLStereotypeSingleIntegerCellEditorConfiguration.ID + "2", cell2.getConfigLabels().getLabels().get(0)); //$NON-NLS-1$

		final ILayerCell cell3 = currentManager.getBodyLayerStack().getCellByPosition(3, 0);
		Assert.assertNotEquals("The fourth cell must have config labels", null, cell3.getConfigLabels()); //$NON-NLS-1$
		Assert.assertNotEquals("The fourth cell must have at least one cell editor configuration", 0, cell3.getConfigLabels().getLabels().size()); //$NON-NLS-1$
		Assert.assertEquals("The fourth cell editor is not the expected one", UMLStereotypeSingleRealCellEditorConfiguration.ID + "3", cell3.getConfigLabels().getLabels().get(0));//$NON-NLS-1$

		// Check the values
		Assert.assertEquals("The first value is not the expected one", "Vincent", currentManager.getDataValue(0, 0)); //$NON-NLS-1$ //$NON-NLS-2$
		Assert.assertEquals("The second value is not the expected one", true, currentManager.getDataValue(1, 0)); //$NON-NLS-1$
		Assert.assertEquals("The third value is not the expected one", 30, currentManager.getDataValue(2, 0));//$NON-NLS-1$
		Assert.assertEquals("The fourth value is not the expected one", 68.5, currentManager.getDataValue(3, 0)); //$NON-NLS-1$

	}

}
