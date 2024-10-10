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
package org.eclipse.papyrus.sirius.uml.diagram.profile.tests.creation.subnodes;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractCreateNodeTests;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.checker.SemanticAndGraphicalCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DNodeListElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.ISemanticRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.SemanticNodeCreationChecker;
import org.eclipse.papyrus.sirius.uml.diagram.profile.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.profile.tests.MappingTypes;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class tests the creation of subnode for PRD.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
@PluginResource("resources/creation/subNodes/subNodes.profile.di")
@RunWith(value = Parameterized.class)
public class SubListElementNodesCreationTest extends AbstractCreateNodeTests {

	private static final String ENUMERATION = "Enumeration1"; //$NON-NLS-1$

	private static final String STEREOTYPE = "Stereotype1"; //$NON-NLS-1$

	private static final String DATA_TYPE = "DataType1"; //$NON-NLS-1$

	private static final String CLASS = "Class1"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "SubNodes_ProfileDiagramSirius"; //$NON-NLS-1$

	private final String creationToolId;

	private final String nodeMappingType;


	private String semanticOwnerName;

	private final Class<? extends Element> expectedType;

	/**
	 * Default Constructor.
	 *
	 */
	public SubListElementNodesCreationTest(String creationToolId, String nodeMappingType, Class<? extends Element> expectedType) {
		this.creationToolId = creationToolId;
		this.nodeMappingType = nodeMappingType;
		this.expectedType = expectedType;
	}

	@Override
	protected Element getSemanticOwner() {
		return this.root.getMember(this.semanticOwnerName);
	}

	@Override
	protected EObject getTopGraphicalContainer() {
		final DDiagram ddiagram = getDDiagram();
		Element semanticOwner = getSemanticOwner();
		// @formatter:off
		Optional<DSemanticDecorator> optionalDSemanticDecorator = ddiagram.getOwnedDiagramElements().stream()
				.filter(DSemanticDecorator.class::isInstance)
				.map(DSemanticDecorator.class::cast)
				.filter(semanticDecorator -> semanticDecorator.getTarget().equals(semanticOwner))
				.findFirst();
		// @formatter:on
		Assert.assertTrue(optionalDSemanticDecorator.isPresent());
		final DSemanticDecorator element = optionalDSemanticDecorator.get();
		Assert.assertEquals(semanticOwner, element.getTarget());
		return element;
	}



	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeIntoClass() {
		if (CreationToolsIds.CREATE__OPERATION__TOOL.equals(this.creationToolId)) {
			createNodeIntoContainer(CLASS, MappingTypes.CLASS_NODE_TYPE, MappingTypes.CLASS_NODE_PRD_OPERATIONSCOMPARTMENT_TYPE, UMLPackage.eINSTANCE.getClass_OwnedOperation());
		} else if (CreationToolsIds.CREATE__PROPERTY__TOOL.equals(this.creationToolId)) {
			createNodeIntoContainer(CLASS, MappingTypes.CLASS_NODE_TYPE, MappingTypes.CLASS_NODE_PRD_ATTRIBUTESCOMPARTMENT_TYPE, UMLPackage.eINSTANCE.getStructuredClassifier_OwnedAttribute());
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeIntoDataType() {
		if (CreationToolsIds.CREATE__OPERATION__TOOL.equals(this.creationToolId)) {
			createNodeIntoContainer(DATA_TYPE, MappingTypes.DATATYPE_NODE_TYPE, MappingTypes.DATATYPE_NODE_PRD_OPERATIONSCOMPARTMENT_TYPE, UMLPackage.eINSTANCE.getDataType_OwnedOperation());
		} else if (CreationToolsIds.CREATE__PROPERTY__TOOL.equals(this.creationToolId)) {
			createNodeIntoContainer(DATA_TYPE, MappingTypes.DATATYPE_NODE_TYPE, MappingTypes.DATATYPE_NODE_PRD_ATTRIBUTESCOMPARTMENT_TYPE, UMLPackage.eINSTANCE.getStructuredClassifier_OwnedAttribute());
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeIntoStereotype() {
		if (CreationToolsIds.CREATE__OPERATION__TOOL.equals(this.creationToolId)) {
			createNodeIntoContainer(STEREOTYPE, MappingTypes.STEREOTYPE_NODE_TYPE, MappingTypes.STEREOTYPE_NODE_PRD_OPERATIONSCOMPARTMENT_TYPE, UMLPackage.eINSTANCE.getClass_OwnedOperation());
		} else if (CreationToolsIds.CREATE__PROPERTY__TOOL.equals(this.creationToolId)) {
			createNodeIntoContainer(STEREOTYPE, MappingTypes.STEREOTYPE_NODE_TYPE, MappingTypes.STEREOTYPE_NODE_PRD_ATTRIBUTESCOMPARTMENT_TYPE, UMLPackage.eINSTANCE.getStructuredClassifier_OwnedAttribute());
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeIntoEnumeration() {
		if (CreationToolsIds.CREATE__ENUMERATION_LITERAL__TOOL.equals(this.creationToolId)) {
			createNodeIntoContainer(ENUMERATION, MappingTypes.ENUMERATION_NODE_TYPE, MappingTypes.ENUMERATION_NODE_PRD_ENUMERATIONLITERALSCOMPARTMENT_TYPE, UMLPackage.eINSTANCE.getEnumeration_OwnedLiteral());
		}
	}

	private void createNodeIntoContainer(final String semanticOwnerName, final String nodeContainerType, final String nodeCompartmentContainerType, EReference containmentFeature) {
		this.semanticOwnerName = semanticOwnerName;
		final EObject graphicalContainer = getSubNodeOfGraphicalContainer(nodeCompartmentContainerType);
		final ISemanticRepresentationElementCreationChecker semanticChecker = new SemanticNodeCreationChecker(getSemanticOwner(), containmentFeature, this.expectedType);
		final IGraphicalRepresentationElementCreationChecker graphicalNodeCreationChecker = new DNodeListElementCreationChecker(getDiagram(), graphicalContainer, this.nodeMappingType);
		createNode(this.creationToolId, new SemanticAndGraphicalCreationChecker(semanticChecker, graphicalNodeCreationChecker), graphicalContainer);
	}

	@Parameters(name = "{index} Test {0} tool")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ CreationToolsIds.CREATE__ENUMERATION_LITERAL__TOOL, MappingTypes.ENUMERATIONLITERAL_NODE_TYPE, EnumerationLiteral.class },
				{ CreationToolsIds.CREATE__OPERATION__TOOL, MappingTypes.OPERATION_NODE_TYPE, Operation.class },
				{ CreationToolsIds.CREATE__PROPERTY__TOOL, MappingTypes.PROPERTY_NODE_TYPE, Property.class },
		});
	}

}
