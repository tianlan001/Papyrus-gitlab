/*****************************************************************************
 * Copyright (c) 2011, 2016, 2021 CEA LIST, Christian W. Damus, and others.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr
 *  Christian W. Damus - bug 485220
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 571814
 *****************************************************************************/
package org.eclipse.papyrus.eclipse.project.editors.project;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.papyrus.eclipse.project.editors.Activator;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IPluginProjectEditor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * This editor allows to edit the plugin file
 *
 */
public class PluginProjectEditor extends ProjectEditor implements IPluginProjectEditor {

	private Document pluginXML;

	private IFile pluginFile;

	private Element pluginRoot;

	/**
	 *
	 * Constructor.
	 *
	 * @param project
	 *            the eclipse project
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws CoreException
	 */
	public PluginProjectEditor(final IProject project) throws ParserConfigurationException, SAXException, IOException, CoreException {
		super(project);
	}

	/**
	 * Initializes me as a slave to another editor, which maintains the canonical
	 * project description.
	 *
	 * @param master
	 *            my master editor
	 */
	PluginProjectEditor(AbstractProjectEditor master) {
		super(master);
	}

	@Override
	public void init() {
		this.pluginFile = getPlugin();
		if (this.pluginFile != null && this.pluginFile.exists()) {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder;
			try {
				documentBuilder = documentFactory.newDocumentBuilder();
				this.pluginXML = documentBuilder.parse(this.pluginFile.getLocation().toFile());
				this.pluginRoot = this.pluginXML.getDocumentElement();
			} catch (ParserConfigurationException e) {
				Activator.log.error(e);
			} catch (SAXException e) {
				Activator.log.error(e);
			} catch (IOException e) {
				Activator.log.error(e);
			}
		}
	}

	/**
	 * Create the file plugin.xml
	 *
	 * @see org.eclipse.papyrus.eclipse.project.editors.project.ProjectEditor#createFiles(Set)
	 *
	 *      {@inheritDoc}
	 */
	@Override
	public void createFiles(final Set<String> files) {
		if (files.contains(PLUGIN_XML_FILE)) {
			IFile plugin = getProject().getFile(PLUGIN_XML_FILE);
			if (!plugin.exists()) {
				InputStream is = getInputStream("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<?eclipse version=\"3.4\"?>\n" + "<plugin>\n" + "</plugin>\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				try {
					plugin.create(is, true, null);
				} catch (CoreException e) {
					Activator.log.error(e);
				}
			}
		}
		super.createFiles(files);
	}

	@Override
	public boolean exists() {
		IFile plugin = getProject().getFile(PLUGIN_XML_FILE);
		return plugin.exists() && super.exists();
	}

	@Override
	public Element addExtension(final String extensionPoint) {
		if (exists()) {
			touch();
			Element extension = this.pluginXML.createElement(EXTENSION);
			extension.setAttribute(POINT, extensionPoint);
			this.pluginRoot.appendChild(extension);
			return extension;
		}
		return null;
	}

	/**
	 * Returns the list of the registered extension with this extension point
	 *
	 * @param extensionPoint
	 *            the name of an extension point
	 * @return
	 *         the list of the registered extension with this extension point
	 */
	@Override
	public List<Node> getExtensions(final String extensionPoint) {
		if (exists()) {
			NodeList nodes = this.pluginRoot.getChildNodes();
			List<Node> extensions = new ArrayList<>();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node item = nodes.item(i);
				if (item instanceof NodeList) {
					String name = item.getNodeName();
					if (name.equals(EXTENSION)) {
						NamedNodeMap attributes = item.getAttributes();
						Node point = attributes.getNamedItem(POINT);
						if (extensionPoint.equals(point.getNodeValue())) {
							if (item instanceof Node) {
								extensions.add(item);
							}
						}
					}
				}
			}
			return extensions;
		}
		return null;
	}


	@Override
	public void setAttribute(final Element element, final String attributeName, final String attributeValue) {
		if (!Objects.equals(element.getAttribute(attributeName), attributeValue)) {
			touch();
			element.setAttribute(attributeName, attributeValue);
		}
	}

	@Override
	public Element addChild(final Element element, final String childName) {
		touch();
		Element child = this.pluginXML.createElement(childName);
		element.appendChild(child);
		return child;
	}

	private IFile getPlugin() {
		IFile plugin = getProject().getFile(PLUGIN_XML_FILE);
		if (plugin.exists()) {
			return plugin;
		}
		return null;
	}


	/**
	 * @since 2.0
	 */
	@Override
	protected void doSave() {
		if (exists()) {
			try {
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8"); //$NON-NLS-1$
				transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //$NON-NLS-1$
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", //$NON-NLS-1$
						"3"); //$NON-NLS-1$
				StreamResult result = new StreamResult(new StringWriter());
				DOMSource source = new DOMSource(this.pluginXML);
				transformer.transform(source, result);

				String resultAsString = result.getWriter().toString();
				if (!resultAsString.endsWith("\n")) { //$NON-NLS-1$
					resultAsString += "\n"; //$NON-NLS-1$
				}
				InputStream inputStream = getInputStream(resultAsString);
				this.pluginFile.setContents(inputStream, true, true, null);
			} catch (TransformerException ex) {
				Activator.log.error(ex);
			} catch (CoreException ex) {
				Activator.log.error(ex);
			}
		}
		super.doSave();
	}

	@Override
	public Set<String> getMissingNature() {
		Set<String> natures = super.getMissingNature();
		if (!hasNature(PLUGIN_NATURE)) {
			natures.add(PLUGIN_NATURE);
		}
		return natures;
	}

	@Override
	public Set<String> getMissingFiles() {
		Set<String> files = super.getMissingFiles();
		IFile plugin = getProject().getFile(PLUGIN_XML_FILE);
		if (!plugin.exists()) {
			files.add(PLUGIN_XML_FILE);
		}
		return files;
	}

	@Override
	public Set<String> getMissingBuildCommand() {
		Set<String> commands = super.getMissingBuildCommand();
		if (!hasBuildCommand(PLUGIN_BUILD_COMMAND)) {
			commands.add(PLUGIN_BUILD_COMMAND);
		}
		return commands;
	}

	@Override
	public Document getDocument() {
		return pluginXML;
	}
}
