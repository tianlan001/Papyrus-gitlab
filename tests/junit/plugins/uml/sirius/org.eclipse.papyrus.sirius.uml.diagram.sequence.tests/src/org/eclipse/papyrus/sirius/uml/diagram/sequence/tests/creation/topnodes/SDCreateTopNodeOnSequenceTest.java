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
import org.eclipse.uml2.uml.Model;
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

	private final NodeCreation usecase;

	/**
	 * Constructor.
	 *
	 * @param description
	 *            test description
	 */
	public SDCreateTopNodeOnSequenceTest(NodeCreation description) {
		usecase = description;
	}

	private Interaction getMainInteraction() {
		return (Interaction) ((Model) getSemanticOwner()).getMembers().get(0);
	}

	@Override
	protected boolean applyCreationTool(String toolId, DDiagram diagram, EObject graphicalContainer) {
		if (usecase.location != null) {
			return fixture.applyNodeCreationToolFromPalette(toolId, diagram, graphicalContainer, usecase.location, null);
		} else {
			return super.applyCreationTool(toolId, diagram, graphicalContainer);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createTopNodeTest() {
		final ISemanticRepresentationElementCreationChecker semanticChecker = new SemanticNodeCreationChecker(getMainInteraction(),
				usecase.getContainment(), usecase.type);
		DNodeCreationChecker viewChecker = new DNodeCreationChecker(getDiagram(), getTopGraphicalContainer(),
				usecase.getMappingType());
		viewChecker.setExpectedCreatedElements(usecase.createdNumber);

		// isSynchronize = true because Sequence diagrams have to be synchronized.
		createNode(usecase.getCreationTool(), new SemanticAndGraphicalCreationChecker(semanticChecker, viewChecker), getTopGraphicalContainer(), true);
	}

	/**
	 * Creates Use-cases for tests.
	 *
	 * @return {@link NodeCreation}s
	 */
	@Parameters(name = "{index} Test {0} tool")
	public static List<NodeCreation> data() {
		return List.of(
				new NodeCreation(Lifeline.class, new Point(100, 100), 3 /* Header, body, resize footer */),
				new NodeCreation(Constraint.class, null, 1),
				new NodeCreation(Comment.class, null, 1));
	}

	/**
	 * Use-case for test suite.
	 * <p>
	 * Provides the parameters for a test instance.
	 * </p>
	 *
	 * @param type
	 *            class of element to create
	 * @param location
	 *            indicates where the creation should happen
	 * @param createdNumber
	 *            number of created elements
	 *
	 */
	record NodeCreation(Class<? extends Element> type, Point location, int createdNumber) {

		String getCreationTool() {
			return CreationToolsIds.getCreationToolId(type);
		}

		String getMappingType() {
			return MappingTypes.getMappingType(type);
		}

		EReference getContainment() {
			return ContainmentFeatureHelper.getContainmentFeature(type);
		}

		@Override
		public final String toString() {
			return getCreationTool();
		}
	}

}
