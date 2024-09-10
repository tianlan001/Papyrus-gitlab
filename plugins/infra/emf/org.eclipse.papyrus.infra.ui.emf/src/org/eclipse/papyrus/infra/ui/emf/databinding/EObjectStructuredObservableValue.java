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
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.emf.databinding;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.databinding.EObjectObservableValue;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;

/**
 * Structured ObservableValue.
 * @since 2.0
 */
public class EObjectStructuredObservableValue extends EMFObservableValue {

	/**
	 * List of child of the ObservableValue.
	 */
	protected List<EObjectObservableValue> observables = new ArrayList<>();

	/**
	 * The parent of this EObjectStructuredObservableValue.
	 */
	protected EObjectStructuredObservableValue parent;

	/**
	 * Constructor.
	 *
	 * @param eObject
	 *            The EObject to edit.
	 * @param eStructuralFeature
	 *            The structural feature.
	 * @param domain
	 *            The current editing domain.
	 * @param browseFeatures
	 *            The features for the browse.
	 */
	public EObjectStructuredObservableValue(final EObject eObject, final EStructuralFeature eStructuralFeature, final EditingDomain domain, final boolean browseFeatures, final EObjectStructuredObservableValue parent) {
		super(eObject, eStructuralFeature, domain);
		this.parent = parent;
		if (null != eObject) {
			if (browseFeatures) {
				browseFeatures(eObject);
			}
		}
	}

	/**
	 * Get the child.
	 *
	 * @return The observables.
	 */
	public List<EObjectObservableValue> getObservables() {
		return observables;
	}

	/**
	 * Get the parent.
	 *
	 * @return The parent.
	 */
	public EObjectStructuredObservableValue getParent() {
		return parent;
	}

	/**
	 * Crete the child.
	 *
	 * @param eObject
	 *            The parent.
	 */
	private void browseFeatures(final EObject eObject) {
		EList<EStructuralFeature> eStructuralFeatures = eObject.eClass().getEStructuralFeatures();
		TransactionalEditingDomain editingDomain = TransactionUtil.getEditingDomain(eObject);
		for (EStructuralFeature eStructuralFeature : eStructuralFeatures) {
			EObjectObservableValue eObjectObservableValue;
			Object eGet = eObject.eGet(eStructuralFeature);
			if (eStructuralFeature instanceof EReference && eGet instanceof EObject) {
				eObjectObservableValue = new EObjectStructuredObservableValue((EObject) eGet, eStructuralFeature, editingDomain, true, this);
			} else {
				eObjectObservableValue = new EObjectStructuredObservableValue(eObject, eStructuralFeature, editingDomain, false, this);
			}
			observables.add(eObjectObservableValue);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.databinding.EObjectObservableValue#getValueType()
	 */
	@Override
	public Object getValueType() {
		Object valueType = super.getValueType();
		if (null == valueType && null != eObject) {
			valueType = eObject.eClass();
		}
		return valueType;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.databinding.EObjectObservableValue#dispose()
	 */
	@Override
	public synchronized void dispose() {
		for (EObjectObservableValue eObjectObservableValue : observables) {
			eObjectObservableValue.dispose();
		}
		super.dispose();
	}
}
