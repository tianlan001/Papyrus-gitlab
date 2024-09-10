/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *  Tatiana Fesenko (CEA LIST) - Initial API and implementation
 *	Saadia Dhouib (CEA LIST) - Implementation of loading diagrams from template files  (.uml, .di , .notation)
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.wizards.template;

import org.eclipse.papyrus.uml.diagram.wizards.utils.WizardsHelper;

/**
 * The Class ModelTemplateDescription.
 */
public class ModelTemplateDescription {

	/** The name. */
	private String name;

	/** The uml_path. */
	private String uml_path;

	/** The di_path. */
	private String di_path;

	/** The notation_path. */
	private String notation_path;

	/** The plugin id. */
	private String pluginId;

	/** The language. */
	private String language;

	private String transfoURI;

	private Boolean selectedByDefault;

	/**
	 * Instantiates a new model template description.
	 *
	 * @param name
	 *            the name
	 * @param pluginId
	 *            the plugin id
	 * @param uml_path
	 *            the uml_path
	 * @param notation_path
	 *            the notation_path
	 * @param di_path
	 *            the di_path
	 */
	public ModelTemplateDescription(String name, String pluginId, String uml_path, String notation_path, String di_path) {
		super();
		this.name = name;
		// this.e = metamodelURI;

		this.setDi_path(di_path);
		this.setNotation_path(notation_path);
		this.setUml_path(uml_path);
		this.pluginId = pluginId;

	}

	/**
	 * Sets the language.
	 *
	 * @param language
	 *            the new language
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 */
	public String getFileName() {
		return WizardsHelper.getFileNameWithoutExtension(uml_path);
	}

	/**
	 * Gets the plugin id.
	 *
	 * @return the plugin id
	 */
	public String getPluginId() {
		return pluginId;
	}

	/**
	 * Gets the language.
	 *
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Gets the uml_path.
	 *
	 * @return the uml_path
	 */
	public String getUml_path() {
		return uml_path;
	}

	/**
	 * Sets the uml_path.
	 *
	 * @param uml_path
	 *            the new uml_path
	 */
	public void setUml_path(String uml_path) {
		this.uml_path = uml_path;
	}

	public void setTransfo(String transfoURI) {
		this.transfoURI = transfoURI;
	}

	public String getTransfoURI() {
		return this.transfoURI;
	}

	/**
	 * Gets the di_path.
	 *
	 * @return the di_path
	 */
	public String getDi_path() {
		return di_path;
	}

	/**
	 * Sets the di_path.
	 *
	 * @param di_path
	 *            the new di_path
	 */
	public void setDi_path(String di_path) {
		this.di_path = di_path;
	}

	/**
	 * Gets the notation_path.
	 *
	 * @return the notation_path
	 */
	public String getNotation_path() {
		return notation_path;
	}

	/**
	 * Sets the notation_path.
	 *
	 * @param notation_path
	 *            the new notation_path
	 */
	public void setNotation_path(String notation_path) {
		this.notation_path = notation_path;
	}

	/**
	 * @return the selectedByDefault
	 */
	public Boolean isSelectedByDefault() {
		return selectedByDefault;
	}

	/**
	 * @param selectedByDefault
	 *            the selectedByDefault to set
	 */
	public void setSelectedByDefault(Boolean selectedByDefault) {
		this.selectedByDefault = selectedByDefault;
	}

}
