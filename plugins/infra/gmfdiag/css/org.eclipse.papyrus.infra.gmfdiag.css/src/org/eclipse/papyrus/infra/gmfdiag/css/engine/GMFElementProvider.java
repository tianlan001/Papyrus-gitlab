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
package org.eclipse.papyrus.infra.gmfdiag.css.engine;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.css.dom.GMFElementAdapter;
import org.eclipse.papyrus.infra.gmfdiag.css.helper.CSSDOMSemanticElementHelper;
import org.eclipse.papyrus.infra.gmfdiag.css.provider.IPapyrusElementProvider;
import org.w3c.dom.Element;

/**
 * Provides an Element for a GMF Notation object.
 *
 * @author Camille Letavernier
 */
@SuppressWarnings("restriction")
public class GMFElementProvider implements IPapyrusElementProvider {

	/**
	 * {@inheritDoc}
	 *
	 * @param element
	 *            A GMF View
	 * @param engine
	 *            An ExtendedCSSEngine
	 */
	@Override
	public Element getElement(Object element, CSSEngine engine) {

		if (!(element instanceof View)) {
			throw new IllegalArgumentException("Unknown element : " + element);
		}

		if (!(engine instanceof ExtendedCSSEngine)) {
			throw new IllegalArgumentException("Invalid CSS Engine : " + engine);
		}

		return new GMFElementAdapter((View) element, (ExtendedCSSEngine) engine);
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.css.provider.IPapyrusElementProvider#getPrimaryView(org.eclipse.emf.ecore.EObject)
	 *
	 * @param notationElement
	 * @return
	 */
	@Override
	public View getPrimaryView(EObject notationElement) {

		View canonicalNotationElement = CSSDOMSemanticElementHelper.getInstance().findPrimaryView(notationElement);
		return canonicalNotationElement;
	}

}
