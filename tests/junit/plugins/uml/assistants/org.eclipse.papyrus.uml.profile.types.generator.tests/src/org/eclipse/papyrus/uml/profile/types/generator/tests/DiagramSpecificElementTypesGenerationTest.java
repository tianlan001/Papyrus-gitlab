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

package org.eclipse.papyrus.uml.profile.types.generator.tests;

import static com.google.common.collect.Iterables.transform;
import static org.eclipse.papyrus.junit.matchers.MoreMatchers.isEmpty;
import static org.eclipse.papyrus.junit.matchers.MoreMatchers.regexContains;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;
import org.eclipse.papyrus.infra.types.IconEntry;
import org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorAsRectangleEditPartTN;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.ActorEditPartTN;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.edit.UMLEditPlugin;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.ClassRule;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;

/**
 * Test cases for diagram-specific element types generation for UML profiles.
 */
@PluginResource("/resources/j2ee.profile.uml")
@BaseElementTypes("org.eclipse.papyrus.umldi.service.types.UMLDIElementTypeSet")
public class DiagramSpecificElementTypesGenerationTest extends AbstractPapyrusTest {

	@ClassRule
	public static final ModelGenFixture fixture = new ModelGenFixture();

	public DiagramSpecificElementTypesGenerationTest() {
		super();
	}

	@Test
	public void elementTypesGenerated() {
		Pair<Stereotype, Class> userActor = fixture.getMetaclassExtension("User", "Actor");
		fixture.assertAllSpecializationTypes(userActor);
		assertThat(fixture.getElementTypeSet().getMetamodelNsURI(), is(UMLPackage.eNS_URI));
	}

	/**
	 * Verifies that diagram-specific element types are generated with both a semantic parent and a visual parent.
	 *
	 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=461717
	 */
	@Test
	public void elementTypeSpecializedTypes_bug461717() {
		Pair<Stereotype, Class> userActor = fixture.getMetaclassExtension("User", "Actor");
		List<SpecializationTypeConfiguration> specializationTypes = fixture.assertAllSpecializationTypes(userActor);
		assertThat("No specialization types generated", specializationTypes, not(isEmpty()));

		String semanticParentID = fixture.prefix + ".User";
		String idPrefix = semanticParentID + "_";

		for (SpecializationTypeConfiguration next : specializationTypes) {
			if (!next.getIdentifier().equals(semanticParentID)) {
				assertThat(next.getIdentifier(), startsWith(idPrefix));

				List<ElementTypeConfiguration> specializedType = next.getSpecializedTypes();
				assertThat(specializedType.size(), is(2));
				assertThat(specializedType.get(0).getIdentifier(), is(semanticParentID));
				assertThat(specializedType.get(1).getIdentifier(), regexContains("Actor_")); // a visual ID
			}
		}
	}

	@Test
	public void distinctHintsGenerated() {
		Pair<Stereotype, Class> userActor = fixture.getMetaclassExtension("User", "Actor");
		List<SpecializationTypeConfiguration> types = fixture.assertAllSpecializationTypes(userActor);
		Set<String> hints = ImmutableSet.copyOf(transform(types, getFeature(ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_CONFIGURATION__HINT, String.class)));
		assertThat(hints, hasItems(ActorEditPartTN.VISUAL_ID, ActorAsRectangleEditPartTN.VISUAL_ID));
	}

	@Test
	public void iconGenerated() {
		Pair<Stereotype, Class> userActor = fixture.getMetaclassExtension("User", "Actor");
		List<SpecializationTypeConfiguration> types = fixture.assertAllSpecializationTypes(userActor);
		for (SpecializationTypeConfiguration type : types) {
			IconEntry icon = type.getIconEntry();
			assertThat(icon.getBundleId(), is(UMLEditPlugin.getPlugin().getSymbolicName()));
			assertThat(icon.getIconPath(), containsString("obj16/Actor.gif"));
		}
	}

	@Test
	public void stereotypeMatcherNotGenerated() {
		Pair<Stereotype, Class> userActor = fixture.getMetaclassExtension("User", "Actor");
		List<SpecializationTypeConfiguration> types = fixture.assertAllSpecializationTypes(userActor);
		for (SpecializationTypeConfiguration type : types) {
			// Stereotype matchers are not required where they are inherited from semantic supertype
			fixture.assertNoStereotypeMatcher(type);
		}
	}

	@Test
	public void stereotypeAdviceNotGenerated() {
		Pair<Stereotype, Class> userActor = fixture.getMetaclassExtension("User", "Actor");
		// Apply-stereotype advice is not required where it is inherited from semantic supertype
		fixture.assertNoApplyStereotypeAdvice(userActor);
	}
	
	public static <T> Function<EObject, T> getFeature(final EStructuralFeature feature, final java.lang.Class<T> ofType) {
		return new Function<EObject, T>() {
			@Override
			public T apply(EObject input) {
				return (input == null) ? null : ofType.cast(input.eGet(feature));
			}
		};
	}	
}
