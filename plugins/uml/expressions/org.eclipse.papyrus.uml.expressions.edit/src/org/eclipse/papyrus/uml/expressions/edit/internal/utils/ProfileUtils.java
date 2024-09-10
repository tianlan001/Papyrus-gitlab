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
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.expressions.edit.internal.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Utility class for UML Profile, Stereotype, ...
 */
public class ProfileUtils {

	/**
	 *
	 * @param anEobject
	 *            an eobject
	 * @return
	 *         a collection of UML Profile accessible from the {@link ResourceSet}
	 */
	public static final Collection<Profile> findAttachedRootProfiles(final EObject anEobject) {
		final Set<Profile> profiles = new HashSet<>();
		if (null != anEobject.eResource() && null != anEobject.eResource().getResourceSet()) {
			// we cross the loaded resource to find EPackage in others resource
			final ResourceSet set = anEobject.eResource().getResourceSet();
			final Iterator<Resource> iter = set.getResources().iterator();
			while (iter.hasNext()) {
				final Resource res = iter.next();
				for (EObject eobject : res.getContents()) {
					if (eobject instanceof Profile) {
						profiles.add((Profile) eobject);
					}
				}
			}
		}
		return profiles;
	}

	/**
	 *
	 * @param editedObject
	 *            the edited object (an element of the DocumentStructureTemplate metamodel (or from one of these extension)
	 * @param stereotypeQualifiedName
	 *            a stereotype's qualified name
	 * @return
	 *         the stereotype itself if we found it or <code>null</code>
	 */
	public static final Stereotype findStereotype(final EObject editedObject, final String stereotypeQualifiedName) {
		final List<String> namespaces = Arrays.asList(stereotypeQualifiedName.split("::")); //$NON-NLS-1$
		final Collection<Profile> profiles = ProfileUtils.findAttachedRootProfiles(editedObject);
		// we assume each name is unique for a given depth
		final List<Profile> findProfilesByName = profiles.stream().filter(p -> p.getName().equals(namespaces.get(0))).collect(Collectors.toList());
		if (null != findProfilesByName && findProfilesByName.size() == 1) {
			final Profile profile = findProfilesByName.get(0);
			final ListIterator<String> iter = namespaces.listIterator();
			iter.next();// we bypass the first profile name
			Namespace current = profile;
			while (iter.hasNext()) {
				final NamedElement namedElement = current.getMember(iter.next());
				if (namedElement instanceof Namespace) {
					// it should always be true
					current = (Namespace) namedElement;
				}
			}
			// it is the stereotype
			if (current instanceof Stereotype) {
				return (Stereotype) current;
			}
		}
		return null;
	}


	public static final Profile findProfile(final EObject editedObject, final String profileURI) {
		if (null == profileURI || profileURI.isEmpty()) {
			return null;
		}
		final Collection<Profile> profiles = ProfileUtils.findAttachedRootProfiles(editedObject);
		final Iterator<Profile> iter = profiles.iterator();
		Profile foundProfile = null;
		while (iter.hasNext() && null == foundProfile) {
			foundProfile = PackageUtils.findPackageFromURI(iter.next(), profileURI, Profile.class);
		}
		return foundProfile;
	}

	/**
	 *
	 * @param editedObject
	 *            the edited object (an element of the DocumentStructureTemplate metamodel (or from one of these extension)
	 * @param propertyName
	 *            a stereotype's property's name
	 * @return
	 *         all matching found propertes
	 */
	public static final Collection<Property> findStereotypeProperty(final EObject editedEObject, final String propertyName) {
		if (null == propertyName || propertyName.isEmpty()) {
			return Collections.emptyList();
		}
		final Collection<Property> properties = new ArrayList<>();
		final Collection<Profile> profiles = findAttachedRootProfiles(editedEObject);
		final Iterator<Profile> profileIter = profiles.iterator();
		while (profileIter.hasNext()) {
			final Profile current = profileIter.next();
			final TreeIterator<EObject> allContentsIterator = current.eAllContents();
			while (allContentsIterator.hasNext()) {
				final EObject eobject = allContentsIterator.next();
				if (eobject instanceof Property && eobject.eContainer() instanceof Stereotype && propertyName.equals(((Property) eobject).getName())) {
					properties.add((Property) eobject);
				}
			}
		}
		return properties;
	}




}
