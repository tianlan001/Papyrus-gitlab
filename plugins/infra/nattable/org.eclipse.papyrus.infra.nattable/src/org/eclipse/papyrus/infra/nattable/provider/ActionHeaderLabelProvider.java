/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.provider;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.ILabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.ObjectLabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.ActionUtils;
import org.eclipse.papyrus.infra.nattable.utils.ILabelProviderCellContextElementWrapper;
import org.eclipse.papyrus.infra.nattable.utils.ILabelProviderContextElementWrapper;

/**
 *
 * LabelProvider for action header. It does nothing expecting managing the alias of the axis.
 * It uses an ObjectLabelProviderConfiguration for configuration
 *
 * @since 6.7
 */
public class ActionHeaderLabelProvider extends AbstractNattableCellLabelProvider {

	/**
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractNattableCellLabelProvider#accept(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public boolean accept(Object element) {
		if (element instanceof ILabelProviderContextElementWrapper) {
			final ILabelProviderContextElementWrapper contextElement = (ILabelProviderContextElementWrapper) element;
			final Object value = getWrappedValue(contextElement);
			return ActionUtils.isAction(value);
		}
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractNattableCellLabelProvider#getText(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public String getText(Object element) {
		final ILabelProviderContextElementWrapper wrapper = (ILabelProviderContextElementWrapper) element;
		final Object value = getWrappedValue(wrapper);
		final IAxis axis = value instanceof IAxis ? (IAxis) value : null;
		String returnedValue = ""; //$NON-NLS-1$
		if (axis != null) {
			final String alias = ((IAxis) value).getAlias();
			ILabelProviderConfiguration conf = null;
			if (wrapper instanceof ILabelProviderCellContextElementWrapper) {
				conf = getLabelConfiguration((ILabelProviderCellContextElementWrapper) wrapper);
			}


			if (conf instanceof ObjectLabelProviderConfiguration && ((ObjectLabelProviderConfiguration) conf).isDisplayLabel()) {
				if (alias != null && !alias.isEmpty()) {
					returnedValue = alias;
				} else {
					returnedValue = ActionUtils.getAction(axis);
				}
			}
		}
		return returnedValue;

	}

	/**
	 * @param wrapper
	 * @return
	 *         the wrapped value to use to calculate the label
	 */
	protected Object getWrappedValue(final ILabelProviderContextElementWrapper wrapper) {
		return wrapper.getObject();
	}
}
