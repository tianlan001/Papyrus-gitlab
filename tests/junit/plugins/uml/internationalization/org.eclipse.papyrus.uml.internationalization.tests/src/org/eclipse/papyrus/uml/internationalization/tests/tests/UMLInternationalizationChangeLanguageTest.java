/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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

package org.eclipse.papyrus.uml.internationalization.tests.tests;

import org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesUtils;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.junit.Test;

/**
 * This allows to test the internationalization preferences modification.
 */
@SuppressWarnings("nls")
@PluginResource({"resources/internationalizationChangeLanguageModel.di", "resources/internationalizationChangeLanguageModel_en_US.properties", "resources/internationalizationChangeLanguageModel_fr_FR.properties"})
public class UMLInternationalizationChangeLanguageTest extends AbstractUMLInternationalizationTest {

	/**
	 * Constructor.
	 */
	public UMLInternationalizationChangeLanguageTest() {
		super();
	}

	/**
	 * This allows to test the language preference modification.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	@Test
	public void testLanguageModification() throws Exception {
		checkFrenchLabels();

		InternationalizationPreferencesUtils.setLanguagePreference(model, "en_US");
		checkEnglishLabels();

		InternationalizationPreferencesUtils.setLanguagePreference(model, "es_ES");
		checkNoLabels();
	}
}
