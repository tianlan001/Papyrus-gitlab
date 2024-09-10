/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
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

package org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater;

import org.eclipse.uml2.uml.LinkEndData;

/**
 *
 * Pins of CreateLinkAction should be create and update automatically
 *
 */
public interface IPinUpdaterLinkEndData {

	/**
	 * The role of a 'pin updater' is to enable update of any pin of a particular linkEndData.
	 * This operation is intended to implement the the general algorithm to update pins.
	 *
	 * @param linkEndData
	 *            the linkEndData for which the pins need to be updated
	 *
	 */
	void updatePins(LinkEndData linkEndData);
}