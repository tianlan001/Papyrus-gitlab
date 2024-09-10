/*****************************************************************************
 * Copyright (c) 2013, 2017 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) - bug 525221
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.provider;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.IElementComparer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.TableSelectionWrapper;

/**
 * The StructuredSelection filled by the tables. It able to embed more precision about the selected elements in the table.
 *
 */
public class TableStructuredSelection implements IStructuredSelection, IAdaptable {

	/**
	 * the wrapper for the table selection
	 */
	private final TableSelectionWrapper wrapper;

	/**
	 * the wrapped selection
	 */
	private final IStructuredSelection wrappedSelection;

	/**
	 *
	 * Constructor.
	 *
	 * @param wrapper
	 */
	public TableStructuredSelection(final TableSelectionWrapper wrapper) {
		this.wrapper = wrapper;
		this.wrappedSelection = StructuredSelection.EMPTY;
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param elements
	 * @param comparer
	 * @param wrapper
	 */
	public TableStructuredSelection(final List<?> elements, final IElementComparer comparer, final TableSelectionWrapper wrapper) {
		this.wrappedSelection = new StructuredSelection(elements, comparer);
		this.wrapper = wrapper;
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param elements
	 * @param wrapper
	 */
	public TableStructuredSelection(final List<?> elements, final TableSelectionWrapper wrapper) {
		this.wrappedSelection = new StructuredSelection(elements);
		this.wrapper = wrapper;
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param element
	 * @param wrapper
	 */
	public TableStructuredSelection(final Object element, final TableSelectionWrapper wrapper) {
		this.wrappedSelection = new StructuredSelection(element);
		this.wrapper = wrapper;
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param elements
	 * @param wrapper
	 */
	public TableStructuredSelection(final Object[] elements, final TableSelectionWrapper wrapper) {
		this.wrappedSelection = new StructuredSelection(elements);
		this.wrapper = wrapper;
	}

	/**
	 *
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 *
	 * @param adapter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") final Class adapter) {
		if (adapter == TableSelectionWrapper.class) {
			return this.wrapper;
		}
		if (adapter == INattableModelManager.class && null != this.wrapper) {// to ease manipulation
			return this.wrapper.getNatTableModelManager();
		}
		return null;
	}

	/**
	 * @see org.eclipse.jface.viewers.ISelection#isEmpty()
	 *
	 * @return
	 */
	@Override
	public boolean isEmpty() {
		return this.wrappedSelection.isEmpty();
	}

	/**
	 * @see org.eclipse.jface.viewers.IStructuredSelection#getFirstElement()
	 *
	 * @return
	 */
	@Override
	public Object getFirstElement() {
		return this.wrappedSelection.getFirstElement();
	}

	/**
	 * @see org.eclipse.jface.viewers.IStructuredSelection#iterator()
	 *
	 * @return
	 */
	@Override
	public Iterator<?> iterator() {
		return this.wrappedSelection.iterator();
	}

	/**
	 * @see org.eclipse.jface.viewers.IStructuredSelection#size()
	 *
	 * @return
	 */
	@Override
	public int size() {
		return this.wrappedSelection.size();
	}

	/**
	 * @see org.eclipse.jface.viewers.IStructuredSelection#toArray()
	 *
	 * @return
	 */
	@Override
	public Object[] toArray() {
		return this.wrappedSelection.toArray();
	}

	/**
	 * @see org.eclipse.jface.viewers.IStructuredSelection#toList()
	 *
	 * @return
	 */
	@Override
	public List<?> toList() {
		return this.wrappedSelection.toList();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 *
	 * @param arg0
	 * @return
	 */
	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}

		if (!(o instanceof TableStructuredSelection)) {
			return false;
		}

		final TableStructuredSelection tss2 = (TableStructuredSelection) o;
		if (tss2.getTableSelectionWrapper().equals(getTableSelectionWrapper())
				&& tss2.getWrappedSelection().equals(getWrappedSelection())) {
			return true;
		}
		return false;
	}

	/**
	 * Internal usage onlyS
	 * 
	 * @return
	 * 		the table selection wrapper
	 */
	private TableSelectionWrapper getTableSelectionWrapper() {
		return this.wrapper;
	}

	/**
	 * Internal usage only
	 * 
	 * @return
	 * 		the wrapped selection
	 */
	private IStructuredSelection getWrappedSelection() {
		return this.wrappedSelection;
	}
}
