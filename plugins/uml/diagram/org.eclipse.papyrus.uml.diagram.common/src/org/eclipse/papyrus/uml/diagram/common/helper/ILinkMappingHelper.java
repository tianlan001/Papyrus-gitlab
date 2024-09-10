/*****************************************************************************
 * Copyright (c) 2009 Atos Origin.
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
 *  Emilien Perico (Atos Origin) emilien.perico@atosorigin.com - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.helper;

import java.util.Collection;

import org.eclipse.uml2.uml.Element;

/**
 * Interface to map links at semantic level.
 */
public interface ILinkMappingHelper {

	/**
	 * Gets the source.
	 *
	 * @param link
	 *            the link
	 *
	 * @return the source
	 */
	public Collection<?> getSource(Element link);

	/**
	 * Gets the target.
	 *
	 * @param link
	 *            the link
	 *
	 * @return the target
	 */
	public Collection<?> getTarget(Element link);
}
