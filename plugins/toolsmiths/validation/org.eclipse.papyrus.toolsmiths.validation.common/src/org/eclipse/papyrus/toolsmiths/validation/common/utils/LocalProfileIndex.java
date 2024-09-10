/*****************************************************************************
 * Copyright (c) 2020, 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common.utils;

import static org.eclipse.papyrus.toolsmiths.validation.common.utils.ModelResourceMapper.byExtension;
import static org.eclipse.papyrus.toolsmiths.validation.common.utils.ModelResourceMapper.rootsOfType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.toolsmiths.validation.common.Activator;
import org.eclipse.papyrus.uml.tools.utils.StaticProfileUtil;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.UMLPlugin;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * An index of available profiles that is local to a {@code ResourceSet}.
 * Unlike the <em>Papyrus Profile Index</em>, this doesn't only have profiles in the workspace
 * but also profiles in the target platform.
 */
public class LocalProfileIndex {

	private final ResourceSet resourceSet;
	private boolean workspaceLoaded;

	private final Map<String, Profile> profilesByName = new HashMap<>();
	private final Map<String, Profile> profilesByURI = new HashMap<>();
	private final Map<String, Stereotype> stereotypesByName = new HashMap<>();

	private LocalProfileIndex(ResourceSet resourceSet) {
		super();

		this.resourceSet = resourceSet;
	}

	/**
	 * Get the cached instance of the local profile index from the validation {@code context},
	 * creating it and putting it into the context if necessary.
	 *
	 * @param eObject
	 *            the model context from which to access a resource set (usually the model being validated)
	 * @param context
	 *            the validation context
	 * @return the local profile index
	 */
	public static LocalProfileIndex getInstance(EObject eObject, Map<Object, Object> context) {
		return (LocalProfileIndex) context.computeIfAbsent(LocalProfileIndex.class, __ -> getInstance(eObject.eResource().getResourceSet()));
	}

	/**
	 * Get the cached instance of the local profile index for a resource set.
	 *
	 * @param resourceSet
	 *            the contextual resource set
	 * @return the local profile index
	 */
	public static LocalProfileIndex getInstance(ResourceSet resourceSet) {
		return Attachment.getIndex(resourceSet);
	}

	/**
	 * Get the cached instance of the local profile index for a {@code resource}.
	 *
	 * @param resource
	 *            the contextual resource
	 * @return the local profile index
	 */
	public static Optional<LocalProfileIndex> getInstance(Resource resource) {
		return Optional.ofNullable(resource.getResourceSet()).map(LocalProfileIndex::getInstance);
	}

	/**
	 * Get the cached instance of the local profile index for an {@code object}.
	 *
	 * @param object
	 *            the contextual object
	 * @return the local profile index
	 */
	public static Optional<LocalProfileIndex> getInstance(EObject object) {
		return Optional.ofNullable(object.eResource()).flatMap(LocalProfileIndex::getInstance);
	}

	void reset() {
		workspaceLoaded = false;
		profilesByName.clear();
		profilesByURI.clear();
		stereotypesByName.clear();
	}

	/**
	 * Get a profile by qualified name, in the context of the given model object.
	 *
	 * @param qualifiedName
	 *            the qualified name of the profile to retrieve
	 * @param context
	 *            the model context from which to access a resource set (usually the model being validated)
	 * @return the named profile, or {@code null} if not found
	 */
	public Profile getProfile(String qualifiedName, EObject context) {
		Profile result = profilesByName.get(qualifiedName);

		if (result == null) {
			out: for (URI registeredProfile : UMLPlugin.getEPackageNsURIToProfileLocationMap().values()) {
				try {
					Resource resource = resourceSet.getResource(registeredProfile.trimFragment(), true);
					Collection<Profile> loadedProfiles = EcoreUtil.getObjectsByType(resource.getContents(), UMLPackage.Literals.PROFILE);
					for (Profile profile : loadedProfiles) {
						if (qualifiedName.equals(profile.getQualifiedName())) {
							result = profile;
							profilesByName.put(qualifiedName, result);
							break out;
						}
					}
				} catch (Exception e) {
					Activator.log.error("Failed to load registered profile.", e); //$NON-NLS-1$
				}
			}

			if (result == null) {
				// Look in the workspace
				loadWorkspace();
				result = profilesByName.get(qualifiedName);
			}
		}

		return result;
	}

