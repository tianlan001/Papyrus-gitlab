/*****************************************************************************
 * Copyright (c) 2008, 2016 CEA LIST, Esterel Technologies SAS and others.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 488082
 *  Sebastien Gabel (Esterel Technologies SAS) - Bug 498743
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.tools.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Extension;
import org.eclipse.uml2.uml.Image;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Utility class for Stereotypes.
 */
@SuppressWarnings("nls")
public class StereotypeUtil {

	protected static final String QUOTE = "\"";

	public static final String SPACE_SEPARATOR = "#";

	public static final String EQUAL_SEPARATOR = "=";

	public static final String PROPERTY_VALUE_SEPARATOR = "|";

	public static final String SETREOTYPE_WITH_VALUE_SEPARATOR = ";";

	protected static final String ST_LEFT = String.valueOf("\u00AB");

	protected static final String ST_RIGHT = String.valueOf("\u00BB");

	// @Deprecated - use Extension.METACLASS_ROLE_PREFIX directly
	public static final String BASE_PREFIX = Extension.METACLASS_ROLE_PREFIX;

	/**
	 * returns the list of all super stereotypes for the specified stereotype
	 *
	 * @param stereotype
	 *            the stereotype for which super-stereotypes are looked for.
	 * @return the list of all stereotypes from which the specified stereotype inherits
	 */
	public static List<Stereotype> getAllSuperStereotypes(Stereotype stereotype) {
		List<Stereotype> generalStereotypes = new ArrayList<Stereotype>();
		for (Classifier generalClassifier : stereotype.getGenerals()) {
			if (generalClassifier instanceof Stereotype) {
				generalStereotypes.add((Stereotype) generalClassifier);
				generalStereotypes.addAll(getAllSuperStereotypes((Stereotype) generalClassifier));
			}
		}
		return generalStereotypes;
	}

	/**
	 * Parse the stereotype image and select those that have an "icon" kind (EAnnotation).
	 *
	 * @param stereotype
	 *            to parse
	 *
	 * @return a EList of {@link Image}
	 */
	public static EList<Image> getIcons(Stereotype stereotype) {

		EList<Image> icons = new BasicEList<Image>();

		Iterator<Image> it = stereotype.getIcons().iterator();
		while (it.hasNext()) {
			Image image = it.next();
			if ("icon".equals(ImageUtil.getKind(image))) {
				icons.add(image);
			}
		}

		return icons;
	}

	/**
	 * Returns the list of names (not qualified) of properties to display.
	 *
	 * @param stereotype
	 * @param stPropList
	 *
	 * @return
	 */
	private static List<String> getStereoPropertiesToDisplay(org.eclipse.uml2.uml.Stereotype stereotype, List<String> stPropList) {
		ArrayList<String> result = new ArrayList<String>();

		Iterator<String> propIter = stPropList.iterator();
		while (propIter.hasNext()) {
			String currentProp = propIter.next();
			if (currentProp.substring(0, currentProp.lastIndexOf(".")).equals(stereotype.getQualifiedName())) {
				result.add(currentProp.substring(currentProp.lastIndexOf(".") + 1, currentProp.length()));
			}
		}
		return result;
	}

	/**
	 * return string that contains value of properties of applied stereotype
	 *
	 * @param stereotypesPropertiesToDisplay
	 *            list of properties of stereotype to display grammar=
	 *            {<B>stereotypequalifiedName</B>'.'<B>propertyName</B>','}*
	 *
	 * @return a string withe the following grammar grammar=
	 *         {'\u00AB'<B>StereotypeName</B>'\u00BB''#'
	 *         {<B>propertyName</B>'='<B>propertyValue</B>'|'}*';'}*
	 */
	public static String getPropertiesValues(String stereotypesPropertiesToDisplay, Element umlElement) {
		HashSet<org.eclipse.uml2.uml.Stereotype> stereoSet = new HashSet<org.eclipse.uml2.uml.Stereotype>();
		ArrayList<String> stPropList = new ArrayList<String>();

		String propValues = "";

		// fill our data structure in order to generate the string
		StringTokenizer propStringTokenizer = new StringTokenizer(stereotypesPropertiesToDisplay, ",");
		while (propStringTokenizer.hasMoreElements()) {
			// extract property to display
			String propertyQN = propStringTokenizer.nextToken();
			// stereotype
			String stereotypeQN = propertyQN.substring(0, propertyQN.indexOf("."));

			Stereotype stereotype = umlElement.getAppliedStereotype(stereotypeQN);
			if (stereotype != null) {
				stereoSet.add(stereotype);
			}
			stPropList.add(propertyQN);
		}

		// Display each stereotype
		Iterator<org.eclipse.uml2.uml.Stereotype> stereoIter = stereoSet.iterator();
		while (stereoIter.hasNext()) {
			org.eclipse.uml2.uml.Stereotype stereotype = stereoIter.next();
			// display the stereotype
			propValues = propValues + ST_LEFT + stereotype.getName() + ST_RIGHT + SPACE_SEPARATOR;
			// get the set of property to display
			Iterator<String> stPropIter = getStereoPropertiesToDisplay(stereotype, stPropList).iterator();

			// display each property
			while (stPropIter.hasNext()) {
				String stProp = stPropIter.next();
				// get the property
				org.eclipse.uml2.uml.Property currentProp = getPropertyByName(stereotype, stProp);

				if (currentProp == null) {
					return "No value";
				}
				propValues += displayPropertyValue(stereotype, currentProp, umlElement, PROPERTY_VALUE_SEPARATOR);
			} // display each property
			if (propValues.endsWith(PROPERTY_VALUE_SEPARATOR)) {
				propValues = propValues.substring(0, propValues.lastIndexOf(PROPERTY_VALUE_SEPARATOR));
			}
			propValues = propValues + SETREOTYPE_WITH_VALUE_SEPARATOR;
		} // end display each property

		return propValues;
	}

