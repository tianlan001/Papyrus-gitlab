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

import org.eclipse.papyrus.infra.nattable.model.nattable.Table;


/**
 * This class provide the observable value to edit the filer defined in the first TreeFillingConfiguration found for depth=0 and defined for row
 */
public class MatrixRowRootFilterObservableValue extends AbstractMatrixFirstTreeFillingConfigurationFilterEMFObservable  {



	/**
	 * Constructor.
	 * 
	 * @param table
	 *            The managed table.
	 */
	public MatrixRowRootFilterObservableValue(final Table table) {
		super(table, 1, false);
	}

	/**
	 * @see org.eclipse.core.databinding.observable.value.AbstractObservableValue#doSetValue(java.lang.Object)
	 *
	 * @param value
	 */
	@Override
	protected void doSetValue(Object value) {
		super.doSetValue(value);
	}


}
