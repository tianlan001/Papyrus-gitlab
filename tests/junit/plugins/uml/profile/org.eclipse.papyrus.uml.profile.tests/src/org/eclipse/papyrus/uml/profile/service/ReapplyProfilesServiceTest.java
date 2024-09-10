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

package org.eclipse.papyrus.uml.profile.service;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.profile.tests.DialogInteractionKind;
import org.eclipse.papyrus.uml.profile.tests.DialogInteractionMode;
import org.eclipse.papyrus.uml.profile.tests.DialogInteractionRule;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Stereotype;
import org.junit.Rule;
import org.junit.Test;

/**
 * Regression tests for the {@link ReapplyProfilesService} class.
 */
public class ReapplyProfilesServiceTest extends AbstractPapyrusTest {
	@Rule
	public final DialogInteractionRule dialog = new DialogInteractionRule();

	@Rule
	public final PapyrusEditorFixture editor = new PapyrusEditorFixture();

	/**
	 * A control test, as it were, for up-to-date application of an UML2-style
	 * profile (one that does not have the Papyrus versioning annotation).
	 * This profile also uses the same namespace URI for every version.
	 */
	@Test
	@DialogInteractionMode(DialogInteractionKind.NONE)
	@PluginResource("resources/uml2/up-to-date.di")
	public void upToDateModelDoesNotPromptForMigration_uml2() {
		dialog.assertInteraction();
	}

	/**
	 * Test migration of an out-of-date application of an UML2-style
	 * profile (on that does not have the Papyrus versioning annotation).
	 * This profile also uses the same namespace URI for every version.
	 */
	@Test
	@DialogInteractionMode(DialogInteractionKind.OK)
	@PluginResource("resources/uml2/out-of-date.di")
	public void outOfDateModelDoMigration_uml2() {
		dialog.assertInteraction();

		// Now check that the migration occurred
		EObject thing = requireFooThing();
		EClass meta = thing.eClass();
		assertThat("Profile application was not migrated", meta.getEStructuralFeature("tag"), notNullValue());
	}

	/**
	 * Test skipping migration of an out-of-date application of an UML2-style
	 * profile (on that does not have the Papyrus versioning annotation).
	 * This profile also uses the same namespace URI for every version.
	 */
	@Test
	@DialogInteractionMode(DialogInteractionKind.CANCEL)
	@PluginResource("resources/uml2/out-of-date.di")
	public void outOfDateModelSkipMigration_uml2() {
		dialog.assertInteraction();

		// Now check that the migration did not occur
		EObject thing = requireFooThing();
		EClass meta = thing.eClass();
		assertThat("Profile application was migrated", meta.getEStructuralFeature("tag"), nullValue());
	}

	/**
	 * A control test, as it were, for up-to-date application of a Papyrus-style
	 * profile (one that has the Papyrus versioning annotation).
	 * This profile also uses the a different namespace URI for every version.
	 */
	@Test
	@DialogInteractionMode(DialogInteractionKind.NONE)
	@PluginResource("resources/papyrus/up-to-date.di")
	public void upToDateModelDoesNotPromptForMigration_papyrus() {
		dialog.assertInteraction();
	}

	/**
	 * Test migration of an out-of-date application of a Papyrus-style
	 * profile (on that has the Papyrus versioning annotation).
	 * This profile also uses the a different namespace URI for every version.
	 */
	@Test
	@DialogInteractionMode(DialogInteractionKind.OK)
	@PluginResource("resources/papyrus/out-of-date.di")
	public void outOfDateModelDoMigration_papyrus() {
		dialog.assertInteraction();

		// Now check that the migration occurred
		EObject thing = requireFooThing();
		EClass meta = thing.eClass();
		assertThat("Profile application was not migrated", meta.getEStructuralFeature("tag"), notNullValue());
	}

	/**
	 * Test skipping migration of an out-of-date application of a Papyrus-style
	 * profile (on that has the Papyrus versioning annotation).
	 * This profile also uses the a different namespace URI for every version.
	 */
	@Test
	@DialogInteractionMode(DialogInteractionKind.CANCEL)
	@PluginResource("resources/papyrus/out-of-date.di")
	public void outOfDateModelSkipMigration_papyrus() {
		dialog.assertInteraction();

		// Now check that the migration did not occur
		EObject thing = requireFooThing();
		EClass meta = thing.eClass();
		assertThat("Profile application was migrated", meta.getEStructuralFeature("tag"), nullValue());
	}

	/**
	 * A control test, as it were, for up-to-date application of a
	 * registered dynamic profile.
	 * This profile also uses the same namespace URI for every version.
	 */
	@Test
	@DialogInteractionMode(DialogInteractionKind.NONE)
	@PluginResource("resources/dynamic/up-to-date.di")
	public void upToDateModelDoesNotPromptForMigration_dynamic() {
		dialog.assertInteraction();
	}

	/**
	 * Test migration of an out-of-date application of a
	 * registered dynamic profile.
	 * This profile also uses the same namespace URI for every version.
	 */
	@Test
	@DialogInteractionMode(DialogInteractionKind.OK)
	@PluginResource("resources/dynamic/out-of-date.di")
	public void outOfDateModelDoMigration_dynamic() {
		dialog.assertInteraction();

		// Now check that the migration occurred
		EObject thing = requireFooThing();
		EClass meta = thing.eClass();
		assertThat("Profile application was not migrated", meta.getEStructuralFeature("tag"), notNullValue());
	}

	/**
	 * Test skipping migration of an out-of-date application of a
	 * registered dynamic profile.
	 * This profile also uses the same namespace URI for every version.
	 */
	@Test
	@DialogInteractionMode(DialogInteractionKind.CANCEL)
	@PluginResource("resources/dynamic/out-of-date.di")
	public void outOfDateModelSkipMigration_dynamic() {
		dialog.assertInteraction();

		// Now check that the migration did not occur
		EObject thing = requireFooThing();
		EClass meta = thing.eClass();
		assertThat("Profile application was migrated", meta.getEStructuralFeature("tag"), nullValue());
	}

	//
	// Test framework
	//

	Class getFoo() {
		return (Class) editor.getModel().getOwnedType("Foo");
	}

	Stereotype getThing() {
		return editor.getModel().getAppliedProfile("stuff").getOwnedStereotype("Thing");
	}

	EObject requireFooThing() {
		EObject result = getFoo().getStereotypeApplication(getThing());
		assertThat("Foo does not have stereotype <<thing>>", result, notNullValue());
		return result;
	}
}
