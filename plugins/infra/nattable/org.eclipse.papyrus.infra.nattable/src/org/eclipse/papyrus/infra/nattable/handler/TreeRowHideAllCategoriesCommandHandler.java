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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 562864
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.IntListValueStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestyleFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.nattable.utils.FillingConfigurationUtils;
import org.eclipse.papyrus.infra.nattable.utils.NamedStyleConstants;

/**
 *
 * @deprecated since Papyrus 4.8, use TreeRowHideAllCategoriesHandler
 *
 *             this class is not used
 *             this class will be removed in Papyrus 5.0 (see bug Bug 562870)
 */
@Deprecated
public class TreeRowHideAllCategoriesCommandHandler extends AbstractTreeRowHideShowCategoryHandler {

	/**
	 *
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 *
	 * @param event
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		int maxDepth = FillingConfigurationUtils.getMaxDepthForTree(getTable());

		Table table = getTable();
		IntListValueStyle values = (IntListValueStyle) table.getNamedStyle(NattablestylePackage.eINSTANCE.getIntListValueStyle(), NamedStyleConstants.HIDDEN_CATEGORY_FOR_DEPTH);
		CompoundCommand cc = new CompoundCommand("Hide All categories Command"); //$NON-NLS-1$
		if (values == null) {
			values = NattablestyleFactory.eINSTANCE.createIntListValueStyle();
			cc.append(AddCommand.create(getTableEditingDomain(), table, NattablestylePackage.eINSTANCE.getStyledElement_Styles(), Collections.singleton(values)));
			cc.append(SetCommand.create(getTableEditingDomain(), values, NattablestylePackage.eINSTANCE.getNamedStyle_Name(), NamedStyleConstants.HIDDEN_CATEGORY_FOR_DEPTH));
		}
		final List<Integer> toHide = new ArrayList<>();
		int start = 0;
		if (!FillingConfigurationUtils.hasTreeFillingConfigurationForDepth(getTable(), 0)) {
			start = 1;
		}
		for (int i = start; i <= maxDepth; i++) {
			toHide.add(Integer.valueOf(i));
		}

		cc.append(SetCommand.create(getTableEditingDomain(), values, NattablestylePackage.eINSTANCE.getIntListValueStyle_IntListValue(), toHide));
		getTableEditingDomain().getCommandStack().execute(cc);
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.handler.AbstractTreeTableHandler#computeEnable(Object)
	 *
	 * @return
	 */
	@Override
	protected boolean computeEnable(Object evaluationContext) {
		boolean calculatedValue = super.computeEnable(evaluationContext);
		if (calculatedValue) {
			calculatedValue = !allCategoriesAreCurrentlyHidden();
		}
		return calculatedValue;
	}

}
