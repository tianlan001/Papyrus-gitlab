/*****************************************************************************
 * Copyright (c) 2009, 2016 CEA LIST, LIFL, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IAbstractPanelModel;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.ISashPanelModel;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.ITabFolderModel;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.IPageModelFactory;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.internal.DiContentProviderTest.MyDIContentProvider;
import org.eclipse.papyrus.infra.core.sashwindows.di.AbstractPanel;
import org.eclipse.papyrus.infra.core.sashwindows.di.DiFactory;
import org.eclipse.papyrus.infra.core.sashwindows.di.PageRef;
import org.eclipse.papyrus.infra.core.sashwindows.di.SashWindowsMngr;
import org.eclipse.papyrus.infra.core.sashwindows.di.impl.TabFolderImpl;
import org.eclipse.papyrus.infra.core.sashwindows.di.util.DiUtils;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.swt.SWT;
import org.junit.Before;
import org.junit.Test;


/**
 * @author dumoulin
 */
public class PageManagerImplTest extends AbstractPapyrusTest {

	/**
	 * The {@link PageManagerImpl} under test.
	 */
	protected PageManagerImpl pageMngr;

	/**
	 * The associated {@link DiContentProvider}. Not tested, but used to check events.
	 */
	protected MyDIContentProvider contentProvider;

	/**
	 * @see junit.framework.TestCase#setUp()
	 *
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		SashWindowsMngr diSashModel = DiUtils.createDefaultSashWindowsMngr();

		// Fix bug to match refactoring in Bug 429239. The current implementation computes allPages() dynamically,
		// and doesn't support addPage() and removePage() anymore
		// Because this test is used in a different context, where allPages() cannot be computed dynamically,
		// we need to create a specific setup
		diSashModel.setPageList(DiFactory.eINSTANCE.createPageList());
		ContentChangedEventProvider eventProvider = new ContentChangedEventProvider(diSashModel);
		pageMngr = new PageManagerImpl(diSashModel, eventProvider) {
			@Override
			protected boolean isLegacyMode() {
				return true;
			}
		};

		IPageModelFactory pageModelFactory = new FakePageModelFactory();
		contentProvider = new MyDIContentProvider(diSashModel.getSashModel(), pageModelFactory, eventProvider);

	}

	/**
	 * Lookup for a folder in the SashModel. Return the first folder found.
	 *
	 * @return
	 */
	private ITabFolderModel lookupFolderModel() {
		if (contentProvider == null) {
			return null;
		}

		Object rawModel = contentProvider.getRootModel();
		// Create the requested model suitable for the folder.
		// It is possible (but not recommended) to create several models for the same object.
		// We can avoid this by using a Map of created models.
		IAbstractPanelModel panelModel = contentProvider.createChildSashModel(rawModel);

		return lookupFolderModel(panelModel);
	}

	/**
	 * Recursively search in sash models for a FolderModel.
	 * Return the first encountered folder.
	 *
	 * @param panelModel
	 * @return
	 */
	private ITabFolderModel lookupFolderModel(IAbstractPanelModel panelModel) {

		if (panelModel instanceof ITabFolderModel) {
			return (ITabFolderModel) panelModel;
		} else {
			ISashPanelModel sashModel = (ISashPanelModel) panelModel;
			// Iterate on children
			for (Object child : sashModel.getChildren()) {
				IAbstractPanelModel childModel = contentProvider.createChildSashModel(child);
				ITabFolderModel res = lookupFolderModel(childModel);
				if (res != null) {
					return res;
				}
			}
		}
		// Not found
		return null;
	}


	/**
	 * Test method for
	 * {@link org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.internal.PageMngrImpl#addPage(org.eclipse.emf.ecore.EObject)}.
	 */
	@Test
	public void testAddPage() {
		// A listener on change event.
		ContentChangeListener changeListener = new ContentChangeListener();

		// Set change listener
		contentProvider.getContentChangedEventProvider().addListener(changeListener);

		// Add identifiers
		// Use Object as identifiers.
		List<Object> identifiers = new ArrayList<Object>();
		// Add 10 folders
		for (int i = 0; i < 10; i++) {
			// reset change count
			changeListener.reset();
			// Add Editor
			Object id = new Object();
			identifiers.add(id);
			pageMngr.addPage(id);

			// Check no fired events
			assertEquals("One event fired", 0, changeListener.getChangeCount());
		}

		// Check if pages are in PageList
		assertEquals("all pages added", 10, pageMngr.allPages().size());

	}

	/**
	 * Test method for {@link org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.internal.PageMngrImpl#allPages()}.
	 */
	@Test
	public void testAllPages() {
		// A listener on change event.
		ContentChangeListener changeListener = new ContentChangeListener();

		// Set change listener
		contentProvider.getContentChangedEventProvider().addListener(changeListener);

		// Add identifiers
		// Use Object as identifiers.
		List<Object> identifiers = new ArrayList<Object>();
		// Add 10 folders
		for (int i = 0; i < 10; i++) {
			// reset change count
			changeListener.reset();
			// Add Editor
			Object id = new Object();
			identifiers.add(id);
			pageMngr.openPage(id);

			// Check fired events
			assertEquals("One event fired", 1, changeListener.getChangeCount());
		}
		// Check if pages are in PageList
		assertEquals("all pages found", 10, pageMngr.allPages().size());
	}

