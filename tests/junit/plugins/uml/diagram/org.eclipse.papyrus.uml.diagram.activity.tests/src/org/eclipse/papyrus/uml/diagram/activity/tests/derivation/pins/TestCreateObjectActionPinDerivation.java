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
import org.eclipse.uml2.uml.CreateObjectAction;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * Pins of CreateObjectAction should be create and update automatically
 * 
 */
public class TestCreateObjectActionPinDerivation extends AbstractTestPinDerivation {

	/**
	 * CallBehaviorAction advice identifier
	 */
	private final String TARGET_ADVICE_IDENTIFIER = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.CreateObjectActionEditHelperAdvice";

	/**
	 * SendSignalAction on which pin derivation is tested
	 */
	private CreateObjectAction createObjectAction;

	private Class classClassifier;

	private Activity activityClassifier;

	/**
	 * Constructor.
	 */
	public TestCreateObjectActionPinDerivation() {
		this.classClassifier = null;
		this.activityClassifier = null;
		this.populateBaseTestModel();
		// Set Automated Model Completion preference to PIN_SYNCHRONIZATION
		IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		prefStore.setValue(IAutomatedModelCompletionPreferencesConstants.CREATE_OBJECT_ACTION_ACCELERATOR, AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION);
	}

	/**
	 * Role:
	 * - Ensure that the advice for the CreateObjectAction is available
	 */
	@Test
	public void testAdviceForCreateObjectActionExists() {
		Assert.assertNotNull("createObjectAction advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER));
	}

	/**
	 * Role:
	 * - For a CreateObjectAction having no referenced classifier, ensure that if a class gets referenced then pins
	 * are inferred.
	 * 
	 * Scenario:
	 * - 1] CreateObjectAction 'classifier' feature is set with the 'classClassifier'
	 * - 2] Synchronization is performed via the advice
	 * - SendSignalAction has now 1 pin
	 * - 1 result type by classClassifier
	 */
	@Test
	public void testPinDerivation_NoClassifier_ToClassClassifier() {
		SetRequest request = new SetRequest(this.editingDomain, this.createObjectAction, UMLPackage.eINSTANCE.getCreateObjectAction_Classifier(), this.classClassifier);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.createObjectAction);
		ICommand editCreateObjectActionCommand = elementEditService.getEditCommand(request);
		if (!editCreateObjectActionCommand.canExecute()) {
			Assert.fail("The CreateObjectAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editCreateObjectActionCommand));

		// Assert target derivation
		Assert.assertTrue("CreateObjectAction shall have a result pin", this.createObjectAction.getResult() != null);
		Assert.assertTrue("CreateObjectAction shall have a result pin typed by classClassifier", this.createObjectAction.getResult().getType() == classClassifier);

		if (!editCreateObjectActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}

	/**
	 * Role:
	 * - For a CreateObjectAction having no referenced classifier, ensure that if a activity gets referenced then pins
	 * are inferred.
	 * 
	 * Scenario:
	 * - 1] CreateObjectAction 'classifier' feature is set with the 'activityClassifier'
	 * - 2] Synchronization is performed via the advice
	 * - SendSignalAction has now 1 pin
	 * - 1 result type by activityClassifier
	 */
	@Test
	public void testPinDerivation_NoClassifier_ToActivityClassifier() {
		SetRequest request = new SetRequest(this.editingDomain, this.createObjectAction, UMLPackage.eINSTANCE.getCreateObjectAction_Classifier(), this.activityClassifier);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.createObjectAction);
		ICommand editCreateObjectActionCommand = elementEditService.getEditCommand(request);
		if (!editCreateObjectActionCommand.canExecute()) {
			Assert.fail("The CreateObjectAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editCreateObjectActionCommand));

		// Assert target derivation
		Assert.assertTrue("CreateObjectAction shall have a result pin", this.createObjectAction.getResult() != null);
		Assert.assertTrue("CreateObjectAction shall have a result pin typed by activityClassifer", this.createObjectAction.getResult().getType() == activityClassifier);

		if (!editCreateObjectActionCommand.canUndo()) {
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

		// Create classClassifier
		this.classClassifier = UMLFactory.eINSTANCE.createClass();
		classClassifier.setName("C");

		// Create class
		this.activityClassifier = UMLFactory.eINSTANCE.createActivity();
		activityClassifier.setName("A");

		// Create the activity containing the create object action
		Activity activity = UMLFactory.eINSTANCE.createActivity();
		this.createObjectAction = UMLFactory.eINSTANCE.createCreateObjectAction();
		activity.getOwnedNodes().add(this.createObjectAction);

		// Add the different elements to the model
		this.umlTestModel.getPackagedElements().add(activity);
		this.umlTestModel.getPackagedElements().add(this.classClassifier);
		this.umlTestModel.getPackagedElements().add(this.activityClassifier);

		// Add the test model within the model set
		Resource umlTestModelResource = new ResourceImpl();
		umlTestModelResource.setURI(URI.createURI("UMLBaseTestModel.tmp"));
		umlTestModelResource.getContents().add(this.umlTestModel);
		this.modelSet.getResources().add(umlTestModelResource);
	}
}
