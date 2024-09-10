/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.properties.ui.providers;

import org.eclipse.papyrus.infra.properties.ui.modelelement.DataSource;

/**
 * This allows to update the data source of the XWT compliant mask provider for the properties view.
 *
 * @since 3.4
 */
public interface XWTCompliantMaskProviderUpdater {

	/**
	 * This allows to update the data source depending to the observable.
	 *
	 * @param input
	 *            The input to set.
	 */
	public void setInput(final DataSource input);
}
