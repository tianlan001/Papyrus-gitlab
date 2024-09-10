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
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.CreateLinkAction;
import org.eclipse.uml2.uml.LinkEndCreationData;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * Pins of CreateLinkAction should be create and update automatically
 * 
 */
public class TestCreateLinkActionPinDerivation extends AbstractTestPinDerivation {
	
	/**
	 * CreateLinkAction advice identifier
	 */
	private final String TARGET_ADVICE_IDENTIFIER = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.CreateLinkActionEditHelperAdvice";

	private final String TARGET_ADVICE_IDENTIFIER_FOR_LINKENDDATA = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.LinkEndCreationDataEditHelperAdvice";

	private final String TARGET_ADVICE_IDENTIFIER_FOR_PROPERTY = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.PropertyEditHelperAdvice";
	
	private CreateLinkAction createLinkAction;

	private Association association1;

	private LinkEndCreationData linkEndCreationDataA;

	private Class classA;

	private Class classB;

	private Class classC;

	private Association association2;

	/**
	 * Constructor.
	 *
	 */
	public TestCreateLinkActionPinDerivation() {
		this.createLinkAction = null;
		this.association1 = null;
		this.linkEndCreationDataA = null;
		this.classA = null;
		this.classB = null;
		this.populateBaseTestModel();
		// Set Automated Model Completion preference to PIN_SYNCHRONIZATION
		IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		prefStore.setValue(IAutomatedModelCompletionPreferencesConstants.CREATE_LINK_ACTION_ACCELERATOR, AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION);
	}
	
