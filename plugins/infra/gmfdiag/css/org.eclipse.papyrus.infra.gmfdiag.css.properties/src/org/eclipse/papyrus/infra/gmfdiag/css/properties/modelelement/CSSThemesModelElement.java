/*****************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 417409
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.css.properties.modelelement;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.infra.gmfdiag.css.properties.provider.CSSStyleSheetLabelProvider;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;


/**
 * Theme ModelElement to provide corresponding label provider because EMF model
 * is not register in service registry.
 *
 * @author gpascual
 *
 */
public class CSSThemesModelElement extends EMFModelElement {

	/** UI view path for theme style sheets. */
	private static final String THEME_STYLESHEETS_PATH = "stylesheets"; //$NON-NLS-1$

	/**
	 * Default constructor.
	 *
	 * @param source
	 * @param domain
	 */
	public CSSThemesModelElement(EObject source, EditingDomain domain) {
		super(source, domain);
	}

	/**
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#getLabelProvider(java.lang.String)
	 *
	 * @param propertyPath
	 * @return
	 */
	@Override
	public ILabelProvider getLabelProvider(String propertyPath) {

		ILabelProvider labelProvider = null;


		if (THEME_STYLESHEETS_PATH.equals(propertyPath)) {
			labelProvider = new CSSStyleSheetLabelProvider();
		}

		// If label provider was not instantiated, we use this ancestor one
		if (labelProvider == null) {
			labelProvider = super.getLabelProvider(propertyPath);
		}

		return labelProvider;
	}

}
