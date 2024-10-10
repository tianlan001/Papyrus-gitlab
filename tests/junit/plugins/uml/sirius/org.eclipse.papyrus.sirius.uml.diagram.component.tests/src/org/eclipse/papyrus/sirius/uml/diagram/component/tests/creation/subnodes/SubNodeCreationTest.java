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
package org.eclipse.papyrus.sirius.uml.diagram.component.tests.creation.subnodes;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractCreateNodeTests;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.checker.SemanticAndGraphicalCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DNodeCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.ISemanticRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.SemanticNodeCreationChecker;
import org.eclipse.papyrus.sirius.uml.diagram.component.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.component.tests.MappingTypes;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Groups all tests related to sub-node creation for the Component diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@PluginResource("resources/creation/subNodes/subNodes.di")
@RunWith(value = Parameterized.class)
public class SubNodeCreationTest extends AbstractCreateNodeTests {

	private static final String DIAGRAM_NAME = "SubNodes_ComponentDiagramSirius"; //$NON-NLS-1$

	private static final String COMPONENT = "Component1"; //$NON-NLS-1$

	private static final String MODEL = "Model1"; //$NON-NLS-1$

	private static final String PACKAGE = "Package1"; //$NON-NLS-1$

	private static final String TYPED_PROPERTY = "TypedProperty"; //$NON-NLS-1$

	private final String creationToolId;

	private final String nodeMappingType;

	private String containerType;

	private String semanticOwnerName;

	private final Class<? extends Element> expectedType;

	public SubNodeCreationTest(String creationToolId, String nodeMappingType, Class<? extends Element> expectedType) {
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
		final DDiagram ddiagram = this.getDDiagram();

		final DSemanticDecorator result;
		if (CreationToolsIds.CREATE__PORT__TOOL.equals(this.creationToolId)
				&& MappingTypes.PROPERTY_NODE_TYPE.equals(this.containerType)) {
			/*
			 * If we are creating a port on a property we need to retrieve the property using
			 * getElementsFromDiagramBySemanticName because the property isn't a direct child
			 * of the root (it is contained in a component which is contained in the root).
			 */
			List<DDiagramElement> properties = this.getElementsFromDiagramBySemanticName(TYPED_PROPERTY, MappingTypes.PROPERTY_NODE_TYPE);
			Assert.assertTrue(properties.size() > 0);
			result = properties.get(0);
		} else {
			Element semanticOwner = this.getSemanticOwner();
			// @formatter:off
			Optional<DSemanticDecorator> optionalDSemanticDecorator = ddiagram.getOwnedDiagramElements().stream()
					.filter(DSemanticDecorator.class::isInstance)
					.map(DSemanticDecorator.class::cast)
					.filter(semanticDecorator -> semanticDecorator.getTarget().equals(semanticOwner))
					.findFirst();
			// @formatter:on
			Assert.assertTrue(optionalDSemanticDecorator.isPresent());
			result = optionalDSemanticDecorator.get();
			Assert.assertEquals(semanticOwner, result.getTarget());
		}
		return result;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeIntoComponent() {
		if (CreationToolsIds.CREATE__PORT__TOOL.equals(this.creationToolId) && MappingTypes.PORT_NODE_TYPE.equals(this.nodeMappingType)) {
			// Port created on components can only be represented with MappingTypes.PORT_NODE_TYPE, do not test with other mappings.
			this.createNodeIntoContainer(COMPONENT, MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.COMPONENT_NODE_CPD_ATTRIBUTES_COMPARTMENT_TYPE, UMLPackage.eINSTANCE.getStructuredClassifier_OwnedAttribute());
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeIntoModel() {
		List<String> allowedTools = List.of(CreationToolsIds.CREATE__COMMENT__TOOL, CreationToolsIds.CREATE__CONSTRAINT__TOOL);
		if (allowedTools.contains(this.creationToolId)) {
			if (MappingTypes.COMMENT_NODE_TYPE.equals(this.nodeMappingType)) {
				this.createNodeIntoContainer(MODEL, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, UMLPackage.eINSTANCE.getElement_OwnedComment());
			} else if (MappingTypes.CONSTRAINT_NODE_TYPE.equals(this.nodeMappingType)) {
				this.createNodeIntoContainer(MODEL, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, UMLPackage.eINSTANCE.getNamespace_OwnedRule());
			}
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeIntoPackage() {
		List<String> allowedTools = List.of(CreationToolsIds.CREATE__COMMENT__TOOL, CreationToolsIds.CREATE__CONSTRAINT__TOOL);
		if (allowedTools.contains(this.creationToolId)) {
			if (MappingTypes.COMMENT_NODE_TYPE.equals(this.nodeMappingType)) {
				this.createNodeIntoContainer(PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, UMLPackage.eINSTANCE.getElement_OwnedComment());
			} else if (MappingTypes.CONSTRAINT_NODE_TYPE.equals(this.nodeMappingType)) {
				this.createNodeIntoContainer(PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, UMLPackage.eINSTANCE.getNamespace_OwnedRule());
			}
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeIntoTypedProperty() {
		if (CreationToolsIds.CREATE__PORT__TOOL.equals(this.creationToolId) && MappingTypes.PORT_ON_TYPED_PROPERTY_NODE_TYPE.equals(this.nodeMappingType)) {
			// Port created on typed properties can only be represented with MappingTypes.PORT_ON_TYPED_PROPERTY_NODE_TYPE, do not test with other mappings.
			// null nodeCompartmentContainerType because compartments are ignored for bordered nodes.
			this.createNodeIntoContainer(COMPONENT, MappingTypes.PROPERTY_NODE_TYPE, null, UMLPackage.eINSTANCE.getClassifier_Attribute());
		}
	}

	private void createNodeIntoContainer(final String semanticOwnerName, final String nodeContainerType, final String nodeCompartmentContainerType, final EReference containmentFeature) {
		if (MappingTypes.isBorderNode(this.nodeMappingType)) {
			this.containerType = nodeContainerType;
		} else {
			this.containerType = nodeCompartmentContainerType;
		}
		this.semanticOwnerName = semanticOwnerName;
		final EObject graphicalContainer = this.getSubNodeOfGraphicalContainer(this.containerType);
		final ISemanticRepresentationElementCreationChecker semanticChecker = new SemanticNodeCreationChecker(this.getSemanticOwner(), containmentFeature, this.expectedType);
		final IGraphicalRepresentationElementCreationChecker graphicalNodeCreationChecker = new DNodeCreationChecker(this.getDiagram(), graphicalContainer, this.nodeMappingType);
		this.createNode(this.creationToolId, new SemanticAndGraphicalCreationChecker(semanticChecker, graphicalNodeCreationChecker), graphicalContainer);
	}

	@Parameters(name = "{index} Test {0} tool")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ CreationToolsIds.CREATE__COMMENT__TOOL, MappingTypes.COMMENT_NODE_TYPE, Comment.class },
				{ CreationToolsIds.CREATE__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE, Constraint.class },
				{ CreationToolsIds.CREATE__PORT__TOOL, MappingTypes.PORT_NODE_TYPE, Port.class },
				{ CreationToolsIds.CREATE__PORT__TOOL, MappingTypes.PORT_ON_TYPED_PROPERTY_NODE_TYPE, Port.class }
		});
	}
}
