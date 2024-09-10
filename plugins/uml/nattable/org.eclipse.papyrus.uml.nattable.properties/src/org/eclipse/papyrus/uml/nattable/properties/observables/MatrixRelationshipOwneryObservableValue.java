/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.NattablecelleditorPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.EObjectWrapper;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.IWrapper;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.NattablewrapperFactory;
import org.eclipse.papyrus.infra.nattable.utils.TableEditingDomainUtils;
import org.eclipse.uml2.uml.Element;

/**
 * @author Vincent LORENZO
 *
 */
public class MatrixRelationshipOwneryObservableValue extends AbstractMatrixRelationshipCellEditorConfigurationObservableValue {

	/**
	 * 
	 * Constructor.
	 *
	 * @param table
	 *            the edited table
	 */
	public MatrixRelationshipOwneryObservableValue(final Table table) {
		super(TableEditingDomainUtils.getTableEditingDomain(table), table, NattablecelleditorPackage.eINSTANCE.getGenericRelationshipMatrixCellEditorConfiguration_RelationshipOwner());
	}

	/**
	 * @see org.eclipse.papyrus.uml.nattable.properties.observables.AbstractMatrixRelationshipCellEditorConfigurationObservableValue#doGetValue()
	 *
	 * @return
	 */
	@Override
	protected Object doGetValue() {
		Object res = super.doGetValue();
		if (res instanceof IWrapper) {
			res = ((IWrapper) res).getElement();// it seems the reference dialog is not able to propagate it as initial selection, so this line is useless
		}
		return res;
	}

	/**
	 * @see org.eclipse.papyrus.uml.nattable.properties.observables.AbstractMatrixRelationshipCellEditorConfigurationObservableValue#doSetValue(java.lang.Object)
	 *
	 * @param value
	 */
	@Override
	protected void doSetValue(Object value) {
		if (value instanceof Element) {
			final EObjectWrapper wrapper = NattablewrapperFactory.eINSTANCE.createEObjectWrapper();
			wrapper.setElement((EObject) value);
			value = wrapper;
		}
		super.doSetValue(value);
	}



}
