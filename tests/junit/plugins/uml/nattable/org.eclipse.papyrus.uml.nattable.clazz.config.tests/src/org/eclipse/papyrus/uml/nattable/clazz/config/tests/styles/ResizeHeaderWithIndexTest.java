/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:k
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.clazz.config.tests.styles;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.resize.command.ColumnResizeCommand;
import org.eclipse.nebula.widgets.nattable.resize.command.RowResizeCommand;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AbstractHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.IntValueStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.nattable.utils.DefaultSizeUtils;
import org.eclipse.papyrus.infra.nattable.utils.NamedStyleConstants;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.junit.Assert;

/**
 * This allows to test the resize of the header (width of each rows' column header and height of column header).
 */
@PluginResource("resources/styles/resize/ResizeHeaderWithIndexTest.di") //$NON-NLS-1$
public class ResizeHeaderWithIndexTest extends ResizeHeaderWithoutIndexTest {

	/**
	 * Constructor.
	 */
	public ResizeHeaderWithIndexTest() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.styles.ResizeHeaderWithoutIndexTest#resizeColumnheader(org.eclipse.nebula.widgets.nattable.layer.DataLayer, org.eclipse.nebula.widgets.nattable.layer.DataLayer)
	 */
	@Override
	protected void resizeColumnheader(DataLayer tableColumnIndexHeaderLayer, DataLayer tableColumnLabelHeaderLayer) {
		// Resize the row header
		final NatTable natTable = (NatTable) currentManager.getAdapter(NatTable.class);
		natTable.doCommand(new RowResizeCommand(tableColumnIndexHeaderLayer, 0, 50));

		super.resizeColumnheader(tableColumnIndexHeaderLayer, tableColumnLabelHeaderLayer);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.styles.ResizeHeaderWithoutIndexTest#checkInitialColumnHeaderHeight(org.eclipse.nebula.widgets.nattable.layer.DataLayer, org.eclipse.nebula.widgets.nattable.layer.DataLayer)
	 */
	@Override
	protected void checkInitialColumnHeaderHeight(final DataLayer tableColumnIndexHeaderLayer, final DataLayer tableColumnLabelHeaderLayer) {
		Assert.assertEquals("The height of the index is initialized with the default value", DefaultSizeUtils.getDefaultCellHeight(), tableColumnIndexHeaderLayer.getRowHeightByPosition(0));
		super.checkInitialColumnHeaderHeight(tableColumnIndexHeaderLayer, tableColumnLabelHeaderLayer);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.styles.ResizeHeaderWithoutIndexTest#checkModifiedColumnHeaderHeight(org.eclipse.nebula.widgets.nattable.layer.DataLayer, org.eclipse.nebula.widgets.nattable.layer.DataLayer, org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AbstractHeaderAxisConfiguration)
	 */
	@Override
	protected void checkModifiedColumnHeaderHeight(final DataLayer tableColumnIndexHeaderLayer, final DataLayer tableColumnLabelHeaderLayer, final AbstractHeaderAxisConfiguration columnHeader) {
		Assert.assertEquals("The height of the index is modified", 50, tableColumnIndexHeaderLayer.getRowHeightByPosition(0));

		final IntValueStyle valueColumnIndex = (IntValueStyle) columnHeader.getNamedStyle(NattablestylePackage.eINSTANCE.getIntValueStyle(), NamedStyleConstants.COLUMN_INDEX_HEIGHT);
		Assert.assertNotNull("The NamedStyle for the index must not be null", valueColumnIndex);
		Assert.assertEquals("The NamedStyle for the index must be modified", 50, valueColumnIndex.getIntValue());

		super.checkModifiedColumnHeaderHeight(tableColumnIndexHeaderLayer, tableColumnLabelHeaderLayer, columnHeader);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.styles.ResizeHeaderWithoutIndexTest#resizeRowheader(org.eclipse.nebula.widgets.nattable.layer.DataLayer, org.eclipse.nebula.widgets.nattable.layer.DataLayer)
	 */
	@Override
	protected void resizeRowheader(DataLayer tableRowIndexHeaderLayer, DataLayer tableRowLabelHeaderLayer) {
		// Resize the row header
		final NatTable natTable = (NatTable) currentManager.getAdapter(NatTable.class);
		natTable.doCommand(new ColumnResizeCommand(tableRowIndexHeaderLayer, 0, 50));

		super.resizeRowheader(tableRowIndexHeaderLayer, tableRowLabelHeaderLayer);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.styles.ResizeHeaderWithoutIndexTest#checkInitialRowHeaderWidth(org.eclipse.nebula.widgets.nattable.layer.DataLayer, org.eclipse.nebula.widgets.nattable.layer.DataLayer)
	 */
	@Override
	protected void checkInitialRowHeaderWidth(final DataLayer tableRowIndexHeaderLayer, final DataLayer tableRowLabelHeaderLayer) {
		Assert.assertEquals("The width of the index is initialized with the default value", DefaultSizeUtils.getDefaultCellWidth(), tableRowIndexHeaderLayer.getRowHeightByPosition(0));

		super.checkInitialRowHeaderWidth(tableRowIndexHeaderLayer, tableRowLabelHeaderLayer);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.styles.ResizeHeaderWithoutIndexTest#checkModifiedRowHeaderWidth(org.eclipse.nebula.widgets.nattable.layer.DataLayer, org.eclipse.nebula.widgets.nattable.layer.DataLayer, org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AbstractHeaderAxisConfiguration)
	 */
	@Override
	protected void checkModifiedRowHeaderWidth(final DataLayer tableRowIndexHeaderLayer, final DataLayer tableRowLabelHeaderLayer, final AbstractHeaderAxisConfiguration rowHeader) {
		Assert.assertEquals("The width of the index is modified", 50, tableRowIndexHeaderLayer.getColumnWidthByPosition(0));

		final IntValueStyle valueColumnIndex = (IntValueStyle) rowHeader.getNamedStyle(NattablestylePackage.eINSTANCE.getIntValueStyle(), NamedStyleConstants.ROW_INDEX_WIDTH);
		Assert.assertNotNull("The NamedStyle for the index must not be null", valueColumnIndex);
		Assert.assertEquals("The NamedStyle for the index must be modified", 50, valueColumnIndex.getIntValue());

		super.checkModifiedRowHeaderWidth(tableRowIndexHeaderLayer, tableRowLabelHeaderLayer, rowHeader);
	}
}
