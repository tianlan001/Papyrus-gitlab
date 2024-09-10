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

import static org.junit.Assert.fail;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;


/**
 * Test suite for the {@link ResourceAdapter} class.
 */
public class ResourceAdapterTest extends AbstractResourceAdapterTest<ResourceSet, ResourceAdapter> {

	public ResourceAdapterTest() {
		super();
	}

	//
	// Test framework
	//

	@Override
	protected ResourceSet createTarget(HouseKeeper keeper) {
		return keeper.createResourceSet();
	}

	@Override
	protected ResourceSet getResourceSet(ResourceSet target) {
		return target;
	}

	@Override
	protected void execute(Runnable command) {
		command.run();
	}

	@Override
	protected void safeExecute(RunnableWithException command) {
		try {
			command.run();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Command execution failed: " + e.getMessage());
		}
	}

	@Override
	protected AbstractResourceAdapterTest<ResourceSet, ResourceAdapter>.Fixture doCreateFixture() {
		return new MyFixture();
	}

	class MyFixture extends Fixture {

		@Override
		protected void doInstall(ResourceAdapter adapter, ResourceSet target) {
			if (!target.eAdapters().contains(adapter)) {
				target.eAdapters().add(adapter);
			}
		}

		@Override
		protected void doUninstall(ResourceAdapter adapter, ResourceSet target) {
			target.eAdapters().remove(adapter);
		}

		@Override
		protected ResourceAdapter createAdapter() {
			return new ResourceAdapter() {
				@Override
				protected void handleResourceAdded(Resource resource) {
					MyFixture.this.handleResourceAdded(resource);
				}

				@Override
				protected void handleResourceRemoved(Resource resource) {
					MyFixture.this.handleResourceRemoved(resource);
				}

				@Override
				protected void handleResourceLoaded(Resource resource) {
					MyFixture.this.handleResourceLoaded(resource);
				}

				@Override
				protected void handleResourceUnloaded(Resource resource) {
					MyFixture.this.handleResourceUnloaded(resource);
				}

				@Override
				protected void handleResourceURI(Resource resource, URI oldURI, URI newURI) {
					MyFixture.this.handleResourceURI(resource, oldURI, newURI);
				}

				@Override
				protected void handleRootAdded(Resource resource, EObject root) {
					MyFixture.this.handleRootAdded(resource, root);
				}

				@Override
				protected void handleRootRemoved(Resource resource, EObject root) {
					MyFixture.this.handleRootRemoved(resource, root);
				}
			};
		}
	}
}
