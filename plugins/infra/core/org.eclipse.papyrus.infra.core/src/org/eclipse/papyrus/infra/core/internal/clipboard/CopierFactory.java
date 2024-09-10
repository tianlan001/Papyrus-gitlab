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

package org.eclipse.papyrus.infra.core.internal.clipboard;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.papyrus.infra.core.clipboard.ICopierFactory;

/**
 * A factory that creates copiers for the Papyrus Clipboard.
 */
public class CopierFactory implements ICopierFactory {

	private final ResourceSet resourceSet;
	private final boolean useOriginalReferences;

	/**
	 * Initializes me with the resource set in which context I copy model elements.
	 * Copiers that I create will use original references.
	 * 
	 * @param resourceSet
	 *            my resource set context
	 */
	public CopierFactory(ResourceSet resourceSet) {
		this(resourceSet, true);
	}

	/**
	 * Initializes me with the resource set in which context I copy model elements.
	 * 
	 * @param resourceSet
	 *            my resource set context
	 * @param useOriginalReferences
	 *            whether non-copied references should be used while copying
	 */
	public CopierFactory(ResourceSet resourceSet, boolean useOriginalReferences) {
		super();

		this.resourceSet = resourceSet;
		this.useOriginalReferences = useOriginalReferences;
	}

	/**
	 * Queries whether non-copied references should be used while copying.
	 * 
	 * @return whether to use non-copied references
	 */
	public boolean isUseOriginalReferences() {
		return useOriginalReferences;
	}

	@Override
	public Copier get() {
		DefaultConfiguration configuration = new DefaultConfiguration(true, isUseOriginalReferences());
		CopierConfiguratorRegistry.INSTANCE.configureCopier(configuration);

		return createCopier(configuration);
	}

	private Copier createCopier(DefaultConfiguration configuration) {
		return configuration.isEmpty()
				? createBasicCopier()
				: new ConfiguredCopier(configuration, getPackageRegistry());
	}

	private Copier createBasicCopier() {
		return new BasicCopier(true, isUseOriginalReferences(), getPackageRegistry());
	}

	private EPackage.Registry getPackageRegistry() {
		return (resourceSet == null) ? EPackage.Registry.INSTANCE : resourceSet.getPackageRegistry();
	}

	//
	// Nested types
	//

	private static class BasicCopier extends Copier {
		private static final long serialVersionUID = 1L;

		private final EPackage.Registry packageRegistry;

		BasicCopier(boolean resolveReferences, boolean useOriginalReferences, EPackage.Registry registry) {
			super(resolveReferences, useOriginalReferences);

			this.packageRegistry = registry;
		}

		@Override
		protected EObject createCopy(EObject eObject) {
			EClass eClass = getTarget(eObject);
			EFactory eFactory = getEFactory(eClass);
			return eFactory.create(eClass);
		}

		EFactory getEFactory(EClass eClass) {
			EFactory result = packageRegistry.getEFactory(eClass.getEPackage().getNsURI());

			if (result == null) {
				result = eClass.getEPackage().getEFactoryInstance();
			}

			return result;
		}
	}

	private static class ConfiguredCopier extends BasicCopier {
		private static final long serialVersionUID = 1L;

		private final DefaultConfiguration configuration;

		ConfiguredCopier(DefaultConfiguration configuration, EPackage.Registry registry) {
			super(configuration.isResolveReferences(), configuration.isUseOriginalReferences(), registry);

			this.configuration = configuration;
		}

		@Override
		protected void copyReference(EReference eReference, EObject eObject, EObject copyEObject) {
			if (configuration.shouldCopyReference(eReference, eObject)) {
				super.copyReference(eReference, eObject, copyEObject);
			}
		}
	}
}
