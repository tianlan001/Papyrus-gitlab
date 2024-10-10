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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * This service class includes all services used for multiple selection.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class PropertiesMultipleSelectionServices {

	/**
	 * The name of the Boolean class.
	 */
	private static final String BOOLEAN_CLASS_NAME = "Boolean"; //$NON-NLS-1$

	/**
	 * The service used to set values and create elements.
	 */
	private PropertiesCrudServices propertiesServices = new PropertiesCrudServices();

	/**
	 * Get all common features for selected Objects list.
	 * 
	 * @param selectedObjects
	 *            the list of semantic objects for which we are looking for common features
	 * @return the list of common features for selected Objects list
	 */
	public List<EStructuralFeature> getCommonFeatures(List<EObject> selectedObjects) {
		List<EStructuralFeature> commonFeatures = getBasicCommonFeatures(selectedObjects);
		Set<EClass> classifiers = selectedObjects.stream().map(object -> object.eClass()).collect(Collectors.toSet());

		commonFeatures = MultipleSelectionViewpointExplorer.getInstance().keepDisplayableFeatures(classifiers, commonFeatures);
		commonFeatures.sort(Comparator.comparing(feature -> feature.getName()));
		commonFeatures.sort(Comparator.comparing(feature -> feature.getEType().eClass().getName()));
		return commonFeatures;
	}

	private List<EStructuralFeature> getBasicCommonFeatures(List<EObject> selectedObjects) {
		List<EStructuralFeature> commonFeatures = new ArrayList<>();
		Map<EStructuralFeature, Integer> mapFeatures = new HashMap<>();
		for (EObject eObject : selectedObjects) {
			EList<EStructuralFeature> allFeatures = eObject.eClass().getEAllStructuralFeatures();
			for (EStructuralFeature feature : allFeatures) {
				if (feature.getEType() instanceof EEnum || feature.getEType() instanceof EDataType) {
					Integer occurences;
					if (mapFeatures.get(feature) != null) {
						occurences = mapFeatures.get(feature);
					} else {
						occurences = Integer.valueOf(0);
					}
					mapFeatures.put(feature, Integer.valueOf(occurences.intValue() + 1));
				}
			}
		}
		for (Entry<EStructuralFeature, Integer> entry : mapFeatures.entrySet()) {
			if (entry.getValue().intValue() == selectedObjects.size()) {
				commonFeatures.add(entry.getKey());
			}
		}
		return commonFeatures;
	}

	/**
	 * Format the name of a feature.
	 * Concatenated words are separated by space and word after space begin with lower letter.
	 * Example: "isAbstract" => "is abstract".
	 * 
	 * @param name
	 *            the name of the feature to format
	 * @return the formatted name, with spaces and no upper case
	 */
	public String formatName(String name) {
		String formattedName = PropertiesUMLServices.EMPTY;
		if (name != null) {
			for (int i = 0; i < name.length(); i++) {
				String currentChar = String.valueOf(name.charAt(i));
				if (i != 0 && currentChar == currentChar.toUpperCase()) {
					currentChar = PropertiesUMLServices.SPACE + currentChar.toLowerCase();
				}
				formattedName = formattedName.concat(currentChar);
			}
		}
		return formattedName;
	}

	/**
	 * Checks if the feature type name is "Boolean".
	 * 
	 * @param feature
	 *            the feature to check
	 * @return <code>true</code> if the type of the feature is a Boolean; <code>false</code> otherwise
	 */
	public boolean isBoolean(EStructuralFeature feature) {
		boolean result = false;
		if (feature != null) {
			result = BOOLEAN_CLASS_NAME.equals(feature.getEType().getName());
		}
		return result;
	}

	/**
	 * Checks if the feature type is an EEnum.
	 * 
	 * @param feature
	 *            the feature to check
	 * @return <code>true</code> if the type of the feature is an EEnum; <code>false</code> otherwise
	 */
	public boolean isEnum(EStructuralFeature feature) {
		boolean result = false;
		if (feature != null) {
			result = feature.getEType() instanceof EEnum;
		}
		return result;
	}

	/**
	 * Get the value to display for multiple selections in the case of Boolean type features. If all
	 * features of the selected objects are set to the same value, the corresponding value is returned.
	 * Otherwise, null is returned.
	 * 
	 * @param selectedObjects
	 *            the list of semantic objects used to retrieve the value of the feature
	 * @param feature
	 *            the feature used to retrieve the value for each object
	 * @return <code>true</code> or <code>false</code> if all objects gives the same value; <code>null</code>
	 *         otherwise
	 */
	public Boolean getCheckboxMultipleValue(List<EObject> selectedObjects, EStructuralFeature feature) {
		Set<Boolean> choices = new HashSet<>();
		if (feature != null && selectedObjects != null) {
			for (EObject eObject : selectedObjects) {
				Object featureValue = eObject.eGet(feature);
				if (featureValue instanceof Boolean) {
					choices.add((Boolean) featureValue);
				}
			}
		}
		if (choices.size() == 1) {
			return choices.iterator().next();
		} else {
			return null;
		}
	}

	/**
	 * Get the value to display for multiple selections in the case of EEnum type features. If all
	 * features of the selected objects are set to the same value, the corresponding value is returned.
	 * Otherwise, null is returned.
	 * 
	 * @param selectedObjects
	 *            the list of semantic objects used to retrieve the value of the feature
	 * @param feature
	 *            the feature used to retrieve the value for each object
	 * @return the corresponding EEnumLiteral if all objects gives the same value; <code>null</code>
	 *         otherwise
	 */
	public EEnumLiteral getSelectMultipleValue(List<EObject> selectedObjects, EStructuralFeature feature) {
		Set<EEnumLiteral> choices = new HashSet<>();
		if (feature != null && selectedObjects != null) {
			for (EObject eObject : selectedObjects) {
				Object featureValue = eObject.eGet(feature);
				if (featureValue instanceof Enumerator && feature.getEType() instanceof EEnum) {
					EEnum featureEnum = (EEnum) feature.getEType();
					Enumerator enumerator = (Enumerator) featureValue;
					choices.add(featureEnum.getEEnumLiteralByLiteral(enumerator.getLiteral()));
				}
			}
		}
		if (choices.size() == 1) {
			return choices.iterator().next();
		} else {
			return null;
		}
	}

	/**
	 * Set the specified value to all objects with the given feature.
	 * 
	 * @param selectedObjects
	 *            the list of semantic objects to modify
	 * @param feature
	 *            the feature to use on each object of the selected Objects list
	 * @param newValue
	 *            the new value to set
	 * @return <code>true</code> if all objects have been properly set; <code>false</code> otherwise
	 */
	public boolean setAll(List<EObject> selectedObjects, EStructuralFeature feature, Object newValue) {
		boolean result = false;
		if (feature != null && selectedObjects != null) {
			result = true;
			for (EObject eObject : selectedObjects) {
				result = result && propertiesServices.set(eObject, feature.getName(), newValue);
			}
		}
		return result;
	}
}
