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

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.isEmpty;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.papyrus.infra.types.IconEntry;
import org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherConfiguration;
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeMatcherAdviceConfiguration;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.edit.UMLEditPlugin;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * Test cases for element types generation for UML profiles that have nested packages and profiles.
 */
@PluginResource("/resources/nesting.profile.uml")
public class ProfilesWithPackageNestingTest extends AbstractPapyrusTest {

	@ClassRule
	public static final ModelGenFixture fixture = new ModelGenFixture();

	public ProfilesWithPackageNestingTest() {
		super();
	}

	@Test
	public void elementTypesGenerated() {
		Pair<Stereotype, Class> s11Class = fixture.getMetaclassExtension("S1_1", "Class");
		Pair<Stereotype, Class> s21Class = fixture.getMetaclassExtension("S2_1", "Class");
		Pair<Stereotype, Class> s12Generalization = fixture.getMetaclassExtension("S1_2", "Generalization");
		Pair<Stereotype, Class> s22Generalization = fixture.getMetaclassExtension("S2_2", "Generalization");
		fixture.assertSpecializationType(s11Class);
		fixture.assertSpecializationType(s21Class);
		fixture.assertSpecializationType(s12Generalization);
		fixture.assertSpecializationType(s22Generalization);
	}

	@Test
	public void iconGenerated() {
		Pair<Stereotype, Class> s11Class = fixture.getMetaclassExtension("S1_1", "Class");
		Pair<Stereotype, Class> s21Class = fixture.getMetaclassExtension("S2_1", "Class");
		Pair<Stereotype, Class> s12Generalization = fixture.getMetaclassExtension("S1_2", "Generalization");
		Pair<Stereotype, Class> s22Generalization = fixture.getMetaclassExtension("S2_2", "Generalization");

		SpecializationTypeConfiguration type = fixture.assertSpecializationType(s11Class);
		IconEntry icon = type.getIconEntry();
		assertThat(icon.getBundleId(), is(UMLEditPlugin.getPlugin().getSymbolicName()));
		assertThat(icon.getIconPath(), containsString("obj16/Class.gif"));

		type = fixture.assertSpecializationType(s21Class);
		icon = type.getIconEntry();
		assertThat(icon.getBundleId(), is(UMLEditPlugin.getPlugin().getSymbolicName()));
		assertThat(icon.getIconPath(), containsString("obj16/Class.gif"));

		type = fixture.assertSpecializationType(s12Generalization);
		icon = type.getIconEntry();
		assertThat(icon.getBundleId(), is(UMLEditPlugin.getPlugin().getSymbolicName()));
		assertThat(icon.getIconPath(), containsString("obj16/Generalization.gif"));

		type = fixture.assertSpecializationType(s22Generalization);
		icon = type.getIconEntry();
		assertThat(icon.getBundleId(), is(UMLEditPlugin.getPlugin().getSymbolicName()));
		assertThat(icon.getIconPath(), containsString("obj16/Generalization.gif"));
	}

	@Test
	public void stereotypeMatcherGenerated() {
		Pair<Stereotype, Class> s11Class = fixture.getMetaclassExtension("S1_1", "Class");
		Pair<Stereotype, Class> s21Class = fixture.getMetaclassExtension("S2_1", "Class");
		Pair<Stereotype, Class> s12Generalization = fixture.getMetaclassExtension("S1_2", "Generalization");
		Pair<Stereotype, Class> s22Generalization = fixture.getMetaclassExtension("S2_2", "Generalization");

		SpecializationTypeConfiguration type = fixture.assertSpecializationType(s11Class);
		StereotypeApplicationMatcherConfiguration matcher = fixture.assertStereotypeMatcher(type);
		assertThat(matcher.getStereotypesQualifiedNames(), hasItem("root::nestedProfile::S1_1"));

		type = fixture.assertSpecializationType(s21Class);
		matcher = fixture.assertStereotypeMatcher(type);
		assertThat(matcher.getStereotypesQualifiedNames(), hasItem("root::nestedPackage::S2_1"));

		type = fixture.assertSpecializationType(s12Generalization);
		matcher = fixture.assertStereotypeMatcher(type);
		assertThat(matcher.getStereotypesQualifiedNames(), hasItem("root::nestedProfile::S1_2"));

		type = fixture.assertSpecializationType(s22Generalization);
		matcher = fixture.assertStereotypeMatcher(type);
		assertThat(matcher.getStereotypesQualifiedNames(), hasItem("root::nestedPackage::S2_2"));
	}

	@Test
	public void stereotypeAdviceGenerated() {
		Pair<Stereotype, Class> s11Class = fixture.getMetaclassExtension("S1_1", "Class");
		Pair<Stereotype, Class> s21Class = fixture.getMetaclassExtension("S2_1", "Class");
		Pair<Stereotype, Class> s12Generalization = fixture.getMetaclassExtension("S1_2", "Generalization");
		Pair<Stereotype, Class> s22Generalization = fixture.getMetaclassExtension("S2_2", "Generalization");

		StereotypeMatcherAdviceConfiguration advice = fixture.assertStereotypeMatcherAdvice(s11Class);
		assertThat(advice.getTarget(), is(fixture.getElementTypeConfiguration(s11Class)));
		assertThat(advice.getStereotypesQualifiedNames(), not(isEmpty()));
		assertThat(advice.getStereotypesQualifiedNames().get(0), is("root::nestedProfile::S1_1"));

		advice = fixture.assertStereotypeMatcherAdvice(s12Generalization);
		assertThat(advice.getTarget(), is(fixture.getElementTypeConfiguration(s12Generalization)));
		assertThat(advice.getStereotypesQualifiedNames(), not(isEmpty()));
		assertThat(advice.getStereotypesQualifiedNames().get(0), is("root::nestedProfile::S1_2"));

		advice = fixture.assertStereotypeMatcherAdvice(s21Class);
		assertThat(advice.getTarget(), is(fixture.getElementTypeConfiguration(s21Class)));
		assertThat(advice.getStereotypesQualifiedNames(), not(isEmpty()));
		assertThat(advice.getStereotypesQualifiedNames().get(0), is("root::nestedPackage::S2_1"));

		advice = fixture.assertStereotypeMatcherAdvice(s22Generalization);
		assertThat(advice.getTarget(), is(fixture.getElementTypeConfiguration(s22Generalization)));
		assertThat(advice.getStereotypesQualifiedNames(), not(isEmpty()));
		assertThat(advice.getStereotypesQualifiedNames().get(0), is("root::nestedPackage::S2_2"));
	}
}