	/**
	 * Computes the display of a property value.
	 *
	 * @param stereotype
	 *            the stereotype that contains the property to be displayed
	 * @param property
	 *            the property to be displayed
	 * @param umlElement
	 *            the element that is stereotyped by the specified
	 * @param separator
	 *            the separator between each property value, in case several properties are
	 *            displayed for the same property
	 * @return a string corresponding to the property value
	 */
	public static String displayPropertyValue(Stereotype stereotype, Property property, Element umlElement, String separator) {
		org.eclipse.uml2.uml.Type propType = property.getType();

		// property type is an enumeration
		if (propType instanceof org.eclipse.uml2.uml.Enumeration) {
			return getPropertyValueForEnumerationType(property, stereotype, umlElement, separator);
		}

		// property type is a metaclass
		else if ((propType instanceof org.eclipse.uml2.uml.Class) && (propType.getAppliedStereotypes() != null) && (propType.getAppliedStereotypes().size() > 0) && propType.getAppliedStereotypes().get(0).getName().equals("Metaclass")) {
			return getPropertyValueForMetaclassType(property, stereotype, umlElement, separator, false);
		}
		// property type is a stereotype
		else if (propType instanceof org.eclipse.uml2.uml.Stereotype) {
			return getPropertyValueForStereotypeType(property, stereotype, umlElement, separator, false);
		}
		// property is a composite class
		else if ((propType instanceof org.eclipse.uml2.uml.Class) && !(propType instanceof org.eclipse.uml2.uml.Stereotype) && property.isComposite()) {
			return /* FIXME stProp + */property.getName() + EQUAL_SEPARATOR + property.getName() + separator;
		}

		// otherwise
		else {
			return getPropertyValue(property, stereotype, umlElement, separator, false);
		}
	}

	public static String displayPropertyValueOnly(Stereotype stereotype, Property property, Element umlElement, String separator) {
		org.eclipse.uml2.uml.Type propType = property.getType();

		// property type is an enumeration
		if (propType instanceof org.eclipse.uml2.uml.Enumeration) {
			return getPropertyValueOnlyForEnumerationType(property, stereotype, umlElement, separator);
		}

		// property type is a metaclass
		else if ((propType instanceof org.eclipse.uml2.uml.Class) && (propType.getAppliedStereotypes() != null) && (propType.getAppliedStereotypes().size() > 0) && propType.getAppliedStereotypes().get(0).getName().equals("Metaclass")) {
			return getPropertyValueOnlyForMetaclassType(property, stereotype, umlElement, separator, false);
		}
		// property type is a stereotype
		else if (propType instanceof org.eclipse.uml2.uml.Stereotype) {
			return getPropertyValueOnlyForStereotypeType(property, stereotype, umlElement, separator, false);
		}
		// property is a composite class
		else if ((propType instanceof org.eclipse.uml2.uml.Class) && !(propType instanceof org.eclipse.uml2.uml.Stereotype) && property.isComposite()) {
			return /* FIXME stProp + */property.getName() + separator;
		}

		// otherwise
		else {
			return getPropertyValueOnly(property, stereotype, umlElement, separator, false);
		}
	}



