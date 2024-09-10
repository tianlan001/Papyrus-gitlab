/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.m2m.qvto.tests.tools.tests;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.compare.CompareFactory;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.ComparisonCanceledException;
import org.eclipse.emf.compare.EMFCompareMessages;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.match.eobject.EObjectIndex;
import org.eclipse.emf.compare.match.eobject.EObjectIndex.Side;
import org.eclipse.emf.compare.match.eobject.IEObjectMatcher;
import org.eclipse.emf.compare.match.eobject.ProximityEObjectMatcher.DistanceFunction;
import org.eclipse.emf.compare.match.eobject.ScopeQuery;
import org.eclipse.emf.compare.match.eobject.internal.ByTypeIndex;
import org.eclipse.emf.compare.match.eobject.internal.MatchAheadOfTime;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.ProfileApplication;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * This matcher is using a distance function to match EObject. It guarantees that elements are matched with
 * the other EObject having the lowest distance. If two elements have the same distance regarding the other
 * EObject it will arbitrary pick one. (You should probably not rely on this and make sure your distance only
 * return 0 if both EObject have the very same content). The matcher will try to use the fact that it is a
 * distance to achieve a suitable scalability. It is also build on the following assumptions :
 * <ul>
 * <li>Most EObjects have no difference and have their corresponding EObject on the other sides of the model
 * (right and origins)</li>
 * <li>Two consecutive calls on the distance function with the same parameters will give the same distance.
 * </li>
 * </ul>
 * The scalability you'll get will highly depend on the complexity of the distance function. The
 * implementation is not caching any distance result from two EObjects.
 * 
 * @author <a href="mailto:cedric.brun@obeo.fr">Cedric Brun</a>
 */
@SuppressWarnings("restriction")
public class PapyrusProximityEObjectMatcher implements IEObjectMatcher, ScopeQuery {

	/**
	 * Number of elements to index before a starting a match ahead step.
	 */
	private static final int NB_ELEMENTS_BETWEEN_MATCH_AHEAD = 10000;

	/**
	 * The index which keep the EObjects.
	 */
	private EObjectIndex index;

	/**
	 * Keeps track of which side was the EObject from.
	 */
	private Map<EObject, Side> eObjectsToSide = Maps.newHashMap();

	/**
	 * The left map of named elements.
	 */
	final Map<String, EObject> leftMap = new HashMap<String, EObject>();

	/**
	 * The right map of named elements.
	 */
	final Map<String, EObject> rightMap = new HashMap<String, EObject>();

	/**
	 * The left map of package import.
	 */
	final Map<String, EObject> leftPackageImportMap = new HashMap<String, EObject>();

	/**
	 * The right map of package import.
	 */
	final Map<String, EObject> rightPackageImportMap = new HashMap<String, EObject>();

	/**
	 * The left map of profile applicaton.
	 */
	final Map<String, EObject> leftProfileApplicationImportMap = new HashMap<String, EObject>();

	/**
	 * The right map of profile applicaton.
	 */
	final Map<String, EObject> rightProfileApplicationImportMap = new HashMap<String, EObject>();

	/**
	 * Create the matcher using the given distance function.
	 * 
	 * @param meter
	 *            A function to measure the distance between two {@link EObject}s.
	 */
	public PapyrusProximityEObjectMatcher(final DistanceFunction meter) {
		this.index = new ByTypeIndex(meter, this);
	}

	/**
	 * This allows to add named element to left map.
	 * 
	 * @param object
	 *            The object to add in the left map.
	 */
	private void addToLeftNamedElementMap(final EObject object) {
		if (object instanceof NamedElement) {
			leftMap.put(((NamedElement) object).getQualifiedName(), object);
		} else if (object instanceof PackageImport) {
			leftPackageImportMap.put(((PackageImport) object).getImportedPackage().getQualifiedName(), object);
		} else if (object instanceof ProfileApplication) {
			leftProfileApplicationImportMap.put(((ProfileApplication) object).getAppliedProfile().getQualifiedName(), object);
		}

	}

