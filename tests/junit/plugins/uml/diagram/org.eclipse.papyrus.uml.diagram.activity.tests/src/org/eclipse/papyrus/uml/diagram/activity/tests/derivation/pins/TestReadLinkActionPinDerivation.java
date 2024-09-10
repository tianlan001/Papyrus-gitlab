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
import org.eclipse.uml2.uml.ReadLinkAction;
import org.eclipse.uml2.uml.LinkEndData;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * Pins of ReadLinkAction should be create and update automatically
 *
 */
public class TestReadLinkActionPinDerivation extends AbstractTestPinDerivation {
	
	/**
	 * ReadLinkAction advice identifier
	 */
	private final String TARGET_ADVICE_IDENTIFIER = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.ReadLinkActionEditHelperAdvice";

	private final String TARGET_ADVICE_IDENTIFIER_FOR_LINKENDDATA = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.LinkEndDataEditHelperAdvice";

	private final String TARGET_ADVICE_IDENTIFIER_FOR_PROPERTY = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.PropertyEditHelperAdvice";
	
	private ReadLinkAction readLinkAction;

	private Association association1;

	private Association association2;
	
	private LinkEndData linkEndDataA;

	private Class classA;

	private Class classB;

	private Class classC;

	/**
	 * Constructor.
	 *
	 */
	public TestReadLinkActionPinDerivation() {
		this.readLinkAction = null;
		this.association1 = null;
		this.linkEndDataA = null;
		this.classA = null;
		this.classB = null;
		this.populateBaseTestModel();
		// Set Automated Model Completion preference to PIN_SYNCHRONIZATION
		IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		prefStore.setValue(IAutomatedModelCompletionPreferencesConstants.READ_LINK_ACTION_ACCELERATOR, AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION);
	}
	