	/**
	 * Computes the display of a property value.
	 *
	 * @param stereotype
	 *            the stereotype that contains the property to be displayed
	 * @param property
	 *            the property to be displayed
	 * @param umlElement
	 *            the element that is stereotyped by the specified
	 * @param separator
	 *            the separator between each property value, in case several properties are
	 *            displayed for the same property
	 * @return a string corresponding to the property value
	 */
	public static String displayPropertyValueToEdit(Stereotype stereotype, Property property, Element umlElement, String separator) {
		org.eclipse.uml2.uml.Type propType = property.getType();

		// property type is an enumeration
		if (propType instanceof org.eclipse.uml2.uml.Enumeration) {
			return getPropertyValueForEnumerationType(property, stereotype, umlElement, separator);
		}

		// property type is a metaclass
		else if ((propType instanceof org.eclipse.uml2.uml.Class) && (propType.getAppliedStereotypes() != null) && (propType.getAppliedStereotypes().size() > 0) && propType.getAppliedStereotypes().get(0).getName().equals("Metaclass")) {
			return getPropertyValueForMetaclassType(property, stereotype, umlElement, separator, true);
		}
		// property type is a stereotype
		else if (propType instanceof org.eclipse.uml2.uml.Stereotype) {
			return getPropertyValueForStereotypeType(property, stereotype, umlElement, separator, true);
		}
		// property is a composite class
		else if ((propType instanceof org.eclipse.uml2.uml.Class) && !(propType instanceof org.eclipse.uml2.uml.Stereotype) && property.isComposite()) {
			return /* FIXME stProp + */property.getName() + EQUAL_SEPARATOR + property.getName() + separator;
		}
		// otherwise
		else {
			return getPropertyValue(property, stereotype, umlElement, separator, true);
		}
	}

	/**
	 * Retrieves a property of the specified stereotype, given its name
	 *
	 * @param stereotype
	 *            the stereotype owner of the property
	 * @param propertyName
	 *            the name of the property to find
	 */
	public static Property getPropertyByName(Stereotype stereotype, String propertyName) {
		Iterator<Property> iterPro = stereotype.getAllAttributes().iterator();
		// from a string look for the property
		while (iterPro.hasNext()) {
			org.eclipse.uml2.uml.Property tmpProperty = iterPro.next();
			String name = "";
			if (tmpProperty != null) {
				name = (tmpProperty.getName() != null) ? tmpProperty.getName() : "";
			}
			if (name.equals(propertyName)) {
				return tmpProperty;
			}
		}
		return null;
	}

	/**
	 * return string that contains value of properties of applied stereotype
	 *
	 * @param stereotypesPropertiesToDisplay
	 *            list of properties of stereotype to display grammar=
	 *            {<B>stereotypequalifiedName</B>'.'<B>propertyName</B>','}*
	 *
	 * @return a string with the following grammar grammar=
	 *         {(<B>propertyName</B>'='<B>propertyValue</B>',')*
	 *         <B>propertyName</B>'='<B>propertyValue</B>'}
	 */
	public static String getPropertiesValuesInBrace(String stereotypesPropertiesToDisplay, Element umlElement) {
		String propertyValues = "";

		HashSet<org.eclipse.uml2.uml.Stereotype> stereoSet = new HashSet<org.eclipse.uml2.uml.Stereotype>();
		ArrayList<String> stPropList = new ArrayList<String>();

		// fill our data structure in order to generate the string
		StringTokenizer propStringTokenizer = new StringTokenizer(stereotypesPropertiesToDisplay, ",");
		while (propStringTokenizer.hasMoreElements()) {
			// extract property to display
			String propertyQN = propStringTokenizer.nextToken();
			// stereotype
			String stereotypeQN = propertyQN.substring(0, propertyQN.indexOf("."));

			Stereotype stereotype = umlElement.getAppliedStereotype(stereotypeQN);
			if (stereotype != null) {
				stereoSet.add(stereotype);
			}

			stPropList.add(propertyQN);
		}

		// Display each stereotype
		Iterator<org.eclipse.uml2.uml.Stereotype> stereoIter = stereoSet.iterator();
		while (stereoIter.hasNext()) {
			Stereotype stereotype = stereoIter.next();
			if (stereotype != null) {
				if (propertyValues != null && propertyValues.trim().length() > 0) {
					propertyValues += ",";
				}
				propertyValues += displayPropertyValuesForStereotype(stereotype, stPropList, umlElement);
			}
		}
		return propertyValues;
	}

	public static String displayPropertyValuesForStereotype(Stereotype stereotype, List<String> stPropList, Element umlElement) {
		StringBuffer buffer = new StringBuffer();

		// add stereotype name. For "In Brace", display nothing
		buffer.append("");
		// get the set of property to display
		Iterator<String> stPropIter = getStereoPropertiesToDisplay(stereotype, stPropList).iterator();

		// display each property
		while (stPropIter.hasNext()) {
			String stProp = stPropIter.next();
			// get the property
			org.eclipse.uml2.uml.Property currentProp = null;
			Iterator<Property> iterPro = stereotype.getAllAttributes().iterator();
			// from a string look for the property
			while (iterPro.hasNext()) {
				org.eclipse.uml2.uml.Property tmpProperty = iterPro.next();
				if (stProp.equals(tmpProperty.getName())) {
					currentProp = tmpProperty;
				}
			}

			if (currentProp == null) {
				return "No value";
			}
			org.eclipse.uml2.uml.Type propType = currentProp.getType();

			// property type is an enumeration

			if (propType instanceof org.eclipse.uml2.uml.Enumeration) {
				buffer.append(getPropertyValueForEnumerationType(currentProp, stereotype, umlElement, ","));
			}

			// property type is a metaclass
			else if ((propType instanceof org.eclipse.uml2.uml.Class) && (propType.getAppliedStereotypes() != null) && (propType.getAppliedStereotypes().size() > 0) && propType.getAppliedStereotypes().get(0).getName().equals("Metaclass")) {
				buffer.append(getPropertyValueForMetaclassType(currentProp, stereotype, umlElement, ",", false));
			}

			// property type is a stereotype
			else if (propType instanceof org.eclipse.uml2.uml.Stereotype) {
				buffer.append(getPropertyValueForStereotypeType(currentProp, stereotype, umlElement, ",", false));
			}

			// property is a composite class
			else if ((propType instanceof org.eclipse.uml2.uml.Class) && !(propType instanceof org.eclipse.uml2.uml.Stereotype) && currentProp.isComposite()) {
				buffer.append(stProp + EQUAL_SEPARATOR + currentProp.getName() + ",");
			}

			// otherwise
			else {
				buffer.append(getPropertyValue(currentProp, stereotype, umlElement, ",", false));
			}
		} // display each property

		String propValues = buffer.toString();
		if (propValues.endsWith(",")) {
			propValues = propValues.substring(0, propValues.lastIndexOf(","));
		}

		return propValues;
	}

