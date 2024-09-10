/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

package org.eclipse.papyrus.uml.profile.assistants.generator.tests;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.transform;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.infra.filters.CompoundFilter;
import org.eclipse.papyrus.infra.filters.Filter;
import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ConnectionAssistant;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter;
import org.eclipse.papyrus.infra.gmfdiag.assistant.PopupAssistant;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.AssociationEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ExtensionPointEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ExtensionPointInRectangleEditPart;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseAsRectangleEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.UseCaseEditPartTN;
import org.eclipse.papyrus.uml.profile.types.generator.tests.BaseElementTypes;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.xtext.xbase.lib.Pair;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.ClassRule;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

/**
 * Test cases for diagram-specific diagram assistants generation for UML profiles.
 */
@PluginResource("/resources/j2ee.profile.uml")
@BaseElementTypes("org.eclipse.papyrus.umldi.service.types.UMLDIElementTypeSet")
public class DiagramSpecificAssistantsGenerationTest extends AbstractPapyrusTest {

	@ClassRule
	public static final ModelGenFixture fixture = new ModelGenFixture();

	public DiagramSpecificAssistantsGenerationTest() {
		super();
	}

	@Test
	public void popupsGenerated() {
		Pair<Stereotype, Class> branchPoint = fixture.getMetaclassExtension("BranchPoint", "ExtensionPoint");
		fixture.assertAllPopupAssistants(branchPoint);
	}

	@Test
	public void connectionsGenerated() {
		Pair<Stereotype, Class> webAssociation = fixture.getMetaclassExtension("Web", "Association");
		fixture.assertAllConnectionAssistants(webAssociation);
	}

	@Test
	public void distinctPopupHintsGenerated() {
		Pair<Stereotype, Class> branchPoint = fixture.getMetaclassExtension("BranchPoint", "ExtensionPoint");
		List<PopupAssistant> popups = fixture.assertAllPopupAssistants(branchPoint);
		Set<String> hints = ImmutableSet.copyOf(transform(popups, visualIDFunction()));
		assertThat(hints, hasItems(ExtensionPointEditPart.VISUAL_ID, ExtensionPointInRectangleEditPart.VISUAL_ID));
	}

	@Test
	public void distinctConnectionHintsGenerated() {
		Pair<Stereotype, Class> webAssociation = fixture.getMetaclassExtension("Web", "Association");
		List<ConnectionAssistant> connections = fixture.assertAllConnectionAssistants(webAssociation);
		Set<String> hints = ImmutableSet.copyOf(transform(connections, visualIDFunction()));
		assertThat(hints, hasItems(AssociationEditPart.VISUAL_ID));
	}


	@SuppressWarnings("unchecked")
	@Test
	public void popupFilters() {
		Pair<Stereotype, Class> branchPoint = fixture.getMetaclassExtension("BranchPoint", "ExtensionPoint");
		List<PopupAssistant> popups = fixture.assertAllPopupAssistants(branchPoint);

		Pair<Stereotype, Class> webScenarioUseCase = fixture.getMetaclassExtension("WebScenario", "UseCase");
		ElementTypeFilter usecaseFilter = fixture.assertMetaclassFilter(webScenarioUseCase, UseCaseEditPartTN.VISUAL_ID);
		ElementTypeFilter usecaseAsRectangleFilter = fixture.assertMetaclassFilter(webScenarioUseCase, UseCaseAsRectangleEditPartTN.VISUAL_ID);

		assertThat(transform(popups, getFeature(AssistantPackage.Literals.POPUP_ASSISTANT__FILTER, Filter.class)), //
				hasItems(includes(usecaseFilter), includes(usecaseAsRectangleFilter)));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void connectionSourceFilters() {
		Pair<Stereotype, Class> webAssociation = fixture.getMetaclassExtension("Web", "Association");
		List<ConnectionAssistant> connections = fixture.assertAllConnectionAssistants(webAssociation);

		Pair<Stereotype, Class> userActor = fixture.getMetaclassExtension("User", "Actor");
		ElementTypeFilter actorFilter = fixture.assertMetaclassFilter(userActor, ActorEditPartTN.VISUAL_ID);
		Pair<Stereotype, Class> webScenarioUseCase = fixture.getMetaclassExtension("WebScenario", "UseCase");
		ElementTypeFilter usecaseFilter = fixture.assertMetaclassFilter(webScenarioUseCase, UseCaseEditPartTN.VISUAL_ID);

		assertThat(transform(connections, getFeature(AssistantPackage.Literals.CONNECTION_ASSISTANT__SOURCE_FILTER, Filter.class)), //
				hasItems(includes(actorFilter), includes(usecaseFilter)));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void connectionTargetFilters() {
		Pair<Stereotype, Class> webAssociation = fixture.getMetaclassExtension("Web", "Association");
		List<ConnectionAssistant> connections = fixture.assertAllConnectionAssistants(webAssociation);

		Pair<Stereotype, Class> userActor = fixture.getMetaclassExtension("User", "Actor");
		ElementTypeFilter actorFilter = fixture.assertMetaclassFilter(userActor, ActorEditPartTN.VISUAL_ID);
		Pair<Stereotype, Class> webScenarioUseCase = fixture.getMetaclassExtension("WebScenario", "UseCase");
		ElementTypeFilter usecaseFilter = fixture.assertMetaclassFilter(webScenarioUseCase, UseCaseEditPartTN.VISUAL_ID);

		assertThat(transform(connections, getFeature(AssistantPackage.Literals.CONNECTION_ASSISTANT__TARGET_FILTER, Filter.class)), //
				hasItems(includes(actorFilter), includes(usecaseFilter)));
	}

	//
	// Test framework
	//

	static Function<String, String> suffixFunction() {
		return new Function<String, String>() {
			@Override
			public String apply(String input) {
				return input.substring(input.indexOf('_')+1);
			}
		};
	}

	static Function<EObject, String> visualIDFunction() {
		return Functions.compose(suffixFunction(), getFeature(AssistantPackage.Literals.ASSISTANT__ELEMENT_TYPE_ID, String.class));
	}

	public static <T> Function<EObject, T> getFeature(final EStructuralFeature feature, final java.lang.Class<T> ofType) {
		return new Function<EObject, T>() {
			@Override
			public T apply(EObject input) {
				return (input == null) ? null : ofType.cast(input.eGet(feature));
			}
		};
	}
	
	static org.hamcrest.Matcher<Filter> includes(final Filter filter) {
		return new BaseMatcher<Filter>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("includes filter ").appendValue(filter.getName());
			}

			@Override
			public boolean matches(Object item) {
				boolean result = item == filter;
				if (!result && (item instanceof CompoundFilter)) {
					result = includes((CompoundFilter) item, filter);
				}
				return result;
			}

			private boolean includes(CompoundFilter compound, Filter filter) {
				Set<Filter> atoms = closureOfAtoms(compound, Sets.<Filter> newHashSet());
				return atoms.contains(filter);
			}

			private Set<Filter> closureOfAtoms(CompoundFilter compound, Set<Filter> result) {
				Iterables.addAll(result, filter(compound.getFilters(), Predicates.not(Predicates.instanceOf(CompoundFilter.class))));
				for (CompoundFilter next : filter(compound.getFilters(), CompoundFilter.class)) {
					closureOfAtoms(next, result);
				}
				return result;
			}
		};
	}
}
