/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.tools.spi;

import org.eclipse.papyrus.infra.tools.notify.INotificationBuilder;

/**
 * An OSGi service protocol for creation of a notification builder
 * (preferably for UI presentation).
 * 
 * @since 2.0
 */
@FunctionalInterface
public interface INotificationBuilderFactory {
	/** Creates notification builder that presents notifications in the UI. */
	INotificationBuilder createNotificationBuilder();
}
