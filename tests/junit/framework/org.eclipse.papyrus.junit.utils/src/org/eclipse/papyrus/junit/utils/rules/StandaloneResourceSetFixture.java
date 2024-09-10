/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

package org.eclipse.papyrus.junit.utils.rules;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.net.URL;
import java.util.Iterator;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.papyrus.junit.utils.JUnitUtils;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.google.common.collect.Iterables;

/**
 * A {@link ResourceSet} fixture rule that is compatible with stand-alone JUnit
 * execution (not in an Eclipse instance). It uses the {@link JavaResource}
 * annotation (only, not also {@link PluginResource}) to identify test resources
 * to load into the resource set on set-up.
 * 
 * @param <T>
 *            the kind of model fixture element to load
 */
public class StandaloneResourceSetFixture<T extends EObject> extends TestWatcher {

	private final Class<? extends T> modelType;

	private ResourceSet resourceSet;
	private Resource resource;
	private T model;

	/**
	 * Initializes me with the kind of model fixture element to load.
	 *
	 * @param modelType
	 *            the model fixture element's type
	 */
	public StandaloneResourceSetFixture(Class<? extends T> modelType) {
		super();

		this.modelType = modelType;
	}

	/**
	 * Obtains the resource set in which the {@linkplain #getModel() model fixture} is loaded.
	 * 
	 * @see #getModel()
	 */
	public ResourceSet getResourceSet() {
		return resourceSet;
	}

	/**
	 * Obtains the resource containing the {@linkplain #getModel() model fixture}.
	 * 
	 * @see #getModel()
	 */
	public Resource getResource() {
		return resource;
	}

	public Resource getResource(URI uri, boolean loadOnDemand) {
		return getResourceSet().getResource(uri, loadOnDemand);
	}

	/**
	 * Obtains the model fixture.
	 */
	public T getModel() {
		return model;
	}

	/**
	 * Obtains the root model element of the specified type from my resource set.
	 * 
	 * @param modelType
	 *            the type of model to retrieve, or {@code null} if none is found
	 */
	public <E extends EObject> E getModel(Class<E> modelType) {
		E result = null;

		Resource resource;
		for (Iterator<Resource> iter = resourceSet.getResources().iterator(); (result == null) && iter.hasNext();) {
			resource = iter.next();
			result = Iterables.getFirst(Iterables.filter(resource.getContents(), modelType), null);
		}

		return result;
	}

	public static <T extends EObject> StandaloneResourceSetFixture<T> create(Class<T> modelType) {
		return new StandaloneResourceSetFixture<>(modelType);
	}

	@Override
	protected void starting(Description description) {
		JavaResource annotation = JUnitUtils.getAnnotation(description, JavaResource.class);
		assertThat("No @JavaResource annotation found in test case.", annotation, notNullValue());

		resourceSet = new ResourceSetImpl();

		if (!EcorePlugin.IS_ECLIPSE_RUNNING) {
			// EMF ensures that additional invocations have no effect
			EcorePlugin.ExtensionProcessor.process(null);
		}

		final Class<?> testClass = JUnitUtils.getTestClass(description);
		for (String next : annotation.value()) {
			resourceSet.getResource(getTestResourceURI(next, testClass), true);
		}

		model = getModel(modelType);

		assertThat("No model of type " + modelType.getSimpleName() + " loaded.", model, notNullValue());
		resource = model.eResource();
	}

	@Override
	protected void finished(Description description) {
		for (Iterator<Resource> iter = resourceSet.getResources().iterator(); iter.hasNext();) {
			Resource next = iter.next();

			next.unload();
			iter.remove();

			next.eAdapters().clear();
		}

		resourceSet.eAdapters().clear();
		resourceSet = null;
		resource = null;
		model = null;
	}

	/**
	 * Obtains the URI of a test resource.
	 * 
	 * @param path
	 *            the path of the test resource. Absolute paths are searched on the classpath
	 *            and relative paths are searched relative to the given {@code context} class
	 * @param context
	 *            the context class for resolution of relative resource paths
	 * 
	 * @return the URI of the referenced test resource
	 */
	protected URI getTestResourceURI(String path, Class<?> context) {
		URL resultURL;

		IPath resourcePath = new Path(path);
		if (resourcePath.isAbsolute()) {
			resultURL = context.getClassLoader().getResource(path);
		} else {
			resultURL = context.getResource(path);
		}

		return URI.createURI(resultURL.toString(), true);
	}
}
