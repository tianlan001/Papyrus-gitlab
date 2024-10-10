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

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.papyrus.sirius.properties.uml.services.PropertiesMemberEndServices;
import org.eclipse.uml2.uml.Artifact;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.UseCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link PropertiesMemberEndServices} service class.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class PropertiesMemberEndServicesTest extends AbstractPropertiesServicesTest {

	/**
	 * Association Owner.
	 */
	private static final String ASSOCIATION = "Association"; //$NON-NLS-1$

	/**
	 * Classifier Owner.
	 */
	private static final String CLASSIFIER = "Classifier"; //$NON-NLS-1$

	/**
	 * Owner.
	 */
	private static final String OWNER = "owner"; //$NON-NLS-1$

	/**
	 * The instance of PropertiesServices being tested.
	 */
	private PropertiesMemberEndServices propertiesService;

	@Before
	public void setUp() {
		this.propertiesService = new PropertiesMemberEndServices();
	}

	@Test
	public void testSetNavigableMemberEndSameNavigable() {
		Property property = create(Property.class);
		try {
			propertiesService.setNavigable(property, false);
			fail("An OperationCanceledException should occurs if a property is setNavigable with the same status."); //$NON-NLS-1$
			// CHECKSTYLE:OFF
		} catch (Exception ex) {
			// CHECKSTYLE:ON
			assertTrue(ex instanceof OperationCanceledException);
		}
	}

	@Test
	public void testSetNavigableMemberEndNavigableTrue() {
		Property property1 = create(Property.class);
		Property property2 = create(Property.class);
		Association association = create(Association.class);
		property1.setAssociation(association);
		association.getNavigableOwnedEnds().add(property2);
		assertFalse(property1.isNavigable());
		propertiesService.setNavigable(property1, true);
		assertTrue(property1.isNavigable());
	}

	@Test
	public void testSetNavigableMemberEndNavigableFalse() {
		Property property1 = create(Property.class);
		Association association = create(Association.class);
		property1.setAssociation(association);
		association.getNavigableOwnedEnds().add(property1);
		assertTrue(property1.isNavigable());
		propertiesService.setNavigable(property1, false);
		assertFalse(property1.isNavigable());
	}

	@Test
	public void testGetOwnerASSOCIATION() {
		Property property1 = create(Property.class);
		Association association = create(Association.class);
		property1.setAssociation(association);
		association.getOwnedEnds().add(property1);
		assertEquals(ASSOCIATION, propertiesService.getOwner(property1));
	}

	@Test
	public void testGetOwnerCLASSIFIER() {
		Property property1 = create(Property.class);
		Property property2 = create(Property.class);
		Association association = create(Association.class);
		property1.setAssociation(association);
		association.getOwnedEnds().add(property2);
		assertEquals(CLASSIFIER, propertiesService.getOwner(property1));
	}

	@Test
	public void testGetOwnerEnumeration() {
		assertEquals(Arrays.asList(ASSOCIATION, CLASSIFIER), propertiesService.getOwnerEnumerations(null));
	}

	@Test
	public void testSetOwnerAssociationWithMultipleMembersEnds() {
		Property property1 = create(Property.class);
		Association association = create(Association.class);
		property1.setAssociation(association);
		association.getMemberEnds().add(property1);
		Property property2 = create(Property.class);
		association.getMemberEnds().add(property2);
		Property property3 = create(Property.class);
		association.getMemberEnds().add(property3);
		try {
			propertiesService.setOwner(property1, ASSOCIATION);
			fail("An OperationCanceledException should occurs if an association contains more than 2 member end."); //$NON-NLS-1$
			// CHECKSTYLE:OFF
		} catch (Exception ex) {
			// CHECKSTYLE:ON
			assertTrue(ex instanceof OperationCanceledException);
		}
	}

	@Test
	public void testSetOwnerIsOwnedByAssociation() {
		Property property1 = create(Property.class);
		Association association = create(Association.class);
		property1.setAssociation(association);
		association.getMemberEnds().add(property1);
		Property property2 = create(Property.class);
		association.getMemberEnds().add(property2);
		assertTrue(association.getOwnedEnds().isEmpty());
		propertiesService.setOwner(property1, ASSOCIATION);
		assertTrue(association.getOwnedEnds().contains(property1));
	}

	@Test
	public void testSetOwnerIsOwnedByClassifier1OwnerType() {
		Property property1 = create(Property.class);
		Association association = create(Association.class);
		org.eclipse.uml2.uml.Class clazz = create(org.eclipse.uml2.uml.Class.class);
		property1.setType(clazz);
		property1.setAssociation(association);
		association.getMemberEnds().add(property1);
		Property property2 = create(Property.class);
		association.getMemberEnds().add(property2);
		assertTrue(association.getOwnedEnds().isEmpty());
		propertiesService.setOwner(property1, CLASSIFIER);
		assertTrue(clazz.getOwnedAttributes().contains(property1));
	}

	@Test
	public void testSetOwnerIsOwnedByClassifier2OwnerType() {
		Property property1 = create(Property.class);
		Association association = create(Association.class);
		org.eclipse.uml2.uml.Class clazz1 = create(org.eclipse.uml2.uml.Class.class);
		property1.setType(clazz1);
		property1.setAssociation(association);
		association.getMemberEnds().add(property1);
		org.eclipse.uml2.uml.Class clazz2 = create(org.eclipse.uml2.uml.Class.class);
		Property property2 = create(Property.class);
		property2.setType(clazz2);
		association.getMemberEnds().add(property2);
		assertTrue(association.getOwnedEnds().isEmpty());
		propertiesService.setOwner(property1, CLASSIFIER);
		assertTrue(clazz2.getOwnedAttributes().contains(property1));
	}


	@Test
	public void testGetOwnedAttributeFeatureForTypeNullType() {
		assertNull(propertiesService.getOwnedAttributeFeatureForType(null));
	}

	@Test
	public void testGetOwnedAttributeFeatureForTypeStructuredClassifierType() {
		org.eclipse.uml2.uml.Class clazz = create(org.eclipse.uml2.uml.Class.class);
		assertEquals(UMLPackage.eINSTANCE.getStructuredClassifier_OwnedAttribute(), propertiesService.getOwnedAttributeFeatureForType(clazz));
	}

	@Test
	public void testGetOwnedAttributeFeatureForTypeDataTypeType() {
		DataType dataType = create(DataType.class);
		assertEquals(UMLPackage.eINSTANCE.getDataType_OwnedAttribute(), propertiesService.getOwnedAttributeFeatureForType(dataType));
	}

	@Test
	public void testGetOwnedAttributeFeatureForTypeInterfaceType() {
		Interface interfaze = create(Interface.class);
		assertEquals(UMLPackage.eINSTANCE.getInterface_OwnedAttribute(), propertiesService.getOwnedAttributeFeatureForType(interfaze));
	}

	@Test
	public void testGetOwnedAttributeFeatureForTypeArtifactType() {
		Artifact artifact = create(Artifact.class);
		assertEquals(UMLPackage.eINSTANCE.getArtifact_OwnedAttribute(), propertiesService.getOwnedAttributeFeatureForType(artifact));
	}

	@Test
	public void testGetOwnedAttributeFeatureForTypeSignalType() {
		Signal signal = create(Signal.class);
		assertEquals(UMLPackage.eINSTANCE.getSignal_OwnedAttribute(), propertiesService.getOwnedAttributeFeatureForType(signal));
	}

	@Test
	public void testGetOwnedAttributeFeatureForTypeUseCaseType() {
		UseCase useCase = create(UseCase.class);
		assertNull(propertiesService.getOwnedAttributeFeatureForType(useCase));
	}

	/**
	 * Test if a MemberEnd property is Editable for a property typed by a class and with association with 2 members ends typed as Class.
	 */
	@Test
	public void testIsMemberEndPropertyEditableOwnerClass() {
		Property property1 = create(Property.class);
		Association association = create(Association.class);
		org.eclipse.uml2.uml.Class clazz1 = create(org.eclipse.uml2.uml.Class.class);
		property1.setType(clazz1);
		property1.setAssociation(association);
		association.getMemberEnds().add(property1);
		org.eclipse.uml2.uml.Class clazz2 = create(org.eclipse.uml2.uml.Class.class);
		Property property2 = create(Property.class);
		property2.setType(clazz2);
		association.getMemberEnds().add(property2);
		assertTrue(propertiesService.isMemberEndPropertyEditable(property1, OWNER));
	}

	/**
	 * Test if a MemberEnd property is Editable for a property typed by a class and with association with 2 members ends typed as Association.
	 */
	@Test
	public void testIsMemberEndPropertyEditableOwnerAssociation() {
		Property property1 = create(Property.class);
		Association association = create(Association.class);
		Association association1 = create(Association.class);
		property1.setType(association1);
		property1.setAssociation(association);
		association.getMemberEnds().add(property1);
		Property property2 = create(Property.class);
		Association association2 = create(Association.class);
		property2.setType(association2);
		association.getMemberEnds().add(property2);
		assertFalse(propertiesService.isMemberEndPropertyEditable(property1, OWNER));
	}

	/**
	 * Test if a MemberEnd property is Editable for a property typed by a class and with association with only one members end.
	 */
	@Test
	public void testIsMemberEndPropertyEditableWithOneMemberEnd() {
		Property property1 = create(Property.class);
		Class clazz1 = create(Class.class);
		Association association = create(Association.class);
		property1.setType(clazz1);
		property1.setAssociation(association);
		association.getMemberEnds().add(property1);
		assertTrue(propertiesService.isMemberEndPropertyEditable(property1, OWNER));
	}

	@Test
	public void testIsMemberEndPropertyNonEditableWithUseCaseTarget() {
		Property property1 = create(Property.class);
		Property property2 = create(Property.class);
		UseCase useCase = create(UseCase.class);
		Class clazz = create(Class.class);
		Association association = create(Association.class);
		property1.setType(useCase);
		property2.setType(clazz);
		property1.setAssociation(association);
		property2.setAssociation(association);
		association.getMemberEnds().add(property1);
		association.getMemberEnds().add(property2);
		assertFalse(propertiesService.isMemberEndPropertyEditable(property2, OWNER));
	}
}
