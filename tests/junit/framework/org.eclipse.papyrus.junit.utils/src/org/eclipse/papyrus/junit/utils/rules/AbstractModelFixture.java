/**
 * Copyright (c) 2014, 2016 CEA, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus (CEA) - Initial API and implementation
 *   Christian W. Damus - bugs 399859, 451230, 458685, 469188, 485220, 496299
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 549108
 */
package org.eclipse.papyrus.junit.utils.rules;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xml.type.AnyType;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.IWorkspaceCommandStack;
import org.eclipse.papyrus.infra.core.resource.ModelMultiException;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.sasheditor.DiModel;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationModel;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.junit.utils.JUnitUtils;
import org.eclipse.papyrus.uml.tools.model.UmlModel;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.google.common.base.Charsets;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.ByteStreams;
import com.google.common.io.Resources;


/**
 * Abstract superclass for JUnit test fixture rules that provide:
 * <ul>
 * <li>an editing domain of some kind (subclasses must create it)</li>
 * <li>a test project in the workspace, exposed via a nested {@link ProjectFixture} rule</li>
 * <li>a test {@link Package} loaded from a resource in the plug-in and saved as <tt>model.uml</tt> in the test project. This model is specified using an annotation on the test, as described below</li>
 * </ul>
 * The test model template to load into the editing domain and project must be specified by one of the following annotations:
 * <ul>
 * <li>{@link JavaResource @JavaResource}: specifies the path to a resource to be loaded from the test class's classpath, using the {@link Class#getResource(String)} API</li>
 * <li>{@link PluginResource @PluginResource}: specifies a path relative to the root of the OSGi bundle containing the test class, to be loaded via the {@link Bundle#getEntry(String)} API</li>
 * </ul>
 * The resource annotation may be specified either on the test method, in which case it applies to that test case, or on the test
 * class, in which case it applies to all test methods in the class that do not have a resource annotation of their own (method
 * annotations take precedence over the class annotation).
 */
public abstract class AbstractModelFixture<T extends EditingDomain> extends TestWatcher {

	private final ProjectFixture project = new ProjectFixture();

	private T domain;

	private EObject root;

	private Package model;

	private Class<?> testClass;

	private Iterable<URI> initialResourceURIs;

	/**
	 * @since 2.3
	 */
	private List<String> managedFileExtension;

