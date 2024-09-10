/*****************************************************************************
 * Copyright (c) 2013, 2016 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bugs 496439, 496299
 *  Vincent Lorenzo - bug 496176
 *****************************************************************************/

package org.eclipse.papyrus.uml.m2m.qvto.common.transformation;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.gmf.runtime.emf.core.resources.GMFResource;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.m2m.qvt.oml.BasicModelExtent;
import org.eclipse.m2m.qvt.oml.ExecutionContext;
import org.eclipse.m2m.qvt.oml.ExecutionContextImpl;
import org.eclipse.m2m.qvt.oml.ExecutionDiagnostic;
import org.eclipse.m2m.qvt.oml.ModelExtent;
import org.eclipse.m2m.qvt.oml.TransformationExecutor;
import org.eclipse.m2m.qvt.oml.util.ISessionData;
import org.eclipse.m2m.qvt.oml.util.Trace;
import org.eclipse.m2m.qvt.oml.util.WriterLog;
import org.eclipse.papyrus.infra.emf.resource.ShardResourceHelper;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.uml.m2m.qvto.common.Activator;
import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MigrationParametersFactory;
import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.ThreadConfig;
import org.eclipse.papyrus.uml.m2m.qvto.common.concurrent.ExecutorsPool;
import org.eclipse.papyrus.uml.m2m.qvto.common.internal.extension.TransformationExtension;
import org.eclipse.papyrus.uml.m2m.qvto.common.utils.TraceHelper;
import org.eclipse.papyrus.uml.m2m.qvto.common.utils.TransformationUI;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Extension;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Executes a single import-to-Papyrus transformation
 *
 * @author Vincent Lorenzo
 *
 */
public abstract class AbstractImportTransformation implements IImportTransformation {
	/** For debug purpose */
	protected static boolean DEBUG = true;

	// SourceURI is the input
	protected final URI sourceURI;

	// targetURI is computed during the transformation
	protected URI targetURI;

	protected ModelExtent outUML, outNotation, outSashModel, inParameters, inPapyrusProfiles, sysML11Profile;

	protected MigrationResourceSet resourceSet;

	protected Job job;

	protected Resource umlResource;

	protected ThreadConfig parameters;

	protected boolean complete = false;

	// For logging purpose (Bug 455001)
	// Starts when the job starts; ends when the job returns
	/** Execution time, in nano-seconds */
	protected long executionTime = 0L;

	/** Execution time of the initial model loading / ns */
	protected long loadingTime = 0L;

	/** Execution time for handling dangling references / ns */
	protected long danglingRefTime = 0L;

	/** Execution time for executing the UML-RT transformation / ns */
	protected long importExtensionsTime = 0L;

	/** Source URI to Target URI map (For Models/Libraries/Fragments) */
	protected final Map<URI, URI> uriMappings = new HashMap<>();

	/** Source URI to Target URI map (For Profiles) */
	protected final Map<URI, URI> profileURIMappings = new HashMap<>();

	protected List<Diagram> diagramsToDelete = new LinkedList<>();

	protected static final ExecutorsPool executorsPool = new ExecutorsPool(2);

	// TODO : check if sourceEPackages is required for Rpy and common tranfo
	/** EPackages corresponding to source native profiles with specific support in the transformation */
	protected static final Set<EPackage> sourceEPackages = new HashSet<>();

	protected final IDependencyAnalysisHelper analysisHelper;


	/** Extensions contributed via other plug-ins */
	protected final List<TransformationExtension> extensions;

	/** Accumulation of incremental update traces from each transformation. */
	private Trace trace = Trace.createEmptyTrace();

	/** Transformation execution context used for all transformation runs. */
	protected ExecutionContext context;

	public AbstractImportTransformation(URI sourceURI) {
		this(sourceURI, MigrationParametersFactory.eINSTANCE.createThreadConfig(), null);
	}

	public AbstractImportTransformation(URI sourceURI, ThreadConfig config, IDependencyAnalysisHelper analysisHelper) {
		Assert.isNotNull(sourceURI);
		this.sourceURI = sourceURI;
		this.parameters = config;
		this.analysisHelper = analysisHelper;
		this.extensions = getAllExtensions();
	}

	/**
	 * Instantiate all the extensions for a specific transformation
	 *
	 * @return
	 * 		A non-null (potentially empty) list of extensions
	 */
	protected static List<TransformationExtension> getAllExtensions() {
		// TODO probably RSA only or the extension point must be rewritten
		return Collections.emptyList();
	}





