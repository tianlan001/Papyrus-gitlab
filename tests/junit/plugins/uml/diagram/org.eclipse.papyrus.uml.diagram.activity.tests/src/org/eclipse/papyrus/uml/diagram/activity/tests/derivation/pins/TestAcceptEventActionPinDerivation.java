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

import java.util.ArrayList;
import java.util.List;

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
import org.eclipse.uml2.uml.AcceptEventAction;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.ChangeEvent;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.SignalEvent;
import org.eclipse.uml2.uml.TimeEvent;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * Test for automated pin derivation for AcceptEventAction and AcceptCallAction
 *
 */
public class TestAcceptEventActionPinDerivation extends AbstractTestPinDerivation {

	private final String TARGET_ADVICE_IDENTIFIER = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.AcceptEventActionEditHelperAdvice";

	private final String TARGET_ADVICE_IDENTIFIER_FOR_SIGNAL = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.SignalEditHelperAdvice";

	private final String TARGET_ADVICE_IDENTIFIER_FOR_SIGNALEVENT = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.SignalEventEditHelperAdvice";

	private final String TARGET_ADVICE_IDENTIFIER_FOR_PROPERTY = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.PropertyEditHelperAdvice";

	private AcceptEventAction acceptEventAction;

	private Trigger baseTrigger;

	private Signal baseSignal;

	private Trigger extendedTrigger1;

	private Signal extendedSignal1;

	private Trigger extendedTrigger2;

	private Signal extendedSignal2;

	private Trigger trigger3;

	private Signal Signal3;

	private Trigger triggerTimerEvent;

	private Trigger triggerChangeEvent;

	private Trigger triggerCallEvent;

	/**
	 * Constructor.
	 */
	public TestAcceptEventActionPinDerivation() {
		this.populateBaseTestModel();
		// Set Automated Model Completion preference to PIN_SYNCHRONIZATION
		IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		prefStore.setValue(IAutomatedModelCompletionPreferencesConstants.ACCEPTE_EVENT_ACTION_ACCELERATOR, AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION);
	}

