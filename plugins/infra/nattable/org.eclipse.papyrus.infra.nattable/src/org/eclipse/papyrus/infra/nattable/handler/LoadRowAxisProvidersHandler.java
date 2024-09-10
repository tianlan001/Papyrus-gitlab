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
import org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.NattablePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.AbstractAxisProvider;

/**
 * Concrete handler to load row axis providers from the table's historic.
 *
 */
public class LoadRowAxisProvidersHandler extends AbstractLoadAxisProvidersHandler {

	/**
	 * @see org.eclipse.papyrus.infra.nattable.handler.AbstractTreeTableHandler#computeEnable(Object)
	 *
	 * @return
	 */
	@Override
	protected boolean computeEnable(Object evaluationContext) {
		boolean calculatedValue = super.computeEnable(evaluationContext);
		if (calculatedValue) {
			if (getCurrentNattableModelManager() != null && getCurrentNattableModelManager().getRowAxisManager() != null) {
				final IAxisManager rowAxisManager = getCurrentNattableModelManager().getRowAxisManager();
				calculatedValue = rowAxisManager.canBeSavedAsConfig();
			} else {
				calculatedValue = false;
			}
		}
		return calculatedValue;
	}

	/**
	 * Getter to obtain the current axis provider, in this case for rows.
	 *
	 * @see org.eclipse.papyrus.infra.nattable.handler.AbstractLoadAxisProvidersHandler#getAxisProvidersHistory()
	 *
	 * @return
	 */
	@Override
	public EList<AbstractAxisProvider> getAxisProvidersHistory() {
		if (!this.getCurrentNattableModelManager().getTable().isInvertAxis()) {
			return this.getCurrentNattableModelManager().getTable().getRowAxisProvidersHistory();
		} else {
			return this.getCurrentNattableModelManager().getTable().getColumnAxisProvidersHistory();
		}
	}

	/**
	 * Getter to obtain the current axis provider, in this case for rows.
	 *
	 * @see org.eclipse.papyrus.infra.nattable.handler.AbstractLoadAxisProvidersHandler#getCurrentAxisProvider()
	 *
	 * @return
	 */
	@Override
	public AbstractAxisProvider getCurrentAxisProvider() {
		if (!this.getCurrentNattableModelManager().getTable().isInvertAxis()) {
			return this.getCurrentNattableModelManager().getTable().getCurrentRowAxisProvider();
		} else {
			return this.getCurrentNattableModelManager().getTable().getCurrentColumnAxisProvider();
		}
	}

	/**
	 * Get the EReference where the current column provider will be set.
	 *
	 * @see org.eclipse.papyrus.infra.nattable.handler.AbstractLoadAxisProvidersHandler#getCurrentAxisProviderEFeature()
	 *
	 * @return
	 */
	@Override
	public EReference getCurrentAxisProviderEFeature() {
		return NattablePackage.eINSTANCE.getTable_CurrentRowAxisProvider();
	}

}