/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.profile.tests.drop.graphical;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractGraphicalDropNodeTests;
import org.eclipse.papyrus.sirius.uml.diagram.profile.tests.GraphicalDropToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.profile.tests.MappingTypes;
import org.junit.Test;

/**
 * This class tests the graphical drop from and within the diagram.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
@PluginResource("resources/drop/graphical/graphicalDrop.profile.di")
public class GraphicalDropTest extends AbstractGraphicalDropNodeTests {

	private static final String DIAGRAM_NAME = "GraphicalDropProfileDiagramSirius"; //$NON-NLS-1$

	private static final String CLASS = "Class1"; //$NON-NLS-1$

	private static final String CLASS_TO_DROP_ELEMENTS = "Class3"; //$NON-NLS-1$

	private static final String DATATYPE = "DataType1"; //$NON-NLS-1$

	private static final String CONSTRAINT = "Constraint1"; //$NON-NLS-1$

	private static final String ENUMERATION = "Enumeration2"; //$NON-NLS-1$

	private static final String STEREOTYPE = "Stereotype1"; //$NON-NLS-1$

	private static final String PACKAGE = "Package1"; //$NON-NLS-1$

	private static final String PACKAGE_TO_DROP_INTO_PACKAGE = "Package3"; //$NON-NLS-1$

	private static final String PROFILE = "Profile1"; //$NON-NLS-1$

	private static final String PROFILE_TO_DROP_INTO_PROFILE = "Profile3"; //$NON-NLS-1$

	private static final String PRIMITIVE = "PrimitiveType1"; //$NON-NLS-1$

	private static final String PROPERTY = "Property1"; //$NON-NLS-1$

	private static final String PROPERTY_IN_DATATYPE = "Property2"; //$NON-NLS-1$

	private static final String OPERATION = "Operation1"; //$NON-NLS-1$

	private static final String OPERATION_IN_DATATYPE = "Operation2"; //$NON-NLS-1$

	private static final String ENUMERATIONLITERAL = "EnumerationLiteral1"; //$NON-NLS-1$

	private static final String CLASS_IN_PACKAGE = "Class2"; //$NON-NLS-1$

	private static final String DATATYPE_IN_PACKAGE = "DataType2"; //$NON-NLS-1$

	private static final String CONSTRAINT_IN_PACKAGE = "Constraint2"; //$NON-NLS-1$

	private static final String ENUMERATION_IN_PACKAGE = "Enumeration3"; //$NON-NLS-1$

	private static final String STEREOTYPE_IN_PACKAGE = "Stereotype2"; //$NON-NLS-1$

	private static final String PACKAGE_IN_PACKAGE = "Package2"; //$NON-NLS-1$

	private static final String PROFILE_IN_PACKAGE = "Profile2"; //$NON-NLS-1$

	private static final String PRIMITIVETYPE_IN_PACKAGE = "PrimitiveType2"; //$NON-NLS-1$


	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropPropertyFromClassToDataType() {
		dropElementIntoContainer(PROPERTY, DATATYPE, MappingTypes.DATATYPE_NODE_TYPE, MappingTypes.DATATYPE_NODE_PRD_ATTRIBUTESCOMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__PROPERTY__TOOL, MappingTypes.PROPERTY_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropOperationFromClassToDataType() {
		dropElementIntoContainer(OPERATION, DATATYPE, MappingTypes.DATATYPE_NODE_TYPE, MappingTypes.DATATYPE_NODE_PRD_OPERATIONSCOMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__OPERATION__TOOL, MappingTypes.OPERATION_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropPropertyFromDataTypeToClass() {
		dropElementIntoContainer(PROPERTY_IN_DATATYPE, CLASS_TO_DROP_ELEMENTS, MappingTypes.CLASS_NODE_TYPE, MappingTypes.CLASS_NODE_PRD_ATTRIBUTESCOMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__PROPERTY__TOOL, MappingTypes.PROPERTY_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropOperationFromDataTypeToClass() {
		dropElementIntoContainer(OPERATION_IN_DATATYPE, CLASS_TO_DROP_ELEMENTS, MappingTypes.CLASS_NODE_TYPE, MappingTypes.CLASS_NODE_PRD_OPERATIONSCOMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__OPERATION__TOOL, MappingTypes.OPERATION_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropPropertyFromClassToStereotypeP() {
		dropElementIntoContainer(PROPERTY, STEREOTYPE, MappingTypes.STEREOTYPE_NODE_TYPE, MappingTypes.STEREOTYPE_NODE_PRD_ATTRIBUTESCOMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__PROPERTY__TOOL, MappingTypes.PROPERTY_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropOperationFromClassToStereotype() {
		dropElementIntoContainer(OPERATION, STEREOTYPE, MappingTypes.STEREOTYPE_NODE_TYPE, MappingTypes.STEREOTYPE_NODE_PRD_OPERATIONSCOMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__OPERATION__TOOL, MappingTypes.OPERATION_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropEnumerationLiteralFromEnumerationToEnumeration() {
		dropElementIntoContainer(ENUMERATIONLITERAL, ENUMERATION, MappingTypes.ENUMERATION_NODE_TYPE, MappingTypes.ENUMERATION_NODE_PRD_ENUMERATIONLITERALSCOMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__ENUMERATION_LITERAL__TOOL,
				MappingTypes.ENUMERATIONLITERAL_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropClassFromDiagramToPackage() {
		dropElementIntoContainer(CLASS, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__CLASS__TOOL,
				MappingTypes.CLASS_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropConstraintFromDiagramToPackage() {
		dropElementIntoContainer(CONSTRAINT, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__CONSTRAINT__TOOL,
				MappingTypes.CONSTRAINT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropDataTypeFromDiagramToPackage() {
		dropElementIntoContainer(DATATYPE, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__DATATYPE__TOOL,
				MappingTypes.DATATYPE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropEnumerationFromClassToPackage() {
		dropElementIntoContainer(ENUMERATION, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__ENUMERATION__TOOL,
				MappingTypes.ENUMERATION_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropProfileFromDiagramToPackage() {
		dropElementIntoContainer(PROFILE, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__PROFILE__TOOL,
				MappingTypes.PROFILE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropStereotypeFromDiagramToPackage() {
		dropElementIntoContainer(STEREOTYPE, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__STEREOTYPE__TOOL,
				MappingTypes.STEREOTYPE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropPrimitiveTypeFromDiagramToPackage() {
		dropElementIntoContainer(PRIMITIVE, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__PRIMITIVETYPE__TOOL,
				MappingTypes.PRIMITIVETYPE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropPackageFromDiagramToPackage() {
		dropElementIntoContainer(PACKAGE_TO_DROP_INTO_PACKAGE, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__PACKAGE__TOOL,
				MappingTypes.PACKAGE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropClassFromDiagramToProfile() {
		dropElementIntoContainer(CLASS, PROFILE, MappingTypes.PROFILE_NODE_TYPE, MappingTypes.PROFILE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__CLASS__TOOL,
				MappingTypes.CLASS_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropConstraintFromDiagramToProfile() {
		dropElementIntoContainer(CONSTRAINT, PROFILE, MappingTypes.PROFILE_NODE_TYPE, MappingTypes.PROFILE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__CONSTRAINT__TOOL,
				MappingTypes.CONSTRAINT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropDataTypeFromDiagramToProfile() {
		dropElementIntoContainer(DATATYPE, PROFILE, MappingTypes.PROFILE_NODE_TYPE, MappingTypes.PROFILE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__DATATYPE__TOOL,
				MappingTypes.DATATYPE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropEnumerationFromDiagramToProfile() {
		dropElementIntoContainer(ENUMERATION, PROFILE, MappingTypes.PROFILE_NODE_TYPE, MappingTypes.PROFILE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__ENUMERATION__TOOL,
				MappingTypes.ENUMERATION_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropStereotypeFromDiagramToProfile() {
		dropElementIntoContainer(STEREOTYPE, PROFILE, MappingTypes.PROFILE_NODE_TYPE, MappingTypes.PROFILE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__STEREOTYPE__TOOL,
				MappingTypes.STEREOTYPE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDromPrimitiveTypeFromDiagramToProfile() {
		dropElementIntoContainer(PRIMITIVE, PROFILE, MappingTypes.PROFILE_NODE_TYPE, MappingTypes.PROFILE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__PRIMITIVETYPE__TOOL,
				MappingTypes.PRIMITIVETYPE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDromProfileFromDiagramToProfile() {
		dropElementIntoContainer(PROFILE_TO_DROP_INTO_PROFILE, PROFILE, MappingTypes.PROFILE_NODE_TYPE, MappingTypes.PROFILE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, GraphicalDropToolsIds.DROP__PROFILE__TOOL,
				MappingTypes.PROFILE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropClassFromPackageToDiagram() {
		dropElementToDiagram(CLASS_IN_PACKAGE, GraphicalDropToolsIds.DROP__CLASS__TOOL, MappingTypes.CLASS_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropPackageFromPackageToDiagram() {
		dropElementToDiagram(PACKAGE_IN_PACKAGE, GraphicalDropToolsIds.DROP__PACKAGE__TOOL, MappingTypes.PACKAGE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropConstraintFromPackageToDiagram() {
		dropElementToDiagram(CONSTRAINT_IN_PACKAGE, GraphicalDropToolsIds.DROP__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropEnumerationFromPackageToDiagram() {
		dropElementToDiagram(ENUMERATION_IN_PACKAGE, GraphicalDropToolsIds.DROP__ENUMERATION__TOOL, MappingTypes.ENUMERATION_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropDataTypeFromPackageToDiagram() {
		dropElementToDiagram(DATATYPE_IN_PACKAGE, GraphicalDropToolsIds.DROP__DATATYPE__TOOL, MappingTypes.DATATYPE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropStereotypeFromPackageToDiagram() {
		dropElementToDiagram(STEREOTYPE_IN_PACKAGE, GraphicalDropToolsIds.DROP__STEREOTYPE__TOOL, MappingTypes.STEREOTYPE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropProfileFromPackageToDiagram() {
		dropElementToDiagram(PROFILE_IN_PACKAGE, GraphicalDropToolsIds.DROP__PROFILE__TOOL, MappingTypes.PROFILE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropPrimitiveTypeFromPackageToDiagram() {
		dropElementToDiagram(PRIMITIVETYPE_IN_PACKAGE, GraphicalDropToolsIds.DROP__PRIMITIVETYPE__TOOL, MappingTypes.PRIMITIVETYPE_NODE_TYPE);
	}
}
