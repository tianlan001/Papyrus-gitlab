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
import org.eclipse.uml2.uml.DestroyLinkAction;
import org.eclipse.uml2.uml.LinkEndDestructionData;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * Pins of DestroyLinkAction should be create and update automatically
 * 
 */
public class TestDestroyLinkActionPinDerivation extends AbstractTestPinDerivation {
	
	/**
	 * DestroyLinkAction advice identifier
	 */
	private final String TARGET_ADVICE_IDENTIFIER = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.DestroyLinkActionEditHelperAdvice";

	private final String TARGET_ADVICE_IDENTIFIER_FOR_LINKENDDATA = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.LinkEndDestructionDataEditHelperAdvice";

	private final String TARGET_ADVICE_IDENTIFIER_FOR_PROPERTY = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.PropertyEditHelperAdvice";
	
	private DestroyLinkAction destroyLinkAction;

	private Association association1;

	private LinkEndDestructionData linkEndDestructionDataA;

	private Class classA;

	private Class classB;

	private Class classC;

	private Association association2;

	/**
	 * Constructor.
	 *
	 */
	public TestDestroyLinkActionPinDerivation() {
		this.destroyLinkAction = null;
		this.association1 = null;
		this.linkEndDestructionDataA = null;
		this.classA = null;
		this.classB = null;
		this.populateBaseTestModel();
		// Set Automated Model Completion preference to PIN_SYNCHRONIZATION
		IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		prefStore.setValue(IAutomatedModelCompletionPreferencesConstants.DESTROY_LINK_ACTION_ACCELERATOR, AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION);
	}
	
