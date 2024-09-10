/*****************************************************************************
 * Copyright (c) 2009, 2016 CEA LIST, Christian W. Damus, and others.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 488965
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.hyperlink.object;

import java.net.URI;
import java.util.List;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.hyperlink.Activator;
import org.eclipse.papyrus.infra.hyperlink.ui.EditorHyperlinkDocumentShell;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

/**
 * The Class HyperLinkDocument. A container of document
 */
public class HyperLinkDocument extends HyperLinkObject {

	/**
	 * Gets the hyperlink document.
	 *
	 * @return the hyperlink document
	 */
	public String getHyperlinkDocument() {
		return (String) super.getObject();
	}

	/**
	 * Sets the hyperlink document.
	 *
	 * @param object
	 *            the new hyperlink document as a string representing an absolute file path
	 */
	public void setHyperlinkDocument(String object) {
		try {
			URI originalURI = URIUtil.toURI(object);
			URI relativeURI = null;

			if (originalURI != null && originalURI.isAbsolute()) {
				relativeURI = org.eclipse.core.runtime.URIUtil.makeRelative(originalURI, getBaseURI());
			} else {
				relativeURI = new URI(object);
			}

			super.setObject(relativeURI.toString());
		} catch (Exception e) {
			Activator.log.error(e);
			super.setObject(""); //$NON-NLS-1$
		}
	}

	@Override
	public void openLink() {
		try {
			// this is a file try to open it
			String fileName = getHyperlinkDocument();

			IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			IWorkbenchPage page = window.getActivePage();

			URI relativeURI = new URI(fileName);
			URI absoluteURI = org.eclipse.core.runtime.URIUtil.makeAbsolute(relativeURI, getBaseURI());

			IFileStore fileStore = EFS.getStore(absoluteURI);

			IDE.openEditorOnFileStore(page, fileStore); // Let eclipse determine the better editor type for our file
		} catch (Exception e) {
			Activator.log.error(e);
		}
	}

	@Override
	public void executeEditMousePressed(Shell parentShell, List<HyperLinkObject> list, EObject amodel) {
		EditorHyperlinkDocumentShell editor = new EditorHyperlinkDocumentShell(parentShell);
		editor.setHyperlinkDocument(this);
		editor.open();
		if (editor.getHyperlinkDocument() != null) {
			int index = list.indexOf(this);
			list.remove(this);
			list.add(index, editor.getHyperlinkDocument());
		}
	}

	@Override
	public boolean needsOpenCommand() {
		return false;
	}

	private URI getBaseURI() {
		return ResourcesPlugin.getWorkspace().getRoot().getLocationURI();
	}
}
