/*****************************************************************************
 * Copyright (c) 2013, 2020 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 562864
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.handler;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.NattablePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.AbstractAxisProvider;

/**
 * Concrete handler to save the current axis provider, in this case for rows.
 *
 */
public class SaveCurrentRowAxisProvidersHandler extends AbstractSaveCurrentAxisProvidersHandler {

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.handler.AbstractSaveCurrentAxisProvidersHandler#getAxisProviderHistoryEReference()
	 *
	 * @return
	 */
	@Override
	public EReference getAxisProviderHistoryEReference() {
		return NattablePackage.eINSTANCE.getTable_RowAxisProvidersHistory();
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.handler.AbstractSaveCurrentAxisProvidersHandler#getAxisProviderHistory()
	 *
	 * @return
	 */
	@Override
	public EList<AbstractAxisProvider> getAxisProviderHistory() {
		return this.getCurrentNattableModelManager().getTable().getRowAxisProvidersHistory();
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.handler.AbstractSaveCurrentAxisProvidersHandler#getAxisProvider()
	 *
	 * @return
	 */
	@Override
	public AbstractAxisProvider getAxisProvider() {
		return this.getCurrentNattableModelManager().getHorizontalAxisProvider();
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.handler.AbstractTreeTableHandler#computeEnable(Object)
	 *
	 * @return
	 * @since 6.7
	 */
	@Override
	protected boolean computeEnable(Object evaluationContext) {
		boolean calculatedValue = super.computeEnable(evaluationContext);
		if (calculatedValue) {
			final INattableModelManager manager = getCurrentNattableModelManager();
			calculatedValue = manager != null
					&& manager.getRowAxisManager() != null
					&& manager.getRowAxisManager().canBeSavedAsConfig();
		}
		return calculatedValue;
	}
}
