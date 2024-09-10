/*****************************************************************************
 * Copyright (c) 2015, 2023 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Christian W. Damus - Initial API and implementation
 * Ansgar Radermacher - bug 582492, move to com.google.inject
 * 
 *****************************************************************************/
package org.eclipse.papyrus.dev.assistants.codegen.generator

import org.eclipse.papyrus.infra.types.ElementTypeConfiguration
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration
import org.eclipse.papyrus.infra.filters.Filter
import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantFactory
import org.eclipse.papyrus.uml.profile.assistants.generator.FiltersUtil
import org.eclipse.papyrus.uml.profile.types.generator.UMLElementTypes
import com.google.inject.Inject

/**
 * Element Type to Assistant transformation rule.
 */
class ElementTypeToAssistantRule {
    
    static extension AssistantFactory assistantFactory = AssistantFactory.eINSTANCE

    @Inject extension UMLElementTypes
    @Inject extension FiltersUtil
    @Inject extension ModelingAssistantProviderRule

    def create createPopupAssistant toPopupAssistant(ElementTypeConfiguration type) {
        elementTypeID = type.identifier
        ownedFilter = type.createPossibleOwnersFilter().reduce()
    }

    private def createPossibleOwnersFilter(ElementTypeConfiguration type) {
        baseElementTypes.fold(null) [ Filter filter, elementType |
            if (elementType.canContainType(type))
                filter || elementType.toFilter()
            else
                filter
        ]
    }

    def create createConnectionAssistant toConnectionAssistant(ElementTypeConfiguration type) {
        elementTypeID = type.identifier
        ownedSourceFilter = type.createPossibleSourcesFilter().reduce()
        ownedTargetFilter = type.createPossibleTargetsFilter().reduce()
    }

    def ElementTypeSetConfiguration owningSet(ElementTypeConfiguration elementType) {
        elementType.eContainer as ElementTypeSetConfiguration
    }
    
    def create createElementTypeFilter toFilter(ElementTypeConfiguration elementType) {
        elementTypeID = elementType.identifier
        name = elementType.name
        
        // Shared with all of the assistants
        elementType.owningSet.toModelingAssistantProvider.ownedFilters.add(it)
    }

    private def createPossibleSourcesFilter(ElementTypeConfiguration type) {
        // Don't assist in creating connections from/to connections (relationships)
        baseElementTypes.filter[!relationship].fold(null) [ Filter filter, elementType |
            if (elementType.canSourceToType(type))
                filter || elementType.toFilter
            else
                filter
        ]
    }

    private def createPossibleTargetsFilter(ElementTypeConfiguration type) {
        // Don't assist in creating connections from/to connections (relationships)
        baseElementTypes.filter[!relationship].fold(null) [ Filter filter, elementType |
            if (elementType.canTargetFromType(type))
                filter || elementType.toFilter
            else
                filter
        ]
    }
    
}