	/**
	 * return the string that represents the value of property when its type is an Enumeration
	 *
	 * @param property
	 *            the property to display
	 * @param stereotype
	 *            the stereotype that contains the property
	 * @param umlElement
	 *            the UML element on which the stereotype is applied
	 * @param PROPERTY_VALUE_SEPARATOR
	 *            the separator to end the expression
	 * @return String with the following grammar property name EQUAL_SEPERATOR propertyValue
	 *         PROPERTY_VALUE_SEPERATOR
	 */
	private static String getPropertyValueForEnumerationType(Property property, Stereotype stereotype, Element umlElement, final String PROPERTY_VALUE_SEPARATOR) {
		String out = "";
		if ((property.getUpper() == 1) && (umlElement.getValue(stereotype, property.getName()) != null)) {
			if ((property.getLower() != 0) || umlElement.getValue(stereotype, property.getName()) != null) {
				if (property.getDefaultValue() != null || umlElement.getValue(stereotype, property.getName()) != null) {
					Object val = umlElement.getValue(stereotype, property.getName());
					if (val instanceof EnumerationLiteral) {
						out = property.getName() + EQUAL_SEPARATOR + ((EnumerationLiteral) val).getLabel() + PROPERTY_VALUE_SEPARATOR;
					} else {
						out = property.getName() + EQUAL_SEPARATOR + val + PROPERTY_VALUE_SEPARATOR;
					}
				} else {
					out = property.getName() + PROPERTY_VALUE_SEPARATOR;
				}
			} else {
				out = property.getName() + PROPERTY_VALUE_SEPARATOR;
			}
		}

		// multiplicity is greater than one
		else {
			out = property.getName() + EQUAL_SEPARATOR + umlElement.getValue(stereotype, property.getName()) + PROPERTY_VALUE_SEPARATOR;
		}
		return out;
	}

	private static String getPropertyValueOnlyForEnumerationType(Property property, Stereotype stereotype, Element umlElement, final String PROPERTY_VALUE_SEPARATOR) {
		String out = "";
		if ((property.getUpper() == 1) && (umlElement.getValue(stereotype, property.getName()) != null)) {
			if ((property.getLower() != 0) || umlElement.getValue(stereotype, property.getName()) != null) {
				if (property.getDefaultValue() != null || umlElement.getValue(stereotype, property.getName()) != null) {
					Object val = umlElement.getValue(stereotype, property.getName());
					if (val instanceof EnumerationLiteral) {
						out = ((EnumerationLiteral) val).getLabel() + PROPERTY_VALUE_SEPARATOR;
					} else {
						out = val + PROPERTY_VALUE_SEPARATOR;
					}
				} else {
					out = PROPERTY_VALUE_SEPARATOR;
				}
			} else {
				out = PROPERTY_VALUE_SEPARATOR;
			}
		}

		// multiplicity is greater than one
		else {
			out = umlElement.getValue(stereotype, property.getName()) + PROPERTY_VALUE_SEPARATOR;
		}
		return out;
	}

