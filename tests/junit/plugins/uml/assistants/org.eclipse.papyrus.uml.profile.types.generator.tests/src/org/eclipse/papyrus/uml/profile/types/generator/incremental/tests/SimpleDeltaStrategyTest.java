/*****************************************************************************
 * Copyright (c) 2020 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   EclipseSource - Bug 569356
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.profile.types.generator.incremental.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ProjectName;
import org.eclipse.papyrus.junit.utils.rules.ResourceSetFixture;
import org.eclipse.papyrus.uml.profile.types.generator.DeltaStrategy;
import org.eclipse.papyrus.uml.profile.types.generator.DeltaStrategy.Diff;
import org.eclipse.papyrus.uml.profile.types.generator.ImpliedExtension;
import org.eclipse.papyrus.uml.profile.types.generator.strategy.SimpleDeltaStrategy;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

@ProjectName("j2ee")
@PluginResource({ "/resources/incremental/j2ee.profile.uml", "/resources/incremental/j2ee.elementtypesconfigurations" })
public class SimpleDeltaStrategyTest {
	@ClassRule
	public static ResourceSetFixture fixture = new ResourceSetFixture();
	private static Diff diff;

	@BeforeClass
	public static void computeDelta() {
		Profile profile = (Profile) fixture.getModel();

		DeltaStrategy strategy = new SimpleDeltaStrategy();

		Resource typesResource = fixture.getResourceSet().getResources().stream().filter(r -> r.getURI().fileExtension().equals("elementtypesconfigurations")).findFirst().orElse(null);
		diff = strategy.findDiffs(profile, (ElementTypeSetConfiguration) typesResource.getContents().get(0));
	}

	@Test
	public void testAddedStereotypes() {
		assertThat(diff.getAddedStereotypes().size(), equalTo(1));
		Stereotype added = diff.getAddedStereotypes().get(0);
		assertThat(added.getQualifiedName(), equalTo("j2ee::NewBrowser"));
	}

	@Test
	public void testRemovedStereotypes() {
		assertThat(diff.getRemovedStereotypes().size(), equalTo(1));
		assertThat(diff.getRemovedStereotypes(), everyItem(equalTo("j2ee::Browser")));
	}

	@Test
	public void testRenamedStereotypes() {
		assertThat(diff.getRenamedStereotypes().size(), equalTo(1));
		assertThat(diff.getRenamedStereotypes().get("j2ee::Web").getQualifiedName(), equalTo("j2ee::WebRenamed"));
	}

	@Test
	public void testAddedExtensions() {
		assertThat(diff.getAddedExtensions().size(), equalTo(1));
		ImpliedExtension ext = diff.getAddedExtensions().get(0);
		assertThat(ext.getStereotype().getQualifiedName(), equalTo("j2ee::AccessControlled"));
		assertThat(ext.getMetaclass().getQualifiedName(), equalTo("UML::Package"));
	}

	@Test
	public void testRemovedExtension() {
		assertThat(diff.getRemovedExtensions().size(), equalTo(1));
		ImpliedExtension ext = diff.getRemovedExtensions().get(0);
		assertThat(ext.getStereotype().getQualifiedName(), equalTo("j2ee::AccessControlled"));
		assertThat(ext.getMetaclass().getQualifiedName(), equalTo("UML::Property"));
	}
}
