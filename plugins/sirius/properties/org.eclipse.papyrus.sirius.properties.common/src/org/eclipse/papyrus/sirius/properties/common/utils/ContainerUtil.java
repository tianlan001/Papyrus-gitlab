/*******************************************************************************
 * Copyright (c) 2022 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.common.utils;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMapUtil;

/**
 * This utility class is used to handle the addition of new elements to
 * EReferences in a similar way to how EStructuralFeature are removed or
 * modified in org.eclipse.emf.ecore.util.EcoreUtil.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 *
 */
public final class ContainerUtil {

	/**
	 * Private constructor to avoid instantiation.
	 */
	private ContainerUtil() {
	}

	/**
	 * Adds the "value" Object to the feature of "container" target.
	 * 
	 * @param container
	 *            the main target container
	 * @param eStructuralFeature
	 *            the feature of the container
	 * @param value
	 *            the object to add
	 */
	public static void addToContainer(EObject container, EStructuralFeature eStructuralFeature, Object value) {
		Objects.requireNonNull(container);
		Objects.requireNonNull(eStructuralFeature);
		if (FeatureMapUtil.isMany(container, eStructuralFeature)) {
			@SuppressWarnings("unchecked")
			List<Object> list = (List<Object>) container.eGet(eStructuralFeature);
			list.add(value);
		} else {
			container.eSet(eStructuralFeature, value);
		}
	}

	/**
	 * Copied from org.eclipse.papyrus.infra.emf.utils.EMFHelper. Return the list of
	 * EClasses that are subtypes of the given EClass
	 *
	 * @param type
	 * @param concreteClassesOnly
	 *            If true, only Concrete EClasses will be returned.
	 *            Abstract and Interface EClasses will be filtered
	 * @return The list of EClasses implementing or extending the given EClass
	 */
	public static List<EClass> getSubclassesOf(final EClass type, final boolean concreteClassesOnly) {
		Objects.requireNonNull(type);
		Set<EClass> result = new LinkedHashSet<>();
		if (!concreteClassesOnly || (!type.isAbstract() && !type.isInterface())) {
			result.add(type);
		}

		EPackage ePackage = getRootPackage(type.getEPackage());
		getSubclassesOf(type, ePackage, result, concreteClassesOnly);
		return new LinkedList<>(result.stream().sorted(Comparator.comparing(EClass::getName)).collect(Collectors.toList()));
	}

	/**
	 * Copied from org.eclipse.papyrus.infra.emf.utils.EMFHelper.
	 */
	private static void getSubclassesOf(final EClass type, final EPackage fromPackage, final Set<EClass> result,
			final boolean concreteClassesOnly) {
		for (EClassifier classifier : fromPackage.getEClassifiers()) {
			if (classifier instanceof EClass) {
				EClass eClass = (EClass) classifier;
				if (eClass.getEAllSuperTypes().contains(type)) {
					if (!concreteClassesOnly || (!eClass.isAbstract() && !eClass.isInterface())) {
						result.add(eClass);
					}
				}
			}
		}

		for (EPackage subPackage : fromPackage.getESubpackages()) {
			getSubclassesOf(type, subPackage, result, concreteClassesOnly);
		}
	}

	/**
	 * Copied from org.eclipse.papyrus.infra.emf.utils.EMFHelper. Return the root
	 * package containing the given package, or the package itself if it is already
	 * the root
	 *
	 * @param ePackage
	 * @return The Root package
	 */
	private static EPackage getRootPackage(final EPackage ePackage) {
		EPackage result = null;
		if (ePackage != null) {
			if (ePackage.getESuperPackage() == null) {
				result = ePackage;
			} else {
				result = getRootPackage(ePackage.getESuperPackage());
			}
		}
		return result;
	}
}
