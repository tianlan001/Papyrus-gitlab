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
package org.eclipse.papyrus.sirius.uml.diagram.component.tests.drop.semantic;

import static org.junit.Assert.assertTrue;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractSemanticDropSubNodeTests;
import org.eclipse.papyrus.sirius.uml.diagram.component.tests.MappingTypes;
import org.eclipse.papyrus.sirius.uml.diagram.component.tests.SemanticDropToolsIds;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Namespace;
import org.junit.Test;

/**
 * Groups all tests related to semantic drop into containers of the Component diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@PluginResource("resources/drop/subNodes/subNodeSemanticDrop.di")
public class SemanticDropIntoContainerTest extends AbstractSemanticDropSubNodeTests {

	private static final String DIAGRAM_NAME = "SubNodeSemanticDropComponentDiagramSirius"; //$NON-NLS-1$

	private static final String COMMENT = "Comment"; //$NON-NLS-1$

	private static final String COMPONENT = "Component1"; //$NON-NLS-1$

	private static final String CONSTRAINT = "Constraint1"; //$NON-NLS-1$

	private static final String INTERFACE = "Interface1"; //$NON-NLS-1$

	private static final String MODEL = "Model1"; //$NON-NLS-1$

	private static final String OPERATION = "Operation1"; //$NON-NLS-1$

	private static final String PACKAGE = "Package1"; //$NON-NLS-1$

	private static final String PROPERTY = "Property1"; //$NON-NLS-1$

	private static final String RECEPTION = "Reception1"; //$NON-NLS-1$

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testCommentDropIntoModel() {
		this.dropElementToContainer(COMMENT, MODEL, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, SemanticDropToolsIds.DROP__COMMENT__TOOL, MappingTypes.COMMENT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testCommentDropIntoPackage() {
		this.dropElementToContainer(COMMENT, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, SemanticDropToolsIds.DROP__COMMENT__TOOL, MappingTypes.COMMENT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testComponentDropIntoComponent() {
		this.dropElementToContainer(COMPONENT, COMPONENT, MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.COMPONENT_NODE_CPD_ATTRIBUTES_COMPARTMENT_TYPE, SemanticDropToolsIds.DROP__COMPONENT__TOOL, MappingTypes.COMPONENT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testComponentDropIntoModel() {
		this.dropElementToContainer(COMPONENT, MODEL, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, SemanticDropToolsIds.DROP__COMPONENT__TOOL, MappingTypes.COMPONENT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testComponentDropIntoPackage() {
		this.dropElementToContainer(COMPONENT, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, SemanticDropToolsIds.DROP__COMPONENT__TOOL, MappingTypes.COMPONENT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testConstraintDropIntoModel() {
		this.dropElementToContainer(CONSTRAINT, MODEL, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, SemanticDropToolsIds.DROP__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testConstraintDropIntoPackage() {
		this.dropElementToContainer(CONSTRAINT, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, SemanticDropToolsIds.DROP__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testInterfaceDropIntoModel() {
		this.dropElementToContainer(INTERFACE, MODEL, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, SemanticDropToolsIds.DROP__INTERFACE__TOOL, MappingTypes.INTERFACE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testInterfaceDropIntoPackage() {
		this.dropElementToContainer(INTERFACE, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, SemanticDropToolsIds.DROP__INTERFACE__TOOL, MappingTypes.INTERFACE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testModelDropIntoModel() {
		this.dropElementToContainer(MODEL, MODEL, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, SemanticDropToolsIds.DROP__MODEL__TOOL, MappingTypes.MODEL_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testModelDropIntoPackage() {
		this.dropElementToContainer(MODEL, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, SemanticDropToolsIds.DROP__MODEL__TOOL, MappingTypes.MODEL_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testOperationDropIntoInterface() {
		this.dropElementToContainer(OPERATION, INTERFACE, MappingTypes.INTERFACE_NODE_TYPE, MappingTypes.INTERFACE_NODE_CPD_OPERATIONS_COMPARTMENT_TYPE, SemanticDropToolsIds.DROP__OPERATION__TOOL, MappingTypes.OPERATION_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testPackageDropIntoModel() {
		this.dropElementToContainer(PACKAGE, MODEL, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, SemanticDropToolsIds.DROP__PACKAGE__TOOL, MappingTypes.PACKAGE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testPackageDropIntoPackage() {
		this.dropElementToContainer(PACKAGE, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, SemanticDropToolsIds.DROP__PACKAGE__TOOL, MappingTypes.PACKAGE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testPropertyDropIntoComponent() {
		this.dropElementToContainer(PROPERTY, COMPONENT, MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.COMPONENT_NODE_CPD_ATTRIBUTES_COMPARTMENT_TYPE, SemanticDropToolsIds.DROP__PROPERTY__TOOL, MappingTypes.PROPERTY_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testPropertyDropIntoInterface() {
		this.dropElementToContainer(PROPERTY, INTERFACE, MappingTypes.INTERFACE_NODE_TYPE, MappingTypes.INTERFACE_NODE_CPD_ATTRIBUTES_COMPARTMENT_TYPE, SemanticDropToolsIds.DROP__PROPERTY__TOOL, MappingTypes.PROPERTY_IN_INTERFACE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testReceptionDropIntoInterface() {
		this.dropElementToContainer(RECEPTION, INTERFACE, MappingTypes.INTERFACE_NODE_TYPE, MappingTypes.INTERFACE_NODE_CPD_RECEPTION_COMPARTMENT_TYPE, SemanticDropToolsIds.DROP__RECEPTION__TOOL, MappingTypes.RECEPTION_NODE_TYPE);
	}

	private void dropElementToContainer(String elementNameToDrop, String semanticOwnerName, String containerMappingType, String compartmentMappingType, String dropToolId, String expectedMappingType) {
		// Get semantic element representing the graphicalOwner
		Element rootElement = this.getRootElement();
		assertTrue(rootElement instanceof Namespace);
		Namespace semanticTargetOfGraphicalOwner = (Namespace) ((Namespace) rootElement).getMember(semanticOwnerName);
		// Get the semantic element to drop
		final Element elementToDrop;
		if (COMMENT.equals(elementNameToDrop)) {
			elementToDrop = semanticTargetOfGraphicalOwner.getOwnedComments().get(0);
		} else {
			elementToDrop = semanticTargetOfGraphicalOwner.getMember(elementNameToDrop);
		}
		this.dropElementToContainer(semanticTargetOfGraphicalOwner, elementToDrop, containerMappingType, compartmentMappingType, dropToolId, expectedMappingType);
	}
}
