/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   (CEA LIST) - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.tests.derivation.pins;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.preferences.AutomatedModelCompletionPreferencesInitializer;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.preferences.IAutomatedModelCompletionPreferencesConstants;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.InstanceSpecification;
import org.eclipse.uml2.uml.InstanceValue;
import org.eclipse.uml2.uml.LiteralBoolean;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralReal;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecificationAction;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * Pin of ValueSpecificationAction should be create and update automatically
 *
 */
public class TestValueSpecificationActionPinDerivation extends AbstractTestPinDerivation {

	private final String TARGET_ADVICE_IDENTIFIER = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.ValueSpecificationActionEditHelperAdvice";

	private final String TARGET_ADVICE_IDENTIFIER_FOR_INSTANCE_SPECIFICATION = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.InstanceSpecificationEditHelperAdvice";

	private final String TARGET_ADVICE_IDENTIFIER_FOR_INSTANCE_VALUE = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.InstanceValueEditHelperAdvice";

	private ValueSpecificationAction valueSpecificationAction;

	private LiteralInteger integer;

	private LiteralBoolean bool;

	private LiteralString string;

	private LiteralUnlimitedNatural unlimitedNatural;

	private LiteralReal real;

	private InstanceValue enumerationInstanceValue;

	private Enumeration enumeration;

	private Class class1;

	private InstanceValue instanceSpecInstanceValue;

	/**
	 * Constructor.
	 */
	public TestValueSpecificationActionPinDerivation() {
		this.integer = null;
		this.bool = null;
		this.string = null;
		this.unlimitedNatural = null;
		this.real = null;
		this.enumerationInstanceValue = null;
		this.enumeration = null;
		this.class1 = null;
		this.instanceSpecInstanceValue = null;
		this.populateBaseTestModel();
		// Set Automated Model Completion preference to PIN_SYNCHRONIZATION
		IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		prefStore.setValue(IAutomatedModelCompletionPreferencesConstants.VALUE_SPECIFICATION_ACTION, AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION);
	}

