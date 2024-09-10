/*****************************************************************************
 * Copyright (c) 2015, 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.tools.databinding;

import java.util.Objects;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.WritableValue;

/**
 * An analogue of the {@link WritableValue} that supports "touches" to send
 * change events even though the value is not replaced.
 * 
 * @since 2.0
 */
public class TouchableValue<T> extends ReferenceCountedObservable.Value<T> {
	private final Class<? extends T> type;

	private T value;

	public TouchableValue(Realm realm, Class<? extends T> type) {
		super(realm);

		this.type = type;
	}

	public TouchableValue(Realm realm, Class<? extends T> type, T initialValue) {
		super(realm);

		this.type = type;
		this.value = initialValue;
	}

	@Override
	public Object getValueType() {
		return type;
	}

	@Override
	protected T doGetValue() {
		return value;
	}

	@Override
	protected void doSetValue(T value) {
		if (!Objects.equals(this.value, value)) {
			T oldValue = this.value;
			this.value = value;
			fireValueChange(Diffs.createValueDiff(oldValue, value));
		}
	}

	/**
	 * Indicates that some kind of change has happened to the observable's value
	 * that observers should know about, but for which specific change details
	 * are not available.
	 */
	public void touch() {
		checkRealm();
		fireEvent(new ChangeEvent(this));
	}
}
