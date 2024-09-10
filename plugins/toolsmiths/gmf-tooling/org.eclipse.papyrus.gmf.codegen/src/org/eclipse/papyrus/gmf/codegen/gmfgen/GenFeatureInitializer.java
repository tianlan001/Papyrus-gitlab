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
 * $Id: GenFeatureInitializer.java,v 1.6 2008/04/18 14:43:20 atikhomirov Exp $
 */
package org.eclipse.papyrus.gmf.codegen.gmfgen;

import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenFeatureSeqInitializer;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Gen Feature Initializer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenFeatureInitializer#getFeature <em>Feature</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenFeatureInitializer#getFeatureSeqInitializer <em>Feature Seq Initializer</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenFeatureInitializer()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface GenFeatureInitializer extends EObject {
	/**
	 * Returns the value of the '<em><b>Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The feature for which is to be initialized by this initializer
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Feature</em>' reference.
	 * @see #setFeature(GenFeature)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenFeatureInitializer_Feature()
	 * @model required="true"
	 *        annotation="http://www.eclipse.org/gmf/2005/constraints ocl='feature &lt;&gt; null implies not featureSeqInitializer.initializers-&gt;exists(i| i &lt;&gt; self and i.feature = self.feature)' description='The feature is already initialized by another \'GenFeatureInitializer\' in the sequence'"
	 *        annotation="http://www.eclipse.org/gmf/2005/constraints ocl='feature &lt;&gt; null implies feature.ecoreFeature.eContainingClass.isSuperTypeOf(featureSeqInitializer.elementClass.ecoreClass)' description='The \'feature\' of \'GenFeatureInitializer\' must be available in \'Meta Class\' of the initialized element'"
	 * @generated
	 */
	GenFeature getFeature();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenFeatureInitializer#getFeature <em>Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feature</em>' reference.
	 * @see #getFeature()
	 * @generated
	 */
	void setFeature(GenFeature value);

	/**
	 * Returns the value of the '<em><b>Feature Seq Initializer</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenFeatureSeqInitializer#getInitializers <em>Initializers</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Feature Seq Initializer</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature Seq Initializer</em>' container reference.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenFeatureInitializer_FeatureSeqInitializer()
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GenFeatureSeqInitializer#getInitializers
	 * @model opposite="initializers" resolveProxies="false" required="true" transient="false" changeable="false"
	 * @generated
	 */
	GenFeatureSeqInitializer getFeatureSeqInitializer();

} // GenFeatureInitializer
