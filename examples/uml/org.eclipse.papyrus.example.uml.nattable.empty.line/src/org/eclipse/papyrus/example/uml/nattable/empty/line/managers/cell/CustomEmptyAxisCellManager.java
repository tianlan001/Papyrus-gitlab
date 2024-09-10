/*****************************************************************************
 * Copyright (c) 2020,2021 CEA LIST
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
 *  Asma SMAOUI (CEA LIST) - bug 573840
  *****************************************************************************/

package org.eclipse.papyrus.example.uml.nattable.empty.line.managers.cell;

import org.eclipse.papyrus.example.uml.nattable.empty.line.Activator;
import org.eclipse.papyrus.infra.nattable.manager.cell.AbstractEmptyAxisCellManager;

/**
 * @author Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr>
 *
 */
public class CustomEmptyAxisCellManager extends AbstractEmptyAxisCellManager {

	/**
	 * Constructor.
	 *
	 * @param tableKindId
	 */
	public CustomEmptyAxisCellManager() {
		super(Activator.TABLE_TYPE);
	}

}
