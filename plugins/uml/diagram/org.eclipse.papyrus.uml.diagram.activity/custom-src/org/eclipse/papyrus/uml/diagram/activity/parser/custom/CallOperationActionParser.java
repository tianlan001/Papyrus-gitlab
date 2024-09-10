/*****************************************************************************
 * Copyright (c) 2009 Atos Origin.
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
 *   Atos Origin - Initial API and implementation
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.parser.custom;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser;
import org.eclipse.papyrus.uml.diagram.activity.parsers.MessageFormatParser;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.CallOperationAction;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * A specific parser for displaying the label of a CallOperationAction. This
 * parser refreshes the text displayed for the CallOperationAction.
 */
public class CallOperationActionParser extends MessageFormatParser implements ISemanticParser {

	private static final UMLPackage eUML = UMLPackage.eINSTANCE;

	public CallOperationActionParser(EAttribute[] features, EAttribute[] editableFeatures) {
		super(features, editableFeatures);
	}

	public CallOperationActionParser(EAttribute[] features) {
		super(features);
	}

	public CallOperationActionParser() {
		super(new EAttribute[] { UMLPackage.eINSTANCE.getNamedElement_Name() });
	}

	protected EStructuralFeature getEStructuralFeature(Object notification) {
		EStructuralFeature featureImpl = null;
		if (notification instanceof Notification) {
			Object feature = ((Notification) notification).getFeature();
			if (feature instanceof EStructuralFeature) {
				featureImpl = (EStructuralFeature) feature;
			}
		}
		return featureImpl;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.papyrus.uml.diagram.sequence.parsers.AbstractParser#isAffectingEvent
	 * (java.lang.Object , int)
	 */
	@Override
	public boolean isAffectingEvent(Object event, int flags) {
		EStructuralFeature feature = getEStructuralFeature(event);
		return isAffectingFeature(feature);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.uml.diagram.sequence.parsers.MessageFormatParser#
	 * getPrintString(org.eclipse .core.runtime.IAdaptable, int)
	 */
	@Override
	public String getPrintString(IAdaptable element, int flags) {
		Object obj = element.getAdapter(EObject.class);
		if (obj instanceof CallOperationAction) {
			CallOperationAction action = (CallOperationAction) obj;
			String name = UMLLabelInternationalization.getInstance().getLabel(action);
			String operation = "";
			if (name == null) {
				name = "";
			}
			if (action.getOperation() != null) {
				operation = UMLLabelInternationalization.getInstance().getLabel(action.getOperation());
				if (operation == null) {
					operation = "";
				}
			}
			// name, operation and className are initialized with non null
			// values
			return getPrintString(name, operation);
		}
		return " ";
	}

	/**
	 * Get the string to print with the given elements
	 *
	 * @param name
	 *            the name of the node or ""
	 * @param operation
	 *            the name of the operation or ""
	 * @param className
	 *            the name of the operation class or ""
	 * @return the string to print
	 */
	private String getPrintString(String name, String operation) {
		return isEmpty(name) ? operation : name;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser#
	 * areSemanticElementsAffected (org.eclipse.emf.ecore.EObject,
	 * java.lang.Object)
	 */
	@Override
	public boolean areSemanticElementsAffected(EObject listener, Object notification) {
		EStructuralFeature feature = getEStructuralFeature(notification);
		return isAffectingFeature(feature);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser#
	 * getSemanticElementsBeingParsed (org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public List<?> getSemanticElementsBeingParsed(EObject element) {
		List<Element> semanticElementsBeingParsed = new ArrayList<>();
		if (element instanceof CallOperationAction) {
			CallOperationAction action = (CallOperationAction) element;
			semanticElementsBeingParsed.add(action);
			Operation operation = action.getOperation();
			if (operation != null) {
				semanticElementsBeingParsed.add(operation);
			}
		}
		return semanticElementsBeingParsed;
	}

	/**
	 * Determines if the given feature has to be taken into account in this
	 * parser
	 *
	 * @param feature
	 *            the feature to test
	 * @return true if is valid, false otherwise
	 */
	private boolean isAffectingFeature(EStructuralFeature feature) {
		return eUML.getNamedElement_Name().equals(feature) || //
				eUML.getCallOperationAction_Operation().equals(feature);
	}

	private static boolean isEmpty(String text) {
		return text == null || text.length() == 0;
	}
}
