/*****************************************************************************
 * Copyright (c) 2011, 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 422257
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - bug 487199
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.newchild;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.ElementCreationMenuModelPackage;
import org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Folder;
import org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.Menu;
import org.eclipse.papyrus.infra.newchild.messages.Messages;
import org.osgi.framework.Bundle;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

/**
 * This class is used to load all extension point call org.eclipse.papyrus.infra.newchild
 * It gives the set of all Folder that has to be displayed
 */
public class CreationMenuRegistry {

	/** The default visibility of folders */
	private static final boolean DEFAULT_VISIBILITY = true;

	/** The registry. */
	protected static volatile CreationMenuRegistry creationMenuRegistry;

	/** The extension id. */
	private static final String MENU_CREATION_MODEL_EXTENSION_ID = "org.eclipse.papyrus.infra.newchild"; //$NON-NLS-1$

	/** The model id. */
	private static final String MODEL_ID = "model"; //$NON-NLS-1$

	/** preferences which contain visible folder */
	private static Preferences preferences;

	/** the folders collection. */
	private static List<Folder> folders = new ArrayList<>();

	/** Default values of folder visibilities */
	private static Map<String, Boolean> defaultValues = new HashMap<>();

	/** the resource set. */
	private static ResourceSetImpl resourceSet;


	/**
	 * returns the singleton instance of this registry
	 *
	 * @return the singleton instance of this registry
	 */
	public static synchronized CreationMenuRegistry getInstance() {
		if (null == creationMenuRegistry) {
			creationMenuRegistry = new CreationMenuRegistry();
			init();
		}
		return creationMenuRegistry;
	}

	/**
	 * Constructor.
	 */
	private CreationMenuRegistry() {
	}

	/**
	 * this method load the extension points and initialize resource set.
	 */
	public static void init() {
		// Obtain a new resource set
		resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(ElementCreationMenuModelPackage.eINSTANCE.getNsURI(), ElementCreationMenuModelPackage.eINSTANCE);

		// Preference init
		preferences = InstanceScope.INSTANCE.getNode(Activator.PLUGIN_ID);

		// Reading data from plugins
		IConfigurationElement[] configElements = Platform.getExtensionRegistry().getConfigurationElementsFor(MENU_CREATION_MODEL_EXTENSION_ID);
		for (int i = 0; i < configElements.length; i++) {
			initializeOneModel(configElements[i]);
		}

	}

	/**
	 * @return the set of root folders
	 */
	public List<Folder> getRootFolder() {
		return folders;
	}

	/**
	 * Return the id of the folder if is loaded.
	 */
	public static String getFolderKey(Folder folder) {
		return EcoreUtil.getURI(folder).trimFragment().toPlatformString(true);
	}

	/**
	 * Load one model.
	 *
	 * @param element
	 *            the extension point
	 * @return the Folder
	 */
	private static Folder initializeOneModel(final IConfigurationElement element) {
		Folder folder = null;
		try {
			folder = createExtension(element, element.getAttribute(MODEL_ID));
		} catch (Exception e) {
			Activator.log.error(Messages.CreationMenuRegistry_Error_ModelCanBeLoaded, e);
		}
		return folder;
	}

	/**
	 * Load the {@link Folder} of the CreationMenuModel from the {@link URI}.
	 * 
	 * @param uri
	 *            the uri of the model file to load
	 * @throws Exception
	 */
	public void loadCreationMenuModel(final URI uri) throws Exception {
		// unload before add it;
		unloadCreationMenuModel(uri);
		try {
			Folder folder = getCreationMenuModel(uri);
			folders.add(folder);
			defaultValues.put(getFolderKey(folder), folder.isVisible());
			folder.setVisible(getPreferedVisibility(folder, DEFAULT_VISIBILITY));

		} catch (URISyntaxException e) {
			throw new Exception(Messages.CreationMenuRegistry_Error_UnableToLoadCreationMenu + e);
		}
	}

	/**
	 * Unload the {@link Folder} of the CreationMenuModel from the {@link URI}.
	 * This is done by removing all {@link Folder} with the same Label.
	 * 
	 * @param uri
	 *            the uri of the model file to unload
	 * @throws Exception
	 */
	public void unloadCreationMenuModel(final URI uri) throws Exception {
		EObject folder = resourceSet.getResource(uri, true).getContents().get(0);
		folders.remove(folder);
		String folderKey = getFolderKey((Folder) folder);
		defaultValues.remove(folderKey);
		preferences.remove(folderKey);
	}

	/**
	 * Load a resource instanceof ElementCreationMenuModel
	 *
	 * @param resourceSet
	 *            the resource set in which to load the menu model
	 * @param element
	 *            the extension point
	 * @param classAttribute
	 *            the name of the resource to load
	 * @return the loaded Folder
	 * @throws Exception
	 *             if the resource is not loaded
	 */
	private static Folder createExtension(final IConfigurationElement element, final String classAttribute) throws Exception {
		Folder folder = null;
		try {
			Bundle extensionBundle = Platform.getBundle(element.getDeclaringExtension().getNamespaceIdentifier());
			URL url = extensionBundle.getResource(classAttribute);
			if (null != url) {
				URI uri = URI.createPlatformPluginURI(element.getContributor().getName() + File.separator + classAttribute, true);

				folder = getCreationMenuModel(uri);
				defaultValues.put(getFolderKey(folder), folder.isVisible());

				String visible = preferences.get(uri.toPlatformString(true), String.valueOf(DEFAULT_VISIBILITY));
				folder.setVisible(Boolean.valueOf(visible));

				folders.add(folder);
			}

		} catch (URISyntaxException e) {
			throw new Exception(Messages.CreationMenuRegistry_Error_UnableToCreateExtension + e);
		}
		return folder;
	}


