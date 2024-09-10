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
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.config;

/**
 * The cell editor configuration for the UML Stereotype multi UML string value.
 * 
 * @deprecated since 2.0, the super class works fine for UML String
 */
@Deprecated
public class UMLStereotypeMultiUMLStringCellEditorConfiguration extends MultiStringCellEditorConfiguration {

	// /**
	// * The id of this editor.
	// */
	// private static final String ID = "org.eclipse.papyrus.uml.nattable.celleditor.configuration.UMLStereotypeMultiUMLStringCellEditorConfiguration.MultiEditor";//$NON-NLS-1$
	//
	//
	// /**
	// * {@inheritDoc}
	// *
	// * @see org.eclipse.papyrus.uml.nattable.config.MultiStringCellEditorConfiguration#getConfigurationId()
	// */
	// @Override
	// public String getConfigurationId() {
	// return ID;
	// }
	//
	// /**
	// * {@inheritDoc}
	// *
	// * @see org.eclipse.papyrus.uml.nattable.config.MultiStringCellEditorConfiguration#handles(org.eclipse.papyrus.infra.nattable.model.nattable.Table, java.lang.Object)
	// */
	// @Override
	// public boolean handles(final Table table, final Object axisElement) {
	// boolean result = false;
	// final String id = AxisUtils.getPropertyId(axisElement);
	// if (id != null && id.startsWith(UMLTableUtils.PROPERTY_OF_STEREOTYPE_PREFIX)) {
	// Property prop = UMLTableUtils.getRealStereotypeProperty(table.getContext(), id);
	// if (prop != null && prop.isMultivalued()) {
	// Type type = prop.getType();
	// if (type instanceof DataType) {
	// result = type instanceof PrimitiveType && PrimitivesTypesUtils.UML_STRING.equals(type.getName());
	// }
	// }
	// }
	// return result;
	// }
}
