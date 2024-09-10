/*****************************************************************************
 * Copyright (c) 2019, 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Bug 552101
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 571814
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.nattable.wizard;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.eclipse.project.editors.file.BuildEditor;
import org.eclipse.papyrus.eclipse.project.editors.file.ManifestEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.TableConfiguration;
import org.eclipse.papyrus.infra.nattable.provider.TableStructuredSelection;
import org.eclipse.papyrus.toolsmiths.nattable.Activator;
import org.eclipse.papyrus.toolsmiths.nattable.messages.Messages;
import org.eclipse.papyrus.toolsmiths.nattable.utils.TableChecker;
import org.eclipse.papyrus.toolsmiths.nattable.utils.TableConfigurationUtils;
import org.eclipse.papyrus.toolsmiths.nattable.utils.TableToTableConfigurationConverter;
import org.eclipse.papyrus.toolsmiths.nattable.wizard.pages.DefineOutputPluginWizardPage;
import org.eclipse.papyrus.toolsmiths.nattable.wizard.pages.DefineTableConfigurationDataWizardPage;
import org.eclipse.papyrus.toolsmiths.nattable.wizard.pages.WarningOnCurrentTableWizardPage;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;




/**
 * The wizard used to configure the transformation of a {@link Table} into a {@link TableConfiguration}
 */
public class ExportAsTableConfigurationWizard extends Wizard implements IExportWizard {

	private static final String ICON_FOLDER_PATH = "icons"; //$NON-NLS-1$

	private static final String GIF_FILE_EXTENSION = ".gif";//$NON-NLS-1$

	private static final String PNG_FILE_EXTENSION = ".png";//$NON-NLS-1$

	private static final String DEFAULT_TABLE_ICON = "icons/table.png";//$NON-NLS-1$

	private static final String PLUGIN_XML_FILE = "plugin.xml";//$NON-NLS-1$

	private static final String SLASH = "/";//$NON-NLS-1$

	/**
	 * The page defined the output of the export
	 */
	private DefineOutputPluginWizardPage outputPage;

	/**
	 * The page defining the datas (name, type, ...) to use for the new table
	 */
	private DefineTableConfigurationDataWizardPage tableDataPage;

	/**
	 * The table to export as table configuration
	 */
	private Table exportedTable;

	/**
	 *
	 * Constructor.
	 *
	 */
	public ExportAsTableConfigurationWizard() {
		setWindowTitle(Messages.ExportAsTableConfigurationWizard_WizardTitle);
	}

	/**
	 *
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 *
	 * @param workbench
	 * @param selection
	 */
	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		if (selection.getFirstElement() instanceof Table) {
			this.exportedTable = (Table) selection.getFirstElement();
		} else if (selection instanceof TableStructuredSelection) {
			final TableStructuredSelection tss = (TableStructuredSelection) selection;
			final INattableModelManager tableModelManager = (INattableModelManager) tss.getAdapter(INattableModelManager.class);
			if (null != tableModelManager) {
				this.exportedTable = tableModelManager.getTable();
			}
		}
		Assert.isNotNull(this.exportedTable, Messages.ExportAsTableConfigurationWizard_WeCantFoundTheTableToExport);

		IStatus status = TableChecker.checkTable(this.exportedTable);
		if (false == status.isOK()) {
			addPage(new WarningOnCurrentTableWizardPage(status));
		}


