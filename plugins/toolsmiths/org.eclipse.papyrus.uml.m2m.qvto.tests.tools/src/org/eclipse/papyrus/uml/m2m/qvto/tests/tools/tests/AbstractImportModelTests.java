/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.m2m.qvto.tests.tools.tests;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.internal.jobs.Worker;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Conflict;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.DisplayUtils;
import org.eclipse.papyrus.junit.utils.ProjectUtils;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MigrationParametersFactory;
import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.ThreadConfig;
import org.eclipse.papyrus.uml.m2m.qvto.common.transformation.AbstractImportTransformationLauncher;
import org.eclipse.papyrus.uml.m2m.qvto.tests.tools.Activator;
import org.eclipse.uml2.uml.Package;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.io.Files;

/**
 * Abstract class used to test the import model into Papyrus.
 */
@SuppressWarnings({ "nls", "restriction" })
public abstract class AbstractImportModelTests extends AbstractPapyrusTest {

	/**
	 * Relative path for some interesting folder to check .
	 */
	protected static final String XMI_ID_ATTRIBUTE_NAME = "xmi:id"; // $NON-NLS-0$


	/**
	 * The files to import in Papyrus.
	 */
	protected Set<IFile> filesToImport = new HashSet<IFile>();

	/**
	 * The Papyrus editor fixture used to load the expected model.
	 */
	@Rule
	public final ModelSetFixture expectedResultFixture = new ModelSetFixture();

	/**
	 * This resource set contains the files created by the transformation
	 */
	protected final ResourceSet resultingResourceSet = new ResourceSetImpl();

	/**
	 * The di resource created by the import.
	 */
	protected Resource diResource = null;

	/**
	 * The notation resource created by the import.
	 */
	protected Resource notationResource = null;

	/**
	 * The uml resource created by the import.
	 */
	protected Resource umlResource = null;

	/**
	 * The created project used for the tests.
	 */
	protected IProject project;

	/**
	 * The model name to import.
	 */
	protected String modelName;

	/**
	 * The resource path.
	 */
	protected String resourcePath;

	/**
	 * The created UML File.
	 */
	protected IFile outputUmlFile = null;

	/**
	 * The created notation File.
	 */
	protected IFile outputNotationFile = null;

	/**
	 * The created di File.
	 */
	protected IFile outputDiFile = null;

	/**
	 * The launcher of the import transformation.
	 */
	protected AbstractImportTransformationLauncher launcher;


	/**
	 * This method allows to create the project.
	 * 
	 * @param projectName
	 *            The name of the project to create for the JUnit test.
	 * @param modelName
	 *            The model name to import.
	 * @param sourceExtension
	 *            The extension of the file to transform.
	 * @param resourcePath
	 *            The path where are stored the file to copy/load to execute the tests.
	 * @param bundle
	 *            The current bundle.
	 * @throws CoreException
	 *             The core exception.
	 * @throws IOException
	 *             The input/output file exception.
	 * @throws URISyntaxException
	 *             The URI syntax exception.
	 * 
	 */
	public void initTest(final String projectName, final String modelName, final String sourceExtension, final String resourcePath, final Bundle bundle) throws CoreException, IOException, URISyntaxException {
		this.modelName = modelName;
		this.project = ProjectUtils.createProject(projectName);

		importModelIntoProject(modelName, resourcePath, sourceExtension, bundle, this.project);
		executeTransformation(this.filesToImport);
		DisplayUtils.flushEventLoop();
		waitEndOfImportThread();
		DisplayUtils.flushEventLoop();
		initOutputIFilesFields();
	}

	/**
	 * This allows to initialize output files fields.
	 */
	protected void initOutputIFilesFields() {
		if (null == this.outputUmlFile) {
			this.outputUmlFile = checkUMLFileCreationAndGetIt();
		}
		if (null == this.outputNotationFile) {
			this.outputNotationFile = checkNotationFileCreationAndGetIt();
		}
		if (null == this.outputDiFile) {
			this.outputDiFile = checkDiFileCreationAndGetIt();
		}
	}

