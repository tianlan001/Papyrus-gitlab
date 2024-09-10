/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.manager.cell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.nattable.Activator;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.messages.Messages;
import org.eclipse.papyrus.infra.ui.converter.AbstractStringValueConverter;

/**
 * The cell manager factory
 *
 * @author Vincent Lorenzo
 *
 */
public final class CellManagerFactory {


	private static final String CLASS_MANAGER = "manager"; //$NON-NLS-1$

	private static final String ORDER = "order"; //$NON-NLS-1$

	private static final String EXTENSION_ID = "org.eclipse.papyrus.infra.nattable.cellmanager"; //$NON-NLS-1$

	/**
	 * Error message when the cell manager has not been found
	 */
	public static final String CELL_MANAGER_NOT_FOUND = Messages.CellManagerFactory_CellManagerNotFound;

	/**
	 * the map of the cell manager sorted by priority
	 */
	private final Map<Integer, Collection<ICellManager>> managersMap;

	/**
	 * The list of registered axis manager
	 */
	private final List<IGenericMatrixRelationshipCellManager> matrixManagers;

	/**
	 * The cell manager factory
	 */
	public static final CellManagerFactory INSTANCE = new CellManagerFactory();

	/**
	 *
	 * Constructor.
	 * Initialize the field of the class
	 */
	private CellManagerFactory() {
		this.managersMap = new TreeMap<Integer, Collection<ICellManager>>();
		this.matrixManagers = new ArrayList<IGenericMatrixRelationshipCellManager>();
		final IConfigurationElement[] configElements = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_ID);

