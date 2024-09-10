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


import org.eclipse.emf.codegen.ecore.genmodel.GenDataType;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditable;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenMetricRule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Gen Audited Metric Target</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * Target metric which can be evaluated by audit rule. The target context here is the metric rule resulting type classifier
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditedMetricTarget#getMetric <em>Metric</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditedMetricTarget#getMetricValueContext <em>Metric Value Context</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenAuditedMetricTarget()
 * @model
 * @generated
 */
public interface GenAuditedMetricTarget extends GenAuditable {
	/**
	 * Returns the value of the '<em><b>Metric</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Metric wich can be involved in audit
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Metric</em>' reference.
	 * @see #setMetric(GenMetricRule)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenAuditedMetricTarget_Metric()
	 * @model required="true"
	 * @generated
	 */
	GenMetricRule getMetric();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditedMetricTarget#getMetric <em>Metric</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Metric</em>' reference.
	 * @see #getMetric()
	 * @generated
	 */
	void setMetric(GenMetricRule value);

	/**
	 * Returns the value of the '<em><b>Metric Value Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metric Value Context</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metric Value Context</em>' reference.
	 * @see #setMetricValueContext(GenDataType)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenAuditedMetricTarget_MetricValueContext()
	 * @model required="true"
	 * @generated
	 */
	GenDataType getMetricValueContext();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditedMetricTarget#getMetricValueContext <em>Metric Value Context</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Metric Value Context</em>' reference.
	 * @see #getMetricValueContext()
	 * @generated
	 */
	void setMetricValueContext(GenDataType value);

} // GenAuditedMetricTarget
