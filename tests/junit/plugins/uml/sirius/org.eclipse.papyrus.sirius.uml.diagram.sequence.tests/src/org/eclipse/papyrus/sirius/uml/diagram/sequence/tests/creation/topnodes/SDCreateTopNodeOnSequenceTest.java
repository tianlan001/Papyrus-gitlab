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
package org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.creation.topnodes;

import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractCreateTopNodeOnDiagramTests;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.checker.SemanticAndGraphicalCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DNodeCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.ISemanticRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.SemanticNodeCreationChecker;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.ContainmentFeatureHelper;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.MappingTypes;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Lifeline;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class groups all tests about element creation on the root of the Sequence Diagram represented by an {@link Interaction}.
 * 
 * @author <a href="mailto:gwendal.daniel@obeosoft.com>Gwendal Daniel</a>
 */
@PluginResource("resources/creation/topNodes/TopNode_CreationTest.di")
@RunWith(value = Parameterized.class)
public class SDCreateTopNodeOnSequenceTest extends AbstractCreateTopNodeOnDiagramTests {

	protected static final String DIAGRAM_NAME = "TopNode_SequenceDiagramSirius"; //$NON-NLS-1$

	private final String creationToolId;

	private final String nodeMappingType;

	private final EReference containmentFeature;

	private final Class<? extends Element> expectedType;

	/**
	 * Constructor.
	 */
	public SDCreateTopNodeOnSequenceTest(Class<? extends Element> expectedType/* , EReference containmentFeature */) {
		this.creationToolId = CreationToolsIds.getCreationToolId(expectedType);
		this.nodeMappingType = MappingTypes.getMappingType(expectedType);
		this.containmentFeature = ContainmentFeatureHelper.getContainmentFeature(expectedType);
		this.expectedType = expectedType;
	}

	@Override
	protected boolean applyCreationTool(String toolId, DDiagram diagram, EObject graphicalContainer) {
		if (MappingTypes.getMappingType(Lifeline.class).equals(nodeMappingType)) {
			/*
			 * Lifeline tests require a custom tool creation invocation.
			 */
			return fixture.applyNodeCreationToolFromPalette(toolId, getDDiagram(), graphicalContainer, new Point(100, 100), null);
		} else {
			return super.applyCreationTool(toolId, diagram, graphicalContainer);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createTopNodeTest() {
		final Diagram diagram = getDiagram();
		final ISemanticRepresentationElementCreationChecker semanticChecker = new SemanticNodeCreationChecker(((org.eclipse.uml2.uml.Package) getSemanticOwner()).getPackagedElements().get(0), this.containmentFeature, this.expectedType);
		DNodeCreationChecker graphicalNodeCreationChecker = new DNodeCreationChecker(diagram, getTopGraphicalContainer(), nodeMappingType);
		if (MappingTypes.getMappingType(Lifeline.class).equals(nodeMappingType)) {
			/*
			 * Lifeline tests require a custom graphical checker: when a Lifeline is created, the header and the line are created.
			 */
			graphicalNodeCreationChecker.setExpectedCreatedElements(2);
		}
		/*
		 * isSynchronize = true because Sequence diagrams have to be synchronized.
		 */
		createNode(creationToolId, new SemanticAndGraphicalCreationChecker(semanticChecker, graphicalNodeCreationChecker), getTopGraphicalContainer(), true);
	}

	@Parameters(name = "{index} Test {0} tool")
	public static List<Class<? extends Element>> data() {
		return List.of(Lifeline.class, Constraint.class, Comment.class);
	}
}