	/**
	 * Test method for
	 * {@link org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.internal.PageMngrImpl#closePage(org.eclipse.emf.ecore.EObject)}.
	 */
	@Test
	public void testClosePage() {
		// A listener on change event.
		ContentChangeListener changeListener = new ContentChangeListener();

		// Set change listener
		contentProvider.getContentChangedEventProvider().addListener(changeListener);

		// Add identifiers
		// Use Object as identifiers.
		List<Object> identifiers = new ArrayList<Object>();
		// Add 10 folders
		for (int i = 0; i < 10; i++) {
			// reset change count
			changeListener.reset();
			// Add Editor
			Object id = new Object();
			identifiers.add(id);
			pageMngr.openPage(id);

			// Check fired events
			assertEquals("One event fired", 1, changeListener.getChangeCount());
		}
		// Check if pages are in PageList
		assertEquals("all pages added", 10, pageMngr.allPages().size());

		// Close page
		changeListener.reset();
		pageMngr.closePage(identifiers.get(3));

		// Check fired events
		assertEquals("One event fired", 1, changeListener.getChangeCount());

		// Check page still in pages
		assertEquals("all pages still in list", 10, pageMngr.allPages().size());

		// Check if pages are in SashStructure
		PageRef pageRef = contentProvider.getDiSashModel().lookupPage(identifiers.get(3));
		assertNull("Page removed from sashStructure ", pageRef);

	}

	/**
	 * Test method for {@link org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.internal.PageMngrImpl#closeAllOpenedPages())}.
	 */
	@Test
	public void testCloseAllOpenedPages() {
		// A listener on change event.
		ContentChangeListener changeListener = new ContentChangeListener();

		// Set change listener
		contentProvider.getContentChangedEventProvider().addListener(changeListener);

		// Add identifiers
		// Use Object as identifiers.
		List<Object> identifiers = new ArrayList<Object>();
		// Add 10 folders
		for (int i = 0; i < 10; i++) {
			// reset change count
			changeListener.reset();
			// Add Editor
			Object id = new Object();
			identifiers.add(id);
			pageMngr.openPage(id);

			// Check fired events
			assertEquals("One event fired", 1, changeListener.getChangeCount());
		}
		// Check if pages are in PageList
		assertEquals("all pages added", 10, pageMngr.allPages().size());

		// Close page
		changeListener.reset();
		pageMngr.closeAllOpenedPages();

		// Check fired events
		assertEquals("One event fired", 1, changeListener.getChangeCount());

		// Check page still in pages
		assertEquals("all pages still in list", 10, pageMngr.allPages().size());

		// Check if pages are in SashStructure
		for (int i = 0; i < 10; i++) {
			PageRef pageRef = contentProvider.getDiSashModel().lookupPage(identifiers.get(i));
			assertNull("Page " + i + " removed from sashStructure ", pageRef);
		}

	}

	/**
	 * Test method for {@link org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.internal.PageMngrImpl#closeAllOpenedPages())}.
	 */
	@Test
	public void testCloseOtherOpenedPages() {
		// A listener on change event.
		ContentChangeListener changeListener = new ContentChangeListener();

		// Set change listener
		contentProvider.getContentChangedEventProvider().addListener(changeListener);

		// Add identifiers
		// Use Object as identifiers.
		List<Object> identifiers = new ArrayList<Object>();
		// Add 10 folders
		for (int i = 0; i < 10; i++) {
			// reset change count
			changeListener.reset();
			// Add Editor
			Object id = new Object();
			identifiers.add(id);
			pageMngr.openPage(id);

			// Check fired events
			assertEquals("One event fired", 1, changeListener.getChangeCount());
		}
		// Check if pages are in PageList
		assertEquals("all pages added", 10, pageMngr.allPages().size());

		// Close page
		changeListener.reset();
		pageMngr.closeOtherPages(identifiers.get(3));

		// Check fired events
		assertEquals("One event fired", 1, changeListener.getChangeCount());

		// Check page still in pages
		assertEquals("all pages still in list", 10, pageMngr.allPages().size());

		// Check if pages are in SashStructure
		for (int i = 0; i < 10; i++) {
			PageRef pageRef = contentProvider.getDiSashModel().lookupPage(identifiers.get(i));
			if (i != 3) {
				assertNull("Page " + i + " removed from sashStructure ", pageRef);
			} else {
				assertNotNull("Page " + i + " not removed from sashStructure ", pageRef);
			}
		}

	}

