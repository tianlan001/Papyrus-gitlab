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
package org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration;

import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanEObjectExpression;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablelabelprovider.ILabelProviderConfiguration;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tree Filling Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration#getAxisUsedAsAxisProvider <em>Axis Used As Axis Provider</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration#getPasteConfiguration <em>Paste Configuration</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration#getDepth <em>Depth</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration#getLabelProvider <em>Label Provider</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration#getLabelProviderContext <em>Label Provider Context</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration#getFilterRule <em>Filter Rule</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationPackage#getTreeFillingConfiguration()
 * @model
 * @generated
 */
public interface TreeFillingConfiguration extends IFillingConfiguration {

	/**
	 * Returns the value of the '<em><b>Axis Used As Axis Provider</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Axis Used As Axis Provider</em>' containment reference isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Axis Used As Axis Provider</em>' containment reference.
	 * @see #setAxisUsedAsAxisProvider(IAxis)
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationPackage#getTreeFillingConfiguration_AxisUsedAsAxisProvider()
	 * @model containment="true" required="true"
	 * @generated
	 */
	IAxis getAxisUsedAsAxisProvider();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration#getAxisUsedAsAxisProvider <em>Axis Used As Axis Provider</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Axis Used As Axis Provider</em>' containment reference.
	 * @see #getAxisUsedAsAxisProvider()
	 * @generated
	 */
	void setAxisUsedAsAxisProvider(IAxis value);

	/**
	 * Returns the value of the '<em><b>Paste Configuration</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Paste Configuration</em>' reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Paste Configuration</em>' reference.
	 * @see #setPasteConfiguration(PasteEObjectConfiguration)
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationPackage#getTreeFillingConfiguration_PasteConfiguration()
	 * @model
	 * @generated
	 */
	PasteEObjectConfiguration getPasteConfiguration();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration#getPasteConfiguration <em>Paste Configuration</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Paste Configuration</em>' reference.
	 * @see #getPasteConfiguration()
	 * @generated
	 */
	void setPasteConfiguration(PasteEObjectConfiguration value);

	/**
	 * Returns the value of the '<em><b>Depth</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Depth</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Depth</em>' attribute.
	 * @see #setDepth(int)
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationPackage#getTreeFillingConfiguration_Depth()
	 * @model
	 * @generated
	 */
	int getDepth();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration#getDepth <em>Depth</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Depth</em>' attribute.
	 * @see #getDepth()
	 * @generated
	 */
	void setDepth(int value);

	/**
	 * Returns the value of the '<em><b>Label Provider</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * le label provider used to display string and image for this level in the tree
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Label Provider</em>' reference.
	 * @see #setLabelProvider(ILabelProviderConfiguration)
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationPackage#getTreeFillingConfiguration_LabelProvider()
	 * @model required="true"
	 * @generated
	 */
	ILabelProviderConfiguration getLabelProvider();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration#getLabelProvider <em>Label Provider</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label Provider</em>' reference.
	 * @see #getLabelProvider()
	 * @generated
	 */
	void setLabelProvider(ILabelProviderConfiguration value);

	/**
	 * Returns the value of the '<em><b>Label Provider Context</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label Provider Context</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label Provider Context</em>' attribute.
	 * @see #setLabelProviderContext(String)
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationPackage#getTreeFillingConfiguration_LabelProviderContext()
	 * @model required="true"
	 * @generated
	 */
	String getLabelProviderContext();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration#getLabelProviderContext <em>Label Provider Context</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label Provider Context</em>' attribute.
	 * @see #getLabelProviderContext()
	 * @generated
	 */
	void setLabelProviderContext(String value);

	/**
	 * Returns the value of the '<em><b>Filter Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This field is used to filter the elements to show for this depth. 
	 * Only element matching the filter will be returned.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Filter Rule</em>' containment reference.
	 * @see #setFilterRule(IBooleanEObjectExpression)
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationPackage#getTreeFillingConfiguration_FilterRule()
	 * @model containment="true"
	 * @generated
	 */
	IBooleanEObjectExpression getFilterRule();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration#getFilterRule <em>Filter Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Filter Rule</em>' containment reference.
	 * @see #getFilterRule()
	 * @generated
	 */
	void setFilterRule(IBooleanEObjectExpression value);
} // TreeFillingConfiguration
