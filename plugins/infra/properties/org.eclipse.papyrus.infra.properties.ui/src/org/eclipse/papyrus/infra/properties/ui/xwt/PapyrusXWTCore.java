/*****************************************************************************
 * Copyright (c) 2017 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.properties.ui.xwt;

import org.eclipse.xwt.XWTLoaderManager;
import org.eclipse.xwt.internal.core.Core;
import org.eclipse.xwt.internal.core.MetaclassService;
import org.eclipse.xwt.javabean.ResourceLoaderFactory;

/**
 * @since 3.2
 */
public class PapyrusXWTCore extends Core {

	/**
	 * Constructor.
	 *
	 * @param loaderFactory
	 * @param xwtLoader
	 */
	public PapyrusXWTCore() {
		super(new ResourceLoaderFactory(), XWTLoaderManager.getActive());
		metaclassService = new CachedMetaclassService(XWTLoaderManager.getActive());
	}

	private final MetaclassService metaclassService;

	/**
	 * @see org.eclipse.xwt.internal.core.Core#getMetaclassService()
	 *
	 * @return
	 */
	@Override
	public MetaclassService getMetaclassService() {
		return metaclassService;
	}

}
