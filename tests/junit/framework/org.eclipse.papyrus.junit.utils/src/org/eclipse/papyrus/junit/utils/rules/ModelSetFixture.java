/**
 * Copyright (c) 2014, 2016, 2019 CEA, Christian W. Damus, and others.
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
 *   Christian W. Damus - bugs 399859, 485220
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 549108
 */
package org.eclipse.papyrus.junit.utils.rules;

import static org.junit.Assert.fail;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.resource.EditingDomainServiceFactory;
import org.eclipse.papyrus.infra.core.resource.ModelMultiException;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ServiceDescriptor;
import org.eclipse.papyrus.infra.core.services.ServiceDescriptor.ServiceTypeKind;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServiceStartKind;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForResourceSet;
import org.junit.runner.Description;


/**
 * This is the ModelSetFixture type. Enjoy.
 */
public class ModelSetFixture extends AbstractModelFixture<TransactionalEditingDomain> {

	public ModelSetFixture() {
		super();
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param additionalFileExtension
	 *            a list of additional file extension to manage
	 * @since 2.3
	 */
	public ModelSetFixture(final List<String> additionalFileExtension) {
		super(additionalFileExtension);
	}


	@Override
	public ModelSet getResourceSet() {
		return (ModelSet) super.getResourceSet();
	}

	@Override
	protected TransactionalEditingDomain createEditingDomain() {
		try {
			ServicesRegistry services = createServiceRegistry();
			return services.getService(ModelSet.class).getTransactionalEditingDomain();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failed to initialize service registry and/or editing domain: " + e.getLocalizedMessage());
			return null; // unreachable
		}
	}

	@Override
	protected void finished(Description description) {
		ResourceSet rset = getEditingDomain().getResourceSet();

		try {
			ServicesRegistry services = ServiceUtilsForResourceSet.getInstance().getServiceRegistry(rset);
			services.disposeRegistry();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			super.finished(description);
		}
	}

	protected ServicesRegistry createServiceRegistry() throws Exception {
		ServicesRegistry result = new ServicesRegistry();

		result.add(ModelSet.class, 10, new ModelSet());

		ServiceDescriptor desc = new ServiceDescriptor(TransactionalEditingDomain.class, EditingDomainServiceFactory.class.getName(), ServiceStartKind.STARTUP, 10, Collections.singletonList(ModelSet.class.getName()));
		desc.setServiceTypeKind(ServiceTypeKind.serviceFactory);
		desc.setClassBundleID(org.eclipse.papyrus.infra.core.Activator.PLUGIN_ID);
		result.add(desc);

		result.startRegistry();

		return result;
	}

	@Override
	protected void didLoadResourceSet() {
		try {
			getResourceSet().loadModels(getModelResourceURI());
		} catch (ModelMultiException e) {
			// Is the problem only a missing model resource?
			Pattern missingResource = Pattern.compile("ResourceException: Resource '.*' does not exist."); //$NON-NLS-1$
			for (Throwable next : e.getExceptions()) {
				if ((next.getMessage() == null) || !missingResource.matcher(next.getMessage()).find()) {
					e.printStackTrace();

					fail("Failed to initialize ModelSet fixture: " + e.getLocalizedMessage());
				}
			}
		}
	}

	public final <S> S tryService(Class<S> serviceType) {
		try {
			ServicesRegistry services = ServiceUtilsForResourceSet.getInstance().getServiceRegistry(getResourceSet());
			return services.getService(serviceType);
		} catch (ServiceException e) {
			// Okay, no such service
			return null; // unreachable
		}
	}

	public final <S> S requireService(Class<S> serviceType) {
		try {
			ServicesRegistry services = ServiceUtilsForResourceSet.getInstance().getServiceRegistry(getResourceSet());
			return services.getService(serviceType);
		} catch (ServiceException e) {
			e.printStackTrace();
			fail("Failed to initialize service registry and/or service: " + e.getLocalizedMessage());
			return null; // unreachable
		}
	}
}
