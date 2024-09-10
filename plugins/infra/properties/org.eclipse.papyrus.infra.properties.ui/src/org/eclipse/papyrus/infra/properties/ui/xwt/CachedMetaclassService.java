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

import org.eclipse.xwt.IConstants;
import org.eclipse.xwt.ILoadingContext;
import org.eclipse.xwt.IXWTLoader;
import org.eclipse.xwt.internal.core.MetaclassManager;
import org.eclipse.xwt.internal.core.MetaclassService;
import org.eclipse.xwt.metadata.IMetaclass;

/**
 * Override the {@link MetaclassService} to use {@link CachedMetaclassManager}
 * instances instead of the default {@link MetaclassManager}.
 *
 * @since 3.2
 *
 * @see {@link CachedMetaclassManager}
 */
public class CachedMetaclassService extends MetaclassService {

	private IXWTLoader xwtLoader;

	/**
	 * Constructor.
	 *
	 * @param xwtLoader
	 */
	public CachedMetaclassService(IXWTLoader xwtLoader) {
		super(xwtLoader);
		this.xwtLoader = xwtLoader;
	}

	/**
	 * @see org.eclipse.xwt.internal.core.MetaclassService#getMetaclass(org.eclipse.xwt.ILoadingContext, java.lang.String, java.lang.String)
	 *
	 * @param context
	 * @param name
	 * @param namespace
	 * @return
	 */
	@Override
	public IMetaclass getMetaclass(ILoadingContext context, String name, String namespace) {
		registerManager(namespace, IConstants.XWT_NAMESPACE);
		return super.getMetaclass(context, name, namespace);
	}

	/**
	 * @see org.eclipse.xwt.internal.core.MetaclassService#getMetaclass(java.lang.Class)
	 *
	 * @param type
	 * @return
	 */
	@Override
	public IMetaclass getMetaclass(Class<?> type) {
		return super.getMetaclass(type);
	}

	/**
	 * @see org.eclipse.xwt.internal.core.MetaclassService#getMetaclass(java.lang.Class, java.lang.String)
	 *
	 * @param type
	 * @param namespace
	 * @return
	 */
	@Override
	public IMetaclass getMetaclass(Class<?> type, String namespace) {
		registerManager(namespace, null);
		return super.getMetaclass(type, namespace);
	}

	/**
	 * @see org.eclipse.xwt.internal.core.MetaclassService#register(org.eclipse.xwt.metadata.IMetaclass, java.lang.String)
	 *
	 * @param metaclass
	 * @param namespace
	 */
	@Override
	public void register(IMetaclass metaclass, String namespace) {
		registerManager(namespace, null);
		super.register(metaclass, namespace);
	}

	/**
	 * @param namespace
	 */
	private void registerManager(String namespace, String parentManagerKey) {
		if (map.containsKey(namespace)) {
			return;
		}
		MetaclassManager parent = parentManagerKey == null ? null : map.get(parentManagerKey);
		map.put(namespace, new CachedMetaclassManager(this, parent, xwtLoader));
	}
}
