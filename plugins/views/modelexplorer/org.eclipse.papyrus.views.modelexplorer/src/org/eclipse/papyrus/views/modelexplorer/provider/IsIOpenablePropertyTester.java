/*****************************************************************************
 * Copyright (c) 2021, 2022 CEA LIST and others.
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
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 578357
 *****************************************************************************/

package org.eclipse.papyrus.views.modelexplorer.provider;

import org.eclipse.core.runtime.IAdapterManager;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.IOpenable;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.ui.IWorkbenchPart;

/**
 * A specific PropertyTester for IOpenable.
 *
 * @since 5.0
 */
public class IsIOpenablePropertyTester extends org.eclipse.core.expressions.PropertyTester {

	/**
	 * The name of the tested property
	 */
	private static final String IS_IOPENABLE = "isIOpenable"; //$NON-NLS-1$

	/**
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String, java.lang.Object[], java.lang.Object)
	 *
	 * @param receiver
	 * @param property
	 * @param args
	 * @param expectedValue
	 * @return
	 */
	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		if (IS_IOPENABLE.equals(property)
				&& receiver instanceof IWorkbenchPart
				&& expectedValue instanceof Boolean) {
			final IWorkbenchPart wp = (IWorkbenchPart) receiver;
			if (wp.getSite() != null && wp.getSite().getSelectionProvider() != null) {
				// /!\ Here we don't use the "selection" variable because it is updated too late to be used by enablement of org.eclipse.ui.navigator.navigatorContent#actionProvider
				final ISelectionProvider selectionProvider = wp.getSite().getSelectionProvider();
				final ISelection selection = selectionProvider.getSelection();
				if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
					final EObject current = EMFHelper.getEObject(((IStructuredSelection) selection).getFirstElement());
					if (current != null) {
						IAdapterManager adapterManager = Platform.getAdapterManager();
						IOpenable adapter = adapterManager.getAdapter(current, IOpenable.class);
						return Boolean.valueOf(adapter != null).equals(expectedValue);
					}
				}
			}
		}
		return false;
	}

}
