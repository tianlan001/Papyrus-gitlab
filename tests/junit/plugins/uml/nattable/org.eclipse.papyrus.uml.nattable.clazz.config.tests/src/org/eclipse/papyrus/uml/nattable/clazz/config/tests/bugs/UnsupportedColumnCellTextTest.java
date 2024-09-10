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
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 515806
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.clazz.config.tests.bugs;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.nattable.Activator;
import org.eclipse.papyrus.infra.nattable.manager.cell.ICellManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.preferences.pages.CellPreferencePage;
import org.eclipse.papyrus.junit.utils.rules.ActiveTable;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.nattable.clazz.config.tests.tests.AbstractTableTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test the unsupported column cell text in the bug 515806.
 */
@SuppressWarnings("nls")
@PluginResource("resources/bugs/bug515806/UnsupportedColumnCellTextTest.di")
public class UnsupportedColumnCellTextTest extends AbstractTableTest {

	/** The test text for unsupported column. */
	protected static final String UNSUPPORTED_COLUMN_NEW_TEXT = "Unsupported Column";

	/**
	 * Default constructor.
	 */
	public UnsupportedColumnCellTextTest() {
		super();
	}

	/**
	 * This tests the update of the unsupported column cell text when its default value in the preference store is modified.
	 *
	 * @throws Exception
	 *             The caught exception
	 */
	@Test
	@ActiveTable("ClassTreeTable")
	public void testUnsupportedColumnCellText() throws Exception {
		startTest();

		// Check the default unsupported column cell text
		Assert.assertEquals(ICellManager.NOT_AVALAIBLE, this.manager.getBodyLayerStack().getViewportLayer().getDataValueByPosition(1, 1));
		Assert.assertEquals(ICellManager.NOT_AVALAIBLE, this.manager.getBodyLayerStack().getViewportLayer().getDataValueByPosition(1, 2));

		// Change the new value for the related preference
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setValue(CellPreferencePage.UNSUPPORTED_COLUMN_CELL_TEXT, UNSUPPORTED_COLUMN_NEW_TEXT);

		// Check the updated cell text
		Assert.assertEquals(UNSUPPORTED_COLUMN_NEW_TEXT, this.manager.getBodyLayerStack().getViewportLayer().getDataValueByPosition(1, 1));
		Assert.assertEquals(UNSUPPORTED_COLUMN_NEW_TEXT, this.manager.getBodyLayerStack().getViewportLayer().getDataValueByPosition(1, 2));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkFillingConfigurationAndHiddenCategoryForTestConsistency(Table table, String simpleClassName) {
		// Do nothing about the name of the class and the model
	}
}
