/*****************************************************************************
 * Copyright (c) 2013 Cedric Dumoulin.
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.sasheditor.contentprovider.simple;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * @author dumoulin
 *
 */
public class SimpleSashWindowContainerTestFacadeTest {

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
	 * Test method for {@link org.eclipse.papyrus.infra.core.sasheditor.contentprovider.simple.SimpleSashWindowContainerTestFacade#SimpleSashWindowContainerTestFacade()}.
	 */
	@Test
	public void testSimpleSashWindowContainerTestFacade() {
		SimpleSashWindowContainerTestFacade facade = new SimpleSashWindowContainerTestFacade();
		
		assertNotNull("Facade created", facade);
	}

	/**
	 * Test method for {@link org.eclipse.papyrus.infra.core.sasheditor.contentprovider.simple.SimpleSashWindowContainerTestFacade#getSashContainer()}.
	 */
	@Test
	public void testGetSashContainer() {
		SimpleSashWindowContainerTestFacade facade = new SimpleSashWindowContainerTestFacade();
		
		assertNotNull("Facade created", facade);
		assertNotNull("container created", facade.getISashWindowsContainer() );
	}

	/**
	 * Test method for {@link org.eclipse.papyrus.infra.core.sasheditor.contentprovider.simple.SimpleSashWindowContainerTestFacade#getIContentProvider()}.
	 */
	@Test
	public void testGetIContentProvider() {
		SimpleSashWindowContainerTestFacade facade = new SimpleSashWindowContainerTestFacade();
		
		assertNotNull("Facade created", facade);
		assertNotNull("contentProvider created", facade.getIContentProvider() );
	}

	/**
	 * Test method for {@link org.eclipse.papyrus.infra.core.sasheditor.contentprovider.simple.SimpleSashWindowContainerTestFacade#dispose()}.
	 */
	@Test
	public void testDispose() {
		SimpleSashWindowContainerTestFacade facade = new SimpleSashWindowContainerTestFacade();
		
		assertNotNull("Facade created", facade);
		
		facade.dispose();
		assertNull("contentProvider deleted", facade.getIContentProvider() );
		assertNull("container deleted", facade.getISashWindowsContainer() );
		
	}

}
