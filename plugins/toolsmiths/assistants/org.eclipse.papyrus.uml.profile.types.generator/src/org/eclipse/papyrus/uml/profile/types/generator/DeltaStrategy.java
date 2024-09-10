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
package org.eclipse.papyrus.uml.profile.types.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.uml2.uml.Extension;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;

/**
 * <p>
 * A (simple) strategy to determine the changes in a Profile since a previous version,
 * used for incremental updates of an {@link ElementTypeSetConfiguration}.
 * </p>
 * <p>
 * The differences are limited to Added/Removed/Renamed {@link Stereotype Stereotypes},
 * as well as Stereotypes with Added/Removed {@link Extension Extensions}.
 * </p>
 */
public interface DeltaStrategy {

	Diff findDiffs(Profile currentProfile, ElementTypeSetConfiguration previousTypes);

	/**
	 * Represents the difference (in terms of Added/Removed/Renamed Stereotypes)
	 * between a current Profile and an {@link ElementTypeSetConfiguration} that
	 * has been generated from this Profile.
	 */
	interface Diff {
		/**
		 * @return
		 *         The list of removed stereotypes, identified by Qualified Name.
		 */
		List<String> getRemovedStereotypes();

		/**
		 * @return
		 *         The list of removed extensions, when the Stereotype still exists (i.e.
		 *         if the stereotype was deleted, use {@link #getRemovedStereotypes()} instead)
		 */
		List<ImpliedExtension> getRemovedExtensions();

		/**
		 * @return
		 *         The list of renamed stereotypes, identified by Qualified Name.
		 *         Each map entry represents the old name as the key, and the current
		 *         Stereotype as the value.
		 */
		Map<String, Stereotype> getRenamedStereotypes();

		/**
		 * @return
		 *         The list of new Stereotypes
		 */
		List<Stereotype> getAddedStereotypes();

		/**
		 * @return
		 *         The list of new Extensions, for Stereotypes that already existed
		 */
		List<ImpliedExtension> getAddedExtensions();

		/**
		 *
		 * @return
		 *         <code>true</code> if this diff doesn't contain any change;
		 *         <code>false</code> if it contains at least one change.
		 */
		default boolean isEmpty() {
			return getRemovedStereotypes().isEmpty() &&
					getRenamedStereotypes().isEmpty() &&
					getAddedStereotypes().isEmpty() &&
					getRemovedExtensions().isEmpty() &&
					getAddedExtensions().isEmpty();
		}
	}

	static class DiffImpl implements Diff {

		final List<String> removedStereotypes = new ArrayList<>();
		final Map<String, Stereotype> renamedStereotypes = new HashMap<>();
		final List<Stereotype> addedStereotypes = new ArrayList<>();
		final List<ImpliedExtension> addedExtensions = new ArrayList<>();
		final List<ImpliedExtension> removedExtensions = new ArrayList<>();

		@Override
		public List<String> getRemovedStereotypes() {
			return removedStereotypes;
		}

		@Override
		public Map<String, Stereotype> getRenamedStereotypes() {
			return renamedStereotypes;
		}

		@Override
		public List<Stereotype> getAddedStereotypes() {
			return addedStereotypes;
		}

		@Override
		public List<ImpliedExtension> getRemovedExtensions() {
			return removedExtensions;
		}

		@Override
		public List<ImpliedExtension> getAddedExtensions() {
			return addedExtensions;
		}

		/**
		 * @see java.lang.Object#toString()
		 *
		 * @return
		 */
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Removed Stereotypes: " + getRemovedStereotypes()).append('\n');
			builder.append("Added Stereotypes: [" + getAddedStereotypes().stream().map(Stereotype::getQualifiedName).collect(Collectors.joining(", "))).append("]\n");
			builder.append("Renamed Stereotypes: " + getRenamedStereotypes()).append('\n');
			builder.append("Added Extensions: [" + getAddedExtensions().stream().map(this::toString).collect(Collectors.joining(", "))).append("]\n");
			builder.append("Removed Extensions: [" + getRemovedExtensions().stream().map(this::toString).collect(Collectors.joining(", "))).append(']');
			return builder.toString();
		}

		private String toString(ImpliedExtension ext) {
			return ext.getStereotype().getQualifiedName() + " -> " + ext.getMetaclass().getName();
		}

	}

}