	public AbstractModelFixture() {
		super();
		this.managedFileExtension = new ArrayList<>();
		this.managedFileExtension.add(DiModel.DI_FILE_EXTENSION);
		this.managedFileExtension.add(UmlModel.UML_FILE_EXTENSION);
		this.managedFileExtension.add(NotationModel.NOTATION_FILE_EXTENSION);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param additionalFileExtension
	 *            the extension of additional files to manage during the project creation
	 * @since 2.3
	 */
	public AbstractModelFixture(final List<String> additionalFileExtension) {
		this();
		this.managedFileExtension.addAll(additionalFileExtension);
	}


	@Override
	public Statement apply(Statement base, Description description) {
		testClass = JUnitUtils.getTestClass(description);

		// Wrap myself in the project rule so that the project exists when I start
		Statement result = super.apply(base, description);
		result = project.apply(result, description);
		return result;
	}

	/**
	 * Obtains the nested project fixture rule. If stored in a field of the test class, it must not be annotated as a {@link Rule @Rule} because that
	 * would result in double initialization of the rule.
	 *
	 * @return the nested project fixture
	 */
	public ProjectFixture getProject() {
		return project;
	}

	public T getEditingDomain() {
		return domain;
	}

	public void execute(Command command) {
		assertThat("Command not executable", command.canExecute(), is(true));
		getEditingDomain().getCommandStack().execute(command);
	}

	public IStatus execute(IUndoableOperation operation, IProgressMonitor monitor, IAdaptable info) {
		assertThat("Operation not executable", operation.canExecute(), is(true));
		assertThat("No operation history available", getEditingDomain().getCommandStack(), instanceOf(IWorkspaceCommandStack.class));

		try {
			IWorkspaceCommandStack stack = (IWorkspaceCommandStack) getEditingDomain().getCommandStack();
			operation.addContext(stack.getDefaultUndoContext());
			return stack.getOperationHistory().execute(operation, monitor, info);
		} catch (ExecutionException e) {
			e.printStackTrace();
			fail("Command execution failed: " + e.getLocalizedMessage());
			return null; // Unreachable
		}
	}

	public IStatus execute(IUndoableOperation operation) {
		return execute(operation, null, null);
	}

	public boolean canUndo() {
		return getEditingDomain().getCommandStack().canUndo();
	}

	public void undo() {
		assertThat("Cannot undo", canUndo(), is(true));
		getEditingDomain().getCommandStack().undo();
	}

	public boolean canRedo() {
		return getEditingDomain().getCommandStack().canRedo();
	}

	public void redo() {
		assertThat("Cannot redo", canRedo(), is(true));
		getEditingDomain().getCommandStack().redo();
	}

	public ResourceSet getResourceSet() {
		EditingDomain domain = getEditingDomain();
		return (domain == null) ? null : domain.getResourceSet();
	}

	/**
	 * Obtains the first root of the main test resource.
	 *
	 * @return the first test resource root
	 */
	public EObject getRoot() {
		return root;
	}

	/**
	 * Obtains the test model, which is resident in the <tt>model.uml</tt> file in the test project (as indicated by its {@linkplain #getModelResourceURI() URI}).
	 *
	 * @return the test model
	 */
	public Package getModel() {
		return model;
	}

	public Resource getModelResource() {
		return getRoot().eResource();
	}

	public URI getModelResourceURI() {
		return getModelResource().getURI();
	}

	public URI getModelURI() {
		return EcoreUtil.getURI(getRoot());
	}

	protected abstract T createEditingDomain();

	@Override
	protected void starting(Description description) {
		domain = createEditingDomain();

		Resource main = Iterables.getFirst(initModelResources(description), null);
		assertThat("No main UML resource in model fixture", main, notNullValue());

		root = main.getContents().get(0);
		if (root instanceof Package) {
			model = (Package) root;
		}

		// We have finished initializing
		initialResourceURIs = null;

		didLoadResourceSet();
	}

	protected void didLoadResourceSet() {
		// Pass
	}

	protected Iterable<Resource> initModelResources(Description description) {
		List<Resource> result;

		// Don't initialize the resources more than once (subclasses such as PapyrusEditorFixture can repeat this)
		if (initialResourceURIs == null) {
			Annotation resourceAnnotation = getResourceAnnotation(description);
			ResourceKind kind = ResourceKind.getResourceKind(resourceAnnotation);

			final String[] paths = kind.getResourcePaths(resourceAnnotation);
			result = Lists.newArrayListWithCapacity(paths.length);

			for (String path : paths) {
				// Ensure that the bundle ID prefix, if any, is taken as the "device"
				Resource resource = initModelResource(Path.forWindows(path), kind);
				if (resource != null) {
					result.add(initModelResource(Path.forWindows(path), kind));
				}
			}

			List<URI> uris = Lists.newArrayListWithCapacity(result.size());
			for (Resource next : result) {
				uris.add(next.getURI());
			}
			initialResourceURIs = uris;

			// Ensure that the ModelSet's IModels are started
			ModelSet modelSet = TypeUtils.as(getResourceSet(), ModelSet.class);
			if (modelSet != null) {
				// It doesn't matter that the resource is already loaded
				try {
					modelSet.loadModels(result.get(0).getURI());
				} catch (ModelMultiException e) {
					// Continue with the test as well as we can
					e.printStackTrace();
				}
			}
		} else {
			ResourceSet rset = getResourceSet();
			boolean bootstrapResourceSet = rset == null;
			if (bootstrapResourceSet) {
				// Bootstrap the initialization of the test model with a plain resource set
				rset = new ResourceSetImpl();
				rset.getLoadOptions().put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE, true);
				rset.getLoadOptions().put(XMLResource.OPTION_LAX_FEATURE_PROCESSING, true);
			}
			result = Lists.newArrayList();
			try {
				for (URI next : initialResourceURIs) {
					result.add(rset.getResource(next, true));
				}
			} finally {
				if (bootstrapResourceSet) {
					EMFHelper.unload(rset);
				}
			}
		}

		return result;
	}

