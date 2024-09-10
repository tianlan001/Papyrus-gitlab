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

package org.eclipse.papyrus.toolsmiths.validation.elementtypes.internal.checkers;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.papyrus.infra.emf.utils.ResourceUtils;
import org.eclipse.papyrus.infra.types.util.ElementTypesConfigurationsSwitch;
import org.eclipse.papyrus.toolsmiths.validation.common.utils.LocalProfileIndex;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.StereotypeToApply;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.util.ApplyStereotypeAdviceSwitch;
import org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.StereotypePropertyReferenceEdgeAdviceConfiguration;
import org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.util.StereotypePropertyReferenceEdgeAdviceSwitch;
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherConfiguration;
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.util.StereotypeApplicationMatcherSwitch;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Computation of referenced UML profiles that need to be packaged in the <tt>build.properties</tt>
 * along with the <em>Element Types Configurations</em> model being validated.
 */
class ElementTypesBuildPropertiesDependencies {

	private final Resource resource;

	/**
	 * Initialize me with the resource to scan for element-types references to profiles (direct and indirect via stereotype names).
	 *
	 * @param resource
	 *            the element types model resource to scan
	 */
	ElementTypesBuildPropertiesDependencies(Resource resource) {
		super();

		this.resource = resource;
	}

	/**
	 * Scan my resource for referenced profiles and return the workspace resources containing those profiles,
	 * where they can be determined to be in the workspace.
	 *
	 * @return the workspace resources containing profiles that are referenced by the element types model
	 */
	Collection<IResource> getDependencies() {
		Set<IResource> result = new HashSet<>();
		LocalProfileIndex index = LocalProfileIndex.getInstance(resource.getResourceSet());

		Set<Profile> profiles = collectProfiles(index, resource.getContents());
		for (Profile next : profiles) {
			// If we found the profile, we must have loaded it from a resource that has an URI
			Resource resource = next.eResource();
			IFile profileFile = ResourceUtils.getFile(resource);
			if (profileFile != null) {
				result.add(profileFile);
			}
		}

		return result;
	}

	private Set<Profile> collectProfiles(LocalProfileIndex index, List<EObject> objects) {
		Set<Profile> result = new HashSet<>();

		Switch<Set<Profile>> applyStereotypeSwitch = new ApplyStereotypeAdviceSwitch<>() {
			@Override
			public Set<Profile> caseStereotypeToApply(StereotypeToApply object) {
				if (object.getStereotypeQualifiedName() != null) {
					Stereotype stereotype = index.getStereotype(object.getStereotypeQualifiedName(), object.getRequiredProfiles(), object);
					if (stereotype != null) {
						result.add(stereotype.containingProfile());
					}
				}

				return result;
			}
		};

		Switch<Set<Profile>> stereotypeMatcherSwitch = new StereotypeApplicationMatcherSwitch<>() {
			public Set<Profile> caseStereotypeApplicationMatcherConfiguration(StereotypeApplicationMatcherConfiguration object) {
				if (object.getProfileUri() != null) {
					Profile profile = index.getProfileByURI(object.getProfileUri(), object);
					if (profile != null) {
						result.add(profile);
					}
				} else {
					object.getStereotypesQualifiedNames().stream()
							.map(name -> index.getStereotype(name, object))
							.filter(Objects::nonNull)
							.map(Stereotype::containingProfile)
							.forEach(result::add);
				}

				return result;
			};
		};

		Switch<Set<Profile>> stereotypeReferenceSwitch = new StereotypePropertyReferenceEdgeAdviceSwitch<>() {
			public Set<Profile> caseStereotypePropertyReferenceEdgeAdviceConfiguration(StereotypePropertyReferenceEdgeAdviceConfiguration object) {
				if (object.getStereotypeQualifiedName() != null) {
					Stereotype stereotype = index.getStereotype(object.getStereotypeQualifiedName(), object);
					if (stereotype != null) {
						result.add(stereotype.containingProfile());
					}
				}

				return result;
			}
		};

		Switch<Set<Profile>> master = new ElementTypesConfigurationsSwitch<>() {

			public Set<Profile> defaultCase(EObject object) {
				object.eContents().forEach(this::doSwitch);
				return result;
			}

			public Set<Profile> doSwitch(EObject eObject) {
				Set<Profile> profiles = applyStereotypeSwitch.doSwitch(eObject);
				if (profiles == null) {
					profiles = stereotypeMatcherSwitch.doSwitch(eObject);
				}
				if (profiles == null) {
					profiles = stereotypeReferenceSwitch.doSwitch(eObject);
				}
				if (profiles == null) {
					profiles = defaultCase(eObject);
				}
				return profiles;
			}

		};

		objects.forEach(master::doSwitch);

		return result;
	}

}