	/**
	 * This allows to add named element to right map.
	 * 
	 * @param object
	 *            The object to add in the right map.
	 */
	private void addToRightNamedElementMap(final EObject object) {
		if (object instanceof NamedElement) {
			rightMap.put(((NamedElement) object).getQualifiedName(), object);
		} else if (object instanceof PackageImport) {
			rightPackageImportMap.put(((PackageImport) object).getImportedPackage().getQualifiedName(), object);
		} else if (object instanceof ProfileApplication) {
			rightProfileApplicationImportMap.put(((ProfileApplication) object).getAppliedProfile().getQualifiedName(), object);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.compare.match.eobject.IEObjectMatcher#createMatches(org.eclipse.emf.compare.Comparison, java.util.Iterator, java.util.Iterator, java.util.Iterator, org.eclipse.emf.common.util.Monitor)
	 */
	@Override
	public void createMatches(final Comparison comparison, final Iterator<? extends EObject> leftEObjects,
			final Iterator<? extends EObject> rightEObjects, final Iterator<? extends EObject> originEObjects,
			final Monitor monitor) {
		if (!leftEObjects.hasNext() && !rightEObjects.hasNext() && !originEObjects.hasNext()) {
			return;
		}

		monitor.subTask(EMFCompareMessages.getString("ProximityEObjectMatcher.monitor.indexing")); //$NON-NLS-1$
		int nbElements = 0;
		int lastSegment = 0;
		/*
		 * We are iterating through the three sides of the scope at the same time so that index might apply
		 * pre-matching strategies elements if they wish.
		 */
		while (leftEObjects.hasNext() || rightEObjects.hasNext() || originEObjects.hasNext()) {
			if (monitor.isCanceled()) {
				throw new ComparisonCanceledException();
			}

			if (leftEObjects.hasNext()) {
				final EObject next = leftEObjects.next();
				nbElements++;
				index.index(next, Side.LEFT);
				eObjectsToSide.put(next, Side.LEFT);
				addToLeftNamedElementMap(next);
			}

			if (rightEObjects.hasNext()) {
				final EObject next = rightEObjects.next();
				index.index(next, Side.RIGHT);
				eObjectsToSide.put(next, Side.RIGHT);
				addToRightNamedElementMap(next);
			}

			if (originEObjects.hasNext()) {
				final EObject next = originEObjects.next();
				index.index(next, Side.ORIGIN);
				eObjectsToSide.put(next, Side.ORIGIN);
			}
			if (nbElements / NB_ELEMENTS_BETWEEN_MATCH_AHEAD > lastSegment) {
				matchAheadOfTime(comparison, monitor);
				lastSegment++;
			}

		}

		monitor.subTask(EMFCompareMessages.getString("ProximityEObjectMatcher.monitor.matching")); //$NON-NLS-1$
		createMatch(comparison);
		matchIndexedObjects(comparison, monitor);

		createUnmatchesForRemainingObjects(comparison);
		restructureMatchModel(comparison);

	}

	/**
	 * This allows to create the match of comparison.
	 * 
	 * @param comparison
	 *            The current comparison.
	 */
	private void createMatch(final Comparison comparison) {
		Iterator<String> iter = leftMap.keySet().iterator();
		while (iter.hasNext()) {
			final String current = iter.next();
			final EObject leftValue = leftMap.get(current);
			final EObject rightValue = rightMap.get(current);
			if (null != leftValue && null != rightValue) {
				areMatching(comparison, leftValue, rightValue, null);
			}
		}
		iter = leftPackageImportMap.keySet().iterator();
		while (iter.hasNext()) {
			final String current = iter.next();
			final EObject leftValue = leftPackageImportMap.get(current);
			final EObject rightValue = rightPackageImportMap.get(current);
			if (null != leftValue && null != rightValue) {
				areMatching(comparison, leftValue, rightValue, null);
			}
		}

		iter = leftProfileApplicationImportMap.keySet().iterator();
		while (iter.hasNext()) {
			final String current = iter.next();
			final EObject leftValue = leftProfileApplicationImportMap.get(current);
			final EObject rightValue = rightProfileApplicationImportMap.get(current);
			if (null != leftValue && null != rightValue) {
				areMatching(comparison, leftValue, rightValue, null);
			}
		}
	}

	/**
	 * If the index supports it, match element ahead of time, in case of failure the elements are kept and
	 * will be processed again later on.
	 * 
	 * @param comparison
	 *            The current comparison.
	 * @param monitor
	 *            Monitor to track progress.
	 */
	private void matchAheadOfTime(final Comparison comparison, final Monitor monitor) {
		if (index instanceof MatchAheadOfTime) {
			matchList(comparison, ((MatchAheadOfTime) index).getValuesToMatchAhead(Side.LEFT), false, monitor);
			matchList(comparison, ((MatchAheadOfTime) index).getValuesToMatchAhead(Side.RIGHT), false,
					monitor);
		}
	}

	/**
	 * Match elements for real, if no match is found for an element, an object will be created to represent
	 * this unmatch and the element will not be processed again.
	 * 
	 * @param comparison
	 *            The current comparison.
	 * @param monitor
	 *            Monitor to track progress.
	 */
	private void matchIndexedObjects(final Comparison comparison, final Monitor monitor) {
		Iterable<EObject> todo = index.getValuesStillThere(Side.LEFT);
		while (todo.iterator().hasNext()) {
			if (monitor.isCanceled()) {
				throw new ComparisonCanceledException();
			}
			todo = matchList(comparison, todo, true, monitor);
		}
		todo = index.getValuesStillThere(Side.RIGHT);
		while (todo.iterator().hasNext()) {
			if (monitor.isCanceled()) {
				throw new ComparisonCanceledException();
			}
			todo = matchList(comparison, todo, true, monitor);
		}

	}

	/**
	 * Create all the Match objects for the remaining EObjects.
	 * 
	 * @param comparison
	 *            The current comparison.
	 */
	private void createUnmatchesForRemainingObjects(final Comparison comparison) {
		for (final EObject notFound : index.getValuesStillThere(Side.RIGHT)) {
			areMatching(comparison, null, notFound, null);
		}
		for (final EObject notFound : index.getValuesStillThere(Side.LEFT)) {
			areMatching(comparison, notFound, null, null);
		}
		for (final EObject notFound : index.getValuesStillThere(Side.ORIGIN)) {
			areMatching(comparison, null, null, notFound);
		}
	}

	/**
	 * Process the list of objects matching them. This method might not be able to process all the EObjects if
	 * - for instance, their container has not been matched already. Every object which could not be matched
	 * is returned in the list.
	 * 
	 * @param comparison
	 *            The comparison being built.
	 * @param todoList
	 *            The list of objects to process.
	 * @param createUnmatches
	 *            Whether elements which have no match should trigger the creation of a Match object (meaning
	 *            we won't try to match them afterwards) or not.
	 * @param monitor
	 *            A monitor to track progress.
	 * @return The list of EObjects which could not be processed for some reason.
	 */
	private Iterable<EObject> matchList(final Comparison comparison, final Iterable<EObject> todoList,
			final boolean createUnmatches, final Monitor monitor) {
		final Set<EObject> remainingResult = Sets.newLinkedHashSet();
		final List<EObject> requiredContainers = Lists.newArrayList();
		final Iterator<EObject> todo = todoList.iterator();
		while (todo.hasNext()) {
			if (monitor.isCanceled()) {
				throw new ComparisonCanceledException();
			}
			final EObject next = todo.next();
			/*
			 * Let's first add every container which is in scope
			 */
			EObject container = next.eContainer();
			while (null != container && isInScope(container)) {
				if (comparison.getMatch(container) == null) {
					requiredContainers.add(0, container);
				}
				container = container.eContainer();
			}
		}
		final Iterator<EObject> containersAndTodo = Iterators.concat(requiredContainers.iterator(),
				todoList.iterator());
		while (containersAndTodo.hasNext()) {
			if (monitor.isCanceled()) {
				throw new ComparisonCanceledException();
			}
			final EObject next = containersAndTodo.next();
			/*
			 * At this point you need to be sure the element has not been matched in any other way before.
			 */
			if (null == comparison.getMatch(next)) {
				if (!tryToMatch(comparison, next, createUnmatches)) {
					remainingResult.add(next);
				}
			}
		}
		return remainingResult;
	}

	/**
	 * Try to create a Match. If the match got created, register it (having actual left/right/origin matches
	 * or not), if not, then return false. Cases where it might not create the match : if some required data
	 * has not been computed yet (for instance if the container of an object has not been matched and if the
	 * distance need to know if it's match to find the children matches).
	 * 
	 * @param comparison
	 *            The comparison under construction, it will be updated with the new match.
	 * @param a
	 *            Object to match.
	 * @param createUnmatches
	 *            Whether elements which have no match should trigger the creation of a Match object (meaning
	 *            we won't try to match them afterwards) or not.
	 * @return <code>false</code> if the conditions are not fulfilled to create the match, <code>true</code> otherwhise.
	 */
	private boolean tryToMatch(final Comparison comparison, final EObject a, final boolean createUnmatches) {
		boolean okToMatch = false;
		Side aSide = eObjectsToSide.get(a);
		assert null != aSide;
		Side bSide = Side.LEFT;
		Side cSide = Side.RIGHT;
		if (Side.RIGHT == aSide) {
			bSide = Side.LEFT;
			cSide = Side.ORIGIN;
		} else if (Side.LEFT == aSide) {
			bSide = Side.RIGHT;
			cSide = Side.ORIGIN;
		} else if (Side.ORIGIN == aSide) {
			bSide = Side.LEFT;
			cSide = Side.RIGHT;
		}
		assert aSide != bSide;
		assert bSide != cSide;
		assert cSide != aSide;
		final Map<Side, EObject> closests = index.findClosests(comparison, a, aSide);
		if (null != closests) {
			final EObject lObj = closests.get(bSide);
			final EObject aObj = closests.get(cSide);
			if (null != lObj || null != aObj) {
				// we have at least one other match
				areMatching(comparison, closests.get(Side.LEFT), closests.get(Side.RIGHT),
						closests.get(Side.ORIGIN));
				okToMatch = true;
			} else if (createUnmatches) {
				areMatching(comparison, closests.get(Side.LEFT), closests.get(Side.RIGHT),
						closests.get(Side.ORIGIN));
				okToMatch = true;
			}
		}
		return okToMatch;
	}

	/**
	 * Process all the matches of the given comparison and re-attach them to their parent if one is found.
	 * 
	 * @param comparison
	 *            The comparison to restructure.
	 */
	private void restructureMatchModel(final Comparison comparison) {
		Iterator<Match> it = ImmutableList.copyOf(Iterators.filter(comparison.eAllContents(), Match.class))
				.iterator();

		while (it.hasNext()) {
			final Match cur = it.next();
			EObject possibleContainer = null;
			if (null != cur.getLeft()) {
				possibleContainer = cur.getLeft().eContainer();
			}
			if (null == possibleContainer && null != cur.getRight()) {
				possibleContainer = cur.getRight().eContainer();
			}
			if (null == possibleContainer && null != cur.getOrigin()) {
				possibleContainer = cur.getOrigin().eContainer();
			}
			final Match possibleContainerMatch = comparison.getMatch(possibleContainer);
			if (null != possibleContainerMatch) {
				((BasicEList<Match>) possibleContainerMatch.getSubmatches()).addUnique(cur);
			}
		}
	}

	/**
	 * Register the given object as a match and add it in the comparison.
	 * 
	 * @param comparison
	 *            Container for the Match.
	 * @param left
	 *            Left element.
	 * @param right
	 *            Right element
	 * @param origin
	 *            Origin element.
	 * @return The created match.
	 */
	private Match areMatching(final Comparison comparison, final EObject left, final EObject right, final EObject origin) {
		final Match result = CompareFactory.eINSTANCE.createMatch();
		result.setLeft(left);
		result.setRight(right);
		result.setOrigin(origin);
		((BasicEList<Match>) comparison.getMatches()).addUnique(result);
		if (null != left) {
			index.remove(left, Side.LEFT);
		}
		if (null != right) {
			index.remove(right, Side.RIGHT);
		}
		if (null != origin) {
			index.remove(origin, Side.ORIGIN);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.compare.match.eobject.ScopeQuery#isInScope(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public boolean isInScope(final EObject eContainer) {
		return null != eObjectsToSide.get(eContainer);
	}
}
