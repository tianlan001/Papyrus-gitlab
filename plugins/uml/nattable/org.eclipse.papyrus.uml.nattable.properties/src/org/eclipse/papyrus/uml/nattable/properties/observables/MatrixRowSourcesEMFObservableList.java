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
 *   Vincent LORENZO (CEA-LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   Vincent Lorenzo (CEA LIST) - Bug 517742
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.properties.observables;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.nattable.manager.table.IMatrixTableWidgetManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.IMasterAxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.NattableaxisproviderPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.IWrapper;

/**
 * Observable for Matrix row sources feature
 *
 */
public class MatrixRowSourcesEMFObservableList extends AbstractMatrixSourcesEMFObservableList {

	/**
	 * Constructor.
	 *
	 * @param wrappedList
	 * @param domain
	 * @param source
	 * @param feature
	 */
	public MatrixRowSourcesEMFObservableList(final EditingDomain domain, final Table table, final IMatrixTableWidgetManager manager) {
		super(domain, table, manager, (IMasterAxisProvider) table.getCurrentRowAxisProvider(), NattableaxisproviderPackage.eINSTANCE.getIMasterAxisProvider_Sources());
	}



	/**
	 * @Override
	 * @see org.eclipse.papyrus.infra.ui.emf.databinding.EMFObservableList#remove(java.lang.Object)
	 *
	 * @param o
	 * @return
	 */
	public boolean remove(Object o) {
		if (isDisposed()) {
			return true;
		}
		Object toRemove = null;
		if (o instanceof IWrapper) {
			toRemove = (IWrapper) o;
		} else {
			for (final Object currentContext : ((IMasterAxisProvider) this.source).getSources()) {
				if (currentContext instanceof IWrapper && (((IWrapper) currentContext).getElement() == o)) {
					toRemove = currentContext;
					break;
				}
			}
		}
		Assert.isNotNull(toRemove);
		return super.remove(toRemove);
	}

	/**
	 * @Override
	 * @see org.eclipse.papyrus.infra.ui.emf.databinding.EMFObservableList#addAll(java.util.Collection)
	 *
	 * @param c
	 * @return
	 */
	public boolean addAll(@SuppressWarnings("rawtypes") Collection c) {
		return super.addAll(c);
	}

	/**
	 * @see org.eclipse.papyrus.infra.ui.emf.databinding.EMFObservableList#getRemoveCommand(java.lang.Object)
	 *
	 * @param value
	 * @return
	 */
	@Override
	public Command getRemoveCommand(Object value) {
		return this.manager.getRemoveRowSourcesCommand(Collections.singletonList(value));
	}

	/**
	 * @see org.eclipse.papyrus.infra.ui.emf.databinding.EMFObservableList#getAddAllCommand(java.util.Collection)
	 *
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Command getAddAllCommand(final Collection<?> values) {
		return new AddAllCommandClass(this.manager, (Collection<Object>) values);
	}


	/**
	 * 
	 * @see org.eclipse.papyrus.infra.ui.emf.databinding.EMFObservableList#clear()
	 *
	 */
	@Override
	public void clear() {
		super.clear();
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.ui.emf.databinding.EMFObservableList#getAddCommand(int, java.lang.Object)
	 *
	 * @param index
	 * @param value
	 * @return
	 */
	@Override
	public Command getAddCommand(int index, Object value) {
		throw new UnsupportedOperationException();
	}


	/**
	 * 
	 * @see org.eclipse.papyrus.infra.ui.emf.databinding.EMFObservableList#getAddCommand(java.lang.Object)
	 *
	 * @param value
	 * @return
	 */
	@Override
	public Command getAddCommand(Object value) {
		throw new UnsupportedOperationException();
	}


	/**
	 * 
	 * @see org.eclipse.papyrus.infra.ui.emf.databinding.EMFObservableList#getAddAllCommand(int, java.util.Collection)
	 *
	 * @param index
	 * @param values
	 * @return
	 */
	@Override
	public Command getAddAllCommand(int index, Collection<?> values) {
		throw new UnsupportedOperationException();
	}


	/**
	 * 
	 * @see org.eclipse.papyrus.infra.ui.emf.databinding.EMFObservableList#getRemoveCommand(int)
	 *
	 * @param index
	 * @return
	 */
	@Override
	public Command getRemoveCommand(int index) {
		throw new UnsupportedOperationException();
	}


	/**
	 * 
	 * @see org.eclipse.papyrus.infra.ui.emf.databinding.EMFObservableList#getRemoveAllCommand(java.util.Collection)
	 *
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Command getRemoveAllCommand(Collection<?> values) {
		return this.manager.getRemoveRowSourcesCommand((Collection<Object>) values);
	}


	/**
	 * 
	 * @see org.eclipse.papyrus.infra.ui.emf.databinding.EMFObservableList#getRetainAllCommand(java.util.Collection)
	 *
	 * @param values
	 * @return
	 */
	@Override
	public Command getRetainAllCommand(Collection<?> values) {
		throw new UnsupportedOperationException();
	}


	/**
	 * 
	 * @see org.eclipse.papyrus.infra.ui.emf.databinding.EMFObservableList#getSetCommand(int, java.lang.Object)
	 *
	 * @param index
	 * @param value
	 * @return
	 */
	@Override
	public Command getSetCommand(int index, Object value) {
		throw new UnsupportedOperationException();
	}


	/**
	 * 
	 * @see org.eclipse.papyrus.infra.ui.emf.databinding.EMFObservableList#add(int, java.lang.Object)
	 *
	 * @param index
	 * @param value
	 */
	@Override
	public void add(int index, Object value) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.ui.emf.databinding.EMFObservableList#add(java.lang.Object)
	 *
	 * @param o
	 * @return
	 */
	@Override
	public boolean add(Object o) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.ui.emf.databinding.EMFObservableList#addAll(int, java.util.Collection)
	 *
	 * @param index
	 * @param c
	 * @return
	 */
	@Override
	public boolean addAll(int index, @SuppressWarnings("rawtypes") Collection c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.ui.emf.databinding.EMFObservableList#removeAll(java.util.Collection)
	 *
	 * @param c
	 * @return
	 */
	@Override
	public boolean removeAll(@SuppressWarnings("rawtypes") Collection c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.ui.emf.databinding.EMFObservableList#retainAll(java.util.Collection)
	 *
	 * @param c
	 * @return
	 */
	@Override
	public boolean retainAll(@SuppressWarnings("rawtypes") Collection c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.ui.emf.databinding.EMFObservableList#move(int, int)
	 *
	 * @param oldIndex
	 * @param newIndex
	 * @return
	 */
	@Override
	public Object move(int oldIndex, int newIndex) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.ui.emf.databinding.EMFObservableList#remove(int)
	 *
	 * @param index
	 * @return
	 */
	@Override
	public Object remove(int index) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @see org.eclipse.core.databinding.observable.list.ObservableList#contains(java.lang.Object)
	 *
	 * @param o
	 * @return
	 */
	@Override
	public boolean contains(Object o) {
		throw new UnsupportedOperationException();
	}

	/**
	 * S
	 * 
	 * @see org.eclipse.core.databinding.observable.list.ObservableList#containsAll(java.util.Collection)
	 *
	 * @param c
	 * @return
	 */
	@Override
	public boolean containsAll(@SuppressWarnings("rawtypes") Collection c) {
		throw new UnsupportedOperationException();
	}



	/**
	 * we need to calculate the AddAllCommand during the execution of the AbstractCommand, because for a Add action, the properties view
	 * does a remove all, then an add all, so the field managedEObject of the AxisManager is not yet updated, so during the add command creation,
	 * managedEObject it continues to contain the object removed by the remove all, then the final result is not the good one
	 */
	private class AddAllCommandClass extends AbstractCommand {

		/**
		 * the current matrix manager
		 */
		private final IMatrixTableWidgetManager manager;

		/**
		 * the values to add
		 */
		private final Collection<Object> values;

		/**
		 * the wrapped add all command
		 */
		private Command wrappedCommand = null;

		/**
		 * 
		 * Constructor.
		 *
		 * @param manager
		 *            the matrix manager used to get the add Command
		 */
		public AddAllCommandClass(final IMatrixTableWidgetManager manager, final Collection<Object> values) {
			super("Add All Command"); //$NON-NLS-1$
			this.manager = manager;
			this.values = values;
		}


		/**
		 * 
		 * @see org.eclipse.emf.common.command.Command#redo()
		 *
		 */
		@Override
		public void redo() {
			if (null != this.wrappedCommand) {
				execute();
                        }
		}

		/**
		 * 
		 * @see org.eclipse.emf.common.command.Command#execute()
		 *
		 */
		@Override
		public void execute() {
			if (null == this.wrappedCommand) {
				this.wrappedCommand = this.manager.getAddRowSourcesCommand(((Collection<Object>) values));
			}
			if (null != this.wrappedCommand) {
				this.wrappedCommand.execute();
			}
		}

		/**
		 * @see org.eclipse.emf.common.command.AbstractCommand#undo()
		 *
		 */
		@Override
		public void undo() {
                 	if (null != this.wrappedCommand) {
				this.wrappedCommand.undo();
                        }
		}
		/**
		 * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
		 *
		 * @return
		 */
		@Override
		protected boolean prepare() {
			return true;
		}
	}
}