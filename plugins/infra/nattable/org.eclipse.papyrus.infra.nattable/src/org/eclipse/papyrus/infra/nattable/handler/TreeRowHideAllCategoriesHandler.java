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
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.IntListValueStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestyleFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.nattable.utils.FillingConfigurationUtils;
import org.eclipse.papyrus.infra.nattable.utils.NamedStyleConstants;
import org.eclipse.papyrus.infra.nattable.utils.StyleUtils;


/**
 *
 * The handler used to hide all categories
 *
 */
public class TreeRowHideAllCategoriesHandler extends AbstractTreeRowHideShowCategoryHandler {

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

		// 1. obtain the list of feature to hide
		final List<Integer> toHide = new ArrayList<>();
		int start = 0;
		if (!FillingConfigurationUtils.hasTreeFillingConfigurationForDepth(getTable(), 0)) {
			start = 1;
		}
		for (int i = start; i <= maxDepth; i++) {
			toHide.add(Integer.valueOf(i));
		}

		IntListValueStyle values = StyleUtils.getHiddenDepthsValueStyle(table);
		Command cmd;
		if (values == null) {
			values = NattablestyleFactory.eINSTANCE.createIntListValueStyle();
			values.setName(NamedStyleConstants.HIDDEN_CATEGORY_FOR_DEPTH);
			values.eSet(NattablestylePackage.eINSTANCE.getIntListValueStyle_IntListValue(), toHide);
			cmd = AddCommand.create(getTableEditingDomain(), table, NattablestylePackage.eINSTANCE.getStyledElement_Styles(), Collections.singleton(values));

			// cc.append(SetCommand.create(getTableEditingDomain(), values, NattablestylePackage.eINSTANCE.getNamedStyle_Name(), NamedStyleConstants.HIDDEN_CATEGORY_FOR_DEPTH));
		} else {
			toHide.removeAll(values.getIntListValue());
			if (toHide.isEmpty()) {
				return null;
			}

			// here we don't manage the order of the hidden category, because it is not important + to manage it we will receive more than 1 notification in UpdateTableContentListener AND we don't want that
			cmd = AddCommand.create(getTableEditingDomain(), values, NattablestylePackage.eINSTANCE.getIntListValueStyle_IntListValue(), toHide);
		}
		getTableEditingDomain().getCommandStack().execute(cmd);
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
			calculatedValue = allCategoriesAreCurrentlyHidden();
		}
		return calculatedValue;
	}

}
