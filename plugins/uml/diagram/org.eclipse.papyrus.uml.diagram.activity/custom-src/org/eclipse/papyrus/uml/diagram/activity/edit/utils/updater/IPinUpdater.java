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
 *   J�r�mie TATIBOUET (CEA LIST) - Initial API and implementation
 *   S�bastien REVOL (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater;

import org.eclipse.uml2.uml.ActivityNode;

public interface IPinUpdater<NodeType extends ActivityNode> {

	/**
	 * The role of a 'pin updater' is to enable update of any pin of a particular activity node.
	 * This operation is intended to implement the the general algorithm to update pins.
	 *
	 * @param node
	 *            the activity node for which the pins need to be updated
	 *
	 */
	public void updatePins(NodeType node);

}
