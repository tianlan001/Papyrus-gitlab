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

import org.eclipse.papyrus.infra.types.ElementTypeConfiguration
import org.eclipse.papyrus.infra.filters.Filter
import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantFactory
import org.eclipse.papyrus.infra.gmfdiag.assistant.PopupAssistant
import org.eclipse.papyrus.uml.profile.types.generator.Identifiers
import org.eclipse.papyrus.uml.profile.types.generator.UML
import org.eclipse.papyrus.uml.profile.types.generator.UMLElementTypes
import org.eclipse.papyrus.uml.profile.types.generator.ImpliedExtension
import com.google.inject.Singleton
import com.google.inject.Inject

/**
 * Transformation rule for generating a {@link PopupAssistant} from a UML {@link Extension}.
 */
@Singleton
class PopupAssistantRule {
    static extension AssistantFactory assistantFactory = AssistantFactory.eINSTANCE

    @Inject extension Identifiers
    @Inject extension UMLElementTypes
    @Inject extension FiltersUtil
    @Inject extension UML

    def create createPopupAssistant toPopupAssistant(ImpliedExtension umlExtension, ElementTypeConfiguration basetype) {
        elementTypeID = umlExtension.toElementTypeID(basetype)
        ownedFilter = basetype.createPossibleOwnersFilter(umlExtension).reduce().andProfileApplied(umlExtension.profile)
    }

    private def createPossibleOwnersFilter(ElementTypeConfiguration basetype, ImpliedExtension umlExtension) {
        baseElementTypes.fold(null) [ Filter filter, elementType |
            if (elementType.canContainType(basetype))
                filter || elementType.toFilter(umlExtension.profile)
            else
                filter
        ]
    }
}
