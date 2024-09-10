/*****************************************************************************
 * Copyright (c) 2019, 2021 CEA LIST, EclipseSource, Christian W. Damus, and others.
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
 *   Remi Schnekenburger (EclipseSource) - Bug 568495
 *   Christian W. Damus - bugs 569357, 570097, 571125, 573986
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common.checkers;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.filebuffers.ITextFileBuffer;
import org.eclipse.core.filebuffers.ITextFileBufferManager;
import org.eclipse.core.filebuffers.LocationKind;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.toolsmiths.validation.common.Activator;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.OpaqueResourceProvider.ClassifiedURI;
import org.eclipse.papyrus.toolsmiths.validation.common.internal.messages.Messages;
import org.eclipse.papyrus.toolsmiths.validation.common.utils.ProjectManagementService;
import org.eclipse.pde.core.build.IBuild;
import org.eclipse.pde.core.build.IBuildEntry;
import org.eclipse.pde.core.build.IBuildModel;
import org.eclipse.pde.internal.core.text.build.BuildEntry;
import org.eclipse.pde.internal.core.text.build.BuildModel;

/**
 * Common checker framework for <tt>build.properties</tt> validation.
 */
@SuppressWarnings("restriction")
public class BuildPropertiesChecker extends AbstractPluginChecker {

	private static final String GENMODEL_EXTENSION = "genmodel"; //$NON-NLS-1$
	private static final String ECORE_EXTENSION = "ecore"; //$NON-NLS-1$

	private final Resource modelResource;

	/**
	 * Computation of the immediate dependencies of a model file. This is invoked recursively
	 * to trace the complete dependency graph.
	 */
	private Function<? super IFile, ? extends Collection<? extends IResource>> dependenciesFunction;

	private OpaqueResourceProvider.EMF modelOpaqueResourceProvider;
	private OpaqueResourceProvider.XML pluginXMLOpaqueResourceProvider;

	/**
	 * Constructor.
	 *
	 * @param project
	 *            The current project resource.
	 * @param modelFile
	 *            The model file.
	 */
	public BuildPropertiesChecker(final IProject project, final IFile modelFile) {
		this(project, modelFile, (Resource) null);
	}

	/**
	 * Constructor.
	 *
	 * @param project
	 *            The current project resource.
	 * @param modelFile
	 *            The model file.
	 * @param markerType
	 *            The marker type.
	 */
	public BuildPropertiesChecker(final IProject project, final IFile modelFile, final String markerType) {
		this(project, modelFile, null, markerType);
	}

	/**
	 * Constructor.
	 *
	 * @param project
	 *            The current project resource.
	 * @param modelFile
	 *            The model file.
	 * @param modelResource
	 *            the loaded EMF model resource to scan for cross-referenced model resources, or {@code null}
	 *            if such scan is not needed
	 */
	public BuildPropertiesChecker(final IProject project, final IFile modelFile, Resource modelResource) {
		super(project, modelFile);

		this.modelResource = modelResource;
	}

	/**
	 * Constructor.
	 *
	 * @param project
	 *            The current project resource.
	 * @param modelFile
	 *            The model file.
	 * @param modelResource
	 *            the loaded EMF model resource to scan for cross-referenced model resources, or {@code null}
	 *            if such scan is not needed
	 * @param markerType
	 *            The marker type.
	 */
	public BuildPropertiesChecker(final IProject project, final IFile modelFile, Resource modelResource, final String markerType) {
		super(project, modelFile, markerType);

		this.modelResource = modelResource;
	}

	/**
	 * Configure the <tt>build.properties</tt> checker to looks for EMF code generation model dependencies to include
	 * in the build in addition to the model file.
	 *
	 * @return myself, for convenience of call chaining
	 *
	 * @see #getEMFCodeGenDependencies(IFile)
	 */
	public BuildPropertiesChecker withEMFGeneratorModels() {
		return withDependencies(BuildPropertiesChecker::getEMFCodeGenDependencies);
	}

	/**
	 * Configure the <tt>build.properties</tt> checker to compute the transitive graph of dependencies (within the project)
	 * to include in the build in addition to the model file.
	 *
	 * @param dependenciesFunction
	 *            computation of the immediate dependencies of a model file. This is invoked recursively
	 *            to trace the complete dependency graph. May be {@code null} if it the model cannot possibly have dependencies
	 *
	 * @return myself, for convenience of call chaining
	 */
	public BuildPropertiesChecker withDependencies(final Function<? super IFile, ? extends Collection<? extends IResource>> dependenciesFunction) {
		this.dependenciesFunction = dependenciesFunction;
		return this;
	}

