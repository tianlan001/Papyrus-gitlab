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

package org.eclipse.papyrus.uml.filters.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.JavaResource;
import org.eclipse.papyrus.junit.utils.rules.StandaloneResourceSetFixture;
import org.eclipse.papyrus.uml.filters.ProfileApplied;
import org.eclipse.papyrus.uml.filters.UMLFiltersFactory;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.junit.Rule;
import org.junit.Test;

/**
 * Specific bug regression tests for the {@link ProfileApplied} filter.
 */
public class ProfileAppliedRegressionTest extends AbstractPapyrusTest {

	@Rule
	public final StandaloneResourceSetFixture<Package> model = StandaloneResourceSetFixture.create(Package.class);

	public ProfileAppliedRegressionTest() {
		super();
	}

	/**
	 * Verify that a filter that doesn't match a package doesn't cause its profile
	 * to be loaded as a side-effect.
	 */
	@JavaResource("bug480221.uml")
	@Test
	public void profileAppliedFilterDoesNotLoadProfiles_miss_bug480221() {
		ProfileApplied filter = UMLFiltersFactory.eINSTANCE.createProfileApplied();
		filter.setProfileQualifiedName("bug480221profile::nested::profile");

		URI profileResourceURI = model.getResource().getURI().trimSegments(1).appendSegment("bug480221.profile.uml");
		filter.setProfileURI(profileResourceURI.appendFragment("_0").toString());

		// Verify that the filter does not load the profile that it references
		Package nested = model.getModel().getNestedPackage("nested");
		assertThat("Package reports as having profile applied", filter.matches(nested), is(false));
		assertThat("Filter loaded the profile", model.getResource(profileResourceURI, false), nullValue());

		// Unless you ask it to
		Profile resolved = filter.resolveProfile(nested);
		assertThat(resolved, notNullValue());
		assertThat(resolved.eIsProxy(), is(false));
	}

	/**
	 * Control test for the case of a filter for a profile that is actually applied.
	 */
	@JavaResource("bug480221.uml")
	@Test
	public void profileAppliedFilterDoesNotLoadProfiles_hit_bug480221() {
		ProfileApplied filter = UMLFiltersFactory.eINSTANCE.createProfileApplied();
		filter.setProfileQualifiedName("my::nested::myprofile");

		URI profileResourceURI = model.getResource().getURI().trimSegments(1).appendSegment("my.profile.uml");
		filter.setProfileURI(profileResourceURI.appendFragment("_0").toString());

		// Verify that the filter does actually work for a profile that is applied
		Package nested = model.getModel().getNestedPackage("nested");
		assertThat("Package does not report as having profile applied", filter.matches(nested), is(true));
	}

	/**
	 * Test that a filter specifying a resource URI instead of an object URI in
	 * reference to the profile doesn't cause NPEs.
	 */
	@JavaResource("bug480221.uml")
	@Test
	public void profileAppliedFilterResourceURI_bug480221() {
		ProfileApplied filter = UMLFiltersFactory.eINSTANCE.createProfileApplied();
		filter.setProfileQualifiedName("bug480221profile::nested::profile");

		URI profileResourceURI = model.getResource().getURI().trimSegments(1).appendSegment("bug480221.profile.uml");
		filter.setProfileURI(profileResourceURI.toString()); // No fragment

		try {
			// The filter does not load the profile that it references nor does it NPE
			Package nested = model.getModel().getNestedPackage("nested");
			assertThat("Package reports as having profile applied", filter.matches(nested), is(false));
			assertThat("Filter loaded the profile", model.getResource(profileResourceURI, false), nullValue());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getClass().getSimpleName() + " thrown: " + e.getMessage());
		}
	}

}