	/**
	 * This allows to initialize output resources fields.
	 */
	protected void initOutputResourcesFields() {
		initOutputIFilesFields();
		if (null == this.umlResource) {
			this.umlResource = addFileToResourceSet(resultingResourceSet, outputUmlFile);
		}
		if (null == this.notationResource) {
			this.notationResource = addFileToResourceSet(resultingResourceSet, outputNotationFile);
		}
		if (null == this.diResource) {
			this.diResource = addFileToResourceSet(resultingResourceSet, outputDiFile);
		}
	}

	/**
	 * This method import the project into the workspace.
	 * 
	 * @param modelName
	 *            The model name.
	 * @param sourcePath
	 *            The source path of the file to transform.
	 * @param sourceExtension
	 *            The source extension of the file to transform.
	 * @param sourceBundle
	 *            The source bundle.
	 * @param targetProject
	 *            The target project.
	 * @throws URISyntaxException
	 *             The URI syntax exception.
	 * @throws IOException
	 *             The input/output file exception.
	 */
	public final void importModelIntoProject(final String modelName, final String sourcePath, final String sourceExtension, final Bundle sourceBundle, final IProject targetProject) throws URISyntaxException, IOException {
		// TODO improve all this path with url, uri, string, ... emf URI will be a good solution
		final String currentPath = sourcePath + modelName + "." + sourceExtension;
		final URL url = sourceBundle.getResource(currentPath);
		final java.net.URI uri = FileLocator.resolve(url).toURI();
		final URI currentURI = copyOldProjectToNewFolder(URI.createFileURI(uri.getRawPath()), URI.createFileURI(targetProject.getLocationURI().getRawPath()));
		try {
			this.project.refreshLocal(IProject.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (final CoreException e) {
			Activator.log.error(e);
		}
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IPath location = Path.fromOSString(currentURI.toFileString());
		final IFile ifile = workspace.getRoot().getFileForLocation(location);
		filesToImport.add(ifile);
	}

	/**
	 * This allows to copy the project to transform in a new folder.
	 * 
	 * @param initialProjectURI
	 *            the initial project URI.
	 * @param newPath
	 *            The new URI path of the project.
	 * @return The URI of the main model to transform.
	 */
	protected URI copyOldProjectToNewFolder(final URI initialProjectURI, final URI newPath) {
		final URI uri = initialProjectURI.trimSegments(1);// to get the parent folder
		final File projectFolder = new File(uri.toFileString());
		final List<File> duplicatedFile = new ArrayList<File>();
		if (projectFolder.exists() && projectFolder.isDirectory()) {
			for (final File subFile : projectFolder.listFiles()) {
				duplicatedFile.addAll(copyFile(subFile, newPath));
			}
		}
		for (final File current : duplicatedFile) {
			final URI newURI = URI.createFileURI(current.getPath());
			if (isNeededSourceExtension(newURI.fileExtension())) {
				return newURI;
			}
		}
		return null;
	}

	/**
	 * This allows to check if the file extension is the needed one for the source file.
	 * 
	 * @param fileExtension
	 *            The current file extension.
	 * @return <code>true</code> if the file extension is the needed one, <code>false</code> otherwise.
	 */
	protected boolean isNeededSourceExtension(final String fileExtension) {
		return "uml".equals(fileExtension);
	}

	/**
	 * This method copy a file to a given path. The File can be a directory. In this case, we create it in the new path with its contents
	 * 
	 * @param src
	 *            the source file (can be a directory
	 * @param target
	 *            the target path for the copied file
	 * @return
	 * 		the file of copied file (useful in case of folder
	 */
	public List<File> copyFile(final File src, final URI target) {
		final List<File> duplicatedFile = new ArrayList<File>();
		URI copiedFileURI = URI.createFileURI(target.devicePath());
		copiedFileURI = copiedFileURI.appendSegment(src.getName());
		if (src.isDirectory()) {
			final File folder = new File(copiedFileURI.devicePath());
			folder.mkdir();
			for (final File f : src.listFiles()) {
				duplicatedFile.addAll(copyFile(f, copiedFileURI));
			}
		} else {
			final File newFile = new File(copiedFileURI.devicePath());
			try {
				Files.copy(src, newFile);
				duplicatedFile.add(newFile);
			} catch (final IOException e) {
				Activator.log.error(e);
			}
		}
		return duplicatedFile;
	}

	/**
	 * This allows to execute the transformation for the files in parameter.
	 * 
	 * @param files
	 *            The list of file to import.
	 */
	protected void executeTransformation(final Set<IFile> files) {
		final List<URI> urisToImport = new ArrayList<URI>();
		for (final IFile current : files) {
			String path = null;
			if (current instanceof IFile) {
				path = ((IFile) current).getFullPath().toString();
			}
			if (null != path) {
				final URI uri = URI.createPlatformResourceURI(path, true);
				urisToImport.add(uri);
			}
		}

		final ThreadConfig config = MigrationParametersFactory.eINSTANCE.createThreadConfig();
		launcher = createLauncher(config);
		launcher.run(urisToImport);
	}

	/**
	 * This allows to create the launcher for the QvTo transformation.
	 * 
	 * @param config
	 *            The ThreadConfig.
	 * @return The created launcher.
	 */
	public abstract AbstractImportTransformationLauncher createLauncher(final ThreadConfig config);

	/**
	 * This allows to check if the UML file is correctly created with the transformation and return it.
	 * 
	 * @return
	 * 		The uml file created by the QVTO transformation if it exists.
	 */
	protected IFile checkUMLFileCreationAndGetIt() {
		String outputFolder = getOutputFolder(); // TODO output folder must be a parameter of the transformation, it could be a project too
		if (!outputFolder.isEmpty() && !outputFolder.endsWith(File.separator)) {
			outputFolder = outputFolder + File.separator;
		}
		final IFile umlFile = project.getFile(outputFolder + getOutputModelName() + ".uml");
		try {
			umlFile.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (CoreException e) {
			Activator.log.error(e);
		}
		Assert.assertTrue("The uml file has not been created: " + umlFile.getFullPath(), umlFile.exists());
		return umlFile;
	}

	/**
	 * This allows to check if the Notation file is correctly created with the transformation and return it.
	 * 
	 * @return
	 * 		The notation file created by the QVTO transformation if it exists.
	 */
	protected IFile checkNotationFileCreationAndGetIt() {
		String outputFolder = getOutputFolder(); // TODO output folder must be a parameter of the transformation, it could be a project too
		if (!outputFolder.isEmpty() && !outputFolder.endsWith(File.separator)) {
			outputFolder = outputFolder + File.separator;
		}
		final IFile notationFile = project.getFile(outputFolder + getOutputModelName() + ".notation");
		Assert.assertTrue("The notation file has not been created: " + notationFile.getFullPath(), notationFile.exists());

		return notationFile;
	}

	/**
	 * This allows to check if the Di file is correctly created with the transformation and return it.
	 * 
	 * @return
	 * 		The Di file created by the QVTO transformation if it exists.
	 */
	protected IFile checkDiFileCreationAndGetIt() {
		String outputFolder = getOutputFolder(); // TODO output folder must be a parameter of the transformation, it could be a project too
		if (!outputFolder.isEmpty() && !outputFolder.endsWith(File.separator)) {
			outputFolder = outputFolder + File.separator;
		}
		final IFile diFile = project.getFile(outputFolder + getOutputModelName() + ".di");
		Assert.assertTrue("The di file has not been created: " + diFile.getFullPath(), diFile.exists());
		return diFile;
	}

	/**
	 * This allows to get the output folder of the transformation.
	 * 
	 * @return The output folder of the transformation.
	 */
	protected String getOutputFolder() {
		return "";
	}

	/**
	 * This allows to get the name of the created output model.
	 * 
	 * @return The name of the created output model.
	 */
	protected String getOutputModelName() {
		return modelName;
	}

	/**
	 * This method allows to wait the end of the import thread.
	 * TODO : refactore API to have a direct access to the import thread!
	 */
	protected void waitEndOfImportThread() {
		Set<Thread> threads = Thread.getAllStackTraces().keySet();
		for (Thread thread : threads) {
			if (thread instanceof Worker) {
				Job job = ((Worker) thread).currentJob();
				if (null != job) {
					if (job.getName().equals(AbstractImportTransformationLauncher.IMPORT_MODELS_JOB_NAME)) {
						try {
							job.join();
						} catch (final InterruptedException e1) {
							Activator.log.error(e1);
						}
					}
				}
			}
		}
	}

	/**
	 * This allows to add a file to the current resource set.
	 * 
	 * @param resourceSet
	 *            The resource set.
	 * @param fileToAdd
	 *            The file to add to the resource set.
	 * @return
	 * 		The resource represented the added file.
	 */
	protected Resource addFileToResourceSet(final ResourceSet resourceSet, final IFile fileToAdd) {
		final String path = fileToAdd.getFullPath().toPortableString();
		final URI uri = URI.createURI(path);
		boolean exist = resourceSet.getURIConverter().exists(uri, null);
		Assert.assertTrue(exist);
		return resourceSet.getResource(uri, true);
	}

	/**
	 * This allows to check the transformed UML model.
	 */
	@Test
	public void checkTransformedUMLModel() {
		initOutputResourcesFields();
		Assert.assertTrue("The created uml resource is empty.", this.umlResource.getContents().size() > 0); //$NON-NLS-1$
		final Iterator<EObject> iter = this.umlResource.getContents().iterator();
		Package root = null;
		while (null == root && iter.hasNext()) {
			final EObject tmp = iter.next();
			if (tmp instanceof Package) {
				root = (Package) tmp;
			}
		}
		Assert.assertNotNull("The root of the imported model has not been found", root); //$NON-NLS-1$

		checkUMLModelSpecificities(root);
	}

	/**
	 * This allows to check the transformed notation model.
	 */
	@Test
	public void checkTransformedNotationModel() {
		initOutputResourcesFields();
		Assert.assertTrue("The created notation resource is empty.", this.notationResource.getContents().size() > 0); //$NON-NLS-1$
		for (final EObject current : this.notationResource.getContents()) {
			if (current instanceof Diagram) {
				checkNotationElementSpecificities((Diagram) current);
			} else if (current instanceof Table) {
				checkNotationElementSpecificities((Table) current);
			}
		}
	}

	/**
	 * This allows to check the specificities on the UML Model transformation.
	 * 
	 * @param rootPackage
	 *            The root UML Package.
	 */
	protected abstract void checkUMLModelSpecificities(final Package rootPackage);

	/**
	 * This allows to check the specificities on the notation elements (Diagrams and Tables).
	 * 
	 * @param notationElement
	 *            The root notation element.
	 */
	protected abstract void checkNotationElementSpecificities(final EModelElement notationElement);


	/**
	 * This method allows to check the created UML Model with the expected one.
	 */
	@Test
	public void checkSemanticWithEMFCompare() {
		initOutputResourcesFields();
		final ResourceSet resultSet = new ResourceSetImpl();
		Resource currentResultResource = resultSet.getResource(this.umlResource.getURI(), true);
		List<EObject> currentContents = new ArrayList<EObject>(currentResultResource.getContents());
		currentContents.sort(new XMIIDSorter());
		currentResultResource.getContents().clear();
		currentResultResource.getContents().addAll(currentContents);

		final ResourceSet expectedSet = new ResourceSetImpl();
		Resource expectedResource = expectedSet.getResource(expectedResultFixture.getModelResource().getURI(), true);
		List<EObject> expectedContents = new ArrayList<EObject>(expectedResource.getContents());
		expectedContents.sort(new XMIIDSorter());
		expectedResource.getContents().clear();
		expectedResource.getContents().addAll(expectedContents);

		final DefaultComparisonScope scope = new DefaultComparisonScope(resultSet, expectedSet, null);
		final Comparison result = EMFCompareUtils.createEMFCompare().compare(scope);
		final List<Conflict> conflicts = result.getConflicts();
		Assert.assertEquals("Conflicts have been detected", 0, conflicts.size()); //$NON-NLS-1$

		final List<Diff> differences = getFilteredDiffForUMLModel(new ArrayList<Diff>(result.getDifferences()));

		if (!differences.isEmpty()) {
			final StringBuilder builder = new StringBuilder(NLS.bind("{0} differences have been detected: \n", differences.size())); //$NON-NLS-1$
			final Iterator<Diff> iter = differences.iterator();
			while (iter.hasNext()) {
				final Diff current = iter.next();
				builder.append(current.toString());
				if (iter.hasNext()) {
					builder.append("\n"); //$NON-NLS-1$
				}
			}
			Assert.assertEquals(builder.toString(), 0, differences.size());
		}
	}

	/**
	 * This allows to manage the filtered differences for the UML Model. Some can be removed because they will not be equals.
	 * 
	 * @param diff
	 *            The initial list of differences.
	 * @return The list of differences to check.
	 */
	protected List<Diff> getFilteredDiffForUMLModel(final List<Diff> diff) {
		return diff;
	}

	/**
	 * This method allows to check the created Notation Model with the expected one.
	 */
	@Test
	public void checkNotationWithEMFCompare() {
		if (compareNotation()) {
			initOutputResourcesFields();
			final ResourceSet resultSet = new ResourceSetImpl();
			resultSet.getResource(this.umlResource.getURI(), true);
			resultSet.getResource(this.notationResource.getURI(), true);

			final ResourceSet expectedSet = new ResourceSetImpl();

			expectedSet.getResource(expectedResultFixture.getModelResource().getURI(), true);
			expectedSet.getResource(expectedResultFixture.getModelResource().getURI().trimFileExtension().appendFileExtension("notation"), true);

			final DefaultComparisonScope scope = new DefaultComparisonScope(resultSet, expectedSet, null);
			final Comparison result = EMFCompareUtils.createEMFCompare().compare(scope);
			final List<Conflict> conflicts = result.getConflicts();
			Assert.assertEquals("Conflicts have been detected", 0, conflicts.size()); //$NON-NLS-1$

			final List<Diff> differences = getFilteredDiffForNotationModel(new ArrayList<Diff>(result.getDifferences()));
			if (!differences.isEmpty()) {
				final StringBuilder builder = new StringBuilder(NLS.bind("{0} differences have been detected: \n", differences.size())); //$NON-NLS-1$
				final Iterator<Diff> iter = differences.iterator();
				while (iter.hasNext()) {
					final Diff current = iter.next();
					builder.append(current.toString());
					if (iter.hasNext()) {
						builder.append("\n"); //$NON-NLS-1$
					}
				}
				Assert.assertEquals(builder.toString(), 0, differences.size());
			}
		}
	}

	/**
	 * This allows to determinate if the notation file must be compared.
	 * 
	 * @return <code>true</code> if the notation file must be compared, <code>false</code> otherwise.
	 */
	protected boolean compareNotation() {
		return true;
	}

	/**
	 * This allows to manage the filtered differences for the Notation Model. Some can be removed because they will not be equals.
	 * 
	 * @param diff
	 *            The initial list of differences.
	 * @return The list of differences to check.
	 */
	protected List<Diff> getFilteredDiffForNotationModel(final List<Diff> diff) {
		return diff;
	}

	/**
	 * Checks that the XMI_ID are unique in the uml file.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	@Test
	public void checkUnicityOfXMIIDInUMLFile() throws Exception {
		final DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		final Document document = dBuilder.parse(getUMLOutputFile());
		final List<String> ids = new ArrayList<String>();
		final List<Node> nodes = flattenDocument(document);
		for (final Node node : nodes) {
			if (null != node.getAttributes()) {
				final Node item = node.getAttributes().getNamedItem(XMI_ID_ATTRIBUTE_NAME);
				if (null != item) {
					final String value = item.getNodeValue();
					ids.add(value);
				}
			}
		}
		final Set<String> uniqueIds = new HashSet<>(ids);
		for (final String t : uniqueIds) {
			// remove all method remove all instance, so it can't be used here
			ids.remove(t);
		}
		Assert.assertEquals("Some ids are duplicated in the UML model: " + ids.toString(), 0, ids.size());
	}

	/**
	 * Checks that the XMI_ID are unique in the notation file.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	@Test
	public void checkUnicityOfXMIIDInNotationFile() throws Exception {
		final DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		final Document document = dBuilder.parse(getNotationOutputFile());
		final List<String> ids = new ArrayList<String>();
		final List<Node> nodes = flattenDocument(document);
		for (final Node node : nodes) {
			if (null != node.getAttributes()) {
				Node item = node.getAttributes().getNamedItem(XMI_ID_ATTRIBUTE_NAME);
				if (null != item) {
					final String value = item.getNodeValue();
					ids.add(value);
				}
			}
		}
		final Set<String> uniqueIds = new HashSet<>(ids);
		for (final String t : uniqueIds) {
			// remove all method remove all instance, so it can't be used here
			ids.remove(t);
		}
		Assert.assertEquals("Some ids are duplicated in the Notation model: " + ids.toString(), 0, ids.size());
	}

	/**
	 * This allows to check if all the UML elements have XMI_ID.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	@Test
	public void checkAllElementInUMLFileHaveAnID() throws Exception {
		final DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		final Document document = dBuilder.parse(getUMLOutputFile());
		final List<String> ids = new ArrayList<String>();
		final List<Node> nodes = flattenDocument(document);
		for (final Node node : nodes) {
			if (null != node.getAttributes()) {
				final Node item = node.getAttributes().getNamedItem(XMI_ID_ATTRIBUTE_NAME);
				if (null != item) {
					final String value = item.getNodeValue();
					ids.add(value);
				}
			}
		}
		int nbElements = 0;

		// Here we need to load the umlResource to know how elements there are in the model.
		// it can fail if several elements have the same IDs and incompatible type
		initOutputResourcesFields();
		final Iterator<EObject> iter = this.umlResource.getAllContents();
		while (iter.hasNext()) {
			iter.next();
			nbElements++;
		}
		Assert.assertEquals("I don't found the same number of XMI_ID than the number of element in the file", nbElements, ids.size());

	}

	/**
	 * This allows to check if all the notation elements have XMI_ID.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	@Test
	public void checkAllElementInNotationFileHaveAnID() throws Exception {
		final DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		final Document document = dBuilder.parse(getNotationOutputFile());
		final List<String> ids = new ArrayList<String>();
		final List<Node> nodes = flattenDocument(document);
		for (final Node node : nodes) {
			if (null != node.getAttributes()) {
				final Node item = node.getAttributes().getNamedItem(XMI_ID_ATTRIBUTE_NAME);
				if (null != item) {
					final String value = item.getNodeValue();
					ids.add(value);
				}
			}
		}
		int nbElements = 0;

		// here we need to load the umlResource to know how elements there are in the model.
		// it can fail if several elements have the same IDs and incompatible type
		initOutputResourcesFields();
		final Iterator<EObject> iter = this.notationResource.getAllContents();
		while (iter.hasNext()) {
			iter.next();
			nbElements++;
		}
		Assert.assertEquals("I don't found the same number of XMI_ID than the number of element in the file", nbElements, ids.size());

	}

	/**
	 * This allows to get the document children as list.
	 * 
	 * @param document
	 *            The document.
	 * @return
	 * 		All the nodes of the document.
	 */
	public List<Node> flattenDocument(final Document document) {
		return getAllChildren(document.getChildNodes());
	}

	/**
	 * This allows to get the node's children as list.
	 * 
	 * @param nodeList
	 *            The node list.
	 * @return
	 * 		All nodes and sub-nodes of the node list.
	 */
	public List<Node> getAllChildren(final NodeList nodeList) {
		final List<Node> objects = new ArrayList<>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			final Node node = nodeList.item(i);
			objects.add(node);
			objects.addAll(getAllChildren(node.getChildNodes()));
		}
		return objects;
	}

	/**
	 * Get the UML output file.
	 * 
	 * @return
	 * 		The UML output file.
	 */
	protected File getUMLOutputFile() {
		final IPath path = this.outputUmlFile.getRawLocation();
		final File file = path.toFile();
		return file;
	}

	/**
	 * Get the Notation output file.
	 * 
	 * @return
	 * 		The Notation output file.
	 */
	protected File getNotationOutputFile() {
		final IPath path = this.outputNotationFile.getRawLocation();
		final File file = path.toFile();
		return file;
	}

	/**
	 * This class allows to sort the XMI ID of objects.
	 */
	public class XMIIDSorter implements Comparator<EObject> {

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(final EObject o1, final EObject o2) {
			final XMIResource res1 = (XMIResource) o1.eResource();
			final XMIResource res2 = (XMIResource) o2.eResource();
			final String id1 = res1.getID(o1);
			final String id2 = res2.getID(o2);
			return id1.compareTo(id2);
		}

	}
}
