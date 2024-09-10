/**
 *
 */
package org.eclipse.papyrus.infra.ui.lifecycleevents;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.papyrus.infra.ui.lifecycleevents.DoSaveEvent;
import org.eclipse.papyrus.infra.ui.lifecycleevents.ISaveEventListener;
import org.eclipse.papyrus.infra.ui.lifecycleevents.LifeCycleEventsProvider;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.junit.Before;
import org.junit.Test;


/**
 * @author cedric dumoulin
 *
 */
public class LifeCycleEventsProviderTest extends AbstractPapyrusTest {

	/**
	 * Object under test.
	 */
	protected LifeCycleEventsProvider eventProvider;

	/**
	 * @see junit.framework.TestCase#setUp()
	 *
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		eventProvider = new LifeCycleEventsProvider();
	}

	/**
	 * Test method for
	 * {@link org.eclipse.papyrus.infra.ui.lifecycleevents.LifeCycleEventsProvider#addDoSaveListener(org.eclipse.papyrus.infra.ui.lifecycleevents.ISaveEventListener)}
	 * .
	 */
	@Test
	public void testAddSaveListener() {

		ISaveEventListener listener = new FakeSaveEventListener();

		try {
			eventProvider.addDoSaveListener(listener);
		} catch (Exception e) {
			fail("Add listener");
		}

	}

	/**
	 * Test method for
	 * {@link org.eclipse.papyrus.infra.ui.lifecycleevents.LifeCycleEventsProvider#removeDoSaveListener(org.eclipse.papyrus.infra.ui.lifecycleevents.ISaveEventListener)}
	 * .
	 */
	@Test
	public void testRemoveSaveListener() {
		ISaveEventListener listener = new FakeSaveEventListener();

		try {
			eventProvider.addDoSaveListener(listener);
			eventProvider.removeDoSaveListener(listener);
		} catch (Exception e) {
			fail("Remove listener");
		}
	}

	/**
	 * Test method for
	 * {@link org.eclipse.papyrus.infra.ui.lifecycleevents.LifeCycleEventsProvider#addAboutToDoSaveListener(org.eclipse.papyrus.infra.ui.lifecycleevents.ISaveEventListener)}
	 * .
	 */
	@Test
	public void testAddAboutToSaveListener() {
		ISaveEventListener listener = new FakeSaveEventListener();

		try {
			eventProvider.addAboutToDoSaveListener(listener);
		} catch (Exception e) {
			fail("Add listener");
		}
	}

	/**
	 * Test method for
	 * {@link org.eclipse.papyrus.infra.ui.lifecycleevents.LifeCycleEventsProvider#removeAboutToDoSaveListener(org.eclipse.papyrus.infra.ui.lifecycleevents.ISaveEventListener)}
	 * .
	 */
	@Test
	public void testRemoveAboutToSaveListener() {
		ISaveEventListener listener = new FakeSaveEventListener();

		try {
			eventProvider.addAboutToDoSaveListener(listener);
			eventProvider.removeAboutToDoSaveListener(listener);
		} catch (Exception e) {
			fail("Remove listener");
		}
	}

	/**
	 * Test method for
	 * {@link org.eclipse.papyrus.infra.ui.lifecycleevents.LifeCycleEventsProvider#addPostDoSaveListener(org.eclipse.papyrus.infra.ui.lifecycleevents.ISaveEventListener)}
	 * .
	 */
	@Test
	public void testAddPostSaveListener() {
		ISaveEventListener listener = new FakeSaveEventListener();

		try {
			eventProvider.addPostDoSaveListener(listener);
		} catch (Exception e) {
			fail("Add listener");
		}
	}

	/**
	 * Test method for
	 * {@link org.eclipse.papyrus.infra.ui.lifecycleevents.LifeCycleEventsProvider#removePostDoSaveListener(org.eclipse.papyrus.infra.ui.lifecycleevents.ISaveEventListener)}
	 * .
	 */
	@Test
	public void testRemovePostSaveListener() {
		ISaveEventListener listener = new FakeSaveEventListener();

		try {
			eventProvider.addPostDoSaveListener(listener);
			eventProvider.removePostDoSaveListener(listener);
		} catch (Exception e) {
			fail("Remove listener");
		}
	}

