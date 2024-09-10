/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.nattable.utils;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.papyrus.eclipse.project.editors.project.PluginProjectEditor;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.TableConfiguration;
import org.eclipse.papyrus.toolsmiths.nattable.Activator;
import org.eclipse.papyrus.toolsmiths.nattable.messages.Messages;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 *
 * Utils method to register a TableConfiguration in a plugin.xml
 *
 */
public class TableConfigurationUtils {

	/**
	 * the extension point to use to register a TableConfiguration
	 */
	private static final String EXTENSION_POINT = org.eclipse.papyrus.infra.nattable.nattableconfiguration.NattableConfigurationRegistry.EXTENSION_ID;

	/**
	 * the element in the previous extension point to use to register the TableConfiguration datas
	 */
	private static final String CONFIGURATION = "configuration"; //$NON-NLS-1$

	/**
	 * the file and the path where is the TableConfiguration to register
	 */
	private static final String FILE = org.eclipse.papyrus.infra.nattable.nattableconfiguration.NattableConfigurationRegistry.FILE_ATTRIBUTE;

	/**
	 * the type of the table configuration to register
	 */
	private static final String TYPE = "type"; //$NON-NLS-1$


	/**
	 *
	 * @param project
	 *            the project where register the {@link TableConfiguration}
	 * @param type
	 *            the type of the table configuration
	 * @param tableConfigurationURI
	 *            the URI of the {@link TableConfiguration} file in the plugin
	 * @return
	 *         a {@link IStatus} indicating if the registration in the plugin.xml file worked fine or not
	 */
	public static final IStatus registerTableConfiguration(final IJavaProject project, final String type, final URI tableConfigurationURI) {
		PluginProjectEditor pEditor = null;
		try {
			pEditor = new PluginProjectEditor(project.getProject());
		} catch (ParserConfigurationException | SAXException | IOException | CoreException e) {
			Activator.log.error(e);
			return new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.TableConfigurationUtils_PluginXMLCanBeCreated);
		}

		pEditor.init();
		if (pEditor.getMissingFiles().size() > 0) {
			pEditor.createFiles(pEditor.getMissingFiles());
			pEditor.init();// we need to redo the init
		}

		URI pathInThePlugin = null;
		// i=0 value is "plugin" or "resource" and i=1 is the project name
		for (int i = 2; i < tableConfigurationURI.segmentCount(); i++) {
			if (pathInThePlugin == null) {
				pathInThePlugin = URI.createFileURI(tableConfigurationURI.segment(i));
			} else {
				pathInThePlugin = pathInThePlugin.appendSegment(tableConfigurationURI.segment(i));
			}
		}

		final String pluginPath = pathInThePlugin.path();
		if (isAlreadyRegistered(pEditor, pluginPath, type)) {
			return Status.OK_STATUS;
		}


		final List<Node> extensions = pEditor.getExtensions(EXTENSION_POINT);
		final Element extensionPoint;
		if (extensions.size() > 0) {
			extensionPoint = (Element) extensions.get(extensions.size() - 1);// we will contribute to the last extension point declaration
		} else {
			extensionPoint = pEditor.addExtension(EXTENSION_POINT);
		}



		final Element configuration = pEditor.addChild(extensionPoint, CONFIGURATION);
		pEditor.setAttribute(configuration, FILE, pluginPath);
		pEditor.setAttribute(configuration, TYPE, type);

		pEditor.save();
		return Status.OK_STATUS;
	}

	/**
	 *
	 * @param pEditor
	 *            the plugin editor
	 * @param filePath
	 *            the path to the {@link TableConfiguration}
	 * @param tableType
	 *            the type of the {@link TableConfiguration}
	 * @return
	 *         <code>true</code> if the {@link TableConfiguration} is already registered
	 */
	private static final boolean isAlreadyRegistered(final PluginProjectEditor pEditor, final String filePath, final String tableType) {
		final List<Node> extensions = pEditor.getExtensions(EXTENSION_POINT);
		if (null == extensions) {
			return false;
		}
		for (final Node currentExtention : extensions) {
			if (isAlreadyRegistered(currentExtention, filePath, tableType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 *
	 * @param extension
	 *            an XML node
	 * @param filePath
	 *            the path to the {@link TableConfiguration}
	 * @param tableType
	 *            the type of the {@link TableConfiguration}
	 * @return
	 *         <code>true</code> if the current node register the current {@link TableConfiguration}
	 */
	private static final boolean isAlreadyRegistered(final Node extension, final String filePath, final String tableType) {
		NodeList childNodes = extension.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			final Node a = childNodes.item(i);
			if (null != a && null != a.getAttributes()) {
				final NamedNodeMap attrs = a.getAttributes();
				final Node valu1 = attrs.getNamedItem(FILE);
				final Node values = attrs.getNamedItem(TYPE);
				if (filePath.equals(valu1.getNodeValue()) && tableType.equals(values.getNodeValue())) {
					// already registered
					return true;
				}
			}
		}
		return false;
	}
}
