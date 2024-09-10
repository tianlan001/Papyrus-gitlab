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
import org.eclipse.uml2.uml.CallOperationAction;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;

public class TestCallOperationActionPinDerivation extends AbstractTestPinDerivation {

	/**
	 * CallBehaviorAction advice identifier
	 */
	private final String TARGET_ADVICE_IDENTIFIER = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.CallOperationActionEditHelperAdvice";

	/**
	 * Operation signature N°1
	 */
	private Operation operationSignatureFirst;

	/**
	 * Operation signature N°2
	 */
	private Operation operationSignatureSecond;

	/**
	 * The action on which pin derivation is tested
	 */
	private CallOperationAction callOperationAction;

	/**
	 * Constructor.
	 */
	public TestCallOperationActionPinDerivation() {
		this.operationSignatureFirst = null;
		this.operationSignatureSecond = null;
		this.populateBaseTestModel();
	}

	/**
	 * Role:
	 * - Ensure that the advice for the CallOperationAction is available
	 */
	@Test
	public void testAdviceForCallOperationActionExists() {
		Assert.assertNotNull("CallOperationAction advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER));
	}

	/**
	 * Role:
	 * - For a CallOperationAction having no behavior, ensure that if an operation is referenced then the pins corresponding to the
	 * the operation signature are inferred.
	 * 
	 * Scenario:
	 * - 1] CallOperationAction 'operation' feature is set with operation 'operationSignatureFirst'
	 * - 2] Synchronization is performed via the advice
	 * - CallOperationAction has now 4 pins
	 * - 1 target (typed by C)
	 * - 2 inputs ([in] p21, p22)
	 * - 1 output ([out] p21)
	 */
	@Test
	public void testPinDerivation_NoOperation_To_OperationWithParameters() {
		SetRequest request = new SetRequest(this.editingDomain, this.callOperationAction, UMLPackage.eINSTANCE.getCallOperationAction_Operation(), this.operationSignatureFirst);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.callOperationAction);
		ICommand editCallOperationActionCommand = elementEditService.getEditCommand(request);
		if (!editCallOperationActionCommand.canExecute()) {
			Assert.fail("The CallBehaviorAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editCallOperationActionCommand));
		// Assert target
		Assert.assertTrue("CallOperationAction shall have a target", this.callOperationAction.getTarget() != null);
		Assert.assertTrue("CallOperationAction shall have a target and this target shall have a type", this.callOperationAction.getTarget().getType() != null);
		// Assert argument pins
		Assert.assertTrue("CallOperationAction shall have two input pins", this.callOperationAction.getArguments().size() == 2);
		Assert.assertTrue("CallOperationAction arguments pins shall be cohesive with operation parameters",
				PinDerivationAssertionUtils.assertArgumentsAndParametersCohesion(this.callOperationAction.getArguments(), this.operationSignatureFirst.getOwnedParameters()));
		// Assert result pins
		Assert.assertTrue("CallOperationAction shall have one result pin", this.callOperationAction.getResults().size() == 1);
		Assert.assertTrue("CallOperation result pins shall be cohesive with operation parameters", PinDerivationAssertionUtils.assertResultsAndParametersCohesion(this.callOperationAction.getResults(), this.operationSignatureFirst.getOwnedParameters()));
		if (!editCallOperationActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}

	/**
	 * Role:
	 * - For a CallOperationAction having already been synchronized with 'operationSignatureFirst' signature, ensure that if the 'operation'
	 * feature of the action evolves then pins are updated accordingly.
	 * 
	 * Scenario:
	 * - 1] CallOperationAction 'operation' feature is set with operation 'operationSignatureFirst'
	 * - 2] Synchronization is performed via the advice
	 * - CallOperationAction has now 4 pins
	 * - 1 target (typed by C)
	 * - 2 inputs ([in] p21, p22)
	 * - 1 output ([out] p21)
	 * - 3] CallOperationAction 'operation' feature is set with operation 'operationSignatureSecond'
	 * - 4] Synchronization is performed via the advice
	 * - CallOperationAction has now 6 pins
	 * - 1 target (typed by C)
	 * - 2 inputs (p11, [in] p12)
	 * - 3 outputs ([out] p12, p13, P14)
	 */
	@Test
	public void testPinDerivation_OperationWithParameters_To_OperationWithParameters() {
		// Initial modification of the model
		SetRequest request = new SetRequest(this.editingDomain, this.callOperationAction, UMLPackage.eINSTANCE.getCallOperationAction_Operation(), this.operationSignatureFirst);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.callOperationAction);
		ICommand firstEditCallOperationActionCommand = elementEditService.getEditCommand(request);
		if (!firstEditCallOperationActionCommand.canExecute()) {
			Assert.fail("The CallBehaviorAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(firstEditCallOperationActionCommand));
		// Set the 'operation' feature with another Operation
		request = new SetRequest(this.editingDomain, this.callOperationAction, UMLPackage.eINSTANCE.getCallOperationAction_Operation(), this.operationSignatureSecond);
		ICommand secondEditCallOperationActionCommand = elementEditService.getEditCommand(request);
		if (!secondEditCallOperationActionCommand.canExecute()) {
			Assert.fail("The CallBehaviorAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(secondEditCallOperationActionCommand));
		// Assert target
		Assert.assertTrue("CallOperationAction shall have a target", this.callOperationAction.getTarget() != null);
		Assert.assertTrue("CallOperationAction shall have a target and this target shall have a type", this.callOperationAction.getTarget().getType() != null);
		// Assert argument pins
		Assert.assertTrue("CallOperationAction shall have three input pins", this.callOperationAction.getArguments().size() == 2);
		Assert.assertTrue("CallOperationAction arguments pins shall be cohesive with operation parameters",
				PinDerivationAssertionUtils.assertArgumentsAndParametersCohesion(this.callOperationAction.getArguments(), this.operationSignatureSecond.getOwnedParameters()));
		// Assert result pins
		Assert.assertTrue("CallOperationAction shall have one result pin", this.callOperationAction.getResults().size() == 3);
		Assert.assertTrue("CallOperation result pins shall be cohesive with operation parameters", PinDerivationAssertionUtils.assertResultsAndParametersCohesion(this.callOperationAction.getResults(), this.operationSignatureSecond.getOwnedParameters()));
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(secondEditCallOperationActionCommand));
		if (!secondEditCallOperationActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
		if (!firstEditCallOperationActionCommand.canUndo()) {
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
		// Create first operation signature
		this.operationSignatureFirst = UMLFactory.eINSTANCE.createOperation();
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
		this.operationSignatureFirst.getOwnedParameters().add(p21);
		this.operationSignatureFirst.getOwnedParameters().add(p22);
		// Create second operation signature
		this.operationSignatureSecond = UMLFactory.eINSTANCE.createOperation();
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
		this.operationSignatureSecond.getOwnedParameters().add(p11);
		this.operationSignatureSecond.getOwnedParameters().add(p12);
		this.operationSignatureSecond.getOwnedParameters().add(p13);
		this.operationSignatureSecond.getOwnedParameters().add(p14);
		// Create class containing operation
		Class class_ = UMLFactory.eINSTANCE.createClass();
		class_.setName("C");
		class_.getOwnedOperations().add(this.operationSignatureFirst);
		class_.getOwnedOperations().add(this.operationSignatureSecond);
		// Create activity containing the call operation action
		Activity activity = UMLFactory.eINSTANCE.createActivity();
		this.callOperationAction = UMLFactory.eINSTANCE.createCallOperationAction();
		activity.getOwnedNodes().add(this.callOperationAction);
		// Add the different elements in the model
		this.umlTestModel.getPackagedElements().add(class_);
		this.umlTestModel.getPackagedElements().add(activity);
		// Add test model within the model set
		Resource umlTestModelResource = new ResourceImpl();
		umlTestModelResource.setURI(URI.createURI("UMLBaseTestModel.tmp"));
		umlTestModelResource.getContents().add(this.umlTestModel);
		this.modelSet.getResources().add(umlTestModelResource);
	}
}
