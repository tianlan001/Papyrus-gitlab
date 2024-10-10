/*******************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo
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

import java.util.List;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.papyrus.sirius.properties.uml.services.PropertiesProfileDefinitionServices;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link PropertiesProfileDefinitionServices} service class.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class PropertiesProfileDefinitionServicesTest extends AbstractPropertiesServicesTest {

	/**
	 * The empty version returned if no version is specified.
	 */
	private static final String EMPTY_VERSION = "0.0.0"; //$NON-NLS-1$

	/**
	 * The instance of PropertiesServices being tested.
	 */
	private PropertiesProfileDefinitionServices propertiesService;

	@Before
	public void setUp() {
		this.propertiesService = new PropertiesProfileDefinitionServices();
	}

	@Test
	public void testGetDefinitionsWithAnnotations() {
		Profile profile = create(Profile.class);
		EAnnotation annotation = EcoreFactory.eINSTANCE.createEAnnotation();
		annotation.setSource(UMLUtil.UML2_UML_PACKAGE_2_0_NS_URI);
		profile.getEAnnotations().add(annotation);
		EPackage ePackage1 = EcoreFactory.eINSTANCE.createEPackage();
		EPackage ePackage2 = EcoreFactory.eINSTANCE.createEPackage();
		annotation.getContents().add(ePackage1);
		annotation.getContents().add(ePackage2);

		List<EPackage> definitions = propertiesService.getDefinitions(profile);
		assertEquals(2, definitions.size());
	}

	@Test
	public void testGetDefinitionsWithNoAnnotation() {
		Profile profile = create(Profile.class);
		List<EPackage> definitions = propertiesService.getDefinitions(profile);
		assertTrue(definitions.isEmpty());
	}

	@Test
	public void testGetDefinitionsNullElt() {
		List<EPackage> definitions = propertiesService.getDefinitions(null);
		assertTrue(definitions.isEmpty());
	}

	@Test
	public void testGetDefinitionsWithWrongAnnotations() {
		Profile profile = create(Profile.class);
		EAnnotation annotation = EcoreFactory.eINSTANCE.createEAnnotation();
		annotation.setSource(UMLUtil.UML2_UML_PACKAGE_2_0_NS_URI);
		profile.getEAnnotations().add(annotation);
		EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
		EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		Property umlProperty = create(Property.class);
		annotation.getContents().add(ePackage);
		annotation.getContents().add(eClass);
		annotation.getContents().add(umlProperty);

		List<EPackage> definitions = propertiesService.getDefinitions(profile);
		assertEquals(1, definitions.size());
	}

	@Test
	public void testGetProfileDefinitionVersion() {
		EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
		EAnnotation annotation = EcoreFactory.eINSTANCE.createEAnnotation();
		annotation.setSource(PropertiesProfileDefinitionServices.PAPYRUS_VERSION);
		annotation.getDetails().put(PropertiesProfileDefinitionServices.PAPYRUS_VERSION_KEY, "1.2.3"); //$NON-NLS-1$
		ePackage.getEAnnotations().add(annotation);

		assertEquals("1.2.3", propertiesService.getProfileDefinitionVersion(ePackage)); //$NON-NLS-1$
	}

	@Test
	public void testGetProfileDefinitionVersionNullElt() {
		assertEquals(EMPTY_VERSION, propertiesService.getProfileDefinitionVersion(null));
	}

	@Test
	public void testGetProfileDefinitionVersionNullAnnotation() {
		EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();

		assertEquals(EMPTY_VERSION, propertiesService.getProfileDefinitionVersion(ePackage));
	}

	@Test
	public void testGetProfileDefinitionVersionWrongVersionFormat() {
		EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
		EAnnotation annotation = EcoreFactory.eINSTANCE.createEAnnotation();
		annotation.setSource(PropertiesProfileDefinitionServices.PAPYRUS_VERSION);
		annotation.getDetails().put(PropertiesProfileDefinitionServices.PAPYRUS_VERSION_KEY, "test"); //$NON-NLS-1$
		ePackage.getEAnnotations().add(annotation);

		assertEquals(EMPTY_VERSION, propertiesService.getProfileDefinitionVersion(ePackage));
	}

	@Test
	public void testGetProfileDefinitionDate() {
		EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
		EAnnotation annotation = EcoreFactory.eINSTANCE.createEAnnotation();
		annotation.setSource(PropertiesProfileDefinitionServices.PAPYRUS_VERSION);
		annotation.getDetails().put(PropertiesProfileDefinitionServices.PAPYRUS_DATE_KEY, "2023-02-15"); //$NON-NLS-1$
		ePackage.getEAnnotations().add(annotation);

		assertEquals("2023-02-15", propertiesService.getProfileDefinitionDate(ePackage)); //$NON-NLS-1$
	}

	@Test
	public void testGetProfileDefinitionDateNullElt() {
		assertEquals(PropertiesProfileDefinitionServices.EMPTY_STRING, propertiesService.getProfileDefinitionDate(null));
	}

	@Test
	public void testGetProfileDefinitionDateNullAnnotation() {
		EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();

		assertEquals(PropertiesProfileDefinitionServices.EMPTY_STRING, propertiesService.getProfileDefinitionDate(ePackage));
	}

	@Test
	public void testGetProfileDefinitionAuthor() {
		EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
		EAnnotation annotation = EcoreFactory.eINSTANCE.createEAnnotation();
		annotation.setSource(PropertiesProfileDefinitionServices.PAPYRUS_VERSION);
		annotation.getDetails().put(PropertiesProfileDefinitionServices.PAPYRUS_AUTHOR_KEY, "OBEO"); //$NON-NLS-1$
		ePackage.getEAnnotations().add(annotation);

		assertEquals("OBEO", propertiesService.getProfileDefinitionAuthor(ePackage)); //$NON-NLS-1$
	}

	@Test
	public void testGetProfileDefinitionAuthorNullElt() {
		assertEquals(PropertiesProfileDefinitionServices.EMPTY_STRING, propertiesService.getProfileDefinitionAuthor(null));
	}

	@Test
	public void testGetProfileDefinitionAuthorNullAnnotation() {
		EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();

		assertEquals(PropertiesProfileDefinitionServices.EMPTY_STRING, propertiesService.getProfileDefinitionAuthor(ePackage));
	}

	@Test
	public void testGetProfileDefinitionCopyright() {
		EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
		EAnnotation annotation = EcoreFactory.eINSTANCE.createEAnnotation();
		annotation.setSource(PropertiesProfileDefinitionServices.PAPYRUS_VERSION);
		annotation.getDetails().put(PropertiesProfileDefinitionServices.PAPYRUS_COPYRIGHT_KEY, "Copyright example"); //$NON-NLS-1$
		ePackage.getEAnnotations().add(annotation);

		assertEquals("Copyright example", propertiesService.getProfileDefinitionCopyright(ePackage)); //$NON-NLS-1$
	}

	@Test
	public void testGetProfileDefinitionCopyrightNullElt() {
		assertEquals(PropertiesProfileDefinitionServices.EMPTY_STRING, propertiesService.getProfileDefinitionCopyright(null));
	}

	@Test
	public void testGetProfileDefinitionCopyrightNullAnnotation() {
		EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();

		assertEquals(PropertiesProfileDefinitionServices.EMPTY_STRING, propertiesService.getProfileDefinitionCopyright(ePackage));
	}

	@Test
	public void testGetProfileDefinitionComment() {
		EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
		EAnnotation annotation = EcoreFactory.eINSTANCE.createEAnnotation();
		annotation.setSource(PropertiesProfileDefinitionServices.PAPYRUS_VERSION);
		annotation.getDetails().put(PropertiesProfileDefinitionServices.PAPYRUS_COMMENT_KEY, "Comment example"); //$NON-NLS-1$
		ePackage.getEAnnotations().add(annotation);

		assertEquals("Comment example", propertiesService.getProfileDefinitionComment(ePackage)); //$NON-NLS-1$
	}

	@Test
	public void testGetProfileDefinitionCommentNullElt() {
		assertEquals(PropertiesProfileDefinitionServices.EMPTY_STRING, propertiesService.getProfileDefinitionComment(null));
	}

	@Test
	public void testGetProfileDefinitionCommentNullAnnotation() {
		EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();

		assertEquals(PropertiesProfileDefinitionServices.EMPTY_STRING, propertiesService.getProfileDefinitionComment(ePackage));
	}

	@Test
	public void testRemoveProfileDefinition() {
		Profile profile = create(Profile.class);
		EAnnotation annotation = EcoreFactory.eINSTANCE.createEAnnotation();
		annotation.setSource(UMLUtil.UML2_UML_PACKAGE_2_0_NS_URI);
		profile.getEAnnotations().add(annotation);
		EPackage ePackage1 = EcoreFactory.eINSTANCE.createEPackage();
		EPackage ePackage2 = EcoreFactory.eINSTANCE.createEPackage();
		annotation.getContents().add(ePackage1);
		annotation.getContents().add(ePackage2);

		assertTrue(propertiesService.removeProfileDefinition(ePackage1, profile));
		assertEquals(1, annotation.getContents().size());
		assertFalse(annotation.getContents().contains(ePackage1));
		assertTrue(annotation.getContents().contains(ePackage2));

		assertTrue(propertiesService.removeProfileDefinition(ePackage2, profile));
		assertEquals(0, annotation.getContents().size());
		assertFalse(annotation.getContents().contains(ePackage1));
		assertFalse(annotation.getContents().contains(ePackage2));
	}

	@Test
	public void testRemoveProfileDefinitionNonExistingObject() {
		Profile profile = create(Profile.class);
		EAnnotation annotation = EcoreFactory.eINSTANCE.createEAnnotation();
		annotation.setSource(UMLUtil.UML2_UML_PACKAGE_2_0_NS_URI);
		profile.getEAnnotations().add(annotation);
		EPackage ePackage1 = EcoreFactory.eINSTANCE.createEPackage();

		assertFalse(propertiesService.removeProfileDefinition(ePackage1, profile));
		assertEquals(0, annotation.getContents().size());
	}

	@Test
	public void testRemoveProfileDefinitionNullParameters() {
		Profile profile = create(Profile.class);
		EAnnotation annotation = EcoreFactory.eINSTANCE.createEAnnotation();
		annotation.setSource(UMLUtil.UML2_UML_PACKAGE_2_0_NS_URI);
		profile.getEAnnotations().add(annotation);
		EPackage ePackage1 = EcoreFactory.eINSTANCE.createEPackage();
		annotation.getContents().add(ePackage1);

		assertFalse(propertiesService.removeProfileDefinition(null, profile));
		assertEquals(1, annotation.getContents().size());
		assertTrue(annotation.getContents().contains(ePackage1));

		assertFalse(propertiesService.removeProfileDefinition(ePackage1, null));
		assertEquals(1, annotation.getContents().size());
		assertTrue(annotation.getContents().contains(ePackage1));

		assertFalse(propertiesService.removeProfileDefinition(null, null));
		assertEquals(1, annotation.getContents().size());
		assertTrue(annotation.getContents().contains(ePackage1));
	}

	@Test
	public void testRemoveProfileDefinitionWithNoAnnotation() {
		Profile profile = create(Profile.class);
		EAnnotation annotation = EcoreFactory.eINSTANCE.createEAnnotation();
		annotation.setSource(UMLUtil.UML2_UML_PACKAGE_2_0_NS_URI);
		EPackage ePackage1 = EcoreFactory.eINSTANCE.createEPackage();
		annotation.getContents().add(ePackage1);

		assertFalse(propertiesService.removeProfileDefinition(ePackage1, profile));
		assertEquals(1, annotation.getContents().size());
		assertTrue(annotation.getContents().contains(ePackage1));
		assertTrue(profile.getEAnnotations().isEmpty());
	}
}
