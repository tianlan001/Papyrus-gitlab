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

package org.eclipse.papyrus.uml.tools.tests.tests;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListenerImpl;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.utils.TransactionHelper;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ServiceRegistryModelSetFixture;
import org.eclipse.papyrus.uml.tools.listeners.ProfileApplicationListener;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

/**
 * Tests the service-registered {@link ProfileApplicationListener}.
 */
@PluginResource("resources/profile_application_listener/model.di")
public class ProfileApplicationListenerTest extends AbstractPapyrusTest {

	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	@Rule
	public final ModelSetFixture modelSet = new ServiceRegistryModelSetFixture();

	private ListenerFixture fixture = new ListenerFixture();

	private boolean _transactionLatch;

	private Profile ecoreProfile;

	private Profile standardProfile;

	private Package noProfile;

	private Package oneProfile;

	private Package twoProfiles;

	public ProfileApplicationListenerTest() {
		super();
	}

	@Test
	public void simpleNewProfileApplication() {
		if (inTransaction()) {
			noProfile.applyProfile(standardProfile);
		} else {
			fixture.assertApplied(noProfile, standardProfile);
		}
	}

	@Test
	public void simpleProfileUnapplication() {
		if (inTransaction()) {
			oneProfile.unapplyProfile(standardProfile);
		} else {
			fixture.assertUnapplied(oneProfile, standardProfile);
		}
	}

	@Test
	public void twoProfilesApplied() {
		if (inTransaction()) {
			noProfile.applyProfile(standardProfile);
			noProfile.applyProfile(ecoreProfile);
		} else {
			fixture.assertApplied(noProfile, standardProfile);
			fixture.assertApplied(noProfile, ecoreProfile);
		}
	}

	@Test
	public void twoProfilesUnapplied() {
		if (inTransaction()) {
			// Unapply them by different means
			twoProfiles.unapplyProfile(standardProfile);
			twoProfiles.getProfileApplication(ecoreProfile).eUnset(UMLPackage.Literals.PROFILE_APPLICATION__APPLIED_PROFILE);
		} else {
			fixture.assertUnapplied(twoProfiles, standardProfile);
			fixture.assertUnapplied(twoProfiles, ecoreProfile);
		}
	}

	@Test
	public void profileReplacedInAnApplication() {
		if (inTransaction()) {
			ProfileApplication application = oneProfile.getProfileApplication(standardProfile);
			application.setAppliedProfile(ecoreProfile);
		} else {
			// The net effect is that the Ecore profile was applied
			fixture.assertApplied(oneProfile, ecoreProfile);

			// And the Standard profile was unapplied
			fixture.assertUnapplied(oneProfile, standardProfile);
		}
	}

	@Test
	public void profileAppliedThenReplacedInSameApplication() {
		if (inTransaction()) {
			noProfile.applyProfile(standardProfile);
			ProfileApplication application = noProfile.getProfileApplication(standardProfile);
			application.setAppliedProfile(ecoreProfile);
		} else {
			// The net effect is that the Ecore profile was applied
			fixture.assertApplied(noProfile, ecoreProfile);

			// The Standard profile was not applied and then unapplied
			fixture.assertNotApplied(noProfile, standardProfile);
			fixture.assertNotUnapplied(noProfile, standardProfile);
		}
	}

	@Test
	public void moveProfileApplication() {
		if (inTransaction()) {
			ProfileApplication application = oneProfile.getProfileApplication(standardProfile);
			noProfile.getProfileApplications().add(application);
		} else {
			// The net effect is that the Standard profile was unapplied from the one package
			fixture.assertUnapplied(oneProfile, standardProfile);

			// and applied to the other
			fixture.assertApplied(noProfile, standardProfile);
		}
	}

