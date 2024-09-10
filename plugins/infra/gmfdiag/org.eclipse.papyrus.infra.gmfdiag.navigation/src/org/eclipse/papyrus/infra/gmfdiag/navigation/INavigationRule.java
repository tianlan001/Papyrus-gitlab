/*****************************************************************************
 * Copyright (c) 2010 Atos Origin.
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
 *  Mathieu Velten (Atos Origin) mathieu.velten@atosorigin.com - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.navigation;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

/**
 * This interface defines a navigation rule.
 *
 * @author mvelten
 *
 */
public interface INavigationRule {

	/**
	 * Tests if an element is supported by this rule.
	 *
	 * @param element
	 * @return
	 */
	public boolean handle(EObject element);

	/**
	 * Retrieve a list of the next possible navigation possibilities.
	 *
	 * @param currentNavElement
	 * @return a list of navigable elements
	 */
	public List<NavigableElement> getNextPossibleElements(NavigableElement currentNavElement);
}
