/*****************************************************************************
 * Copyright (c) 2010, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - Factor out workspace storage for pluggable storage providers (CDO)
 *  Christian W. Damus (CEA) - Support implicit enablement of prototypes of unavailable contexts (CDO)
 *  Christian W. Damus - bugs 482930, 469188, 485220
 *****************************************************************************/

package org.eclipse.papyrus.infra.properties.internal.ui.runtime;

import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.internal.ContextExtensionPoint;
import org.eclipse.papyrus.infra.properties.ui.runtime.IConfigurationManager;

/**
 * Internal interface for manipulation of the Properties Run-time configuration.
 */
public interface IInternalConfigurationManager extends IConfigurationManager {

	/**
	 * Checks the conflicts between all applied {@linkplain #getEnabledContexts() enabled}) configurations
	 * A Conflict may occur when two sections have the same ID : they can't
	 * be displayed at the same time
	 *
	 * @return
	 * 		The list of conflicts
	 */
	Collection<ConfigurationConflict> checkConflicts();

	/**
	 * Checks the conflicts between those of the specified configurations that are enabled.
	 * A Conflict may occur when two sections have the same ID : they can't
	 * be displayed at the same time.
	 *
	 * @param contexts
	 *            a set of configurations to check for conflicts (amongst the subset of these
	 *            that are actually {@linkplain #isEnabled(Context) enabled}
	 * 
	 * @return
	 * 		The list of conflicts
	 * 
	 * @see #isEnabled(Context)
	 */
	Collection<ConfigurationConflict> checkConflicts(Collection<? extends Context> contexts);

	/**
	 * Enables a Context
	 *
	 * @param context
	 *            The Context to enable
	 * @param update
	 *            If true, the constraint engine will be updated to handle the
	 *            modification
	 *            If false, you should call manually {@link #update()} to refresh
	 *            the constraint engine
	 *
	 * @see #disableContext(Context, boolean)
	 */
	void enableContext(Context context, boolean update);

	/**
	 * Disable a Context.
	 *
	 * @param context
	 *            The Context to disable
	 * @param update
	 *            If true, the constraint engine will be updated to handle the
	 *            modification
	 *            If false, you should call manually {@link #update()} to refresh
	 *            the constraint engine
	 * @see Preferences
	 * @see #enableContext(Context, boolean)
	 */
	void disableContext(Context context, boolean update);

	/**
	 * Updates the constraint engine to handle changes in the contexts
	 * activation
	 */
	void update();


	/**
	 * Programmatically register a new context to this ConfigurationManager.
	 * Most of the time, new contexts should be registered through {@link ContextExtensionPoint}.
	 * However, you can still call this method when creating a Context at runtime, programmatically
	 * (Wizards, ...)
	 * All {@link Context} should have unique names
	 *
	 * @param context
	 *            The new context to register
	 * @param apply
	 *            Whether the context should be enabled or not
	 *
	 * @see ConfigurationManager#addContext(URI)
	 */
	void addContext(Context context, boolean apply);

	/**
	 * Refresh the given Context. This method should be called when a model is edited
	 * at runtime, to re-load it from persistent storage.
	 *
	 * @param context
	 *            A Context model to re-load
	 */
	void refresh(Context context);

	/**
	 * Disable, then unregisters a Context. The Context won't be available anymore in the framework
	 * (not even in the Preferences page). This method <strong>won't</strong> delete the context's files
	 * on the file system.
	 *
	 * @param context
	 *            The context to delete
	 */
	void deleteContext(Context context);

	//
	// Nested types
	//

	interface Provider {
		IInternalConfigurationManager getConfigurationManager();
	}
}
