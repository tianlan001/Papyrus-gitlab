/*
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
 */
package org.eclipse.papyrus.infra.core.resource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;


/**
 * Test suite for the {@link ResourceAdapter.Transactional} class.
 */
public class ResourceAdapterTransactionalTest extends AbstractResourceAdapterTest<TransactionalEditingDomain, ResourceAdapter.Transactional> {

	public ResourceAdapterTransactionalTest() {
		super();
	}

	//
	// Test framework
	//

	@Override
	protected boolean isTransactional() {
		return true;
	}

	@Override
	protected TransactionalEditingDomain createTarget(HouseKeeper keeper) {
		return keeper.createSimpleEditingDomain();
	}

	@Override
	protected ResourceSet getResourceSet(TransactionalEditingDomain target) {
		return target.getResourceSet();
	}

	@Override
	protected void execute(Runnable command) {
		TransactionalEditingDomain domain = getTarget();
		domain.getCommandStack().execute(new RecordingCommand(domain, "Test") {

			@Override
			protected void doExecute() {
				command.run();
			}
		});
	}

	@Override
	protected void safeExecute(RunnableWithException command) {
		TransactionalEditingDomain domain = getTarget();
		domain.getCommandStack().execute(new RecordingCommand(domain, "Test") {

			@Override
			protected void doExecute() {
				try {
					command.run();
				} catch (Exception e) {
					throw new WrappedException(e);
				}
			}
		});
	}

	@Override
	protected Fixture doCreateFixture() {
		return new MyFixture();
	}

	class MyFixture extends Fixture {

		@Override
		protected void doInstall(ResourceAdapter.Transactional adapter, TransactionalEditingDomain target) {
			adapter.install(target);
		}

		@Override
		protected void doUninstall(ResourceAdapter.Transactional adapter, TransactionalEditingDomain target) {
			adapter.uninstall(target);
		}

		@Override
		protected ResourceAdapter.Transactional createAdapter() {
			return new ResourceAdapter.Transactional() {
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
