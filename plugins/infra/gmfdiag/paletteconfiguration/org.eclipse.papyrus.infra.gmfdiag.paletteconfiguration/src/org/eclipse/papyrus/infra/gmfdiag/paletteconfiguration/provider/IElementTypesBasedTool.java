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
 *  Remi Schnekenburger (CEA LIST) remi.schnekenburger@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.provider;

import java.util.List;

import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ElementDescriptor;

/**
 * Tool based on element types
 */
public interface IElementTypesBasedTool {

	/**
	 * Returns the list of element types created by this tool
	 *
	 * @return the list of element types created by this tool
	 */
	public List<IElementType> getElementTypes();

	/**
	 * Returns the list of Element descriptors created by this tool
	 *
	 * @return the list of Element descriptors created by this tool
	 */
	public List<ElementDescriptor> getElementDescriptors();
}
