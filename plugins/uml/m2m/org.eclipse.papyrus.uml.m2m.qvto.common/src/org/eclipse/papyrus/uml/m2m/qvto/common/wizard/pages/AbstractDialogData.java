/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Quentin Le Menez (CEA LIST) quentin.lemenez@cea.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 496176
 *****************************************************************************/

package org.eclipse.papyrus.uml.m2m.qvto.common.wizard.pages;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.papyrus.infra.widgets.util.FileUtil;
import org.eclipse.papyrus.uml.m2m.qvto.common.Activator;
import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MigrationParametersFactory;
import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.ThreadConfig;
import org.eclipse.papyrus.uml.m2m.qvto.common.wizard.TransformationWizardConstants;



/**
 * 
 * Class containing the bulk of the wizard's data for an easy access throughout the wizard
 * 
 * @author Quentin Le Menez
 *
 */
public abstract class AbstractDialogData {

	protected Collection<Object> selectedFiles;

	protected Collection<Object> systemSelectedFiles;

	protected Collection<Object> transformationFiles;

	protected ThreadConfig config = MigrationParametersFactory.eINSTANCE.createThreadConfig();

	protected Collection<String> unselectedFiles;

	public IDialogSettings dialogSection;

	protected Collection<Object> uncheckedFiles;

	protected Collection<Object> importedProjects;

	/**
	 * 
	 * Constructor, instantiate the configuration file to store the user's selections, if none exist, or retrieve it
	 *
	 */
	public AbstractDialogData() {
		// Necessary for storing and recalling the previous selections
		dialogSection = Activator.getDefault().getDialogSettings().getSection(TransformationWizardConstants.TRANSFORMATION_WIZARD_SETTINGS);
		if (dialogSection == null) {
			dialogSection = Activator.getDefault().getDialogSettings().addNewSection(TransformationWizardConstants.TRANSFORMATION_WIZARD_SETTINGS);
		}

		systemSelectedFiles = new LinkedList<Object>();
	}

	/**
	 * 
	 * @param selectedFiles
	 *            The selected files from the workspace to be displayed in the transformation page
	 */
	public void setSelectedFiles(Collection<Object> selectedFiles) {
		this.selectedFiles = selectedFiles;
	}

	/**
	 * 
	 * @param selectedFiles
	 *            The selected files outside of the workspace to be displayed in the transformation page
	 */
	public void setSystemSelectedFiles(Collection<String> systemPaths) {
		for (String filePath : systemPaths) {
			File file = new File(filePath);
			if (file != null && !systemSelectedFiles.contains(file)) {
				systemSelectedFiles.add(file);
			}
		}
	}

	/**
	 * 
	 * @param transformationFiles
	 *            The selected files in the configuration page to be transformed
	 */
	public void setTransformationFiles(Collection<Object> transformationFiles) {
		this.transformationFiles = transformationFiles;
	}

	/**
	 * 
	 * @return
	 *         The default extensions used to filter the workspace
	 */
	public abstract String[] getExtensions();

	/**
	 * 
	 * @return
	 *         The default extensions' name used to filter the workspace
	 */
	public abstract String[] getExtensionsNames(); 

	/**
	 * 
	 * @return
	 *         The selected files from the workspace
	 */
	public Collection<Object> getSelectedFiles() {
		return selectedFiles;
	}

	/**
	 * 
	 * @return
	 *         The selected files outside of the workspace
	 */
	public Collection<Object> getSystemSelectedFiles() {
		return systemSelectedFiles;
	}

	/**
	 * 
	 * @return
	 *         The selected files from both workspace and outside selections
	 */
	public Collection<Object> getAllSelectedFiles() {
		Collection<Object> allSelectedFiles = new LinkedList<Object>();
		if (selectedFiles != null && selectedFiles.size() > 0) {
			// TODO select only the files that are not already present in the selection done outside of the workspace
			// Test
			// for (Object object : selectedFiles) {
			// if (!systemSelectedFiles.contains(object)) {
			// allSelectedFiles.add(object);
			// }
			// }
			// endTest
			allSelectedFiles.addAll(selectedFiles);
		}
		// if (systemSelectedFiles != null && systemSelectedFiles.size() > 0) {
		// allSelectedFiles.addAll(systemSelectedFiles);
		// }
		return allSelectedFiles;
	}

	/**
	 * 
	 * @return
	 *         The parameters used for the transformation
	 */
	public ThreadConfig getConfig() {
		return config;
	}

	/**
	 * 
	 * @return
	 *         The selected files to be transformed
	 */
	public Collection<Object> getTransformationFiles() {
		return transformationFiles;
	}

	/**
	 * 
	 * @param uncheckedFiles
	 *            The selected files to be ignored during the transformation
	 */
	public void setUncheckedFiles(Collection<Object> uncheckedFiles) {
		this.uncheckedFiles = uncheckedFiles;
	}

	/**
	 * 
	 * Sets or updates the projects to be imported in the workspace
	 * 
	 * @param importedProjects
	 *            The selcted projects
	 */
	public void setImportedProjects(Collection<Object> importedProjects) {
		this.importedProjects = importedProjects;
	}

	/**
	 * 
	 * @return
	 *         The imported projects
	 */
	public Collection<Object> getImportedProjects() {
		return this.importedProjects;
	}

	/**
	 * 
	 * Update or create the selection map stored inside the configuration file in order to remember the previous selection choices
	 * 
	 */
	public void setSelectionMap() {
		// Retrieve or create the list of unselected elements
		if (getUnSelectionArray() != null) {
			unselectedFiles = new LinkedList<String>(Arrays.asList(getUnSelectionArray()));
		}
		else {
			unselectedFiles = new LinkedList<String>();
		}
		// Updates the unselected files for future references
		for (Object object : uncheckedFiles) {
			if (object instanceof IFile) {
				IFile ifile = (IFile) object;
				String ifilePath = FileUtil.getPath(ifile, true);
				if (!unselectedFiles.contains(ifilePath)) {
					unselectedFiles.add(ifilePath);
				}
			}
			if (object instanceof File) {
				File file = (File) object;
				String filePath = file.getAbsolutePath();
				if (!unselectedFiles.contains(filePath)) {
					unselectedFiles.add(filePath);
				}
			}
		}
		// Remove any newly selected files from the unselected files pool
		for (Object object : transformationFiles) {
			if (object instanceof IFile) {
				IFile ifile = (IFile) object;
				String ifilePath = FileUtil.getPath(ifile, true);
				if (unselectedFiles.contains(ifilePath)) {
					unselectedFiles.remove(ifilePath);
				}
			}
			if (object instanceof File) {
				File file = (File) object;
				String filePath = file.getAbsolutePath();
				if (unselectedFiles.contains(filePath)) {
					unselectedFiles.remove(filePath);
				}
			}
		}

		// Update the map
		dialogSection.put(TransformationWizardConstants.WIZARD_SELECTION_KEY, unselectedFiles.toArray(new String[unselectedFiles.size()]));
	}

	/**
	 * 
	 * Used to set the checked state of the selected files inside the viewer
	 * 
	 * @return
	 *         The user's previously unchecked files in the configPage
	 */
	public String[] getUnSelectionArray() {
		return dialogSection.getArray(TransformationWizardConstants.WIZARD_SELECTION_KEY);
	}

}
