/*****************************************************************************
 * Copyright (c) 2020, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *   Christian W. Damus - bug 570097
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.plugin.builder.helper;

/**
 * This class allows
 * <ul>
 * <li>to get the current version of a bundle</li>
 * <li>to calculate the dependency range to declare in the manifest</li>
 * </ul>
 *
 * @deprecated Since version 1.1 of the bundle, use the {@link org.eclipse.papyrus.toolsmiths.validation.common.utils.BundleVersionHelper} class, instead.
 */
@Deprecated
public class BundleVersionHelper extends org.eclipse.papyrus.toolsmiths.validation.common.utils.BundleVersionHelper {

	/**
	 *
	 * Constructor.
	 *
	 * @param bundleName
	 *            the name of the bundle
	 */
	public BundleVersionHelper(final String bundleName) {
		super(bundleName);
	}

}
