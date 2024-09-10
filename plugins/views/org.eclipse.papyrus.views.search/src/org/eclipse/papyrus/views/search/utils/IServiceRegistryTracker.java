/*****************************************************************************
 * Copyright (c) 2013, 2023 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Pauline DEVILLE (CEA LIST) <pauline.deville@cea.fr> - Bug 581217
 *
 *****************************************************************************/
package org.eclipse.papyrus.views.search.utils;

import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.views.search.scope.IScopeEntry;


/**
 * A tracker of references to service registries used by {@linkplain IScopeEntry scope entries}, which automatically shuts them down
 * when they are no longer needed.
 */
public interface IServiceRegistryTracker {

	/**
	 * Queries whether I am still actively tracking service registries and able to handle requests to {@link IServiceRegistryTracker#track(Object, ServicesRegistry) track} or {@link #untrack(Object, ServicesRegistry) un-track} service
	 * registries.
	 *
	 * @return whether I am currently active
	 */
	boolean isActive();

	/**
	 * <p>
	 * Adds a service registry for me to track. Multiple service registries may be tracked against one owner, but a service registry may only be tracked against at most one owner.
	 * </p>
	 * <p>
	 * Has no effect if this {@code serviceRegistry} is already tracked against this {@code owner}.
	 * </p>
	 *
	 * @param owner
	 *            the owner of the {@code serviceRegistry}. I track references to this object; when it is no longer in use, the {@code serviceRegistry} is
	 *            shut down.
	 *
	 * @param serviceRegistry
	 *            a service registry to shut down when its {@code owner} is no longer in use
	 *
	 * @throws IllegalArgumentException
	 *             if this {@code serviceRegistry} is already tracked against a different owner than the given {@code owner}
	 *
	 * @throws IllegalStateException
	 *             if I am not {@link #isActive() active}
	 *
	 * @see #untrack(Object, ServicesRegistry)
	 * @see #isActive()
	 */
	void track(Object owner, ServicesRegistry serviceRegistry);

	/**
	 * Stops tracking the given service registry. Has no effect if this {@code serviceRegistry} is not currently being {@linkplain #track(Object, ServicesRegistry) tracked} against this {@code owner}.
	 *
	 * @param owner
	 *            the owner of the {@code serviceRegistry}
	 * @param serviceRegistry
	 *            a service registry that I may be tracking against its {@code owner}
	 *
	 * @throws IllegalStateException
	 *             if I am not {@link #isActive() active}
	 */
	void untrack(Object owner, ServicesRegistry serviceRegistry);
}
