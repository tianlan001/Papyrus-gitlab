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

import org.eclipse.xwt.ILoadingContext;
import org.eclipse.xwt.IXWTLoader;
import org.eclipse.xwt.internal.core.MetaclassManager;
import org.eclipse.xwt.internal.core.MetaclassService;
import org.eclipse.xwt.metadata.IMetaclass;

/**
 * For some reason, the XWT {@link MetaclassManager} doesn't cache
 * CLR Metaclasses (i.e. Papyrus Metaclasses, in our case).
 *
 * This has two consequences: the (Buddy Policy) ClassLoader will
 * be invoked many times for each class, and the {@link IMetaclass#initialize(Object)}
 * method will be invoked as many times. Both are expensive operations.
 *
 * The {@link CachedMetaclassManager} keeps all metaclasses in cache,
 * to make sure that each Java class will be loaded and initialized
 * only once per runtime, in the Papyrus properties view.
 *
 * @since 3.2
 *
 */
public class CachedMetaclassManager extends MetaclassManager {

	/**
	 * Constructor.
	 *
	 * @param service
	 * @param parent
	 * @param xwtLoader
	 */
	public CachedMetaclassManager(MetaclassService service, MetaclassManager parent, IXWTLoader xwtLoader) {
		super(service, parent, xwtLoader);
	}

	@Override
	public IMetaclass getMetaclass(ILoadingContext context, String name, String namespace) {
		IMetaclass metaclass = super.getMetaclass(context, name, namespace);
		if (metaclass != null) {
			register(metaclass);
		}
		return metaclass;
	}

	@Override
	public void register(IMetaclass metaclass) {
		// Force re-registration
		classRegister.remove(metaclass.getType());
		super.register(metaclass);
	}

}
