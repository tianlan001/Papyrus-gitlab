/*******************************************************************************
 * Copyright (c) 2022 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.uml.tests.services;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.eclipse.papyrus.sirius.properties.uml.services.PropertiesImageServices;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link PropertiesImageServices} service class.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class PropertiesImageServicesTest extends AbstractPropertiesServicesTest {

	/**
	 * All values for Kind enumeration on Image.
	 */
	private static final List<String> KIND_ENUMERATION = Arrays.asList("undefined", "icon", "shape"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	/**
	 * The instance of PropertiesServices being tested.
	 */
	private PropertiesImageServices propertiesService;

	@Before
	public void setUp() {
		this.propertiesService = new PropertiesImageServices();
	}

	@Test
	public void testGetImageKindEnumerations() {
		assertEquals(KIND_ENUMERATION, propertiesService.getImageKindEnumerations(null));
	}

}
