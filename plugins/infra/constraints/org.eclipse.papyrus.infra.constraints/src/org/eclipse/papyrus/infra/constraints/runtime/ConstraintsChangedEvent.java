/*
 * Copyright (c) 2014 CEA and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus (CEA) - Initial API and implementation
 *
 */
package org.eclipse.papyrus.infra.constraints.runtime;

import java.util.EventObject;


/**
 * Notification object for the {@linkplain ConstraintEngineListener#constraintsChanged(ConstraintsChangedEvent) constraints-changed event}.
 */
public class ConstraintsChangedEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	/**
	 * Initializes me with my originating constraint engine.
	 *
	 * @param source
	 *            my source
	 */
	public ConstraintsChangedEvent(ConstraintEngine<?> source) {
		super(source);
	}

	/**
	 * Queries the constraint engine that generated this event.
	 *
	 * @return the source constraint engine
	 */
	public ConstraintEngine<?> getConstraintEngine() {
		return (ConstraintEngine<?>) getSource();
	}
}
