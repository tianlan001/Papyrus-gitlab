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
package org.eclipse.papyrus.infra.nattable.utils;

import java.util.Comparator;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;


/**
 * The comparator used to sort IAxis
 *
 * @author Vincent Lorenzo
 *
 */
public class AxisComparator implements Comparator<IAxis> {

	/**
	 * indicates the direction of the sort
	 */
	private boolean alphabeticOrder;

	/**
	 * the config registry is used to find the label provider service
	 */
	private IConfigRegistry configRegistry;

	private LabelProviderContextElementWrapper wrapper1;
	
	private LabelProviderContextElementWrapper wrapper2;

	/**
	 *
	 * Constructor.
	 *
	 * @param alphabeticOrder
	 *            indicates the direction of the sort
	 * @param configRegistry
	 *            the config registry used by the table
	 */
	public AxisComparator(boolean alphabticOrder, final IConfigRegistry configRegistry) {
		this.alphabeticOrder = alphabticOrder;
		this.configRegistry = configRegistry;
		wrapper1 = new LabelProviderContextElementWrapper();
		wrapper2 = new LabelProviderContextElementWrapper();
		wrapper1.setConfigRegistry(configRegistry);
		wrapper2.setConfigRegistry(configRegistry);
	}

	/**
	 * Compare 2 {@link IAxis}
	 *
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 *
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	@Override
	public int compare(IAxis arg0, IAxis arg1) {
		final LabelProviderService serv = this.configRegistry.getConfigAttribute(NattableConfigAttributes.LABEL_PROVIDER_SERVICE_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.LABEL_PROVIDER_SERVICE_ID);
		wrapper1.setObject(arg0);
		wrapper2.setObject(arg1);
		final ILabelProvider provider = serv.getLabelProvider(Constants.HEADER_LABEL_PROVIDER_CONTEXT);
		
		final String str1 = provider.getText(wrapper1).replaceAll(AxisUtils.REGEX, "");// we keep only words characters (letters + numbers) + whitespace //$NON-NLS-1$
		final String str2 = provider.getText(wrapper2).replaceAll(AxisUtils.REGEX, ""); //$NON-NLS-1$
		if (this.alphabeticOrder) {
			return str1.compareToIgnoreCase(str2);
		}
		return str2.compareToIgnoreCase(str1);

	}

	/**
	 *
	 * @param serv
	 *            the label provider service
	 * @param obj
	 *            the object for which we want the displayed text
	 * @return
	 * 
	 * @Deprecated since Eclipse Mars
	 */
	@Deprecated
	protected String getText(final LabelProviderService serv, final Object obj) {
		final ILabelProvider provider = serv.getLabelProvider(Constants.HEADER_LABEL_PROVIDER_CONTEXT);
		LabelProviderContextElementWrapper wrapper = new LabelProviderContextElementWrapper();
		wrapper.setConfigRegistry(this.configRegistry);
		wrapper.setObject(obj);
		return provider.getText(wrapper);
	}
}
