/*****************************************************************************
 * Copyright (c) 2015, 2016 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.gmfdiag.welcome.tests;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeThat;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.ListDiffEntry;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.papyrus.infra.editor.welcome.tests.AbstractWelcomePageTest;
import org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram;
import org.eclipse.papyrus.infra.gmfdiag.welcome.internal.modelelements.NotationObservable;
import org.eclipse.papyrus.infra.gmfdiag.welcome.internal.modelelements.WelcomeModelElement;
import org.eclipse.papyrus.infra.gmfdiag.welcome.internal.modelelements.WelcomeModelElementFactory;
import org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.NamedElement;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the {@link WelcomeModelElement} and its properties.
 */
@PluginResource(value = "resources/many_diagrams.di", bundle = "org.eclipse.papyrus.infra.editor.welcome.tests")
public class WelcomeModelElementTest extends AbstractWelcomePageTest {

	private WelcomeModelElement fixture;

	public WelcomeModelElementTest() {
		super();
	}

	@Test
	public void viewsProperty() {
		IObservableList<NotationObservable> views = getNotationViews();

		assertThat(views.size(), is(6));
		assertThat(views, hasItem(anything()));

		List<String> diagramNames = views.stream()
				.map(NotationObservable::getView)
				.map(IObservableValue::getValue)
				.map(this::getName)
				.collect(Collectors.toList());
		assertThat(diagramNames, both(hasItem("classes")).and(hasItem("packages"))
				.and(hasItem("use cases")).and(hasItem("components"))
				.and(hasItem("table")).and(hasItem("tree table")));

		List<String> contextNames = views.stream()
				.map(NotationObservable::getContext)
				.map(IObservableValue::getValue)
				.map(NamedElement.class::cast)
				.map(NamedElement::getName)
				.collect(Collectors.toList());
		assertThat(contextNames, is(Collections.nCopies(6, "model")));
	}

	@Test
	public void deleteView() {
		IObservableList<NotationObservable> views = getNotationViews();

		NotationObservable toBeDeleted = getView(views, "use cases");

		boolean[] deleted = { false };
		views.addListChangeListener(event -> {
			for (ListDiffEntry<? extends NotationObservable> next : event.diff.getDifferences()) {
				if (!next.isAddition() && (next.getElement() == toBeDeleted)) {
					deleted[0] = true;
				}
			}
		});

		editor.execute(new RecordingCommand(editor.getEditingDomain(), "Delete Diagram") {

			@Override
			protected void doExecute() {
				EcoreUtil.delete(toBeDeleted.getView().getValue(), true);
			}
		});

		assertThat("List did not notify", deleted[0], is(true));
		assertThat(views.size(), is(5));
		assertThat(views, not(hasItem(toBeDeleted)));
	}

	@Test
	public void createView() {
		IObservableList<NotationObservable> views = getNotationViews();

		NotationObservable[] created = { null };

		views.addListChangeListener(event -> {
			for (ListDiffEntry<? extends NotationObservable> next : event.diff.getDifferences()) {
				if (next.isAddition() && "CreatedInTest".equals(getName(next.getElement().getView().getValue()))) {
					created[0] = next.getElement();
				}
			}
		});

		createSomeDiagram();

		assertThat("List did not notify", created[0], notNullValue());
		assertThat(views.size(), is(7));
		assertThat(views, hasItem(created[0]));
	}

	@Test
	public void isEditable() {
		assertThat(fixture.isEditable("views"), is(true));
		assertThat(fixture.isEditable("garbage"), is(false));
	}

	/**
	 * Verifies correct and complete disposal of observables when they are no longer needed.
	 */
	@Test
	public void dispose_bug487027() {
		IObservableList<NotationObservable> views = getNotationViews();

		NotationObservable[] obs = { null };

		views.addListChangeListener(event -> {
			for (ListDiffEntry<? extends NotationObservable> next : event.diff.getDifferences()) {
				if (next.isAddition() && "CreatedInTest".equals(getName(next.getElement().getView().getValue()))) {
					obs[0] = next.getElement();
				}
			}
		});

		createSomeDiagram();

		assumeThat("Didn't get the created notation observable", obs[0], notNullValue());

		// Dispose the model-element
		fixture.dispose();

		// Let the auto-release pool clean up
		editor.flushDisplayEvents();

		assertThat("Notation observable not disposed", obs[0].isDisposed(), is(true));
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() {
		fixture = (WelcomeModelElement) new WelcomeModelElementFactory().createFromSource(
				requireWelcome(), null); // The data-context isn't used by the factory
	}

	IObservableList<NotationObservable> getNotationViews() {
		@SuppressWarnings("unchecked")
		IObservableList<NotationObservable> result = (IObservableList<NotationObservable>) fixture.getObservable("views");
		return result;
	}

	NotationObservable getView(Collection<? extends NotationObservable> views, String name) {
		return views.stream()
				.filter(d -> name.equals(getName(d.getView().getValue())))
				.findFirst().get();
	}

	// Diagrams and tables have a 'name' attribute
	String getName(EObject object) {
		EStructuralFeature nameAttribute = object.eClass().getEStructuralFeature("name");
		return (String) object.eGet(nameAttribute);
	}

	void createSomeDiagram() {
		editor.execute(new RecordingCommand(editor.getEditingDomain(), "Create Diagram") {

			@Override
			protected void doExecute() {
				// Be resilient against misconfigured diagrams: look for the class diagram, specifically
				Collection<ViewPrototype> prototypes = PolicyChecker.getFor(editor.getModelSet()).getPrototypesFor(editor.getModel());
				ViewPrototype prototype = prototypes.stream()
						.filter(proto -> proto.getRepresentationKind() instanceof PapyrusDiagram)
						.filter(proto -> proto.getRepresentationKind().getName().equals("Class Diagram"))
						.findAny().get();
				prototype.instantiateOn(editor.getModel(), "CreatedInTest");
			}
		});
	}
}
