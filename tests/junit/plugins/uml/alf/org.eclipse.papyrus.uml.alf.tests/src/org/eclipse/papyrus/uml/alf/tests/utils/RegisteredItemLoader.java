/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
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
 *  Jeremie Tatibouet
 * 
 *****************************************************************************/
package org.eclipse.papyrus.uml.alf.tests.utils;

import java.util.Iterator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.papyrus.uml.extensionpoints.library.IRegisteredLibrary;
import org.eclipse.papyrus.uml.extensionpoints.library.RegisteredLibrary;
import org.eclipse.papyrus.uml.extensionpoints.profile.IRegisteredProfile;
import org.eclipse.papyrus.uml.extensionpoints.profile.RegisteredProfile;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Profile;

/**
 * Helper providing methods to load registered Papyrus libraries and profiles.
 * These artifact can loaded in specific resource sets or in the one managed by this
 * class.
 */
public class RegisteredItemLoader {

	private static RegisteredItemLoader instance;

	private ResourceSet registeredItemResourceSet;

	private RegisteredItemLoader() {
		this.registeredItemResourceSet = new ResourceSetImpl();
	}

	public static RegisteredItemLoader getInstance() {
		if (instance == null) {
			instance = new RegisteredItemLoader();
		}
		return instance;
	}

	/**
	 * Load a library in the resourceSet managed by this class
	 * 
	 * @param name
	 *            - the searched library
	 * @return library - a reference to the loaded library
	 */
	public PackageableElement getLibrary(final String name) {
		PackageableElement library = null;
		URI libraryURI = this.getRegisteredLibraryURI(name);
		if (libraryURI != null) {
			Resource resource = null;
			Iterator<Resource> resourceIterator = this.registeredItemResourceSet.getResources().iterator();
			while (resource == null && resourceIterator.hasNext()) {
				Resource current = resourceIterator.next();
				if (current.getURI().equals(libraryURI)) {
					resource = current;
				}
			}
			if (resource == null) {
				library = this.loadLibrary(this.registeredItemResourceSet, name);
			} else {
				EObject target = resource.getContents().get(0);
				if (target instanceof PackageableElement) {
					library = (PackageableElement) target;
				}
			}
		}
		return library;
	}

	/**
	 * Load a profile in the resourceSet managed by this class
	 * 
	 * @param name
	 *            - the searched profile
	 * @return profile - a reference to the loaded profile
	 */
	public Profile getProfile(final String name) {
		Profile profile = null;
		URI profileURI = this.getRegisteredProfileURI(name);
		if (profileURI != null) {
			Resource resource = null;
			Iterator<Resource> resourceIterator = this.registeredItemResourceSet.getResources().iterator();
			while (resource == null && resourceIterator.hasNext()) {
				Resource current = resourceIterator.next();
				if (current.getURI().equals(profileURI)) {
					resource = current;
				}
			}
			if (resource == null) {
				profile = this.loadProfile(this.registeredItemResourceSet, name);
			} else {
				EObject target = resource.getContents().get(0);
				if (target instanceof Profile) {
					profile = (Profile) target;
				}
			}
		}
		return profile;
	}

	/**
	 * Return the URI corresponding to the searched library
	 * 
	 * @param name
	 *            - the name of the searched library
	 * @return libraryURI - the URI corresponding to the library
	 */
	private URI getRegisteredLibraryURI(final String name) {
		URI libraryURI = null;
		Iterator<IRegisteredLibrary> librariesIterator = RegisteredLibrary.getRegisteredLibraries().iterator();
		while (libraryURI == null && librariesIterator.hasNext()) {
			IRegisteredLibrary registeredLibrary = librariesIterator.next();
			if (registeredLibrary.getName().equals(name)) {
				libraryURI = registeredLibrary.getUri();
			}
		}
		return libraryURI;
	}

	/**
	 * Return the URI corresponding to the searched profile
	 * 
	 * @param name
	 *            - the name of the profile
	 * @return profileURI - the URI corresponding to the profile
	 */
	private URI getRegisteredProfileURI(final String name) {
		URI profileURI = null;
		IRegisteredProfile registeredProfile = RegisteredProfile.getRegisteredProfile(name);
		if (registeredProfile != null) {
			profileURI = registeredProfile.getUri();
		}
		return profileURI;
	}

	/**
	 * Load the library in the context of the given resourceSet
	 * 
	 * @param context
	 *            - the resource set
	 * @param name
	 *            - the name of the library to load
	 * @return library - a reference to the loaded library (null if not loaded)
	 */
	public PackageableElement loadLibrary(ResourceSet context, final String name) {
		PackageableElement library = null;
		URI libraryURI = this.getRegisteredLibraryURI(name);
		if (libraryURI != null) {
			Resource resource = context.getResource(libraryURI, true);
			if (resource != null) {
				EObject target = resource.getContents().get(0);
				if (target instanceof PackageableElement) {
					library = (PackageableElement) target;
				}
			}
		}
		return library;
	}

	/**
	 * Load the profile in the context of the given resourceSet
	 * 
	 * @param context
	 *            - the resourceSet
	 * @param name
	 *            - the name of the profile to load
	 * @return profile - a reference to the loaded profile (null if not loaded)
	 */
	public Profile loadProfile(ResourceSet context, final String name) {
		Profile profile = null;
		URI profileURI = this.getRegisteredProfileURI(name);
		if (profileURI != null) {
			Resource resource = context.getResource(profileURI, true);
			if (resource != null) {
				EObject target = resource.getContents().get(0);
				if (target instanceof Profile) {
					profile = (Profile) target;
				}
			}
		}
		return profile;
	}

	/**
	 * Unload the resources managed by the class
	 */
	public void clean() {
		for (Resource r : this.registeredItemResourceSet.getResources()) {
			if (r.isLoaded()) {
				r.unload();
			}
		}
		this.registeredItemResourceSet.getResources().clear();
		this.registeredItemResourceSet = null;
	}
	
	public class RequiredElementsNames {
		/* Profiles */
		public final static String STANDARD_PROFILE = "Standard";
		public final static String ACTION_LANGUAGE_PROFILE = "ActionLanguage";
		/* Libraries */
		public final static String ALF_LIBRARY = "Alf Library";
		public final static String UML_PRIMITIVE_TYPES = "UMLPrimitiveTypes";
	}
}
