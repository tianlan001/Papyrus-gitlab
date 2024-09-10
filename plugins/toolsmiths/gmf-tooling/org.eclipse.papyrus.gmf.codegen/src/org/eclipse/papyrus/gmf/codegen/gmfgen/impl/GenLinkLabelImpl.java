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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenFloatingLabel;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLinkLabel;
import org.eclipse.papyrus.gmf.codegen.gmfgen.LinkLabelAlignment;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Link Label</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenLinkLabelImpl#getLink <em>Link</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenLinkLabelImpl#getAlignment <em>Alignment</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenLinkLabelImpl#getLabelVisibilityPreference <em>Label Visibility Preference</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenLinkLabelImpl extends GenLabelImpl implements GenLinkLabel {
	/**
	 * The default value of the '{@link #getAlignment() <em>Alignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlignment()
	 * @generated
	 * @ordered
	 */
	protected static final LinkLabelAlignment ALIGNMENT_EDEFAULT = LinkLabelAlignment.MIDDLE_LITERAL;

	/**
	 * The cached value of the '{@link #getAlignment() <em>Alignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlignment()
	 * @generated
	 * @ordered
	 */
	protected LinkLabelAlignment alignment = ALIGNMENT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLabelVisibilityPreference() <em>Label Visibility Preference</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabelVisibilityPreference()
	 * @generated
	 * @ordered
	 */
	protected GenFloatingLabel labelVisibilityPreference;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenLinkLabelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getGenLinkLabel();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenLink getLink() {
		if (eContainerFeatureID() != GMFGenPackage.GEN_LINK_LABEL__LINK) return null;
		return (GenLink)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLink(GenLink newLink, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newLink, GMFGenPackage.GEN_LINK_LABEL__LINK, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLink(GenLink newLink) {
		if (newLink != eInternalContainer() || (eContainerFeatureID() != GMFGenPackage.GEN_LINK_LABEL__LINK && newLink != null)) {
			if (EcoreUtil.isAncestor(this, newLink))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newLink != null)
				msgs = ((InternalEObject)newLink).eInverseAdd(this, GMFGenPackage.GEN_LINK__LABELS, GenLink.class, msgs);
			msgs = basicSetLink(newLink, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_LINK_LABEL__LINK, newLink, newLink));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LinkLabelAlignment getAlignment() {
		return alignment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAlignment(LinkLabelAlignment newAlignment) {
		LinkLabelAlignment oldAlignment = alignment;
		alignment = newAlignment == null ? ALIGNMENT_EDEFAULT : newAlignment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_LINK_LABEL__ALIGNMENT, oldAlignment, alignment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenFloatingLabel getLabelVisibilityPreference() {
		return labelVisibilityPreference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLabelVisibilityPreference(GenFloatingLabel newLabelVisibilityPreference, NotificationChain msgs) {
		GenFloatingLabel oldLabelVisibilityPreference = labelVisibilityPreference;
		labelVisibilityPreference = newLabelVisibilityPreference;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_LINK_LABEL__LABEL_VISIBILITY_PREFERENCE, oldLabelVisibilityPreference, newLabelVisibilityPreference);
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
	public void setLabelVisibilityPreference(GenFloatingLabel newLabelVisibilityPreference) {
		if (newLabelVisibilityPreference != labelVisibilityPreference) {
			NotificationChain msgs = null;
			if (labelVisibilityPreference != null)
				msgs = ((InternalEObject)labelVisibilityPreference).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GMFGenPackage.GEN_LINK_LABEL__LABEL_VISIBILITY_PREFERENCE, null, msgs);
			if (newLabelVisibilityPreference != null)
				msgs = ((InternalEObject)newLabelVisibilityPreference).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GMFGenPackage.GEN_LINK_LABEL__LABEL_VISIBILITY_PREFERENCE, null, msgs);
			msgs = basicSetLabelVisibilityPreference(newLabelVisibilityPreference, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_LINK_LABEL__LABEL_VISIBILITY_PREFERENCE, newLabelVisibilityPreference, newLabelVisibilityPreference));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GMFGenPackage.GEN_LINK_LABEL__LINK:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetLink((GenLink)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GMFGenPackage.GEN_LINK_LABEL__LINK:
				return basicSetLink(null, msgs);
			case GMFGenPackage.GEN_LINK_LABEL__LABEL_VISIBILITY_PREFERENCE:
				return basicSetLabelVisibilityPreference(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case GMFGenPackage.GEN_LINK_LABEL__LINK:
				return eInternalContainer().eInverseRemove(this, GMFGenPackage.GEN_LINK__LABELS, GenLink.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GMFGenPackage.GEN_LINK_LABEL__LINK:
				return getLink();
			case GMFGenPackage.GEN_LINK_LABEL__ALIGNMENT:
				return getAlignment();
			case GMFGenPackage.GEN_LINK_LABEL__LABEL_VISIBILITY_PREFERENCE:
				return getLabelVisibilityPreference();
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
			case GMFGenPackage.GEN_LINK_LABEL__LINK:
				setLink((GenLink)newValue);
				return;
			case GMFGenPackage.GEN_LINK_LABEL__ALIGNMENT:
				setAlignment((LinkLabelAlignment)newValue);
				return;
			case GMFGenPackage.GEN_LINK_LABEL__LABEL_VISIBILITY_PREFERENCE:
				setLabelVisibilityPreference((GenFloatingLabel)newValue);
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
			case GMFGenPackage.GEN_LINK_LABEL__LINK:
				setLink((GenLink)null);
				return;
			case GMFGenPackage.GEN_LINK_LABEL__ALIGNMENT:
				setAlignment(ALIGNMENT_EDEFAULT);
				return;
			case GMFGenPackage.GEN_LINK_LABEL__LABEL_VISIBILITY_PREFERENCE:
				setLabelVisibilityPreference((GenFloatingLabel)null);
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
			case GMFGenPackage.GEN_LINK_LABEL__LINK:
				return getLink() != null;
			case GMFGenPackage.GEN_LINK_LABEL__ALIGNMENT:
				return alignment != ALIGNMENT_EDEFAULT;
			case GMFGenPackage.GEN_LINK_LABEL__LABEL_VISIBILITY_PREFERENCE:
				return labelVisibilityPreference != null;
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
		result.append(" (alignment: ");
		result.append(alignment);
		result.append(')');
		return result.toString();
	}

	public GenDiagram getDiagram() {
		return getLink().getDiagram();
	}

	protected String getHostName() {
		return getLink().getClassNamePrefix();
	}

} //GenLinkLabelImpl
