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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.diff.DefaultDiffEngine;
import org.eclipse.emf.compare.diff.DiffBuilder;
import org.eclipse.emf.compare.diff.FeatureFilter;
import org.eclipse.emf.compare.diff.IDiffEngine;
import org.eclipse.emf.compare.diff.IDiffProcessor;
import org.eclipse.emf.compare.match.DefaultComparisonFactory;
import org.eclipse.emf.compare.match.DefaultEqualityHelperFactory;
import org.eclipse.emf.compare.match.DefaultMatchEngine;
import org.eclipse.emf.compare.match.IComparisonFactory;
import org.eclipse.emf.compare.match.IEqualityHelperFactory;
import org.eclipse.emf.compare.match.IMatchEngine;
import org.eclipse.emf.compare.match.eobject.IEObjectMatcher;
import org.eclipse.emf.compare.match.eobject.WeightProviderDescriptorRegistryImpl;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryImpl;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryRegistryImpl;
import org.eclipse.emf.compare.utils.EqualityHelper;
import org.eclipse.emf.compare.utils.IEqualityHelper;
import org.eclipse.emf.compare.utils.UseIdentifiers;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;

import com.google.common.cache.LoadingCache;

/**
 * The utils for the EMF Compare engine.
 */
public class EMFCompareUtils {

	/**
	 * This allows to create the EMF compare element for the JUnit tests.
	 * 
	 * @returnThe created EMF Compare element.
	 */
	public static final EMFCompare createEMFCompare() {
		// testing equality helper

		// we override the equality helper to be able to match some objects which didn't match with the default one
		final IEqualityHelperFactory helperFactory = new DefaultEqualityHelperFactory();
		final IComparisonFactory comparisonFactory = new DefaultComparisonFactory(helperFactory);

		// we override the DiffEngine to ignore differences for non ordered feature
		final IDiffProcessor diffProcessor = new DiffBuilder();
		final IDiffEngine diffEngine = createDiffEngineIgnoringNonOrderedFeature(diffProcessor);

		final IMatchEngine.Factory.Registry registry = MatchEngineFactoryRegistryImpl.createStandaloneInstance();

		final IEObjectMatcher matcher = DefaultMatchEngine.createDefaultEObjectMatcher(UseIdentifiers.ONLY, WeightProviderDescriptorRegistryImpl.createStandaloneInstance());
		final MatchEngineFactoryImpl mathEnginefactory = new MatchEngineFactoryImpl(matcher, comparisonFactory);
		registry.add(mathEnginefactory);
		mathEnginefactory.setRanking(30); // default engine ranking is 10, must be higher to override.

		return EMFCompare.builder().setDiffEngine(diffEngine).setMatchEngineFactoryRegistry(registry).build();
	}

	/**
	 * This allows to create the needed equality helper.
	 * 
	 * @return
	 * 		A new equality helper used for UML.
	 */
	protected static final IEqualityHelperFactory createUMLEqualityHelper() {
		// we override the equality helper to be able to match some objects which didn't match with the default one
		final IEqualityHelperFactory helperFactory = new DefaultEqualityHelperFactory() {
			@Override
			public IEqualityHelper createEqualityHelper() {
				final LoadingCache<EObject, URI> cache = EqualityHelper.createDefaultCache(getCacheBuilder());
				return new UMLIgnoringIdentifiedEqualityHelper(cache);
			}
		};
		return helperFactory;
	}

	/**
	 * This allows to create the DiffEngine for the needed feature ignored.
	 * 
	 * @param diffProcessor
	 *            The diff processor.
	 * @return
	 * 		A DiffEngine ignoring the feature where isOrdered() return false.
	 */
	protected static final IDiffEngine createDiffEngineIgnoringNonOrderedFeature(final IDiffProcessor diffProcessor) {
		return new DefaultDiffEngine(diffProcessor) {
			@Override
			protected FeatureFilter createFeatureFilter() {
				return new FeatureFilter() {


					/**
					 * {@inheritDoc}
					 * 
					 * @see org.eclipse.emf.compare.diff.FeatureFilter#checkForOrderingChanges(org.eclipse.emf.ecore.EStructuralFeature)
					 */
					@Override
					public boolean checkForOrderingChanges(final EStructuralFeature feature) {
						if (feature.isOrdered()) {
							if (EcorePackage.eINSTANCE == feature.eClass().getEPackage()) {
								return false;
							}
						}
						return feature.isOrdered();
					}
				};
			}
		};
	}
}
