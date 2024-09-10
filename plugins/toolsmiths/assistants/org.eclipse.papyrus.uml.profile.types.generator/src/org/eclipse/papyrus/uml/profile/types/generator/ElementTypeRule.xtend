/*****************************************************************************
 * Copyright (c) 2014, 2015, 2018, 2020, 2023 Christian W. Damus and others.
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
 * Ansgar Radermacher - Bug 526155, enable re-generation from profile: copy existing advices
 * Ansgar Radermacher - Bug 526156, reference semantic base element type, bug 582492, move to com.google.inject
 * Camille Letavernier - Bug 569354: remove StereotypeAdvice; use StereotypeMatcherAdvice instead
 * 
 *****************************************************************************/
package org.eclipse.papyrus.uml.profile.types.generator

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsFactory
import org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration
import org.eclipse.papyrus.infra.types.core.impl.ConfiguredHintedSpecializationElementType
import org.eclipse.papyrus.uml.profile.types.generator.strategy.ElementTypeConfigHelper
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.ApplyStereotypeAdviceConfiguration
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherConfiguration
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherFactory
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeMatcherAdviceConfiguration
import org.eclipse.uml2.uml.NamedElement
import org.eclipse.uml2.uml.Stereotype

import static extension org.eclipse.emf.common.util.URI.decode

/**
 * Transformation rule for generating a {@link SpecializationTypeConfiguration} from a UML metaclass {@link Extension}.
 */
@Singleton
class ElementTypeRule {
	static extension ElementTypesConfigurationsFactory elementtypesconfigurationsFactory = ElementTypesConfigurationsFactory.
		eINSTANCE
	static extension StereotypeApplicationMatcherFactory stereotypeApplicationMatcherConfigurationFactory = StereotypeApplicationMatcherFactory.
		eINSTANCE

	@Inject extension UMLElementTypes
	@Inject extension Identifiers
	@Inject extension ElementTypeConfigHelper

	def create createSpecializationTypeConfiguration toElementType(ImpliedExtension umlExtension,
		ElementTypeConfiguration supertype) {

		// Basics
		identifier = umlExtension.toElementTypeID(supertype)
		if (hasSemanticSupertype(supertype)) {
			// try to lookup base type in the registry first
			val baseTypeId = umlExtension.toSemanticElementTypeID(umlExtension.metaclass.elementTypeConfiguration);
			val baseTypeFromRegistry = ElementTypeRegistry.instance.getType(baseTypeId)
			if (baseTypeFromRegistry instanceof ConfiguredHintedSpecializationElementType) {
				// base type found, reference it instead of creating a new one
				val baseType = (baseTypeFromRegistry as ConfiguredHintedSpecializationElementType).configuration
				specializedTypes.add(baseType)
			} else {
				// Add the base semantic type in addition to the parent visual type
				val baseType = createSpecializationTypeConfiguration()
				baseType.identifier = umlExtension.toElementTypeID(umlExtension.metaclass.elementTypeConfiguration)
				baseType.specializedTypes.add(umlExtension.metaclass.elementTypeConfiguration)
				baseType.hint = umlExtension.metaclass.elementTypeConfiguration.hint
				baseType.name = umlExtension.toElementTypeName(umlExtension.metaclass.elementTypeConfiguration)
				// Icon
				var icon = umlExtension.stereotype.iconEntry
				baseType.iconEntry = if(icon != null) icon else umlExtension.metaclass.iconEntry
				val addedBaseType = ConfigurationSetRule.addElementType(baseType)
				specializedTypes.add(addedBaseType)
			}
		}
		specializedTypes.add(supertype)
		hint = supertype.hint
		name = umlExtension.toElementTypeName(supertype)
		setSource(EcoreUtil.getURI(umlExtension.stereotype).toString());

		// copy eventually already existing advices from registry
		val elemTypeFromRegistry = ElementTypeRegistry.instance.getType(identifier)
		if (elemTypeFromRegistry instanceof ConfiguredHintedSpecializationElementType) {
			// existing element type found, copy helper advice, if any
			val elemTypeConfigFromRegistry = elemTypeFromRegistry.configuration
			if (elemTypeConfigFromRegistry instanceof SpecializationTypeConfiguration) {
				val helperAdviceFromRegistry = (elemTypeConfigFromRegistry as SpecializationTypeConfiguration).
					editHelperAdviceConfiguration
				if (helperAdviceFromRegistry != null) {
					editHelperAdviceConfiguration = helperAdviceFromRegistry
				}
			}
		}

		// Icon
		var icon = umlExtension.stereotype.iconEntry
		iconEntry = if(icon != null) icon else umlExtension.metaclass.iconEntry

		// Add stereotype matcher, if it isn't inherited from a semantic supertype
		if (!hasSemanticSupertype(supertype)) {
			matcherConfiguration = umlExtension.toMatcherConfiguration(supertype)
		}
	}

