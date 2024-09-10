/**
 * Copyright (c) 2014 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 */
package org.eclipse.papyrus.infra.gmfdiag.assistant.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.gmf.runtime.common.core.service.IProvider;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider;
import org.eclipse.papyrus.infra.filters.Filter;

import org.eclipse.papyrus.infra.gmfdiag.assistant.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 *
 * @see org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage
 * @generated
 */
public class AssistantSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected static AssistantPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public AssistantSwitch() {
		if (modelPackage == null) {
			modelPackage = AssistantPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param ePackage
	 *            the package in question.
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
	 *
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case AssistantPackage.ASSISTED_ELEMENT_TYPE_FILTER: {
			AssistedElementTypeFilter assistedElementTypeFilter = (AssistedElementTypeFilter) theEObject;
			T result = caseAssistedElementTypeFilter(assistedElementTypeFilter);
			if (result == null) {
				result = caseFilter(assistedElementTypeFilter);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case AssistantPackage.MODELING_ASSISTANT_PROVIDER: {
			ModelingAssistantProvider modelingAssistantProvider = (ModelingAssistantProvider) theEObject;
			T result = caseModelingAssistantProvider(modelingAssistantProvider);
			if (result == null) {
				result = caseIModelingAssistantProvider(modelingAssistantProvider);
			}
			if (result == null) {
				result = caseIProvider(modelingAssistantProvider);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case AssistantPackage.ASSISTANT: {
			Assistant assistant = (Assistant) theEObject;
			T result = caseAssistant(assistant);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case AssistantPackage.POPUP_ASSISTANT: {
			PopupAssistant popupAssistant = (PopupAssistant) theEObject;
			T result = casePopupAssistant(popupAssistant);
			if (result == null) {
				result = caseAssistant(popupAssistant);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case AssistantPackage.CONNECTION_ASSISTANT: {
			ConnectionAssistant connectionAssistant = (ConnectionAssistant) theEObject;
			T result = caseConnectionAssistant(connectionAssistant);
			if (result == null) {
				result = caseAssistant(connectionAssistant);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case AssistantPackage.IMODELING_ASSISTANT_PROVIDER: {
			IModelingAssistantProvider iModelingAssistantProvider = (IModelingAssistantProvider) theEObject;
			T result = caseIModelingAssistantProvider(iModelingAssistantProvider);
			if (result == null) {
				result = caseIProvider(iModelingAssistantProvider);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case AssistantPackage.IPROVIDER: {
			IProvider iProvider = (IProvider) theEObject;
			T result = caseIProvider(iProvider);
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		case AssistantPackage.ELEMENT_TYPE_FILTER: {
			ElementTypeFilter elementTypeFilter = (ElementTypeFilter) theEObject;
			T result = caseElementTypeFilter(elementTypeFilter);
			if (result == null) {
				result = caseFilter(elementTypeFilter);
			}
			if (result == null) {
				result = defaultCase(theEObject);
			}
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Assisted Element Type Filter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Assisted Element Type Filter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAssistedElementTypeFilter(AssistedElementTypeFilter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Modeling Assistant Provider</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Modeling Assistant Provider</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseModelingAssistantProvider(ModelingAssistantProvider object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Assistant</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Assistant</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAssistant(Assistant object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IModeling Assistant Provider</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IModeling Assistant Provider</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIModelingAssistantProvider(IModelingAssistantProvider object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IProvider</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IProvider</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIProvider(IProvider object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Connection Assistant</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Connection Assistant</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseConnectionAssistant(ConnectionAssistant object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Popup Assistant</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Popup Assistant</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePopupAssistant(PopupAssistant object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Element Type Filter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Element Type Filter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseElementTypeFilter(ElementTypeFilter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Filter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Filter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFilter(Filter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 *
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} // AssistantSwitch
