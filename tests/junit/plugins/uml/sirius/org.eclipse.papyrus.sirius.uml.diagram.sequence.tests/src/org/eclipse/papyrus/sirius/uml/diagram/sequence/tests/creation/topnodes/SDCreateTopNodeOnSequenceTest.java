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
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractCreateTopNodeOnDiagramTests;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.util.SequenceTestHelper;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.util.SequenceTestHelper.NodeCreation;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
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

	private final SequenceTestHelper helper = new SequenceTestHelper(this);
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

	@Override
	protected boolean applyCreationTool(String toolId, DDiagram diagram, EObject graphicalContainer) {
		return usecase.applyTool(helper, toolId, graphicalContainer);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createTopNodeTest() {
		Interaction root = helper.getRoot();

		EObject containerView = getTopGraphicalContainer();
		createNode(usecase.getCreationTool(),
				usecase.createNodeChecker(helper, root, containerView), containerView);
	}

	/**
	 * Creates Use-cases for tests.
	 *
	 * @return {@link NodeCreation}s
	 */
	@Parameters(name = "{index} Test {0} tool")
	public static List<NodeCreation> data() {
		return List.of(
				new NodeCreation(Lifeline.class, new Point(100, 100), 1, 3 /* Header, body, resize footer */),
				new NodeCreation(Constraint.class, null, 1, 1),
				new NodeCreation(Comment.class, null, 1, 1));
	}


}
