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
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DNodeContainerCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.ISemanticRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.SemanticNodeCreationChecker;
import org.eclipse.papyrus.sirius.uml.diagram.component.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.component.tests.MappingTypes;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Groups all tests related to sub-node container creation for the Component diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@PluginResource("resources/creation/subNodes/subNodes.di")
@RunWith(value = Parameterized.class)
public class SubNodeContainersCreationTest extends AbstractCreateNodeTests {

	private static final String DIAGRAM_NAME = "SubNodes_ComponentDiagramSirius"; //$NON-NLS-1$

	private static final String COMPONENT = "Component1"; //$NON-NLS-1$

	private static final String MODEL = "Model1"; //$NON-NLS-1$

	private static final String PACKAGE = "Package1"; //$NON-NLS-1$

	private static final String TYPED_PROPERTY = "TypedProperty"; //$NON-NLS-1$

	private final String creationToolId;

	private final String nodeMappingType;

	private final List<String> nodeCompartmentTypes;

	private String semanticOwnerName;

	private final Class<? extends Element> expectedType;

	public SubNodeContainersCreationTest(String creationToolId, String nodeMappingType, List<String> nodeCompartmentTypes, Class<? extends Element> expectedType) {
		this.creationToolId = creationToolId;
		this.nodeMappingType = nodeMappingType;
		this.nodeCompartmentTypes = List.copyOf(nodeCompartmentTypes);
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
		if (MappingTypes.PROPERTY_IN_TYPED_PROPERTY_NODE_TYPE.equals(this.nodeMappingType)) {
			/*
			 * If we are creating a property in a property we need to retrieve the property using
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
	public void createNodeContainerIntoComponent() {
		List<String> allowedTools = List.of(CreationToolsIds.CREATE__COMPONENT__TOOL, CreationToolsIds.CREATE__PROPERTY__TOOL);
		if (allowedTools.contains(this.creationToolId)) {
			if (CreationToolsIds.CREATE__COMPONENT__TOOL.equals(this.creationToolId)) {
				this.createNodeContainer(COMPONENT, MappingTypes.COMPONENT_NODE_CPD_ATTRIBUTES_COMPARTMENT_TYPE, UMLPackage.eINSTANCE.getComponent_PackagedElement());
			} else if (CreationToolsIds.CREATE__PROPERTY__TOOL.equals(this.creationToolId) && MappingTypes.PROPERTY_NODE_TYPE.equals(this.nodeMappingType)) {
				// Do not run the test for MappingTypes.PROPERTY_IN_TYPED_PROPERTY_NODE_TYPE: this mapping cannot be created in a Component.
				this.createNodeContainer(COMPONENT, MappingTypes.COMPONENT_NODE_CPD_ATTRIBUTES_COMPARTMENT_TYPE, UMLPackage.eINSTANCE.getStructuredClassifier_OwnedAttribute());
			}
		}
	}


	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeContainerIntoModel() {
		List<String> allowedTools = List.of(CreationToolsIds.CREATE__COMPONENT__TOOL, CreationToolsIds.CREATE__INTERFACE__TOOL, CreationToolsIds.CREATE__MODEL__TOOL, CreationToolsIds.CREATE__PACKAGE__TOOL);
		if (allowedTools.contains(this.creationToolId)) {
			this.createNodeContainer(MODEL, MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, UMLPackage.eINSTANCE.getPackage_PackagedElement());
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeContainerIntoPackage() {
		List<String> allowedTools = List.of(CreationToolsIds.CREATE__COMPONENT__TOOL, CreationToolsIds.CREATE__INTERFACE__TOOL, CreationToolsIds.CREATE__MODEL__TOOL, CreationToolsIds.CREATE__PACKAGE__TOOL);
		if (allowedTools.contains(this.creationToolId)) {
			this.createNodeContainer(PACKAGE, MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, UMLPackage.eINSTANCE.getPackage_PackagedElement());
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeContainerIntoTypedProperty() {
		if (CreationToolsIds.CREATE__PROPERTY__TOOL.equals(this.creationToolId) && MappingTypes.PROPERTY_IN_TYPED_PROPERTY_NODE_TYPE.equals(this.nodeMappingType)) {
			// Do not run the test for MappingTypes.PROPERTY_NODE_TYPE: this mapping cannot be created in a Property.
			// Component1 is the semantic owner of the created property (it types the containing property)
			this.createNodeContainer(COMPONENT, MappingTypes.PROPERTY_NODE_CPD_PROPERTY_INTERNAL_STRUCTURE_COMPARTMENT, UMLPackage.eINSTANCE.getStructuredClassifier_OwnedAttribute());
		}
	}

	private void createNodeContainer(final String semanticOwnerName, final String containerMappingTypeName, final EReference containmentFeature) {
		this.semanticOwnerName = semanticOwnerName;
		final EObject graphicalContainer = this.getSubNodeOfGraphicalContainer(containerMappingTypeName);
		final ISemanticRepresentationElementCreationChecker semanticChecker = new SemanticNodeCreationChecker(this.getSemanticOwner(), containmentFeature, this.expectedType);
		final IGraphicalRepresentationElementCreationChecker graphicalNodeCreationChecker = new DNodeContainerCreationChecker(this.getDiagram(), graphicalContainer, this.nodeMappingType, this.nodeCompartmentTypes);
		this.createNode(this.creationToolId, new SemanticAndGraphicalCreationChecker(semanticChecker, graphicalNodeCreationChecker), graphicalContainer);
	}

	@Parameters(name = "{index} Test {0} tool")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ CreationToolsIds.CREATE__COMPONENT__TOOL, MappingTypes.COMPONENT_NODE_TYPE, List.of(MappingTypes.COMPONENT_NODE_CPD_ATTRIBUTES_COMPARTMENT_TYPE), Component.class },
				{ CreationToolsIds.CREATE__INTERFACE__TOOL, MappingTypes.INTERFACE_NODE_TYPE,
						List.of(MappingTypes.INTERFACE_NODE_CPD_ATTRIBUTES_COMPARTMENT_TYPE, MappingTypes.INTERFACE_NODE_CPD_OPERATIONS_COMPARTMENT_TYPE, MappingTypes.INTERFACE_NODE_CPD_RECEPTION_COMPARTMENT_TYPE), Interface.class },
				{ CreationToolsIds.CREATE__MODEL__TOOL, MappingTypes.MODEL_NODE_TYPE, List.of(MappingTypes.MODEL_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE), Model.class },
				{ CreationToolsIds.CREATE__PACKAGE__TOOL, MappingTypes.PACKAGE_NODE_TYPE, List.of(MappingTypes.PACKAGE_NODE_CPD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE), Package.class },
				{ CreationToolsIds.CREATE__PROPERTY__TOOL, MappingTypes.PROPERTY_NODE_TYPE, List.of(MappingTypes.PROPERTY_NODE_CPD_PROPERTY_INTERNAL_STRUCTURE_COMPARTMENT), Property.class },
				{ CreationToolsIds.CREATE__PROPERTY__TOOL, MappingTypes.PROPERTY_IN_TYPED_PROPERTY_NODE_TYPE, List.of(MappingTypes.PROPERTY_IN_TYPED_PROPERTY_NODE_CPD_INTERNAL_STRUCTURE_COMPARTMENT), Property.class }
		});
	}
}