	/**
	 * Test method for
	 * {@link org.eclipse.papyrus.infra.ui.lifecycleevents.LifeCycleEventsProvider#fireAboutToDoSaveEvent(org.eclipse.papyrus.infra.ui.lifecycleevents.DoSaveEvent)}
	 * .
	 */
	@Test
	public void testFireAboutToSaveEvent() {
		FakeSaveEventListener listener = new FakeSaveEventListener();

		eventProvider.addAboutToDoSaveListener(listener);
		DoSaveEvent event = new FakeSaveEvent();
		eventProvider.fireAboutToDoSaveEvent(event);

		assertTrue("event received", listener.isEventReceived(event));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.papyrus.infra.ui.lifecycleevents.LifeCycleEventsProvider#fireAboutToDoSaveAsEvent(org.eclipse.papyrus.infra.ui.lifecycleevents.DoSaveEvent)}
	 * .
	 */
	@Test
	public void testFireAboutToSaveAsEvent() {
		FakeSaveEventListener listener = new FakeSaveEventListener();

		eventProvider.addAboutToDoSaveListener(listener);
		DoSaveEvent event = new FakeSaveEvent();
		eventProvider.fireAboutToDoSaveAsEvent(event);

		assertTrue("event received", listener.isEventReceived(event));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.papyrus.infra.ui.lifecycleevents.LifeCycleEventsProvider#fireDoSaveEvent(org.eclipse.papyrus.infra.ui.lifecycleevents.DoSaveEvent)}
	 * .
	 */
	@Test
	public void testFireSaveEvent() {
		FakeSaveEventListener listener = new FakeSaveEventListener();

		eventProvider.addDoSaveListener(listener);
		DoSaveEvent event = new FakeSaveEvent();
		eventProvider.fireDoSaveEvent(event);

		assertTrue("event received", listener.isEventReceived(event));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.papyrus.infra.ui.lifecycleevents.LifeCycleEventsProvider#fireDoSaveAsEvent(org.eclipse.papyrus.infra.ui.lifecycleevents.DoSaveEvent)}
	 * .
	 */
	@Test
	public void testFireSaveAsEvent() {
		FakeSaveEventListener listener = new FakeSaveEventListener();

		eventProvider.addDoSaveListener(listener);
		DoSaveEvent event = new FakeSaveEvent();
		eventProvider.fireDoSaveAsEvent(event);

		assertTrue("event received", listener.isEventReceived(event));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.papyrus.infra.ui.lifecycleevents.LifeCycleEventsProvider#firePostDoSaveEvent(org.eclipse.papyrus.infra.ui.lifecycleevents.DoSaveEvent)}
	 * .
	 */
	@Test
	public void testFirePostSaveEvent() {
		FakeSaveEventListener listener = new FakeSaveEventListener();

		eventProvider.addPostDoSaveListener(listener);
		DoSaveEvent event = new FakeSaveEvent();
		eventProvider.firePostDoSaveEvent(event);

		assertTrue("event received", listener.isEventReceived(event));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.papyrus.infra.ui.lifecycleevents.LifeCycleEventsProvider#firePostDoSaveAsEvent(org.eclipse.papyrus.infra.ui.lifecycleevents.DoSaveEvent)}
	 * .
	 */
	@Test
	public void testFirePostSaveAsEvent() {
		FakeSaveEventListener listener = new FakeSaveEventListener();

		eventProvider.addPostDoSaveListener(listener);
		DoSaveEvent event = new FakeSaveEvent();
		eventProvider.firePostDoSaveAsEvent(event);

		assertTrue("event received", listener.isEventReceived(event));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.papyrus.infra.ui.lifecycleevents.LifeCycleEventsProvider#fireAllDoSaveEvent(org.eclipse.papyrus.infra.ui.lifecycleevents.DoSaveEvent)}
	 * .
	 */
	@Test
	public void testFireAllSaveEvent() {
		FakeSaveEventListener preListener = new FakeSaveEventListener();
		FakeSaveEventListener listener = new FakeSaveEventListener();
		FakeSaveEventListener postListener = new FakeSaveEventListener();

		eventProvider.addAboutToDoSaveListener(preListener);
		eventProvider.addDoSaveListener(listener);
		eventProvider.addPostDoSaveListener(postListener);

		DoSaveEvent event = new FakeSaveEvent();
		eventProvider.fireAllDoSaveEvent(event);

		assertTrue("event received", preListener.isEventReceived(event));
		assertTrue("event received", listener.isEventReceived(event));
		assertTrue("event received", postListener.isEventReceived(event));
	}

	/**
	 * Test method for
	 * {@link org.eclipse.papyrus.infra.ui.lifecycleevents.LifeCycleEventsProvider#fireAllDoSaveAsEvent(org.eclipse.papyrus.infra.ui.lifecycleevents.DoSaveEvent)}
	 * .
	 */
	@Test
	public void testFireAllSaveAsEvent() {
		FakeSaveEventListener preListener = new FakeSaveEventListener();
		FakeSaveEventListener listener = new FakeSaveEventListener();
		FakeSaveEventListener postListener = new FakeSaveEventListener();

		eventProvider.addAboutToDoSaveListener(preListener);
		eventProvider.addDoSaveListener(listener);
		eventProvider.addPostDoSaveListener(postListener);

		DoSaveEvent event = new FakeSaveEvent();
		eventProvider.fireAllDoSaveAsEvent(event);

		assertTrue("event received", preListener.isEventReceived(event));
		assertTrue("event received", listener.isEventReceived(event));
		assertTrue("event received", postListener.isEventReceived(event));
	}

}
