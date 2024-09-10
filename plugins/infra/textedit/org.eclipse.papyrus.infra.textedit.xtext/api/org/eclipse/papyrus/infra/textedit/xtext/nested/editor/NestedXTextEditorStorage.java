/*****************************************************************************
 * Copyright (c) 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.textedit.xtext.nested.editor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.ICustomDirectEditorConfiguration;
import org.eclipse.papyrus.infra.textedit.textdocument.TextDocument;

/**
 * The storage use to be able to edit a model element in a nested Xtext editor
 */
public class NestedXTextEditorStorage implements IStorage {

	/**
	 * The {@link TextDocument}
	 */
	private TextDocument textDocument;

	/**
	 * the fileExtension
	 */
	private String fileExtension;

	/**
	 * The Xtext editor configuration
	 */
	private ICustomDirectEditorConfiguration configuration;

	/**
	 *
	 * Constructor.
	 *
	 * @param configuration
	 *            the editor configuration
	 * @param textDocument
	 *            the {@link TextDocument}
	 * @param fileExtension
	 *            the file extension declared in the XText grammar
	 */
	public NestedXTextEditorStorage(final ICustomDirectEditorConfiguration configuration, final TextDocument textDocument, final String fileExtension) {
		this.textDocument = textDocument;
		this.fileExtension = fileExtension;
		this.configuration = configuration;
	}

	/**
	 *
	 * @return
	 *         the text to edit
	 */
	public String getTextValue() {
		final EObject semanticContext = this.textDocument.getSemanticContext();
		final IParser parser = this.configuration.createParser(semanticContext);
		String string = parser.getEditString(new EObjectAdapter(semanticContext), 0);
		if (string == null) {
			string = ""; //$NON-NLS-1$
		}
		return string;
	}

	/**
	 *
	 * @see org.eclipse.core.resources.IStorage#getContents()
	 *
	 * @return
	 * @throws CoreException
	 */
	@Override
	public InputStream getContents() throws CoreException {
		return new ByteArrayInputStream(getTextValue().getBytes());
	}

	/**
	 *
	 * @return
	 *         the path (it is only here to get a functional behavior. We must not save a real resource with this path
	 */
	@Override
	public IPath getFullPath() {
		final Resource res = this.textDocument.eResource();
		if (res == null) {// sometimes we get an expection here... I don't find another way to fix it...
			return new Path("");//$NON-NLS-1$
		}
		URI uri = res.getURI();
		if (uri.segmentCount() > 1) {
			uri = uri.trimSegments(1);
		}
		String xmiID = "";//$NON-NLS-1$
		if (res instanceof XMIResource) {
			xmiID = ((XMIResource) res).getID(this.textDocument);
		}
		uri = uri.appendSegment("nested"); //$NON-NLS-1$ // to be consistent with XText in ViewProperty. In this case the fake resource is called "embedded"
		uri = uri.appendSegment(xmiID);
		uri = uri.appendFileExtension(this.fileExtension); // not really used, but to be consistent with the real used grammar
		// toPlatformString returns null for CDO models
		// final String platformString = uri.toPlatformString(true);
		return new Path(uri.path());
	}

	/**
	 *
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 *
	 * @param adapter
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

	/**
	 *
	 * @see org.eclipse.core.resources.IStorage#getName()
	 *
	 * @return
	 */
	@Override
	public String getName() {
		return getFullPath().lastSegment();
	}

	/**
	 *
	 * @see org.eclipse.core.resources.IStorage#isReadOnly()
	 *
	 * @return
	 */
	@Override
	public boolean isReadOnly() {
		return false;
	}
}
