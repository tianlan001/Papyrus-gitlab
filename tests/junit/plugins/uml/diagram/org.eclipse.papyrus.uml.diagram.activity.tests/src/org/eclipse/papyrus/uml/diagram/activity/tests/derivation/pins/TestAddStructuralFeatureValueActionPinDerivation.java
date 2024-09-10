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
 *  (CEA LIST) - Initial API and implementation
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
import org.eclipse.uml2.uml.AddStructuralFeatureValueAction;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * Pins of AddStructuralFeatureValueAction should be create and update automatically
 * 
 */
public class TestAddStructuralFeatureValueActionPinDerivation extends AbstractTestPinDerivation {

	private final String TARGET_ADVICE_IDENTIFIER = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.AddStructuralFeatureValueActionEditHelperAdvice";

	private final String TARGET_ADVICE_IDENTIFIER_FOR_PROPERTY = "org.eclipse.papyrus.uml.diagram.activity.edit.advices.PropertyEditHelperAdvice";

	private AddStructuralFeatureValueAction addStructuralFeatureValueAction;

	private Class portClass;
	
	private Port port;
	
	private Class propertyClass;
	
	private Property property;
	/**
	 * Constructor.
	 */
	public TestAddStructuralFeatureValueActionPinDerivation() {
		this.portClass = null;
		this.port = null;
		this.propertyClass = null;
		this.property = null;
		this.populateBaseTestModel();
		// Set Automated Model Completion preference to PIN_SYNCHRONIZATION
		IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		prefStore.setValue(IAutomatedModelCompletionPreferencesConstants.ADD_STRUCTURAL_FEATURE_VALUE_ACTION_ACCELERATOR, AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION);
	}

