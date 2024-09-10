/*****************************************************************************
 * Copyright (c) 2014, 2015 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus (CEA) - Add support for updating Oomph setup models
 *  Christian W. Damus - Add support for updating multiple development streams in a setup model
 *  Christian W. Damus - Support updating of multiple selected files
 *
 *****************************************************************************/
package org.eclipse.papyrus.releng.tools.internal.popup.actions;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.cbi.p2repo.aggregator.Contribution;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.releng.tools.internal.Activator;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListDialog;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class OomphSetupUpdater extends XMLDependencyUpdater {

	public static final String ANNOTATION_SOURCE = "http://www.eclipse.org/Papyrus/2014/releng/dependencytools";//$NON-NLS-1$

	public static final String UPDATE_KEY = "updateFrom";//$NON-NLS-1$

	private final Pattern annotationPattern = Pattern.compile("updateFrom:([^:]+):(\\d+)"); //$NON-NLS-1$

	private final Pattern indexPattern = Pattern.compile(":\\d+$"); //$NON-NLS-1$

	private String streamName;

	public OomphSetupUpdater() {
		super();
	}

	@Override
	public boolean canUpdate(IFile file) {
		return "setup".equals(file.getFileExtension()); //$NON-NLS-1$
	}

	@Override
	public void updateDocument(final Shell parentShell, final IFile mapFile, final EList<Contribution> contributions, final Map<Object, Object> context) throws CoreException {
		streamName = promptForStreamName(parentShell, mapFile, context);
		if (streamName == null) {
			// Cancel
			return;
		}

		super.updateDocument(parentShell, mapFile, contributions, context);
	}

	@Override
	protected Pattern getCommentPattern() {
		return annotationPattern;
	}

	@Override
	protected String getCommentContent(Node comment) {
		StringBuilder result = new StringBuilder("updateFrom:"); //$NON-NLS-1$

		Element annotation = (Element) comment;
		NodeList details = annotation.getElementsByTagName("detail"); //$NON-NLS-1$
		for (int i = 0; i < details.getLength(); i++) {
			Element next = (Element) details.item(i);
			if (UPDATE_KEY.equals(next.getAttribute("key"))) { //$NON-NLS-1$
				String repoSpec = null;
				if (next.hasAttribute("value")) { //$NON-NLS-1$
					repoSpec = next.getAttribute("value"); //$NON-NLS-1$
				} else {
					NodeList values = next.getElementsByTagName("value"); //$NON-NLS-1$
					if (values.getLength() > 0) {
						repoSpec = values.item(0).getTextContent().trim();
					}
				}
				if (repoSpec != null) {
					result.append(repoSpec);
					if (!indexPattern.matcher(repoSpec).find()) {
						// default index
						result.append(":0"); //$NON-NLS-1$
						break;
					}
				}
			}
		}

		return result.toString();
	}

	@Override
	protected String getCommentSyntax() {
		return String.format("Annotation with source %s and detail 'updateFrom=<contributionName>[:<index>]?'", ANNOTATION_SOURCE); //$NON-NLS-1$
	}

	@Override
	protected Node getPrecedingComment(Node node) {
		Element result = null;

		for (Node next = node.getFirstChild(); next != null; next = next.getNextSibling()) {
			if (next.getNodeType() == Node.ELEMENT_NODE) {
				if ("annotation".equals(next.getNodeName())) { //$NON-NLS-1$
					Element annotation = (Element) next;
					if (ANNOTATION_SOURCE.equals(annotation.getAttribute("source"))) { //$NON-NLS-1$
						result = annotation;
						break;
					}
				}
			}
		}

		return result;
	}

	@Override
	protected String getXpath() {
		return String.format("//setupTask[@type='setup.targlets:TargletTask']/targlet/repositoryList[@name='%s']/repository", streamName);
	}

	@Override
	protected String getCurrentLocation(Node uri) {
		return uri.getAttributes().getNamedItem("url").getTextContent(); //$NON-NLS-1$
	}

	@Override
	protected void updateUri(Node uri, String location) {
		uri.getAttributes().getNamedItem("url").setTextContent(location); //$NON-NLS-1$
	}

	@Override
	protected void save(Document document, File destination) throws Exception {
		// Use EMF resource serialization to format the file in the EMF style
		ResourceSet rset = new ResourceSetImpl();
		Resource resource = rset.createResource(URI.createFileURI(destination.getAbsolutePath()));
		Map<Object, Object> options = new HashMap<>();
		options.put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE, true);
		options.put(XMLResource.OPTION_DEFER_IDREF_RESOLUTION, true);
		((XMLResource) resource).load(document, options);

		options.clear();
		options.put(XMLResource.OPTION_FORMATTED, true);
		options.put(XMLResource.OPTION_PROCESS_DANGLING_HREF, XMLResource.OPTION_PROCESS_DANGLING_HREF_RECORD);
		resource.save(options);
	}

	protected String promptForStreamName(Shell parentShell, IFile setupFile, Map<Object, Object> context) throws CoreException {
		final String key = "$setup.stream$"; //$NON-NLS-1$

		String result = (String) context.get(key);
		if (result != null) {
			return result;
		}

		final Set<String> repositoryLists = new LinkedHashSet<>();

		try (InputStream input = setupFile.getContents()) {
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			parser.parse(input, new DefaultHandler() {
				int inTarglet;
				String repositoryListName;
				boolean foundAnnotation;

				@Override
				public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
					if ("targlet".equals(qName)) { //$NON-NLS-1$
						if ((inTarglet > 0) || "${eclipse.target.platform}".equals(attributes.getValue("activeRepositoryList"))) { //$NON-NLS-1$ //$NON-NLS-2$
							// This is a stream-switching targlet. Get its repository names
							inTarglet++;
						}
					} else if ((inTarglet > 0) && "repositoryList".equals(qName)) {
						String listName = attributes.getValue("name"); //$NON-NLS-1$
						if (listName != null && !listName.isEmpty()) {
							repositoryListName = listName;
							foundAnnotation = false;
						}
					} else if ("annotation".equals(qName) && OomphSetupUpdater.ANNOTATION_SOURCE.equals(attributes.getValue("source"))) { //$NON-NLS-1$ //$NON-NLS-2$
						foundAnnotation = true;
					}
				}

				@Override
				public void endElement(String uri, String localName, String qName) throws SAXException {
					if ("targlet".equals(qName)) { //$NON-NLS-1$
						inTarglet = Math.max(inTarglet - 1, 0);
					} else if ("repositoryList".equals(qName) && (repositoryListName != null)) { //$NON-NLS-1$
						if (foundAnnotation) {
							repositoryLists.add(repositoryListName);
						}
						repositoryListName = null;
						foundAnnotation = false;
					}
				}
			});
		} catch (Exception e) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Failed to scan setup model for available streams.", e));
		}

		if (repositoryLists.isEmpty()) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "No streams are defined in the selected setup model."));
		}

		String first = repositoryLists.iterator().next();
		if (repositoryLists.size() == 1) {
			return first;
		}

		ILabelProvider labels = new LabelProvider();
		ListDialog dlg = new ListDialog(parentShell);
		dlg.setContentProvider(ArrayContentProvider.getInstance());
		dlg.setLabelProvider(labels);
		dlg.setInput(repositoryLists);
		dlg.setInitialSelections(new Object[] { repositoryLists.iterator().next() });
		dlg.setTitle("Select Stream");
		dlg.setMessage(NLS.bind("Select the development stream to update in \"{0}\".", setupFile.getFullPath()));
		labels.dispose();

		dlg.open();
		Object[] dlgResult = dlg.getResult();
		result = ((dlgResult == null) || (dlgResult.length == 0)) ? null : (String) dlgResult[0];
		if (result != null) {
			context.put(key, result);
		}
		return result;
	}
}
