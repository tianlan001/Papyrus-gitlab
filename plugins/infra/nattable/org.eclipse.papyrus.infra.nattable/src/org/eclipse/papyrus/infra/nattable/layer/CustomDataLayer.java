/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
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
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.layer;

import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.papyrus.infra.nattable.manager.refresh.StructuralRefreshConfiguration;

/**
 * This layer has been created to change the structural refresh behavior of NatTable (bug 562619)
 *
 * @since 6.7
 */
public class CustomDataLayer extends DataLayer {

	/**
	 * Constructor.
	 *
	 * @param dataProvider
	 * @param defaultColumnWidth
	 * @param defaultRowHeight
	 */
	public CustomDataLayer(IDataProvider dataProvider, int defaultColumnWidth, int defaultRowHeight) {
		super(dataProvider, defaultColumnWidth, defaultRowHeight);
		addConfiguration(new StructuralRefreshConfiguration());
	}

	/**
	 * Constructor.
	 *
	 * @param dataProvider
	 */
	public CustomDataLayer(IDataProvider dataProvider) {
		super(dataProvider);
		addConfiguration(new StructuralRefreshConfiguration());
	}

}
