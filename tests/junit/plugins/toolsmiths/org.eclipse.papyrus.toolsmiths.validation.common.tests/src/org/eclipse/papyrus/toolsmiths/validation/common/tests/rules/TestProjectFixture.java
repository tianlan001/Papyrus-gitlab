/*****************************************************************************
 * Copyright (c) 2020, 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common.tests.rules;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.greaterThanOrEqual;
import static org.eclipse.papyrus.junit.matchers.MoreMatchers.isEmpty;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.architecture.ArchitectureDomainManager;
import org.eclipse.papyrus.infra.architecture.ArchitectureDomainPreferences;
import org.eclipse.papyrus.junit.utils.JUnitUtils;
import org.eclipse.papyrus.junit.utils.rules.ProjectFixture;
import org.eclipse.papyrus.toolsmiths.plugin.builder.preferences.PluginBuilderPreferencesConstants;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.IPluginChecker2;
import org.eclipse.papyrus.toolsmiths.validation.common.utils.ModelResourceMapper;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator2;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import junit.framework.AssertionFailedError;

/**
 * <p>
 * A project fixture that copies initial content into the project and ensures that the project name
 * matches the bundle symbolic name if the project is a bundle project. The base contents of the
 * test project are specified by the {@link TestProject @TestProject} annotation. Additional contents
 * may overlaid* to replace or add files via one or more {@link OverlayFile @OverlayFile} annotations.
 * Additional projects may be created for the duration of the test via {@link AuxProject @AuxProject}
 * annotations.
 * </p>
 * <p>
 * The fixture may be configured to build the project before running the test, to prepare problem
 * markers for verification. Use the {@link Build @Build} annotation for this.
 * </p>
 *
 * @see TestProject
 * @see OverlayFile
 * @see AuxProject
 * @see Build
 */
public class TestProjectFixture extends ProjectFixture {

	static final String JAVA_PROBLEM = "org.eclipse.jdt.core.problem"; //$NON-NLS-1$
	static final Pattern BUNDLE_SYMBOLIC_NAME = Pattern.compile("Bundle-SymbolicName:\\s*([^;]+)"); //$NON-NLS-1$

	private String markerType = JAVA_PROBLEM;
	private String projectNameOverride;
	private final Set<String> filteredDiagnosticSources = new HashSet<>();

	// Quick-fix testing
	private List<IMarker> modelMarkers;
	private List<Map<String, Object>> fixedMarkers = new ArrayList<>();

	/**
	 * Initializes me.
	 */
	public TestProjectFixture() {
		super();
	}

	/**
	 * Configure the fixture with a {@link Diagnostic} source to ignore. This allows, for example,
	 * model validation tests to ignore problems reported by validators of other languages, such as
	 * on UML concepts that are inappropriately reused in the models being validated.
	 *
	 * @param source
	 *            a diagnostic source to filter out
	 * @return myself, for convenience of call chaining
	 */
	public TestProjectFixture filterDiagnosticSource(String source) {
		filteredDiagnosticSources.add(source);
		return this;
	}

	@Override
	public Statement apply(Statement base, Description description) {
		QuickFixWith fixWith = JUnitUtils.getAnnotation(description, QuickFixWith.class);
		QuickFix fix = JUnitUtils.getAnnotation(description, QuickFix.class);
		if (fixWith != null && fix != null && fixWith.value() != null) {
			base = new QuickFixes(fixWith, fix, base);
		}

		Build build = JUnitUtils.getAnnotation(description, Build.class);
		if (build != null && build.value()) {
			base = new BuildProject(base);
		}

		List<AuxProject> auxiliaryProjects = JUnitUtils.getAnnotationsByType(description, AuxProject.class);
		if (!auxiliaryProjects.isEmpty()) {
			base = new CreateAuxiliaryProjects(auxiliaryProjects, base, description);
		}

		List<OverlayFile> overlayFiles = JUnitUtils.getAnnotationsByType(description, OverlayFile.class);
		if (!overlayFiles.isEmpty()) {
			base = new OverlayFilesInProject(overlayFiles, base, description);
		}

		TestProject testProject = JUnitUtils.getAnnotation(description, TestProject.class);
		Statement initProject = new Preferences(new InitializeProject(testProject, base, description));

		Statement createProject = super.apply(initProject, description);

		Statement projectName = new InitProjectName(testProject, createProject, description);

		MarkerType markerTypeAnnotation = JUnitUtils.getAnnotation(description, MarkerType.class);
		Statement markerType = new SetMarkerType(markerTypeAnnotation, projectName, description);

		return markerType;
	}

