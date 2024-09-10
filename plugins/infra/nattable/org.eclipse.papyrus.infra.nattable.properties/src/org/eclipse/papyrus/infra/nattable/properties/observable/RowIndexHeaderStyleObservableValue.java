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

import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AbstractHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationPackage;


/**
 * The row observable value for {@link AbstractHeaderAxisConfiguration#indexStyle}
 *
 * @author Vincent Lorenzo
 *
 */
public class RowIndexHeaderStyleObservableValue extends AbstractRowHeaderAxisConfigurationObservableValue {

	/**
	 *
	 * Constructor.
	 *
	 * @param table
	 */
	public RowIndexHeaderStyleObservableValue(final Table table) {
		super(table, NattableaxisconfigurationPackage.eINSTANCE.getAbstractHeaderAxisConfiguration_IndexStyle());
	}

	/**
	 *
	 * @see org.eclipse.core.databinding.observable.value.IObservableValue#getValueType()
	 *
	 * @return
	 */
	@Override
	public Object getValueType() {
		return NattableaxisconfigurationPackage.eINSTANCE.getAxisIndexStyle();
	}
}
