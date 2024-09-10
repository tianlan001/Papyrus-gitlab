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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA LIST) - Bug 517742
 *  Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 525367
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.manager.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.MasterObjectAxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.NattableaxisproviderPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.EObjectWrapper;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.IWrapper;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.NattablewrapperFactory;
import org.eclipse.papyrus.infra.nattable.selection.ISelectionExtractor;

/**
 * The manager to use for Matrix
 * 
 * @since 3.0
 *
 */
public class MatrixTableWidgetModelManager extends TreeNattableModelManager implements IMatrixTableWidgetManager {

	/**
	 * Constructor.
	 *
	 * @param rawModel
	 * @param selectionExtractor
	 * @param initializeListeners
	 */
	public MatrixTableWidgetModelManager(Table rawModel, ISelectionExtractor selectionExtractor, boolean initializeListeners) {
		super(rawModel, selectionExtractor, initializeListeners);
	}

	/**
	 * Constructor.
	 *
	 * @param rawModel
	 * @param selectionExtractor
	 */
	public MatrixTableWidgetModelManager(Table rawModel, ISelectionExtractor selectionExtractor) {
		super(rawModel, selectionExtractor);
	}

	/**
	 * Constructor.
	 *
	 * @param rawModel
	 */
	public MatrixTableWidgetModelManager(Table rawModel) {
		super(rawModel);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager#canDropRowElement(java.util.Collection)
	 *
	 * @param objectsToAdd
	 * @return
	 */
	@Override
	public boolean canDropRowElement(Collection<Object> objectsToAdd) {
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager#canDropColumnsElement(java.util.Collection)
	 *
	 * @param objectsToAdd
	 * @return
	 */
	@Override
	public boolean canDropColumnsElement(Collection<Object> objectsToAdd) {
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager#canInvertAxis()
	 *
	 * @return
	 */
	@Override
	public boolean canInvertAxis() {
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager#canCreateColumnElement(java.lang.String)
	 *
	 * @param elementType
	 * @return
	 */
	@Override
	public boolean canCreateColumnElement(String elementType) {
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager#canCreateDestroyRowsAxis()
	 *
	 * @return
	 */
	@Override
	public boolean canCreateDestroyRowsAxis() {
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager#canMoveColumns()
	 *
	 * @return
	 */
	@Override
	public boolean canMoveColumns() {
		return false;
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager#canInsertRow(java.util.Collection, int)
	 *
	 * @param objectsToAdd
	 * @param index
	 * @return
	 */
	@Override
	public boolean canInsertRow(Collection<Object> objectsToAdd, int index) {
		return false;
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager#canInsertColumns(java.util.Collection, int)
	 *
	 * @param objectsToAdd
	 * @param index
	 * @return
	 */
	@Override
	public boolean canInsertColumns(Collection<Object> objectsToAdd, int index) {
		return false;
	}


	/**
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager#canMoveRows()
	 *
	 * @return
	 */
	@Override
	public boolean canMoveRows() {
		return false;
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager#canCreateRowElement(java.lang.String)
	 *
	 * @param elementType
	 * @return
	 */
	@Override
	public boolean canCreateRowElement(String elementType) {
		return false;
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager#canCreateDestroyColumnsAxis()
	 *
	 * @return
	 */
	@Override
	public boolean canCreateDestroyColumnsAxis() {
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.IMatrixTableWidgetManager#addRowSources(java.util.Collection)
	 *
	 * @param objectToAdd
	 */
	@Override
	public void addRowSources(final Collection<Object> objectToAdd) {
		final Command cmd = getAddRowSourcesCommand(objectToAdd);
		if (null != cmd && cmd.canExecute()) {
			getTableEditingDomain().getCommandStack().execute(cmd);
		}
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.IMatrixTableWidgetManager#removeRowSources(java.util.Collection)
	 *
	 * @param objectToRemove
	 */
	@Override
	public void removeRowSources(final Collection<Object> objectToRemove) {
		final Command cmd = getRemoveRowSourcesCommand(objectToRemove);
		if (null != cmd && cmd.canExecute()) {
			getTableEditingDomain().getCommandStack().execute(cmd);
		}
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.IMatrixTableWidgetManager#addColumnSources(java.util.Collection)
	 *
	 * @param objectToAdd
	 */
	@Override
	public void addColumnSources(final Collection<Object> objectToAdd) {
		final Command cmd = getAddColumnSourcesCommand(objectToAdd);
		if (null != cmd && cmd.canExecute()) {
			getTableEditingDomain().getCommandStack().execute(cmd);
		}
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.IMatrixTableWidgetManager#removeColumnSources(java.util.Collection)
	 *
	 * @param objectToRemove
	 */
	@Override
	public void removeColumnSources(final Collection<Object> objectToRemove) {
		final Command cmd = getRemoveColumnSourcesCommand(objectToRemove);
		if (null != cmd && cmd.canExecute()) {
			getTableEditingDomain().getCommandStack().execute(cmd);
		}
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.IMatrixTableWidgetManager2#getAddRowSourceCommand(java.util.Collection)
	 *
	 * @param objectsToAdd
	 * @return
	 */
	@Override
	public Command getAddRowSourcesCommand(final Collection<Object> objectsToAdd) {
		// the list of rows to add to the matrix
		final List<Object> toAdd = new ArrayList<Object>();
		// the list of wrapper to add as matrix sources
		final List<IWrapper> wrappersToAdd = new ArrayList<IWrapper>();
		for (final Object current : objectsToAdd) {
			if (current instanceof IWrapper) {
				final Object wrappedElement = ((IWrapper) current).getElement();
				if (null != wrappedElement) {
					toAdd.add(wrappedElement);
				}
				wrappersToAdd.add((IWrapper) current);
			} else {
				if (current instanceof EObject) {
					toAdd.add(current);
					final EObjectWrapper wrapper = NattablewrapperFactory.eINSTANCE.createEObjectWrapper();
					wrapper.setElement((EObject) current);
					wrappersToAdd.add(wrapper);
				}
			}
		}
		final CompoundCommand cc = new CompoundCommand("Add Matrix Row Sources Command"); //$NON-NLS-1$
		cc.append(getAddRowElementCommand(toAdd));
		cc.append(AddCommand.create(getTableEditingDomain(), getTable().getCurrentRowAxisProvider(), NattableaxisproviderPackage.eINSTANCE.getIMasterAxisProvider_Sources(), wrappersToAdd));
		return cc.canExecute() ? cc : null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.IMatrixTableWidgetManager2#getRemoveRowSources(java.util.Collection)
	 *
	 * @param objectsToRemove
	 * @return
	 */
	@Override
	public Command getRemoveRowSourcesCommand(final Collection<Object> objectsToRemove) {
		final CompoundCommand cc = new CompoundCommand("Remove Matrix rows sources command");//$NON-NLS-1$
		final MasterObjectAxisProvider provider = (MasterObjectAxisProvider) getTable().getCurrentRowAxisProvider();// this cast is always true, we are in a matrix
		final Collection<Object> axisToRemove = new ArrayList<Object>();
		for (final Object current : objectsToRemove) {
			// 1. remove the source
			IWrapper wrapperToRemove = null;
			if (current instanceof IWrapper) {
				wrapperToRemove = (IWrapper) current;
			} else {
				// it is the wrapped Object
				for (final IWrapper wrapper : provider.getSources()) {
					if (wrapper.getElement() == current) {
						wrapperToRemove = wrapper;
						break;
					}
				}
			}
			if (null != wrapperToRemove) {
				cc.append(RemoveCommand.create(getTableEditingDomain(), provider, NattableaxisproviderPackage.eINSTANCE.getIMasterAxisProvider_Sources(), wrapperToRemove));
			}

			// 2. remove the IAxis
			if (null != wrapperToRemove) {
				final Object wrappedObject = wrapperToRemove.getElement();

				for (final IAxis iaxis : getRowAxisManager().getRepresentedContentProvider().getAxis()) {
					if (iaxis.getElement() == wrappedObject) {// we assume the element is represented only one time as root
						axisToRemove.add(iaxis);
						break;
					}
				}
			}
		}
		if (axisToRemove.size() > 0) {
			cc.append(getRowAxisManager().getDestroyAxisCommand((TransactionalEditingDomain) getTableEditingDomain(), axisToRemove));
		}
		return cc.canExecute() ? cc : null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.IMatrixTableWidgetManager2#getAddColumnSources(java.util.Collection)
	 *
	 * @param objectsToAdd
	 * @return
	 */
	@Override
	public Command getAddColumnSourcesCommand(final Collection<Object> objectsToAdd) {
		// The list of wrapper to add as matrix column sources
		final List<IWrapper> wrappersToAdd = new ArrayList<IWrapper>();
		for (final Object current : objectsToAdd) {
			if (current instanceof IWrapper) {
				wrappersToAdd.add((IWrapper) current);
			} else if (current instanceof EObject) {
				final EObjectWrapper wrapper = NattablewrapperFactory.eINSTANCE.createEObjectWrapper();
				wrapper.setElement((EObject) current);
				wrappersToAdd.add(wrapper);
			}
		}

		final CompoundCommand cc = new CompoundCommand("Add Matrix Column Sources Command"); //$NON-NLS-1$
		cc.append(AddCommand.create(getTableEditingDomain(), getTable().getCurrentColumnAxisProvider(), NattableaxisproviderPackage.eINSTANCE.getIMasterAxisProvider_Sources(), wrappersToAdd));
		return cc.canExecute() ? cc : null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.IMatrixTableWidgetManager2#getRemoveColumnSources(java.util.Collection)
	 *
	 * @param objectsToRemove
	 * @return
	 */
	@Override
	public Command getRemoveColumnSourcesCommand(Collection<Object> objectsToRemove) {
		final MasterObjectAxisProvider columnAxisProvider = (MasterObjectAxisProvider) getTable().getCurrentColumnAxisProvider(); // this cast is always true as we are in a matrix

		// The list of column source wrappers to remove
		final List<IWrapper> wrappersToRemove = new ArrayList<IWrapper>();
		for (final Object current : objectsToRemove) {
			IWrapper wrapperObj = null;
			// If current object to be removed is a wrapper, just remember it
			if (current instanceof IWrapper) {
				wrapperObj = (IWrapper) current;
			} else {
				// Otherwise, it is the wrapped Object, find the relevant wrapper in the column sources
				for (final IWrapper wrapper : columnAxisProvider.getSources()) {
					if (wrapper.getElement() == current) {
						wrapperObj = wrapper;
						break;
					}
				}
			}
			if (null != wrapperObj) {
				wrappersToRemove.add(wrapperObj);
			}
		}

		final CompoundCommand cc = new CompoundCommand("Remove Matrix Column Sources Command");//$NON-NLS-1$
		cc.append(RemoveCommand.create(getTableEditingDomain(), columnAxisProvider, NattableaxisproviderPackage.eINSTANCE.getIMasterAxisProvider_Sources(), wrappersToRemove));
		return cc.canExecute() ? cc : null;
	}
}
