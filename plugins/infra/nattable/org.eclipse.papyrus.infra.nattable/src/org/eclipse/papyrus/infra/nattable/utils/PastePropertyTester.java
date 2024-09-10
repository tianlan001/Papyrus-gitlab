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
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.utils;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.infra.nattable.provider.TableStructuredSelection;

/**
 * The property tester for the paste action.
 */
public class PastePropertyTester extends PropertyTester {

	/**
	 * The Constant CAN_PASTE_PROPERTY.
	 */
	private static final String CAN_PASTE_PROPERTY = "canPaste"; //$NON-NLS-1$

	/**
	 * The Constant CAN_INSERT_PROPERTY.
	 */
	private static final String CAN_INSERT_PROPERTY = "canInsert"; //$NON-NLS-1$

	/**
	 * Constructor.
	 */
	public PastePropertyTester() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String, java.lang.Object[], java.lang.Object)
	 */
	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		boolean result = false;

		// Try to parse
		TypeSelectionEnum typeSelection = TypeSelectionEnum.NONE;
		if (1 == args.length) {
			typeSelection = TypeSelectionEnum.getTypeSelection(args[0].toString());
		}

		// Manage the can paste property
		if (CAN_PASTE_PROPERTY.equals(property)) {
			if (receiver instanceof TableStructuredSelection) {
				final TableStructuredSelection selection = (TableStructuredSelection) receiver;
				final TableSelectionWrapper tableSelectionWrapper = (TableSelectionWrapper) selection.getAdapter(TableSelectionWrapper.class);
				if (null != tableSelectionWrapper) {
					TypeSelectionEnum typeSelectionFromWrapper = getTypeSelection(tableSelectionWrapper);
					// The paste is allowed for :
					// - empty selection
					// - cells when only cells are selected
					// - rows when only rows are selected (and no single cells selected)
					// - columns when only columns are selected (and no single cells selected)
					result = false;
					
					if (TypeSelectionEnum.CELL.equals(typeSelection)) {
						if(TypeSelectionEnum.NONE.equals(typeSelectionFromWrapper)){
							result = true;
						}else if(TypeSelectionEnum.CELL.equals(typeSelectionFromWrapper)){
							result = tableSelectionWrapper.getFullySelectedColumns().isEmpty()
									&& tableSelectionWrapper.getFullySelectedRows().isEmpty()
									&& !tableSelectionWrapper.getSelectedCells().isEmpty()
									&& tableSelectionWrapper.isContinuousCells();
						}
					} else if (TypeSelectionEnum.ROW.equals(typeSelection) && TypeSelectionEnum.ROW.equals(typeSelectionFromWrapper)) {
						result = tableSelectionWrapper.getFullySelectedColumns().isEmpty()
								&& !tableSelectionWrapper.getFullySelectedRows().isEmpty()
								&& !tableSelectionWrapper.isCellsOutsideOfAxis()
								&& tableSelectionWrapper.isContinuousRows();
					} else if (TypeSelectionEnum.COLUMN.equals(typeSelection) && TypeSelectionEnum.COLUMN.equals(typeSelectionFromWrapper)) {
						result = !tableSelectionWrapper.getFullySelectedColumns().isEmpty()
								&& tableSelectionWrapper.getFullySelectedRows().isEmpty()
								&& !tableSelectionWrapper.isCellsOutsideOfAxis()
								&& tableSelectionWrapper.isContinuousColumns();
					}
				}
			} else if (receiver instanceof StructuredSelection && ((StructuredSelection) receiver).isEmpty()) {
				result = true;
			}
			// Manage the can insert property
		} else if (CAN_INSERT_PROPERTY.equals(property)) {
			if (receiver instanceof TableStructuredSelection) {
				final TableStructuredSelection selection = (TableStructuredSelection) receiver;
				final TableSelectionWrapper tableSelectionWrapper = (TableSelectionWrapper) selection.getAdapter(TableSelectionWrapper.class);
				if (null != tableSelectionWrapper) {
					TypeSelectionEnum typeSelectionFromWrapper = getTypeSelection(tableSelectionWrapper);
					// The paste is allowed for :
					// - empty selection
					// - rows when only rows are selected (and no single cells selected)
					// and not allowed for the cells and columns selection
					result = false;
					if (TypeSelectionEnum.ROW.equals(typeSelection) && TypeSelectionEnum.ROW.equals(typeSelectionFromWrapper)) {
						result = tableSelectionWrapper.getFullySelectedColumns().isEmpty()
								&& !tableSelectionWrapper.getFullySelectedRows().isEmpty()
								&& !tableSelectionWrapper.isCellsOutsideOfAxis()
								&& tableSelectionWrapper.isContinuousRows();
					}
				}
			} else if (receiver instanceof StructuredSelection && ((StructuredSelection) receiver).isEmpty()) {
				result = true;
			}
		}

		return result;
	}

	/**
	 * Get the calculate type selection from the table selection wrapper.
	 * 
	 * @param tableSelectionWrapper
	 *            the table selection wrapper.
	 * @return The type selection corresponding to the table selection wrapper.
	 */
	protected TypeSelectionEnum getTypeSelection(final TableSelectionWrapper tableSelectionWrapper) {
		TypeSelectionEnum result = TypeSelectionEnum.NONE;
		if (!tableSelectionWrapper.getFullySelectedRows().isEmpty() && tableSelectionWrapper.getFullySelectedColumns().isEmpty()) {
			result = TypeSelectionEnum.ROW;
		} else if (tableSelectionWrapper.getFullySelectedRows().isEmpty() && !tableSelectionWrapper.getFullySelectedColumns().isEmpty()) {
			result = TypeSelectionEnum.COLUMN;
		} else if (tableSelectionWrapper.getFullySelectedRows().isEmpty() && tableSelectionWrapper.getFullySelectedColumns().isEmpty() && !tableSelectionWrapper.getSelectedCells().isEmpty()) {
			result = TypeSelectionEnum.CELL;
		}
		return result;
	}
}
