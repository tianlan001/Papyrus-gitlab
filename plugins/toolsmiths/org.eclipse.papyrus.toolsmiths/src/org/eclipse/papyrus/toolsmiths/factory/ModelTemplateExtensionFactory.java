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
import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IPluginEditor;
import org.eclipse.papyrus.toolsmiths.messages.Messages;
import org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizableElement;
import org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizationPluginPackage;
import org.eclipse.papyrus.toolsmiths.model.customizationplugin.FileBasedCustomizableElement;
import org.eclipse.papyrus.toolsmiths.model.customizationplugin.ModelTemplate;
import org.w3c.dom.Element;


public class ModelTemplateExtensionFactory extends FileBasedExtensionFactory {
	
	public static final String MODEL_TEMPLATE_ROOT_FOLDER = new String("modelTemplate"); 
	

	public ModelTemplateExtensionFactory() {
		super(Messages.ModelTemplateExtensionFactory_ModelTemplate, "org.eclipse.papyrus.uml.diagram.wizards.templates", "file", "template", true); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	@Override
	public void addElement(CustomizableElement element, IPluginEditor editor) {
		super.addElement(element, editor);

		editor.addDependency("org.eclipse.papyrus.uml.diagram.wizards"); //$NON-NLS-1$
	}

	@Override
	protected Element createExtension(FileBasedCustomizableElement customizableElement, IPluginEditor editor) {
		Element extension = super.createExtension(customizableElement, editor);
		if (customizableElement instanceof ModelTemplate) {
			ModelTemplate element = (ModelTemplate) customizableElement;

			extension.setAttribute("id", element.getId()); //$NON-NLS-1$

			if (element.getName() != null) {
				extension.setAttribute("name", element.getName()); //$NON-NLS-1$
			}

			if (element.getLanguage() != null) {
				extension.setAttribute("language", element.getLanguage()); //$NON-NLS-1$
			}
			
			if (element.getLanguage() != null) {
				extension.setAttribute("di_file", getFilePath(element.getDi_file())); //$NON-NLS-1$
			}
			if (element.getLanguage() != null) {
				extension.setAttribute("notation_file", getFilePath(element.getNotation_file())); //$NON-NLS-1$
			}          
		    
		}

		return extension;
	}

	@Override
	protected String getTargetPath(FileBasedCustomizableElement element) {
		return getFilePath(element.getFile()); //$NON-NLS-1$
	}

	protected String getFilePath(String file) {
		return File.separator+MODEL_TEMPLATE_ROOT_FOLDER+File.separator + getFileName(file);
	}
	
	public EClass getCustomizableElementClass() {
		return CustomizationPluginPackage.eINSTANCE.getModelTemplate();
	}
	
	protected void copyFile(FileBasedCustomizableElement element, IPluginEditor editor) throws FileNotFoundException, IOException {
		copyFile(element.getFile(), getFilePath(((ModelTemplate)element).getFile()), editor);
		copyFile(((ModelTemplate)element).getDi_file(), getFilePath(((ModelTemplate)element).getDi_file()), editor);
		copyFile(((ModelTemplate)element).getNotation_file(), getFilePath(((ModelTemplate)element).getNotation_file()), editor);
	}
}
