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

package org.eclipse.papyrus.infra.editor.welcome.internal.modelelements;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.papyrus.infra.core.language.ILanguage;
import org.eclipse.papyrus.infra.core.language.Version;
import org.eclipse.papyrus.infra.tools.databinding.ReferenceCountedObservable;
import org.eclipse.papyrus.infra.tools.databinding.TouchableValue;

/**
 * An observable encapsulation of an {@link ILanguage}.
 */
public class LanguageObservable extends ReferenceCountedObservable.Abstract {
	private final ILanguage language;

	private final TouchableValue<String> nameValue;
	private final TouchableValue<Version> versionValue;

	public LanguageObservable(ILanguage language) {
		this(Realm.getDefault(), language);
	}

	public LanguageObservable(Realm realm, ILanguage language) {
		super(realm);

		this.language = language;

		this.nameValue = new TouchableValue<String>(realm, String.class, language.getName());
		this.versionValue = new TouchableValue<Version>(realm, Version.class, language.getVersion());

		// Roll up changes to my elements as changes to me
		IChangeListener rollup = new IChangeListener() {

			@Override
			public void handleChange(ChangeEvent event) {
				LanguageObservable.this.fireChange();
			}
		};
		nameValue.addChangeListener(rollup);
		versionValue.addChangeListener(rollup);
	}

	@Override
	public synchronized void dispose() {
		nameValue.dispose();
		versionValue.dispose();

		super.dispose();
	}

	public IObservableValue<String> getName() {
		return nameValue;
	}

	public IObservableValue<Version> getVersion() {
		return versionValue;
	}

	@Override
	public boolean isStale() {
		return false;
	}

	ILanguage getLanguage() {
		return language;
	}
}
