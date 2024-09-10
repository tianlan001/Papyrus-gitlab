/*****************************************************************************
 * Copyright (c) 2009-2011 CEA LIST.
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
 * 
 * 		Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 * 		Mickaël ADAM (ALL4TEC) - mickael.adam@all4tec.net - Bug 517679
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * Utility class use to retrieve stereotype applications.
 * 
 * Copied from oep.umlutils to avoid dependency toward this plug-in.
 * 
 */
public class ElementUtil {

	/**
	 * The ID for Papyrus EAnnotations.
	 */
	private static final String PAPYRUS_URI = "org.eclipse.papyrus"; //$NON-NLS-1$

	/**
	 * The ID for element nature in Papyrus EAnnotations.
	 */
	private static final String PAPYRUS_ELEMENT_NATURE = "nature"; //$NON-NLS-1$

	/**
	 * Convenient method to retrieve the StereotypeApplication by passing an
	 * element of the static profile.
	 * 
	 * @deprecated prefer using {@link UMLUtil#getStereotypeApplication(Element, Class)}
	 */
	@Deprecated
	public static <T extends EObject> T getStereotypeApplication(Element element, java.lang.Class<T> clazz) {
		return UMLUtil.getStereotypeApplication(element, clazz);
	}

	/**
	 * Adds the specified nature to this element.
	 * 
	 * @param element
	 *            The receiving '<em><b>Element</b></em>' model object.
	 * @param nature
	 *            The nature to add.
	 */
	public static void addNature(Element element, String nature) {
		EMap<String, String> details = UML2Util.getEAnnotation(element, PAPYRUS_URI, true).getDetails();

		if (!details.containsKey(PAPYRUS_ELEMENT_NATURE)) {
			details.put(PAPYRUS_ELEMENT_NATURE, nature);
		} else {
			details.removeKey(PAPYRUS_ELEMENT_NATURE);
			details.put(PAPYRUS_ELEMENT_NATURE, nature);
		}

	}

	/**
	 * Retrieves the nature for this element.
	 * 
	 * @param element
	 *            The receiving '<em><b>Element</b></em>' model object.
	 */
	public static String getNature(Element element) {
		EAnnotation eAnnotation = element.getEAnnotation(PAPYRUS_URI);

		if ((eAnnotation != null) && (eAnnotation.getDetails().containsKey(PAPYRUS_ELEMENT_NATURE))) {
			return eAnnotation.getDetails().get(PAPYRUS_ELEMENT_NATURE);
		}

		return ""; //$NON-NLS-1$
	}

	/**
	 * Removes the nature from this element.
	 * 
	 * @param element
	 *            The receiving '<em><b>Element</b></em>' model object.
	 */
	public static boolean removeNature(Element element) {
		EAnnotation eAnnotation = element.getEAnnotation(PAPYRUS_URI);

		if (eAnnotation != null) {
			EMap<String, String> details = eAnnotation.getDetails();

			if (details.containsKey(PAPYRUS_ELEMENT_NATURE)) {
				details.removeKey(PAPYRUS_ELEMENT_NATURE);
				return true;
			}
		}

		return false;
	}

	/**
	 * Determines whether this element has the specified nature.
	 * 
	 * @param element
	 *            The receiving '<em><b>Element</b></em>' model object.
	 * @param nature
	 *            The nature in question.
	 */
	public static boolean hasNature(Element element, String nature) {
		EAnnotation eAnnotation = element.getEAnnotation(PAPYRUS_URI);

		if ((eAnnotation != null) && (eAnnotation.getDetails().containsKey(PAPYRUS_ELEMENT_NATURE))) {
			if (nature.equals(eAnnotation.getDetails().get(PAPYRUS_ELEMENT_NATURE))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return true element is type of type to match
	 * @since 3.1
	 */
	public static boolean isTypeOf(IElementType element, IElementType typeTomatch) {
		if (typeTomatch.equals(element)) {
			return true;
		}
		List<IElementType> supers = Arrays.asList(element.getAllSuperTypes());
		if (supers.contains(typeTomatch)) {
			return true;
		}
		return false;
	}

	/**
	 * Check for stereotype applied on {@link Element}.
	 *
	 * @param element
	 *            the element to test
	 * @param stereotype
	 *            the stereotype qualify name
	 * @return true, if successful
	 * @since 3.1
	 */
	public static boolean hasStereotypeApplied(final Element element, final String stereotype) {
		List<String> sourceAppliedStereotypes = element.getAppliedStereotypes().stream()
				.map(st -> st.getQualifiedName())
				.collect(Collectors.toList());

		sourceAppliedStereotypes.addAll(element.getAppliedStereotypes().stream()
				.flatMap(st -> st.allParents().stream())
				.filter(Stereotype.class::isInstance).map(Stereotype.class::cast)
				.map(st -> st.getQualifiedName())
				.collect(Collectors.toList()));

		return sourceAppliedStereotypes.contains(stereotype);
	}

	/**
	 * Gets the stereotype application of the {@link Stereotype} in an {@link Element}.
	 *
	 * @param umlElement
	 *            the UML {@link Element}
	 * @param stereotype
	 *            the {@link Stereotype}
	 * @return the stereotype application
	 * @since 3.1
	 */
	public static EObject getStereotypeApplication(final Element umlElement, final Stereotype stereotype) {
		Stereotype actual = (stereotype == null) ? null : org.eclipse.papyrus.uml.tools.utils.UMLUtil.getAppliedSubstereotype(umlElement, stereotype);
		EObject stereotypeApplication = (null == actual) ? null : umlElement.getStereotypeApplication(actual);
		return stereotypeApplication;
	}
}
