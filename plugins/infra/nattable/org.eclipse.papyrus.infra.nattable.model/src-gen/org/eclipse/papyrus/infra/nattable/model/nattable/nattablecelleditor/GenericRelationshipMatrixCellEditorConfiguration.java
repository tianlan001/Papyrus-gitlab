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

import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanEObjectExpression;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.IWrapper;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Generic Relationship Matrix Cell Editor Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Concret implementation of IMatrixRelationshipCellEditorConfiguration
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration#getDirection <em>Direction</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration#getCellContentsFilter <em>Cell Contents Filter</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration#getEditedElement <em>Edited Element</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration#getRelationshipOwnerStrategy <em>Relationship Owner Strategy</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration#getRelationshipOwner <em>Relationship Owner</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration#getRelationshipOwnerFeature <em>Relationship Owner Feature</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.NattablecelleditorPackage#getGenericRelationshipMatrixCellEditorConfiguration()
 * @model
 * @generated
 */
public interface GenericRelationshipMatrixCellEditorConfiguration extends IMatrixCellEditorConfiguration {
	/**
	 * Returns the value of the '<em><b>Direction</b></em>' attribute.
	 * The default value is <code>"FROM_ROW_TO_COLUMN"</code>.
	 * The literals are from the enumeration {@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.MatrixRelationShipDirection}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Direction</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Direction</em>' attribute.
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.MatrixRelationShipDirection
	 * @see #setDirection(MatrixRelationShipDirection)
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.NattablecelleditorPackage#getGenericRelationshipMatrixCellEditorConfiguration_Direction()
	 * @model default="FROM_ROW_TO_COLUMN"
	 * @generated
	 */
	MatrixRelationShipDirection getDirection();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration#getDirection <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Direction</em>' attribute.
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.MatrixRelationShipDirection
	 * @see #getDirection()
	 * @generated
	 */
	void setDirection(MatrixRelationShipDirection value);

	/**
	 * Returns the value of the '<em><b>Cell Contents Filter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This field allows to the user to filter the contents of the cells.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Cell Contents Filter</em>' containment reference.
	 * @see #setCellContentsFilter(IBooleanEObjectExpression)
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.NattablecelleditorPackage#getGenericRelationshipMatrixCellEditorConfiguration_CellContentsFilter()
	 * @model containment="true"
	 * @generated
	 */
	IBooleanEObjectExpression getCellContentsFilter();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration#getCellContentsFilter <em>Cell Contents Filter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cell Contents Filter</em>' containment reference.
	 * @see #getCellContentsFilter()
	 * @generated
	 */
	void setCellContentsFilter(IBooleanEObjectExpression value);

	/**
	 * Returns the value of the '<em><b>Edited Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This field allows to define the kind of element to edit in the cell.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Edited Element</em>' reference.
	 * @see #setEditedElement(ElementTypeConfiguration)
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.NattablecelleditorPackage#getGenericRelationshipMatrixCellEditorConfiguration_EditedElement()
	 * @model
	 * @generated
	 */
	ElementTypeConfiguration getEditedElement();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration#getEditedElement <em>Edited Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Edited Element</em>' reference.
	 * @see #getEditedElement()
	 * @generated
	 */
	void setEditedElement(ElementTypeConfiguration value);

	/**
	 * Returns the value of the '<em><b>Relationship Owner Strategy</b></em>' attribute.
	 * The default value is <code>"DEFAULT"</code>.
	 * The literals are from the enumeration {@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.MatrixRelationShipOwnerStrategy}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This field is used to define the owner of relationships created editing the matrix.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Relationship Owner Strategy</em>' attribute.
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.MatrixRelationShipOwnerStrategy
	 * @see #setRelationshipOwnerStrategy(MatrixRelationShipOwnerStrategy)
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.NattablecelleditorPackage#getGenericRelationshipMatrixCellEditorConfiguration_RelationshipOwnerStrategy()
	 * @model default="DEFAULT" required="true"
	 * @generated
	 */
	MatrixRelationShipOwnerStrategy getRelationshipOwnerStrategy();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration#getRelationshipOwnerStrategy <em>Relationship Owner Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Relationship Owner Strategy</em>' attribute.
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.MatrixRelationShipOwnerStrategy
	 * @see #getRelationshipOwnerStrategy()
	 * @generated
	 */
	void setRelationshipOwnerStrategy(MatrixRelationShipOwnerStrategy value);

	/**
	 * Returns the value of the '<em><b>Relationship Owner</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This field is used only when the relationshipOwnerStrategy is set to Other.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Relationship Owner</em>' containment reference.
	 * @see #setRelationshipOwner(IWrapper)
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.NattablecelleditorPackage#getGenericRelationshipMatrixCellEditorConfiguration_RelationshipOwner()
	 * @model containment="true"
	 * @generated
	 */
	IWrapper getRelationshipOwner();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration#getRelationshipOwner <em>Relationship Owner</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Relationship Owner</em>' containment reference.
	 * @see #getRelationshipOwner()
	 * @generated
	 */
	void setRelationshipOwner(IWrapper value);

	/**
	 * Returns the value of the '<em><b>Relationship Owner Feature</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This field is used only when the relationshipOwnerStrategy is set to Other.
	 * It will be used, if required, to define the feature in the relationshipOwner which will owns the created relationship
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Relationship Owner Feature</em>' containment reference.
	 * @see #setRelationshipOwnerFeature(IAxis)
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.NattablecelleditorPackage#getGenericRelationshipMatrixCellEditorConfiguration_RelationshipOwnerFeature()
	 * @model containment="true"
	 * @generated
	 */
	IAxis getRelationshipOwnerFeature();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.GenericRelationshipMatrixCellEditorConfiguration#getRelationshipOwnerFeature <em>Relationship Owner Feature</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Relationship Owner Feature</em>' containment reference.
	 * @see #getRelationshipOwnerFeature()
	 * @generated
	 */
	void setRelationshipOwnerFeature(IAxis value);

} // GenericRelationshipMatrixCellEditorConfiguration