	/**
	 * return the string that represents the value of property when its type is a Metaclass
	 *
	 * @param property
	 *            the property to display
	 * @param stereotype
	 *            the stereotype that contains the property
	 * @param umlElement
	 *            the UML element on which the stereotype is applied
	 * @param EQUAL_SEPARATOR
	 *            the separator between property and property value
	 * @param PROPERTY_VALUE_SEPARATOR
	 *            the separator to end the expression
	 * @return String with the following grammar property name EQUAL_SEPERATOR propertyValue
	 *         PROPERTY_VALUE_SEPERATOR
	 */
	private static String getPropertyValueForMetaclassType(Property property, Stereotype stereotype, Element umlElement, final String PROPERTY_VALUE_SEPARATOR, boolean withQualifiedName) {
		String out = "";

		if ((property.getUpper() == 1) && (umlElement.getValue(stereotype, property.getName()) != null) && (umlElement.getValue(stereotype, property.getName()) instanceof NamedElement)) {
			if (withQualifiedName) {
				out = property.getName() + EQUAL_SEPARATOR + ((NamedElement) (umlElement.getValue(stereotype, property.getName()))).getQualifiedName() + PROPERTY_VALUE_SEPARATOR;
			} else {
				out = property.getName() + EQUAL_SEPARATOR + ((NamedElement) (umlElement.getValue(stereotype, property.getName()))).getName() + PROPERTY_VALUE_SEPARATOR;
			}
		}

		// multiplicity greater than one
		else if (property.getUpper() != 1) {
			List<EObject> values = (List<EObject>) umlElement.getValue(stereotype, property.getName());
			ArrayList<String> elementNames = new ArrayList<String>();
			if (values != null) {
				for (int count = 0; count < values.size(); count++) {
					if (values.get(count) instanceof NamedElement) {
						if (withQualifiedName) {
							elementNames.add(((NamedElement) values.get(count)).getQualifiedName());
						} else {
							elementNames.add(((NamedElement) values.get(count)).getName());
						}
					}
				}
			}
			out = property.getName() + EQUAL_SEPARATOR + elementNames + PROPERTY_VALUE_SEPARATOR;
		}

		// multiplicity = 1 and property value null
		else {
			out = property.getName() + EQUAL_SEPARATOR + umlElement.getValue(stereotype, property.getName()) + PROPERTY_VALUE_SEPARATOR;
		}
		return out;
	}

	private static String getPropertyValueOnlyForMetaclassType(Property property, Stereotype stereotype, Element umlElement, final String PROPERTY_VALUE_SEPARATOR, boolean withQualifiedName) {
		String out = "";

		if ((property.getUpper() == 1) && (umlElement.getValue(stereotype, property.getName()) != null) && (umlElement.getValue(stereotype, property.getName()) instanceof NamedElement)) {
			if (withQualifiedName) {
				out = ((NamedElement) (umlElement.getValue(stereotype, property.getName()))).getQualifiedName() + PROPERTY_VALUE_SEPARATOR;
			} else {
				out = ((NamedElement) (umlElement.getValue(stereotype, property.getName()))).getName() + PROPERTY_VALUE_SEPARATOR;
			}
		}

		// multiplicity greater than one
		else if (property.getUpper() != 1) {
			List<EObject> values = (List<EObject>) umlElement.getValue(stereotype, property.getName());
			ArrayList<String> elementNames = new ArrayList<String>();
			if (values != null) {
				for (int count = 0; count < values.size(); count++) {
					if (values.get(count) instanceof NamedElement) {
						if (withQualifiedName) {
							elementNames.add(((NamedElement) values.get(count)).getQualifiedName());
						} else {
							elementNames.add(((NamedElement) values.get(count)).getName());
						}
					}
				}
			}
			out = elementNames + PROPERTY_VALUE_SEPARATOR;
		}

		// multiplicity = 1 and property value null
		else {
			out = umlElement.getValue(stereotype, property.getName()) + PROPERTY_VALUE_SEPARATOR;
		}
		return out;
	}

	/**
	 * return the string that represents the value of property when its type is a stereotype
	 *
	 * @param property
	 *            the property to display
	 * @param stereotype
	 *            the stereotype that contains the property
	 * @param umlElement
	 *            the umlelement on which the stereotype is applied
	 * @param EQUAL_SEPARATOR
	 *            the separator between property and property value
	 * @param PROPERTY_VALUE_SEPARATOR
	 *            the separator to end the exprestion
	 * @return String withe the following grammar propertyname EQUAL_SEPERATOR propertyValue
	 *         PROPERTY_VALUE_SEPERATOR
	 */
	private static String getPropertyValueForStereotypeType(Property property, Stereotype stereotype, Element umlElement, final String PROPERTY_VALUE_SEPARATOR, boolean withQualifiedName) {
		String out = "";
		if ((property.getUpper() == 1) && (umlElement.getValue(stereotype, property.getName()) != null)) {

			// retrieve the base element from the stereotype application
			Object value = umlElement.getValue(stereotype, property.getName());
			Element baseElement = UMLUtil.getBaseElement((EObject) value);

			// display the base element's qualified name
			if (withQualifiedName) {
				out = property.getName() + EQUAL_SEPARATOR + ((NamedElement) baseElement).getQualifiedName() + PROPERTY_VALUE_SEPARATOR;
			} else {
				out = property.getName() + EQUAL_SEPARATOR + ((NamedElement) baseElement).getName() + PROPERTY_VALUE_SEPARATOR;
			}
		}

		// multiplicity greater than one
		else if (property.getUpper() != 1) {
			// retrieve the base element from the stereotype application
			List<EObject> values = (List<EObject>) umlElement.getValue(stereotype, property.getName());
			ArrayList<String> baseElements = new ArrayList<String>();
			if (values != null) {
				for (int k = 0; k < values.size(); k++) {
					Element base = UMLUtil.getBaseElement(values.get(k));
					if (base instanceof NamedElement) {
						if (withQualifiedName) {
							baseElements.add(((NamedElement) base).getQualifiedName());
						} else {
							baseElements.add(((NamedElement) base).getName());
						}
					}
				}
			}

			out = property.getName() + EQUAL_SEPARATOR + baseElements + PROPERTY_VALUE_SEPARATOR;
		}

		// multiplicity = 1 and property value null
		else {
			out = property.getName() + EQUAL_SEPARATOR + (umlElement.getValue(stereotype, property.getName())) + PROPERTY_VALUE_SEPARATOR;
		}
		return out;
	}

