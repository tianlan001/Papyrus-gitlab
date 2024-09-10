/*****************************************************************************
 * Copyright (c) 2012, 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 515737
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.utils;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EOperationAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.AbstractAxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.ISlaveAxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.DisplayStyle;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;


public class NattableWidgetPropertyTester extends PropertyTester {

	public static final String IS_NATTABLE_WIDGET = "isNattableWidget"; //$NON-NLS-1$

	/**
	 * The unbounded multiplicity number.
	 *
	 * @since 4.0
	 */
	protected static final int UNBOUNDED_MULTIPLICITY = -1;

	/**
	 * The string to test if one axis with multiple values is selected.
	 *
	 * @since 4.0
	 */
	protected static final String IS_ONE_MULTIPLE_VALUES_AXIS_SELECTED = "isOneMultipleValuesAxisSelected"; //$NON-NLS-1$

	private static final String HAS_FEATURE_ROW_HEADER_CONFIGURATION = "hasFeatureRowHeaderConfiguration"; //$NON-NLS-1$

	private static final String HAS_FEATURE_COLUMN_HEADER_CONFIGURATION = "hasFeatureColumnHeaderConfiguration"; //$NON-NLS-1$

	private static final String HAS_SLAVE_ROWS_AXIS_PROVIDER = "hasSlaveRowAxisProvider"; //$NON-NLS-1$

	private static final String HAS_SLAVE_COLUMNS_AXIS_PROVIDER = "hasSlaveColumnAxisProvider"; //$NON-NLS-1$

	private static final String CAN_INVERT_AXIS = "canInvertAxis";//$NON-NLS-1$

	private static final String IS_HIERARCHIC_TABLE = "isHierarchicTable"; //$NON-NLS-1$

	@Override
	public boolean test(final Object receiver, final String property, final Object[] args, final Object expectedValue) {
		final INattableModelManager manager = getNattableModelManager();

		if (IS_NATTABLE_WIDGET.equals(property) && expectedValue instanceof Boolean) {
			return expectedValue.equals(null != manager) && ((!(Boolean) expectedValue) || manager.getAdapter(NatTable.class).isFocusControl());
		}
		if (manager != null && expectedValue instanceof Boolean) {
			if (HAS_FEATURE_ROW_HEADER_CONFIGURATION.equals(property)) {
				LabelConfigurationManagementUtils.getRowFeatureLabelConfigurationInTable(manager.getTable());
				return expectedValue.equals(LabelConfigurationManagementUtils.hasRowFeatureLabelConfiguration(manager.getTable()));
			} else if (HAS_FEATURE_COLUMN_HEADER_CONFIGURATION.equals(property)) {
				return expectedValue.equals(LabelConfigurationManagementUtils.hasColumnFeatureLabelConfiguration(manager.getTable()));
			} else if (HAS_SLAVE_COLUMNS_AXIS_PROVIDER.equals(property)) {
				return expectedValue.equals(AxisUtils.getAxisProviderUsedForColumns(manager) instanceof ISlaveAxisProvider);
			} else if (HAS_SLAVE_ROWS_AXIS_PROVIDER.equals(property)) {
				return expectedValue.equals(AxisUtils.getAxisProviderUsedForRows(manager) instanceof ISlaveAxisProvider);
			} else if (CAN_INVERT_AXIS.equals(property)) {
				return expectedValue.equals(manager.canInvertAxis());
			} else if (IS_HIERARCHIC_TABLE.equals(property)) {
				return expectedValue.equals(isHierarchicTable(manager));
			} else if (IS_ONE_MULTIPLE_VALUES_AXIS_SELECTED.equals(property)) {

				// Get the selected axis index
				final int axisIndex = AxisUtils.getUniqueSelectedAxisIndex(manager);

				// Always get the column axis provider for invert or non-invert table
				final AbstractAxisProvider axisProvider = manager.getTable().getCurrentColumnAxisProvider();

				// If the index is in range
				if (null != axisProvider && null != axisProvider.getAxis() && 0 <= axisIndex && axisIndex < axisProvider.getAxis().size()) {
					final IAxis selectedAxis = axisProvider.getAxis().get(axisIndex);

					// EOperationAxis is not supported in this moment as there is no cell editor configuration for operation axis
					if (!(selectedAxis instanceof EOperationAxis)) {
						// Get the selected axis element
						final Object axisElement = selectedAxis.getElement();

						// Check its upper bound if typed element can be casted from the axis element
						if (axisElement instanceof ETypedElement) {
							final ETypedElement typedElement = (ETypedElement) axisElement;
							return expectedValue.equals(isMultipleValues(typedElement.getUpperBound()));
						}
					}
				}
			}
		}

		return false;
	}

	/**
	 * @param table
	 * @return
	 */
	public static final boolean isHierarchicTable(final INattableModelManager tableManager) {
		final DisplayStyle style = TableHelper.getTableDisplayStyle(tableManager);
		return (DisplayStyle.HIERARCHIC_SINGLE_TREE_COLUMN.equals(style) || DisplayStyle.HIERARCHIC_MULTI_TREE_COLUMN.equals(style));
	}

	/**
	 *
	 * @return
	 * 		the current nattable model manager or <code>null</code> if not found
	 */
	protected INattableModelManager getNattableModelManager() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchPart current = null;
		if (workbench != null) {
			final IWorkbenchWindow activeWorkbench = workbench.getActiveWorkbenchWindow();
			if (activeWorkbench != null) {
				final IWorkbenchPage activePage = activeWorkbench.getActivePage();
				if (activePage != null) {
					current = activePage.getActivePart();
				}
			}
		}

		if (current != null) {
			return current.getAdapter(INattableModelManager.class);
		}
		return null;
	}

	/**
	 * @param upperBound
	 *            The upper bound to be checked
	 * @return <code>true</code> if upper bound > 1 or unbounded, <code>false</code> otherwise
	 * @since 4.0
	 */
	protected boolean isMultipleValues(final int upperBound) {
		return (upperBound > 1) || (UNBOUNDED_MULTIPLICITY == upperBound);
	}
}
