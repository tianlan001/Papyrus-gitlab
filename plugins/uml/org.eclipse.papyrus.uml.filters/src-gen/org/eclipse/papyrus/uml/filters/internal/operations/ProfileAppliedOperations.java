/**
 * Copyright (c) 2014, 2015 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 */
package org.eclipse.papyrus.uml.filters.internal.operations;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.filters.internal.UMLFiltersPlugin;
import org.eclipse.papyrus.uml.filters.ProfileApplied;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;

import com.google.common.base.Strings;

/**
 * <!-- begin-user-doc -->
 * A static utility class that provides operations related to '<em><b>Profile Applied</b></em>' model objects.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following operations are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.filters.Filter#matches(java.lang.Object) <em>Matches</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.filters.ProfileApplied#resolveProfile(java.lang.Object) <em>Resolve Profile</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProfileAppliedOperations {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ProfileAppliedOperations() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static boolean matches(ProfileApplied profileApplied, Object input) {
		boolean result = false;

		// Don't bother if the filter is invalid
		if (!Strings.isNullOrEmpty(profileApplied.getProfileQualifiedName())) {
			if (!(input instanceof EObject) && (input instanceof IAdaptable)) {
				input = ((IAdaptable) input).getAdapter(EObject.class);
			}

			if (input instanceof Element) {
				Package package_ = ((Element) input).getNearestPackage();
				ResourceSet context = package_.eResource().getResourceSet();

				if (package_ != null) {
					String qualifiedName = Strings.emptyToNull(profileApplied.getProfileQualifiedName());
					URI profileURI = URI.createURI(profileApplied.getProfileURI(), true);
					for (Profile next : package_.getAllAppliedProfiles()) {
						if (prematch(qualifiedName, next)) {
							result = match(profileURI, next, context);
							if (result) {
								break;
							}
						}
					}
				}
			}
		}

		return result;
	}

	/**
	 * Determines whether the first-level filter of a qualified profile name matches
	 * an applied profile, enabling the second-level identity filter.
	 *
	 * @param qualifiedName
	 *            the profile qualified name, or {@code null} if the filter has none
	 * @param profile
	 *            an applied profile, which may be a {@linkplain EObject#eIsProxy() proxy} if not resolved
	 *
	 * @return whether the {@code profile} matches the expected qualified name, or there is
	 *         no expected qualified name
	 */
	protected static boolean prematch(String qualifiedName, Profile profile) {
		boolean result = true;

		// If the profile is a proxy, we can only check the proxy URI against the filter
		if ((qualifiedName != null) && !profile.eIsProxy()) {
			// We should get a proper qualified name from the profile
			result = qualifiedName.equals(profile.getQualifiedName());
		}

		return result;
	}

	/**
	 * Determines whether the second-level filter of a profile object URI matches an
	 * applied profile.
	 *
	 * @param expectedURI
	 *            the object URI of the profile that is expected to be applied
	 * @param profile
	 *            a profile that actually is applied. Must not be {@code null}
	 *            but may be an {@linkplain EObject#eIsProxy() unresolved proxy}
	 * @param context
	 *            the resource set context in which URIs are to be resolved
	 *
	 * @return whether the applied {@code profile} actually is the expected one
	 */
	protected static boolean match(URI expectedURI, Profile profile, ResourceSet context) {
		// Compare normalized URIs because this is how the profile would actually be loaded
		URIConverter converter = context.getURIConverter();
		expectedURI = converter.normalize(expectedURI);
		URI profileURI = converter.normalize(EcoreUtil.getURI(profile));

		return expectedURI.equals(profileURI);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	public static Profile resolveProfile(ProfileApplied profileApplied, Object context) {
		Profile result = null;

		if (!(context instanceof EObject) && (context instanceof IAdaptable)) {
			context = ((IAdaptable) context).getAdapter(EObject.class);
		}

		if ((profileApplied.getProfileURI() != null) && (context instanceof EObject)) {
			Resource resource = ((EObject) context).eResource();
			ResourceSet rset = (resource == null) ? null : resource.getResourceSet();
			if (rset != null) {
				try {
					URI uri = URI.createURI(profileApplied.getProfileURI(), true);
					EObject resolved = rset.getEObject(uri, true);
					if (resolved instanceof Profile) {
						result = (Profile) resolved;
					}
				} catch (Exception e) {
					UMLFiltersPlugin.INSTANCE.log(e);
					// Fine. Unresolved
				}
			}
		}

		return result;
	}

} // ProfileAppliedOperations
