/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.internal.ui.observable;

import org.eclipse.core.databinding.observable.Diffs;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;


public class PreferencesObservableValue extends AbstractObservableValue implements IPropertyChangeListener {

	protected String key;

	protected IPreferenceStore store;

	public PreferencesObservableValue(String key, IPreferenceStore store) {
		this.key = key;
		this.store = store;
		store.addPropertyChangeListener(this);
	}

	public Object getValueType() {
		return String.class;
	}

	@Override
	protected Object doGetValue() {
		if (store.contains(key)) {
			return store.getString(key);
		}
		return null;
	}

	@Override
	protected void doSetValue(Object value) {
		if (value instanceof String) {
			store.setValue(key, (String) value);
		}
	}

	public void propertyChange(PropertyChangeEvent event) {
		if (event.getProperty().equals(key)) {
			fireValueChange(Diffs.createValueDiff(event.getOldValue(), event.getNewValue()));
		}
	}

	@Override
	public void dispose() {
		store.removePropertyChangeListener(this);
		super.dispose();
	}
}
