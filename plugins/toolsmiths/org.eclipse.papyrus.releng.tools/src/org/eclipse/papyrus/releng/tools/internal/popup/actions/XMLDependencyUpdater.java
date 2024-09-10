/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.releng.tools.internal.popup.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrus.releng.tools.internal.Activator;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Specialization of the DependencyAdapter for XML Documents
 *
 * @author Camille Letavernier
 *
 */
public abstract class XMLDependencyUpdater extends DependencyUpdater<Node> {

	private Document currentDocument;

	protected abstract String getXpath();

	@Override
	protected List<Node> getNodesToUpdate(IFile file) throws CoreException {
		File rmapFile = file.getLocation().toFile();

		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			currentDocument = docBuilder.parse(rmapFile);
			currentDocument.normalize();
			Element documentElement = currentDocument.getDocumentElement();

			XPath xpath = XPathFactory.newInstance().newXPath();
			NodeList uris = (NodeList) xpath.evaluate(getXpath(), documentElement, XPathConstants.NODESET);

			List<Node> result = new ArrayList<>(uris.getLength());
			for (int i = 0; i < uris.getLength(); i++) {
				result.add(uris.item(i));
			}

			return result;
		} catch (OperationCanceledException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Error updating map: " + ex.getLocalizedMessage(), ex)); //$NON-NLS-1$
		}


	}

	@Override
	protected String getComment(final Node node) {
		Node comment = getPrecedingComment(node);

		return comment == null ? null : getCommentContent(comment);
	}

	protected Node getPrecedingComment(Node node) {
		Comment comment = null;

		Node previous = node.getPreviousSibling();
		while (previous != null) {
			if (previous.getNodeType() == Node.COMMENT_NODE) {
				comment = (Comment) previous;
				break;
			} else if (previous.getNodeType() != Node.TEXT_NODE) {
				break;
			}
			previous = previous.getPreviousSibling();
		}

		return comment;
	}

	protected String getCommentContent(Node comment) {
		return comment.getTextContent();
	}

	/**
	 * @see org.eclipse.papyrus.releng.tools.internal.popup.actions.DependencyUpdater#save(org.eclipse.core.resources.IFile)
	 *
	 * @param file
	 */
	@Override
	protected void save(IFile file) throws Exception {
		File destination = file.getLocation().toFile();

		save(currentDocument, destination);
	}

	protected void save(Document document, File destination) throws Exception {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //$NON-NLS-1$

		StreamResult result = new StreamResult(destination);
		DOMSource source = new DOMSource(currentDocument);
		transformer.transform(source, result);
	}

}