	private static String getPropertyValueOnlyForStereotypeType(Property property, Stereotype stereotype, Element umlElement, final String PROPERTY_VALUE_SEPARATOR, boolean withQualifiedName) {
		String out = "";
		if ((property.getUpper() == 1) && (umlElement.getValue(stereotype, property.getName()) != null)) {

			// retrieve the base element from the stereotype application
			Object value = umlElement.getValue(stereotype, property.getName());
			Element baseElement = UMLUtil.getBaseElement((EObject) value);

			// display the base element's qualified name
			if (withQualifiedName) {
				out = ((NamedElement) baseElement).getQualifiedName() + PROPERTY_VALUE_SEPARATOR;
			} else {
				out = ((NamedElement) baseElement).getName() + PROPERTY_VALUE_SEPARATOR;
			}
		}

		// multiplicity greater than one
		else if (property.getUpper() != 1) {
			// retrieve the base element from the stereotype application
			List<EObject> values = (List<EObject>) umlElement.getValue(stereotype, property.getName());
			ArrayList<String> baseElements = new ArrayList<String>();
			if (values != null) {
				for (int k = 0; k < values.size(); k++) {
					if (withQualifiedName) {
						baseElements.add(((NamedElement) UMLUtil.getBaseElement(values.get(k))).getQualifiedName());
					} else {
						baseElements.add(((NamedElement) UMLUtil.getBaseElement(values.get(k))).getName());
					}
				}
			}

			out = baseElements + PROPERTY_VALUE_SEPARATOR;
		}

		// multiplicity = 1 and property value null
		else {
			out = (umlElement.getValue(stereotype, property.getName())) + PROPERTY_VALUE_SEPARATOR;
		}
		return out;
	}

	/**
	 * return the string that represents the value of property
	 *
	 * @param property
	 *            the property to display
	 * @param stereotype
	 *            the stereotype that contains the property
	 * @param umlElement
	 *            the UML element on which the stereotype is applied
	 * @param PROPERTY_VALUE_SEPARATOR
	 *            the separator to end the expression
	 * @return String with the following grammar property name EQUAL_SEPERATOR propertyValue
	 *         PROPERTY_VALUE_SEPERATOR
	 */
	private static String getPropertyValue(Property property, Stereotype stereotype, Element umlElement, final String PROPERTY_VALUE_SEPARATOR, boolean withDelimitator) {
		String out = "";
		Object valueObject = umlElement.getValue(stereotype, property.getName());

		if (valueObject == null) {
			out = property.getName() + PROPERTY_VALUE_SEPARATOR;
		} else {
			if (property.getDefaultValue() != null) {
				if (withDelimitator) {
					String value = "" + valueObject;
					out = property.getName() + EQUAL_SEPARATOR + value + PROPERTY_VALUE_SEPARATOR;
					if (value.contains("[")) {
						out = out.replace("[", "[" + QUOTE);
						out = out.replace("]", QUOTE + "]");
						out = out.replace(", ", QUOTE + "," + QUOTE);
					} else {
						out = property.getName() + EQUAL_SEPARATOR + QUOTE + value + QUOTE + PROPERTY_VALUE_SEPARATOR;
					}
				} else {
					if (valueObject instanceof EObject) {
						ILabelProvider labelProvider = getLabelProvider(property);
						return out = property.getName() + EQUAL_SEPARATOR + labelProvider.getText(valueObject) + PROPERTY_VALUE_SEPARATOR;
					} else {
						out = property.getName() + EQUAL_SEPARATOR + valueObject + PROPERTY_VALUE_SEPARATOR;
					}
				}
			} else {
				out = property.getName() + EQUAL_SEPARATOR + valueObject + PROPERTY_VALUE_SEPARATOR;
			}
		}
		return out;
	}

