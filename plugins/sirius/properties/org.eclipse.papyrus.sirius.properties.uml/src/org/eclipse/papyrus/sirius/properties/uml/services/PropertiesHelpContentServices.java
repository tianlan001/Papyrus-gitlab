/*******************************************************************************
 * Copyright (c) 2022 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.uml.services;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.ext.emf.edit.EditingDomainServices;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * This service class includes all services used to get the Help Content tooltip
 * for UML model elements.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class PropertiesHelpContentServices {

	/**
	 * The key of the EAnnotation details map used to get the documentation for each
	 * model element.
	 */
	private static final String DOCUMENTATION_ANNOTATION_DETAILS_KEY = "documentation"; //$NON-NLS-1$

	/**
	 * The example of valid formats for the multiplicity text widget, displayed in the Help content.
	 */
	private static final String MULTIPLICITY_DOC_EXAMPLE = "Example of valid formats: 1, 0..12, 1..*, *"; //$NON-NLS-1$

	/**
	 * The name of the MultiplicityElement type.
	 */
	private static final String MULTIPLICITY_ELEMENT_TYPE = UMLPackage.eINSTANCE.getMultiplicityElement().getName();

	/**
	 * Value of the "source" attribute to get the right EAnnotation which contains
	 * the documentation for each model element.
	 */
	private static final String SOURCE_ANNOTATION_GEN_MODEL = "http://www.eclipse.org/emf/2002/GenModel"; //$NON-NLS-1$

	/**
	 * The pattern used to match additional documentation from superclasses.
	 */
	private static final String SUPERCLASS_DOCUMENTATION_PATTERN = "<p>.*</p>"; //$NON-NLS-1$

	/**
	 * Get help content for a given feature.
	 * 
	 * @param obj
	 *            the object which contains the feature
	 * @param featureName
	 *            the name of the feature with the help to display
	 * @return help content for a given feature.
	 */
	public String getFeatureDescription(EObject obj, String featureName) {
		Objects.requireNonNull(obj);
		EPackage uml2ePackage = UMLDocumentationResource.getInstance().getUml2EPackage();
		String description = null;
		if (uml2ePackage != null) {
			EClassifier eClassifier = uml2ePackage.getEClassifier(obj.eClass().getName());
			if (eClassifier instanceof EClass) {
				EStructuralFeature eStructuralFeature = ((EClass) eClassifier).getEStructuralFeature(featureName);
				if (eStructuralFeature != null) {
					EAnnotation eAnnotation = eStructuralFeature.getEAnnotation(SOURCE_ANNOTATION_GEN_MODEL);
					description = extractDescriptionFromEAnnotation(eAnnotation);
				}
			}
		}
		if (description == null) {
			EditingDomainServices editServices = new EditingDomainServices();
			description = editServices.getPropertyDescriptorDescription(obj, featureName);
		}
		return description;
	}

	/**
	 * Get the help content for the MultiplicityElement type.
	 * 
	 * @param target
	 *            the target that should be a MultiplicityElement
	 * @return the help content for the MultiplicityElement type.
	 */
	public String getMultiplicityHelpContent(EObject target) {
		EPackage uml2ePackage = UMLDocumentationResource.getInstance().getUml2EPackage();
		String description = PropertiesUMLServices.EMPTY;
		if (uml2ePackage != null) {
			EClassifier multiplicityClassifier = uml2ePackage.getEClassifier(MULTIPLICITY_ELEMENT_TYPE);
			EAnnotation eAnnotation = multiplicityClassifier.getEAnnotation(SOURCE_ANNOTATION_GEN_MODEL);
			description = extractDescriptionFromEAnnotation(eAnnotation);
		}
		description = description.concat(MULTIPLICITY_DOC_EXAMPLE);
		return description;
	}

	private String extractDescriptionFromEAnnotation(EAnnotation eAnnotation) {
		String description = PropertiesUMLServices.EMPTY;
		if (eAnnotation != null) {
			description = eAnnotation.getDetails().get(DOCUMENTATION_ANNOTATION_DETAILS_KEY);
			if (description != null) {
				String regex = SUPERCLASS_DOCUMENTATION_PATTERN;
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(description);
				if (m.find()) {
					description = description.replaceAll(m.group(0), PropertiesUMLServices.EMPTY);
				}
			}
		}
		return description;
	}
}
