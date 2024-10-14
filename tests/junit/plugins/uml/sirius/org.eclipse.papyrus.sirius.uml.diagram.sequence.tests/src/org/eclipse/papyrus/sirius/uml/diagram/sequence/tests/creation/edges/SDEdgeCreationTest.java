/*****************************************************************************
 * Copyright (c) 2024 CEA LIST.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.creation.edges;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractCreateEdgeTests;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.checker.SemanticAndGraphicalCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DEdgeCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.GraphicalOwnerUtils;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.ISemanticRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.SemanticEdgeCreationChecker;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.MappingTypes;
import org.eclipse.sirius.diagram.AbsoluteBoundsFilter;
import org.eclipse.sirius.diagram.AbstractDNode;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DNode;
import org.eclipse.sirius.diagram.EdgeTarget;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class groups all tests about edge creation in the Sequence Diagram represented by an {@link Interaction}.
 * 
 * @author <a href="mailto:gwendal.daniel@obeo.fr">Gwendal Daniel</a>
 */
@PluginResource("resources/creation/edges/Edge_CreationTest.di")
@RunWith(value = Parameterized.class)
public class SDEdgeCreationTest extends AbstractCreateEdgeTests {

	private static final String LIFELINE_1 = "Lifeline1"; //$NON-NLS-1$

	private static final String LIFELINE_2 = "Lifeline2"; //$NON-NLS-1$

	private static final String ACTION_EXECUTION_SPECIFICATION = "ActionExecutionSpecification"; //$NON-NLS-1$

	// @formatter:off
	private static final Map<String, String> elementTypeToMapping = Map.of(
			ACTION_EXECUTION_SPECIFICATION, MappingTypes.EXECUTION_SPECIFICATION_NODE_TYPE);
	// @formatter:on

	private static final String DIAGRAM_NAME = "Edge_SequenceDiagramSirius"; //$NON-NLS-1$

	private final String sourceName;

	private final String targetName;

	private final String mappingSourceTypeName;

	private final String mappingTargetTypeName;

	/**
	 * Constructor.
	 */
	public SDEdgeCreationTest(String sourceName, String targetName, String mappingSourceTypeName, String mappingTargetTypeName) {
		this.sourceName = sourceName;
		this.targetName = targetName;
		this.mappingSourceTypeName = mappingSourceTypeName;
		this.mappingTargetTypeName = mappingTargetTypeName;
	}

	private DSemanticDecorator getSDLifelineExecution(String lifelineName) {
		final DDiagram ddiagram = getDDiagram();

		EObject owner = ((Interaction) root.getMembers().get(0)).getMember(lifelineName);

		// @formatter:off
		Optional<DNode> optionalDSemanticDecorator = ddiagram.getOwnedDiagramElements().stream()
				.filter(DNode.class::isInstance)
				.map(DNode.class::cast)
				.filter(semanticDecorator -> semanticDecorator.getTarget().equals(owner))
				.findFirst();
		// @formatter:on
		Assert.assertTrue(optionalDSemanticDecorator.isPresent());
		final DNode lifelineElement = optionalDSemanticDecorator.get();
		Assert.assertEquals(owner, lifelineElement.getTarget());
		Assert.assertEquals(1, lifelineElement.getOwnedBorderedNodes().size());
		Assert.assertEquals(MappingTypes.LIFELINE_EXECUTION_NODE_TYPE, lifelineElement.getOwnedBorderedNodes().get(0).getMapping().getName());
		return lifelineElement.getOwnedBorderedNodes().get(0);
	}

	@Override
	protected AbstractDNode getNodeFromContainer(String elementNameNode, String mappingTypeName, EObject graphicalContainer) {
		if (elementNameNode.startsWith(ACTION_EXECUTION_SPECIFICATION)) {
			EObject element = ((Interaction) this.root.getMembers().get(0)).getMember(elementNameNode);
			for (EObject dDiagramElement : GraphicalOwnerUtils.getChildren(graphicalContainer)) {
				if (dDiagramElement instanceof AbstractDNode && mappingTypeName.equals(((AbstractDNode) dDiagramElement).getMapping().getName())) {
					AbstractDNode abstractNode = (AbstractDNode) dDiagramElement;
					if (element.equals(abstractNode.getSemanticElements().get(0))) {
						return abstractNode;
					}
				}
			}
			return null;
		}
		return super.getNodeFromContainer(elementNameNode, mappingTypeName, graphicalContainer);
	}


	@Override
	protected boolean applyCreationTool(String creationToolId, DDiagram diagram, EdgeTarget edgeSource, EdgeTarget edgeTarget) {
		Point edgeSourceAbsolutePosition = this.getAbsolutePosition((AbstractDNode) this.getEdgeSource());
		Point edgeTargetAbsolutePosition = this.getAbsolutePosition((AbstractDNode) this.getEdgeTarget());
		return fixture.applyEdgeCreationToolFromPalette(creationToolId, diagram, edgeSource, edgeTarget, edgeSourceAbsolutePosition, edgeTargetAbsolutePosition);
	}

	private Point getAbsolutePosition(AbstractDNode node) {
		AbsoluteBoundsFilter boundsFilter = (AbsoluteBoundsFilter) node.getGraphicalFilters().get(0);
		// Use width/2 and height/2 to make sure we select the graphical element at its center.
		return new Point(boundsFilter.getX().intValue() + (boundsFilter.getWidth().intValue() / 2), boundsFilter.getY().intValue() + (boundsFilter.getHeight().intValue() / 2));
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createMessageTest() {
		testEdgeCreation(CreationToolsIds.CREATE_MESSAGE_SYNC_TOOL, UMLPackage.eINSTANCE.getInteraction_Message(), Message.class, this.root.getMembers().get(0), MappingTypes.MESSAGE_EDGE_TYPE);
	}

	private void testEdgeCreation(String toolId, EReference containmentFeature, Class<? extends Element> expectedType, EObject expectedOwner, String expectedEdgeMappingType) {
		this.setSemanticSource(this.root.getMember(sourceName));
		this.setSemanticTarget(this.root.getMember(targetName));
		this.setEdgeSource(getNodeFromContainer(sourceName, mappingSourceTypeName, this.getSDLifelineExecution(LIFELINE_1)));
		this.setEdgeTarget(getNodeFromContainer(targetName, mappingTargetTypeName, this.getSDLifelineExecution(LIFELINE_2)));
		final ISemanticRepresentationElementCreationChecker semanticChecker = new SemanticEdgeCreationChecker(expectedOwner, containmentFeature, expectedType);
		final IGraphicalRepresentationElementCreationChecker graphicalEdgeCreationChecker = new DEdgeCreationChecker(getDiagram(), getDDiagram(), expectedEdgeMappingType);
		createEdge(toolId, new SemanticAndGraphicalCreationChecker(semanticChecker, graphicalEdgeCreationChecker), true);
	}

	@Parameters(name = "{index} create edge between {0} and {1}")
	public static Collection<Object[]> data() {
		Collection<Object[]> data = new ArrayList<>();
		for (String sourceName : elementTypeToMapping.keySet()) {
			for (String targetName : elementTypeToMapping.keySet()) {
				Object[] array = { sourceName + "1", targetName + "2", elementTypeToMapping.get(sourceName), elementTypeToMapping.get(targetName) }; //$NON-NLS-1$//$NON-NLS-2$
				data.add(array);
			}
		}
		return data;
	}

}
