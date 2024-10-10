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
 *  Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.common.utils.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.properties.common.utils.ContainerUtil;
import org.eclipse.uml2.uml.FunctionBehavior;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

/**
 * Test {@link ContainerUtil} class.
 * 
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 *
 */
public class ContainerUtilTest {


	@SuppressWarnings("unchecked")
	protected <T extends EObject> T create(java.lang.Class<T> type) {
		EClassifier newType = UMLPackage.eINSTANCE.getEClassifier(type.getSimpleName());
		EObject newElement = UMLFactory.eINSTANCE.create((EClass) newType);
		return (T) newElement;
	}

	@Test
	public void testAddInContainerNullContainer() {
		try {
			Property property1 = create(Property.class);
			ContainerUtil.addToContainer(null, UMLPackage.eINSTANCE.getStructuredClassifier_OwnedAttribute(), property1);
			fail("An exception should occurs if container is null."); //$NON-NLS-1$
			// CHECKSTYLE:OFF
		} catch (Exception ex) {
			// CHECKSTYLE:ON
			assertTrue(ex instanceof NullPointerException);
		}
	}

	@Test
	public void testAddInContainerNullFeature() {
		try {
			org.eclipse.uml2.uml.Class clazz = create(org.eclipse.uml2.uml.Class.class);
			Property property1 = create(Property.class);
			ContainerUtil.addToContainer(clazz, null, property1);
			fail("An exception should occurs if feature is null."); //$NON-NLS-1$
			// CHECKSTYLE:OFF
		} catch (Exception ex) {
			// CHECKSTYLE:ON
			assertTrue(ex instanceof NullPointerException);
		}
	}

	@Test
	public void testAddInContainerNullValue() {
		org.eclipse.uml2.uml.Class clazz = create(org.eclipse.uml2.uml.Class.class);
		FunctionBehavior behavior = create(FunctionBehavior.class);
		clazz.setClassifierBehavior(behavior);
		assertNotNull(clazz.getClassifierBehavior());
		ContainerUtil.addToContainer(clazz, UMLPackage.eINSTANCE.getBehavioredClassifier_ClassifierBehavior(), null);
		assertNull(clazz.getClassifierBehavior());
	}

	@Test
	public void testAddInContainerMultiValue() {
		org.eclipse.uml2.uml.Class clazz = create(org.eclipse.uml2.uml.Class.class);
		Property property1 = create(Property.class);
		assertTrue(clazz.getOwnedAttributes().isEmpty());
		ContainerUtil.addToContainer(clazz, UMLPackage.eINSTANCE.getStructuredClassifier_OwnedAttribute(), property1);
		assertEquals(Arrays.asList(property1), clazz.getOwnedAttributes());
	}

	@Test
	public void testAddInContainerMonoValue() {
		org.eclipse.uml2.uml.Class clazz = create(org.eclipse.uml2.uml.Class.class);
		FunctionBehavior behavior = create(FunctionBehavior.class);
		assertNull(clazz.getClassifierBehavior());
		ContainerUtil.addToContainer(clazz, UMLPackage.eINSTANCE.getBehavioredClassifier_ClassifierBehavior(), behavior);
		assertEquals(behavior, clazz.getClassifierBehavior());
	}

	@Test
	public void testGetSubclassesOfNullEClass() {
		try {
			ContainerUtil.getSubclassesOf(null, false);
			fail("An exception should occurs if EClass is null."); //$NON-NLS-1$
			// CHECKSTYLE:OFF
		} catch (Exception ex) {
			// CHECKSTYLE:ON
			assertTrue(ex instanceof NullPointerException);
		}
	}

	@Test
	public void testGetSubclassesOfNoneSubclass() {
		EClass eClass = (EClass) UMLPackage.eINSTANCE.getEClassifier("FunctionBehavior"); //$NON-NLS-1$
		List<EClass> subclassesOf = ContainerUtil.getSubclassesOf(eClass, false);
		assertEquals(Arrays.asList(eClass), subclassesOf);
	}

	@Test
	public void testGetSubclassesOfSubclass() {
		EClass eClassProperty = (EClass) UMLPackage.eINSTANCE.getEClassifier("Property"); //$NON-NLS-1$
		List<EClass> subclassesOf = ContainerUtil.getSubclassesOf(eClassProperty, false);
		// property has 2 subclass (extentionEnd, Port) and its self
		assertEquals(3, subclassesOf.size());
	}
}
