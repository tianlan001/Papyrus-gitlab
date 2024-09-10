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
 *   CEA LIST - Initial API and implementation
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
import org.eclipse.uml2.uml.AcceptCallAction;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * Test for	automated pin derivation for AcceptEventAction and AcceptCallAction
 *
 */
public class TestAcceptCallActionPinDerivation extends AbstractTestPinDerivation  {

	private static final String TARGET_ADVICE_IDENTIFIER = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.AcceptEventActionEditHelperAdvice";

	private static final String TARGET_ADVICE_IDENTIFIER_FOR_CALLEVENT = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.CallEventEditHelperAdvice";
	
	private static final String TARGET_ADVICE_IDENTIFIER_FOR_OPERATION = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.OperationEditHelperAdvice";
	
	private static final String TARGET_ADVICE_IDENTIFIER_FOR_PARAMETER = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.ParameterEditHelperAdvice";
	
	private AcceptCallAction acceptCallAction;
	
	private Trigger trigger;
	
	private Class clazz;
	private Operation operation;
	
	/**
	 * Constructor.
	 */
	public TestAcceptCallActionPinDerivation() {
		this.trigger = null;
		this.operation = null;
		this.populateBaseTestModel();
		// Set Automated Model Completion preference to PIN_SYNCHRONIZATION
		IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		prefStore.setValue(IAutomatedModelCompletionPreferencesConstants.ACCEPT_CALL_ACTION_ACCELERATOR, AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION);
	}
	
