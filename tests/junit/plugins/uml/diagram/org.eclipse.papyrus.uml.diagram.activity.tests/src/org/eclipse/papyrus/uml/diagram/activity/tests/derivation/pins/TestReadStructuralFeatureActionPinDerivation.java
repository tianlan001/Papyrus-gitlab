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
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.ReadStructuralFeatureAction;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * Pins of ReadStructuralFeatureAction should be create and update automatically
 * 
 */
public class TestReadStructuralFeatureActionPinDerivation extends AbstractTestPinDerivation {

	/**
	 * ReadStructuralFeatureAction advice identifier
	 */
	private final String TARGET_ADVICE_IDENTIFIER = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.ReadStructuralFeatureActionEditHelperAdvice";

	private final String TARGET_ADVICE_IDENTIFIER_FOR_PROPERTY = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.PropertyEditHelperAdvice";
	
	private ReadStructuralFeatureAction readStructuralFeatureAction;

	private Class portClass;
	
	private Port port; 
	
	private Class propertyClass;
	
	private Property property; 


	/**
	 * Constructor.
	 */
	public TestReadStructuralFeatureActionPinDerivation() {
		this.port = null;
		this.property = null;
		this.populateBaseTestModel();
		// Set Automated Model Completion preference to PIN_SYNCHRONIZATION
		IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		prefStore.setValue(IAutomatedModelCompletionPreferencesConstants.READ_STRUCTURAL_FEATURE_ACTION_ACCELERATOR, AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION);
	}