	/**
	 * Role:
	 * - Ensure that the advice for the DestroyLinkAction is available
	 */
	@Test
	public void testAdviceForDestroyLinkActionExists() {
		Assert.assertNotNull("DestroyLinkAction advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER));
		Assert.assertNotNull("LinkEndData advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER_FOR_LINKENDDATA));
		Assert.assertNotNull("Property advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER_FOR_PROPERTY));
	}
	
	/**
	 * Role:
	 * - For a DestroyLinkAction having no end data, ensure that if an end data is referenced then the pins value are inferred.
	 * 
	 * Scenario:
	 * - 1] LinkEndDestructionData end feature is set with the association end a
	 * - 2] Synchronization is performed via the advice
	 * - LinkEndDestructionData has now 1 pin
	 * - 1 value pin
	 * 
	 * - 3] DestroyLinkAction 'endData' feature is set with the LinkEndDestructionData
	 * - 4] Synchronization is performed via the advice
	 * - DestroyLinkAction has now:
	 * - 1 linkEnd: the one we just add
	 * - 1 inputValue: the value pin of the linkEnd
	 */
	@Test
	public void testPinDerivation_NoEndData_To_EndDataWithValuePin() {
		SetRequest requestLinkEndDestruction = new SetRequest(this.editingDomain, this.linkEndDestructionDataA, UMLPackage.eINSTANCE.getLinkEndData_End(), this.association1.getMemberEnds().get(0));
		IElementEditService elementLinkEndDestructionEditService = ElementEditServiceUtils.getCommandProvider(this.linkEndDestructionDataA);
		ICommand editLinkEndDestructionDataCommand = elementLinkEndDestructionEditService.getEditCommand(requestLinkEndDestruction);
		if (!editLinkEndDestructionDataCommand.canExecute()) {
			Assert.fail("The LinkEndDestructionData cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editLinkEndDestructionDataCommand));
		
		// Assert value
		Assert.assertTrue("LinkEndDestructionData shall have a value pin", this.linkEndDestructionDataA.getValue() != null);
		Assert.assertTrue("LinkEndDestructionData shall have a value pin typed by classA", this.linkEndDestructionDataA.getValue().getType() == classA);
		
		
		SetRequest requestDestroyLinkAction = new SetRequest(this.editingDomain, this.destroyLinkAction, UMLPackage.eINSTANCE.getLinkAction_EndData(), this.linkEndDestructionDataA);
		IElementEditService elementDestroyLinkActionEditService = ElementEditServiceUtils.getCommandProvider(this.linkEndDestructionDataA);
		ICommand editDestroyLinkActionCommand = elementDestroyLinkActionEditService.getEditCommand(requestDestroyLinkAction);
		if (!editDestroyLinkActionCommand.canExecute()) {
			Assert.fail("The DestroyLinkAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editDestroyLinkActionCommand));
		
		// Assert endData
		Assert.assertTrue("DestroyLinkAction shall have 1 endData", this.destroyLinkAction.getEndData().size() == 1);
		Assert.assertTrue("DestroyLinkAction shall have a endData: LinkEndDestructionDataA", this.destroyLinkAction.getEndData().get(0).equals(linkEndDestructionDataA));
				
		// Assert input value
		Assert.assertTrue("DestroyLinkAction shall have 2 inputValue", this.destroyLinkAction.getInputValues().size() == 1);
		Assert.assertTrue("DestroyLinkAction shall have a inputValue same as in the LinkEndDestructionData", this.destroyLinkAction.getInputValues().get(0).equals(this.linkEndDestructionDataA.getValue()));
				
				
		if (!editDestroyLinkActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation (DestroyLinkAction)");
		}
		if (!editLinkEndDestructionDataCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation (LinkEndDestructionData)");
		}
		this.editingDomain.getCommandStack().undo();
	}
	
	/**
	 * Role:
	 * - For a DestroyLinkAction having already 2 linkEndData, ensure that if an end data is modified then the pins value are inferred.
	 * 
	 * Scenario:
	 * - 1] LinkEndDestructionDataA end feature is set with the association end a
	 * - 2] Synchronization is performed via the advice
	 * - LinkEndDestructionData has now 1 value pin
	 * 
	 * - 3] DestroyLinkAction 'endData' feature is set with the LinkEndDestructionDataA
	 * - 4] Synchronization is performed via the advice
	 * - DestroyLinkAction has now:
	 * - 2 linkEnd: the one we just add, an other on for the second end of the link (a and b)
	 * - 2 inputValue: one for each linkEnd
	 * 
	 * - 5] LinkEndDestructionDataA end feature is set with the association end c
	 * - 4] Synchronization is performed via the advice
	 * - DestroyLinkAction has now:
	 * - 1 linkEnd: the one we just modified
	 * - 1 inputValue: the value pin of the linkEnd
	 */
	@Test
	public void testPinDerivation_ModificationLinkEndDestructionData() {
		SetRequest requestLinkEndDestruction = new SetRequest(this.editingDomain, this.linkEndDestructionDataA, UMLPackage.eINSTANCE.getLinkEndData_End(), this.association1.getMemberEnds().get(0));
		IElementEditService elementLinkEndDestructionEditService = ElementEditServiceUtils.getCommandProvider(this.linkEndDestructionDataA);
		ICommand editLinkEndDestructionDataCommand = elementLinkEndDestructionEditService.getEditCommand(requestLinkEndDestruction);
		if (!editLinkEndDestructionDataCommand.canExecute()) {
			Assert.fail("The LinkEndDestructionData cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editLinkEndDestructionDataCommand));
		
		SetRequest requestDestroyLinkAction = new SetRequest(this.editingDomain, this.destroyLinkAction, UMLPackage.eINSTANCE.getLinkAction_EndData(), this.linkEndDestructionDataA);
		IElementEditService elementDestroyLinkActionEditService = ElementEditServiceUtils.getCommandProvider(this.linkEndDestructionDataA);
		ICommand editDestroyLinkActionCommand = elementDestroyLinkActionEditService.getEditCommand(requestDestroyLinkAction);
		if (!editDestroyLinkActionCommand.canExecute()) {
			Assert.fail("The DestroyLinkAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editDestroyLinkActionCommand));
		
		SetRequest requestLinkEndDestruction2 = new SetRequest(this.editingDomain, this.destroyLinkAction.getEndData().get(0), UMLPackage.eINSTANCE.getLinkEndData_End(), this.association2.getMemberEnds().get(1));
		IElementEditService elementLinkEndDestructionEditService2 = ElementEditServiceUtils.getCommandProvider(this.linkEndDestructionDataA);
		ICommand editLinkEndDestructionDataCommand2 = elementLinkEndDestructionEditService2.getEditCommand(requestLinkEndDestruction2);
		if (!editLinkEndDestructionDataCommand2.canExecute()) {
			Assert.fail("The LinkEndDestructionData cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editLinkEndDestructionDataCommand2));
		
		// Assert endData
		Assert.assertTrue("DestroyLinkAction shall have 1 endData", this.destroyLinkAction.getEndData().size() == 1);
		Assert.assertTrue("DestroyLinkAction shall have a endData: LinkEndDestructionDataA", this.destroyLinkAction.getEndData().get(0).getEnd().getType() == classC);
			
		// Assert input value
		Assert.assertTrue("DestroyLinkAction shall have 1 inputValue", this.destroyLinkAction.getInputValues().size() == 1);
		Assert.assertTrue("DestroyLinkAction shall have a inputValue same as in the LinkEndDestructionData", this.destroyLinkAction.getInputValues().get(0).equals(this.linkEndDestructionDataA.getValue()));
	
		if (!editLinkEndDestructionDataCommand2.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation (modification of LinkEndDestructionData)");
		}	
		if (!editDestroyLinkActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation (DestroyLinkAction)");
		}
		if (!editLinkEndDestructionDataCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation (LinkEndDestructionData)");
		}
		this.editingDomain.getCommandStack().undo();
	}
	
	/**
	 * Role:
	 * - For a DestroyLinkAction having already 2 linkEndData, ensure that if an end data is modified then the pins value are inferred.
	 * 
	 * Scenario:
	 * - 1] LinkEndDestructionDataA end feature is set with the association end a
	 * - 2] Synchronization is performed via the advice
	 * - LinkEndDestructionData has now 1 value pin
	 * 
	 * - 3] DestroyLinkAction 'endData' feature is set with the LinkEndDestructionDataA
	 * - 4] Synchronization is performed via the advice
	 * - DestroyLinkAction has now:
	 * - 2 linkEnd: the one we just add, an other on for the second end of the link (a and b)
	 * - 2 inputValue: one for each linkEnd
	 * 
	 * - 5] the type of the LinkEndDestructionDataA first end is set with classC
	 * - 4] Synchronization is performed via the advice
	 * - DestroyLinkAction has now:
	 * - 1 linkEnd: the one we just add
	 * - 1 inputValue: the value pin of the linkEnd
	 */
	@Test
	public void testPinDerivation_ModificationPropertyOfLinkEndDestructionData() {
		SetRequest requestLinkEndDestruction = new SetRequest(this.editingDomain, this.linkEndDestructionDataA, UMLPackage.eINSTANCE.getLinkEndData_End(), this.association1.getMemberEnds().get(0));
		IElementEditService elementLinkEndDestructionEditService = ElementEditServiceUtils.getCommandProvider(this.linkEndDestructionDataA);
		ICommand editLinkEndDestructionDataCommand = elementLinkEndDestructionEditService.getEditCommand(requestLinkEndDestruction);
		if (!editLinkEndDestructionDataCommand.canExecute()) {
			Assert.fail("The LinkEndDestructionData cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editLinkEndDestructionDataCommand));
		
		SetRequest requestDestroyLinkAction = new SetRequest(this.editingDomain, this.destroyLinkAction, UMLPackage.eINSTANCE.getLinkAction_EndData(), this.linkEndDestructionDataA);
		IElementEditService elementDestroyLinkActionEditService = ElementEditServiceUtils.getCommandProvider(this.linkEndDestructionDataA);
		ICommand editDestroyLinkActionCommand = elementDestroyLinkActionEditService.getEditCommand(requestDestroyLinkAction);
		if (!editDestroyLinkActionCommand.canExecute()) {
			Assert.fail("The DestroyLinkAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editDestroyLinkActionCommand));
		
		SetRequest requestLinkEndDestruction2 = new SetRequest(this.editingDomain, this.destroyLinkAction.getEndData().get(0).getEnd(), UMLPackage.eINSTANCE.getTypedElement_Type(), classC);
		IElementEditService elementLinkEndDestructionEditService2 = ElementEditServiceUtils.getCommandProvider(this.linkEndDestructionDataA);
		ICommand editLinkEndDestructionDataCommand2 = elementLinkEndDestructionEditService2.getEditCommand(requestLinkEndDestruction2);
		if (!editLinkEndDestructionDataCommand2.canExecute()) {
			Assert.fail("The LinkEndDestructionData cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editLinkEndDestructionDataCommand2));
		
		// Assert endData
		Assert.assertTrue("DestroyLinkAction shall have 1 endData", this.destroyLinkAction.getEndData().size() == 1);
		Assert.assertTrue("DestroyLinkAction shall have a endData which type is classC", this.destroyLinkAction.getEndData().get(0).getEnd().getType() == classC);
				
		// Assert input value
		Assert.assertTrue("DestroyLinkAction shall have 1 inputValue", this.destroyLinkAction.getInputValues().size() == 1);
		Assert.assertTrue("DestroyLinkAction shall have a inputValue which type is classC", this.destroyLinkAction.getInputValues().get(0).getType() == classC);
			
		if (!editLinkEndDestructionDataCommand2.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation (modification of property)");
		}	
		if (!editDestroyLinkActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation (DestroyLinkAction)");
		}
		if (!editLinkEndDestructionDataCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation (LinkEndDestructionData)");
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
		// Create LinkEndDestructionData
		linkEndDestructionDataA = UMLFactory.eINSTANCE.createLinkEndDestructionData();
		
		// Create activity containing the DestroyLinkAction
		Activity activity = UMLFactory.eINSTANCE.createActivity();
		this.destroyLinkAction = UMLFactory.eINSTANCE.createDestroyLinkAction();
		activity.getOwnedNodes().add(this.destroyLinkAction);
		
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
