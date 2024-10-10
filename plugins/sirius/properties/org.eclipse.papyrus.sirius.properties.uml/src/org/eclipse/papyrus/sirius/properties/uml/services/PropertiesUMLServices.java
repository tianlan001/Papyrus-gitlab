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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.sirius.properties.uml.Activator;
import org.eclipse.papyrus.sirius.properties.uml.messages.Messages;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.papyrus.uml.tools.utils.UMLUtil;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * This service class includes all common services to get or compute a value for
 * UML model elements.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class PropertiesUMLServices {

	/**
	 * Empty String.
	 */
	public static final String EMPTY = ""; //$NON-NLS-1$

	/**
	 * Space in value.
	 */
	public static final String SPACE = " "; //$NON-NLS-1$

	/**
	 * Metaclass stereotype name.
	 */
	private static final String STANDARD_METACLASS = "StandardProfile::Metaclass"; //$NON-NLS-1$

	/**
	 * Message to display when a {@link Package} is orphan.
	 */
	private static final String UNKNOWN_LOCATION = Messages.ImportedPackageLocationObservableValue_Unknown;

	/**
	 * Get location of a given {@link Package}.
	 * 
	 * @see {@link org.eclipse.papyrus.uml.properties.databinding.ImportedPackageLocationObservableValue.doGetValue()}
	 * 
	 * @param umlPackage
	 *            the {@link Package} which contains the location,
	 * @return location of a given {@link Package}.
	 */
	public String getLocation(org.eclipse.uml2.uml.Package umlPackage) {
		Objects.requireNonNull(umlPackage, "None location on Null Package."); //$NON-NLS-1$
		String location = UNKNOWN_LOCATION;
		if (umlPackage.eIsProxy()) {
			location = EcoreUtil.getURI(umlPackage).trimFragment().toString();
		} else if (umlPackage.eResource() != null) {
			URI uri = umlPackage.eResource().getURI();
			if (uri != null) {
				location = uri.toString();
			}
		}
		return location;
	}

	// UMLLabelObservableValue services

	/**
	 * Get label from a given {@link NamedElement}.
	 * 
	 * @see {@link org.eclipse.papyrus.uml.properties.databinding.UMLLabelObservableValue.doGetValue()}.
	 * 
	 * @param namedElement
	 *            the {@link NamedElement} which contains the label to get,
	 * @return the label of the {@link NamedElement}.
	 */
	public String getLabel(NamedElement namedElement) {
		return UMLLabelInternationalization.getInstance().getLabelWithoutUML(namedElement);
	}

	/**
	 * Set Label on a given {@link NamedElement}.
	 * 
	 * @see {@link org.eclipse.papyrus.uml.properties.databinding.UMLLabelObservableValue.doSetValue(Object)}.
	 * 
	 * @param namedElement
	 *            the {@link NamedElement} with the label to update,
	 * @param label
	 *            the new label value.
	 */
	public void setLabel(NamedElement namedElement, String label) {
		UMLLabelInternationalization.getInstance().setLabel(namedElement, label, null);
	}

	// IntegerEditor services

	/**
	 * Convert a String into an Integer.
	 * 
	 * @see {@link org.eclipse.papyrus.infra.widgets.editors.IntegerEditor.IntegerEditor(...).new
	 *      IConverter() {...}.convert(Object)}.
	 * 
	 * @param fromObject
	 *            the String to convert
	 * @return the String as Integer.
	 */
	public Integer convertStringToInteger(Object fromObject) {
		Integer stringToInteger = null;
		if (fromObject instanceof String) {
			String newString = ((String) fromObject).replaceAll(SPACE, EMPTY);
			try {
				stringToInteger = Integer.valueOf(Integer.parseInt(newString));
				// CHECKSTYLE:OFF
			} catch (Exception ex) {
				// CHECKSTYLE:ON
				Activator.getDefault().getLog()
						.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, ex.getMessage(), ex));
			}
		}
		return stringToInteger;
	}

	// UnlimitedNaturalEditor services

	/**
	 * Convert a String into {@link LiteralUnlimitedNatural}.
	 * 
	 * @see {@link org.eclipse.papyrus.infra.widgets.editors.UnlimitedNaturalEditor.UnlimitedNaturalEditor(...).new
	 *      IConverter() {...}.convert(Object)}.
	 * 
	 * @param fromObject
	 *            the {@link String} to convert
	 * @return the String as {@link LiteralUnlimitedNatural}.
	 */
	public Integer convertStringToIUnlimitedNatural(Object fromObject) {
		Integer stringToIUnlimitedNatural = Integer.valueOf(0);
		if (fromObject instanceof String) {
			String newString = ((String) fromObject).replaceAll(SPACE, EMPTY);
			if (newString.equals("*")) { //$NON-NLS-1$
				stringToIUnlimitedNatural = Integer.valueOf(-1);
			} else {
				try {
					stringToIUnlimitedNatural = Integer.valueOf(Integer.parseInt(newString));
					// CHECKSTYLE:OFF
				} catch (Exception ex) {
					// CHECKSTYLE:ON
					Activator.getDefault().getLog()
							.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, ex.getMessage(), ex));
					return null;
				}
			}
		}
		return stringToIUnlimitedNatural;
	}

	// Double editor service
	/**
	 * Convert a String into an Integer.
	 * 
	 * @see {@link org.eclipse.papyrus.infra.widgets.editors.DoubleEditor.DoubleEditor(...).new
	 *      IConverter() {...}.convert(Object)}.
	 * 
	 * @param fromObject
	 *            the String to convert
	 * @return the String as Real.
	 */
	public Double convertStringToReal(Object fromObject) {
		Double stringToReal = null;
		if (fromObject instanceof String) {
			String newString = ((String) fromObject).replaceAll(SPACE, EMPTY);
			try {
				stringToReal = Double.valueOf(Double.parseDouble(newString));
			} catch (NumberFormatException ex) {
				Activator.getDefault().getLog()
						.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, ex.getMessage(), ex));
				return null;
			}
		}
		return stringToReal;
	}

	/**
	 * Convert a {@link LiteralUnlimitedNatural} into String.
	 * 
	 * @see {@link org.eclipse.papyrus.infra.widgets.editors.UnlimitedNaturalEditor.UnlimitedNaturalEditor(...).new
	 *      IConverter() {...}.convert(Object)}
	 * 
	 * @param fromObject
	 *            the {@link LiteralUnlimitedNatural} to convert
	 * @return {@link LiteralUnlimitedNatural} as String.
	 */
	public String convertUnlimitedNaturalToString(Object fromObject) {
		String unlimitedNaturalAsString = EMPTY;
		if (fromObject instanceof Integer) {
			if (((Integer) fromObject).intValue() == -1) {
				unlimitedNaturalAsString = "*"; //$NON-NLS-1$
			} else {
				unlimitedNaturalAsString = Integer.toString(((Integer) fromObject).intValue());
			}
		}
		return unlimitedNaturalAsString; // $NON-NLS-1$
	}

	/**
	 * Get list of boolean candidates.
	 * 
	 * @param obj
	 *            A given element which contains boolean attribute.
	 * @return list of boolean candidates.
	 */
	public List<Boolean> getBooleanEnumerations(Element obj) {
		return Arrays.asList(Boolean.TRUE, Boolean.FALSE);
	}

	/**
	 * Check if a {@link org.eclipse.uml2.uml.Class} has Metaclass stereotype. Copy
	 * from
	 * {@link org.eclipse.papyrus.uml.properties.constraints.HasStereotypeConstraint.match(Object)}.
	 * 
	 * @param obj
	 *            the object to check
	 * @return <code>true</code> if {@link org.eclipse.uml2.uml.Class} has Metaclass
	 *         stereotype, <code>false</code>otherwise.
	 */
	public boolean isMetaclass(Element obj) {
		boolean isMetaclass = false;
		if (obj instanceof org.eclipse.uml2.uml.Class) {
			Stereotype stereotype = UMLUtil.getAppliedStereotype(obj, STANDARD_METACLASS, false);
			isMetaclass = stereotype != null;
		}
		return isMetaclass;
	}

	/**
	 * Check if an {@link Element} is attached to a resource.
	 * 
	 * @see {@link org.eclipse.papyrus.infra.constraints.constraints.AttachedToResourceConstraint.match(Object)}.
	 * 
	 * @param obj
	 *            the object to check
	 * @return <code>true</code> if {@link Element} is attached to a resource,
	 *         <code>false</code>otherwise.
	 */
	public boolean isAttachedToResource(Element obj) {
		boolean result = false;
		if (obj != null) {
			result = obj.eResource() != null;
		}
		return result;
	}

	/**
	 * Check if a given Object has a specific EAnnotation.
	 * 
	 * @see {@link org.eclipse.papyrus.infra.constraints.constraints.HasEAnnotationConstraint.match(Object)}.
	 * 
	 * @param obj
	 *            the object to check
	 * @return <code>true</code> if Object has a specific EAnnotation,
	 *         <code>false</code>otherwise.
	 */
	public boolean hasEAnnotation(EObject obj) {
		if (obj instanceof EModelElement) {
			EModelElement modelElement = (EModelElement) obj;
			return modelElement.getEAnnotation(PropertiesProfileDefinitionServices.PAPYRUS_VERSION) != null;
		}
		return false;
	}

	/**
	 * Gets all comments having the specified eobject as annotated element.
	 * 
	 * @param obj
	 *            the object to find
	 * @return all comments having the specified eobject as annotated element
	 */
	public List<Comment> getAllAppliedComments(EObject obj) {
		List<Comment> comments = new ArrayList<>();
		if (obj instanceof Element) {
			Element element = (Element) obj;
			ECrossReferenceAdapter crossReferenceAdapter = ECrossReferenceAdapter
					.getCrossReferenceAdapter(element);
			List<Comment> collect = crossReferenceAdapter
					.getInverseReferences(element, UMLPackage.eINSTANCE.getComment_AnnotatedElement(), true).stream()
					.map(setting -> setting.getEObject())
					.filter(Comment.class::isInstance)
					.map(Comment.class::cast)
					.collect(Collectors.toList());
			comments.addAll(collect);
		}
		return comments;
	}
}