	/**
	 * Role:
	 * - Ensure that the advice for the ReadStructuralFeatureAction is available
	 */
	@Test
	public void testAdviceForCallOperationActionExists() {
		Assert.assertNotNull("ReadStructuralFeatureAction advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER));
		Assert.assertNotNull("Property advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER_FOR_PROPERTY));
	}
	
	/**
	 * Role:
	 * - For a ReadStructuralFeatureAction having no structural feature, ensure that if a structural feature is referenced then the pins corresponding to the
	 * structural feature are inferred.
	 * 
	 * Scenario:
	 * - 1] readStructuralFeatureAction 'structuralFeature' feature is set with the Property 'property'
	 * - 2] Synchronization is performed via the advice
	 * - readStructuralFeatureAction has now 2 pins
	 * - 1 object ([in] typed by propertyClass)
	 * - 1 result ([out] typed by Integer, with multiplicity of P1)
	 */
	@Test
	public void testPinDerivation_NoStructuralFeature_To_PropertyStructuralFeature() {
		SetRequest request = new SetRequest(this.editingDomain, this.readStructuralFeatureAction, UMLPackage.eINSTANCE.getStructuralFeatureAction_StructuralFeature(), this.property);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.readStructuralFeatureAction);
		ICommand editReadStructuralFeatureActionCommand = elementEditService.getEditCommand(request);
		if (!editReadStructuralFeatureActionCommand.canExecute()) {
			Assert.fail("The ReadStructuralFeatureAction cannot be edited (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editReadStructuralFeatureActionCommand));
		
		// Assert object
		Assert.assertTrue("ReadStructuralFeatureAction shall have a object", this.readStructuralFeatureAction.getObject() != null);
		Assert.assertTrue("ReadStructuralFeatureAction shall have an object pin and this pin shall have a type", this.readStructuralFeatureAction.getObject().getType() == propertyClass);
		
		// Assert result pins
		Assert.assertTrue("ReadStructuralFeatureAction shall have one result pin", this.readStructuralFeatureAction.getResult() != null);
		Assert.assertTrue("ReadStructuralFeatureAction result pins shall typed by Integer", this.readStructuralFeatureAction.getResult().getType() == this.getPrimitiveType("Integer"));
		Assert.assertTrue("ReadStructuralFeatureAction result pins shall with upper multiplicity of 10", this.readStructuralFeatureAction.getResult().getUpper() == 10);
		Assert.assertTrue("ReadStructuralFeatureAction result pins shall with lower multiplicity of 1", this.readStructuralFeatureAction.getResult().getLower() == 1);
		
		if (!editReadStructuralFeatureActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}
	
	/**
	 * Role:
	 * - For a ReadStructuralFeatureAction having no structural feature, ensure that if a structural feature is referenced then the pins corresponding to the
	 * structural feature are inferred.
	 * 
	 * Scenario:
	 * - 1] readStructuralFeatureAction 'structuralFeature' feature is set with the Port 'port'
	 * - 2] Synchronization is performed via the advice
	 * - readStructuralFeatureAction has now 2 pins
	 * - 1 object ([in] typed by portClass)
	 * - 1 result ([out] typed by String)
	 */
	@Test
	public void testPinDerivation_NoStructuralFeature_To_PortStructuralFeature() {
		SetRequest request = new SetRequest(this.editingDomain, this.readStructuralFeatureAction, UMLPackage.eINSTANCE.getStructuralFeatureAction_StructuralFeature(), this.port);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.readStructuralFeatureAction);
		ICommand editReadStructuralFeatureActionCommand = elementEditService.getEditCommand(request);
		if (!editReadStructuralFeatureActionCommand.canExecute()) {
			Assert.fail("The ReadStructuralFeatureAction cannot be edited (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editReadStructuralFeatureActionCommand));
		
		// Assert object
		Assert.assertTrue("ReadStructuralFeatureAction shall have a object", this.readStructuralFeatureAction.getObject() != null);
		Assert.assertTrue("ReadStructuralFeatureAction shall have an object pin and this pin shall have a type", this.readStructuralFeatureAction.getObject().getType() == portClass);
		
		// Assert result pins
		Assert.assertTrue("ReadStructuralFeatureAction shall have one result pin", this.readStructuralFeatureAction.getResult() != null);
		Assert.assertTrue("ReadStructuralFeatureAction result pins shall typed by Integer", this.readStructuralFeatureAction.getResult().getType() == this.getPrimitiveType("String"));
		Assert.assertTrue("ReadStructuralFeatureAction result pins shall with upper multiplicity of 2", this.readStructuralFeatureAction.getResult().getUpper() == 2);
		Assert.assertTrue("ReadStructuralFeatureAction result pins shall with lower multiplicity of 0", this.readStructuralFeatureAction.getResult().getLower() == 0);
		
		if (!editReadStructuralFeatureActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}
	
	/**
	 * Role:
	 * - For a ReadStructuralFeatureAction having structural feature, ensure that if the structural feature  type is change then the pins corresponding to the
	 * structural feature are inferred.
	 * 
	 * Scenario:
	 * - 1] readStructuralFeatureAction 'structuralFeature' feature is set with the Property 'property'
	 * - 2] Synchronization is performed via the advice
	 * - readStructuralFeatureAction has now 2 pins
	 * - 1 object ([in] typed by portClass)
	 * - 1 result ([out] typed by String)
	 * - 3] property type is set to "Boolean"
	 * - readStructuralFeatureAction has now 2 pins
	 * - 1 object ([in] typed by propertyClass)
	 * - 1 result ([out] typed by Boolean)
	 */
	@Test
	public void testPinDerivation_changePropertyStructuralFeatureType() {
		SetRequest request = new SetRequest(this.editingDomain, this.readStructuralFeatureAction, UMLPackage.eINSTANCE.getStructuralFeatureAction_StructuralFeature(), this.property);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.readStructuralFeatureAction);
		ICommand editReadStructuralFeatureActionCommand = elementEditService.getEditCommand(request);
		if (!editReadStructuralFeatureActionCommand.canExecute()) {
			Assert.fail("The ReadStructuralFeatureAction cannot be edited (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editReadStructuralFeatureActionCommand));
		
		// Change port type
		SetRequest editPropertyRequest = new SetRequest(this.editingDomain, this.property, UMLPackage.eINSTANCE.getTypedElement_Type(), this.getPrimitiveType("Boolean")); 
		IElementEditService editPropertyElementEditService = ElementEditServiceUtils.getCommandProvider(this.readStructuralFeatureAction);
		ICommand editPropertyCommand = editPropertyElementEditService.getEditCommand(editPropertyRequest);
		if (!editPropertyCommand.canExecute()) {
			Assert.fail("The attribut type of property cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editPropertyCommand));
		
		// Assert result pins
		Assert.assertTrue("ReadStructuralFeatureAction shall have one result pin", this.readStructuralFeatureAction.getResult() != null);
		Assert.assertTrue("ReadStructuralFeatureAction result pins shall typed by Integer", this.readStructuralFeatureAction.getResult().getType() == this.getPrimitiveType("Boolean"));
		
		if (!editPropertyCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
		if (!editReadStructuralFeatureActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}
	
	/**
	 * Role:
	 * - For a ReadStructuralFeatureAction having structural feature, ensure that if the structural feature  type is change then the pins corresponding to the
	 * structural feature are inferred.
	 * 
	 * Scenario:
	 * - 1] readStructuralFeatureAction 'structuralFeature' feature is set with the Port 'port'
	 * - 2] Synchronization is performed via the advice
	 * - readStructuralFeatureAction has now 2 pins
	 * - 1 object ([in] typed by portClass)
	 * - 1 result ([out] typed by String)
	 * - 3] port type is set to "Boolean"
	 * - readStructuralFeatureAction has now 2 pins
	 * - 1 object ([in] typed by portClass)
	 * - 1 result ([out] typed by Boolean)
	 */
	@Test
	public void testPinDerivation_changePortStructuralFeatureType() {
		SetRequest request = new SetRequest(this.editingDomain, this.readStructuralFeatureAction, UMLPackage.eINSTANCE.getStructuralFeatureAction_StructuralFeature(), this.port);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.readStructuralFeatureAction);
		ICommand editReadStructuralFeatureActionCommand = elementEditService.getEditCommand(request);
		if (!editReadStructuralFeatureActionCommand.canExecute()) {
			Assert.fail("The ReadStructuralFeatureAction cannot be edited (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editReadStructuralFeatureActionCommand));

		// Change port type
		SetRequest editPortRequest = new SetRequest(this.editingDomain, this.port, UMLPackage.eINSTANCE.getTypedElement_Type(), this.getPrimitiveType("Boolean")); 
		IElementEditService editPortElementEditService = ElementEditServiceUtils.getCommandProvider(this.readStructuralFeatureAction);
		ICommand editPortCommand = editPortElementEditService.getEditCommand(editPortRequest);
		if (!editPortCommand.canExecute()) {
			Assert.fail("The attribut type of port cannot be edited  (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editPortCommand));
		
		// Assert result pins
		Assert.assertTrue("ReadStructuralFeatureAction shall have one result pin", this.readStructuralFeatureAction.getResult() != null);
		Assert.assertTrue("ReadStructuralFeatureAction result pins shall typed by Integer", this.readStructuralFeatureAction.getResult().getType() == this.getPrimitiveType("Boolean"));
		
		if (!editPortCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
		if (!editReadStructuralFeatureActionCommand.canUndo()) {
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
		// Define the property
		this.property = UMLFactory.eINSTANCE.createProperty();
		this.property.setName("p1");
		this.property.setUpper(10);
		this.property.setLower(1);
		this.property.setType(this.getPrimitiveType("Integer"));
		
		// Define the port
		this.port = UMLFactory.eINSTANCE.createPort();
		this.port.setUpper(2);
		this.port.setLower(0);
		this.port.setType(this.getPrimitiveType("String"));

		// Create propertyClass with a property
		propertyClass = UMLFactory.eINSTANCE.createClass();
		propertyClass.setName("C1");
		propertyClass.getOwnedAttributes().add(property);
		
		// Create portClass with a port
		portClass = UMLFactory.eINSTANCE.createClass();
		portClass.setName("C2");
		portClass.getOwnedPorts().add(port);
		
		// Create the activity containing the readStructuralFeatureAction
		Activity activity = UMLFactory.eINSTANCE.createActivity();
		this.readStructuralFeatureAction = UMLFactory.eINSTANCE.createReadStructuralFeatureAction();
		this.readStructuralFeatureAction.setStructuralFeature(port);
		activity.getOwnedNodes().add(this.readStructuralFeatureAction);
		
		// Add the different elements to the model
		this.umlTestModel.getPackagedElements().add(propertyClass);
		this.umlTestModel.getPackagedElements().add(portClass);
		this.umlTestModel.getPackagedElements().add(activity);
		
		// Add the test model within the model set
		Resource umlTestModelResource = new ResourceImpl();
		umlTestModelResource.setURI(URI.createURI("UMLBaseTestModel.tmp"));
		umlTestModelResource.getContents().add(this.umlTestModel);
		this.modelSet.getResources().add(umlTestModelResource);
	}
}
