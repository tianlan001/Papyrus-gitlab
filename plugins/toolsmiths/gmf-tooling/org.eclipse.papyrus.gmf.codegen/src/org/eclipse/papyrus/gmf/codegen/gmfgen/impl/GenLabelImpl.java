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
package org.eclipse.papyrus.gmf.codegen.gmfgen.impl;

import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.papyrus.gmf.codegen.gmfgen.DesignLabelModelFacet;
import org.eclipse.papyrus.gmf.codegen.gmfgen.FeatureLabelModelFacet;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLabel;
import org.eclipse.papyrus.gmf.codegen.gmfgen.LabelModelFacet;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Label</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenLabelImpl#isReadOnly <em>Read Only</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenLabelImpl#isElementIcon <em>Element Icon</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenLabelImpl#getModelFacet <em>Model Facet</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class GenLabelImpl extends GenCommonBaseImpl implements GenLabel {
	/**
	 * The default value of the '{@link #isReadOnly() <em>Read Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReadOnly()
	 * @generated
	 * @ordered
	 */
	protected static final boolean READ_ONLY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isReadOnly() <em>Read Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReadOnly()
	 * @generated
	 * @ordered
	 */
	protected boolean readOnly = READ_ONLY_EDEFAULT;

	/**
	 * The default value of the '{@link #isElementIcon() <em>Element Icon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isElementIcon()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ELEMENT_ICON_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isElementIcon() <em>Element Icon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isElementIcon()
	 * @generated
	 * @ordered
	 */
	protected boolean elementIcon = ELEMENT_ICON_EDEFAULT;

	/**
	 * The cached value of the '{@link #getModelFacet() <em>Model Facet</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModelFacet()
	 * @generated
	 * @ordered
	 */
	protected LabelModelFacet modelFacet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenLabelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getGenLabel();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReadOnly(boolean newReadOnly) {
		boolean oldReadOnly = readOnly;
		readOnly = newReadOnly;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_LABEL__READ_ONLY, oldReadOnly, readOnly));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isElementIcon() {
		return elementIcon;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setElementIcon(boolean newElementIcon) {
		boolean oldElementIcon = elementIcon;
		elementIcon = newElementIcon;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_LABEL__ELEMENT_ICON, oldElementIcon, elementIcon));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LabelModelFacet getModelFacet() {
		return modelFacet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetModelFacet(LabelModelFacet newModelFacet, NotificationChain msgs) {
		LabelModelFacet oldModelFacet = modelFacet;
		modelFacet = newModelFacet;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_LABEL__MODEL_FACET, oldModelFacet, newModelFacet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setModelFacet(LabelModelFacet newModelFacet) {
		if (newModelFacet != modelFacet) {
			NotificationChain msgs = null;
			if (modelFacet != null)
				msgs = ((InternalEObject)modelFacet).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GMFGenPackage.GEN_LABEL__MODEL_FACET, null, msgs);
			if (newModelFacet != null)
				msgs = ((InternalEObject)newModelFacet).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GMFGenPackage.GEN_LABEL__MODEL_FACET, null, msgs);
			msgs = basicSetModelFacet(newModelFacet, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_LABEL__MODEL_FACET, newModelFacet, newModelFacet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<GenFeature> getMetaFeatures() {
		return getMetaFeatures(getModelFacet());
	}

	public static EList<GenFeature> getMetaFeatures(LabelModelFacet modelFacet) {
		BasicEList<GenFeature> metaFeatures = new BasicEList<GenFeature>();
		if (modelFacet instanceof FeatureLabelModelFacet) {
			metaFeatures.addAll(((FeatureLabelModelFacet) modelFacet).getMetaFeatures());
		}
		return metaFeatures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GMFGenPackage.GEN_LABEL__MODEL_FACET:
				return basicSetModelFacet(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GMFGenPackage.GEN_LABEL__READ_ONLY:
				return isReadOnly();
			case GMFGenPackage.GEN_LABEL__ELEMENT_ICON:
				return isElementIcon();
			case GMFGenPackage.GEN_LABEL__MODEL_FACET:
				return getModelFacet();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case GMFGenPackage.GEN_LABEL__READ_ONLY:
				setReadOnly((Boolean)newValue);
				return;
			case GMFGenPackage.GEN_LABEL__ELEMENT_ICON:
				setElementIcon((Boolean)newValue);
				return;
			case GMFGenPackage.GEN_LABEL__MODEL_FACET:
				setModelFacet((LabelModelFacet)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case GMFGenPackage.GEN_LABEL__READ_ONLY:
				setReadOnly(READ_ONLY_EDEFAULT);
				return;
			case GMFGenPackage.GEN_LABEL__ELEMENT_ICON:
				setElementIcon(ELEMENT_ICON_EDEFAULT);
				return;
			case GMFGenPackage.GEN_LABEL__MODEL_FACET:
				setModelFacet((LabelModelFacet)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case GMFGenPackage.GEN_LABEL__READ_ONLY:
				return readOnly != READ_ONLY_EDEFAULT;
			case GMFGenPackage.GEN_LABEL__ELEMENT_ICON:
				return elementIcon != ELEMENT_ICON_EDEFAULT;
			case GMFGenPackage.GEN_LABEL__MODEL_FACET:
				return modelFacet != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (readOnly: ");
		result.append(readOnly);
		result.append(", elementIcon: ");
		result.append(elementIcon);
		result.append(')');
		return result.toString();
	}

	protected abstract String getHostName();

	public String getClassNamePrefix() {
		// should be consistent with ClassNamingStrategy
		StringBuilder sb = new StringBuilder();
		for (GenFeature metaFeature : getMetaFeatures()) {
			sb.append(metaFeature.getCapName());
		}
		return getHostName() + (sb.length() > 0 ? sb.toString() : CLASS_NAME_PREFIX);
	}
	
	public boolean isSansDomain() {
		return getModelFacet() instanceof DesignLabelModelFacet;
	}
} //GenLabelImpl