	/**
	 * Get a profile by URI, in the context of the given model object.
	 *
	 * @param profileURI
	 *            the URI of the profile to retrieve
	 * @param context
	 *            the model context from which to access a resource set (usually the model being validated)
	 * @return the named profile, or {@code null} if not found
	 */
	public Profile getProfileByURI(String profileURI, EObject context) {
		Profile result = profilesByURI.get(profileURI);

		if (result == null) {
			ResourceSet rset = context.eResource().getResourceSet();
			EPackage ePackage = rset.getPackageRegistry().getEPackage(profileURI);
			result = (ePackage != null) ? UMLUtil.getProfile(ePackage, context) : null;

			if (result != null) {
				profilesByURI.put(profileURI, result);
			} else {
				// Look in the workspace
				loadWorkspace();
				result = profilesByURI.get(profileURI);
			}
		}

		return result;
	}

	/**
	 * Get a stereotype by qualified name, in the context of the given model object. With known profile
	 * qualified names as hints, we can optimize the search by looking into registered profiles, first.
	 *
	 * @param qualifiedName
	 *            the qualified name of the stereotype to retrieve
	 * @param profileQualifiedNames
	 *            to try first as hints
	 * @param context
	 *            the model context from which to access a resource set (usually the model being validated)
	 * @return the named stereotype, or {@code null} if not found
	 */
	public Stereotype getStereotype(String qualifiedName, Collection<String> profileQualifiedNames, EObject context) {
		Stereotype result = null;

		// First, search the profiles by URI
		for (Iterator<String> iter = profileQualifiedNames.iterator(); iter.hasNext() && result == null;) {
			// First-pass filter: check that the profile name is a prefix of the stereotype name
			String profileName = iter.next();
			if (!qualifiedName.startsWith(profileName + NamedElement.SEPARATOR)) {
				continue;
			}

			Profile profile = getProfile(profileName, context);
			if (profile != null) {
				result = getStereotype(qualifiedName, profile, context);
			}
		}

		if (result == null) {
			// Didn't find it the easy way? Look in the workspace
			loadWorkspace();
			result = stereotypesByName.get(qualifiedName);
		}

		return result;
	}

	/**
	 * Get a stereotype by qualified name within the scope of a particular {@code profile}.
	 *
	 * @param qualifiedName
	 *            the qualified name of the stereotype to retrieve
	 * @param profile
	 *            a profile to look in
	 * @param context
	 *            the model context from which to access a resource set (usually the model being validated)
	 * @return the named stereotype, or {@code null} if not found
	 */
	public Stereotype getStereotype(String qualifiedName, Profile profile, EObject context) {
		return (Stereotype) UMLUtil.findNamedElements(profile.eResource(), qualifiedName, false, UMLPackage.Literals.STEREOTYPE)
				.stream().findAny().orElse(null);
	}

	/**
	 * Get a stereotype by qualified name within the scope of the resource set containing a given {@code context} object.
	 *
	 * @param qualifiedName
	 *            the qualified name of the stereotype to retrieve
	 * @param context
	 *            the model context from which to access a resource set (usually the model being validated)
	 * @return the named stereotype, or {@code null} if not found
	 */
	public Stereotype getStereotype(String qualifiedName, EObject context) {
		Stereotype result = null;

		int profileNameLength = qualifiedName.lastIndexOf(NamedElement.SEPARATOR);
		// Not a qualified name? Then don't bother looking for it and report the problem because
		// the advice won't be able to find it at run-time
		if (profileNameLength > 0) {
			// Stereotypes don't nest within stereotypes or other classes, but they do nest in packages
			do {
				String profileQualifiedName = qualifiedName.substring(0, profileNameLength);
				Profile profile = getProfile(profileQualifiedName, context);
				if (profile != null) {
					result = getStereotype(qualifiedName, profile, context);
				}
				profileNameLength = profileQualifiedName.lastIndexOf(NamedElement.SEPARATOR);
				if (profileNameLength >= 0) {
					profileQualifiedName = qualifiedName.substring(0, profileNameLength);
				}
			} while (result == null && profileNameLength > 0);
			if (result == null) {
				loadWorkspace();
				result = stereotypesByName.get(qualifiedName);
			}
		}

		return result;
	}

