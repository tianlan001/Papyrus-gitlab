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
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.StartObjectBehaviorAction;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * Pins of StartObjectBehaviorAction should be create and update automatically
 * 
 */
public class TestStartObjectBehaviorActionPinDerivation extends AbstractTestPinDerivation {

	/**
	 * StartObjectBehaviorAction advice identifier
	 */
	private final String TARGET_ADVICE_IDENTIFIER = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.StartObjectBehaviorActionEditHelperAdvice";

	private final String TARGET_ADVICE_IDENTIFIER_FOR_INPUTPIN = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.InputPinStartObjectBehaviorActionEditHelperAdvice";
	
	private final String TARGET_ADVICE_IDENTIFIER_FOR_PARAMETER = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.ParameterEditHelperAdvice";
	
	private final String TARGET_ADVICE_IDENTIFIER_FOR_BEHAVIOR = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.BehaviorEditHelperAdvice";
	/**
	 * Function behavior specification N1
	 */
	private Behavior behaviorSpecificationFirst;

	/**
	 * Function behavior specification N2
	 */
	private Behavior behaviorSpecificationSecond;

	/**
	 * The action on which pin derivation is tested;
	 */
	private StartObjectBehaviorAction startObjectBehaviorAction;

	/**
	 * Constructor.
	 */
	public TestStartObjectBehaviorActionPinDerivation() {
		this.behaviorSpecificationFirst = null;
		this.behaviorSpecificationSecond = null;
		this.populateBaseTestModel();
		// Set Automated Model Completion preference to PIN_SYNCHRONIZATION
		IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		prefStore.setValue(IAutomatedModelCompletionPreferencesConstants.START_OBJECT_BEHAVIOR_ACTION, AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION);
	}

