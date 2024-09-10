/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.performances.test;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.UMLFactory;
import org.junit.Assert;

/**
 *
 */
public abstract class AbstractUMLPerformancesTest {

	protected URI umlURI = null;

	protected org.eclipse.uml2.uml.Package rootModel;

	protected void createBaseURI(final long size) {
		if (null == umlURI) {
			String fileName = size + "_Elements";
			final String timsestamp = Long.toString(System.currentTimeMillis());
			fileName = fileName + "_" + timsestamp;
			umlURI = URI.createURI(fileName).appendFileExtension("uml");
		}
	}

	public void createModelAndClasses(final int size) {
		System.out.println("Creation in local file");
		System.out.println("+ create " + size + " elements with the factory " + UMLFactory.eINSTANCE.getClass().getName());

		// 1. Create the resource/model
		createResources(size);
		save();

		long start = System.currentTimeMillis();

		// 2. create the elements
		for (long i = 0; i < size; i++) {
			createUMLClass(rootModel, i);
		}

		long end = System.currentTimeMillis();
		System.out.println("+ " + size + " creations WITH UML done: " + (end - start) + " ms");

		// 3. Save the resource
		start = System.currentTimeMillis();
		save();
		end = System.currentTimeMillis();
		System.out.println("+ save " + size + " elements with EMF: " + (end - start) + " ms");

		// 4. Dispose
		try {
			dispose();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		// 5. Reload in a new ResourceSet
		final ResourceSet resourceSetLoad = new ResourceSetImpl();
		start = System.currentTimeMillis();
		final Resource resource2 = resourceSetLoad.getResource(umlURI, true);
		final org.eclipse.uml2.uml.Package p = (org.eclipse.uml2.uml.Package) resource2.getContents().get(0);
		end = System.currentTimeMillis();
		Assert.assertNotNull(p);
		resource2.unload();

		umlURI = null;

		System.out.println("+ load " + size + " elements with EMF, then get root: " + (end - start) + " ms");
		System.out.println("\n");
	}

	protected abstract void createResources(final long size);

	protected abstract void createUMLClass(final org.eclipse.uml2.uml.Package owner, final long index);

	protected abstract void save();

	public void dispose() throws Exception {
		rootModel = null;
	}

}