	/**
	 * Add a function that computes additional requirements from the model that are encoded in some other
	 * ways than cross-document references to other EMF resources in other bundles.
	 *
	 * @param opaqueReferenceProvider
	 *            a provider of opaque resource references from the model
	 * @return myself, for convenience of call chaining
	 */
	public BuildPropertiesChecker withReferencedResources(OpaqueResourceProvider.EMF opaqueReferenceProvider) {
		this.modelOpaqueResourceProvider = OpaqueResourceProvider.and(opaqueReferenceProvider, this.modelOpaqueResourceProvider);
		return this;
	}

	/**
	 * Add a function that computes additional requirements from the <tt>plugin.xml</tt>.
	 *
	 * @param opaqueReferenceProvider
	 *            a provider of opaque resource references from the <tt>plugin.xml</tt>
	 * @return myself, for convenience of call chaining
	 */
	public BuildPropertiesChecker withReferencedResources(OpaqueResourceProvider.XML opaqueReferenceProvider) {
		this.pluginXMLOpaqueResourceProvider = OpaqueResourceProvider.and(opaqueReferenceProvider, this.pluginXMLOpaqueResourceProvider);
		return this;
	}

	@Override
	public void check(DiagnosticChain diagnostics, final IProgressMonitor monitor) {
		SubMonitor subMonitor = SubMonitor.convert(monitor, NLS.bind(Messages.BuildPropertiesChecker_2, getModelFile().getName()), 1);
		if (subMonitor.isCanceled()) {
			return;
		}

		// Get the build.properties entries from the project
		final IBuildModel buildModel = ProjectManagementService.getPluginBuild(getProject());
		if (null != buildModel) {
			// Calculate the closure of resources to get
			Set<IResource> requiredResources = computeRequiredResources(getModelFile());
			requiredResources.addAll(getCrossReferencedResources());
			requiredResources.addAll(getReferencedOpaqueResources(diagnostics));

			// And don't worry about resources in other projects, because those projects will package them
			requiredResources.removeIf(Predicate.not(getProject()::contains));

			final IBuild build = buildModel.getBuild();
			final IBuildEntry buildEntry = build.getEntry(IBuildEntry.BIN_INCLUDES);

			// Iterate on existing tokens
			final String[] tokens = buildEntry.getTokens();
			for (int i = 0; i < tokens.length && !requiredResources.isEmpty(); i++) {
				String token = tokens[i];
				for (Iterator<IResource> iter = requiredResources.iterator(); iter.hasNext();) {
					IResource next = iter.next();
					String path = next.getProjectRelativePath().toString();
					if (path.equals(token)) {
						// Exact match. Good. And it won't match any other resource, so break
						iter.remove();
						break;
					}

					// Only accept folders as prefixes
					if (path.startsWith(token)) {
						if (token.endsWith("/")) { //$NON-NLS-1$
							// It's a folder explicitly. Other required resources may also match it
							iter.remove();
						} else {
							int prefixLength = token.length();
							if (prefixLength < path.length() && path.charAt(prefixLength) == '/') {
								// It's a folder match. Other required resources may also match it
								iter.remove();
							}
						}
					}
				}
			}

			if (!requiredResources.isEmpty()) {
				// Create marker for every required resource that wasn't matched
				List<BuildError> errors = new ArrayList<>(requiredResources.size());
				for (IResource next : requiredResources) {
					errors.add(new BuildError(getMarkerType(), NLS.bind(Messages.BuildPropertiesChecker_3, next.getProjectRelativePath()), Diagnostic.ERROR, IBuildEntry.BIN_INCLUDES, next.getProjectRelativePath().toString()));
				}
				reportErrors(diagnostics, errors);
			}
		}

		subMonitor.worked(1);
		SubMonitor.done(monitor);
	}

	/**
	 * For the common case of EMF Generator Model imported from the source {@code modelFile}, compute that
	 * GenModel and the Ecore model generated from it.
	 *
	 * @param modelFile
	 *            a model file
	 * @return its EMF code generation models, or empty if none
	 */
	public static Collection<IResource> getEMFCodeGenDependencies(IFile modelFile) {
		if (GENMODEL_EXTENSION.equals(modelFile.getFileExtension())) {
			// This implementation only looks for genmodels and their Ecores
			return Collections.emptyList();
		}

		IProject project = modelFile.getProject();

		String genModelPath = modelFile.getProjectRelativePath().removeFileExtension().addFileExtension(GENMODEL_EXTENSION).toString();
		Optional<GenModel> maybeGenModel = loadGenModel(project, genModelPath);
		if (maybeGenModel.isEmpty()) {
			return Collections.emptyList();
		}

		GenModel genModel = maybeGenModel.get();
		if (!genModel.getForeignModel().contains(modelFile.getName())) {
			// This genmodel wasn't loaded from our model, so it's not needed
			return Collections.emptyList();
		}

		if (ECORE_EXTENSION.equals(modelFile.getFileExtension())) {
			// The genmodel was imported from the Ecore. Don't need to look for it again
			return List.of(project.getFile(genModelPath));
		}

		String ecorePath = ecoreFilePath(genModel);

		return List.of(project.getFile(genModelPath), project.getFile(ecorePath));
	}

