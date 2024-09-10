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

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.gmf.codegen.gmfgen.ElementType;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode;
import org.eclipse.papyrus.gmf.codegen.gmfgen.LinkModelFacet;
import org.eclipse.papyrus.gmf.codegen.gmfgen.TypeLinkModelFacet;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Element Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.ElementTypeImpl#getDiagramElement <em>Diagram Element</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.ElementTypeImpl#getUniqueIdentifier <em>Unique Identifier</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.ElementTypeImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.ElementTypeImpl#isDefinedExternally <em>Defined Externally</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ElementTypeImpl extends EObjectImpl implements ElementType {
	/**
	 * The default value of the '{@link #getUniqueIdentifier() <em>Unique Identifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUniqueIdentifier()
	 * @generated
	 * @ordered
	 */
	protected static final String UNIQUE_IDENTIFIER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUniqueIdentifier() <em>Unique Identifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUniqueIdentifier()
	 * @generated
	 * @ordered
	 */
	protected String uniqueIdentifier = UNIQUE_IDENTIFIER_EDEFAULT;

	/**
	 * The default value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected static final String DISPLAY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected String displayName = DISPLAY_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isDefinedExternally() <em>Defined Externally</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDefinedExternally()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DEFINED_EXTERNALLY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDefinedExternally() <em>Defined Externally</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDefinedExternally()
	 * @generated
	 * @ordered
	 */
	protected boolean definedExternally = DEFINED_EXTERNALLY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ElementTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getElementType();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenCommonBase getDiagramElement() {
		if (eContainerFeatureID() != GMFGenPackage.ELEMENT_TYPE__DIAGRAM_ELEMENT) return null;
		return (GenCommonBase)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDiagramElement(GenCommonBase newDiagramElement, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newDiagramElement, GMFGenPackage.ELEMENT_TYPE__DIAGRAM_ELEMENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDiagramElement(GenCommonBase newDiagramElement) {
		if (newDiagramElement != eInternalContainer() || (eContainerFeatureID() != GMFGenPackage.ELEMENT_TYPE__DIAGRAM_ELEMENT && newDiagramElement != null)) {
			if (EcoreUtil.isAncestor(this, newDiagramElement))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newDiagramElement != null)
				msgs = ((InternalEObject)newDiagramElement).eInverseAdd(this, GMFGenPackage.GEN_COMMON_BASE__ELEMENT_TYPE, GenCommonBase.class, msgs);
			msgs = basicSetDiagramElement(newDiagramElement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.ELEMENT_TYPE__DIAGRAM_ELEMENT, newDiagramElement, newDiagramElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUniqueIdentifierGen() {
		return uniqueIdentifier;
	}

	public String getUniqueIdentifier() {
		String value = getUniqueIdentifierGen();
		
		if(getDiagramElement() == null || getDiagramElement().getDiagram() == null
				|| getDiagramElement().getDiagram().getEditorGen() == null
				|| getDiagramElement().getDiagram().getEditorGen().getPlugin() == null)
			return value;
		
		if (GenCommonBaseImpl.isEmpty(value)) {
			value = getDiagramElement().getDiagram().getEditorGen().getPlugin().getID() + '.' + getDiagramElement().getUniqueIdentifier();
		}
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUniqueIdentifier(String newUniqueIdentifier) {
		String oldUniqueIdentifier = uniqueIdentifier;
		uniqueIdentifier = newUniqueIdentifier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.ELEMENT_TYPE__UNIQUE_IDENTIFIER, oldUniqueIdentifier, uniqueIdentifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDisplayNameGen() {
		return displayName;
	}

	public String getDisplayName() {
		if (!GenCommonBaseImpl.isEmpty(getDisplayNameGen())) {
			return getDisplayNameGen();
		}
		if (getDiagramElement() instanceof GenNode) {
			GenClass type = ((GenNode) getDiagramElement()).getDomainMetaClass();
			if (type != null) {
				return type.getName();
			}
		} else if (getDiagramElement() instanceof GenLink) {
			LinkModelFacet mf = ((GenLink) getDiagramElement()).getModelFacet();
			if (mf instanceof TypeLinkModelFacet) {
				GenClass type = ((TypeLinkModelFacet) mf).getMetaClass();
				if (type != null) {
					return type.getName();
				}
			}
		}
		return "Undefined";
		// for more details see https://bugs.eclipse.org/bugs/show_bug.cgi?id=157512
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDisplayName(String newDisplayName) {
		String oldDisplayName = displayName;
		displayName = newDisplayName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.ELEMENT_TYPE__DISPLAY_NAME, oldDisplayName, displayName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDefinedExternally() {
		return definedExternally;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDefinedExternally(boolean newDefinedExternally) {
		boolean oldDefinedExternally = definedExternally;
		definedExternally = newDefinedExternally;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.ELEMENT_TYPE__DEFINED_EXTERNALLY, oldDefinedExternally, definedExternally));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GMFGenPackage.ELEMENT_TYPE__DIAGRAM_ELEMENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetDiagramElement((GenCommonBase)otherEnd, msgs);
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
			case GMFGenPackage.ELEMENT_TYPE__DIAGRAM_ELEMENT:
				return basicSetDiagramElement(null, msgs);
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
			case GMFGenPackage.ELEMENT_TYPE__DIAGRAM_ELEMENT:
				return eInternalContainer().eInverseRemove(this, GMFGenPackage.GEN_COMMON_BASE__ELEMENT_TYPE, GenCommonBase.class, msgs);
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
			case GMFGenPackage.ELEMENT_TYPE__DIAGRAM_ELEMENT:
				return getDiagramElement();
			case GMFGenPackage.ELEMENT_TYPE__UNIQUE_IDENTIFIER:
				return getUniqueIdentifier();
			case GMFGenPackage.ELEMENT_TYPE__DISPLAY_NAME:
				return getDisplayName();
			case GMFGenPackage.ELEMENT_TYPE__DEFINED_EXTERNALLY:
				return isDefinedExternally();
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
			case GMFGenPackage.ELEMENT_TYPE__DIAGRAM_ELEMENT:
				setDiagramElement((GenCommonBase)newValue);
				return;
			case GMFGenPackage.ELEMENT_TYPE__UNIQUE_IDENTIFIER:
				setUniqueIdentifier((String)newValue);
				return;
			case GMFGenPackage.ELEMENT_TYPE__DISPLAY_NAME:
				setDisplayName((String)newValue);
				return;
			case GMFGenPackage.ELEMENT_TYPE__DEFINED_EXTERNALLY:
				setDefinedExternally((Boolean)newValue);
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
			case GMFGenPackage.ELEMENT_TYPE__DIAGRAM_ELEMENT:
				setDiagramElement((GenCommonBase)null);
				return;
			case GMFGenPackage.ELEMENT_TYPE__UNIQUE_IDENTIFIER:
				setUniqueIdentifier(UNIQUE_IDENTIFIER_EDEFAULT);
				return;
			case GMFGenPackage.ELEMENT_TYPE__DISPLAY_NAME:
				setDisplayName(DISPLAY_NAME_EDEFAULT);
				return;
			case GMFGenPackage.ELEMENT_TYPE__DEFINED_EXTERNALLY:
				setDefinedExternally(DEFINED_EXTERNALLY_EDEFAULT);
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
			case GMFGenPackage.ELEMENT_TYPE__DIAGRAM_ELEMENT:
				return getDiagramElement() != null;
			case GMFGenPackage.ELEMENT_TYPE__UNIQUE_IDENTIFIER:
				return UNIQUE_IDENTIFIER_EDEFAULT == null ? uniqueIdentifier != null : !UNIQUE_IDENTIFIER_EDEFAULT.equals(uniqueIdentifier);
			case GMFGenPackage.ELEMENT_TYPE__DISPLAY_NAME:
				return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
			case GMFGenPackage.ELEMENT_TYPE__DEFINED_EXTERNALLY:
				return definedExternally != DEFINED_EXTERNALLY_EDEFAULT;
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
		result.append(" (uniqueIdentifier: ");
		result.append(uniqueIdentifier);
		result.append(", displayName: ");
		result.append(displayName);
		result.append(", definedExternally: ");
		result.append(definedExternally);
		result.append(')');
		return result.toString();
	}

} //ElementTypeImpl
