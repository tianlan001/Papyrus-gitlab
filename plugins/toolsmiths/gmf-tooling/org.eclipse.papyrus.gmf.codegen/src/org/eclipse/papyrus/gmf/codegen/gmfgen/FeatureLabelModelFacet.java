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


import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature Label Model Facet</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Model facet of label based on domain model attribute(s)
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLabelModelFacet#getMetaFeatures <em>Meta Features</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLabelModelFacet#getEditableMetaFeatures <em>Editable Meta Features</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLabelModelFacet#getViewPattern <em>View Pattern</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLabelModelFacet#getEditorPattern <em>Editor Pattern</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLabelModelFacet#getEditPattern <em>Edit Pattern</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLabelModelFacet#getViewMethod <em>View Method</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLabelModelFacet#getEditMethod <em>Edit Method</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getFeatureLabelModelFacet()
 * @model
 * @generated
 */
public interface FeatureLabelModelFacet extends LabelModelFacet {
	/**
	 * Returns the value of the '<em><b>Meta Features</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.codegen.ecore.genmodel.GenFeature}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Meta Features</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Meta Features</em>' reference list.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getFeatureLabelModelFacet_MetaFeatures()
	 * @model required="true"
	 * @generated
	 */
	EList<GenFeature> getMetaFeatures();

	/**
	 * Returns the value of the '<em><b>Editable Meta Features</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.codegen.ecore.genmodel.GenFeature}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Editable Meta Features</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Editable Meta Features</em>' reference list.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getFeatureLabelModelFacet_EditableMetaFeatures()
	 * @model
	 * @generated
	 */
	EList<GenFeature> getEditableMetaFeatures();

	/**
	 * Returns the value of the '<em><b>View Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Pattern to produce label on diagram, depends on view method
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>View Pattern</em>' attribute.
	 * @see #setViewPattern(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getFeatureLabelModelFacet_ViewPattern()
	 * @model
	 * @generated
	 */
	String getViewPattern();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLabelModelFacet#getViewPattern <em>View Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>View Pattern</em>' attribute.
	 * @see #getViewPattern()
	 * @generated
	 */
	void setViewPattern(String value);

	/**
	 * Returns the value of the '<em><b>Editor Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Pattern to produce text for inplace editor, depends on view method; if not specified then viewPattern should be used
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Editor Pattern</em>' attribute.
	 * @see #setEditorPattern(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getFeatureLabelModelFacet_EditorPattern()
	 * @model
	 * @generated
	 */
	String getEditorPattern();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLabelModelFacet#getEditorPattern <em>Editor Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Editor Pattern</em>' attribute.
	 * @see #getEditorPattern()
	 * @generated
	 */
	void setEditorPattern(String value);

	/**
	 * Returns the value of the '<em><b>View Method</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.papyrus.gmf.codegen.gmfgen.LabelTextAccessMethod}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>View Method</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>View Method</em>' attribute.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.LabelTextAccessMethod
	 * @see #setViewMethod(LabelTextAccessMethod)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getFeatureLabelModelFacet_ViewMethod()
	 * @model annotation="http://www.eclipse.org/gmf/2006/deprecated documentation='Replaced with GenParsers and PredefinedParser'"
	 * @generated
	 */
	LabelTextAccessMethod getViewMethod();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLabelModelFacet#getViewMethod <em>View Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>View Method</em>' attribute.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.LabelTextAccessMethod
	 * @see #getViewMethod()
	 * @generated
	 */
	void setViewMethod(LabelTextAccessMethod value);

	/**
	 * Returns the value of the '<em><b>Edit Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Pattern to extract values from input text, depends on edit method; if not specified then viewPattern should be used
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Edit Pattern</em>' attribute.
	 * @see #setEditPattern(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getFeatureLabelModelFacet_EditPattern()
	 * @model
	 * @generated
	 */
	String getEditPattern();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLabelModelFacet#getEditPattern <em>Edit Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Edit Pattern</em>' attribute.
	 * @see #getEditPattern()
	 * @generated
	 */
	void setEditPattern(String value);

	/**
	 * Returns the value of the '<em><b>Edit Method</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.papyrus.gmf.codegen.gmfgen.LabelTextAccessMethod}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edit Method</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Edit Method</em>' attribute.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.LabelTextAccessMethod
	 * @see #setEditMethod(LabelTextAccessMethod)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getFeatureLabelModelFacet_EditMethod()
	 * @model annotation="http://www.eclipse.org/gmf/2006/deprecated documentation='Replaced with GenParsers and PredefinedParser'"
	 * @generated
	 */
	LabelTextAccessMethod getEditMethod();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLabelModelFacet#getEditMethod <em>Edit Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Edit Method</em>' attribute.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.LabelTextAccessMethod
	 * @see #getEditMethod()
	 * @generated
	 */
	void setEditMethod(LabelTextAccessMethod value);

} // FeatureLabelModelFacet
