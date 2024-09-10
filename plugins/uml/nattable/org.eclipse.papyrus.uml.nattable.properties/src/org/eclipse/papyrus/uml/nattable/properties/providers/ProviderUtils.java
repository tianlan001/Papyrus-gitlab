/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent LORENZO (CEA-LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.properties.providers;

import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.MetamodelTypeConfiguration;
import org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration;

/**
 * This class provides utils method for Label and Content Provider
 *
 */
public class ProviderUtils {

	/**
	 * separator used in the display name field of the IElementType
	 */
	public static final String ELEMENT_TYPE_DISPLAY_NAME_SEPARATOR = "::"; //$NON-NLS-1$

	/**
	 * 
	 * Constructor.
	 *
	 */
	private ProviderUtils() {
		// to prevent instanciation
	}

	/**
	 * 
	 * @param element
	 *            an element type configuration
	 * @return
	 * 		the display name for the element name or an empty string
	 */
	public static String getElementTypeDisplayName(final ElementTypeConfiguration element) {
		String result = ""; //$NON-NLS-1$
		final IElementType elementType;

		if (element instanceof MetamodelTypeConfiguration) {
			elementType = ElementTypeRegistry.getInstance().getType(((MetamodelTypeConfiguration) element).getIdentifier());
		} else if (element instanceof SpecializationTypeConfiguration) {
			elementType = ElementTypeRegistry.getInstance().getType(((SpecializationTypeConfiguration) element).getIdentifier());
		} else {
			elementType = null;
		}
		if (null != elementType) {
			result = null != elementType.getDisplayName() && !elementType.getDisplayName().isEmpty() ? elementType.getDisplayName() : result;
		}

		return result;
	}

	/**
	 * 
	 * @param configuration
	 *            a configuration
	 * @return
	 * 		the display name to use for the configuration or an empty string
	 */
	public static final String getNameToDisplay(final ElementTypeConfiguration configuration) {
		final String displayName = ProviderUtils.getElementTypeDisplayName(configuration);
		final String[] res = displayName.split(ELEMENT_TYPE_DISPLAY_NAME_SEPARATOR);
		String metamodelName = null;
		if (res.length == 2) {
			metamodelName = res[1];
		}
		String returnedValue = ""; //$NON-NLS-1$
		if (null != metamodelName && !metamodelName.isEmpty()) {
			returnedValue = metamodelName;
		} else if (null != configuration.getName() && !configuration.getName().isEmpty()) {
			returnedValue = configuration.getName();
		}
		return returnedValue;
	}

}
