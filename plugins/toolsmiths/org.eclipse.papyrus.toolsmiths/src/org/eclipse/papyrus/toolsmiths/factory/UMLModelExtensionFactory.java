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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IPluginEditor;
import org.eclipse.papyrus.toolsmiths.messages.Messages;
import org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizableElement;
import org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizationPluginPackage;
import org.eclipse.papyrus.toolsmiths.model.customizationplugin.FileBasedCustomizableElement;
import org.eclipse.papyrus.toolsmiths.model.customizationplugin.UMLModel;
import org.w3c.dom.Element;


public class UMLModelExtensionFactory extends FileBasedExtensionFactory {

	public UMLModelExtensionFactory() {
		super(Messages.UMLModelExtensionFactory_UMLModel, "org.eclipse.papyrus.uml.extensionpoints.UMLLibrary", "path", "library", false); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	@Override
	public void addElement(CustomizableElement element, IPluginEditor editor) {
		super.addElement(element, editor);

		editor.addDependency("org.eclipse.papyrus.uml.extensionpoints"); //$NON-NLS-1$
	}

	@Override
	protected Element createExtension(FileBasedCustomizableElement element, IPluginEditor editor) {
		Element extension = super.createExtension(element, editor);
		UMLModel model = (UMLModel) element;

		if (model.getDescription() != null) {
			extension.setAttribute("description", model.getDescription()); //$NON-NLS-1$
		}

		if (model.getIconpath() != null) {
			extension.setAttribute("iconpath", model.getIconpath()); //$NON-NLS-1$
		}

		if (model.getName() != null) {
			extension.setAttribute("name", model.getName()); //$NON-NLS-1$
		}

		if (model.getProvider() != null) {
			extension.setAttribute("provider", model.getProvider()); //$NON-NLS-1$
		}

		return extension;
	}

	@Override
	protected String getTargetPath(FileBasedCustomizableElement element) {
		return "/umlLibrary/" + getFileName(element); //$NON-NLS-1$
	}

	public EClass getCustomizableElementClass() {
		return CustomizationPluginPackage.eINSTANCE.getUMLModel();
	}
}
