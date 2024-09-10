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
package org.eclipse.papyrus.infra.nattable.converter;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.convert.IDisplayConverter;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.nattable.utils.Constants;
import org.eclipse.papyrus.infra.nattable.utils.LabelProviderCellContextElementWrapper;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;

/**
 * This converter uses the label provider service to display the correct text
 *
 * @author Vincent Lorenzo
 *
 */
public class GenericDisplayConverter implements IDisplayConverter {

	/**
	 * wrapper used to get the label of the element to convert
	 */
	private LabelProviderCellContextElementWrapper contextElement = new LabelProviderCellContextElementWrapper();
	
	/**
	 * throw new UnsupportedOperationException();
	 *
	 * @see org.eclipse.nebula.widgets.nattable.data.convert.IDisplayConverter#canonicalToDisplayValue(java.lang.Object)
	 *
	 * @param canonicalValue
	 * @return
	 */
	@Override
	public Object canonicalToDisplayValue(Object canonicalValue) {
		throw new UnsupportedOperationException();
	}

	/**
	 * throw new UnsupportedOperationException();
	 *
	 * @see org.eclipse.nebula.widgets.nattable.data.convert.IDisplayConverter#displayToCanonicalValue(java.lang.Object)
	 *
	 * @param displayValue
	 * @return
	 */
	@Override
	public Object displayToCanonicalValue(Object displayValue) {
		return displayValue.toString();
	}
	

	/**
	 *
	 * @see org.eclipse.nebula.widgets.nattable.data.convert.IDisplayConverter#canonicalToDisplayValue(org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell, org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object)
	 *
	 * @param cell
	 * @param configRegistry
	 * @param canonicalValue
	 * @return
	 */
	@Override
	public Object canonicalToDisplayValue(ILayerCell cell, final IConfigRegistry configRegistry, final Object canonicalValue) {
		if (canonicalValue == null) {
			return null;
		}

		final LabelProviderService service = configRegistry.getConfigAttribute(NattableConfigAttributes.LABEL_PROVIDER_SERVICE_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.LABEL_PROVIDER_SERVICE_ID);
		final ILabelProvider labelProvider = service.getLabelProvider(Constants.TABLE_LABEL_PROVIDER_CONTEXT);
		contextElement.setConfigRegistry(configRegistry);
		contextElement.setCell(cell);
		contextElement.setObject(canonicalValue);
		Assert.isNotNull(labelProvider);
		return labelProvider.getText(contextElement);
	}

	/**
	 * throw new UnsupportedOperationException();
	 *
	 * @see org.eclipse.nebula.widgets.nattable.data.convert.IDisplayConverter#displayToCanonicalValue(org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell, org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object)
	 *
	 * @param cell
	 * @param configRegistry
	 * @param displayValue
	 * @return
	 */
	@Override
	public Object displayToCanonicalValue(ILayerCell cell, IConfigRegistry configRegistry, Object displayValue) {
		return displayValue;
	}

}
