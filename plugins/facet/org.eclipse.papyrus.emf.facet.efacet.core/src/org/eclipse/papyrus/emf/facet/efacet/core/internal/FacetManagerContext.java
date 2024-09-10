/*******************************************************************************
 * Copyright (c) 2011, 2014 Mia-Software, CEA, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Olivier Remaud (Soft-Maint) - Bug 361794 - [Restructuring] EMF Facet customization meta-model
 *     Gregoire Dupe (Mia-Software) - Bug 364325 - [Restructuring] The user must be able to navigate into a model using the Facet.
 *     Gregoire Dupe (Mia-Software) - Bug 361794 - [Restructuring] EMF Facet customization meta-model
 *     Gregoire Dupe (Mia-Software) - Bug 373078 - API Cleaning
 *     Gregoire Dupe (Mia-Software) - Bug 375087 - [Table] ITableWidget.addColumn(List<ETypedElement>, List<FacetSet>)
 *     Gregoire Dupe (Mia-Software) - Bug 372626 - Aggregates
 *     Gregoire Dupe (Mia-Software) - Bug 377178 - [EFacet] infinite recursion in override resolution
 *     Gregoire Dupe (Mia-software) - Bug 383418 - [Table] FacetManagerContext.getOverrideCandidateFeatures(...) is empty
 *     Gregoire Dupe (Mia-software) - Bug 420093 - [EFacet] The facetManger list doesn't deal with uniqueness
 *     Thomas Cicognani (Soft-Maint) - Bug 420193 - Listener on FacetManager
 *     Fabien Treguer (Soft-Maint) - Bug 423285 - [Table] FacetSets not stored in a resource cause model manager crashes
 *     Christian W. Damus (CEA) - Bug 441857 - [Performances - Model Explorer] Severe performance problems for larger models
 *******************************************************************************/

package org.eclipse.papyrus.emf.facet.efacet.core.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.emf.facet.efacet.core.FacetUtils;
import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManagerListener;
import org.eclipse.papyrus.emf.facet.efacet.core.exception.FacetManagerException;
import org.eclipse.papyrus.emf.facet.efacet.core.internal.FacetManager.ConformanceState;
import org.eclipse.papyrus.emf.facet.efacet.core.internal.exception.FacetConformanceEvaluationException;
import org.eclipse.papyrus.emf.facet.efacet.core.internal.exception.UnmatchingExpectedTypeException;
import org.eclipse.papyrus.emf.facet.efacet.core.internal.exported.IResolverManager;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.DerivedTypedElement;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.Facet;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.FacetSet;
import org.eclipse.papyrus.emf.facet.util.core.Logger;
import org.eclipse.papyrus.emf.facet.util.core.internal.exported.ListUtils;
import org.eclipse.papyrus.emf.facet.util.emf.core.ModelUtils;

/**
 * @author oremaud
 *
 *         FacetManager Context
 *
 *         Defines which FacetSets will be taken into account, and in which
 *         order ('front' FacetSets have higher precedence)
 *
 *         Responsible of overrides resolution for Facets and Customs
 */
class FacetManagerContext implements List<FacetSet> {

	private static final String SILENT_OPTION = "org.eclipse.papyrus.emf.facet.efacet.core.internal.FacetManagerContext.getOverrideCandidateFeatures.silent"; //$NON-NLS-1$
	private static final boolean SILENT = Boolean
			.getBoolean(FacetManagerContext.SILENT_OPTION);

	// We cannot use the interface (i.e, List) instead because we need to use
	// the methods addLast and addFirst
	private final EList<FacetSet> managedFacetSets = new BasicEList<FacetSet>() {
		private static final long serialVersionUID = 1L;

		@Override
		protected void didRemove(int index, FacetSet oldObject) {
			unconfigure(oldObject);
		}

		@Override
		protected void didAdd(int index, FacetSet newObject) {
			configure(newObject);
		}

		@Override
		protected void didSet(int index, FacetSet newObject, FacetSet oldObject) {
			unconfigure(oldObject);
			configure(newObject);
		}

		@Override
		protected void didChange() {
			facetsUpdated();
		}
	};
	private final transient FacetManager manager;

