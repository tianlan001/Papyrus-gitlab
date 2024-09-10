/**
 * Copyright (c) 2021 CEA LIST.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 */
package org.eclipse.papyrus.infra.textedit.textdocument.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.papyrus.infra.textedit.textdocument.TextDocument;
import org.eclipse.papyrus.infra.textedit.textdocument.TextDocumentPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Text Document</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.textedit.textdocument.impl.TextDocumentImpl#getSemanticContext <em>Semantic Context</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.textedit.textdocument.impl.TextDocumentImpl#getGraphicalContext <em>Graphical Context</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.textedit.textdocument.impl.TextDocumentImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.textedit.textdocument.impl.TextDocumentImpl#getKindId <em>Kind Id</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.textedit.textdocument.impl.TextDocumentImpl#getType <em>Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TextDocumentImpl extends MinimalEObjectImpl.Container implements TextDocument {
	/**
	 * The cached value of the '{@link #getSemanticContext() <em>Semantic Context</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getSemanticContext()
	 * @generated
	 * @ordered
	 */
	protected EObject semanticContext;

	/**
	 * The cached value of the '{@link #getGraphicalContext() <em>Graphical Context</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getGraphicalContext()
	 * @generated
	 * @ordered
	 */
	protected EObject graphicalContext;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getKindId() <em>Kind Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getKindId()
	 * @generated
	 * @ordered
	 */
	protected static final String KIND_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getKindId() <em>Kind Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getKindId()
	 * @generated
	 * @ordered
	 */
	protected String kindId = KIND_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected TextDocumentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TextDocumentPackage.Literals.TEXT_DOCUMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EObject getSemanticContext() {
		if (semanticContext != null && semanticContext.eIsProxy()) {
			InternalEObject oldSemanticContext = (InternalEObject) semanticContext;
			semanticContext = eResolveProxy(oldSemanticContext);
			if (semanticContext != oldSemanticContext) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TextDocumentPackage.TEXT_DOCUMENT__SEMANTIC_CONTEXT, oldSemanticContext, semanticContext));
				}
			}
		}
		return semanticContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public EObject basicGetSemanticContext() {
		return semanticContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setSemanticContext(EObject newSemanticContext) {
		EObject oldSemanticContext = semanticContext;
		semanticContext = newSemanticContext;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TextDocumentPackage.TEXT_DOCUMENT__SEMANTIC_CONTEXT, oldSemanticContext, semanticContext));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EObject getGraphicalContext() {
		if (graphicalContext != null && graphicalContext.eIsProxy()) {
			InternalEObject oldGraphicalContext = (InternalEObject) graphicalContext;
			graphicalContext = eResolveProxy(oldGraphicalContext);
			if (graphicalContext != oldGraphicalContext) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, TextDocumentPackage.TEXT_DOCUMENT__GRAPHICAL_CONTEXT, oldGraphicalContext, graphicalContext));
				}
			}
		}
		return graphicalContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public EObject basicGetGraphicalContext() {
		return graphicalContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setGraphicalContext(EObject newGraphicalContext) {
		EObject oldGraphicalContext = graphicalContext;
		graphicalContext = newGraphicalContext;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TextDocumentPackage.TEXT_DOCUMENT__GRAPHICAL_CONTEXT, oldGraphicalContext, graphicalContext));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TextDocumentPackage.TEXT_DOCUMENT__NAME, oldName, name));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getKindId() {
		return kindId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setKindId(String newKindId) {
		String oldKindId = kindId;
		kindId = newKindId;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TextDocumentPackage.TEXT_DOCUMENT__KIND_ID, oldKindId, kindId));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setType(String newType) {
		String oldType = type;
		type = newType;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, TextDocumentPackage.TEXT_DOCUMENT__TYPE, oldType, type));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case TextDocumentPackage.TEXT_DOCUMENT__SEMANTIC_CONTEXT:
			if (resolve) {
				return getSemanticContext();
			}
			return basicGetSemanticContext();
		case TextDocumentPackage.TEXT_DOCUMENT__GRAPHICAL_CONTEXT:
			if (resolve) {
				return getGraphicalContext();
			}
			return basicGetGraphicalContext();
		case TextDocumentPackage.TEXT_DOCUMENT__NAME:
			return getName();
		case TextDocumentPackage.TEXT_DOCUMENT__KIND_ID:
			return getKindId();
		case TextDocumentPackage.TEXT_DOCUMENT__TYPE:
			return getType();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case TextDocumentPackage.TEXT_DOCUMENT__SEMANTIC_CONTEXT:
			setSemanticContext((EObject) newValue);
			return;
		case TextDocumentPackage.TEXT_DOCUMENT__GRAPHICAL_CONTEXT:
			setGraphicalContext((EObject) newValue);
			return;
		case TextDocumentPackage.TEXT_DOCUMENT__NAME:
			setName((String) newValue);
			return;
		case TextDocumentPackage.TEXT_DOCUMENT__KIND_ID:
			setKindId((String) newValue);
			return;
		case TextDocumentPackage.TEXT_DOCUMENT__TYPE:
			setType((String) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case TextDocumentPackage.TEXT_DOCUMENT__SEMANTIC_CONTEXT:
			setSemanticContext((EObject) null);
			return;
		case TextDocumentPackage.TEXT_DOCUMENT__GRAPHICAL_CONTEXT:
			setGraphicalContext((EObject) null);
			return;
		case TextDocumentPackage.TEXT_DOCUMENT__NAME:
			setName(NAME_EDEFAULT);
			return;
		case TextDocumentPackage.TEXT_DOCUMENT__KIND_ID:
			setKindId(KIND_ID_EDEFAULT);
			return;
		case TextDocumentPackage.TEXT_DOCUMENT__TYPE:
			setType(TYPE_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case TextDocumentPackage.TEXT_DOCUMENT__SEMANTIC_CONTEXT:
			return semanticContext != null;
		case TextDocumentPackage.TEXT_DOCUMENT__GRAPHICAL_CONTEXT:
			return graphicalContext != null;
		case TextDocumentPackage.TEXT_DOCUMENT__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case TextDocumentPackage.TEXT_DOCUMENT__KIND_ID:
			return KIND_ID_EDEFAULT == null ? kindId != null : !KIND_ID_EDEFAULT.equals(kindId);
		case TextDocumentPackage.TEXT_DOCUMENT__TYPE:
			return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", kindId: "); //$NON-NLS-1$
		result.append(kindId);
		result.append(", type: "); //$NON-NLS-1$
		result.append(type);
		result.append(')');
		return result.toString();
	}

} // TextDocumentImpl
