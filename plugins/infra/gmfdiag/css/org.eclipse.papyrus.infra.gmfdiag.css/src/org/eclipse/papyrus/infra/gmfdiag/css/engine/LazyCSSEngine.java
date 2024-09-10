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

import org.w3c.dom.css.CSSValue;


/**
 * An interface for Lazy CSS Engine
 *
 * The Engine doesn't modify an Element. Instead, for an element, it returns the
 * value of the required property.
 *
 * @author Camille Letavernier
 */
public interface LazyCSSEngine {

	/**
	 * Lazily retrieves a property's value on the selected node
	 *
	 * @param node
	 * @param property
	 * @return
	 */
	public CSSValue retrievePropertyValue(Object node, String property);

}
