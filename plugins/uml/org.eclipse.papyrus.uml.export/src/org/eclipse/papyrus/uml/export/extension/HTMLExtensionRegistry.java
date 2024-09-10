/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.export.extension;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.papyrus.uml.export.Activator;
import org.eclipse.papyrus.uml.export.HTMLExportHelper;


/**
 * The Class HTMLExtensionRegistry.
 */
public class HTMLExtensionRegistry {

	/** The Constant ORG_ECLIPSE_PAPYRUS_EXPORT_HTML_GENERATE. */
	private static final String ORG_ECLIPSE_PAPYRUS_EXPORT_HTML_GENERATE = "org.eclipse.papyrus.uml.export.generation"; //$NON-NLS-1$ 
	
	/** The Constant ANNOTATION. */
	// keys for exploiting extension point
	private static final String ANNOTATION = "annotation"; //$NON-NLS-1$ 
	
	/** The Constant ADDITIONNAL_INFO. */
	private static final String ADDITIONNAL_INFO = "additionnalInformation"; //$NON-NLS-1$ 
	
	/** The Constant DATA. */
	private static final String DATA = "data";//$NON-NLS-1$ 
	
	/** The Constant KEY. */
	private static final String KEY = "key";//$NON-NLS-1$ 
	
	/** The Constant HTML. */
	private static final String HTML = "html";//$NON-NLS-1$ 
	
	/** The Constant HEADER. */
	private static final String HEADER = "header";//$NON-NLS-1$ 
	
	/** The Constant TXT. */
	private static final String TXT = "text";//$NON-NLS-1$ 
	
	/** The Constant COPYFILE. */
	private static final String COPYFILE = "copyFile";//$NON-NLS-1$ 
	
	/** The Constant FROM. */
	private static final String FROM = "from";//$NON-NLS-1$ 
	
	/** The Constant TO. */
	private static final String TO = "to";//$NON-NLS-1$ 

	/** The instance. */
	private static HTMLExtensionRegistry instance;

	/**
	 * Instantiates a new HTML extension registry.
	 */
	private HTMLExtensionRegistry() {
	}

	/**
	 * Gets the single instance of HTMLExtensionRegistry.
	 *
	 * @return single instance of HTMLExtensionRegistry
	 */
	public static HTMLExtensionRegistry getInstance() {
		if (instance == null) {
			instance = new HTMLExtensionRegistry();
		}
		return instance;
	}

	/**
	 * Gets the all extension.
	 *
	 * @return the all extension
	 */
	public List<IExtension> getAllExtension() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtension[] extensions = registry.getExtensionPoint(ORG_ECLIPSE_PAPYRUS_EXPORT_HTML_GENERATE).getExtensions();
		return Arrays.asList(extensions);
	}

	/**
	 * Gets the helper from prefs.
	 *
	 * @param activatePref the activate pref
	 * @return the helper from prefs
	 */
	public static HTMLExportHelper getHelperFromPrefs(List<String> activatePref) {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = registry.getExtensionPoint(ORG_ECLIPSE_PAPYRUS_EXPORT_HTML_GENERATE);
		IExtension[] extensions = extensionPoint.getExtensions();

		HTMLExportHelper helper = new HTMLExportHelper();
		for (int i = 0; i < extensions.length; i++) {
			String simpleIdentifier = extensions[i].getUniqueIdentifier();
			if (activatePref.contains(simpleIdentifier)) {
				IConfigurationElement[] configurationElements = extensions[i].getConfigurationElements();
				for (int j = 0; j < configurationElements.length; j++) {
					readExtension(configurationElements[j], helper);
				}	
			}

		}
		return helper;
	}
	
	
	/**
	 * Read extension.
	 *
	 * @param configurationElementsFor the configuration elements for
	 * @param helper the helper
	 */
	private static void readExtension(IConfigurationElement configurationElementsFor, HTMLExportHelper helper) {
		String name = configurationElementsFor.getName();
		try {
			if (ANNOTATION.equals(name)) {
				AnnotateSVG createExecutableExtension = (AnnotateSVG) configurationElementsFor
						.createExecutableExtension(ANNOTATION);
				helper.getAnnotations().add(createExecutableExtension);
			}

			if (ADDITIONNAL_INFO.equals(name)) {
				AdditionalInformations createExecutableExtensionAdditionalInformations = (AdditionalInformations) configurationElementsFor
						.createExecutableExtension(DATA);
				String attribute = configurationElementsFor.getAttribute(KEY);
				helper.getAdditionnalDatas().put(attribute, createExecutableExtensionAdditionalInformations);
			}

			if (HTML.equals(name)) {
				String headerD = configurationElementsFor.getAttribute(HEADER);
				String attribute = configurationElementsFor.getAttribute(TXT);
				if (Boolean.parseBoolean(headerD)) {
					helper.getHeaders().add(attribute);
				} else {
					helper.getFooters().add(attribute);
				}
			}

			if (COPYFILE.equals(name)) {
				String from = configurationElementsFor.getAttribute(FROM);
				String to = configurationElementsFor.getAttribute(TO);
				helper.getFromTO().put(from, to);
			}

		} catch (CoreException e) {
			Activator.log(e);
		}
	}

}
