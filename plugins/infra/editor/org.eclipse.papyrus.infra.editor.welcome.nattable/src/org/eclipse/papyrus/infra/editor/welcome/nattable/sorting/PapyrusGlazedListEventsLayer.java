/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.editor.welcome.nattable.sorting;

import org.eclipse.nebula.widgets.nattable.extension.glazedlists.GlazedListsEventLayer;
import org.eclipse.nebula.widgets.nattable.layer.AbstractLayerTransform;
import org.eclipse.nebula.widgets.nattable.layer.IUniqueIndexLayer;
import org.eclipse.nebula.widgets.nattable.layer.event.RowStructuralRefreshEvent;
import org.eclipse.nebula.widgets.nattable.layer.event.VisualRefreshEvent;
import org.eclipse.swt.widgets.Display;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;

/**
 * An alternative to the {@link GlazedListsEventLayer} that doesn't run a thread
 * continuously checking for updates every 100 milliseconds, but instead posts
 * 100-millisecond deferred updates that coalesce events in the interim.
 */
public class PapyrusGlazedListEventsLayer<E> extends AbstractLayerTransform implements IUniqueIndexLayer, ListEventListener<E> {

	private final IUniqueIndexLayer underlyingLayer;
	private EventList<? extends E> eventList;

	private volatile boolean active;
	private volatile Update pendingUpdate;

	/**
	 * Initializes me.
	 *
	 * @param underlyingLayer
	 *            the layer to which I post refresh events
	 * @param eventList
	 *            the event list from which the {@code underlyingLayer} obtains its data
	 */
	public PapyrusGlazedListEventsLayer(IUniqueIndexLayer underlyingLayer, EventList<? extends E> eventList) {
		super(underlyingLayer);
		this.underlyingLayer = underlyingLayer;
		this.eventList = eventList;

		this.eventList.addListEventListener(this);
	}

	@Override
	public void dispose() {
		super.dispose();

		if (eventList != null) {
			eventList.removeListEventListener(this);
			eventList = null;
		}
	}

	/**
	 * Queries whether I am currently reactiving to changes in the list that I am observing.
	 * 
	 * @return whether I am active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets I am currently reactiving to changes in the list that I am observing.
	 * It is useful to deactivate me when initiating bulk operations on the list
	 * to avoid useless work when a full table refresh will follow, anyways.
	 * 
	 * @param active
	 *            whether I am active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	//
	// Event handling
	//

	@Override
	public void listChanged(ListEvent<E> event) {
		// Check whether we even want to process anything
		if (!isActive()) {
			return;
		}

		boolean structure = false;

		out: while (event.next()) {
			int eventType = event.getType();
			switch (eventType) {
			case ListEvent.DELETE:
			case ListEvent.INSERT:
				structure = true;
				Update update = pendingUpdate;
				if (update != null) {
					update.structuralUpdate = structure;
				}
				break out;
			}
		}

		if (pendingUpdate == null) {
			// May have missed the boat
			pendingUpdate = new Update();
			pendingUpdate.structuralUpdate = structure;
			Display.getDefault().timerExec(100, pendingUpdate);
		}
	}

	//
	// IUniqueIndexLayer protocol
	//

	@Override
	public int getColumnPositionByIndex(int columnIndex) {
		return this.underlyingLayer.getColumnPositionByIndex(columnIndex);
	}

	@Override
	public int getRowPositionByIndex(int rowIndex) {
		return this.underlyingLayer.getRowPositionByIndex(rowIndex);
	}

	//
	// Nested types
	//

	private class Update implements Runnable {
		volatile boolean structuralUpdate;

		@Override
		public void run() {
			if (pendingUpdate == this) {
				pendingUpdate = null;

				// Check that we haven't been disposed since posting the update
				if (eventList != null) {
					if (structuralUpdate) {
						fireLayerEvent(new RowStructuralRefreshEvent(getUnderlyingLayer()));
					} else {
						fireLayerEvent(new VisualRefreshEvent(getUnderlyingLayer()));
					}
				}
			}
		}
	}
}