		this.outputPage = new DefineOutputPluginWizardPage();
		this.tableDataPage = new DefineTableConfigurationDataWizardPage();
		this.outputPage.setExportedTable(this.exportedTable);
		this.tableDataPage.setExportedTable(this.exportedTable);
		addPage(outputPage);
		addPage(tableDataPage);
	}

	/**
	 *
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 *
	 * @return
	 */
	@Override
	public boolean performFinish() {
		final IJavaProject outputJavaProject = this.outputPage.getOutputJavaProject();
		final String outputFolderName = this.outputPage.getExportFolderName();
		final String fileName = this.tableDataPage.getFileName();
		final String newTableName = tableDataPage.getNewTableDefaultName();
		final String newTableType = tableDataPage.getNewTableType();
		final String newTableDescription = this.tableDataPage.getNewTableConfigurationDescription();
		final IStatus status = createAndRegisterTableConfiguration(outputJavaProject, outputFolderName, fileName, newTableName, newTableType, newTableDescription, this.exportedTable);
		if (false == status.isOK()) {
			MessageDialog.openError(Display.getCurrent().getActiveShell(), Messages.ExportAsTableConfigurationWizard_ErrorDuringTableConfigurationCreation, status.getMessage());
		}
		// refresh workspace
		try {
			outputJavaProject.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (CoreException e) {
			Activator.log.error(e);
		}
		return true;
	}

	/**
	 *
	 * @see org.eclipse.jface.wizard.Wizard#canFinish()
	 *
	 * @return
	 */
	@Override
	public boolean canFinish() {
		// it is not required to check the warning page
		return this.outputPage.isPageComplete() && this.tableDataPage.isPageComplete();
	}

	/**
	 *
	 * @param message
	 * @return
	 */
	private static final IStatus createErrorStatus(final String message) {
		return new Status(IStatus.ERROR, Activator.PLUGIN_ID, message);
	}

	/**
	 *
	 * This method creates a new TableConfiguration file, register it via the required extension point, defines the default icon to use for it and configure Manifest file and build.properties file
	 *
	 * @param outputJavaProject
	 *            the name of the output java project
	 * @param outputFolderName
	 *            the name of the output folder for the table configuration
	 * @param outputFileName
	 *            the name of the table configuration file name
	 * @param newTableName
	 *            the name of the table
	 * @param newTableType
	 *            the type of the table
	 * @param newTableDescription
	 *            the table description
	 * @param tableToConvert
	 *            the table to convert as TableConfiguration
	 * @return
	 *         a IStatus indicating if all worked fine or not
	 */
	private IStatus createAndRegisterTableConfiguration(final IJavaProject outputJavaProject, final String outputFolderName, final String outputFileName,
			final String newTableName, final String newTableType, final String newTableDescription, final Table tableToConvert) {

		// 1. create the output table configuration folder
		final IFolder folder = outputJavaProject.getProject().getFolder(outputFolderName);
		if (!folder.exists()) {
			try {
				folder.create(true, true, null);
			} catch (final CoreException e) {
				Activator.log.error(e);
				return createErrorStatus(NLS.bind(Messages.ExportAsTableConfigurationWizard_OutputFolderCantBeCreateed, folder, outputJavaProject.getProject().getName()));
			}
		}

		// 2. create the output table icon folder
		final IFolder folderIcon = outputJavaProject.getProject().getFolder(ICON_FOLDER_PATH);
		if (!folderIcon.exists()) {
			try {
				folderIcon.create(true, true, null);
			} catch (final CoreException e) {
				Activator.log.error(e);
				return createErrorStatus(NLS.bind(Messages.ExportAsTableConfigurationWizard_IconFolderCantBeCreated, folderIcon.getName(), outputJavaProject.getProject().getName()));
			}
		}

		// 3. create the 2 possibles files (.gif or .png) for an existing icon file in the plugin
		final IFile imageFileGIF = folderIcon.getFile(newTableType + GIF_FILE_EXTENSION);
		final IFile imageFilePNG = folderIcon.getFile(newTableType + PNG_FILE_EXTENSION);


		// 3.1 create the newIconPath variable, (used later)
		final String newIconPath;
		if (imageFileGIF.exists()) {
			newIconPath = URI.createPlatformPluginURI(imageFileGIF.getFullPath().toPortableString(), true).toString();
		} else {
			newIconPath = URI.createPlatformPluginURI(imageFilePNG.getFullPath().toPortableString(), true).toString();
			// 3.2 we create the defaut table icon, using the PNG format.
			if (!imageFilePNG.exists()) {
				final URL fileURL = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getURL(Activator.PLUGIN_ID, DEFAULT_TABLE_ICON);
				String str = fileURL.getFile();

				// The difficulty is to manage the spaces and the accentuated char in the path of the installation to copy the image file to the expected output target

				// tested with windows and MacOS
				if (OperatingSystem.isWindows() && str.startsWith("/")) { //$NON-NLS-1$
					str = str.replaceFirst("/", ""); //$NON-NLS-1$ //$NON-NLS-2$
				}


				final Path path = Paths.get(str, ""); //$NON-NLS-1$

				// 3. copy the image
				if (null != path) {
					try {
						final OutputStream out = new FileOutputStream(imageFilePNG.getLocation().toString());
						Files.copy(path, out);
						out.close();
					} catch (final IOException e) {
						Activator.log.error(e);
						return createErrorStatus(Messages.ExportAsTableConfigurationWizard_TableIconFileCantBeSaved);
					}
				} else {
					return createErrorStatus(Messages.ExportAsTableConfigurationWizard_TableIconFileCantBeCreated);
				}
			}
		}


		// 4. we create a resource set
		final ResourceSet set = new ResourceSetImpl();
		final IPath path = folder.getFullPath();
		URI tableConfigurationURI = URI.createPlatformResourceURI(path.toPortableString(), true);

		tableConfigurationURI = tableConfigurationURI.appendSegment(outputFileName);
		final String fileExtension = org.eclipse.papyrus.internal.infra.nattable.model.resources.NattableConfigurationResource.NATTABLE_CONFIGURATION_RESOURCE_FILE_EXTENSION;
		tableConfigurationURI = tableConfigurationURI.appendFileExtension(fileExtension);

		// 5. create the resource, existing one, will be erase
		final Resource res = set.createResource(tableConfigurationURI);

		TableToTableConfigurationConverter converter = new TableToTableConfigurationConverter(tableToConvert, newTableName, newTableType, newIconPath, newTableDescription);
		TableConfiguration tc = converter.convertTable();
		res.getContents().add(tc);
		try {
			res.save(null);
		} catch (IOException e) {
			Activator.log.error(e);
			return new Status(IStatus.ERROR, Activator.PLUGIN_ID, NLS.bind(Messages.ExportAsTableConfigurationWizard_ResourceFileCantBeSaved, tableConfigurationURI.toFileString()));
		}

		// 6. register the TableConfiguration in the plugin.xml file
		TableConfigurationUtils.registerTableConfiguration(outputJavaProject, newTableType, tableConfigurationURI);

		// 7. add the required dependency into the MANIFEST.MF file
		ManifestEditor manifestEditor = null;
		try {
			manifestEditor = new ManifestEditor(outputJavaProject.getProject());
			manifestEditor.init();
		} catch (IOException | CoreException e) {
			Activator.log.error(e);
			return createErrorStatus(Messages.ExportAsTableConfigurationWizard_ManifestEditorCantRegisterDependencies);
		}
		manifestEditor.setSingleton(true);// required because we add an extension point

		final List<String> requiredDependencies = new ArrayList<>();
		requiredDependencies.add(org.eclipse.papyrus.infra.nattable.Activator.PLUGIN_ID);// required, because it provides the extension point
		for (final String dependency : requiredDependencies) {
			if (false == manifestEditor.hasDependency(dependency)) {
				manifestEditor.addDependency(dependency);
			}
		}
		manifestEditor.save();

		// 8. add the icons and the output folder to the binary build
		final BuildEditor buildEditor = new BuildEditor(outputJavaProject.getProject());
		buildEditor.init();
		buildEditor.addToBuild(outputFolderName + SLASH);
		buildEditor.addToBuild(ICON_FOLDER_PATH + SLASH);
		buildEditor.addToBuild(PLUGIN_XML_FILE + SLASH);
		buildEditor.save();

		// 9. we check the table created table configuration reloading it

		try {
			outputJavaProject.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		} catch (CoreException e) {
			Activator.log.error(e);
		}
		checkTableConfigurationDependencies(tableConfigurationURI);
		return Status.OK_STATUS;
	}

	/**
	 *
	 * This method checks the model associated to the TableConfiguration model.
	 * There are kind of possible href in the files :
	 * <ul>
	 * <li>1. : href="http://www.eclipse.org/uml2/5.0.0/UML#//ActivityNode/activity"/</li>
	 * <ul>
	 * <li>TODO : In this, case we don't found the dependency. The EcoreEditor seems not able to present associated file too, doing expand all on the model</li>
	 * </ul>
	 * <li>2. : href="platform:/plugin/org.eclipse.uml2.uml/model/UML.ecore#//ActivityNode/activity"/></li>
	 * <ul>
	 * <li>I don't yet enter in this case, I hope it should work</li>
	 * </ul>
	 * </ul>
	 *
	 * TODO : coded, used, but I did yet met the case 2 described upper.
	 *
	 * @param tableConfigurationURI
	 *            the uri of the new {@link TableConfiguration}
	 */
	private void checkTableConfigurationDependencies(final URI tableConfigurationURI) {
		final ResourceSet resSet = new ResourceSetImpl();
		final Resource secondLoad = resSet.getResource(tableConfigurationURI, true);
		EcoreUtil.resolveAll(secondLoad);

		if (resSet.getResources().size() > 1) {
			final StringBuilder builder = new StringBuilder();
			final Iterator<Resource> iter = resSet.getResources().iterator();
			while (iter.hasNext()) {
				final Resource current = iter.next();
				if (current != secondLoad) {
					if (current.getURI().isPlatformResource()) {
						// not yet coded, but we are sure to have to notify the user
					}
					if (current.getURI().isPlatformPlugin()) {
						final String fileExtension = current.getURI().fileExtension();
						if ("elementtypesconfigurations".equals(fileExtension)) {// allowed for matrix //$NON-NLS-1$
							// continue doWhileLoop;
						}
					}
					builder.append(current.getURI().toString());
					if (iter.hasNext()) {
						builder.append("\n"); //$NON-NLS-1$
					}
				}
			}
			if (builder.length() > 0) {
				final String message = NLS.bind(Messages.ExportAsTableConfigurationWizard_WarningCreatedTableConfigurationDependsOnSeveralOthersModel, builder.toString());
				MessageDialog.openWarning(Display.getDefault().getActiveShell(), Messages.ExportAsTableConfigurationWizard_CheckTableConfigurationDependencies, message);
			}
		}
	}


	private static class OperatingSystem {

		private static String OS = System.getProperty("os.name", "unknown").toLowerCase(Locale.ROOT); //$NON-NLS-1$ //$NON-NLS-2$

		public static boolean isWindows() {
			return OS.contains("win"); //$NON-NLS-1$
		}

		public static boolean isMac() {
			return OS.contains("mac"); //$NON-NLS-1$
		}

		public static boolean isUnix() {
			return OS.contains("nux"); //$NON-NLS-1$
		}
	}

}