	/**
	 * Executes the transformation
	 *
	 * The transformation will be executed asynchronously in a Job
	 */
	public void run(final boolean isUserJob) {

		job = new Job("Import " + getModelName()) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				long begin = System.nanoTime();
				IStatus result = AbstractImportTransformation.this.run(monitor);
				long end = System.nanoTime();
				executionTime = end - begin;
				return result;
			}
		};

		job.setUser(isUserJob);

		job.addJobChangeListener(new JobChangeAdapter() {

			@Override
			public void done(IJobChangeEvent event) {
				complete = true;
				if (isUserJob) {
					if (event.getResult().getSeverity() == IStatus.OK) {
						Display.getDefault().asyncExec(new Runnable() {

							@Override
							public void run() {
								MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), job.getName(), String.format("Model %s has been successfully imported", getModelName()));
							}
						});

					} else if (event.getResult().getSeverity() == IStatus.CANCEL) {
						Display.getDefault().asyncExec(new Runnable() {

							@Override
							public void run() {
								MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), job.getName(), String.format("Operation canceled: %s", getModelName()));
							}
						});
					} else {
						StatusManager.getManager().handle(event.getResult(), StatusManager.BLOCK);
					}
				}
			}

		});

		job.schedule();
	}

	public void waitForCompletion() {
		try {
			job.join();
		} catch (InterruptedException ex) {
			Activator.log.error(ex);
		}
	}

	public boolean isComplete() {
		return complete;
	}

	public IStatus getStatus() {
		if (job == null) { // If job hasn't been created, the operation has probably been canceled before the transformation is ran
			return new Status(IStatus.CANCEL, Activator.PLUGIN_ID, "Operation canceled");
		}
		return job.getResult();
	}

	public long getExecutionTime() {
		return executionTime;
	}

	public long getLoadingTime() {
		return loadingTime;
	}

	public long getHandleDanglingRefTime() {
		return danglingRefTime;
	}

	public long getImportExtensionsTime() {
		// TODO maybe only for RSa!
		return importExtensionsTime;
	}

	public URI getTargetURI() {
		return targetURI;
	}


	/**
	 * Initializes the resource set, and resolve all dependencies
	 */
	protected void initResourceSet(IProgressMonitor monitor) {
		resourceSet = new MigrationResourceSetImpl();
		synchronized (UMLUtil.class) {
			UMLUtil.init(resourceSet);
		}
		resourceSet.getLoadOptions().put(XMLResource.OPTION_DEFER_ATTACHMENT, true);
		resourceSet.getLoadOptions().put(XMLResource.OPTION_DEFER_IDREF_RESOLUTION, true);
		resourceSet.getLoadOptions().put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE, Boolean.TRUE);
		resourceSet.getLoadOptions().put(XMLResource.OPTION_USE_PACKAGE_NS_URI_AS_LOCATION, Boolean.FALSE);

		monitor.subTask("Loading source model " + getModelName());

		try {
			resourceSet.getResource(sourceURI, true);
			loadInPapyrusProfiles();
		} catch (Exception ex) {
			Activator.log.error("An error occurred while loading " + getModelName(), ex);
		}
	}

	protected abstract int countSupportedElements(); // TODO


	protected IStatus loadTransformations(IProgressMonitor monitor) {
		for (URI transformationURI : getAllTransformationURIs()) {
			executorsPool.preLoad(transformationURI);
			monitor.worked(1);
		}

		return Status.OK_STATUS;
	}

	// MemoryLeak: Don't rely on BasicDiagnostic.toIStatus
	// The source Diagnostic contains references to the QVTo ModelExtents, referencing the Model elements (used in #extractPapyrusProfiles())
	// When using the standard conversion, these references are not discarded
	protected static IStatus createStatusFromDiagnostic(Diagnostic diagnostic) {
		return new Status(diagnostic.getSeverity(),
				diagnostic.getSource(),
				diagnostic.getMessage(),
				diagnostic.getException());
	}




	/**
	 * Actually runs the transformation (in the current thread)
	 *
	 * @param monitor
	 * @return The transformation IStatus
	 */
	// TODO maybe more common stuff with RSA import here
	// TODO : maybe in the interface if we keep it!
	protected abstract IStatus run(final IProgressMonitor monitor);

	/**
	 * Functional interface to abstract {@link TransformationExtension#executeBefore(ExecutionContext, IProgressMonitor)}
	 * and {@link TransformationExtension#executeAfter(ExecutionContext, IProgressMonitor)}
	 *
	 * @author Camille Letavernier
	 *
	 */
	@FunctionalInterface
	protected static interface ExtensionFunction {
		public IStatus apply(TransformationExtension extension, ExecutionContext context, IProgressMonitor monitor);

		/**
		 * Implements ExtensionFunction
		 *
		 * Delegates to {@link TransformationExtension#executeBefore(ExecutionContext, IProgressMonitor)}
		 */
		public static IStatus executeBefore(TransformationExtension extension, ExecutionContext context, IProgressMonitor monitor) {
			return extension.executeBefore(context, monitor);
		}

		/**
		 * Implements ExtensionFunction
		 *
		 * Delegates to {@link TransformationExtension#executeAfter(ExecutionContext, IProgressMonitor)}
		 */
		public static IStatus executeAfter(TransformationExtension extension, ExecutionContext context, IProgressMonitor monitor) {
			return extension.executeAfter(context, monitor);
		}
	}

	protected void prepareExtensions() {
		for (TransformationExtension extension : extensions) {
			extension.setResourceSet(resourceSet);
			extension.setExecutorsPool(executorsPool);
			extension.setTransformation(this);
		}
	}

	protected IStatus importExtensions(ExecutionContext context, IProgressMonitor monitor, ExtensionFunction function) {
		List<IStatus> allResults = new ArrayList<>(extensions.size());
		for (TransformationExtension extension : extensions) {
			IStatus result = function.apply(extension, context, monitor);
			allResults.add(result);
		}

		if (allResults.isEmpty()) {
			return Status.OK_STATUS;
		} else if (allResults.size() == 1) {
			return allResults.get(0);
		} else {
			return aggregateStatus(allResults);
		}
	}

	// FIXME implement properly
	public static MultiStatus aggregateStatus(List<IStatus> statuses) {
		return new MultiStatus(Activator.PLUGIN_ID, IStatus.OK, statuses.toArray(new IStatus[statuses.size()]), "", null);
	}

	/**
	 * @param resource
	 */
	protected void cleanMetadataAnnotations(Resource resource) {
		// Bug 471684: UML2.x to UML2.5 creates (invalid) Ecore Metadata EAnnotations, which then cause OCL validation to fail
		// Remove these EAnnotations from the model to avoid side effects
		Iterator<EObject> rootElementsIterator = resource.getContents().iterator();
		while (rootElementsIterator.hasNext()) {
			EObject root = rootElementsIterator.next();
			if (root instanceof EAnnotation) {
				EAnnotation annotation = (EAnnotation) root;
				if (ExtendedMetaData.ANNOTATION_URI.equals(annotation.getSource())) {
					rootElementsIterator.remove();
				}
			}
		}
	}

	protected void handleDanglingURIs(Collection<Resource> resourcesToSave) {
		if (analysisHelper != null) {
			resourceSet.freeze();
			try {
				analysisHelper.computeURIMappings(resourcesToSave);
			} finally {
				resourceSet.unfreeze();
			}
		}
	}

	protected void unloadResourceSet(ResourceSet resourceSet) {
		EMFHelper.unload(resourceSet);
	}
	

	protected TransformationExecutor getTransformation(URI transformationURI, IProgressMonitor monitor) throws DiagnosticException {
		return executorsPool.getExecutor(transformationURI);
	}

	protected ModelExtent getInProfileDefinitions() {
		//TODO : not used, useful for common and Rpy import 
		return null;
	}
	
	protected ModelExtent getInPapyrusProfiles() {
		if (inPapyrusProfiles == null) {
			loadInPapyrusProfiles();
		}

		return inPapyrusProfiles;
	}
	

	protected abstract Diagnostic loadInPapyrusProfiles(); // TODO : add path of profile are parameters ?
	
	protected void checkResource(Resource resource) {
		Assert.isNotNull(resource);
		Assert.isTrue(!resource.getContents().isEmpty(), "The resource " + resource.getURI() + " is empty");
		for (EObject rootElement : resource.getContents()) {
			Assert.isTrue(!rootElement.eIsProxy());
		}
	}
	
	protected abstract Resource createUMLResource(ResourceSet resourceSet, URI sourceResourceURI, URI targetResourceURI);

	protected ModelExtent getInConfig() {
		if (inParameters == null) {
			inParameters = new BasicModelExtent(Collections.singletonList(parameters));
		}
		return inParameters;
	}
	
	protected Collection<Resource> handleFragments(Resource umlResource, Resource notationResource, Resource sashResource) {
		Collection<Resource> result = new HashSet<>();
		result.add(umlResource);
		result.add(notationResource);
		result.add(sashResource);

		ResourceSet resourceSet = umlResource.getResourceSet();

		Iterator<EObject> elementIterator = umlResource.getAllContents();

		Set<Resource> fragmentResources = new HashSet<>();
		List<EAnnotation> rsaAnnotations = new ArrayList<>();

		while (elementIterator.hasNext()) {
			EObject element = elementIterator.next();
			Resource possibleFragment = element.eResource();
			if ((possibleFragment != umlResource) && possibleFragment.getContents().contains(element)) { // Controlled/Fragment root
				fragmentResources.add(possibleFragment);
			}
			// TODO commented because it is RSA import only
			// collectRSAAnnotations(element, rsaAnnotations);
		}

		// Strip all RSA fragment annotations
		rsaAnnotations.forEach(EcoreUtil::remove);

		List<Resource> fragmentUMLResources = new LinkedList<>();

		for (Resource fragmentResource : fragmentResources) {
			URI papyrusFragmentURI = convertToPapyrus(fragmentResource.getURI(), UMLResource.FILE_EXTENSION);

			uriMappings.put(fragmentResource.getURI(), papyrusFragmentURI);

			Resource newResource = resourceSet.getResource(papyrusFragmentURI, false);
			if (newResource == null) {
				newResource = createUMLResource(resourceSet, fragmentResource.getURI(), papyrusFragmentURI);

				fragmentUMLResources.add(newResource);

				Resource fragmentNotationResource = new GMFResource(convertToPapyrus(papyrusFragmentURI, "notation"));
				Resource fragmentDiResource = new XMIResourceImpl(convertToPapyrus(papyrusFragmentURI, "di"));

				result.add(fragmentNotationResource);
				result.add(fragmentDiResource);

				resourceSet.getResources().add(fragmentNotationResource);
				resourceSet.getResources().add(fragmentDiResource);
			}

			newResource.getContents().addAll(fragmentResource.getContents());

			// Make it a Papyrus controlled unit of the "shard" variety
			try (ShardResourceHelper shard = new ShardResourceHelper(newResource)) {
				shard.setShard(true);
			}

			result.add(newResource);
		}

		deleteSourceStereotypes(fragmentResources);

		List<EObject> importedElements = new LinkedList<>(notationResource.getContents());
		for (EObject notationElement : importedElements) {
			if (notationElement instanceof Diagram) {
				EObject semanticElement = ((Diagram) notationElement).getElement();
				if (semanticElement.eResource() != umlResource && semanticElement.eResource() != null) {

					URI notationFragmentURI = convertToPapyrus(semanticElement.eResource().getURI(), "notation");

					Resource newNotationResource = resourceSet.getResource(notationFragmentURI, false);
					if (newNotationResource == null) {
						newNotationResource = new GMFResource(notationFragmentURI);
						resourceSet.getResources().add(newNotationResource);
					}
					newNotationResource.getContents().add(notationElement);
					result.add(newNotationResource);
				}
			}
		}

		handleFragmentStereotypes(umlResource, fragmentUMLResources);

		for (Resource resource : result) {
			if (resource instanceof XMIResource) {
				configureResource((XMIResource) resource);
			}
		}

		return result;
	}

	/*
	 * Bug 447097: [Model Import] Importing a fragmented model causes stereotype applications to be lost in resulting submodel
	 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=447097
	 *
	 * Before the transformation, We moved all root elements from the fragment resources to the main
	 * resource, then we transformed some of them to Papyrus Stereotype Applications. We need to move
	 * these stereotype applications back to the proper fragment resource
	 */
	protected void handleFragmentStereotypes(Resource mainUMLResource, List<Resource> umlResources) {
		Iterator<EObject> rootElementIterator = mainUMLResource.getContents().iterator();
		while (rootElementIterator.hasNext()) {
			EObject rootElement = rootElementIterator.next();
			if (rootElement instanceof Element) {
				continue;
			}

			Resource targetStereotypeResource = getTargetStereotypeResource(rootElement, umlResources);
			if (targetStereotypeResource != null && targetStereotypeResource != mainUMLResource) {
				rootElementIterator.remove(); // To avoid ConcurrentModificationException when moving to the other resource
				targetStereotypeResource.getContents().add(rootElement);
			}
		}
	}

	protected Resource getTargetStereotypeResource(EObject rootElement, List<Resource> umlResources) {
		for (EReference eReference : rootElement.eClass().getEAllReferences()) {
			if (eReference.getName().startsWith(Extension.METACLASS_ROLE_PREFIX)) {
				Object value = rootElement.eGet(eReference);
				if (value instanceof Element) {
					return ((Element) value).eResource();
				}
			}
		}

		return null;
	}

	protected void deleteSourceStereotypes(Collection<Resource> fragmentResources) {
		Set<Resource> allResources = new HashSet<>(fragmentResources);
		allResources.add(umlResource);

		for (Resource resource : allResources) {

			// For performance reasons, RSA RT Stereotypes have not been deleted during the QVTo transformation (Bug 444379)
			// Delete them as a post-action. Iterate on all controlled models and delete the RealTime stereotypes at the root of each resource
			List<EObject> resourceContents = new LinkedList<>(resource.getContents());
			for (EObject rootElement : resourceContents) {
				if (sourceEPackages.contains(rootElement.eClass().getEPackage())) {
					delete(rootElement);
				}
			}
		}
	}

	protected abstract URI convertToPapyrus(URI rsaURI, String extension);
	
	/**
	 * Runs a transformation using the context shared by all transformations.
	 * 
	 * @param transformationURI
	 *            the transformation to run
	 * @param extents
	 *            the extents on which to apply the transformation
	 * @param monitor
	 *            progress monitor
	 * 
	 * @return the result of the transformation execution
	 */
	public IStatus runTransformation(URI transformationURI, List<ModelExtent> extents, IProgressMonitor monitor) {
		return runTransformation(transformationURI, context, monitor, extents);
	}

	protected IStatus runTransformation(URI transformationURI, ExecutionContext context, IProgressMonitor monitor, List<ModelExtent> extents) {
		if (monitor.isCanceled()) {
			return new Status(IStatus.CANCEL, Activator.PLUGIN_ID, "Operation canceled");
		}

		TransformationExecutor executor;
		try {
			executor = getTransformation(transformationURI, monitor);
		} catch (DiagnosticException ex) {
			Diagnostic diagnostic = ex.getDiagnostic();

			Activator.log.warn(String.format("Cannot load the transformation : %s. Diagnostic: %s", transformationURI, diagnostic.getMessage()));
			return createStatusFromDiagnostic(diagnostic);
		}

		ExecutionDiagnostic result;
		synchronized (executor) {
			try {
				// Gather the new execution traces
				Trace newTraces = Trace.createEmptyTrace();
				@SuppressWarnings("restriction")
				ISessionData.SimpleEntry<Trace> traceKey = org.eclipse.m2m.internal.qvt.oml.evaluator.QVTEvaluationOptions.INCREMENTAL_UPDATE_TRACE;
				context.getSessionData().setValue(traceKey, newTraces);

				result = executor.execute(context, extents.toArray(new ModelExtent[0]));

				// Append to our history
				List<EObject> history = new ArrayList<>(trace.getTraceContent());
				history.addAll(newTraces.getTraceContent());
				trace.setTraceContent(history);
			} finally {
				executor.cleanup();
				executorsPool.releaseExecutor(executor);
			}
		}

		return createStatusFromDiagnostic(result);
	}

	protected ExecutionContext createExecutionContext(final IProgressMonitor monitor, final MultiStatus generationStatus) {
		ExecutionContextImpl context = new ExecutionContextImpl();
		context.setConfigProperty("keepModeling", true); //$NON-NLS-1$ o
		context.setConfigProperty(TransformationUI.MONITOR, monitor);

		// context.setProgressMonitor(monitor);

		context.setLog(new WriterLog(new OutputStreamWriter(System.out)) {

			@Override
			public void log(String message) {
				super.log(message);
			}

			@Override
			public void log(String message, Object param) {
				super.log(message, param);
			}

			@Override
			public void log(int level, String message) {
				super.log(level, message);
				if (level >= 1) {
					generationStatus.merge(new Status(level, Activator.PLUGIN_ID, message));
				}

			}

			@Override
			public void log(int level, String message, Object param) {
				super.log(level, message, param);
				if (level >= 1) {
					generationStatus.merge(new Status(level, Activator.PLUGIN_ID, message + ", data:" + param));
				}
			}
		});

		initTransformationProperties(context);

		// Invoke extensions as incremental transformations

		context.getSessionData().setValue(TraceHelper.TRACE_HISTORY, trace);

		return context;
	}
	
	/**
	 * Initializes the ExecutionContext with configuration properties required by transformations
	 *
	 * This is a lightweight mechanism to avoid initializing ModelExtents for a single EObject reference, or for non-EMF values
	 *
	 * Typically used by blackbox methods
	 *
	 * @param context
	 */
	protected abstract void initTransformationProperties(ExecutionContextImpl context);
	

	protected void configureResource(XMIResource resource) {
		Map<Object, Object> saveOptions = new HashMap<>();

		// default save options.
		saveOptions.put(XMLResource.OPTION_DECLARE_XML, Boolean.TRUE);
		saveOptions.put(XMLResource.OPTION_PROCESS_DANGLING_HREF, XMLResource.OPTION_PROCESS_DANGLING_HREF_DISCARD);
		saveOptions.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
		saveOptions.put(XMIResource.OPTION_USE_XMI_TYPE, Boolean.TRUE);
		saveOptions.put(XMLResource.OPTION_SAVE_TYPE_INFORMATION, Boolean.TRUE);
		saveOptions.put(XMLResource.OPTION_SKIP_ESCAPE_URI, Boolean.FALSE);
		saveOptions.put(XMLResource.OPTION_ENCODING, "UTF-8");

		// see bug 397987: [Core][Save] The referenced plugin models are saved using relative path
		saveOptions.put(XMLResource.OPTION_URI_HANDLER, new org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl.PlatformSchemeAware());

		resource.setEncoding("UTF-8");
		resource.getDefaultSaveOptions().putAll(saveOptions);
	}

	protected abstract List<ModelExtent> getModelExtents();

	public abstract ModelExtent getInOutUMLModel();

	/* Notation model is initially empty, but will be filled successively by each transformation */
	public ModelExtent getInoutNotationModel() {
		if (outNotation == null) {
			outNotation = new BasicModelExtent();
		}

		return outNotation;
	}

	protected ModelExtent getOutSashModel() {
		if (outSashModel == null) {
			outSashModel = new BasicModelExtent();
		}

		return outSashModel;
	}


	protected abstract Collection<URI> getDiagramTransformationURIs();
	
	//TODO : warning, the RSA code will probably be changed and this method will not used for RSA, because it is called after diagrams transformation for RSA.
	protected abstract URI getSemanticTransformationURI();

	protected abstract Collection<URI> getProfilesTransformationURI();

	protected Collection<URI> getAdditionalTransformationURIs() {
		return Collections.emptyList();
	}

	//currently abstract because We probably doesn't apply the transformation in the same order for Rpy than for  RSA
	protected abstract Collection<URI> getAllTransformationURIs();

	protected URI getTransformationURI(String transformationName, String pluginID) {
		return URI.createPlatformPluginURI(String.format("%s/transform/%s.qvto", pluginID, transformationName), true); //$NON-NLS-1$
	}

	public String getModelName() {
		return URI.decode(sourceURI.lastSegment());
	}


	public void cancel() {
		job.cancel();
	}

	/** Lightweight delete operation, which only removes the object from its parent. Incoming references are not deleted */
	public void delete(EObject elementToDelete) {
		CacheAdapter adapter = CacheAdapter.getCacheAdapter(elementToDelete); // bug 541313 [CDO] - change is not required here
		if (adapter == null) {
			adapter = CacheAdapter.getInstance();
		}
		adapter.unsetTarget(elementToDelete);
		if (elementToDelete.eResource() != null) {
			elementToDelete.eResource().getContents().remove(elementToDelete);
		}

		EObject parent = elementToDelete.eContainer();
		if (parent == null) {
			return;
		}
		EReference containmentFeature = elementToDelete.eContainmentFeature();

		if (containmentFeature.getUpperBound() == 1) {
			parent.eUnset(containmentFeature);
		} else {
			List<?> values = (List<?>) parent.eGet(containmentFeature);
			values.remove(elementToDelete);
		}
	}
	
	/**
	 * 
	 * @return
	 * 		the trace of the transformation
	 */
	protected Trace getTrace() {
		return this.trace;
	}
}
