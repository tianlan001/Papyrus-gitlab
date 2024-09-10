/*******************************************************************************
 * Copyright (c) 2010, 2018 CEA LIST.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     CEA LIST - initial API and implementation
 *     Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 497289
 *     Ansgar Radermacher (CEA LIST) ansgar.radermacher@cea.fr - Bug 528199
 *
 *******************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.definition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.constraints.constraints.JavaQuery;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.Activator;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.DefaultDirectEditorConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.IAdvancedEditorConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.IDirectEditorConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.IDirectEditorConstraint;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.utils.DirectEditorsUtil;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.utils.IDirectEditorsIds;
import org.eclipse.swt.graphics.Image;

/**
 * Represented class for Extension point of Direct Editor.
 */
public class DirectEditorExtensionPoint implements IDirectEditorExtensionPoint {

	/**
	 * Id of the deprecated direct editor configuration extension point.
	 * TODO : This field will be removed in Papyrus 5.0. See bug 541707.
	 *
	 * @deprecated since 1.0.
	 */
	@Deprecated
	private static final String OLD_DIRECT_EDITOR_CONFIGURATION_EXTENSION_ID = "org.eclipse.papyrus.extensionpoints.editors.DirectEditor"; //$NON-NLS-1$

	private static volatile DirectEditorExtensionPoint instance = null;

	/** Array that stores registered transformations */
	private static IDirectEditorExtensionPoint[] configurations;

	private static DirectEditorRegistry directEditorProvider;

	/** value of the language attribute */
	private String language;

	/** value of the superType attribute */
	private boolean superType;

	/** value of the editor configuration attribute */
	private IDirectEditorConfiguration directEditorConfiguration;

	private Class<? extends EObject> objectClassToEdit;

	/** the current priority of the direct editor, can be null **/
	private Integer extensionPriority;

	/** an optional additional constraint to filter the supported elements (In addition to the Metaclass) */
	private IDirectEditorConstraint constraint;

	private DirectEditorExtensionPoint() {
		super();
		init();
	}

	/**
	 * @since 2.0
	 */
	protected void init() {
		// It was not already computed,
		// returns the new Collection of DirectEditorExtensionPoint
		List<DirectEditorExtensionPoint> directEditorExtensionPoints = new ArrayList<>();

		// Reading data from plug-ins
		Collection<IConfigurationElement> configElements = new ArrayList<>();
		configElements.addAll(Arrays.asList(Platform.getExtensionRegistry().getConfigurationElementsFor(IDirectEditorConfigurationIds.DIRECT_EDITOR_CONFIGURATION_EXTENSION_ID)));

		// TODO : This line will be removed in Papyrus 5.0. See bug  541707
		// This manage the old extension point
		configElements.addAll(Arrays.asList(Platform.getExtensionRegistry().getConfigurationElementsFor(OLD_DIRECT_EDITOR_CONFIGURATION_EXTENSION_ID)));

		// Read configuration elements for the current extension
		for (IConfigurationElement configElement : configElements) {
			try {
				DirectEditorExtensionPoint proxy = parseDirectEditorConfiguration(configElement);

				if (proxy != null) {
					directEditorExtensionPoints.add(proxy);
				}
			} catch (Throwable ex) {
				Activator.log.error(ex);
				continue;
			}
		} // end of configElements loop

		configurations = directEditorExtensionPoints.toArray(new DirectEditorExtensionPoint[directEditorExtensionPoints.size()]);
		directEditorProvider = new DirectEditorRegistry();
		directEditorProvider.init(configurations);
	}

	/**
	 * @since 2.0
	 */
	public final synchronized static DirectEditorExtensionPoint getInstance() {
		if (DirectEditorExtensionPoint.instance == null) {
			DirectEditorExtensionPoint.instance = new DirectEditorExtensionPoint();
		}
		return DirectEditorExtensionPoint.instance;
	}

	/**
	 * Returns the set of transformations registered in the platform
	 *
	 * @return the set of transformations registered in the platform
	 */
	public IDirectEditorExtensionPoint[] getDirectEditorConfigurations() {
		return configurations;
	}

	public DirectEditorRegistry getDirectEditorProvider() {
		return directEditorProvider;
	}