	/**
	 * Role:
	 * - Ensure that the advice for the AcceptCallAction is available
	 */
	@Test
	public void testAdviceForAcceptCallActionExists() {
		Assert.assertNotNull("AcceptCallAction advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER));
		Assert.assertNotNull("CallEvent advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER_FOR_CALLEVENT));
		Assert.assertNotNull("Operation advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER_FOR_OPERATION));
		Assert.assertNotNull("Parameter advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER_FOR_PARAMETER));
	}
	
	/**
	 * Role:
	 * - For a AcceptCallAction having no referenced trigger, ensure that if a trigger gets referenced then pins are inferred when isUnmarshall is false.
	 * 
	 * Scenario:
	 * - 1] AcceptCallAction 'trigger' feature is set with the 'trigger'
	 * - 2] Synchronization is performed via the advice
	 * - AcceptCallAction has now 2 result pin (as when isUnmarshall is true)
	 * - 1 for the parameter p1 of the operation (in)
	 * - 1 for the parameter p2 of the operation (inout)
	 * - AcceptCallAction has now 1 returnInformation pin 
	 */
	@Test
	public void testPinDerivation_IsUnmarshallFalse_SingleTrigger_CallEvent() {
		SetRequest request = new SetRequest(this.editingDomain, this.acceptCallAction, UMLPackage.eINSTANCE.getAcceptEventAction_Trigger(), this.trigger);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptCallAction);
		ICommand editAcceptCallActionCommand = elementEditService.getEditCommand(request);
		if (!editAcceptCallActionCommand.canExecute()) {
			Assert.fail("The AcceptCallAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editAcceptCallActionCommand));

		// Assert result derivation
		Assert.assertTrue("AcceptCallAction shall have 2 result pins", this.acceptCallAction.getResults().size() == 2);
		Assert.assertTrue("AcceptCallAction shall have a result pin typed by the type of the parameter p1", 
				this.acceptCallAction.getResults().get(0).getType() == this.getPrimitiveType("Integer"));
		Assert.assertTrue("AcceptCallAction shall have a result pin typed by the type of the parameter p2", 
				this.acceptCallAction.getResults().get(1).getType() == this.getPrimitiveType("Boolean"));
		//Assert return information pin
		Assert.assertTrue("AcceptCallAction shall have a returnInformation pin", this.acceptCallAction.getReturnInformation() != null);
		
		if (!editAcceptCallActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}
	
	/**
	 * Role:
	 * - For a AcceptCallAction having no referenced trigger, ensure that if a trigger gets referenced then pins are inferred when isUnmarshall is true.
	 * 
	 * Scenario:
	 * - 1] AcceptCallAction 'isUnmarshall' feature is set to true
	 * - 2] AcceptCallAction 'trigger' feature is set with the 'trigger'
	 * - 3] Synchronization is performed via the advice
	 * - AcceptCallAction has now 2 result pin 
	 * - 1 for the parameter p1 of the operation (in)
	 * - 1 for the parameter p2 of the operation (inout)
	 * - AcceptCallAction has now 1 returnInformation pin 
	 */
	@Test
	public void testPinDerivation_IsUnmarshallTrue_SimpleTrigger_CallEvent() {
		//Set isUnmarshall to true
		SetRequest isUnmarshallRequest = new SetRequest(this.editingDomain, this.acceptCallAction, UMLPackage.eINSTANCE.getAcceptEventAction_IsUnmarshall(), true); 
		IElementEditService isUnmarshallElementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptCallAction);
		ICommand isUnmarshallEditAcceptCallActionCommand = isUnmarshallElementEditService.getEditCommand(isUnmarshallRequest);
		if (!isUnmarshallEditAcceptCallActionCommand.canExecute()) {
			Assert.fail("The attribut isUnmarshall of AcceptCallAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(isUnmarshallEditAcceptCallActionCommand));
		
		//Set trigger
		SetRequest request = new SetRequest(this.editingDomain, this.acceptCallAction, UMLPackage.eINSTANCE.getAcceptEventAction_Trigger(), this.trigger);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptCallAction);
		ICommand editAcceptCallActionCommand = elementEditService.getEditCommand(request);
		if (!editAcceptCallActionCommand.canExecute()) {
			Assert.fail("The AcceptCallAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editAcceptCallActionCommand));
		
		// Assert result derivation
		Assert.assertTrue("AcceptCallAction shall have 2 result pins", this.acceptCallAction.getResults().size() == 2);
		Assert.assertTrue("AcceptCallAction shall have a result pin typed by the type of the parameter p1", 
				this.acceptCallAction.getResults().get(0).getType() == this.getPrimitiveType("Integer"));
		Assert.assertTrue("AcceptCallAction shall have a result pin typed by the type of the parameter p2", 
				this.acceptCallAction.getResults().get(1).getType() == this.getPrimitiveType("Boolean"));
		//Assert return information pin
		Assert.assertTrue("AcceptCallAction shall have a returnInformation pin", this.acceptCallAction.getReturnInformation() != null);
		
		if (!editAcceptCallActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}
	
	/**
	 * Role:
	 * - For a AcceptCallAction having trigger, ensure that if the operation associate to the callEvent change then pins are inferred when isUnmarshall is true.
	 * 
	 * Scenario:
	 * - 1] AcceptCallAction 'isUnmarshall' feature is set to true
	 * - 2] AcceptCallAction 'trigger' feature is set with the 'trigger'
	 * - 3] Synchronization is performed via the advice
	 * - AcceptCallAction has now 2
	 * - 1 for the property p1 of the operation (in)
	 * - 1 for the property p2 of the operation (inout)
	 * - 4] Change direction of the property 'p1' => out
	 * - AcceptCallAction has now 1
	 * - 1 for the property p2 of the operation (inout)
	 * 
	 */
	@Test
	public void testPinDerivation_IsUnmarshallTrue_SimpleTrigger_CallEvent_OperationChange() {
		//Set isUnmarshall to true
		SetRequest isUnmarshallRequest = new SetRequest(this.editingDomain, this.acceptCallAction, UMLPackage.eINSTANCE.getAcceptEventAction_IsUnmarshall(), true); 
		IElementEditService isUnmarshallElementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptCallAction);
		ICommand isUnmarshallEditAcceptCallActionCommand = isUnmarshallElementEditService.getEditCommand(isUnmarshallRequest);
		if (!isUnmarshallEditAcceptCallActionCommand.canExecute()) {
			Assert.fail("The attribut isUnmarshall of AcceptCallAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(isUnmarshallEditAcceptCallActionCommand));
		
		SetRequest request = new SetRequest(this.editingDomain, this.acceptCallAction, UMLPackage.eINSTANCE.getAcceptEventAction_Trigger(), this.trigger);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptCallAction);
		ICommand editAcceptCallActionCommand = elementEditService.getEditCommand(request);
		if (!editAcceptCallActionCommand.canExecute()) {
			Assert.fail("The AcceptCallAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editAcceptCallActionCommand));
		
		//Change direction of the property p1
		SetRequest changeSignalRequest = new SetRequest(this.editingDomain, this.operation.getOwnedParameter("p1", this.getPrimitiveType("Integer")), UMLPackage.eINSTANCE.getParameter_Direction(), ParameterDirectionKind.OUT_LITERAL); 
		IElementEditService changeSignalElementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptCallAction);
		ICommand changeSignalEditAcceptCallActionCommand = changeSignalElementEditService.getEditCommand(changeSignalRequest);
		if (!changeSignalEditAcceptCallActionCommand.canExecute()) {
			Assert.fail("The attribut isUnmarshall of AcceptCallAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(changeSignalEditAcceptCallActionCommand));
		
		// Assert result derivation
		Assert.assertTrue("AcceptCallAction shall have a result pin", this.acceptCallAction.getResults().size() == 1);
		Assert.assertTrue("AcceptCallAction shall have a result pin typed by the type of the parameter p2", 
				this.acceptCallAction.getResults().get(0).getType() == this.getPrimitiveType("Boolean"));
		if (!editAcceptCallActionCommand.canUndo()) {
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
		
		//Define the trigger
		this.trigger = UMLFactory.eINSTANCE.createTrigger();
		this.operation = UMLFactory.eINSTANCE.createOperation();
		Parameter p1 = this.operation.createOwnedParameter("p1", this.getPrimitiveType("Integer"));
		p1.setDirection(ParameterDirectionKind.IN_LITERAL);
		Parameter p2 = this.operation.createOwnedParameter("p2", this.getPrimitiveType("Boolean"));
		p2.setDirection(ParameterDirectionKind.INOUT_LITERAL);
		Parameter p3 = this.operation.createOwnedParameter("p3", this.getPrimitiveType("String"));
		p3.setDirection(ParameterDirectionKind.OUT_LITERAL);
		Parameter p4 = this.operation.createOwnedParameter("p4", this.getPrimitiveType("Boolean"));
		p4.setDirection(ParameterDirectionKind.RETURN_LITERAL);
		this.clazz = UMLFactory.eINSTANCE.createClass();
		this.clazz.getOwnedOperations().add(this.operation);
		CallEvent callEvent = UMLFactory.eINSTANCE.createCallEvent();
		callEvent.setOperation(this.operation);
		this.trigger.setEvent(callEvent);

		// Create the activity containing the accept event action
		Activity activity = UMLFactory.eINSTANCE.createActivity();
		this.acceptCallAction = UMLFactory.eINSTANCE.createAcceptCallAction();
		this.acceptCallAction.setIsUnmarshall(false);
		activity.getOwnedNodes().add(this.acceptCallAction);
		
		// Add the different elements to the model
		this.umlTestModel.getPackagedElements().add(activity);
		this.umlTestModel.getPackagedElements().add(this.clazz);
		
		// Add the test model within the model set
		Resource umlTestModelResource = new ResourceImpl();
		umlTestModelResource.setURI(URI.createURI("UMLBaseTestModel.tmp"));
		umlTestModelResource.getContents().add(this.umlTestModel);
		this.modelSet.getResources().add(umlTestModelResource);
	}
	
}
