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

package org.eclipse.papyrus.infra.emf.nattable.filter.configuration;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.papyrus.infra.emf.utils.EMFContants;
import org.eclipse.papyrus.infra.nattable.filter.configuration.AbstractBooleanComboBoxCellEditorFilterConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.tools.util.TypesConstants;


/**
 * 
 * This class provide the configuration for boolean
 */
public class EBooleanComboBoxCellEditorFilterConfiguration extends AbstractBooleanComboBoxCellEditorFilterConfiguration {

	/**
	 * The ID for this filter configuration
	 */
	private static final String ID = "org.eclipse.papyrus.infra.emf.nattable.filter.eboolean.combo.with.NA"; //$NON-NLS-1$

	/**
	 * @see org.eclipse.papyrus.infra.nattable.filter.configuration.IFilterConfiguration#handles(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object)
	 *
	 * @param registry
	 * @param columnElement
	 * @return
	 */
	@Override
	public boolean handles(IConfigRegistry registry, Object columnElement) {
		Object object = AxisUtils.getRepresentedElement(columnElement);
		if (object instanceof EStructuralFeature) {
			EStructuralFeature feature = (EStructuralFeature) object;
			EClassifier etype = feature.getEType();
			if (etype instanceof EDataType) {
				EDataType datatype = (EDataType) etype;
				return TypesConstants.BOOLEAN.equals(datatype.getName()) || EMFContants.EBOOLEAN.equals(datatype.getName());
			}
		}
		return false;
	}




	/**
	 * @see org.eclipse.papyrus.infra.nattable.filter.configuration.IFilterConfiguration#getConfigurationId()
	 *
	 * @return
	 */
	@Override
	public String getConfigurationId() {
		return ID;
	}

}
