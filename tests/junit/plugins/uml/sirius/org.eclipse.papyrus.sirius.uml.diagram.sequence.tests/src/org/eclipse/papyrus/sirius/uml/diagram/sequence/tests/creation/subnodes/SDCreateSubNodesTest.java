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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractCreateNodeTests;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.checker.SemanticAndGraphicalCreationChecker;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.util.SequenceTestHelper;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.util.SequenceTestHelper.NodeCreation;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interaction;
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

	private static final String DIAGRAM_NAME = "SubNodes_SequenceDiagramSirius"; //$NON-NLS-1$

	private final SequenceTestHelper helper = new SequenceTestHelper(this);
	private final NodeCreation usecase;

	/**
	 * Constructor.
	 *
	 * @param description
	 *            test description
	 */
	public SDCreateSubNodesTest(NodeCreation description) {
		usecase = description;
	}

	@Override
	protected Element getSemanticOwner() {
		// There is only 1 type of Free Form container in Sequence Diagram: SD_Operand.
		// CombinedFragment is not Free Form.
		CombinedFragment combine = (CombinedFragment) helper.getRoot().getOwnedMember("CombinedFragment1"); //$NON-NLS-1$
		return combine.getOperand("InteractionOperand1"); //$NON-NLS-1$
	}

	@Override
	protected DSemanticDecorator getTopGraphicalContainer() {
		Element operand = getSemanticOwner();
		DSemanticDecorator combineView = helper.getSingleView(operand.getOwner());
		return helper.getSingleView(operand, combineView);
	}

	@Override
	protected boolean applyCreationTool(String toolId, DDiagram diagram, EObject graphicalContainer) {
		return usecase.applyTool(helper, toolId, graphicalContainer);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeIntoInteractionOperand() {
		DSemanticDecorator containerView = getTopGraphicalContainer();
		SemanticAndGraphicalCreationChecker checker = usecase.createNodeChecker(helper, containerView.getTarget(), containerView);
		createNode(usecase.getCreationTool(), checker, containerView);
	}

	/**
	 * Creates Use-cases for tests.
	 *
	 * @return {@link NodeCreation}s
	 */
	@Parameters(name = "{index} Test {0} tool")
	public static List<NodeCreation> data() {
		return List.of(
				new NodeCreation(Constraint.class, null, 1, 1),
				new NodeCreation(Comment.class, null, 1, 1));
	}

}
