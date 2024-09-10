/*****************************************************************************
 * Copyright (c) 2014 Christian W. Damus and others.
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

package org.eclipse.papyrus.uml.decoratormodel.internal.resource;

import java.util.EventObject;

/**
 * Event object notifying of changes to the {@link DecoratorModelIndex}.
 */
public class DecoratorModelIndexEvent extends EventObject {

	public static final int INDEX_CHANGED = 0;

	private static final long serialVersionUID = 1L;

	private final int eventType;

	public DecoratorModelIndexEvent(DecoratorModelIndex source, int eventType) {
		super(source);

		this.eventType = eventType;
	}

	public final DecoratorModelIndex getIndex() {
		return (DecoratorModelIndex) getSource();
	}

	public final int getEventType() {
		return eventType;
	}
}
