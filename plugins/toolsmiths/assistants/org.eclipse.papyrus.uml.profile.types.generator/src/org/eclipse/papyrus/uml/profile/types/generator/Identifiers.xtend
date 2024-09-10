/*****************************************************************************
 * Copyright (c) 2014, 2018, 2023 Christian W. Damus and others.
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
 * Ansgar Radermacher - Bug 526156, reference semantic base element type,
 *                      Bug 582492, move to com.google.inject
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.profile.types.generator

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.emf.common.notify.AdapterFactory
import org.eclipse.emf.common.util.ResourceLocator
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.edit.provider.IItemLabelProvider
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration
import org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration
import org.eclipse.uml2.uml.Package
import org.eclipse.xtend.lib.annotations.Accessors

import static extension org.eclipse.uml2.common.util.UML2Util.getValidJavaIdentifier

/**
 * Utility extensions for working with and generating unique identifiers in the element types model.
 */
@Singleton
class Identifiers {
    @Inject extension UMLElementTypes

    @Accessors
    String prefix

    @Accessors
    final String umlElementTypesSet = "org.eclipse.papyrus.uml.service.types.UMLElementTypeSet"

    @Accessors
    final String contextId = "org.eclipse.papyrus.infra.services.edit.TypeContext";

    @Accessors
    String baseElementTypesSet = umlElementTypesSet

    @Accessors
    ElementTypeSetConfiguration baseElementTypesSetConfiguration

    @Accessors
    boolean suppressSemanticSuperElementTypes

    @Accessors
    AdapterFactory adapterFactory

    String identifierBase

	boolean useDiPostfix
	
	/**
	 * Constant for postfix that is appended in case of DI element types
	 * @since 2.1
	 */
	public static def String diPostfix() {
		".di"
	}
	
	def void setUseDiPostfix(boolean useDiPostfix) {
		this.useDiPostfix = useDiPostfix	
	}
	
	def boolean useDiPostfix() {
		useDiPostfix
	}
	
    def setIdentifierBase(Package umlPackage) {
        identifierBase = prefix
    }

    def getQualified(String id) {
        identifierBase + "." + id
    }
    
    def toElementTypeID(ImpliedExtension umlExtension, ElementTypeConfiguration supertype) {
        val stereo = umlExtension.stereotype
        var name = if (stereo.allExtendedMetaclasses.size <= 1) stereo.name else stereo.name + "_" + umlExtension.metaclass.name
        name.validJavaIdentifier.qualified + supertype.hintSuffix
    }
    
    /**
     * Return ID of eventually already existing semantic element types. Removes ".di" postfix from
     * the base identifier. This assures that the user can use different IDs for DI and semantic
     * element types - if the naming convention to use a ".di" postfix compared to the semantic element
     * is followed.
	 * @since 2.1    
     */
    def toSemanticElementTypeID(ImpliedExtension umlExtension, ElementTypeConfiguration supertype) {
		val stereo = umlExtension.stereotype
		var name = if (stereo.allExtendedMetaclasses.size <= 1) stereo.name else stereo.name + "_" + umlExtension.metaclass.name
		var baseId = identifierBase
		if (baseId.endsWith(diPostfix)) {
			baseId.substring(0, baseId.length-2) + name.validJavaIdentifier + supertype.hintSuffix
		}
		else {
			name.validJavaIdentifier.qualified + supertype.hintSuffix
		}
	}

    def toElementTypeName(ImpliedExtension umlExtension, ElementTypeConfiguration supertype) {
        val stereo = umlExtension.stereotype
        val discriminators = newArrayList() => [
            if (stereo.extensions.size > 1) add(umlExtension.metaclass.name)    
            if (!supertype.hint.nullOrEmpty && (umlExtension.metaclass.diagramSpecificElementTypes.size > 1)) add(supertype.hint)
        ]
        
        if (discriminators.nullOrEmpty) 
        	if (stereo.allExtendedMetaclasses.size <= 1) stereo.name 
        	else stereo.name + " " + umlExtension.metaclass.name 
        else stereo.name + discriminators.join(" (", ", ", ")")[toString]
    }

    def dispatch hintSuffix(ElementTypeConfiguration elementType) {
        ""
    }

    def dispatch hintSuffix(SpecializationTypeConfiguration elementType) {
        if (elementType.hint.nullOrEmpty) "" else "_" + elementType.hint
    }

    def dispatch getLabel(EObject object) {
        val labels = adapterFactory?.adapt(object, IItemLabelProvider) as IItemLabelProvider
        labels?.getText(object)
    }

    def dispatch getLabel(EClassifier eClassifier) {
        try {
            eClassifier.resourceLocator?.getString("_UI_" + eClassifier.name + "_type")
        } catch (Exception e) {
            eClassifier.name
        }
    }

    private def ResourceLocator getResourceLocator(EObject object) {
        switch adapter : adapterFactory?.adapt(object, IItemLabelProvider) {
            ResourceLocator : adapter
        }
    }
}
