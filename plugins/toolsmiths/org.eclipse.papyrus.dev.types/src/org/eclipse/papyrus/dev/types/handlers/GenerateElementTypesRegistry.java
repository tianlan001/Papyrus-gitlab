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
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.dev.types.handlers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.dev.types.generator.ElementTypeRegistryGenerator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class GenerateElementTypesRegistry extends AbstractHandler {


	public GenerateElementTypesRegistry() {
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		ResourceSet resourceSet = new ResourceSetImpl();
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		if (!(currentSelection instanceof IStructuredSelection) || currentSelection.isEmpty()) {
			return null;
		}

		final IStructuredSelection selection = (IStructuredSelection) currentSelection;
		Object selectedElement = selection.getFirstElement();

		if (selectedElement instanceof IFile) {

			String selectedFilePath = ((IFile) selectedElement).getFullPath().toString();
			String outputType = ((IFile) selectedElement).getFullPath().removeFileExtension().lastSegment();
			String outputFileName = outputType + ".java";

			ContainerSelectionDialog dialog = new ContainerSelectionDialog(Display.getCurrent().getActiveShell(), null, true, "Select a folder:");
			dialog.setTitle("Select output folder");
			dialog.open();
			if (dialog.getResult() != null && dialog.getResult().length > 0 && dialog.getResult()[0] instanceof IPath) {

				IWorkspace workspace = ResourcesPlugin.getWorkspace();
				IWorkspaceRoot root = workspace.getRoot();
				IResource container = root.findMember((IPath) dialog.getResult()[0]);

				if (container instanceof IFolder) {

					Resource inputResource = resourceSet.getResource(URI.createURI(selectedFilePath), true);
					CharSequence registryText = ElementTypeRegistryGenerator.generateRegistry(inputResource, outputType);


					IFile outputFile = ((IFolder) container).getFile(outputFileName);
					if (!outputFile.exists()) {

						InputStream source = new ByteArrayInputStream(registryText.toString().getBytes());
						try {
							outputFile.create(source, IResource.NONE, null);
						} catch (CoreException e) {
							e.printStackTrace();
						}
					} else {
						MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "Registry already exists", "No file generated: the registry file already exists.");
					}
				} else {
					MessageDialog.openWarning(Display.getCurrent().getActiveShell(), "Container selection", "Selection must be a folder");
				}
			}


		}
		return null;
	}
}
