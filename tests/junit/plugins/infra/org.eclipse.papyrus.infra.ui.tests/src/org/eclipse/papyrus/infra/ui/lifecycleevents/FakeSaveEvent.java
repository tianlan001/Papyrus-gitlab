/**
 * 
 */
package org.eclipse.papyrus.infra.ui.lifecycleevents;

import org.eclipse.papyrus.infra.ui.lifecycleevents.DoSaveEvent;

/**
 * @author dumoulin
 *
 */
public class FakeSaveEvent extends DoSaveEvent {

	/**
	 * Constructor.
	 *
	 * @param serviceRegistry
	 * @param multiDiagramEditor
	 */
	public FakeSaveEvent() {
		super(null, null);
		
	}

}
