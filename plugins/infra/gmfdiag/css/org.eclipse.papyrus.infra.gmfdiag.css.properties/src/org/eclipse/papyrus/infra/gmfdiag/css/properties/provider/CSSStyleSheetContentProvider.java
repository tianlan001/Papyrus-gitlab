/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css.properties.provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.ModelStyleSheets;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.StyleSheet;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.StylesheetsPackage;
import org.eclipse.papyrus.infra.ui.emf.providers.EMFContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.AbstractStaticContentProvider;


public class CSSStyleSheetContentProvider extends EMFContentProvider {

	private EObject context;

	public CSSStyleSheetContentProvider(EObject sourceEObject) {
		super(sourceEObject, StylesheetsPackage.eINSTANCE.getModelStyleSheets_Stylesheets());
		this.context = sourceEObject;
	}

	/**
	 * Returns the content provider associated to feature being edited
	 *
	 * @param editedEObject
	 *            The object being edited
	 * @param feature
	 *            The object's feature being edited
	 * @return
	 * 		A content provider returning all the values valid for the given feature
	 */
	@Override
	protected IStructuredContentProvider getSemanticProvider(EObject editedEObject, EStructuralFeature feature) {

		return new AbstractStaticContentProvider() {

			public Object[] getElements() {
				List<Object> result = new LinkedList<Object>();
				if (context == null || context.eResource() == null) {
					return new Object[0];
				}
				Resource contextResource = context.eResource();
				List<Resource> resources;
				if (contextResource.getResourceSet() == null) {
					resources = Collections.singletonList(contextResource);
				} else {
					resources = new ArrayList<Resource>(contextResource.getResourceSet().getResources());
				}

				for (Resource resource : resources) {
					for (Object object : resource.getContents()) {
						if (object instanceof ModelStyleSheets) {
							ModelStyleSheets styleSheets = (ModelStyleSheets) object;
							for (StyleSheet styleSheet : styleSheets.getStylesheets()) {
								result.add(styleSheet);
							}
						} else {
							if (object instanceof StyleSheet) {
								result.add(object);
							}
						}
					}
				}
				return result.toArray();
			}
		};
	}

}
