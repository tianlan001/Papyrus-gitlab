/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi  benoit.maggi@cea.fr - Initial API and implementation
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.onefile.internal.ui.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.core.resource.ModelMultiException;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.sasheditor.DiModel;
import org.eclipse.papyrus.infra.core.resource.sasheditor.SashModel;
import org.eclipse.papyrus.infra.core.resource.sasheditor.SashModelUtils;
import org.eclipse.papyrus.infra.core.utils.DiResourceSet;
import org.eclipse.papyrus.infra.emf.resource.DependencyManagementHelper;
import org.eclipse.papyrus.infra.emf.resource.MoveFileURIReplacementStrategy;
import org.eclipse.papyrus.infra.emf.resource.RestoreDependencyHelper;
import org.eclipse.papyrus.infra.emf.utils.ResourceUtils;
import org.eclipse.papyrus.infra.internationalization.utils.PropertiesFilesUtils;
import org.eclipse.papyrus.infra.onefile.internal.ui.Activator;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.undo.CopyResourcesOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;



/**
 * Implementation of Papyrus paste for model
 */
public class PapyrusCopyFilesAndFoldersOperation extends CopyFilesAndFoldersOperation {

	protected Map<String, String> renameMapping = new HashMap<String, String>();

	protected IPath[] destinationPaths = null;

