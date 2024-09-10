/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Nizar GUEDIDI (CEA LIST) - Add tests for Primitive Type properties and operations 
 *  Nizar GUEDIDI (CEA LIST) - Add tests for nested classifier compartments
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.test.canonical;

import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.uml.diagram.clazz.CreateClassDiagramCommand;
import org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part.CustomUMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassAttributeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassNestedClassifierCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassOperationCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ComponentAttributeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ComponentNestedClassifierCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ComponentOperationCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.EnumerationEnumerationLiteralCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InstanceSpecificationSlotCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InterfaceAttributeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InterfaceNestedClassifierCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InterfaceOperationCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.PrimitiveTypeAttributeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.PrimitiveTypeOperationCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.SignalAttributeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.clazz.test.IClassDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestChildLabel;
import org.junit.Test;

/**
 * The Class TestClassDiagramChildLabel.
 */
public class TestClassDiagramChildLabel extends TestChildLabel {
	@Override
	public DiagramUpdater getDiagramUpdater() {
		return CustomUMLDiagramUpdater.INSTANCE;
	}
	
	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateClassDiagramCommand();
	}

	@Override
	protected String getProjectName() {
		return IClassDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return IClassDiagramTestsConstants.FILE_NAME;
	}

	@Test
	@FailingTest
	public void testToManageInstanceSlot() {
		//instance specification
		testToManageTopNode(UMLElementTypes.InstanceSpecification_Shape, UMLElementTypes.Slot_SlotLabel, InstanceSpecificationSlotCompartmentEditPart.VISUAL_ID, "<UNDEFINED>");
	}

	@Test
	public void testToManageComponentProperty() {
		//instance specification
		testToManageTopNodeWithMask(UMLElementTypes.Component_Shape, UMLElementTypes.Property_ComponentAttributeLabel, ComponentAttributeCompartmentEditPart.VISUAL_ID,"Attribute");
	}

	@Test
	public void testToManageComponentNestedClass() {
		//Class
		testToManageTopNode(UMLElementTypes.Component_Shape, UMLElementTypes.Class_ComponentNestedClassifierLabel, ComponentNestedClassifierCompartmentEditPart.VISUAL_ID);
	}

	@Test
	public void testToManageComponentNestedInterface() {
		//Interface
		testToManageTopNode(UMLElementTypes.Component_Shape, UMLElementTypes.Interface_ComponentNestedClassifierLabel, ComponentNestedClassifierCompartmentEditPart.VISUAL_ID);
	}

	@Test
	public void testToManageComponentNestedSignal() {
		//Signal
		testToManageTopNode(UMLElementTypes.Component_Shape, UMLElementTypes.Signal_ComponentNestedClassifierLabel, ComponentNestedClassifierCompartmentEditPart.VISUAL_ID);
	}

	@Test
	public void testToManageComponentNestedDataType() {
		//Data Type
		testToManageTopNode(UMLElementTypes.Component_Shape, UMLElementTypes.DataType_ComponentNestedClassifierLabel, ComponentNestedClassifierCompartmentEditPart.VISUAL_ID);
	}

	@Test
	public void testToManageComponentNestedEnumeration() {
		//Enumeration
		testToManageTopNode(UMLElementTypes.Component_Shape, UMLElementTypes.Enumeration_ComponentNestedClassifierLabel, ComponentNestedClassifierCompartmentEditPart.VISUAL_ID);
	}

	@Test
	public void testToManageComponentNestedPrimitiveType() {
		//primitive Type
		testToManageTopNode(UMLElementTypes.Component_Shape, UMLElementTypes.PrimitiveType_ComponentNestedClassifierLabel, ComponentNestedClassifierCompartmentEditPart.VISUAL_ID);
	}

	@Test
	public void testToManageComponentOperation() {
		//instance specification
		testToManageTopNodeWithMask(UMLElementTypes.Component_Shape, UMLElementTypes.Operation_ComponentOperationLabel, ComponentOperationCompartmentEditPart.VISUAL_ID,null);
	}

	@Test
	public void testToManageSignalProperty() {
		//instance specification
		testToManageTopNodeWithMask(UMLElementTypes.Signal_Shape, UMLElementTypes.Property_SignalAttributeLabel, SignalAttributeCompartmentEditPart.VISUAL_ID,"Attribute");
	}

	@Test
	public void testToManageInterfaceProperty() {
		//interface
		testToManageTopNodeWithMask(UMLElementTypes.Interface_Shape, UMLElementTypes.Property_InterfaceAttributeLabel, InterfaceAttributeCompartmentEditPart.VISUAL_ID,"Attribute");
	}

	@Test
	public void testToManageInterfaceOperation() {
		//interface
		testToManageTopNodeWithMask(UMLElementTypes.Interface_Shape, UMLElementTypes.Operation_InterfaceOperationLabel, InterfaceOperationCompartmentEditPart.VISUAL_ID,null);
	}

	@Test
	public void testToManageInterfaceNestedClass() {
		//Class
		testToManageTopNode(UMLElementTypes.Interface_Shape, UMLElementTypes.Class_InterfaceNestedClassifierLabel, InterfaceNestedClassifierCompartmentEditPart.VISUAL_ID);
	}

	@Test
	public void testToManageInterfaceNestedInterface() {
		//Interface
		testToManageTopNode(UMLElementTypes.Interface_Shape, UMLElementTypes.Interface_InterfaceNestedClassifierLabel, InterfaceNestedClassifierCompartmentEditPart.VISUAL_ID);
	}

	@Test
	public void testToManageInterfaceNestedSignal() {
		//Signal
		testToManageTopNode(UMLElementTypes.Interface_Shape, UMLElementTypes.Signal_InterfaceNestedClassifierLabel, InterfaceNestedClassifierCompartmentEditPart.VISUAL_ID);
	}

	@Test
	public void testToManageInterfaceNestedDataType() {
		//Data Type
		testToManageTopNode(UMLElementTypes.Interface_Shape, UMLElementTypes.DataType_InterfaceNestedClassifierLabel, InterfaceNestedClassifierCompartmentEditPart.VISUAL_ID);
	}

	@Test
	public void testToManageInterfaceNestedEnumeration() {
		//Enumeration
		testToManageTopNode(UMLElementTypes.Interface_Shape, UMLElementTypes.Enumeration_InterfaceNestedClassifierLabel, InterfaceNestedClassifierCompartmentEditPart.VISUAL_ID);
	}

	@Test
	public void testToManageInterfaceNestedPrimitiveType() {
		//Primitive Type
		testToManageTopNode(UMLElementTypes.Interface_Shape, UMLElementTypes.PrimitiveType_InterfaceNestedClassifierLabel, InterfaceNestedClassifierCompartmentEditPart.VISUAL_ID);
	}

	@Test
	public void testToManageEnumerationLiteralEnumeration() {
		//Enumeration
		testToManageTopNode(UMLElementTypes.Enumeration_Shape, UMLElementTypes.EnumerationLiteral_LiteralLabel, EnumerationEnumerationLiteralCompartmentEditPart.VISUAL_ID);
	}

	@Test
	public void testToManageClassProperty() {
		//Enumeration
		testToManageTopNodeWithMask(UMLElementTypes.Class_Shape, UMLElementTypes.Property_ClassAttributeLabel, ClassAttributeCompartmentEditPart.VISUAL_ID,"Attribute");
	}

	@Test
	public void testToManageClassReception() {
		//Enumeration
		testToManageTopNode(UMLElementTypes.Class_Shape, UMLElementTypes.Reception_ReceptionLabel, ClassOperationCompartmentEditPart.VISUAL_ID);
	}

	@Test
	public void testToManageClassOperation() {
		//Enumeration
		testToManageTopNodeWithMask(UMLElementTypes.Class_Shape, UMLElementTypes.Operation_ClassOperationLabel, ClassOperationCompartmentEditPart.VISUAL_ID,null);
	}

	@Test
	public void testToManageClassNestedClass() {
		//class
		testToManageTopNode(UMLElementTypes.Class_Shape, UMLElementTypes.Class_ClassNestedClassifierLabel, ClassNestedClassifierCompartmentEditPart.VISUAL_ID);
	}

	@Test
	public void testToManageClassNestedInetrface() {
		//Interface
		testToManageTopNode(UMLElementTypes.Class_Shape, UMLElementTypes.Interface_ClassNestedClassifierLabel, ClassNestedClassifierCompartmentEditPart.VISUAL_ID);
	}

	@Test
	public void testToManageClassNestedSignal() {
		//Signal
		testToManageTopNode(UMLElementTypes.Class_Shape, UMLElementTypes.Signal_ClassNestedClassifierLabel, ClassNestedClassifierCompartmentEditPart.VISUAL_ID);
	}

	@Test
	public void testToManageClassNestedDataType() {
		//Data Type
		testToManageTopNode(UMLElementTypes.Class_Shape, UMLElementTypes.DataType_ClassNestedClassifierLabel, ClassNestedClassifierCompartmentEditPart.VISUAL_ID);
	}

	@Test
	public void testToManageClassNestedEnumeration() {
		//Enumeration
		testToManageTopNode(UMLElementTypes.Class_Shape, UMLElementTypes.Enumeration_ClassNestedClassifierLabel, ClassNestedClassifierCompartmentEditPart.VISUAL_ID);
	}

	@Test
	public void testToManageClassNestedPrimitiveType() {
		//Primitive Type
		testToManageTopNode(UMLElementTypes.Class_Shape, UMLElementTypes.PrimitiveType_ClassNestedClassifierLabel, ClassNestedClassifierCompartmentEditPart.VISUAL_ID);
	}

	@Test
	public void testToManageComponentReception() {
		//class
		testToManageTopNode(UMLElementTypes.Component_Shape, UMLElementTypes.Reception_ReceptionLabel, ComponentOperationCompartmentEditPart.VISUAL_ID);
	}

	@Test
	public void testToManageInterfaceReception() {
		//class
		testToManageTopNode(UMLElementTypes.Interface_Shape, UMLElementTypes.Reception_InterfaceReceptionLabel, InterfaceOperationCompartmentEditPart.VISUAL_ID);
	}

	@Test
	public void testToManagePrimitiveTypeProperty() {
		//class
		testToManageTopNodeWithMask(UMLElementTypes.PrimitiveType_Shape, UMLElementTypes.Property_PrimitiveTypeAttributeLabel, PrimitiveTypeAttributeCompartmentEditPart.VISUAL_ID,"Attribute");
	}

	@Test
	public void testToManagePrimitiveTypeOperation() {
		//class
		testToManageTopNodeWithMask(UMLElementTypes.PrimitiveType_Shape, UMLElementTypes.Operation_PrimitiveTypeOperationLabel, PrimitiveTypeOperationCompartmentEditPart.VISUAL_ID,null);
	}
}
