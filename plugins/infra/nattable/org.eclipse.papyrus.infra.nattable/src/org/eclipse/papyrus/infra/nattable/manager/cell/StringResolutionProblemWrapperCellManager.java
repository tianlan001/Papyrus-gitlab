/*****************************************************************************
 * Copyright (c) 2015, 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.manager.cell;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecell.Cell;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableproblem.Problem;
import org.eclipse.papyrus.infra.nattable.utils.CellHelper;
import org.eclipse.papyrus.infra.ui.converter.AbstractStringValueConverter;

/**
 * This wrapper manage the display and the creation/destruction of the StringResolutionProblem associated to the cells
 *
 */
public class StringResolutionProblemWrapperCellManager implements IUnsetValueCellManager {

	/**
	 * the wrapped cell manager
	 */
	private ICellManager wrappedCellManager;

	/**
	 * Constructor.
	 *
	 */
	public StringResolutionProblemWrapperCellManager(ICellManager wrappedCellManager) {
		this.wrappedCellManager = wrappedCellManager;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.ICellManager#handles(java.lang.Object, java.lang.Object, INattableModelManager)
	 *
	 * @param columnElement
	 * @param rowElement
	 * @return
	 */
	@Override
	public boolean handles(Object columnElement, Object rowElement, INattableModelManager tableManager) {
		return this.wrappedCellManager.handles(columnElement, rowElement, tableManager);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.ICellManager#getValue(java.lang.Object, java.lang.Object, org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 *
	 * @param columnElement
	 * @param rowElement
	 * @param tableManager
	 * @return
	 *         this method returns the value currently contains by the cell. It is the "real" value OR the problem attached to the cell when there is problem (copy/paster error)
	 */
	@Override
	public Object getValue(Object columnElement, Object rowElement, INattableModelManager tableManager) {
		final Cell cell = tableManager.getCell(columnElement, rowElement);
		if (cell != null) {
			final Collection<Problem> problems = cell.getProblems();
			if (problems.size() != 0) {
				return problems;
			}
		}
		return this.wrappedCellManager.getValue(columnElement, rowElement, tableManager);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.ICellManager#getValue(java.lang.Object, java.lang.Object, org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 *
	 * @param columnElement
	 * @param rowElement
	 * @param tableManager
	 * @return
	 *         this method returns the "real" value to display in the cell ignoring the problem attached to the cell
	 */
	public Object getValueIgnoringCellProblem(Object columnElement, Object rowElement, INattableModelManager tableManager) {
		return this.wrappedCellManager.getValue(columnElement, rowElement, tableManager);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.ICellManager#setValue(org.eclipse.emf.transaction.TransactionalEditingDomain, java.lang.Object, java.lang.Object, java.lang.Object,
	 *      org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 *
	 * @param domain
	 * @param columnElement
	 * @param rowElement
	 * @param newValue
	 * @param tableManager
	 */
	@Override
	public void setValue(final TransactionalEditingDomain domain, final Object columnElement, final Object rowElement, final Object newValue, final INattableModelManager tableManager) {
		final Command destroyStringProblemCommand = CellHelper.getDestroyStringResolutionProblemCommand(domain, columnElement, rowElement, newValue, tableManager);
		//we destroy existing problem associated to the cell when we change its value
		if (destroyStringProblemCommand != null) {
			RecordingCommand rc = new RecordingCommand(domain) {

				@Override
				protected void doExecute() {
					destroyStringProblemCommand.execute();
					wrappedCellManager.setValue(domain, columnElement, rowElement, newValue, tableManager);
				}
			};
			domain.getCommandStack().execute(rc);
		} else {
			this.wrappedCellManager.setValue(domain, columnElement, rowElement, newValue, tableManager);
		}

	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.ICellManager#isCellEditable(java.lang.Object, java.lang.Object, INattableModelManager)
	 *
	 * @param columnElement
	 * @param rowElement
	 * @return
	 */
	@Override
	public boolean isCellEditable(Object columnElement, Object rowElement, INattableModelManager tableManager) {
		return this.wrappedCellManager.isCellEditable(columnElement, rowElement, tableManager);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.ICellManager#getSetValueCommand(org.eclipse.emf.transaction.TransactionalEditingDomain, java.lang.Object, java.lang.Object, java.lang.Object,
	 *      org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 *
	 * @param domain
	 * @param columnElement
	 * @param rowElement
	 * @param newValue
	 * @param tableManager
	 * @return
	 */
	@Override
	public Command getSetValueCommand(TransactionalEditingDomain domain, Object columnElement, Object rowElement, Object newValue, INattableModelManager tableManager) {
		Command cmd = this.wrappedCellManager.getSetValueCommand(domain, columnElement, rowElement, newValue, tableManager);
		//we destroy existing problem associated to the cell when we change its value
		Command destroyStringProblem = CellHelper.getDestroyStringResolutionProblemCommand(domain, columnElement, rowElement, newValue, tableManager);
		if (cmd != null && destroyStringProblem != null && destroyStringProblem.canExecute()) {
			return cmd.chain(destroyStringProblem);
		}
		return cmd;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.ICellManager#getSetStringValueCommand(org.eclipse.emf.transaction.TransactionalEditingDomain, java.lang.Object, java.lang.Object, java.lang.String,
	 *      org.eclipse.papyrus.infra.ui.converter.AbstractStringValueConverter, org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 *
	 * @param domain
	 * @param columnElement
	 * @param rowElement
	 * @param newValue
	 * @param valueConverter
	 * @param tableManager
	 * @return
	 */
	@Override
	public Command getSetStringValueCommand(TransactionalEditingDomain domain, Object columnElement, Object rowElement, String newValue, AbstractStringValueConverter valueConverter, INattableModelManager tableManager) {
		CompoundCommand cc = new CompoundCommand();

		Command setValueCommand = this.wrappedCellManager.getSetStringValueCommand(domain, columnElement, rowElement, newValue, valueConverter, tableManager);
		if (setValueCommand != null && setValueCommand.canExecute()) {
			cc.append(setValueCommand);
			// we need to destroy existing associated cell problem. It could be the case when we edit the cell with an Xtext editor
			Command destroyExistingCellStringResolutionProblemCommand = CellHelper.getDestroyStringResolutionProblemCommand(domain, columnElement, rowElement, newValue, tableManager);
			if (destroyExistingCellStringResolutionProblemCommand != null && destroyExistingCellStringResolutionProblemCommand.canExecute()) {
				cc.append(destroyExistingCellStringResolutionProblemCommand);
			}

		}
		if (setValueCommand == null) {
			// The problem is created or updated with the following command, we don't need to destroy the previous problem
			Command createCellProblemCommand = CellHelper.getCreateStringResolutionProblemCommand(domain, tableManager, columnElement, rowElement, newValue, valueConverter.getConvertedValue());
			if (createCellProblemCommand != null && createCellProblemCommand.canExecute()) {
				cc.append(createCellProblemCommand);
			}

		}
		if (cc.isEmpty() || !cc.canExecute()) {
			return null;
		}
		return cc;
	}

	/**
	 * @param existingConverters
	 * @param multiValueSeparator
	 * @param tableManager
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.ICellManager#getOrCreateStringValueConverterClass(java.util.Map, java.lang.String, org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 *
	 * @return
	 */
	@Override
	public AbstractStringValueConverter getOrCreateStringValueConverterClass(Map<Class<? extends AbstractStringValueConverter>, AbstractStringValueConverter> existingConverters, String multiValueSeparator, INattableModelManager tableManager) {
		return this.wrappedCellManager.getOrCreateStringValueConverterClass(existingConverters, multiValueSeparator, tableManager);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.ICellManager#isCellEditable(java.lang.Object, java.lang.Object, java.util.Map, INattableModelManager)
	 *
	 * @param columnElement
	 * @param rowElement
	 * @param sharedMap
	 * @return
	 */
	@Override
	public boolean isCellEditable(Object columnElement, Object rowElement, Map<?, ?> sharedMap, INattableModelManager tableManager) {
		return this.wrappedCellManager.isCellEditable(columnElement, rowElement, sharedMap, tableManager);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.ICellManager#setStringValue(java.lang.Object, java.lang.Object, java.lang.String, org.eclipse.papyrus.infra.ui.converter.AbstractStringValueConverter,
	 *      java.util.Map, org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 *
	 * @param columnElement
	 * @param rowElement
	 * @param valueAsString
	 * @param valueConverter
	 * @param sharedMap
	 * @param tableManager
	 */
	@Override
	public void setStringValue(Object columnElement, Object rowElement, String valueAsString, AbstractStringValueConverter valueConverter, Map<?, ?> sharedMap, INattableModelManager tableManager) {
		this.wrappedCellManager.setStringValue(columnElement, rowElement, valueAsString, valueConverter, sharedMap, tableManager);
		CellHelper.createStringResolutionProblem(tableManager, columnElement, rowElement, valueAsString, valueConverter.getConvertedValue(), sharedMap);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.IUnsetValueCellManager#unsetCellValue(org.eclipse.emf.transaction.TransactionalEditingDomain, java.lang.Object, java.lang.Object,
	 *      org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 *
	 * @param domain
	 * @param columnElement
	 * @param rowElement
	 * @param tableManager
	 */
	@Override
	public void unsetCellValue(TransactionalEditingDomain domain, Object columnElement, Object rowElement, INattableModelManager tableManager) {
		if (this.wrappedCellManager instanceof IUnsetValueCellManager) {
			((IUnsetValueCellManager) this.wrappedCellManager).unsetCellValue(domain, columnElement, rowElement, tableManager);
		}
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.cell.IUnsetValueCellManager#getUnsetCellValueCommand(org.eclipse.emf.transaction.TransactionalEditingDomain, java.lang.Object, java.lang.Object,
	 *      org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 *
	 * @param domain
	 * @param columnElement
	 * @param rowElement
	 * @param tableManager
	 * @return
	 */
	@Override
	public Command getUnsetCellValueCommand(TransactionalEditingDomain domain, Object columnElement, Object rowElement, INattableModelManager tableManager) {
		if (this.wrappedCellManager instanceof IUnsetValueCellManager) {
			return ((IUnsetValueCellManager) this.wrappedCellManager).getUnsetCellValueCommand(domain, columnElement, rowElement, tableManager);
		}
		return null;
	}
	
	/**
	 * @return the text of cell to be displayed with unsupported column
	 * @since 4.0
	 */
	@Override
	public String getUnsupportedCellContentsText() {
		return CellHelper.getUnsupportedCellContentsText();
	}
}