	public PapyrusCopyFilesAndFoldersOperation(Shell shell) {
		super(shell);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.papyrus.infra.onefile.action.CopyFilesAndFoldersOperation#performCopyWithAutoRename(org.eclipse.core.resources.IResource[],
	 * org.eclipse.core.runtime.IPath, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	// perform rename only for .di
	protected boolean performCopyWithAutoRename(IResource[] resources, IPath destination, IProgressMonitor monitor) {
		IWorkspace workspace = resources[0].getWorkspace();
		destinationPaths = new IPath[resources.length];
		try {
			String oldName = ""; //$NON-NLS-1$
			String newName = ""; //$NON-NLS-1$
			for (int i = 0; i < resources.length; i++) {
				IResource source = resources[i];
				destinationPaths[i] = destination.append(source.getName());
				IPath relativSourcePath = source.getFullPath();
				String sourceFileName = relativSourcePath.removeFileExtension().lastSegment();
				if (!oldName.isEmpty() && sourceFileName.startsWith(oldName)) {
					destinationPaths[i] = getDestinationPath(relativSourcePath, newName, oldName);
				} else {
					oldName = sourceFileName;
					if (workspace.getRoot().exists(destinationPaths[i]) && destinationPaths[i].getFileExtension().equals(DiModel.MODEL_FILE_EXTENSION)) {
						destinationPaths[i] = getNewNameFor(destinationPaths[i], workspace);
						newName = destinationPaths[i].removeFileExtension().lastSegment();
					}
					renameMapping.put(oldName, newName);
				}

			}
			CopyResourcesOperation op = new CopyResourcesOperation(resources, destinationPaths, IDEWorkbenchMessages.CopyFilesAndFoldersOperation_copyTitle);
			op.setModelProviderIds(getModelProviderIds());
			PlatformUI.getWorkbench().getOperationSupport().getOperationHistory().execute(op, monitor, WorkspaceUndoUtil.getUIInfoAdapter(messageShell));
		} catch (ExecutionException e) {
			if (e.getCause() instanceof CoreException) {
				recordError((CoreException) e.getCause());
			} else {
				IDEWorkbenchPlugin.log(e.getMessage(), e);
				displayError(e.getMessage());
			}
			return false;
		}
		return true;
	}

	/**
	 * Get the new destination path for the current relative source path.
	 * 
	 * @param relativeSourcePath
	 *            The relative source path.
	 * @param newName
	 *            The new name to set.
	 * @param oldName
	 *            The old name.
	 * @return The destination path.
	 */
	public IPath getDestinationPath(final IPath relativeSourcePath, final String newName, final String oldName) {
		final String fileExtension = relativeSourcePath.getFileExtension();
		// Get the source file name
		final String sourceFileName = relativeSourcePath.removeFileExtension().lastSegment();
		// Replace the oldName by the newName
		final String destinationFileName = sourceFileName.replace(oldName, newName);

		// Retrieve the last segment and append the new one with the file extension
		return relativeSourcePath.removeLastSegments(1).append(destinationFileName).addFileExtension(fileExtension);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.papyrus.infra.onefile.action.CopyFilesAndFoldersOperation#performCopy(org.eclipse.core.resources.IResource[],
	 * org.eclipse.core.runtime.IPath, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	// store path of created files
	protected boolean performCopy(IResource[] resources, IPath destination, IProgressMonitor monitor) {
		boolean performCopy = super.performCopy(resources, destination, monitor);
		if (performCopy) {
			destinationPaths = new IPath[resources.length];
			for (int i = 0; i < resources.length; i++) {
				String name = resources[i].getName();
				destinationPaths[i] = destination.append(name);
			}
		}
		return performCopy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.papyrus.infra.onefile.action.CopyFilesAndFoldersOperation#copyResources(org.eclipse.core.resources.IResource[],
	 * org.eclipse.core.resources.IContainer)
	 */
	@Override
	// restore internal and external link coherence
	public IResource[] copyResources(IResource[] resources, IContainer destination) {
		IResource[] copyResources = super.copyResources(resources, destination);
		try {
			List<ModelSet> modelSetList = initModelSet(copyResources);
			Map<URI, URI> constructInternalMapping = constructInternalMapping(copyResources);
			for (int i = 0; i < resources.length; i++) {
				for (ModelSet modelSet : modelSetList) {
					if (checkResource(modelSet, resources[i])) {
						restoreAllLink(modelSet, constructInternalMapping, copyResources[i], destinationPaths[i]);
					}
				}
			}
		} catch (IOException e) {
			Activator.log.error("It was not possible to restore broken links", e); //$NON-NLS-1$
		}
		return copyResources;
	}

	/**
	 * Init a modelSet with registred model from resources
	 *
	 * @param resources
	 * @return
	 */
	protected List<ModelSet> initModelSet(IResource[] resources) {
		List<ModelSet> modelSetList = new ArrayList<ModelSet>();
		for (IResource iResource : resources) {
			IPath fullPath = iResource.getFullPath();
			if (DiModel.MODEL_FILE_EXTENSION.equals(fullPath.getFileExtension())) {
				if (iResource instanceof IFile) {
					try {
						ModelSet modelSet = new DiResourceSet();
						modelSet.loadModels((IFile) iResource);
						modelSetList.add(modelSet);
					} catch (ModelMultiException e) {
						Activator.log.error("It was not possible to load models", e); //$NON-NLS-1$
					}
				}
			}
		}
		return modelSetList;
	}

	/**
	 * Check if the iResource is known by the ModelSet
	 *
	 * @param modelSet
	 * @param iResource
	 * @return
	 */
	protected boolean checkResource(ModelSet modelSet, IResource iResource) {
		URI uri = URI.createPlatformResourceURI(iResource.getFullPath().toString(), Boolean.TRUE);
		Resource resource = modelSet.getResource(uri, Boolean.FALSE);
		return resource != null;
	}

	/**
	 * Restore referenced URI following the pattern ;
	 * - if there is an accessible Resource use it
	 * - else search the resource in the source location of the copy
	 *
	 * Restore links to maintain coherence in the 3 files: uml-notation-di
	 *
	 * @param modelSet
	 * @param constructInternalMapping
	 * @param copyResources
	 * @param targetPath
	 * @throws IOException
	 */
	protected void restoreAllLink(ModelSet modelSet, Map<URI, URI> constructInternalMapping, IResource copyResources, IPath targetPath) throws IOException {
		URI uri = URI.createPlatformResourceURI(targetPath.toString(), Boolean.TRUE);
		Resource resource = modelSet.getResource(uri, Boolean.TRUE);

		// restore external links
		MoveFileURIReplacementStrategy iURIReplacementStrategy = new MoveFileURIReplacementStrategy(new HashMap<URI, URI>(), copyResources.getFullPath().removeLastSegments(1), targetPath.removeLastSegments(1));
		RestoreDependencyHelper restoreDependencyHelper = new RestoreDependencyHelper(iURIReplacementStrategy);
		restoreDependencyHelper.restoreDependencies(resource);

		// restore internal links
		for (Entry<URI, URI> oneInternalCopyMapping : constructInternalMapping.entrySet()) {
			DependencyManagementHelper.updateDependencies(oneInternalCopyMapping.getKey(), oneInternalCopyMapping.getValue(), resource);
		}
		// Don't save the resource properties, the model resource manage it at create model call
		if(!resource.getURI().fileExtension().equals(PropertiesFilesUtils.PROPERTIES_FILE_EXTENSION)){
			resource.save(ResourceUtils.getSaveOptions());
		}
		IPath fullPath = copyResources.getFullPath();
		Resource sashResource = null;

		// restore links for sash
		if (DiModel.MODEL_FILE_EXTENSION.equals(fullPath.getFileExtension())) {
			SashModel sashModel = SashModelUtils.getSashModel(modelSet);
			if (sashModel != null && !constructInternalMapping.containsKey(sashModel.getURI())) { // Kepler and earlier stored the sash model in the DI
				sashResource = sashModel.getResource();
				for (Entry<URI, URI> oneInternalCopyMapping : constructInternalMapping.entrySet()) {
					DependencyManagementHelper.updateDependencies(oneInternalCopyMapping.getKey(), oneInternalCopyMapping.getValue(), sashResource);
				}
			}
			if (sashResource != null) { // save new sash model
				ModelSet tempModelSet = new DiResourceSet();
				tempModelSet.createModels(uri);
				URI newsashModelURI = SashModelUtils.getSashModel(tempModelSet).getURI();
				sashResource.setURI(newsashModelURI);
				sashResource.save(ResourceUtils.getSaveOptions());
			}
		}
	}

	/**
	 * Construct an URI mapping from source to target
	 *
	 * @param copyResources
	 * @return
	 */
	protected Map<URI, URI> constructInternalMapping(IResource[] copyResources) {
		Map<URI, URI> internalCopyMapping = new HashMap<URI, URI>();
		for (int j = 0; j < copyResources.length; j++) {
			IPath targetPath = destinationPaths[j];
			IResource sourceResource = copyResources[j];
			URI targetURI = URI.createPlatformResourceURI(targetPath.toString(), true);
			URI sourceURI = URI.createPlatformResourceURI(sourceResource.getFullPath().toString(), true);
			internalCopyMapping.put(sourceURI, targetURI);
		}
		return internalCopyMapping;
	}
}
