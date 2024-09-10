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
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenElementInitializer;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenFeatureInitializer;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenReferenceNewElementSpec;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Gen Feature Seq Initializer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Feature sequence initializer
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenFeatureSeqInitializer#getInitializers <em>Initializers</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenFeatureSeqInitializer#getElementClass <em>Element Class</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenFeatureSeqInitializer#getCreatingInitializer <em>Creating Initializer</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenFeatureSeqInitializer()
 * @model
 * @generated
 */
public interface GenFeatureSeqInitializer extends GenElementInitializer {
	/**
	 * Returns the value of the '<em><b>Initializers</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenFeatureInitializer}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenFeatureInitializer#getFeatureSeqInitializer <em>Feature Seq Initializer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Value specifications as initializers for individual features which should be initialized in the order given by this list
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Initializers</em>' containment reference list.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenFeatureSeqInitializer_Initializers()
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GenFeatureInitializer#getFeatureSeqInitializer
	 * @model opposite="featureSeqInitializer" containment="true" required="true"
	 * @generated
	 */
	EList<GenFeatureInitializer> getInitializers();

	/**
	 * Returns the value of the '<em><b>Element Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element Class</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element Class</em>' reference.
	 * @see #setElementClass(GenClass)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenFeatureSeqInitializer_ElementClass()
	 * @model annotation="http://www.eclipse.org/gmf/2005/constraints ocl='not creatingInitializer.feature.oclIsUndefined() implies creatingInitializer.feature.ecoreFeature.oclAsType(ecore::EReference).eReferenceType.isSuperTypeOf(elementClass.ecoreClass)' description='\'elementClass\' must be the same as or sub-type of the containing \'GenReferenceNewElementSpec\' reference type'"
	 *        annotation="http://www.eclipse.org/gmf/2005/constraints ocl='not creatingInitializer.feature.oclIsUndefined() implies not (elementClass.ecoreClass.interface or elementClass.ecoreClass.abstract)' description='\'elementClass\' must be a concrete EClass which is the same or sub-type of the containing \'GenReferenceNewElementSpec\' reference type'"
	 * @generated
	 */
	GenClass getElementClass();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenFeatureSeqInitializer#getElementClass <em>Element Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Element Class</em>' reference.
	 * @see #getElementClass()
	 * @generated
	 */
	void setElementClass(GenClass value);

	/**
	 * Returns the value of the '<em><b>Creating Initializer</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenReferenceNewElementSpec#getNewElementInitializers <em>New Element Initializers</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Creating Initializer</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Creating Initializer</em>' container reference.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenFeatureSeqInitializer_CreatingInitializer()
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GenReferenceNewElementSpec#getNewElementInitializers
	 * @model opposite="newElementInitializers" resolveProxies="false" transient="false" changeable="false"
	 * @generated
	 */
	GenReferenceNewElementSpec getCreatingInitializer();
} // GenFeatureSeqInitializer
