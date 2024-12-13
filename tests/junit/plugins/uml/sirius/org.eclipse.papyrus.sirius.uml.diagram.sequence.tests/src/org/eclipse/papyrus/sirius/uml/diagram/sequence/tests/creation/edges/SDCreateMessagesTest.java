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

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractCreateEdgeTests;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.checker.SemanticAndGraphicalCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DEdgeCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.SemanticEdgeCreationChecker;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.MappingTypes;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.util.SequenceTestHelper;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.util.SequenceTestHelper.ViewKind;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.EdgeTarget;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.ExecutionSpecification;
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
 * @author <a href="mailto:nicolas.peransin@obeo.fr">Nicolas Peransin</a>
 */
@PluginResource("resources/creation/edges/Edge_CreationTest.di")
@RunWith(value = Parameterized.class)
public class SDCreateMessagesTest extends AbstractCreateEdgeTests {

	private static final String ACTIVE_DIAGRAM = "Edge_SequenceDiagramSirius"; //$NON-NLS-1$

	private final SequenceTestHelper helper = new SequenceTestHelper(this);
	private final EdgeCreation usecase;

	/**
	 * Constructor.
	 *
	 * @param description
	 *            test description
	 */
	public SDCreateMessagesTest(EdgeCreation description) {
		usecase = description;
	}

	@Override
	protected boolean applyCreationTool(String toolId, DDiagram diagram,
			EdgeTarget edgeSource, EdgeTarget edgeTarget) {
		return fixture.applyEdgeCreationToolFromPalette(toolId, diagram,
				edgeSource, edgeTarget,
				helper.getPositionFromEnd(edgeSource, usecase.y), helper.getPositionFromEnd(edgeTarget, usecase.y));
	}

	private void createMessage(String toolId, List<EdgeAssertion> expectations, int localAddition, int globalAddition) {
		usecase.configureContext(this);

		// Checkers must be created once context is configured.
		// Creates checker for each expected edges.
		int messageNumber = expectations.size();
		SemanticEdgeCreationChecker modelChecker = new SemanticEdgeCreationChecker(helper.getRoot(), UMLPackage.eINSTANCE.getInteraction_Message());
		modelChecker.setExpectedCreatedElements(messageNumber);

		List<SemanticAndGraphicalCreationChecker> verifications = expectations.stream().map(expectation -> {
			DEdgeCreationChecker viewChecker = new DEdgeCreationChecker(getDiagram(), MappingTypes.getMappingType(Message.class));
			viewChecker.setExpectedAdditionalEdges(messageNumber);
			viewChecker.setExpectedAdditionalChildren(messageNumber + localAddition);
			viewChecker.setExpectedCreatedElements(messageNumber + localAddition + globalAddition);
			expectation.accept(helper, viewChecker);

			return new SemanticAndGraphicalCreationChecker(modelChecker, viewChecker);
		}).toList();

		// Apply tool
		createEdge(CreationToolsIds.getCreationToolId(toolId), verifications);
	}


	/**
	 * Tests Synchronous call message creation.
	 */
	@Test
	@ActiveDiagram(ACTIVE_DIAGRAM)
	public void createSyncMessage() {
		createMessage("MessageSync", //$NON-NLS-1$
				List.of(EdgeAssertion.TO_EXECUTION, /* call */
						EdgeAssertion.INVERSE_FROM_EXECUTION/* reply */),
				2 /* messages */ * 2 /* sender, received */,
				1 /* execution */);
	}

	/**
	 * Tests Asynchronous message creation.
	 */
	@Test
	@ActiveDiagram(ACTIVE_DIAGRAM)
	public void createAsyncMessage() {
		createMessage("MessageAsync", //$NON-NLS-1$
				List.of(EdgeAssertion.DIRECT), 2 /* sender, received */, 0);
	}

	/**
	 * Tests Reply message creation.
	 */
	@Test
	@ActiveDiagram(ACTIVE_DIAGRAM)
	public void createReplyMessage() {
		createMessage("MessageReply", //$NON-NLS-1$
				List.of(EdgeAssertion.DIRECT), 2 /* sender, received */, 0);
	}

