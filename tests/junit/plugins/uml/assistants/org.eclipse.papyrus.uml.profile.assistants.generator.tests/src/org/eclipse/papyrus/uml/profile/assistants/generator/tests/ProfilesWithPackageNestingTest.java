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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

/**
 * Test cases for diagram assistants generation for UML profiles that have nested packages and profiles.
 */
@PluginResource("/resources/nesting.profile.uml")
public class ProfilesWithPackageNestingTest extends AbstractPapyrusTest {

	@ClassRule
	public static final ModelGenFixture fixture = new ModelGenFixture();

	public ProfilesWithPackageNestingTest() {
		super();
	}

	@Test
	public void popupsGenerated() {
		Pair<Stereotype, Class> s11Class = fixture.getMetaclassExtension("S1_1", "Class");
		fixture.assertAllPopupAssistants(s11Class);
	}

	@Test
	public void connectionsGenerated() {
		Pair<Stereotype, Class> s12Generalization = fixture.getMetaclassExtension("S1_2", "Generalization");
		fixture.assertAllConnectionAssistants(s12Generalization);
	}


	@SuppressWarnings("unchecked")
	@Test
	public void popupFilters() {
		Pair<Stereotype, Class> s11Class = fixture.getMetaclassExtension("S1_1", "Class");
		List<PopupAssistant> popups = fixture.assertAllPopupAssistants(s11Class);

		ElementTypeFilter filterClass = fixture.assertMetaclassFilter(s11Class, null);
		Pair<Stereotype, Class> s21Class = fixture.getMetaclassExtension("S2_1", "Class");
		ElementTypeFilter filterClass2 = fixture.assertMetaclassFilter(s21Class, null);

		assertThat(transform(popups, getFeature(AssistantPackage.Literals.POPUP_ASSISTANT__FILTER, Filter.class)), //
				hasItems(includes(filterClass), includes(filterClass2)));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void connectionSourceFilters() {
		Pair<Stereotype, Class> s12Generalization = fixture.getMetaclassExtension("S1_2", "Generalization");
		List<ConnectionAssistant> connections = fixture.assertAllConnectionAssistants(s12Generalization);

		Pair<Stereotype, Class> s11Class = fixture.getMetaclassExtension("S1_1", "Class");
		ElementTypeFilter filterClass = fixture.assertMetaclassFilter(s11Class, null);
		Pair<Stereotype, Class> s21Class = fixture.getMetaclassExtension("S2_1", "Class");
		ElementTypeFilter filterClass2 = fixture.assertMetaclassFilter(s21Class, null);

		assertThat(transform(connections, getFeature(AssistantPackage.Literals.CONNECTION_ASSISTANT__SOURCE_FILTER, Filter.class)), //
				hasItems(includes(filterClass), includes(filterClass2)));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void connectionTargetFilters() {
		Pair<Stereotype, Class> s12Generalization = fixture.getMetaclassExtension("S1_2", "Generalization");
		List<ConnectionAssistant> connections = fixture.assertAllConnectionAssistants(s12Generalization);

		Pair<Stereotype, Class> s11Class = fixture.getMetaclassExtension("S1_1", "Class");
		ElementTypeFilter filterClass = fixture.assertMetaclassFilter(s11Class, null);
		Pair<Stereotype, Class> s21Class = fixture.getMetaclassExtension("S2_1", "Class");
		ElementTypeFilter filterClass2 = fixture.assertMetaclassFilter(s21Class, null);

		assertThat(transform(connections, getFeature(AssistantPackage.Literals.CONNECTION_ASSISTANT__TARGET_FILTER, Filter.class)), //
				hasItems(includes(filterClass), includes(filterClass2)));
	}

	//
	// Test framework
	//

	static Function<String, String> suffixFunction() {
		return new Function<String, String>() {
			final Pattern suffix = Pattern.compile("[._]([0-9a-zA-Z]+)$");

			@Override
			public String apply(String input) {
				Matcher m = suffix.matcher(input);
				return m.find() ? m.group(1) : null;
			}
		};
	}

	static Function<EObject, Integer> visualIDFunction() {
		Function<String, Integer> parse = new Function<String, Integer>() {
			@Override
			public Integer apply(String input) {
				return Integer.valueOf(input);
			}
		};
		return Functions.compose(parse, Functions.compose(suffixFunction(), getFeature(AssistantPackage.Literals.ASSISTANT__ELEMENT_TYPE_ID, String.class)));
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
