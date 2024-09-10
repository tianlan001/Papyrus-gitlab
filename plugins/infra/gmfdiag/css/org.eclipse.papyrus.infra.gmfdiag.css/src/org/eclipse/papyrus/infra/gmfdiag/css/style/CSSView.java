/*****************************************************************************
 * Copyright (c) 2013, 2016 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bugs 433206, 436665
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css.style;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmf.runtime.notation.NamedStyle;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.gmf.runtime.notation.View;


public interface CSSView {

	/**
	 * CSS Implementation of the isVisible() method
	 *
	 * @return
	 *
	 * @see View#isVisible()
	 */
	public boolean isCSSVisible();

	/**
	 * CSS Implementation of the getNamedStyle() method
	 *
	 * @param eClass
	 * @param name
	 * @return
	 *
	 * @see View#getNamedStyle(EClass, String)
	 */
	public NamedStyle getCSSNamedStyle(EClass eClass, String name);

	/**
	 * CSS implementation of the {@link View#getStyle(EClass)} API.
	 * 
	 * @param eClass
	 *            the style kind to get
	 * @return the (possibly implicitly defined) CSS implementation of the style
	 * 
	 * @see View#getStyle(EClass)
	 */
	public Style getCSSStyle(EClass eClass);
	
	//
	// Nested types
	//
	
	/**
	 * Private interface for management of CSS lifecycle and other
	 * internal concerns
	 * 
	 * @since 1.2
	 * 
	 * @noimplement This interface is not intended to be implemented by clients.
	 * @noreference This method is not intended to be referenced by clients.
	 */
	interface Internal extends CSSView {
		/**
		 * Resets my association with the CSS engine and any local caches
		 * thereof that I may have.
		 */
		void resetCSS();
	}
}
