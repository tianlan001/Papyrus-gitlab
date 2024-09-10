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
package org.eclipse.papyrus.toolsmiths.profilemigration.tests.automatic;

import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;

/**
 * A util class to get element on the model Test.profile.uml
 */
public class AutomaticProfileUtil {
	public static Package getPackage(Profile profile) {
		return profile.getNestedPackage("Package"); //$NON-NLS-1$
	}

	public static Package getPackage_Package_appliedProfile(Profile profile) {
		return getPackage(profile).getNestedPackage("Package_appliedProfile"); //$NON-NLS-1$
	}

	public static Stereotype getPackage_move_into_profileAlreadyApply(Profile profile) {
		return (Stereotype) getPackage_Package_appliedProfile(profile).getOwnedType("Package_move_into_profileAlreadyApply"); //$NON-NLS-1$
	}

	public static Package getProfile(Profile profile) {
		return profile.getNestedPackage("Profile"); //$NON-NLS-1$
	}

	public static Package getProfile_Profile_alreadyApplied(Profile profile) {
		return getProfile(profile).getNestedPackage("Profile_alreadyApplied"); //$NON-NLS-1$
	}

	public static Stereotype getProfile_move_into_profileAlreadyApply(Profile profile) {
		return (Stereotype) getProfile_Profile_alreadyApplied(profile).getOwnedType("Profile_move_into_profileAlreadyApply"); //$NON-NLS-1$
	}

	public static Package getStereotype(Profile profile) {
		return profile.getNestedPackage("Stereotype"); //$NON-NLS-1$
	}

	public static Stereotype getStereotype_add_Property_default_value(Profile profile) {
		return (Stereotype) getStereotype(profile).getOwnedType("Stereotype_add_Property_default_value"); //$NON-NLS-1$
	}

	public static Stereotype getStereotype_add_Property_noDefaultValue_optionalValue(Profile profile) {
		return (Stereotype) getStereotype(profile).getOwnedType("Stereotype_add_Property_noDefaultValue_optionalValue"); //$NON-NLS-1$
	}

	public static Stereotype getStereotype_add_Generalization(Profile profile) {
		return (Stereotype) getStereotype(profile).getOwnedType("Stereotype_add_Generalization"); //$NON-NLS-1$
	}

	public static Stereotype getStereotype_delete_Property(Profile profile) {
		return (Stereotype) getStereotype(profile).getOwnedType("Stereotype_delete_Property"); //$NON-NLS-1$
	}

	public static Stereotype getStereotype_delete_Generalization(Profile profile) {
		return (Stereotype) getStereotype(profile).getOwnedType("Stereotype_delete_Generalization"); //$NON-NLS-1$
	}

	public static Package getProperty(Profile profile) {
		return profile.getNestedPackage("Property"); //$NON-NLS-1$
	}

	public static Stereotype getProperty_modify_isDerived_falseToTrue(Profile profile) {
		return (Stereotype) getProperty(profile).getOwnedType("Property_modify_isDerived_falseToTrue"); //$NON-NLS-1$
	}

	public static Stereotype getProperty_modify_isUnique_falseToTrue(Profile profile) {
		return (Stereotype) getProperty(profile).getOwnedType("Property_modify_isUnique_falseToTrue"); //$NON-NLS-1$
	}

	public static Stereotype getProperty_modify_type_isConvertible(Profile profile) {
		return (Stereotype) getProperty(profile).getOwnedType("Property_modify_type_isConvertible"); //$NON-NLS-1$
	}

	public static Stereotype getProperty_modify_type_isNotConvertible(Profile profile) {
		return (Stereotype) getProperty(profile).getOwnedType("Property_modify_type_isNotConvertible"); //$NON-NLS-1$
	}

	public static Package getGeneralization(Profile profile) {
		return profile.getNestedPackage("Generalization"); //$NON-NLS-1$
	}

	public static Stereotype getGeneralization_modify_general_olt_isKingOf_new(Profile profile) {
		return (Stereotype) getGeneralization(profile).getOwnedType("Generalization_modify_general_olt.isKingOf(new)"); //$NON-NLS-1$
	}

	public static Stereotype getGeneralization_modify_general_new_isKingOf_old(Profile profile) {
		return (Stereotype) getGeneralization(profile).getOwnedType("Generalization_modify_general_new.isKingOf(old)"); //$NON-NLS-1$
	}

	public static Stereotype getGeneralization_modify_general_noHierarchicalLink(Profile profile) {
		return (Stereotype) getGeneralization(profile).getOwnedType("Generalization_modify_general_noHierarchicalLink"); //$NON-NLS-1$
	}

	public static Stereotype getGeneralization_modify_specialization1(Profile profile) {
		return (Stereotype) getGeneralization(profile).getOwnedType("Generalization_modify_specialization1"); //$NON-NLS-1$
	}

	public static Stereotype getGeneralization_modify_specialization2(Profile profile) {
		return (Stereotype) getGeneralization(profile).getOwnedType("Generalization_modify_specialization2"); //$NON-NLS-1$
	}

	public static Stereotype getGeneralization_move1(Profile profile) {
		return (Stereotype) getGeneralization(profile).getOwnedType("Generalization_move1"); //$NON-NLS-1$
	}

	public static Stereotype getGeneralization_move2(Profile profile) {
		return (Stereotype) getGeneralization(profile).getOwnedType("Generalization_move2"); //$NON-NLS-1$
	}
}
