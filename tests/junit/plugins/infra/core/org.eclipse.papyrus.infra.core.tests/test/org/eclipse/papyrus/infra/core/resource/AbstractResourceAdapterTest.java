/*
 * Copyright (c) 2014, 2015 CEA, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus (CEA) - Initial API and implementation
 *   Christian W. Damus - bug 482949
 *   
 */
package org.eclipse.papyrus.infra.core.resource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.AbstractHouseKeeperRule.CleanUp;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;


/**
 * Abstract test suite for the {@link ResourceAdapter} class, providing tests
 * for both the base and transactional variants.
 */
public abstract class AbstractResourceAdapterTest<T, A extends ResourceAdapter> extends AbstractPapyrusTest {

	private static final String RESOURCE_ADDED = "handleResourceAdded";

	private static final String RESOURCE_REMOVED = "handleResourceRemoved";

	private static final String RESOURCE_LOADED = "handleResourceLoaded";

	private static final String RESOURCE_UNLOADED = "handleResourceUnloaded";

	private static final String RESOURCE_URI = "handleResourceURI";

	private static final String RESOURCE_ROOT_ADDED = "handleRootAdded";

	private static final String RESOURCE_ROOT_REMOVED = "handleRootRemoved";

	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	private T target;

	private ResourceSet rset;

	@CleanUp
	private Fixture fixture;

	public AbstractResourceAdapterTest() {
		super();
	}

	@Test
	public void testResourceAdded() {
		Resource res = new ResourceImpl();
		execute(() -> rset.getResources().add(res));

		fixture.assertHooks(RESOURCE_ADDED);
		fixture.assertResources(res);
	}

	@Test
	public void testResourceRemoved() {
		Resource res = new ResourceImpl();
		execute(() -> rset.getResources().add(res));

		fixture.reset();
		execute(() -> rset.getResources().remove(0));

		fixture.assertHooks(RESOURCE_REMOVED);
		fixture.assertResources(res);
	}

	@Test
	public void testResourceLoaded() {
		Resource res = rset.createResource(getTestResourceURI());

		fixture.reset();
		safeExecute(() -> res.load(null));

		fixture.assertHooks(RESOURCE_LOADED);
		fixture.assertResources(res);
	}

	@Test
	public void testResourceUnloaded() {
		Resource res = rset.getResource(getTestResourceURI(), true);

		fixture.reset();
		execute(() -> res.unload());

		fixture.assertHooks(RESOURCE_UNLOADED);
		fixture.assertResources(res);
	}

	@Test
	public void testResourceURI() {
		URI oldURI = getTestResourceURI();
		Resource res = rset.getResource(oldURI, true);

		fixture.reset();
		URI newURI = URI.createURI("http:///bogus.ecore");
		execute(() -> res.setURI(newURI));

		fixture.assertHooks(RESOURCE_URI);
		fixture.assertResources(res);
		fixture.assertURIs(oldURI, newURI);
	}

	@Test
	public void testRootAdded() {
		Resource res = rset.getResource(getTestResourceURI(), true);

		fixture.reset();
		EObject root = EcoreFactory.eINSTANCE.createEObject();
		execute(() -> res.getContents().add(root));

		fixture.assertHooks(RESOURCE_ROOT_ADDED);
		fixture.assertResources(res);
		fixture.assertRoots(root);
	}

	@Test
	public void testRootRemoved() {
		Resource res = rset.getResource(getTestResourceURI(), true);

		fixture.reset();
		EObject root = res.getContents().get(0);
		execute(() -> res.getContents().remove(root));

		fixture.assertHooks(RESOURCE_ROOT_REMOVED);
		fixture.assertResources(res);
		fixture.assertRoots(root);
	}

	@Test
	public void testChangesInPreexistingResources_bug481151() {
		// Need a different one than the usual, which already has the fixture attached
		T newTarget = createTarget(houseKeeper);
		ResourceSet newRset = getResourceSet(newTarget);

		// A pre-existing resource
		Resource resource = new ResourceImpl(URI.createURI("bogus://test"));
		execute(() -> newRset.getResources().add(resource));

		fixture.install(newTarget);

		// Consequence of initial discovery
		fixture.assertHooks(RESOURCE_ADDED);
		fixture.reset();

		EObject newRoot = EcoreFactory.eINSTANCE.createEObject();
		execute(() -> resource.getContents().add(newRoot));

		// An unloaded resource becomes implicitly loaded when its contents are changed
		if (isTransactional()) {
			// In the transactional case, all observations are delayed until the end
			// of the transaction, so if a resource was loaded during the transaction,
			// it doesn't matter what else happened to its contents because we don't
			// observe any of those contents until later. So, there is no distinction
			// to be had in the additional root-add event, and it isn't observed
			fixture.assertHooks(RESOURCE_LOADED);
		} else {
			// In the non-transactional case, the add happens after load has completed,
			// so the resource is no longer loading at that point, and we observe the
			// root being added
			fixture.assertHooks(RESOURCE_LOADED, RESOURCE_ROOT_ADDED);
		}
	}

