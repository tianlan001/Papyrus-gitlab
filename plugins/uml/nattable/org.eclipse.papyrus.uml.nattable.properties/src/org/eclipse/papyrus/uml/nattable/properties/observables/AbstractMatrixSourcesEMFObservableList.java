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
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.properties.observables;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.nattable.manager.table.IMatrixTableWidgetManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.IMasterAxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.IWrapper;
import org.eclipse.papyrus.infra.ui.emf.databinding.EMFObservableList;

/**
 * Abstract Observable list to matrix sources
 */
public abstract class AbstractMatrixSourcesEMFObservableList extends EMFObservableList {

	/** the current matrix table manager */
	protected IMatrixTableWidgetManager manager;

	/**
	 * Constructor.
	 *
	 * @param wrappedList
	 * @param domain
	 * @param source
	 * @param feature
	 */
	@SuppressWarnings("unchecked")
	public AbstractMatrixSourcesEMFObservableList(final EditingDomain domain, final Table table, final IMatrixTableWidgetManager manager, final IMasterAxisProvider provider, final EStructuralFeature editedFeature) {
		super(EMFProperties.list(editedFeature).observe(provider), domain, provider, editedFeature);
		this.manager = manager;
	}

	/**
	 * @see org.eclipse.core.databinding.observable.list.ObservableList#toArray()
	 *
	 * @return
	 */
	@Override
	public Object[] toArray() { // override to be able to manage the isUnique value for the edited feature
		List<Object> toReturn = new ArrayList<Object>();
		for (Object current : super.toArray()) {
			if (current instanceof IWrapper) {
				toReturn.add(((IWrapper) current).getElement());
			}
		}
		return toReturn.toArray(new Object[toReturn.size()]);
	}

	/**
	 * @see org.eclipse.core.databinding.observable.list.ObservableList#indexOf(java.lang.Object)
	 *
	 * @param o
	 * @return
	 */
	@Override
	public int indexOf(Object o) {
		for (int i = 0; i < this.wrappedList.size(); i++) {
			Object current = this.wrappedList.get(i);
			if (o == current) {
				return i;
			}
			if (current instanceof IWrapper && o == ((IWrapper) current).getElement()) {
				return i;
			}
		}
		return -1;
	}
}