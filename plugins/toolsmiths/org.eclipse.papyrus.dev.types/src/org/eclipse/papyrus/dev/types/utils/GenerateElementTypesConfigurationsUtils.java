/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.dev.types.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.UMLPackage;

public class GenerateElementTypesConfigurationsUtils {



	static public Collection<EReference> findAmbiguousContainments(EReference eReference, Collection<EReference> possibleContainmentsEReference) {
		HashSet<EReference> result = new HashSet<EReference>();

		for (EReference ownerContainment : eReference.getEContainingClass().getEAllContainments()) {
			for (EReference possibleContainement : possibleContainmentsEReference) {
				if (eReference != possibleContainement) {
					if (ownerContainment.equals(possibleContainement)) {
						result.add(possibleContainement);
					}
				}
			}
		}

		return result;
	}

	static public Collection<EReference> findPossibleContainmentsEReference(EClass ownedEClass, Collection<EClass> ownerTypes) {
		HashSet<EReference> result = new HashSet<EReference>();
		for (EClass candidateOwner : ownerTypes) {
			for (EReference eReference : candidateOwner.getEAllContainments()) {
				if (isKindOf(ownedEClass, eReference.getEReferenceType())) {
					result.add(eReference);
				}
			}
		}
		return result;
	}

	static public boolean isKindOf(EClass a, EClassifier b) {
		if (a.getEAllSuperTypes().contains(b)) {
			return true;
		} else {
			return a.equals(b);
		}
	}

	static public Collection<EClass> getAllEClass(EPackage ePackage) {
		List<EClass> eClasses = new ArrayList<EClass>();
		for (EClassifier eClassifier : ePackage.getEClassifiers()) {
			if (eClassifier instanceof EClass) {
				eClasses.add((EClass) eClassifier);
			}
		}

		Collections.sort(eClasses, new EClassComparator());

		return eClasses;
	}

	static public String getIdentifier(EClass eClass) {
		return "org.eclipse.papyrus.uml." + eClass.getName();
	}

	static public String getAsName(EReference containmentEReference, EClass container) {
		return "_As_" + ((ENamedElement) container.eContainer()).getName().toUpperCase() + "::" + container.getName() + "::" + containmentEReference.getName();
	}

	static public boolean isSpecializedASpecialization(EClass eClass, EReference containmentEReference) {
		Collection<EReference> possibleContainmentsEReference = findPossibleContainmentsEReference(eClass, getAllEClass(UMLPackage.eINSTANCE));
		if (!findAmbiguousContainments(containmentEReference, possibleContainmentsEReference).isEmpty()) {
			// The SpecialiazedType is SpecializationType
			return true;
		} else {
			// The SpecialiazedType is MetamodelType
			return false;

		}
	}

	static public String findSpecializedTypesIDs(EClass eClass, EReference containmentEReference) {
		if (isSpecializedASpecialization(eClass, containmentEReference)) {
			// The SpecialiazedType is SpecializationType
			return GenerateElementTypesConfigurationsUtils.getIdentifier(eClass) + GenerateElementTypesConfigurationsUtils.getAsName(containmentEReference, eClass);
		} else {
			// The SpecialiazedType is MetamodelType
			return getIdentifier(eClass);
		}


	}
}