	@Test
	public void testUninstallResourceAdapter_bug482949() {
		// uninstall now
		fixture.uninstall(target);

		// We should not observe any events
		Resource res = new ResourceImpl();
		execute(() -> rset.getResources().add(res));

		fixture.assertHooks();
		fixture.assertResources();
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() {
		target = createTarget(houseKeeper);
		rset = getResourceSet(target);
		fixture = doCreateFixture();

		fixture.install(target);
		houseKeeper.cleanUpLater(fixture, f -> f.uninstall(target));
	}

	protected boolean isTransactional() {
		return false;
	}

	protected abstract T createTarget(HouseKeeper keeper);

	protected abstract ResourceSet getResourceSet(T target);

	protected abstract void execute(Runnable command);

	protected abstract void safeExecute(RunnableWithException command);

	protected abstract Fixture doCreateFixture();

	protected final T getTarget() {
		return target;
	}

	URI getTestResourceURI() {
		// Doesn't matter the resource; this one's conveniently available
		return URI.createURI(getClass().getResource("Bug402525.ecore").toExternalForm(), true);
	}

	//
	// Nested types
	//

	protected abstract class Fixture {

		private Set<Resource> resources = Sets.newHashSet();

		private Set<String> hooksCalled = Sets.newHashSet();

		private Set<EObject> roots = Sets.newHashSet();

		private URI oldURI;

		private URI newURI;

		private A adapter;

		void reset() {
			resources.clear();
			hooksCalled.clear();
			roots.clear();
			oldURI = null;
			newURI = null;
		}

		void assertResources(Resource... resources) {
			assertThat(this.resources, is((Set<Resource>) ImmutableSet.copyOf(resources)));
		}

		void assertHooks(String... hooks) {
			assertThat(this.hooksCalled, is((Set<String>) ImmutableSet.copyOf(hooks)));
		}

		void assertRoots(EObject... objects) {
			assertThat(this.roots, is((Set<EObject>) ImmutableSet.copyOf(objects)));
		}

		void assertURIs(URI oldURI, URI newURI) {
			assertThat(this.oldURI, is(oldURI));
			assertThat(this.newURI, is(newURI));
		}

		private void called(Resource resource, String hook) {
			hooksCalled.add(hook);
			resources.add(resource);
		}

		private void root(EObject root) {
			roots.add(root);
		}

		private void uri(URI oldURI, URI newURI) {
			this.oldURI = oldURI;
			this.newURI = newURI;
		}

		protected void handleResourceAdded(Resource resource) {
			called(resource, RESOURCE_ADDED);
		}

		protected void handleResourceRemoved(Resource resource) {
			called(resource, RESOURCE_REMOVED);
		}

		protected void handleResourceLoaded(Resource resource) {
			called(resource, RESOURCE_LOADED);
		}

		protected void handleResourceUnloaded(Resource resource) {
			called(resource, RESOURCE_UNLOADED);
		}

		protected void handleResourceURI(Resource resource, URI oldURI, URI newURI) {
			called(resource, RESOURCE_URI);
			uri(oldURI, newURI);
		}

		protected void handleRootAdded(Resource resource, EObject root) {
			called(resource, RESOURCE_ROOT_ADDED);
			root(root);
		}

		protected void handleRootRemoved(Resource resource, EObject root) {
			called(resource, RESOURCE_ROOT_REMOVED);
			root(root);
		}

		void install(T target) {
			adapter = createAdapter();
			doInstall(adapter, target);
		}

		void uninstall(T target) {
			if (adapter != null) {
				doUninstall(adapter, target);
				adapter = null;
			}
		}

		protected abstract A createAdapter();

		protected abstract void doInstall(A adapter, T target);

		protected abstract void doUninstall(A adapter, T target);
	}

	@FunctionalInterface
	protected interface RunnableWithException {
		void run() throws Exception;
	}
}
