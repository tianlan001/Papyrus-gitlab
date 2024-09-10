/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.profilemigration.ui.preferences;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.papyrus.toolsmiths.profilemigration.ui.Messages;

/**
 * Definition of preference constants for the profile migration tool
 */
abstract public class ProfileMigrationPreferenceConstants {

	/**
	 * Map the preference constant and the displayed label
	 */
	public static final Map<String, String> mapPrefConstToLabel = init();


	/**
	 * @return a map with the preference constant and the displayed label
	 */
	protected static Map<String, String> init() {
		Map<String, String> map = new HashMap<>();
		map.put(ProfileMigrationPreferenceConstants.ADD_PROPERTY_TO_STEREOTYPE,
				Messages.ProfileMigrationPreferenceConstants_AddPropertyToStereotype);
		map.put(ProfileMigrationPreferenceConstants.SUPER_STEREOTYPE_BECOMING_ABSTRACT,
				Messages.ProfileMigrationPreferenceConstants_SuperStereotypeBecomingAbstract);
		map.put(ProfileMigrationPreferenceConstants.STEREOTYPE_MOVE,
				Messages.ProfileMigrationPreferenceConstants_StereotypeMove);
		map.put(ProfileMigrationPreferenceConstants.CHANGE_LOWER_MULTIPLICITY_PROPERTY,
				Messages.ProfileMigrationPreferenceConstants_ChangeLowerMultiplicityOfProperty);
		map.put(ProfileMigrationPreferenceConstants.CHANGE_UPPER_MULTIPLICITY_PROPERTY,
				Messages.ProfileMigrationPreferenceConstants_ChangeUpperMultiplicityOfProperty);
		map.put(ProfileMigrationPreferenceConstants.CHANGE_IS_STATIC_FEATURE_PROPERTY,
				Messages.ProfileMigrationPreferenceConstants_ChangeIsStaticFeatureOfProperty);
		map.put(ProfileMigrationPreferenceConstants.DELETE_ENUM_LITERAL_FROM_ENUMERATION,
				Messages.ProfileMigrationPreferenceConstants_DeleteEnumLiteralFromEnumeration);
		map.put(ProfileMigrationPreferenceConstants.PACKAGE_MOVE,
				Messages.ProfileMigrationPreferenceConstants_PackageMove);
		map.put(ProfileMigrationPreferenceConstants.PROFILE_MOVE,
				Messages.ProfileMigrationPreferenceConstants_ProfileMove);
		return map;
	}

	/**
	 * Preference constant to show AddPropertyToStereotypeDialog
	 */
	public static final String ADD_PROPERTY_TO_STEREOTYPE = "AddPropertyToStereotype"; //$NON-NLS-1$

	/**
	 * Preference constant to show ChangeIsAbstractFromStereotypeDialog
	 */
	public static final String SUPER_STEREOTYPE_BECOMING_ABSTRACT = "SuperStereotypeBecomingAbstract"; //$NON-NLS-1$

	/**
	 * Preference constant to show MoveStereotypeDialog
	 */
	public static final String STEREOTYPE_MOVE = "StereotypeMove"; //$NON-NLS-1$

	/**
	 * Preference constant to show ChangeLowerMultiplicityDialog
	 */
	public static final String CHANGE_LOWER_MULTIPLICITY_PROPERTY = "ChangeLowerMultiplicityOfProperty"; //$NON-NLS-1$

	/**
	 * Preference constant to show ChangUpperMultiplicityDialog
	 */
	public static final String CHANGE_UPPER_MULTIPLICITY_PROPERTY = "ChangeUpperMultiplicityOfProperty"; //$NON-NLS-1$

	/**
	 * Preference constant to show ChangeIsStaticFeatureDialog
	 */
	public static final String CHANGE_IS_STATIC_FEATURE_PROPERTY = "ChangeIsStaticFeatureOfProperty"; //$NON-NLS-1$

	/**
	 * Preference constant to show DeleteEnumerationLiteralFromEnumerationDialog
	 */
	public static final String DELETE_ENUM_LITERAL_FROM_ENUMERATION = "DeleteEnumLiteralFromEnumeration"; //$NON-NLS-1$

	/**
	 * Preference constant to show MovePackageDialog
	 */
	public static final String PACKAGE_MOVE = "PackageMove"; //$NON-NLS-1$

	/**
	 * Preference constant to show MoveProfileDialog
	 */
	public static final String PROFILE_MOVE = "ProfileMove"; //$NON-NLS-1$
}
