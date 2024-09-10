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

package org.eclipse.papyrus.infra.properties.contexts.command;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.contexts.util.ContextUsageCrossReferencer;

/**
 * A specialized deletion command that restricts the scan for incoming references to only
 * within loaded context resources.
 *
 * @since 4.1
 */
public class ContextDeleteCommand extends DeleteCommand {

	/**
	 * Initializes me with my contextual editing {@code domain} and a {@code collection} of objects to delete.
	 *
	 * @param domain
	 *            the editing domain
	 * @param collection
	 *            objects to delete
	 */
	public ContextDeleteCommand(EditingDomain domain, Collection<?> collection) {
		super(domain, collection);
	}

	@Override
	protected Map<EObject, Collection<EStructuralFeature.Setting>> findReferences(Collection<EObject> eObjects) {
		// Scope the search for cross-references to just the context resources, and excluding the XWT sections
		Set<Resource> scope = domain.getResourceSet().getResources().stream()
				.filter(this::isContextResource)
				.collect(Collectors.toSet());

		return new ContextUsageCrossReferencer(scope).findAllUsage(eObjects);
	}

	/**
	 * Query whether a {@code resource} contains a Properties {@link Context}.
	 *
	 * @param resource
	 *            a resource
	 * @return whether it contains a context
	 */
	boolean isContextResource(Resource resource) {
		return resource.isLoaded() && !resource.getContents().isEmpty()
				&& resource.getContents().stream().anyMatch(Context.class::isInstance);
	}

	/**
	 * Create a command to delete an {@code object}.
	 */
	public static Command create(EditingDomain domain, Object object) {
		return create(domain, Set.of(object));
	}

	/**
	 * Create a command to delete an {@code collection} of objects.
	 */
	public static Command create(EditingDomain domain, final Collection<?> collection) {
		return new ContextDeleteCommand(domain, collection);
	}

}