	/**
	 * Role:
	 * - Ensure that the advice for the ReadLinkAction is available
	 */
	@Test
	public void testAdviceForReadLinkActionExists() {
		Assert.assertNotNull("ReadLinkAction advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER));
		Assert.assertNotNull("LinkEndData advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER_FOR_LINKENDDATA));
		Assert.assertNotNull("Property advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER_FOR_PROPERTY));
	}
	
	/**
	 * Role:
	 * - For a ReadLinkAction having no end data, ensure that if an end data is referenced then the pins value are inferred.
	 * 
	 * Scenario:
	 * - 1] linkEndData end feature is set with the association end a
	 * - 2] Synchronization is performed via the advice
	 * - linkEndData has now 1 pin
	 * - 1 value pin
	 * 
	 * - 3] ReadLinkAction 'endData' feature is set with the linkEndData
	 * - 4] Synchronization is performed via the advice
	 * - ReadLinkAction has now:
	 * - 2 linkEnd: the one we just add, an other on for the second end of the link (a and b)
	 * - 1 inputValue: the value pin of the add linkEndData (a)
	 * - 1 result pin corresponding to the open end (b)
	 */
	@Test
	public void testPinDerivation_NoEndData_To_EndData() {
		SetRequest requestLinkEnd = new SetRequest(this.editingDomain, this.linkEndDataA, UMLPackage.eINSTANCE.getLinkEndData_End(), this.association1.getMemberEnds().get(0));
		IElementEditService elementLinkEndEditService = ElementEditServiceUtils.getCommandProvider(this.linkEndDataA);
		ICommand editLinkEndDataCommand = elementLinkEndEditService.getEditCommand(requestLinkEnd);
		if (!editLinkEndDataCommand.canExecute()) {
			Assert.fail("The LinkEndData cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editLinkEndDataCommand));
		
		// Assert value
		Assert.assertTrue("LinkEndData shall have a value pin", this.linkEndDataA.getValue() != null);
		Assert.assertTrue("LinkEndData shall have a value pin typed by classA", this.linkEndDataA.getValue().getType() == classA);
		
		SetRequest requestReadLinkAction = new SetRequest(this.editingDomain, this.readLinkAction, UMLPackage.eINSTANCE.getLinkAction_EndData(), this.linkEndDataA);
		IElementEditService elementReadLinkActionEditService = ElementEditServiceUtils.getCommandProvider(this.linkEndDataA);
		ICommand editReadLinkActionCommand = elementReadLinkActionEditService.getEditCommand(requestReadLinkAction);
		if (!editReadLinkActionCommand.canExecute()) {
			Assert.fail("The ReadLinkAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editReadLinkActionCommand));
		
		// Assert endData
		Assert.assertTrue("ReadLinkAction shall have 2 endData", this.readLinkAction.getEndData().size() == 2);
		Assert.assertTrue("ReadLinkAction shall have a endData: linkEndDataA", this.readLinkAction.getEndData().get(0).equals(linkEndDataA));
		Assert.assertTrue("ReadLinkAction shall have a endData corresponding to the other end of the association1", this.readLinkAction.getEndData().get(1).getEnd().equals(linkEndDataA.getEnd().getOtherEnd()));
				
		// Assert input value
		Assert.assertTrue("ReadLinkAction shall have 1 inputValue", this.readLinkAction.getInputValues().size() == 1);
		Assert.assertTrue("ReadLinkAction shall have a inputValue same as in the linkEndData", this.readLinkAction.getInputValues().get(0).equals(this.linkEndDataA.getValue()));
		
		// Assert result
		Assert.assertTrue("ReadLinkAction shall have a result pin", this.readLinkAction.getResult() != null);
		Assert.assertTrue("ReadLinkAction shall have a result pin typed by classB", this.readLinkAction.getResult().getType() == classB);
						
		if (!editReadLinkActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation (ReadLinkAction)");
		}
		if (!editLinkEndDataCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation (LinkEndData)");
		}
		this.editingDomain.getCommandStack().undo();
	}
	
	/**
	 * Role:
	 * - For a ReadLinkAction having already 2 linkEndData, ensure that if an end data is modified then the pins value are inferred.
	 * 
	 * Scenario:
	 * - 1] linkEndDataA end feature is set with the association end a
	 * - 2] Synchronization is performed via the advice
	 * - linkEndData has now 1 value pin
	 * 
	 * - 3] ReadLinkAction 'endData' feature is set with the linkEndData
	 * - 4] Synchronization is performed via the advice
	 * - ReadLinkAction has now:
	 * - 2 linkEnd: the one we just add, an other on for the second end of the link (a and b)
	 * - 1 inputValue: the value pin of the add linkEndData (a)
	 * - 1 result pin corresponding to the open end (b)
	 * 
	 * - 5] linkEndDataA end feature is set with the association end c (not the open end)
	 * - 4] Synchronization is performed via the advice
	 * - ReadLinkAction has now:
	 * - 2 linkEnd: the one we just add, an other on for the second end of the link (c and a)
	 * - 1 inputValue: the value pin of the add linkEndData (c)
	 * - 1 result pin corresponding to the open end (a)
	 */
	@Test
	public void testPinDerivation_ModificationLinkEndData_NotOpenEnd() {
		SetRequest requestLinkEnd = new SetRequest(this.editingDomain, this.linkEndDataA, UMLPackage.eINSTANCE.getLinkEndData_End(), this.association1.getMemberEnds().get(0));
		IElementEditService elementLinkEndEditService = ElementEditServiceUtils.getCommandProvider(this.linkEndDataA);
		ICommand editLinkEndDataCommand = elementLinkEndEditService.getEditCommand(requestLinkEnd);
		if (!editLinkEndDataCommand.canExecute()) {
			Assert.fail("The LinkEndData cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editLinkEndDataCommand));
		
		SetRequest requestReadLinkAction = new SetRequest(this.editingDomain, this.readLinkAction, UMLPackage.eINSTANCE.getLinkAction_EndData(), this.linkEndDataA);
		IElementEditService elementReadLinkActionEditService = ElementEditServiceUtils.getCommandProvider(this.linkEndDataA);
		ICommand editReadLinkActionCommand = elementReadLinkActionEditService.getEditCommand(requestReadLinkAction);
		if (!editReadLinkActionCommand.canExecute()) {
			Assert.fail("The ReadLinkAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editReadLinkActionCommand));
		
		SetRequest requestLinkEnd2 = new SetRequest(this.editingDomain, this.readLinkAction.getEndData().get(0), UMLPackage.eINSTANCE.getLinkEndData_End(), this.association2.getMemberEnds().get(1));
		IElementEditService elementLinkEndEditService2 = ElementEditServiceUtils.getCommandProvider(this.linkEndDataA);
		ICommand editLinkEndDataCommand2 = elementLinkEndEditService2.getEditCommand(requestLinkEnd2);
		if (!editLinkEndDataCommand2.canExecute()) {
			Assert.fail("The LinkEndData cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editLinkEndDataCommand2));
		
		// Assert endData
		Assert.assertTrue("ReadLinkAction shall have 2 endData", this.readLinkAction.getEndData().size() == 2);
		Assert.assertTrue("ReadLinkAction shall have a endData: linkEndDataA", this.readLinkAction.getEndData().get(0).getEnd().getType() == classC);
		Assert.assertTrue("ReadLinkAction shall have a endData corresponding to the other end of the association2", this.readLinkAction.getEndData().get(1).getEnd().getType() == classA);
				
		// Assert input value
		Assert.assertTrue("ReadLinkAction shall have 1 inputValue", this.readLinkAction.getInputValues().size() == 1);
		Assert.assertTrue("ReadLinkAction shall have a inputValue same as in the linkEndData", this.readLinkAction.getInputValues().get(0).getType() == classC);
		
		// Assert result
		Assert.assertTrue("ReadLinkAction shall have a result pin", this.readLinkAction.getResult() != null);
		Assert.assertTrue("ReadLinkAction shall have a result pin typed by classB", this.readLinkAction.getResult().getType() == classA);
						
		if (!editLinkEndDataCommand2.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation (modification of LinkEndData)");
		}	
		if (!editReadLinkActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation (ReadLinkAction)");
		}
		if (!editLinkEndDataCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation (LinkEndData)");
		}
		this.editingDomain.getCommandStack().undo();
	}
	
	/**
	 * Role:
	 * - For a ReadLinkAction having already 2 linkEndData, ensure that if an end data is modified then the pins value are inferred.
	 * 
	 * Scenario:
	 * - 1] linkEndDataA end feature is set with the association end a
	 * - 2] Synchronization is performed via the advice
	 * - linkEndData has now 1 value pin
	 * 
	 * - 3] ReadLinkAction 'endData' feature is set with the linkEndData
	 * - 4] Synchronization is performed via the advice
	 * - ReadLinkAction has now:
	 * - 2 linkEnd: the one we just add, an other on for the second end of the link (a and b)
	 * - 1 inputValue: the value pin of the add linkEndData (a)
	 * - 1 result pin corresponding to the open end (b)
	 * 
	 * - 5] linkEndDataA end feature is set with the association end c (the open end)
	 * - 4] Synchronization is performed via the advice
	 * - ReadLinkAction has now:
	 * - 2 linkEnd: the one we just add, an other on for the second end of the link (c and a)
	 * - 1 inputValue: the value pin of the add linkEndData (c)
	 * - 1 result pin corresponding to the open end (a)
	 */
	@Test
	public void testPinDerivation_ModificationLinkEndData_OpenEnd() {
		SetRequest requestLinkEnd = new SetRequest(this.editingDomain, this.linkEndDataA, UMLPackage.eINSTANCE.getLinkEndData_End(), this.association1.getMemberEnds().get(0));
		IElementEditService elementLinkEndEditService = ElementEditServiceUtils.getCommandProvider(this.linkEndDataA);
		ICommand editLinkEndDataCommand = elementLinkEndEditService.getEditCommand(requestLinkEnd);
		if (!editLinkEndDataCommand.canExecute()) {
			Assert.fail("The LinkEndData cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editLinkEndDataCommand));
		
		SetRequest requestReadLinkAction = new SetRequest(this.editingDomain, this.readLinkAction, UMLPackage.eINSTANCE.getLinkAction_EndData(), this.linkEndDataA);
		IElementEditService elementReadLinkActionEditService = ElementEditServiceUtils.getCommandProvider(this.linkEndDataA);
		ICommand editReadLinkActionCommand = elementReadLinkActionEditService.getEditCommand(requestReadLinkAction);
		if (!editReadLinkActionCommand.canExecute()) {
			Assert.fail("The ReadLinkAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editReadLinkActionCommand));
		
		SetRequest requestLinkEnd2 = new SetRequest(this.editingDomain, this.readLinkAction.getEndData().get(1), UMLPackage.eINSTANCE.getLinkEndData_End(), this.association2.getMemberEnds().get(0));
		IElementEditService elementLinkEndEditService2 = ElementEditServiceUtils.getCommandProvider(this.linkEndDataA);
		ICommand editLinkEndDataCommand2 = elementLinkEndEditService2.getEditCommand(requestLinkEnd2);
		if (!editLinkEndDataCommand2.canExecute()) {
			Assert.fail("The LinkEndData cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editLinkEndDataCommand2));
		
		// Assert endData
		Assert.assertTrue("ReadLinkAction shall have 2 endData", this.readLinkAction.getEndData().size() == 2);
		Assert.assertTrue("ReadLinkAction shall have a endData: linkEndDataA", this.readLinkAction.getEndData().get(0).getEnd().getType() == classC);
		Assert.assertTrue("ReadLinkAction shall have a endData corresponding to the other end of the association2", this.readLinkAction.getEndData().get(1).getEnd().getType() == classA);
				
		// Assert input value
		Assert.assertTrue("ReadLinkAction shall have 1 inputValue", this.readLinkAction.getInputValues().size() == 1);
		Assert.assertTrue("ReadLinkAction shall have a inputValue same as in the linkEndData", this.readLinkAction.getInputValues().get(0).getType() == classC);
		
		// Assert result
		Assert.assertTrue("ReadLinkAction shall have a result pin", this.readLinkAction.getResult() != null);
		Assert.assertTrue("ReadLinkAction shall have a result pin typed by classB", this.readLinkAction.getResult().getType() == classA);
						
		if (!editLinkEndDataCommand2.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation (modification of LinkEndData)");
		}	
		if (!editReadLinkActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation (ReadLinkAction)");
		}
		if (!editLinkEndDataCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation (LinkEndData)");
		}
		this.editingDomain.getCommandStack().undo();
	}
	
	/**
	 * Role:
	 * - For a ReadLinkAction having already 2 linkEndData, ensure that if an end data is modified then the pins value are inferred.
	 * 
	 * Scenario:
	 * - 1] linkEndDataA end feature is set with the association end a
	 * - 2] Synchronization is performed via the advice
	 * - linkEndData has now 1 value pin
	 * 
	 * - 3] ReadLinkAction 'endData' feature is set with the linkEndData
	 * - 4] Synchronization is performed via the advice
	 * - ReadLinkAction has now:
	 * - 2 linkEnd: the one we just add, an other on for the second end of the link (a and b)
	 * - 1 inputValue: the value pin of the add linkEndData (a)
	 * - 1 result pin corresponding to the open end (b)
	 * 
	 * - 5] the type of the LinkEndDataA first end is set with classC
	 * - 4] Synchronization is performed via the advice
	 * - ReadLinkAction has now:
	 * - 2 linkEnd: the one we just add, an other on for the second end of the link (a and b)
	 * - 1 inputValue: the value pin of the add linkEndData (a type by classC)
	 * - 1 result pin corresponding to the open end (b)
	 */
	@Test
	public void testPinDerivation_ModificationPropertyOfLinkEndData() {
		SetRequest requestLinkEnd = new SetRequest(this.editingDomain, this.linkEndDataA, UMLPackage.eINSTANCE.getLinkEndData_End(), this.association1.getMemberEnds().get(0));
		IElementEditService elementLinkEndEditService = ElementEditServiceUtils.getCommandProvider(this.linkEndDataA);
		ICommand editLinkEndDataCommand = elementLinkEndEditService.getEditCommand(requestLinkEnd);
		if (!editLinkEndDataCommand.canExecute()) {
			Assert.fail("The LinkEndData cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editLinkEndDataCommand));
		
		SetRequest requestReadLinkAction = new SetRequest(this.editingDomain, this.readLinkAction, UMLPackage.eINSTANCE.getLinkAction_EndData(), this.linkEndDataA);
		IElementEditService elementReadLinkActionEditService = ElementEditServiceUtils.getCommandProvider(this.linkEndDataA);
		ICommand editReadLinkActionCommand = elementReadLinkActionEditService.getEditCommand(requestReadLinkAction);
		if (!editReadLinkActionCommand.canExecute()) {
			Assert.fail("The ReadLinkAction cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editReadLinkActionCommand));
		
		SetRequest requestLinkEnd2 = new SetRequest(this.editingDomain, this.readLinkAction.getEndData().get(0).getEnd(), UMLPackage.eINSTANCE.getTypedElement_Type(), classC);
		IElementEditService elementLinkEndEditService2 = ElementEditServiceUtils.getCommandProvider(this.linkEndDataA);
		ICommand editLinkEndDataCommand2 = elementLinkEndEditService2.getEditCommand(requestLinkEnd2);
		if (!editLinkEndDataCommand2.canExecute()) {
			Assert.fail("The LinkEndData cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editLinkEndDataCommand2));
		
		// Assert endData
		Assert.assertTrue("ReadLinkAction shall have 2 endData", this.readLinkAction.getEndData().size() == 2);
		Assert.assertTrue("ReadLinkAction shall have a endData which type is classC", this.readLinkAction.getEndData().get(0).getEnd().getType() == classC);
		Assert.assertTrue("ReadLinkAction shall have a endData corresponding to the other end of the association1", this.readLinkAction.getEndData().get(1).getEnd().getType() == classB);
				
		// Assert input value
		Assert.assertTrue("ReadLinkAction shall have 1 inputValue", this.readLinkAction.getInputValues().size() == 1);
		Assert.assertTrue("ReadLinkAction shall have a inputValue same as in the linkEndData", this.readLinkAction.getInputValues().get(0).getType() == classC);
		
		// Assert result
		Assert.assertTrue("ReadLinkAction shall have a result pin", this.readLinkAction.getResult() != null);
		Assert.assertTrue("ReadLinkAction shall have a result pin typed by classB", this.readLinkAction.getResult().getType() == classB);
				
		if (!editLinkEndDataCommand2.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation (modification of property)");
		}	
		if (!editReadLinkActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation (ReadLinkAction)");
		}
		if (!editLinkEndDataCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation (LinkEndData)");
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
		// Create LinkEndData
		linkEndDataA = UMLFactory.eINSTANCE.createLinkEndData();
		
		// Create activity containing the ReadLinkAction
		Activity activity = UMLFactory.eINSTANCE.createActivity();
		this.readLinkAction = UMLFactory.eINSTANCE.createReadLinkAction();
		activity.getOwnedNodes().add(this.readLinkAction);
		
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
