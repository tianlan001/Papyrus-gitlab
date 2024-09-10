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
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.SendSignalAction;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;

public class TestSendSignalActionPinDerivation extends AbstractTestPinDerivation {

	/**
	 * CallBehaviorAction advice identifier
	 */
	private final String TARGET_ADVICE_IDENTIFIER = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.SendSignalActionEditHelperAdvice";

	/**
	 * SendSignalAction on which pin derivation is tested
	 */
	private SendSignalAction sendSignalAction;

	/**
	 * Signal specification N°1
	 */
	private Signal baseSignal;

	/**
	 * Signal specification N°2
	 */
	private Signal extendedSignal;

	/**
	 * Constructor.
	 */
	public TestSendSignalActionPinDerivation() {
		this.baseSignal = null;
		this.extendedSignal = null;
		this.populateBaseTestModel();
	}

	/**
	 * Role:
	 * - Ensure that the advice for the SendSignalAction is available
	 */
	@Test
	public void testAdviceForSendSignalActionExists() {
		Assert.assertNotNull("SendSignalAction advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER));
	}

	/**
	 * Role:
	 * - For a SendSignalAction having no referenced signal, ensure that if a signal gets referenced then target and argument pins
	 * are inferred.
	 * 
	 * Scenario:
	 * - 1] SendSignalAction 'signal' feature is set with the 'baseSignal'
	 * - 2] Synchronization is performed via the advice
	 * - SendSignalAction has now 3 pins
	 * - 1 target (of type C)
	 * - 2 argument pins (p1 and p2)
	 */
	@Test
	public void testPinDerivation_NoSignal_ToSignalWithAttributes() {
		SetRequest request = new SetRequest(this.editingDomain, this.sendSignalAction, UMLPackage.eINSTANCE.getSendSignalAction_Signal(), this.baseSignal);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.sendSignalAction);
		ICommand editSendSignalActionCommand = elementEditService.getEditCommand(request);
		if (!editSendSignalActionCommand.canExecute()) {
			Assert.fail("The SendSignalAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editSendSignalActionCommand));
		// Assert target derivation
		Assert.assertTrue("SendSignalAction shall have a target", this.sendSignalAction.getTarget() != null);
		Assert.assertTrue("SendSignalAction shall have a target with a type", this.sendSignalAction.getTarget().getType() != null);
		// Assert arguments derivation
		Assert.assertTrue("SendSignalAction shall have a two input pins", this.sendSignalAction.getArguments().size() == 2);
		Assert.assertTrue("SendSignalAction pins shall be cohesive with signal attributes", PinDerivationAssertionUtils.assertArgumentsAndAttributesCohesion(this.sendSignalAction.getArguments(), this.baseSignal.getAttributes()));
		if (!editSendSignalActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}

	/**
	 * Role:
	 * - For a SendSignalAction having already been synchronized with 'baseSignal' specification, ensure that if the the 'signal' feature
	 * of the action evolves then pins are updated accordingly
	 * 
	 * Scenario:
	 * - 1] SendSignalAction 'signal' feature is set with 'baseSignal'
	 * - 2] Synchronization is performed via the advice
	 * - SendSignalAction has now 3 pins
	 * - 1 target (of type C)
	 * - 2 argument pins (p1 and p2).
	 * - 3] SendSignalAction 'signal' feature is set with 'extendedSignal'
	 * - 4] Synchronization is performed via the advice
	 * - SendSignalAction has now 4 pins
	 * - 1 target (of type C)
	 * - 3 argument pins (p1, p2 and p3)
	 * 
	 * Note: along the synchronization process pins p1 and p2 are not deleted. This makes sure that user edges can be preserved.
	 */
	@Test
	public void testPinDerivation_SignalWithAttributes_To_SignalWithAttributes() {
		SetRequest request = new SetRequest(this.editingDomain, this.sendSignalAction, UMLPackage.eINSTANCE.getSendSignalAction_Signal(), this.baseSignal);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.sendSignalAction);
		ICommand firstEditSendSignalActionCommand = elementEditService.getEditCommand(request);
		if (!firstEditSendSignalActionCommand.canExecute()) {
			Assert.fail("The SendSignalAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(firstEditSendSignalActionCommand));
		request = new SetRequest(this.editingDomain, this.sendSignalAction, UMLPackage.eINSTANCE.getSendSignalAction_Signal(), this.extendedSignal);
		ICommand secondEditSendSignalActionCommand = elementEditService.getEditCommand(request);
		if (!secondEditSendSignalActionCommand.canExecute()) {
			Assert.fail("The SendSignalAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(secondEditSendSignalActionCommand));
		InputPin p1 = this.sendSignalAction.getArguments().get(0);
		InputPin p2 = this.sendSignalAction.getArguments().get(1);
		// Assert target derivation
		Assert.assertTrue("SendSignalAction shall have a target", this.sendSignalAction.getTarget() != null);
		Assert.assertTrue("SendSignalAction shall have a target with a type", this.sendSignalAction.getTarget().getType() != null);
		// Assert arguments derivation
		Assert.assertTrue("SendSignalAction shall have a two input pins", this.sendSignalAction.getArguments().size() == 3);
		Assert.assertTrue("SendSignalAction pins shall be cohesive with signal attributes", PinDerivationAssertionUtils.assertArgumentsAndAttributesCohesion(this.sendSignalAction.getArguments(), this.extendedSignal.getAllAttributes()));
		// Assert pin preservation
		Assert.assertTrue("Pin p1 shall be preserved", p1 == this.sendSignalAction.getArguments().get(0));
		Assert.assertTrue("Pin p2 shall be preserved", p2 == this.sendSignalAction.getArguments().get(1));
		if (!secondEditSendSignalActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
		if (!firstEditSendSignalActionCommand.canUndo()) {
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
		// Define base signal
		this.baseSignal = UMLFactory.eINSTANCE.createSignal();
		Property p1 = UMLFactory.eINSTANCE.createProperty();
		p1.setName("p1");
		p1.setUpper(1);
		p1.setLower(1);
		p1.setType(this.getPrimitiveType("Integer"));
		Property p2 = UMLFactory.eINSTANCE.createProperty();
		p2.setName("p2");
		p2.setUpper(1);
		p2.setLower(1);
		p2.setType(this.getPrimitiveType("Boolean"));
		this.baseSignal.getOwnedAttributes().add(p1);
		this.baseSignal.getOwnedAttributes().add(p2);
		// Define extended signal
		this.extendedSignal = UMLFactory.eINSTANCE.createSignal();
		Property p3 = UMLFactory.eINSTANCE.createProperty();
		p3.setName("p3");
		p3.setUpper(1);
		p3.setLower(-1);
		p3.setType(this.getPrimitiveType("String"));
		this.extendedSignal.getOwnedAttributes().add(p3);
		// Create generalization between the two
		Generalization generalization = UMLFactory.eINSTANCE.createGeneralization();
		generalization.setGeneral(this.baseSignal);
		generalization.setSpecific(this.extendedSignal);
		this.extendedSignal.getGeneralizations().add(generalization);
		// Create class with a port
		Class class_ = UMLFactory.eINSTANCE.createClass();
		class_.setName("C");
		Port port = UMLFactory.eINSTANCE.createPort();
		class_.getOwnedPorts().add(port);
		// Create the activity containing the send signal action
		Activity activity = UMLFactory.eINSTANCE.createActivity();
		this.sendSignalAction = UMLFactory.eINSTANCE.createSendSignalAction();
		this.sendSignalAction.setOnPort(port);
		activity.getOwnedNodes().add(this.sendSignalAction);
		// Add the different elements to the model
		this.umlTestModel.getPackagedElements().add(class_);
		this.umlTestModel.getPackagedElements().add(activity);
		this.umlTestModel.getPackagedElements().add(this.baseSignal);
		this.umlTestModel.getPackagedElements().add(this.extendedSignal);
		// Add the test model within the model set
		Resource umlTestModelResource = new ResourceImpl();
		umlTestModelResource.setURI(URI.createURI("UMLBaseTestModel.tmp"));
		umlTestModelResource.getContents().add(this.umlTestModel);
		this.modelSet.getResources().add(umlTestModelResource);
	}
}
