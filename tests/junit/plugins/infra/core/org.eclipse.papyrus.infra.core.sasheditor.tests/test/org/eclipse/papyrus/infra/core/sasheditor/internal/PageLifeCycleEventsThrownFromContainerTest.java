/**
 *
 */
package org.eclipse.papyrus.infra.core.sasheditor.internal;

import static org.eclipse.papyrus.infra.core.sasheditor.pagesmodel.SashPagesModelFactory.folder;
import static org.eclipse.papyrus.infra.core.sasheditor.pagesmodel.SashPagesModelFactory.page;
import static org.eclipse.papyrus.infra.core.sasheditor.pagesmodel.SashPagesModelFactory.vSash;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IPageModel;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.ISashWindowsContentProvider;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.ITabFolderModel;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.simple.SimpleSashWindowContentProviderUtils;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.simple.SimpleSashWindowsContentProvider;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.MessagePartModel;
import org.eclipse.papyrus.infra.core.sasheditor.pagesmodel.IModelExp;
import org.eclipse.papyrus.infra.core.sasheditor.pagesmodel.PagesModelException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


/**
 * Test if the {@link SashWindowsContainer} send the events of a page lifecycle.
 * Test if the page lifecycle events are correctly thrown by the {@link SashWindowsContainer} when
 * page are added/removed, ...
 *
 * @author cedric dumoulin
 *
 */
public class PageLifeCycleEventsThrownFromContainerTest /* extends AbstractPapyrusTest */{

