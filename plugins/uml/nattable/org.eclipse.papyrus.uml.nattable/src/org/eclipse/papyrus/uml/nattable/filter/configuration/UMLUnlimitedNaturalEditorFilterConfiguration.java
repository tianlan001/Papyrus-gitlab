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

import static org.eclipse.nebula.widgets.nattable.style.DisplayMode.NORMAL;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.validate.IDataValidator;
import org.eclipse.nebula.widgets.nattable.filterrow.TextMatchingMode;
import org.eclipse.nebula.widgets.nattable.filterrow.config.FilterRowConfigAttributes;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.nattable.filter.configuration.AbstractNaturalEditorFilterConfiguration;
import org.eclipse.papyrus.infra.nattable.filter.validator.NaturalFilterDataValidator;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.uml.nattable.utils.UMLTableUtils;
import org.eclipse.papyrus.uml.tools.utils.PrimitivesTypesUtils;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;

/**
 * Filter configuration for unlimited natural
 *
 */
public class UMLUnlimitedNaturalEditorFilterConfiguration extends AbstractNaturalEditorFilterConfiguration {

	/**
	 * the id for this editor
	 */
	private static final String ID = "org.eclipse.papyrus.uml.nattable.unlimited.natural.text";//$NON-NLS-1$

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
					if (null != type){
						return PrimitivesTypesUtils.UML_UNLIMITED_NATURAL.equals(type.getName());
					}
				}
			}
		}
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.filter.configuration.AbstractNumericEditorFilterConfiguration#configureRegistry(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry, java.lang.Object, java.lang.String, List<Object>)
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
	 * @see org.eclipse.papyrus.infra.nattable.filter.configuration.TextEditorFilterConfiguration#getDataValidator(IConfigRegistry)
	 *
	 * @return
	 */
	@Override
	protected IDataValidator getDataValidator(IConfigRegistry configRegistry) {
		return new NaturalFilterDataValidator(configRegistry.getConfigAttribute(FilterRowConfigAttributes.TEXT_DELIMITER, NORMAL), TextMatchingMode.REGULAR_EXPRESSION);
	}

	/**
	 * @return the id
	 * @deprecated since Eclipse Mars
	 */
	@Deprecated
	public static String getId() {
		return ID;
	};

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
