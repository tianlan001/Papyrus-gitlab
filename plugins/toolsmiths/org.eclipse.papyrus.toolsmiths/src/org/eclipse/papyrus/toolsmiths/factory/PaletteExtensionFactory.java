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
 *  Francois Le Fevre (CEA LIST) francois.le-fevre@cea.fr - Initial API and implementation
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
import org.eclipse.papyrus.toolsmiths.model.customizationplugin.Palette;
import org.w3c.dom.Element;


public class PaletteExtensionFactory extends FileBasedExtensionFactory {
	
	public static final String PALETTE_ROOT_FOLDER = new String("palette"); 
	

	public PaletteExtensionFactory() {
		super(Messages.PaletteFactory_Palette, "org.eclipse.papyrus.infra.gmfdiag.common.paletteDefinition", "path", "paletteDefinition", true); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	@Override
	public void addElement(CustomizableElement element, IPluginEditor editor) {
		super.addElement(element, editor);

		editor.addDependency("org.eclipse.papyrus.infra.gmfdiag.common"); //$NON-NLS-1$
	}

	@Override
	protected Element createExtension(FileBasedCustomizableElement customizableElement, IPluginEditor editor) {
		Element extension = super.createExtension(customizableElement, editor);
		if (customizableElement instanceof Palette) {
			Palette element = (Palette) customizableElement;

			extension.setAttribute("ID", element.getID()); //$NON-NLS-1$

			if (element.getName() != null) {
				extension.setAttribute("name", element.getName()); //$NON-NLS-1$
			}

			if (element.getClazz() != null) {
				extension.setAttribute("class", element.getClazz()); //$NON-NLS-1$
			}
			
			if (element.getProvider() != null) {
				extension.setAttribute("provider", element.getProvider()); //$NON-NLS-1$
			}
			if (element.getPriorityName() != null) {
				Element priority = extension.getOwnerDocument().createElement("Priority");//$NON-NLS-1$
				priority.setAttribute("name", element.getPriorityName());
				extension.appendChild(priority);
			}
			if (element.getProvider() != null) {
				Element editor2 = extension.getOwnerDocument().createElement("editor");//$NON-NLS-1$
				editor2.setAttribute("id", element.getEditorId());
				extension.appendChild(editor2);
			}  
		    
		}

		return extension;
	}

	@Override
	protected String getTargetPath(FileBasedCustomizableElement element) {
		return getFilePath(element.getFile()); //$NON-NLS-1$
	}

	protected String getFilePath(String file) {
		return File.separator+PALETTE_ROOT_FOLDER+File.separator + getFileName(file);
	}
	
	public EClass getCustomizableElementClass() {
		return CustomizationPluginPackage.eINSTANCE.getPalette();
	}
	
	protected void copyFile(FileBasedCustomizableElement element, IPluginEditor editor) throws FileNotFoundException, IOException {
		copyFile(element.getFile(), getFilePath(((Palette)element).getFile()), editor);
	}
}
