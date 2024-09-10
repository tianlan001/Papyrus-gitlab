/*****************************************************************************
 * Copyright (c) 2014, 2015, 2023 Christian W. Damus and others.
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
package org.eclipse.papyrus.uml.profile.assistants.generator

import org.eclipse.papyrus.infra.filters.CompoundFilter
import org.eclipse.papyrus.infra.filters.Filter
import org.eclipse.papyrus.infra.filters.FiltersFactory
import org.eclipse.papyrus.infra.filters.OperatorKind
import org.eclipse.gmf.runtime.emf.type.core.IElementType
import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantFactory
import org.eclipse.uml2.uml.Profile
import org.eclipse.papyrus.uml.filters.UMLFiltersFactory
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration
import org.eclipse.papyrus.uml.profile.types.generator.UML
import com.google.inject.Singleton
import com.google.inject.Inject

/**
 * Utility extensions for working with {@link Filter}s.
 */
@Singleton
class FiltersUtil {
    static extension FiltersFactory filtersFactory = FiltersFactory.eINSTANCE
    static extension UMLFiltersFactory umlFiltersFactory = UMLFiltersFactory.eINSTANCE
    static extension AssistantFactory assistantFactory = AssistantFactory.eINSTANCE

    @Inject extension UML
    @Inject extension ModelingAssistantProviderRule

    def create createElementTypeFilter toElementTypeFilter(String typeID, Profile umlProfile) {
        elementTypeID = typeID
        name = typeID.substring(typeID.lastIndexOf('.') + 1)
        
        umlProfile.rootProfile.toModelingAssistantProvider.ownedFilters.add(it)
    }
    
    def toFilter(IElementType elementType, Profile umlProfile) {
        elementType.internalToFilter(umlProfile.rootProfile)
    }

    private def create createElementTypeFilter internalToFilter(IElementType elementType, Profile rootProfile) {
        elementTypeID = elementType.id
        name = elementType.displayName
        
        // Shared with all of the assistants
        rootProfile.toModelingAssistantProvider.ownedFilters.add(it)
    }

    def toFilter(ElementTypeConfiguration elementType, Profile umlProfile) {
        elementType.internalToFilter(umlProfile.rootProfile)
    }
    
    private def create createElementTypeFilter internalToFilter(ElementTypeConfiguration elementType, Profile rootProfile) {
        elementTypeID = elementType.identifier
        name = elementType.name
        
        // Shared with all of the assistants
        rootProfile.toModelingAssistantProvider.ownedFilters.add(it)
    }
    
    def create createCompoundFilter toFilter(Profile umlProfile) {
        operator = OperatorKind.OR
        name = "pertains to Profile " + umlProfile.qualifiedName
        ownedFilters.addAll(umlProfile.toAppliedFilter, umlProfile.toAssistedElementTypeFilter)
        
        // Shared with all of the assistants
        umlProfile.rootProfile.toModelingAssistantProvider.ownedFilters.add(it)
    }
    
    private def create createProfileApplied toAppliedFilter(Profile umlProfile) {
        profileURI = EcoreUtil.getURI(umlProfile).toString
        profileQualifiedName = umlProfile.qualifiedName // back-up in case of lost reference
        name = umlProfile.qualifiedName + " is applied in context"
    }
    
    def dispatch andProfileApplied(Void filter, Profile umlProfile) {
        null
    }

    def dispatch andProfileApplied(Filter filter, Profile umlProfile) {
        // Match an object that has the profile applied or is of an element type that is assisted by this model
        // (the latter is useful especially for creating connections to new elements, to determine eligible
        //  elements types to create)
        umlProfile.toFilter && filter
    }

    private def create createAssistedElementTypeFilter toAssistedElementTypeFilter(Profile umlProfile) {
        // No details
    }

    def dispatch isCompound(Void filter) {
        false
    }

    def dispatch isCompound(Filter filter) {
        false
    }

    def dispatch isCompound(CompoundFilter filter) {
        true
    }

    def dispatch reduce(Void filter) {
        null
    }

    def dispatch reduce(Filter filter) {
        filter
    }

    def dispatch reduce(CompoundFilter filter) {
        switch filter.filters.size {
            case 0: null
            case 1: filter.filters.get(0)
            default: filter
        }
    }

    private def add(CompoundFilter compound, Filter other) {
        if (other.eContainer != null) {
            // Sharing the filter
            compound.filters.add(other)
        } else {
            // Owning the filter
            compound.ownedFilters.add(other)
        }
    }
    
    private def addAll(CompoundFilter compound, CompoundFilter other) {
        if (other.eContainer != null) {
            // Sharing the filter's components
            other.filters.forEach[compound.add(it)]
        } else {
            // No need to share with the other
            compound.ownedFilters.addAll(other.ownedFilters)
            
            // This now has removed any that were owned
            compound.filters.addAll(other.filters)
        }
    }

    def dispatch operator_or(Void left, Filter right) {
        right
    }

    def dispatch operator_or(Filter left, Filter right) {
        createOr(left, right)
    }

    private def createOr(Filter left, Filter right) {
        createCompoundFilter => [
            operator = OperatorKind.OR
            add(left)
            add(right)
        ]
    }

    def dispatch operator_or(CompoundFilter left, Filter right) {
        switch left.operator {
            case OR: {
                left.add(right)
                left
            }
            default:
                createOr(left, right)
        }
    }

    def dispatch operator_or(Filter left, CompoundFilter right) {
        switch right.operator {
            case OR: {
                right.add(left)
                right
            }
            default:
                createOr(left, right)
        }
    }

    def dispatch operator_or(CompoundFilter left, CompoundFilter right) {
        switch left.operator {
            case OR: {
                switch right.operator {
                    case OR: {
                        // If the right is owned somewhere, then we should share it as is
                        // because it may yet accumulate more components
                        if (right.eContainer == null) left.addAll(right) else left.add(right)
                    }
                    default: {
                        left.add(right)
                    }
                }
                left
            }
            default:
                createOr(left, right)
        }
    }

    def dispatch operator_and(Void left, Filter right) {
        right
    }

    def dispatch operator_and(Filter left, Filter right) {
        createAnd(left, right)
    }

    private def createAnd(Filter left, Filter right) {
        createCompoundFilter => [
            operator = OperatorKind.AND
            add(left)
            add(right)
        ]
    }

    def dispatch operator_and(CompoundFilter left, Filter right) {
        switch left.operator {
            case AND: {
                left.add(right)
                left
            }
            default:
                createAnd(left, right)
        }
    }

    def dispatch operator_and(Filter left, CompoundFilter right) {
        switch right.operator {
            case AND: {
                right.add(left)
                right
            }
            default:
                createAnd(left, right)
        }
    }

    def dispatch operator_and(CompoundFilter left, CompoundFilter right) {
        switch left.operator {
            case AND: {
                switch right.operator {
                    case AND: {
                        // If the right is owned somewhere, then we should share it as is
                        // because it may yet accumulate more components
                        if (right.eContainer == null) left.addAll(right) else left.add(right)
                    }
                    default: {
                        left.add(right)
                    }
                }
                left
            }
            default:
                createAnd(left, right)
        }
    }
}
