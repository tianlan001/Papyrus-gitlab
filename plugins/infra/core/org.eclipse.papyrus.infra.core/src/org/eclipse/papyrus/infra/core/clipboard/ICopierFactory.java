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

package org.eclipse.papyrus.infra.core.clipboard;

import java.util.function.BiPredicate;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.papyrus.infra.core.internal.clipboard.CopierFactory;

/**
 * An extensible factory for EMF {@link Copier}s used for copy/paste
 * operations in Papyrus. Extensions are registered on the
 * {@code org.eclipse.papyrus.infra.core.copier} extension point.
 * 
 * @since 3.0
 * 
 * @see EcoreUtil.Copier
 */
@FunctionalInterface
public interface ICopierFactory extends Supplier<EcoreUtil.Copier> {

	/**
	 * Obtains a copier factory suitable for most copy/paste operations.
	 * The result is configured from the extension point.
	 * 
	 * @param resourceSet
	 *            a resource set context in which to look for registered
	 *            {@link EFactory} instances for creation of new objects
	 * @return a copier factory
	 */
	static ICopierFactory getInstance(ResourceSet resourceSet) {
		return getInstance(resourceSet, true);
	}

	/**
	 * Obtains a copier factory with the option of not using original references.
	 * The result is configured from the extension point.
	 * 
	 * @param resourceSet
	 *            a resource set context in which to look for registered
	 *            {@link EFactory} instances for creation of new objects
	 * @param useOriginalReferences
	 *            whether non-copied references should be used while copying
	 * 
	 * @return a copier factory
	 */
	static ICopierFactory getInstance(ResourceSet resourceSet, boolean useOriginalReferences) {
		return new CopierFactory(resourceSet, useOriginalReferences);
	}

	//
	// Nested types
	//

	/**
	 * Configuration protocol for the {@linkplain ICopierFactory copier factory}
	 * that {@link Configurator} extensions use to tweak the behaviour of
	 * the copiers that it creates.
	 * 
	 * @noextend This interface is not intended to be extended by clients.
	 * @noimplement This interface is not intended to be implemented by clients.
	 * 
	 * @since 2.2
	 */
	interface Configuration {
		/**
		 * Queries whether the copier to be configured resolves references
		 * in its copying. {@link Configurator}s may need to know this to determine
		 * how to configure it.
		 * 
		 * @return whether the copier resolves references
		 */
		boolean isResolveReferences();

		/**
		 * Queries whether the copier to be configured uses original references
		 * in its copying. {@link Configurator}s may need to know this to determine
		 * how to configure it.
		 * 
		 * @return whether the copier uses original references
		 */
		boolean isUseOriginalReferences();

		/**
		 * Adds a filter matching references that should not be copied for
		 * select objects.
		 * 
		 * @param filter
		 *            a filter that matches some reference for some object
		 *            that should not have that reference copied
		 */
		void filterReferences(BiPredicate<? super EReference, ? super EObject> filter);
	}

	/**
	 * Extension protocol for the {@linkplain ICopierFactory copier factory}
	 * that allows plug-ins to customize the behaviour of the copiers that it creates.
	 * Instances are registered on the {@code org.eclipse.papyrus.infra.core.copier}
	 * extension point.
	 * 
	 * @since 2.2
	 */
	@FunctionalInterface
	interface Configurator {
		/**
		 * Installs configurations for the copier factory.
		 * 
		 * @param copierConfiguration
		 *            the configuration to update
		 */
		void configureCopier(Configuration copierConfiguration);
	}
}