	/**
	 * @param uri
	 *            The uri of the file of the model.
	 * @return the root {@link Folder} from the model CreationMenuModel.
	 * @throws URISyntaxException
	 */
	private static Folder getCreationMenuModel(final URI uri) throws URISyntaxException {
		Folder folder = null;
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());

		// Get the resource
		Resource resource = resourceSet.getResource(uri, true);
		if (null != resource.getContents() && 0 < resource.getContents().size() && resource.getContents().get(0) instanceof Folder) {
			folder = (Folder) resource.getContents().get(0);
		}

		return folder;
	}

	/**
	 * Set the visibility of a menu by its uri.
	 * 
	 * @param uri
	 *            The uri.
	 * @param visibility
	 *            The visibility to set.
	 */
	public void setCreationMenuVisibility(final URI uri, final Boolean visibility) {
		preferences.put(uri.toPlatformString(true), String.valueOf(visibility));

		EObject eObject = resourceSet.getEObject(uri, true);
		if (eObject instanceof Folder) {
			((Menu) eObject).setVisible(visibility);
		}

		try {
			// forces the application to save the preferences
			preferences.flush();
		} catch (BackingStoreException e) {
			Activator.log.error(e);
		}
	}

	/**
	 * Set the creation menu visibility by its folder model.
	 * 
	 * @param folder
	 *            The folder.
	 * @param visibility
	 *            The visibility to set.
	 */
	public void setCreationMenuVisibility(final Folder folder, final Boolean visibility) {
		setCreationMenuVisibility(EcoreUtil.getURI(folder), visibility);
	}

	/**
	 * Gets the visibility of a {@link Folder}.
	 * 
	 * @param folder
	 *            The folder.
	 * @return The current visibility of the folder.
	 */
	public boolean getCreationMenuVisibility(final Folder folder) {
		String visible = preferences.get(getFolderKey(folder), "");//$NON-NLS-1$
		return visible.isEmpty() ? folder.isVisible() : Boolean.valueOf(visible);// $NON-NLS-1$
	}

	/**
	 * Gets the visibility of a {@link Folder} refereed by its URI.
	 * 
	 * @param folder
	 *            The folder.
	 * @return The current visibility of the folder.
	 */
	public boolean getCreationMenuVisibility(final URI uri) {
		Folder folder = null;
		try {
			folder = getCreationMenuModel(uri);
		} catch (URISyntaxException e) {
			Activator.log.error(e);
		}
		String visible = preferences.get(uri.toPlatformString(true), "");//$NON-NLS-1$
		return visible.isEmpty() ? folder.isVisible() : Boolean.valueOf(visible);// $NON-NLS-1$
	}

	/**
	 * Gets the default value of a {@link Folder}.
	 * 
	 * @param folder
	 *            The folder
	 * @return The default visibility of the folder.
	 */
	public boolean getDefaultCreationMenuVisibility(final Folder folder) {
		Boolean defaultVisibility = defaultValues.get(getFolderKey(folder));
		return null != defaultVisibility ? defaultVisibility : folder.isVisible();
	}

	/**
	 * Gets the default value of a {@link Folder} refereed by its URI.
	 * 
	 * @param folder
	 *            The folder
	 * @return The default visibility of the folder.
	 */
	public boolean getDefaultCreationMenuVisibility(final URI uri) {
		Folder folder = null;
		try {
			folder = getCreationMenuModel(uri);
		} catch (URISyntaxException e) {
			Activator.log.error(e);
		}
		return getDefaultCreationMenuVisibility(folder);
	}

	/**
	 * Gets the visibility of the {@link Folder} set in preferences.
	 * 
	 * @param folder
	 *            The folder.
	 * @param defautVisibility
	 *            The default visibility if it is not set in preferences.
	 * @return
	 * 		The preferred visibility.
	 */
	public boolean getPreferedVisibility(final Folder folder, final boolean defautVisibility) {
		String visible = preferences.get(getFolderKey(folder), String.valueOf(defautVisibility));
		return Boolean.valueOf(visible);
	}

	/**
	 * Gets the visibility of the {@link Folder} refereed by its URI set in preferences.
	 * 
	 * @param folder
	 *            The folder.
	 * @param defautVisibility
	 *            The default visibility if it is not set in preferences.
	 * @return
	 * 		The preferred visibility.
	 */
	public boolean getPreferedVisibility(final URI uri, final boolean defautVisibility) {
		String visible = preferences.get(uri.toPlatformString(true), String.valueOf(defautVisibility));
		return Boolean.valueOf(visible);
	}

	/**
	 * Restore the default visibility value of folders.
	 */
	public void restoreDefault() {
		try {
			preferences.clear();
		} catch (BackingStoreException e) {
			Activator.log.error(e);
		}
		for (Folder folder : folders) {
			folder.setVisible(getDefaultCreationMenuVisibility(folder));
		}
	}

}