/*****************************************************************************
 * Copyright (c) 2014, 2015, 2017, 2020, 2023 Christian W. Damus and others.
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
 * Benoit Maggi       - Bug 474408: order by identifier the generated file
 * Ansgar Radermacher - Bug 526155: set element type name from profile, bug 582492, move to com.google.inject
 * Camille Letavernier - Bug 569354: remove StereotypeAdvice; use StereotypeMatcherAdvice instead
 * 
 *****************************************************************************/
package org.eclipse.papyrus.uml.profile.types.generator

import com.google.inject.Inject
import com.google.inject.Singleton
import java.util.ArrayList
import java.util.List
import java.util.Map
import java.util.Optional
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsFactory
import org.eclipse.papyrus.uml.profile.types.generator.DeltaStrategy.Diff
import org.eclipse.papyrus.uml.profile.types.generator.DeltaStrategy.DiffImpl
import org.eclipse.papyrus.uml.profile.types.generator.strategy.ElementTypeConfigHelper
import org.eclipse.uml2.uml.Profile
import org.eclipse.uml2.uml.Stereotype
import org.eclipse.uml2.uml.UMLPackage

/**
 * <p>
 * Transformation rule for generating an {@link ElementTypeSetConfiguration} from a UML {@link Profile}.
 * </p>
 * <p>
 * Supports incremental (re)generation, if an Optional {@link ElementTypeSetConfiguration} and {@link DeltaStrategy.Diff}
 * are provided.
 * </p>
 */
@Singleton
class ConfigurationSetRule {
	static extension ElementTypesConfigurationsFactory elementtypesconfigurationsFactory = ElementTypesConfigurationsFactory.
		eINSTANCE

	@Inject extension Identifiers
	@Inject extension UML
	@Inject extension UMLElementTypes
	@Inject extension ElementTypeRule
	@Inject extension ElementTypeConfigHelper
	@Inject Optional<DeltaStrategy.Diff> diff;

	static var List<ElementTypeConfiguration> elementTypeConfigurationList

	static def addElementType(ElementTypeConfiguration elementtype) {
		var found = elementTypeConfigurationList.findFirst[el|el.identifier.equals(elementtype.identifier)]
		if (found == null) {
			elementTypeConfigurationList.add(elementtype);
			return elementtype
		} else {
			return found
		}
	}

	/**
	 * <p>
	 * Create or (incrementally) regenerate an ElementTypeSetConfiguration, and populate
	 * it with ElementTypeConfigurations, from the given umlProfile.
	 * </p>
	 * 
	 * @param umlProfile
	 * 		The source profile used to generate the ElementTypeSetConfiguration
	 * @param originalOutput
	 * 		The contents EList from the existing ElementTypeSetConfiguration, or null if we are generating a new Config.
	 */
	def ElementTypeSetConfiguration toConfigurationSet(Profile umlProfile, EList<? super EObject> originalOutput) {
		if (originalOutput != null && ! originalOutput.empty) {
			if (! diff.isEmpty()) {
				val typeSet = originalOutput.
					findFirst[it instanceof ElementTypeSetConfiguration] as ElementTypeSetConfiguration;
				updateElementTypeSet(umlProfile, typeSet, diff.get());
				return typeSet;
			}
			return null;
		} else {
			return newConfigurationSet(umlProfile);
		}
	}

	/**
	 * <p>
	 * Create a new ElementTypeSetConfiguration and populate it with generated ElementTypeConfigurations,
	 * from the given umlProfile.
	 * </p>
	 */
	def create createElementTypeSetConfiguration newConfigurationSet(Profile umlProfile) {

		val newDiff = new DiffImpl();
		newDiff.addedStereotypes.addAll(umlProfile.allStereotypes);
		
		// Initialize the generation of IDs
		umlProfile.setIdentifierBase
		
		// Only set the identifier for non-incremental generations. For incremental generations,
		// keep the existing identifier (Even if the user selected a different prefix in the Wizard)
		identifier = "elementTypes".qualified;

		updateElementTypeSet(umlProfile, it, newDiff);
	}

	def updateElementTypeSet(Profile umlProfile, ElementTypeSetConfiguration typeSet, Diff diff) {

		val helper = new ElementTypeConfigHelper();

		elementTypeConfigurationList = newArrayList()
		
		// Initialize the generation of IDs
		umlProfile.setIdentifierBase

		typeSet.metamodelNsURI = baseUMLElementTypeSet?.metamodelNsURI ?: UMLPackage.eNS_URI;
		
		val addedStereotypes = diff.addedStereotypes as List<Stereotype>
		for (addedStereotype : addedStereotypes) {
			for (ext : addedStereotype.impliedExtensions) {
				for (element : ext.metaclass.diagramSpecificElementTypes) {
					val elementtype = ext.toElementType(element)
					elementTypeConfigurationList.add(elementtype);
				}
			}
		}

		val removedStereotypes = diff.removedStereotypes as List<String>;
		removedStereotypes.forEach [
			if (it == null || it.empty) {
				return;
			}
			// One stereotype may correspond to multiple element types. Remove all of them.
			new ArrayList(typeSet.elementTypeConfigurations).forEach[typeConfig | 
				if (typeConfig.stereotypeName == it) {
					typeSet.elementTypeConfigurations.remove(typeConfig);
				}
			]
		]
		
		val renamedStereotypes = diff.renamedStereotypes as Map<String, Stereotype>;
		for (entry : renamedStereotypes.entrySet) {
			val oldName = entry.key;
			val stereotype = entry.value;
			
			// TODO: Should we manipulate Extensions rather than Stereotypes?
			typeSet.elementTypeConfigurations
				.filter(type | helper.getStereotypeName(type) == oldName)
				.forEach(type | type.setStereotypeName(stereotype));
			
			// TODO Edit corresponding ElementTypeConfigurations (Change ID, StereotypeQName)
		}
		
		val addedExtensions = diff.addedExtensions as List<ImpliedExtension>;
		for (ext : addedExtensions) {
			for (element : ext.metaclass.diagramSpecificElementTypes) {
				val elementtype = ext.toElementType(element)
				elementTypeConfigurationList.add(elementtype);
			}
		}
		
		// Remove all elementTypeConfigurations that correspond to one of the removed extensions
		typeSet.elementTypeConfigurations.removeIf[type | diff.removedExtensions.contains(type.getExtension(umlProfile))]

		typeSet => [
			// set name (otherwise, the element type set remains invalid)
			if (useDiPostfix()) {
				name = umlProfile.name + " DI"
			} else {
				name = umlProfile.name
			}

			elementTypeConfigurations.addAll(elementTypeConfigurationList.sortBy[identifier])
		]
	}
}
