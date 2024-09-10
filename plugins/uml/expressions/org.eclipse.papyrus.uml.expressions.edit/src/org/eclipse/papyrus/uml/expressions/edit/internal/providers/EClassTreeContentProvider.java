/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.expressions.edit.internal.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.viewers.ITreeContentProvider;

/**
 * This contents provider allows to navigate until to find EClass owned by EPackage
 * Only EClass and EPackage are displayed
 */
public class EClassTreeContentProvider implements ITreeContentProvider {

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
	 *
	 * @param inputElement
	 * @return
	 */
	@Override
	public Object[] getElements(final Object inputElement) {
		if (inputElement instanceof Collection<?>) {
			return ((Collection<?>) inputElement).toArray();
		}
		if (inputElement instanceof Object[]) {
			return (Object[]) inputElement;
		}
		return new Object[0];
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 *
	 * @param parentElement
	 * @return
	 */
	@Override
	public Object[] getChildren(final Object parentElement) {
		if (parentElement instanceof EPackage) {
			final Collection<EObject> returnedValues = new ArrayList<EObject>();
			final EPackage ePackage = (EPackage) parentElement;
			for (final EPackage current : ePackage.getESubpackages()) {
				if (isValidValue(current)) {
					returnedValues.add(current);
				}
			}
			for (final EClassifier current : ePackage.getEClassifiers()) {
				if (isValidValue(current)) {
					returnedValues.add(current);
				}
			}
			return returnedValues.toArray();
		}
		return new Object[0];
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public Object getParent(final Object element) {
		if (element instanceof EObject) {
			return ((EObject) element).eContainer();
		}
		return null;
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public boolean hasChildren(final Object element) {
		if (element instanceof EClass) {
			return false;
		}
		return isValidValue(element);
	}


	/**
	 *
	 * @param ePackage
	 *            an EPackage
	 * @return
	 *         <code>true</code> if there is an {@link EClass} in the contents/subcontents of the EPackage
	 */
	protected final boolean hasEClassInHierarchy(final EPackage ePackage) {
		final Iterator<EClassifier> eClassIter = ePackage.getEClassifiers().iterator();
		boolean isValidValue = false;
		while (eClassIter.hasNext() && false == isValidValue) {
			isValidValue = isValidValue(eClassIter.next());
		}

		final Iterator<EPackage> packIter = ePackage.getESubpackages().iterator();
		while (packIter.hasNext() && false == isValidValue) {
			isValidValue = hasEClassInHierarchy(packIter.next());
		}
		return isValidValue;
	}


	/**
	 * @see org.eclipse.papyrus.model2doc.emf.documentstructuretemplate.edit.providers.IHierarchicContentProvider#isValidValue(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	public boolean isValidValue(Object element) {
		if (element instanceof EClass) {
			return true;
		}
		if (element instanceof EPackage) {
			return hasEClassInHierarchy((EPackage) element);
		}
		return false;
	}
}
