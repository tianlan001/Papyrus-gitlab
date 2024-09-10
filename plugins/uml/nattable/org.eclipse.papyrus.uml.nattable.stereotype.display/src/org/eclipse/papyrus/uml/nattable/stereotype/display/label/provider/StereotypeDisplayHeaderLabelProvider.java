/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.stereotype.display.label.provider;

import org.eclipse.papyrus.infra.emf.nattable.provider.EMFFeatureHeaderLabelProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.ILabelProviderContextElementWrapper;
import org.eclipse.papyrus.uml.nattable.stereotype.display.utils.StereotypeDisplayTreeTableConstants;

/**
 * The label provider for the sterotype display column header.
 */
public class StereotypeDisplayHeaderLabelProvider extends EMFFeatureHeaderLabelProvider {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractNattableCellLabelProvider#accept(java.lang.Object)
	 */
	@Override
	public boolean accept(final Object element) {

		if (element instanceof ILabelProviderContextElementWrapper) {
			Object wrappedElement = ((ILabelProviderContextElementWrapper) element).getObject();
			Object object = AxisUtils.getRepresentedElement(wrappedElement);
			if (object instanceof String) {
				if (((String) object).startsWith(StereotypeDisplayTreeTableConstants.PREFIX)) {
					return true;
				}
			}
		}

		return false;
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.emf.nattable.provider.EMFFeatureHeaderLabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(final Object element) {
		String returnedValue = ""; //$NON-NLS-1$
		final ILabelProviderContextElementWrapper wrapper = (ILabelProviderContextElementWrapper) element;

		final Object value = getWrappedValue(wrapper);
		if (value instanceof IAxis && !((IAxis) value).getAlias().isEmpty()) {
			returnedValue = ((IAxis) value).getAlias();
		} else {
			String axisValue = (String) AxisUtils.getRepresentedElement(value);
			String axisId = axisValue.substring(StereotypeDisplayTreeTableConstants.PREFIX.length());
			switch (axisId) {
			case StereotypeDisplayTreeTableConstants.IN_BRACE:
				returnedValue = StereotypeDisplayTreeTableConstants.IN_BRACE_LABEL;
				break;
			case StereotypeDisplayTreeTableConstants.IN_COMMENT:
				returnedValue = StereotypeDisplayTreeTableConstants.IN_COMMENT_LABEL;
				break;
			case StereotypeDisplayTreeTableConstants.IN_COMPARTMENT:
				returnedValue = StereotypeDisplayTreeTableConstants.IN_COMPARTMENT_LABEL;
				break;
			case StereotypeDisplayTreeTableConstants.IS_DISPLAYED:
				returnedValue = StereotypeDisplayTreeTableConstants.IS_DISPLAYED_LABEL;
				break;
			case StereotypeDisplayTreeTableConstants.NAME_DEPTH:
				returnedValue = StereotypeDisplayTreeTableConstants.NAME_DEPTH_LABEL;
				break;
			default:
				break;
			}
		}

		return returnedValue;
	}
}
