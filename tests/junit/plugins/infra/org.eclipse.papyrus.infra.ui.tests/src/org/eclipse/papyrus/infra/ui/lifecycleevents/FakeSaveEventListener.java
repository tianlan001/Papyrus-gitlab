/**
 * 
 */
package org.eclipse.papyrus.infra.ui.lifecycleevents;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.infra.ui.lifecycleevents.DoSaveEvent;
import org.eclipse.papyrus.infra.ui.lifecycleevents.ISaveEventListener;


/**
 * @author dumoulin
 *
 */
public class FakeSaveEventListener implements ISaveEventListener {

	List<DoSaveEvent> receivedEvents = new ArrayList<DoSaveEvent>();
	
	/**
	 * @see org.eclipse.papyrus.infra.ui.lifecycleevents.ISaveEventListener#doSave(org.eclipse.papyrus.infra.ui.lifecycleevents.DoSaveEvent)
	 *
	 * @param event
	 */
	public void doSave(DoSaveEvent event) {
		receivedEvents.add(event);

	}

	/**
	 * @see org.eclipse.papyrus.infra.ui.lifecycleevents.ISaveEventListener#doSaveAs(org.eclipse.papyrus.infra.ui.lifecycleevents.DoSaveEvent)
	 *
	 * @param event
	 */
	public void doSaveAs(DoSaveEvent event) {
		receivedEvents.add(event);

	}

	/**
	 * Return true if the event has been received.
	 * @param event
	 * @return
	 */
	public boolean isEventReceived(DoSaveEvent event) {
		return receivedEvents.contains(event);
	}

	
	/**
	 * @return the receivedEvents
	 */
	public List<DoSaveEvent> getReceivedEvents() {
		return receivedEvents;
	}

}
