/*****************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
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
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.tools.databinding;

import java.util.List;

import org.eclipse.core.databinding.observable.value.IObservableValue;



/**
 * Interface for a multiple selection of observable values.
 */
public interface IMultipleObservableValue extends AggregatedObservable, IObservableValue {

	/**
	 * @return the list of sub-observable values
	 */
	List<IObservableValue> getObservableValues();

	/**
	 * @return the list of observed values
	 */
	List<Object> getObservedValues();

}