	/**
	 * Change the stereotype qualified name for this type configuration (i.e. modify
	 * the qualified name of the ApplyStereotypeAdvice and StereotypeMatcher)
	 * 
	 * @param typeConfig
	 * 		The element type to edit
	 * @param stereotype
	 * 		The current stereotype, that was renamed
	 */
	def setStereotypeName(ElementTypeConfiguration typeConfig, Stereotype stereotype) {
		val newName = stereotype.qualifiedName;
		val ext = getExtension(typeConfig, stereotype);
		if (ext === null){
			return;
		}
		
		val advices = new ElementTypeConfigHelper().getRelatedAdvices(typeConfig);

		// We only expect element types with a single advice. Otherwise, they probably
		// were not generated by this template, and should be ignored.
		if (advices.size != 1) {
			return;
		}

		val advice = advices.get(0);
		if (advice instanceof ApplyStereotypeAdviceConfiguration) {
			// Older type of generated advice (<= 5.0.0). We probably won't get
			// in this case, as we didn't have a 'source' attribute at this point,
			// so it's difficult to detect renamed stereotypes that need to be updated.
			if (advice.stereotypesToApply.size == 1) {
				val stereotypeToApply = advice.stereotypesToApply.get(0);
				stereotypeToApply.stereotypeQualifiedName = newName;
				if (stereotypeToApply.requiredProfiles.size == 1) {
					val profileName = getProfileName(newName);
					if (profileName != null) {
						stereotypeToApply.requiredProfiles.set(0, profileName);
					}
				}
			}
		} else if (advice instanceof StereotypeMatcherAdviceConfiguration) {
			// New type of generated advice (> 5.0.0)
			if (advice.stereotypesQualifiedNames.size == 1) {
				advice.stereotypesQualifiedNames.set(0, newName);
			}
		}

		// Should be the same as StereotypeMatcherAdviceConfiguration, 
		// as this element is used as both a Matcher and an Advice
		if (typeConfig instanceof SpecializationTypeConfiguration) {
			val matcher = typeConfig.matcherConfiguration;
			if (matcher instanceof StereotypeApplicationMatcherConfiguration) {
				if (matcher.stereotypesQualifiedNames.size == 1) {
					matcher.stereotypesQualifiedNames.set(0, newName);
				}
			}
		}
		
		// Update the name and ID of the ElementType
		
		if (typeConfig instanceof SpecializationTypeConfiguration){
			val supertype = typeConfig.specializedTypes.get(0);
			typeConfig.name = ext.toElementTypeName(supertype);
			typeConfig.identifier = ext.toElementTypeID(supertype);
		}
	}
	
	private def getProfileName(String stereoName){
		val sep = stereoName.lastIndexOf(NamedElement.SEPARATOR);
		if (sep > 0) {
			val profileName = stereoName.substring(0, sep);
			return profileName;
		}
		return null;
	}

	private def create createStereotypeMatcherAdviceConfiguration toMatcherConfiguration(ImpliedExtension umlExtension,
		ElementTypeConfiguration supertype) {

		val umlStereotype = umlExtension.stereotype
		identifier = umlStereotype.name.toFirstLower.qualified + supertype.hintSuffix;
		stereotypesQualifiedNames.add(umlStereotype.qualifiedName)
		description = "Apply Stereotype " + umlStereotype.name
	}

	private def getIconEntry(Stereotype stereotype) {
		val image = stereotype.icons.findFirst[!location.nullOrEmpty]
		if (image != null) {
			val uri = URI.createURI(image.location, true)

			if (uri != null) {
				createIconEntry => [
					if (uri.platform) {

						// Explicit platform-scheme URI
						bundleId = uri.segment(1)
						iconPath = "/" + uri.segmentsList.drop(2).join("/", [decode])
					} else if (uri.relative) {

						// Bundle-relative path.  Infer the bundle ID from the containing project
						bundleId = stereotype.containingProject.name
						iconPath = "/" + uri.toString.decode
					} else {

						// Absolute URI: use as is; don't decode it
						iconPath = uri.toString
					}
				]
			}
		}
	}

	private def containingProject(EObject object) {
		ResourcesPlugin.workspace.root.getProject(object.eResource.URI.segment(2))
	}
}
