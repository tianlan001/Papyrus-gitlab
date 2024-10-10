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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.sirius.properties.uml.services.PropertiesCrudServices;
import org.eclipse.uml2.uml.Actor;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Property;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link PropertiesCrudServices} service class.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class PropertiesCrudServicesTest extends AbstractPropertiesServicesTest {

	/**
	 * ConstrainedElement reference name.
	 */
	private static final String CONSTRAINED_ELEMENT = "constrainedElement"; //$NON-NLS-1$

	/**
	 * OwnedAttribute reference name.
	 */
	private static final String OWNED_ATTRIBUTE = "ownedAttribute"; //$NON-NLS-1$

	/**
	 * PackagedElement reference name.
	 */
	private static final String PACKAGED_ELEMENT = "packagedElement"; //$NON-NLS-1$

	/**
	 * Type reference name.
	 */
	private static final String TYPE = "type"; //$NON-NLS-1$

	/**
	 * New name to set.
	 */
	private static final String UPDATE_NAME = "updateName"; //$NON-NLS-1$

	/**
	 * The instance of PropertiesServices being tested.
	 */
	private PropertiesCrudServices propertiesService;

	@Before
	public void setUp() {
		this.propertiesService = new PropertiesCrudServices();
	}

	@Test
	public void testDeleteNullObj() {
		Component component = create(Component.class);
		assertFalse(propertiesService.delete(null, component, PACKAGED_ELEMENT));
	}

	/**
	 * reference is null or unknown: EcoreUtil.remove is called by default.
	 */
	@Test
	public void testDeleteUnknownRef() {
		Component component = create(Component.class);
		org.eclipse.uml2.uml.Class clazz = create(org.eclipse.uml2.uml.Class.class);
		component.getPackagedElements().add(clazz);
		assertTrue(propertiesService.delete(clazz, component, null));
		assertTrue(component.getPackagedElements().isEmpty());
	}

	@Test
	public void testDeleteKnownContainmentRef() {
		Component component = create(Component.class);
		org.eclipse.uml2.uml.Class clazz = create(org.eclipse.uml2.uml.Class.class);
		component.getPackagedElements().add(clazz);
		assertTrue(propertiesService.delete(clazz, component, PACKAGED_ELEMENT));
		assertTrue(component.getPackagedElements().isEmpty());
	}

	@Test
	public void testDeleteKnownNonContainmentRef() {
		Constraint constraint = create(Constraint.class);
		org.eclipse.uml2.uml.Class clazz = create(org.eclipse.uml2.uml.Class.class);
		constraint.getConstrainedElements().add(clazz);
		assertTrue(propertiesService.delete(clazz, constraint, CONSTRAINED_ELEMENT));
		assertTrue(constraint.getConstrainedElements().isEmpty());
	}

	@Test
	public void testCreateContainmentRef() {
		Component component = create(Component.class);
		EObject createdObject = propertiesService.create(component, Actor.class.getSimpleName(), PACKAGED_ELEMENT);
		assertEquals(component, createdObject.eContainer());
		EStructuralFeature ref = component.eClass().getEStructuralFeature(PACKAGED_ELEMENT);
		Object eGet = component.eGet(ref);
		List<?> list = (List<?>) eGet;
		assertTrue(list.contains(createdObject));
	}

	@Test
	public void testCreateNonContainmentRef() {
		Property property = create(Property.class);
		EObject createdObject = propertiesService.create(property, Actor.class.getSimpleName(), TYPE);
		EStructuralFeature ref = property.eClass().getEStructuralFeature(TYPE);
		assertEquals(createdObject, property.eGet(ref));
	}

	@Test
	public void testSetNullTarget() {
		assertFalse(propertiesService.set(null, NAME, UPDATE_NAME));
	}

	@Test
	public void testSetUknownFeatureName() {
		org.eclipse.uml2.uml.Class clazz = create(org.eclipse.uml2.uml.Class.class);
		assertFalse(propertiesService.set(clazz, FAKE, UPDATE_NAME));
	}

	@Test
	public void testSetUnaryFeature() {
		org.eclipse.uml2.uml.Class clazz = create(org.eclipse.uml2.uml.Class.class);
		clazz.setName("name1"); //$NON-NLS-1$
		assertTrue(propertiesService.set(clazz, NAME, UPDATE_NAME));
		assertEquals(UPDATE_NAME, clazz.getName());
	}

	@Test
	public void testSetNaryFeature() {
		org.eclipse.uml2.uml.Class clazz = create(org.eclipse.uml2.uml.Class.class);
		Property property1 = create(Property.class);
		Property property2 = create(Property.class);
		clazz.getOwnedAttributes().add(property1);
		assertTrue(propertiesService.set(clazz, OWNED_ATTRIBUTE, Arrays.asList(property2)));
		assertEquals(Arrays.asList(property2), clazz.getOwnedAttributes());
	}

	@Test
	public void testUpdateReferenceNullTarget() {
		Property property2 = create(Property.class);
		assertFalse(propertiesService.updateReference(null, property2, OWNED_ATTRIBUTE));
	}

	@Test
	public void testUpdateReferenceUnknownFeature() {
		org.eclipse.uml2.uml.Class clazz = create(org.eclipse.uml2.uml.Class.class);
		Property property2 = create(Property.class);
		assertFalse(propertiesService.updateReference(clazz, property2, FAKE));
	}

	@Test
	public void testUpdateReferenceContainmentFeature() {
		org.eclipse.uml2.uml.Class clazz = create(org.eclipse.uml2.uml.Class.class);
		Property property1 = create(Property.class);
		Property property2 = create(Property.class);
		clazz.getOwnedAttributes().add(property1);
		assertFalse(propertiesService.updateReference(clazz, property2, OWNED_ATTRIBUTE));
	}

	@Test
	public void testUpdateReferenceNonContainmentFeatureWothElement() {
		Constraint constraint = create(Constraint.class);
		org.eclipse.uml2.uml.Class clazz1 = create(org.eclipse.uml2.uml.Class.class);
		constraint.getConstrainedElements().add(clazz1);
		org.eclipse.uml2.uml.Class clazz2 = create(org.eclipse.uml2.uml.Class.class);
		assertTrue(propertiesService.updateReference(constraint, clazz2, CONSTRAINED_ELEMENT));
		assertEquals(Arrays.asList(clazz1, clazz2), constraint.getConstrainedElements());
	}

	@Test
	public void testUpdateReferenceNonContainmentFeatureWithList() {
		Constraint constraint = create(Constraint.class);
		org.eclipse.uml2.uml.Class clazz1 = create(org.eclipse.uml2.uml.Class.class);
		constraint.getConstrainedElements().add(clazz1);
		org.eclipse.uml2.uml.Class clazz2 = create(org.eclipse.uml2.uml.Class.class);
		assertTrue(propertiesService.updateReference(constraint, Arrays.asList(clazz2), CONSTRAINED_ELEMENT));
		assertEquals(Arrays.asList(clazz2), constraint.getConstrainedElements());
	}

}
