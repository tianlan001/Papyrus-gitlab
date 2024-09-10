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
package org.eclipse.papyrus.toolsmiths.profilemigration.internal.utils;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;

/**
 * A class containing static utility method regarding UML profiles
 */
public class ProfileUtil {

	/**
	 * find in every profile applied to umlPackage every sub stereotypes of parentStereotype
	 * 
	 * @param parentStereotype
	 *            the referenced stereotype
	 * @param umlPackage
	 *            the package on which profiles are applied
	 * @param concreteOnly
	 *            true if you want only concrete stereotype as result, false otherwise
	 * @return the list of sub-stereotype of parentStereotype
	 */
	public static List<Stereotype> findAllSubStereotypes(Stereotype parentStereotype, Package umlPackage, boolean concreteOnly) {
		Collection<Stereotype> result = new LinkedHashSet<>();

		for (Profile profile : umlPackage.getAllAppliedProfiles()) {
			List<Stereotype> allStereotypes = new LinkedList<>();
			findAllStereotypes(profile, allStereotypes);
			for (Stereotype stereotype : allStereotypes) {
				if (concreteOnly && stereotype.isAbstract()) {
					continue; // Skip abstract stereotypes
				}

				if (isSubStereotype(parentStereotype, stereotype)) {
					result.add(stereotype);
				}
			}

		}

		return new LinkedList<>(result);
	}

	/**
	 * check if childStereotype is a sub-stereotype of parentStereotype
	 * 
	 * @param parentStereotype
	 * @param childStereotype
	 * @return true if childStereotype is a sub-stereotype of parentStereotype, false otherwise
	 */
	private static boolean isSubStereotype(Stereotype parentStereotype, Stereotype childStereotype) {
		if (parentStereotype == childStereotype) {
			return true;
		}
		if (childStereotype.getGenerals().contains(parentStereotype)) {
			return true;
		} else {
			for (Classifier general : childStereotype.getGenerals()) {
				if (general instanceof Stereotype) {
					return isSubStereotype(parentStereotype, (Stereotype) general);
				}
			}
		}
		return false;
	}

	/**
	 * Finds recursively all stereotypes contained in this profile
	 *
	 * @param profile
	 *
	 * @param result
	 */
	public static void findAllStereotypes(Package profile, List<Stereotype> result) {
		for (Stereotype stereotype : profile.getOwnedStereotypes()) {
			result.add(stereotype);
		}
		for (Package subPackage : profile.getNestedPackages()) {
			Package subProfile = subPackage;
			findAllStereotypes(subProfile, result);
		}
	}

	/**
	 * Retrieves the nearest profile that owns (either directly or indirectly) this element, or the element itself (if it is a profile)
	 *
	 * @param element
	 * @return the nearest profile that owns this element
	 */
	public static Profile getNearestProfile(Element element) {
		Package package_ = element.getNearestPackage();
		if (package_ instanceof Profile) {
			return (Profile) package_;
		}
		return getNearestProfile(package_);
	}

}
