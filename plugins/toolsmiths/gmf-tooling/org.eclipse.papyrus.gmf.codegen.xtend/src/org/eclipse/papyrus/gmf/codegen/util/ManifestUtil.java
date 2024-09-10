/******************************************************************************
 * Copyright (c) 2005, 2020 Borland Software Corporation, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Artem Tikhomirov (Borland) - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.codegen.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

/**
 * @author artem
 */
public class ManifestUtil {

	private static final String defaultManifestContent = "" //
			+ "Manifest-Version: 1.0\n" //
			+ "Bundle-ManifestVersion: 2\n" //
			+ "Bundle-Version: 2.9.0.qualifier\n" //
			+ "Bundle-Activator: org.eclipse.papyrus.gmf.codegen.util.Activator\n" //
			+ "Bundle-RequiredExecutionEnvironment: JavaSE-1.6\n" //
			+ "Require-Bundle: org.eclipse.papyrus.gmf.codegen"; //

	public static void createOrFillManifest(IProject project) {
		try {
			IFile manifest = project.getFile("META-INF/MANIFEST.MF");
			if (!manifest.exists()) {
				createManifest(manifest);
				return;
			} else {
				BufferedReader manifestStream = new BufferedReader(new InputStreamReader(manifest.getContents(), manifest.getCharset()));
				StringBuilder manifestContent = checkRequiredBundles(manifestStream);
				manifest.setContents(new ByteArrayInputStream(manifestContent.toString().getBytes(manifest.getCharset())), true, false, new NullProgressMonitor());
			}
		} catch (CoreException e) {
			throw new RuntimeException("Cannot create or read mainfest file in " + project.getName(), e);
		} catch (Exception ex) {
			throw new RuntimeException("Can't get project " + project.getName() + " ready to be started as bundle", ex);
		}
	}

	public static void createManifest(IFile file) throws CoreException {
		StringBuilder manifestContent = new StringBuilder(defaultManifestContent);
		String projectName = file.getProject().getName();
		manifestContent.append("Bundle-Name: " + projectName + "\n");
		manifestContent.append("Bundle-SymbolicName: " + projectName + "\n");
		manifestContent.append("Bundle-ClassPath: bin/, .\n");
		manifestContent.append("Bundle-Activator: org.eclipse.papyrus.gmf.codegen.util.DefaultActivator\n");
		InputStream manifestInputStream = new ByteArrayInputStream(manifestContent.toString().getBytes());
		file.create(manifestInputStream, false, null);
	}

	public static StringBuilder checkRequiredBundles(BufferedReader manifestStream) throws IOException {
		StringBuilder result = new StringBuilder();
		String line;
		boolean foundClassPath = false;
		boolean foundActivator = false;
		while ((line = manifestStream.readLine()) != null) {
			result.append(line);
			if (!foundClassPath && line.startsWith("Bundle-ClassPath:")) {
					if (!Arrays.asList(line.split(",\\s*")).contains("bin/")) {
						result.append(", bin/");	
					}				
				foundClassPath = true;
			}
			if (!foundActivator && line.contains("Bundle-Activator:")) {
				foundActivator = true;
			}
			result.append("\n");
		}
		if (!foundClassPath) {
			result.insert(0, "Bundle-ClassPath: bin/, .\n");
		}
		if (!foundActivator) {
			result.insert(0, "Bundle-Activator: org.eclipse.papyrus.gmf.codegen.util.DefaultActivator\n");
		}
		return result;
	}
}