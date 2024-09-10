/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.contentprovider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.papyrus.infra.emf.utils.ENamedElementComparator;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.EStructuralFeatureValueFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.IFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.FillingConfigurationUtils;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;

/**
 * Abtract content provider for containement feature for the paste
 *
 * @author Vincent Lorenzo
 *
 */
public abstract class AbstractContainmentFeatureContentProvider implements IStaticContentProvider {

	/**
	 * the table manager
	 */
	private Table table;

	/**
	 * Determinate if this is used as column.
	 */
	private final boolean onColumn;

	/**
	 * The {@link EClass} of the parent elements
	 */
	private List<EClass> eClasses;

	/**
	 *
	 * Constructor.
	 *
	 * @param table
	 *            the table used to get the available features
	 * @param eClasses
	 *            the {@link EClass} of the parent elements
	 * @param onColumn
	 *            this determinate if this is used as column
	 */
	public AbstractContainmentFeatureContentProvider(final Table table, final List<EClass> eClasses, final boolean onColumn) {
		this.table = table;
		this.onColumn = onColumn;
		this.eClasses = eClasses;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	@Override
	public Object[] getElements(final Object inputElement) {
		final Collection<EStructuralFeature> availableFeatures = new TreeSet<EStructuralFeature>(new ENamedElementComparator());
		
		for(final EClass eClass : eClasses){
			final Collection<EStructuralFeature> allFeatures = eClass.getEAllStructuralFeatures();
			for (EStructuralFeature eStructuralFeature : allFeatures) {
				if (eStructuralFeature instanceof EReference && ((EReference) eStructuralFeature).isContainment() && eStructuralFeature.isChangeable() && eStructuralFeature.isMany()) {
					availableFeatures.add(eStructuralFeature);
				}
			}
		}

		final Collection<IFillingConfiguration> fillingConfigurations = FillingConfigurationUtils.getFillingConfigurationUsedInTable(table, onColumn);
		List<EStructuralFeature> listenFeatures = new ArrayList<EStructuralFeature>();
		for (final IFillingConfiguration current : fillingConfigurations) {
			if (current instanceof EStructuralFeatureValueFillingConfiguration) {
				listenFeatures.add(((EStructuralFeatureValueFillingConfiguration) current).getListenFeature());
			}
		}

		List<EClassifier> types = new ArrayList<EClassifier>();
		ListIterator<EStructuralFeature> iter = listenFeatures.listIterator();
		boolean hasFillingMode = listenFeatures.size() != 0;
		// the filling configuration can be done on a derived feature, that's why we must verify it!
		while (iter.hasNext()) {
			EStructuralFeature current = iter.next();
			if (current.isDerived() && !current.isChangeable() && !((EReference) current).isContainment()) {
				iter.remove();
				types.add(current.getEType());
			}
		}

		if (hasFillingMode) {
			// we look for features referenced by availabales feature and consistent with the filling feature
			Set<EStructuralFeature> bestFeatures = new HashSet<EStructuralFeature>();
			bestFeatures.addAll(listenFeatures);
			for (final EClassifier eClassifier : types) {
				for (final EStructuralFeature availableFeature : availableFeatures) {
					EClassifier currentEType = availableFeature.getEType();
					if (eClassifier instanceof EClass && currentEType instanceof EClass) {
						if (eClassifier == currentEType || ((EClass) eClassifier).isSuperTypeOf((EClass) currentEType)) {
							bestFeatures.add(availableFeature);
						}
					} else if (currentEType == eClassifier) {
						bestFeatures.add(availableFeature);
					}
				}
			}
			return bestFeatures.toArray();
		}

		if (listenFeatures.size() != 0) {
			availableFeatures.retainAll(listenFeatures);
		}
		return availableFeatures.toArray();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public final void dispose() {
		this.table = null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public final void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider#getElements()
	 */
	@Override
	public final Object[] getElements() {
		return getElements(null);
	}


}
