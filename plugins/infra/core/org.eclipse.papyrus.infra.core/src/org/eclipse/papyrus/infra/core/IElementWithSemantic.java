/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 */
package org.eclipse.papyrus.infra.core;

/**
 *
 * all implementation is used to give the semantic element form a wrapper
 *
 * the implementation can call method getAdapter for example for notation
 * element return semantic element, for edit part return the semantic element
 * and not the view...
 */
public interface IElementWithSemantic {

	/**
	 * return the semantic element linked to this wrapper
	 *
	 * @param wrapper
	 *            an object that wrapped or are linked to a semantic element
	 *            <B>cannot be null</B>
	 * @return null or the semantic element
	 */
	public Object getSemanticElement(Object wrapper);
}
