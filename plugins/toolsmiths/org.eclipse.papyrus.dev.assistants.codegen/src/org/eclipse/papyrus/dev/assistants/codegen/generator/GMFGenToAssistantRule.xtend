/*****************************************************************************
 * Copyright (c) 2015, 2021, 2023 Christian W. Damus and others.
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
 * Vincent Lorenzo (CEA LIST) - vincent.lorenzo@cea.fr - bug 569174, 570944
 * Ansgar Radermacher - bug 582492, move to com.google.inject
 *****************************************************************************/
package org.eclipse.papyrus.dev.assistants.codegen.generator

import org.eclipse.emf.ecore.EObject
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenChildNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenTopLevelNode
import org.eclipse.papyrus.gmf.codegen.gmfgen.ModelFacet
import org.eclipse.papyrus.infra.filters.Filter
import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantFactory
import org.eclipse.papyrus.uml.profile.assistants.generator.FiltersUtil
import com.google.inject.Inject

/**
 * GMFGen node/link to Assistant transformation rule.
 */
class GMFGenToAssistantRule {
    
    static extension AssistantFactory assistantFactory = AssistantFactory.eINSTANCE

    @Inject extension FiltersUtil
    @Inject extension ModelingAssistantProviderRule

    def cleanedName(String name) {
        var result = name.substring(name.lastIndexOf('.') + 1) // Strip off namespace qualifier, if any
        if (result.matches("^.*_\\d+$")) {
            result = result.substring(0, result.lastIndexOf('_')) // Strip off visual ID suffix, if any
        }
        result = result.replaceAll("([a-z])([A-Z])", "$1 $2") // put space between camel-case words
    }

    def dispatch GenEditorGenerator rootEditor(EObject element) {
        element.eContainer?.rootEditor
    }
    
    def dispatch rootEditor(GenDiagram diagram) {
        diagram.editorGen
    }

    def validNodes(GenDiagram diagram) {
        diagram.allNodes.filter[modelFacet != null]
    }
    
    def validLinks(GenDiagram diagram) {
        diagram.links.filter[modelFacet != null]
    }

    /** Head of the polymorphic transformation. */
    def dispatch toElementTypeFilter(EObject element) {
        null
    }
    
    def dispatch create createElementTypeFilter toElementTypeFilter(GenDiagram diagram) {
        elementTypeID = diagram.elementType.uniqueIdentifier
        name = diagram.domainDiagramElement.formattedName.cleanedName
        
        diagram.editorGen.toModelingAssistantProvider.ownedFilters.add(it)
    }

    private def getElementType(ModelFacet facet) {
        (facet.eContainer as GenCommonBase).elementType
    }

    def dispatch create createElementTypeFilter toElementTypeFilter(ModelFacet modelFacet) {
        elementTypeID = modelFacet.elementType.uniqueIdentifier
        name = modelFacet.elementType.displayName
        
        modelFacet.rootEditor.toModelingAssistantProvider.ownedFilters.add(it)
    }

    /** Head of polymorphic transformation. */
    def dispatch toPopupAssistant(GenNode node) {
        null
    }
    
    def dispatch create createPopupAssistant toPopupAssistant(GenTopLevelNode node) {
        elementTypeID = node.modelFacet.elementType.uniqueIdentifier
        filter = node.diagram.toElementTypeFilter
    }

    def dispatch create createPopupAssistant toPopupAssistant(GenChildNode node) {
        elementTypeID = node.modelFacet.elementType.uniqueIdentifier
        filter = node.createPossibleOwnersFilter.reduce && node.rootEditor.assistedElementTypeFilter
        
        if (filter.isCompound) {
            // I need to own it if I created a new compound
            ownedFilter = filter
        }
    }

    private def createPossibleOwnersFilter(GenChildNode node) {
        // Find all nodes that have a compartment that is a container of the child node
        node.diagram.allNodes.filter[compartments.exists[node.containers.contains(it)]]
                .map[modelFacet?.toElementTypeFilter]
                .filterNull
                .fold(null) [ Filter filter, parentFilter |
            filter || parentFilter
        ]
    }

    def create createConnectionAssistant toConnectionAssistant(GenLink link) {
        elementTypeID = link.modelFacet.elementType.uniqueIdentifier
        sourceFilter = link.createPossibleSourcesFilter().reduce && link.rootEditor.assistedElementTypeFilter
        targetFilter = link.createPossibleTargetsFilter().reduce && link.rootEditor.assistedElementTypeFilter
        
        if (sourceFilter.isCompound) {
            // I need to own it if I created a new compound
            ownedSourceFilter = sourceFilter
        }
        if (targetFilter.isCompound) {
            // I need to own it if I created a new compound
            ownedTargetFilter = targetFilter
        }
    }

    private def createPossibleSourcesFilter(GenLink link) {
        // Don't assist in creating connections from/to connections (links)
        link.sources.filter(GenNode).map[modelFacet?.toElementTypeFilter].filterNull.fold(null) [ Filter filter, parentFilter |
            filter || parentFilter
        ]
    }

    private def createPossibleTargetsFilter(GenLink link) {
        // Don't assist in creating connections from/to connections (links)
        link.targets.filter(GenNode).map[modelFacet?.toElementTypeFilter].filterNull.fold(null) [ Filter filter, parentFilter |
            filter || parentFilter
        ]
    }

    private def create createAssistedElementTypeFilter assistedElementTypeFilter(GenEditorGenerator genEditor) {
        genEditor.toModelingAssistantProvider.ownedFilters.add(it)
    }
}
