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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.papyrus.sirius.properties.uml.services.PropertiesUMLServices;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.internal.resource.UMLResourceImpl;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link PropertiesUMLServices} service class.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
@SuppressWarnings("restriction")
public class PropertiesUMLServicesTest extends AbstractPropertiesServicesTest {

	/**
	 * Fake URI used to create resource.
	 */
	private static final String FAKE_URI = "fake://1"; //$NON-NLS-1$

	/**
	 * Integer as String.
	 */
	private static final String INTEGER_AS_STRING = "15"; //$NON-NLS-1$

	/**
	 * Integer as String with space.
	 */
	private static final String INTEGER_AS_STRING_WITH_SPACE = " 15"; //$NON-NLS-1$

	/**
	 * Papyrus Version EAnnotation.
	 */
	private static final String PAPYRUS_VERSION = "PapyrusVersion"; //$NON-NLS-1$

	/**
	 * Wildcard string.
	 */
	private static final String STAR = "*"; //$NON-NLS-1$

	/**
	 * Unknown location for package.
	 */
	private static final String UNKNOWN = "Unknown"; //$NON-NLS-1$

	/**
	 * The instance of PropertiesServices being tested.
	 */
	private PropertiesUMLServices propertiesService;

	@Before
	public void setUp() {
		this.propertiesService = new PropertiesUMLServices();
	}

	@Test
	public void testConvertStringToIntegerNullString() {
		assertNull(propertiesService.convertStringToInteger(null));
	}

	@Test
	public void testConvertStringToIntegerEmptyString() {
		assertNull(propertiesService.convertStringToInteger(EMPTY));
	}

	@Test
	public void testConvertStringToIntegerIntString() {
		assertEquals(Integer.valueOf(15), propertiesService.convertStringToInteger(INTEGER_AS_STRING_WITH_SPACE));
	}

	@Test
	public void testConvertStringToIUnlimitedNaturalNullString() {
		assertEquals(Integer.valueOf(0), propertiesService.convertStringToIUnlimitedNatural(null));
	}

	@Test
	public void testConvertStringToIUnlimitedNaturalEmptyString() {
		assertNull(propertiesService.convertStringToIUnlimitedNatural(EMPTY));
	}

	@Test
	public void testConvertStringToIUnlimitedNaturalIntString() {
		assertEquals(Integer.valueOf(15), propertiesService.convertStringToIUnlimitedNatural(INTEGER_AS_STRING_WITH_SPACE));
	}

	@Test
	public void testConvertStringToIUnlimitedNaturalWildcardString() {
		assertEquals(Integer.valueOf(-1), propertiesService.convertStringToIUnlimitedNatural(STAR));
	}


	@Test
	public void testConvertStringToRealNullString() {
		assertNull(propertiesService.convertStringToReal(null));
	}

	@Test
	public void testConvertStringToRealEmptyString() {
		assertNull(propertiesService.convertStringToReal(EMPTY));
	}

	@Test
	public void testConvertStringToRealIntString() {
		assertEquals(Double.valueOf(15), propertiesService.convertStringToReal(INTEGER_AS_STRING_WITH_SPACE));
	}

	@Test
	public void testConvertUnlimitedNaturalToStringNullString() {
		assertEquals(EMPTY, propertiesService.convertUnlimitedNaturalToString(null));
	}

	@Test
	public void testConvertUnlimitedNaturalToStringEmptyString() {
		assertEquals(INTEGER_AS_STRING, propertiesService.convertUnlimitedNaturalToString(Integer.valueOf(15)));
	}

	@Test
	public void testConvertUnlimitedNaturalToStringIntString() {
		assertEquals(STAR, propertiesService.convertUnlimitedNaturalToString(Integer.valueOf(-1)));
	}

	@Test
	public void testGetLocationNullPackage() {
		try {
			propertiesService.getLocation(null);
			fail("An exception should occurs when Package is null."); //$NON-NLS-1$
		} catch (NullPointerException ex) {
			assertEquals("None location on Null Package.", ex.getMessage()); //$NON-NLS-1$
		}
	}


	@Test
	public void testGetLocationNullResource() {
		org.eclipse.uml2.uml.Package package1 = create(org.eclipse.uml2.uml.Package.class);
		assertEquals(UNKNOWN, propertiesService.getLocation(package1));
	}

	@Test
	public void testGetLocationPackageInResource() {
		ResourceSet rs = new ResourceSetImpl();
		UMLResource r1 = new UMLResourceImpl(URI.createURI(FAKE_URI));
		rs.getResources().add(r1);
		org.eclipse.uml2.uml.Package package1 = create(org.eclipse.uml2.uml.Package.class);
		r1.getContents().add(package1);
		assertEquals(FAKE_URI, propertiesService.getLocation(package1));
	}

