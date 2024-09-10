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

package org.eclipse.papyrus.uml.decoratormodel.internal.resource.index.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.junit.utils.rules.ProjectFixture;
import org.eclipse.papyrus.uml.tools.profile.index.IProfileIndex;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

/**
 * Tests the implementation of the Profile Index Service.
 */
public class ProfileIndexTest {
	@ClassRule
	public static final ProjectFixture project = new ProjectFixture();

	static ServicesRegistry registry;

	public ProfileIndexTest() {
		super();
	}

	@Test
	public void decoratorIndexing() throws ServiceException {
		IProfileIndex index = registry.getService(IProfileIndex.class);

		ListenableFuture<Set<URI>> futureProfileURIs = index.getAppliedProfiles(project.getURI("testmodel.uml"));
		Set<URI> profileURIs = trimFragments(Futures.getChecked(futureProfileURIs, ServiceException.class, 1L, TimeUnit.MINUTES));

		Set<URI> expected = ImmutableSet.of(
				project.getURI("profile/profile1.profile.uml"), // an externally applied profile
				URI.createURI(UMLResource.ECORE_PROFILE_URI), // also externally applied
				URI.createURI(UMLResource.STANDARD_PROFILE_URI)); // internally applied, actually
		assertThat(profileURIs, is(expected));
	}

	@Test
	public void userModelIndexing() throws ServiceException {
		IProfileIndex index = registry.getService(IProfileIndex.class);

		ListenableFuture<Set<URI>> futureProfileURIs = index.getAppliedProfiles(project.getURI("profile/profile1.profile.uml"));
		Set<URI> profileURIs = trimFragments(Futures.getChecked(futureProfileURIs, ServiceException.class, 1L, TimeUnit.MINUTES));

		assertThat(profileURIs, is(Collections.singleton(URI.createURI(UMLResource.ECORE_PROFILE_URI))));
	}

	@Test
	public void decoratorAsUserModelIndexing() throws ServiceException {
		IProfileIndex index = registry.getService(IProfileIndex.class);

		ListenableFuture<Set<URI>> futureProfileURIs = index.getAppliedProfiles(project.getURI("package2.decorator.uml"));
		Set<URI> profileURIs = trimFragments(Futures.getChecked(futureProfileURIs, ServiceException.class, 1L, TimeUnit.MINUTES));

		Set<URI> expected = Collections.emptySet(); // We don't index these as user models, but as decorators only
		assertThat(profileURIs, is(expected));
	}

	//
	// Test framework
	//

	@BeforeClass
	public static void createResources() throws IOException {
		project.createFile(ProfileIndexTest.class, "resources/testmodel.uml");
		project.createFile(ProfileIndexTest.class, "resources/package2.decorator.uml");
		project.createFile(ProfileIndexTest.class, "resources/package2.ecore.uml");
		project.createFile("profile/profile1.profile.uml", ProfileIndexTest.class, "resources/profile/profile1.profile.uml");
	}

	@SuppressWarnings("restriction")
	@BeforeClass
	public static void createRegistry() throws Exception {
		registry = new ServicesRegistry();
		registry.add(IProfileIndex.class, 1, new org.eclipse.papyrus.uml.decoratormodel.internal.resource.index.ProfileIndex());
		registry.startRegistry();
	}

	@AfterClass
	public static void destroyRegistry() throws Exception {
		if (registry != null) {
			registry.disposeRegistry();
		}
		registry = null;
	}

	static Set<URI> trimFragments(Set<URI> uris) {
		return ImmutableSet.copyOf(Iterables.transform(uris,
				new Function<URI, URI>() {
					@Override
					public URI apply(URI input) {
						return input.trimFragment();
					}
				}));
	}
}
