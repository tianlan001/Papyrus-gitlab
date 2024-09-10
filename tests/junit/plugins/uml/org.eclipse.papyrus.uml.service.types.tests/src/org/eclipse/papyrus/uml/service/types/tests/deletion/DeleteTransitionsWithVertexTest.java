/*****************************************************************************
 * Copyright (c) 2017 EclipseSource Services GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Martin Fleck (EclipseSource) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.tests.deletion;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ServiceRegistryModelSetFixture;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Vertex;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * Tests the {@link VertexEditHelperAdvice} that deletes all incoming and outgoing transitions when a vertex is deleted.
 * The used test model consists of a statemachine with four states and four transitions:
 * 
 * <pre>
 *              +--------------+
 *              |              v
 * [Initial]-->[S1]-->[S2]-->[Final]
 * </pre>
 * 
 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=510268
 * @author Martin Fleck <mfleck@eclipsesource.com>
 *
 */
@PluginResource("resource/bug510268/StateMachine.di")
public class DeleteTransitionsWithVertexTest {

	/** HouseKeeper to clean up resources after a test has finished. */
	@Rule
	public HouseKeeper houseKeeper = new HouseKeeper();

	/** ModelSetFixture with initialized service registry. */
	@Rule
	public ModelSetFixture modelSet = new ServiceRegistryModelSetFixture();

	/** Region containing the states and transitions. */
	private StateMachine stateMachine;

	/** Region containing the states and transitions. */
	private Region region;

	/** Initial State. */
	private Vertex initialState;

	/** State 1. */
	private Vertex s1;

	/** State 2. */
	private Vertex s2;

	/** Final State. */
	private Vertex finalState;

	/** Transition from Initial State to State 1. */
	private Transition initialToS1;

	/** Transition from State 1 to State 2. */
	private Transition s1ToS2;

	/** Transition from State 1 to Final State. */
	private Transition s1ToFinal;

	/** Transition from State 2 to Final State. */
	private Transition s2ToFinal;

	/**
	 * Default Constructor.
	 */
	public DeleteTransitionsWithVertexTest() {
	}

	/**
	 * Asserts the structure of the model fixture with four states and four transitions.
	 */
	@Before
	public void assertModelFixture() {
		stateMachine = (StateMachine) modelSet.getModel().getOwnedMember("StateMachine");
		assertThat("StateMachine does not exist!", stateMachine, is(notNullValue()));

		region = stateMachine.getRegion("Region");
		assertThat("Region does not exist!", region, is(notNullValue()));

		initialState = region.getSubvertex("InitialState");
		assertThat("Initial State does not exist!", initialState, is(notNullValue()));

		s1 = region.getSubvertex("S1");
		assertThat("State 1 does not exist!", s1, is(notNullValue()));

		s2 = region.getSubvertex("S2");
		assertThat("State 2 does not exist!", s2, is(notNullValue()));

		finalState = region.getSubvertex("FinalState");
		assertThat("Final State does not exist!", finalState, is(notNullValue()));

		initialToS1 = region.getTransition("InitialToS1");
		assertThat("Transition from Initial State to State 1 does not exist!", initialToS1, is(notNullValue()));
		assertThat("Transition from Initial State to State 1 not connected correctly (source).", initialToS1.getSource(), is(initialState));
		assertThat("Transition from Initial State to State 1 not connected correctly (target).", initialToS1.getTarget(), is(s1));

		s1ToS2 = region.getTransition("S1ToS2");
		assertThat("Transition from State 1 to State 2 does not exist!", s1ToS2, is(notNullValue()));
		assertThat("Transition from State 1 to State 2 not connected correctly (source).", s1ToS2.getSource(), is(s1));
		assertThat("Transition from State 1 to State 2 not connected correctly (target).", s1ToS2.getTarget(), is(s2));

		s1ToFinal = region.getTransition("S1ToFinal");
		assertThat("Transition from State 1 to Final State does not exist!", s1ToFinal, is(notNullValue()));
		assertThat("Transition from State 1 to Final State not connected correctly (source).", s1ToFinal.getSource(), is(s1));
		assertThat("Transition from State 1 to Final State not connected correctly (target).", s1ToFinal.getTarget(), is(finalState));

		s2ToFinal = region.getTransition("S2ToFinal");
		assertThat("Transition from State 2 to Final State does not exist!", s2ToFinal, is(notNullValue()));
		assertThat("Transition from State 2 to Final State not connected correctly (source).", s2ToFinal.getSource(), is(s2));
		assertThat("Transition from State 2 to Final State not connected correctly (target).", s2ToFinal.getTarget(), is(finalState));
	}

	/**
	 * Verify that all outgoing transitions from the initial state are deleted.
	 */
	@Test
	public void deleteInitialState() throws Exception {
		testDeleteVertex(initialState, initialToS1);
	}