	@Test
	public void testGetLocationPackageInResourceNullUri() {
		ResourceSet rs = new ResourceSetImpl();
		UMLResource r1 = new UMLResourceImpl(null);
		rs.getResources().add(r1);
		org.eclipse.uml2.uml.Package package1 = create(org.eclipse.uml2.uml.Package.class);
		r1.getContents().add(package1);
		assertEquals(UNKNOWN, propertiesService.getLocation(package1));
	}

	@Test
	public void testGetBooleanEnumerations() {
		assertEquals(Arrays.asList(Boolean.TRUE, Boolean.FALSE), propertiesService.getBooleanEnumerations(null));
	}

	@Test
	public void testIsAttachedToResourceNullElt() {
		assertFalse(propertiesService.isAttachedToResource(null));
	}

	@Test
	public void testIsAttachedToResourceNotNullElt() {
		Property property1 = create(Property.class);
		assertFalse(propertiesService.isAttachedToResource(property1));
	}

	@Test
	public void testIsAttachedToResourceNotNullEltWithResource() {
		ResourceSet rs = new ResourceSetImpl();
		UMLResource r1 = new UMLResourceImpl(URI.createURI(FAKE_URI)); // $NON-NLS-1$
		rs.getResources().add(r1);
		org.eclipse.uml2.uml.Package package1 = create(org.eclipse.uml2.uml.Package.class);
		r1.getContents().add(package1);
		assertTrue(propertiesService.isAttachedToResource(package1));
	}

	@Test
	public void testHasEAnnotationNullElt() {
		assertFalse(propertiesService.hasEAnnotation(null));
	}

	@Test
	public void testHasEAnnotationModelEltWithNoEAnnotation() {
		Property property1 = create(Property.class);
		assertFalse(propertiesService.hasEAnnotation(property1));
	}

	@Test
	public void testHasEAnnotationModelEltWithEAnnotation() {
		Property property1 = create(Property.class);
		EAnnotation annotation = EcoreFactory.eINSTANCE.createEAnnotation();
		annotation.setSource(PAPYRUS_VERSION);
		property1.getEAnnotations().add(annotation);
		assertTrue(propertiesService.hasEAnnotation(property1));
	}

	@Test
	public void testIsMetaclassNullElt() {
		assertFalse(propertiesService.isMetaclass(null));
	}

	@Test
	public void testIsMetaclassProperty() {
		Property property1 = create(Property.class);
		assertFalse(propertiesService.isMetaclass(property1));
	}

	@Test
	public void testIsMetaclassClassNoStereotyped() {
		Class clazz1 = create(Class.class);
		assertFalse(propertiesService.isMetaclass(clazz1));
	}

	@Test
	public void testgetAllAppliedComments() {
		Class clazz1 = create(Class.class);
		Comment comment1 = create(Comment.class);
		Comment comment2 = create(Comment.class);

		assertTrue(propertiesService.getAllAppliedComments(clazz1).isEmpty());

		comment1.getAnnotatedElements().add(clazz1);
		comment2.getAnnotatedElements().add(clazz1);

		assertEquals(2, propertiesService.getAllAppliedComments(clazz1).size());
		assertTrue(propertiesService.getAllAppliedComments(clazz1).contains(comment1));
		assertTrue(propertiesService.getAllAppliedComments(clazz1).contains(comment2));
		assertTrue(clazz1.getOwnedComments().isEmpty());
	}

	@Test
	public void testGetAllAppliedCommentsWithOwnedComment() {
		Class clazz1 = create(Class.class);
		Comment comment1 = create(Comment.class);
		Comment comment2 = create(Comment.class);

		assertTrue(clazz1.getOwnedComments().isEmpty());

		clazz1.getOwnedComments().add(comment1);
		clazz1.getOwnedComments().add(comment2);

		assertTrue(propertiesService.getAllAppliedComments(clazz1).isEmpty());

		comment1.getAnnotatedElements().add(clazz1);
		comment2.getAnnotatedElements().add(clazz1);

		assertEquals(2, propertiesService.getAllAppliedComments(clazz1).size());
		assertTrue(propertiesService.getAllAppliedComments(clazz1).contains(comment1));
		assertTrue(propertiesService.getAllAppliedComments(clazz1).contains(comment2));
		assertEquals(2, clazz1.getOwnedComments().size());
	}

	@Test
	public void testGetAllAppliedCommentsNullElt() {
		assertTrue(propertiesService.getAllAppliedComments(null).isEmpty());
	}

	// TODO
	// @Test
	// public void testIsMetaclass_ClassStereotyped() {
	// org.eclipse.uml2.uml.Class clazz1 = create(org.eclipse.uml2.uml.Class.class);
	// Stereotype stereotype = create(Stereotype.class);
	// Profile profile = create(Profile.class);
	// profile.
	// clazz1.applyStereotype(stereotype);
	// assertFalse(propertiesService.isMetaclass(clazz1));
	// }

}