	/**
	 * Role:
	 * - Ensure that the advice for the AcceptEventAction is available
	 */
	@Test
	public void testAdviceForAcceptEventActionExists() {
		Assert.assertNotNull("AcceptEventAction advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER));
		Assert.assertNotNull("SignalEvent advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER_FOR_SIGNALEVENT));
		Assert.assertNotNull("Signal advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER_FOR_SIGNAL));
		Assert.assertNotNull("Property advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER_FOR_PROPERTY));
	}

	/**
	 * Role:
	 * - For a AcceptEventAction having already been synchronized with 'baseTrigger', ensure that if the isUnmarshall property is set to true
	 * the action evolves then pins are updated accordingly.
	 * 
	 * Scenario:
	 * - 1] AcceptEventAction 'trigger' feature is set with the 'baseTrigger'
	 * - 2] Synchronization is performed via the advice
	 * - AcceptEventAction has now 1 pin typed by the signal
	 * - 3] AcceptEventAction 'isUmnarshall' feature is set to true
	 * - AcceptEventAction has now 2 pin
	 * - 1 for the property p1 of baseSignal type with integer (type of p1)
	 * - 1 for the property p2 of baseSignal type with boolean (type of p2)
	 */
	@Test
	public void testPinDerivation_IsUnmarshallFalse_ToIsUnmarshallTrue() {
		// Assign baseTrigger to acceptEventAction
		SetRequest request = new SetRequest(this.editingDomain, this.acceptEventAction, UMLPackage.eINSTANCE.getAcceptEventAction_Trigger(), this.baseTrigger);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand editAcceptEventActionCommand = elementEditService.getEditCommand(request);
		if (!editAcceptEventActionCommand.canExecute()) {
			Assert.fail("The AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editAcceptEventActionCommand));

		// Set isUnmarshall to true
		SetRequest isUnmarshallRequest = new SetRequest(this.editingDomain, this.acceptEventAction, UMLPackage.eINSTANCE.getAcceptEventAction_IsUnmarshall(), true);
		IElementEditService isUnmarshallElementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand isUnmarshallEditAcceptEventActionCommand = isUnmarshallElementEditService.getEditCommand(isUnmarshallRequest);
		if (!isUnmarshallEditAcceptEventActionCommand.canExecute()) {
			Assert.fail("The attribut isUnmarshall of AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(isUnmarshallEditAcceptEventActionCommand));

		// Assert result derivation
		Assert.assertTrue("AcceptEventAction shall have 2 result pins", this.acceptEventAction.getResults().size() == 2);
		Assert.assertTrue("AcceptEventAction shall have a result pin typed by the type of the property p1",
				this.acceptEventAction.getResults().get(0).getType() == this.getPrimitiveType("Integer"));
		Assert.assertTrue("AcceptEventAction shall have a result pin typed by the type of the property p2",
				this.acceptEventAction.getResults().get(1).getType() == this.getPrimitiveType("Boolean"));
		if (!editAcceptEventActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}

	/**
	 * Role:
	 * - For a AcceptEventAction having no referenced trigger, ensure that if a trigger gets referenced then pins are inferred when isUnmarshall is false.
	 * 
	 * Scenario:
	 * - 1] AcceptEventAction 'trigger' feature is set with the 'baseTrigger'
	 * - 2] Synchronization is performed via the advice
	 * - AcceptEventAction has now 1 pin typed by the signal
	 */
	@Test
	public void testPinDerivation_IsUnmarshallFalse_SingleTrigger_SignalEvent() {
		SetRequest request = new SetRequest(this.editingDomain, this.acceptEventAction, UMLPackage.eINSTANCE.getAcceptEventAction_Trigger(), this.baseTrigger);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand editAcceptEventActionCommand = elementEditService.getEditCommand(request);
		if (!editAcceptEventActionCommand.canExecute()) {
			Assert.fail("The AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editAcceptEventActionCommand));
		// Assert result derivation
		Assert.assertTrue("AcceptEventAction shall have a result pin", this.acceptEventAction.getResults().size() == 1);
		Assert.assertTrue("AcceptEventAction shall have a result pin typed by the baseSignal", this.acceptEventAction.getResults().get(0).getType() == baseSignal);

		if (!editAcceptEventActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}

	/**
	 * Role:
	 * - For a AcceptEventAction having no referenced trigger, ensure that if a trigger gets referenced then pins are inferred when isUnmarshall is false.
	 * 
	 * Scenario:
	 * - 1] AcceptEventAction 'trigger' feature is set with the 'TriggerTimeEvent'
	 * - 2] Synchronization is performed via the advice
	 * - AcceptEventAction has now 1 pin typed by Real
	 */
	@Test
	public void testPinDerivation_IsUnmarshallFalse_SingleTrigger_TimeEvent() {
		SetRequest request = new SetRequest(this.editingDomain, this.acceptEventAction, UMLPackage.eINSTANCE.getAcceptEventAction_Trigger(), this.triggerTimerEvent);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand editAcceptEventActionCommand = elementEditService.getEditCommand(request);
		if (!editAcceptEventActionCommand.canExecute()) {
			Assert.fail("The AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editAcceptEventActionCommand));
		// Assert result derivation
		Assert.assertTrue("AcceptEventAction shall have a result pin", this.acceptEventAction.getResults().size() == 1);
		Assert.assertTrue("AcceptEventAction shall have a result pin typed by the real", this.acceptEventAction.getResults().get(0).getType() == this.getPrimitiveType("Real"));

		if (!editAcceptEventActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}

	/**
	 * Role:
	 * - For a AcceptEventAction having no referenced trigger, ensure that if a trigger gets referenced then pins are inferred when isUnmarshall is false.
	 * 
	 * Scenario:
	 * - 1] AcceptEventAction 'trigger' feature is set with the 'TriggerChangeEvent'
	 * - 2] Synchronization is performed via the advice
	 * - AcceptEventAction has now 0 pin
	 */
	@Test
	public void testPinDerivation_IsUnmarshallFalse_SingleTrigger_ChangeEvent() {
		SetRequest request = new SetRequest(this.editingDomain, this.acceptEventAction, UMLPackage.eINSTANCE.getAcceptEventAction_Trigger(), this.triggerChangeEvent);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand editAcceptEventActionCommand = elementEditService.getEditCommand(request);
		if (!editAcceptEventActionCommand.canExecute()) {
			Assert.fail("The AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editAcceptEventActionCommand));
		// Assert result derivation
		Assert.assertTrue("AcceptEventAction shall have no result pin", this.acceptEventAction.getResults().size() == 0);

		if (!editAcceptEventActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}

	/**
	 * Role:
	 * - For a AcceptEventAction having no referenced trigger, ensure that if a trigger gets referenced then pins are inferred when isUnmarshall is false.
	 * 
	 * Scenario:
	 * - 1] AcceptEventAction 'trigger' feature is set with the 'TriggerCallEvent'
	 * - 2] Synchronization is performed via the advice
	 * - AcceptEventAction has now 0 pin
	 */
	@Test
	public void testPinDerivation_IsUnmarshallFalse_SingleTrigger_CallEvent() {
		SetRequest request = new SetRequest(this.editingDomain, this.acceptEventAction, UMLPackage.eINSTANCE.getAcceptEventAction_Trigger(), this.triggerCallEvent);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand editAcceptEventActionCommand = elementEditService.getEditCommand(request);
		if (!editAcceptEventActionCommand.canExecute()) {
			Assert.fail("The AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editAcceptEventActionCommand));
		// Assert result derivation
		Assert.assertTrue("AcceptEventAction shall have no result pin", this.acceptEventAction.getResults().size() == 0);

		if (!editAcceptEventActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}

	/**
	 * Role:
	 * - For a AcceptEventAction having already been synchronized with 'extendedTrigger1' specification,
	 * ensure that if a new trigger is add pin evolves accordingly
	 * 
	 * Scenario:
	 * - 1] AcceptEventAction 'trigger' feature is set with 'extendedTrigger1'
	 * - 2] Synchronization is performed via the advice
	 * - AcceptEventAction has now 1 pin
	 * - 1 pin typed by the extendedSignal1
	 * - 3] AcceptEventAction 'trigger' feature is set with 'extendedTrigger1' and 'extendedTrigger2'
	 * - 4] Synchronization is performed via the advice
	 * - AcceptEventAction has now 1 pin
	 * - 1 pin typed by the baseSignal (common ancestor)
	 * 
	 */
	@Test
	public void testPinDerivation_IsUnmarshallFalse_MultipleTrigger_SignalEvent_CommonAncestor() {
		// Assign extendedTrigger1 to acceptEventAction
		SetRequest request = new SetRequest(this.editingDomain, this.acceptEventAction, UMLPackage.eINSTANCE.getAcceptEventAction_Trigger(), this.extendedTrigger1);
		IElementEditService baseElementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand baseEditAcceptEventActionCommand = baseElementEditService.getEditCommand(request);
		if (!baseEditAcceptEventActionCommand.canExecute()) {
			Assert.fail("The AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(baseEditAcceptEventActionCommand));

		// Assign extendedTrigger1 to acceptEventAction
		List<Trigger> triggers = new ArrayList<Trigger>();
		triggers.add(this.extendedTrigger1);
		triggers.add(this.extendedTrigger2);
		request = new SetRequest(this.editingDomain, this.acceptEventAction, UMLPackage.eINSTANCE.getAcceptEventAction_Trigger(), triggers);
		IElementEditService extendedElementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand extendedEditAcceptEventActionCommand = extendedElementEditService.getEditCommand(request);
		if (!extendedEditAcceptEventActionCommand.canExecute()) {
			Assert.fail("The AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(extendedEditAcceptEventActionCommand));

		// Assert result derivation
		Assert.assertTrue("AcceptEventAction shall have a result pin", this.acceptEventAction.getResults().size() == 1);
		Assert.assertTrue("AcceptEventAction shall have a result pin typed by BaseSignal",
				this.acceptEventAction.getResults().get(0).getType() == this.baseSignal);
		if (!baseEditAcceptEventActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}

	/**
	 * Role:
	 * - For a AcceptEventAction having already been synchronized with 'extendedTrigger1' specification,
	 * ensure that if a new trigger is add pin evolves accordingly
	 * 
	 * Scenario:
	 * - 1] AcceptEventAction 'trigger' feature is set with 'extendedTrigger1'
	 * - 2] Synchronization is performed via the advice
	 * - AcceptEventAction has now 1 pin
	 * - 1 pin typed by the extendedSignal1
	 * - 3] AcceptEventAction 'trigger' feature is set with 'extendedTrigger1' and 'triggerTimerEvent'
	 * - 4] Synchronization is performed via the advice
	 * - AcceptEventAction has now 0 pin
	 * 
	 */
	@Test
	public void testPinDerivation_IsUnmarshallFalse_MultipleTrigger_MixedEvents() {
		// Assign extendedTrigger1 to acceptEventAction
		SetRequest request = new SetRequest(this.editingDomain, this.acceptEventAction, UMLPackage.eINSTANCE.getAcceptEventAction_Trigger(), this.extendedTrigger1);
		IElementEditService baseElementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand baseEditAcceptEventActionCommand = baseElementEditService.getEditCommand(request);
		if (!baseEditAcceptEventActionCommand.canExecute()) {
			Assert.fail("The AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(baseEditAcceptEventActionCommand));

		// Assign extendedTrigger1 to acceptEventAction
		request = new SetRequest(this.editingDomain, this.acceptEventAction, UMLPackage.eINSTANCE.getAcceptEventAction_Trigger(), triggerTimerEvent);
		IElementEditService extendedElementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand extendedEditAcceptEventActionCommand = extendedElementEditService.getEditCommand(request);
		if (!extendedEditAcceptEventActionCommand.canExecute()) {
			Assert.fail("The AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(extendedEditAcceptEventActionCommand));

		// Assert result derivation
		Assert.assertTrue("AcceptEventAction shall have a result pin", this.acceptEventAction.getResults().size() == 1);
		Assert.assertTrue("AcceptEventAction shall have a result not type",
				this.acceptEventAction.getResults().get(0).getType() == null);
		if (!baseEditAcceptEventActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}

	/**
	 * Role:
	 * - For a AcceptEventAction having no referenced trigger, ensure that if a trigger gets referenced then pins are inferred when isUnmarshall is true.
	 * 
	 * Scenario:
	 * - 1] AcceptEventAction 'isUmnarshall' feature is set to true
	 * - 2] AcceptEventAction 'trigger' feature is set with the 'baseTrigger'
	 * - 3] Synchronization is performed via the advice
	 * - AcceptEventAction has now 2 pin
	 * - 1 for the property p1 of baseSignal type with integer (type of p1)
	 * - 1 for the property p2 of baseSignal type with boolean (type of p2)
	 */
	@Test
	public void testPinDerivation_IsUnmarshallTrue_SingleTrigger_SignalEvent() {
		// Set isUnmarshall to true
		SetRequest isUnmarshallRequest = new SetRequest(this.editingDomain, this.acceptEventAction, UMLPackage.eINSTANCE.getAcceptEventAction_IsUnmarshall(), true);
		IElementEditService isUnmarshallElementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand isUnmarshallEditAcceptEventActionCommand = isUnmarshallElementEditService.getEditCommand(isUnmarshallRequest);
		if (!isUnmarshallEditAcceptEventActionCommand.canExecute()) {
			Assert.fail("The attribut isUnmarshall of AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(isUnmarshallEditAcceptEventActionCommand));

		// Assign baseTrigger to acceptEventAction
		SetRequest request = new SetRequest(this.editingDomain, this.acceptEventAction, UMLPackage.eINSTANCE.getAcceptEventAction_Trigger(), this.baseTrigger);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand editAcceptEventActionCommand = elementEditService.getEditCommand(request);
		if (!editAcceptEventActionCommand.canExecute()) {
			Assert.fail("The AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editAcceptEventActionCommand));

		// Assert result derivation
		Assert.assertTrue("AcceptEventAction shall have 2 result pins", this.acceptEventAction.getResults().size() == 2);
		Assert.assertTrue("AcceptEventAction shall have a result pin typed by the type of the property p1",
				this.acceptEventAction.getResults().get(0).getType() == this.getPrimitiveType("Integer"));
		Assert.assertTrue("AcceptEventAction shall have a result pin typed by the type of the property p2",
				this.acceptEventAction.getResults().get(1).getType() == this.getPrimitiveType("Boolean"));
		if (!editAcceptEventActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}

	/**
	 * Role:
	 * - For a AcceptEventAction having no referenced trigger, ensure that if a trigger gets referenced then pins are inferred when isUnmarshall is true.
	 * 
	 * Scenario:
	 * - 1] AcceptEventAction 'isUmnarshall' feature is set to true
	 * - 2] AcceptEventAction 'trigger' feature is set with the 'baseTrigger'
	 * - 3] Synchronization is performed via the advice
	 * - AcceptEventAction has now 1 pin
	 * - 1 pin type by real
	 */
	@Test
	public void testPinDerivation_IsUnmarshallTrue_SingleTrigger_TimeEvent() {
		// Set isUnmarshall to true
		SetRequest isUnmarshallRequest = new SetRequest(this.editingDomain, this.acceptEventAction, UMLPackage.eINSTANCE.getAcceptEventAction_IsUnmarshall(), true);
		IElementEditService isUnmarshallElementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand isUnmarshallEditAcceptEventActionCommand = isUnmarshallElementEditService.getEditCommand(isUnmarshallRequest);
		if (!isUnmarshallEditAcceptEventActionCommand.canExecute()) {
			Assert.fail("The attribut isUnmarshall of AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(isUnmarshallEditAcceptEventActionCommand));

		// Assign baseTrigger to acceptEventAction
		SetRequest request = new SetRequest(this.editingDomain, this.acceptEventAction, UMLPackage.eINSTANCE.getAcceptEventAction_Trigger(), this.triggerTimerEvent);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand editAcceptEventActionCommand = elementEditService.getEditCommand(request);
		if (!editAcceptEventActionCommand.canExecute()) {
			Assert.fail("The AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editAcceptEventActionCommand));

		// Assert result derivation
		Assert.assertTrue("AcceptEventAction shall have a result pins", this.acceptEventAction.getResults().size() == 1);
		Assert.assertTrue("AcceptEventAction shall have a result pin typed by real",
				this.acceptEventAction.getResults().get(0).getType() == this.getPrimitiveType("Real"));
		if (!editAcceptEventActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}

	/**
	 * Role:
	 * - For a AcceptEventAction having already been synchronized with 'baseTrigger' specification, ensure that if the the 'trigger' feature
	 * of the action evolves then pins are updated accordingly
	 * 
	 * Scenario:
	 * - 1] AcceptEventAction 'trigger' feature is set with 'extendedTrigger1'
	 * - 2] Synchronization is performed via the advice
	 * - AcceptEventAction has now 3 pins
	 * - 3 argument pins (p1, p2 and p3).
	 * - 3] AcceptEventAction 'trigger' feature is set an other trigger 'extendedTrigger2'
	 * - 4] Synchronization is performed via the advice
	 * - AcceptEventAction has now 2 pins
	 * - 2 pins for parameter of the common signal (baseSignal)
	 * 
	 * Note: along the synchronization process pins p1 and p2 are not deleted. This makes sure that user edges can be preserved.
	 */
	@Test
	public void testPinDerivation_IsUnmarshallTrue_MultipleTrigger_SignalEvent_CommonAncestor() {
		// Set isUnmarshall to true
		SetRequest isUnmarshallRequest = new SetRequest(this.editingDomain, this.acceptEventAction, UMLPackage.eINSTANCE.getAcceptEventAction_IsUnmarshall(), true);
		IElementEditService isUnmarshallElementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand isUnmarshallEditAcceptEventActionCommand = isUnmarshallElementEditService.getEditCommand(isUnmarshallRequest);
		if (!isUnmarshallEditAcceptEventActionCommand.canExecute()) {
			Assert.fail("The attribut isUnmarshall of AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(isUnmarshallEditAcceptEventActionCommand));

		// Assign extendedTrigger1 to acceptEventAction
		SetRequest request = new SetRequest(this.editingDomain, this.acceptEventAction, UMLPackage.eINSTANCE.getAcceptEventAction_Trigger(), this.extendedTrigger1);
		IElementEditService baseElementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand baseEditAcceptEventActionCommand = baseElementEditService.getEditCommand(request);
		if (!baseEditAcceptEventActionCommand.canExecute()) {
			Assert.fail("The AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(baseEditAcceptEventActionCommand));

		// Assign extendedTrigger2 to acceptEventAction
		request = new SetRequest(this.editingDomain, this.acceptEventAction, UMLPackage.eINSTANCE.getAcceptEventAction_Trigger(), this.extendedTrigger2);
		IElementEditService extendedElementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand extendedEditAcceptEventActionCommand = extendedElementEditService.getEditCommand(request);
		if (!extendedEditAcceptEventActionCommand.canExecute()) {
			Assert.fail("The AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(extendedEditAcceptEventActionCommand));

		// Assert result derivation
		Assert.assertTrue("AcceptEventAction shall have 2 result pins", this.acceptEventAction.getResults().size() == 2);
		Assert.assertTrue("AcceptEventAction shall have a result pin typed by the type of the property p1",
				this.acceptEventAction.getResults().get(0).getType() == this.getPrimitiveType("Integer"));
		Assert.assertTrue("AcceptEventAction shall have a result pin typed by the type of the property p2",
				this.acceptEventAction.getResults().get(1).getType() == this.getPrimitiveType("Boolean"));
		if (!baseEditAcceptEventActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}

	/**
	 * Role:
	 * - For a AcceptEventAction having already been synchronized with 'baseTrigger' specification, ensure that if the the 'trigger' feature
	 * of the action evolves then pins are updated accordingly
	 * 
	 * Scenario:
	 * - 1] AcceptEventAction 'trigger' feature is set with 'extendedTrigger1'
	 * - 2] Synchronization is performed via the advice
	 * - AcceptEventAction has now 3 pins
	 * - 3 argument pins (p1, p2 and p3).
	 * - 3] AcceptEventAction 'trigger' feature is set an other trigger 'extendedTrigger2'
	 * - 4] Synchronization is performed via the advice
	 * - AcceptEventAction has now 0 pins because there is no common ancestor
	 * 
	 * Note: along the synchronization process pins p1 and p2 are not deleted. This makes sure that user edges can be preserved.
	 */
	@Test
	public void testPinDerivation_IsUnmarshallTrue_MultipleTrigger_SignalEvent_NoCommonAncestor() {
		// Set isUnmarshall to true
		SetRequest isUnmarshallRequest = new SetRequest(this.editingDomain, this.acceptEventAction, UMLPackage.eINSTANCE.getAcceptEventAction_IsUnmarshall(), true);
		IElementEditService isUnmarshallElementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand isUnmarshallEditAcceptEventActionCommand = isUnmarshallElementEditService.getEditCommand(isUnmarshallRequest);
		if (!isUnmarshallEditAcceptEventActionCommand.canExecute()) {
			Assert.fail("The attribut isUnmarshall of AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(isUnmarshallEditAcceptEventActionCommand));

		// Assign extendedTrigger1 to acceptEventAction
		SetRequest request = new SetRequest(this.editingDomain, this.acceptEventAction, UMLPackage.eINSTANCE.getAcceptEventAction_Trigger(), this.extendedTrigger1);
		IElementEditService baseElementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand baseEditAcceptEventActionCommand = baseElementEditService.getEditCommand(request);
		if (!baseEditAcceptEventActionCommand.canExecute()) {
			Assert.fail("The AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(baseEditAcceptEventActionCommand));

		// Assign trigger3 to acceptEventAction
		request = new SetRequest(this.editingDomain, this.acceptEventAction, UMLPackage.eINSTANCE.getAcceptEventAction_Trigger(), this.trigger3);
		IElementEditService extendedElementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand extendedEditAcceptEventActionCommand = extendedElementEditService.getEditCommand(request);
		if (!extendedEditAcceptEventActionCommand.canExecute()) {
			Assert.fail("The AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(extendedEditAcceptEventActionCommand));

		// Assert result derivation
		Assert.assertTrue("AcceptEventAction shall have no result pins", this.acceptEventAction.getResults().size() == 0);

		if (!baseEditAcceptEventActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}

	/**
	 * Role:
	 * - For a AcceptEventAction having already been synchronized with 'extendedTrigger1' specification,
	 * ensure that if a new trigger is add pin evolves accordingly
	 * 
	 * Scenario:
	 * - 1] AcceptEventAction 'trigger' feature is set with 'extendedTrigger1'
	 * - 2] Synchronization is performed via the advice
	 * - AcceptEventAction has now 1 pin
	 * - 1 pin typed by the extendedSignal1
	 * - 3] Ass a trigger associate to a timeEvent
	 * - 4] Synchronization is performed via the advice
	 * - AcceptEventAction has now 1 pin
	 * - 1 pin no type
	 * 
	 */
	@Test
	public void testPinDerivation_IsUnmarshallTrue_MultipleTrigger_TimeEvent() {
		// Set isUnmarshall to true
		SetRequest isUnmarshallRequest = new SetRequest(this.editingDomain, this.acceptEventAction, UMLPackage.eINSTANCE.getAcceptEventAction_IsUnmarshall(), true);
		IElementEditService isUnmarshallElementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand isUnmarshallEditAcceptEventActionCommand = isUnmarshallElementEditService.getEditCommand(isUnmarshallRequest);
		if (!isUnmarshallEditAcceptEventActionCommand.canExecute()) {
			Assert.fail("The attribut isUnmarshall of AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(isUnmarshallEditAcceptEventActionCommand));

		// Assign triggerTimerEvent
		SetRequest request = new SetRequest(this.editingDomain, this.acceptEventAction, UMLPackage.eINSTANCE.getAcceptEventAction_Trigger(), this.triggerTimerEvent);
		IElementEditService baseElementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand baseEditAcceptEventActionCommand = baseElementEditService.getEditCommand(request);
		if (!baseEditAcceptEventActionCommand.canExecute()) {
			Assert.fail("The AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(baseEditAcceptEventActionCommand));

		// Add a trigger
		Trigger trigger = UMLFactory.eINSTANCE.createTrigger();
		trigger.setEvent(UMLFactory.eINSTANCE.createTimeEvent());
		request = new SetRequest(this.editingDomain, this.acceptEventAction, UMLPackage.eINSTANCE.getAcceptEventAction_Trigger(), trigger);
		IElementEditService extendedElementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand extendedEditAcceptEventActionCommand = extendedElementEditService.getEditCommand(request);
		if (!extendedEditAcceptEventActionCommand.canExecute()) {
			Assert.fail("The AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(extendedEditAcceptEventActionCommand));

		// Assert result derivation
		Assert.assertTrue("AcceptEventAction shall have a result pin", this.acceptEventAction.getResults().size() == 1);
		Assert.assertTrue("AcceptEventAction shall have a result pin no type",
				this.acceptEventAction.getResults().get(0).getType() == null);
		if (!baseEditAcceptEventActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}

	/**
	 * Role:
	 * - For a AcceptEventAction having triggers, ensure that if the SignalEvent associate to a trigger changes then pins are inferred when is unmarshall is true.
	 * 
	 * Scenario:
	 * - 1] AcceptEventAction 'isUmnarshall' feature is set to true
	 * - 2] AcceptEventAction 'trigger' feature is set with the 'baseTrigger'
	 * - 3] Synchronization is performed via the advice
	 * - AcceptEventAction has now 2 pin
	 * - 1 for the property p1 of baseSignal type with integer (type of p1)
	 * - 1 for the property p2 of baseSignal type with boolean (type of p2)
	 * - 3] Add attribute (String) to the signal 'baseSignal'
	 * - AcceptEventAction has now 3 pin
	 * - 1 for the property p1 of baseSignal type with integer (type of p1)
	 * - 1 for the property p2 of baseSignal type with boolean (type of p2)
	 * - 1 for the property p2 of baseSignal type with string
	 * 
	 */
	@Test
	public void testPinDerivation_IsUnmarshallTrue_SingleTrigger_SignalEvent_SignalChange() {
		// Set isUnmarshall to true
		SetRequest isUnmarshallRequest = new SetRequest(this.editingDomain, this.acceptEventAction, UMLPackage.eINSTANCE.getAcceptEventAction_IsUnmarshall(), true);
		IElementEditService isUnmarshallElementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand isUnmarshallEditAcceptEventActionCommand = isUnmarshallElementEditService.getEditCommand(isUnmarshallRequest);
		if (!isUnmarshallEditAcceptEventActionCommand.canExecute()) {
			Assert.fail("The attribut isUnmarshall of AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(isUnmarshallEditAcceptEventActionCommand));

		// Assign baseTrigger to acceptEventAction
		SetRequest request = new SetRequest(this.editingDomain, this.acceptEventAction, UMLPackage.eINSTANCE.getAcceptEventAction_Trigger(), this.baseTrigger);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand editAcceptEventActionCommand = elementEditService.getEditCommand(request);
		if (!editAcceptEventActionCommand.canExecute()) {
			Assert.fail("The AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editAcceptEventActionCommand));

		// Add attribute to baseSignal
		Property p4 = UMLFactory.eINSTANCE.createProperty();
		p4.setName("p4");
		p4.setUpper(1);
		p4.setLower(-1);
		p4.setType(this.getPrimitiveType("String"));
		SetRequest changeSignalRequest = new SetRequest(this.editingDomain, this.baseSignal, UMLPackage.eINSTANCE.getSignal_OwnedAttribute(), p4);
		IElementEditService changeSignalElementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand changeSignalEditAcceptEventActionCommand = changeSignalElementEditService.getEditCommand(changeSignalRequest);
		if (!changeSignalEditAcceptEventActionCommand.canExecute()) {
			Assert.fail("The attribut isUnmarshall of AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(changeSignalEditAcceptEventActionCommand));

		// Assert result derivation
		Assert.assertTrue("AcceptEventAction shall have 3 result pins", this.acceptEventAction.getResults().size() == 3);
		Assert.assertTrue("AcceptEventAction shall have a result pin typed by the type of the property p1",
				this.acceptEventAction.getResults().get(0).getType() == this.getPrimitiveType("Integer"));
		Assert.assertTrue("AcceptEventAction shall have a result pin typed by the type of the property p2",
				this.acceptEventAction.getResults().get(1).getType() == this.getPrimitiveType("Boolean"));
		Assert.assertTrue("AcceptEventAction shall have a result pin typed by the type of the property p4",
				this.acceptEventAction.getResults().get(2).getType() == this.getPrimitiveType("String"));
		if (!editAcceptEventActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}

	/**
	 * Role:
	 * - For a AcceptEventAction having already been synchronized with 'extendedTrigger1' specification,
	 * ensure that if a new trigger is add pin evolves accordingly
	 * 
	 * Scenario:
	 * - 1] AcceptEventAction 'isUmnarshall' feature is set to true
	 * - 2] AcceptEventAction 'trigger' feature is set with 'extendedTrigger1'
	 * - 3] Synchronization is performed via the advice
	 * - AcceptEventAction has now 1 pin
	 * - 1 pin typed by the extendedSignal1
	 * - 4] AcceptEventAction 'trigger' feature is set with 'extendedTrigger1' and 'triggerTimerEvent'
	 * - 5] Synchronization is performed via the advice
	 * - AcceptEventAction has now 0 pin
	 * 
	 */
	@Test
	public void testPinDerivation_IsUnmarshallTrue_MultipleTrigger_MixedEvents() {
		// Set isUnmarshall to true
		SetRequest isUnmarshallRequest = new SetRequest(this.editingDomain, this.acceptEventAction, UMLPackage.eINSTANCE.getAcceptEventAction_IsUnmarshall(), true);
		IElementEditService isUnmarshallElementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand isUnmarshallEditAcceptEventActionCommand = isUnmarshallElementEditService.getEditCommand(isUnmarshallRequest);
		if (!isUnmarshallEditAcceptEventActionCommand.canExecute()) {
			Assert.fail("The attribut isUnmarshall of AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(isUnmarshallEditAcceptEventActionCommand));

		// Assign extendedTrigger1 to acceptEventAction
		SetRequest request = new SetRequest(this.editingDomain, this.acceptEventAction, UMLPackage.eINSTANCE.getAcceptEventAction_Trigger(), this.extendedTrigger1);
		IElementEditService baseElementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand baseEditAcceptEventActionCommand = baseElementEditService.getEditCommand(request);
		if (!baseEditAcceptEventActionCommand.canExecute()) {
			Assert.fail("The AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(baseEditAcceptEventActionCommand));

		// Assign extendedTrigger1 to acceptEventAction
		request = new SetRequest(this.editingDomain, this.acceptEventAction, UMLPackage.eINSTANCE.getAcceptEventAction_Trigger(), triggerTimerEvent);
		IElementEditService extendedElementEditService = ElementEditServiceUtils.getCommandProvider(this.acceptEventAction);
		ICommand extendedEditAcceptEventActionCommand = extendedElementEditService.getEditCommand(request);
		if (!extendedEditAcceptEventActionCommand.canExecute()) {
			Assert.fail("The AcceptEventAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(extendedEditAcceptEventActionCommand));

		// Assert result derivation
		Assert.assertTrue("AcceptEventAction shall have 0 result pin", this.acceptEventAction.getResults().size() == 0);

		if (!baseEditAcceptEventActionCommand.canUndo()) {
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

		// Define base trigger N1
		this.baseTrigger = UMLFactory.eINSTANCE.createTrigger();
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
		this.baseSignal = UMLFactory.eINSTANCE.createSignal();
		this.baseSignal.getOwnedAttributes().add(p1);
		this.baseSignal.getOwnedAttributes().add(p2);
		SignalEvent se1 = UMLFactory.eINSTANCE.createSignalEvent();
		se1.setSignal(this.baseSignal);
		this.baseTrigger.setEvent(se1);

		// Define extended trigger N2
		this.extendedTrigger1 = UMLFactory.eINSTANCE.createTrigger();
		Property p3 = UMLFactory.eINSTANCE.createProperty();
		p3.setName("p3");
		p3.setUpper(1);
		p3.setLower(-1);
		p3.setType(this.getPrimitiveType("String"));
		this.extendedSignal1 = UMLFactory.eINSTANCE.createSignal();
		this.extendedSignal1.getOwnedAttributes().add(p3);
		SignalEvent se2 = UMLFactory.eINSTANCE.createSignalEvent();
		se2.setSignal(this.extendedSignal1);
		this.extendedTrigger1.setEvent(se2);

		// Define extended trigger N3
		this.extendedTrigger2 = UMLFactory.eINSTANCE.createTrigger();
		Property p4 = UMLFactory.eINSTANCE.createProperty();
		p4.setName("p4");
		p4.setUpper(1);
		p4.setLower(-1);
		p4.setType(this.getPrimitiveType("String"));
		this.extendedSignal2 = UMLFactory.eINSTANCE.createSignal();
		this.extendedSignal2.getOwnedAttributes().add(p4);
		SignalEvent se3 = UMLFactory.eINSTANCE.createSignalEvent();
		se3.setSignal(this.extendedSignal2);
		this.extendedTrigger2.setEvent(se3);

		// Define extended trigger N4
		this.trigger3 = UMLFactory.eINSTANCE.createTrigger();
		this.Signal3 = UMLFactory.eINSTANCE.createSignal();
		SignalEvent se4 = UMLFactory.eINSTANCE.createSignalEvent();
		se4.setSignal(this.Signal3);
		this.trigger3.setEvent(se4);

		// Define extended trigger N5 time event
		this.triggerTimerEvent = UMLFactory.eINSTANCE.createTrigger();
		TimeEvent te1 = UMLFactory.eINSTANCE.createTimeEvent();
		this.triggerTimerEvent.setEvent(te1);

		// Define extended trigger N6 change event
		this.triggerChangeEvent = UMLFactory.eINSTANCE.createTrigger();
		ChangeEvent ce1 = UMLFactory.eINSTANCE.createChangeEvent();
		this.triggerChangeEvent.setEvent(ce1);

		// Define extended trigger N7 call event
		this.triggerCallEvent = UMLFactory.eINSTANCE.createTrigger();
		CallEvent ce2 = UMLFactory.eINSTANCE.createCallEvent();
		this.triggerCallEvent.setEvent(ce2);

		// Create generalization between the three signals
		Generalization generalization1 = UMLFactory.eINSTANCE.createGeneralization();
		generalization1.setGeneral(this.baseSignal);
		generalization1.setSpecific(this.extendedSignal1);
		this.extendedSignal1.getGeneralizations().add(generalization1);
		Generalization generalization2 = UMLFactory.eINSTANCE.createGeneralization();
		generalization2.setGeneral(this.baseSignal);
		generalization2.setSpecific(this.extendedSignal2);
		this.extendedSignal2.getGeneralizations().add(generalization2);

		// Create the activity containing the accept event action
		Activity activity = UMLFactory.eINSTANCE.createActivity();
		this.acceptEventAction = UMLFactory.eINSTANCE.createAcceptEventAction();
		this.acceptEventAction.setIsUnmarshall(false);
		activity.getOwnedNodes().add(this.acceptEventAction);

		// Add the different elements to the model
		this.umlTestModel.getPackagedElements().add(activity);
		this.umlTestModel.getPackagedElements().add(this.baseSignal);
		this.umlTestModel.getPackagedElements().add(this.extendedSignal1);
		this.umlTestModel.getPackagedElements().add(this.extendedSignal2);
		this.umlTestModel.getPackagedElements().add(this.Signal3);

		// Add the test model within the model set
		Resource umlTestModelResource = new ResourceImpl();
		umlTestModelResource.setURI(URI.createURI("UMLBaseTestModel.tmp"));
		umlTestModelResource.getContents().add(this.umlTestModel);
		this.modelSet.getResources().add(umlTestModelResource);
	}
}
