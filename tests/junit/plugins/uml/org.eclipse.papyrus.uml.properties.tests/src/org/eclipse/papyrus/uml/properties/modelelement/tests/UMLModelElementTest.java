/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
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

package org.eclipse.papyrus.uml.properties.modelelement.tests;

import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toSet;
import static org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain.getEditingDomainFor;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.papyrus.infra.ui.emf.providers.strategy.ContainmentBrowseStrategy;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.infra.widgets.strategy.ProviderBasedBrowseStrategy;
import org.eclipse.papyrus.infra.widgets.strategy.StrategyBasedContentProvider;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.JavaResource;
import org.eclipse.papyrus.junit.utils.rules.ResourceSetFixture;
import org.eclipse.papyrus.uml.properties.modelelement.UMLModelElement;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.Lifeline;
import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for the {@link UMLModelElement} class.
 */
public class UMLModelElementTest extends AbstractPapyrusTest {

	@Rule
	public final ResourceSetFixture rset = new ResourceSetFixture();

	/**
	 * Initializes me.
	 */
	public UMLModelElementTest() {
		super();
	}

	/**
	 * Verify that the content provider supplied for browsing potential covered lifelines
	 * of combined fragments includes only and exactly lifelines from the contextual
	 * interaction.
	 */
	@Test
	@JavaResource("bug507479.uml")
	public void combinedFragment_contentProvider() {
		Interaction interaction = getInteraction("Foo");
		CombinedFragment cfrag = (CombinedFragment) interaction.getFragment("cfrag");

		ITreeContentProvider content = getContentProvider(getElement(cfrag), "covered");

		// The other class and its lifelines are not provided
		Collection<Lifeline> lifelines = getProvidedContent(content, Lifeline.class);
		Set<Interaction> fromInteractions = lifelines.stream().map(Lifeline::getInteraction).collect(toSet());
		assertThat("Lifelines from wrong interaction", fromInteractions, is(singleton(interaction)));
	}

	/**
	 * Verify that the content provider supplied for browsing potential covered lifelines
	 * of interaction operands includes only and exactly lifelines from the contextual
	 * interaction.
	 */
	@Test
	@JavaResource("bug507479.uml")
	public void interactionOperand_contentProvider() {
		Interaction interaction = getInteraction("Foo");
		CombinedFragment cfrag = (CombinedFragment) interaction.getFragment("cfrag");
		InteractionOperand operand = cfrag.getOperand("opt");

		ITreeContentProvider content = getContentProvider(getElement(operand), "covered");

		// The other class and its lifelines are not provided
		Collection<Lifeline> lifelines = getProvidedContent(content, Lifeline.class);
		Set<Interaction> fromInteractions = lifelines.stream().map(Lifeline::getInteraction).collect(toSet());
		assertThat("Lifelines from wrong interaction", fromInteractions, is(singleton(interaction)));
	}

	//
	// Test framework
	//

	private Class getClass(String name) {
		return (Class) rset.getModel().getOwnedType(name);
	}

	private Interaction getInteraction(String className) {
		return (Interaction) getClass(className).getClassifierBehavior();
	}

	ModelElement getElement(EObject object) {
		return new UMLModelElement(object, getEditingDomainFor(object));
	}

	ITreeContentProvider getContentProvider(ModelElement element, String propertyPath) {
		IStaticContentProvider content = element.getContentProvider(propertyPath);
		assertThat("Not a tree content provider", content, instanceOf(ITreeContentProvider.class));

		ITreeContentProvider treeContent = (ITreeContentProvider) content;

		// This uses isValidValue as in the Reference Editor Dialog to determine what
		// to show and what may be selected
		return new StrategyBasedContentProvider(new ProviderBasedBrowseStrategy(treeContent),
				new ContainmentBrowseStrategy(treeContent));
	}

	<T> Collection<T> getProvidedContent(ITreeContentProvider content, java.lang.Class<T> type) {
		Collection<T> result = new LinkedHashSet<>();

		IStaticContentProvider staticContent = (IStaticContentProvider) content;
		collectProvidedContent(content, staticContent.getElements(), type, 0, result);

		return result;
	}

	private <T> void collectProvidedContent(ITreeContentProvider content, Object[] elements, java.lang.Class<T> type, int depth, Collection<? super T> result) {
		if (depth < 100) { // Some content providers are cyclic
			Stream.of(elements)
					.peek(child -> collectProvidedContent(content, child, type, depth + 1, result))
					.map(EMFHelper::getEObject)
					.filter(type::isInstance).map(type::cast)
					.forEach(result::add);
		}
	}

	private <T> void collectProvidedContent(ITreeContentProvider content, Object parent, java.lang.Class<T> type, int depth, Collection<? super T> result) {
		collectProvidedContent(content, content.getChildren(parent), type, depth, result);
	}
}
