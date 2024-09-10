/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, ALL4TEC and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.infra.types.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.MetamodelTypeConfiguration;
import org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration;

/**
 * Utilities Class for {@link ElementTypeConfiguration} element.
 */
public class ElementTypeConfigurationUtil {

	/**
	 * @return true element is type of type to match
	 */
	public static boolean isTypeOf(final ElementTypeConfiguration elementType, final ElementTypeConfiguration elementTypeConfiguration) {
		boolean isTypeOf = false;

		if (elementTypeConfiguration.equals(elementType)) {
			isTypeOf = true;
		}

		List<ElementTypeConfiguration> supers = getAllSpecializedTypes(elementType);
		if (supers.contains(elementTypeConfiguration)) {
			isTypeOf = true;
		}
		return isTypeOf;
	}

	/**
	 * Get all specialized types recursively.
	 * 
	 * @param elementType
	 *            the {@link ElementTypeConfiguration}
	 * @return the list of all Children of specialized type.
	 */
	public static List<ElementTypeConfiguration> getAllSpecializedTypes(final ElementTypeConfiguration elementType) {
		List<ElementTypeConfiguration> result = new ArrayList<>();

		result.add(elementType);
		if (elementType instanceof SpecializationTypeConfiguration) {
			EList<ElementTypeConfiguration> specializedTypes = ((SpecializationTypeConfiguration) elementType).getSpecializedTypes();
			if (!specializedTypes.isEmpty()) {
				for (ElementTypeConfiguration elementTypeConfiguration : specializedTypes) {
					result.addAll(getAllSpecializedTypes(elementTypeConfiguration));
				}
			}
		}
		return result;
	}

	/**
	 * Gets the EClass of the first find created semantic element.
	 * 
	 * @param elementTypesSem
	 *            the {@link SpecializationTypeConfiguration}
	 * @return the EClass founded
	 */
	public static EClass getFirstCreatedElementEClass(final SpecializationTypeConfiguration elementType) {
		EClass eClass = null;
		Optional<MetamodelTypeConfiguration> findFirst = getAllSpecializedTypes(elementType).stream()
				.filter(MetamodelTypeConfiguration.class::isInstance)
				.map(MetamodelTypeConfiguration.class::cast)
				.distinct()
				.findFirst();

		if (findFirst.isPresent()) {
			MetamodelTypeConfiguration elementTypeConfiguration = findFirst.get();
			eClass = elementTypeConfiguration.getEClass();
		}

		return eClass;
	}
}
