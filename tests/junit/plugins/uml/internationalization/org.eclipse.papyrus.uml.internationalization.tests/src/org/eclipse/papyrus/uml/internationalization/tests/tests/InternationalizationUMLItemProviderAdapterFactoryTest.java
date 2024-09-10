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
package org.eclipse.papyrus.uml.internationalization.tests.tests;

import static org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesUtils.setInternationalizationPreference;
import static org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesUtils.setLanguagePreference;
import static org.eclipse.papyrus.junit.matchers.MoreMatchers.greaterThan;
import static org.eclipse.papyrus.junit.matchers.MoreMatchers.lessThan;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Locale;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.internationalization.edit.providers.InternationalizationUMLItemProviderAdapterFactory;
import org.eclipse.papyrus.uml.internationalization.tests.DefaultLocaleRule;
import org.junit.After;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * Test cases for the {@link InternationalizationUMLItemProviderAdapterFactory} class.
 *
 * @author Christian W. Damus
 */
@SuppressWarnings("nls")
@PluginResource({ "resources/model.di", "resources/model_en_US.properties", "resources/model_fr_FR.properties" })
public class InternationalizationUMLItemProviderAdapterFactoryTest extends AbstractUMLInternationalizationTest {

	// Set a locale in the VM that does not have a corresponding properties file, otherwise
	// if the host system happens to be en_US then these tests will fail because the framework
	// will default to enabling internationalization
	@ClassRule
	public static final DefaultLocaleRule defaultLocale = new DefaultLocaleRule(Locale.KOREAN);

	private AdapterFactory factory;

	/**
	 * Initializes e.
	 */
	public InternationalizationUMLItemProviderAdapterFactoryTest() {
		super();
	}

	@Test
	public void normalAdapters() {
		checkUMLNoLabels();

		// The last adapter added should be our item-provider
		Adapter last = modelClass.eAdapters().get(modelClass.eAdapters().size() - 1);
		assertThat(last.getClass().getSimpleName(), is("ClassItemProvider"));
	}

	@Test
	public void rebuildAdapters() {
		int initialAdapterCount = modelClass.eAdapters().size();
		checkUMLNoLabels();

		int newAdapterCount = modelClass.eAdapters().size();
		assertThat(newAdapterCount, greaterThan(initialAdapterCount));

		Adapter adapter = modelClass.eAdapters().get(newAdapterCount - 1);

		setLanguagePreference(modelClass, "fr_FR");
		setInternationalizationPreference(modelClass, true);

		// Purged the adapters
		assertThat(modelClass.eAdapters().size(), lessThan(newAdapterCount));

		checkUMLFrenchLabels();

		// Just replaced the adapter with a new one
		assertThat(modelClass.eAdapters().size(), is(newAdapterCount));
		Adapter newAdapter = modelClass.eAdapters().get(newAdapterCount - 1);
		assertThat(newAdapter.getClass(), not(adapter.getClass()));
	}

	//
	// Test framework
	//

	@Override
	protected ILabelProvider initLabelProvider() {
		factory = new InternationalizationUMLItemProviderAdapterFactory();

		// Initially disable the i18n
		setInternationalizationPreference(fixture.getModelResource(), false);

		return new AdapterFactoryLabelProvider(factory);
	}

	@After
	public void disposeFactory() {
		labelProvider.dispose();
		((IDisposable) factory).dispose();
	}

	/**
	 * Verify that the labels in the model are the standard UML-defined labels.
	 */
	public void checkUMLNoLabels() {
		assertThat(labelProvider.getText(model), is(withMetaclass(model, "RootElement")));
		assertThat(labelProvider.getText(modelClass), is(withMetaclass(modelClass, CLASS_NAME)));
		assertThat(labelProvider.getText(modelOperation),
				is(withMetaclass(modelOperation, OPERATION_NAME + " (" + PARAMETER_NAME + ")")));
		assertThat(labelProvider.getText(modelParameter), is(withMetaclass(modelParameter, PARAMETER_NAME)));
		assertThat(labelProvider.getText(modelProperty), is(withMetaclass(modelProperty, PROPERTY_NAME)));
		assertThat(labelProvider.getText(modelPackage), is(withMetaclass(modelPackage, PACKAGE_NAME)));
		assertThat(labelProvider.getText(modelInterface), is(withMetaclass(modelInterface, INTERFACE_NAME)));
		assertThat(labelProvider.getText(modelEnumeration), is(withMetaclass(modelEnumeration, ENUMERATION_NAME)));
	}

	/**
	 * Verify that the labels in the model are the standard UML-ish French labels.
	 */
	public void checkUMLFrenchLabels() {
		assertThat(labelProvider.getText(model), is(withMetaclass(model, "MonElementRoot")));
		assertThat(labelProvider.getText(modelClass), is(withMetaclass(modelClass, "MaPremiereClasse")));
		assertThat(labelProvider.getText(modelOperation),
				is(withMetaclass(modelOperation, "MonOperation (MonParametre)")));
		assertThat(labelProvider.getText(modelParameter), is(withMetaclass(modelParameter, "MonParametre")));
		assertThat(labelProvider.getText(modelProperty), is(withMetaclass(modelProperty, "MonAttribut")));
		assertThat(labelProvider.getText(modelPackage), is(withMetaclass(modelPackage, "MonPackage")));
		assertThat(labelProvider.getText(modelInterface), is(withMetaclass(modelInterface, "MonInterface")));
		assertThat(labelProvider.getText(modelEnumeration), is(withMetaclass(modelEnumeration, "MonEnumeration")));
	}

	private static String withMetaclass(EObject object, String label) {
		return String.format("<%s> %s", object.eClass().getName(), label);
	}
}
