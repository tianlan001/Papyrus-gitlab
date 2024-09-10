/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.viewpoints.internal.policy.advice.tests;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.lessThan;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Iterator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.viewpoints.internal.policy.advice.RepresentationDependencyAdvice;
import org.eclipse.papyrus.infra.viewpoints.policy.NotationUtils;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ServiceRegistryModelSetFixture;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test cases for the {@link RepresentationDependencyAdviceTest} class.
 */
@PluginResource("/resources/owned-views.di")
public class RepresentationDependencyAdviceTest {

	private static final String STATE_MACHINE = "model::Class1::StateMachine1"; //$NON-NLS-1$
	private static final String DIAGRAMS_PACKAGE = "model::diagrams"; //$NON-NLS-1$

	@Rule
	public final ModelSetFixture modelSet = new ServiceRegistryModelSetFixture();

	public RepresentationDependencyAdviceTest() {
		super();
	}

	/**
	 * Test that the advice deletes a diagram when the context of the diagram is being deleted
	 * and that context is also the logical owner.
	 */
	@Test
	public void destroyOwningContextOfView() {
		NamedElement stateMachine = getElement(STATE_MACHINE);
		EObject diagram = getDiagram();

		DestroyDependentsRequest request = destroyDependents(stateMachine);
		process(request);

		assertThat("Owned diagram not deleted by the advice.", diagram.eResource(), nullValue());
	}

	/**
	 * Test that the advice deletes a diagram when the context of the diagram is being deleted
	 * and some other element is the logical owner.
	 */
	@Test
	@PluginResource("/resources/unowned-views.di")
	public void destroyNonOwningContextOfView() {
		NamedElement stateMachine = getElement(STATE_MACHINE);
		EObject diagram = getDiagram();

		DestroyDependentsRequest request = destroyDependents(stateMachine);
		process(request);

		assertThat("Unowned diagram not deleted by the advice.", diagram.eResource(), nullValue());
	}

	/**
	 * Test that the advice does not delete a diagram when the logical owner of the diagram is
	 * being deleted but not its context element. In this model, the state machine diagram is
	 * logically owned by a "diagrams" package.
	 */
	@Test
	@PluginResource("/resources/unowned-views2.di")
	public void destroyOwnerOfViewOnly() {
		NamedElement logicalOwner = getElement(DIAGRAMS_PACKAGE);
		EObject diagram = getDiagram();

		DestroyDependentsRequest request = destroyDependents(logicalOwner);
		process(request);

		assertThat("Unowned diagram was deleted by the advice.", diagram.eResource(), notNullValue());
	}

	//
	// Test framework
	//

	NamedElement getElement(String qualifiedName) {
		return UMLUtil.findNamedElements(modelSet.getModelResource(), qualifiedName).iterator().next();
	}

	EObject getDiagram() {
		return getDiagram(0);
	}

	EObject getDiagram(int index) {
		Iterator<EObject> notations = NotationUtils.getNotationRoots(modelSet.getRoot());
		for (int i = 0; i < index; i++) {
			notations.next();
		}
		return notations.next();
	}

	DestroyDependentsRequest destroyDependents(EObject object) {
		return new DestroyDependentsRequest(modelSet.getEditingDomain(), object, false);
	}

	void process(IEditCommandRequest request) {
		IEditHelperAdvice advice = new RepresentationDependencyAdvice();
		ICommand before = advice.getBeforeEditCommand(request);
		ICommand after = advice.getAfterEditCommand(request);

		ICommand composed = CompositeCommand.compose(before, after);

		if (composed != null && composed.canExecute()) {
			IStatus status = modelSet.execute(composed);
			assertThat("Command execution failed.", status.getSeverity(), lessThan(IStatus.ERROR));
		}
	}

}