	/**
	 * Role:
	 * - Ensure that the advice for the StartObjectBehaviorAction is available
	 */
	@Test
	public void testAdviceForStartObjectBehaviorActionExists() {
		Assert.assertNotNull("StartObjectBehaviorAction advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER));
		Assert.assertNotNull("InputPin advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER_FOR_INPUTPIN));
		Assert.assertNotNull("Parameter advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER_FOR_PARAMETER));
		Assert.assertNotNull("Behavior advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER_FOR_BEHAVIOR));
	}

	/**
	 * Role:
	 * - For a StartObjectBehaviorAction having no behavior, ensure that if a behavior is referenced then the pins corresponding to the
	 * the behavior signature are inferred. With Synchronous = true.
	 * 
	 * Scenario:
	 * - 1] StartObjectBehaviorAction object feature is set with as type the function behavior 'behaviorSpecificationFirst'
	 * - 2] Synchronization is performed via the advice
	 * - StartObjectBehaviorAction has now 6 pins
	 * - 1 input (object)
	 * - 2 inputs (p11, [in] p12)
	 * - 3 outputs ([out] p12, P13, P14)
	 */
	@Test
	public void testPinDerivation_NoBehavior_To_BehaviorWithParameters_Synchronous() {
		SetRequest request = new SetRequest(this.editingDomain, this.startObjectBehaviorAction.getObject(), UMLPackage.eINSTANCE.getTypedElement_Type(), this.behaviorSpecificationFirst);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.startObjectBehaviorAction);
		ICommand editStartObjectBehaviorActionCommand = elementEditService.getEditCommand(request);
		if (!editStartObjectBehaviorActionCommand.canExecute()) {
			Assert.fail("The StartObjectBehaviorAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editStartObjectBehaviorActionCommand));
		
		// Assert Arguments
		Assert.assertTrue("The StartObjectBehaviorAction shall have two input pins", this.startObjectBehaviorAction.getArguments().size() == 2);
		Assert.assertTrue("Input pins and behavior parameters are not cohesive", PinDerivationAssertionUtils.assertArgumentsAndParametersCohesion(this.startObjectBehaviorAction.getArguments(), this.behaviorSpecificationFirst.getOwnedParameters()));
		
		// Assert Result
		Assert.assertTrue("The StartObjectBehaviorAction shall have two output pins", this.startObjectBehaviorAction.getResults().size() == 3);
		Assert.assertTrue("Output pins and behavior parameters are not cohesive", PinDerivationAssertionUtils.assertResultsAndParametersCohesion(this.startObjectBehaviorAction.getResults(), this.behaviorSpecificationFirst.getOwnedParameters()));
		
		if (!editStartObjectBehaviorActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}
	
	/**
	 * Role:
	 * - For a StartObjectBehaviorAction having no behavior, ensure that if a behavior is referenced then the pins corresponding to the
	 * the behavior signature are inferred. With Synchronous = false.
	 * 
	 * Scenario:
	 * - 1] StartObjectBehaviorAction object feature is set with as type the function behavior 'behaviorSpecificationFirst'
	 * - 2] Synchronization is performed via the advice
	 * - StartObjectBehaviorAction has now 6 pins
	 * - 1 input (object)
	 * - 2 inputs (p11, [in] p12)
	 * - 3 outputs ([out] p12, P13, P14)
	 * 
	 * - 3] Synchronous is set to false
	 * - 4] Synchronization is performed via the advice
	 * - StartObjectBehaviorAction has now 3 pins
	 * - 1 input (object)
	 * - 2 inputs (p11, [in] p12)
	 */
	@Test
	public void testPinDerivation_NoBehavior_To_BehaviorWithParameters_Asynchronous() {
		SetRequest request = new SetRequest(this.editingDomain, this.startObjectBehaviorAction.getObject(), UMLPackage.eINSTANCE.getTypedElement_Type(), this.behaviorSpecificationFirst);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.startObjectBehaviorAction);
		ICommand editStartObjectBehaviorActionCommand = elementEditService.getEditCommand(request);
		if (!editStartObjectBehaviorActionCommand.canExecute()) {
			Assert.fail("The StartObjectBehaviorAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editStartObjectBehaviorActionCommand));
		
		// Set isSynchronous to false
		request = new SetRequest(this.editingDomain, this.startObjectBehaviorAction, UMLPackage.eINSTANCE.getCallAction_IsSynchronous(), false);
		ICommand setIsSynchronousCommand = elementEditService.getEditCommand(request);
		if (!setIsSynchronousCommand.canExecute()) {
			Assert.fail("The StartObjectBehaviorAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(setIsSynchronousCommand));
		
		// Assert Arguments
		Assert.assertTrue("The StartObjectBehaviorAction shall have two input pins", this.startObjectBehaviorAction.getArguments().size() == 2);
		Assert.assertTrue("Input pins and behavior parameters are not cohesive", PinDerivationAssertionUtils.assertArgumentsAndParametersCohesion(this.startObjectBehaviorAction.getArguments(), this.behaviorSpecificationFirst.getOwnedParameters()));
		
		// Assert Result
		Assert.assertTrue("The StartObjectBehaviorAction shall have zero output pins", this.startObjectBehaviorAction.getResults().size() == 0);
		
		if (!setIsSynchronousCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
		if (!editStartObjectBehaviorActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}

	/**
	 * Role:
	 * - For a StartObjectBehaviorAction having already been synchronized with 'behaviorSpecificationFirst' signature, ensure that if the behavior
	 * feature of the action evolves then pins are updated accordingly. With Synchronous = true.
	 * 
	 * Scenario:
	 * - 1] StartObjectBehaviorAction object feature is set with as type the function behavior 'behaviorSpecificationFirst'
	 * - 2] Synchronization is performed via the advice
	 * - StartObjectBehaviorAction has now 6 pins
	 * - 1 input (object)
	 * - 2 inputs (p11, [in] p12)
	 * - 3 outputs ([out] p12, P13, P14)
	 * - 3] StartObjectBehaviorAction object feature is set with as type the function behavior 'behaviorSpecificationSecond'
	 * - 4] Synchronization is performed via the advice
	 * - StartObjectBehaviorAction has now 3 pins
	 * - 2 inputs ([in] p21, p22)
	 * - 1 output ([out] p21)
	 * 
	 * Note: along the synchronization process pin '[in] p12' is not deleted but updated with '[in P21]'. This was made possible
	 * because of type conformance existing between the two pins
	 */
	@Test
	public void testPinDerivation_BehaviorWithParameters_To_OtherBehaviorWithParameters_Synchronous() {
		// Prepare test model
		SetRequest request = new SetRequest(this.editingDomain, this.startObjectBehaviorAction.getObject(), UMLPackage.eINSTANCE.getTypedElement_Type(), this.behaviorSpecificationFirst);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.startObjectBehaviorAction);
		ICommand firstEditStartObjectBehaviorActionCommand = elementEditService.getEditCommand(request);
		if (!firstEditStartObjectBehaviorActionCommand.canExecute()) {
			Assert.fail("The StartObjectBehaviorAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(firstEditStartObjectBehaviorActionCommand));
		InputPin preservedPin = this.startObjectBehaviorAction.getArguments().get(1);
		
		// Change behavior for 'behaviorSpecificationSecond'
		request = new SetRequest(this.editingDomain, this.startObjectBehaviorAction.getObject(), UMLPackage.eINSTANCE.getTypedElement_Type(), this.behaviorSpecificationSecond);
		ICommand secondEditStartObjectBehaviorActionCommand = elementEditService.getEditCommand(request);
		if (!secondEditStartObjectBehaviorActionCommand.canExecute()) {
			Assert.fail("The StartObjectBehaviorAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(secondEditStartObjectBehaviorActionCommand));
		
		Assert.assertTrue("The StartObjectBehaviorAction shall have two input pins", this.startObjectBehaviorAction.getArguments().size() == 2);
		Assert.assertTrue("Input pins and behavior parameters are not cohesive", PinDerivationAssertionUtils.assertArgumentsAndParametersCohesion(this.startObjectBehaviorAction.getArguments(), this.behaviorSpecificationSecond.getOwnedParameters()));
		Assert.assertTrue("This pin shall have been preserved during update", preservedPin == this.startObjectBehaviorAction.getArguments().get(0));
		Assert.assertTrue("The StartObjectBehaviorAction shall have one output pins", this.startObjectBehaviorAction.getResults().size() == 1);
		Assert.assertTrue("Output pins and behavior parameters are not cohesive", PinDerivationAssertionUtils.assertResultsAndParametersCohesion(this.startObjectBehaviorAction.getResults(), this.behaviorSpecificationSecond.getOwnedParameters()));
		
		if (!secondEditStartObjectBehaviorActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
		if (!firstEditStartObjectBehaviorActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}

	/**
	 * Role:
	 * - For a StartObjectBehaviorAction having already been synchronized with 'behaviorSpecificationFirst' signature, ensure that if the behavior
	 * feature of the action evolves then pins are updated accordingly. With Synchronous = true.
	 * 
	 * Scenario:
	 * - 1] StartObjectBehaviorAction object feature is set with as type the function behavior 'behaviorSpecificationFirst'
	 * - 2] Synchronization is performed via the advice
	 * - StartObjectBehaviorAction has now 6 pins
	 * - 1 input (object)
	 * - 2 inputs (p11, [in] p12)
	 * - 3 outputs ([out] p12, P13, P14)
	 * - 3] Type of the parameter p11 of the 'behaviorSpecificationFirst' is change
	 * - 4] Synchronization is performed via the advice
	 * - StartObjectBehaviorAction has 6 pins
	 * - 1 input (object)
	 * - 2 inputs (p11, [in] p12)
	 * - 3 outputs ([out] p12, P13, P14)
	 * 
	 */
	@Test
	public void testPinDerivation_BehaviorWithParameters_ChangeParameter_Synchronous() {
		// Prepare test model
		SetRequest request = new SetRequest(this.editingDomain, this.startObjectBehaviorAction.getObject(), UMLPackage.eINSTANCE.getTypedElement_Type(), this.behaviorSpecificationFirst);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.startObjectBehaviorAction);
		ICommand firstEditStartObjectBehaviorActionCommand = elementEditService.getEditCommand(request);
		if (!firstEditStartObjectBehaviorActionCommand.canExecute()) {
			Assert.fail("The StartObjectBehaviorAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(firstEditStartObjectBehaviorActionCommand));
		
		// Change Type of 'behaviorSpecificationFirst'
		request = new SetRequest(this.editingDomain, this.behaviorSpecificationFirst.getOwnedParameter("p11", this.getPrimitiveType("UnlimitedNatural")), UMLPackage.eINSTANCE.getTypedElement_Type(), this.getPrimitiveType("Integer"));
		ICommand secondEditStartObjectBehaviorActionCommand = elementEditService.getEditCommand(request);
		if (!secondEditStartObjectBehaviorActionCommand.canExecute()) {
			Assert.fail("The StartObjectBehaviorAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(secondEditStartObjectBehaviorActionCommand));
		
		Assert.assertTrue("The StartObjectBehaviorAction shall have two input pins", this.startObjectBehaviorAction.getArguments().size() == 2);
		Assert.assertTrue("Input pins and behavior parameters are not cohesive", PinDerivationAssertionUtils.assertArgumentsAndParametersCohesion(this.startObjectBehaviorAction.getArguments(), this.behaviorSpecificationFirst.getOwnedParameters()));
		Assert.assertTrue("The StartObjectBehaviorAction shall have one output pins", this.startObjectBehaviorAction.getResults().size() == 3);
		Assert.assertTrue("Output pins and behavior parameters are not cohesive", PinDerivationAssertionUtils.assertResultsAndParametersCohesion(this.startObjectBehaviorAction.getResults(), this.behaviorSpecificationFirst.getOwnedParameters()));
		
		if (!secondEditStartObjectBehaviorActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
		if (!firstEditStartObjectBehaviorActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}
	
	/**
	 * Role:
	 * - For a StartObjectBehaviorAction having already been synchronized with 'behaviorSpecificationFirst' signature, ensure that if the behavior
	 * feature of the action evolves then pins are updated accordingly. With Synchronous = true.
	 * 
	 * Scenario:
	 * - 1] StartObjectBehaviorAction object feature is set with as type the function behavior 'behaviorSpecificationFirst'
	 * - 2] Synchronization is performed via the advice
	 * - StartObjectBehaviorAction has now 6 pins
	 * - 1 input (object)
	 * - 2 inputs (p11, [in] p12)
	 * - 3 outputs ([out] p12, P13, P14)
	 * - 3] A parameter (INOUT) is add to the Behavior
	 * - 4] Synchronization is performed via the advice
	 * - StartObjectBehaviorAction has 8 pins
	 * - 1 input (object)
	 * - 3 inputs (p11, [in] p12) + the new one
	 * - 4 outputs ([out] p12, P13, P14) + the new one
	 * 
	 */
	@Test
	public void testPinDerivation_BehaviorWithParameters_AddParameter_Synchronous() {
		// Prepare test model
		SetRequest request = new SetRequest(this.editingDomain, this.startObjectBehaviorAction.getObject(), UMLPackage.eINSTANCE.getTypedElement_Type(), this.behaviorSpecificationFirst);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.startObjectBehaviorAction);
		ICommand firstEditStartObjectBehaviorActionCommand = elementEditService.getEditCommand(request);
		if (!firstEditStartObjectBehaviorActionCommand.canExecute()) {
			Assert.fail("The StartObjectBehaviorAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(firstEditStartObjectBehaviorActionCommand));
		
		// Add paramter to 'behaviorSpecificationFirst'
		Parameter parameter = UMLFactory.eINSTANCE.createParameter();
		parameter.setDirection(ParameterDirectionKind.INOUT_LITERAL);
		request = new SetRequest(this.editingDomain, this.behaviorSpecificationFirst, UMLPackage.eINSTANCE.getBehavior_OwnedParameter(), parameter);
		ICommand secondEditStartObjectBehaviorActionCommand = elementEditService.getEditCommand(request);
		if (!secondEditStartObjectBehaviorActionCommand.canExecute()) {
			Assert.fail("The StartObjectBehaviorAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(secondEditStartObjectBehaviorActionCommand));
		
		Assert.assertTrue("The StartObjectBehaviorAction shall have 3 input pins", this.startObjectBehaviorAction.getArguments().size() == 3);
		Assert.assertTrue("Input pins and behavior parameters are not cohesive", PinDerivationAssertionUtils.assertArgumentsAndParametersCohesion(this.startObjectBehaviorAction.getArguments(), this.behaviorSpecificationFirst.getOwnedParameters()));
		Assert.assertTrue("The StartObjectBehaviorAction shall have one output pins", this.startObjectBehaviorAction.getResults().size() == 4);
		Assert.assertTrue("Output pins and behavior parameters are not cohesive", PinDerivationAssertionUtils.assertResultsAndParametersCohesion(this.startObjectBehaviorAction.getResults(), this.behaviorSpecificationFirst.getOwnedParameters()));
		
		if (!secondEditStartObjectBehaviorActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
		if (!firstEditStartObjectBehaviorActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}
	
	/**
	 * @see org.eclipse.papyrus.uml.diagram.activity.tests.derivation.pins.AbstractTestPinDerivation#createBaseTestModel()
	 */
	@Override
	public void populateBaseTestModel() {
		super.populateBaseTestModel();
		
		// Define and attach the first behavior specification to the model
		this.behaviorSpecificationFirst = UMLFactory.eINSTANCE.createFunctionBehavior();
		Parameter p11 = UMLFactory.eINSTANCE.createParameter();
		p11.setName("p11");
		p11.setDirection(ParameterDirectionKind.IN_LITERAL);
		p11.setLower(1);
		p11.setUpper(1);
		p11.setType(this.getPrimitiveType("UnlimitedNatural"));
		Parameter p12 = UMLFactory.eINSTANCE.createParameter();
		p12.setName("p12");
		p12.setDirection(ParameterDirectionKind.INOUT_LITERAL);
		p12.setLower(1);
		p12.setUpper(1);
		p12.setType(this.getPrimitiveType("String"));
		Parameter p13 = UMLFactory.eINSTANCE.createParameter();
		p13.setName("p13");
		p13.setDirection(ParameterDirectionKind.OUT_LITERAL);
		p13.setLower(1);
		p13.setUpper(1);
		p13.setType(this.getPrimitiveType("String"));
		Parameter p14 = UMLFactory.eINSTANCE.createParameter();
		p14.setName("p14");
		p14.setDirection(ParameterDirectionKind.RETURN_LITERAL);
		p14.setLower(1);
		p14.setUpper(1);
		p14.setType(this.getPrimitiveType("Boolean"));
		this.behaviorSpecificationFirst.getOwnedParameters().add(p11);
		this.behaviorSpecificationFirst.getOwnedParameters().add(p12);
		this.behaviorSpecificationFirst.getOwnedParameters().add(p13);
		this.behaviorSpecificationFirst.getOwnedParameters().add(p14);
		
		// Define and attach the second behavior specification to the model
		this.behaviorSpecificationSecond = UMLFactory.eINSTANCE.createFunctionBehavior();
		Parameter p21 = UMLFactory.eINSTANCE.createParameter();
		p21.setName("p21");
		p21.setDirection(ParameterDirectionKind.INOUT_LITERAL);
		p21.setLower(1);
		p21.setUpper(1);
		p21.setType(this.getPrimitiveType("String"));
		Parameter p22 = UMLFactory.eINSTANCE.createParameter();
		p22.setName("p22");
		p22.setDirection(ParameterDirectionKind.IN_LITERAL);
		p22.setLower(1);
		p22.setUpper(1);
		p22.setType(this.getPrimitiveType("Integer"));
		this.behaviorSpecificationSecond.getOwnedParameters().add(p21);
		this.behaviorSpecificationSecond.getOwnedParameters().add(p22);
		
		// Add both behaviors in the model
		this.umlTestModel.getPackagedElements().add(this.behaviorSpecificationFirst);
		this.umlTestModel.getPackagedElements().add(this.behaviorSpecificationSecond);
		
		// Add the activity which will contain the call behavior action
		Activity activity = UMLFactory.eINSTANCE.createActivity();
		this.startObjectBehaviorAction = UMLFactory.eINSTANCE.createStartObjectBehaviorAction();
		InputPin object = UMLFactory.eINSTANCE.createInputPin();
		this.startObjectBehaviorAction.setObject(object);
		activity.getOwnedNodes().add(this.startObjectBehaviorAction);
		this.umlTestModel.getPackagedElements().add(activity);
		
		// Add the test model within the model set
		Resource umlTestModelResource = new ResourceImpl();
		umlTestModelResource.setURI(URI.createURI("UMLBaseTestModel.tmp"));
		umlTestModelResource.getContents().add(this.umlTestModel);
		this.modelSet.getResources().add(umlTestModelResource);
	}
}