	private transient Adapter facetAdapter; // Facets aren't serializable so no need for transient, but be consistent with the manager
	transient long facetGeneration; // Likewise
	private transient boolean updateEnabled = true;

	/**
	 * This field is used to avoid to have to many error messages in the log.
	 */
	private final Set<ETypedElement> failingFeatures = new HashSet<ETypedElement>();

	private final Set<IFacetManagerListener> listeners = new HashSet<IFacetManagerListener>();

	public FacetManagerContext(final FacetManager manager) {
		this.manager = manager;
	}

	/**
	 *
	 * @param baseFeature
	 * @param eObject
	 * @return
	 * @throws FacetConformanceEvaluationException
	 * @throws UnmatchingExpectedTypeException
	 * @throws FacetManagerException
	 */
	public <T extends ETypedElement> T resolveOverrides(final T baseFeature,
			final EObject eObject) throws FacetManagerException {
		T result = baseFeature;
		if (baseFeature instanceof DerivedTypedElement) {
			final DerivedTypedElement derivedResult = resolveOverrides(
					(DerivedTypedElement) baseFeature, eObject);
			if (derivedResult != null
					&& !(derivedResult instanceof ETypedElement)) {
				throw new UnmatchingExpectedTypeException(
						"Type mismatch in override resolution '" + baseFeature.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$
			}
			result = (T) derivedResult;
		}
		// If nothing is found, return the original basefeature
		if (result == null) {
			result = baseFeature;
		}
		return result;
	}

	/**
	 *
	 * @param baseFeature
	 * @param eObject
	 * @return
	 * @throws FacetConformanceEvaluationException
	 * @throws FacetManagerException
	 */
	public <T extends DerivedTypedElement> T resolveOverrides(
			final T baseFeature, final EObject eObject)
			throws FacetManagerException {

		final FacetCache cache = FacetCache.getInstance(eObject, this);
		T result = cache.resolve(baseFeature);

		if (result == null) {
			try {
				// -- Find master override
				// If the baseFeature overrides another feature, then we follow the
				// override chain to find the top feature
				// The main idea behind this is that the baseFeature does not really
				// represents a specific feature but a
				// feature signature (as in java method invocation)
				final T signatureFeature = FacetUtils
						.getTopOverrideFeature(baseFeature);

				// -- Find all candidates
				// Get all Facet referenced by the FacetManager to get all features
				// matching this signature
				// Note : candidates are searched ine the order provided by the current
				// FacetManager,
				// so they are already ordered by the wanted priority
				// XXX : Debug check that DerivedTypedElement only contained by Facet
				final List<T> orderedCandidates = getOverrideCandidateFeatures(eObject,
						signatureFeature);

				// -- Find the most specific feature
				result = findMostSpecificFeature(orderedCandidates);

				cache.add(baseFeature, result);
			} catch (Exception e) {
				throw new FacetManagerException(e);
			}
		}

		return result;
	}

	public List<FacetSet> getManagedFacetSets() {
		return Collections.unmodifiableList(this.managedFacetSets);
	}

	public void setManagedFacetSets(final List<FacetSet> facetSets) {
		// Don't increment the generation and notify listeners multiple times in this composite operation
		final boolean enableUpdate = updateEnabled;
		updateEnabled = false;
		try {
			ECollections.setEList(this.managedFacetSets, facetSets);
		} finally {
			updateEnabled = enableUpdate;
		}

		facetsUpdated();
	}

	public void addBackManagedFacetSet(final FacetSet facetSet) {
		add(facetSet);
	}

	public void addFrontManagedFacetSet(final FacetSet facetSet) {
		add(0, facetSet);
	}

	/**
	 * Find the most specific feature in the candidates list. The most specific
	 * is the feature that : - is the most specific (in terms of Facet
	 * inheritance) : the lowest in the inheritance tree (per branch) - is
	 * encountered first. The order is given by the FacetManager context.
	 *
	 * @param candidates
	 *            The list of candidates features. Can be empty.
	 * @return
	 */
	private static <T extends DerivedTypedElement> T findMostSpecificFeature(
			final List<T> orderedCandidates) {
		T result = null;
		if (!orderedCandidates.isEmpty()) {
			final Iterator<T> candidatesIt = orderedCandidates.iterator();
			result = candidatesIt.next();
			// We have the highest priority feature, we just have to check
			// that there is no more specific one in the override chain (from
			// top to bottom)
			while (candidatesIt.hasNext()) {
				final T candidate = candidatesIt.next();
				if (isOverridenBy(candidate, result)) {
					result = candidate;
				}
			}
		}
		return result;
	}

	/**
	 * Find matching candidates.
	 *
	 * @param eObject
	 * @param baseFeature
	 * @return A list of candidates, in the right order for conflict resolution
	 * @throws FacetManagerException
	 */
	private <T extends DerivedTypedElement> List<T> getOverrideCandidateFeatures(
			final EObject eObject, final T baseFeature)
			throws FacetManagerException {
		final ResourceSet baserFeatureRS = baseFeature.eResource().getResourceSet();
		final List<T> result = new LinkedList<T>();
		// Iterate over all Facets from all facetSets to find conforming
		// features
		final List<FacetSet> managedFSets = getManagedFacetSets();
		final List<FacetSet> allFacetSets = new ArrayList<FacetSet>(managedFSets);
		// add aggregated FacetSets
		for (FacetSet facetSet : managedFSets) {
			// FIXME Should handle recursive containment.
			for (FacetSet subFacetSet : facetSet.getFacetSets()) {
				final FacetSet resolvedFacetSet = IResolverManager.DEFAULT
						.resolve(subFacetSet, FacetSet.class);
				allFacetSets.add(resolvedFacetSet);
			}
		}
		for (FacetSet facetSet : allFacetSets) {
			final Resource resource = facetSet.eResource();
			if (resource == null) {
				final String message = String.format("The facetSet %s (%s) is not stored in a resource.", //$NON-NLS-1$
						facetSet.getName(), facetSet.getNsURI());
				Logger.logWarning(message, Activator.getDefault());
			} else {
				final ResourceSet facetSetRS = resource.getResourceSet();
				if (!facetSetRS.equals(baserFeatureRS)) {
					Logger.logWarning("The facet manager is dealing with more than one resource set.", //$NON-NLS-1$
							Activator.getDefault());
				}
			}
			for (Facet facet : FacetUtils.getFacets(facetSet)) {
				final T matchingFeature = getMatchingFeature(eObject, facet,
						baseFeature);
				if (matchingFeature != null) {
					result.add(matchingFeature);
				}
			}
		}
		if (result.isEmpty()) {
			if (!this.failingFeatures.contains(baseFeature)
					&& !FacetManagerContext.SILENT) {
				Logger.logWarning(
						"The result of " //$NON-NLS-1$
								+ this.getClass().getSimpleName()
								+ ".getOverrideCandidateFeatures(...) is empty! baseFeature=" //$NON-NLS-1$
								+ EcoreUtil.getURI(baseFeature)
								+ " (This message will be sent only once)", //$NON-NLS-1$
						Activator.getDefault());
				// This avoid to have to many error messages in the log.
				this.failingFeatures.add(baseFeature);
			}
			result.add(baseFeature);
		}
		return result;
	}

	/**
	 * Test whether a feature is overridden by another (directly or not)
	 *
	 * @param targetParent
	 * @param child
	 * @return true if child is directly or indirectly overridden by parent,
	 *         false otherwise
	 */
	private static boolean isOverridenBy(final DerivedTypedElement child,
			final DerivedTypedElement targetParent) {

		boolean result = false;
		if (child.equals(targetParent)) {
			result = true;
		} else {
			DerivedTypedElement currentParent = child.getOverride();
			while (!result && currentParent != null) {
				if (currentParent.equals(targetParent)) {
					result = true;
				} else {
					currentParent = currentParent.getOverride();
				}
			}
		}
		return result;
	}

	/**
	 * Find DerivedTypedElement features that matches the 'signature'
	 *
	 * @param eObject
	 *            EObject used to test conformance
	 * @param facet
	 * @param signatureFeature
	 *            reference feature that serves as 'signature'
	 * @throws FacetManagerException
	 */
	private <T extends DerivedTypedElement> T getMatchingFeature(
			final EObject eObject, final Facet facet, final T signatureFeature)
			throws FacetManagerException {
		T result = null;
		EList<? extends ETypedElement> eTypedElements;
		if (signatureFeature instanceof EOperation) {
			eTypedElements = facet.getFacetOperations();
		} else {
			eTypedElements = facet.getFacetElements();
		}
		// For each eTypedElement check is it override the signature feature and
		// if the eTypedElement is owned by a facet to which the eObject
		// conforms.
		for (ETypedElement feature : eTypedElements) {
			if (isMatchingFeature2(signatureFeature, feature)) {
				// The conformance check is done after the check on the override
				// to avoid infinite recursion.
				final ConformanceState conformanceState = this.manager
						.getConformanceState(eObject, facet);
				if (conformanceState == ConformanceState.Conformant) {
					if (!signatureFeature.getClass().isInstance(feature)) {
						throw new FacetManagerException(
								ModelUtils.getQualifiedName(feature)
										+ " overrides " //$NON-NLS-1$
										+ ModelUtils
												.getQualifiedName(signatureFeature)
										+ " but both are not of the same kind."); //$NON-NLS-1$
					}
					@SuppressWarnings("unchecked")
					// @SuppressWarnings("unchecked") check by
					// "if (!signatureFeature.getClass().isInstance(feature))"
					final T tmpFeature = (T) feature;
					result = tmpFeature;
					break;
					// Two features from the same Facet cannot
					// override a feature, so only one can be find
					// in this Facet => stop search here.
				}
			}
		}
		return result;
	}

	private static <T extends DerivedTypedElement> boolean isMatchingFeature2(
			final T signatureFeature, final ETypedElement feature)
			throws FacetManagerException {
		boolean result = false;
		// We're focusing on DerivedTypedElements
		// ECore native features could not be overridden by Facets
		// (EMF facet is supposed to be non intrusive)

		if (signatureFeature.getClass().isInstance(feature)) {
			final T element = (T) feature;
			// Just check that the top override is the same as the base feature
			// i.e. that the current feature matches the 'signature'
			final DerivedTypedElement topFeature = FacetUtils
					.getTopOverrideFeature(element);
			if (topFeature == signatureFeature) {
				// found a match
				result = true;
			} else {
				final Resource topResource = topFeature.eResource();
				final Resource signatureResource = signatureFeature.eResource();
				if (topResource == null || signatureResource == null || topFeature.eResource().getResourceSet() != signatureFeature.eResource().getResourceSet()) {
					Logger.logWarning("topOverrideFeature.eResource().getResourceSet() != signatureFeature.eResource().getResourceSet()", //$NON-NLS-1$
							Activator.getDefault());
				}
			}
		}
		return result;
	}

	public void removeFacetSet(final FacetSet facetSet) {
		this.managedFacetSets.remove(facetSet);
	}

	public void clear() {
		this.managedFacetSets.clear();
	}

	public int size() {
		return this.managedFacetSets.size();
	}

	public boolean isEmpty() {
		return this.managedFacetSets.isEmpty();
	}

	public boolean contains(final Object object) {
		return this.managedFacetSets.contains(object);
	}

	public Iterator<FacetSet> iterator() {
		return this.managedFacetSets.iterator();
	}

	public Object[] toArray() {
		return this.managedFacetSets.toArray();
	}

	public <T> T[] toArray(final T[] array) {
		return this.managedFacetSets.toArray(array);
	}

	public boolean add(final FacetSet object) {
		boolean result = false;

		if (object != null) {
			// adding an already managed FacetSet again moves it to the last position
			int existing = managedFacetSets.indexOf(object);
			int last = size() - 1;
			if (existing >= 0) {
				if (existing != last) {
					managedFacetSets.move(last, existing);
					result = true;
				}
			} else {
				result = managedFacetSets.add(object);
			}
		}

		return result;
	}

	public boolean remove(final Object object) {
		return this.managedFacetSets.remove(object);
	}

	public boolean containsAll(final Collection<?> collection) {
		return this.managedFacetSets.containsAll(collection);
	}

	public boolean addAll(final Collection<? extends FacetSet> collection) {
		// Don't increment the generation and notify listeners multiple times in this composite operation
		final boolean result;
		final boolean enableUpdate = updateEnabled;
		updateEnabled = false;
		try {
			final boolean removed = this.managedFacetSets.removeAll(collection);
			final boolean added = this.managedFacetSets.addAll(collection);
			result = removed || added;
		} finally {
			updateEnabled = enableUpdate;
		}

		if (result) {
			facetsUpdated();
		}

		return result;
	}

	public boolean addAll(final int index, final Collection<? extends FacetSet> collection) {
		final List<FacetSet> filtered = new ArrayList<FacetSet>();
		for (FacetSet facetSet : collection) {
			if (!filtered.contains(facetSet)) {
				filtered.add(facetSet);
			}
		}

		// Don't increment the generation and notify listeners multiple times in this composite operation
		final boolean result;
		final boolean enableUpdate = updateEnabled;
		updateEnabled = false;
		try {
			final boolean removed = this.managedFacetSets.removeAll(filtered);
			final boolean added = this.managedFacetSets.addAll(index, ListUtils.cleanList(filtered));
			result = removed || added;
		} finally {
			updateEnabled = enableUpdate;
		}

		if (result) {
			facetsUpdated();
		}

		return result;
	}

	public boolean removeAll(final Collection<?> collection) {
		return this.managedFacetSets.removeAll(collection);
	}

	public boolean retainAll(final Collection<?> collection) {
		return this.managedFacetSets.retainAll(collection);
	}

	public FacetSet get(final int index) {
		return this.managedFacetSets.get(index);
	}

	public FacetSet set(final int index, final FacetSet element) {
		return this.managedFacetSets.set(index, element);
	}

	public void add(final int index, final FacetSet element) {
		if (element != null) {
			int existing = managedFacetSets.indexOf(element);
			if (existing >= 0) {
				if (existing != index) {
					managedFacetSets.move(index, existing);
				}
			} else {
				managedFacetSets.add(index, element);
			}
		}
	}

	public FacetSet remove(final int index) {
		return this.managedFacetSets.remove(index);
	}

	public int indexOf(final Object object) {
		return this.managedFacetSets.indexOf(object);
	}

	public int lastIndexOf(final Object object) {
		return this.managedFacetSets.lastIndexOf(object);
	}

	public ListIterator<FacetSet> listIterator() {
		return this.managedFacetSets.listIterator();
	}

	public ListIterator<FacetSet> listIterator(final int index) {
		return this.managedFacetSets.listIterator(index);
	}

	public List<FacetSet> subList(final int fromIndex, final int toIndex) {
		return this.subList(fromIndex, toIndex);
	}

	public void addListener(final IFacetManagerListener listener) {
		this.listeners.add(listener);
	}

	public void removeListener(final IFacetManagerListener listener) {
		this.listeners.remove(listener);
	}

	private void notifyListeners() {
		for (IFacetManagerListener listener : this.listeners) {
			listener.facetManagerChanged();
		}
	}

	private void facetsUpdated() {
		if (updateEnabled) {
			incrementGeneration();
			notifyListeners();
		}
	}

	private void incrementGeneration() {
		facetGeneration++;
	}

	private FacetSet configure(FacetSet facetSet) {
		if ((facetSet != null) && !facetSet.eAdapters().contains(getFacetSetAdapter())) {
			facetSet.eAdapters().add(getFacetSetAdapter());
		}
		return facetSet;
	}

	private <T> T unconfigure(T facetSet) {
		if (facetSet instanceof FacetSet) {
			((FacetSet) facetSet).eAdapters().remove(getFacetSetAdapter());
		}
		return facetSet;
	}

	private Adapter getFacetSetAdapter() {
		if (facetAdapter == null) {
			facetAdapter = new EContentAdapter() {
				@Override
				protected void selfAdapt(Notification notification) {
					if (!notification.isTouch()) {
						incrementGeneration();
					}

					super.selfAdapt(notification);
				}
			};
		}

		return facetAdapter;
	}
}
