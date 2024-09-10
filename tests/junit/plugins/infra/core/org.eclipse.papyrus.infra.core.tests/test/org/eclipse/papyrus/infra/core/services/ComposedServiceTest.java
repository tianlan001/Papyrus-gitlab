/*****************************************************************************
 * Copyright (c) 2010, 2016 LIFL, CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   LIFL - Initial API and implementation
 *   Christian W. Damus - bug 488791
 *   
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.papyrus.infra.core.services.ServicesRegistryTest.TestServiceDescriptor;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ComposedServiceTest extends AbstractPapyrusTest {

	private FakeComposedService masterService;


	@Before
	public void setUp() throws Exception {
		masterService = new FakeComposedService();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddServicePart() {
		FakeComposedServicePartA partA = new FakeComposedServicePartA();
		FakeComposedServicePartB partB = new FakeComposedServicePartB();

		masterService.addServicePart(partA);
		masterService.addServicePart(partB);

		List<?> addedParts = masterService.getRegisteredServices();

		assertTrue("service contains partA", addedParts.contains(partA));
		assertTrue("service contains partB", addedParts.contains(partB));
	}

	@Test
	public void testRemoveServicePart() {
		FakeComposedServicePartA partA = new FakeComposedServicePartA();
		FakeComposedServicePartB partB = new FakeComposedServicePartB();

		masterService.addServicePart(partA);
		masterService.addServicePart(partB);

		List<?> addedParts = masterService.getRegisteredServices();

		assertTrue("service contains partA", addedParts.contains(partA));
		assertTrue("service contains partB", addedParts.contains(partB));

		masterService.removeServicePart(partA);
		assertFalse("service contains partA", addedParts.contains(partA));
		assertTrue("service contains partB", addedParts.contains(partB));

	}

	/**
	 * Test that the parts registered to the ServiceRegistry are automaticly
	 * registered to there associated main service when registry is started.
	 * 
	 * @throws ServiceException
	 */
	@Test
	public void testPartAutomaticRegistration() throws ServiceException {

		ServicesRegistry serviceRegistry = new ServicesRegistry();

		// Register services
		serviceRegistry.add(new ComposedServiceDescriptor());
		serviceRegistry.add(new ServicePartADescriptor());
		serviceRegistry.add(new ServicePartBDescriptor());

		// start registry
		serviceRegistry.startRegistry();

		// Check if connected
		FakeComposedService masterService = serviceRegistry.getService(FakeComposedService.class);
		FakeComposedServicePartA partA = serviceRegistry.getService(FakeComposedServicePartA.class);
		FakeComposedServicePartB partB = serviceRegistry.getService(FakeComposedServicePartB.class);

		List<?> addedParts = masterService.getRegisteredServices();

		assertNotNull("master service found", masterService);
		assertTrue("service contains partA", addedParts.contains(partA));
		assertTrue("service contains partB", addedParts.contains(partB));


	}


	/* ***************************** */

	/**
	 * A descriptor
	 * 
	 * @author dumoulin
	 *
	 */
	public class ServicePartADescriptor extends TestServiceDescriptor {


		public ServicePartADescriptor() {
			super(FakeComposedServicePartA.class, FakeComposedServicePartA.class.getName(), ServiceStartKind.STARTUP, 1);
		}
	}


	/**
	 * A descriptor
	 * 
	 * @author dumoulin
	 *
	 */
	public class ServicePartBDescriptor extends TestServiceDescriptor {


		public ServicePartBDescriptor() {
			super(FakeComposedServicePartB.class, FakeComposedServicePartB.class.getName(), ServiceStartKind.STARTUP, 1);
		}
	}

	/**
	 * A descriptor
	 * 
	 * @author dumoulin
	 *
	 */
	public class ComposedServiceDescriptor extends TestServiceDescriptor {


		public ComposedServiceDescriptor() {
			super(FakeComposedService.class, FakeComposedService.class.getName(), ServiceStartKind.STARTUP, 1);
		}
	}


}
