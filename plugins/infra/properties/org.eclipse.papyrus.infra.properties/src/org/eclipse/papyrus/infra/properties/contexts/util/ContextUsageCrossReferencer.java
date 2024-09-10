/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.properties.contexts.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.impl.EClassImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentsEList.FeatureIterator;
import org.eclipse.emf.ecore.util.ECrossReferenceEList;
import org.eclipse.emf.ecore.util.EcoreUtil.UsageCrossReferencer;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrus.infra.properties.contexts.ContextsPackage;

/**
 * A usage cross-referencer for <em>Properties Context</em> models that ignores the
 * XWT sections.
 */
public class ContextUsageCrossReferencer extends UsageCrossReferencer {

	private static final long serialVersionUID = -3574652001979900132L;

	public ContextUsageCrossReferencer(Collection<?> emfObjects) {
		super(emfObjects);
	}

	public ContextUsageCrossReferencer(EObject eObject) {
		super(eObject);
	}

	public ContextUsageCrossReferencer(Resource resource) {
		super(resource);
	}

	public ContextUsageCrossReferencer(ResourceSet resourceSet) {
		super(resourceSet);
	}

	@Override
	public Collection<Setting> findUsage(EObject eObject) {
		return super.findUsage(eObject);
	}

	@Override
	public Map<EObject, Collection<Setting>> findAllUsage(Collection<?> eObjectsOfInterest) {
		return super.findAllUsage(eObjectsOfInterest);
	}

	@Override
	protected FeatureIterator<EObject> getCrossReferences(EObject eObject) {
		if (ContextsPackage.Literals.SECTION.isInstance(eObject)) {
			InternalEList<EObject> crossReferences = new SectionCrossReferenceEList(eObject);
			return (FeatureIterator<EObject>) (resolve() ? crossReferences.iterator() : crossReferences.basicIterator());
		} else {
			return super.getCrossReferences(eObject);
		}
	}

	//
	// Nested types
	//

	private static final class SectionCrossReferenceEList extends ECrossReferenceEList<EObject> {

		/**
		 * Constructor.
		 *
		 * @param eObject
		 */
		public SectionCrossReferenceEList(EObject eObject) {
			super(eObject, crossReferenceFeatures(eObject));
		}

		private static EStructuralFeature[] crossReferenceFeatures(EObject eObject) {
			List<EStructuralFeature> crossReferences = new ArrayList<>(Arrays.asList(((EClassImpl.FeatureSubsetSupplier) eObject.eClass().getEAllStructuralFeatures()).crossReferences()));

			// This is logically a containment reference (albeit cross-resource containment). In any case,
			// it is the purpose of this class to ignore this reference
			crossReferences.remove(ContextsPackage.Literals.SECTION__WIDGET);

			return crossReferences.toArray(EStructuralFeature[]::new);
		}

	}

}
