/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *	Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.dialogs;

import org.eclipse.gef.commands.Command;


/**
 * Interface for Port interface manager dialog.
 */
public interface IPortInterfaceManagerDialog {

	/**
	 * Returns the commands to apply the changes.
	 *
	 * @return the commands to apply the changes
	 */
	Command getCommand();

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#open()
	 *
	 * @return
	 */
	int open();

}
