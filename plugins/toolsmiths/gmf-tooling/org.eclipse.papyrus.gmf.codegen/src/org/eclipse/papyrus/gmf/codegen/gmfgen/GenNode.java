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

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.common.util.EList;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenChildContainer;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCompartment;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLinkEnd;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNodeLabel;
import org.eclipse.papyrus.gmf.codegen.gmfgen.TypeModelFacet;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Gen Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * EClass mapped to Node
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode#getModelFacet <em>Model Facet</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode#getLabels <em>Labels</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode#getCompartments <em>Compartments</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode#getPrimaryDragEditPolicyQualifiedClassName <em>Primary Drag Edit Policy Qualified Class Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode#getGraphicalNodeEditPolicyClassName <em>Graphical Node Edit Policy Class Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode#getCreateCommandClassName <em>Create Command Class Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode#getReorientedIncomingLinks <em>Reoriented Incoming Links</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode#getRefreshHook <em>Refresh Hook</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode#isSpecificNotificationEvent <em>Specific Notification Event</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenNode()
 * @model abstract="true"
 *        annotation="http://www.eclipse.org/gmf/2005/constraints ocl='diagramRunTimeClass.ecoreClass.eAllSuperTypes-&gt;including(diagramRunTimeClass.ecoreClass)-&gt;one(ePackage.name = \'notation\' and name = \'Node\')' description='Node \'Diagram Runtime Class\' must be a notation::Node or sub-class'"
 * @generated
 */
public interface GenNode extends GenChildContainer, GenLinkEnd {

	public static final String CLASS_NAME_PREFIX = "Node"; //$NON-NLS-1$

	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain Meta Class</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Delegates to modelFacet
	 * <!-- end-model-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	GenClass getDomainMetaClass();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * valid only when there are compartments
	 * <!-- end-model-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getGraphicalNodeEditPolicyQualifiedClassName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getCreateCommandQualifiedClassName();

	/**
	 * Returns the value of the '<em><b>Labels</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenNodeLabel}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenNodeLabel#getNode <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Labels</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Labels</em>' containment reference list.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenNode_Labels()
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GenNodeLabel#getNode
	 * @model opposite="node" containment="true"
	 * @generated
	 */
	EList<GenNodeLabel> getLabels();

	/**
	 * Returns the value of the '<em><b>Compartments</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCompartment}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCompartment#getNode <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Compartments</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compartments</em>' reference list.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenNode_Compartments()
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GenCompartment#getNode
	 * @model opposite="node"
	 * @generated
	 */
	EList<GenCompartment> getCompartments();

	/**
	 * Returns the value of the '<em><b>Primary Drag Edit Policy Qualified Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Custom primary drag edit policy
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Primary Drag Edit Policy Qualified Class Name</em>' attribute.
	 * @see #setPrimaryDragEditPolicyQualifiedClassName(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenNode_PrimaryDragEditPolicyQualifiedClassName()
	 * @model
	 * @generated
	 */
	String getPrimaryDragEditPolicyQualifiedClassName();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode#getPrimaryDragEditPolicyQualifiedClassName <em>Primary Drag Edit Policy Qualified Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primary Drag Edit Policy Qualified Class Name</em>' attribute.
	 * @see #getPrimaryDragEditPolicyQualifiedClassName()
	 * @generated
	 */
	void setPrimaryDragEditPolicyQualifiedClassName(String value);

	/**
	 * Returns the value of the '<em><b>Graphical Node Edit Policy Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Graphical Node Edit Policy Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Graphical Node Edit Policy Class Name</em>' attribute.
	 * @see #setGraphicalNodeEditPolicyClassName(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenNode_GraphicalNodeEditPolicyClassName()
	 * @model
	 * @generated
	 */
	String getGraphicalNodeEditPolicyClassName();

	public static final String GRAPHICAL_NODE_EDIT_POLICY_SUFFIX = "GraphicalNodeEditPolicy"; //$NON-NLS-1$

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode#getGraphicalNodeEditPolicyClassName <em>Graphical Node Edit Policy Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Graphical Node Edit Policy Class Name</em>' attribute.
	 * @see #getGraphicalNodeEditPolicyClassName()
	 * @generated
	 */
	void setGraphicalNodeEditPolicyClassName(String value);

	/**
	 * Returns the value of the '<em><b>Create Command Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Create Command Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Create Command Class Name</em>' attribute.
	 * @see #setCreateCommandClassName(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenNode_CreateCommandClassName()
	 * @model
	 * @generated
	 */
	String getCreateCommandClassName();

	String CREATE_COMMAND_SUFFIX = "CreateCommand"; //$NON-NLS-1$

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode#getCreateCommandClassName <em>Create Command Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Create Command Class Name</em>' attribute.
	 * @see #getCreateCommandClassName()
	 * @generated
	 */
	void setCreateCommandClassName(String value);

	/**
	 * Returns the value of the '<em><b>Reoriented Incoming Links</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reoriented Incoming Links</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reoriented Incoming Links</em>' reference list.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenNode_ReorientedIncomingLinks()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	EList<GenLink> getReorientedIncomingLinks();

	/**
	 * Returns the value of the '<em><b>Refresh Hook</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Bug 569174 : from PropertyRefreshHook
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Refresh Hook</em>' containment reference.
	 * @see #setRefreshHook(RefreshHook)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenNode_RefreshHook()
	 * @model containment="true"
	 * @generated
	 */
	RefreshHook getRefreshHook();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode#getRefreshHook <em>Refresh Hook</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Refresh Hook</em>' containment reference.
	 * @see #getRefreshHook()
	 * @generated
	 */
	void setRefreshHook(RefreshHook value);

	/**
	 * Returns the value of the '<em><b>Specific Notification Event</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Bug 569174 : from ExtendedGenView
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Specific Notification Event</em>' attribute.
	 * @see #setSpecificNotificationEvent(boolean)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenNode_SpecificNotificationEvent()
	 * @model
	 * @generated
	 */
	boolean isSpecificNotificationEvent();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode#isSpecificNotificationEvent <em>Specific Notification Event</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Specific Notification Event</em>' attribute.
	 * @see #isSpecificNotificationEvent()
	 * @generated
	 */
	void setSpecificNotificationEvent(boolean value);

	/**
	 * Returns the value of the '<em><b>Model Facet</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Model Facet</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Model Facet</em>' containment reference.
	 * @see #setModelFacet(TypeModelFacet)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenNode_ModelFacet()
	 * @model containment="true"
	 * @generated
	 */
	TypeModelFacet getModelFacet();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode#getModelFacet <em>Model Facet</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Model Facet</em>' containment reference.
	 * @see #getModelFacet()
	 * @generated
	 */
	void setModelFacet(TypeModelFacet value);

} // GenNode
