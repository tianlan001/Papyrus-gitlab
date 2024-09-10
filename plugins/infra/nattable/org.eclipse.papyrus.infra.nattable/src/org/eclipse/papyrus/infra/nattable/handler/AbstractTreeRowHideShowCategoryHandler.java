/*****************************************************************************
 * Copyright (c) 2014, 2020 CEA LIST and others.
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
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 562864
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.handler;

import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.IntListValueStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.nattable.utils.FillingConfigurationUtils;
import org.eclipse.papyrus.infra.nattable.utils.NamedStyleConstants;


/**
 * @author Vincent Lorenzo
 *
 */
public abstract class AbstractTreeRowHideShowCategoryHandler extends AbstractTreeTableHandler {

	/**
	 *
	 * @return
	 *         <code>true</code> if all categories are currently hidden
	 */
	protected boolean allCategoriesAreCurrentlyHidden() {
		Table table = getTable();
		IntListValueStyle values = (IntListValueStyle) table.getNamedStyle(NattablestylePackage.eINSTANCE.getIntListValueStyle(), NamedStyleConstants.HIDDEN_CATEGORY_FOR_DEPTH);
		if (values == null) {
			return false;
		}
		int maxDepth = FillingConfigurationUtils.getMaxDepthForTree(getTable());
		return values.getIntListValue().size() == (maxDepth + 1);
	}

	/**
	 *
	 * @return
	 *         <code>true</code> if all categories are currently visible
	 */
	protected boolean allCategoriesAreCurrentlyVisible() {
		Table table = getTable();
		IntListValueStyle values = (IntListValueStyle) table.getNamedStyle(NattablestylePackage.eINSTANCE.getIntListValueStyle(), NamedStyleConstants.HIDDEN_CATEGORY_FOR_DEPTH);
		return values == null || values.getIntListValue().isEmpty();
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.handler.AbstractTableHandler#computeEnable(Object)
	 *
	 * @return
	 */
	@Override
	protected boolean computeEnable(Object evaluationContext) {
		boolean calculatedValue = super.computeEnable(evaluationContext);
		if (calculatedValue) {
			calculatedValue = FillingConfigurationUtils.getAllTreeFillingConfiguration(getTable()).size() != 0;
		}
		return calculatedValue;
	}
}