	/**
	 * Retrieves the preferred editor configuration for the specified type
	 *
	 * @param class_
	 *            the type of element to edit
	 * @return the preferred editor configuration for the specified type or <code>null</code>
	 * @deprecated Use {@link DirectEditorsUtil#getDefaultDirectEditorExtension(Object, Object)} instead
	 */
	@Deprecated
	public DirectEditorExtensionPoint getDefautDirectEditorConfiguration(EObject semanticObjectToEdit, Object selectedObject) {
		return (DirectEditorExtensionPoint) DirectEditorsUtil.getDefaultDirectEditorExtension(semanticObjectToEdit, selectedObject);

	}

	/**
	 * Returns the set of transformations registered in the platform for the specified kind of
	 * element
	 *
	 * @param the
	 *            type of element to be edited
	 * @return the set of transformations registered in the platform for the specified kind of
	 *         element
	 * @deprecated Use {@link DirectEditorsUtil#getDirectEditorExtensions(Object, Object)} instead
	 */
	@Deprecated
	public Collection<DirectEditorExtensionPoint> getDirectEditorConfigurations(EObject semanticObjectToEdit, Object selectedObject) {
		Collection<IDirectEditorExtensionPoint> directEditorConfigurations = DirectEditorsUtil.getDirectEditorExtensions(semanticObjectToEdit, selectedObject);

		List<DirectEditorExtensionPoint> returnList = new ArrayList<>();
		for (IDirectEditorExtensionPoint extension : directEditorConfigurations) {
			if (extension instanceof DirectEditorExtensionPoint) {
				returnList.add((DirectEditorExtensionPoint) extension);
			}
		}
		return returnList;
	}

	/**
	 * Returns a configuration, given elements from the ConfigurationElement
	 *
	 * @param configElt
	 *            the element that declares the extension
	 * @return a new configuration, given the information of the specified configElt
	 */
	public DirectEditorExtensionPoint parseDirectEditorConfiguration(IConfigurationElement configElt) {

		// check that the ConfigElement is a transformation
		if (!IDirectEditorConfigurationIds.TAG_DIRECT_EDITOR_CONFIGURATION.equals(configElt.getName())) {
			return null;
		}
		// this is a transformation, tries to parse extension, and create the
		// java corresponding
		// class
		try {
			return new DirectEditorExtensionPoint(configElt);
		} catch (Exception e) {
			Activator.log(e);
			return null;
		}
	}

	/**
	 * Creates a new DirectEditorExtensionPoint, according to the ConfigurationElement
	 *
	 * @param configElt
	 *            the configuration element corresponding to the configuration
	 */
	public DirectEditorExtensionPoint(IConfigurationElement configElt) {
		String attribute = getAttribute(configElt, IDirectEditorConfigurationIds.ATT_SUPER_TYPE, "false", false); //$NON-NLS-1$
		superType = attribute.equals("true"); //$NON-NLS-1$

		language = getAttribute(configElt, IDirectEditorConfigurationIds.ATT_LANGUAGE, "undefined", true); // should
		// already
		// be
		// a
		// string
		String objectToEdit = getAttribute(configElt, IDirectEditorConfigurationIds.ATT_OBJECT_TO_EDIT, EObject.class.getCanonicalName(), true);

		directEditorConfiguration = getDirectEditorConfigurationClass(configElt);
		// the constraint maybe null!

		extensionPriority = getPriority(configElt);
		if (directEditorConfiguration == null) {
			directEditorConfiguration = getAdvancedDirectEditorConfigurationClass(configElt);
		}
		// Block added for the case of popup editors
		if (directEditorConfiguration == null) {
			directEditorConfiguration = getPopupDirectEditorConfigurationClass(configElt);
		}
		directEditorConfiguration.setLanguage(language);
		directEditorConfiguration.setSuperType(superType);

		// retrieve the bundle loader of the plugin that declares the extension
		try {
			String pluginID = configElt.getContributor().getName();
			objectClassToEdit = Platform.getBundle(pluginID).loadClass(objectToEdit).asSubclass(EObject.class);
		} catch (ClassNotFoundException e) {
			Activator.log.error(e);
		} catch (ClassCastException e) {
			Activator.log.error(e);
		}

		if (configElt.getAttribute(IDirectEditorConfigurationIds.ATT_ADDITIONAL_CONSTRAINT) != null) {
			try {
				constraint = (IDirectEditorConstraint) configElt.createExecutableExtension(IDirectEditorConfigurationIds.ATT_ADDITIONAL_CONSTRAINT);

			} catch (CoreException ex) {
				Activator.log.error(ex);
			} catch (ClassCastException ex) {
				Activator.log.error(ex);
			}
		}

	}

