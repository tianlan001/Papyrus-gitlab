/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.dev.project.management.handlers.plugins;

import java.io.File;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentDescription;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.dev.project.management.Activator;
import org.eclipse.papyrus.infra.tools.util.PlatformHelper;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Handler for the "open parent POM" command.
 * 
 * @since 1.2
 */
public class OpenParentPOMHandler extends AbstractHandler {
	private final String POM_CONTENT_TYPE = "org.eclipse.m2e.core.pomFile"; //$NON-NLS-1$

	public OpenParentPOMHandler() {
		super();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			IFile pom = getSelectedPOM(HandlerUtil.getActiveMenuSelection(event));
			IPath parentPath = (pom == null) ? null : getParentPath(pom);

			if (parentPath != null) {
				open(HandlerUtil.getActiveWorkbenchWindow(event).getActivePage(), parentPath);
			}
		} catch (Exception e) {
			throw new ExecutionException("Failed to open parent POM", e);
		}

		return null;
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_MENU_SELECTION_NAME);
		setBaseEnabled(getSelectedPOM(selection) != null);
	}

	private IFile getSelectedPOM(Object selection) {
		IFile result = null;

		if (selection instanceof IStructuredSelection) {
			Object first = ((IStructuredSelection) selection).getFirstElement();
			IFile file = PlatformHelper.getAdapter(first, IFile.class);
			if (file != null) {
				try {
					IContentType expected = Platform.getContentTypeManager().getContentType(POM_CONTENT_TYPE);
					if (expected == null) {
						// Don't have m2e installed? Only support pom.xml, then
						if (file.getName().equals("pom.xml")) {
							result = file;
						}
					} else {
						IContentDescription desc = file.getContentDescription();
						IContentType type = (desc == null) ? null : desc.getContentType();
						if ((type != null) && type.isKindOf(expected)) {
							result = file;
						}
					}
					result = file;
				} catch (CoreException e) {
					// Can't describe it? Must not be a valid POM
					Activator.log.log(e.getStatus());
				}
			}
		}

		return result;
	}

	private void open(IWorkbenchPage page, IPath pomPath) throws Exception {
		File file = pomPath.toFile();
		IWorkspaceRoot ws = ResourcesPlugin.getWorkspace().getRoot();
		IFile[] workspaceFiles = ws.findFilesForLocationURI(file.toURI());
		if (workspaceFiles.length > 0) {
			IDE.openEditor(page, workspaceFiles[0]);
		} else {
			String editor = IDE.getEditorDescriptor(file.getName()).getId();
			IDE.openEditor(page, file.toURI(), editor, true);
		}
	}

	IPath getParentPath(IFile pom) throws Exception {
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		class ParentHandler extends DefaultHandler {
			private StringBuilder parentBuilder;
			private boolean inParent;

			@Override
			public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
				if ("parent".equals(localName) || "parent".equals(qName)) {
					inParent = true;
				} else if (inParent && ("relativePath".equals(localName) || "relativePath".equals(qName))) {
					// Getting the parent relative path
					parentBuilder = new StringBuilder();
				}
			}

			@Override
			public void characters(char[] ch, int start, int length) throws SAXException {
				if (parentBuilder != null) {
					// We're collecting the parent relative path
					parentBuilder.append(ch, start, length);
				}
			}

			@Override
			public void endElement(String uri, String localName, String qName) throws SAXException {
				if ("parent".equals(localName) || "parent".equals(qName)) {
					// Done
					throw new OperationCanceledException();
				} else if (inParent && ("relativePath".equals(localName) || "relativePath".equals(qName))) {
					// Done
					throw new OperationCanceledException();
				}
			}

			IPath getParentPath() {
				IPath result = null;

				IPath location = pom.getLocation();
				if (location != null) { // Can be null if not actually a real file
					if (parentBuilder == null) {
						// Implicit parent path
						result = location.removeLastSegments(2).append("pom.xml");
					} else {
						IPath relative = new Path(parentBuilder.toString().trim());
						result = location.removeLastSegments(1).append(relative);

						// Is it just pointing at a directory?
						File parent = result.toFile();
						if (parent.exists() && parent.isDirectory()) {
							result = result.append("pom.xml");
						}
					}
				}

				return result;
			}
		}

		ParentHandler handler = new ParentHandler();

		try (InputStream input = pom.getContents()) {
			parser.parse(input, handler);
		} catch (OperationCanceledException e) {
			// Normal
		}

		return handler.getParentPath();
	}
}
