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

import java.util.List;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Gen Common Base</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * One that combines attributes that are essential for diagram elements
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase#getDiagramRunTimeClass <em>Diagram Run Time Class</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase#getVisualID <em>Visual ID</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase#getElementType <em>Element Type</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase#getEditPartClassName <em>Edit Part Class Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase#getItemSemanticEditPolicyClassName <em>Item Semantic Edit Policy Class Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase#getNotationViewFactoryClassName <em>Notation View Factory Class Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase#getViewmap <em>Viewmap</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase#getStyles <em>Styles</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase#getBehaviour <em>Behaviour</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase#isSansDomain <em>Sans Domain</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase#getSuperEditPart <em>Super Edit Part</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase#getVisualIDOverride <em>Visual ID Override</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase#isUsingDeleteService <em>Using Delete Service</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase#isUsingReorientService <em>Using Reorient Service</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenCommonBase()
 * @model abstract="true"
 *        annotation="http://www.eclipse.org/gmf/2005/constraints ocl='styles-&gt;forAll(style|style.ecoreClass.eAllSuperTypes-&gt;including(style.ecoreClass)-&gt;one(ePackage.name = \'notation\' and name = \'Style\'))' description='Each style must be a notation::Style or sub-class'"
 * @generated
 */
public interface GenCommonBase extends EObject {
	/**
	 * Returns the value of the '<em><b>Diagram Run Time Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Diagram Run Time Class</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Diagram Run Time Class</em>' reference.
	 * @see #setDiagramRunTimeClass(GenClass)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenCommonBase_DiagramRunTimeClass()
	 * @model required="true"
	 * @generated
	 */
	GenClass getDiagramRunTimeClass();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase#getDiagramRunTimeClass <em>Diagram Run Time Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Diagram Run Time Class</em>' reference.
	 * @see #getDiagramRunTimeClass()
	 * @generated
	 */
	void setDiagramRunTimeClass(GenClass value);

	/**
	 * Returns the value of the '<em><b>Visual ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * way to decide which editpart to use for specific diagram element. Maps to attribute in DRT.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Visual ID</em>' attribute.
	 * @see #setVisualID(int)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenCommonBase_VisualID()
	 * @model required="true"
	 *        annotation="http://www.eclipse.org/gmf/2005/constraints ocl='visualID &gt;= 0' description='Visual ID must be a non-negative integer'"
	 * @generated
	 */
	int getVisualID();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase#getVisualID <em>Visual ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Visual ID</em>' attribute.
	 * @see #getVisualID()
	 * @generated
	 */
	void setVisualID(int value);

	/**
	 * Returns the value of the '<em><b>Element Type</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.ElementType#getDiagramElement <em>Diagram Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element Type</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element Type</em>' containment reference.
	 * @see #setElementType(ElementType)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenCommonBase_ElementType()
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.ElementType#getDiagramElement
	 * @model opposite="diagramElement" containment="true"
	 * @generated
	 */
	ElementType getElementType();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase#getElementType <em>Element Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Element Type</em>' containment reference.
	 * @see #getElementType()
	 * @generated
	 */
	void setElementType(ElementType value);

	/**
	 * Returns the value of the '<em><b>Edit Part Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edit Part Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edit Part Class Name</em>' attribute.
	 * @see #setEditPartClassName(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenCommonBase_EditPartClassName()
	 * @model
	 * @generated
	 */
	String getEditPartClassName();

	public static final String EDIT_PART_SUFFIX = "EditPart"; //$NON-NLS-1$

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase#getEditPartClassName <em>Edit Part Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Edit Part Class Name</em>' attribute.
	 * @see #getEditPartClassName()
	 * @generated
	 */
	void setEditPartClassName(String value);

	/**
	 * Returns the value of the '<em><b>Item Semantic Edit Policy Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Item Semantic Edit Policy Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Item Semantic Edit Policy Class Name</em>' attribute.
	 * @see #setItemSemanticEditPolicyClassName(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenCommonBase_ItemSemanticEditPolicyClassName()
	 * @model
	 * @generated
	 */
	String getItemSemanticEditPolicyClassName();

	public static final String ITEM_SEMANTIC_EDIT_POLICY_SUFFIX = "ItemSemanticEditPolicy"; //$NON-NLS-1$

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase#getItemSemanticEditPolicyClassName <em>Item Semantic Edit Policy Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Item Semantic Edit Policy Class Name</em>' attribute.
	 * @see #getItemSemanticEditPolicyClassName()
	 * @generated
	 */
	void setItemSemanticEditPolicyClassName(String value);

	/**
	 * Returns the value of the '<em><b>Notation View Factory Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Notation View Factory Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Notation View Factory Class Name</em>' attribute.
	 * @see #setNotationViewFactoryClassName(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenCommonBase_NotationViewFactoryClassName()
	 * @model annotation="http://www.eclipse.org/gmf/2006/deprecated documentation='ViewFactories are no longer generated'"
	 * @generated
	 */
	String getNotationViewFactoryClassName();

