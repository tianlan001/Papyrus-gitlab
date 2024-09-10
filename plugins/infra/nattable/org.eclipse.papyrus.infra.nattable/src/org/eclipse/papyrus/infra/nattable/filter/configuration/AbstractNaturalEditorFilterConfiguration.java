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

package org.eclipse.papyrus.infra.nattable.filter.configuration;

import static org.eclipse.nebula.widgets.nattable.style.DisplayMode.NORMAL;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.validate.IDataValidator;
import org.eclipse.nebula.widgets.nattable.filterrow.TextMatchingMode;
import org.eclipse.nebula.widgets.nattable.filterrow.config.FilterRowConfigAttributes;
import org.eclipse.papyrus.infra.nattable.filter.validator.NaturalFilterDataValidator;

/**
 * abstract class used to filter natural numbers
 */
public class AbstractNaturalEditorFilterConfiguration extends AbstractIntegerEditorFilterConfiguration {

	/**
	 * @see org.eclipse.papyrus.infra.nattable.filter.configuration.TextEditorFilterConfiguration#getDataValidator(IConfigRegistry)
	 *
	 * @return
	 */
	@Override
	protected IDataValidator getDataValidator(IConfigRegistry configRegistry) {
		return new NaturalFilterDataValidator(configRegistry.getConfigAttribute(FilterRowConfigAttributes.TEXT_DELIMITER, NORMAL), TextMatchingMode.REGULAR_EXPRESSION);
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.filter.configuration.TextEditorFilterConfiguration#getConfigurationDescription()
	 *
	 * @return
	 */
	public String getConfigurationDescription() {
		return "This editor allows to filter natural values, using >, >=, <, <=, =, <>"; //$NON-NLS-1$
	}
}
