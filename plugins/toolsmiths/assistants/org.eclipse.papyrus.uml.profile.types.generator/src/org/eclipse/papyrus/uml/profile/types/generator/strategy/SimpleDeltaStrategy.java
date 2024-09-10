/*****************************************************************************
 * Copyright (c) 2020 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.profile.types.generator.strategy;

import java.util.Optional;

import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.uml.profile.types.generator.DeltaStrategy;
import org.eclipse.papyrus.uml.profile.types.generator.ImpliedExtension;
import org.eclipse.papyrus.uml.profile.types.generator.UML;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;

/**
 * A {@link DeltaStrategy} based on stereotype qualified name, URI and Extensions comparison.
 *
 * <ul>
 * <li>A Stereotype is considered "added" if Qualified Name and URI don't match any existing element type.</li>
 * <li>A Stereotype is considered "removed" if Qualified Name and URI of an Element Type don't match any stereotype in the current profile.</li>
 * <li>A Stereotype is considered "renamed" if the Source URI from the Element Type matches the URI of a current sterotype, but qualified names
 * are different.</li>
 * <li>A Stereotype is considered "modified" if it already exists in the current Profile and ElementTypeConfiguration, with a different set of Extensions</li>
 * </ul>
 */
public class SimpleDeltaStrategy implements DeltaStrategy {

	@Override
	public Diff findDiffs(Profile currentProfile, ElementTypeSetConfiguration previousTypes) {
		Diff result = new DiffImpl();
		UML uml = new UML();
		ElementTypeConfigHelper helper = new ElementTypeConfigHelper();
		Iterable<ImpliedExtension> allExtensions = uml.getAllExtensions(currentProfile);

		findAddedStereotypes(currentProfile, previousTypes, result, uml, helper);
		findRemovedStereotypesAndExtensions(currentProfile, previousTypes, result, helper, allExtensions);
		findRenamedSteretoypes(currentProfile, previousTypes, result, helper);
		findAddedExtensions(previousTypes, result, helper, allExtensions);

		return result;
	}

	private void findAddedStereotypes(Profile currentProfile, ElementTypeSetConfiguration previousTypes, Diff result, UML uml, ElementTypeConfigHelper helper) {
		for (Stereotype stereotype : uml.getAllStereotypes(currentProfile)) {
			if (!helper.exists(stereotype, previousTypes)) {
				result.getAddedStereotypes().add(stereotype);
			}
		}
	}

	// Removed Stereotypes and Extensions
	private void findRemovedStereotypesAndExtensions(Profile currentProfile, ElementTypeSetConfiguration previousTypes, Diff result, ElementTypeConfigHelper helper, Iterable<ImpliedExtension> allExtensions) {
		for (ElementTypeConfiguration config : previousTypes.getElementTypeConfigurations()) {
			Optional<Boolean> exists = helper.exists(config, currentProfile);
			if (exists.isEmpty()) {
				// This type config doesn't correspond to a stereotype; ignore it
				continue;
			}
			if (!exists.get()) {
				// The stereotype doesn't exist in the current profile, i.e. it has been deleted
				result.getRemovedStereotypes().add(helper.getStereotypeName(config));
			} else {
				// The stereotype still exists; but some extensions might have been removed
				ImpliedExtension ext = helper.getExtension(config, currentProfile);
				// Test if this element type represents an extension. If not, ignore it.
				if (ext != null && ext.getMetaclass() != null && ext.getStereotype() != null) {
					if (!helper.exists(ext, allExtensions)) {
						// The stereotype exists, but this specific extension was removed
						result.getRemovedExtensions().add(ext);
					}
				}
			}
		}
	}

	// Renamed Stereotypes (or Profiles)
	private void findRenamedSteretoypes(Profile currentProfile, ElementTypeSetConfiguration previousTypes, Diff result, ElementTypeConfigHelper helper) {
		for (ElementTypeConfiguration config : previousTypes.getElementTypeConfigurations()) {
			String originalName = helper.getStereotypeName(config);
			Stereotype currentStereotype = helper.getCurrentStereotype(config, currentProfile);
			if (currentStereotype != null) {
				String currentName = currentStereotype.getQualifiedName();
				if (originalName != null && currentName != null && !originalName.equals(currentName)) {
					result.getRenamedStereotypes().put(originalName, currentStereotype);
				}
			}
		}
	}

	// Identify stereotypes for which Extension(s) have been added
	private void findAddedExtensions(ElementTypeSetConfiguration previousTypes, Diff result, ElementTypeConfigHelper helper, Iterable<ImpliedExtension> allExtensions) {
		for (ImpliedExtension extension : allExtensions) {
			if (result.getAddedStereotypes().contains(extension.getStereotype())) {
				// This is a new Stereotype; we only care about extensions that have
				// been added/removed for an existing stereotype
				continue;
			}
			if (!helper.exists(extension, previousTypes)) {
				result.getAddedExtensions().add(extension);
			}
		}
	}
}
