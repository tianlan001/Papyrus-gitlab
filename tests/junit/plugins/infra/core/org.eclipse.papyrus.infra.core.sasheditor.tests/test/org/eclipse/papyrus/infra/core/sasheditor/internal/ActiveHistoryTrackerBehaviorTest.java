/*****************************************************************************
 * Copyright (c) 2009-2013 Cedric Dumoulin.
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
package org.eclipse.papyrus.infra.core.sasheditor.internal;

import static org.eclipse.papyrus.infra.core.sasheditor.pagesmodel.SashPagesModelFactory.folder;
import static org.eclipse.papyrus.infra.core.sasheditor.pagesmodel.SashPagesModelFactory.page;
import static org.eclipse.papyrus.infra.core.sasheditor.pagesmodel.SashPagesModelFactory.vSash;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IPageModel;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.ISashWindowsContentProvider;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.simple.SimpleSashWindowContentProviderUtils;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.simple.SimpleSashWindowsContentProvider;
import org.eclipse.papyrus.infra.core.sasheditor.editor.MessagePartModel;
import org.eclipse.papyrus.infra.core.sasheditor.pagesmodel.IModelExp;
import org.eclipse.papyrus.infra.core.sasheditor.pagesmodel.PagesModelException;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Before;
import org.junit.Test;


/**
 * Run as normal test.
 *
 * @author cedric dumoulin
 *
 */
public class ActiveHistoryTrackerBehaviorTest {


	protected Display display;

	/**
	 * Constructor.
	 *
	 * @param name
	 */
	public ActiveHistoryTrackerBehaviorTest() {
	}

	/**
	 * @see junit.framework.TestCase#setUp()
	 * @throws java.lang.Exception
	 *
	 */
	@Before
	public void setUp() throws Exception {
		display = Display.getCurrent();
		if(display == null) {
			display = new Display();
		}
	}

	/**
	 * Create a {@link SashWindowsContainer} to test. Initialize it with provided {@link ISashWindowsContentProvider}.
	 *
	 * @param contentProvider
	 * @return
	 */
	protected SashWindowsContainer createSashWindowsContainer(ISashWindowsContentProvider contentProvider) {
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());

		//
		//new ShellEditor(shell);
		SashWindowsContainer sashWindowContainer = new SashWindowsContainer();

		sashWindowContainer.setContentProvider(contentProvider);

		sashWindowContainer.createPartControl(shell);
		//		shell.open();
		return sashWindowContainer;
	}

	/**
	 * Create a contentProvider.
	 *
	 * @return
	 */
	protected ISashWindowsContentProvider createContentProvider() {
		SimpleSashWindowsContentProvider contentProvider = new SimpleSashWindowsContentProvider();

		// Create pages and add them to the default folder
		List<IPageModel> models = new ArrayList<IPageModel>();
		for(int i = 0; i < 8; i++) {
			IPageModel newModel = new MessagePartModel("model" + i);
			contentProvider.addPage(newModel);
			models.add(newModel);
		}

		return contentProvider;
	}




	/**
	 * Test history of active pages when pages are closed.
	 * 
	 * @throws PagesModelException 
	 * 
	 */
	@Test
	public void testActivePageHistoryWhenPageIsClosed() throws PagesModelException {

		// Create content provider
		SimpleSashWindowsContentProvider contentProvider = new SimpleSashWindowsContentProvider();
		SimpleSashWindowContentProviderUtils helper = new SimpleSashWindowContentProviderUtils(contentProvider);

		// define how to populate contentProvider
		IModelExp expr = vSash(folder("f1", page("p11"), page("p12"), page("p13"), page("p14"), page("p15")), 
				                 folder("f2", page("p21"), page("p22"), page("p23")));
		// Try to create the model
		helper.createModel(expr);


		// Create the SashWindowsContainer
		SashWindowsContainer container = createSashWindowsContainer(contentProvider);

		// creates Pages in the sashContainer 
		container.refreshTabs();

		// Navigate pages
		container.selectPage(container.lookupModelPage(helper.lookupPageByName("p11")));
		container.selectPage(container.lookupModelPage(helper.lookupPageByName("p13")));
		container.selectPage(container.lookupModelPage(helper.lookupPageByName("p15")));
		container.selectPage(container.lookupModelPage(helper.lookupPageByName("p12")));
		container.selectPage(container.lookupModelPage(helper.lookupPageByName("p14")));
		container.selectPage(container.lookupModelPage(helper.lookupPageByName("p21")));
		container.selectPage(container.lookupModelPage(helper.lookupPageByName("p23")));
		container.selectPage(container.lookupModelPage(helper.lookupPageByName("p22")));
		

		// Now close and check next active pages
		
		
		// Now test the lifeCycle by closing n turn
		helper.removePage("p22");
		container.refreshTabs();
		container.getActiveSashWindowsPage();
		assertEquals( "expected active page", "p23", ((MessagePartModel)container.getActiveSashWindowsPage().getRawModel()).getTabTitle());
		assertEquals( "expected active page", helper.lookupPageByName("p23"), container.getActiveSashWindowsPage().getRawModel());

		helper.removePage("p23");
		container.refreshTabs();
		container.getActiveSashWindowsPage();
		assertEquals( "expected active page", "p21", ((MessagePartModel)container.getActiveSashWindowsPage().getRawModel()).getTabTitle());

		helper.removePage("p21");
		container.refreshTabs();
		container.getActiveSashWindowsPage();
		assertEquals( "expected active page", "p14", ((MessagePartModel)container.getActiveSashWindowsPage().getRawModel()).getTabTitle());

		helper.removePage("p14");
		container.refreshTabs();
		container.getActiveSashWindowsPage();
		assertEquals( "expected active page", "p12", ((MessagePartModel)container.getActiveSashWindowsPage().getRawModel()).getTabTitle());

		helper.removePage("p12");
		container.refreshTabs();
		container.getActiveSashWindowsPage();
		assertEquals( "expected active page", "p15", ((MessagePartModel)container.getActiveSashWindowsPage().getRawModel()).getTabTitle());

		helper.removePage("p15");
		container.refreshTabs();
		container.getActiveSashWindowsPage();
		assertEquals( "expected active page", "p13", ((MessagePartModel)container.getActiveSashWindowsPage().getRawModel()).getTabTitle());

	}


}