	private static String getPropertyValueOnly(Property property, Stereotype stereotype, Element umlElement, final String PROPERTY_VALUE_SEPARATOR, boolean withDelimitator) {
		String out = "";
		if ((property.getLower() != 0) || umlElement.getValue(stereotype, property.getName()) != null) {
			if (property.getDefaultValue() != null || umlElement.getValue(stereotype, property.getName()) != null) {
				if (withDelimitator) {
					String value = "" + umlElement.getValue(stereotype, property.getName());
					out = value + PROPERTY_VALUE_SEPARATOR;
					if (!value.contains("[")) {
						out = value + PROPERTY_VALUE_SEPARATOR;
					}
				} else {
					if (umlElement.getValue(stereotype, property.getName()) instanceof EObject) {
						ILabelProvider labelProvider = getLabelProvider(property);
						return out = labelProvider.getText(umlElement.getValue(stereotype, property.getName())) + PROPERTY_VALUE_SEPARATOR;
					} else {
						out = umlElement.getValue(stereotype, property.getName()) + PROPERTY_VALUE_SEPARATOR;
					}
				}
			} else {
				out = PROPERTY_VALUE_SEPARATOR;
			}
		} else {
			out = PROPERTY_VALUE_SEPARATOR;

		}
		return out;
	}

