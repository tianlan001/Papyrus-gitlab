/**
 * Copyright (c) 2022, 2023 CEA LIST, Obeo
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
 */
package org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.*;

import org.eclipse.sirius.properties.AbstractContainerDescription;
import org.eclipse.sirius.properties.AbstractControlDescription;
import org.eclipse.sirius.properties.AbstractWidgetDescription;
import org.eclipse.sirius.properties.ContainerDescription;
import org.eclipse.sirius.properties.ControlDescription;
import org.eclipse.sirius.properties.WidgetDescription;

import org.eclipse.sirius.properties.ext.widgets.reference.propertiesextwidgetsreference.AbstractExtReferenceDescription;
import org.eclipse.sirius.properties.ext.widgets.reference.propertiesextwidgetsreference.ExtReferenceDescription;

import org.eclipse.sirius.viewpoint.description.DocumentedElement;
import org.eclipse.sirius.viewpoint.description.IdentifiedElement;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.PropertiesAdvancedControlsPackage
 * @generated
 */
public class PropertiesAdvancedControlsSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static PropertiesAdvancedControlsPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertiesAdvancedControlsSwitch() {
		if (modelPackage == null) {
			modelPackage = PropertiesAdvancedControlsPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case PropertiesAdvancedControlsPackage.EXT_EDITABLE_REFERENCE_DESCRIPTION: {
			ExtEditableReferenceDescription extEditableReferenceDescription = (ExtEditableReferenceDescription) theEObject;
			T result = caseExtEditableReferenceDescription(extEditableReferenceDescription);
			if (result == null)
				result = caseExtReferenceDescription(extEditableReferenceDescription);
			if (result == null)
				result = caseAbstractExtReferenceDescription(extEditableReferenceDescription);
			if (result == null)
				result = caseWidgetDescription(extEditableReferenceDescription);
			if (result == null)
				result = caseAbstractWidgetDescription(extEditableReferenceDescription);
			if (result == null)
				result = caseControlDescription(extEditableReferenceDescription);
			if (result == null)
				result = caseAbstractControlDescription(extEditableReferenceDescription);
			if (result == null)
				result = caseIdentifiedElement(extEditableReferenceDescription);
			if (result == null)
				result = caseDocumentedElement(extEditableReferenceDescription);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case PropertiesAdvancedControlsPackage.CONTAINER_BORDER_DESCRIPTION: {
			ContainerBorderDescription containerBorderDescription = (ContainerBorderDescription) theEObject;
			T result = caseContainerBorderDescription(containerBorderDescription);
			if (result == null)
				result = caseContainerDescription(containerBorderDescription);
			if (result == null)
				result = caseControlDescription(containerBorderDescription);
			if (result == null)
				result = caseAbstractContainerDescription(containerBorderDescription);
			if (result == null)
				result = caseAbstractControlDescription(containerBorderDescription);
			if (result == null)
				result = caseIdentifiedElement(containerBorderDescription);
			if (result == null)
				result = caseDocumentedElement(containerBorderDescription);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case PropertiesAdvancedControlsPackage.LANGUAGE_EXPRESSION_DESCRIPTION: {
			LanguageExpressionDescription languageExpressionDescription = (LanguageExpressionDescription) theEObject;
			T result = caseLanguageExpressionDescription(languageExpressionDescription);
			if (result == null)
				result = caseWidgetDescription(languageExpressionDescription);
			if (result == null)
				result = caseAbstractWidgetDescription(languageExpressionDescription);
			if (result == null)
				result = caseControlDescription(languageExpressionDescription);
			if (result == null)
				result = caseAbstractControlDescription(languageExpressionDescription);
			if (result == null)
				result = caseIdentifiedElement(languageExpressionDescription);
			if (result == null)
				result = caseDocumentedElement(languageExpressionDescription);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case PropertiesAdvancedControlsPackage.PROFILE_APPLICATION_DESCRIPTION: {
			ProfileApplicationDescription profileApplicationDescription = (ProfileApplicationDescription) theEObject;
			T result = caseProfileApplicationDescription(profileApplicationDescription);
			if (result == null)
				result = caseWidgetDescription(profileApplicationDescription);
			if (result == null)
				result = caseAbstractWidgetDescription(profileApplicationDescription);
			if (result == null)
				result = caseControlDescription(profileApplicationDescription);
			if (result == null)
				result = caseAbstractControlDescription(profileApplicationDescription);
			if (result == null)
				result = caseIdentifiedElement(profileApplicationDescription);
			if (result == null)
				result = caseDocumentedElement(profileApplicationDescription);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case PropertiesAdvancedControlsPackage.STEREOTYPE_APPLICATION_DESCRIPTION: {
			StereotypeApplicationDescription stereotypeApplicationDescription = (StereotypeApplicationDescription) theEObject;
			T result = caseStereotypeApplicationDescription(stereotypeApplicationDescription);
			if (result == null)
				result = caseWidgetDescription(stereotypeApplicationDescription);
			if (result == null)
				result = caseContainerDescription(stereotypeApplicationDescription);
			if (result == null)
				result = caseControlDescription(stereotypeApplicationDescription);
			if (result == null)
				result = caseAbstractContainerDescription(stereotypeApplicationDescription);
			if (result == null)
				result = caseAbstractControlDescription(stereotypeApplicationDescription);
			if (result == null)
				result = caseIdentifiedElement(stereotypeApplicationDescription);
			if (result == null)
				result = caseDocumentedElement(stereotypeApplicationDescription);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case PropertiesAdvancedControlsPackage.INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION: {
			InputContentPapyrusReferenceDescription inputContentPapyrusReferenceDescription = (InputContentPapyrusReferenceDescription) theEObject;
			T result = caseInputContentPapyrusReferenceDescription(inputContentPapyrusReferenceDescription);
			if (result == null)
				result = caseExtEditableReferenceDescription(inputContentPapyrusReferenceDescription);
			if (result == null)
				result = caseExtReferenceDescription(inputContentPapyrusReferenceDescription);
			if (result == null)
				result = caseAbstractExtReferenceDescription(inputContentPapyrusReferenceDescription);
			if (result == null)
				result = caseWidgetDescription(inputContentPapyrusReferenceDescription);
			if (result == null)
				result = caseAbstractWidgetDescription(inputContentPapyrusReferenceDescription);
			if (result == null)
				result = caseControlDescription(inputContentPapyrusReferenceDescription);
			if (result == null)
				result = caseAbstractControlDescription(inputContentPapyrusReferenceDescription);
			if (result == null)
				result = caseIdentifiedElement(inputContentPapyrusReferenceDescription);
			if (result == null)
				result = caseDocumentedElement(inputContentPapyrusReferenceDescription);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ext Editable Reference Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ext Editable Reference Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExtEditableReferenceDescription(ExtEditableReferenceDescription object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Container Border Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Container Border Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseContainerBorderDescription(ContainerBorderDescription object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Language Expression Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Language Expression Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLanguageExpressionDescription(LanguageExpressionDescription object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Profile Application Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Profile Application Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseProfileApplicationDescription(ProfileApplicationDescription object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Stereotype Application Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Stereotype Application Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStereotypeApplicationDescription(StereotypeApplicationDescription object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Input Content Papyrus Reference Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Input Content Papyrus Reference Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInputContentPapyrusReferenceDescription(InputContentPapyrusReferenceDescription object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Identified Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Identified Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIdentifiedElement(IdentifiedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Documented Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Documented Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDocumentedElement(DocumentedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Control Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Control Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractControlDescription(AbstractControlDescription object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Widget Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Widget Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractWidgetDescription(AbstractWidgetDescription object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Ext Reference Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Ext Reference Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractExtReferenceDescription(AbstractExtReferenceDescription object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Control Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Control Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseControlDescription(ControlDescription object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Widget Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Widget Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseWidgetDescription(WidgetDescription object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Ext Reference Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Ext Reference Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExtReferenceDescription(ExtReferenceDescription object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Container Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Container Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractContainerDescription(AbstractContainerDescription object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Container Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Container Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseContainerDescription(ContainerDescription object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //PropertiesAdvancedControlsSwitch
