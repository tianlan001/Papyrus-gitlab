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
package org.eclipse.papyrus.sirius.uml.diagram.profile.tests.drop.semantic;

import static org.junit.Assert.assertTrue;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractSemanticDropSubNodeTests;
import org.eclipse.papyrus.sirius.uml.diagram.profile.tests.MappingTypes;
import org.eclipse.papyrus.sirius.uml.diagram.profile.tests.SemanticDropToolsIds;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Namespace;
import org.junit.Test;

/**
 * This class tests the semantic drop into a container node.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
@PluginResource("resources/drop/subNodes/subNodeSemanticDrop.profile.di")
public class SemanticDropIntoContainerTest extends AbstractSemanticDropSubNodeTests {

	private static final String DIAGRAM_NAME = "SubNodeSemanticDropProfileDiagramSirius"; //$NON-NLS-1$

	private static final String CLASS = "Class1"; //$NON-NLS-1$

	private static final String CONSTRAINT = "Constraint1"; //$NON-NLS-1$

	private static final String COMMENT = "Comment"; //$NON-NLS-1$

	private static final String DATATYPE = "DataType1"; //$NON-NLS-1$

	private static final String ENUMERATION = "Enumeration1"; //$NON-NLS-1$

	private static final String ENUMERATIONLITERAL = "EnumerationLiteral1"; //$NON-NLS-1$

	private static final String METACLASS = "Activity"; //$NON-NLS-1$

	private static final String OPERATION = "Operation1"; //$NON-NLS-1$

	private static final String PACKAGE = "Package1"; //$NON-NLS-1$

	private static final String PRIMITIVETYPE = "PrimitiveType1"; //$NON-NLS-1$

	private static final String PROFILE = "Profile1"; //$NON-NLS-1$

	private static final String PROPERTY = "Property1"; //$NON-NLS-1$

	private static final String STEREOTYPE = "Stereotype1"; //$NON-NLS-1$


	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testPropertyDropIntoClass() {
		dropElementToContainer(PROPERTY, CLASS, MappingTypes.CLASS_NODE_TYPE, MappingTypes.CLASS_NODE_PRD_ATTRIBUTESCOMPARTMENT_TYPE, SemanticDropToolsIds.DROP__PROPERTY__TOOL, MappingTypes.PROPERTY_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testOperationDropIntoClass() {
		dropElementToContainer(OPERATION, CLASS, MappingTypes.CLASS_NODE_TYPE, MappingTypes.CLASS_NODE_PRD_OPERATIONSCOMPARTMENT_TYPE, SemanticDropToolsIds.DROP__OPERATION__TOOL, MappingTypes.OPERATION_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testPropertyDropIntoDataType() {
		dropElementToContainer(PROPERTY, DATATYPE, MappingTypes.DATATYPE_NODE_TYPE, MappingTypes.DATATYPE_NODE_PRD_ATTRIBUTESCOMPARTMENT_TYPE, SemanticDropToolsIds.DROP__PROPERTY__TOOL, MappingTypes.PROPERTY_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testOperationDropIntoDataType() {
		dropElementToContainer(OPERATION, DATATYPE, MappingTypes.DATATYPE_NODE_TYPE, MappingTypes.DATATYPE_NODE_PRD_OPERATIONSCOMPARTMENT_TYPE, SemanticDropToolsIds.DROP__OPERATION__TOOL, MappingTypes.OPERATION_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testPropertyDropIntoStereotype() {
		dropElementToContainer(PROPERTY, STEREOTYPE, MappingTypes.STEREOTYPE_NODE_TYPE, MappingTypes.STEREOTYPE_NODE_PRD_ATTRIBUTESCOMPARTMENT_TYPE, SemanticDropToolsIds.DROP__PROPERTY__TOOL, MappingTypes.PROPERTY_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testOperationDropIntoStereotype() {
		dropElementToContainer(OPERATION, STEREOTYPE, MappingTypes.STEREOTYPE_NODE_TYPE, MappingTypes.STEREOTYPE_NODE_PRD_OPERATIONSCOMPARTMENT_TYPE, SemanticDropToolsIds.DROP__OPERATION__TOOL, MappingTypes.OPERATION_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testEnumerationLiteralDropIntoEnumeration() {
		dropElementToContainer(ENUMERATIONLITERAL, ENUMERATION, MappingTypes.ENUMERATION_NODE_TYPE, MappingTypes.ENUMERATION_NODE_PRD_ENUMERATIONLITERALSCOMPARTMENT_TYPE, SemanticDropToolsIds.DROP__ENUMERATION_LITERAL__TOOL,
				MappingTypes.ENUMERATIONLITERAL_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testClassDropIntoPackage() {
		dropElementToContainer(CLASS, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, SemanticDropToolsIds.DROP__CLASS__TOOL,
				MappingTypes.CLASS_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testCommentDropIntoPackage() {
		dropElementToContainer(COMMENT, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, SemanticDropToolsIds.DROP__COMMENT__TOOL,
				MappingTypes.COMMENT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testConstraintDropIntoPackage() {
		dropElementToContainer(CONSTRAINT, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, SemanticDropToolsIds.DROP__CONSTRAINT__TOOL,
				MappingTypes.CONSTRAINT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testPrimitiveTypeDropIntoPackage() {
		dropElementToContainer(PRIMITIVETYPE, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, SemanticDropToolsIds.DROP__PRIMITIVETYPE__TOOL,
				MappingTypes.PRIMITIVETYPE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDataTypeDropIntoPackage() {
		dropElementToContainer(DATATYPE, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, SemanticDropToolsIds.DROP__DATATYPE__TOOL,
				MappingTypes.DATATYPE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testEnumerationDropIntoPackage() {
		dropElementToContainer(ENUMERATION, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, SemanticDropToolsIds.DROP__ENUMERATION__TOOL,
				MappingTypes.ENUMERATION_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testPackageDropIntoPackage() {
		dropElementToContainer(PACKAGE, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, SemanticDropToolsIds.DROP__PACKAGE__TOOL,
				MappingTypes.PACKAGE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testProfileDropIntoPackage() {
		dropElementToContainer(PROFILE, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, SemanticDropToolsIds.DROP__PROFILE__TOOL,
				MappingTypes.PROFILE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testStereotypeDropIntoPackage() {
		dropElementToContainer(STEREOTYPE, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, SemanticDropToolsIds.DROP__STEREOTYPE__TOOL,
				MappingTypes.STEREOTYPE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testClassDropIntoProfile() {
		dropElementToContainer(CLASS, PROFILE, MappingTypes.PROFILE_NODE_TYPE, MappingTypes.PROFILE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, SemanticDropToolsIds.DROP__CLASS__TOOL,
				MappingTypes.CLASS_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testCommentDropIntoProfile() {
		dropElementToContainer(COMMENT, PROFILE, MappingTypes.PROFILE_NODE_TYPE, MappingTypes.PROFILE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, SemanticDropToolsIds.DROP__COMMENT__TOOL,
				MappingTypes.COMMENT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testConstraintDropIntoProfile() {
		dropElementToContainer(CONSTRAINT, PROFILE, MappingTypes.PROFILE_NODE_TYPE, MappingTypes.PROFILE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, SemanticDropToolsIds.DROP__CONSTRAINT__TOOL,
				MappingTypes.CONSTRAINT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testPrimitiveTypeDropIntoProfile() {
		dropElementToContainer(PRIMITIVETYPE, PROFILE, MappingTypes.PROFILE_NODE_TYPE, MappingTypes.PROFILE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, SemanticDropToolsIds.DROP__PRIMITIVETYPE__TOOL,
				MappingTypes.PRIMITIVETYPE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDataTypeDropIntoProfile() {
		dropElementToContainer(DATATYPE, PROFILE, MappingTypes.PROFILE_NODE_TYPE, MappingTypes.PROFILE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, SemanticDropToolsIds.DROP__DATATYPE__TOOL,
				MappingTypes.DATATYPE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testEnumerationDropIntoProfile() {
		dropElementToContainer(ENUMERATION, PROFILE, MappingTypes.PROFILE_NODE_TYPE, MappingTypes.PROFILE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, SemanticDropToolsIds.DROP__ENUMERATION__TOOL,
				MappingTypes.ENUMERATION_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testMetaClassDropIntoProfile() {
		dropElementToContainer(METACLASS, PROFILE, MappingTypes.PROFILE_NODE_TYPE, MappingTypes.PROFILE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, SemanticDropToolsIds.DROP__ELEMENTIMPORT__TOOL,
				MappingTypes.METACLASS_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testPackageDropIntoProfile() {
		dropElementToContainer(PACKAGE, PROFILE, MappingTypes.PROFILE_NODE_TYPE, MappingTypes.PROFILE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, SemanticDropToolsIds.DROP__PACKAGE__TOOL,
				MappingTypes.PACKAGE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testProfileDropIntoProfile() {
		dropElementToContainer(PROFILE, PROFILE, MappingTypes.PROFILE_NODE_TYPE, MappingTypes.PROFILE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, SemanticDropToolsIds.DROP__PROFILE__TOOL,
				MappingTypes.PROFILE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testStereotypeDropIntoProfile() {
		dropElementToContainer(STEREOTYPE, PROFILE, MappingTypes.PROFILE_NODE_TYPE, MappingTypes.PROFILE_NODE_PRD_PACKAGEDELEMENTSCOMPARTMENT_TYPE, SemanticDropToolsIds.DROP__STEREOTYPE__TOOL,
				MappingTypes.STEREOTYPE_NODE_TYPE);
	}


	/**
	 * Drop the element to a container.
	 * 
	 * @param elementNameToDrop
	 *            the semantic name of the element to drop.
	 * @param semanticOwnerName
	 *            the semantic name of the parent containing the element to drop.
	 * @param containerMappingType
	 *            the mapping of the graphical container where the element is dropped.
	 * @param compartmentMappingType
	 *            the type of the compartment in which we want to drop the element.
	 * @param dropToolId
	 *            the drop tool to test.
	 * @param expectedMappingType
	 *            the expected mapping once the element is dropped.
	 */
	private void dropElementToContainer(String elementNameToDrop, String semanticOwnerName, String containerMappingType, String compartmentMappingType, String dropToolId, String expectedMappingType) {
		Element rootElement = this.getRootElement();
		assertTrue(rootElement instanceof Namespace);
		Namespace semanticOwner = (Namespace) ((Namespace) rootElement).getMember(semanticOwnerName);
		final Element elementToDrop;
		if (COMMENT.equals(elementNameToDrop)) {
			elementToDrop = semanticOwner.getOwnedComments().get(0);
		} else if (METACLASS.equals(elementNameToDrop)) {
			elementToDrop = semanticOwner.getElementImports().get(0);
		} else {
			elementToDrop = semanticOwner.getMember(elementNameToDrop);
		}
		this.dropElementToContainer(semanticOwner, elementToDrop, containerMappingType, compartmentMappingType, dropToolId, expectedMappingType);
	}
}
