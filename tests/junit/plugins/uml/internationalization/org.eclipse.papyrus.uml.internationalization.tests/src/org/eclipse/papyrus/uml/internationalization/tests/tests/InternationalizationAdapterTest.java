/*****************************************************************************
 * Copyright (c) 2019 CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.internationalization.tests.tests;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.uml.internationalization.edit.providers.InternationalizationUMLItemProviderAdapterFactory;
import org.eclipse.uml2.uml.Abstraction;
import org.eclipse.uml2.uml.UMLFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * This class allows to test the uml internationalization adapter validation.
 */
public class InternationalizationAdapterTest {

	/**
	 * This test allows to test the label provider adapter for UML element (here abstraction for example).
	 */
	@Test
	public void testLabelProviderAdapter() {

		final Abstraction abstraction = UMLFactory.eINSTANCE.createAbstraction();

		// Add abstraction to a resource and a resourceSet. By default this configuration leads to not needsInterationalization
		final Resource resource = new ResourceImpl();
		resource.getContents().add(abstraction);

		final ResourceSet resourceSet = new ModelSet();
		resourceSet.getResources().add(resource);

		// There should be a CacheAdapter
		Assert.assertEquals(1, abstraction.eAdapters().size());

		final ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		composedAdapterFactory.insertAdapterFactory(new InternationalizationUMLItemProviderAdapterFactory());

		// Successive adaptations to IItemLabelProvider should always reveal the same provider
		Adapter labelProviderAdapter1 = composedAdapterFactory.adapt(abstraction, IItemLabelProvider.class);
		Assert.assertTrue(labelProviderAdapter1 instanceof IItemLabelProvider);
		Assert.assertEquals(2, abstraction.eAdapters().size());
		Adapter labelProviderAdapter2 = composedAdapterFactory.adapt(abstraction, IItemLabelProvider.class);
		Assert.assertTrue(labelProviderAdapter2 instanceof IItemLabelProvider);
		Assert.assertEquals(labelProviderAdapter1, labelProviderAdapter2);

		Assert.assertEquals(2, abstraction.eAdapters().size());

	}

}