	/**
	 * Role:
	 * - Ensure that the advice for the AddStructuralFeatureValueAction is available
	 */
	@Test
	public void testAdviceForAddStructuraFeatureActionExists() {
		Assert.assertNotNull("AddStructuralFeatureValueAction advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER));
		Assert.assertNotNull("Property advice for activity models is missing", this.getAdvice(TARGET_ADVICE_IDENTIFIER_FOR_PROPERTY));
	}

	/**
	 * Role:
	 * - For a AddStructuralFeatureValueAction having no structural feature, ensure that if a structural feature is referenced then the pins corresponding to the
	 * structural feature are inferred.
	 * 
	 * Scenario:
	 * - 1] AddStructuralFeatureValueAction 'structuralFeature' feature is set with the Property 'property'
	 * - 2] Synchronization is performed via the advice
	 * - AddStructuralFeatureValueAction has now 4 pins
	 * - 1 object ([in] typed by propertyClass)
	 * - 1 result ([out] typed by propertyClass)
	 * - 1 value ([in] typed by Integer)
	 * - 1 insertAt ([in] typed by UnlimitedNatural)
	 */
	@Test
	public void testPinDerivation_NoStructuralFeature_To_PropertyStructuralFeature() {
		SetRequest request = new SetRequest(this.editingDomain, this.addStructuralFeatureValueAction, UMLPackage.eINSTANCE.getStructuralFeatureAction_StructuralFeature(), this.property);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.addStructuralFeatureValueAction);
		ICommand editAddStructuralFeatureValueActionCommand = elementEditService.getEditCommand(request);
		if (!editAddStructuralFeatureValueActionCommand.canExecute()) {
			Assert.fail("The AddStructuralFeatureValueAction cannot be edited (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editAddStructuralFeatureValueActionCommand));

		// Assert object pin
		Assert.assertTrue("AddStructuralFeatureValueAction shall have a object", this.addStructuralFeatureValueAction.getObject() != null);
		Assert.assertTrue("AddStructuralFeatureValueAction shall have an object pin typed by propertyClass", this.addStructuralFeatureValueAction.getObject().getType() == propertyClass);
		
		// Assert result pin
		Assert.assertTrue("AddStructuralFeatureValueAction shall have one result pin", this.addStructuralFeatureValueAction.getResult() != null);
		Assert.assertTrue("AddStructuralFeatureValueAction shall have a result pin typed by propertyClass", this.addStructuralFeatureValueAction.getResult().getType() == propertyClass);
		
		// Assert value pin
		Assert.assertTrue("AddStructuralFeatureValueAction shall have one value pin", this.addStructuralFeatureValueAction.getValue() != null);
		Assert.assertTrue("AddStructuralFeatureValueAction shall have a value pin typed by integer", this.addStructuralFeatureValueAction.getValue().getType() == this.getPrimitiveType("Integer"));
		
		// Assert insertAt pin
		Assert.assertTrue("AddStructuralFeatureValueAction shall have one insertAt pin", this.addStructuralFeatureValueAction.getInsertAt() != null);
		Assert.assertTrue("AddStructuralFeatureValueAction shall have an insertAt pin typed by UnlimitedNatural", this.addStructuralFeatureValueAction.getInsertAt().getType() == this.getPrimitiveType("UnlimitedNatural"));
		
		if (!editAddStructuralFeatureValueActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}
	
	/**
	 * Role:
	 * - For a AddStructuralFeatureValueAction having no structural feature, ensure that if a structural feature is referenced then the pins corresponding to the
	 * structural feature are inferred.
	 * 
	 * Scenario:
	 * - 1] AddStructuralFeatureValueAction 'structuralFeature' feature is set with the Port 'port'
	 * - 2] Synchronization is performed via the advice
	 * - AddStructuralFeatureValueAction has now 3 pins
	 * - 1 object ([in] typed by portClass)
	 * - 1 result ([out] typed by portClass)
	 * - 1 value ([in] typed by String)
	 */
	@Test
	public void testPinDerivation_NoStructuralFeature_To_PortStructuralFeature() {
		SetRequest request = new SetRequest(this.editingDomain, this.addStructuralFeatureValueAction, UMLPackage.eINSTANCE.getStructuralFeatureAction_StructuralFeature(), this.port);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.addStructuralFeatureValueAction);
		ICommand editAddStructuralFeatureValueActionCommand = elementEditService.getEditCommand(request);
		if (!editAddStructuralFeatureValueActionCommand.canExecute()) {
			Assert.fail("The AddStructuralFeatureValueAction cannot be edited (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editAddStructuralFeatureValueActionCommand));

		// Assert object pin
		Assert.assertTrue("AddStructuralFeatureValueAction shall have a object", this.addStructuralFeatureValueAction.getObject() != null);
		Assert.assertTrue("AddStructuralFeatureValueAction shall have an object pin typed by portClass", this.addStructuralFeatureValueAction.getObject().getType() == portClass);
		
		// Assert result pin
		Assert.assertTrue("AddStructuralFeatureValueAction shall have one result pin", this.addStructuralFeatureValueAction.getResult() != null);
		Assert.assertTrue("AddStructuralFeatureValueAction shall have a result pin typed by portClass", this.addStructuralFeatureValueAction.getResult().getType() == portClass);
		
		// Assert value pin
		Assert.assertTrue("AddStructuralFeatureValueAction shall have one value pin", this.addStructuralFeatureValueAction.getValue() != null);
		Assert.assertTrue("AddStructuralFeatureValueAction shall have a value pin typed by integer", this.addStructuralFeatureValueAction.getValue().getType() == this.getPrimitiveType("String"));
		
		// Assert insertAt pin
		Assert.assertTrue("AddStructuralFeatureValueAction shall have not insertAt pin", this.addStructuralFeatureValueAction.getInsertAt() == null);
		
		if (!editAddStructuralFeatureValueActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}
	
	/**
	 * Role:
	 * - For a AddStructuralFeatureValueAction having structural feature, ensure that if the structural feature  type is change then the pins corresponding to the
	 * structural feature are inferred.
	 * 
	 * Scenario:
	 * - 1] AddStructuralFeatureValueAction 'structuralFeature' feature is set with the Port 'port'
	 * - 2] Synchronization is performed via the advice
	 * - AddStructuralFeatureValueAction has now 4 pins
	 * - 1 object ([in] typed by propertyClass)
	 * - 1 result ([out] typed by propertyClass)
	 * - 1 value ([in] typed by Integer)
	 * - 1 insertAt ([in] typed by UnlimitedNatural)
	 * - 3] property type is set to "Boolean"
	 * - AddStructuralFeatureValueAction has now 4 pins
	 * - 1 object ([in] typed by propertyClass)
	 * - 1 result ([out] typed by propertyClass)
	 * - 1 value ([in] typed by Boolean)
	 * - 1 insertAt ([in] typed by UnlimitedNatural)
	 */
	@Test
	public void testPinDerivation_changePropertyStructuralFeatureType() {
		SetRequest request = new SetRequest(this.editingDomain, this.addStructuralFeatureValueAction, UMLPackage.eINSTANCE.getStructuralFeatureAction_StructuralFeature(), this.property);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.addStructuralFeatureValueAction);
		ICommand editAddStructuralFeatureValueActionCommand = elementEditService.getEditCommand(request);
		if (!editAddStructuralFeatureValueActionCommand.canExecute()) {
			Assert.fail("The AddStructuralFeatureValueAction cannot be edited (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editAddStructuralFeatureValueActionCommand));
		
		// Change property type
		SetRequest changePropertyRequest = new SetRequest(this.editingDomain, this.property, UMLPackage.eINSTANCE.getTypedElement_Type(), this.getPrimitiveType("Boolean")); 
		IElementEditService changePropertyElementEditService = ElementEditServiceUtils.getCommandProvider(this.addStructuralFeatureValueAction);
		ICommand changePropertyEditAddStructuralFeatureValueActionCommand = changePropertyElementEditService.getEditCommand(changePropertyRequest);
		if (!changePropertyEditAddStructuralFeatureValueActionCommand.canExecute()) {
			Assert.fail("The attribut type of property cannot be edited (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(changePropertyEditAddStructuralFeatureValueActionCommand));
		
		// Assert result pins
		Assert.assertTrue("AddStructuralFeatureValueAction shall have one result pin", this.addStructuralFeatureValueAction.getValue() != null);
		Assert.assertTrue("AddStructuralFeatureValueAction result pins shall typed by Integer", this.addStructuralFeatureValueAction.getValue().getType() == this.getPrimitiveType("Boolean"));
		
		if (!changePropertyEditAddStructuralFeatureValueActionCommand.canUndo()) {
			Assert.fail("It shall be possible to undo the pin derivation");
		}
		this.editingDomain.getCommandStack().undo();
	}
	
	/**
	 * Role:
	 * - For a AddStructuralFeatureValueAction having structural feature, ensure that if the structural feature  type is change then the pins corresponding to the
	 * structural feature are inferred.
	 * 
	 * Scenario:
	 * - 1] AddStructuralFeatureValueAction 'structuralFeature' feature is set with the Port 'port'
	 * - 2] Synchronization is performed via the advice
	 * - AddStructuralFeatureValueAction has now 3 pins
	 * - 1 object ([in] typed by portClass)
	 * - 1 result ([out] typed by portClass)
	 * - 1 value ([in] typed by String)
	 * - 3] port type is set to "Boolean"
	 * - AddStructuralFeatureValueAction has now 3 pins
	 * - 1 object ([in] typed by portClass)
	 * - 1 result ([out] typed by portClass)
	 * - 1 value ([in] typed by Boolean)
	 */
	@Test
	public void testPinDerivation_changePortStructuralFeatureType() {
		SetRequest request = new SetRequest(this.editingDomain, this.addStructuralFeatureValueAction, UMLPackage.eINSTANCE.getStructuralFeatureAction_StructuralFeature(), this.port);
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(this.addStructuralFeatureValueAction);
		ICommand editAddStructuralFeatureValueActionCommand = elementEditService.getEditCommand(request);
		if (!editAddStructuralFeatureValueActionCommand.canExecute()) {
			Assert.fail("The AddStructuralFeatureValueAction cannot be edited (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(editAddStructuralFeatureValueActionCommand));
		
		// Change port type
		SetRequest changePortRequest = new SetRequest(this.editingDomain, this.port, UMLPackage.eINSTANCE.getTypedElement_Type(), this.getPrimitiveType("Boolean")); 
		IElementEditService changePortElementEditService = ElementEditServiceUtils.getCommandProvider(this.addStructuralFeatureValueAction);
		ICommand changePortEditAddStructuralFeatureValueActionCommand = changePortElementEditService.getEditCommand(changePortRequest);
		if (!changePortEditAddStructuralFeatureValueActionCommand.canExecute()) {
			Assert.fail("The attribut type of port cannot be edited (Edit command is not executable)");
		}
		this.editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(changePortEditAddStructuralFeatureValueActionCommand));
		
		// Assert result pins
		Assert.assertTrue("AddStructuralFeatureValueAction shall have one result pin", this.addStructuralFeatureValueAction.getValue() != null);
		Assert.assertTrue("AddStructuralFeatureValueAction result pins shall typed by Integer", this.addStructuralFeatureValueAction.getValue().getType() == this.getPrimitiveType("Boolean"));
		
		if (!changePortEditAddStructuralFeatureValueActionCommand.canUndo()) {
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
		this.port.setUpper(1);
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
		
		// Create the activity containing the AddStructuralFeatureValueAction
		Activity activity = UMLFactory.eINSTANCE.createActivity();
		this.addStructuralFeatureValueAction = UMLFactory.eINSTANCE.createAddStructuralFeatureValueAction();
		this.addStructuralFeatureValueAction.setStructuralFeature(port);
		activity.getOwnedNodes().add(this.addStructuralFeatureValueAction);
		
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