	public static final String NOTATION_VIEW_FACTORY_SUFFIX = "ViewFactory"; //$NON-NLS-1$

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase#getNotationViewFactoryClassName <em>Notation View Factory Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Notation View Factory Class Name</em>' attribute.
	 * @see #getNotationViewFactoryClassName()
	 * @generated
	 */
	void setNotationViewFactoryClassName(String value);

	/**
	 * Returns the value of the '<em><b>Viewmap</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Viewmap</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Viewmap</em>' containment reference.
	 * @see #setViewmap(Viewmap)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenCommonBase_Viewmap()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Viewmap getViewmap();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase#getViewmap <em>Viewmap</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Viewmap</em>' containment reference.
	 * @see #getViewmap()
	 * @generated
	 */
	void setViewmap(Viewmap value);

	/**
	 * Returns the value of the '<em><b>Styles</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.codegen.ecore.genmodel.GenClass}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Styles</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Styles</em>' reference list.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenCommonBase_Styles()
	 * @model
	 * @generated
	 */
	EList<GenClass> getStyles();

	/**
	 * Returns the value of the '<em><b>Behaviour</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.gmf.codegen.gmfgen.Behaviour}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.Behaviour#getSubject <em>Subject</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Behaviour</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Behaviour</em>' containment reference list.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenCommonBase_Behaviour()
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.Behaviour#getSubject
	 * @model opposite="subject" containment="true"
	 * @generated
	 */
	EList<Behaviour> getBehaviour();

	/**
	 * Returns the value of the '<em><b>Sans Domain</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sans Domain</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Indicates this element has no associated domain model. IOW, pure design element.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Sans Domain</em>' attribute.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenCommonBase_SansDomain()
	 * @model transient="true" changeable="false" volatile="true" derived="true"
	 * @generated
	 */
	boolean isSansDomain();

	/**
	 * Returns the value of the '<em><b>Super Edit Part</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Bug 569174 : from ExtendedGenView
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Super Edit Part</em>' attribute.
	 * @see #setSuperEditPart(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenCommonBase_SuperEditPart()
	 * @model
	 * @generated
	 */
	String getSuperEditPart();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase#getSuperEditPart <em>Super Edit Part</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Super Edit Part</em>' attribute.
	 * @see #getSuperEditPart()
	 * @generated
	 */
	void setSuperEditPart(String value);

	/**
	 * Returns the value of the '<em><b>Visual ID Override</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Bug 569174 : from VisualIDOverride
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Visual ID Override</em>' attribute.
	 * @see #setVisualIDOverride(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenCommonBase_VisualIDOverride()
	 * @model unique="false" ordered="false"
	 * @generated
	 */
	String getVisualIDOverride();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase#getVisualIDOverride <em>Visual ID Override</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Visual ID Override</em>' attribute.
	 * @see #getVisualIDOverride()
	 * @generated
	 */
	void setVisualIDOverride(String value);

	/**
	 * Returns the value of the '<em><b>Using Delete Service</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Bug 569174 : from EditPartUsingDeleteService
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Using Delete Service</em>' attribute.
	 * @see #setUsingDeleteService(boolean)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenCommonBase_UsingDeleteService()
	 * @model
	 * @generated
	 */
	boolean isUsingDeleteService();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase#isUsingDeleteService <em>Using Delete Service</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Using Delete Service</em>' attribute.
	 * @see #isUsingDeleteService()
	 * @generated
	 */
	void setUsingDeleteService(boolean value);

	/**
	 * Returns the value of the '<em><b>Using Reorient Service</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Bug 569174 : from EditPartUsingReorientService
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Using Reorient Service</em>' attribute.
	 * @see #setUsingReorientService(boolean)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenCommonBase_UsingReorientService()
	 * @model
	 * @generated
	 */
	boolean isUsingReorientService();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase#isUsingReorientService <em>Using Reorient Service</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Using Reorient Service</em>' attribute.
	 * @see #isUsingReorientService()
	 * @generated
	 */
	void setUsingReorientService(boolean value);

	/**
	 * Filter list of behavior to instances of particular kind. Note, behaviour returned is not 
	 * necessarily owned (think contained) by this element, as this method unwraps {@link SharedBehaviour}   
	 * @param behaviourClass should be Behaviour.class.isAssignableFrom(behaviourClass)
	 */
	<T extends Behaviour> List<T> getBehaviour(Class<T> behaviourClass);

	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class Name Prefix</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getClassNamePrefix();

	public static final String DEFAULT_CLASS_NAME_PREFIX = "Unknown"; //$NON-NLS-1$

	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class Name Suffux</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 *        annotation="http://www.eclipse.org/gmf/2006/deprecated documentation='No longer in use'"
	 * @generated
	 */
	String getClassNameSuffux();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This unique string identifier could be used to construct unique identifiers in generated java code
	 * <!-- end-model-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getUniqueIdentifier();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	ViewmapLayoutType getLayoutType();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getEditPartQualifiedClassName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getItemSemanticEditPolicyQualifiedClassName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 *        annotation="http://www.eclipse.org/gmf/2006/deprecated documentation='ViewFactories are no longer generated'"
	 * @generated
	 */
	String getNotationViewFactoryQualifiedClassName();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	GenDiagram getDiagram();

} // GenCommonBase
