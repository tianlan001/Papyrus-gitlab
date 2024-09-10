/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Juan Cadavid (CEA LIST) juan.cadavid@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.ui.handlers;

import org.eclipse.papyrus.uml.service.types.filter.ICommandFilter;
import org.eclipse.papyrus.uml.service.types.filter.UmlElementCommandFilter;


/**
 * Abstract handler for every creation command for UML elements
 * used in the ModelExplorer contextual ("Create new child") menu
 *
 * @deprecated since Eclipse Mars. Use AbstractCreateUmlElementCommand instead of this class
 */
@Deprecated
public abstract class AbstractUmlCreateCommandHandler extends AbstractCreateCommandHandler {

	private static final ICommandFilter filter = UmlElementCommandFilter.INSTANCE;

	/**
	 * 
	 * @see org.eclipse.papyrus.uml.service.types.ui.handlers.AbstractCreateCommandHandler#getCommandFilter()
	 * 
	 * @return
	 */
	@Override
	public ICommandFilter getCommandFilter() {
		return filter;
	}
}