	protected static IDirectEditorConfiguration getDirectEditorConfigurationClass(IConfigurationElement configElement) {
		IDirectEditorConfiguration configuration = null;
		try {
			if (configElement.getChildren(IDirectEditorConfigurationIds.TAG_SIMPLE_EDITOR).length > 0) {
				Object config = configElement.getChildren(IDirectEditorConfigurationIds.TAG_SIMPLE_EDITOR)[0].createExecutableExtension(IDirectEditorConfigurationIds.ATT_EDITOR_CONFIGURATION);
				if (config instanceof IDirectEditorConfiguration) {
					configuration = (IDirectEditorConfiguration) config;
				}
			}
		} catch (CoreException e) {
			Activator.log(e);
			configuration = new DefaultDirectEditorConfiguration();
		}
		return configuration;
	}



	/**
	 * Try to load a javaQuery defined in the extension point
	 *
	 * @param configElement
	 *            the config element see {@link IConfigurationElement}
	 * @return the java query class see {@link JavaQuery}, can return null because this attribute is optional
	 */
	protected static JavaQuery getJavaQueryClass(IConfigurationElement configElement) {
		JavaQuery javaQuery = null;
		try {
			if (configElement.getAttribute(IDirectEditorConfigurationIds.ATT_CONSTRAINT) == null) {
				return null;
			}
			Object config = configElement.createExecutableExtension(IDirectEditorConfigurationIds.ATT_CONSTRAINT);
			if (config instanceof JavaQuery) {
				javaQuery = (JavaQuery) config;
			}
		} catch (CoreException e) {
			Activator.log(e);
		}
		return javaQuery;
	}

	/**
	 * This priority policy has been defined so that:
	 * - an external contribution is NOT the default editor with the classic configuration (LOW)
	 * - an external contribution can be set to default editor(use High or Highest)
	 *
	 * To achieve that:
	 * - the default priority is set to low
	 * - Papyrus editors priority is set to medium
	 * - Papyrus editors, defined to be the default ones, their priority is set at medium
	 */
	protected static Integer getPriority(IConfigurationElement configElement) {
		try {
			for (IConfigurationElement childConfigElement : configElement.getChildren(IDirectEditorConfigurationIds.ATT_PRIORITY)) {

				String config = getAttribute(childConfigElement, IDirectEditorConfigurationIds.ATT_PRIORITY_NAME, null, true);
				if (config.equals(IDirectEditorConfigurationIds.PRIORITY_HIGHEST)) {
					return new Integer(0);
				}
				if (config.equals(IDirectEditorConfigurationIds.PRIORITY_HIGH)) {
					return new Integer(1);
				}
				if (config.equals(IDirectEditorConfigurationIds.PRIORITY_MEDIUM)) {
					return new Integer(2);
				}
				if (config.equals(IDirectEditorConfigurationIds.PRIORITY_LOW)) {
					return new Integer(3);
				}
				if (config.equals(IDirectEditorConfigurationIds.PRIORITY_LOWEST)) {
					return new Integer(4);
				}
			}
		} catch (Exception e) {
			Activator.log.error(e);
		}
		return new Integer(3); // PRIORITY_LOW
	}


	protected static IAdvancedEditorConfiguration getAdvancedDirectEditorConfigurationClass(IConfigurationElement configElement) {
		IAdvancedEditorConfiguration configuration = null;
		try {
			for (IConfigurationElement childConfigElement : configElement.getChildren(IDirectEditorConfigurationIds.TAG_ADVANCED_EDITOR)) {
				for (String attname : childConfigElement.getAttributeNames()) {
					Activator.log.debug(attname);
				}

				Object config = childConfigElement.createExecutableExtension(IDirectEditorConfigurationIds.ATT_EDITOR_CONFIGURATION);
				if (config instanceof IAdvancedEditorConfiguration) {
					configuration = (IAdvancedEditorConfiguration) config;
				}
			}

		} catch (CoreException e) {
			Activator.log.error(e);
			configuration = null;
		}
		return configuration;
	}

	// /////////////////////////////// TODO:(done) Method added for the case of popup editors
	protected static IDirectEditorConfiguration getPopupDirectEditorConfigurationClass(IConfigurationElement configElement) {
		IDirectEditorConfiguration configuration = null;
		try {
			for (IConfigurationElement childConfigElement : configElement.getChildren(IDirectEditorConfigurationIds.TAG_POPUP_EDITOR)) {
				for (String attname : childConfigElement.getAttributeNames()) {
					Activator.log.debug(attname);
				}

				Object config = childConfigElement.createExecutableExtension(IDirectEditorConfigurationIds.ATT_EDITOR_CONFIGURATION);
				if (config instanceof IDirectEditorConfiguration) {
					configuration = (IDirectEditorConfiguration) config;
				}
			}

		} catch (CoreException e) {
			Activator.log(e);
			configuration = null;
		}
		return configuration;
	}

