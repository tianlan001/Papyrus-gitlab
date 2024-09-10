/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
package org.eclipse.papyrus.infra.nattable.properties.observable;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;

/**
 * Observable value for the element type id
 *
 * @author Vincent Lorenzo
 *
 */
public class RowPasteObjectPostActionsObservableValue extends AbstractPasteObjectPostActionsObservableValue {

	/**
	 *
	 * Constructor.
	 *
	 * @param table
	 */
	public RowPasteObjectPostActionsObservableValue(final EditingDomain domain, final Table table) {
		super(domain, table, false);
	}
}
