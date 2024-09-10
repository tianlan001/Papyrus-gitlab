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
package org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.NattablecelleditorFactory
 * @model kind="package"
 * @generated
 */
public interface NattablecelleditorPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "nattablecelleditor"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/nattable/model/table/nattablecelleditor"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "nattablecelleditor"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	NattablecelleditorPackage eINSTANCE = org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.impl.NattablecelleditorPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.ICellEditorConfiguration <em>ICell Editor Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.ICellEditorConfiguration
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.impl.NattablecelleditorPackageImpl#getICellEditorConfiguration()
	 * @generated
	 */
	int ICELL_EDITOR_CONFIGURATION = 0;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICELL_EDITOR_CONFIGURATION__EANNOTATIONS = NattablestylePackage.STYLED_ELEMENT__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Styles</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICELL_EDITOR_CONFIGURATION__STYLES = NattablestylePackage.STYLED_ELEMENT__STYLES;

	/**
	 * The feature id for the '<em><b>Cell Editor Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICELL_EDITOR_CONFIGURATION__CELL_EDITOR_ID = NattablestylePackage.STYLED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>ICell Editor Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICELL_EDITOR_CONFIGURATION_FEATURE_COUNT = NattablestylePackage.STYLED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get EAnnotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICELL_EDITOR_CONFIGURATION___GET_EANNOTATION__STRING = NattablestylePackage.STYLED_ELEMENT___GET_EANNOTATION__STRING;

	/**
	 * The operation id for the '<em>Get Named Style</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICELL_EDITOR_CONFIGURATION___GET_NAMED_STYLE__ECLASS_STRING = NattablestylePackage.STYLED_ELEMENT___GET_NAMED_STYLE__ECLASS_STRING;

	/**
	 * The operation id for the '<em>Get Style</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICELL_EDITOR_CONFIGURATION___GET_STYLE__ECLASS = NattablestylePackage.STYLED_ELEMENT___GET_STYLE__ECLASS;

	/**
	 * The operation id for the '<em>Create Style</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICELL_EDITOR_CONFIGURATION___CREATE_STYLE__ECLASS = NattablestylePackage.STYLED_ELEMENT___CREATE_STYLE__ECLASS;

	/**
	 * The number of operations of the '<em>ICell Editor Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICELL_EDITOR_CONFIGURATION_OPERATION_COUNT = NattablestylePackage.STYLED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.IMatrixCellEditorConfiguration <em>IMatrix Cell Editor Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.IMatrixCellEditorConfiguration
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.impl.NattablecelleditorPackageImpl#getIMatrixCellEditorConfiguration()
	 * @generated
	 */
	int IMATRIX_CELL_EDITOR_CONFIGURATION = 2;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMATRIX_CELL_EDITOR_CONFIGURATION__EANNOTATIONS = ICELL_EDITOR_CONFIGURATION__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Styles</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMATRIX_CELL_EDITOR_CONFIGURATION__STYLES = ICELL_EDITOR_CONFIGURATION__STYLES;

	/**
	 * The feature id for the '<em><b>Cell Editor Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMATRIX_CELL_EDITOR_CONFIGURATION__CELL_EDITOR_ID = ICELL_EDITOR_CONFIGURATION__CELL_EDITOR_ID;

	/**
	 * The number of structural features of the '<em>IMatrix Cell Editor Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMATRIX_CELL_EDITOR_CONFIGURATION_FEATURE_COUNT = ICELL_EDITOR_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get EAnnotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMATRIX_CELL_EDITOR_CONFIGURATION___GET_EANNOTATION__STRING = ICELL_EDITOR_CONFIGURATION___GET_EANNOTATION__STRING;

	/**
	 * The operation id for the '<em>Get Named Style</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMATRIX_CELL_EDITOR_CONFIGURATION___GET_NAMED_STYLE__ECLASS_STRING = ICELL_EDITOR_CONFIGURATION___GET_NAMED_STYLE__ECLASS_STRING;

	/**
	 * The operation id for the '<em>Get Style</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMATRIX_CELL_EDITOR_CONFIGURATION___GET_STYLE__ECLASS = ICELL_EDITOR_CONFIGURATION___GET_STYLE__ECLASS;

	/**
	 * The operation id for the '<em>Create Style</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMATRIX_CELL_EDITOR_CONFIGURATION___CREATE_STYLE__ECLASS = ICELL_EDITOR_CONFIGURATION___CREATE_STYLE__ECLASS;

	/**
	 * The number of operations of the '<em>IMatrix Cell Editor Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMATRIX_CELL_EDITOR_CONFIGURATION_OPERATION_COUNT = ICELL_EDITOR_CONFIGURATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.impl.GenericRelationshipMatrixCellEditorConfigurationImpl <em>Generic Relationship Matrix Cell Editor Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.impl.GenericRelationshipMatrixCellEditorConfigurationImpl
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.impl.NattablecelleditorPackageImpl#getGenericRelationshipMatrixCellEditorConfiguration()
	 * @generated
	 */
	int GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION = 1;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__EANNOTATIONS = IMATRIX_CELL_EDITOR_CONFIGURATION__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Styles</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__STYLES = IMATRIX_CELL_EDITOR_CONFIGURATION__STYLES;

	/**
	 * The feature id for the '<em><b>Cell Editor Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__CELL_EDITOR_ID = IMATRIX_CELL_EDITOR_CONFIGURATION__CELL_EDITOR_ID;

	/**
	 * The feature id for the '<em><b>Direction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__DIRECTION = IMATRIX_CELL_EDITOR_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Cell Contents Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__CELL_CONTENTS_FILTER = IMATRIX_CELL_EDITOR_CONFIGURATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Edited Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__EDITED_ELEMENT = IMATRIX_CELL_EDITOR_CONFIGURATION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Relationship Owner Strategy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER_STRATEGY = IMATRIX_CELL_EDITOR_CONFIGURATION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Relationship Owner</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER = IMATRIX_CELL_EDITOR_CONFIGURATION_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Relationship Owner Feature</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER_FEATURE = IMATRIX_CELL_EDITOR_CONFIGURATION_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Generic Relationship Matrix Cell Editor Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION_FEATURE_COUNT = IMATRIX_CELL_EDITOR_CONFIGURATION_FEATURE_COUNT + 6;

	/**
	 * The operation id for the '<em>Get EAnnotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION___GET_EANNOTATION__STRING = IMATRIX_CELL_EDITOR_CONFIGURATION___GET_EANNOTATION__STRING;

	/**
	 * The operation id for the '<em>Get Named Style</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION___GET_NAMED_STYLE__ECLASS_STRING = IMATRIX_CELL_EDITOR_CONFIGURATION___GET_NAMED_STYLE__ECLASS_STRING;

	/**
	 * The operation id for the '<em>Get Style</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION___GET_STYLE__ECLASS = IMATRIX_CELL_EDITOR_CONFIGURATION___GET_STYLE__ECLASS;

	/**
	 * The operation id for the '<em>Create Style</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION___CREATE_STYLE__ECLASS = IMATRIX_CELL_EDITOR_CONFIGURATION___CREATE_STYLE__ECLASS;

	/**
	 * The number of operations of the '<em>Generic Relationship Matrix Cell Editor Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION_OPERATION_COUNT = IMATRIX_CELL_EDITOR_CONFIGURATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.MatrixRelationShipDirection <em>Matrix Relation Ship Direction</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.MatrixRelationShipDirection
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.impl.NattablecelleditorPackageImpl#getMatrixRelationShipDirection()
	 * @generated
	 */
	int MATRIX_RELATION_SHIP_DIRECTION = 3;


	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.MatrixRelationShipOwnerStrategy <em>Matrix Relation Ship Owner Strategy</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.MatrixRelationShipOwnerStrategy
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.impl.NattablecelleditorPackageImpl#getMatrixRelationShipOwnerStrategy()
	 * @generated
	 */
	int MATRIX_RELATION_SHIP_OWNER_STRATEGY = 4;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.ICellEditorConfiguration <em>ICell Editor Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ICell Editor Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.ICellEditorConfiguration
	 * @generated
	 */
	EClass getICellEditorConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.ICellEditorConfiguration#getCellEditorId <em>Cell Editor Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cell Editor Id</em>'.
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.ICellEditorConfiguration#getCellEditorId()
	 * @see #getICellEditorConfiguration()
	 * @generated
	 */
	EAttribute getICellEditorConfiguration_CellEditorId();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration <em>Generic Relationship Matrix Cell Editor Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Generic Relationship Matrix Cell Editor Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration
	 * @generated
	 */
	EClass getGenericRelationshipMatrixCellEditorConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration#getDirection <em>Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Direction</em>'.
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration#getDirection()
	 * @see #getGenericRelationshipMatrixCellEditorConfiguration()
	 * @generated
	 */
	EAttribute getGenericRelationshipMatrixCellEditorConfiguration_Direction();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration#getCellContentsFilter <em>Cell Contents Filter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cell Contents Filter</em>'.
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration#getCellContentsFilter()
	 * @see #getGenericRelationshipMatrixCellEditorConfiguration()
	 * @generated
	 */
	EReference getGenericRelationshipMatrixCellEditorConfiguration_CellContentsFilter();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration#getEditedElement <em>Edited Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Edited Element</em>'.
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration#getEditedElement()
	 * @see #getGenericRelationshipMatrixCellEditorConfiguration()
	 * @generated
	 */
	EReference getGenericRelationshipMatrixCellEditorConfiguration_EditedElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration#getRelationshipOwnerStrategy <em>Relationship Owner Strategy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Relationship Owner Strategy</em>'.
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration#getRelationshipOwnerStrategy()
	 * @see #getGenericRelationshipMatrixCellEditorConfiguration()
	 * @generated
	 */
	EAttribute getGenericRelationshipMatrixCellEditorConfiguration_RelationshipOwnerStrategy();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration#getRelationshipOwner <em>Relationship Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Relationship Owner</em>'.
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration#getRelationshipOwner()
	 * @see #getGenericRelationshipMatrixCellEditorConfiguration()
	 * @generated
	 */
	EReference getGenericRelationshipMatrixCellEditorConfiguration_RelationshipOwner();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration#getRelationshipOwnerFeature <em>Relationship Owner Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Relationship Owner Feature</em>'.
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration#getRelationshipOwnerFeature()
	 * @see #getGenericRelationshipMatrixCellEditorConfiguration()
	 * @generated
	 */
	EReference getGenericRelationshipMatrixCellEditorConfiguration_RelationshipOwnerFeature();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.IMatrixCellEditorConfiguration <em>IMatrix Cell Editor Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IMatrix Cell Editor Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.IMatrixCellEditorConfiguration
	 * @generated
	 */
	EClass getIMatrixCellEditorConfiguration();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.MatrixRelationShipDirection <em>Matrix Relation Ship Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Matrix Relation Ship Direction</em>'.
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.MatrixRelationShipDirection
	 * @generated
	 */
	EEnum getMatrixRelationShipDirection();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.MatrixRelationShipOwnerStrategy <em>Matrix Relation Ship Owner Strategy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Matrix Relation Ship Owner Strategy</em>'.
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.MatrixRelationShipOwnerStrategy
	 * @generated
	 */
	EEnum getMatrixRelationShipOwnerStrategy();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	NattablecelleditorFactory getNattablecelleditorFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.ICellEditorConfiguration <em>ICell Editor Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.ICellEditorConfiguration
		 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.impl.NattablecelleditorPackageImpl#getICellEditorConfiguration()
		 * @generated
		 */
		EClass ICELL_EDITOR_CONFIGURATION = eINSTANCE.getICellEditorConfiguration();

		/**
		 * The meta object literal for the '<em><b>Cell Editor Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ICELL_EDITOR_CONFIGURATION__CELL_EDITOR_ID = eINSTANCE.getICellEditorConfiguration_CellEditorId();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.impl.GenericRelationshipMatrixCellEditorConfigurationImpl <em>Generic Relationship Matrix Cell Editor Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.impl.GenericRelationshipMatrixCellEditorConfigurationImpl
		 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.impl.NattablecelleditorPackageImpl#getGenericRelationshipMatrixCellEditorConfiguration()
		 * @generated
		 */
		EClass GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION = eINSTANCE.getGenericRelationshipMatrixCellEditorConfiguration();

		/**
		 * The meta object literal for the '<em><b>Direction</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__DIRECTION = eINSTANCE.getGenericRelationshipMatrixCellEditorConfiguration_Direction();

		/**
		 * The meta object literal for the '<em><b>Cell Contents Filter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__CELL_CONTENTS_FILTER = eINSTANCE.getGenericRelationshipMatrixCellEditorConfiguration_CellContentsFilter();

		/**
		 * The meta object literal for the '<em><b>Edited Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__EDITED_ELEMENT = eINSTANCE.getGenericRelationshipMatrixCellEditorConfiguration_EditedElement();

		/**
		 * The meta object literal for the '<em><b>Relationship Owner Strategy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER_STRATEGY = eINSTANCE.getGenericRelationshipMatrixCellEditorConfiguration_RelationshipOwnerStrategy();

		/**
		 * The meta object literal for the '<em><b>Relationship Owner</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER = eINSTANCE.getGenericRelationshipMatrixCellEditorConfiguration_RelationshipOwner();

		/**
		 * The meta object literal for the '<em><b>Relationship Owner Feature</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GENERIC_RELATIONSHIP_MATRIX_CELL_EDITOR_CONFIGURATION__RELATIONSHIP_OWNER_FEATURE = eINSTANCE.getGenericRelationshipMatrixCellEditorConfiguration_RelationshipOwnerFeature();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.IMatrixCellEditorConfiguration <em>IMatrix Cell Editor Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.IMatrixCellEditorConfiguration
		 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.impl.NattablecelleditorPackageImpl#getIMatrixCellEditorConfiguration()
		 * @generated
		 */
		EClass IMATRIX_CELL_EDITOR_CONFIGURATION = eINSTANCE.getIMatrixCellEditorConfiguration();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.MatrixRelationShipDirection <em>Matrix Relation Ship Direction</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.MatrixRelationShipDirection
		 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.impl.NattablecelleditorPackageImpl#getMatrixRelationShipDirection()
		 * @generated
		 */
		EEnum MATRIX_RELATION_SHIP_DIRECTION = eINSTANCE.getMatrixRelationShipDirection();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.MatrixRelationShipOwnerStrategy <em>Matrix Relation Ship Owner Strategy</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.MatrixRelationShipOwnerStrategy
		 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.impl.NattablecelleditorPackageImpl#getMatrixRelationShipOwnerStrategy()
		 * @generated
		 */
		EEnum MATRIX_RELATION_SHIP_OWNER_STRATEGY = eINSTANCE.getMatrixRelationShipOwnerStrategy();

	}

} //NattablecelleditorPackage