	public static ILabelProvider getLabelProvider(EObject eObject) {
		try {
			return ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class, eObject).getLabelProvider();
		} catch (ServiceException ex) {
			Activator.log.error(ex);
			return new LabelProvider();
		}
	}

	/**
	 * Parse the stereotype image and select those that have an "shape" kind (EAnnotation).
	 *
	 * @param stereotype
	 *            to parse
	 *
	 * @return a EList of {@link Image}
	 */
	public static EList<Image> getShapes(Stereotype stereotype) {

		EList<Image> shapes = new BasicEList<Image>();

		Iterator<Image> it = stereotype.getIcons().iterator();
		while (it.hasNext()) {
			Image image = it.next();
			if ("shape".equals(ImageUtil.getKind(image))) {
				shapes.add(image);
			}
		}

		return shapes;
	}

	/**
	 *
	 * @param stereotype
	 *            a stereotype
	 * @return
	 *         the list of the properties of this stereotype, excluding the properties of the extending metaclasses
	 */
	public static final List<Property> getStereotypePropertiesWithoutBaseProperties(final Stereotype stereotype) {
		final List<Property> properties = new ArrayList<Property>();
		for (Property property : stereotype.getOwnedAttributes()) {
			if (isValidStereotypeProperty(property)) {
				properties.add(property);
			}
		}
		return properties;
	}

	/**
	 *
	 * @param stereotype
	 *            a stereotype
	 * @return
	 *         the list of the properties of this stereotype, excluding the properties of the extending metaclasses
	 */
	public static final List<Property> getAllStereotypePropertiesWithoutBaseProperties(final Stereotype stereotype) {
		final List<Property> properties = new ArrayList<Property>();
		for (Property property : stereotype.getAllAttributes()) {
			if (isValidStereotypeProperty(property)) {
				properties.add(property);
			}
		}
		return properties;
	}

	/**
	 *
	 * @param property
	 *            a property
	 * @return
	 *         <code>true</code> if the property is a Metaclass property
	 */
	public static final boolean isValidStereotypeProperty(final Property property) {
		Association association = property.getAssociation();
		if (association instanceof Extension) {
			Extension ext = (Extension) association;
			Class metaClass = ext.getMetaclass();
			if (property.getName().equals(BASE_PREFIX + metaClass.getName())) {
				return false;
			}
		}
		return true;
	}



	/**
	 * This method verifies if a stereotype is applied on an UML element
	 *
	 * @param element
	 *            A UML element
	 * @param str_name
	 *            a qualified stereotype name
	 */
	public static boolean isApplied(Element element, String str_name) {
		return (element.getAppliedStereotype(str_name) != null);
	}

	/**
	 * This method verifies if a stereotype is applied on an UML element
	 *
	 * @param element
	 *            A UML element
	 * @param the
	 *            class of an element of a static profile
	 */
	public static boolean isApplied(Element element, java.lang.Class<? extends EObject> clazz) {
		for (EObject stereoApplication : element.getStereotypeApplications()) {
			// check whether the stereotype is a subclass of the passed parameter clazz
			if (clazz.isAssignableFrom(stereoApplication.getClass())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method verifies if a stereotype is applicable on an UML element
	 *
	 * @param element
	 *            A UML element
	 * @param the
	 *            class of an element of a static profile
	 */
	public static boolean isApplicable(Element element, java.lang.Class<? extends EObject> clazz) {
		return getStereoName(element, clazz) != null;
	}


	/**
	 * Apply a stereotype. The stereotype is not applied, if already a sub-stereotype is applied.
	 * If you want to apply the new stereotype also in this case, use applyExact instead.
	 *
	 * @param element
	 *            the element
	 * @param stereo_name
	 *            the stereotype name
	 * @return
	 */
	public static Stereotype apply(Element element, String stereo_name) {
		Stereotype stereotype = element.getApplicableStereotype(stereo_name);
		if (stereotype != null) {
			EList<Stereotype> subStereos = element.getAppliedSubstereotypes(stereotype);
			boolean alreadyApplied = (subStereos.size() > 0);
			if (!alreadyApplied) {
				// it seems that subSterotypes do not include the stereotype itself
				if (element.getStereotypeApplication(stereotype) == null) {
					element.applyStereotype(stereotype);
				}
			}
		}
		return stereotype;
	}

	/**
	 * unapply a stereotype when the name of the stereotype is given.
	 *
	 * @param element
	 *            the element
	 * @param stereo_name
	 *            the stereotype name
	 * @return
	 */
	public static void unapply(Element element, String stereo_name) {
		Stereotype stereotype = element.getApplicableStereotype(stereo_name);
		if (stereotype != null) {
			if (element.getStereotypeApplication(stereotype) != null) {
				element.unapplyStereotype(stereotype);
			}
		}
	}

	/**
	 * Apply a stereotype and return the stereotype application (if successful).
	 * The stereotype is not applied, if already a sub-stereotype is applied.
	 * If you want to apply the new stereotype also in this case, use applyExact instead.
	 *
	 * @param element
	 *            the element
	 * @param stereo_name
	 *            the stereotype name
	 * @return
	 */
	public static <T extends EObject> T applyApp(Element element, java.lang.Class<T> clazz) {
		if (apply(element, clazz) != null) {
			return UMLUtil.getStereotypeApplication(element, clazz);
		}
		return null;
	}


	/**
	 * Apply a stereotype.
	 * Caveat: the function relies on the correspondence between the fully qualified
	 * stereotype name and the package name within the static profile. The latter may
	 * use a different prefix (as it is the case with the MARTE analysis & design profile).
	 *
	 * @param element
	 *            the element
	 * @param class
	 *            a class of a static profile
	 * @return
	 */
	public static Stereotype apply(Element element, java.lang.Class<? extends EObject> clazz) {
		String qualifiedSN = getStereoName(element, clazz);
		if (qualifiedSN != null) {
			return apply(element, qualifiedSN);
		} else {
			return null;
		}
	}

	/**
	 * Unapply a stereotype.
	 * Caveat: the function relies on the correspondence between the fully qualified
	 * stereotype name and the package name within the static profile. The latter may
	 * use a different prefix (as it is the case with the MARTE analysis & design profile).
	 *
	 * @param element
	 *            the element
	 * @param stereo_name
	 *            the stereotype name
	 * @return
	 */
	public static void unapply(Element element, java.lang.Class<? extends EObject> clazz) {
		String qualifiedSN = getStereoName(element, clazz);
		if (qualifiedSN != null) {
			unapply(element, qualifiedSN);
		}
	}


	/**
	 * Get the qualified stereotype-name when given a class from a static profile.
	 * The comparison is based on the qualified names of the definition of the profiles that
	 * are already applied (i.e. it would fail, if the profile is not already applied).
	 *
	 * @param element
	 *            an element to which a stereotype should be applied
	 * @param clazz
	 *            a class from a static profile
	 * @return the qualified stereotype name, associated with the passed class from the static profile (or null, if none could be found)
	 */
	public static String getStereoName(Element element, java.lang.Class<? extends EObject> clazz) {
		String pkgName = clazz.getPackage().getName();
		EList<Profile> appliedProfiles = element.getNearestPackage().getAllAppliedProfiles();
		for (Profile appliedProfile : appliedProfiles) {
			EPackage definition = appliedProfile.getDefinition();
			if (definition != null) {
				String defClassName = definition.getClass().getName();
				if (defClassName != null && defClassName.startsWith(pkgName)) {
					return appliedProfile.getQualifiedName() + NamedElement.SEPARATOR + clazz.getSimpleName();
				}
			}
		}
		return null;
	}

	/**
	 * This allows to get all stereotypes of a profile (check in sub packages).
	 * 
	 * @param profile
	 *            the profile.
	 * @return The list of stereotypes corresponding to the profile.
	 */
	public static List<Stereotype> getAllStereotypes(final Profile profile) {
		final List<Stereotype> stereotypes = new ArrayList<Stereotype>(profile.getOwnedStereotypes());
		stereotypes.addAll(getStereotypeInMembers(profile.getOwnedMembers()));
		return stereotypes;
	}

	/**
	 * This allows to get all stereotypes in sub packages.
	 * 
	 * @param members
	 *            The owned members of an element.
	 * @return The list of stereotypes in sub packages.
	 */
	protected static List<Stereotype> getStereotypeInMembers(final List<NamedElement> members) {
		final List<Stereotype> stereotypes = new ArrayList<Stereotype>();

		// Loop on members
		final Iterator<NamedElement> membersIterator = members.iterator();
		while (membersIterator.hasNext()) {
			NamedElement member = membersIterator.next();

			// Get stereotypes in packages
			if (member instanceof Package) {
				stereotypes.addAll(((Package) member).getOwnedStereotypes());
			}

			// Loop recursively in members
			if (member instanceof Namespace) {
				stereotypes.addAll(getStereotypeInMembers(((Namespace) member).getOwnedMembers()));
			}
		}

		return stereotypes;
	}
}
