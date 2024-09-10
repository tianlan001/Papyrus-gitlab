/**
 * 
 */
package org.eclipse.papyrus.infra.core.sasheditor.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.infra.core.sasheditor.editor.IPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IPageLifeCycleEventsListener;


/**
 * A fake listener implementation for testing purpose.
 * @author cedric dumoulin
 *
 */
public class FakePageLifeCycleEventsListener implements IPageLifeCycleEventsListener {

	public static final String PAGE_CHANGED = "pageChanged";

	public static final String PAGE_OPENED = "pageOpened";

	public static final String PAGE_CLOSED = "pageClosed";

	public static final String PAGE_ACTIVATED = "pageActivated";

	public static final String PAGE_DEACTIVATED = "pageDeactivated";

	public static final String PAGE_ABOUTTOBECLOSED = "pageAboutToBeClosed";

	public static final String PAGE_ABOUTTOBEOPENED = "pageAboutToBeOpened";

	/** */
	public int eventCount = 0;

	/** */
	public List<String> traces;

	/** */
	public List<IPage> events;

	/**
	 * 
	 * Constructor.
	 *
	 */
	public FakePageLifeCycleEventsListener() {
		traces = new ArrayList<String>();
		events = new ArrayList<IPage>();
	}


	/**
	 * @return the eventCount
	 */
	public int getEventCount() {
		return eventCount;
	}


	/**
	 * @return the trace
	 */
	public List<String> getTraces() {
		return traces;
	}

	/**
	 * @return the trace
	 */
	public String getTrace(int index) {
		return traces.get(index);
	}

	/**
	 * @return the trace
	 */
	public List<IPage> getEvents() {
		return events;
	}

	/**
	 * @return the trace
	 */
	public IPage getEvent(int index) {
		return events.get(index);
	}

	/**
	 * 
	 */
	public void resetChangeCount() {
		eventCount = 0;
	}

	/**
	 * 
	 */
	public void resetTraces() {
		traces.clear();
		events.clear();
	}

	/* ************************** */
	/* Methods          */
	/* ************************** */

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.editor.IPageChangedListener#pageChanged(org.eclipse.papyrus.infra.core.sasheditor.editor.IPage)
	 *
	 * @param newPage
	 */
	public void pageChanged(IPage newPage) {
		traces.add(PAGE_CHANGED);
		events.add(newPage);
		eventCount++;
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.IPageLifeCycleEventsListener#pageOpened(org.eclipse.papyrus.infra.core.sasheditor.editor.IPage)
	 *
	 * @param page
	 */
	public void pageOpened(IPage page) {
		traces.add(PAGE_OPENED);
		events.add(page);
		eventCount++;
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.IPageLifeCycleEventsListener#pageClosed(org.eclipse.papyrus.infra.core.sasheditor.editor.IPage)
	 *
	 * @param page
	 */
	public void pageClosed(IPage page) {
		traces.add(PAGE_CLOSED);
		events.add(page);
		eventCount++;
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.IPageLifeCycleEventsListener#pageActivated(org.eclipse.papyrus.infra.core.sasheditor.editor.IPage)
	 *
	 * @param page
	 */
	public void pageActivated(IPage page) {
		traces.add(PAGE_ACTIVATED);
		events.add(page);
		eventCount++;
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.IPageLifeCycleEventsListener#pageDeactivated(org.eclipse.papyrus.infra.core.sasheditor.editor.IPage)
	 *
	 * @param page
	 */
	public void pageDeactivated(IPage page) {
		traces.add(PAGE_DEACTIVATED);
		events.add(page);
		eventCount++;
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.IPageLifeCycleEventsListener#pageAboutToBeOpened(org.eclipse.papyrus.infra.core.sasheditor.editor.IPage)
	 *
	 * @param page
	 */
	public void pageAboutToBeOpened(IPage page) {
		traces.add(PAGE_ABOUTTOBEOPENED);
		events.add(page);
		eventCount++;
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.IPageLifeCycleEventsListener#pageAboutToBeClosed(org.eclipse.papyrus.infra.core.sasheditor.editor.IPage)
	 *
	 * @param page
	 */
	public void pageAboutToBeClosed(IPage page) {
		traces.add(PAGE_ABOUTTOBECLOSED);
		events.add(page);
		eventCount++;
	}

}
