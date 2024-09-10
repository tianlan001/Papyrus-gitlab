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

import java.util.Map;

import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IPageModel;
import org.eclipse.papyrus.infra.core.sasheditor.pagesmodel.IModelExp;
import org.eclipse.papyrus.infra.core.sasheditor.pagesmodel.NotFoundException;
import org.eclipse.papyrus.infra.core.sasheditor.pagesmodel.PagesModelException;

import static org.eclipse.papyrus.infra.core.sasheditor.pagesmodel.SashPagesModelFactory.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * @author dumoulin
 *
 */
public class SimpleSashWindowContentProviderUtilsTest {

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
	 * Test method for {@link org.eclipse.papyrus.infra.core.sasheditor.contentprovider.simple.SimpleSashWindowContentProviderUtils#assertConform(org.eclipse.papyrus.infra.core.sasheditor.pagesmodel.IModelExp)}.
	 * @throws PagesModelException 
	 */
	@Test
	public void testAssertConform() throws PagesModelException {
		SimpleSashWindowsContentProvider contentProvider = new SimpleSashWindowsContentProvider();
		SimpleSashWindowContentProviderUtils helper = new SimpleSashWindowContentProviderUtils(contentProvider);
		
		assertNotNull("helper created", helper);
		// Create a query
		IModelExp expr = vSash( folder( "f1", page("p1")), folder( "f2", page("p2")));
		// Try to create the model
		helper.createModel(expr);

		// Try to check model
		helper.assertConform(expr);
	
	}

	/**
	 * Test method for {@link org.eclipse.papyrus.infra.core.sasheditor.contentprovider.simple.SimpleSashWindowContentProviderUtils#createModel(org.eclipse.papyrus.infra.core.sasheditor.pagesmodel.IModelExp)}.
	 * @throws PagesModelException 
	 */
	@Test
	public void testCreateModel() throws PagesModelException {
		SimpleSashWindowsContentProvider contentProvider = new SimpleSashWindowsContentProvider();
		SimpleSashWindowContentProviderUtils helper = new SimpleSashWindowContentProviderUtils(contentProvider);
		
		assertNotNull("helper created", helper);
		// Create a query
		IModelExp expr = vSash( folder( "f1", page("p1")), folder( "f2", page("p2")));
		// Try to create the model
		helper.createModel(expr);
	}

	/**
	 * Test method for {@link org.eclipse.papyrus.infra.core.sasheditor.contentprovider.simple.SimpleSashWindowContentProviderUtils#queryModel(org.eclipse.papyrus.infra.core.sasheditor.pagesmodel.IModelExp)}.
	 * @throws PagesModelException 
	 */
	@Test
	public void testQueryModel() throws PagesModelException {
		SimpleSashWindowsContentProvider contentProvider = new SimpleSashWindowsContentProvider();
		SimpleSashWindowContentProviderUtils helper = new SimpleSashWindowContentProviderUtils(contentProvider);
		
		assertNotNull("helper created", helper);
		// Create a query
		IModelExp expr = vSash( folder( "f1", page("p1")), folder( "f2", page("p2")));
		// Try to create the model
		helper.createModel(expr);
		
		// Query model
		Map<String, Object> res = helper.queryModel(expr);
		assertNotNull("found f1", res.get("f1"));
		assertTrue("right type", res.get("f1") instanceof TabFolderModel );

		assertNotNull("found f2", res.get("f2"));
		assertTrue("right type", res.get("f2") instanceof TabFolderModel );

		assertNotNull("found p2", res.get("p2"));
		assertTrue("right type", res.get("p2") instanceof IPageModel );
	}
	
	/**
	 * Test method for {@link SimpleSashWindowContentProviderUtils#lookupPageByName(String)}.
	 * @throws PagesModelException 
	 */
	@Test
	public void testLookupPageByName() throws PagesModelException {
		SimpleSashWindowsContentProvider contentProvider = new SimpleSashWindowsContentProvider();
		SimpleSashWindowContentProviderUtils helper = new SimpleSashWindowContentProviderUtils(contentProvider);
		
		assertNotNull("helper created", helper);
		// Create a query
		IModelExp expr = vSash( folder( "f1", page("p1"), page("p2")), folder( "f2", page("p3"), page("p4"), page("p5")));
		// Try to create the model
		helper.createModel(expr);
		
		// Do tests
		assertNotNull("p1 found", helper.lookupPageByName("p1"));
		assertNotNull("p1 found", helper.lookupPageByName("p4"));
	}
	
	
	/**
	 * Test method for {@link SimpleSashWindowContentProviderUtils#createNewPage(String)}.
	 * @throws PagesModelException 
	 */
	@Test
	public void testCreateNewPage() throws PagesModelException {
		SimpleSashWindowsContentProvider contentProvider = new SimpleSashWindowsContentProvider();
		SimpleSashWindowContentProviderUtils helper = new SimpleSashWindowContentProviderUtils(contentProvider);
		
		assertNotNull("helper created", helper);
		// Create a query
		IModelExp expr = vSash( folder( "f1", page("p1"), page("p2")), folder( "f2", page("p3"), page("p4"), page("p5")));
		// Try to create the model
		helper.createModel(expr);
		
		// Do Tests
		IPageModel newPage = helper.createNewPage("newPage");
		assertNotNull("page found", helper.lookupPageByName("p4"));
		assertNotNull("page found", helper.lookupPageByName("newPage"));
		
	}

	/**
	 * Test method for {@link SimpleSashWindowContentProviderUtils#createNewPage(String)}.
	 * @throws PagesModelException 
	 */
	@Test
	public void testRemovePageByName() throws PagesModelException {
		SimpleSashWindowsContentProvider contentProvider = new SimpleSashWindowsContentProvider();
		SimpleSashWindowContentProviderUtils helper = new SimpleSashWindowContentProviderUtils(contentProvider);
		
		assertNotNull("helper created", helper);
		// Create a query
		IModelExp expr = vSash( folder( "f1", page("p1"), page("p2")), folder( "f2", page("p3"), page("p4"), page("p5")));
		// Try to create the model
		helper.createModel(expr);
		
		// Do Tests
		helper.removePage("p2");
		assertNotNull("page found", helper.lookupPageByName("p4"));
		try {
			helper.lookupPageByName("p2");
			fail("Page is removed");
		}
		catch (NotFoundException e) {
			// Exception is expected
		}
		
	}

}