	/**
	 * Test method for
	 * {@link org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.internal.PageMngrImpl#openPage(org.eclipse.emf.ecore.EObject)}.
	 */
	@Test
	public void testOpenPage() {
		// A listener on change event.
		ContentChangeListener changeListener = new ContentChangeListener();

		// Set change listener
		contentProvider.getContentChangedEventProvider().addListener(changeListener);

		// Add identifiers
		// Use Object as identifiers.
		List<Object> identifiers = new ArrayList<Object>();
		// Add 10 folders
		for (int i = 0; i < 10; i++) {
			// reset change count
			changeListener.reset();
			// Add Editor
			Object id = new Object();
			identifiers.add(id);
			pageMngr.openPage(id);

			// Check fired events
			assertEquals("One event fired", 1, changeListener.getChangeCount());
		}

		// Check if pages are in PageList
		assertEquals("all pages added", 10, pageMngr.allPages().size());

		// Check if pages are in SashStructure
		PageRef pageRef = contentProvider.getDiSashModel().lookupPage(identifiers.get(3));
		assertNotNull("Page exist in sashStructure ", pageRef);
		assertSame("found the right page", identifiers.get(3), pageRef.getPageIdentifier());

	}

	/**
	 * Test method for
	 * {@link org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.internal.PageMngrImpl#removePage(org.eclipse.emf.ecore.EObject)}.
	 */
	@Test
	public void testRemovePage() {
		// A listener on change event.
		ContentChangeListener changeListener = new ContentChangeListener();

		// Set change listener
		contentProvider.getContentChangedEventProvider().addListener(changeListener);

		// Add identifiers
		// Use Object as identifiers.
		List<Object> identifiers = new ArrayList<Object>();
		// Add 10 folders
		for (int i = 0; i < 10; i++) {
			// reset change count
			changeListener.reset();
			// Add Editor
			Object id = new Object();
			identifiers.add(id);
			pageMngr.openPage(id);

			// Check fired events
			assertEquals("One event fired", 1, changeListener.getChangeCount());
		}
		// Check if pages are in PageList
		assertEquals("all pages added", 10, pageMngr.allPages().size());
		// Check if page id is in page list
		assertTrue("check page id is added to page list", pageMngr.allPages().contains(identifiers.get(3)));

		// Close page
		changeListener.reset();
		pageMngr.removePage(identifiers.get(3));

		// Check fired events
		assertEquals("One event fired", 1, changeListener.getChangeCount());

		// Check page still in pages
		assertEquals("page remove from list", 9, pageMngr.allPages().size());

		// Check removed from page list
		assertFalse("check removed from page list", pageMngr.allPages().contains(identifiers.get(3)));

		// Check if pages are in SashStructure
		PageRef pageRef = contentProvider.getDiSashModel().lookupPage(identifiers.get(3));
		assertNull("Page removed from sashStructure ", pageRef);
	}

	/**
	 * Check if closing the last page in a second tabfolder work propoerly.
	 * Create 3 editors, move one in a new folder, then remove the moved one.
	 * The new folder should automatically be removed.
	 *
	 */
	@Test
	public void testCloseLastPageOfTabFolder() {
		// A listener on change event.
		ContentChangeListener changeListener = new ContentChangeListener();

		// Set change listener
		contentProvider.getContentChangedEventProvider().addListener(changeListener);

		// Create 3 editors, move one in another table
		// Then remove the moved one.

		// Add identifiers
		// Use Object as identifiers.
		List<Object> identifiers = new ArrayList<Object>();
		// Add pages
		int pageCount = 3;
		for (int i = 0; i < pageCount; i++) {
			// Add Editor
			Object id = new Object();
			identifiers.add(id);
			pageMngr.openPage(id);
		}
		// Check if pages are in PageList
		assertEquals("all pages added", pageCount, pageMngr.allPages().size());


		// Move the last page
		int movedPageIndex = 0;
		ITabFolderModel tabFolder = lookupFolderModel();
		assertTrue("Moved page index  is valid", movedPageIndex < pageCount);

		contentProvider.createFolder(tabFolder, movedPageIndex, tabFolder, SWT.TOP);
		// check if the folder is created
		// List<?> folders = getAllFolders(contentProvider.getDiSashModel());
		// assertEquals("additional folder is removed", 2, folders.size());

		// Close page
		pageMngr.closePage(identifiers.get(movedPageIndex));

		// Check the resulting model: we should have one folder with two page.
		// folders = getAllFolders(contentProvider.getDiSashModel());
		// assertEquals("additional folder is removed", 1, folders.size());

		List<AbstractPanel> children = contentProvider.getDiSashModel().getWindows().get(0).getChildren();
		assertEquals("Windows has one child", 1, children.size());
		assertEquals("Windows child is the tabfolder", TabFolderImpl.class, children.get(0).getClass());

		// Check if pages are in SashStructure
		PageRef pageRef = contentProvider.getDiSashModel().lookupPage(identifiers.get(movedPageIndex));
		assertNull("Page removed from sashStructure ", pageRef);

	}

}
