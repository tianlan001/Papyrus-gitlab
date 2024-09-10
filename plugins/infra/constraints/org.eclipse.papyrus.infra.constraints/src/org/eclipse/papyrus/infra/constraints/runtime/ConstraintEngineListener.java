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

import java.util.EventListener;


/**
 * Protocol for notifications of changes in the constraint listener that may affect its clients.
 */
public interface ConstraintEngineListener extends EventListener {

	/**
	 * Notifies listeners that the engine's constraints have changed, so any remembered calculation results may need to be recomputed.
	 *
	 * @param event
	 *            the notification object
	 */
	void constraintsChanged(ConstraintsChangedEvent event);
}