	/**
	 * Role:
	 * - Ensure that the advice for the ValueSpecificationAction is available
	 */
	@Test
	public void testAdviceForValueSpecificationActionExists() {
		Assert.assertNotNull("ValueSpecificationAction advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER));
		Assert.assertNotNull("InstanceSpecification advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER_FOR_INSTANCE_SPECIFICATION));
		Assert.assertNotNull("InstanceValue advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER_FOR_INSTANCE_VALUE));
	}

	/**
	 * Role:
	 * - For a ValueSpecificationAction having no referenced ValueSpecification, ensure that if a value gets referenced the pin
	 * is inferred.
	 * 
	 * Scenario:
	 * - 1] ValueSpecificationAction 'value' feature is set with the 'integer'
	 * - 2] Synchronization is performed via the advice
	 * - ValueSpecificationAction has now 1 pin
	 * - 1 result (typed by Integer)
	 */
	@Test
	public void testPinDerivation_NoValue_ToLiteralIntegerValue() {
		SetRequest request = new SetRequest(this.editingDomain, this.valueSpecificationAction, UMLPackage.eINSTANCE.getValueSpecificationAction_Value(), this.integer);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.valueSpecificationAction);
		ICommand editValueSpecificationActionCommand = elementEditService.getEditCommand(request);
		if (!editValueSpecificationActionCommand.canExecute()) {
			Assert.fail("The ValueSpecificationAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editValueSpecificationActionCommand));

		// Assert target derivation
		Assert.assertTrue("ValueSpecificationAction shall have a result", this.valueSpecificationAction.getResult() != null); 
		Assert.assertTrue("ValueSpecificationAction shall have a result typed by Integer", this.valueSpecificationAction.getResult().getType() == this.getPrimitiveType("Integer"));

		if (!editValueSpecificationActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}

	/**
	 * Role:
	 * - For a ValueSpecificationAction having no referenced ValueSpecification, ensure that if a value gets referenced the pin
	 * is inferred.
	 * 
	 * Scenario:
	 * - 1] ValueSpecificationAction 'value' feature is set with the 'boolean'
	 * - 2] Synchronization is performed via the advice
	 * - ValueSpecificationAction has now 1 pin
	 * - 1 result (typed by Boolean)
	 */
	@Test
	public void testPinDerivation_NoValue_ToLiteralBooleanValue() {
		SetRequest request = new SetRequest(this.editingDomain, this.valueSpecificationAction, UMLPackage.eINSTANCE.getValueSpecificationAction_Value(), this.bool);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.valueSpecificationAction);
		ICommand editValueSpecificationActionCommand = elementEditService.getEditCommand(request);
		if (!editValueSpecificationActionCommand.canExecute()) {
			Assert.fail("The ValueSpecificationAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editValueSpecificationActionCommand));

		// Assert target derivation
		Assert.assertTrue("ValueSpecificationAction shall have a result", this.valueSpecificationAction.getResult() != null); 
		Assert.assertTrue("ValueSpecificationAction shall have a result typed by Boolean", this.valueSpecificationAction.getResult().getType() == this.getPrimitiveType("Boolean"));

		if (!editValueSpecificationActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}

	/**
	 * Role:
	 * - For a ValueSpecificationAction having no referenced ValueSpecification, ensure that if a value gets referenced the pin
	 * is inferred.
	 * 
	 * Scenario:
	 * - 1] ValueSpecificationAction 'value' feature is set with the 'string'
	 * - 2] Synchronization is performed via the advice
	 * - ValueSpecificationAction has now 1 pin
	 * - 1 result (typed by String)
	 */
	@Test
	public void testPinDerivation_NoValue_ToLiteralStringValue() {
		SetRequest request = new SetRequest(this.editingDomain, this.valueSpecificationAction, UMLPackage.eINSTANCE.getValueSpecificationAction_Value(), this.string);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.valueSpecificationAction);
		ICommand editValueSpecificationActionCommand = elementEditService.getEditCommand(request);
		if (!editValueSpecificationActionCommand.canExecute()) {
			Assert.fail("The ValueSpecificationAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editValueSpecificationActionCommand));

		// Assert target derivation
		Assert.assertTrue("ValueSpecificationAction shall have a result", this.valueSpecificationAction.getResult() != null);
		Assert.assertTrue("ValueSpecificationAction shall have a result typed by String", this.valueSpecificationAction.getResult().getType() == this.getPrimitiveType("String"));

		if (!editValueSpecificationActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}

	/**
	 * Role:
	 * - For a ValueSpecificationAction having no referenced ValueSpecification, ensure that if a value gets referenced the pin
	 * is inferred.
	 * 
	 * Scenario:
	 * - 1] ValueSpecificationAction 'value' feature is set with the 'string'
	 * - 2] Synchronization is performed via the advice
	 * - ValueSpecificationAction has now 1 pin
	 * - 1 result (typed by String)
	 */
	@Test
	public void testPinDerivation_NoValue_ToLiteralUnlimitedNaturalValue() {
		SetRequest request = new SetRequest(this.editingDomain, this.valueSpecificationAction, UMLPackage.eINSTANCE.getValueSpecificationAction_Value(), this.unlimitedNatural);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.valueSpecificationAction);
		ICommand editValueSpecificationActionCommand = elementEditService.getEditCommand(request);
		if (!editValueSpecificationActionCommand.canExecute()) {
			Assert.fail("The ValueSpecificationAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editValueSpecificationActionCommand));

		// Assert target derivation
		Assert.assertTrue("ValueSpecificationAction shall have a result", this.valueSpecificationAction.getResult() != null);
		Assert.assertTrue("ValueSpecificationAction shall have a result typed by UnlimitedNatural", this.valueSpecificationAction.getResult().getType() == this.getPrimitiveType("UnlimitedNatural"));

		if (!editValueSpecificationActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}

	/**
	 * Role:
	 * - For a ValueSpecificationAction having no referenced ValueSpecification, ensure that if a value gets referenced the pin
	 * is inferred.
	 * 
	 * Scenario:
	 * - 1] ValueSpecificationAction 'value' feature is set with the 'string'
	 * - 2] Synchronization is performed via the advice
	 * - ValueSpecificationAction has now 1 pin
	 * - 1 result (typed by String)
	 */
	@Test
	public void testPinDerivation_NoValue_ToLiteralRealValue() {
		SetRequest request = new SetRequest(this.editingDomain, this.valueSpecificationAction, UMLPackage.eINSTANCE.getValueSpecificationAction_Value(), this.real);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.valueSpecificationAction);
		ICommand editValueSpecificationActionCommand = elementEditService.getEditCommand(request);
		if (!editValueSpecificationActionCommand.canExecute()) {
			Assert.fail("The ValueSpecificationAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editValueSpecificationActionCommand));

		// Assert target derivation
		Assert.assertTrue("ValueSpecificationAction shall have a result", this.valueSpecificationAction.getResult() != null);
		Assert.assertTrue("ValueSpecificationAction shall have a result typed by Real", this.valueSpecificationAction.getResult().getType() == this.getPrimitiveType("Real"));

		if (!editValueSpecificationActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}

	/**
	 * Role:
	 * - For a ValueSpecificationAction having no referenced ValueSpecification, ensure that if a value gets referenced the pin
	 * is inferred.
	 * 
	 * Scenario:
	 * - 1] ValueSpecificationAction 'value' feature is set with the 'enumerationInstance'
	 * - 2] Synchronization is performed via the advice
	 * - ValueSpecificationAction has now 1 pin
	 * - 1 result (typed by the enumeration)
	 */
	@Test
	public void testPinDerivation_NoValue_ToLiteralEnumerationValue() {
		SetRequest request = new SetRequest(this.editingDomain, this.valueSpecificationAction, UMLPackage.eINSTANCE.getValueSpecificationAction_Value(), this.enumerationInstanceValue);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.valueSpecificationAction);
		ICommand editValueSpecificationActionCommand = elementEditService.getEditCommand(request);
		if (!editValueSpecificationActionCommand.canExecute()) {
			Assert.fail("The ValueSpecificationAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editValueSpecificationActionCommand));

		// Assert target derivation
		Assert.assertTrue("ValueSpecificationAction shall have a result", this.valueSpecificationAction.getResult() != null);
		Assert.assertTrue("ValueSpecificationAction shall have a result typed by enumeration", this.valueSpecificationAction.getResult().getType() == this.enumeration);

		if (!editValueSpecificationActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}

	/**
	 * Role:
	 * - For a ValueSpecificationAction having no referenced ValueSpecification, ensure that if a value gets referenced the pin
	 * is inferred.
	 * 
	 * Scenario:
	 * - 1] ValueSpecificationAction 'value' feature is set with the 'instanceSpecification'
	 * - 2] Synchronization is performed via the advice
	 * - ValueSpecificationAction has now 1 pin
	 * - 1 result (typed by clazz)
	 */
	@Test
	public void testPinDerivation_NoValue_ToInstanceSpecificationValueSingleClassifier() {
		SetRequest request = new SetRequest(this.editingDomain, this.valueSpecificationAction, UMLPackage.eINSTANCE.getValueSpecificationAction_Value(), this.instanceSpecInstanceValue);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.valueSpecificationAction);
		ICommand editValueSpecificationActionCommand = elementEditService.getEditCommand(request);
		if (!editValueSpecificationActionCommand.canExecute()) {
			Assert.fail("The ValueSpecificationAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editValueSpecificationActionCommand));

		// Assert target derivation
		Assert.assertTrue("ValueSpecificationAction shall have a result", this.valueSpecificationAction.getResult() != null);
		Assert.assertTrue("ValueSpecificationAction shall have a result typed by clazz", this.valueSpecificationAction.getResult().getType() == this.class1);

		if (!editValueSpecificationActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.tests.derivation.pins.AbstractTestPinDerivation#populateBaseTestModel()
	 *
	 */
	@Override
	public void populateBaseTestModel() {
		super.populateBaseTestModel();
		// Create LiteralInteger
		integer = UMLFactory.eINSTANCE.createLiteralInteger();

		// Create LiteralBoolean
		bool = UMLFactory.eINSTANCE.createLiteralBoolean();

		// Create LiteralString
		string = UMLFactory.eINSTANCE.createLiteralString();

		// Create LiteralUnlimitedNatural
		unlimitedNatural = UMLFactory.eINSTANCE.createLiteralUnlimitedNatural();

		// Create LiteralReal
		real = UMLFactory.eINSTANCE.createLiteralReal();

		// Create EnumerationInstanceValue
		enumerationInstanceValue = UMLFactory.eINSTANCE.createInstanceValue();
		enumeration = UMLFactory.eINSTANCE.createEnumeration();
		EnumerationLiteral enumerationLiteral = UMLFactory.eINSTANCE.createEnumerationLiteral();
		enumeration.getOwnedLiterals().add(enumerationLiteral);
		enumerationInstanceValue.setInstance(enumerationLiteral);

		// Create InstanceSpecification
		instanceSpecInstanceValue = UMLFactory.eINSTANCE.createInstanceValue();
		class1 = UMLFactory.eINSTANCE.createClass();
		class1.setName("C1");
		InstanceSpecification instanceSpec = UMLFactory.eINSTANCE.createInstanceSpecification();
		instanceSpec.getClassifiers().add(class1);
		instanceSpecInstanceValue.setInstance(instanceSpec);

		// Create the activity containing the send signal action
		Activity activity = UMLFactory.eINSTANCE.createActivity();
		this.valueSpecificationAction = UMLFactory.eINSTANCE.createValueSpecificationAction();
		activity.getOwnedNodes().add(this.valueSpecificationAction);

		// Add the different elements to the model
		this.umlTestModel.getPackagedElements().add(activity);

		// Add the test model within the model set
		Resource umlTestModelResource = new ResourceImpl();
		umlTestModelResource.setURI(URI.createURI("UMLBaseTestModel.tmp"));
		umlTestModelResource.getContents().add(this.umlTestModel);
		this.modelSet.getResources().add(umlTestModelResource);
	}
}
