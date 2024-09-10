/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
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

package org.eclipse.papyrus.toolsmiths.plugin.builder;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.URIConverter;

/**
 * A pluggable service that contributes {@link AbstractPapyrusBuilder}s for various
 * kinds of resources to the {@link PapyrusPluginBuilder}.
 *
 * @since 1.1
 */
public interface IPapyrusBuilderProvider {

	/**
	 * Queries the ID of the marker type under which problems that my builders generate should be recorded.
	 *
	 * @param builderKind
	 *            a builder kind
	 *
	 * @return the problem marker type that I generate for that builder kind
	 */
	String getProblemMarkerType(PapyrusBuilderKind builderKind);

	/**
	 * Query whether I provide a builder of the given kind that validates some particular resource.
	 * This is used by the framework to skip certain generic validation for that resource.
	 *
	 * @param builderKind
	 *            a builder kind
	 * @param resourceURI
	 *            a resource URI
	 * @return whether I provide a builder that covers the resource
	 */
	boolean providesBuilder(PapyrusBuilderKind builderKind, URI resourceURI);

	/**
	 * Query whether I provide a builder of the given kind that validates some particular {@code resource}.
	 * This is used by the framework to skip certain generic validation for that resource.
	 *
	 * @param builderKind
	 *            a builder kind
	 * @param resource
	 *            a resource
	 * @return whether I provide a builder that covers the {@code resource}
	 */
	default boolean providesBuilder(PapyrusBuilderKind builderKind, IResource resource) {
		boolean result = false;

		if (resource.getType() == IResource.FILE) {
			URI uri = URI.createPlatformResourceURI(resource.getFullPath().toPortableString(), true);
			result = providesBuilder(builderKind, uri);
		}

		return result;
	}

	/**
	 * Get a Papyrus builder delegate of the given kind for a {@code project}.
	 *
	 * @param builderKind
	 *            the kind of builder to obtain
	 * @param project
	 *            the project for which to obtain it
	 *
	 * @return the builder, or {@code null} if none
	 */
	AbstractPapyrusBuilder getBuilder(PapyrusBuilderKind builderKind, IProject project);

	/**
	 * Query whether a resource matches a specific content type.
	 *
	 * @param resourceURI
	 *            the URI of the resource to test
	 * @param contentType
	 *            the content type against which to match the resource
	 * @return whether the resource is of the given content type
	 */
	default boolean hasContentType(URI resourceURI, String contentType) {
		boolean result = false;

		try {
			IContentType match = Platform.getContentTypeManager().getContentType(contentType);
			Map<String, ?> description = URIConverter.INSTANCE.contentDescription(resourceURI,
					Map.of(ContentHandler.OPTION_REQUESTED_PROPERTIES, Set.of(ContentHandler.CONTENT_TYPE_PROPERTY)));
			result = Optional.ofNullable((String) description.get(ContentHandler.CONTENT_TYPE_PROPERTY))
					.map(Platform.getContentTypeManager()::getContentType)
					.filter(type -> match != null && type.isKindOf(match))
					.isPresent();
		} catch (IOException e) {
			Activator.log.error("Failed to determine content type of model resource.", e); //$NON-NLS-1$
		}

		return result;
	}

}
