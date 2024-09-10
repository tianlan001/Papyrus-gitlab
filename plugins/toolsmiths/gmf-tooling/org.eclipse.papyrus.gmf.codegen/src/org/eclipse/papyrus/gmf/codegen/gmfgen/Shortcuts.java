/*******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, ARTAL
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 * 
 *  SPDX-License-Identifier: EPL-2.0
 * 
 *  Contributors:
 *     Borland - initial API and implementation
 *     Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 ******************************************************************************/
/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.papyrus.gmf.codegen.gmfgen;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.gmf.codegen.gmfgen.ProviderPriority;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Shortcuts</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.Shortcuts#getShortcutsDecoratorProviderClassName <em>Shortcuts Decorator Provider Class Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.Shortcuts#getShortcutsDecoratorProviderPriority <em>Shortcuts Decorator Provider Priority</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.Shortcuts#getCreateShortcutActionClassName <em>Create Shortcut Action Class Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.Shortcuts#getCreateShortcutDecorationsCommandClassName <em>Create Shortcut Decorations Command Class Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.Shortcuts#getShortcutPropertyTesterClassName <em>Shortcut Property Tester Class Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.Shortcuts#getContainsShortcutsTo <em>Contains Shortcuts To</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.Shortcuts#getShortcutsProvidedFor <em>Shortcuts Provided For</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getShortcuts()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface Shortcuts extends EObject {
	/**
	 * Returns the value of the '<em><b>Shortcuts Decorator Provider Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shortcuts Decorator Provider Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shortcuts Decorator Provider Class Name</em>' attribute.
	 * @see #setShortcutsDecoratorProviderClassName(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getShortcuts_ShortcutsDecoratorProviderClassName()
	 * @model
	 * @generated
	 */
	String getShortcutsDecoratorProviderClassName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.Shortcuts#getShortcutsDecoratorProviderClassName <em>Shortcuts Decorator Provider Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shortcuts Decorator Provider Class Name</em>' attribute.
	 * @see #getShortcutsDecoratorProviderClassName()
	 * @generated
	 */
	void setShortcutsDecoratorProviderClassName(String value);

	/**
	 * Returns the value of the '<em><b>Shortcuts Decorator Provider Priority</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.papyrus.gmf.codegen.gmfgen.ProviderPriority}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shortcuts Decorator Provider Priority</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shortcuts Decorator Provider Priority</em>' attribute.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.ProviderPriority
	 * @see #setShortcutsDecoratorProviderPriority(ProviderPriority)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getShortcuts_ShortcutsDecoratorProviderPriority()
	 * @model
	 * @generated
	 */
	ProviderPriority getShortcutsDecoratorProviderPriority();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.Shortcuts#getShortcutsDecoratorProviderPriority <em>Shortcuts Decorator Provider Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shortcuts Decorator Provider Priority</em>' attribute.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.ProviderPriority
	 * @see #getShortcutsDecoratorProviderPriority()
	 * @generated
	 */
	void setShortcutsDecoratorProviderPriority(ProviderPriority value);

	/**
	 * Returns the value of the '<em><b>Create Shortcut Action Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Create Shortcut Action Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Create Shortcut Action Class Name</em>' attribute.
	 * @see #setCreateShortcutActionClassName(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getShortcuts_CreateShortcutActionClassName()
	 * @model annotation="http://www.eclipse.org/gmf/2006/deprecated documentation='Use CreateShortcutAction instead'"
	 * @generated
	 */
	String getCreateShortcutActionClassName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.Shortcuts#getCreateShortcutActionClassName <em>Create Shortcut Action Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Create Shortcut Action Class Name</em>' attribute.
	 * @see #getCreateShortcutActionClassName()
	 * @generated
	 */
	void setCreateShortcutActionClassName(String value);

	/**
	 * Returns the value of the '<em><b>Create Shortcut Decorations Command Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Create Shortcut Decorations Command Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Create Shortcut Decorations Command Class Name</em>' attribute.
	 * @see #setCreateShortcutDecorationsCommandClassName(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getShortcuts_CreateShortcutDecorationsCommandClassName()
	 * @model
	 * @generated
	 */
	String getCreateShortcutDecorationsCommandClassName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.Shortcuts#getCreateShortcutDecorationsCommandClassName <em>Create Shortcut Decorations Command Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Create Shortcut Decorations Command Class Name</em>' attribute.
	 * @see #getCreateShortcutDecorationsCommandClassName()
	 * @generated
	 */
	void setCreateShortcutDecorationsCommandClassName(String value);

	/**
	 * Returns the value of the '<em><b>Shortcut Property Tester Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shortcut Property Tester Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shortcut Property Tester Class Name</em>' attribute.
	 * @see #setShortcutPropertyTesterClassName(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getShortcuts_ShortcutPropertyTesterClassName()
	 * @model
	 * @generated
	 */
	String getShortcutPropertyTesterClassName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.Shortcuts#getShortcutPropertyTesterClassName <em>Shortcut Property Tester Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shortcut Property Tester Class Name</em>' attribute.
	 * @see #getShortcutPropertyTesterClassName()
	 * @generated
	 */
	void setShortcutPropertyTesterClassName(String value);

	/**
	 * Returns the value of the '<em><b>Contains Shortcuts To</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Set of domain meta-model file extensions. Elements of the corresponding models could be added as a shortcut on this diagram.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Contains Shortcuts To</em>' attribute list.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getShortcuts_ContainsShortcutsTo()
	 * @model
	 * @generated
	 */
	EList<String> getContainsShortcutsTo();

	/**
	 * Returns the value of the '<em><b>Shortcuts Provided For</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Set of domain meta-model names. Elements of this diagram domain model could be added as a shortcuts to the diagrams of the specified domains.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Shortcuts Provided For</em>' attribute list.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getShortcuts_ShortcutsProvidedFor()
	 * @model
	 * @generated
	 */
	EList<String> getShortcutsProvidedFor();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getShortcutsDecoratorProviderQualifiedClassName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 *        annotation="http://www.eclipse.org/gmf/2006/deprecated documentation='Use CreateShortcutAction instead'"
	 * @generated
	 */
	String getCreateShortcutActionQualifiedClassName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getCreateShortcutDecorationsCommandQualifiedClassName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getShortcutPropertyTesterQualifiedClassName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/gmf/2006/deprecated documentation='Use CreateShortcutAction instead'"
	 * @generated
	 */
	boolean generateCreateShortcutAction();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	boolean generateShortcutIcon();

} // Shortcuts
