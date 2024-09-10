/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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
package org.eclipse.papyrus.emf.facet.efacet.ui.internal.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrus.emf.facet.efacet.core.IFacetSetCatalogManagerFactory;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.Facet;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.FacetSet;

/**
 * @author Camille Letavernier
 *
 */
//Moved from FacetUtils to clarify
public class FacetUIUtils {

	/**
	 * This methods returns all the known (registered) eTypedElements.
	 *
	 * @param resourceSet
	 *            the eTypedElement search will be done in/using this resourceSet
	 * @return all the known (registered) eTypedElements
	 */
	public static Set<? extends ETypedElement> getETypedElements(
			final ResourceSet resourceSet) {
		final Set<ETypedElement> result = new HashSet<ETypedElement>();
		final Collection<Object> ePackages = EPackage.Registry.INSTANCE
				.values();
		for (Object object : ePackages) {
			EPackage ePackage = null;
			if (object instanceof EPackage) {
				ePackage = (EPackage) object;
			} else if (object instanceof EPackage.Descriptor) {
				final EPackage.Descriptor descriptor = (EPackage.Descriptor) object;
				ePackage = descriptor.getEPackage();
			}
			if (ePackage != null) {
				result.addAll(getETypedElements(ePackage));
			}
		}
		result.addAll(getFacetETypedElements(resourceSet));
		return result;
	}

	/**
	 * @since 0.3
	 */
	public static Set<? extends ETypedElement> getFacetETypedElements(
			final ResourceSet resourceSet) {
		final Set<ETypedElement> result = new HashSet<ETypedElement>();
		final Collection<FacetSet> facetSets = IFacetSetCatalogManagerFactory.DEFAULT
				.getOrCreateFacetSetCatalogManager(resourceSet)
				.getRegisteredFacetSets();
		for (FacetSet facetSet : facetSets) {
			result.addAll(getETypedElements(facetSet));
		}
		return result;
	}

	public static List<ETypedElement> getETypedElements(final EPackage ePackage) {
		final List<ETypedElement> result = new ArrayList<ETypedElement>();
		for (EClassifier eClassifier : ePackage.getEClassifiers()) {
			result.addAll(getETypedElements(eClassifier));
		}
		for (EPackage subPackage : ePackage.getESubpackages()) {
			result.addAll(getETypedElements(subPackage));
		}
		return result;
	}

	/**
	 * @since 0.3
	 */
	public static List<ETypedElement> getETypedElements(
			final EClassifier eClassifier) {
		final List<ETypedElement> result = new ArrayList<ETypedElement>();
		if (eClassifier instanceof Facet) {
			final Facet facet = (Facet) eClassifier;
			result.addAll(facet.getFacetElements());
			result.addAll(facet.getFacetOperations());
		} else if (eClassifier instanceof EClass) {
			final EClass eClass = (EClass) eClassifier;
			result.addAll(eClass.getEAllStructuralFeatures());
			result.addAll(eClass.getEAllOperations());
		}
		return result;
	}
	
}