	private static String ecoreFilePath(GenModel genModel) {
		GenPackage genPackage = genModel.getGenPackages().get(0);
		EPackageImpl ecoreImpl = ((EPackageImpl) genPackage.getEcorePackage());
		URI ecoreURI = ecoreImpl.eResource().getURI();
		List<String> segments = new ArrayList<>(ecoreURI.segmentsList());
		// return only relative path from plugin root (platform:/plugin/<plugin-id>/ is removed)
		return String.join("/", segments.subList(2, segments.size())); //$NON-NLS-1$
	}

	/**
	 * Load the genmodel located at specified URI and return the root element or <code>null</code> if none could be found.
	 *
	 * @param profileGenModelPath
	 *            project relative path to the gen model file to load
	 * @return the GenModel root element or empty optional in case of loading issues
	 */
	private static Optional<GenModel> loadGenModel(IProject project, String genModelPath) {
		try {
			URI uri = URI.createPlatformPluginURI(project.getName() + "/" + genModelPath, true); //$NON-NLS-1$
			ResourceSet set = new ResourceSetImpl();
			set.getURIConverter().getURIMap().putAll(EcorePlugin.computePlatformURIMap(true));
			if (set.getURIConverter().exists(uri, null)) {
				Resource genModelResource = set.getResource(uri, true);
				if (genModelResource != null && genModelResource.getContents().size() > 0) {
					EObject root = genModelResource.getContents().get(0);
					if (root instanceof GenModel) {
						return Optional.of((GenModel) root);
					}
				}
			}
		} catch (Exception e) {
			Activator.log.error("Failed to load genmodel in Papyrus Build Checker.", e); //$NON-NLS-1$
		}
		return Optional.empty();
	}

	private Set<IResource> computeRequiredResources(IResource root) {
		Set<IResource> result = new LinkedHashSet<>();

		Queue<IResource> queue = new ArrayDeque<>();
		queue.add(root);
		for (IResource next = queue.poll(); next != null; next = queue.poll()) {
			if (!result.add(next)) {
				continue; // Processed this one already
			}

			// Gather dependencies, iteratively
			if (dependenciesFunction != null && next instanceof IFile) {
				queue.addAll(dependenciesFunction.apply((IFile) next));
			}
		}

		return result;
	}

	private Set<IResource> getCrossReferencedResources() {
		Set<IResource> result = Set.of();

		if (modelResource != null) {
			Set<URI> xrefURIs = ModelDependenciesChecker.computeExternalCrossReferences(modelResource);
			result = xrefURIs.stream().map(this::getResource).filter(Objects::nonNull).collect(Collectors.toSet());
		}

		return result;
	}

	private IResource getResource(URI uri) {
		IResource result = null;

		if (modelResource != null) {
			uri = modelResource.getResourceSet().getURIConverter().normalize(uri);
		}

		if (uri.isPlatformResource()) {
			// If it's a platform plugin resource, then it is expected to be deployed independently
			// and so needs not be considered in our build.properties validation
			String uriPlatformString = uri.toPlatformString(true);
			result = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(uriPlatformString));
		}

