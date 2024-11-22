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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractCreateNodeTests;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.checker.SemanticAndGraphicalCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DNodeCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.SemanticNodeCreationChecker;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.MappingTypes;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.ActionExecutionSpecification;
import org.eclipse.uml2.uml.BehaviorExecutionSpecification;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class groups all tests about element creation as sub-nodes of the Sequence Diagram represented by an {@link Interaction}.
 * 
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@PluginResource("resources/creation/subNodes/SubNodes_CreationTest.di")
@RunWith(value = Parameterized.class)
public class SDCreateSubNodesOnSequenceTest extends AbstractCreateNodeTests {

	private static final String DIAGRAM_NAME = "SubNodes_SequenceDiagramSirius"; //$NON-NLS-1$

	private static final String INTERACTION_NAME = "Interaction"; //$NON-NLS-1$

	private static final String LIFELINE_NAME = "Lifeline1"; //$NON-NLS-1$

	private final String creationToolId;

	private final String nodeMappingType;

	private EReference containmentFeature;

	private String semanticOwnerName;

	private final Class<? extends Element> expectedType;

	/**
	 * Constructor.
	 * 
	 */
	public SDCreateSubNodesOnSequenceTest(String creationToolId, String nodeMappingType, EReference containmentFeature, Class<? extends Element> expectedType) {
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
		Element owner;
		if (MappingTypes.EXECUTION_SPECIFICATION_NODE_TYPE.equals(this.nodeMappingType)) {
			// The graphical owner of an Execution is the lifeline, even if the semantic owner is the Interaction.
			owner = ((Interaction) getSemanticOwner()).getMember(LIFELINE_NAME);

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
	protected boolean applyCreationTool(String creationToolId, DDiagram diagram, EObject graphicalContainer) {
		if (nodeMappingType.equals(MappingTypes.EXECUTION_SPECIFICATION_NODE_TYPE)) {
			return fixture.applyNodeCreationToolFromPalette(creationToolId, diagram, graphicalContainer, new Point(1, 1000), null);
		}
		return super.applyCreationTool(creationToolId, diagram, graphicalContainer);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeIntoLifeline() {
		createNodeIntoContainer(INTERACTION_NAME, MappingTypes.EXECUTION_SPECIFICATION_NODE_TYPE, MappingTypes.LIFELINE_EXECUTION_NODE_TYPE);
	}

	private void createNodeIntoContainer(final String ownerName, final String nodeContainerType, final String nodeCompartmentContainerType) {
		final String containerType = nodeCompartmentContainerType;
		this.semanticOwnerName = ownerName;
		final EObject graphicalContainer = getSubNodeOfGraphicalContainer(containerType);
		SemanticNodeCreationChecker semanticChecker = new SemanticNodeCreationChecker(getSemanticOwner(), this.containmentFeature, this.expectedType);
		DNodeCreationChecker graphicalChecker = new DNodeCreationChecker(getDiagram(), graphicalContainer, this.nodeMappingType);
		List<String> executionSpecificationTools = List.of(//
				CreationToolsIds.CREATE_ACTION_EXECUTION_SPECIFICATION_TOOL //
				, CreationToolsIds.CREATE_BEHAVIOR_EXECUTION_SPECIFICATION_TOOL);
		if (executionSpecificationTools.contains(this.creationToolId)) {
			semanticChecker.setExpectedCreatedElements(3); // ExecutionSpecification creation tools create 3 semantic elements in the same containment reference: the
															// ExecutionSpecification itself plus the OccurrenceSpecification representing the start/finish of the execution.
			graphicalChecker.setExpectedCreatedElements(3); // ExecutionSpecification creation tools create 3 graphical element: the node for the ExecutionSpecification and
															// the transparent nodes associated to OccurrenceSpecification representing the semantic ends of the execution.
		}

		createNode(this.creationToolId, new SemanticAndGraphicalCreationChecker(semanticChecker, graphicalChecker), graphicalContainer, true);
	}

	@Parameters(name = "{index} Test {0} tool")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ CreationToolsIds.CREATE_ACTION_EXECUTION_SPECIFICATION_TOOL, MappingTypes.EXECUTION_SPECIFICATION_NODE_TYPE, UMLPackage.eINSTANCE.getInteraction_Fragment(), ActionExecutionSpecification.class },
				{ CreationToolsIds.CREATE_BEHAVIOR_EXECUTION_SPECIFICATION_TOOL, MappingTypes.EXECUTION_SPECIFICATION_NODE_TYPE, UMLPackage.eINSTANCE.getInteraction_Fragment(), BehaviorExecutionSpecification.class }
		});
	}
}
