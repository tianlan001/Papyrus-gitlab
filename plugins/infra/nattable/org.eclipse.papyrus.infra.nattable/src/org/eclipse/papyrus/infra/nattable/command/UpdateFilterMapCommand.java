/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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

package org.eclipse.papyrus.infra.nattable.command;

import org.eclipse.nebula.widgets.nattable.command.AbstractContextFreeCommand;
import org.eclipse.papyrus.infra.nattable.layer.FilterRowDataLayer;

/**
 * This class allows us to update the map in {@link FilterRowDataLayer}
 *
 */
public class UpdateFilterMapCommand extends AbstractContextFreeCommand {

	/**
	 * the index for which we want update the filter map
	 */
	private int index;

	/**
	 * 
	 * Constructor.
	 *
	 * @param indexToUpdate
	 *            the column index for which we want update the value in the Map
	 */
	public UpdateFilterMapCommand(int indexToUpdate) {
		this.index = indexToUpdate;
	}

	/**
	 * @return the column index for which we want update the value in the Map
	 */
	public int getColumnIndexToUpdate() {
		return index;
	}

}