	private Resource initModelResource(IPath resourcePath, ResourceKind kind) {
		String targetResourceName = "model";
		if (isDIModel(resourcePath)) {
			// We will be initializing all three resources, and they have cross-references, so must not change
			// resource name
			targetResourceName = resourcePath.removeFileExtension().lastSegment();
		}

		return initModelResource(targetResourceName, kind, resourcePath.toString());
	}

	protected boolean isDIModel(IPath path) {
		String fileExtension = path.getFileExtension();
		return DiModel.DI_FILE_EXTENSION.equals(fileExtension);
	}

	protected Resource initModelResource(String targetPath, ResourceKind resourceKind, String resourcePath) {
		Resource result = null;

		ResourceSet resourceSet = getResourceSet();
		final boolean bootstrapResourceSet = resourceSet == null;
		if (bootstrapResourceSet) {
			// Bootstrap the initialization of the test model with a plain resource set
			resourceSet = new ResourceSetImpl();
			resourceSet.getLoadOptions().put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE, true);
			resourceSet.getLoadOptions().put(XMLResource.OPTION_LAX_FEATURE_PROCESSING, true);
		}

		Set<Resource> toUnload = Sets.newHashSet();
		try {
			// Ensure that the bundle ID prefix, if any, is taken as the "device"
			IPath resourceIPath = Path.forWindows(resourcePath);
			if (isDIModel(resourceIPath)) {
				// Try to initialize the entire collection of files
				resourceIPath = resourceIPath.removeFileExtension();

				Map<IPath, Boolean> manifest = loadManifest(resourceKind, resourceIPath);
				for (Map.Entry<IPath, Boolean> next : manifest.entrySet()) {
					Resource resource = doInitModelResource(resourceSet, targetPath, resourceKind, next.getKey());

					if ((result == null) && UmlModel.UML_FILE_EXTENSION.equals(next.getKey().getFileExtension())) {
						// We should always have this one, at least, and it's the one we most care about
						result = resource;
					}

					if (!next.getValue()) {
						// Unload this resource
						toUnload.add(resource);
					}
				}
			} else {
				result = doInitModelResource(resourceSet, targetPath, resourceKind, resourceIPath);
			}

			if (result == null) {
				fail("No UML resource in test model");
			}

			// Look for any other dependencies (libraries, profiles, etc.) that also need to be copied
			Queue<Resource> dependents = new LinkedList<>();
			Set<Resource> scanned = new HashSet<>();
			dependents.add(result);
			boolean loadedProfiles = false;
			for (Resource dependent = dependents.poll(); dependent != null; dependent = dependents.poll()) {
				if (scanned.add(dependent)) {
					URI baseURI = result.getURI().trimSegments(1);
					if (!baseURI.isPrefix()) {
						baseURI = baseURI.appendSegment("");
					}

					for (EObject proxy : EcoreUtil.UnresolvedProxyCrossReferencer.find(dependent).keySet()) {
						URI dependencyURI = EcoreUtil.getURI(proxy).trimFragment();
						if (dependencyURI.toString().startsWith(baseURI.toString())) {
							Resource dependency = resourceSet.getResource(dependencyURI, false);
							if ((dependency == null) || !dependency.isLoaded() || !dependency.getErrors().isEmpty()) {
								// It should be available in the test bundle. Try to get it
								URI relative = dependencyURI.deresolve(baseURI);
								IPath depPath = resourceIPath.removeLastSegments(1).append(URI.decode(relative.toString()));
								if (resourceKind.exists(testClass, depPath)) {
									if (dependency == null) {
										dependency = resourceSet.createResource(dependencyURI);
									} else {
										dependency.unload();
									}

									dependency = doInitModelResource(resourceSet, URI.decode(relative.toString()), resourceKind, depPath);
									loadedProfiles = loadedProfiles || Iterables.any(dependency.getContents(), Predicates.instanceOf(Profile.class));

									// Enqueue this for recursive dependency processing
									dependents.add(dependency);
								}
							}
						}
					}
				}
			}

			// If we depend on profiles, then we may have stereotype applications that need to resolve against that schema.
			// In such case, re-load the model resource to resolve the stereotype schema
			if (loadedProfiles && Iterables.any(result.getContents(), Predicates.instanceOf(AnyType.class))) {
				try {
					result.unload();
					result.load(null);
				} catch (Exception e) {
					e.printStackTrace();
					fail("Error re-loading resource to resolve stereotype schema: " + e.getLocalizedMessage());
				}
			}

			// Now unload resources that the manifest indicates should not be loaded initially
			for (Resource next : toUnload) {
				next.unload();
				next.getResourceSet().getResources().remove(next);
				next.eAdapters().clear();
			}
		} finally {
			if (bootstrapResourceSet) {
				EMFHelper.unload(resourceSet);
			}
		}