	/**
	 * Verify that all incoming and outgoing transitions from state 1 are deleted.
	 */
	@Test
	public void deleteS1() throws Exception {
		testDeleteVertex(s1, initialToS1, s1ToS2, s1ToFinal);
	}

	/**
	 * Verify that all incoming and outgoing transitions from state 2 are deleted.
	 */
	@Test
	public void deleteS2() throws Exception {
		testDeleteVertex(s2, s1ToS2, s2ToFinal);
	}

	/**
	 * Verify that all incoming transitions from the final state are deleted.
	 */
	@Test
	public void deleteFinalState() throws Exception {
		testDeleteVertex(finalState, s1ToFinal, s2ToFinal);
	}

	/**
	 * Deletes the given vertex and asserts that the given transitions are automatically deleted as well.
	 * 
	 * @param vertexToDelete
	 *            vertex element to delete.
	 * @param deletedTransitions
	 *            transitions that should be deleted together with the vertex.
	 */
	protected void testDeleteVertex(Vertex vertexToDelete, Transition... deletedTransitions) {
		ICommand deleteCommand = getDeleteCommand(vertexToDelete);

		modelSet.execute(deleteCommand);
		assertVertexDeleted(vertexToDelete);
		assertTransitionsDeleted(Lists.newArrayList(deletedTransitions));

		modelSet.undo();
		assertModelFixture();

		modelSet.redo();
		assertVertexDeleted(vertexToDelete);
		assertTransitionsDeleted(Lists.newArrayList(deletedTransitions));
	}

	/**
	 * Asserts that all given transitions are deleted from the model and that all other transitions are unchanged.
	 * 
	 * @param deletedTransitions
	 *            transitions that should have been deleted from the model.
	 */
	protected void assertTransitionsDeleted(List<Transition> deletedTransitions) {
		List<Transition> allTransitions = Lists.newArrayList(initialToS1, s1ToS2, s1ToFinal, s2ToFinal);
		for (Transition transition : allTransitions) {
			if (deletedTransitions.contains(transition)) {
				assertTransitionDeleted(transition);
			} else {
				assertTransitionNotDeleted(transition);
			}
		}
	}

	/**
	 * Asserts that the given vertex was deleted from the model.
	 * 
	 * @param vertex
	 *            vertex that should have been deleted.
	 */
	protected void assertVertexDeleted(Vertex vertex) {
		assertThat("Deleted vertex still has incoming transitions.", vertex.getIncomings().isEmpty());
		assertThat("Deleted vertex still has outgoing transitions.", vertex.getOutgoings().isEmpty());
		assertThat("Deleted vertex still in the resource.", vertex.eResource(), is(nullValue()));
		assertThat("Deleted vertex still in region.", region.getSubvertices(), not(hasItem(vertex)));
	}

	/**
	 * Asserts that the given transition was deleted from the model.
	 * 
	 * @param transition
	 *            transition that should have been deleted.
	 */
	protected void assertTransitionDeleted(Transition transition) {
		assertThat("Deleted transition still connected to its source.", transition.getSource(), is(nullValue()));
		assertThat("Deleted transition still connected to its target.", transition.getTarget(), is(nullValue()));
		assertThat("Deleted transition still in the resource.", transition.eResource(), is(nullValue()));
		assertThat("Deleted transition still in region.", region.getTransitions(), not(hasItem(transition)));
	}

	/**
	 * Asserts that the given transition was NOT deleted from the model.
	 * 
	 * @param transition
	 *            transition that should be left unchanged.
	 */
	protected void assertTransitionNotDeleted(Transition transition) {
		assertThat("Not deleted transition lost its source.", transition.getSource(), is(notNullValue()));
		assertThat("Not deleted transition lost its target.", transition.getTarget(), is(notNullValue()));
		assertThat("Not deleted transition lost its container.", transition.eContainer(), is((EObject) region));
		assertThat("Not deleted transition not in region.", region.getTransitions(), hasItem(transition));
	}

	/**
	 * Creates a command to delete the given element.
	 * 
	 * @param elementToDelete
	 *            element that should be deleted.
	 * @return deletion command.
	 */
	protected ICommand getDeleteCommand(EObject elementToDelete) {
		IElementEditService elementEditService = ElementEditServiceUtils.getCommandProvider(elementToDelete);
		assertThat("No ElementEditService found for element: " + elementToDelete, elementEditService, notNullValue());

		ICommand command = elementEditService.getEditCommand(new DestroyElementRequest(elementToDelete, false));
		assertThat("No deletion command provided", command, notNullValue());
		assertThat("Deletion command is not executable", command.canExecute(), is(true));

		return command;
	}
}