	/**
	 * Creates Use-cases for tests.
	 *
	 * @return {@link EdgeCreation}s
	 */
	@Parameters(name = "Create Message {0}")
	public static Collection<EdgeCreation> data() {
		// A Root level:
		String atRootHint = " (Root)"; //$NON-NLS-1$
		Stream<EdgeCreation> atRootLevel = Stream.of(
				new EdgeCreation(ViewKind.lifeline, 1, ViewKind.lifeline, 2, 125, atRootHint),
				new EdgeCreation(ViewKind.execution, 1, ViewKind.lifeline, 2, 189, atRootHint),
				new EdgeCreation(ViewKind.execution, 1, ViewKind.execution, 2, 254, atRootHint),
				new EdgeCreation(ViewKind.lifeline, 1, ViewKind.execution, 2, 340, atRootHint));

		// In Combined Fragment:
		String inOperandHint = " (Operand)"; //$NON-NLS-1$
		Stream<EdgeCreation> inOperand = Stream.of(
				new EdgeCreation(ViewKind.lifeline, 1, ViewKind.lifeline, 2, 508, inOperandHint),
				new EdgeCreation(ViewKind.execution, 3, ViewKind.lifeline, 2, 567, inOperandHint),
				new EdgeCreation(ViewKind.execution, 3, ViewKind.execution, 4, 624, inOperandHint),
				new EdgeCreation(ViewKind.lifeline, 1, ViewKind.execution, 4, 686, inOperandHint));

		return Stream.concat(atRootLevel, inOperand).toList();
	}

	/**
	 * Use-case for test suite.
	 * <p>
	 * Provides the parameters for a test instance.
	 * </p>
	 *
	 * @param source
	 *            kind of view for the source
	 * @param sourceId
	 *            id of the source element
	 * @param target
	 *            kind of view for the target
	 * @param targetId
	 *            id of the target element
	 * @param y
	 *            vertical position to create the edge
	 * @param hint
	 *            indication for test label
	 */
	record EdgeCreation(ViewKind source, int sourceId, ViewKind target, int targetId, int y, String hint) {

		/**
		 * Configures the context of the test.
		 *
		 * @param context
		 *            test to configure
		 */
		private void configureContext(SDCreateMessagesTest context) {
			context.setEdgeSource(source.getView(context.helper, sourceId));
			context.setEdgeTarget(target.getView(context.helper, targetId));
		}

		@Override
		public final String toString() {
			// Used in test label.
			return source.name() + sourceId
					+ " - " + target.name() + targetId //$NON-NLS-1$
					+ hint;
		}
	}

	/**
	 * List of expectations for edge ends.
	 */
	interface EdgeAssertion extends BiConsumer<SequenceTestHelper, DEdgeCreationChecker> {

		/** Verifying the edge end match the target in test. */
		Consumer<EdgeTarget> IS_EXECUTION = end -> Assert.assertTrue(
				"End is not an ExecutionSpecification", //$NON-NLS-1$
				((DSemanticDecorator) end).getTarget() instanceof ExecutionSpecification);

		/** Expecting source and target match context ends. */
		EdgeAssertion DIRECT = (context, checker) -> {
			checker.setSourceVerification(context.verifyIsSource());
			checker.setTargetVerification(context.verifyIsTarget());
		};

		/** Expecting source matches test source and target is an {@link ExecutionSpecification}. */
		EdgeAssertion TO_EXECUTION = (context, checker) -> {
			checker.setSourceVerification(context.verifyIsSource());
			checker.setTargetVerification(// edge target is a child of test target
					context.onContainer(context.verifyIsTarget()) // and an ExecutionSpecification
							.andThen(IS_EXECUTION));
		};

		/** Expecting source is an {@link ExecutionSpecification} and target matches test source. */
		EdgeAssertion INVERSE_FROM_EXECUTION = (context, checker) -> {
			checker.setSourceVerification(// edge source is a child of test target
					context.onContainer(context.verifyIsTarget()) // and an ExecutionSpecification
							.andThen(IS_EXECUTION));
			checker.setTargetVerification(context.verifyIsSource());
		};

	}

}
