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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.sirius.properties.uml.services.MultipleSelectionViewpointExplorer;
import org.eclipse.papyrus.sirius.properties.uml.services.PropertiesMultipleSelectionServices;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.eclipse.uml2.uml.profile.standard.StandardPackage;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link PropertiesMultipleSelectionServices} service class.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class PropertiesMultipleSelectionServicesTest extends AbstractPropertiesServicesTest {

	/**
	 * The Aggregation feature used in Property UML element.
	 */
	private static final EStructuralFeature AGGREGATION_FEATURE = UMLPackage.eINSTANCE.getProperty_Aggregation();

	/**
	 * The Direction feature used in Parameter UML element.
	 */
	private static final EStructuralFeature DIRECTION_FEATURE = UMLPackage.eINSTANCE.getParameter_Direction();

	/**
	 * The IsAbstract feature used in Classifier UML element.
	 */
	private static final EStructuralFeature IS_ABSTRACT_FEATURE = UMLPackage.eINSTANCE.getClassifier_IsAbstract();

	/**
	 * The Name feature used in NamedElement UML element.
	 */
	private static final EStructuralFeature NAME_FEATURE = UMLPackage.eINSTANCE.getNamedElement_Name();

	/**
	 * The OwneUseCase feature used in Classifier UML element.
	 */
	private static final EStructuralFeature OWNED_USE_CASE_FEATURE = UMLPackage.eINSTANCE.getClassifier_OwnedUseCase();

	/**
	 * The Visibility feature used in NamedElement UML element.
	 */
	private static final EStructuralFeature VISIBILITY_FEATURE = UMLPackage.eINSTANCE.getNamedElement_Visibility();

	/**
	 * The instance of PropertiesServices being tested.
	 */
	private PropertiesMultipleSelectionServices propertiesServices;

	@Before
	public void setUp() {
		this.propertiesServices = new PropertiesMultipleSelectionServices();
	}

	@Test
	public void testFormatName() {
		assertEquals("is an abstract property", propertiesServices.formatName("isAnAbstractProperty")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void testFormatNameWithNullValue() {
		assertEquals(EMPTY, propertiesServices.formatName(null));
	}

	@Test
	public void testFormatNameWithFirstUpperCase() {
		assertEquals("Is an abstract property", propertiesServices.formatName("IsAnAbstractProperty")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void testFormatNameWithEmptyString() {
		assertEquals(EMPTY, propertiesServices.formatName(EMPTY));
	}

	@Test
	public void testIsBoolean() {
		assertTrue(propertiesServices.isBoolean(IS_ABSTRACT_FEATURE));
	}

	@Test
	public void testIsBooleanNotBooleanFeature() {
		assertFalse(propertiesServices.isBoolean(OWNED_USE_CASE_FEATURE));
	}

	@Test
	public void testIsBooleanNullFeature() {
		assertFalse(propertiesServices.isBoolean(null));
	}

	@Test
	public void testIsEnum() {
		assertTrue(propertiesServices.isEnum(VISIBILITY_FEATURE));
		assertTrue(propertiesServices.isEnum(DIRECTION_FEATURE));
		assertTrue(propertiesServices.isEnum(AGGREGATION_FEATURE));
	}

	@Test
	public void testIsEnumNotEnumFeature() {
		assertFalse(propertiesServices.isEnum(OWNED_USE_CASE_FEATURE));
	}

	@Test
	public void testIsEnumNullFeature() {
		assertFalse(propertiesServices.isEnum(null));
	}

	@Test
	public void testGetCheckboxMultipleValueWithFalseValues() {
		List<EObject> objects = new ArrayList<>();
		objects.add(create(Class.class));
		objects.add(create(Component.class));
		objects.add(create(Signal.class));
		objects.add(create(Interface.class));
		assertFalse(propertiesServices.getCheckboxMultipleValue(objects, IS_ABSTRACT_FEATURE).booleanValue());
	}

	@Test
	public void testGetCheckboxMultipleValueWithTrueValues() {
		List<EObject> objects = new ArrayList<>();
		Class clazz = create(Class.class);
		Component component = create(Component.class);
		Signal signal = create(Signal.class);
		Interface inter = create(Interface.class);
		clazz.setIsAbstract(true);
		component.setIsAbstract(true);
		signal.setIsAbstract(true);
		inter.setIsAbstract(true);
		objects.add(clazz);
		objects.add(component);
		objects.add(signal);
		objects.add(inter);
		assertTrue(propertiesServices.getCheckboxMultipleValue(objects, IS_ABSTRACT_FEATURE).booleanValue());
	}

	@Test
	public void testGetCheckboxMultipleValueWithDifferentValues() {
		List<EObject> objects = new ArrayList<>();
		Class clazz = create(Class.class);
		Component component = create(Component.class);
		Signal signal = create(Signal.class);
		Interface inter = create(Interface.class);
		clazz.setIsAbstract(true);
		component.setIsAbstract(true);
		signal.setIsAbstract(false);
		inter.setIsAbstract(false);
		objects.add(clazz);
		objects.add(component);
		objects.add(signal);
		objects.add(inter);
		assertNull(propertiesServices.getCheckboxMultipleValue(objects, IS_ABSTRACT_FEATURE));
	}

	@Test
	public void testGetCheckboxMultipleValueWithNotBooleanFeature() {
		List<EObject> objects = new ArrayList<>();
		objects.add(create(Class.class));
		objects.add(create(Component.class));
		objects.add(create(Signal.class));
		objects.add(create(Interface.class));
		assertNull(propertiesServices.getCheckboxMultipleValue(objects, OWNED_USE_CASE_FEATURE));
	}

	@Test
	public void testGetCheckboxMultipleValueWithNullValues() {
		assertNull(propertiesServices.getCheckboxMultipleValue(null, IS_ABSTRACT_FEATURE));

		List<EObject> objects = new ArrayList<>();
		Class clazz = create(Class.class);
		objects.add(clazz);
		assertNull(propertiesServices.getCheckboxMultipleValue(objects, null));

		assertNull(propertiesServices.getCheckboxMultipleValue(null, null));
	}


	@Test
	public void testGetSelectMultipleValueWithSameValues() {
		List<EObject> objects = new ArrayList<>();
		Class clazz = create(Class.class);
		Component component = create(Component.class);
		Signal signal = create(Signal.class);
		Interface inter = create(Interface.class);
		VisibilityKind protectedLiteral = VisibilityKind.PROTECTED_LITERAL;
		clazz.setVisibility(protectedLiteral);
		component.setVisibility(protectedLiteral);
		signal.setVisibility(protectedLiteral);
		inter.setVisibility(protectedLiteral);
		objects.add(clazz);
		objects.add(component);
		objects.add(signal);
		objects.add(inter);
		EEnumLiteral protectedEEnumLiteral = ((EEnum) VISIBILITY_FEATURE.getEType()).getEEnumLiteralByLiteral(protectedLiteral.getLiteral());
		assertEquals(protectedEEnumLiteral, propertiesServices.getSelectMultipleValue(objects, VISIBILITY_FEATURE));
	}

	@Test
	public void testGetSelectMultipleValueWithDifferentValues() {
		List<EObject> objects = new ArrayList<>();
		Class clazz = create(Class.class);
		Component component = create(Component.class);
		Signal signal = create(Signal.class);
		Interface inter = create(Interface.class);
		VisibilityKind protectedLiteral = VisibilityKind.PROTECTED_LITERAL;
		VisibilityKind privateLiteral = VisibilityKind.PRIVATE_LITERAL;
		clazz.setVisibility(protectedLiteral);
		component.setVisibility(protectedLiteral);
		signal.setVisibility(privateLiteral);
		inter.setVisibility(privateLiteral);
		objects.add(clazz);
		objects.add(component);
		objects.add(signal);
		objects.add(inter);
		assertNull(propertiesServices.getSelectMultipleValue(objects, VISIBILITY_FEATURE));
	}

	@Test
	public void testGetSelectMultipleValueWithNotEnumFeature() {
		List<EObject> objects = new ArrayList<>();
		objects.add(create(Class.class));
		objects.add(create(Component.class));
		objects.add(create(Signal.class));
		objects.add(create(Interface.class));
		assertNull(propertiesServices.getSelectMultipleValue(objects, OWNED_USE_CASE_FEATURE));
	}

	@Test
	public void testGetSelectMultipleValueWithNullValues() {
		assertNull(propertiesServices.getSelectMultipleValue(null, VISIBILITY_FEATURE));

		List<EObject> objects = new ArrayList<>();
		Class clazz = create(Class.class);
		objects.add(clazz);
		assertNull(propertiesServices.getSelectMultipleValue(objects, null));

		assertNull(propertiesServices.getSelectMultipleValue(null, null));
	}

	@Test
	public void testSetAll() {
		List<EObject> objects = new ArrayList<>();
		Class clazz = create(Class.class);
		Component component = create(Component.class);
		Signal signal = create(Signal.class);
		Interface inter = create(Interface.class);
		objects.add(clazz);
		objects.add(component);
		objects.add(signal);
		objects.add(inter);
		VisibilityKind privateLiteral = VisibilityKind.PRIVATE_LITERAL;
		assertTrue(propertiesServices.setAll(objects, VISIBILITY_FEATURE, privateLiteral));
		assertEquals(privateLiteral, clazz.getVisibility());
		assertEquals(privateLiteral, component.getVisibility());
		assertEquals(privateLiteral, signal.getVisibility());
		assertEquals(privateLiteral, inter.getVisibility());
	}

	@Test
	public void testSetAllWithDifferentValuesBefore() {
		List<EObject> objects = new ArrayList<>();
		Class clazz = create(Class.class);
		Component component = create(Component.class);
		Signal signal = create(Signal.class);
		Interface inter = create(Interface.class);
		VisibilityKind protectedLiteral = VisibilityKind.PROTECTED_LITERAL;
		VisibilityKind packageLiteral = VisibilityKind.PACKAGE_LITERAL;
		clazz.setVisibility(protectedLiteral);
		component.setVisibility(protectedLiteral);
		signal.setVisibility(packageLiteral);
		inter.setVisibility(packageLiteral);
		objects.add(clazz);
		objects.add(component);
		objects.add(signal);
		objects.add(inter);
		VisibilityKind privateLiteral = VisibilityKind.PRIVATE_LITERAL;
		assertTrue(propertiesServices.setAll(objects, VISIBILITY_FEATURE, privateLiteral));
		assertEquals(privateLiteral, clazz.getVisibility());
		assertEquals(privateLiteral, component.getVisibility());
		assertEquals(privateLiteral, signal.getVisibility());
		assertEquals(privateLiteral, inter.getVisibility());
	}

	@Test
	public void testSelAllValuesToNull() {
		List<EObject> objects = new ArrayList<>();
		Class clazz = create(Class.class);
		Component component = create(Component.class);
		Signal signal = create(Signal.class);
		Interface inter = create(Interface.class);
		VisibilityKind protectedLiteral = VisibilityKind.PROTECTED_LITERAL;
		clazz.setVisibility(protectedLiteral);
		component.setVisibility(protectedLiteral);
		signal.setVisibility(protectedLiteral);
		inter.setVisibility(protectedLiteral);
		objects.add(clazz);
		objects.add(component);
		objects.add(signal);
		objects.add(inter);
		VisibilityKind defaultVisibility = VisibilityKind.PUBLIC_LITERAL;
		assertTrue(propertiesServices.setAll(objects, VISIBILITY_FEATURE, null));
		assertEquals(defaultVisibility, clazz.getVisibility());
		assertEquals(defaultVisibility, component.getVisibility());
		assertEquals(defaultVisibility, signal.getVisibility());
		assertEquals(defaultVisibility, inter.getVisibility());
	}

	@Test
	public void testSetAllWithNullValues() {
		assertFalse(propertiesServices.setAll(null, VISIBILITY_FEATURE, null));

		List<EObject> objects = new ArrayList<>();
		Class clazz = create(Class.class);
		objects.add(clazz);
		assertFalse(propertiesServices.setAll(objects, null, null));

		assertFalse(propertiesServices.setAll(null, null, null));
	}

	@Test
	public void testGetCommonFeatures() {
		List<EObject> objects = new ArrayList<>();
		Class clazz = create(Class.class);
		Component component = create(Component.class);
		Signal signal = create(Signal.class);
		Interface inter = create(Interface.class);
		objects.add(clazz);
		objects.add(component);
		objects.add(signal);
		objects.add(inter);
		List<EStructuralFeature> commonFeatures = propertiesServices.getCommonFeatures(objects);
		assertEquals(3, commonFeatures.size());
		assertTrue(commonFeatures.contains(VISIBILITY_FEATURE));
		assertTrue(commonFeatures.contains(IS_ABSTRACT_FEATURE));
		assertTrue(commonFeatures.contains(NAME_FEATURE));
	}

	@Test
	public void testKeepDisplayableFeaturesExceptions() {
		List<EStructuralFeature> features = new ArrayList<>();
		Set<EClass> classes = new HashSet<>();
		features.add(StandardPackage.eINSTANCE.getMetaclass_Base_Class());
		classes.add(StandardPackage.eINSTANCE.getMetaclass());

		MultipleSelectionViewpointExplorer instance = MultipleSelectionViewpointExplorer.getInstance();
		List<EStructuralFeature> displayableFeatures = instance.keepDisplayableFeatures(classes, features);
		assertTrue(displayableFeatures.isEmpty());

		features.clear();
		classes.clear();
		features.add(UMLPackage.eINSTANCE.getAssociation_MemberEnd());
		classes.add(UMLPackage.eINSTANCE.getAssociation());
		displayableFeatures = instance.keepDisplayableFeatures(classes, features);
		assertTrue(displayableFeatures.isEmpty());
	}

	@Test
	public void testKeepDisplayableFeaturesWithNullValues() {
		Set<EClass> classes = new HashSet<>();
		classes.add(StandardPackage.eINSTANCE.getMetaclass());

		MultipleSelectionViewpointExplorer instance = MultipleSelectionViewpointExplorer.getInstance();
		List<EStructuralFeature> displayableFeatures = instance.keepDisplayableFeatures(classes, null);
		assertTrue(displayableFeatures.isEmpty());

		List<EStructuralFeature> features = new ArrayList<>();
		EReference baseClassFeature = StandardPackage.eINSTANCE.getMetaclass_Base_Class();
		features.add(baseClassFeature);
		displayableFeatures = instance.keepDisplayableFeatures(null, features);
		assertFalse(displayableFeatures.isEmpty());
		assertEquals(baseClassFeature, displayableFeatures.get(0));
	}

}
