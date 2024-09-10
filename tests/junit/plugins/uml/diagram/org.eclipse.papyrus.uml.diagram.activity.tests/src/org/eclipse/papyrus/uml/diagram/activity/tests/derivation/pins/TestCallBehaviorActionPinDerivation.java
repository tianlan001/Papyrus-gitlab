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
 *   Jérémie TATIBOUET (CEA LIST) - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.tests.derivation.pins;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.CallBehaviorAction;
import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;

public class TestCallBehaviorActionPinDerivation extends AbstractTestPinDerivation {

	/**
	 * CallBehaviorAction advice identifier
	 */
	private final String TARGET_ADVICE_IDENTIFIER = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.CallBehaviorActionEditHelperAdvice";

	/**
	 * Function behavior specification N°1
	 */
	private Behavior behaviorSpecificationFirst;

	/**
	 * Function behavior specification N°2
	 */
	private Behavior behaviorSpecificationSecond;

	/**
	 * The action on which pin derivation is tested;
	 */
	private CallBehaviorAction callBehaviorAction;

	/**
	 * Constructor.
	 */
	public TestCallBehaviorActionPinDerivation() {
		this.behaviorSpecificationFirst = null;
		this.behaviorSpecificationSecond = null;
		this.populateBaseTestModel();
	}

	/**
	 * Role:
	 * - Ensure that the advice for the CallBehaviorAction is available
	 */
	@Test
	public void testAdviceForCallBehaviorActionExists() {
		Assert.assertNotNull("CallBehaviorAction advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER));
	}

	/**
	 * Role:
	 * - For a CallBehaviorAction having no behavior, ensure that if a behavior is referenced then the pins corresponding to the
	 * the behavior signature are inferred.
	 * 
	 * Scenario:
	 * - 1] CallBehaviorAction behavior feature is set with function behavior 'behaviorSpecificationFirst'
	 * - 2] Synchronization is performed via the advice
	 * - CallBehaviorAction has now 5 pins
	 * - 2 inputs (p11, [in] p12)
	 * - 3 outputs ([out] p12, P13, P14)
	 */
	@Test
	public void testPinDerivation_NoBehavior_To_BehaviorWithParameters() {
		SetRequest request = new SetRequest(this.editingDomain, this.callBehaviorAction, UMLPackage.eINSTANCE.getCallBehaviorAction_Behavior(), this.behaviorSpecificationFirst);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.callBehaviorAction);
		ICommand editCallBehaviorActionCommand = elementEditService.getEditCommand(request);
		if (!editCallBehaviorActionCommand.canExecute()) {
			Assert.fail("The CallBehaviorAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editCallBehaviorActionCommand));
		Assert.assertTrue("The CallBehaviorAction shall have two input pins", this.callBehaviorAction.getArguments().size() == 2);
		Assert.assertTrue("Input pins and behavior parameters are not cohesive", PinDerivationAssertionUtils.assertArgumentsAndParametersCohesion(this.callBehaviorAction.getArguments(), this.behaviorSpecificationFirst.getOwnedParameters()));
		Assert.assertTrue("The CallBehaviorAction shall have two output pins", this.callBehaviorAction.getResults().size() == 3);
		Assert.assertTrue("Output pins and behavior parameters are not cohesive", PinDerivationAssertionUtils.assertResultsAndParametersCohesion(this.callBehaviorAction.getResults(), this.behaviorSpecificationFirst.getOwnedParameters()));
		if (!editCallBehaviorActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}


	/**
	 * Role:
	 * - For a CallBehaviorAction having already been synchronized with 'behaviorSpecificationFirst' signature, ensure that if the behavior
	 * feature of the action evolves then pins are updated accordingly.
	 * 
	 * Scenario:
	 * - 1] CallBehaviorAction behavior feature is set with function behavior 'behaviorSpecificationFirst'
	 * - 2] Synchronization is performed via the advice
	 * - CallBehaviorAction has now 5 pins
	 * - 2 inputs (p11, [in] p12)
	 * - 3 outputs ([out] p12, P13, P14)
	 * - 3] CallBehaviorAction behavior feature is set with function behavior 'behaviorSpecificationSecond'
	 * - 4] Synchronization is performed via the advice
	 * - CallBehaviorAction has now 3 pins
	 * - 2 inputs ([in] p21, p22)
	 * - 1 output ([out] p21)
	 * 
	 * Note: along the synchronization process pin '[in] p12' is not deleted but updated with '[in P21]'. This was made possible
	 * because of type conformance existing between the two pins
	 */
	@Test
	public void testPinDerivation_BehaviorWithParameters_To_BehaviorWithParameters() {
		// Prepare test model
		SetRequest request = new SetRequest(this.editingDomain, this.callBehaviorAction, UMLPackage.eINSTANCE.getCallBehaviorAction_Behavior(), this.behaviorSpecificationFirst);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.callBehaviorAction);
		ICommand firstEditCallBehaviorActionCommand = elementEditService.getEditCommand(request);
		if (!firstEditCallBehaviorActionCommand.canExecute()) {
			Assert.fail("The CallBehaviorAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(firstEditCallBehaviorActionCommand));
		InputPin preservedPin = this.callBehaviorAction.getArguments().get(1);
		// Change behavior for 'behaviorSpecificationSecond'
		request = new SetRequest(this.editingDomain, this.callBehaviorAction, UMLPackage.eINSTANCE.getCallBehaviorAction_Behavior(), this.behaviorSpecificationSecond);
		ICommand secondEditCallBehaviorActionCommand = elementEditService.getEditCommand(request);
		if (!secondEditCallBehaviorActionCommand.canExecute()) {
			Assert.fail("The CallBehaviorAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(secondEditCallBehaviorActionCommand));
		Assert.assertTrue("The CallBehaviorAction shall have two input pins", this.callBehaviorAction.getArguments().size() == 2);
		Assert.assertTrue("Input pins and behavior parameters are not cohesive", PinDerivationAssertionUtils.assertArgumentsAndParametersCohesion(this.callBehaviorAction.getArguments(), this.behaviorSpecificationSecond.getOwnedParameters()));
		Assert.assertTrue("This pin shall have been preserved during update", preservedPin == this.callBehaviorAction.getArguments().get(0));
		Assert.assertTrue("The CallBehaviorAction shall have one output pins", this.callBehaviorAction.getResults().size() == 1);
		Assert.assertTrue("Output pins and behavior parameters are not cohesive", PinDerivationAssertionUtils.assertResultsAndParametersCohesion(this.callBehaviorAction.getResults(), this.behaviorSpecificationSecond.getOwnedParameters()));
		if (!secondEditCallBehaviorActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
		if (!firstEditCallBehaviorActionCommand.canUndo()) {
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
		this.callBehaviorAction = UMLFactory.eINSTANCE.createCallBehaviorAction();
		activity.getNodes().add(this.callBehaviorAction);
		this.umlTestModel.getPackagedElements().add(activity);
		// Add the test model within the model set
		Resource umlTestModelResource = new ResourceImpl();
		umlTestModelResource.setURI(URI.createURI("UMLBaseTestModel.tmp"));
		umlTestModelResource.getContents().add(this.umlTestModel);
		this.modelSet.getResources().add(umlTestModelResource);
	}
}
