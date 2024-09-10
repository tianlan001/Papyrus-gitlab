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

package org.eclipse.papyrus.uml.internationalization.controlmode.tests.tests;

import org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesUtils;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.junit.Test;

/**
 * This allows to test the internationalization during the control mode.
 */
@SuppressWarnings("nls")
@PluginResource({ "resources/controlmode/internationalizationModel.di",
		"resources/controlmode/internationalizationModel_en_US.properties",
		"resources/controlmode/internationalizationModel_fr_FR.properties" })
public class InternationalizationControlModeTest extends AbstractInternationalizationControlModeTest {

	/**
	 * Constructor.
	 */
	public InternationalizationControlModeTest() {
		super();
	}

	/**
	 * This allows to test the control of the class object.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	@Test
	public void testControlClass() throws Exception {
		checkFrenchLabels();

		control(model, modelPackage, fixture);
		checkFrenchLabels();

		undo();
		checkFrenchLabels();

		redo();
		checkFrenchLabels();
	}

	/**
	 * This allows to test the control of the class object with the language
	 * modification preference.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	@Test
	public void testControlWithLanguageModificationTest() throws Exception {
		checkFrenchLabels();

		control(model, modelPackage, fixture);
		checkFrenchLabels();

		InternationalizationPreferencesUtils.setLanguagePreference(modelClass, "en_US");
		checkEnglishLabels();

		undo();
		checkEnglishLabels();

		redo();
		checkEnglishLabels();
	}

	/**
	 * This allows to test the control of the class object with the use
	 * internationalization preference.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	@Test
	public void testControlWithUseInternationalizationModificationTest() throws Exception {
		checkFrenchLabels();

		control(model, modelPackage, fixture);
		checkFrenchLabels();

		InternationalizationPreferencesUtils.setInternationalizationPreference(modelClass, false);
		checkNoLabels();

		undo();
		checkNoLabels();

		redo();
		checkNoLabels();
	}
}