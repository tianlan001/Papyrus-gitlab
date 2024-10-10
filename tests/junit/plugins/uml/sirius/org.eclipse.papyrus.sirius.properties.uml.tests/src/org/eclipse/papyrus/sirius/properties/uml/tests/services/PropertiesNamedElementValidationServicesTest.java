/*******************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.papyrus.sirius.properties.uml.services.PropertiesNamedElementValidationServices;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Package;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link PropertiesNamedElementValidationServices} service class.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class PropertiesNamedElementValidationServicesTest extends AbstractPropertiesServicesTest {

	private PropertiesNamedElementValidationServices propertiesService;

	@Before
	public void setUp() {
		this.propertiesService = new PropertiesNamedElementValidationServices();
	}

	@Test
	public void testValidateNamedElementValidNames() {
		Package pack = create(Package.class);
		Class clazz = createIn(Class.class, pack);
		Component component = createIn(Component.class, pack);
		clazz.setName("My Named Element"); //$NON-NLS-1$
		component.setName("My other Named Element"); //$NON-NLS-1$
		assertTrue(propertiesService.validateNamedElement(clazz));
		assertTrue(propertiesService.validateNamedElement(component));
		assertTrue(propertiesService.validateNamedElement(pack));
	}

	@Test
	public void testValidateNamedElementInvalidNames() {
		Package pack = create(Package.class);
		Class clazz = createIn(Class.class, pack);
		Component component = createIn(Component.class, pack);
		clazz.setName("custom Named Element"); //$NON-NLS-1$
		component.setName("custom Named Element"); //$NON-NLS-1$
		assertFalse(propertiesService.validateNamedElement(clazz));
		assertFalse(propertiesService.validateNamedElement(component));
		assertTrue(propertiesService.validateNamedElement(pack));
	}
}
