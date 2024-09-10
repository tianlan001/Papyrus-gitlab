/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.export.extension;


/**
 * The Interface AdditionalInformations.
 */
public interface AdditionalInformations {
	
	/**
	 * Gets the data.
	 *
	 * @param object the object
	 * @return the data
	 */
	// key in extension point
	String getData(Object object);
}
