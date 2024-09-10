/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
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
 *  Onder Gurcan (Onder.Gurcan@cea.fr) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.profile.newchildmenu.generator.ui.handlers;

import java.net.URI;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.uml.profile.newchildmenu.generator.NewChildMenuGenerator;
import org.eclipse.ui.handlers.HandlerUtil;


public class GenerateNewChildMenuHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sselection = (IStructuredSelection) selection;
			if (!sselection.isEmpty()) {
				Object selectedElement = sselection.getFirstElement();
				if (selectedElement instanceof IFile) {
					IFile selectedFile = (IFile) selectedElement;
					String fileExtension = selectedFile.getFileExtension();
					if (fileExtension.equals("elementtypesconfigurations")) {
						URI uri = selectedFile.getLocationURI();
						String filePath = uri.toString();
						IContainer parentFolder = selectedFile.getParent();
						URI uri2 = parentFolder.getLocationURI();
						String folderPath = uri2.toString();
						new NewChildMenuGenerator().generate(folderPath, filePath);
					}
				}
			}
		}
		return null;
	}

}
