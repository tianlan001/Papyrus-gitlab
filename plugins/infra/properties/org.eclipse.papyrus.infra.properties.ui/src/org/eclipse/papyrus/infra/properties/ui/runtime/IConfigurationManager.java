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

package org.eclipse.papyrus.infra.properties.ui.runtime;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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
import org.eclipse.papyrus.infra.properties.internal.ContextExtensionPoint;


/**
 * Central API of the Property View framework. It lists the available environments and contexts,
 * and is responsible for Enabling or Disabling contexts programmatically.
 *
 * All {@link Context}s should have unique names.
 *
 * @author Camille Letavernier
 */
public interface IConfigurationManager {

	/**
	 * Tests if a Context is enabled.
	 *
	 * @param context
	 * @return
	 * 		true if the given context is enabled.
	 */
	boolean isApplied(Context context);

	Boolean isAppliedByDefault(Context context);

	/**
	 * @return the list of <strong>enabled</strong> contexts
	 */
	Collection<Context> getEnabledContexts();

	/**
	 * Queries whether a given context is {@linkplain #getEnabledContexts() enabled}.
	 * 
	 * @param context
	 *            a context
	 * @return whether it is currently enabled
	 * 
	 * @see #getEnabledContexts()
	 */
	boolean isEnabled(Context context);

	/**
	 * Tests if a Context is a plugin context. plugin contexts
	 * are registered through {@link ContextExtensionPoint} and are
	 * read-only.
	 *
	 * @param context
	 * @return
	 * 		True if the context comes from a plugin, and is thus read-only
	 */
	boolean isPlugin(Context context);

	/**
	 * Queries whether the specified {@code context} is a proxy for a missing context. That is a
	 * context that is expected to exist but is (temporarily) unavailable.
	 *
	 * @param context
	 *            a context
	 * @return whether it represents a missing context
	 */
	boolean isMissing(Context context);

	/**
	 * Loads a Context from the given URI. The model is loaded in the {@link ConfigurationManager}'s resourceSet
	 *
	 * @param uri
	 *            The URI from which the Context is loaded
	 * @return
	 * 		The loaded context
	 * @throws IOException
	 *             If the URI doesn't represent a valid Context model
	 */
	Context getContext(URI uri) throws IOException;

	/**
	 * Returns the context from the given context name
	 *
	 * @param contextName
	 *            The name of the context to retrieve
	 * @return
	 * 		The context corresponding to the given name
	 */
	Context getContext(String contextName);

	/**
	 * Returns all the known contexts, even if they are not applied
	 * To get only applied contexts, see {@link #getEnabledContexts()}
	 *
	 * @return All known contexts
	 */
	Collection<Context> getContexts();

	/**
	 * Returns all the known customizable contexts.
	 *
	 * @return All known contexts
	 *
	 * @see {@link #getEnabledContexts()}
	 */
	Collection<Context> getCustomizableContexts();

	/**
	 * Obtains proxies (not the EMF kind) for all contexts that the system knows about
	 * but are currently unavailable.
	 *
	 * @return the current collection of missing contexts
	 */
	Collection<Context> getMissingContexts();

	/**
	 * @return the default implementation of CompositeWidgetType
	 */
	CompositeWidgetType getDefaultCompositeType();

	/**
	 * @return the default implementation of LayoutType
	 */
	LayoutType getDefaultLayoutType();

	/**
	 * @return the default implementation of StandardWidgetType
	 */
	StandardWidgetType getDefaultWidgetType();

	/**
	 * @param propertyType
	 * @param multiple
	 * @return the default implementation of PropertyEditorType for the given property Type
	 *         and multiplicity
	 */
	PropertyEditorType getDefaultEditorType(Type propertyType, boolean multiple);

	/**
	 * Returns the default XWT namespaces
	 *
	 * @return the default XWT namespaces
	 */
	Set<Namespace> getBaseNamespaces();

	/**
	 * @param name
	 * @return
	 * 		The namespace corresponding to the given name
	 */
	Namespace getNamespaceByName(String name);

	/**
	 * @param property
	 * @return
	 * 		the default PropertyEditorType for the given Property
	 */
	PropertyEditorType getDefaultEditorType(Property property);

	/**
	 * Retrieves the Property object associated to the propertyPath in the given context
	 *
	 * @param propertyPath
	 * @param context
	 * @return
	 * 		The property associated to the given propertyPath
	 */
	Property getProperty(String propertyPath, Context context);

	/**
	 * Returns the ResourceSet associated to the ConfigurationManager,
	 * ie. the ResourceSet containing all Environments and Contexts
	 *
	 * @return
	 */
	ResourceSet getResourceSet();

	boolean isCustomizable(Context propertyViewConfiguration);

	ViewConstraintEngine getConstraintEngine();

	List<Context> getContextsForPreferencePage(String page);

	void addContext(URI uri) throws IOException;

}