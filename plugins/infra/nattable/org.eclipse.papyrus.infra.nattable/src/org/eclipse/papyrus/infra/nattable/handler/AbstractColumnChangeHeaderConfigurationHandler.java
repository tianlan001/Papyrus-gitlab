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
package org.eclipse.papyrus.infra.nattable.handler;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.infra.nattable.model.nattable.NattablePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AbstractHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.HeaderAxisConfigurationManagementUtils;

/**
 * Abstract class to edit the header cofniguration
 *
 * @author Vincent Lorenzo
 *
 */
public abstract class AbstractColumnChangeHeaderConfigurationHandler extends AbstractChangeHeaderConfigurationHandler {

	/**
	 *
	 * @return
	 *         the edited axis configuration or <code>null</code> if it doesn't exists in the table
	 */
	@Override
	protected AbstractHeaderAxisConfiguration getEditedAxisConfiguration() {
		return HeaderAxisConfigurationManagementUtils.getColumnAbstractHeaderAxisConfigurationUsedInTable(getCurrentNattableModelManager().getTable());
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.handler.AbstractChangeHeaderConfigurationHandler#getLocalHeaderAxisConfigurationFeature()
	 *
	 * @return
	 */
	@Override
	protected EStructuralFeature getLocalHeaderAxisConfigurationFeature() {
		final Table table = getCurrentNattableModelManager().getTable();
		if (!table.isInvertAxis()) {
			return NattablePackage.eINSTANCE.getTable_LocalColumnHeaderAxisConfiguration();
		}
		return NattablePackage.eINSTANCE.getTable_LocalRowHeaderAxisConfiguration();
	}
}
