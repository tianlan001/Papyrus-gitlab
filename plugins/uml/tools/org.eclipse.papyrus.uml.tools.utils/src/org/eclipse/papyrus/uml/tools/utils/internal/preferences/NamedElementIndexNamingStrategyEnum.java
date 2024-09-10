/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr  - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.tools.utils.internal.preferences;

import org.eclipse.papyrus.uml.tools.utils.internal.messages.Messages;

/**
 * This enumeration is used to define the strategy to use to initialize the name of the new created NamedElement
 * 
 * @since 3.3
 */
public enum NamedElementIndexNamingStrategyEnum {

	/** The provided naming strategies */

	/**
	 * No Index: the name of the created NamedElement won't be suffixed with an index.
	 */
	NO_INDEX_INITIALIZATION("NO_INDEX_INITIALIZATION", Messages.NamedElementIndexNamingStrategyEnum_NoIndexDescription), //$NON-NLS-1$

	/**
	 * Unique Index: the name of the created NamedElement will be suffixed with a unique index. This behavior increases the time to create new element in big model
	 */
	UNIQUE_INDEX_INITIALIZATION("UNIQUE_INDEX_INITIALIZATION", Messages.NamedElementIndexNamingStrategyEnum_UniqueIndexDescription), //$NON-NLS-1$

	/**
	 * Quick Index: the name of the created NamedElements will be suffixed with an quick index. It takes few time, but in some case, the name won't be unique in the Namespace.
	 */
	QUICK_INDEX_INITIALIZATION("QUICK_INDEX_INITIALIZATION", Messages.NamedElementIndexNamingStrategyEnum_QuickIndexDescription); //$NON-NLS-1$


	/**
	 * The name of the initialization method
	 */
	private String name;

	/**
	 * the description of the method
	 */
	private String description;


	/**
	 * 
	 * Constructor.
	 *
	 * @param name
	 *            the name of strategy
	 * @param description
	 *            the description of the strategy
	 */
	private NamedElementIndexNamingStrategyEnum(final String name, final String description) {
		this.name = name;
		this.description = description;
	}

	/**
	 * 
	 * @return
	 * 		the name of the strategy
	 */
	public final String getName() {
		return this.name();
	}

	/**
	 * 
	 * @return
	 * 		the description of the strategy
	 */
	public final String getDescription() {
		return this.description;
	}
}