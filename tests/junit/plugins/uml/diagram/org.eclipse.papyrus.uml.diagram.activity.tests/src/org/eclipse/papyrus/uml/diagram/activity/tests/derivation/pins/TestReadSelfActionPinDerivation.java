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
 * (CEA LIST) - Initial API and implementation
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
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ReadSelfActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.preferences.AutomatedModelCompletionPreferencesInitializer;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.preferences.IAutomatedModelCompletionPreferencesConstants;
import org.eclipse.papyrus.uml.diagram.activity.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.ReadSelfAction;
import org.eclipse.uml2.uml.UMLFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * Pin of ReadSelfAction should be create and update automatically
 * 
 */
public class TestReadSelfActionPinDerivation extends AbstractTestPinDerivation {

	/**
	 * CallBehaviorAction advice identifier
	 */
	private final String TARGET_ADVICE_IDENTIFIER = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.ReadSelfActionEditHelperAdvice";

	private Class classA;

	private Class classB;

	private Class classC;

	private Activity activityA_SpecifiedOperation;

	private Activity activityB_ownedBehavior;

	private Activity activityB_nestedClassifier;

	private Activity activityC_classiferBahaviour;

	private Activity activityD_InRootElement;

	private ReadSelfAction readSelfActionA;

	private ReadSelfAction readSelfActionB1;

	private ReadSelfAction readSelfActionB2;

	private ReadSelfAction readSelfActionC;

	private ReadSelfAction readSelfActionD;

	/**
	 * Constructor.
	 */
	public TestReadSelfActionPinDerivation() {
		this.populateBaseTestModel();
		// Set Automated Model Completion preference to PIN_SYNCHRONIZATION
		IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		prefStore.setValue(IAutomatedModelCompletionPreferencesConstants.READ_SELF_ACTION_ACCELERATOR, AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION);
	}

