/**
 * Copyright (c) 2022, 2023 CEA LIST, Obeo
 * 
 *  All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *    Obeo - Initial API and implementation
 */
package org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.util;

import org.eclipse.eef.EEFContainerDescription;
import org.eclipse.eef.EEFControlDescription;
import org.eclipse.eef.EEFWidgetDescription;

import org.eclipse.eef.ext.widgets.reference.eefextwidgetsreference.EEFExtReferenceDescription;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.*;

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
 * @see org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EefAdvancedControlsPackage
 * @generated
 */
public class EefAdvancedControlsSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static EefAdvancedControlsPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EefAdvancedControlsSwitch() {
		if (modelPackage == null) {
			modelPackage = EefAdvancedControlsPackage.eINSTANCE;
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
			case EefAdvancedControlsPackage.EEF_EXT_EDITABLE_REFERENCE_DESCRIPTION: {
				EEFExtEditableReferenceDescription eefExtEditableReferenceDescription = (EEFExtEditableReferenceDescription)theEObject;
				T result = caseEEFExtEditableReferenceDescription(eefExtEditableReferenceDescription);
				if (result == null) result = caseEEFExtReferenceDescription(eefExtEditableReferenceDescription);
				if (result == null) result = caseEEFWidgetDescription(eefExtEditableReferenceDescription);
				if (result == null) result = caseEEFControlDescription(eefExtEditableReferenceDescription);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EefAdvancedControlsPackage.EEF_CONTAINER_BORDER_DESCRIPTION: {
				EEFContainerBorderDescription eefContainerBorderDescription = (EEFContainerBorderDescription)theEObject;
				T result = caseEEFContainerBorderDescription(eefContainerBorderDescription);
				if (result == null) result = caseEEFContainerDescription(eefContainerBorderDescription);
				if (result == null) result = caseEEFControlDescription(eefContainerBorderDescription);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EefAdvancedControlsPackage.EEF_LANGUAGE_EXPRESSION_DESCRIPTION: {
				EEFLanguageExpressionDescription eefLanguageExpressionDescription = (EEFLanguageExpressionDescription)theEObject;
				T result = caseEEFLanguageExpressionDescription(eefLanguageExpressionDescription);
				if (result == null) result = caseEEFWidgetDescription(eefLanguageExpressionDescription);
				if (result == null) result = caseEEFControlDescription(eefLanguageExpressionDescription);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EefAdvancedControlsPackage.EEF_PROFILE_APPLICATION_DESCRIPTION: {
				EEFProfileApplicationDescription eefProfileApplicationDescription = (EEFProfileApplicationDescription)theEObject;
				T result = caseEEFProfileApplicationDescription(eefProfileApplicationDescription);
				if (result == null) result = caseEEFWidgetDescription(eefProfileApplicationDescription);
				if (result == null) result = caseEEFControlDescription(eefProfileApplicationDescription);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EefAdvancedControlsPackage.EEF_STEREOTYPE_APPLICATION_DESCRIPTION: {
				EEFStereotypeApplicationDescription eefStereotypeApplicationDescription = (EEFStereotypeApplicationDescription)theEObject;
				T result = caseEEFStereotypeApplicationDescription(eefStereotypeApplicationDescription);
				if (result == null) result = caseEEFContainerDescription(eefStereotypeApplicationDescription);
				if (result == null) result = caseEEFControlDescription(eefStereotypeApplicationDescription);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case EefAdvancedControlsPackage.EEF_INPUT_CONTENT_PAPYRUS_REFERENCE_DESCRIPTION: {
				EEFInputContentPapyrusReferenceDescription eefInputContentPapyrusReferenceDescription = (EEFInputContentPapyrusReferenceDescription)theEObject;
				T result = caseEEFInputContentPapyrusReferenceDescription(eefInputContentPapyrusReferenceDescription);
				if (result == null) result = caseEEFExtEditableReferenceDescription(eefInputContentPapyrusReferenceDescription);
				if (result == null) result = caseEEFExtReferenceDescription(eefInputContentPapyrusReferenceDescription);
				if (result == null) result = caseEEFWidgetDescription(eefInputContentPapyrusReferenceDescription);
				if (result == null) result = caseEEFControlDescription(eefInputContentPapyrusReferenceDescription);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EEF Ext Editable Reference Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EEF Ext Editable Reference Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEEFExtEditableReferenceDescription(EEFExtEditableReferenceDescription object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EEF Container Border Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EEF Container Border Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEEFContainerBorderDescription(EEFContainerBorderDescription object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EEF Language Expression Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EEF Language Expression Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEEFLanguageExpressionDescription(EEFLanguageExpressionDescription object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EEF Profile Application Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EEF Profile Application Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEEFProfileApplicationDescription(EEFProfileApplicationDescription object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EEF Stereotype Application Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EEF Stereotype Application Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEEFStereotypeApplicationDescription(EEFStereotypeApplicationDescription object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EEF Input Content Papyrus Reference Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EEF Input Content Papyrus Reference Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEEFInputContentPapyrusReferenceDescription(EEFInputContentPapyrusReferenceDescription object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EEF Control Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EEF Control Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEEFControlDescription(EEFControlDescription object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EEF Widget Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EEF Widget Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEEFWidgetDescription(EEFWidgetDescription object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EEF Ext Reference Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EEF Ext Reference Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEEFExtReferenceDescription(EEFExtReferenceDescription object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EEF Container Description</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EEF Container Description</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEEFContainerDescription(EEFContainerDescription object) {
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

} //EefAdvancedControlsSwitch
