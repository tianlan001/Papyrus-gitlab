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

import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLabel;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink;
import org.eclipse.papyrus.gmf.codegen.gmfgen.LinkLabelAlignment;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Gen Link Label</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Label attached to link
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenLinkLabel#getLink <em>Link</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenLinkLabel#getAlignment <em>Alignment</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenLinkLabel#getLabelVisibilityPreference <em>Label Visibility Preference</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenLinkLabel()
 * @model annotation="http://www.eclipse.org/gmf/2005/constraints ocl='modelFacet.oclIsTypeOf(FeatureLabelModelFacet)=true implies link.modelFacet.oclIsTypeOf(TypeLinkModelFacet)' description='Feature based link labels can only be used on link with class (TypeLinkModelFacet)'"
 *        annotation="http://www.eclipse.org/gmf/2005/constraints ocl='let tl: TypeLinkModelFacet = link.modelFacet.oclAsType(TypeLinkModelFacet) in not tl.oclIsUndefined() implies self.getMetaFeatures()-&gt;forAll(f|f.ecoreFeature.eContainingClass.isSuperTypeOf(tl.metaClass.ecoreClass))' description='Link label meta features must be owned by the node \'Meta Class\' or its super-classes'"
 * @generated
 */
public interface GenLinkLabel extends GenLabel {
	/**
	 * Returns the value of the '<em><b>Link</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink#getLabels <em>Labels</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Link</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Link</em>' container reference.
	 * @see #setLink(GenLink)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenLinkLabel_Link()
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink#getLabels
	 * @model opposite="labels" required="true" transient="false"
	 * @generated
	 */
	GenLink getLink();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenLinkLabel#getLink <em>Link</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Link</em>' container reference.
	 * @see #getLink()
	 * @generated
	 */
	void setLink(GenLink value);

	/**
	 * Returns the value of the '<em><b>Alignment</b></em>' attribute.
	 * The default value is <code>"MIDDLE"</code>.
	 * The literals are from the enumeration {@link org.eclipse.papyrus.gmf.codegen.gmfgen.LinkLabelAlignment}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Alignment</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alignment</em>' attribute.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.LinkLabelAlignment
	 * @see #setAlignment(LinkLabelAlignment)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenLinkLabel_Alignment()
	 * @model default="MIDDLE"
	 * @generated
	 */
	LinkLabelAlignment getAlignment();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenLinkLabel#getAlignment <em>Alignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Alignment</em>' attribute.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.LinkLabelAlignment
	 * @see #getAlignment()
	 * @generated
	 */
	void setAlignment(LinkLabelAlignment value);

	/**
	 * Returns the value of the '<em><b>Label Visibility Preference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Bug 569174 : from LabelVisibilityPreference
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Label Visibility Preference</em>' containment reference.
	 * @see #setLabelVisibilityPreference(GenFloatingLabel)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenLinkLabel_LabelVisibilityPreference()
	 * @model containment="true"
	 * @generated
	 */
	GenFloatingLabel getLabelVisibilityPreference();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenLinkLabel#getLabelVisibilityPreference <em>Label Visibility Preference</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label Visibility Preference</em>' containment reference.
	 * @see #getLabelVisibilityPreference()
	 * @generated
	 */
	void setLabelVisibilityPreference(GenFloatingLabel value);

} // GenLinkLabel