	/**
	 * Role:
	 * - Ensure that the advice for the ReadSelfAction is available
	 */
	@Test
	public void testAdviceForReadSelfActionExists() {
		Assert.assertNotNull("ReadSelfAction advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER));
	}

	/**
	 * Role:
	 * - For adding a ReadSelfAction to the activity, insure that the pin is correctly typed. 
	 * ActivityA is owned by the rootElement
	 * ActivityA specified an operation of the classA
	 * 
	 * Scenario:
	 * - 1] ReadSelfAction is add to the activity
	 * - 2] Synchronization is performed via the advice
	 * - ReadSelfAction has now 1 pin
	 * - 1 result typed by the activity
	 */
	@Test
	public void testPinDerivation_ActivitySpecifiedOperation() {
		ConfigureRequest request = new ConfigureRequest(this.editingDomain, this.readSelfActionA, UMLElementTypes.getElementType(ReadSelfActionEditPart.VISUAL_ID));
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.readSelfActionA);
		ICommand editReadSelfActionCommand = elementEditService.getEditCommand(request);
		if (!editReadSelfActionCommand.canExecute()) {
			Assert.fail("The ReadSelfAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editReadSelfActionCommand));
		
		// Assert result
		Assert.assertTrue("ReadSelfAction shall have a result pin", this.readSelfActionA.getResult() != null);
		Assert.assertTrue("ReadSelfAction shall have a result pin typed by classA", this.readSelfActionA.getResult().getType() == null);

		if (!editReadSelfActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}

	/**
	 * Role:
	 * - For adding a ReadSelfAction to the activity, insure that the pin is correctly typed. 
	 * ActivityB is an ownedBehavior of the classB
	 * 
	 * Scenario:
	 * - 1] ReadSelfAction is add to the activity
	 * - 2] Synchronization is performed via the advice
	 * - ReadSelfAction has now 1 pin
	 * - 1 result typed by classB
	 */
	@Test
	public void testPinDerivation_ActivityOwnedBeahaviour() {
		ConfigureRequest request = new ConfigureRequest(this.editingDomain, this.readSelfActionB1, UMLElementTypes.getElementType(ReadSelfActionEditPart.VISUAL_ID));
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.readSelfActionB1);
		ICommand editReadSelfActionCommand = elementEditService.getEditCommand(request);
		if (!editReadSelfActionCommand.canExecute()) {
			Assert.fail("The ReadSelfAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editReadSelfActionCommand));
		
		// Assert result
		Assert.assertTrue("ReadSelfAction shall have a result pin", this.readSelfActionB1.getResult() != null);
		Assert.assertTrue("ReadSelfAction shall have a result pin typed by classB", this.readSelfActionB1.getResult().getType() == classB);

		if (!editReadSelfActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}
	
	/**
	 * Role:
	 * - For adding a ReadSelfAction to the activity, insure that the pin is correctly typed. 
	 * ActivityB is an nestedClassifier of the classB
	 * 
	 * Scenario:
	 * - 1] ReadSelfAction is add to the activity
	 * - 2] Synchronization is performed via the advice
	 * - ReadSelfAction has now 1 pin
	 * - 1 result typed by activityB_nestedClassifer
	 */
	@Test
	public void testPinDerivation_ActivityNestedClassifer() {
		ConfigureRequest request = new ConfigureRequest(this.editingDomain, this.readSelfActionB2, UMLElementTypes.getElementType(ReadSelfActionEditPart.VISUAL_ID));
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.readSelfActionB2);
		ICommand editReadSelfActionCommand = elementEditService.getEditCommand(request);
		if (!editReadSelfActionCommand.canExecute()) {
			Assert.fail("The ReadSelfAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editReadSelfActionCommand));
		
		// Assert result
		Assert.assertTrue("ReadSelfAction shall have a result pin", this.readSelfActionB2.getResult() != null);
		Assert.assertTrue("ReadSelfAction shall have a result pin typed by classB", this.readSelfActionB2.getResult().getType() == null);

		if (!editReadSelfActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}
	
	/**
	 * Role:
	 * - For adding a ReadSelfAction to the activity, insure that the pin is correctly typed. 
	 * ActivityC is owned by classC
	 * ActivityC is classifierBehaviour of classC
	 * 
	 * Scenario:
	 * - 1] ReadSelfAction is add to the activity
	 * - 2] Synchronization is performed via the advice
	 * - ReadSelfAction has now 1 pin
	 * - 1 result typed by classC
	 */
	@Test
	public void testPinDerivation_ActivityAsClassifierBehaviour() {
		ConfigureRequest request = new ConfigureRequest(this.editingDomain, this.readSelfActionC, UMLElementTypes.getElementType(ReadSelfActionEditPart.VISUAL_ID));
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.readSelfActionC);
		ICommand editReadSelfActionCommand = elementEditService.getEditCommand(request);
		if (!editReadSelfActionCommand.canExecute()) {
			Assert.fail("The ReadSelfAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editReadSelfActionCommand));
		
		// Assert result
		Assert.assertTrue("ReadSelfAction shall have a result pin", this.readSelfActionC.getResult() != null);
		Assert.assertTrue("ReadSelfAction shall have a result pin typed by classB", this.readSelfActionC.getResult().getType() == classC);

		if (!editReadSelfActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}
	
	/**
	 * Role:
	 * - For adding a ReadSelfAction to the activity, insure that the pin is correctly typed. 
	 * ActivityC is owned by the rootElement
	 * 
	 * Scenario:
	 * - 1] ReadSelfAction is add to the activity
	 * - 2] Synchronization is performed via the advice
	 * - ReadSelfAction has now 1 pin
	 * - 1 result typed by activityD
	 */
	@Test
	public void testPinDerivation_ActivityInRootElement() {
		ConfigureRequest request = new ConfigureRequest(this.editingDomain, this.readSelfActionD, UMLElementTypes.getElementType(ReadSelfActionEditPart.VISUAL_ID));
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.readSelfActionD);
		ICommand editReadSelfActionCommand = elementEditService.getEditCommand(request);
		if (!editReadSelfActionCommand.canExecute()) {
			Assert.fail("The ReadSelfAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editReadSelfActionCommand));
		
		// Assert result
		Assert.assertTrue("ReadSelfAction shall have a result pin", this.readSelfActionD.getResult() != null);
		Assert.assertTrue("ReadSelfAction shall have a result pin not typed", this.readSelfActionD.getResult().getType() == null);

		if (!editReadSelfActionCommand.canUndo()) {
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
		
		// Create Activity A => specification
		this.readSelfActionA = UMLFactory.eINSTANCE.createReadSelfAction();
		classA = UMLFactory.eINSTANCE.createClass();
		classA.setName("a");
		classA.getOwnedOperations().add(UMLFactory.eINSTANCE.createOperation());
		activityA_SpecifiedOperation = UMLFactory.eINSTANCE.createActivity();
		activityA_SpecifiedOperation.setSpecification(classA.getOwnedOperations().get(0));
		activityA_SpecifiedOperation.getOwnedNodes().add(readSelfActionA);
		
		// Create Activity B => OwnedBehaviour and NestedClassifier
		this.readSelfActionB1 = UMLFactory.eINSTANCE.createReadSelfAction();
		this.readSelfActionB2 = UMLFactory.eINSTANCE.createReadSelfAction();
		classB = UMLFactory.eINSTANCE.createClass();
		classB.setName("b");
		activityB_ownedBehavior = UMLFactory.eINSTANCE.createActivity();
		activityB_ownedBehavior.getOwnedNodes().add(readSelfActionB1);
		classB.getOwnedBehaviors().add(activityB_ownedBehavior);
		activityB_nestedClassifier = UMLFactory.eINSTANCE.createActivity();
		activityB_nestedClassifier.getOwnedNodes().add(readSelfActionB2);
		classB.getNestedClassifiers().add(activityB_nestedClassifier);
		
		// Create Activity C => classifierBehaviour
		this.readSelfActionC = UMLFactory.eINSTANCE.createReadSelfAction();
		classC = UMLFactory.eINSTANCE.createClass();
		classC.setName("c");
		activityC_classiferBahaviour = UMLFactory.eINSTANCE.createActivity();
		activityC_classiferBahaviour.getOwnedNodes().add(readSelfActionC);
		classC.getOwnedBehaviors().add(activityC_classiferBahaviour);
		classC.setClassifierBehavior(activityC_classiferBahaviour);
		
		// Create Activity D => rootElement
		this.readSelfActionD = UMLFactory.eINSTANCE.createReadSelfAction();
		activityD_InRootElement = UMLFactory.eINSTANCE.createActivity();
		activityD_InRootElement.getOwnedNodes().add(readSelfActionD);

		// Add the different elements to the model
		this.umlTestModel.getPackagedElements().add(activityA_SpecifiedOperation);
		this.umlTestModel.getPackagedElements().add(this.classA);
		this.umlTestModel.getPackagedElements().add(this.classB);
		this.umlTestModel.getPackagedElements().add(this.classC);
		this.umlTestModel.getPackagedElements().add(this.activityD_InRootElement);
		
		// Add the test model within the model set
		Resource umlTestModelResource = new ResourceImpl();
		umlTestModelResource.setURI(URI.createURI("UMLBaseTestModel.tmp"));
		umlTestModelResource.getContents().add(this.umlTestModel);
		this.modelSet.getResources().add(umlTestModelResource);
	}
}
