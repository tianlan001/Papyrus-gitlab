/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
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
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IPluginEditor;
import org.eclipse.papyrus.infra.widgets.util.FileUtil;
import org.eclipse.papyrus.toolsmiths.Activator;
import org.eclipse.papyrus.toolsmiths.messages.Messages;
import org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizableElement;
import org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizationPluginPackage;
import org.eclipse.papyrus.toolsmiths.model.customizationplugin.FileBasedCustomizableElement;
import org.eclipse.papyrus.toolsmiths.model.customizationplugin.Profile;
import org.w3c.dom.Element;


public class ProfileExtensionFactory extends FileBasedExtensionFactory {

	public ProfileExtensionFactory() {
		super(Messages.ProfileExtensionFactory_Profile, "org.eclipse.papyrus.uml.extensionpoints.UMLProfile", "path", "profile", true); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	@Override
	public void addElement(CustomizableElement element, IPluginEditor editor) {
		super.addElement(element, editor);

		editor.addDependency("org.eclipse.papyrus.uml.extensionpoints"); //$NON-NLS-1$
	}

	@Override
	protected Element createExtension(FileBasedCustomizableElement element, IPluginEditor editor) {
		Element extension = super.createExtension(element, editor);
		Profile profile = (Profile) element;

		extension.setAttribute("name", profile.getName()); //$NON-NLS-1$

		if (profile.getDescription() != null) {
			extension.setAttribute("description", profile.getDescription()); //$NON-NLS-1$
		}

		if (profile.getIconpath() != null && !profile.getIconpath().trim().equals("")) {
			// extension.setAttribute("iconpath", profile.getIconpath()); //$NON-NLS-1$
			copyIcon(profile.getIconpath(), editor);
			extension.setAttribute("iconpath", getIconPath(profile.getIconpath())); //$NON-NLS-1$
		}

		if (profile.getProvider() != null) {
			extension.setAttribute("provider", profile.getProvider()); //$NON-NLS-1$
		}

		return extension;
	}

	protected void copyIcon(String path, IPluginEditor editor) {
		File sourceFile = FileUtil.getFile(path);
		File targetFile = FileUtil.getWorkspaceFile("/" + editor.getProject().getName() + "/" + getIconPath(path)); //$NON-NLS-1$ //$NON-NLS-2$
		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
		}

		try {
			copy(new FileInputStream(sourceFile), targetFile);
		} catch (IOException ex) {
			Activator.log.error(ex);
		}

		editor.addToBuild("icons/"); //$NON-NLS-1$
	}

	protected String getIconPath(String path) {
		return "icons/" + getFileName(path); //$NON-NLS-1$
	}

	@Override
	protected String getFileName(String path) {
		String fileName;
		path = path.replace("\\", "/");
		if (path.indexOf("/") < 0) { //$NON-NLS-1$
			fileName = path;
		} else {
			fileName = path.substring(path.lastIndexOf("/") + 1, path.length()); //$NON-NLS-1$
		}
		return fileName;
	}

	@Override
	protected String getTargetPath(FileBasedCustomizableElement element) {
		return "/umlProfile/" + getFileName(element); //$NON-NLS-1$
	}

	public EClass getCustomizableElementClass() {
		return CustomizationPluginPackage.eINSTANCE.getProfile();
	}

}
