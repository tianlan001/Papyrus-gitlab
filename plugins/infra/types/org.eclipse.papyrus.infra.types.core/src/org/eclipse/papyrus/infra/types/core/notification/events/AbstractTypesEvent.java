/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.types.core.notification.events;

import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelper;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;

public abstract class AbstractTypesEvent implements ITypesEvent {

	/**
	 * Timestamp of the creation of this event
	 */
	private long timestamp;

	/**
	 * The {@link IEditCommandRequest} that triggered this {@link ITypesEvent}
	 */
	private IEditCommandRequest request;

	private IEditHelper editHelper;

	public AbstractTypesEvent(IEditCommandRequest req, IEditHelper editHelper) {
		this();
		this.request = req;
		this.editHelper = editHelper;
	}

	/**
	 * @return the {@link IEditCommandRequest} that triggered the event
	 */
	public IEditCommandRequest getRequest() {
		return request;
	}

	/**
	 * @return the {@link IEditHelper} that triggered the event
	 */
	public IEditHelper getEditHelper() {
		return editHelper;
	}

	AbstractTypesEvent() {
		timestamp = System.currentTimeMillis();
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.types.core.notification.events.ITypesEvent#getTimestamp()
	 *
	 * @return
	 */
	@Override
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.types.core.notification.events.ITypesEvent#getEventName()
	 *
	 * @return
	 */
	@Override
	public String getEventName() {
		return this.getClass().getSimpleName();
	}
}
