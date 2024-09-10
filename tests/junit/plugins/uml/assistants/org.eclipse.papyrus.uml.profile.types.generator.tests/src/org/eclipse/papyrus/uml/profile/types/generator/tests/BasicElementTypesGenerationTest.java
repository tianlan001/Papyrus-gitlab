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
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.IconEntry;
import org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherConfiguration;
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeMatcherAdviceConfiguration;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.edit.UMLEditPlugin;
import org.eclipse.xtext.xbase.lib.Pair;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * Test cases for the basics of element types generation for UML profiles.
 */
@PluginResource("/resources/j2ee.profile.uml")
public class BasicElementTypesGenerationTest extends AbstractPapyrusTest {

	@ClassRule
	public static final ModelGenFixture fixture = new ModelGenFixture();

	public BasicElementTypesGenerationTest() {
		super();
	}

	@Test
	public void elementTypesGenerated() {
		Pair<Stereotype, Class> beanClass = fixture.getMetaclassExtension("Bean", "Class");
		fixture.assertSpecializationType(beanClass);
		assertThat(fixture.getElementTypeSet().getMetamodelNsURI(), is(UMLPackage.eNS_URI));
	}

	/**
	 * Verifies that non-diagram-specific element types are generated with only a semantic parent (no visual parent).
	 *
	 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=461717
	 */
	@Test
	public void elementTypeSpecializedTypes_bug461717() {
		Pair<Stereotype, Class> beanClass = fixture.getMetaclassExtension("Bean", "Class");
		SpecializationTypeConfiguration specialization = fixture.assertSpecializationType(beanClass);

		assertThat(specialization.getIdentifier(), is(fixture.prefix + ".Bean"));

		List<ElementTypeConfiguration> specializedTypes = specialization.getSpecializedTypes();
		assertThat(specializedTypes.size(), is(1));
		assertThat(specializedTypes.get(0).getIdentifier(), is(UMLElementTypes.CLASS.getId()));
	}

	@Test
	public void multipleElementTypesForOneStereotypeGenerated() {
		Pair<Stereotype, Class> accessClass = fixture.getMetaclassExtension("AccessControlled", "Class");
		Pair<Stereotype, Class> accessProperty = fixture.getMetaclassExtension("AccessControlled", "Property");
		assertThat(fixture.assertSpecializationType(accessClass), not(fixture.assertSpecializationType(accessProperty)));
	}

	@Test
	public void iconGenerated() {
		Pair<Stereotype, Class> beanClass = fixture.getMetaclassExtension("Bean", "Class");
		SpecializationTypeConfiguration type = fixture.assertSpecializationType(beanClass);
		IconEntry icon = type.getIconEntry();
		assertThat(icon.getBundleId(), is(UMLEditPlugin.getPlugin().getSymbolicName()));
		assertThat(icon.getIconPath(), containsString("obj16/Class.gif"));
	}

	@Test
	public void stereotypeMatcherGenerated() {
		Pair<Stereotype, Class> beanClass = fixture.getMetaclassExtension("Bean", "Class");
		SpecializationTypeConfiguration type = fixture.assertSpecializationType(beanClass);
		StereotypeApplicationMatcherConfiguration matcher = fixture.assertStereotypeMatcher(type);
		assertThat(matcher.getStereotypesQualifiedNames(), hasItem("j2ee::Bean"));
		assertThat(matcher, instanceOf(StereotypeMatcherAdviceConfiguration.class));
	}

	@Test
	public void stereotypeAdviceGenerated() {
		Pair<Stereotype, Class> beanClass = fixture.getMetaclassExtension("Bean", "Class");
		StereotypeMatcherAdviceConfiguration advice = fixture.assertStereotypeMatcherAdvice(beanClass);
		assertThat(advice.getTarget(), is(fixture.getElementTypeConfiguration(beanClass)));
		assertThat(advice.getStereotypesQualifiedNames(), not(isEmpty()));
		assertThat(advice.getStereotypesQualifiedNames().get(0), is("j2ee::Bean"));
	}

	@Test
	public void typeSetAttributes() {
		ElementTypeSetConfiguration typeSet = fixture.getElementTypeSet();
		assertThat(typeSet.getMetamodelNsURI(), is("http://www.eclipse.org/uml2/5.0.0/UML"));
		assertThat(typeSet.getIdentifier(), is("org.eclipse.papyrus.test.elementTypes"));
	}
}
