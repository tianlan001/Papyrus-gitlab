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

package org.eclipse.papyrus.infra.internationalization.tests.tests;

import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.junit.Test;

/**
 * This allows to test the labels of different objects and test the
 * internationalization.
 */
@SuppressWarnings("nls")
@PluginResource({"resources/internationalizationModel.di", "resources/internationalizationModel_en_US.properties", "resources/internationalizationModel_fr_FR.properties"})
public class InternationalizationLabelProviderTest extends AbstractInternationalizationTest {

	/**
	 * Constructor.
	 */
	public InternationalizationLabelProviderTest() {
		super();
	}

	/**
	 * This allows to test the labels of different objects.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	@Test
	public void testLabels() throws Exception {
		checkFrenchLabels();
	}
}