	protected Display display;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		if(display == null || display.isDisposed()) {
			display = Display.getCurrent();
		}
		if(display == null) {
			display = new Display();
		}
	}

	/**
	 *
	 * @return
	 */
	private Display getDisplay() {
		return display;
	}

	/**
	 * Create a {@link SashWindowsContainer} to test. Initialize it with provided {@link ISashWindowsContentProvider}.
	 *
	 * @param contentProvider
	 * @return
	 */
	protected SashWindowsContainer createSashWindowsContainer(ISashWindowsContentProvider contentProvider) {

		display = getDisplay();
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
	protected SimpleSashWindowsContentProvider createContentProvider() {
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
	 * Lookup a page by its raw model.
	 *
	 * @param container
	 * @param rawModel
	 * @return
	 */
	protected IPage lookupTabFolderPart(SashWindowsContainer container, Object rawModel) {

		LookupModelPageVisitor visitor = new LookupModelPageVisitor(rawModel);
		container.visit(visitor);
		return visitor.result();
	}


	/**
	 * Test method for
	 * {@link org.eclipse.papyrus.infra.core.sasheditor.internal.SashContainerEventsProvider#addListener(org.eclipse.papyrus.infra.core.sasheditor.internal.SashContainerEventsListener)}
	 * .
	 */
	@Ignore("TODO")
	@Test
	public void testAddActiveEditorChangedListener() {
		//TODO
	}

	/**
	 * Test method for
	 * {@link org.eclipse.papyrus.infra.core.sasheditor.internal.SashContainerEventsProvider#removeListener(org.eclipse.papyrus.infra.core.sasheditor.internal.SashContainerEventsListener)}
	 * .
	 */
	@Ignore("TODO")
	@Test
	public void testRemoveActiveEditorChangedListener() {
		//TODO
	}

	/**
	 * Test method for
	 * {@link org.eclipse.papyrus.infra.core.sasheditor.internal.SashContainerEventsProvider#firePageOpenedEvent(org.eclipse.papyrus.infra.core.sasheditor.internal.PagePart)}
	 * .
	 */
	@Test
	public void testFirePageOpenedEvent() {

		// Create container and contentProvider
		ISashWindowsContentProvider contentProvider = createContentProvider();
		SashWindowsContainer container = createSashWindowsContainer(contentProvider);

		// Create listener and attach it
		FakePageLifeCycleEventsListener listener = new FakePageLifeCycleEventsListener();
		container.addPageLifeCycleListener(listener);

		// Do refresh. This fire events
		container.refreshTabs();
		listener.resetChangeCount();
		listener.resetTraces();


		// Add an editor
		contentProvider.addPage(new MessagePartModel("newPage"));
		container.refreshTabs();

		// check events (there is more than the 2 expected)
		assertTrue("event fired", 2 <= listener.getEventCount());
		int i = 0;
		assertEquals("right event", FakePageLifeCycleEventsListener.PAGE_ABOUTTOBEOPENED, listener.getTraces().get(i++));
		assertEquals("right event", FakePageLifeCycleEventsListener.PAGE_OPENED, listener.getTraces().get(i++));
		
		// Next test is convenient when debugging (because it output what is sent), but it is hard to write
		assertEquals("expected events", "[pageAboutToBeOpened, pageOpened, pageActivated]", listener.getTraces().toString());
	}

	/**
	 * Test method for
	 * {@link org.eclipse.papyrus.infra.core.sasheditor.internal.SashContainerEventsProvider#firePageClosedEvent(org.eclipse.papyrus.infra.core.sasheditor.internal.PagePart)}
	 * .
	 */
	@Test
	public void testFirePageClosedEvent() {
		// Create container and contentProvider
		ISashWindowsContentProvider contentProvider = createContentProvider();
		SashWindowsContainer container = createSashWindowsContainer(contentProvider);

		// Create listener and attach it
		FakePageLifeCycleEventsListener listener = new FakePageLifeCycleEventsListener();
		container.addPageLifeCycleListener(listener);

		// Do refresh. This fire events
		container.refreshTabs();


		// Add an editor
		IPageModel model = new MessagePartModel("newPage");
		contentProvider.addPage(model);
		container.refreshTabs();
		listener.resetChangeCount();
		listener.resetTraces();

		IPage page1 = lookupTabFolderPart(container, model);
		assertNotNull("Page found", page1);

		// Close page
		contentProvider.removePage(model);
		container.refreshTabs();

		// check events (there is more than the 2 expected)
		assertTrue("event fired", 2 <= listener.getEventCount());

		assertTrue("close event fired", listener.getEvents().contains(page1));
		assertTrue("close event fired", listener.getTraces().contains(FakePageLifeCycleEventsListener.PAGE_CLOSED));
		int index = listener.getTraces().indexOf(FakePageLifeCycleEventsListener.PAGE_CLOSED);
		assertEquals("right page closed", page1, listener.getEvent(index));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.papyrus.infra.core.sasheditor.internal.SashContainerEventsProvider#firePageAboutToBeOpenedEvent(org.eclipse.papyrus.infra.core.sasheditor.internal.PagePart)}
	 * .
	 */
	@Test
	public void testFirePageAboutToBeOpenedEvent() {
		// Create container and contentProvider
		ISashWindowsContentProvider contentProvider = createContentProvider();
		SashWindowsContainer container = createSashWindowsContainer(contentProvider);

		// Create listener and attach it
		FakePageLifeCycleEventsListener listener = new FakePageLifeCycleEventsListener();
		container.addPageLifeCycleListener(listener);

		// Do refresh. This fire events
		container.refreshTabs();
		listener.resetChangeCount();
		listener.resetTraces();


		// Add an editor
		contentProvider.addPage(new MessagePartModel("newPage"));
		container.refreshTabs();

		// check events (there is more than the 2 expected)
		assertTrue("event fired", 2 <= listener.getEventCount());
		int i = 0;
		assertEquals("right event", FakePageLifeCycleEventsListener.PAGE_ABOUTTOBEOPENED, listener.getTraces().get(i++));
		//
		
	}

	/**
	 * Test method for
	 * {@link org.eclipse.papyrus.infra.core.sasheditor.internal.SashContainerEventsProvider#firePageAboutToBeClosedEvent(org.eclipse.papyrus.infra.core.sasheditor.internal.PagePart)}
	 * .
	 */
	@Test
	public void testFirePageAboutToBeClosedEvent() {
		// no such event anymore
	}

	/**
	 * Test method for
	 * {@link org.eclipse.papyrus.infra.core.sasheditor.internal.SashContainerEventsProvider#firePageActivatedEvent(org.eclipse.papyrus.infra.core.sasheditor.internal.PagePart)}
	 * .
	 */
	@Test
	public void testFirePageActivatedEvent() {
		// Create container and contentProvider
		SimpleSashWindowsContentProvider contentProvider = createContentProvider();
		SashWindowsContainer container = createSashWindowsContainer(contentProvider);

		// Create listener and attach it
		FakePageLifeCycleEventsListener listener = new FakePageLifeCycleEventsListener();
		container.addPageLifeCycleListener(listener);

		container.refreshTabs();


		// Add two pages
		IPageModel pageModel1 = new MessagePartModel("newPage1");
		IPageModel pageModel2 = new MessagePartModel("newPage2");
		contentProvider.addPage(pageModel1);
		contentProvider.addPage(pageModel2);


		// Do refresh. This fire events
		container.refreshTabs();
		listener.resetChangeCount();
		listener.resetTraces();

		// Activate page 2 (1 is already active, and activate it
		// do not throw events)
		IPage page1 = lookupTabFolderPart(container, pageModel2);
		container.selectPage(page1);

		// check events (1 expected)
		assertEquals("event fired", 1, listener.getEventCount());
		int i = 0;
		assertEquals("right event", FakePageLifeCycleEventsListener.PAGE_ACTIVATED, listener.getTraces().get(i++));

		// ****************
		// Check with a move
		listener.resetChangeCount();
		listener.resetTraces();

		ITabFolderModel folderModel = contentProvider.getCurrentTabFolder();
		contentProvider.createFolder(folderModel, 0, folderModel, SWT.TOP);

		// Do refresh. This fire events
		container.refreshTabs();
		// check events (there is none, as active page does not change)
		//		assertEquals("event fired", 3, listener.getEventCount());
		//		i=0;
		//		assertEquals("right event", FakePageLifeCycleEventsListener.PAGE_ACTIVATED, listener.getTraces().get(i++));

		// Create a new page
		IPageModel pageModel3 = new MessagePartModel("newPage3");
		contentProvider.addPage(pageModel3);
		// Do refresh. This fire events
		container.refreshTabs();

		// check events (there is more than the 2 expected)
		assertEquals("event fired", 3, listener.getEventCount());
		i = 0;
		assertEquals("right event", FakePageLifeCycleEventsListener.PAGE_ABOUTTOBEOPENED, listener.getTraces().get(i++));
		assertEquals("right event", FakePageLifeCycleEventsListener.PAGE_OPENED, listener.getTraces().get(i++));
		assertEquals("right event", FakePageLifeCycleEventsListener.PAGE_ACTIVATED, listener.getTraces().get(i++));

	}

	/**
	 * Test method for
	 * {@link org.eclipse.papyrus.infra.core.sasheditor.internal.SashContainerEventsProvider#firePageDeactivatedEvent(org.eclipse.papyrus.infra.core.sasheditor.internal.PagePart)}
	 * .
	 */
	@Test
	public void testFirePageDeactivatedEvent() {
		// Deactivated is no sent
	}

	/**
	 * Test the life cycle of the events when an editor is closed.
	 * @throws PagesModelException 
	 * 
	 */
	@Test
	public void testPageClosedEventLifeCycle() throws PagesModelException {

		// Create content provider
		SimpleSashWindowsContentProvider contentProvider = new SimpleSashWindowsContentProvider();
		SimpleSashWindowContentProviderUtils helper = new SimpleSashWindowContentProviderUtils(contentProvider);

		// define how to populate contentProvider
		IModelExp expr = vSash(folder("f1", page("p11"), page("p12")), folder("f2", page("p21"), page("p22"), page("p23")));
		// Try to create the model
		helper.createModel(expr);


		// Create the SashWindowsContainer
		SashWindowsContainer container = createSashWindowsContainer(contentProvider);

		// Create listener and attach it
		FakePageLifeCycleEventsListener listener = new FakePageLifeCycleEventsListener();
		container.addPageLifeCycleListener(listener);

		// creates Pages in the sashContainer 
		container.refreshTabs();

		// Set current active page (to force a pageActivated after closing)
		container.selectPage(container.lookupModelPage(helper.lookupPageByName("p12")));
		
		// refresh traces
		listener.resetChangeCount();
		listener.resetTraces();

		// Now test the lifeCycle by closing a page
		helper.removePage("p12");
		// Do refresh. This fire events
		container.refreshTabs();
				
		// check events (there is more than the 2 expected)
		// Next test is convenient when debugging (because it output what is sent), but it is hard to write
		assertEquals("expected events", "[pageClosed, pageActivated]", listener.getTraces().toString());

		assertEquals("event fired", 2, listener.getEventCount());
		int i = 0;
		assertEquals("right event", FakePageLifeCycleEventsListener.PAGE_CLOSED, listener.getTraces().get(i++));
		assertEquals("right event", FakePageLifeCycleEventsListener.PAGE_ACTIVATED, listener.getTraces().get(i++));
		
	}

	/**
	 * Test the life cycle of the events when an editor, which is the last one of a folder, is closed.
	 * The associated folder should also be closed by the container.
	 * @throws PagesModelException 
	 * 
	 */
	@Test
	public void testPageClosedEventLifeCycleWhenLastPageOfFolder() throws PagesModelException {

		// Create content provider
		SimpleSashWindowsContentProvider contentProvider = new SimpleSashWindowsContentProvider();
		SimpleSashWindowContentProviderUtils helper = new SimpleSashWindowContentProviderUtils(contentProvider);

		// define how to populate contentProvider
		IModelExp expr = vSash(folder("f1", page("p11"), page("p12")), folder("f2", page("p21")));
		// Try to create the model
		helper.createModel(expr);


		// Create the SashWindowsContainer
		SashWindowsContainer container = createSashWindowsContainer(contentProvider);

		// Create listener and attach it
		FakePageLifeCycleEventsListener listener = new FakePageLifeCycleEventsListener();
		container.addPageLifeCycleListener(listener);

		// creates Pages in the sashContainer 
		container.refreshTabs();

		// Set current active page (to force a pageActivated after closing)
		container.selectPage(container.lookupModelPage(helper.lookupPageByName("p21")));

		// refresh traces
		listener.resetChangeCount();
		listener.resetTraces();

		// Now test the lifeCycle by closing a page
		helper.removePage("p21");
		// Do refresh. This fire events
		container.refreshTabs();
				
		// check events (there is more than the 2 expected)
		// Next test is convenient when debugging (because it output what is sent), but it is hard to write
		assertEquals("expected events", "[pageClosed, pageActivated]", listener.getTraces().toString());
		
		assertEquals("event fired", 2, listener.getEventCount());
		int i = 0;
		assertEquals("right event", FakePageLifeCycleEventsListener.PAGE_CLOSED, listener.getTraces().get(i++));
		assertEquals("right event", FakePageLifeCycleEventsListener.PAGE_ACTIVATED, listener.getTraces().get(i++));
		
	}
	

}
