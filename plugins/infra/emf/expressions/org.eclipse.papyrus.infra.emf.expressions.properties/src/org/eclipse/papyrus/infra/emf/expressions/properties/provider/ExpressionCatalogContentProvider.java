/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.papyrus.infra.emf.expressions.ExpressionCatalog;
import org.eclipse.papyrus.infra.emf.expressions.catalog.ExpressionCatalogRegistry;
import org.eclipse.papyrus.infra.widgets.providers.IGraphicalContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IHierarchicContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.swt.widgets.Composite;

/**
 * This ContentProvider allows to show the expressions owned by a catalog
 *
 */
public class ExpressionCatalogContentProvider implements IGraphicalContentProvider, IHierarchicContentProvider, IStaticContentProvider, ITreeContentProvider, IContentProvider {

	/** used to return empty value */
	private final Object[] emptyArray = new Object[] {};

	/**
	 * the edited feature
	 */
	private final EReference editedFeature;

	/**
	 * 
	 * Constructor.
	 *
	 * @param editedFeature
	 *            the edited feature
	 */
	public ExpressionCatalogContentProvider(final EReference editedFeature) {
		this.editedFeature = editedFeature;
	}

	/**
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 *
	 * @param inputElement
	 * @return
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		return ExpressionCatalogRegistry.INSTANCE.getAllRegisteredCatalog().toArray();
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 *
	 * @param parentElement
	 * @return
	 */
	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof ExpressionCatalog) {
			return ((ExpressionCatalog) parentElement).getExpressions().toArray();
		}
		return this.emptyArray;
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public Object getParent(Object element) {
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
	public boolean hasChildren(Object element) {
		if (element instanceof ExpressionCatalog) {
			return !((ExpressionCatalog) element).getExpressions().isEmpty();
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
		return this.emptyArray;
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

	/**
	 * @see org.eclipse.papyrus.infra.widgets.providers.IHierarchicContentProvider#isValidValue(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public boolean isValidValue(Object element) {
		if(null==this.editedFeature) {
			return true;
		}
		if (null != element) {
			return this.editedFeature.getEType().isInstance(element);
		}
		return false;
	}

}
