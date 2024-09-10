/**
 * Copyright (c) 2017, 2021 CEA LIST, Christian W. Damus, and others.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors:
 *  Maged Elaasar - Initial API and implementation
 *  Christian W. Damus - bugs 539694, 570856, 570486
 * 
 */
package org.eclipse.papyrus.infra.core.architecture.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDomain;
import org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureViewpoint;
import org.eclipse.papyrus.infra.core.architecture.internal.operations.ArchitectureContextOperations;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Context</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.impl.ArchitectureContextImpl#getViewpoints <em>Viewpoints</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.impl.ArchitectureContextImpl#getDefaultViewpoints <em>Default Viewpoints</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.impl.ArchitectureContextImpl#getElementTypes <em>Element Types</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.impl.ArchitectureContextImpl#getDomain <em>Domain</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.impl.ArchitectureContextImpl#getExtensionPrefix <em>Extension Prefix</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.impl.ArchitectureContextImpl#getCreationCommandClass <em>Creation Command Class</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.impl.ArchitectureContextImpl#getConversionCommandClass <em>Conversion Command Class</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.impl.ArchitectureContextImpl#getGeneralContext <em>General Context</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.impl.ArchitectureContextImpl#getExtendedContexts <em>Extended Contexts</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.impl.ArchitectureContextImpl#isExtension <em>Extension</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ArchitectureContextImpl extends ADElementImpl implements ArchitectureContext {
	/**
	 * The cached value of the '{@link #getViewpoints() <em>Viewpoints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getViewpoints()
	 * @generated
	 * @ordered
	 */
	protected EList<ArchitectureViewpoint> viewpoints;

	/**
	 * The cached value of the '{@link #getDefaultViewpoints() <em>Default Viewpoints</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultViewpoints()
	 * @generated
	 * @ordered
	 */
	protected EList<ArchitectureViewpoint> defaultViewpoints;

	/**
	 * The cached value of the '{@link #getElementTypes() <em>Element Types</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<ElementTypeSetConfiguration> elementTypes;

	/**
	 * The default value of the '{@link #getExtensionPrefix() <em>Extension Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtensionPrefix()
	 * @generated
	 * @ordered
	 */
	protected static final String EXTENSION_PREFIX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExtensionPrefix() <em>Extension Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtensionPrefix()
	 * @generated
	 * @ordered
	 */
	protected String extensionPrefix = EXTENSION_PREFIX_EDEFAULT;

	/**
	 * The default value of the '{@link #getCreationCommandClass() <em>Creation Command Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreationCommandClass()
	 * @generated
	 * @ordered
	 */
	protected static final String CREATION_COMMAND_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCreationCommandClass() <em>Creation Command Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreationCommandClass()
	 * @generated
	 * @ordered
	 */
	protected String creationCommandClass = CREATION_COMMAND_CLASS_EDEFAULT;

	/**
	 * The default value of the '{@link #getConversionCommandClass() <em>Conversion Command Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConversionCommandClass()
	 * @generated
	 * @ordered
	 */
	protected static final String CONVERSION_COMMAND_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getConversionCommandClass() <em>Conversion Command Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConversionCommandClass()
	 * @generated
	 * @ordered
	 */
	protected String conversionCommandClass = CONVERSION_COMMAND_CLASS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getGeneralContext() <em>General Context</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGeneralContext()
	 * @generated
	 * @ordered
	 */
	protected ArchitectureContext generalContext;

	/**
	 * The cached value of the '{@link #getExtendedContexts() <em>Extended Contexts</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtendedContexts()
	 * @generated
	 * @ordered
	 */
	protected EList<ArchitectureContext> extendedContexts;

	/**
	 * The default value of the '{@link #isExtension() <em>Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isExtension()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EXTENSION_EDEFAULT = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArchitectureContextImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ArchitectureViewpoint> getViewpoints() {
		if (viewpoints == null) {
			viewpoints = new EObjectContainmentWithInverseEList<ArchitectureViewpoint>(ArchitectureViewpoint.class, this, ArchitecturePackage.ARCHITECTURE_CONTEXT__VIEWPOINTS, ArchitecturePackage.ARCHITECTURE_VIEWPOINT__CONTEXT);
		}
		return viewpoints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ArchitectureViewpoint> getDefaultViewpoints() {
		if (defaultViewpoints == null) {
			defaultViewpoints = new EObjectResolvingEList<ArchitectureViewpoint>(ArchitectureViewpoint.class, this, ArchitecturePackage.ARCHITECTURE_CONTEXT__DEFAULT_VIEWPOINTS);
		}
		return defaultViewpoints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ElementTypeSetConfiguration> getElementTypes() {
		if (elementTypes == null) {
			elementTypes = new EObjectResolvingEList<ElementTypeSetConfiguration>(ElementTypeSetConfiguration.class, this, ArchitecturePackage.ARCHITECTURE_CONTEXT__ELEMENT_TYPES);
		}
		return elementTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getExtensionPrefix() {
		return extensionPrefix;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setExtensionPrefix(String newExtensionPrefix) {
		String oldExtensionPrefix = extensionPrefix;
		extensionPrefix = newExtensionPrefix;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchitecturePackage.ARCHITECTURE_CONTEXT__EXTENSION_PREFIX, oldExtensionPrefix, extensionPrefix));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getCreationCommandClass() {
		return creationCommandClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCreationCommandClass(String newCreationCommandClass) {
		String oldCreationCommandClass = creationCommandClass;
		creationCommandClass = newCreationCommandClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchitecturePackage.ARCHITECTURE_CONTEXT__CREATION_COMMAND_CLASS, oldCreationCommandClass, creationCommandClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getConversionCommandClass() {
		return conversionCommandClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setConversionCommandClass(String newConversionCommandClass) {
		String oldConversionCommandClass = conversionCommandClass;
		conversionCommandClass = newConversionCommandClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchitecturePackage.ARCHITECTURE_CONTEXT__CONVERSION_COMMAND_CLASS, oldConversionCommandClass, conversionCommandClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ArchitectureContext getGeneralContext() {
		if (generalContext != null && generalContext.eIsProxy()) {
			InternalEObject oldGeneralContext = (InternalEObject)generalContext;
			generalContext = (ArchitectureContext)eResolveProxy(oldGeneralContext);
			if (generalContext != oldGeneralContext) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ArchitecturePackage.ARCHITECTURE_CONTEXT__GENERAL_CONTEXT, oldGeneralContext, generalContext));
			}
		}
		return generalContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArchitectureContext basicGetGeneralContext() {
		return generalContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGeneralContext(ArchitectureContext newGeneralContext) {
		ArchitectureContext oldGeneralContext = generalContext;
		generalContext = newGeneralContext;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchitecturePackage.ARCHITECTURE_CONTEXT__GENERAL_CONTEXT, oldGeneralContext, generalContext));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ArchitectureContext> getExtendedContexts() {
		if (extendedContexts == null) {
			extendedContexts = new EObjectResolvingEList<ArchitectureContext>(ArchitectureContext.class, this, ArchitecturePackage.ARCHITECTURE_CONTEXT__EXTENDED_CONTEXTS);
		}
		return extendedContexts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public boolean isExtension() {
		return ArchitectureContextOperations.isExtension(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean ceationCommandClassExists(DiagnosticChain diagnostics, Map<Object, Object> context) {
		return ArchitectureContextOperations.ceationCommandClassExists(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean conversionCommandClassExists(DiagnosticChain diagnostics, Map<Object, Object> context) {
		return ArchitectureContextOperations.conversionCommandClassExists(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public boolean isConsistentWith(ArchitectureContext context) {
		return ArchitectureContextOperations.isConsistentWith(this, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public boolean contextExtensionsAreConsistent(DiagnosticChain diagnostics, Map<Object, Object> context) {
		return ArchitectureContextOperations.contextExtensionsAreConsistent(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public boolean contextGeneralizationIsConsistent(DiagnosticChain diagnostics, Map<Object, Object> context) {
		return ArchitectureContextOperations.contextGeneralizationIsConsistent(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public boolean creationCommandClassRequired(DiagnosticChain diagnostics, Map<Object, Object> context) {
		return ArchitectureContextOperations.creationCommandClassRequired(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public EList<ArchitectureContext> allExtendedContexts() {
		return ArchitectureContextOperations.allExtendedContexts(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public EList<ArchitectureContext> allGeneralContexts() {
		return ArchitectureContextOperations.allGeneralContexts(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public boolean extensionCycle(DiagnosticChain diagnostics, Map<Object, Object> context) {
		return ArchitectureContextOperations.extensionCycle(this, diagnostics, context);
	}
					
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public boolean generalizationCycle(DiagnosticChain diagnostics, Map<Object, Object> context) {
		return ArchitectureContextOperations.generalizationCycle(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public boolean generalNotExtended(DiagnosticChain diagnostics, Map<Object, Object> context) {
		return ArchitectureContextOperations.generalNotExtended(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ArchitectureDomain getDomain() {
		if (eContainerFeatureID() != ArchitecturePackage.ARCHITECTURE_CONTEXT__DOMAIN) return null;
		return (ArchitectureDomain)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDomain(ArchitectureDomain newDomain, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newDomain, ArchitecturePackage.ARCHITECTURE_CONTEXT__DOMAIN, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDomain(ArchitectureDomain newDomain) {
		if (newDomain != eInternalContainer() || (eContainerFeatureID() != ArchitecturePackage.ARCHITECTURE_CONTEXT__DOMAIN && newDomain != null)) {
			if (EcoreUtil.isAncestor(this, newDomain))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newDomain != null)
				msgs = ((InternalEObject)newDomain).eInverseAdd(this, ArchitecturePackage.ARCHITECTURE_DOMAIN__CONTEXTS, ArchitectureDomain.class, msgs);
			msgs = basicSetDomain(newDomain, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchitecturePackage.ARCHITECTURE_CONTEXT__DOMAIN, newDomain, newDomain));
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
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__VIEWPOINTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getViewpoints()).basicAdd(otherEnd, msgs);
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__DOMAIN:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetDomain((ArchitectureDomain)otherEnd, msgs);
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
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__VIEWPOINTS:
				return ((InternalEList<?>)getViewpoints()).basicRemove(otherEnd, msgs);
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__DOMAIN:
				return basicSetDomain(null, msgs);
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
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__DOMAIN:
				return eInternalContainer().eInverseRemove(this, ArchitecturePackage.ARCHITECTURE_DOMAIN__CONTEXTS, ArchitectureDomain.class, msgs);
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
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__VIEWPOINTS:
				return getViewpoints();
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__DEFAULT_VIEWPOINTS:
				return getDefaultViewpoints();
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__ELEMENT_TYPES:
				return getElementTypes();
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__DOMAIN:
				return getDomain();
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__EXTENSION_PREFIX:
				return getExtensionPrefix();
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__CREATION_COMMAND_CLASS:
				return getCreationCommandClass();
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__CONVERSION_COMMAND_CLASS:
				return getConversionCommandClass();
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__GENERAL_CONTEXT:
				if (resolve) return getGeneralContext();
				return basicGetGeneralContext();
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__EXTENDED_CONTEXTS:
				return getExtendedContexts();
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__EXTENSION:
				return isExtension();
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
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__VIEWPOINTS:
				getViewpoints().clear();
				getViewpoints().addAll((Collection<? extends ArchitectureViewpoint>)newValue);
				return;
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__DEFAULT_VIEWPOINTS:
				getDefaultViewpoints().clear();
				getDefaultViewpoints().addAll((Collection<? extends ArchitectureViewpoint>)newValue);
				return;
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__ELEMENT_TYPES:
				getElementTypes().clear();
				getElementTypes().addAll((Collection<? extends ElementTypeSetConfiguration>)newValue);
				return;
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__DOMAIN:
				setDomain((ArchitectureDomain)newValue);
				return;
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__EXTENSION_PREFIX:
				setExtensionPrefix((String)newValue);
				return;
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__CREATION_COMMAND_CLASS:
				setCreationCommandClass((String)newValue);
				return;
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__CONVERSION_COMMAND_CLASS:
				setConversionCommandClass((String)newValue);
				return;
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__GENERAL_CONTEXT:
				setGeneralContext((ArchitectureContext)newValue);
				return;
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__EXTENDED_CONTEXTS:
				getExtendedContexts().clear();
				getExtendedContexts().addAll((Collection<? extends ArchitectureContext>)newValue);
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
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__VIEWPOINTS:
				getViewpoints().clear();
				return;
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__DEFAULT_VIEWPOINTS:
				getDefaultViewpoints().clear();
				return;
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__ELEMENT_TYPES:
				getElementTypes().clear();
				return;
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__DOMAIN:
				setDomain((ArchitectureDomain)null);
				return;
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__EXTENSION_PREFIX:
				setExtensionPrefix(EXTENSION_PREFIX_EDEFAULT);
				return;
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__CREATION_COMMAND_CLASS:
				setCreationCommandClass(CREATION_COMMAND_CLASS_EDEFAULT);
				return;
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__CONVERSION_COMMAND_CLASS:
				setConversionCommandClass(CONVERSION_COMMAND_CLASS_EDEFAULT);
				return;
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__GENERAL_CONTEXT:
				setGeneralContext((ArchitectureContext)null);
				return;
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__EXTENDED_CONTEXTS:
				getExtendedContexts().clear();
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
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__VIEWPOINTS:
				return viewpoints != null && !viewpoints.isEmpty();
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__DEFAULT_VIEWPOINTS:
				return defaultViewpoints != null && !defaultViewpoints.isEmpty();
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__ELEMENT_TYPES:
				return elementTypes != null && !elementTypes.isEmpty();
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__DOMAIN:
				return getDomain() != null;
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__EXTENSION_PREFIX:
				return EXTENSION_PREFIX_EDEFAULT == null ? extensionPrefix != null : !EXTENSION_PREFIX_EDEFAULT.equals(extensionPrefix);
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__CREATION_COMMAND_CLASS:
				return CREATION_COMMAND_CLASS_EDEFAULT == null ? creationCommandClass != null : !CREATION_COMMAND_CLASS_EDEFAULT.equals(creationCommandClass);
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__CONVERSION_COMMAND_CLASS:
				return CONVERSION_COMMAND_CLASS_EDEFAULT == null ? conversionCommandClass != null : !CONVERSION_COMMAND_CLASS_EDEFAULT.equals(conversionCommandClass);
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__GENERAL_CONTEXT:
				return generalContext != null;
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__EXTENDED_CONTEXTS:
				return extendedContexts != null && !extendedContexts.isEmpty();
			case ArchitecturePackage.ARCHITECTURE_CONTEXT__EXTENSION:
				return isExtension() != EXTENSION_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case ArchitecturePackage.ARCHITECTURE_CONTEXT___CEATION_COMMAND_CLASS_EXISTS__DIAGNOSTICCHAIN_MAP:
				return ceationCommandClassExists((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
			case ArchitecturePackage.ARCHITECTURE_CONTEXT___CONVERSION_COMMAND_CLASS_EXISTS__DIAGNOSTICCHAIN_MAP:
				return conversionCommandClassExists((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
			case ArchitecturePackage.ARCHITECTURE_CONTEXT___IS_CONSISTENT_WITH__ARCHITECTURECONTEXT:
				return isConsistentWith((ArchitectureContext)arguments.get(0));
			case ArchitecturePackage.ARCHITECTURE_CONTEXT___CONTEXT_EXTENSIONS_ARE_CONSISTENT__DIAGNOSTICCHAIN_MAP:
				return contextExtensionsAreConsistent((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
			case ArchitecturePackage.ARCHITECTURE_CONTEXT___CONTEXT_GENERALIZATION_IS_CONSISTENT__DIAGNOSTICCHAIN_MAP:
				return contextGeneralizationIsConsistent((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
			case ArchitecturePackage.ARCHITECTURE_CONTEXT___CREATION_COMMAND_CLASS_REQUIRED__DIAGNOSTICCHAIN_MAP:
				return creationCommandClassRequired((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
			case ArchitecturePackage.ARCHITECTURE_CONTEXT___ALL_EXTENDED_CONTEXTS:
				return allExtendedContexts();
			case ArchitecturePackage.ARCHITECTURE_CONTEXT___ALL_GENERAL_CONTEXTS:
				return allGeneralContexts();
			case ArchitecturePackage.ARCHITECTURE_CONTEXT___EXTENSION_CYCLE__DIAGNOSTICCHAIN_MAP:
				return extensionCycle((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
			case ArchitecturePackage.ARCHITECTURE_CONTEXT___GENERALIZATION_CYCLE__DIAGNOSTICCHAIN_MAP:
				return generalizationCycle((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
			case ArchitecturePackage.ARCHITECTURE_CONTEXT___GENERAL_NOT_EXTENDED__DIAGNOSTICCHAIN_MAP:
				return generalNotExtended((DiagnosticChain)arguments.get(0), (Map<Object, Object>)arguments.get(1));
		}
		return super.eInvoke(operationID, arguments);
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
		result.append(" (extensionPrefix: "); //$NON-NLS-1$
		result.append(extensionPrefix);
		result.append(", creationCommandClass: "); //$NON-NLS-1$
		result.append(creationCommandClass);
		result.append(", conversionCommandClass: "); //$NON-NLS-1$
		result.append(conversionCommandClass);
		result.append(')');
		return result.toString();
	}

} //ArchitectureContextImpl
