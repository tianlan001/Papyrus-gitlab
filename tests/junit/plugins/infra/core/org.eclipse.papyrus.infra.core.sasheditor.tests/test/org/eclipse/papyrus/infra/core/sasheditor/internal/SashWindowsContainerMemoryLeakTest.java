/*****************************************************************************
 * Copyright (c) 2012 Cedric Dumoulin.
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

import static org.eclipse.papyrus.infra.core.sasheditor.tests.utils.memoryleak.MemoryLeakUtil.assertIsGarbageCollected;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IPageModel;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.simple.SimpleSashWindowsContentProvider;
import org.eclipse.papyrus.infra.core.sasheditor.tests.texteditor.FakeMultiSashPageEditor;
import org.eclipse.papyrus.infra.core.sasheditor.tests.texteditor.TestTextEditor;
import org.eclipse.papyrus.infra.core.sasheditor.tests.texteditor.TextEditorPartModel;
import org.eclipse.papyrus.infra.core.sasheditor.tests.utils.trace.ITraceRecords;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests to check  memory leak and dispose() calls.
 * 
 * The test do not work because the {@link TestTextEditor} don not dispose properly. We need to find
 * how to dispose it properly, or use another editor.
 * 
 * @author Cedric Dumoulin
 *
 */
@Ignore
public class SashWindowsContainerMemoryLeakTest {

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
		
		if (PlatformUI.isWorkbenchRunning()) {

			// Close all remaining opened editors
			IWorkbenchWindow window=PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			IWorkbenchPage page = window.getActivePage();
			page.closeAllEditors(false);
		}
	}
	
	/**
	 * Test if calling dispose on TestEditor do not induce memory leak.
	 * 
	 * 	 * @throws PartInitException 
	 * @throws Exception 
	 */
	@Test
	public void testDisposeOnTestEditor() throws Exception {
		
		TestTextEditor editor = TestTextEditor.openEditor();
		
		TestTextEditor.closeEditor(editor);
		// Check memory leak
		WeakReference<IEditorPart> ref = new WeakReference<IEditorPart>(editor);
		editor = null;
		assertIsGarbageCollected(ref, 10*1000);

		
	}
	
	
	/**
	 * Test the call of dispose() on nestedEditor when the editor is 
	 * closed independently
	 * 
	 * 	 * @throws PartInitException 
	 * @throws Exception 
	 */
	@Test
	@Ignore
	public void testDisposeCallOnNestedEditorRemoval() throws Exception {
		// Create 
		SimpleSashWindowsContentProvider contentProvider = new SimpleSashWindowsContentProvider();
	
		// Create pages and add them to the default folder
		int pageCount = 3;
		List<IPageModel> models = new ArrayList<IPageModel>();
		for(int i = 0; i < pageCount; i++) {
			IPageModel newModel = new TextEditorPartModel("model" + i, true);
			contentProvider.addPage(newModel);
			models.add(newModel);
		}
	
		
		FakeMultiSashPageEditor editor = FakeMultiSashPageEditor.openEditor(contentProvider);
		// Get the container
		SashWindowsContainer container = editor.getSashWindowsContainer();
		
		// Check if nested editor creation work
		IEditorPart activeNestedEditor = editor.getActiveEditor();
		assertEquals("nested editor is of right type", TestTextEditor.class, activeNestedEditor.getClass());

//		// Close the editor by removing its model
//		contentProvider.removePage(models.get(0));	
//		// Refresh the container: this should close the editor
//		container.refreshTabs();
//		assertNull( "No more active editor", container.getActiveEditor() );
//		// Check if dispose() is called
//		ITraceRecords traces = ((TextEditorPartModel)models.get(0)).getTraceRecords();
//		assertTrue("dispose is called", traces.contains(null, "dispose"));		
		
		// Close each container and check dispose call
		for(IPageModel model : models) {
			// Close the editor by removing its model
			contentProvider.removePage(model);
			// Refresh the container: this should close the editor
			container.refreshTabs();
			// Check if dispose() is called
			ITraceRecords traces = ((TextEditorPartModel)model).getTraceRecords();
			assertTrue("dispose is called", traces.contains(null, "dispose"));		
			
		}
		
		// Check memory leak
		editor = null;
		WeakReference<IEditorPart> ref = new WeakReference<IEditorPart>(activeNestedEditor);
		activeNestedEditor = null;
		assertIsGarbageCollected(ref, 10*1000);
		
		FakeMultiSashPageEditor.closeEditor(editor);

	}

	/**
		 * Test the call of dispose() on nestedEditor when the main editor is 
		 * closed. Normally, each nested editor should be disposed.
		 * 
		 * 	 * @throws PartInitException 
		 */
		@Test
		@Ignore
		public void testDisposeCallOnMainEditorClose() throws PartInitException {
			// Create 
			SimpleSashWindowsContentProvider contentProvider = new SimpleSashWindowsContentProvider();
		
			// Create pages and add them to the default folder
			int pageCount = 3;
			List<IPageModel> models = new ArrayList<IPageModel>();
			for(int i = 0; i < pageCount; i++) {
				IPageModel newModel = new TextEditorPartModel("model" + i, true);
				contentProvider.addPage(newModel);
				models.add(newModel);
			}
		
			
			FakeMultiSashPageEditor editor = FakeMultiSashPageEditor.openEditor(contentProvider);
			
			// Check if nested editor creation work
			IEditorPart activeNestedEditor = editor.getActiveEditor();
			assertEquals("nested editor is of right type", TestTextEditor.class, activeNestedEditor.getClass());
	
			// Close the main editor
			FakeMultiSashPageEditor.closeEditor(editor);
			editor = null;
			
			// for each container  check dispose call
			assertEquals("list of model is alive", pageCount, models.size());
			for(IPageModel model : models) {
				// Check if dispose() is called
				ITraceRecords traces = ((TextEditorPartModel)model).getTraceRecords();
				assertTrue("dispose is called", traces.contains(null, "dispose"));		
			}
			
			// Check memory leak
			WeakReference<IEditorPart> ref = new WeakReference<IEditorPart>(activeNestedEditor);
			activeNestedEditor = null;
			assertIsGarbageCollected(ref, 10*1000);


		}


}
