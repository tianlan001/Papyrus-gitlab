/*****************************************************************************
 * Copyright (c) 2011, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.eclipse.project.editors.project;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Objects;
import java.util.Properties;
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
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IFeatureProjectEditor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class FeatureProjectEditor extends ProjectEditor implements IFeatureProjectEditor {

	/**
	 * the name of the file feature.xml
	 * 
	 * @since 2.0
	 */
	public static final String FEATURE_XML_FILE = "feature.xml"; //$NON-NLS-1$

	private static final String FEATURE_NATURE = "org.eclipse.pde.FeatureNature"; //$NON-NLS-1$
	private static final String FEATURE_BUILDER = "org.eclipse.pde.FeatureBuilder"; //$NON-NLS-1$

	private static final String ID = "id"; //$NON-NLS-1$
	private static final String LABEL = "label"; //$NON-NLS-1$
	private static final String VERSION = "version"; //$NON-NLS-1$
	private static final String PROVIDER = "provider-name"; //$NON-NLS-1$

	private static final String URL = "url"; //$NON-NLS-1$
	private static final String COPYRIGHT = "copyright"; //$NON-NLS-1$
	private static final String LICENSE = "license"; //$NON-NLS-1$
	private static final String DESCRIPTION = "description"; //$NON-NLS-1$

	private static final String OS = "os"; //$NON-NLS-1$
	private static final String WS = "ws"; //$NON-NLS-1$
	private static final String NL = "nl"; //$NON-NLS-1$
	private static final String ARCH = "arch"; //$NON-NLS-1$
	private static final String UPDATE = "update"; //$NON-NLS-1$

	private static final String PLUGIN = "plugin"; //$NON-NLS-1$
	private static final String IMPORT = "import"; //$NON-NLS-1$
	private static final String INCLUDES = "includes"; //$NON-NLS-1$
	private static final String REQUIRES = "requires"; //$NON-NLS-1$
	private static final String FEATURE = "feature"; //$NON-NLS-1$

	// TODO pour l'externalization : utiliser l'�diteur de Properties! dans java Utils

	private Document featureXML;

	private IFile featureFile;

	private Element featureRoot;

	/**
	 * Constructor.
	 *
	 * @param project
	 *            the eclipse project
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws CoreException
	 */
	public FeatureProjectEditor(final IProject project) throws ParserConfigurationException, SAXException, IOException, CoreException {
		super(project);
	}

	@Override
	public void init() {
		featureFile = getFeature();
		if ((featureFile != null) && featureFile.exists()) {
			final DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			try {
				DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
				featureXML = documentBuilder.parse(featureFile.getLocation().toOSString());
				featureRoot = featureXML.getDocumentElement();
			} catch (final ParserConfigurationException e) {
				Activator.log.error(e);
			} catch (final SAXException e) {
				Activator.log.error(e);
			} catch (final IOException e) {
				Activator.log.error(e);
			}
		}
	}

	@Override
	public void createFiles(final Set<String> files) {
		if (files.contains(FEATURE_XML_FILE)) {
			featureFile = getProject().getFile(FEATURE_XML_FILE);
			if (!featureFile.exists()) {
				InputStream content = getInputStream("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<feature>\n</feature>\n\n"); //$NON-NLS-1$

				try {
					featureFile.create(content, true, null);
				} catch (CoreException e) {
					Activator.log.error(e);
				}
			}
		}
	}

	@Override
	public boolean exists() {
		return getFeature().exists() && super.exists();
	}

	private void setAttribute(final String attributeName, final String attributeValue) {
		setAttribute(featureRoot, attributeName, attributeValue);
	}

	private void setAttribute(final Element element, final String attributeName, final String attributeValue) {
		if (!Objects.equals(element.getAttribute(attributeName), attributeValue)) {
			touch();
			element.setAttribute(attributeName, attributeValue);
		}
	}

	private void setTextContent(Element element, String text) {
		if (!Objects.equals(element.getTextContent(), text)) {
			touch();
			element.setTextContent(text);
		}
	}

	/**
	 * @return the feature.xml file if it exists
	 */
	private IFile getFeature() {
		final IFile result = getProject().getFile(FEATURE_XML_FILE);

		if (result.exists()) {
			return result;
		}

		return null;
	}

	/**
	 * @since 2.0
	 */
	@Override
	protected void doSave() {
		if ((featureFile != null) && featureFile.exists()) {
			try {
				final TransformerFactory transformerFactory = TransformerFactory.newInstance();
				final Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8"); //$NON-NLS-1$
				final StreamResult result = new StreamResult(new StringWriter());
				final DOMSource source = new DOMSource(featureXML);
				transformer.transform(source, result);

				final InputStream inputStream = getInputStream(result.getWriter().toString());
				featureFile.setContents(inputStream, true, true, null);
			} catch (final TransformerException ex) {
				Activator.log.error(ex);
			} catch (final CoreException ex) {
				Activator.log.error(ex);
			}
		}
		super.doSave();
	}

	@Override
	public Set<String> getMissingNature() {
		Set<String> result = super.getMissingNature();
		if (!hasNature(FEATURE_NATURE)) {
			result.add(FEATURE_NATURE);
		}
		return result;
	}

	@Override
	public Set<String> getMissingFiles() {
		Set<String> result = super.getMissingFiles();
		IFile feature = getProject().getFile(FEATURE_XML_FILE);
		if (!feature.exists()) {
			result.add(FEATURE_XML_FILE);
		}
		return result;
	}

	@Override
	public Set<String> getMissingBuildCommand() {
		Set<String> result = super.getMissingBuildCommand();
		if (!hasBuildCommand(FEATURE_BUILDER)) {
			result.add(FEATURE_BUILDER);
		}
		return result;
	}

	@Override
	public Document getDocument() {
		return featureXML;
	}

	private String getAttribute(String name) {
		return getAttribute(featureRoot, name);
	}

	private String getAttribute(Element element, String name) {
		return element.getAttribute(name);
	}

	/**
	 * @since 2.0
	 */
	@Override
	public String getId() {
		return getAttribute(ID);
	}

	@Override
	public String getLabel() {
		return getAttribute(LABEL);
	}

	@Override
	public String getVersion() {
		return getAttribute(VERSION);
	}

	@Override
	public String getProviderName() {
		return getAttribute(PROVIDER);
	}

	/**
	 * @since 2.0
	 */
	@Override
	public String getDescriptionText() {
		Element description = getNodeChild(DESCRIPTION, featureRoot);
		return (description == null) ? null : description.getTextContent().trim();
	}

	/**
	 * @since 2.0
	 */
	@Override
	public String getDescriptionURL() {
		Element description = getNodeChild(DESCRIPTION, featureRoot);
		return (description == null) || !description.hasAttribute(URL)
				? null
				: description.getAttribute(URL);
	}

	@Override
	public String getCopyrightURL() {
		final Element copyrightNode = getNode(COPYRIGHT);
		if (copyrightNode != null) {
			final String value = copyrightNode.getAttribute("url");
			if (value != null && value.startsWith("%")) {
				final IFile file = getProject().getFile("feature.properties");
				final Properties prop = new Properties(); // TODO create a method to use Properties for others fields too
				try {
					prop.load(file.getContents());
				} catch (final IOException e) {
					Activator.log.error(e);
				} catch (final CoreException e) {
					Activator.log.error(e);
				}
				final Object val = prop.get("url");
				if (val != null) {
					return (String) val;
				}
			}
			return copyrightNode.getAttribute("url");
		}
		return null;
	}

	@Override
	public String getCopyrightText() {
		final Element copyrightNode = getNode(COPYRIGHT);

		return copyrightNode != null ? copyrightNode.getTextContent() : null;
	}

	/**
	 * @since 2.0
	 */
	@Override
	public String getLicenseText() {
		Element license = getNodeChild(LICENSE, featureRoot);
		return (license == null)
				? null
				: license.getTextContent().trim();
	}

	/**
	 * @since 2.0
	 */
	@Override
	public String getLicenseURL() {
		Element license = getNodeChild(LICENSE, featureRoot);
		return (license == null) || !license.hasAttribute(URL)
				? null
				: license.getAttribute(URL);
	}

	/**
	 * @since 2.0
	 */
	@Override
	public String getOS() {
		return getAttribute(OS);
	}

	/**
	 * @since 2.0
	 */
	@Override
	public String getWS() {
		return getAttribute(WS);
	}

	/**
	 * @since 2.0
	 */
	@Override
	public String getNL() {
		return getAttribute(NL);
	}

	/**
	 * @since 2.0
	 */
	@Override
	public String getArch() {
		return getAttribute(ARCH);
	}

	/**
	 * @since 2.0
	 */
	@Override
	public void setId(final String id) {
		setAttribute(ID, id);
	}

	@Override
	public void setLabel(final String label) {
		setAttribute(LABEL, label);
	}

	@Override
	public void setVersion(final String version) {
		setAttribute(VERSION, version);
	}

	@Override
	public void setProviderName(final String providerName) {
		setAttribute(PROVIDER, providerName);
	}

	private Element forceElement(String name) {
		return forceElement(featureRoot, name);
	}

	private Element forceElement(Element parent, String name) {
		Element result = getNodeChild(name, parent);
		if (result == null) {
			touch();
			result = createElement(name);
			parent.appendChild(result);
		}
		return result;
	}

	@Override
	public void setDescription(final String descriptionURL, final String description) {
		if (featureRoot != null) {
			Element extension = forceElement(DESCRIPTION);
			setAttribute(extension, URL, descriptionURL);
			setTextContent(extension, description);
		}
	}

	@Override
	public void setCopyright(final String copyrightURL, final String copyrightDesc) {
		setURLNode(COPYRIGHT, copyrightURL, copyrightDesc);
	}

	/**
	 * @since 2.0
	 */
	@Override
	public void setLicense(final String licenseURL, final String licenseDesc) {
		setURLNode(LICENSE, licenseURL, licenseDesc);
	}

	/**
	 * @since 2.0
	 */
	@Override
	public void setOS(final String os) {
		setAttribute(OS, os);
	}

	/**
	 * @since 2.0
	 */
	@Override
	public void setWS(final String ws) {
		setAttribute(WS, ws);
	}

	/**
	 * @since 2.0
	 */
	@Override
	public void setNL(final String nl) {
		setAttribute(NL, nl);
	}

	/**
	 * @since 2.0
	 */
	@Override
	public void setArch(final String architecture) {
		setAttribute(ARCH, architecture);
	}

	/**
	 * @since 2.0
	 */
	@Override
	public void setUpdateURL(final String urlLabel, final String url) {
		Element urlNode = forceElement(URL);
		Element updateNode = forceElement(urlNode, UPDATE);

		setAttribute(updateNode, LABEL, urlLabel);
		setAttribute(updateNode, URL, url);
	}

	/**
	 * @since 2.0
	 */
	@Override
	public void addPlugin(final String pluginName) {
		// Get the plug-in element or create it if it does not exist
		Element plugin = forcePlugin(pluginName);
		if (!plugin.hasAttribute(VERSION)) {
			plugin.setAttribute(VERSION, "0.0.0"); //$NON-NLS-1$
		}
		if (!plugin.hasAttribute("download-size")) {
			plugin.setAttribute("download-size", "0"); //$NON-NLS-1$
		}
		if (!plugin.hasAttribute("install-size")) {
			plugin.setAttribute("install-size", "0"); //$NON-NLS-1$
		}
		if (!plugin.hasAttribute("unpack")) {
			plugin.setAttribute("unpack", "false"); //$NON-NLS-1$
		}
	}

	/**
	 * @since 2.0
	 */
	@Override
	public void addRequiredFeature(final String featureName, final String version) {
		// Get or create the required feature element
		Element feature = forceRequiredFeature(featureName);

		// Set the element values
		feature.setAttribute(VERSION, version);
	}

	/**
	 * @since 2.0
	 */
	@Override
	public void addRequiredPlugin(final String pluginName) {
		// Get or create the plug-in element
		forceRequiredPlugin(pluginName);
	}

	/**
	 * @since 2.0
	 */
	@Override
	public void addInclude(final String featureName, final String version) {
		Element includeNode = forceInclude(featureName);
		setAttribute(includeNode, VERSION, (version == null) ? "0.0.0" : version);
	}

	/**
	 * Creates an element and returns it.
	 *
	 * @param elementName
	 *            the name of the element to create
	 * @return the created element
	 */
	private Element createElement(String elementName) {
		return featureXML.createElement(elementName);
	}

	protected void setURLNode(final String nodeName, final String url, final String description) {
		if (featureRoot != null) {
			Element urlNode = forceElement(nodeName);
			if (url != null) {
				setAttribute(urlNode, URL, url);
			}
			setTextContent(urlNode, description);
		}
	}

	/**
	 * Gets an element inside a parent element.
	 *
	 * @param parentElement
	 * @param nodeName
	 *            the node name of the element
	 * @param attributeValue
	 *            the value of the element's attribute to retrieve
	 * @return the element or null if it does not exist
	 */
	private Element getElement(final Element parentElement, final String nodeName, final String attributeName, final String attributeValue) {
		NodeList childNodes = parentElement.getChildNodes();

		for (int i = 0; i < childNodes.getLength(); i++) {
			Node item = childNodes.item(i);

			if (nodeName.equals(item.getNodeName())) {
				if (attributeValue.equals(getNodeAttribute(item, attributeName))) {
					if (item instanceof Element) {
						return (Element) item;
					}
				}
			}
		}

		return null;
	}

	private Element getNodeChild(final String childName, final Element node) {
		NodeList childNodes = node.getChildNodes();

		if (childNodes == null) {
			return null;
		}

		for (int i = 0; i < childNodes.getLength(); i++) {
			Node item = childNodes.item(i);

			if (item.getNodeName().equals(childName)) {
				if (item instanceof Element) {
					return (Element) item;
				}
			}
		}

		return null;
	}

	/**
	 * Gets a node element inside the root element.
	 *
	 * @param nodeName
	 *            the node name
	 * @return the node element or null if it does not exist.
	 */
	private Element getNode(final String nodeName) {
		if (featureRoot != null) {
			final NodeList nodes = featureRoot.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				final Node item = nodes.item(i);
				if (item instanceof NodeList) {
					final String name = item.getNodeName();
					if (name.equals(nodeName)) {
						if (item instanceof Element) {
							return (Element) item;
						}
					}
				}
			}
		}

		return null;
	}

	private Element getPlugin(String pluginName) {
		return getElement(featureRoot, PLUGIN, ID, pluginName);
	}

	private Element forcePlugin(String pluginName) {
		Element result = getPlugin(pluginName);
		if (result == null) {
			result = createElement(PLUGIN);
			featureRoot.appendChild(result);
			setAttribute(result, ID, pluginName);
		}
		return result;
	}

	private Element getInclude(String featureName) {
		return getElement(featureRoot, INCLUDES, ID, featureName);
	}

	private Element forceInclude(String featureName) {
		Element result = getInclude(featureName);
		if (result == null) {
			result = createElement(INCLUDES);
			featureRoot.appendChild(result);
			setAttribute(result, ID, featureName);
		}
		return result;
	}

	/**
	 * @param pluginName
	 * @return
	 */
	private Element getRequiredPlugin(String pluginName) {
		Element requires = getNode(REQUIRES);

		if (requires != null) {
			return getElement(requires, IMPORT, PLUGIN, pluginName);
		}

		return null;
	}

	private Element forceRequiredPlugin(String pluginName) {
		Element result = getRequiredPlugin(pluginName);

		if (result == null) {
			result = createElement(IMPORT);
			forceElement(REQUIRES).appendChild(result);
			setAttribute(result, PLUGIN, pluginName);
		}

		return result;
	}

	private String getNodeAttribute(Node node, String name) {
		Node attribute = node.getAttributes().getNamedItem(name);

		return attribute != null ? attribute.getNodeValue() : null;
	}

	private Element getRequiredFeature(String featureName) {
		Element requires = getNode(REQUIRES);

		if (requires != null) {
			return getElement(requires, IMPORT, FEATURE, featureName);
		}

		return null;
	}

	private Element forceRequiredFeature(String featureName) {
		Element result = getRequiredFeature(featureName);

		if (result == null) {
			result = createElement(IMPORT);
			forceElement(REQUIRES).appendChild(result);
			setAttribute(result, FEATURE, featureName);
		}

		return result;
	}

}
