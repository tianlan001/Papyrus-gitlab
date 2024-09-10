package org.eclipse.papyrus.eclipse.project.editors.interfaces;

import org.w3c.dom.Document;

/**
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IFeatureProjectEditor extends IProjectEditor {

	/**
	 * Retrieves the feature.xml XML Document associated to this project.
	 *
	 * @return The XML Document associated to this feature.xml file
	 */
	Document getDocument();

	/**
	 * Gets the feature's id.
	 *
	 * @return the feature's id
	 * @since 2.0
	 */
	String getId();

	/**
	 * Gets the feature's label.
	 *
	 * @return the feature's label
	 */
	String getLabel();

	/**
	 * Gets the feature's version.
	 *
	 * @return the feature's version
	 */
	String getVersion();

	/**
	 * Gets the feature's provider name.
	 *
	 * @return the feature's provider name
	 */
	String getProviderName();

	/**
	 * Gets the operating system of the feature
	 * 
	 * @return
	 * @since 2.0
	 */
	String getOS();

	/**
	 * @since 2.0
	 */
	String getWS();

	/**
	 * @since 2.0
	 */
	String getNL();

	/**
	 * @since 2.0
	 */
	String getArch();

	/**
	 * @since 2.0
	 */
	String getDescriptionText();

	/**
	 * @since 2.0
	 */
	String getDescriptionURL();

	/**
	 * @since 2.0
	 */
	String getCopyrightText();

	/**
	 * @since 2.0
	 */
	String getCopyrightURL();

	/**
	 * @since 2.0
	 */
	String getLicenseText();

	/**
	 * @since 2.0
	 */
	String getLicenseURL();

	/**
	 * @since 2.0
	 */
	void setId(final String id);

	void setLabel(final String label);

	/**
	 * @since 2.0
	 */
	void setVersion(final String version);

	/**
	 * @since 2.0
	 */
	void setProviderName(final String providerName);

	/**
	 * @since 2.0
	 */
	void setOS(final String os);

	/**
	 * @since 2.0
	 */
	void setWS(final String ws);

	/**
	 * @since 2.0
	 */
	void setNL(final String nl);

	/**
	 * @since 2.0
	 */
	void setArch(final String arch);

	/**
	 * @since 2.0
	 */
	void setDescription(final String descriptionURL, final String descriptionDesc);

	/**
	 * @since 2.0
	 */
	void setCopyright(final String copyrightURL, final String copyrightDesc);

	/**
	 * @since 2.0
	 */
	void setLicense(final String licenseURL, final String licenseDesc);

	/**
	 * @since 2.0
	 */
	void setUpdateURL(final String urlLabel, final String url);

	/**
	 * @since 2.0
	 */
	void addPlugin(final String pluginName);

	/**
	 * @since 2.0
	 */
	void addRequiredFeature(final String featureName, final String version);

	/**
	 * @since 2.0
	 */
	void addRequiredPlugin(String pluginName);

	/**
	 * @since 2.0
	 */
	void addInclude(String featureName, String version);

}
