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
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.TestIdentityActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.preferences.AutomatedModelCompletionPreferencesInitializer;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.preferences.IAutomatedModelCompletionPreferencesConstants;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.TestIdentityAction;
import org.eclipse.uml2.uml.UMLFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * Pins of TestIdentityAction should be create automatically
 * 
 */
public class TestTestIdentityActionPinDerivation extends AbstractTestPinDerivation {


	/**
	 * CallBehaviorAction advice identifier
	 */
	private final String TARGET_ADVICE_IDENTIFIER = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.TestIdentityActionEditHelperAdvice";

	private TestIdentityAction testIdentityAction;

	private Activity activity;

	/**
	 * Constructor.
	 */
	public TestTestIdentityActionPinDerivation() {
		this.testIdentityAction = null;
		this.activity = null;
		this.populateBaseTestModel();
		// Set Automated Model Completion preference to PIN_SYNCHRONIZATION
		IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		prefStore.setValue(IAutomatedModelCompletionPreferencesConstants.TEST_IDENTITY_ACTION, AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION);
	}

	/**
	 * Role:
	 * - Ensure that the advice for the TestIdentityAction is available
	 */
	@Test
	public void testAdviceForTestIdentityActionExists() {
		Assert.assertNotNull("TestIdentityAction advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER));
	}

	/**
	 * Role:
	 * - For a TestIdentityAction insertion ensure that if the the 'signal' feature
	 * of the action evolves then pins are updated accordingly
	 * 
	 * Scenario:
	 * - 1] Insertion in the activity of testIdentityAction
	 * - 2] Synchronization is performed via the advice
	 * - testIdentityAction has now 3 pins
	 * - 1 first untyped [in]
	 * - 1 second untyped [in]
	 * - 1 result typed by Boolean [out]
	 *  
	 */
	@Test
	public void testPinDerivation_Creation() {
		ConfigureRequest request = new ConfigureRequest(editingDomain, testIdentityAction, org.eclipse.papyrus.uml.diagram.activity.providers.UMLElementTypes.getElementType(TestIdentityActionEditPart.VISUAL_ID));
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.activity);
		ICommand testIdentityActionCommand = elementEditService.getEditCommand(request);
		if (!testIdentityActionCommand.canExecute()) {
			Assert.fail("The TestIdentityAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(testIdentityActionCommand));

		// Assert first derivation
		Assert.assertTrue("TestIdentityAction shall have a first", this.testIdentityAction.getFirst() != null);
		Assert.assertTrue("TestIdentityAction shall have a first without a type", this.testIdentityAction.getFirst().getType() == null);
		// Assert second derivation
		Assert.assertTrue("TestIdentityAction shall have a second", this.testIdentityAction.getSecond() != null);
		Assert.assertTrue("TestIdentityAction shall have a second without a type", this.testIdentityAction.getSecond().getType() == null);
		// Assert result derivation
		Assert.assertTrue("TestIdentityAction shall have a result", this.testIdentityAction.getResult() != null);
		Assert.assertTrue("TestIdentityAction shall have a result typed by Boolean", this.testIdentityAction.getResult().getType() == this.getPrimitiveType("Boolean"));

		if (!testIdentityActionCommand.canUndo()) {
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

		// Create the activity containing the send signal action
		activity = UMLFactory.eINSTANCE.createActivity();
		this.testIdentityAction = UMLFactory.eINSTANCE.createTestIdentityAction();

		// Add the different elements to the model
		this.umlTestModel.getPackagedElements().add(activity);
		activity.getOwnedNodes().add(testIdentityAction);

		// Add the test model within the model set
		Resource umlTestModelResource = new ResourceImpl();
		umlTestModelResource.setURI(URI.createURI("UMLBaseTestModel.tmp"));
		umlTestModelResource.getContents().add(this.umlTestModel);
		this.modelSet.getResources().add(umlTestModelResource);
	}
}
