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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Gen Diagram Updater</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagramUpdater#getEditorGen <em>Editor Gen</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagramUpdater#getDiagramUpdaterClassName <em>Diagram Updater Class Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagramUpdater#getNodeDescriptorClassName <em>Node Descriptor Class Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagramUpdater#getLinkDescriptorClassName <em>Link Descriptor Class Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagramUpdater#getUpdateCommandClassName <em>Update Command Class Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagramUpdater#getUpdateCommandID <em>Update Command ID</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagramUpdater#getCustomDiagramUpdaterSingletonPath <em>Custom Diagram Updater Singleton Path</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenDiagramUpdater()
 * @model
 * @generated
 */
public interface GenDiagramUpdater extends EObject {
	/**
	 * Returns the value of the '<em><b>Editor Gen</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getDiagramUpdater <em>Diagram Updater</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Editor Gen</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Editor Gen</em>' container reference.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenDiagramUpdater_EditorGen()
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getDiagramUpdater
	 * @model opposite="diagramUpdater" resolveProxies="false" transient="false" changeable="false"
	 * @generated
	 */
	GenEditorGenerator getEditorGen();

	/**
	 * Returns the value of the '<em><b>Diagram Updater Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Diagram Updater Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Diagram Updater Class Name</em>' attribute.
	 * @see #setDiagramUpdaterClassName(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenDiagramUpdater_DiagramUpdaterClassName()
	 * @model
	 * @generated
	 */
	String getDiagramUpdaterClassName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagramUpdater#getDiagramUpdaterClassName <em>Diagram Updater Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Diagram Updater Class Name</em>' attribute.
	 * @see #getDiagramUpdaterClassName()
	 * @generated
	 */
	void setDiagramUpdaterClassName(String value);

	/**
	 * Returns the value of the '<em><b>Node Descriptor Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Node Descriptor Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Node Descriptor Class Name</em>' attribute.
	 * @see #setNodeDescriptorClassName(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenDiagramUpdater_NodeDescriptorClassName()
	 * @model
	 * @generated
	 */
	String getNodeDescriptorClassName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagramUpdater#getNodeDescriptorClassName <em>Node Descriptor Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Node Descriptor Class Name</em>' attribute.
	 * @see #getNodeDescriptorClassName()
	 * @generated
	 */
	void setNodeDescriptorClassName(String value);

	/**
	 * Returns the value of the '<em><b>Link Descriptor Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Link Descriptor Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Link Descriptor Class Name</em>' attribute.
	 * @see #setLinkDescriptorClassName(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenDiagramUpdater_LinkDescriptorClassName()
	 * @model
	 * @generated
	 */
	String getLinkDescriptorClassName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagramUpdater#getLinkDescriptorClassName <em>Link Descriptor Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Link Descriptor Class Name</em>' attribute.
	 * @see #getLinkDescriptorClassName()
	 * @generated
	 */
	void setLinkDescriptorClassName(String value);

	/**
	 * Returns the value of the '<em><b>Update Command Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Update Command Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Update Command Class Name</em>' attribute.
	 * @see #setUpdateCommandClassName(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenDiagramUpdater_UpdateCommandClassName()
	 * @model
	 * @generated
	 */
	String getUpdateCommandClassName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagramUpdater#getUpdateCommandClassName <em>Update Command Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Update Command Class Name</em>' attribute.
	 * @see #getUpdateCommandClassName()
	 * @generated
	 */
	void setUpdateCommandClassName(String value);

	/**
	 * Returns the value of the '<em><b>Update Command ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Update Command ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Update Command ID</em>' attribute.
	 * @see #setUpdateCommandID(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenDiagramUpdater_UpdateCommandID()
	 * @model
	 * @generated
	 */
	String getUpdateCommandID();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagramUpdater#getUpdateCommandID <em>Update Command ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Update Command ID</em>' attribute.
	 * @see #getUpdateCommandID()
	 * @generated
	 */
	void setUpdateCommandID(String value);

	/**
	 * Returns the value of the '<em><b>Custom Diagram Updater Singleton Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Bug 569174 : from CustomDiagramUpdaterSingleton
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Custom Diagram Updater Singleton Path</em>' attribute.
	 * @see #setCustomDiagramUpdaterSingletonPath(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenDiagramUpdater_CustomDiagramUpdaterSingletonPath()
	 * @model
	 * @generated
	 */
	String getCustomDiagramUpdaterSingletonPath();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagramUpdater#getCustomDiagramUpdaterSingletonPath <em>Custom Diagram Updater Singleton Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Custom Diagram Updater Singleton Path</em>' attribute.
	 * @see #getCustomDiagramUpdaterSingletonPath()
	 * @generated
	 */
	void setCustomDiagramUpdaterSingletonPath(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getDiagramUpdaterQualifiedClassName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getNodeDescriptorQualifiedClassName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getLinkDescriptorQualifiedClassName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getUpdateCommandQualifiedClassName();

} // GenDiagramUpdater
