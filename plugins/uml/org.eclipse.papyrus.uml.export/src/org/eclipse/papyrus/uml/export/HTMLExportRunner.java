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
 *  Benoit Maggi (CEA LIST) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.uml.export.tree.UMLTreeFactory;
import org.eclipse.papyrus.uml.export.util.FileUtil;
import org.eclipse.papyrus.uml.export.util.IconHelper;
import org.eclipse.papyrus.uml.export.util.ImageUtil;
import org.eclipse.papyrus.uml.export.util.IndexHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * The Class HTMLExportRunner.
 */
public class HTMLExportRunner {

	/** The Constant ICONS_DIRECTORY. */
	private static final String ICONS_DIRECTORY = "icons";
	
	/**
	 * Generate html web site.
	 *
	 * @param targetDirectoryPath the target directory path
	 * @param allDiagrams the all diagrams
	 * @param helper the helper
	 * @return the i path
	 */
	public static IPath generateHtmlWebSite(Path targetDirectoryPath, List<Diagram> allDiagrams, HTMLExportHelper helper) {

		// Generate all svg files
		for (Diagram diagram : allDiagrams) {
			ImageUtil.reFactoredgenerateImg(targetDirectoryPath, diagram, helper.getAnnotations());
		}

		// generate json tree of diagrams
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		UMLTreeFactory diagramExplorer = new UMLTreeFactory(helper.getAdditionnalDatas());
		for (Diagram diagram : allDiagrams) {
			diagramExplorer.addDiagram(diagram);
		}
		
		IPath append = targetDirectoryPath.append("index.html");
		File file = append.toFile();
		try {
		    BufferedWriter out = new BufferedWriter(new FileWriter(file));
		    IndexHelper indexHelper = new IndexHelper(helper.getHeaders(),helper.getFooters());
		    out.write(indexHelper.getIndex(gson.toJsonTree(diagramExplorer.getTree()).toString()));
		    out.close();
		}
		catch (IOException e)
		{
			Activator.log(e);
		}

		for (Entry<String, String> entry : helper.getFromTO().entrySet()) {
			FileUtil.copyFileFromPlatform(targetDirectoryPath,entry.getValue(), entry.getKey());
		}
		
		IPath iconDirPath = targetDirectoryPath.append(ICONS_DIRECTORY);
		File iconDir = iconDirPath.toFile();
		if (!iconDir.exists()) {
			iconDir.mkdirs();
		}

		IconHelper.printIcon(iconDirPath);			
		
		return iconDirPath;
	}
	
}
