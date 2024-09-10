/*****************************************************************************
 * Copyright (c) 2012, 2014, 2019 LIFL, CEA, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  LIFL - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 431953 (fix start-up of selective services to require only their dependencies)
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 550321
 */
package org.eclipse.papyrus.infra.emf.utils;

import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.emf.commands.AddToResourceCommand;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author dumoulin
 *
 */
public class ServiceUtilsForResourceTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForResource#getInstance()}.
	 */
	@Test
	public void testGetInstance() {

		assertNotNull("Instance ready", ServiceUtilsForResource.getInstance());
	}

	/**
	 * Test method for {@link org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForResource#getServiceRegistry(org.eclipse.emf.ecore.resource.Resource)}.
	 * 
	 * @throws ServiceException
	 */
	@Test
	public void testGetServiceRegistryResource() throws ServiceException {


		// Create services
		ServicesRegistry servicesRegistry = new ServicesRegistry();

		ModelSet modelSet = new ModelSet();
		URI uri = URI.createURI("temp.ecore");

		// Register the default resource factory -- only needed for stand-alone!
		modelSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
				Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());

		Resource resource = modelSet.createResource(uri);
		assertNotNull("resource created", resource);

		EClass obj1 = EcoreFactory.eINSTANCE.createEClass();
		EPackage pack2 = EcoreFactory.eINSTANCE.createEPackage();
		EClass obj2 = EcoreFactory.eINSTANCE.createEClass();
		pack2.getEClassifiers().add(obj2);

		final TransactionalEditingDomain transactionalEditingDomain = modelSet.getTransactionalEditingDomain();
		final Command addRootCommand1 = new GMFtoEMFCommandWrapper(new AddToResourceCommand(transactionalEditingDomain, resource, obj1));
		addRootCommand1.execute();
		final Command addRootCommand2 = new GMFtoEMFCommandWrapper(new AddToResourceCommand(transactionalEditingDomain, resource, pack2));
		addRootCommand2.execute();

		servicesRegistry.add(ModelSet.class, 10, modelSet);

		servicesRegistry.startServicesByClassKeys(ModelSet.class);

		// Check registry
		assertNotNull("service ModelSet", servicesRegistry.getService(ModelSet.class));
		assertNotNull("ModelSet service not correctly initialized", ServiceUtils.getInstance().getModelSet(servicesRegistry));

		// Do tests
		ServicesRegistry r2 = ServiceUtilsForResource.getInstance().getServiceRegistry(obj1.eResource());
		assertNotNull("found from obj1", r2);

		r2 = ServiceUtilsForResource.getInstance().getServiceRegistry(obj2.eResource());
		assertNotNull("found from obj2", r2);

		r2 = ServiceUtilsForResource.getInstance().getServiceRegistry(pack2.eResource());
		assertNotNull("found from pack2", r2);
	}

}
