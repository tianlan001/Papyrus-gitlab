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
 * 	Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.expressions.properties.provider;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.papyrus.infra.emf.expressions.ExpressionsPackage;
import org.eclipse.papyrus.infra.services.labelprovider.service.IFilteredLabelProvider;
import org.eclipse.papyrus.infra.ui.emf.providers.EMFFilteredLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * Expressions EClass Label Provider
 *
 */
public class ExpressionsEClassLabelProvider implements IFilteredLabelProvider {

	/**
	 * The generic EMF label provider
	 */
	private IFilteredLabelProvider emfLabelProvider = new EMFFilteredLabelProvider();

	/**
	 * the cache used to avoid to instantiate several time the expression
	 */
	private Map<EClass, Image> cache = new HashMap<EClass, Image>();

	/**
	 * @see org.eclipse.papyrus.infra.services.labelprovider.service.IFilteredLabelProvider#accept(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public boolean accept(Object element) {
		if (element instanceof EClass) {
			return (!((EClass) element).isAbstract()) && ((EClass) element).getEAllSuperTypes().contains(ExpressionsPackage.eINSTANCE.getIExpression());
		}
		return false;
	}

	/**
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public Image getImage(Object element) {
		if (!this.cache.containsKey(element)) {
			if (element instanceof EClass && !((EClass) element).isAbstract()) {
				final EClass eClass = (EClass) element;
				final EObject instance = eClass.getEPackage().getEFactoryInstance().create(eClass);
				if (this.emfLabelProvider.accept(instance)) {
					this.cache.put((EClass) element, this.emfLabelProvider.getImage(instance));
				}
			}
		}
		return this.cache.get(element);
	}

	/**
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof EClass) {
			return ((EClass) element).getName();
		}
		return null;
	}

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 *
	 * @param listener
	 */
	@Override
	public void addListener(ILabelProviderListener listener) {
		// nothing to do
	}

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 *
	 */
	@Override
	public void dispose() {
		this.cache.clear();
	}

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
	 *
	 * @param element
	 * @param property
	 * @return
	 */
	@Override
	public boolean isLabelProperty(Object element, String property) {
		// nothing to do
		return false;
	}

	/**
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 *
	 * @param listener
	 */
	@Override
	public void removeListener(ILabelProviderListener listener) {
		// nothing to do
	}

}
