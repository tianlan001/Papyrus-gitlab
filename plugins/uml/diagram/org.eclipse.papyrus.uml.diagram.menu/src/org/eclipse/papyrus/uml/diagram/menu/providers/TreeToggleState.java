/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.menu.providers;

import org.eclipse.papyrus.uml.diagram.menu.actions.LineStyleAction;

/**
 * This class provides the toggle state for the Tree Line Style
 *
 *
 *
 */
public class TreeToggleState extends AbstractLineStyleToggleState {

	/**
	 *
	 * Constructor.
	 *
	 */
	public TreeToggleState() {
		super(LineStyleAction.TREE);
	}

}
