/*****************************************************************************
 * Copyright (c) 2021, 2023 Christian W. Damus, CEA LIST, and others.
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
 *   Ansgar Radermacher - bug 582492, move to com.google.inject
 * 
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.internal.architecture.merger

import com.google.inject.Inject
import com.google.inject.Singleton
import java.util.Set
import org.eclipse.papyrus.infra.core.architecture.ArchitectureContext
import org.eclipse.papyrus.infra.core.architecture.ArchitectureFactory
import org.eclipse.papyrus.infra.core.architecture.ArchitectureViewpoint

import static extension org.eclipse.papyrus.infra.core.internal.architecture.merger.ArchitectureExtensions.logf

/**
 * Merge rule for {@link ArchitectureViewpoint}s.
 */
@Singleton
class ArchitectureViewpointRule {
	static extension ArchitectureFactory factory = ArchitectureFactory.eINSTANCE
	
	@Inject extension ArchitectureExtensions
	@Inject extension RepresentationKindRule
	
	/**
	 * Obtain the merge result for a viewpoint owned by merged {@code contexts}. Viewpoints are merged by name.
	 * If merged previously, that previous merge result is returned.
	 */
	def mergedViewpoint(String name, Set<? extends ArchitectureContext> contexts) {
		name.mergedViewpoint(contexts, currentScope) // Unique merge per domain scope
	}
	
	private def create result: createArchitectureViewpoint mergedViewpoint(String name, Set<? extends ArchitectureContext> contexts, Object scope) {
		contexts.flatMap[viewpoints].named(name).forEach[viewpoint |
			result.copy(viewpoint) => [
				concerns += viewpoint.concerns.mapUnique[it.name].map[mergedConcern]
				representationKinds += viewpoint.representationKinds.map[merged]
			]
			
			"Merged %s into %s".logf(viewpoint, result)
		]
	}
	
}
