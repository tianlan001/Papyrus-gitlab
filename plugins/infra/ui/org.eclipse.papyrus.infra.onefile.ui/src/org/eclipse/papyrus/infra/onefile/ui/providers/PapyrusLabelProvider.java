/*****************************************************************************
 * Copyright (c) 2011, 2016 Atos Origin Integration, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Tristan Faure (Atos Origin Integration) tristan.faure@atosorigin.com - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.onefile.ui.providers;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.papyrus.infra.onefile.model.IPapyrusFile;
import org.eclipse.papyrus.infra.onefile.model.ISubResourceFile;
import org.eclipse.papyrus.infra.ui.util.PapyrusImageUtils;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

/**
 * Label Provider for Papyrus Model Elements
 *
 * @author tristan.faure@atosorigin.com
 *
 */
public class PapyrusLabelProvider implements ILabelProvider {

	private ImageRegistry images = new ImageRegistry(JFaceResources.getResources());

	public void addListener(ILabelProviderListener listener) {
	}

	public void dispose() {
		images.dispose();
	}

	public boolean isLabelProperty(Object element, String property) {
		return true;
	}

	public void removeListener(ILabelProviderListener listener) {
	}

	public Image getImage(Object element) {
		if (element instanceof IPapyrusFile) {
			return PapyrusImageUtils.getDefaultIcon();
		}
		if (element instanceof ISubResourceFile) {
			IFile file = ((ISubResourceFile) element).getFile();
			String ext = file.getFileExtension();
			Image image = images.get(ext);
			if (image == null) {
				ImageDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getImageDescriptor(file.getName());
				images.put(ext, desc);
			}
			return images.get(ext);
		}
		return null;
	}

	public String getText(Object element) {
		if (element instanceof IPapyrusFile) {
			IPapyrusFile papyFile = (IPapyrusFile) element;
			return papyFile.getText();
		}
		if (element instanceof ISubResourceFile) {
			return ((ISubResourceFile) element).getText();
		}
		if (element instanceof IResource) {
			return ((IResource) element).getName();
		}
		return null;
	}

}