	@Test
	public void applyProfileThenMoveApplication() {
		if (inTransaction()) {
			noProfile.applyProfile(ecoreProfile);
			ProfileApplication application = noProfile.getProfileApplication(ecoreProfile);
			oneProfile.getProfileApplications().add(application);
		} else {
			// The net effect is that the Ecore profile was unapplied to the one package
			fixture.assertApplied(oneProfile, ecoreProfile);

			// and not applied to nor unapplied from the other
			fixture.assertNotApplied(noProfile, ecoreProfile);
			fixture.assertNotUnapplied(noProfile, ecoreProfile);
		}
	}

	//
	// Test framework
	//

	@Before
	public void setup() throws ServiceException {
		ecoreProfile = UML2Util.load(modelSet.getResourceSet(), URI.createURI(UMLResource.ECORE_PROFILE_URI), UMLPackage.Literals.PROFILE);
		standardProfile = UML2Util.load(modelSet.getResourceSet(), URI.createURI(UMLResource.STANDARD_PROFILE_URI), UMLPackage.Literals.PROFILE);

		oneProfile = modelSet.getModel().getNestedPackage("OneProfile"); //$NON-NLS-1$
		twoProfiles = modelSet.getModel().getNestedPackage("TwoProfiles"); //$NON-NLS-1$
		noProfile = modelSet.getModel().getNestedPackage("NoProfile"); //$NON-NLS-1$

		modelSet.getEditingDomain().addResourceSetListener(fixture);
		fixture.clear();
	}

	private boolean inTransaction() {
		boolean result = _transactionLatch;

		if (!result) {
			_transactionLatch = true;
			try {
				final Method method = getClass().getMethod(houseKeeper.getTestName());
				TransactionHelper.run(modelSet.getEditingDomain(), new Runnable() {

					@Override
					public void run() {
						try {
							method.invoke(ProfileApplicationListenerTest.this);
						} catch (Exception e) {
							throw new Error("Failed to reflectively invoke test within transaction", e); //$NON-NLS-1$
						}
					}
				});
			} catch (Exception e) {
				throw new Error("Failed to reflectively invoke test within transaction", e); //$NON-NLS-1$
			} finally {
				_transactionLatch = false;
			}
		}

		return result;
	}

	private static class ListenerFixture extends ResourceSetListenerImpl {
		private Multimap<Package, Profile> applications = LinkedHashMultimap.create();
		private Multimap<Package, Profile> unapplications = LinkedHashMultimap.create();

		ListenerFixture() {
			super(new NotificationFilter.Custom() {
				@Override
				public boolean matches(Notification notification) {
					return notification instanceof ProfileApplicationListener.ProfileApplicationNotification;
				}
			});
		}

		@Override
		public boolean isPostcommitOnly() {
			return true;
		}

		@Override
		public void resourceSetChanged(ResourceSetChangeEvent event) {
			for (ProfileApplicationListener.ProfileApplicationNotification next : Iterables.filter(event.getNotifications(), ProfileApplicationListener.ProfileApplicationNotification.class)) {
				switch (next.getEventType()) {
				case ProfileApplicationListener.ProfileApplicationNotification.PROFILE_APPLIED:
					applications.put(next.getNotifyingPackage(), next.getAppliedProfile());
					break;
				case ProfileApplicationListener.ProfileApplicationNotification.PROFILE_UNAPPLIED:
					unapplications.put(next.getNotifyingPackage(), next.getUnappliedProfile());
					break;
				default:
					fail("Odd sort of a notification to have received: " + next.getEventType());
					break; // Unreachable
				}
			}
		}

		void clear() {
			applications.clear();
			unapplications.clear();
		}

		void assertApplied(Package package_, Profile profile) {
			assertThat(applications.get(package_), hasItem(profile));
		}

		void assertUnapplied(Package package_, Profile profile) {
			assertThat(unapplications.get(package_), hasItem(profile));
		}

		void assertNotApplied(Package package_, Profile profile) {
			assertThat(applications.get(package_), not(hasItem(profile)));
		}

		void assertNotUnapplied(Package package_, Profile profile) {
			assertThat(unapplications.get(package_), not(hasItem(profile)));
		}
	}
}
