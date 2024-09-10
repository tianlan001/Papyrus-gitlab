/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.viewpoints.policy.tests;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.greaterThan;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ServiceRegistryModelSetFixture;
import org.eclipse.uml2.uml.BehavioredClassifier;
import org.eclipse.uml2.uml.StateMachine;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test cases for the {@link ViewPrototype} class.
 */
@PluginResource("/resources/owned-views.di")
public class ViewPrototypeTest {
	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	@Rule
	public final ModelSetFixture modelSet = new ServiceRegistryModelSetFixture();

	private static PolicyChecker policyToRestore;

	private StateMachine class1_stateMachine1;
	private StateMachine class2_stateMachine1;

	/**
	 * Verifies accurate counting of the views of the prototype owned by an element.
	 */
	@Test
	public void getOwnedViewCount() {
		assertThat(count(class1_stateMachine1, ViewPrototype::getOwnedViewCount), is(1));
		assertThat(count(class2_stateMachine1, ViewPrototype::getOwnedViewCount), is(0));
	}

	/**
	 * Verifies accurate counting of the views of the prototype whose context is an element.
	 * Uses unowned views in the test model to be sure to isolate the "view on" logic.
	 */
	@PluginResource("/resources/unowned-views.di")
	@Test
	public void getViewCountOn() {
		assertThat(count(class1_stateMachine1, ViewPrototype::getViewCountOn), is(1));
		assertThat(count(class2_stateMachine1, ViewPrototype::getViewCountOn), is(0));
	}

	//
	// Test framework
	//

	@Before
	public void getModelElements() {
		class1_stateMachine1 = (StateMachine) ((BehavioredClassifier) modelSet.getModel().getOwnedType("Class1")).getClassifierBehavior();
		class2_stateMachine1 = (StateMachine) ((BehavioredClassifier) modelSet.getModel().getOwnedType("Class2")).getClassifierBehavior();
	}

	List<ViewPrototype> getApplicableViewPrototypes(EObject object) {
		Collection<ViewPrototype> result = PolicyChecker.getFor(object).getPrototypesFor(object);

		// Only one diagram, so there must be only one prototype
		assertThat(result.size(), greaterThan(0));

		return new ArrayList<>(result);
	}

	/**
	 * Counts the views associated with an {@code object}, of all applicable prototypes,
	 * according to the given function.
	 * 
	 * @param object
	 *            an object in the model
	 * @param countFunction
	 *            the view-prototype view-counting function
	 * 
	 * @return the total number of views of all applicable views associated with the
	 *         {@code object}, according to the counting function
	 */
	int count(EObject object, ToIntBiFunction<? super ViewPrototype, ? super EObject> countFunction) {
		ToIntFunction<? super ViewPrototype> curriedCounter = proto -> countFunction.applyAsInt(proto, object);
		return getApplicableViewPrototypes(object).stream()
				.mapToInt(curriedCounter)
				.sum();
	}
}
