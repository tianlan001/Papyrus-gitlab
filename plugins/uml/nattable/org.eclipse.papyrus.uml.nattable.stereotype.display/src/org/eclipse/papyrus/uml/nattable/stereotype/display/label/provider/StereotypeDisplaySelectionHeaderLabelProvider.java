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

import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.provider.AbstractNattableCellLabelProvider;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.ILabelProviderContextElementWrapper;
import org.eclipse.papyrus.uml.nattable.stereotype.display.utils.StereotypeDisplayTreeTableConstants;

/**
 * The label provider for the selection in the stereotype display tree table.
 */
public class StereotypeDisplaySelectionHeaderLabelProvider extends AbstractNattableCellLabelProvider {

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
			if (object instanceof TreeFillingConfiguration) {
				IAxis axis = ((TreeFillingConfiguration) object).getAxisUsedAsAxisProvider();
				Object representedelement = AxisUtils.getRepresentedElement(axis);
				if(representedelement instanceof String){
					if (((String) representedelement).startsWith(StereotypeDisplayTreeTableConstants.GMF_CUSTOM_PREFIX)) {
						return true;
					}
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
		String result = ""; //$NON-NLS-1$
		final ILabelProviderContextElementWrapper wrapper = (ILabelProviderContextElementWrapper) element;
		
		Object object = wrapper.getObject();
		if (object instanceof ITreeItemAxis) {
			object = AxisUtils.getRepresentedElement(object);
			if (object instanceof TreeFillingConfiguration) {
				IAxis axis = ((TreeFillingConfiguration) object).getAxisUsedAsAxisProvider();
				if (axis instanceof IAxis && !((IAxis) axis).getAlias().isEmpty()) {
					result = ((IAxis) axis).getAlias();
				}else{
					Object representedelement = AxisUtils.getRepresentedElement(axis);
					if(representedelement instanceof String){
						if (((String) representedelement).startsWith(StereotypeDisplayTreeTableConstants.GMF_CUSTOM_PREFIX) && ((String) representedelement).endsWith(StereotypeDisplayTreeTableConstants.SELECTION)){
							result = "Selection"; //$NON-NLS-1$
						}
					}
				}
					
			}
		}

		return result;
	}
	
}
