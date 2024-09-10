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
package org.eclipse.papyrus.uml.service.types.filter;

import java.util.List;

import org.eclipse.gmf.runtime.emf.type.core.IElementType;

/**
 * Interface for creation command filter.
 */
public interface ICommandFilter {

	/**
	 * Get the list of element type for which the creation command is visible.
	 * 
	 * @return the list of allowed element types.
	 */
	public List<IElementType> getVisibleCommands();

}
