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

import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantFactory
import org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider
import org.eclipse.papyrus.uml.profile.types.generator.Identifiers
import org.eclipse.papyrus.uml.profile.types.generator.UML
import org.eclipse.papyrus.uml.profile.types.generator.UMLElementTypes
import org.eclipse.uml2.uml.Profile
import com.google.inject.Singleton
import com.google.inject.Inject

/**
 * Transformation rule for generating a {@link ModelingAssistantProvider} from a UML {@link Profile}.
 */
@Singleton
class ModelingAssistantProviderRule {
    static extension AssistantFactory assistantFactory = AssistantFactory.eINSTANCE

    @Inject extension Identifiers
    @Inject extension UML
    @Inject extension UMLElementTypes
    @Inject extension FiltersUtil

    @Inject extension PopupAssistantRule
    @Inject extension ConnectionAssistantRule

    def create createModelingAssistantProvider toModelingAssistantProvider(Profile umlProfile) {

        // Initialize the generation of IDs
        umlProfile.setIdentifierBase

        name = umlProfile.name

        umlProfile.allExtensions.forEach [ ext |
            // Add the diagram-specific element types for our profile
            var profileElementTypes = ext.metaclass.diagramSpecificElementTypes.map[ext.toElementTypeID(it)]
            
            // And filters for the same, which the user may employ in edits of the model
            profileElementTypes.forEach[toElementTypeFilter(umlProfile)]
            
            if (!ext.metaclass.EClass.isRelationship) {
                // Popup assistants to create non-relationships
                elementTypeIDs.addAll(profileElementTypes)
                popupAssistants.addAll(ext.metaclass.diagramSpecificElementTypes.map[ext.toPopupAssistant(it)])
            } else {
                // Connection assistants to create relationships
                relationshipTypeIDs.addAll(profileElementTypes)
                connectionAssistants.addAll(ext.metaclass.diagramSpecificElementTypes.map[ext.toConnectionAssistant(it)])
            }
        ]
    }
}
