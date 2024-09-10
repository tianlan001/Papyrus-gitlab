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
 * Thibault Le Ouay (Sherpa Engineering) t.leouay@sherpa-eng.com  - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.wizards.template;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;


public abstract class AbstractModelTemplateContentProvider implements IStructuredContentProvider {

	/** The Constant EXTENSION_POINT_ID. */
	protected static final String EXTENSION_POINT_ID = "org.eclipse.papyrus.uml.diagram.wizards.templates"; //$NON-NLS-1$

	/** The Constant ATTRIBUTE_NAME. */
	protected static final String ATTRIBUTE_NAME = "name"; //$NON-NLS-1$

	/** The Constant ATTRIBUTE_FILE. */
	protected static final String ATTRIBUTE_UML_FILE = "file"; //$NON-NLS-1$

	/** The Constant ATTRIBUTE_NOTATION_FILE. */
	protected static final String ATTRIBUTE_NOTATION_FILE = "notation_file"; //$NON-NLS-1$

	/** The Constant ATTRIBUTE_DI_FILE. */
	protected static final String ATTRIBUTE_DI_FILE = "di_file"; //$NON-NLS-1$

	/** The Constant ATTRIBUTE_LANGUAGE. */
	protected static final String ATTRIBUTE_LANGUAGE = "language"; //$NON-NLS-1$

	protected static final String TRANSFO_URI = "Transformation_URI"; //$NON-NLS-1$

	protected static final String SELECT_BY_DEFAULT = "selectedByDefault"; //$NON-NLS-1$

	/** The my template descriptions. */
	protected ModelTemplateDescription[] myTemplateDescriptions;

	/**
	 * Dispose.
	 *
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public void dispose() {

	}

	/**
	 * Gets the templates description.
	 *
	 * @return the templates description
	 */
	protected ModelTemplateDescription[] getTemplatesDescription() {
		if (myTemplateDescriptions == null) {
			List<ModelTemplateDescription> templates = new ArrayList<ModelTemplateDescription>();

			IExtensionRegistry registry = Platform.getExtensionRegistry();
			// get all the extensions configured into the extension point corresponding to the 'templates' Point ID from the 'org.eclipse.papyrus.uml.diagram.wizards' plugin
			IExtension[] extensions = registry.getExtensionPoint(EXTENSION_POINT_ID).getExtensions();

			for (IExtension extension : extensions) {
				templates.addAll(processExtension(extension));
			}
			myTemplateDescriptions = templates.toArray(new ModelTemplateDescription[templates.size()]);
		}
		return myTemplateDescriptions;
	}

	/**
	 * Process extension.
	 *
	 * @param extension
	 *            the extension
	 * @return the collection
	 */
	private Collection<ModelTemplateDescription> processExtension(IExtension extension) {
		List<ModelTemplateDescription> templates = new ArrayList<ModelTemplateDescription>();
		// 'extension.getConfigurationElements()' Returns all configuration elements declared by this extension.
		// These elements are a direct reflection of the configuration markup supplied in the manifest (plugin.xml) file for the plug-in that declares this extension.
		// Each extension can contain multiple elements, e.g. org.eclipse.papyrus.robotml.templaterepository's extension
		for (IConfigurationElement configElement : extension.getConfigurationElements()) {
			// Construct the tempalte from the element's parameters, e.g. template's notation_file, name, ... and transformation's id or name
			ModelTemplateDescription template = new ModelTemplateDescription(configElement.getAttribute(ATTRIBUTE_NAME), extension.getContributor().getName(), configElement.getAttribute(ATTRIBUTE_UML_FILE),
					configElement.getAttribute(ATTRIBUTE_NOTATION_FILE), configElement.getAttribute(ATTRIBUTE_DI_FILE));
			template.setLanguage(configElement.getAttribute(ATTRIBUTE_LANGUAGE));
			template.setTransfo(configElement.getAttribute(TRANSFO_URI));
			template.setSelectedByDefault(Boolean.valueOf(configElement.getAttribute(SELECT_BY_DEFAULT)));
			templates.add(template);
		}
		return templates;
	}

	/**
	 * Gets the elements.
	 *
	 * @param inputElement
	 *            the input element
	 * @return the elements
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	@Override
	public abstract Object[] getElements(Object inputElement);

	/**
	 * Input changed.
	 *
	 * @param viewer
	 *            the viewer
	 * @param oldInput
	 *            the old input
	 * @param newInput
	 *            the new input
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (viewer instanceof TableViewer) {
			((TableViewer) viewer).add(getElements(null));
		}
	}


}
