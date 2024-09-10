/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Juan Cadavid (CEA LIST) juan.cadavid@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.ui.menu;

import org.eclipse.papyrus.infra.ui.util.AbstractCreateMenuFromCommandCategory;


/**
 * Abstract menu for the creation of UML elements
 */
public abstract class AbstractCreateUmlChildMenu extends AbstractCreateMenuFromCommandCategory {

	/** the category of the UML Creation Command */
	public static final String UML_CREATION_COMMAND_CATEGORY = "org.eclipse.papyrus.uml.service.types.umlElementCreationCommands"; //$NON-NLS-1$

	/**
	 * 
	 * Constructor.
	 * 
	 */
	public AbstractCreateUmlChildMenu() {
		super(UML_CREATION_COMMAND_CATEGORY);
	}

}
