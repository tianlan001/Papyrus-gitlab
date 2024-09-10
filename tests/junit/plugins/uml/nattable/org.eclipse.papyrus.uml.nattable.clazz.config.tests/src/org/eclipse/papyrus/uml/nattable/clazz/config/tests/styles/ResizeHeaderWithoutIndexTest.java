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
 * This allows to test the resize of the header (width of each rows' column header and height of column header) when the index is not displayed.
 */
@PluginResource("resources/styles/resize/ResizeHeaderWithoutIndexTest.di") //$NON-NLS-1$
public class ResizeHeaderWithoutIndexTest extends AbstractResizeHeaderTest {

	/**
	 * Constructor.
	 */
	public ResizeHeaderWithoutIndexTest() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.styles.AbstractResizeHeaderTest#resizeColumnheader(org.eclipse.nebula.widgets.nattable.layer.DataLayer, org.eclipse.nebula.widgets.nattable.layer.DataLayer)
	 */
	@Override
	protected void resizeColumnheader(final DataLayer tableColumnIndexHeaderLayer, final DataLayer tableColumnLabelHeaderLayer) {
		// Resize the row header
		final NatTable natTable = (NatTable) currentManager.getAdapter(NatTable.class);
		natTable.doCommand(new RowResizeCommand(tableColumnLabelHeaderLayer, 0, 75));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.styles.AbstractResizeHeaderTest#checkInitialColumnHeaderHeight(org.eclipse.nebula.widgets.nattable.layer.DataLayer, org.eclipse.nebula.widgets.nattable.layer.DataLayer)
	 */
	@Override
	protected void checkInitialColumnHeaderHeight(final DataLayer tableColumnIndexHeaderLayer, final DataLayer tableColumnLabelHeaderLayer) {
		Assert.assertEquals("The height of the label is initialized with the default value", DefaultSizeUtils.getDefaultCellHeight(), tableColumnLabelHeaderLayer.getRowHeightByPosition(0)); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.styles.AbstractResizeHeaderTest#checkModifiedColumnHeaderHeight(org.eclipse.nebula.widgets.nattable.layer.DataLayer, org.eclipse.nebula.widgets.nattable.layer.DataLayer,
	 *      org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AbstractHeaderAxisConfiguration)
	 */
	@Override
	protected void checkModifiedColumnHeaderHeight(final DataLayer tableColumnIndexHeaderLayer, final DataLayer tableColumnLabelHeaderLayer, final AbstractHeaderAxisConfiguration columnHeader) {
		Assert.assertEquals("The height of the label is modified", 75, tableColumnLabelHeaderLayer.getRowHeightByPosition(0)); //$NON-NLS-1$

		final IntValueStyle valueColumnLabel = (IntValueStyle) columnHeader.getNamedStyle(NattablestylePackage.eINSTANCE.getIntValueStyle(), NamedStyleConstants.COLUMN_LABEL_HEIGHT);
		Assert.assertNotNull("The NamedStyle for the label must not be null", valueColumnLabel); //$NON-NLS-1$
		Assert.assertEquals("The NamedStyle for the label must be modified", 75, valueColumnLabel.getIntValue()); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.styles.AbstractResizeHeaderTest#resizeRowheader(org.eclipse.nebula.widgets.nattable.layer.DataLayer, org.eclipse.nebula.widgets.nattable.layer.DataLayer)
	 */
	@Override
	protected void resizeRowheader(final DataLayer tableRowIndexHeaderLayer, final DataLayer tableRowLabelHeaderLayer) {
		// Resize the row header
		final NatTable natTable = (NatTable) currentManager.getAdapter(NatTable.class);
		natTable.doCommand(new ColumnResizeCommand(tableRowLabelHeaderLayer, 0, 75));
		natTable.doCommand(new ColumnResizeCommand(tableRowLabelHeaderLayer, 1, 100));
		natTable.doCommand(new ColumnResizeCommand(tableRowLabelHeaderLayer, 2, 10));
		natTable.doCommand(new ColumnResizeCommand(tableRowLabelHeaderLayer, 3, 35));
		natTable.doCommand(new ColumnResizeCommand(tableRowLabelHeaderLayer, 4, 75));
		natTable.doCommand(new ColumnResizeCommand(tableRowLabelHeaderLayer, 5, 90));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.styles.AbstractResizeHeaderTest#checkInitialRowHeaderWidth(org.eclipse.nebula.widgets.nattable.layer.DataLayer, org.eclipse.nebula.widgets.nattable.layer.DataLayer)
	 */
	protected void checkInitialRowHeaderWidth(final DataLayer tableRowIndexHeaderLayer, final DataLayer tableRowLabelHeaderLayer) {
		for (int i = 0; i < tableRowLabelHeaderLayer.getColumnCount(); i++) {
			Assert.assertEquals("The width of the label is initialized with the default value", DefaultSizeUtils.getDefaultCellWidth(), tableRowLabelHeaderLayer.getRowHeightByPosition(i)); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.styles.AbstractResizeHeaderTest#checkModifiedRowHeaderWidth(org.eclipse.nebula.widgets.nattable.layer.DataLayer, org.eclipse.nebula.widgets.nattable.layer.DataLayer,
	 *      org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AbstractHeaderAxisConfiguration)
	 */
	@Override
	protected void checkModifiedRowHeaderWidth(final DataLayer tableRowIndexHeaderLayer, final DataLayer tableRowLabelHeaderLayer, final AbstractHeaderAxisConfiguration rowHeader) {
		Assert.assertEquals("The width of the label is modified", 75, tableRowLabelHeaderLayer.getColumnWidthByPosition(0)); //$NON-NLS-1$
		Assert.assertEquals("The width of the label is modified", 100, tableRowLabelHeaderLayer.getColumnWidthByPosition(1)); //$NON-NLS-1$
		Assert.assertEquals("The width of the label is modified", 10, tableRowLabelHeaderLayer.getColumnWidthByPosition(2)); //$NON-NLS-1$
		Assert.assertEquals("The width of the label is modified", 35, tableRowLabelHeaderLayer.getColumnWidthByPosition(3)); //$NON-NLS-1$
		Assert.assertEquals("The width of the label is modified", 75, tableRowLabelHeaderLayer.getColumnWidthByPosition(4)); //$NON-NLS-1$
		Assert.assertEquals("The width of the label is modified", 90, tableRowLabelHeaderLayer.getColumnWidthByPosition(5)); //$NON-NLS-1$

		final IntValueStyle valueRowLabel = (IntValueStyle) rowHeader.getNamedStyle(NattablestylePackage.eINSTANCE.getIntValueStyle(), NamedStyleConstants.ROW_LABEL_WIDTH);
		Assert.assertNotNull("The NamedStyle for the label must not be null", valueRowLabel); //$NON-NLS-1$
		Assert.assertEquals("The NamedStyle for the label must be modified", 75, valueRowLabel.getIntValue()); //$NON-NLS-1$

		final StringBuilder nameStyle = new StringBuilder(NamedStyleConstants.ROW_LABEL_POSITION_PREFIX_WIDTH);
		nameStyle.append(NAME_STYLE_TEMPLATE_TO_REPLACE);
		nameStyle.append(NamedStyleConstants.ROW_LABEL_POSITION_SUFFIX_WIDTH);

		IntValueStyle valueColumn2Label = (IntValueStyle) rowHeader.getNamedStyle(NattablestylePackage.eINSTANCE.getIntValueStyle(), nameStyle.toString().replace(NAME_STYLE_TEMPLATE_TO_REPLACE, "2"));
		Assert.assertNotNull("The NamedStyle for the label must not be null", valueColumn2Label); //$NON-NLS-1$
		Assert.assertEquals("The NamedStyle for the label must be modified", 100, valueColumn2Label.getIntValue()); //$NON-NLS-1$

		IntValueStyle valueColumn3Label = (IntValueStyle) rowHeader.getNamedStyle(NattablestylePackage.eINSTANCE.getIntValueStyle(), nameStyle.toString().replace(NAME_STYLE_TEMPLATE_TO_REPLACE, "3"));
		Assert.assertNotNull("The NamedStyle for the label must not be null", valueColumn3Label); //$NON-NLS-1$
		Assert.assertEquals("The NamedStyle for the label must be modified", 10, valueColumn3Label.getIntValue()); //$NON-NLS-1$

		IntValueStyle valueColumn4Label = (IntValueStyle) rowHeader.getNamedStyle(NattablestylePackage.eINSTANCE.getIntValueStyle(), nameStyle.toString().replace(NAME_STYLE_TEMPLATE_TO_REPLACE, "4"));
		Assert.assertNotNull("The NamedStyle for the label must not be null", valueColumn4Label); //$NON-NLS-1$
		Assert.assertEquals("The NamedStyle for the label must be modified", 35, valueColumn4Label.getIntValue()); //$NON-NLS-1$

		IntValueStyle valueColumn5Label = (IntValueStyle) rowHeader.getNamedStyle(NattablestylePackage.eINSTANCE.getIntValueStyle(), nameStyle.toString().replace(NAME_STYLE_TEMPLATE_TO_REPLACE, "5"));
		Assert.assertNotNull("The NamedStyle for the label must not be null", valueColumn5Label); //$NON-NLS-1$
		Assert.assertEquals("The NamedStyle for the label must be modified", 75, valueColumn5Label.getIntValue()); //$NON-NLS-1$

		IntValueStyle valueColumn6Label = (IntValueStyle) rowHeader.getNamedStyle(NattablestylePackage.eINSTANCE.getIntValueStyle(), nameStyle.toString().replace(NAME_STYLE_TEMPLATE_TO_REPLACE, "6"));
		Assert.assertNotNull("The NamedStyle for the label must not be null", valueColumn6Label); //$NON-NLS-1$
		Assert.assertEquals("The NamedStyle for the label must be modified", 90, valueColumn6Label.getIntValue()); //$NON-NLS-1$
	}
}
