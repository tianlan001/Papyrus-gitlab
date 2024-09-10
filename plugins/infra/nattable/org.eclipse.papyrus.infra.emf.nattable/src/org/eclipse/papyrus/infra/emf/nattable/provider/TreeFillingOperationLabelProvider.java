/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
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

package org.eclipse.papyrus.infra.emf.nattable.provider;

import org.eclipse.emf.ecore.EOperation;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EOperationAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.ILabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.ILabelProviderCellContextElementWrapper;
import org.eclipse.papyrus.infra.nattable.utils.ILabelProviderContextElementWrapper;

/**
 * The label provider for the tree filling operation
 */
public class TreeFillingOperationLabelProvider extends EMFEOperationHeaderLabelProvider {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.emf.nattable.provider.EMFEOperationHeaderLabelProvider#accept(java.lang.Object)
	 */
	@Override
	public boolean accept(final Object element) {
		boolean result = false;
		if (element instanceof ILabelProviderContextElementWrapper) {
			final Object object = ((ILabelProviderContextElementWrapper) element).getObject();
			if (object instanceof ITreeItemAxis) {
				ITreeItemAxis curr = (ITreeItemAxis) object;
				Object el = curr.getElement();
				if (el instanceof TreeFillingConfiguration) {
					final IAxis axis = ((TreeFillingConfiguration) el).getAxisUsedAsAxisProvider();
					result = axis instanceof EOperation || axis instanceof EOperationAxis;
				}
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractNattableCellLabelProvider#getLabelConfiguration(org.eclipse.papyrus.infra.nattable.utils.LabelProviderCellContextElementWrapper)
	 */
	@Override
	protected ILabelProviderConfiguration getLabelConfiguration(final ILabelProviderCellContextElementWrapper element) {
		ILabelProviderConfiguration result = null;
		if (element instanceof ILabelProviderContextElementWrapper) {
			final Object object = ((ILabelProviderContextElementWrapper) element).getObject();
			if (object instanceof IAxis && ((IAxis) object).getElement() instanceof TreeFillingConfiguration) {
				result = ((TreeFillingConfiguration) ((IAxis) object).getElement()).getLabelProvider();
			}
		}
		return result;
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.emf.nattable.provider.EMFEOperationHeaderLabelProvider#getWrappedValue(org.eclipse.papyrus.infra.nattable.utils.ILabelProviderContextElementWrapper)
	 */
	@Override
	protected Object getWrappedValue(final ILabelProviderContextElementWrapper wrapper) {
		Object result = null;
		Object value = wrapper.getObject();
		if (value instanceof IAxis && ((IAxis) value).getElement() instanceof TreeFillingConfiguration) {
			result = ((TreeFillingConfiguration) ((IAxis) value).getElement()).getAxisUsedAsAxisProvider();
		}
		return null != result ? result : super.getWrappedValue(wrapper);
	}

}
