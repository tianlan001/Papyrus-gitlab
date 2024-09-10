/*****************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
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

package org.eclipse.papyrus.junit.utils.rules;

import java.util.function.BiFunction;

import org.eclipse.jface.preference.IPreferenceStore;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * A JUnit test rule for ensuring some Eclipse Preference value for the duration
 * of a test.
 */
public final class PreferenceRule<T> implements TestRule {

	private final IPreferenceStore store;
	private final String key;
	private final T value;
	private T oldValue;

	private final BiFunction<IPreferenceStore, String, ? extends T> accessor;
	private final TriConsumer<IPreferenceStore, String, ? super T> mutator;

	/**
	 * Initializes me.
	 * 
	 * @param store
	 *            the preference store on which to operate
	 * @param key
	 *            the preference key on which to operate
	 * @param the
	 *            value to set in the preference
	 */
	private PreferenceRule(IPreferenceStore store, String key, T value,
			BiFunction<IPreferenceStore, String, ? extends T> accessor,
			TriConsumer<IPreferenceStore, String, ? super T> mutator) {

		super();

		this.store = store;
		this.key = key;
		this.value = value;
		this.accessor = accessor;
		this.mutator = mutator;
	}

	@Override
	public Statement apply(Statement base, Description description) {
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				setPreference(store, key);

				try {
					base.evaluate();
				} finally {
					restorePreference(store, key);
				}
			}
		};
	}

	protected void setPreference(IPreferenceStore store, String key) {
		oldValue = accessor.apply(store, key);
		mutator.accept(store, key, value);
	}

	protected void restorePreference(IPreferenceStore store, String key) {
		mutator.accept(store, key, oldValue);
	}

	/**
	 * Create a boolean-valued preference rule.
	 * 
	 * @param store
	 *            the preference store
	 * @param key
	 *            the preference key
	 * @param value
	 *            the value to set for the duration of the test
	 * @return the preference rule
	 */
	public static final PreferenceRule<Boolean> create(IPreferenceStore store, String key, boolean value) {
		return new PreferenceRule<Boolean>(store, key, value,
				IPreferenceStore::getBoolean, IPreferenceStore::setValue);
	}

	/**
	 * Create a string-valued preference rule.
	 * 
	 * @param store
	 *            the preference store
	 * @param key
	 *            the preference key
	 * @param value
	 *            the value to set for the duration of the test
	 * @return the preference rule
	 */
	public static final PreferenceRule<String> create(IPreferenceStore store, String key, String value) {
		return new PreferenceRule<String>(store, key, value,
				IPreferenceStore::getString, IPreferenceStore::setValue);
	}

	/**
	 * Create an integer-valued preference rule.
	 * 
	 * @param store
	 *            the preference store
	 * @param key
	 *            the preference key
	 * @param value
	 *            the value to set for the duration of the test
	 * @return the preference rule
	 */
	public static final PreferenceRule<Integer> create(IPreferenceStore store, String key, int value) {
		return new PreferenceRule<Integer>(store, key, value,
				IPreferenceStore::getInt, IPreferenceStore::setValue);
	}

	//
	// Nested types
	//

	@FunctionalInterface
	private interface TriConsumer<T, U, V> {
		void accept(T t, U u, V v);
	}
}
