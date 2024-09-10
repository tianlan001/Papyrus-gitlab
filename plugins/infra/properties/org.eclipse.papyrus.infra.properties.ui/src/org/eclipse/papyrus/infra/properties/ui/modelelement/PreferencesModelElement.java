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
package org.eclipse.papyrus.infra.properties.ui.modelelement;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.internal.ui.observable.PreferencesObservableValue;
import org.eclipse.ui.preferences.ScopedPreferenceStore;


public class PreferencesModelElement extends AbstractModelElement {

	protected DataContextElement context;

	protected IPreferenceStore store;

	public PreferencesModelElement(DataContextElement context) {
		this.context = context;
		store = new ScopedPreferenceStore(InstanceScope.INSTANCE, context.getName());
	}

	@Override
	protected IObservable doGetObservable(String propertyPath) {
		return new PreferencesObservableValue(propertyPath, store);
	}
}
