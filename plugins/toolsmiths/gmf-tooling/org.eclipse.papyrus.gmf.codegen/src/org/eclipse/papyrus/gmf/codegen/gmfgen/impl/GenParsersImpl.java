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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenParserImplementation;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenParsers;
import org.eclipse.papyrus.gmf.codegen.gmfgen.ProviderPriority;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Parsers</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenParsersImpl#getEditorGen <em>Editor Gen</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenParsersImpl#getPackageName <em>Package Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenParsersImpl#getClassName <em>Class Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenParsersImpl#isExtensibleViaService <em>Extensible Via Service</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenParsersImpl#getProviderPriority <em>Provider Priority</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenParsersImpl#getImplementations <em>Implementations</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenParsersImpl#getImplPackageName <em>Impl Package Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenParsersImpl extends EObjectImpl implements GenParsers {
	/**
	 * The default value of the '{@link #getPackageName() <em>Package Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageName()
	 * @generated
	 * @ordered
	 */
	protected static final String PACKAGE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPackageName() <em>Package Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageName()
	 * @generated
	 * @ordered
	 */
	protected String packageName = PACKAGE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassName()
	 * @generated
	 * @ordered
	 */
	protected String className = CLASS_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isExtensibleViaService() <em>Extensible Via Service</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExtensibleViaService()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EXTENSIBLE_VIA_SERVICE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isExtensibleViaService() <em>Extensible Via Service</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExtensibleViaService()
	 * @generated
	 * @ordered
	 */
	protected boolean extensibleViaService = EXTENSIBLE_VIA_SERVICE_EDEFAULT;

	/**
	 * The default value of the '{@link #getProviderPriority() <em>Provider Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProviderPriority()
	 * @generated
	 * @ordered
	 */
	protected static final ProviderPriority PROVIDER_PRIORITY_EDEFAULT = ProviderPriority.LOWEST_LITERAL;

	/**
	 * The cached value of the '{@link #getProviderPriority() <em>Provider Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProviderPriority()
	 * @generated
	 * @ordered
	 */
	protected ProviderPriority providerPriority = PROVIDER_PRIORITY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getImplementations() <em>Implementations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementations()
	 * @generated
	 * @ordered
	 */
	protected EList<GenParserImplementation> implementations;

	/**
	 * The default value of the '{@link #getImplPackageName() <em>Impl Package Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplPackageName()
	 * @generated
	 * @ordered
	 */
	protected static final String IMPL_PACKAGE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImplPackageName() <em>Impl Package Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplPackageName()
	 * @generated
	 * @ordered
	 */
	protected String implPackageName = IMPL_PACKAGE_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenParsersImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getGenParsers();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenEditorGenerator getEditorGen() {
		if (eContainerFeatureID() != GMFGenPackage.GEN_PARSERS__EDITOR_GEN) return null;
		return (GenEditorGenerator)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPackageNameGen() {
		return packageName;
	}

	public String getPackageName() {
		if (getPackageNameGen() == null && getEditorGen() != null && getEditorGen().getDiagram() != null) {
			return getEditorGen().getDiagram().getProvidersPackageName();
		}
		return getPackageNameGen();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPackageName(String newPackageName) {
		String oldPackageName = packageName;
		packageName = newPackageName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_PARSERS__PACKAGE_NAME, oldPackageName, packageName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getClassNameGen() {
		return className;
	}

	public String getClassName() {
		if (getClassNameGen() == null && getEditorGen() != null && getEditorGen().getDiagram() != null) {
			return getEditorGen().getDiagram().getParserProviderClassName();
		}
		return getClassNameGen();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setClassName(String newClassName) {
		String oldClassName = className;
		className = newClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_PARSERS__CLASS_NAME, oldClassName, className));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isExtensibleViaService() {
		return extensibleViaService;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExtensibleViaService(boolean newExtensibleViaService) {
		boolean oldExtensibleViaService = extensibleViaService;
		extensibleViaService = newExtensibleViaService;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_PARSERS__EXTENSIBLE_VIA_SERVICE, oldExtensibleViaService, extensibleViaService));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProviderPriority getProviderPriorityGen() {
		return providerPriority;
	}

	public ProviderPriority getProviderPriority() {
		if (getProviderPriorityGen() == PROVIDER_PRIORITY_EDEFAULT && getEditorGen() != null && getEditorGen().getDiagram() != null) {
			return getEditorGen().getDiagram().getParserProviderPriority();
		}
		return getProviderPriorityGen();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setProviderPriority(ProviderPriority newProviderPriority) {
		ProviderPriority oldProviderPriority = providerPriority;
		providerPriority = newProviderPriority == null ? PROVIDER_PRIORITY_EDEFAULT : newProviderPriority;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_PARSERS__PROVIDER_PRIORITY, oldProviderPriority, providerPriority));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<GenParserImplementation> getImplementations() {
		if (implementations == null) {
			implementations = new EObjectContainmentWithInverseEList<GenParserImplementation>(GenParserImplementation.class, this, GMFGenPackage.GEN_PARSERS__IMPLEMENTATIONS, GMFGenPackage.GEN_PARSER_IMPLEMENTATION__HOLDER);
		}
		return implementations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImplPackageNameGen() {
		return implPackageName;
	}

	public String getImplPackageName() {
		String p = getImplPackageNameGen();
		if (!GenCommonBaseImpl.isEmpty(p)) {
			return p;
		}
		if (getEditorGen() != null && getEditorGen().getDiagram() != null) {
			// XXXX use old data. remove this code when model migrated
			p = getEditorGen().getDiagram().getParsersPackageName();
			if (!GenCommonBaseImpl.isEmpty(p)) {
				return p;
			}
		}
		return getPackageName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setImplPackageName(String newImplPackageName) {
		String oldImplPackageName = implPackageName;
		implPackageName = newImplPackageName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_PARSERS__IMPL_PACKAGE_NAME, oldImplPackageName, implPackageName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getQualifiedClassName() {
		return getPackageName() == null ? getClassName() : getPackageName() + '.' + getClassName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GMFGenPackage.GEN_PARSERS__EDITOR_GEN:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return eBasicSetContainer(otherEnd, GMFGenPackage.GEN_PARSERS__EDITOR_GEN, msgs);
			case GMFGenPackage.GEN_PARSERS__IMPLEMENTATIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getImplementations()).basicAdd(otherEnd, msgs);
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
			case GMFGenPackage.GEN_PARSERS__EDITOR_GEN:
				return eBasicSetContainer(null, GMFGenPackage.GEN_PARSERS__EDITOR_GEN, msgs);
			case GMFGenPackage.GEN_PARSERS__IMPLEMENTATIONS:
				return ((InternalEList<?>)getImplementations()).basicRemove(otherEnd, msgs);
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
			case GMFGenPackage.GEN_PARSERS__EDITOR_GEN:
				return eInternalContainer().eInverseRemove(this, GMFGenPackage.GEN_EDITOR_GENERATOR__LABEL_PARSERS, GenEditorGenerator.class, msgs);
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
			case GMFGenPackage.GEN_PARSERS__EDITOR_GEN:
				return getEditorGen();
			case GMFGenPackage.GEN_PARSERS__PACKAGE_NAME:
				return getPackageName();
			case GMFGenPackage.GEN_PARSERS__CLASS_NAME:
				return getClassName();
			case GMFGenPackage.GEN_PARSERS__EXTENSIBLE_VIA_SERVICE:
				return isExtensibleViaService();
			case GMFGenPackage.GEN_PARSERS__PROVIDER_PRIORITY:
				return getProviderPriority();
			case GMFGenPackage.GEN_PARSERS__IMPLEMENTATIONS:
				return getImplementations();
			case GMFGenPackage.GEN_PARSERS__IMPL_PACKAGE_NAME:
				return getImplPackageName();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case GMFGenPackage.GEN_PARSERS__PACKAGE_NAME:
				setPackageName((String)newValue);
				return;
			case GMFGenPackage.GEN_PARSERS__CLASS_NAME:
				setClassName((String)newValue);
				return;
			case GMFGenPackage.GEN_PARSERS__EXTENSIBLE_VIA_SERVICE:
				setExtensibleViaService((Boolean)newValue);
				return;
			case GMFGenPackage.GEN_PARSERS__PROVIDER_PRIORITY:
				setProviderPriority((ProviderPriority)newValue);
				return;
			case GMFGenPackage.GEN_PARSERS__IMPLEMENTATIONS:
				getImplementations().clear();
				getImplementations().addAll((Collection<? extends GenParserImplementation>)newValue);
				return;
			case GMFGenPackage.GEN_PARSERS__IMPL_PACKAGE_NAME:
				setImplPackageName((String)newValue);
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
			case GMFGenPackage.GEN_PARSERS__PACKAGE_NAME:
				setPackageName(PACKAGE_NAME_EDEFAULT);
				return;
			case GMFGenPackage.GEN_PARSERS__CLASS_NAME:
				setClassName(CLASS_NAME_EDEFAULT);
				return;
			case GMFGenPackage.GEN_PARSERS__EXTENSIBLE_VIA_SERVICE:
				setExtensibleViaService(EXTENSIBLE_VIA_SERVICE_EDEFAULT);
				return;
			case GMFGenPackage.GEN_PARSERS__PROVIDER_PRIORITY:
				setProviderPriority(PROVIDER_PRIORITY_EDEFAULT);
				return;
			case GMFGenPackage.GEN_PARSERS__IMPLEMENTATIONS:
				getImplementations().clear();
				return;
			case GMFGenPackage.GEN_PARSERS__IMPL_PACKAGE_NAME:
				setImplPackageName(IMPL_PACKAGE_NAME_EDEFAULT);
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
			case GMFGenPackage.GEN_PARSERS__EDITOR_GEN:
				return getEditorGen() != null;
			case GMFGenPackage.GEN_PARSERS__PACKAGE_NAME:
				return PACKAGE_NAME_EDEFAULT == null ? packageName != null : !PACKAGE_NAME_EDEFAULT.equals(packageName);
			case GMFGenPackage.GEN_PARSERS__CLASS_NAME:
				return CLASS_NAME_EDEFAULT == null ? className != null : !CLASS_NAME_EDEFAULT.equals(className);
			case GMFGenPackage.GEN_PARSERS__EXTENSIBLE_VIA_SERVICE:
				return extensibleViaService != EXTENSIBLE_VIA_SERVICE_EDEFAULT;
			case GMFGenPackage.GEN_PARSERS__PROVIDER_PRIORITY:
				return providerPriority != PROVIDER_PRIORITY_EDEFAULT;
			case GMFGenPackage.GEN_PARSERS__IMPLEMENTATIONS:
				return implementations != null && !implementations.isEmpty();
			case GMFGenPackage.GEN_PARSERS__IMPL_PACKAGE_NAME:
				return IMPL_PACKAGE_NAME_EDEFAULT == null ? implPackageName != null : !IMPL_PACKAGE_NAME_EDEFAULT.equals(implPackageName);
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
		result.append(" (packageName: ");
		result.append(packageName);
		result.append(", className: ");
		result.append(className);
		result.append(", extensibleViaService: ");
		result.append(extensibleViaService);
		result.append(", providerPriority: ");
		result.append(providerPriority);
		result.append(", implPackageName: ");
		result.append(implPackageName);
		result.append(')');
		return result.toString();
	}

} //GenParsersImpl
