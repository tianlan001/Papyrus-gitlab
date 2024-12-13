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

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractCreateNodeTests;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.util.SequenceTestHelper;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.util.SequenceTestHelper.NodeCreation;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.uml2.uml.ActionExecutionSpecification;
import org.eclipse.uml2.uml.BehaviorExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Lifeline;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class groups all tests about element creation as sub-nodes on a {@link Lifeline} of the Sequence Diagram represented by an {@link Interaction}.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@PluginResource("resources/creation/subNodes/SubNodes_CreationTest.di")
@RunWith(value = Parameterized.class)
public class SDCreateSubNodesOnLifelineTest extends AbstractCreateNodeTests {

	private static final String DIAGRAM_NAME = "SubNodes_SequenceDiagramSirius"; //$NON-NLS-1$

	private final SequenceTestHelper helper = new SequenceTestHelper(this);
	private final NodeCreation usecase;

	/**
	 * Constructor.
	 *
	 * @param description
	 *            test description
	 */
	public SDCreateSubNodesOnLifelineTest(NodeCreation description) {
		usecase = description;
	}

	@Override
	protected EObject getSemanticOwner() {
		return helper.getRoot();
	}

	@Override
	protected EObject getTopGraphicalContainer() {
		return helper.getLifelineView(1);
	}

	@Override
	protected boolean applyCreationTool(String toolId, DDiagram diagram, EObject graphicalContainer) {
		return usecase.applyTool(helper, toolId, graphicalContainer);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeIntoLifeline() {
		EObject lifeline = getTopGraphicalContainer();
		createNode(usecase.getCreationTool(),
				usecase.createNodeChecker(helper, getSemanticOwner(), lifeline), lifeline);
	}


	/**
	 * Creates Use-cases for tests.
	 *
	 * @return {@link NodeCreation}s
	 */
	@Parameters(name = "{index} Test {0} tool")
	public static List<NodeCreation> data() {
		return List.of(
				new NodeCreation(ActionExecutionSpecification.class, new Point(1, 1000),
						3 /* execution, start, finish */,
						3 /* node, start anchor, finish anchor */),
				new NodeCreation(BehaviorExecutionSpecification.class, new Point(1, 1000), 3, 3));
	}

}
