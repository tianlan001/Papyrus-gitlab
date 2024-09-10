/*******************************************************************************
 * Copyright (c) 2008 CEA LIST.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.infra.core.utils;

/**
 * This interface contains all channels to trace papyrus
 *
 * @author Patrick Tessier
 */
public interface IDebugChannel {

	/**
	 * constant used to trace the core running
	 */
	public static final String PAPYRUS_CORE = "org.eclipse.papyrus.infra.core/debug/core";

	/**
	 * constant used to trace the loading of extension point
	 */
	public static final String PAPYRUS_EXTENSIONPOINT_LOADING = "org.eclipse.papyrus.infra.core/debug/extensionpoint";

}
