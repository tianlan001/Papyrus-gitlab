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

package org.eclipse.papyrus.infra.nattable.comparator;


import java.text.Collator;
import java.util.Comparator;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.papyrus.infra.nattable.utils.Constants;
import org.eclipse.papyrus.infra.nattable.utils.LabelProviderFullCellContextElementWrapper;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.infra.nattable.utils.SortLabelProviderFullCellContextElementWrapper;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;

/**
 * This comparator is used to sort rows selected column header. This comparator uses the text returned by the label provider used by the table
 *
 */
public class TableCellLabelProviderComparator implements Comparator<SortLabelProviderFullCellContextElementWrapper> {

	/**
	 * the label stack to use for the comparison
	 */
	private final LabelStack stack;

	/**
	 * the wrapper used for the first value
	 */
	private final LabelProviderFullCellContextElementWrapper wrapper1;

	/**
	 * the wrapper used for the second value
	 */
	private final LabelProviderFullCellContextElementWrapper wrapper2;

	/**
	 * Constructor.
	 *
	 */
	public TableCellLabelProviderComparator() {
		this.stack = new LabelStack(GridRegion.BODY);
		this.wrapper1 = new LabelProviderFullCellContextElementWrapper();
		this.wrapper2 = new LabelProviderFullCellContextElementWrapper();
		this.wrapper1.setConfigLabels(this.stack);
		this.wrapper2.setConfigLabels(this.stack);
	}

	/**
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 *
	 * @param sortWrapper
	 * @param sortWrapper2
	 * @return
	 */
	@Override
	public int compare(SortLabelProviderFullCellContextElementWrapper sortWrapper1, SortLabelProviderFullCellContextElementWrapper sortWrapper2) {
		final IConfigRegistry configRegistry = sortWrapper1.getConfigRegistry();
		LabelProviderService serv = configRegistry.getConfigAttribute(NattableConfigAttributes.LABEL_PROVIDER_SERVICE_CONFIG_ATTRIBUTE, org.eclipse.nebula.widgets.nattable.style.DisplayMode.NORMAL, NattableConfigAttributes.LABEL_PROVIDER_SERVICE_ID);

		final String txt1 = serv.getLabelProvider(Constants.TABLE_LABEL_PROVIDER_CONTEXT).getText(sortWrapper1);
		final String txt2 = serv.getLabelProvider(Constants.TABLE_LABEL_PROVIDER_CONTEXT).getText(sortWrapper2);

		int res = Collator.getInstance().compare(txt1, txt2);
		return res;
	}

}