	/**
	 * Build the test project.
	 */
	public void build() {
		try {
			getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());
			getProject().build(IncrementalProjectBuilder.FULL_BUILD, new NullProgressMonitor());
		} catch (CoreException e) {
			throw new AssertionError("Failed to build project.", e);
		}
	}

	/**
	 * Create a new project.
	 *
	 * @param name
	 *            the project name to create
	 * @param testClass
	 *            the test class in which context to look for bundle resources to fill the project, or {@code null} if none required
	 * @param contentPath
	 *            path to a folder in the bundle resources that contains content to fill the project, or {@code null} if none required
	 * @return the new project
	 */
	public IProject createProject(String name, Class<?> testClass, String contentPath) {
		IProject result = getProject().getWorkspace().getRoot().getProject(name);

		try {
			result.create(null);
			result.open(null);

			if (contentPath != null) {
				copyFolder(testClass, contentPath, result);
			}
		} catch (CoreException | IOException e) {
			throw new AssertionError("Failed to create project.", e);
		}

		return result;
	}

	/**
	 * Get all of the markers in the project.
	 *
	 * @return the markers
	 * @see #getMarkers(IResource)
	 */
	public List<IMarker> getMarkers() {
		return getMarkers(getProject());
	}

	/**
	 * Get the markers on the given {@code resource}. In the case that the resource is a container,
	 * the markers of all contained resources are aggregated within it.
	 *
	 * @param resource
	 *            the resource for which to get markers
	 * @return the markers
	 */
	public List<IMarker> getMarkers(IResource resource) {
		List<IMarker> result = null;

		try {
			Predicate<IMarker> markerPredicate = Predicate.not(marker -> filteredDiagnosticSources.contains(marker.getAttribute(IPluginChecker2.MARKER_ATTRIBUTE_DIAGNOSTIC_SOURCE, (String) null)));
			result = Stream.of(resource.findMarkers(markerType, true, IResource.DEPTH_INFINITE))
					.filter(markerPredicate)
					.collect(Collectors.toList());
		} catch (CoreException e) {
			throw new AssertionError("Failed to get project markers.", e); //$NON-NLS-1$
		}

		return result;
	}

	/**
	 * Get the markers on the named resource. In the case that the resource is a container,
	 * the markers of all contained resources are aggregated within it.
	 *
	 * @param path
	 *            the resource path for which to get markers
	 * @return the markers
	 * @see #getMarkers(IResource)
	 */
	public List<IMarker> getMarkers(String path) {
		IResource resource = getProject().findMember(path);
		assertThat("No such resource in the project: " + path, resource, notNullValue()); //$NON-NLS-1$

		return getMarkers(resource);
	}

	@Override
	protected void createProject(String name) throws CoreException {
		if (projectNameOverride != null) {
			super.createProject(projectNameOverride);
		} else {
			super.createProject(name);
		}
	}

	/**
	 * Load a model resource in a suitable resource set.
	 *
	 * @param modelFile
	 *            the model file to load
	 * @return the resource, loaded in a resource set
	 */
	public Resource loadModelResource(IFile modelFile) {
		Resource result;

		URI uri = URI.createPlatformResourceURI(modelFile.getFullPath().toString(), true);
		URI rsetURI = uri;

		if ("uml".equals(uri.fileExtension()) || "notation".equals(uri.fileExtension())) { //$NON-NLS-1$//$NON-NLS-2$
			// Look for a DI file to initialize the resource set, instead
			URI di = uri.trimFileExtension().appendFileExtension("di"); //$NON-NLS-1$
			if (URIConverter.INSTANCE.exists(di, null)) {
				rsetURI = di;
			}
		}

		ResourceSet rset = ("di".equals(rsetURI.fileExtension())) //$NON-NLS-1$
				? ModelResourceMapper.modelSets().apply(rsetURI)
				: ModelResourceMapper.resourceSets().apply(rsetURI);

		try {
			result = rset.getResource(uri, true);
		} catch (Exception e) {
			throw new AssertionError("Failed to load test model resource: " + modelFile.getFullPath(), e); //$NON-NLS-1$
		}

		return result;
	}

	/**
	 * Load a model resource in a suitable resource set.
	 *
	 * @param modelPath
	 *            the project-relative path of the model file to load
	 * @return the resource, loaded in a resource set
	 */
	public Resource loadModelResource(String modelPath) {
		return loadModelResource(getFile(modelPath));
	}

	/**
	 * Scan a project, looking for architecture models to register.
	 *
	 * @param project
	 *            a project to scan
	 * @return the URIs of architecture models int it that were registered
	 *
	 * @see #unregisterArchitectureModels(Collection)
	 */
	private Set<URI> registerArchitectureModels(IProject project) {
		Set<URI> scanned = new HashSet<>();

		try {
			project.accept(new IResourceProxyVisitor() {

				@Override
				public boolean visit(IResourceProxy proxy) throws CoreException {
					switch (proxy.getType()) {
					case IResource.FILE:
						if (proxy.getName().endsWith(".architecture")) { //$NON-NLS-1$
							scanned.add(URI.createPlatformResourceURI(proxy.requestFullPath().toString(), true));
						}
						break;
					default:
						// Pass
						break;
					}

					return true;
				}
			}, IResource.DEPTH_INFINITE, 0);
		} catch (CoreException e) {
			throw new AssertionError("Failed to scan test project for architecture models.", e);
		}

		@SuppressWarnings("deprecation")
		ArchitectureDomainPreferences prefs = ArchitectureDomainManager.getInstance().getPreferences();

		Set<String> result = scanned.stream().map(URI::toString).collect(Collectors.toSet());
		result.removeIf(prefs.getAddedModelURIs()::contains); // Don't re-register existing models

		if (!result.isEmpty()) {
			prefs.getAddedModelURIs().addAll(result);
			prefs.write(); // Trigger update in the manager
		}

		return result.stream().map(URI::createURI).collect(Collectors.toSet());
	}

	/**
	 * Unregister a set of architecture models previously registered.
	 *
	 * @param architectureModels
	 *            model URIs to unregister
	 *
	 * @see #registerArchitectureModels(IProject)
	 */
	private void unregisterArchitectureModels(Collection<URI> architectureModels) {
		@SuppressWarnings("deprecation")
		ArchitectureDomainPreferences prefs = ArchitectureDomainManager.getInstance().getPreferences();

		Set<String> uriStrings = architectureModels.stream().map(URI::toString).collect(Collectors.toSet());
		if (prefs.getAddedModelURIs().removeAll(uriStrings)) {
			// Trigger update in the manager. Note that "added URIs" are not models that were added but
			// models that are added to the manager by the preferences
			prefs.write();
		}
	}

	/**
	 * Get the contents of a {@code file} as a string.
	 *
	 * @param file
	 *            a file
	 * @return its contents
	 */
	public final String getContent(IFile file) {
		try {
			return Files.readString(Paths.get(file.getLocationURI()), Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new AssertionFailedError("Failed to read contents of test file: " + e.getMessage());
		}
	}

	/**
	 * Create a mock marker with string-valued {@code attributes}.
	 *
	 * @param resource
	 *            the marker owner
	 * @param attributes
	 *            the marker attributes
	 * @return a mock marker that knows how to answer {@link IMarker#getResource()},
	 *         {@link IMarker#getAttribute(String)}, and {@link IMarker#getAttribute(String, String)}.
	 */
	public IMarker mockMarker(IResource resource, Map<String, String> attributes) {
		return (IMarker) Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[] { IMarker.class },
				(proxy, method, args) -> {
					switch (method.getName()) {
					case "getResource": //$NON-NLS-1$
						return resource;
					case "getAttribute": //$NON-NLS-1$
						if (method.getReturnType() == String.class && args.length == 2) {
							String result = attributes.get(args[0]);
							if (result == null) {
								result = (String) args[1];
							}
							return result;
						}
						if (args.length == 1) {
							return attributes.get(args[0]);
						}
						break;
					}
					return null;
				});
	}

	public Map<String, Object> getAttributes(IMarker marker) {
		try {
			return marker.getAttributes();
		} catch (CoreException e) {
			throw new AssertionError("Failed to extract marker attributes.", e);
		}
	}

	public Set<Map<String, Object>> getMarkerAttributes(Collection<? extends IMarker> markers) {
		return markers.stream()
				.map(this::getAttributes)
				.collect(Collectors.toSet());
	}

	public void fix(int problemID, IMarkerResolutionGenerator2 resolutionGenerator) {
		IMarker marker = getFixableMarker(problemID);
		Map<String, Object> attributes = getAttributes(marker);

		assertThat("No quick fix available for problem " + problemID, resolutionGenerator.hasResolutions(marker), is(true));
		IMarkerResolution[] fixes = resolutionGenerator.getResolutions(marker);
		assertThat("No quick fix provided by generator", fixes.length, greaterThanOrEqual(1));
		fixes[0].run(marker);

		fixedMarkers.add(attributes);
	}

	public IMarker getFixableMarker(int problemID) {
		IPluginChecker2.MarkerAttribute attr = IPluginChecker2.problem(problemID);
		IMarker result = modelMarkers.stream().filter(m -> m.getAttribute(attr.getName(), -1) == problemID)
				.findAny().orElse(null);
		assertThat("Fixable problem marker not found: " + problemID, result, notNullValue());
		return result;
	}

	//
	// Nested types
	//

	/**
	 * A statement that peeks into the contents of the project before the project is created,
	 * to override the name of the project that will be created with the bundle symbolic name
	 * in the case that the project's content will be a bundle project.
	 */
	private final class InitProjectName extends Statement {
		private final TestProject testProject;
		private final Statement base;
		private final Description description;

		InitProjectName(TestProject testProject, Statement base, Description description) {
			super();

			this.testProject = testProject;
			this.base = base;
			this.description = description;
		}

		@Override
		public void evaluate() throws Throwable {
			initializeProjectNameOverride();

			base.evaluate();
		}

		private void initializeProjectNameOverride() throws IOException {
			String path = testProject.value();
			while (path.endsWith("/")) {
				path = path.substring(0, path.length() - 1);
			}

			// If it's a bundle project, it needs to have the same name as the bundle
			String resourcePath = "resources/" + path + "/META-INF/MANIFEST.MF";
			URL resource = JUnitUtils.getTestClass(description).getClassLoader().getResource(resourcePath);
			if (resource != null) {
				Matcher m = null;
				try (InputStream input = resource.openStream()) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
					for (String line = reader.readLine(); line != null; line = reader.readLine()) {
						if (m == null) {
							m = BUNDLE_SYMBOLIC_NAME.matcher(line);
						} else {
							m.reset(line);
						}
						if (m.find()) {
							projectNameOverride = m.group(1);
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * A statement that initializes the contents of the project before the test.
	 */
	private final class InitializeProject extends Statement {
		private final TestProject testProject;
		private final Statement base;
		private final Description description;

		InitializeProject(TestProject testProject, Statement base, Description description) {
			super();

			this.testProject = testProject;
			this.base = base;
			this.description = description;
		}

		@Override
		public void evaluate() throws Throwable {
			if (testProject == null) {
				throw new IllegalStateException("No @TestProject annotation found."); //$NON-NLS-1$
			}

			// Turn off autobuild so that we do not trigger builds while setting up resources
			// in the test project
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IWorkspaceDescription wsDesc = null;
			if (workspace.isAutoBuilding()) {
				wsDesc = workspace.getDescription();
				wsDesc.setAutoBuilding(false);
				workspace.setDescription(wsDesc);
			}

			Set<URI> architectureModels = null;

			try {
				copyFolder(JUnitUtils.getTestClass(description), "resources/" + testProject.value()); //$NON-NLS-1$
				architectureModels = registerArchitectureModels(getProject());
			} catch (IOException e) {
				throw new IOException("Failed to initialize project contents.", e); //$NON-NLS-1$
			}

			try {
				base.evaluate();
			} finally {
				if (architectureModels != null) {
					unregisterArchitectureModels(architectureModels);
				}
				if (wsDesc != null) {
					wsDesc.setAutoBuilding(true);
					workspace.setDescription(wsDesc);
				}
			}
		}
	}

	/**
	 * A statement that overlays additional files onto the initialized project.
	 *
	 * @see InitializeProject
	 */
	private final class OverlayFilesInProject extends Statement {
		private final List<OverlayFile> overlayFiles;
		private final Statement base;
		private final Description description;

		OverlayFilesInProject(List<OverlayFile> overlayFiles, Statement base, Description description) {
			super();

			this.overlayFiles = List.copyOf(overlayFiles);
			this.base = base;
			this.description = description;
		}

		@Override
		public void evaluate() throws Throwable {
			Class<?> testClass = JUnitUtils.getTestClass(description);

			// Only process the first overlay of a file, which lets a test method annotation
			// override an annotation on the class for the same destination path
			Set<IPath> overlaid = new HashSet<>();
			for (OverlayFile overlay : overlayFiles) {
				IPath targetPath = getTargetPath(overlay);
				if (overlaid.add(targetPath)) {
					URL resource = testClass.getClassLoader().getResource("resources/" + overlay.value()); //$NON-NLS-1$
					try (InputStream input = resource.openStream()) {
						IFile file = getProject().getFile(targetPath);
						if (file.exists()) {
							file.setContents(input, true, false, null);
						} else {
							file.create(input, true, null);
						}
					}
				}
			}

			Set<URI> architectureModels = registerArchitectureModels(getProject());

			try {
				base.evaluate();
			} finally {
				unregisterArchitectureModels(architectureModels);
			}
		}

		private IPath getTargetPath(OverlayFile overlay) {
			IPath result;

			if (overlay.path().isBlank()) {
				result = new Path(overlay.value());
				if (result.segmentCount() > 1) {
					result = result.removeFirstSegments(1);
				}
			} else {
				result = new Path(overlay.path());
			}

			return result;
		}

	}

	/**
	 * A statement that builds the project before the test.
	 */
	private final class BuildProject extends Statement {
		private final Statement base;

		BuildProject(Statement base) {
			super();

			this.base = base;
		}

		@Override
		public void evaluate() throws Throwable {
			build();
			base.evaluate();
		}

	}

	/**
	 * A statement that ensures that Papyrus builders are enabled in the preferences
	 * and restores the preferences to the previous values when complete.
	 */
	private final class Preferences extends Statement {
		private final Set<String> prefNames = Set.of(
				PluginBuilderPreferencesConstants.ACTIVATE_MAIN_PAPYRUS_BUILDER,
				PluginBuilderPreferencesConstants.ACTIVATE_PAPYRUS_MODEL_BUILDER,
				PluginBuilderPreferencesConstants.PAPYRUS_MODEL_BUILDER_CHECK_MODEL_DEPENDENCIES,
				PluginBuilderPreferencesConstants.PAPYRUS_MODEL_BUILDER_VALIDATE_MODEL,
				PluginBuilderPreferencesConstants.ACTIVATE_PAPYRUS_MANIFEST_BUILDER,
				PluginBuilderPreferencesConstants.PAPYRUS_MANIFEST_BUILDER_CHECK_DEPENDENCY_RANGE,
				PluginBuilderPreferencesConstants.PAPYRUS_MANIFEST_BUILDER_CHECK_NO_REEXPORT);
		private final IPreferenceStore prefs = org.eclipse.papyrus.toolsmiths.plugin.builder.Activator.getDefault().getPreferenceStore();
		private final Statement base;

		private final Map<String, Boolean> restorePrefs = new HashMap<>();

		Preferences(Statement base) {
			super();

			this.base = base;
		}

		@Override
		public void evaluate() throws Throwable {
			prefNames.forEach(pref -> restorePrefs.put(pref, prefs.getBoolean(pref)));
			prefNames.forEach(pref -> prefs.setValue(pref, true));

			try {
				base.evaluate();
			} finally {
				restorePrefs.forEach((pref, restore) -> prefs.setValue(pref, restore));
			}
		}

	}

	/**
	 * A statement that sets the marker type to extract from resources for the duration of a test.
	 */
	private final class SetMarkerType extends Statement {
		private final String testMarkerType;
		private final Statement base;
		private String restoreMarkerType;

		SetMarkerType(MarkerType annotation, Statement base, Description description) {
			super();

			this.testMarkerType = Optional.ofNullable(annotation).map(MarkerType::value).orElse(JAVA_PROBLEM);
			this.base = base;
		}

		@Override
		public void evaluate() throws Throwable {
			restoreMarkerType = TestProjectFixture.this.markerType;

			try {
				TestProjectFixture.this.markerType = testMarkerType;
				base.evaluate();
			} finally {
				TestProjectFixture.this.markerType = restoreMarkerType;
			}
		}

	}

	/**
	 * A statement that creates auxiliary projects before the test.
	 */
	private final class CreateAuxiliaryProjects extends Statement {
		private final Collection<? extends AuxProject> auxProjects;
		private final Statement base;
		private final Description description;

		CreateAuxiliaryProjects(Collection<? extends AuxProject> auxProjects, Statement base, Description description) {
			super();

			this.auxProjects = auxProjects;
			this.base = base;
			this.description = description;
		}

		@Override
		public void evaluate() throws Throwable {
			Set<URI> architectureModels = new HashSet<>();

			final Set<IProject> toDelete = auxProjects.stream().map(this::createAuxProject)
					.peek(project -> architectureModels.addAll(registerArchitectureModels(project)))
					.collect(Collectors.toSet());

			try {
				base.evaluate();
			} finally {
				unregisterArchitectureModels(architectureModels);

				AssertionError failure = null;

				for (IProject next : toDelete) {
					try {
						next.delete(true, true, null);
					} catch (CoreException e) {
						if (failure == null) {
							failure = new AssertionError("Failed to clean up additional workspace resources", e);
						} else {
							failure.addSuppressed(e);
						}
					}
				}

				if (failure != null) {
					throw failure;
				}
			}
		}

		private IProject createAuxProject(AuxProject auxProject) {
			String resourcePath = "resources/" + auxProject.value();
			String projectName = auxProject.as().isBlank() ? new Path(resourcePath).lastSegment() : auxProject.as();
			return createProject(projectName, JUnitUtils.getTestClass(description), resourcePath.toString());
		}

	}

	private final class QuickFixes extends Statement {

		private final Class<? extends IMarkerResolutionGenerator2> quickFixer;
		private final int problemID;
		private final Optional<String> path;

		private final Statement base;

		QuickFixes(QuickFixWith fixWith, QuickFix fix, Statement base) {
			super();

			this.quickFixer = fixWith.value();
			this.problemID = fix.value();
			this.path = Optional.ofNullable(fix.path()).filter(Predicate.not(String::isBlank));

			this.base = base;
		}

		@Override
		public void evaluate() throws Throwable {
			String path = resourceToFix();
			modelMarkers = getMarkers(path);

			try {
				base.evaluate();

				IMarkerResolutionGenerator2 generator = quickFixer.getConstructor().newInstance();
				fix(problemID, generator);

				assertThat("No problem markers processed by quick fix", fixedMarkers, not(isEmpty()));

				build();

				Set<Map<String, Object>> newMarkerAttributes = getMarkerAttributes(getMarkers(path));
				Set<Map<String, Object>> unfixed = new HashSet<>(fixedMarkers);
				unfixed.retainAll(newMarkerAttributes);

				assertThat("Some problem(s) not fixed", unfixed, isEmpty());
			} finally {
				modelMarkers = null;
			}
		}

		private String resourceToFix() {
			return path.or(this::findResourceToFix)
					.orElseThrow(() -> new AssertionError(String.format("Could not find resource to fix problem ID 0x%x.", problemID)));
		}

		private Optional<String> findResourceToFix() {
			Optional<IResource> result;

			try {
				result = Stream.of(getProject().findMarkers(markerType, false, IResource.DEPTH_INFINITE))
						.filter(marker -> marker.getAttribute("problemId", -1) == problemID)
						.map(IMarker::getResource)
						.findAny();
			} catch (CoreException e) {
				throw new AssertionError("Failed to search markers in project.", e);
			}

			return result.map(IResource::getProjectRelativePath).map(Object::toString);
		}
	}

}
