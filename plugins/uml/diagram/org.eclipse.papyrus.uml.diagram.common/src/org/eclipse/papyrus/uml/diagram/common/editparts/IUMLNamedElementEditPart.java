/*****************************************************************************
 * Copyright (c) 2008 CEA LIST.
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editparts;

import org.eclipse.uml2.uml.NamedElement;

/**
 * this uml edit part can give the UML NamedElement associated to this editpart.
 */
public interface IUMLNamedElementEditPart extends IUMLEditPart {

	/**
	 * Gets the named element.
	 *
	 * @return the named element
	 */
	public NamedElement getNamedElement();

}
