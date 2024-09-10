/*****************************************************************************
 * Copyright (c) 2011, 2016 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 485220
 *  
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.papyrus.eclipse.project.editors.interfaces.IPluginEditor;
import org.eclipse.papyrus.infra.widgets.util.FileUtil;
import org.eclipse.papyrus.toolsmiths.Activator;
import org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizableElement;
import org.eclipse.papyrus.toolsmiths.model.customizationplugin.FileBasedCustomizableElement;
import org.w3c.dom.Element;


public abstract class FileBasedExtensionFactory implements ExtensionFactory {

	protected final String extensionPoint;

	protected final String fileAttributeName;

	protected final String fileElementName;

	protected final boolean allowMultiple;

	protected final String name;

	public FileBasedExtensionFactory(String name, String extensionPoint, String fileAttributeName, String fileElementName, boolean allowMultiple) {
		this.extensionPoint = extensionPoint;
		this.fileAttributeName = fileAttributeName;
		this.fileElementName = fileElementName;
		this.allowMultiple = allowMultiple;
		this.name = name;
	}

	public void addElement(CustomizableElement element, IPluginEditor editor) {
		createExtension((FileBasedCustomizableElement) element, editor);
		try {
			copyFile((FileBasedCustomizableElement) element, editor);
		} catch (IOException ex) {
			Activator.log.error(ex);
		}
	}

	protected Element createExtension(FileBasedCustomizableElement element, IPluginEditor editor) {
		Element extensionElement = null;
		if (element instanceof FileBasedCustomizableElement) {
			Element extension = editor.addExtension(extensionPoint);
			extensionElement = editor.addChild(extension, fileElementName);
			editor.setAttribute(extensionElement, fileAttributeName, getTargetPath(element));
		}
		return extensionElement;
	}

	protected void copyFile(FileBasedCustomizableElement element, IPluginEditor editor) throws FileNotFoundException, IOException {
		copyFile(element.getFile(), getTargetPath(element), editor);
	}

	protected void copyFile(String sourcePath, String targetPath, IPluginEditor editor) throws FileNotFoundException, IOException {
		File sourceFile = FileUtil.getFile(sourcePath);
		File targetFile = FileUtil.getWorkspaceFile("/" + editor.getProject().getName() + "/" + targetPath); //$NON-NLS-1$ //$NON-NLS-2$

		if (sourceFile == null) {
			throw new IllegalArgumentException("The source path " + sourcePath + " is not valid");
		}

		if (targetFile == null) {
			throw new IllegalArgumentException("The target path " + targetPath + " is not valid");
		}

		if (!targetFile.getParentFile().exists()) {
			targetFile.getParentFile().mkdirs();
		}

		copy(new FileInputStream(sourceFile), targetFile);

		editor.addToBuild(targetPath);
	}

	protected String getTargetPath(FileBasedCustomizableElement element) {
		return "/" + getFileName(element); //$NON-NLS-1$
	}

	protected String getFileName(FileBasedCustomizableElement element) {
		return getFileName(element.getFile());
	}

	protected String getFileName(String path) {
		if (path == null) {
			throw new IllegalArgumentException("File path should not be null");
		}
		String fileName;
		path = path.replace("\\", "/");
		if (path.indexOf("/") < 0) { //$NON-NLS-1$
			fileName = path;
		} else {
			fileName = path.substring(path.lastIndexOf("/") + 1, path.length()); //$NON-NLS-1$
		}
		return fileName;
	}

	protected void copy(InputStream source, File target) throws IOException {
		if (!target.getParentFile().exists()) {
			target.getParentFile().mkdirs();
		}

		OutputStream out = new FileOutputStream(target);
		try {
			int c;

			while ((c = source.read()) != -1) {
				out.write(c);
			}

		} catch (IOException ex) {
			throw ex;
		} finally {
			source.close();
			out.close();
		}
	}

	public boolean isValidElement(CustomizableElement element) {
		return element instanceof FileBasedCustomizableElement;
	}

	public String getName() {
		return name;
	}

}
