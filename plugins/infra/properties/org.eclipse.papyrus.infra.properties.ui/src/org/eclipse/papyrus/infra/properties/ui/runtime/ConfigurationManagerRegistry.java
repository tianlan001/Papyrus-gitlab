/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.properties.ui.runtime;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.contexts.Property;
import org.eclipse.papyrus.infra.properties.environment.CompositeWidgetType;
import org.eclipse.papyrus.infra.properties.environment.LayoutType;
import org.eclipse.papyrus.infra.properties.environment.Namespace;
import org.eclipse.papyrus.infra.properties.environment.PropertyEditorType;
import org.eclipse.papyrus.infra.properties.environment.StandardWidgetType;
import org.eclipse.papyrus.infra.properties.environment.Type;
import org.eclipse.papyrus.infra.properties.internal.ui.Activator;
import org.eclipse.papyrus.infra.properties.internal.ui.runtime.ConfigurationConflict;
import org.eclipse.papyrus.infra.properties.internal.ui.runtime.IInternalConfigurationManager;

/**
 * Registry of configuration manager implementations.
 */
class ConfigurationManagerRegistry {

	ConfigurationManagerRegistry() {
		super();
	}

	IInternalConfigurationManager getConfigurationManager() {
		IInternalConfigurationManager result = null;

		for (IConfigurationElement config : Platform.getExtensionRegistry().getConfigurationElementsFor(Activator.PLUGIN_ID, "configuration")) { //$NON-NLS-1$
			switch (config.getName()) {
			case "configurationManager": //$NON-NLS-1$
				try {
					if (result == null) {
						result = createConfigurationManager(config);
					} else {
						Activator.log.warn("Multiple configuration managers installed. Ignoring implementation provided by " + config.getContributor().getName()); //$NON-NLS-1$
					}
				} catch (Exception e) {
					Activator.log.error("Invalid configuration manager extension.", e); //$NON-NLS-1$
				}

				break;
			}
		}

		if (result == null) {
			result = createNullConfigurationManager();
		}

		return result;
	}

	private IInternalConfigurationManager createConfigurationManager(IConfigurationElement config) throws Exception {
		IInternalConfigurationManager.Provider provider = (IInternalConfigurationManager.Provider) config.createExecutableExtension("class"); //$NON-NLS-1$
		IInternalConfigurationManager result = provider.getConfigurationManager();

		if (result == null) {
			Activator.log.error("Extension does not provide a configuration manger in contributor " + config.getContributor().getName(), null); //$NON-NLS-1$
		}

		return result;
	}

	private IInternalConfigurationManager createNullConfigurationManager() {
		Activator.log.error("No configuration manager is installed.  The properties UI will not function.", null); //$NON-NLS-1$

		return new IInternalConfigurationManager() {

			private ViewConstraintEngine constraintEngine;

			@Override
			public boolean isPlugin(Context context) {
				return false;
			}

			@Override
			public boolean isMissing(Context context) {
				return true;
			}

			@Override
			public boolean isEnabled(Context context) {
				return false;
			}

			@Override
			public boolean isCustomizable(Context propertyViewConfiguration) {
				return false;
			}

			@Override
			public Boolean isAppliedByDefault(Context context) {
				return Boolean.FALSE;
			}

			@Override
			public boolean isApplied(Context context) {
				return false;
			}

			@Override
			public ResourceSet getResourceSet() {
				return null;
			}

			@Override
			public Property getProperty(String propertyPath, Context context) {
				return null;
			}

			@Override
			public Namespace getNamespaceByName(String name) {
				return null;
			}

			@Override
			public Collection<Context> getMissingContexts() {
				return Collections.emptySet();
			}

			@Override
			public Collection<Context> getEnabledContexts() {
				return Collections.emptySet();
			}

			@Override
			public StandardWidgetType getDefaultWidgetType() {
				return null;
			}

			@Override
			public LayoutType getDefaultLayoutType() {
				return null;
			}

			@Override
			public PropertyEditorType getDefaultEditorType(Property property) {
				return null;
			}

			@Override
			public PropertyEditorType getDefaultEditorType(Type propertyType, boolean multiple) {
				return null;
			}

			@Override
			public CompositeWidgetType getDefaultCompositeType() {
				return null;
			}

			@Override
			public Collection<Context> getCustomizableContexts() {
				return Collections.emptySet();
			}

			@Override
			public List<Context> getContextsForPreferencePage(String page) {
				return Collections.emptyList();
			}

			@Override
			public Collection<Context> getContexts() {
				return Collections.emptySet();
			}

			@Override
			public Context getContext(String contextName) {
				return null;
			}

			@Override
			public Context getContext(URI uri) throws IOException {
				throw new IOException("Properties configuration manager is unavailable"); //$NON-NLS-1$
			}

			@Override
			public ViewConstraintEngine getConstraintEngine() {
				if (constraintEngine == null) {
					constraintEngine = new ViewConstraintEngineImpl(this);
				}

				return constraintEngine;
			}

			@Override
			public Set<Namespace> getBaseNamespaces() {
				return Collections.emptySet();
			}

			@Override
			public void addContext(URI uri) throws IOException {
				throw new IOException("Properties configuration manager is unavailable"); //$NON-NLS-1$
			}

			@Override
			public void update() {
				// Pass
			}

			@Override
			public void refresh(Context context) {
				// Pass
			}

			@Override
			public void enableContext(Context context, boolean update) {
				// Pass
			}

			@Override
			public void disableContext(Context context, boolean update) {
				// Pass
			}

			@Override
			public void deleteContext(Context context) {
				// Pass
			}

			@Override
			public Collection<ConfigurationConflict> checkConflicts(Collection<? extends Context> contexts) {
				return Collections.emptySet();
			}

			@Override
			public Collection<ConfigurationConflict> checkConflicts() {
				return Collections.emptySet();
			}

			@Override
			public void addContext(Context context, boolean apply) {
				// Pass
			}
		};
	}
}
