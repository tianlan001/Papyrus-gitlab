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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.papyrus.sirius.properties.uml.services.PropertiesHelpContentServices;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.Property;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link PropertiesHelpContentServices} service class.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class PropertiesHelpContentServicesTest extends AbstractPropertiesServicesTest {

	/**
	 * The instance of PropertiesServices being tested.
	 */
	private PropertiesHelpContentServices propertiesService;

	@Before
	public void setUp() {
		this.propertiesService = new PropertiesHelpContentServices();
	}

	@Test
	public void testGetFeatureDescriptionNullObj() {
		try {
			propertiesService.getFeatureDescription(null, NAME);
			fail("An exception should occurs when a null target is used."); //$NON-NLS-1$
			// CHECKSTYLE:OFF
		} catch (Exception ex) {
			// CHECKSTYLE:ON
			assertTrue(ex instanceof NullPointerException);
		}
	}

	@Test
	public void testGetFeatureDescriptionNullFeatureName() {
		Property property1 = create(Property.class);
		assertNull(propertiesService.getFeatureDescription(property1, null));
	}

	@Test
	public void testGetFeatureDescriptionUnknownFeatureName() {
		Property property1 = create(Property.class);
		assertNull(propertiesService.getFeatureDescription(property1, FAKE));
	}

	@Test
	public void testGetFeatureDescriptionKnownFeatureName() {
		Property property1 = create(Property.class);
		assertEquals("The name of the NamedElement.\n", propertiesService.getFeatureDescription(property1, NAME)); //$NON-NLS-1$
	}

	@Test
	public void testGetMultiplicityHelpContent() {
		MultiplicityElement multiplicity = create(Property.class);
		String helpContent = propertiesService.getMultiplicityHelpContent(multiplicity);
		assertEquals("A multiplicity is a definition of an inclusive interval of non-negative integers " //$NON-NLS-1$
				+ "beginning with a lower bound and ending with a (possibly infinite) upper bound. " //$NON-NLS-1$
				+ "A MultiplicityElement embeds this information to specify the allowable cardinalities " //$NON-NLS-1$
				+ "for an instantiation of the Element.\n" //$NON-NLS-1$
				+ "Example of valid formats: 1, 0..12, 1..*, *", helpContent); //$NON-NLS-1$
	}

}
