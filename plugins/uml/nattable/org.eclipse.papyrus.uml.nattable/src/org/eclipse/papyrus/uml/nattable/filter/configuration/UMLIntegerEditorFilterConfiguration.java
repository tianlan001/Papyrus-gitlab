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

package org.eclipse.papyrus.uml.nattable.filter.configuration;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.emf.utils.EMFContants;
import org.eclipse.papyrus.infra.nattable.filter.configuration.AbstractIntegerEditorFilterConfiguration;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.uml.nattable.utils.UMLTableUtils;
import org.eclipse.papyrus.uml.tools.utils.PrimitivesTypesUtils;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

/**
 * @author Vincent Lorenzo
 *
 */
public class UMLIntegerEditorFilterConfiguration extends AbstractIntegerEditorFilterConfiguration {

	/**
	 * the id for this editor
	 */
	private static final String ID = "org.eclipse.papyrus.uml.nattable.integer.text";//$NON-NLS-1$

	/**
	 * @see org.eclipse.papyrus.infra.nattable.filter.configuration.TextEditorFilterConfiguration#handles(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object)
	 *
	 * @param registry
	 * @param columnElement
	 * @return
	 */
	@Override
	public boolean handles(IConfigRegistry registry, Object columnElement) {
		Object object = AxisUtils.getRepresentedElement(columnElement);
		if (object instanceof String) {
			String string = (String) object;
			if (string.startsWith(UMLTableUtils.PROPERTY_OF_STEREOTYPE_PREFIX)) {
				INattableModelManager manager = registry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
				Table table = manager.getTable();
				final Property prop = UMLTableUtils.getRealStereotypeProperty(table.getContext(), AxisUtils.getPropertyId(string));
				if (prop != null) {
					Type type = prop.getType();
					if (null != type) {
						return PrimitivesTypesUtils.UML_INTEGER.equals(type.getName()) || EMFContants.EINT.equals(type.getName());
					}
				}
			}
		}
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.filter.configuration.AbstractIntegerEditorFilterConfiguration#configureRegistry(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object, java.lang.String, List<Object>)
	 *
	 * @param configRegistry
	 * @param configLabel
	 * @param columnElement
	 */
	@Override
	public void configureFilter(IConfigRegistry configRegistry, Object columnElement, String configLabel) {
		super.configureFilter(configRegistry, columnElement, configLabel);
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.filter.configuration.TextEditorFilterConfiguration#getConfigurationId()
	 *
	 * @return
	 */
	@Override
	public String getConfigurationId() {
		return ID;
	}
}
