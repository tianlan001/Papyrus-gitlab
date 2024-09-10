/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
package org.eclipse.papyrus.infra.nattable.properties.observable;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.NattablelabelproviderPackage;


/**
 * The Class ColumnObjectLabelDisplayLabelObservableValue.
 */
public class ColumnObjectLabelDisplayLabelObservableValue extends AbstractColumnObjectLabelProviderConfigurationObservableValue {

	/**
	 * Instantiates a new column object label display label observable value.
	 *
	 * @param table
	 *            the table
	 */
	public ColumnObjectLabelDisplayLabelObservableValue(Table table) {
		super(table, NattablelabelproviderPackage.eINSTANCE.getObjectLabelProviderConfiguration_DisplayLabel());
	}

	/**
	 * @see org.eclipse.core.databinding.observable.value.IObservableValue#getValueType()
	 *
	 * @return
	 */

	@Override
	public Object getValueType() {
		return EcorePackage.eINSTANCE.getEBoolean();
	}



}
