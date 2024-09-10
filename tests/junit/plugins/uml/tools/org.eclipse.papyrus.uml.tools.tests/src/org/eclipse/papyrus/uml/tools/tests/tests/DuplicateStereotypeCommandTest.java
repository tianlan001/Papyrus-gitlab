/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Pauline DEVILLE (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.tools.tests.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.tools.commands.DuplicateStereotypeCommand;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

@PluginResource("/resources/duplicateStereotypeCommandTest/model.di")
/**
 * This test is used to test the copy of stereotype in especially for stereotype having composite association
 * WARNING this test tests only the DuplicateStereotypeCommand (do not duplicate the element but only the stereotype application)
 */
public class DuplicateStereotypeCommandTest extends AbstractPapyrusTest {

	@Rule
	public final PapyrusEditorFixture editor = new PapyrusEditorFixture();

	private Class stereotypedClass;
	private Class simpleClass;

	private static final String STEREOTYPE1_NAME = "Stereotype1"; //$NON-NLS-1$
	private static final String STEREOTYPE_SINGLE_MULTIPLICITY_PROPERTY_NAME = "singleMultiplicityProp"; //$NON-NLS-1$

	private static final String STEREOTYPE2_NAME = "Stereotype2"; //$NON-NLS-1$
	private static final String STEREOTYPE_MULTIVALUED_PROPERTY_NAME = "multivaluedProp"; //$NON-NLS-1$

	private static final String STEREOTYPE3_NAME = "Stereotype3"; //$NON-NLS-1$
	private static final String STEREOTYPE_MULTIVALUED_DATATYPE_PROPERTY_NAME = "MultivaluedDataTypeProp"; //$NON-NLS-1$
	private static final String DATATYPE_PROPERTY_1_NAME = "dt_p1"; //$NON-NLS-1$
	private static final String DATATYPE_PROPERTY_2_NAME = "dt_p2"; //$NON-NLS-1$

	private static final String SIMPLE_CLASS_NAME = "SimpleClass"; //$NON-NLS-1$
	private static final String STEREOTYPED_CLASS_NAME = "StereotypedClass"; //$NON-NLS-1$

	/**
	 * Constructor.
	 *
	 */
	public DuplicateStereotypeCommandTest() {
		super();
	}

	@Before
	public void init() {
		Package model = editor.getModel();
		Type type1 = model.getOwnedType(STEREOTYPED_CLASS_NAME);
		Type type2 = model.getOwnedType(SIMPLE_CLASS_NAME);
		assertTrue("type1 should be a Class", type1 instanceof Class); //$NON-NLS-1$
		assertTrue("type2 should be a Class", type2 instanceof Class); //$NON-NLS-1$
		stereotypedClass = (Class) type1;
		simpleClass = (Class) type2;
	}

	@Test
	public void duplicateStereotypeWithSingleContainmentFeatureTest() {
		TransactionalEditingDomain domain = editor.getEditingDomain();

		EObject originalStereotypeAplications = getStereotypeApplication(stereotypedClass, STEREOTYPE1_NAME);

		EStructuralFeature propertyStructuralFeature = getStructuralFeature(originalStereotypeAplications, STEREOTYPE_SINGLE_MULTIPLICITY_PROPERTY_NAME);
		assertNotNull("the value of singleMultiplicityProp should not be null", originalStereotypeAplications.eGet(propertyStructuralFeature)); //$NON-NLS-1$

		DuplicateStereotypeCommand command = new DuplicateStereotypeCommand(domain, simpleClass, originalStereotypeAplications);
		domain.getCommandStack().execute(command);

		EObject copiedStereotypeAplications = getStereotypeApplication(simpleClass, STEREOTYPE1_NAME);
		assertNotNull("the value of singleMultiplicityProp should not be null in the copied stereotype application", copiedStereotypeAplications.eGet(propertyStructuralFeature)); //$NON-NLS-1$
		assertNotNull("the value of singleMultiplicityProp should not be null in the original stereotype application", originalStereotypeAplications.eGet(propertyStructuralFeature)); //$NON-NLS-1$
	}

	@Test
	public void duplicateStereotypeWithMultivaluedContainmentFeatureTest() {
		TransactionalEditingDomain domain = editor.getEditingDomain();

		EObject originalStereotypeAplications = getStereotypeApplication(stereotypedClass, STEREOTYPE2_NAME);
		EStructuralFeature propertyStructuralFeature = getStructuralFeature(originalStereotypeAplications, STEREOTYPE_MULTIVALUED_PROPERTY_NAME);
		assertTrue("the value of multivaluedProp should not be null and should be of type List", originalStereotypeAplications.eGet(propertyStructuralFeature) instanceof List<?>); //$NON-NLS-1$

		DuplicateStereotypeCommand command = new DuplicateStereotypeCommand(domain, simpleClass, originalStereotypeAplications);
		domain.getCommandStack().execute(command);

		EObject copiedStereotypeAplications = getStereotypeApplication(simpleClass, STEREOTYPE2_NAME);
		assertTrue("the value of multivaluedProp should not be null and should be of type List in the copied stereotype application", copiedStereotypeAplications.eGet(propertyStructuralFeature) instanceof List<?>); //$NON-NLS-1$
		assertEquals("the value of multivaluedProp should not be empty in the copied stereotype application", 3, ((List<?>) copiedStereotypeAplications.eGet(propertyStructuralFeature)).size()); //$NON-NLS-1$
		assertTrue("the value of multivaluedProp should not be null and should be of type List in the original stereotype application", originalStereotypeAplications.eGet(propertyStructuralFeature) instanceof List<?>); //$NON-NLS-1$
		assertEquals("the value of multivaluedProp should not be empty in the original stereotype application", 3, ((List<?>) originalStereotypeAplications.eGet(propertyStructuralFeature)).size()); //$NON-NLS-1$
	}

	@Test
	@SuppressWarnings("unchecked")
	public void duplicateStereotypeWithMultivaluedDataTypeContainmentFeatureTest() {
		TransactionalEditingDomain domain = editor.getEditingDomain();

		EObject originalStereotypeAplications = getStereotypeApplication(stereotypedClass, STEREOTYPE3_NAME);
		EStructuralFeature propertyStructuralFeature = getStructuralFeature(originalStereotypeAplications, STEREOTYPE_MULTIVALUED_DATATYPE_PROPERTY_NAME);
		assertTrue("the value of MultivaluedDataTypeProp should not be null and should be of type List", originalStereotypeAplications.eGet(propertyStructuralFeature) instanceof List<?>); //$NON-NLS-1$

		DuplicateStereotypeCommand command = new DuplicateStereotypeCommand(domain, simpleClass, originalStereotypeAplications);
		domain.getCommandStack().execute(command);

		EObject copiedStereotypeAplications = getStereotypeApplication(simpleClass, STEREOTYPE3_NAME);
		assertTrue("the value of MultivaluedDataTypeProp should not be null and should be of type List in the copied stereotype application", copiedStereotypeAplications.eGet(propertyStructuralFeature) instanceof List<?>); //$NON-NLS-1$
		List<EObject> copiedStereotypeApplicationP3Value = (List<EObject>) copiedStereotypeAplications.eGet(propertyStructuralFeature);
		assertEquals("the value of MultivaluedDataTypeProp should not be empty in the copied stereotype application", 2, copiedStereotypeApplicationP3Value.size()); //$NON-NLS-1$
		assertTrue("the value of MultivaluedDataTypeProp should not be null and should be of type List in the original stereotype application", originalStereotypeAplications.eGet(propertyStructuralFeature) instanceof List<?>); //$NON-NLS-1$
		List<EObject> originalStereotypeApplicationP3Value = (List<EObject>) originalStereotypeAplications.eGet(propertyStructuralFeature);
		assertEquals("the value of MultivaluedDataTypeProp should not be empty in the original stereotype application", 2, originalStereotypeApplicationP3Value.size()); //$NON-NLS-1$

		// Test DataType copy
		EStructuralFeature dataTypeProperty1 = getStructuralFeature(copiedStereotypeApplicationP3Value.get(0), DATATYPE_PROPERTY_1_NAME);
		EStructuralFeature dataTypeProperty2 = getStructuralFeature(copiedStereotypeApplicationP3Value.get(0), DATATYPE_PROPERTY_2_NAME);
		assertTrue("the copied data type should have a non null dt_p1 value of type Class", copiedStereotypeApplicationP3Value.get(0).eGet(dataTypeProperty1) instanceof Class); //$NON-NLS-1$
		assertTrue("the copied data type should have a non null dt_p2 value of type Property", copiedStereotypeApplicationP3Value.get(0).eGet(dataTypeProperty2) instanceof Property); //$NON-NLS-1$

		assertTrue("the copied data type should have a non null dt_p1 value of type Class", originalStereotypeApplicationP3Value.get(0).eGet(dataTypeProperty1) instanceof Class); //$NON-NLS-1$
		assertTrue("the copied data type should have a non null dt_p2 value of type Property", originalStereotypeApplicationP3Value.get(0).eGet(dataTypeProperty2) instanceof Property); //$NON-NLS-1$

		assertTrue("since dt_p1 is a reference then the original value and the copied one should be the same", originalStereotypeApplicationP3Value.get(0).eGet(dataTypeProperty1).equals(copiedStereotypeApplicationP3Value.get(0).eGet(dataTypeProperty1))); //$NON-NLS-1$
		assertFalse("since dt_p2 is a containement association then the value should be copied (the original value and the copied one should be different)", //$NON-NLS-1$
				originalStereotypeApplicationP3Value.get(0).eGet(dataTypeProperty2).equals(copiedStereotypeApplicationP3Value.get(0).eGet(dataTypeProperty2)));
	}

	protected EObject getStereotypeApplication(Class stereotypedClass, String stereotypeName) {
		EObject originalStereotypeApplication = null;
		List<EObject> originalStereotypeApplications = stereotypedClass.getStereotypeApplications();
		assertFalse("the stereotyped class should have at least one stereotype", originalStereotypeApplications.isEmpty()); //$NON-NLS-1$
		for (Iterator<EObject> iterator = originalStereotypeApplications.iterator(); iterator.hasNext();) {
			EObject stereotypeApplication = iterator.next();
			if (stereotypeApplication.eClass().getName().equals(stereotypeName)) {
				originalStereotypeApplication = stereotypeApplication;
				break;
			}
		}
		assertNotNull("the stereotype " + stereotypeName + " should be applied on " + stereotypedClass.getName(), originalStereotypeApplication); //$NON-NLS-1$ //$NON-NLS-2$
		return originalStereotypeApplication;
	}

	protected EStructuralFeature getStructuralFeature(EObject eobject, String featureName) {
		EStructuralFeature propertyStructuralFeature = eobject.eClass().getEStructuralFeature(featureName);
		assertNotNull("the element should have a property named: " + featureName, propertyStructuralFeature); //$NON-NLS-1$
		return propertyStructuralFeature;
	}

}
