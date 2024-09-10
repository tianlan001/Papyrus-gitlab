/*****************************************************************************
 * Copyright (c) 2016, 2017 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   Christian W. Damus - bug 528343
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.internationalization.tests.tests;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;

/**
 * This allows to define the abstract class for the UML internationalization
 * tests.
 */
@SuppressWarnings("nls")
public abstract class AbstractUMLInternationalizationTest extends AbstractPapyrusTest {

	/** The papyrus fixture for the project. */
	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();

	/** The class name. */
	protected static final String CLASS_NAME = "Class1";

	/** The operation name. */
	protected static final String OPERATION_NAME = "Operation1";

	/** The parameter name. */
	protected static final String PARAMETER_NAME = "Parameter1";

	/** The property name. */
	protected static final String PROPERTY_NAME = "Attribute1";

	/** The package name. */
	protected static final String PACKAGE_NAME = "Package1";

	/** The interface name. */
	protected static final String INTERFACE_NAME = "Interface1";

	/** The enumeration name. */
	protected static final String ENUMERATION_NAME = "Enumeration1";

	/** The model. */
	protected Model model;

	/** The class. */
	protected org.eclipse.uml2.uml.Class modelClass;

	/** The operation. */
	protected Operation modelOperation;

	/** The parameter. */
	protected Parameter modelParameter;

	/** The property. */
	protected Property modelProperty;

	/** The package. */
	protected Package modelPackage;

	/** The interface. */
	protected Interface modelInterface;

	/** The enumeration. */
	protected Enumeration modelEnumeration;

	/** The label provider. */
	protected ILabelProvider labelProvider;

	/**
	 * Constructor.
	 */
	public AbstractUMLInternationalizationTest() {
		super();
	}

	/**
	 * Initialize the model.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	@Before
	public void initTest() throws Exception {
		
		fixture.flushDisplayEvents();

		model = (Model) fixture.getModel();
		Assert.assertNotNull("The model cannot be null", model);

		labelProvider = initLabelProvider();

		modelClass = (Class) model.getOwnedMember(CLASS_NAME);
		Assert.assertNotNull("The class cannot be null", modelClass);
		Assert.assertEquals("Class is not the one Expected", CLASS_NAME, modelClass.getName());

		modelOperation = (Operation) modelClass.getOwnedMember(OPERATION_NAME);
		Assert.assertNotNull("The operation cannot be null", modelOperation);
		Assert.assertEquals("Operation is not the one Expected", OPERATION_NAME, modelOperation.getName());

		modelParameter = (Parameter) modelOperation.getOwnedMember(PARAMETER_NAME);
		Assert.assertNotNull("The parameter cannot be null", modelParameter);
		Assert.assertEquals("Parameter is not the one Expected", PARAMETER_NAME, modelParameter.getName());

		modelProperty = (Property) modelClass.getOwnedMember(PROPERTY_NAME);
		Assert.assertNotNull("The property cannot be null", modelProperty);
		Assert.assertEquals("Property is not the one Expected", PROPERTY_NAME, modelProperty.getName());

		modelPackage = (Package) model.getOwnedMember(PACKAGE_NAME);
		Assert.assertNotNull("The package cannot be null", modelPackage);
		Assert.assertEquals("Package is not the one Expected", PACKAGE_NAME, modelPackage.getName());

		modelInterface = (Interface) modelPackage.getOwnedMember(INTERFACE_NAME);
		Assert.assertNotNull("The interface cannot be null", modelInterface);
		Assert.assertEquals("Interface is not the one Expected", INTERFACE_NAME, modelInterface.getName());

		modelEnumeration = (Enumeration) modelInterface.getOwnedMember(ENUMERATION_NAME);
		Assert.assertNotNull("The enumeration cannot be null", modelEnumeration);
		Assert.assertEquals("Enumeration is not the one Expected", ENUMERATION_NAME, modelEnumeration.getName());
	}
	
	/**
	 * Obtain or create the label provider for tests to verify labels.
	 * 
	 * @return the label provider fixture
	 */
	protected ILabelProvider initLabelProvider() {
		return (ILabelProvider) fixture.getModelExplorerView().getCommonViewer().getLabelProvider();
	}

	/**
	 * This allows to test the labels when the internationalization is not used.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	public void checkNoLabels() throws Exception {

		Assert.assertEquals("The root element label is not the expected one.", "RootElement",
				labelProvider.getText(model));

		Assert.assertEquals("The class label is not the expected one.", CLASS_NAME, labelProvider.getText(modelClass));

		Assert.assertEquals("The operation label is not the expected one.",
				OPERATION_NAME + " (" + PARAMETER_NAME + ")", labelProvider.getText(modelOperation));

		Assert.assertEquals("The parameter label is not the expected one.", PARAMETER_NAME,
				labelProvider.getText(modelParameter));

		Assert.assertEquals("The property label is not the expected one.", PROPERTY_NAME,
				labelProvider.getText(modelProperty));

		Assert.assertEquals("The package label is not the expected one.", PACKAGE_NAME,
				labelProvider.getText(modelPackage));

		Assert.assertEquals("The interface label is not the expected one.", INTERFACE_NAME,
				labelProvider.getText(modelInterface));

		Assert.assertEquals("The enumeration label is not the expected one.", ENUMERATION_NAME,
				labelProvider.getText(modelEnumeration));
	}

	/**
	 * This allows to test the french labels of all initial existing objects.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	public void checkFrenchLabels() throws Exception {

		Assert.assertEquals("The root element label is not the expected one.", "MonElementRoot",
				labelProvider.getText(model));

		Assert.assertEquals("The class label is not the expected one.", "MaPremiereClasse",
				labelProvider.getText(modelClass));

		Assert.assertEquals("The operation label is not the expected one.", "MonOperation (MonParametre)",
				labelProvider.getText(modelOperation));

		Assert.assertEquals("The parameter label is not the expected one.", "MonParametre",
				labelProvider.getText(modelParameter));

		Assert.assertEquals("The property label is not the expected one.", "MonAttribut",
				labelProvider.getText(modelProperty));

		Assert.assertEquals("The package label is not the expected one.", "MonPackage",
				labelProvider.getText(modelPackage));

		Assert.assertEquals("The interface label is not the expected one.", "MonInterface",
				labelProvider.getText(modelInterface));

		Assert.assertEquals("The enumeration label is not the expected one.", "MonEnumeration",
				labelProvider.getText(modelEnumeration));
	}

	/**
	 * This allows to test the english labels of all initial existing objects.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	public void checkEnglishLabels() throws Exception {

		Assert.assertEquals("The root element label is not the expected one.", "MyRootElement",
				labelProvider.getText(model));

		Assert.assertEquals("The class label is not the expected one.", "MyFirstClass",
				labelProvider.getText(modelClass));

		Assert.assertEquals("The operation label is not the expected one.", "MyOperation (MyParameter)",
				labelProvider.getText(modelOperation));

		Assert.assertEquals("The parameter label is not the expected one.", "MyParameter",
				labelProvider.getText(modelParameter));

		Assert.assertEquals("The property label is not the expected one.", "MyAttribute",
				labelProvider.getText(modelProperty));

		Assert.assertEquals("The package label is not the expected one.", "MyPackage",
				labelProvider.getText(modelPackage));

		Assert.assertEquals("The interface label is not the expected one.", "MyInterface",
				labelProvider.getText(modelInterface));

		Assert.assertEquals("The enumeration label is not the expected one.", "MyEnumeration",
				labelProvider.getText(modelEnumeration));
	}
}
