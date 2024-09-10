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
import org.eclipse.papyrus.gmf.codegen.gmfgen.LinkModelFacet;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature Link Model Facet</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Model facet of a feature-based link
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLinkModelFacet#getMetaFeature <em>Meta Feature</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getFeatureLinkModelFacet()
 * @model
 * @generated
 */
public interface FeatureLinkModelFacet extends LinkModelFacet {
	/**
	 * Returns the value of the '<em><b>Meta Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Meta Feature</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Meta Feature</em>' reference.
	 * @see #setMetaFeature(GenFeature)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getFeatureLinkModelFacet_MetaFeature()
	 * @model required="true"
	 *        annotation="http://www.eclipse.org/gmf/2005/constraints ocl='metaFeature.ecoreFeature.unique' description='All references are unique in EMF due to the current code generation'"
	 * @generated
	 */
	GenFeature getMetaFeature();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLinkModelFacet#getMetaFeature <em>Meta Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Meta Feature</em>' reference.
	 * @see #getMetaFeature()
	 * @generated
	 */
	void setMetaFeature(GenFeature value);

} // FeatureLinkModelFacet