		for (final IConfigurationElement iConfigurationElement : configElements) {
			// id is not used
			// final String id = iConfigurationElement.getAttribute(SOLVER_ID);

			final Integer order = new Integer(iConfigurationElement.getAttribute(ORDER));
			try {
				final ICellManager solver = (ICellManager) iConfigurationElement.createExecutableExtension(CLASS_MANAGER);
				if (!this.managersMap.containsKey(order)) {
					this.managersMap.put(order, new HashSet<ICellManager>());
				}
				if (solver instanceof IGenericMatrixRelationshipCellManager) {
					this.matrixManagers.add((IGenericMatrixRelationshipCellManager) solver);
				}
				this.managersMap.get(order).add(new StringResolutionProblemWrapperCellManager(solver));
			} catch (final CoreException e) {
				Activator.log.error(e);
			}
		}
	}

	/**
	 *
	 * @param columnElement
	 *            the column element as described in the model (you must ignore the invert axis)
	 *
	 * @param rowElement
	 *            the row element as described in the model (you must ignore the invert axis)
	 * @return
	 * 		the value to display in the cell
	 */
	public Object getCrossValue(final Object columnElement, final Object rowElement, final INattableModelManager tableManager) {
		final ICellManager cellManager = getCellManager(columnElement, rowElement, tableManager);
		if (cellManager != null) {
			return cellManager.getValue(columnElement, rowElement, tableManager);
		} else {
			return CELL_MANAGER_NOT_FOUND;
		}
	}

	/**
	 *
	 * @param columnElement
	 *            the column element as described in the model (you must ignore the invert axis)
	 *
	 * @param rowElement
	 *            the row element as described in the model (you must ignore the invert axis)
	 * @return
	 * 		the value to display in the cell
	 */
	public Object getCrossValueIgnoringProblems(final Object columnElement, final Object rowElement, final INattableModelManager tableManager) {
		final ICellManager cellManager = getCellManager(columnElement, rowElement, tableManager);
		if (cellManager != null) {
			return ((StringResolutionProblemWrapperCellManager) cellManager).getValueIgnoringCellProblem(columnElement, rowElement, tableManager);
		} else {
			return CELL_MANAGER_NOT_FOUND;
		}
	}

	/**
	 *
	 * @param columnElement
	 *            the column element as described in the model (you must ignore the invert axis)
	 * @param rowElement
	 *            the row element as described in the model (you must ignore the invert axis)
	 * @param tableManager
	 *            the current table manager
	 *
	 * @return
	 * 		the cell manager
	 */
	private ICellManager getCellManager(final Object columnElement, final Object rowElement, final INattableModelManager tableManager) {
		ICellManager result = null;
		final Iterator<Integer> orders = this.managersMap.keySet().iterator();
		while (orders.hasNext() && null == result) {
			final Integer integer = orders.next();
			final Iterator<ICellManager> cellManagers = this.managersMap.get(integer).iterator();
			while (cellManagers.hasNext() && null == result) {
				final ICellManager current = cellManagers.next();
				if (current.handles(columnElement, rowElement, tableManager)) {
					result = current;
				}
			}
		}
		return result;
	}

	/**
	 *
	 * @param columnElement
	 *            the column element as described in the model (you must ignore the invert axis)
	 * @param rowElement
	 *            the row element as described in the model (you must ignore the invert axis)
	 * @param tableManager
	 *            the current table manager
	 * @return
	 * 		<code>true</code> if the cell is editable
	 * @since 3.0 add tableManager argument
	 */
	public boolean isCellEditable(final Object columnElement, final Object rowElement, final INattableModelManager tableManager) {
		final ICellManager cellManager = getCellManager(columnElement, rowElement, tableManager);
		if (cellManager != null) {
			return cellManager.isCellEditable(columnElement, rowElement, tableManager);
		}
		return false;

	}

	/**
	 *
	 * @param domain
	 *            the editing domain
	 *
	 * @param columnElement
	 *            the column element as described in the model (you must ignore the invert axis)
	 * @param rowElement
	 *            the row element as described in the model (you must ignore the invert axis)
	 * @param newValue
	 *            the new value
	 * @param tableManager
	 *            the table manager
	 */
	public void setCellValue(final TransactionalEditingDomain domain, final Object columnElement, final Object rowElement, final Object newValue, final INattableModelManager tableManager) {
		final ICellManager cellManager = getCellManager(columnElement, rowElement, tableManager);
		if (cellManager != null) {
			cellManager.setValue(domain, columnElement, rowElement, newValue, tableManager);
		} else {
			throw new UnsupportedOperationException(CELL_MANAGER_NOT_FOUND);
		}
	}

	/**
	 *
	 * @param editingDomain
	 *            the editing domain to use
	 * @param columnElement
	 *            the column element
	 * @param rowElement
	 *            the row element
	 * @param valueAsString
	 *            the value as string
	 * @param stringResolvers
	 *            a map with instances of existing string resolvers
	 * @param tableManager
	 *            the table manager
	 * @return
	 * 		the command to set the value
	 */
	public Command getSetStringValueCommand(final TransactionalEditingDomain editingDomain, final Object columnElement, final Object rowElement, final String valueAsString, final AbstractStringValueConverter stringResolvers,
			final INattableModelManager tableManager) {
		final ICellManager cellManager = getCellManager(columnElement, rowElement, tableManager);
		if (cellManager != null) {
			return cellManager.getSetStringValueCommand(editingDomain, columnElement, rowElement, valueAsString, stringResolvers, tableManager);
		} else {
			return UnexecutableCommand.INSTANCE;
		}
	}

	/**
	 *
	 * @param columnElement
	 *            the columnElement
	 * @param rowElement
	 *            the rowElement
	 * @param existingConverters
	 *            the map of the existing converters (to avoid to create same too many times
	 * @param tableManager
	 *            the table manager
	 * @return
	 * 		the converter to use, or null if not found
	 * @since 3.0 : change the arguments order
	 */
	public AbstractStringValueConverter getOrCreateStringValueConverterClass(final Object columnElement, final Object rowElement, final Map<Class<? extends AbstractStringValueConverter>, AbstractStringValueConverter> existingConverters,
			final String multiValueSeparator, final INattableModelManager tableManager) {
		final ICellManager cellManager = getCellManager(columnElement, rowElement, tableManager);
		if (cellManager != null) {
			return cellManager.getOrCreateStringValueConverterClass(existingConverters, multiValueSeparator, tableManager);
		}
		return null;
	}

	/**
	 * This method is used when we are pasting elements in detached mode
	 *
	 * @param columnElement
	 *            the column element
	 * @param rowElement
	 *            the row element
	 * @param valueAsString
	 *            the value as string
	 * @param valueConverter
	 *            the value converter to use
	 * @param sharedMap
	 *            a map with shared elements. The method may read/add elements to the shared map. These contributions will be managed by a paste post
	 *            action or by the paste manager itself
	 * @param tableManager
	 *            the table manager
	 * @since 3.0 change parameter order
	 */
	public void setStringValue(final Object columnElement, final Object rowElement, final String valueAsString, final AbstractStringValueConverter valueConverter, final Map<?, ?> sharedMap, final INattableModelManager tableManager) {
		final ICellManager cellManager = getCellManager(columnElement, rowElement, tableManager);
		if (cellManager != null) {
			cellManager.setStringValue(columnElement, rowElement, valueAsString, valueConverter, sharedMap, tableManager);
		} else {
			throw new UnsupportedOperationException(CELL_MANAGER_NOT_FOUND);
		}
	}

	/**
	 *
	 * @param columnElement
	 *            the column element
	 * @param rowElement
	 *            the row element
	 * @param sharedMap
	 *            a map with shared elements
	 * @param tableManager
	 *            the current table manager
	 * @return
	 * 		<code>true</code> if the cell is editable
	 * @since 3.0 add tableManager argument
	 */
	public boolean isCellEditable(final Object columnElement, final Object rowElement, final Map<?, ?> sharedMap, final INattableModelManager tableManager) {
		final ICellManager cellManager = getCellManager(columnElement, rowElement, tableManager);
		if (cellManager != null) {
			return cellManager.isCellEditable(columnElement, rowElement, sharedMap, tableManager);
		}
		return false;

	}

	/**
	 *
	 * @param domain
	 *            the editing domain
	 * @param columnElement
	 *            the column element
	 * @param rowElement
	 *            the row element
	 * @param newValue
	 *            the new value
	 * @param tableManager
	 *            the table manager
	 * @return
	 * 		the command to use to do the set value. It is used by the DnD from the ModelExplorer
	 */
	public Command getSetCellValueCommand(final TransactionalEditingDomain domain, final Object columnElement, final Object rowElement, final Object newValue, final INattableModelManager tableManager) {
		final ICellManager cellManager = getCellManager(columnElement, rowElement, tableManager);
		if (cellManager != null) {
			return cellManager.getSetValueCommand(domain, columnElement, rowElement, newValue, tableManager);
		}
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 *
	 * @param columnElement
	 *            a column element
	 * @param rowElement
	 *            a row element
	 * @param tableManager
	 *            the table manager
	 * @return
	 * 		a collection of all values for the intersection of the columnElement and the row element.
	 */
	public final Collection<?> getCrossValueAsCollection(final Object columnElement, final Object rowElement, final INattableModelManager tableManager) {
		Object value = CellManagerFactory.INSTANCE.getCrossValue(columnElement, rowElement, tableManager);
		Collection<?> collection = Collections.emptyList();
		if (value instanceof Collection<?>) {
			collection = (Collection<?>) value;
		} else if (value instanceof Object[]) {
			collection = Arrays.asList(value);
		} else if (null != value) {
			collection = Collections.singletonList(value);
		}
		return collection;
	}

	/**
	 * 
	 * @param domain
	 *            the editing domain to use
	 * @param columnElement
	 *            the column element
	 * @param rowElement
	 *            the row element
	 * @param tableManager
	 *            the table manager
	 * 
	 *            unset the cell value if possible (reset to default value)
	 */
	public final void unsetCellValue(final TransactionalEditingDomain domain, final Object columnElement, final Object rowElement, final INattableModelManager tableManager) {
		final ICellManager cellManager = getCellManager(columnElement, rowElement, tableManager);
		if (cellManager instanceof IUnsetValueCellManager) {
			((IUnsetValueCellManager) cellManager).unsetCellValue(domain, columnElement, rowElement, tableManager);
		}
	}

	/**
	 * 
	 * @param domain
	 *            the editing domain to use
	 * @param columnElement
	 *            the column element
	 * @param rowElement
	 *            the row element
	 * @param tableManager
	 *            the table manager
	 * @return
	 * 		the command to unset the cell value (reset to default value
	 */
	public final Command getUnsetCellValueCommand(final TransactionalEditingDomain domain, final Object columnElement, final Object rowElement, final INattableModelManager tableManager) {
		final ICellManager cellManager = getCellManager(columnElement, rowElement, tableManager);
		if (cellManager instanceof IUnsetValueCellManager) {
			return ((IUnsetValueCellManager) cellManager).getUnsetCellValueCommand(domain, columnElement, rowElement, tableManager);
		}
		return null;
	}

	/**
	 * @return
	 * 		the list of known {@link IGenericMatrixRelationshipCellManager}. Warning, this method ignores the order defined by the user
	 * @since 3.0
	 */
	public List<IGenericMatrixRelationshipCellManager> getRegisteredGenericMatrixRelationshipCellManager() {
		return matrixManagers;
	}
}
