/**
 * Copyright (c) 2013 CEA LIST.
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
 * 	Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 */
package org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.util;

import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.*;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.FeatureLabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.ILabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.NattablelabelproviderPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.ObjectLabelProviderConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.StyledElement;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.NattablelabelproviderPackage
 * @generated
 */
public class NattablelabelproviderSwitch<T> extends Switch<T> {

	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static NattablelabelproviderPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NattablelabelproviderSwitch() {
		if (modelPackage == null) {
			modelPackage = NattablelabelproviderPackage.eINSTANCE;
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
			case NattablelabelproviderPackage.ILABEL_PROVIDER_CONFIGURATION: {
				ILabelProviderConfiguration iLabelProviderConfiguration = (ILabelProviderConfiguration)theEObject;
				T result = caseILabelProviderConfiguration(iLabelProviderConfiguration);
				if (result == null) result = caseStyledElement(iLabelProviderConfiguration);
				if (result == null) result = caseEModelElement(iLabelProviderConfiguration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case NattablelabelproviderPackage.FEATURE_LABEL_PROVIDER_CONFIGURATION: {
				FeatureLabelProviderConfiguration featureLabelProviderConfiguration = (FeatureLabelProviderConfiguration)theEObject;
				T result = caseFeatureLabelProviderConfiguration(featureLabelProviderConfiguration);
				if (result == null) result = caseObjectLabelProviderConfiguration(featureLabelProviderConfiguration);
				if (result == null) result = caseILabelProviderConfiguration(featureLabelProviderConfiguration);
				if (result == null) result = caseStyledElement(featureLabelProviderConfiguration);
				if (result == null) result = caseEModelElement(featureLabelProviderConfiguration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case NattablelabelproviderPackage.OBJECT_LABEL_PROVIDER_CONFIGURATION: {
				ObjectLabelProviderConfiguration objectLabelProviderConfiguration = (ObjectLabelProviderConfiguration)theEObject;
				T result = caseObjectLabelProviderConfiguration(objectLabelProviderConfiguration);
				if (result == null) result = caseILabelProviderConfiguration(objectLabelProviderConfiguration);
				if (result == null) result = caseStyledElement(objectLabelProviderConfiguration);
				if (result == null) result = caseEModelElement(objectLabelProviderConfiguration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case NattablelabelproviderPackage.OPERATION_LABEL_PROVIDER_CONFIGURATION: {
				OperationLabelProviderConfiguration operationLabelProviderConfiguration = (OperationLabelProviderConfiguration)theEObject;
				T result = caseOperationLabelProviderConfiguration(operationLabelProviderConfiguration);
				if (result == null) result = caseObjectLabelProviderConfiguration(operationLabelProviderConfiguration);
				if (result == null) result = caseILabelProviderConfiguration(operationLabelProviderConfiguration);
				if (result == null) result = caseStyledElement(operationLabelProviderConfiguration);
				if (result == null) result = caseEModelElement(operationLabelProviderConfiguration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ILabel Provider Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ILabel Provider Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseILabelProviderConfiguration(ILabelProviderConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Feature Label Provider Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Feature Label Provider Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFeatureLabelProviderConfiguration(FeatureLabelProviderConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Object Label Provider Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Object Label Provider Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseObjectLabelProviderConfiguration(ObjectLabelProviderConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Operation Label Provider Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Operation Label Provider Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOperationLabelProviderConfiguration(OperationLabelProviderConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EModel Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EModel Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEModelElement(EModelElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Styled Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Styled Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseStyledElement(StyledElement object) {
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
} // NattablelabelproviderSwitch
