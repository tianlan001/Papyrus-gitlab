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

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.papyrus.infra.nattable.filter.configuration.AbstractBooleanFilterRowComboBoxCellEditorFilterConfiguration;

/**
 * This configuration provides a Combo With checkbox to edit boolean values
 *
 */
public class EBooleanFilterRowComboBoxCellEditorFilterConfiguration extends AbstractBooleanFilterRowComboBoxCellEditorFilterConfiguration {
	/**
	 * The ID for this filter configuration
	 */
	private static final String ID = "org.eclipse.papyrus.infra.emf.nattable.filter.eboolean.checkboxcombo.with.NA"; //$NON-NLS-1$

	/**
	 * An other conf used for EBoolean values
	 */
	private static final EBooleanComboBoxCellEditorFilterConfiguration conf = new EBooleanComboBoxCellEditorFilterConfiguration();

	/**
	 * @see org.eclipse.papyrus.infra.nattable.filter.configuration.IFilterConfiguration#handles(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object)
	 *
	 * @param registry
	 * @param axis
	 * @return
	 */
	@Override
	public boolean handles(IConfigRegistry registry, Object axis) {
		return conf.handles(registry, axis);
	}

	/**
	 * @see org.eclipse.papyrus.infra.emf.nattable.filter.configuration.EBooleanComboBoxCellEditorFilterConfiguration#getConfigurationId()
	 *
	 * @return
	 */
	public String getConfigurationId() {
		return ID;
	}
}