	private void loadWorkspace() {
		if (!workspaceLoaded) {
			workspaceLoaded = true;

			ModelResourceMapper<Profile> profileMapper = new ModelResourceMapper<>(ResourcesPlugin.getWorkspace().getRoot());
			profileMapper.map(byExtension("uml"), __ -> resourceSet, rootsOfType(Profile.class)) //$NON-NLS-1$
					.values().stream().distinct().forEach(this::mapProfile);
		}
	}

	private void mapProfile(Profile profile) {
		String qName = profile.getQualifiedName();
		if (qName != null && !qName.isBlank()) {
			profilesByName.put(qName, profile);

			for (Stereotype stereo : profile.getOwnedStereotypes()) {
				String stereoName = stereo.getQualifiedName();
				if (stereoName != null && !stereoName.isBlank()) {
					stereotypesByName.put(stereoName, stereo);
				}
			}
		}

		String uri = getProfileURI(profile);
		if (uri != null && !uri.isBlank()) {
			profilesByURI.put(uri, profile);
		}

		mapNestedPackages(profile);
	}

	private void mapNestedPackages(org.eclipse.uml2.uml.Package package_) {
		for (org.eclipse.uml2.uml.Package nested : package_.getNestedPackages()) {
			if (nested instanceof Profile) {
				mapProfile((Profile) nested);
			} else {
				// Stereotypes nested in regular packages within the profile can also be applied in models
				mapPackage(nested);
			}
		}
	}

	private void mapPackage(org.eclipse.uml2.uml.Package package_) {
		for (Stereotype stereo : package_.getOwnedStereotypes()) {
			String stereoName = stereo.getQualifiedName();
			if (stereoName != null && !stereoName.isBlank()) {
				stereotypesByName.put(stereoName, stereo);
			}
		}

		mapNestedPackages(package_);
	}

	/**
	 * Get the namespace URI of a {@code profile}, in order of preference (as in UML2 code generation):
	 * <ul>
	 * <li>the namespace URI set on an applied <tt>«ePackage»</tt> stereotype</li>
	 * <li>the intrinsic UML {@linkplain org.eclipse.uml2.uml.Package#getURI() package URI}</li>
	 * <li>the namespace URI generated by the EMF Model Importer base on the model resource name</li>
	 * </ul>
	 *
	 * @param profile
	 *            a profile
	 * @return the the profile namespace URI
	 */
	private static String getProfileURI(Profile profile) {
		return new StaticProfileUtil(profile).getDefinition().getNsURI();
	}

	//
	// Nested types
	//

	private static final class Attachment extends AdapterImpl {
		private final LocalProfileIndex index;

		Attachment(LocalProfileIndex index) {
			super();

			this.index = index;
		}

		static LocalProfileIndex getIndex(ResourceSet rset) {
			Attachment attachment = (Attachment) EcoreUtil.getAdapter(rset.eAdapters(), LocalProfileIndex.class);
			if (attachment == null) {
				attachment = new Attachment(new LocalProfileIndex(rset));
				rset.eAdapters().add(attachment);
			}
			return attachment.getIndex();
		}

		@Override
		public boolean isAdapterForType(Object type) {
			return type == LocalProfileIndex.class;
		}

		LocalProfileIndex getIndex() {
			return index;
		}

		@Override
		public void notifyChanged(Notification msg) {
			if (msg.getNotifier() instanceof ResourceSet && msg.getFeatureID(ResourceSet.class) == ResourceSet.RESOURCE_SET__RESOURCES) {
				getIndex().reset();
			}
		}
	}
}
