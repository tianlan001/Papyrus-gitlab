/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Adapted code from the class diagram
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.profile.custom.providers;

import org.eclipse.papyrus.uml.diagram.profile.custom.factory.CustomUMLeditPartFactory;

/**
 * Custom EditPartProvider, to use our own UMLEditPartFactory
 */
public class CustomUMLEditPartProvider extends org.eclipse.papyrus.uml.diagram.profile.providers.UMLEditPartProvider {

	/**
	 * @generated
	 */
	public CustomUMLEditPartProvider() {
		setFactory(new CustomUMLeditPartFactory());
		setAllowCaching(true);
	}
}
