/*****************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
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

package org.eclipse.papyrus.infra.emf.nattable.provider;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EStructuralFeatureAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.ILabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.ILabelProviderCellContextElementWrapper;
import org.eclipse.papyrus.infra.nattable.utils.ILabelProviderContextElementWrapper;

/**
 * @author Vincent Lorenzo
 *
 */
public class TreeFillingFeatureLabelProvider extends EMFFeatureHeaderLabelProvider {

	/**
	 * @see org.eclipse.papyrus.infra.emf.nattable.provider.EMFFeatureHeaderLabelProvider#accept(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public boolean accept(Object element) {
		if (element instanceof ILabelProviderContextElementWrapper) {
			final Object object = ((ILabelProviderContextElementWrapper) element).getObject();
			if (object instanceof ITreeItemAxis) {
				ITreeItemAxis curr = (ITreeItemAxis) object;
				Object el = curr.getElement();
				if (el instanceof TreeFillingConfiguration) {
					final IAxis axis = ((TreeFillingConfiguration) el).getAxisUsedAsAxisProvider();
					return axis instanceof EStructuralFeature || axis instanceof EStructuralFeatureAxis;
				}
			}
		}
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.provider.AbstractNattableCellLabelProvider#getLabelConfiguration(org.eclipse.papyrus.infra.nattable.utils.LabelProviderCellContextElementWrapper)
	 *
	 * @param element
	 * @return
	 */
	@Override
	protected ILabelProviderConfiguration getLabelConfiguration(ILabelProviderCellContextElementWrapper element) {
		if (element instanceof ILabelProviderContextElementWrapper) {
			final Object object = ((ILabelProviderContextElementWrapper) element).getObject();
			if (object instanceof IAxis && ((IAxis) object).getElement() instanceof TreeFillingConfiguration) {
				return ((TreeFillingConfiguration) ((IAxis) object).getElement()).getLabelProvider();
			}
		}
		return null;
	}


	/**
	 * 
	 * @param wrapper
	 * @return
	 * 		the wrapped value to use to calculate the label
	 */
	@Override
	protected Object getWrappedValue(final ILabelProviderContextElementWrapper wrapper) {
		Object value = wrapper.getObject();
		if (value instanceof IAxis && ((IAxis) value).getElement() instanceof TreeFillingConfiguration) {
			return ((TreeFillingConfiguration) ((IAxis) value).getElement()).getAxisUsedAsAxisProvider();
		}
		return super.getWrappedValue(wrapper);
	}

}
