/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST.
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
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 515806
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.stereotype.display.manager.cell;

import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.nattable.manager.cell.ICellManager;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.CellHelper;
import org.eclipse.papyrus.infra.ui.converter.AbstractStringValueConverter;
import org.eclipse.papyrus.uml.nattable.stereotype.display.utils.StereotypeDisplayTreeTableConstants;
import org.eclipse.papyrus.uml.nattable.stereotype.display.utils.StereotypeDisplayTreeTableHelper;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * The stereotype display tree table cell manager.
 */
public class StereotypeDisplayTreeTableCellManager implements ICellManager {

	/**
	 * The helper class instance.
	 */
	private StereotypeDisplayTreeTableHelper helper = StereotypeDisplayTreeTableHelper.getInstance();

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.ICellManager#handles(java.lang.Object, java.lang.Object, INattableModelManager)
	 */
	@Override
	public boolean handles(final Object columnElement, final Object rowElement, INattableModelManager tableManager) {
		boolean handles = false;
		Object element = AxisUtils.getRepresentedElement(columnElement);
		if (element instanceof String) {
			handles = ((String) element).startsWith(StereotypeDisplayTreeTableConstants.PREFIX);
		}

		return handles;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.ICellManager#getValue(java.lang.Object, java.lang.Object, org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 */
	@Override
	public Object getValue(final Object columnElement, final Object rowElement, final INattableModelManager tableManager) {
		Object result = ICellManager.EMPTY_STRING;
		Object element = AxisUtils.getRepresentedElement(columnElement);

		if (element instanceof String) {
			String shortElement = StereotypeDisplayTreeTableHelper.getInstance().getShortValue(element);
			switch (shortElement) {
			case StereotypeDisplayTreeTableConstants.NAME_DEPTH:
				result = helper.getDepthValue(rowElement);
				break;
			case StereotypeDisplayTreeTableConstants.IS_DISPLAYED:
				result = helper.getDisplayedValue(rowElement);
				break;
			case StereotypeDisplayTreeTableConstants.IN_BRACE:
				result = helper.getBraceValue(rowElement);
				break;
			case StereotypeDisplayTreeTableConstants.IN_COMMENT:
				result = helper.getCommentValue(rowElement);
				break;
			case StereotypeDisplayTreeTableConstants.IN_COMPARTMENT:
				result = helper.getCompartmentValue(rowElement);
				break;
			default:
				break;
			}
		}
		return result;


	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.ICellManager#setValue(org.eclipse.emf.transaction.TransactionalEditingDomain, java.lang.Object, java.lang.Object, java.lang.Object,
	 *      org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 */
	@Override
	public void setValue(final TransactionalEditingDomain domain, final Object columnElement, final Object rowElement, final Object newValue, final INattableModelManager tableManager) {
		Object element = AxisUtils.getRepresentedElement(columnElement);

		if (element instanceof String) {
			String shortElement = StereotypeDisplayTreeTableHelper.getInstance().getShortValue(element);
			switch (shortElement) {
			case StereotypeDisplayTreeTableConstants.NAME_DEPTH:
				helper.setDepthValue(domain, rowElement, newValue);
				break;
			case StereotypeDisplayTreeTableConstants.IS_DISPLAYED:
				helper.setDisplayedValue(domain, rowElement, newValue);
				break;
			case StereotypeDisplayTreeTableConstants.IN_BRACE:
				helper.setBraceValue(domain, rowElement, newValue);
				break;
			case StereotypeDisplayTreeTableConstants.IN_COMMENT:
				helper.setCommentValue(domain, rowElement, newValue);
				break;
			case StereotypeDisplayTreeTableConstants.IN_COMPARTMENT:
				helper.setCompartmentValue(domain, rowElement, newValue);
				break;
			default:
				break;


			}

		}
	}



	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.ICellManager#isCellEditable(java.lang.Object, java.lang.Object, INattableModelManager)
	 */
	@Override
	public boolean isCellEditable(final Object columnElement, final Object rowElement, INattableModelManager tableManager) {
		boolean result = true;

		// Disable the isVisible for the properties
		Object column = AxisUtils.getRepresentedElement(columnElement);
		if (column instanceof String) {
			String shortElement = StereotypeDisplayTreeTableHelper.getInstance().getShortValue(column);

			if (StereotypeDisplayTreeTableConstants.IS_DISPLAYED.equals(shortElement)) {
				Object row = AxisUtils.getRepresentedElement(rowElement);

				if (row instanceof Property) {
					result = false;
				}
			}

			if (StereotypeDisplayTreeTableConstants.NAME_DEPTH.equals(shortElement)) {
				Object row = AxisUtils.getRepresentedElement(rowElement);

				if (!(row instanceof Stereotype)) {
					result = false;
				}
			}
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.ICellManager#getSetValueCommand(org.eclipse.emf.transaction.TransactionalEditingDomain, java.lang.Object, java.lang.Object, java.lang.Object,
	 *      org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 */
	@Override
	public Command getSetValueCommand(final TransactionalEditingDomain domain, final Object columnElement, final Object rowElement, final Object newValue, final INattableModelManager tableManager) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.ICellManager#getSetStringValueCommand(org.eclipse.emf.transaction.TransactionalEditingDomain, java.lang.Object, java.lang.Object, java.lang.String,
	 *      org.eclipse.papyrus.infra.ui.converter.AbstractStringValueConverter, org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 */
	@Override
	public Command getSetStringValueCommand(final TransactionalEditingDomain domain, final Object columnElement, final Object rowElement, final String newValue, final AbstractStringValueConverter valueConverter, final INattableModelManager tableManager) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.ICellManager#getOrCreateStringValueConverterClass(java.util.Map, java.lang.String, org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 */
	@Override
	public AbstractStringValueConverter getOrCreateStringValueConverterClass(final Map<Class<? extends AbstractStringValueConverter>, AbstractStringValueConverter> existingConverters, final String multiValueSeparator,
			final INattableModelManager tableManager) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.ICellManager#isCellEditable(java.lang.Object, java.lang.Object, java.util.Map, INattableModelManager)
	 */
	@Override
	public boolean isCellEditable(final Object columnElement, final Object rowElement, final Map<?, ?> sharedMap, INattableModelManager tableManager) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.ICellManager#setStringValue(java.lang.Object, java.lang.Object, java.lang.String, org.eclipse.papyrus.infra.ui.converter.AbstractStringValueConverter,
	 *      java.util.Map, org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 */
	@Override
	public void setStringValue(final Object columnElement, final Object rowElement, final String valueAsString, final AbstractStringValueConverter valueConverter, final Map<?, ?> sharedMap, final INattableModelManager tableManager) {
		// Nothing to do
	}

	/**
	 * @return the text of cell to be displayed with unsupported column
	 */
	@Override
	public String getUnsupportedCellContentsText() {
		return CellHelper.getUnsupportedCellContentsText();
	}
}