		return result;
	}

	private Map<IPath, Boolean> loadManifest(ResourceKind resourceKind, IPath resourceIPath) {
		Map<IPath, Boolean> result = null;
		IPath manifestPath = resourceIPath.addFileExtension("manifest");

		URL manifestURL = resourceKind.getResourceURL(testClass, manifestPath);
		if (manifestURL != null) {
			try {
				result = parseManifest(manifestPath.removeLastSegments(1), manifestURL);
			} catch (IOException e) {
				e.printStackTrace();
				// Create a default manifest
			}
		}

		if (result == null) {
			// Default manifest
			result = Maps.newHashMap();
			IPath basePath = manifestPath.removeFileExtension();
			for (final String current : this.managedFileExtension) {
				result.put(basePath.addFileExtension(current), true);
			}
		}

		return result;
	}

	private Map<IPath, Boolean> parseManifest(IPath baseResourcePath, URL manifestURL) throws IOException {
		Map<IPath, Boolean> result = Maps.newLinkedHashMap();

		List<String> lines = Resources.asByteSource(manifestURL).asCharSource(Charsets.UTF_8).readLines();
		Pattern pattern = Pattern.compile("([^=]+)(?:=(true|false))?");
		Matcher m = pattern.matcher("");
		for (String line : lines) {
			m.reset(line);
			if (m.matches()) {
				IPath path = baseResourcePath.append(m.group(1));

				boolean load = true;
				if (m.group(2) != null) {
					load = Boolean.valueOf(m.group(2));
				}

				result.put(path, load);
			}
		}

		return result;
	}

	private Resource doInitModelResource(ResourceSet resourceSet, String targetPath, ResourceKind resourceKind, IPath resourceIPath) {
		IPath targetIPath = new Path(targetPath);
		if (!resourceIPath.getFileExtension().equals(targetIPath.getFileExtension())) {
			targetIPath = targetIPath.addFileExtension(resourceIPath.getFileExtension());
		}

		// If the file name is different from the core model name, then use it as is. It's an extra resource for some purpose
		// (perhaps such as a library model)
		if (!targetIPath.lastSegment().equals(resourceIPath.lastSegment())) {
			targetIPath = targetIPath.removeLastSegments(1).append(resourceIPath.lastSegment());
		}

		final URI modelURI = project.getURI(targetIPath);
		Resource result = resourceSet.getResource(modelURI, false);

		if (result == null) {
			String extension = modelURI.fileExtension();
			if (this.managedFileExtension.contains(extension)) {
				// Default load behaviour
				result = resourceSet.createResource(modelURI);
			} else {
				// Assume it's a fragment of UML content (such as a profile-application resource)
				result = resourceSet.createResource(modelURI, UMLPackage.eCONTENT_TYPE);
			}
		}

		if (!result.isLoaded()) {
			if (resourceSet instanceof ModelSet) {
				ModelSet modelSet = (ModelSet) resourceSet;
				if (modelSet.getURIWithoutExtension() == null) {
					modelSet.getInternal().setPrimaryModelResourceURI(modelURI);
				}
			}

			try {
				InputStream input = resourceKind.getResourceURL(testClass, resourceIPath).openStream();
				OutputStream output = resourceSet.getURIConverter().createOutputStream(result.getURI());

				try {
					ByteStreams.copy(input, output);
				} finally {
					input.close();
					output.close();
				}

				result.load(null);
			} catch (Exception e) {
				e.printStackTrace();
				fail("Failed to load test resource: " + resourceIPath.toString());
			}
		}

		return result;
	}

	@Override
	protected void finished(Description description) {
		final ResourceSet resourceSet = getResourceSet();

		initialResourceURIs = null;
		model = null;
		root = null;

		if (domain instanceof TransactionalEditingDomain) {
			((TransactionalEditingDomain) domain).dispose();
		}
		domain = null;

		if (resourceSet != null) {
			EMFHelper.unload(resourceSet);
		}
	}

	private Annotation getResourceAnnotation(Description description) {
		Annotation result = JUnitUtils.getAnyAnnotation(description, JavaResource.class, PluginResource.class);

		assertThat("No JavaResource or PluginResource annotation found on test.", result, notNullValue());

		return result;
	}

	public static enum ResourceKind {
		JAVA, BUNDLE;

		static ResourceKind getResourceKind(Annotation resourceAnnotation) {
			return (resourceAnnotation instanceof JavaResource) ? JAVA : (resourceAnnotation instanceof PluginResource) ? BUNDLE : null;
		}

		String[] getResourcePaths(Annotation resourceAnnotation) {
			switch (this) {
			case JAVA:
				return ((JavaResource) resourceAnnotation).value();
			case BUNDLE:
				PluginResource plugin = (PluginResource) resourceAnnotation;
				return plugin.bundle().isEmpty()
						? plugin.value()
						: Stream.of(plugin.value()).map(path -> String.format("%s:%s", plugin.bundle(), path)).toArray(String[]::new);
			}

			fail("Not a resource annotation: " + resourceAnnotation);
			return null; // Not reachable
		}

		boolean exists(Class<?> context, IPath path) {
			return getResourceURL(context, path) != null;
		}

		URL getResourceURL(Class<?> context, IPath path) {
			URL result = null;

			switch (this) {
			case JAVA:
				result = context.getResource(path.toString());
				break;
			case BUNDLE:
				result = getBundleURL(context, path);
				break;
			}

			return result;
		}

		private URL getBundleURL(Class<?> testClass, IPath resourcePath) {
			URL result = null;

			String bundleID = resourcePath.getDevice();
			if (bundleID != null) {
				resourcePath = resourcePath.setDevice(null);

				int colon = bundleID.lastIndexOf(':');
				if (colon >= 0) {
					bundleID = bundleID.substring(0, colon);
				}
			}

			String pattern = resourcePath.lastSegment();
			IPath search;
			if (resourcePath.segmentCount() > 1) {
				search = resourcePath.removeLastSegments(1);
			} else {
				search = Path.ROOT;
			}

			Bundle testBundle = (bundleID != null)
					? Platform.getBundle(bundleID)
					: FrameworkUtil.getBundle(testClass);
			Enumeration<URL> urls = testBundle.findEntries(search.toPortableString(), pattern, false);
			if ((urls != null) && urls.hasMoreElements()) {
				result = urls.nextElement();
			}

			if (result == null) {
				// A test case can override a resource in a base test bundle with a corresponding resource of its
				// own. But, it may also just use the resource provided by the base test bundle, so look for it
				Bundle lastBundle = testBundle;
				for (Class<?> baseClass = testClass.getSuperclass(); (baseClass != null); baseClass = baseClass.getSuperclass()) {
					testBundle = FrameworkUtil.getBundle(baseClass);
					if (testBundle == null) {
						break;
					} else if (testBundle != lastBundle) {
						lastBundle = testBundle;
						urls = testBundle.findEntries(search.toPortableString(), pattern, false);
						if ((urls != null) && urls.hasMoreElements()) {
							result = urls.nextElement();
						}
					}
				}
			}

			return result;
		}
	}
}
