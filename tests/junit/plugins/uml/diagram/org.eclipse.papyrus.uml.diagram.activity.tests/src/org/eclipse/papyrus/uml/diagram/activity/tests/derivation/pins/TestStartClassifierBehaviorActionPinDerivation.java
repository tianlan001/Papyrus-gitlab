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
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.StartClassifierBehaviorActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.preferences.AutomatedModelCompletionPreferencesInitializer;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.preferences.IAutomatedModelCompletionPreferencesConstants;
import org.eclipse.papyrus.uml.diagram.activity.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.StartClassifierBehaviorAction;
import org.eclipse.uml2.uml.UMLFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * Pin of StartClassifierBehaviorAction should be create and update automatically
 * 
 */
public class TestStartClassifierBehaviorActionPinDerivation extends AbstractTestPinDerivation {

	/**
	 * CallBehaviorAction advice identifier
	 */
	private final String TARGET_ADVICE_IDENTIFIER = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.StartClassifierBehaviorActionEditHelperAdvice";
	private StartClassifierBehaviorAction startClassifierBehaviorAction;
	private Activity activity;

	/**
	 * Constructor.
	 */
	public TestStartClassifierBehaviorActionPinDerivation() {
		this.activity = null;
		this.startClassifierBehaviorAction = null;
		this.populateBaseTestModel();
		// Set Automated Model Completion preference to PIN_SYNCHRONIZATION
		IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		prefStore.setValue(IAutomatedModelCompletionPreferencesConstants.START_CLASSIFIER_BEHAVIOR_ACTION, AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION);
	}

	/**
	 * Role:
	 * - Ensure that the advice for the StartClassifierBehaviorAction is available
	 */
	@Test
	public void testAdviceForStartClassifierBehaviorActionExists() {
		Assert.assertNotNull("StartClassifierBehaviorAction advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER));
	}

	/**
	 * Role:
	 * - For a StartClassifierBehaviorAction having no referenced signal, ensure that if a signal gets referenced then target and argument pins
	 * are inferred.
	 * 
	 * Scenario:
	 * - 1] StartClassifierBehaviorAction 'signal' feature is set with the 'baseSignal'
	 * - 2] Synchronization is performed via the advice
	 * - StartClassifierBehaviorAction has now 3 pins
	 * - 1 target (of type C)
	 * - 2 argument pins (p1 and p2)
	 */
	@Test
	public void testPinDerivation_StartClassifierBehaviorAction() {
		ConfigureRequest request = new ConfigureRequest(editingDomain, this.startClassifierBehaviorAction, UMLElementTypes.getElementType(StartClassifierBehaviorActionEditPart.VISUAL_ID));
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.activity);
		ICommand editStartClassifierBehaviorActionCommand = elementEditService.getEditCommand(request);
		if (!editStartClassifierBehaviorActionCommand.canExecute()) {
			Assert.fail("The TestIdentityAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editStartClassifierBehaviorActionCommand));
		
		// Assert object
		Assert.assertTrue("StartClassifierBehaviorAction shall have a target", this.startClassifierBehaviorAction.getObject() != null);

		if (!editStartClassifierBehaviorActionCommand.canUndo()) {
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
		
		// Create the startClassifierBehaviorAction
		this.startClassifierBehaviorAction = UMLFactory.eINSTANCE.createStartClassifierBehaviorAction();
		
		// Create the activity containing the startClassifierBehaviorAction
		activity = UMLFactory.eINSTANCE.createActivity();
		activity.getOwnedNodes().add(this.startClassifierBehaviorAction);
		
		// Add the different elements to the model
		this.umlTestModel.getPackagedElements().add(activity);
		
		// Add the test model within the model set
		Resource umlTestModelResource = new ResourceImpl();
		umlTestModelResource.setURI(URI.createURI("UMLBaseTestModel.tmp"));
		umlTestModelResource.getContents().add(this.umlTestModel);
		this.modelSet.getResources().add(umlTestModelResource);
	}
}
