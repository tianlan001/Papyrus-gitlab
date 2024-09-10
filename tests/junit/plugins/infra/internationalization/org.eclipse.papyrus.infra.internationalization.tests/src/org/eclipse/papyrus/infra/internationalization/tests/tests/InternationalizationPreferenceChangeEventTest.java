/*******************************************************************************
 * Copyright (c) 2017 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Christian W. Damus - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.infra.internationalization.tests.tests;

import static org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesConstants.LANGUAGE_PREFERENCE;
import static org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesConstants.USE_INTERNATIONALIZATION_PREFERENCE;
import static org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesUtils.getInternationalizationPreference;
import static org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesUtils.getPreferenceStore;
import static org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesUtils.setInternationalizationPreference;
import static org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesUtils.setLanguagePreference;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntSupplier;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.internationalization.common.Activator;
import org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferenceChangeEvent;
import org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferenceListener;
import org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesUtils;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the notification of internationalization preference changes.
 *
 * @author Christian W. Damus
 */
@SuppressWarnings("nls")
@PluginResource({ "resources/internationalizationModel.di", "resources/internationalizationModel_en_US.properties",
		"resources/internationalizationModel_fr_FR.properties" })
public class InternationalizationPreferenceChangeEventTest extends AbstractInternationalizationTest {

	private boolean useRestore;
	private String languageRestore;

	private InternationalizationPreferenceListener listener;

	/**
	 * Initializes me.
	 */
	public InternationalizationPreferenceChangeEventTest() {
		super();
	}

	/**
	 * Verify the notification that enablement of internationalization changes.
	 */
	@Test
	public void enablementNotification() {
		IntSupplier count = listen(InternationalizationPreferenceChangeEvent.ENABLED, diagram,
				event -> assertThat(event.isInternationalizationEnabled(), is(false)));

		setInternationalizationPreference(diagram, false);
		assertThat(count.getAsInt(), is(1));

		count = listen(InternationalizationPreferenceChangeEvent.ENABLED, diagram,
				event -> assertThat(event.isInternationalizationEnabled(), is(true)));

		setInternationalizationPreference(diagram, true);
		assertThat(count.getAsInt(), is(1));
	}

	/**
	 * Verify the notification that the locale of internationalization changes.
	 */
	@Test
	public void localeNotification() {
		IntSupplier count = listen(InternationalizationPreferenceChangeEvent.LOCALE, diagram,
				event -> assertThat(event.getLocale(), is(Locale.CANADA_FRENCH)));

		setLanguagePreference(diagram, Locale.CANADA_FRENCH.toLanguageTag());
		assertThat(count.getAsInt(), is(1));

		Locale en_US = Locale.forLanguageTag("en_US");
		count = listen(InternationalizationPreferenceChangeEvent.LOCALE, diagram,
				event -> assertThat(event.getLocale(), is(en_US)));

		setLanguagePreference(diagram, "en_US");
		assertThat(count.getAsInt(), is(1));
	}

	/**
	 * Verify the notification that enablement of internationalization changes by direct
	 * manipulation of the preference store.
	 */
	@Test
	public void enablementNotificationByPreferences() {
		IntSupplier count = listen(InternationalizationPreferenceChangeEvent.ENABLED, diagram,
				event -> assertThat(event.isInternationalizationEnabled(), is(false)));

		IPreferenceStore prefStore = Activator.getDefault().getInternationalizationPreferenceStore(fixture.getProject().getProject(),
				fixture.getModelURI().trimFileExtension().lastSegment());
		assumeThat("No preference store", prefStore, notNullValue());
		prefStore.setValue(USE_INTERNATIONALIZATION_PREFERENCE, "false"); // Most primitive access
		assertThat(count.getAsInt(), is(1));

		count = listen(InternationalizationPreferenceChangeEvent.ENABLED, diagram,
				event -> assertThat(event.isInternationalizationEnabled(), is(true)));

		prefStore.setValue(USE_INTERNATIONALIZATION_PREFERENCE, "true"); // Most primitive access
		assertThat(count.getAsInt(), is(1));
	}

	/**
	 * Verify the notification that the locale of internationalization changes by direct
	 * manipulation of the preference store.
	 */
	@Test
	public void localeNotificationByPreferences() {
		IntSupplier count = listen(InternationalizationPreferenceChangeEvent.LOCALE, diagram,
				event -> assertThat(event.getLocale(), is(Locale.CANADA_FRENCH)));

		IPreferenceStore prefStore = Activator.getDefault().getInternationalizationPreferenceStore(fixture.getProject().getProject(),
				fixture.getModelURI().trimFileExtension().lastSegment());
		assumeThat("No preference store", prefStore, notNullValue());
		prefStore.setValue(LANGUAGE_PREFERENCE, Locale.CANADA_FRENCH.toLanguageTag());
		assertThat(count.getAsInt(), is(1));

		Locale en_US = Locale.forLanguageTag("en_US");
		count = listen(InternationalizationPreferenceChangeEvent.LOCALE, diagram,
				event -> assertThat(event.getLocale(), is(en_US)));

		prefStore.setValue(LANGUAGE_PREFERENCE, "en_US");
		assertThat(count.getAsInt(), is(1));
	}

	/**
	 * Verify that a removed listener gets no notifications.
	 */
	@Test
	public void removePreferenceListener() {
		IntSupplier count = listen(InternationalizationPreferenceChangeEvent.LOCALE, diagram,
				event -> assertThat(event.getLocale(), is(Locale.CANADA_FRENCH)));

		setLanguagePreference(diagram, Locale.CANADA_FRENCH.toLanguageTag());
		assertThat(count.getAsInt(), is(1));

		removeListener();

		setLanguagePreference(diagram, "en_US");
		assertThat(count.getAsInt(), is(1)); // No new notification
	}

	//
	// Test framework
	//

	@Before
	public void captureInitialState() {
		useRestore = getInternationalizationPreference(diagram);

		// Asymmetric locale getter and language setter (!)
		languageRestore = getPreferenceStore(diagram).getString(LANGUAGE_PREFERENCE);
	}

	@After
	public void removeListener() {
		if (listener != null) {
			InternationalizationPreferencesUtils.removePreferenceListener(listener);
			listener = null;
		}
	}

	@After
	public void restoreInitialState() {
		setInternationalizationPreference(diagram, useRestore);
		setLanguagePreference(diagram, languageRestore);
	}

	/**
	 * Adds a preference listener.
	 * 
	 * @param eventType
	 *            the event type to assert for every incoming event
	 * @param subject
	 *            an object in the resource which URI to assert for every incoming
	 *            event
	 * @param listener
	 *            the listener to add, with custom assertions
	 * 
	 * @return a supplier of the number of events received by the {@code listener}
	 */
	IntSupplier listen(int eventType, EObject subject, InternationalizationPreferenceListener listener) {
		// Remove previous listener, if any
		removeListener();

		if (listener == null) {
			return () -> 0;
		} else {
			AtomicInteger count = new AtomicInteger();

			// Wrap the listener with standard assertions on the events
			this.listener = event -> {
				assertThat(event.getSource(), instanceOf(IPreferenceStore.class));
				assertThat(event.getResourceURI().trimFileExtension(),
						is(fixture.getModelSet().getURIWithoutExtension()));
				assertThat(event.getEventType(), is(eventType));

				count.incrementAndGet();
			};

			InternationalizationPreferencesUtils.addPreferenceListener(this.listener);
			return count::intValue;
		}
	}
}
