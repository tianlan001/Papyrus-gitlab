/*****************************************************************************
* Copyright (c) 2019 CEA LIST.
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


package org.eclipse.papyrus.infra.emf.expressions.properties.provider;

import java.util.Collection;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IGraphicalContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IHierarchicContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * This content provider allows to navigate until to final single EAttribute in EMF metamodel
 *
 */
public class SingleEAttributeContentProvider implements IGraphicalContentProvider, IHierarchicContentProvider, IStaticContentProvider, ITreeContentProvider, IContentProvider {

	/**
	 * The list of the EPackage to propose
	 */
	private final Collection<EPackage> ePackages;

	/**
	 *
	 * Constructor.
	 *
	 * @param epackages
	 *            the list of the EPackage to propose
	 */
	public SingleEAttributeContentProvider(final Collection<EPackage> epackages) {
		this.ePackages = epackages;
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
	 *
	 * @param inputElement
	 * @return
	 */
	@Override
	public Object[] getElements(final Object inputElement) {
		return ePackages.toArray();
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
			return ((EPackage) parentElement).eContents().toArray();
		}
		if (parentElement instanceof EClass) {
			return ((EClass) parentElement).getEAllAttributes().toArray();
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
		if (element instanceof EObject) {
			return ((EObject) element).eContents().size() > 0;
		}
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider#getElements()
	 *
	 * @return
	 */
	@Override
	public Object[] getElements() {
		return ePackages.toArray();
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.providers.IHierarchicContentProvider#isValidValue(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public boolean isValidValue(final Object element) {
		if (element instanceof EAttribute) {
			final EAttribute attr = (EAttribute) element;
			return !attr.isMany();
		}
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.providers.IGraphicalContentProvider#createBefore(org.eclipse.swt.widgets.Composite)
	 *
	 * @param parent
	 */
	@Override
	public void createBefore(Composite parent) {
		// nothing to do
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.providers.IGraphicalContentProvider#createAfter(org.eclipse.swt.widgets.Composite)
	 *
	 * @param parent
	 */
	@Override
	public void createAfter(Composite parent) {
		// nothing to do
	}


}
