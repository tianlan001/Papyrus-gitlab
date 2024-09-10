/******************************************************************************
 * Copyright (c) 2013, 2020 Montages A.G, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Guillaume Hillairet (Montages A.G.) : initial implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *    Etienne ALLOGO (ARTAL) - etienne.allogo@artal.fr - Bug 569174 - Use project or worksapce preference as new line characters
 *****************************************************************************/
package org.eclipse.papyrus.gmf.codegen.util;

import static org.eclipse.xtext.util.Strings.notNull;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.codegen.merge.java.JControlModel;
import org.eclipse.emf.codegen.merge.java.JMerger;
import org.eclipse.emf.codegen.util.CodeGenUtil;
import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.gmf.common.codegen.OutputFormatterUtil;
import org.eclipse.papyrus.gmf.internal.common.codegen.DefaultTextMerger;
import org.eclipse.papyrus.gmf.internal.common.codegen.TextMerger;
import org.eclipse.xtext.generator.AbstractFileSystemAccess;
import org.eclipse.xtext.util.StringInputStream;

import com.google.inject.Inject;

/**
 * {@link MergeFileSystemAccess} is an implementation of {@link AbstractFileSystemAccess} that 
 * calls a {@link TextMerger} to merge the content of Java files during the generation of files.
 * 
 * @author ghillairet
 */
public class MergeFileSystemAccess extends AbstractFileSystemAccess {

	@Inject
	private IWorkspaceRoot root;

	@Override
	public void generateFile(String fileName, String slot, CharSequence contents) {		
		IFile file = getFile(fileName, slot);

		try {
			createFolder(file.getParent());

			final String defaultCharset = file.getCharset();
			final String newContentAsString = postProcess(fileName, slot, contents).toString();

			if (file.exists()) {
				StringInputStream newContent = null;
				try {
					newContent = new StringInputStream(newContentAsString, defaultCharset);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				final boolean contentChanged = hasChanged(file, newContent);
				if (contentChanged) {
					if (isJava(file)) {
						InputStream mergedContent = null;
						try {
							mergedContent = getMergedContent(file, newContentAsString, defaultCharset); 
							file.setContents(mergedContent, true, true, null);
						} finally {
							if (mergedContent != null) {
								try {
									mergedContent.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			} else {
				file.create(new StringInputStream(newContentAsString, defaultCharset), true, null);
			}

			file.setDerived(true, new NullProgressMonitor());

		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public URI getURI(String fileName, String outputConfiguration) {
		IFile file = getFile(fileName, outputConfiguration);
		return URI.createPlatformResourceURI(file.getFullPath().toString(), true);
	}

	protected IFile getFile(String fileName, String slot) {
		String outletPath = getPathes().get(slot);
		return root.getFile(new Path(outletPath + "/" + fileName));
	}

	protected void createFolder(IContainer parent) throws CoreException {
		if(!parent.exists()) {
			if(!(parent instanceof IFolder)) 
				throw new RuntimeException("IContainer " + notNull(parent) + " does not exist");
			createFolder(parent.getParent());
			((IFolder)parent).create(true, false, new NullProgressMonitor());
		}
	}

	private boolean hasChanged(IFile file, InputStream newContent) {
		boolean contentChanged = false;
		BufferedInputStream oldContent = null;
	
		try {
			if (newContent != null) {
				oldContent = new BufferedInputStream(file.getContents());
				int newByte = newContent.read();
				int oldByte = oldContent.read();
				while(newByte != -1 && oldByte != -1 && newByte == oldByte) {
					newByte = newContent.read();
					oldByte = oldContent.read();
				}
				contentChanged = newByte != oldByte;
			}
		} catch (CoreException e) {
			contentChanged = true;
		} catch (IOException e) {
			contentChanged = true;
		} finally {
			if (oldContent != null) {
				try {
					oldContent.close();
				} catch (IOException e) {
					// ignore
				}
			}
			// reset to offset zero allows to reuse internal byte[]
			try {
				newContent.reset();
			} catch (IOException e) {
				// ignore
			}
		}
		return contentChanged;
	}

	private boolean isJava(IFile file) {
		return file.getFileExtension().equals("java");
	}

	private String getStringContent(IFile file, String defaultCharset) {
		String oldContentAsString = null;
		InputStream in;
		try {
			in = file.getContents();
		} catch (CoreException e) {
			throw new RuntimeException(e);
		}
		try {
			oldContentAsString = convertStreamToString(in, 2048, defaultCharset);
		} finally {					
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return oldContentAsString;
	}

	private InputStream getMergedContent(IFile file, String newContentAsString, String defaultCharset) {
		final TextMerger textMerger = createMergeService(file);
		final String oldContentAsString = getStringContent(file, defaultCharset);
		final String mergedString = textMerger.mergeJava(oldContentAsString, newContentAsString);
		StringInputStream mergedContent = null;
		try {
			mergedContent = new StringInputStream(mergedString, defaultCharset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return mergedContent;
	}

	protected TextMerger createMergeService(IFile file) {
		URL controlFile = getJMergeControlFile();
		if (controlFile != null) {
			JControlModel controlModel = new JControlModel();
			controlModel.initialize(CodeGenUtil.instantiateFacadeHelper(JMerger.DEFAULT_FACADE_HELPER_CLASS), controlFile.toString());
			if (!controlModel.canMerge()) {
				throw new IllegalStateException("Can not initialize JControlModel");
			}
			 //Bug 569174 - Use project or worksapce preference as new line characters
			return new DefaultTextMerger(OutputFormatterUtil.getDefaultLineSeparator(file.getProject()),controlModel);
		}
		return null;
	}

	protected URL getJMergeControlFile() {
		return MergeFileSystemAccess.class.getResource("emf-merge.xml");
	}

	private String convertStreamToString(final InputStream is, final int bufferSize, String defaultCharset) {
		final char[] buffer = new char[bufferSize];
		final StringBuilder out = new StringBuilder();
		try {
			final Reader in = new InputStreamReader(is, defaultCharset);
			try {
				for (;;) {
					int rsz = in.read(buffer, 0, buffer.length);
					if (rsz < 0)
						break;
					out.append(buffer, 0, rsz);
				} 
			}
			finally {
				in.close();
			}
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return out.toString();
	}

}
