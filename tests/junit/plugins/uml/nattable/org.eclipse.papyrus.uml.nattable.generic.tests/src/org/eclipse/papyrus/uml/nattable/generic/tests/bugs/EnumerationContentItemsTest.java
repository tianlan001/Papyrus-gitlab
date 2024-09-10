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

import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.editor.ComboBoxCellEditor;
import org.eclipse.nebula.widgets.nattable.edit.editor.ICellEditor;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.FeatureIdAxis;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.TableUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.nattable.dataprovider.UMLStereotypeSingleEnumerationComboBoxDataProvider;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * This allows to check the enumeration content items from enumeration in same package, in other package, in other profile and from imported type.
 */
@PluginResource("resources/bugs/bug443814/443814.di")
public class EnumerationContentItemsTest extends AbstractPapyrusTest {

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
	public EnumerationContentItemsTest() {
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
	public void testStereotypePropeties() throws Exception {
		// Open the table and get the manager
		final IPageManager pageManager = fixture.getPageManager();
		final List<Object> pages = pageManager.allPages();
		pageManager.openPage(pages.get(0));
		final IEditorPart part = fixture.getEditor().getActiveEditor();
		Assert.assertTrue(part instanceof NatTableEditor);
		final NatTableEditor editor = (NatTableEditor) part;
		final INattableModelManager currentManager = (INattableModelManager) editor.getAdapter(INattableModelManager.class);
		Assert.assertTrue(currentManager instanceof INattableModelManager);

		// check the initial table content
		checkTableContent(currentManager);

		// Check the first cell with an enumeration in the same package than the stereotype
		checkSamePackageEumerationItems(currentManager);

		// Check the second cell with an enumeration in an other package than the stereotype
		checkOtherPackageEnumerationItems(currentManager);

		// Check the third cell with an enumeration in an other profile than the stereotype
		checkOtherProfileEnumerationItems(currentManager);

		// Check the third cell with an enumeration from imported type
		checkEnumFromExternalItems(currentManager);
	}

	/**
	 * This allows to test the table cell values.
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

		// Check the values
		final Object firstCellDataValue = currentManager.getDataValue(0, 0);
		Assert.assertTrue("The first value is not an enumeration literal", firstCellDataValue instanceof EnumerationLiteral); //$NON-NLS-1$
		Assert.assertEquals("The first value is not the expected one", "itemSamePackage1", ((EnumerationLiteral) firstCellDataValue).getName()); //$NON-NLS-1$ //$NON-NLS-2$
		final Object secondCellDataValue = currentManager.getDataValue(1, 0);
		Assert.assertTrue("The second value is not an enumeration literal", secondCellDataValue instanceof EnumerationLiteral); //$NON-NLS-1$
		Assert.assertEquals("The second value is not the expected one", "itemOtherPackage1", ((EnumerationLiteral) secondCellDataValue).getName()); //$NON-NLS-1$ //$NON-NLS-2$
		final Object thirdCellDataValue = currentManager.getDataValue(2, 0);
		Assert.assertTrue("The third value is not an enumeration literal", thirdCellDataValue instanceof EnumerationLiteral); //$NON-NLS-1$
		Assert.assertEquals("The third value is not the expected one", "itemOtherProfile1", ((EnumerationLiteral) thirdCellDataValue).getName());//$NON-NLS-1$ //$NON-NLS-2$
		final Object fourthCellDataValue = currentManager.getDataValue(3, 0);
		Assert.assertTrue("The fourth value is not an enumeration literal", fourthCellDataValue instanceof ParameterDirectionKind); //$NON-NLS-1$
		Assert.assertEquals("The fourth value is not the expected one", "in", ((ParameterDirectionKind) fourthCellDataValue).getName()); //$NON-NLS-1$ //$NON-NLS-2$

	}

	/**
	 * This allows to check the editor and the values available in the first cell (with enumeration in same package of stereotype).
	 * 
	 * @param currentManager
	 *            The current nattable model manager.
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void checkSamePackageEumerationItems(final INattableModelManager currentManager) throws Exception {
		final ILayerCell cell0 = currentManager.getBodyLayerStack().getCellByPosition(0, 0);

		// Check the cell editor
		final NatTable nattable = currentManager.getAdapter(NatTable.class);
		ICellEditor cell0Editor = nattable.getConfigRegistry().getConfigAttribute(EditConfigAttributes.CELL_EDITOR, DisplayMode.EDIT, cell0.getConfigLabels().getLabels());
		Assert.assertTrue("The cell editor must be a combo box cell editor", cell0Editor instanceof ComboBoxCellEditor); //$NON-NLS-1$

		// Check the available values
		final UMLStereotypeSingleEnumerationComboBoxDataProvider comboBoxDataProvider = new UMLStereotypeSingleEnumerationComboBoxDataProvider(((FeatureIdAxis) currentManager.getColumnElement(0)).getElement(), currentManager.getTableAxisElementProvider());
		final List<?> values = comboBoxDataProvider.getValues(0, 0);
		Assert.assertEquals("The enumeration must have 3 items", 3, values.size()); //$NON-NLS-1$

		Assert.assertEquals("The first item is not the expected one", "itemSamePackage1", ((EEnumLiteral) values.get(0)).getName()); //$NON-NLS-1$ //$NON-NLS-2$
		Assert.assertEquals("The second item is not the expected one", "itemSamePackage2", ((EEnumLiteral) values.get(1)).getName()); //$NON-NLS-1$ //$NON-NLS-2$
		Assert.assertEquals("The third item is not the expected one", "itemSamePackage3", ((EEnumLiteral) values.get(2)).getName()); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * This allows to check the editor and the values available in the second cell (with enumeration in other package than the stereotype).
	 * 
	 * @param currentManager
	 *            The current nattable model manager.
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void checkOtherPackageEnumerationItems(final INattableModelManager currentManager) throws Exception {
		final ILayerCell cell1 = currentManager.getBodyLayerStack().getCellByPosition(1, 0);

		// Check the cell editor
		final NatTable nattable = currentManager.getAdapter(NatTable.class);
		ICellEditor cell1Editor = nattable.getConfigRegistry().getConfigAttribute(EditConfigAttributes.CELL_EDITOR, DisplayMode.EDIT, cell1.getConfigLabels().getLabels());
		Assert.assertTrue("The cell editor must be a combo box cell editor", cell1Editor instanceof ComboBoxCellEditor); //$NON-NLS-1$

		// Check the available values
		final UMLStereotypeSingleEnumerationComboBoxDataProvider comboBoxDataProvider = new UMLStereotypeSingleEnumerationComboBoxDataProvider(((FeatureIdAxis) currentManager.getColumnElement(1)).getElement(), currentManager.getTableAxisElementProvider());
		final List<?> values = comboBoxDataProvider.getValues(1, 0);
		Assert.assertEquals("The enumeration must have 3 items", 3, values.size()); //$NON-NLS-1$

		Assert.assertEquals("The first item is not the expected one", "itemOtherPackage1", ((EEnumLiteral) values.get(0)).getName()); //$NON-NLS-1$ //$NON-NLS-2$
		Assert.assertEquals("The second item is not the expected one", "itemOtherPackage2", ((EEnumLiteral) values.get(1)).getName()); //$NON-NLS-1$ //$NON-NLS-2$
		Assert.assertEquals("The third item is not the expected one", "itemOtherPackage3", ((EEnumLiteral) values.get(2)).getName()); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * This allows to check the editor and the values available in the third cell (with enumeration in other profile than the stereotype).
	 * 
	 * @param currentManager
	 *            The current nattable model manager.
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void checkOtherProfileEnumerationItems(final INattableModelManager currentManager) throws Exception {
		final ILayerCell cell2 = currentManager.getBodyLayerStack().getCellByPosition(2, 0);

		// Check the cell editor
		final NatTable nattable = currentManager.getAdapter(NatTable.class);
		ICellEditor cell2Editor = nattable.getConfigRegistry().getConfigAttribute(EditConfigAttributes.CELL_EDITOR, DisplayMode.EDIT, cell2.getConfigLabels().getLabels());
		Assert.assertTrue("The cell editor must be a combo box cell editor", cell2Editor instanceof ComboBoxCellEditor); //$NON-NLS-1$

		// Check the available values
		final UMLStereotypeSingleEnumerationComboBoxDataProvider comboBoxDataProvider = new UMLStereotypeSingleEnumerationComboBoxDataProvider(((FeatureIdAxis) currentManager.getColumnElement(2)).getElement(), currentManager.getTableAxisElementProvider());
		final List<?> values = comboBoxDataProvider.getValues(2, 0);
		Assert.assertEquals("The enumeration must have 3 items", 3, values.size()); //$NON-NLS-1$

		Assert.assertEquals("The first item is not the expected one", "itemOtherProfile1", ((EEnumLiteral) values.get(0)).getName()); //$NON-NLS-1$ //$NON-NLS-2$
		Assert.assertEquals("The second item is not the expected one", "itemOtherProfile2", ((EEnumLiteral) values.get(1)).getName()); //$NON-NLS-1$ //$NON-NLS-2$
		Assert.assertEquals("The third item is not the expected one", "itemOtherProfile3", ((EEnumLiteral) values.get(2)).getName()); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * This allows to check the editor and the values available in the fourth cell (with enumeration from imported type).
	 * 
	 * @param currentManager
	 *            The current nattable model manager.
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void checkEnumFromExternalItems(final INattableModelManager currentManager) throws Exception {
		final ILayerCell cell0 = currentManager.getBodyLayerStack().getCellByPosition(3, 0);

		// Check the cell editor
		final NatTable nattable = currentManager.getAdapter(NatTable.class);
		ICellEditor cell0Editor = nattable.getConfigRegistry().getConfigAttribute(EditConfigAttributes.CELL_EDITOR, DisplayMode.EDIT, cell0.getConfigLabels().getLabels());
		Assert.assertTrue("The cell editor must be a combo box cell editor", cell0Editor instanceof ComboBoxCellEditor); //$NON-NLS-1$

		// Check the available values
		final UMLStereotypeSingleEnumerationComboBoxDataProvider comboBoxDataProvider = new UMLStereotypeSingleEnumerationComboBoxDataProvider(((FeatureIdAxis) currentManager.getColumnElement(3)).getElement(), currentManager.getTableAxisElementProvider());
		final List<?> values = comboBoxDataProvider.getValues(3, 0);
		Assert.assertEquals("The enumeration must have 4 items", 4, values.size()); //$NON-NLS-1$

		Assert.assertEquals("The first item is not the expected one", ParameterDirectionKind.IN_LITERAL, values.get(0)); //$NON-NLS-1$ //$NON-NLS-2$
		Assert.assertEquals("The second item is not the expected one", ParameterDirectionKind.INOUT_LITERAL, values.get(1)); //$NON-NLS-1$ //$NON-NLS-2$
		Assert.assertEquals("The third item is not the expected one", ParameterDirectionKind.OUT_LITERAL, values.get(2)); //$NON-NLS-1$ //$NON-NLS-2$
		Assert.assertEquals("The fourth item is not the expected one", ParameterDirectionKind.RETURN_LITERAL, values.get(3)); //$NON-NLS-1$ //$NON-NLS-2$
	}

}
