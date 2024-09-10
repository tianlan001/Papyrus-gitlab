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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type Link Model Facet</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Model facet of an EClass that associates two EClasses
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.TypeLinkModelFacet#getSourceMetaFeature <em>Source Meta Feature</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.TypeLinkModelFacet#getTargetMetaFeature <em>Target Meta Feature</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getTypeLinkModelFacet()
 * @model annotation="http://www.eclipse.org/gmf/2005/constraints ocl='not containmentMetaFeature.oclIsUndefined()' description='Link \'Containment Meta Feature\' must be specified'"
 * @generated
 */
public interface TypeLinkModelFacet extends TypeModelFacet, LinkModelFacet {
	/**
	 * Returns the value of the '<em><b>Source Meta Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Meta Feature</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If source feature is not specified then link container is a link source
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Source Meta Feature</em>' reference.
	 * @see #setSourceMetaFeature(GenFeature)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getTypeLinkModelFacet_SourceMetaFeature()
	 * @model annotation="http://www.eclipse.org/gmf/2005/constraints ocl='not sourceMetaFeature.oclIsUndefined() implies sourceMetaFeature.genClass.ecoreClass.isSuperTypeOf(metaClass.ecoreClass)' description='Link \'Source Meta Feature\' must be owned by link \'Meta Class\' or its super-class'"
	 * @generated
	 */
	GenFeature getSourceMetaFeature();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.TypeLinkModelFacet#getSourceMetaFeature <em>Source Meta Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Meta Feature</em>' reference.
	 * @see #getSourceMetaFeature()
	 * @generated
	 */
	void setSourceMetaFeature(GenFeature value);

	/**
	 * Returns the value of the '<em><b>Target Meta Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Meta Feature</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Meta Feature</em>' reference.
	 * @see #setTargetMetaFeature(GenFeature)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getTypeLinkModelFacet_TargetMetaFeature()
	 * @model required="true"
	 *        annotation="http://www.eclipse.org/gmf/2005/constraints ocl='not targetMetaFeature.oclIsUndefined() implies targetMetaFeature.genClass.ecoreClass.isSuperTypeOf(metaClass.ecoreClass)' description='Link \'Target Meta Feature\' must be owned by link \'Meta Class\' or its super-class'"
	 * @generated
	 */
	GenFeature getTargetMetaFeature();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.TypeLinkModelFacet#getTargetMetaFeature <em>Target Meta Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Meta Feature</em>' reference.
	 * @see #getTargetMetaFeature()
	 * @generated
	 */
	void setTargetMetaFeature(GenFeature value);

} // TypeLinkModelFacet