		return result;
	}

	private Set<IResource> getReferencedOpaqueResources(DiagnosticChain diagnostics) {
		Set<IResource> result = new LinkedHashSet<>();

		Function<URI, IResource> resourceFunction = this::getResource;
		if (modelOpaqueResourceProvider != null && modelResource != null) {
			modelOpaqueResourceProvider.processModel(getProject(), getModelFile(), modelResource, diagnostics,
					resourceFunction.compose(ClassifiedURI::uri), result::add);
			result.remove(null); // Handle non-workspace resources
		}

		if (pluginXMLOpaqueResourceProvider != null) {
			IFile pluginXML = ProjectManagementService.getPluginXMLFile(getProject());
			if (pluginXML != null) {
				pluginXMLOpaqueResourceProvider.processModel(getProject(), pluginXML, diagnostics,
						resourceFunction.compose(ClassifiedURI::uri), result::add);
				result.remove(null); // Handle non-workspace resources
			}
		}

		return result;
	}

	/**
	 * generate markers for the specified list of errors.
	 *
	 * @param errors
	 *            the list of errors for which markers will be created.
	 */
	private void reportErrors(DiagnosticChain diagnostics, List<BuildError> errors) {
		final IFile buildPropertiesFile = ProjectManagementService.getBuildFile(getProject());
		BuildModel textBuildModel = prepareTextBuildModel(buildPropertiesFile);

		errors.stream().forEach(error -> {
			reportBuildError(diagnostics, buildPropertiesFile, textBuildModel, error.type, error.message, error.severity, error.entry, error.missingValue);
		});
	}

	/**
	 * Create a Marker on the specified file, with the given parameters.
	 *
	 * @param buildFile
	 *            the file on which the marker should be created.
	 * @param textBuildModel
	 *            the textual model representation of the build.properties file
	 * @param type
	 *            the type of the marker to create
	 * @param message
	 *            the message of the marker to create
	 * @param severity
	 *            the severity of the marker to create
	 * @param header
	 *            the header entry of the build file on which marker is created.
	 */
	private void reportBuildError(DiagnosticChain diagnostics, IFile buildFile, BuildModel textBuildModel, String type, String message, int severity, String entry, String missingValue) {
		Diagnostic diagnostic;

		if (textBuildModel != null) {
			diagnostic = createDiagnostic(buildFile, severity, 0, message,
					IPluginChecker2.markerType(type),
					IPluginChecker2.lineNumber(getLineNumber(textBuildModel.getBuild().getEntry(entry))),
					IPluginChecker2.problem(CommonProblemConstants.MISSING_FROM_BINARY_BUILD_MARKER_ID),
					IPluginChecker2.missingBinInclude(missingValue));
		} else {
			diagnostic = createDiagnostic(buildFile, severity, 0, message,
					IPluginChecker2.markerType(type));
		}

		diagnostics.add(diagnostic);
	}

	/**
	 * Read and parse the build.properties file to create the abstract representation.
	 *
	 * @param file
	 *            the file to parse
	 * @return the model of the build file.
	 */
	private BuildModel prepareTextBuildModel(IFile file) {
		try {
			IDocument doc = createDocument(file);
			if (doc == null) {
				return null;
			}
			BuildModel bm = new BuildModel(doc, true);
			bm.load();
			if (!bm.isLoaded()) {
				return null;
			}
			return bm;
		} catch (CoreException e) {
			Activator.log.error(e);
			return null;
		}
	}

	/**
	 * Read the manifest file and provide a {@link IDocument}.
	 *
	 * @param file
	 *            the file to parse
	 * @return the document of the textual file.
	 */
	protected IDocument createDocument(IFile file) {
		if (!file.exists()) {
			return null;
		}
		ITextFileBufferManager manager = FileBuffers.getTextFileBufferManager();
		if (manager == null) {
			return null;
		}
		try {
			manager.connect(file.getFullPath(), LocationKind.NORMALIZE, null);
			ITextFileBuffer textBuf = manager.getTextFileBuffer(file.getFullPath(), LocationKind.NORMALIZE);
			IDocument document = textBuf.getDocument();
			manager.disconnect(file.getFullPath(), LocationKind.NORMALIZE, null);
			return document;
		} catch (CoreException e) {
			Activator.log.error(e);
		}
		return null;
	}

	/**
	 * Retrieve the line number for the specified entry in the build.properties file.
	 *
	 * @param imh
	 *            the entry to be retrieved.
	 * @return the line number or <code>0</code> if none was found.
	 */
	private int getLineNumber(IBuildEntry ibe) {
		if (!(ibe instanceof BuildEntry)) {
			return 0;
		}
		BuildEntry be = (BuildEntry) ibe;
		org.eclipse.jface.text.IDocument doc = ((BuildModel) be.getModel()).getDocument();
		try {
			int buildEntryLineNumber = doc.getLineOfOffset(be.getOffset()) + 1;
			// we are interested in the build entry name
			// (getLineOfOffset is 0-indexed, need 1-indexed)
			return buildEntryLineNumber;
		} catch (BadLocationException e) {
			Activator.log.error(e);
		}
		return 0;
	}

	/**
	 * Representation of an error on the build.properties file.
	 */
	private static class BuildError {
		public final String entry;
		public final int severity;
		public final String message;
		public final String type;
		public final String missingValue;

		public BuildError(String type, String message, int severity, String entry, String missingValue) {
			this.type = type;
			this.message = message;
			this.severity = severity;
			this.entry = entry;
			this.missingValue = missingValue;
		}
	}
}
