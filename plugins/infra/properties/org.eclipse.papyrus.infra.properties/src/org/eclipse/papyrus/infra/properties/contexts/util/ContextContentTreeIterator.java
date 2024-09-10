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
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EClassImpl;
import org.eclipse.emf.ecore.util.EContentsEList;
import org.eclipse.emf.ecore.util.EcoreUtil.ContentTreeIterator;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrus.infra.properties.contexts.ContextsPackage;
import org.eclipse.papyrus.infra.properties.contexts.Section;

/**
 * A custom content tree iterator that traverses the {@link Section#getWidget() Section::widget}
 * reference as pseudo-containment.
 */
public class ContextContentTreeIterator extends ContentTreeIterator<EObject> {

	private static final long serialVersionUID = 1L;

	public ContextContentTreeIterator(Collection<?> emfObjects) {
		super(emfObjects);
	}

	public ContextContentTreeIterator(Object object, boolean isResolveProxies) {
		super(object, isResolveProxies);
	}

	/**
	 * @see org.eclipse.emf.ecore.util.EcoreUtil.ContentTreeIterator#getEObjectChildren(org.eclipse.emf.ecore.EObject)
	 *
	 * @param eObject
	 * @return
	 */
	@Override
	protected Iterator<? extends EObject> getEObjectChildren(EObject eObject) {
		if (ContextsPackage.Literals.SECTION.isInstance(eObject)) {
			InternalEList<EObject> containments = new SectionContentsEList(eObject);
			return isResolveProxies() ? containments.iterator() : containments.basicIterator();
		} else {
			return super.getEObjectChildren(eObject);
		}
	}

	//
	// Nested types
	//

	private static final class SectionContentsEList extends EContentsEList<EObject> {

		/**
		 * Constructor.
		 *
		 * @param eObject
		 */
		public SectionContentsEList(EObject eObject) {
			super(eObject, containmentFeatures(eObject));
		}

		private static EStructuralFeature[] containmentFeatures(EObject eObject) {
			List<EStructuralFeature> containments = new ArrayList<>(Arrays.asList(((EClassImpl.FeatureSubsetSupplier) eObject.eClass().getEAllStructuralFeatures()).containments()));

			if (ContextsPackage.Literals.SECTION.isInstance(eObject)) {
				// This is logically a containment reference (albeit cross-resource containment). In any case,
				// it is the purpose of this class to ignore this reference
				containments.add(ContextsPackage.Literals.SECTION__WIDGET);
			}

			return containments.toArray(EStructuralFeature[]::new);
		}

	}
}
