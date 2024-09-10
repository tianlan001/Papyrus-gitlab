/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.properties.observable;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;

/**
 * The observable value for the prototype of the {@link Table}.
 */
@SuppressWarnings("rawtypes")
public class TablePrototypeObservableValue extends AbstractObservableValue {

	/**
	 * The object instance.
	 */
	protected Table table;

	/**
	 * Constructor.
	 *
	 * @param table
	 *            The table.
	 */
	public TablePrototypeObservableValue(final Table table) {
		this(Realm.getDefault(), table);
	}

	/**
	 * Constructor.
	 *
	 * @param realm
	 *            The current Realm.
	 * @param table
	 *            The table.
	 */
	public TablePrototypeObservableValue(final Realm realm, final Table table) {
		super(realm);
		this.table = table;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.databinding.observable.AbstractObservable#dispose()
	 */
	@Override
	public synchronized void dispose() {
		table = null;
		super.dispose();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.databinding.observable.IObserving#getObserved()
	 */
	public Object getObserved() {
		return table;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.databinding.observable.value.AbstractObservableValue#doGetValue()
	 */
	@Override
	protected Object doGetValue() {
		return ViewPrototype.get(table);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.databinding.observable.value.AbstractObservableValue#doSetValue(java.lang.Object)
	 */
	@Override
	protected void doSetValue(final Object value) {
		// Do nothing, impossible to modify it
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.databinding.observable.value.IObservableValue#getValueType()
	 */
	@Override
	public Object getValueType() {
		return ViewPrototype.class;
	}
}
