/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Quentin Le Menez (CEA LIST) quentin.lemenez@cea.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 496176
 *****************************************************************************/

package org.eclipse.papyrus.uml.m2m.qvto.common.internal.utils;

import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

//TODO : must be moved into org.eclipse.papyrus.eclipse.project.editors refactored
public class CreateFile {

	public static IFile createFile(String filePath, IContainer container, String content)
			throws Exception
	{
		IFile file = null;
		if ((container instanceof IProject)) {
			file = ((IProject) container).getFile(filePath);
		} else if ((container instanceof IFolder)) {
			file = ((IFolder) container).getFile(filePath);
		} else {
			throw new Exception("Cannot find the file " +
					filePath +
					" under " +
					container.getName());
		}
		IContainer parent = file.getParent();
		if ((parent instanceof IFolder)) {
			mkdirs((IFolder) parent);
		}
		InputStream stream = new ByteArrayInputStream(content.getBytes(file.getCharset()));
		if (file.exists()) {
			file.setContents(stream, true, true, null);
		} else {
			file.create(stream, true, null);
		}
		stream.close();

		return file;
	}

	public static void mkdirs(IFolder folder)
			throws CoreException
	{
		if (!folder.exists())
		{
			if ((folder.getParent() instanceof IFolder)) {
				mkdirs((IFolder) folder.getParent());
			}
			folder.create(true, true, null);
		}
	}

	public static String getContents(String relativePath, String resource, Class clazz)
			throws IOException
	{
		String resourcePath = relativePath + '/' + resource;
		File sourcePrj = getSourceProject(clazz);


		JarFile jar = null;
		InputStream resourceStream;
		if (sourcePrj.isFile())
		{
			jar = new JarFile(sourcePrj);
			JarEntry resEntry = jar.getJarEntry(resourcePath);
			resourceStream = jar.getInputStream(resEntry);
		}
		else
		{
			resourceStream = new FileInputStream(sourcePrj.getAbsolutePath() +
					"/" +
					resourcePath);
		}
		byte[] buffer = new byte[4096];
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		for (;;)
		{
			int read = resourceStream.read(buffer);
			if (read == -1) {
				break;
			}
			outputStream.write(buffer, 0, read);
		}
		outputStream.close();
		resourceStream.close();
		if (jar != null) {
			jar.close();
		}
		return outputStream.toString("iso-8859-1");
	}

	private static File getSourceProject(Class clazz)
	{
		ProtectionDomain protectionDomain = clazz.getProtectionDomain();
		CodeSource codeSource = protectionDomain.getCodeSource();
		URL location = codeSource.getLocation();
		File sourcePrj = new File(location.getFile());
		return sourcePrj;
	}

	public static void appendContents(IFile pagesFile, String data)
			throws IOException
	{
		File file = pagesFile.getLocation().toFile();
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fileWriter = new FileWriter(file, true);
		BufferedWriter buffWriter = new BufferedWriter(fileWriter);
		PrintWriter writer = new PrintWriter(buffWriter);
		writer.println(data);
		writer.close();
		buffWriter.close();
		fileWriter.close();
	}

	public static void replaceContents(IFile file, String template, String newContent)
			throws CoreException, IOException
	{
		String content = getContents(file).replace(template, newContent);
		InputStream stream = new ByteArrayInputStream(content.getBytes(file.getCharset()));
		if (file.exists()) {
			file.setContents(stream, true, true, new NullProgressMonitor());
		}
		stream.close();
	}

	public static String getContents(IFile iFile)
			throws IOException
	{
		File file = iFile.getLocation().toFile();
		FileInputStream stream = new FileInputStream(file);
		StringBuffer contents = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(stream));
		String line;
		while ((line = br.readLine()) != null)
		{
			contents.append(line).append("\n");
		}
		br.close();
		stream.close();
		return contents.toString();
	}

}
