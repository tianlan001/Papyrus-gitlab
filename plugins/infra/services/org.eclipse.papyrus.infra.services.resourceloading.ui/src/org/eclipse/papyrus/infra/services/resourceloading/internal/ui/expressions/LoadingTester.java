/*****************************************************************************
 * Copyright (c) 2011, 2016 Atos, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Hemery (Atos) vincent.hemery@atos.net - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.resourceloading.internal.ui.expressions;

import java.util.Iterator;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;

/**
 * This class provides test to perform on resources to know their loading
 * status.
 */
public class LoadingTester extends PropertyTester {

	/**
	 * property to test if the selected elements are in loaded resources (at
	 * least one other than the opened one)
	 */
	public static final String IS_ALL_LOADED = "isAllLoaded"; //$NON-NLS-1$

	/** property to test if the selected elements are in not loaded resources */
	public static final String IS_ALL_NOTLOADED = "isAllNotLoaded"; //$NON-NLS-1$

	/**
	 * Test a property
	 *
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String, java.lang.Object[], java.lang.Object)
	 *
	 * @param receiver
	 * @param property
	 * @param args
	 * @param expectedValue
	 * @return
	 */
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (IS_ALL_LOADED.equals(property) && receiver instanceof IStructuredSelection) {
			boolean answer = isInLoadedResource((IStructuredSelection) receiver);
			return new Boolean(answer).equals(expectedValue);
		}
		if (IS_ALL_NOTLOADED.equals(property) && receiver instanceof IStructuredSelection) {
			boolean answer = isInNotLoadedResource((IStructuredSelection) receiver);
			return new Boolean(answer).equals(expectedValue);
		}
		return false;
	}

	/**
	 * Tests the selection in order to know if it is in a loaded resource
	 *
	 * @param selection
	 *            selected elements
	 * @return <code>true</code> if all selected elements are in loaded
	 *         resources ; <code>false</code otherwise or if empty selection
	 */
	private boolean isInLoadedResource(IStructuredSelection selection) {
		if (!selection.isEmpty()) {
			boolean atLeastOneInSubmodel = false;
			URI mainURI = null;
			Iterator<?> iter = selection.iterator();
			while (iter.hasNext()) {
				Object obj = iter.next();
				EObject eObject = EMFHelper.getEObject(obj);
				if (eObject != null && !eObject.eIsProxy()) {
					// test that there is at least one not loaded resource
					// object
					if (!atLeastOneInSubmodel) {
						Resource containingResource = eObject.eResource();
						if (mainURI == null && containingResource != null && containingResource.getResourceSet() instanceof ModelSet) {
							mainURI = ((ModelSet) containingResource.getResourceSet()).getURIWithoutExtension();
						}
						if (mainURI != null) {
							URI uriTrim = containingResource.getURI().trimFileExtension();
							atLeastOneInSubmodel = !uriTrim.equals(mainURI);
						}
					}
					continue;
				}

				// a step failed
				return false;
			}
			return atLeastOneInSubmodel;
		}
		return false;
	}

	/**
	 * Tests the selection in order to know if it is in a not loaded resource
	 *
	 * @param selection
	 *            selected elements
	 * @return <code>true</code> if all selected elements are in not loaded
	 *         resources ; <code>false</code otherwise or if empty selection
	 */
	private boolean isInNotLoadedResource(IStructuredSelection selection) {
		if (!selection.isEmpty()) {
			Iterator<?> iter = selection.iterator();
			while (iter.hasNext()) {
				Object obj = iter.next();
				EObject eObject = EMFHelper.getEObject(obj);
				if (eObject != null && eObject.eIsProxy()) {
					continue;
				} else if (obj instanceof IAdaptable) {
					View view = ((IAdaptable) obj).getAdapter(View.class);

					if (view instanceof Edge) {
						View target = ((Edge) view).getTarget();
						if (target != null && resolveSemanticElement(target) == null) {
							// there is a backslash decorator
							continue;
						}
					}
				}
				// a step failed
				return false;
			}
			return true;
		}
		return false;
	}

	private EObject resolveSemanticElement(View view) {
		EObject result = view.getElement();

		if ((result != null) && result.eIsProxy()) {
			// Try harder to resolve it
			result = EMFCoreUtil.resolve(TransactionUtil.getEditingDomain(view), result);
		}

		return result;
	}
}
