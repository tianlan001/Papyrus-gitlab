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

package org.eclipse.papyrus.infra.emf.utils;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <p>
 * An analogue of the {@link EcoreUtil.ExternalCrossReferencer} that considers only <em>internal</em>
 * cross-references, between elements of the graphs being searched for cross-references.
 * </p>
 * <p>
 * <strong>Note</strong> that, by design, this class will scan an entire graph of EMF objects
 * from top to bottom. In environments such as CDO where the content of the graph is not all
 * loaded in memory at all times and where filling out the graph could be expensive, it may
 * be advisable at least to derive a customization of this class to reduce its scope or (more
 * likely) the framework in question has native solutions that are more appropriate and
 * perhaps more capable.
 * </p>
 *
 * @since 4.2
 */
@SuppressWarnings("serial")
public class InternalCrossReferencer extends EcoreUtil.CrossReferencer {

	/**
	 * Initializes me with the given scope of EMF objects to search for cross-references.
	 * These can be {@link EObject}s, {@link Resource}s, {@link ResourceSet}s, or any mix thereof.
	 *
	 * @param emfObjects
	 *            EMF objects to scan for cross-references
	 */
	protected InternalCrossReferencer(Collection<?> emfObjects) {
		super(emfObjects);
	}

	/**
	 * Initializes me with an object whose content tree to scan for cross-references.
	 *
	 * @param eObject
	 *            a content tree to scan for cross-references
	 */
	protected InternalCrossReferencer(EObject eObject) {
		super(eObject);
	}

	/**
	 * Initializes me with a resource whose content tree to scan for cross-references.
	 *
	 * @param resource
	 *            a resource to scan for cross-references
	 */
	protected InternalCrossReferencer(Resource resource) {
		super(resource);
	}

	/**
	 * Initializes me with a resource set whose content tree to scan for cross-references.
	 *
	 * @param resourceSet
	 *            a resource set to scan for cross-references
	 */
	protected InternalCrossReferencer(ResourceSet resourceSet) {
		super(resourceSet);
	}

	@Override
	protected boolean crossReference(EObject eObject, EReference eReference, EObject crossReferencedEObject) {
		return EcoreUtil.isAncestor(emfObjects, crossReferencedEObject); // Opposite of ExternalCrossReferencer criterion
	}

	/**
	 * Compute a map of all cross references that are strictly internal to the given content tree.
	 * The given scope of EMF objects can be {@link EObject}s, {@link Resource}s,
	 * {@link ResourceSet}s, or any mix thereof.
	 *
	 * @param emfObjects
	 *            EMF objects to scan for cross-references
	 * @return a map of internal cross-references
	 */
	public static Map<EObject, Collection<EStructuralFeature.Setting>> find(Collection<?> emfObjects) {
		return new InternalCrossReferencer(emfObjects).findInternalCrossReferences();
	}

	/**
	 * Compute a map of all cross references that are strictly internal to the given content tree.
	 *
	 * @param eObject
	 *            a content tree to scan for cross-references
	 * @return a map of internal cross-references
	 */
	public static Map<EObject, Collection<EStructuralFeature.Setting>> find(EObject eObject) {
		return new InternalCrossReferencer(eObject).findInternalCrossReferences();
	}

	/**
	 * Compute a map of all cross references that are strictly internal to the given resource.
	 *
	 * @param resource
	 *            a resource to scan for cross-references
	 * @return a map of internal cross-references
	 */
	public static Map<EObject, Collection<EStructuralFeature.Setting>> find(Resource resource) {
		return new InternalCrossReferencer(resource).findInternalCrossReferences();
	}

	/**
	 * Compute a map of all cross references that are strictly internal to the given resource set.
	 *
	 * @param eObject
	 *            a resource set to scan for cross-references
	 * @return a map of internal cross-references
	 */
	public static Map<EObject, Collection<EStructuralFeature.Setting>> find(ResourceSet resourceSet) {
		return new InternalCrossReferencer(resourceSet).findInternalCrossReferences();
	}

	/**
	 * Compute the internal cross-references.
	 *
	 * @return a map of internal cross-references
	 */
	protected Map<EObject, Collection<EStructuralFeature.Setting>> findInternalCrossReferences() {
		crossReference();
		done();

		return this;
	}

}
