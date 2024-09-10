/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
package org.eclipse.papyrus.infra.nattable.celleditor.config;

import org.eclipse.nebula.widgets.nattable.data.validate.IDataValidator;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;



public abstract class AbstractCellEditorConfiguration implements IAxisCellEditorConfiguration {

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.config.IAxisCellEditorConfiguration#getDataValidator(Table, Object)
	 *
	 * @return
	 *         <code>null</code>
	 */
	@Override
	public IDataValidator getDataValidator(Table table, Object axisElement) {
		return null;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.config.IAxisCellEditorConfiguration#getEditorDescription()
	 *
	 * @return
	 */
	@Override
	public String getEditorDescription() {
		return "No Existing Description"; //$NON-NLS-1$
	}
}
