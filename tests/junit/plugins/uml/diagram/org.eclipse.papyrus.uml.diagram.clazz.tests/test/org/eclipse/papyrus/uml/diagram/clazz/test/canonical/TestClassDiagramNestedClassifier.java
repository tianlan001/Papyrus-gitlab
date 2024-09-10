/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *  Nizar GUEDIDI (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.test.canonical;

import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.uml.diagram.clazz.CreateClassDiagramCommand;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassNestedClassifierCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ComponentNestedClassifierCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.InterfaceNestedClassifierCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.PackagePackageableElementCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.clazz.test.IClassDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestNested;
import org.junit.Test;

/**
 * The Class TestClassDiagramNestedClassifier.
 */
public class TestClassDiagramNestedClassifier extends TestNested {

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
	public void testToManageComponentNestedClass() {
		//Class
		testToManageTopNode(UMLElementTypes.Package_Shape, UMLElementTypes.Component_Shape_CN, UMLElementTypes.Class_ComponentNestedClassifierLabel, PackagePackageableElementCompartmentEditPart.VISUAL_ID, ComponentNestedClassifierCompartmentEditPartCN.VISUAL_ID);
	}

	@Test
	public void testToManageComponentNestedInterface() {
		//Interface
		testToManageTopNode(UMLElementTypes.Package_Shape, UMLElementTypes.Component_Shape_CN, UMLElementTypes.Interface_ComponentNestedClassifierLabel, PackagePackageableElementCompartmentEditPart.VISUAL_ID, ComponentNestedClassifierCompartmentEditPartCN.VISUAL_ID);
	}

	@Test
	public void testToManageComponentNestedSignal() {
		//Signal
		testToManageTopNode(UMLElementTypes.Package_Shape, UMLElementTypes.Component_Shape_CN, UMLElementTypes.Signal_ComponentNestedClassifierLabel, PackagePackageableElementCompartmentEditPart.VISUAL_ID, ComponentNestedClassifierCompartmentEditPartCN.VISUAL_ID);
	}

	@Test
	public void testToManageComponentNestedDataType() {
		//Data Type
		testToManageTopNode(UMLElementTypes.Package_Shape, UMLElementTypes.Component_Shape_CN, UMLElementTypes.DataType_ComponentNestedClassifierLabel, PackagePackageableElementCompartmentEditPart.VISUAL_ID, ComponentNestedClassifierCompartmentEditPartCN.VISUAL_ID);
	}

	@Test
	public void testToManageComponentNestedEnumeration() {
		//Enumeration
		testToManageTopNode(UMLElementTypes.Package_Shape, UMLElementTypes.Component_Shape_CN, UMLElementTypes.Enumeration_ComponentNestedClassifierLabel, PackagePackageableElementCompartmentEditPart.VISUAL_ID, ComponentNestedClassifierCompartmentEditPartCN.VISUAL_ID);
	}

	@Test
	public void testToManageComponentNestedPrimitiveType() {
		//primitive Type
		testToManageTopNode(UMLElementTypes.Package_Shape, UMLElementTypes.Component_Shape_CN, UMLElementTypes.PrimitiveType_ComponentNestedClassifierLabel, PackagePackageableElementCompartmentEditPart.VISUAL_ID, ComponentNestedClassifierCompartmentEditPartCN.VISUAL_ID);
	}

	@Test
	public void testToManageInterfaceNestedClass() {
		//Class
		testToManageTopNode(UMLElementTypes.Package_Shape, UMLElementTypes.Interface_Shape_CN, UMLElementTypes.Class_InterfaceNestedClassifierLabel, PackagePackageableElementCompartmentEditPart.VISUAL_ID, InterfaceNestedClassifierCompartmentEditPartCN.VISUAL_ID);
	}

	@Test
	public void testToManageInterfaceNestedInterface() {
		//Interface
		testToManageTopNode(UMLElementTypes.Package_Shape, UMLElementTypes.Interface_Shape_CN, UMLElementTypes.Interface_InterfaceNestedClassifierLabel, PackagePackageableElementCompartmentEditPart.VISUAL_ID, InterfaceNestedClassifierCompartmentEditPartCN.VISUAL_ID);
	}

	@Test
	public void testToManageInterfaceNestedSignal() {
		//Signal
		testToManageTopNode(UMLElementTypes.Package_Shape, UMLElementTypes.Interface_Shape_CN, UMLElementTypes.Signal_InterfaceNestedClassifierLabel, PackagePackageableElementCompartmentEditPart.VISUAL_ID, InterfaceNestedClassifierCompartmentEditPartCN.VISUAL_ID);
	}

	@Test
	public void testToManageInterfaceNestedDataType() {
		//Data Type
		testToManageTopNode(UMLElementTypes.Package_Shape, UMLElementTypes.Interface_Shape_CN, UMLElementTypes.DataType_InterfaceNestedClassifierLabel, PackagePackageableElementCompartmentEditPart.VISUAL_ID, InterfaceNestedClassifierCompartmentEditPartCN.VISUAL_ID);
	}

	@Test
	public void testToManageInterfaceNestedEnumeration() {
		//Enumeration
		testToManageTopNode(UMLElementTypes.Package_Shape, UMLElementTypes.Interface_Shape_CN, UMLElementTypes.Enumeration_InterfaceNestedClassifierLabel, PackagePackageableElementCompartmentEditPart.VISUAL_ID, InterfaceNestedClassifierCompartmentEditPartCN.VISUAL_ID);
	}

	@Test
	public void testToManageInterfaceNestedPrimitiveType() {
		//Primitive Type
		testToManageTopNode(UMLElementTypes.Package_Shape, UMLElementTypes.Interface_Shape_CN, UMLElementTypes.PrimitiveType_InterfaceNestedClassifierLabel, PackagePackageableElementCompartmentEditPart.VISUAL_ID, InterfaceNestedClassifierCompartmentEditPartCN.VISUAL_ID);
	}

	@Test
	public void testToManageClassNestedClass() {
		//class
		testToManageTopNode(UMLElementTypes.Package_Shape, UMLElementTypes.Class_Shape_CN, UMLElementTypes.Class_ClassNestedClassifierLabel, PackagePackageableElementCompartmentEditPart.VISUAL_ID, ClassNestedClassifierCompartmentEditPartCN.VISUAL_ID);
	}

	@Test
	public void testToManageClassNestedInetrface() {
		//Interface
		testToManageTopNode(UMLElementTypes.Package_Shape, UMLElementTypes.Class_Shape_CN, UMLElementTypes.Interface_ClassNestedClassifierLabel, PackagePackageableElementCompartmentEditPart.VISUAL_ID, ClassNestedClassifierCompartmentEditPartCN.VISUAL_ID);
	}

	@Test
	public void testToManageClassNestedSignal() {
		//Signal
		testToManageTopNode(UMLElementTypes.Package_Shape, UMLElementTypes.Class_Shape_CN, UMLElementTypes.Signal_ClassNestedClassifierLabel, PackagePackageableElementCompartmentEditPart.VISUAL_ID, ClassNestedClassifierCompartmentEditPartCN.VISUAL_ID);
	}

	@Test
	public void testToManageClassNestedDataType() {
		//Data Type
		testToManageTopNode(UMLElementTypes.Package_Shape, UMLElementTypes.Class_Shape_CN, UMLElementTypes.DataType_ClassNestedClassifierLabel, PackagePackageableElementCompartmentEditPart.VISUAL_ID, ClassNestedClassifierCompartmentEditPartCN.VISUAL_ID);
	}

	@Test
	public void testToManageclassNestedEnumeration() {
		//Enumeration
		testToManageTopNode(UMLElementTypes.Package_Shape, UMLElementTypes.Class_Shape_CN, UMLElementTypes.Enumeration_ClassNestedClassifierLabel, PackagePackageableElementCompartmentEditPart.VISUAL_ID, ClassNestedClassifierCompartmentEditPartCN.VISUAL_ID);
	}

	@Test
	public void testToManageClassNestedPrimitiveType() {
		//Primitive Type
		testToManageTopNode(UMLElementTypes.Package_Shape, UMLElementTypes.Class_Shape_CN, UMLElementTypes.PrimitiveType_ClassNestedClassifierLabel, PackagePackageableElementCompartmentEditPart.VISUAL_ID, ClassNestedClassifierCompartmentEditPartCN.VISUAL_ID);
	}
}
