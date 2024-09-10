/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
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
import org.eclipse.papyrus.toolsmiths.model.customizationplugin.TableConfiguration;
import org.w3c.dom.Element;


/**
 * Toolsmiths DSML Configurator Extension to manage the Table configuration file
 * @author Francois Le Fevre
 *
 */
public class TableExtensionFactory extends FileBasedExtensionFactory {
	
	/**
	 * the folder name where will be gather all tabel configuration files
	 */
	public static final String TABLE_ROOT_FOLDER = new String("table");  //$NON-NLS-1$
	

	public TableExtensionFactory() {
		super(Messages.TableFactory_Table, "org.eclipse.papyrus.infra.nattable.configuration", "file", "configuration", true); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	@Override
	public void addElement(CustomizableElement element, IPluginEditor editor) {
		super.addElement(element, editor);

		editor.addDependency("org.eclipse.papyrus.infra.nattable"); //$NON-NLS-1$
	}

	@Override
	protected Element createExtension(FileBasedCustomizableElement customizableElement, IPluginEditor editor) {
		Element extension = super.createExtension(customizableElement, editor);
		if (customizableElement instanceof TableConfiguration) {
			TableConfiguration element = (TableConfiguration) customizableElement;

			if (element.getType() != null) {
				extension.setAttribute("type", element.getType()); //$NON-NLS-1$
			}
		    
		}

		return extension;
	}

	@Override
	protected String getTargetPath(FileBasedCustomizableElement element) {
		return getFilePath(element.getFile()); //$NON-NLS-1$
	}
	
	/**
	 * Return the full file path where will be written the tabel configuration
	 * @param file the file name
	 * @return
	 */
	protected String getFilePath(String file) {
		return File.separator+TABLE_ROOT_FOLDER+File.separator + getFileName(file);
	}
	
	public EClass getCustomizableElementClass() {
		return CustomizationPluginPackage.eINSTANCE.getTableConfiguration();
	}
	
	@Override
	protected void copyFile(FileBasedCustomizableElement element, IPluginEditor editor) throws FileNotFoundException, IOException {
		copyFile(element.getFile(), getFilePath(((TableConfiguration)element).getFile()), editor);
	}
}
