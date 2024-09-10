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
 *  Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 515737
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.nattable.utils.Constants;
import org.eclipse.papyrus.uml.nattable.paste.StereotypeApplicationStructure;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * This class provides useful methods to manage stereotypes in the table.
 * Methods in this class are duplicated from those in oep.uml.tools.utils.StereotypeUtil.
 *
 * @since 4.0
 */
public class UMLNattableStereotypeUtils {

	public static final String PROPERTY_OF_STEREOTYPE_PREFIX = "property_of_stereotype:/"; //$NON-NLS-1$

	/**
	 * Private constructor to prevent instantiation.
	 */
	private UMLNattableStereotypeUtils() {
	}

	/**
	 * @param eobject
	 *            an element of the model (currently, if it is not an UML::Element, we can't find the property)
	 * @param id
	 *            the id used to identify the property of the stereotype
	 *
	 * @return
	 * 		the UML::Property or <code>null</code> if we can't resolve it (the required profile is not applied)
	 */
	public static Property getRealStereotypeProperty(final EObject eobject, final String id) {
		return getRealStereotypeProperty(eobject, id, null);
	}

	/**
	 * @param packages
	 *            a list of package
	 * @param propertyQN
	 *            the qualified name of the wanted property
	 * @return
	 * 		the property or <code>null</code> if not found
	 */
	protected static Property getProperty(Collection<Package> packages, String propertyQN) {
		final String propertyName = getNameFromQualifiedName(propertyQN);
		final String stereotypeQN = getParentQualifiedName(propertyQN);
		final String stereotypeName = getNameFromQualifiedName(stereotypeQN);
		final String profileQN = getParentQualifiedName(stereotypeQN);
		for (Package package1 : packages) {
			for (Profile prof : package1.getAppliedProfiles()) {
				if (prof.getQualifiedName().equals(profileQN)) {
					NamedElement ste = prof.getMember(stereotypeName);
					if (ste instanceof Stereotype) {
						NamedElement prop = ((Stereotype) ste).getMember(propertyName);
						if (prop instanceof Property && prop.getQualifiedName().equals(propertyQN)) {
							return (Property) prop;
						}
					}
				}
				Property p = getProperty(package1.getNestedPackages(), propertyQN);
				if (null != p) {
					return p;
				}
			}
		}
		return null;
	}

	/**
	 * @param eobject
	 *            an element of the model (currently, if it is not an UML::Element, we can't find the property)
	 * @param id
	 *            the id used to identify the property of the stereotype
	 * @param sharedMap
	 *            a map owning interesting information, like {@link StereotypeApplicationStructure} which can be used to find stereotype, stereotype
	 *            application and so on
	 * @return
	 * 		the UML::Property or <code>null</code> if we can't resolve it (the required profile is not applied)
	 */
	public static Property getRealStereotypeProperty(final EObject eobject, final String id, final Map<?, ?> sharedMap) {
		Property result = null;

		Assert.isTrue(id.startsWith(PROPERTY_OF_STEREOTYPE_PREFIX));
		if (eobject instanceof Element) {
			final Element element = (Element) eobject;
			final String propertyQN = id.replace(UMLNattableStereotypeUtils.PROPERTY_OF_STEREOTYPE_PREFIX, ""); //$NON-NLS-1$

			Package nearestPackage = null;
			if (null != sharedMap) {
				final Element context = (Element) sharedMap.get(Constants.PASTED_ELEMENT_CONTAINER_KEY);
				nearestPackage = context.getNearestPackage();
			} else {
				nearestPackage = element.getNearestPackage();
			}

			if (null != nearestPackage) {
				// Search the properties by their qualified name instead of search by its stereotypes first
				// This allows to manage the inherit properties and the stereotypes in packages
				final Iterator<Profile> appliedProfilesIterator = nearestPackage.getAllAppliedProfiles().iterator();
				while (appliedProfilesIterator.hasNext() && null == result) {
					final Profile appliedProfile = appliedProfilesIterator.next();

					// Loop on all stereotypes (check in sub packages)
					final Iterator<Stereotype> stereotypesIterator = getAllStereotypes(appliedProfile).iterator();
					while (stereotypesIterator.hasNext() && null == result) {
						final Stereotype ownedStereotype = stereotypesIterator.next();
						final Iterator<Property> propertiesIterator = ownedStereotype.getAllAttributes().iterator();
						while (propertiesIterator.hasNext() && null == result) {
							final Property property = propertiesIterator.next();
							if (property.getQualifiedName().equals(propertyQN)) {
								result = property;
							}
						}
					}
				}

				// If the property is still not found , the profile could be applied on a sub-package of the nearest package
				// The table can show element which are not children of its context, so the profile could not be available in its context
				if (null == result) {
					result = getProperty(element.getNearestPackage().getNestedPackages(), propertyQN);
				}
			}
		}
		return result;
	}

	/**
	 * This allows to get all stereotypes of a profile (check in sub packages).
	 *
	 * @param profile
	 *            the profile
	 * @return The list of stereotypes corresponding to the profile
	 */
	public static List<Stereotype> getAllStereotypes(final Profile profile) {
		final List<Stereotype> stereotypes = new ArrayList<>(profile.getOwnedStereotypes());
		stereotypes.addAll(getStereotypeInMembers(profile.getOwnedMembers()));
		return stereotypes;
	}

	/**
	 * @param members
	 *            The owned members of an element
	 * @return The list of all stereotypes in sub packages
	 */
	protected static List<Stereotype> getStereotypeInMembers(final List<NamedElement> members) {
		final List<Stereotype> stereotypes = new ArrayList<>();

		// Loop on members
		final Iterator<NamedElement> membersIterator = members.iterator();
		while (membersIterator.hasNext()) {
			NamedElement member = membersIterator.next();

			// Get stereotypes in packages
			if (member instanceof Package) {
				stereotypes.addAll(((Package) member).getOwnedStereotypes());
			}

			// Loop recursively in members
			if (member instanceof Namespace) {
				stereotypes.addAll(getStereotypeInMembers(((Namespace) member).getOwnedMembers()));
			}
		}

		return stereotypes;
	}

	/**
	 * Return the name of an element, given its qualified name.
	 *
	 * @param qualifiedName
	 *            the qualified name of the element
	 * @return the name of the element, must not be <code>null</code>
	 */
	public static String getNameFromQualifiedName(String qualifiedName) {
		String name = qualifiedName.substring(qualifiedName.lastIndexOf(NamedElement.SEPARATOR) + NamedElement.SEPARATOR.length());
		return (null != name) ? name : ""; //$NON-NLS-1$
	}

	/**
	 * @param childQualifiedName
	 *            the qualifiedName of an element
	 * @return
	 * 		the qualified name of its parent
	 */
	public static String getParentQualifiedName(final String childQualifiedName) {
		final String childName = getNameFromQualifiedName(childQualifiedName);
		final String parentQualifiedName = childQualifiedName.substring(0, childQualifiedName.length() - (NamedElement.SEPARATOR.length() + childName.length()));
		return (null != parentQualifiedName) ? parentQualifiedName : ""; //$NON-NLS-1$
	}
}