	// ///////////////////////////////////

	/**
	 * Returns the value of the attribute that has the given name, for the given configuration
	 * element.
	 * <p>
	 * if the attribute has no value, and if default value is not <code>null</code>, it returns defaultValue.
	 * <p>
	 * if it has no value, no default value, and if the attribute is required, it throws an exception.
	 *
	 * @param defaultValue
	 *            the default value (if exists) of the attribute
	 * @param isRequired
	 *            boolean that indicates if this attribute is required
	 * @param configElt
	 *            configuration element that reflects the content of the extension
	 * @param name
	 *            the name of the attribute to read
	 * @return the attribute value
	 */
	protected static String getAttribute(IConfigurationElement configElt, String name, String defaultValue, boolean isRequired) {
		String value = configElt.getAttribute(name);

		if (value != null) {
			return value;
		} else if (defaultValue != null) {
			return defaultValue;
		}

		if (isRequired) {
			throw new IllegalArgumentException("Missing " + name + " attribute"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		return null;
	}

	/**
	 * Icon getter
	 *
	 * @return the icon which path is in extension
	 */
	// @unused
	protected Image getImage(String iconPath, IConfigurationElement configElement) {

		// no image associated to this plug-in
		if (iconPath == null) {
			return null;
		}
		IExtension extension = configElement.getDeclaringExtension();
		String extensionPluginId = extension.getContributor().getName();

		return Activator.getImage(extensionPluginId, iconPath);
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.definition.IDirectEditorExtensionPoint#getLanguage()
	 *
	 * @return
	 */
	@Override
	public String getLanguage() {
		return language;
	}


	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.definition.IDirectEditorExtensionPoint#getObjectToEdit()
	 *
	 * @return
	 */
	@Override
	public String getObjectToEdit() {
		return objectClassToEdit.getCanonicalName();
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.definition.IDirectEditorExtensionPoint#getObjectClassToEdit()
	 *
	 * @return
	 */
	@Override
	public Class<? extends EObject> getObjectClassToEdit() {
		return objectClassToEdit;
	}


	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.definition.IDirectEditorExtensionPoint#getDirectEditorConfiguration()
	 *
	 * @return
	 */
	@Override
	public IDirectEditorConfiguration getDirectEditorConfiguration() {
		return directEditorConfiguration;
	}


	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.definition.IDirectEditorExtensionPoint#setDirectEditorConfiguration(org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.IDirectEditorConfiguration)
	 *
	 * @param directEditorConfiguration
	 */
	@Override
	public void setDirectEditorConfiguration(IDirectEditorConfiguration directEditorConfiguration) {
		this.directEditorConfiguration = directEditorConfiguration;
	}


	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.definition.IDirectEditorExtensionPoint#getPriority()
	 *
	 * @return
	 */
	@Override
	public Integer getPriority() {
		int preferencePriority = getPreferencePriority();
		return preferencePriority != -1 ? preferencePriority : extensionPriority;
	}


	/**
	 * Gets the preference priority.
	 *
	 * @return the preference priority
	 */
	private int getPreferencePriority() {
		int preferencePriority = -1;
		int preference = Activator.getDefault().getPreferenceStore().getInt(IDirectEditorsIds.EDITOR_FOR_ELEMENT + getObjectToEdit() + '.' + language);
		if (IPreferenceStore.INT_DEFAULT_DEFAULT != preference) {
			preferencePriority = preference;
		}
		return preferencePriority;
	}


	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.definition.IDirectEditorExtensionPoint#setPriority(java.lang.Integer)
	 *
	 * @param priority
	 */
	@Override
	public void setPriority(Integer priority) {
		this.extensionPriority = priority;
	}

	/**
	 * Gets the additional constraint.
	 *
	 * @return the additional constraint
	 */
	@Override
	public IDirectEditorConstraint getAdditionalConstraint() {
		return constraint;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.definition.IDirectEditorExtensionPoint#getIcon()
	 *
	 * @return
	 */
	@Override
	public Image getIcon() {
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.definition.IDirectEditorExtensionPoint#isSuperType()
	 *
	 * @return
	 */
	@Override
	public boolean isSuperType() {
		return superType;
	}
}
