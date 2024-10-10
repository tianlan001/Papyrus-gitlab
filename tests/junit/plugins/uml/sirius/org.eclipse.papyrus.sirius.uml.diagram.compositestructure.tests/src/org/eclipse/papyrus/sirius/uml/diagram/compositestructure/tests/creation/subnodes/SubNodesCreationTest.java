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
package org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests.creation.subnodes;

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
import org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests.MappingTypes;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.CollaborationUse;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.InformationItem;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class tests the creation of subnode containers for CSD.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
@PluginResource("resources/creation/subNodes/subNodes.di")
@RunWith(value = Parameterized.class)
public class SubNodesCreationTest extends AbstractCreateNodeTests {

	private static final String DIAGRAM_NAME = "SubNodeCompositeStructureDiagramSirius"; //$NON-NLS-1$

	private final String creationToolId;

	private final String nodeMappingType;

	private final EReference containmentFeature;

	private String semanticOwnerName;

	private final Class<? extends Element> expectedType;

	/**
	 * Default Constructor.
	 *
	 */
	public SubNodesCreationTest(String creationToolId, String nodeMappingType, EReference containmentFeature, Class<? extends Element> expectedType) {
		this.creationToolId = creationToolId;
		this.nodeMappingType = nodeMappingType;
		this.containmentFeature = containmentFeature;
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
	public void createNodeIntoActivity() {
		createNodeIntoContainer("Activity1", MappingTypes.ACTIVITY_NODE_TYPE, MappingTypes.ACTIVITY_NODE_CSD_COMPARTMENTS_TYPE); //$NON-NLS-1$
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeIntoClass() {
		List<String> forbiddenTools = List.of(CreationToolsIds.CREATE__PARAMETER__TOOL);
		if (!forbiddenTools.contains(this.creationToolId)) {
			createNodeIntoContainer("Class1", MappingTypes.CLASS_NODE_TYPE, MappingTypes.CLASS_NODE_CSD_COMPARTMENTS_TYPE); //$NON-NLS-1$
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeIntoColaboration() {
		List<String> forbiddenTools = List.of(CreationToolsIds.CREATE__PORT__TOOL, CreationToolsIds.CREATE__INFORMATION_ITEM__TOOL, CreationToolsIds.CREATE__PARAMETER__TOOL);
		if (!forbiddenTools.contains(this.creationToolId)) {
			createNodeIntoContainer("Collaboration1", MappingTypes.COLLABORATION_NODE_TYPE, MappingTypes.COLLABORATION_NODE_CSD_COMPARTMENTS_TYPE); //$NON-NLS-1$
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeIntoFunctionBehavior() {
		createNodeIntoContainer("FunctionBehavior1", MappingTypes.FUNCTION_BEHAVIOR_NODE_TYPE, MappingTypes.FUNCTION_BEHAVIOR_NODE_CSD_COMPARTMENTS_TYPE); //$NON-NLS-1$
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeIntoInteraction() {
		createNodeIntoContainer("Interaction1", MappingTypes.INTERACTION_NODE_TYPE, MappingTypes.INTERACTION_NODE_CSD_COMPARTMENTS_TYPE); //$NON-NLS-1$
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeIntoOpaqueBehavior() {
		createNodeIntoContainer("OpaqueBehavior1", MappingTypes.OPAQUE_BEHAVIOR_NODE_TYPE, MappingTypes.OPAQUE_BEHAVIOR_NODE_CSD_COMPARTMENTS_TYPE); //$NON-NLS-1$
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeIntoProtocolStateMachine() {
		createNodeIntoContainer("ProtocolStateMachine1", MappingTypes.PROTOCOL_STATE_MACHINE_NODE_TYPE, MappingTypes.PROTOCOL_STATE_MACHINE_NODE_CSD_COMPARTMENTS_TYPE); //$NON-NLS-1$
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeIntoStateMachine() {
		createNodeIntoContainer("StateMachine1", MappingTypes.STATE_MACHINE_NODE_TYPE, MappingTypes.STATE_MACHINE_NODE_CSD_COMPARTMENTS_TYPE); //$NON-NLS-1$
	}

	private void createNodeIntoContainer(final String semanticOwnerName, final String nodeContainerType, final String nodeCompartmentContainerType) {
		final String containerType;
		if (MappingTypes.isBorderNode(this.nodeMappingType)) {
			containerType = nodeContainerType;
		} else {
			containerType = nodeCompartmentContainerType;
		}
		this.semanticOwnerName = semanticOwnerName;
		final EObject graphicalContainer = getSubNodeOfGraphicalContainer(containerType);
		final ISemanticRepresentationElementCreationChecker semanticChecker = new SemanticNodeCreationChecker(getSemanticOwner(), this.containmentFeature, this.expectedType);
		final IGraphicalRepresentationElementCreationChecker graphicalNodeCreationChecker = new DNodeCreationChecker(getDiagram(), graphicalContainer, this.nodeMappingType);
		createNode(this.creationToolId, new SemanticAndGraphicalCreationChecker(semanticChecker, graphicalNodeCreationChecker), graphicalContainer);
	}

	@Parameters(name = "{index} Test {0} tool")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ CreationToolsIds.CREATE__COMMENT__TOOL, MappingTypes.COMMENT_NODE_TYPE, UMLPackage.eINSTANCE.getElement_OwnedComment(), Comment.class },
				{ CreationToolsIds.CREATE__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE, UMLPackage.eINSTANCE.getNamespace_OwnedRule(), Constraint.class },
				{ CreationToolsIds.CREATE__PORT__TOOL, MappingTypes.PORT_NODE_TYPE, UMLPackage.eINSTANCE.getStructuredClassifier_OwnedAttribute(), Port.class },
				{ CreationToolsIds.CREATE__COLABORATION_USE__TOOL, MappingTypes.COLLABORATION_USE_NODE_TYPE, UMLPackage.eINSTANCE.getClassifier_CollaborationUse(), CollaborationUse.class },
				{ CreationToolsIds.CREATE__INFORMATION_ITEM__TOOL, MappingTypes.INFORMATION_ITEM_NODE_TYPE, UMLPackage.eINSTANCE.getClass_NestedClassifier(), InformationItem.class },
				{ CreationToolsIds.CREATE__PARAMETER__TOOL, MappingTypes.PARAMETER_NODE_TYPE, UMLPackage.eINSTANCE.getBehavior_OwnedParameter(), Parameter.class }
		});
	}

}
