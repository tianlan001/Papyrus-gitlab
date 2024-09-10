/**
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.uml.diagram.usecase.navigator;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class UMLNavigatorItem extends UMLAbstractNavigatorItem {

	/**
	 * @generated
	 */
	static {
		@SuppressWarnings("rawtypes")
		final Class[] supportedTypes = new Class[] { View.class, EObject.class };
		Platform.getAdapterManager().registerAdapters(new IAdapterFactory() {

			@Override
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Object getAdapter(Object adaptableObject, Class adapterType) {
				if (adaptableObject instanceof org.eclipse.papyrus.uml.diagram.usecase.navigator.UMLNavigatorItem && (adapterType == View.class || adapterType == EObject.class)) {
					return ((org.eclipse.papyrus.uml.diagram.usecase.navigator.UMLNavigatorItem) adaptableObject).getView();
				}
				return null;
			}

			@Override
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class[] getAdapterList() {
				return supportedTypes;
			}
		}, org.eclipse.papyrus.uml.diagram.usecase.navigator.UMLNavigatorItem.class);
	}

	/**
	 * @generated
	 */
	private View myView;

	/**
	 * @generated
	 */
	private boolean myLeaf = false;

	/**
	 * @generated
	 */
	public UMLNavigatorItem(View view, Object parent, boolean isLeaf) {
		super(parent);
		myView = view;
		myLeaf = isLeaf;
	}

	/**
	 * @generated
	 */
	public View getView() {
		return myView;
	}

	/**
	 * @generated
	 */
	public boolean isLeaf() {
		return myLeaf;
	}

	/**
	 * @generated
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof org.eclipse.papyrus.uml.diagram.usecase.navigator.UMLNavigatorItem) {
			return EcoreUtil.getURI(getView()).equals(EcoreUtil.getURI(((org.eclipse.papyrus.uml.diagram.usecase.navigator.UMLNavigatorItem) obj).getView()));
		}
		return super.equals(obj);
	}

	/**
	 * @generated
	 */
	@Override
	public int hashCode() {
		return EcoreUtil.getURI(getView()).hashCode();
	}
}