	/**
	 * Role:
	 * - Ensure that the advice for the CreateLinkAction is available
	 */
	@Test
	public void testAdviceForCreateLinkActionExists() {
		Assert.assertNotNull("CreateLinkAction advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER));
		Assert.assertNotNull("LinkEndData advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER_FOR_LINKENDDATA));
		Assert.assertNotNull("Property advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER_FOR_PROPERTY));
	}
	
	/**
	 * Role:
	 * - For a CreateLinkAction having no end data, ensure that if an end data is referenced then the pins value are inferred.
	 * 
	 * Scenario:
	 * - 1] linkEndCreationData end feature is set with the association end a
	 * - 2] Synchronization is performed via the advice
	 * - linkEndCreationData has now 1 pin
	 * - 1 value pin
	 * 
	 * - 3] CreateLinkAction 'endData' feature is set with the linkEndCreationData
	 * - 4] Synchronization is performed via the advice
	 * - CreateLinkAction has now:
	 * - 2 linkEnd: the one we just add, an other on for the second end of the link
	 * - 2 inputValue: one for each linkEnd
	 */
	@Test
	public void testPinDerivation_NoEndData_To_EndDataWithValuePin() {
		SetRequest requestLinkEndCreation = new SetRequest(this.editingDomain, this.linkEndCreationDataA, UMLPackage.eINSTANCE.getLinkEndData_End(), this.association1.getMemberEnds().get(0));
		IElementEditService elementLinkEndCreationEditService = ElementEditServiceUtils.getCommandProvider(this.linkEndCreationDataA);
		ICommand editLinkEndCreationDataCommand = elementLinkEndCreationEditService.getEditCommand(requestLinkEndCreation);
		if (!editLinkEndCreationDataCommand.canExecute()) {
			Assert.fail("The LinkEndCreationData cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editLinkEndCreationDataCommand));
		
		// Assert value
		Assert.assertTrue("LinkEndCreationData shall have a value pin", this.linkEndCreationDataA.getValue() != null);
		Assert.assertTrue("LinkEndCreationData shall have a value pin typed by classA", this.linkEndCreationDataA.getValue().getType() == classA);
		
		
		SetRequest requestCreateLinkAction = new SetRequest(this.editingDomain, this.createLinkAction, UMLPackage.eINSTANCE.getLinkAction_EndData(), this.linkEndCreationDataA);
		IElementEditService elementCreateLinkActionEditService = ElementEditServiceUtils.getCommandProvider(this.linkEndCreationDataA);
		ICommand editCreateLinkActionCommand = elementCreateLinkActionEditService.getEditCommand(requestCreateLinkAction);
		if (!editCreateLinkActionCommand.canExecute()) {
			Assert.fail("The CreateLinkAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editCreateLinkActionCommand));
		
		// Assert endData
		Assert.assertTrue("CreateLinkAction shall have 2 endData", this.createLinkAction.getEndData().size() == 2);
		Assert.assertTrue("CreateLinkAction shall have a endData: linkEndCreationDataA", this.createLinkAction.getEndData().get(0).equals(linkEndCreationDataA));
		Assert.assertTrue("CreateLinkAction shall have a endData corresponding to the other end of the association1", this.createLinkAction.getEndData().get(1).getEnd().equals(linkEndCreationDataA.getEnd().getOtherEnd()));
				
		// Assert input value
		Assert.assertTrue("CreateLinkAction shall have 2 inputValue", this.createLinkAction.getInputValues().size() == 2);
		Assert.assertTrue("CreateLinkAction shall have a inputValue same as in the linkEndCreationData", this.createLinkAction.getInputValues().get(0).equals(this.linkEndCreationDataA.getValue()));
		Assert.assertTrue("CreateLinkAction shall have a inputValue same as in the other end of the association1", this.createLinkAction.getInputValues().get(1).equals(this.createLinkAction.getEndData().get(1).getValue()));
				
				
		if (!editCreateLinkActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation (CreateLinkAction)");
		}
		if (!editLinkEndCreationDataCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation (LinkEndCreationData)");
		}
		this.editingDomain.getCommandStack().undo();
	}
	
	/**
	 * Role:
	 * - For a CreateLinkAction having already 2 linkEndData, ensure that if an end data is modified then the pins value are inferred.
	 * 
	 * Scenario:
	 * - 1] linkEndCreationDataA end feature is set with the association end a
	 * - 2] Synchronization is performed via the advice
	 * - linkEndCreationData has now 1 value pin
	 * 
	 * - 3] CreateLinkAction 'endData' feature is set with the linkEndCreationDataA
	 * - 4] Synchronization is performed via the advice
	 * - CreateLinkAction has now:
	 * - 2 linkEnd: the one we just add, an other on for the second end of the link (a and b)
	 * - 2 inputValue: one for each linkEnd
	 * 
	 * - 5] linkEndCreationDataA end feature is set with the association end c
	 * - 4] Synchronization is performed via the advice
	 * - CreateLinkAction has now:
	 * - 2 linkEnd: the one we just add, an other on for the second end of the link (c and a)
	 * - 2 inputValue: one for each linkEnd
	 */
	@Test
	public void testPinDerivation_ModificationLinkEndCreationData() {
		SetRequest requestLinkEndCreation = new SetRequest(this.editingDomain, this.linkEndCreationDataA, UMLPackage.eINSTANCE.getLinkEndData_End(), this.association1.getMemberEnds().get(0));
		IElementEditService elementLinkEndCreationEditService = ElementEditServiceUtils.getCommandProvider(this.linkEndCreationDataA);
		ICommand editLinkEndCreationDataCommand = elementLinkEndCreationEditService.getEditCommand(requestLinkEndCreation);
		if (!editLinkEndCreationDataCommand.canExecute()) {
			Assert.fail("The LinkEndCreationData cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editLinkEndCreationDataCommand));
		
		SetRequest requestCreateLinkAction = new SetRequest(this.editingDomain, this.createLinkAction, UMLPackage.eINSTANCE.getLinkAction_EndData(), this.linkEndCreationDataA);
		IElementEditService elementCreateLinkActionEditService = ElementEditServiceUtils.getCommandProvider(this.linkEndCreationDataA);
		ICommand editCreateLinkActionCommand = elementCreateLinkActionEditService.getEditCommand(requestCreateLinkAction);
		if (!editCreateLinkActionCommand.canExecute()) {
			Assert.fail("The CreateLinkAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editCreateLinkActionCommand));
		
		SetRequest requestLinkEndCreation2 = new SetRequest(this.editingDomain, this.createLinkAction.getEndData().get(0), UMLPackage.eINSTANCE.getLinkEndData_End(), this.association2.getMemberEnds().get(1));
		IElementEditService elementLinkEndCreationEditService2 = ElementEditServiceUtils.getCommandProvider(this.linkEndCreationDataA);
		ICommand editLinkEndCreationDataCommand2 = elementLinkEndCreationEditService2.getEditCommand(requestLinkEndCreation2);
		if (!editLinkEndCreationDataCommand2.canExecute()) {
			Assert.fail("The LinkEndCreationData cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editLinkEndCreationDataCommand2));
		
		// Assert endData
		Assert.assertTrue("CreateLinkAction shall have 2 endData", this.createLinkAction.getEndData().size() == 2);
		Assert.assertTrue("CreateLinkAction shall have a endData: linkEndCreationDataA", this.createLinkAction.getEndData().get(0).getEnd().getType() == classC);
		Assert.assertTrue("CreateLinkAction shall have a endData corresponding to the other end of the association2", this.createLinkAction.getEndData().get(1).getEnd().getType() == classA);
				
		// Assert input value
		Assert.assertTrue("CreateLinkAction shall have 2 inputValue", this.createLinkAction.getInputValues().size() == 2);
		Assert.assertTrue("CreateLinkAction shall have a inputValue same as in the linkEndCreationData", this.createLinkAction.getInputValues().get(0).equals(this.linkEndCreationDataA.getValue()));
		Assert.assertTrue("CreateLinkAction shall have a inputValue same as in the other end of the association2", this.createLinkAction.getInputValues().get(1).equals(this.createLinkAction.getEndData().get(1).getValue()));
				
		if (!editLinkEndCreationDataCommand2.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation (modification of LinkEndCreationData)");
		}	
		if (!editCreateLinkActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation (CreateLinkAction)");
		}
		if (!editLinkEndCreationDataCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation (LinkEndCreationData)");
		}
		this.editingDomain.getCommandStack().undo();
	}
	
	/**
	 * Role:
	 * - For a CreateLinkAction having already 2 linkEndData, ensure that if an end data is modified then the pins value are inferred.
	 * 
	 * Scenario:
	 * - 1] linkEndCreationDataA end feature is set with the association end a
	 * - 2] Synchronization is performed via the advice
	 * - linkEndCreationData has now 1 value pin
	 * 
	 * - 3] CreateLinkAction 'endData' feature is set with the linkEndCreationDataA
	 * - 4] Synchronization is performed via the advice
	 * - CreateLinkAction has now:
	 * - 2 linkEnd: the one we just add, an other on for the second end of the link (a and b)
	 * - 2 inputValue: one for each linkEnd
	 * 
	 * - 5] the type of the linkEndCreationDataA first end is set with classC
	 * - 4] Synchronization is performed via the advice
	 * - CreateLinkAction has now:
	 * - 2 linkEnd: the one we just add, an other on for the second end of the link (c and b)
	 * - 2 inputValue: one for each linkEnd
	 */
	@Test
	public void testPinDerivation_ModificationPropertyOfLinkEndCreationData() {
		SetRequest requestLinkEndCreation = new SetRequest(this.editingDomain, this.linkEndCreationDataA, UMLPackage.eINSTANCE.getLinkEndData_End(), this.association1.getMemberEnds().get(0));
		IElementEditService elementLinkEndCreationEditService = ElementEditServiceUtils.getCommandProvider(this.linkEndCreationDataA);
		ICommand editLinkEndCreationDataCommand = elementLinkEndCreationEditService.getEditCommand(requestLinkEndCreation);
		if (!editLinkEndCreationDataCommand.canExecute()) {
			Assert.fail("The LinkEndCreationData cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editLinkEndCreationDataCommand));
		
		SetRequest requestCreateLinkAction = new SetRequest(this.editingDomain, this.createLinkAction, UMLPackage.eINSTANCE.getLinkAction_EndData(), this.linkEndCreationDataA);
		IElementEditService elementCreateLinkActionEditService = ElementEditServiceUtils.getCommandProvider(this.linkEndCreationDataA);
		ICommand editCreateLinkActionCommand = elementCreateLinkActionEditService.getEditCommand(requestCreateLinkAction);
		if (!editCreateLinkActionCommand.canExecute()) {
			Assert.fail("The CreateLinkAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editCreateLinkActionCommand));
		
		SetRequest requestLinkEndCreation2 = new SetRequest(this.editingDomain, this.createLinkAction.getEndData().get(0).getEnd(), UMLPackage.eINSTANCE.getTypedElement_Type(), classC);
		IElementEditService elementLinkEndCreationEditService2 = ElementEditServiceUtils.getCommandProvider(this.linkEndCreationDataA);
		ICommand editLinkEndCreationDataCommand2 = elementLinkEndCreationEditService2.getEditCommand(requestLinkEndCreation2);
		if (!editLinkEndCreationDataCommand2.canExecute()) {
			Assert.fail("The LinkEndCreationData cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editLinkEndCreationDataCommand2));
		
		// Assert endData
		Assert.assertTrue("CreateLinkAction shall have 2 endData", this.createLinkAction.getEndData().size() == 2);
		Assert.assertTrue("CreateLinkAction shall have a endData which type is classC", this.createLinkAction.getEndData().get(0).getEnd().getType() == classC);
		Assert.assertTrue("CreateLinkAction shall have a endDatacorresponding to the other end of the association1", this.createLinkAction.getEndData().get(1).getEnd().getType() == classB);
				
		// Assert input value
		Assert.assertTrue("CreateLinkAction shall have 2 inputValue", this.createLinkAction.getInputValues().size() == 2);
		Assert.assertTrue("CreateLinkAction shall have a inputValue which type is classC", this.createLinkAction.getInputValues().get(0).getType() == classC);
		Assert.assertTrue("CreateLinkAction shall have a inputValue same as in the other  end of the association1", this.createLinkAction.getInputValues().get(1).getType() == classB);
				
		if (!editLinkEndCreationDataCommand2.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation (modification of property)");
		}	
		if (!editCreateLinkActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation (CreateLinkAction)");
		}
		if (!editLinkEndCreationDataCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation (LinkEndCreationData)");
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
		
		// Create 2 classes 
		classA = UMLFactory.eINSTANCE.createClass();
		classA.setName("A");
		classB = UMLFactory.eINSTANCE.createClass();
		classB.setName("B");
		classC = UMLFactory.eINSTANCE.createClass();
		classC.setName("C");
		
		// Create Association
		association1 = UMLFactory.eINSTANCE.createAssociation();
		association1.createOwnedEnd("a",	classA);
		association1.createOwnedEnd("b",	classB);
		
		association2 = UMLFactory.eINSTANCE.createAssociation();
		association2.createOwnedEnd("a",	classA);
		association2.createOwnedEnd("c",	classC);
		// Create LinkEndCreationData
		linkEndCreationDataA = UMLFactory.eINSTANCE.createLinkEndCreationData();
		
		// Create activity containing the CreateLinkAction
		Activity activity = UMLFactory.eINSTANCE.createActivity();
		this.createLinkAction = UMLFactory.eINSTANCE.createCreateLinkAction();
		activity.getOwnedNodes().add(this.createLinkAction);
		
		// Add the different elements in the model
		this.umlTestModel.getPackagedElements().add(classA);
		this.umlTestModel.getPackagedElements().add(classB);
		this.umlTestModel.getPackagedElements().add(classC);
		this.umlTestModel.getPackagedElements().add(association1);
		this.umlTestModel.getPackagedElements().add(association2);
		this.umlTestModel.getPackagedElements().add(activity);
		
		
		// Add test model within the model set
		Resource umlTestModelResource = new ResourceImpl();
		umlTestModelResource.setURI(URI.createURI("UMLBaseTestModel.tmp"));
		umlTestModelResource.getContents().add(this.umlTestModel);
		this.modelSet.getResources().add(umlTestModelResource);
	}
	
}
