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

import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Port;

/**
 * A util class to get element on the model ToMigrator.uml
 */
public class AutomaticModelUtil {

	public static Package getPackage(Package package_) {
		return package_.getNestedPackage("Package"); //$NON-NLS-1$
	}

	public static Class getPackage_delete_Stereotype(Package package_) {
		return (Class) getPackage(package_).getOwnedType("Package_delete_Stereotype"); //$NON-NLS-1$
	}

	public static Class getPackage_delete_Extension(Package package_) {
		return (Class) getPackage(package_).getOwnedType("Package_delete_Extension"); //$NON-NLS-1$
	}

	public static Class getPackage_move_into_profileAlreadyApply(Package package_) {
		return (Class) getPackage(package_).getOwnedType("Package_move_into_profileAlreadyApply"); //$NON-NLS-1$
	}

	public static Package getProfile(Package package_) {
		return package_.getNestedPackage("Profile"); //$NON-NLS-1$
	}

	public static Class getProfile_delete_Stereotype(Package package_) {
		return (Class) getProfile(package_).getOwnedType("Profile_delete_Stereotype"); //$NON-NLS-1$
	}

	public static Class getProfile_delete_Extension(Package package_) {
		return (Class) getProfile(package_).getOwnedType("Profile_delete_Extension"); //$NON-NLS-1$
	}

	public static Class getProfile_move_into_profileAlreadyApply(Package package_) {
		return (Class) getProfile(package_).getOwnedType("Profile_move_into_profileAlreadyApply"); //$NON-NLS-1$
	}

	public static Package getStereotype(Package package_) {
		return package_.getNestedPackage("Stereotype"); //$NON-NLS-1$
	}

	public static Class getStereotype_add_Property_default_value(Package package_) {
		return (Class) getStereotype(package_).getOwnedType("Stereotype_add_Property_default_value"); //$NON-NLS-1$
	}

	public static Class getStereotype_add_Property_noDefaultValue_optionalValue(Package package_) {
		return (Class) getStereotype(package_).getOwnedType("Stereotype_add_Property_noDefaultValue_optionalValue"); //$NON-NLS-1$
	}

	public static Class getStereotype_add_Generalization(Package package_) {
		return (Class) getStereotype(package_).getOwnedType("Stereotype_add_Generalization"); //$NON-NLS-1$
	}

	public static Class getStereotype_delete_Property(Package package_) {
		return (Class) getStereotype(package_).getOwnedType("Stereotype_delete_Property"); //$NON-NLS-1$
	}

	public static Class getStereotype_delete_Generalization(Package package_) {
		return (Class) getStereotype(package_).getOwnedType("Stereotype_delete_Generalization"); //$NON-NLS-1$
	}

	public static Class getStereotype_move_into_profileNotApplyYet(Package package_) {
		return (Class) getStereotype(package_).getOwnedType("Stereotype_move_into_profileNotApplyYet"); //$NON-NLS-1$
	}

	public static Package getProperty(Package package_) {
		return package_.getNestedPackage("Property"); //$NON-NLS-1$
	}

	public static Class getProperty_modify_isDerived_falseToTrue(Package package_) {
		return (Class) getProperty(package_).getOwnedType("Property_modify_isDerived_falseToTrue"); //$NON-NLS-1$
	}

	public static Class getProperty_modify_isUnique_falseToTrue(Package package_) {
		return (Class) getProperty(package_).getOwnedType("Property_modify_isUnique_falseToTrue"); //$NON-NLS-1$
	}

	public static Class getProperty_modify_type_isConvertible(Package package_) {
		return (Class) getProperty(package_).getOwnedType("Property_modify_type_isConvertible"); //$NON-NLS-1$
	}

	public static Port getPort1(Package package_) {
		return getProperty_modify_type_isConvertible(package_).getOwnedPort("Port1", null); //$NON-NLS-1$
	}

	public static Class getProperty_modify_type_isNotConvertible(Package package_) {
		return (Class) getProperty(package_).getOwnedType("Property_modify_type_isNotConvertible"); //$NON-NLS-1$
	}

	public static Package getGeneralization(Package package_) {
		return package_.getNestedPackage("Generalization"); //$NON-NLS-1$
	}

	public static Class getGeneralization_modify_general_olt_isKingOf_new(Package package_) {
		return (Class) getGeneralization(package_).getOwnedType("Generalization_modify_general_olt.isKingOf(new)"); //$NON-NLS-1$
	}

	public static Class getGeneralization_modify_general_new_isKingOf_old(Package package_) {
		return (Class) getGeneralization(package_).getOwnedType("Generalization_modify_general_new.isKingOf(old)"); //$NON-NLS-1$
	}

	public static Class getGeneralization_modify_general_noHierarchicalLink(Package package_) {
		return (Class) getGeneralization(package_).getOwnedType("Generalization_modify_general_noHierarchicalLink"); //$NON-NLS-1$
	}

	public static Class getGeneralization_modify_specialization1(Package package_) {
		return (Class) getGeneralization(package_).getOwnedType("Generalization_modify_specialization1"); //$NON-NLS-1$
	}

	public static Class getGeneralization_modify_specialization2(Package package_) {
		return (Class) getGeneralization(package_).getOwnedType("Generalization_modify_specialization2"); //$NON-NLS-1$
	}

	public static Class getGeneralization_move1(Package package_) {
		return (Class) getGeneralization(package_).getOwnedType("Generalization_move1"); //$NON-NLS-1$
	}

	public static Class getGeneralization_move2(Package package_) {
		return (Class) getGeneralization(package_).getOwnedType("Generalization_move2"); //$NON-NLS-1$
	}
}
