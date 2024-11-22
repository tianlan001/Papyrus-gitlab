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
package org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.creation.subnodes;

import java.util.List;
import java.util.Optional;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractCreateNodeTests;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.checker.SemanticAndGraphicalCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DNodeContainerCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DNodeCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.ISemanticRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.SemanticNodeCreationChecker;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.ContainmentFeatureHelper;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.MappingTypes;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.ActionExecutionSpecification;
import org.eclipse.uml2.uml.BehaviorExecutionSpecification;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionOperand;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class groups all tests about element creation as sub-nodes of the Sequence Diagram represented by an {@link Interaction}.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
@PluginResource("resources/creation/subNodes/SubNodes_CreationTest.di")
@RunWith(value = Parameterized.class)
public class SDCreateSubNodesTest extends AbstractCreateNodeTests {

	private static final String COMBINED_FRAGMENT_NAME = "CombinedFragment1"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "SubNodes_SequenceDiagramSirius"; //$NON-NLS-1$

	private static final String INTERACTION_NAME = "Interaction"; //$NON-NLS-1$

	private static final String INTERACTION_OPERAND_NAME = "InteractionOperand1"; //$NON-NLS-1$

	private final String creationToolId;

	private final String nodeMappingType;

	private EReference containmentFeature;

	private String semanticOwnerName;

	private final Class<? extends Element> expectedType;

	/**
	 * Constructor.
	 * 
	 */
	public SDCreateSubNodesTest(Class<? extends Element> expectedType) {
		this.creationToolId = CreationToolsIds.getCreationToolId(expectedType);
		this.nodeMappingType = MappingTypes.getMappingType(expectedType);
		this.containmentFeature = ContainmentFeatureHelper.getContainmentFeature(expectedType);
		this.expectedType = expectedType;
	}

	@Override
	protected Element getSemanticOwner() {
		return this.getElementByNameFromRoot(INTERACTION_NAME).get();
	}

	@Override
	protected EObject getTopGraphicalContainer() {
		final DDiagram ddiagram = getDDiagram();
		Element owner;
		if (INTERACTION_OPERAND_NAME.equals(this.semanticOwnerName)) {
			owner = ((Interaction) getSemanticOwner()).getMember(COMBINED_FRAGMENT_NAME);
		} else {
			owner = getSemanticOwner();
		}
		// @formatter:off
		Optional<DSemanticDecorator> optionalDSemanticDecorator = ddiagram.getOwnedDiagramElements().stream()
				.filter(DSemanticDecorator.class::isInstance)
				.map(DSemanticDecorator.class::cast)
				.filter(semanticDecorator -> semanticDecorator.getTarget().equals(owner))
				.findFirst();
		// @formatter:on
		Assert.assertTrue(optionalDSemanticDecorator.isPresent());
		final DSemanticDecorator element = optionalDSemanticDecorator.get();
		Assert.assertEquals(owner, element.getTarget());
		return element;
	}

	@Override
	protected boolean applyCreationTool(String toolId, DDiagram diagram, EObject graphicalContainer) {
		if (nodeMappingType.equals(MappingTypes.getMappingType(ExecutionSpecification.class))) {
			return fixture.applyNodeCreationToolFromPalette(toolId, diagram, graphicalContainer, new Point(1, 1000), null);
		}
		return super.applyCreationTool(toolId, diagram, graphicalContainer);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeIntoInteractionOperand() {
		List<String> forbiddenTools = List.of(CreationToolsIds.getCreationToolId(ActionExecutionSpecification.class), CreationToolsIds.getCreationToolId(BehaviorExecutionSpecification.class));
		if (!forbiddenTools.contains(this.creationToolId)) {
			this.semanticOwnerName = INTERACTION_OPERAND_NAME;
			Element semanticOwner = this.getElementByNameFromRoot(this.semanticOwnerName).get();
			createNodeIntoContainer(semanticOwner, MappingTypes.getMappingType(CombinedFragment.class), MappingTypes.getMappingType(InteractionOperand.class));
		}
	}

	private void createNodeIntoContainer(Element semanticOwner, final String nodeContainerType, final String nodeCompartmentContainerType) {
		final String containerType;
		if (MappingTypes.isBorderNode(this.nodeMappingType)) {
			containerType = nodeContainerType;
		} else {
			containerType = nodeCompartmentContainerType;
		}
		final EObject graphicalContainer = this.getSubNodeOfGraphicalContainer(containerType);
		final ISemanticRepresentationElementCreationChecker semanticChecker = new SemanticNodeCreationChecker(semanticOwner, this.containmentFeature, this.expectedType);
		final IGraphicalRepresentationElementCreationChecker graphicalNodeCreationChecker;
		if (MappingTypes.isNode(this.nodeMappingType) || MappingTypes.isBorderNode(this.nodeMappingType)) {
			graphicalNodeCreationChecker = new DNodeCreationChecker(this.getDiagram(), graphicalContainer, this.nodeMappingType);
		} else {
			graphicalNodeCreationChecker = new DNodeContainerCreationChecker(this.getDiagram(), graphicalContainer, this.nodeMappingType, List.of());
		}

		this.createNode(this.creationToolId, new SemanticAndGraphicalCreationChecker(semanticChecker, graphicalNodeCreationChecker), graphicalContainer, true);
	}

	@Parameters(name = "{index} Test {0} tool")
	public static List<Class<? extends Element>> data() {
		return List.of(Constraint.class, Comment.class);
	}
}
