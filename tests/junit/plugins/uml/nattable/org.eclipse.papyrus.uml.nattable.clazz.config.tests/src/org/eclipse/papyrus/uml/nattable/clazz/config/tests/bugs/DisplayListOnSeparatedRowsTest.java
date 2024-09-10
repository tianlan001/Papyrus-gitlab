/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 515737
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.clazz.config.tests.bugs;

import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.papyrus.infra.nattable.applynamedstyle.PapyrusApplyNamedStyleCommand;
import org.eclipse.papyrus.infra.nattable.applynamedstyle.PapyrusApplyNamedStyleCommandHandler;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.BooleanValueStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.nattable.utils.NamedStyleConstants;
import org.eclipse.papyrus.junit.utils.rules.ActiveTable;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.nattable.clazz.config.tests.tests.AbstractTableTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test the execution of handler for the {@link PapyrusApplyNamedStyleCommand} command with the displayListOnSeparatedRows named style.
 */
@PluginResource("resources/bugs/bug515737/DisplayListOnSeparatedRowsTest.di")
public class DisplayListOnSeparatedRowsTest extends AbstractTableTest {

	/** The column axis to apply the named style. */
	protected IAxis columnAxis = null;

	/**
	 * Default constructor.
	 */
	public DisplayListOnSeparatedRowsTest() {
		super();
	}

	/**
	 * This allows to test the application of named style.
	 *
	 * @throws Exception
	 *             The caught exception
	 */
	@Test
	@ActiveTable("ClassTreeTable")
	public void testDisplayListOnSeparatedRowsNamedStyle() throws Exception {
		startTest();

		// Unregister and register the handler for the PapyrusApplyNamedStyleCommand
		final GridLayer gridLayer = this.manager.getGridLayer();
		gridLayer.unregisterCommandHandler(PapyrusApplyNamedStyleCommand.class);
		gridLayer.registerCommandHandler(new PapyrusApplyNamedStyleCommandHandler());

		selectColumn(1);

		// Get the displayListOnSeparatedRows named style for the selected column axis
		BooleanValueStyle namedStyle = (BooleanValueStyle) this.columnAxis.getNamedStyle(NattablestylePackage.eINSTANCE.getBooleanValueStyle(), NamedStyleConstants.DISPLAY_LIST_ON_SEPARATED_ROWS);
		// The named style must not exist by default
		Assert.assertNull(namedStyle);

		// Do the command to apply the named style displayListOnSeparatedRows
		natTable.doCommand(new PapyrusApplyNamedStyleCommand(getTableEditingDomain(), this.columnAxis, NamedStyleConstants.DISPLAY_LIST_ON_SEPARATED_ROWS));
		namedStyle = (BooleanValueStyle) this.columnAxis.getNamedStyle(NattablestylePackage.eINSTANCE.getBooleanValueStyle(), NamedStyleConstants.DISPLAY_LIST_ON_SEPARATED_ROWS);
		// The named style must not be null
		Assert.assertNotNull(namedStyle);
		// And its value must be true
		Assert.assertTrue(namedStyle.isBooleanValue());

		// Redo the command
		natTable.doCommand(new PapyrusApplyNamedStyleCommand(getTableEditingDomain(), this.columnAxis, NamedStyleConstants.DISPLAY_LIST_ON_SEPARATED_ROWS));
		namedStyle = (BooleanValueStyle) this.columnAxis.getNamedStyle(NattablestylePackage.eINSTANCE.getBooleanValueStyle(), NamedStyleConstants.DISPLAY_LIST_ON_SEPARATED_ROWS);
		// Now the named style must be deleted successfully
		Assert.assertNull(namedStyle);
	}

	/**
	 * Select a column to apply named style.
	 */
	protected void selectColumn(final int columnOrder) {
		this.columnAxis = manager.getTable().getCurrentColumnAxisProvider().getAxis().get(columnOrder);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkFillingConfigurationAndHiddenCategoryForTestConsistency(Table table, String simpleClassName) {
		// Do nothing about the name of the class and the model
	}
}
