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
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.papyrus.infra.core.architecture.ArchitectureContext
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDomain
import org.eclipse.papyrus.infra.core.architecture.Concern
import org.eclipse.papyrus.infra.core.architecture.RepresentationKind
import org.eclipse.papyrus.infra.core.architecture.Stakeholder

import static org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage.Literals.*

import static extension org.eclipse.papyrus.infra.core.internal.architecture.merger.ArchitectureExtensions.logf

/**
 * Merge rule for {@link RepresentationKind}s.
 */
@Singleton
class RepresentationKindRule {

	@Inject extension ArchitectureExtensions
	@Inject extension ArchitectureContextRule
	@Inject extension ArchitectureDomainRule
	
	/**
	 * Obtain the corresponding {@code representation} kind in the merge result model.
	 * Representation kinds are not merged <em>per se</em>, but their cross-references
	 * to other architecture model elements are rewritten to reference merged elements
	 * in the merge result model.
	 */
	def RepresentationKind merged(RepresentationKind representation) {
		representation.merged(currentScope) // Unique merge per domain scope
	}
	
	private def create result: EcoreUtil.<RepresentationKind> copy(representation) merged(RepresentationKind representation, Object scope) {
		// Replace the concerns with the merged copies. Also, any other cross-references to
		// architecture elements, e.g. PapyrusDiagram::parent reference. Do this reflectively
		// because we don't know the specifics of the representation kind model
		representation.eClass.EAllReferences
				.reject[containment || container || !changeable]  // Changeable cross-references only
				.filter[AD_ELEMENT.isSuperTypeOf(EReferenceType)] // That can contain mergeable elements
				.forEach[xref |
			
			switch type: xref.EReferenceType {
				case CONCERN.isSuperTypeOf(type): result.eGetAsList(xref, Concern).replaceAll[name.mergedConcern]
				case STAKEHOLDER.isSuperTypeOf(type): result.eGetAsList(xref, Stakeholder).replaceAll[name.mergedStakeholder]
				case REPRESENTATION_KIND.isSuperTypeOf(type): result.eGetAsList(xref, RepresentationKind).replaceAll[merged]
				case ARCHITECTURE_CONTEXT.isSuperTypeOf(type): result.eGetAsList(xref, ArchitectureContext).replaceAll[merged]
				case ARCHITECTURE_DOMAIN.isSuperTypeOf(type): result.eGetAsList(xref, ArchitectureDomain).replaceAll[merged]
			}
		]

		"Copied %s to %s".logf(representation, result)
	}
	
}
