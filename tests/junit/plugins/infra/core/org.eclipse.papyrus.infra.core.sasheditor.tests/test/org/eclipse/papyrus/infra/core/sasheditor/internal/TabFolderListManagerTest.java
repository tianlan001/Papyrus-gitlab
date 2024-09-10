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

package org.eclipse.papyrus.infra.core.sasheditor.internal;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * Tests for the {@link SashContainerFolderEventsProvider} class.
 * @author cedric dumoulin
 *
 */
public class TabFolderListManagerTest {

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
	 * Test addition of a folder. Check if the folder is added to the list, and event added.
	 * 
	 */
	@Test
	public void testFolderAdded() {

		// Create listManager and folder event provider
		TabFolderListManager listManager = new TabFolderListManager();
		SashContainerFolderEventsProvider folderEventProvider = new SashContainerFolderEventsProvider();
		folderEventProvider.addListener(listManager);
		
		// fire folder added event
		TabFolderPart folder1= new FakeTabFolderPart();
		folderEventProvider.fireFolderCreatedEvent(folder1);
		
		// Check the list
		assertTrue( "folder added", listManager.getFolderList().contains(folder1) );

	}

	/**
	 * Test addition of a folder. Check if the folder is added to the list, and event added.
	 */
	@Test
	public void testFolderRemoved() {
		// Create listManager and folder event provider
		TabFolderListManager listManager = new TabFolderListManager();
		SashContainerFolderEventsProvider folderEventProvider = new SashContainerFolderEventsProvider();
		folderEventProvider.addListener(listManager);
		
		// fire folder added event
		TabFolderPart folder1= new FakeTabFolderPart();
		folderEventProvider.fireFolderCreatedEvent(folder1);
		
		TabFolderPart folder2= new FakeTabFolderPart();
		folderEventProvider.fireFolderCreatedEvent(folder2);
		
		// Check the list
		assertTrue( "folder added", listManager.getFolderList().contains(folder1) );
		assertTrue( "folder added", listManager.getFolderList().contains(folder2) );

		// remove the folder
		folderEventProvider.fireFolderDisposedEvent(folder1);
		assertFalse( "folder removed", listManager.getFolderList().contains(folder1) );
		assertTrue( "folder added", listManager.getFolderList().contains(folder2) );
		
		
	}

}
