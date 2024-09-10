/**
 * Copyright (c) 2014, 2020 CEA LIST, Christian W. Damus, and others.
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
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bugs 568782, 568853
 */
package org.eclipse.papyrus.infra.types.impl;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration;
import org.eclipse.papyrus.infra.types.ConfigurationElement;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;
import org.eclipse.papyrus.infra.types.IconEntry;
import org.eclipse.papyrus.infra.types.IdentifiedConfiguration;
import org.eclipse.papyrus.infra.types.NamedConfiguration;
import org.eclipse.uml2.common.util.DerivedSubsetEObjectEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Element Type Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.types.impl.ElementTypeConfigurationImpl#getIdentifier <em>Identifier</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.types.impl.ElementTypeConfigurationImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.types.impl.ElementTypeConfigurationImpl#getOwnedConfigurations <em>Owned Configurations</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.types.impl.ElementTypeConfigurationImpl#getHint <em>Hint</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.types.impl.ElementTypeConfigurationImpl#getKind <em>Kind</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.types.impl.ElementTypeConfigurationImpl#getIconEntry <em>Icon Entry</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.types.impl.ElementTypeConfigurationImpl#getOwnedAdvice <em>Owned Advice</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.types.impl.ElementTypeConfigurationImpl#getOwningSet <em>Owning Set</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ElementTypeConfigurationImpl extends ConfigurationElementImpl implements ElementTypeConfiguration {
	/**
	 * The default value of the '{@link #getIdentifier() <em>Identifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdentifier()
	 * @generated
	 * @ordered
	 */
	protected static final String IDENTIFIER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIdentifier() <em>Identifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdentifier()
	 * @generated
	 * @ordered
	 */
	protected String identifier = IDENTIFIER_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOwnedConfigurations() <em>Owned Configurations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedConfigurations()
	 * @generated
	 * @ordered
	 */
	protected EList<ConfigurationElement> ownedConfigurations;

	/**
	 * The default value of the '{@link #getHint() <em>Hint</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHint()
	 * @generated
	 * @ordered
	 */
	protected static final String HINT_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getHint() <em>Hint</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHint()
	 * @generated
	 * @ordered
	 */
	protected String hint = HINT_EDEFAULT;

	/**
	 * The default value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected static final String KIND_EDEFAULT = "org.eclipse.gmf.runtime.emf.type.core.IHintedType";

	/**
	 * The cached value of the '{@link #getKind() <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKind()
	 * @generated
	 * @ordered
	 */
	protected String kind = KIND_EDEFAULT;

	/**
	 * The cached value of the '{@link #getIconEntry() <em>Icon Entry</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIconEntry()
	 * @generated
	 * @ordered
	 */
	protected IconEntry iconEntry;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ElementTypeConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIdentifier(String newIdentifier) {
		String oldIdentifier = identifier;
		identifier = newIdentifier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__IDENTIFIER, oldIdentifier, identifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ConfigurationElement> getOwnedConfigurations() {
		if (ownedConfigurations == null) {
			ownedConfigurations = new EObjectContainmentWithInverseEList<ConfigurationElement>(ConfigurationElement.class, this, ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS, ElementTypesConfigurationsPackage.CONFIGURATION_ELEMENT__OWNING_TYPE);
		}
		return ownedConfigurations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getHint() {
		return hint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHint(String newHint) {
		String oldHint = hint;
		hint = newHint;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__HINT, oldHint, hint));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getKind() {
		return kind;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setKind(String newKind) {
		String oldKind = kind;
		kind = newKind;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__KIND, oldKind, kind));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IconEntry getIconEntry() {
		return iconEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIconEntry(IconEntry newIconEntry, NotificationChain msgs) {
		IconEntry oldIconEntry = iconEntry;
		iconEntry = newIconEntry;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__ICON_ENTRY, oldIconEntry, newIconEntry);
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
	public void setIconEntry(IconEntry newIconEntry) {
		if (newIconEntry != iconEntry) {
			NotificationChain msgs = null;
			if (iconEntry != null)
				msgs = ((InternalEObject)iconEntry).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__ICON_ENTRY, null, msgs);
			if (newIconEntry != null)
				msgs = ((InternalEObject)newIconEntry).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__ICON_ENTRY, null, msgs);
			msgs = basicSetIconEntry(newIconEntry, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__ICON_ENTRY, newIconEntry, newIconEntry));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public EList<AbstractAdviceBindingConfiguration> getOwnedAdvice() {
		return new DerivedSubsetEObjectEList<AbstractAdviceBindingConfiguration>(AbstractAdviceBindingConfiguration.class, this,
			ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS, OWNED_ADVICE_ESUPERSETS);
	}

	/**
	 * The array of superset feature identifiers for the '{@link #getOwnedAdvice() <em>Owned Advice</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedAdvice()
	 * @generated
	 * @ordered
	 */
	protected static final int[] OWNED_ADVICE_ESUPERSETS = new int[] {ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS};

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ElementTypeSetConfiguration getOwningSet() {
		if (eContainerFeatureID() != ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNING_SET) return null;
		return (ElementTypeSetConfiguration)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOwningSet(ElementTypeSetConfiguration newOwningSet, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newOwningSet, ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNING_SET, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOwningSet(ElementTypeSetConfiguration newOwningSet) {
		if (newOwningSet != eInternalContainer() || (eContainerFeatureID() != ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNING_SET && newOwningSet != null)) {
			if (EcoreUtil.isAncestor(this, newOwningSet))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newOwningSet != null)
				msgs = ((InternalEObject)newOwningSet).eInverseAdd(this, ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__ELEMENT_TYPE_CONFIGURATIONS, ElementTypeSetConfiguration.class, msgs);
			msgs = basicSetOwningSet(newOwningSet, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNING_SET, newOwningSet, newOwningSet));
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
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOwnedConfigurations()).basicAdd(otherEnd, msgs);
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNING_SET:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetOwningSet((ElementTypeSetConfiguration)otherEnd, msgs);
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
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS:
				return ((InternalEList<?>)getOwnedConfigurations()).basicRemove(otherEnd, msgs);
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__ICON_ENTRY:
				return basicSetIconEntry(null, msgs);
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNING_SET:
				return basicSetOwningSet(null, msgs);
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
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNING_SET:
				return eInternalContainer().eInverseRemove(this, ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__ELEMENT_TYPE_CONFIGURATIONS, ElementTypeSetConfiguration.class, msgs);
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
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__IDENTIFIER:
				return getIdentifier();
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__NAME:
				return getName();
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS:
				return getOwnedConfigurations();
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__HINT:
				return getHint();
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__KIND:
				return getKind();
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__ICON_ENTRY:
				return getIconEntry();
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNED_ADVICE:
				return getOwnedAdvice();
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNING_SET:
				return getOwningSet();
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
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__IDENTIFIER:
				setIdentifier((String)newValue);
				return;
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__NAME:
				setName((String)newValue);
				return;
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS:
				getOwnedConfigurations().clear();
				getOwnedConfigurations().addAll((Collection<? extends ConfigurationElement>)newValue);
				return;
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__HINT:
				setHint((String)newValue);
				return;
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__KIND:
				setKind((String)newValue);
				return;
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__ICON_ENTRY:
				setIconEntry((IconEntry)newValue);
				return;
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNED_ADVICE:
				getOwnedAdvice().clear();
				getOwnedAdvice().addAll((Collection<? extends AbstractAdviceBindingConfiguration>)newValue);
				return;
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNING_SET:
				setOwningSet((ElementTypeSetConfiguration)newValue);
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
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__IDENTIFIER:
				setIdentifier(IDENTIFIER_EDEFAULT);
				return;
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS:
				getOwnedConfigurations().clear();
				return;
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__HINT:
				setHint(HINT_EDEFAULT);
				return;
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__KIND:
				setKind(KIND_EDEFAULT);
				return;
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__ICON_ENTRY:
				setIconEntry((IconEntry)null);
				return;
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNED_ADVICE:
				getOwnedAdvice().clear();
				return;
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNING_SET:
				setOwningSet((ElementTypeSetConfiguration)null);
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
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__IDENTIFIER:
				return IDENTIFIER_EDEFAULT == null ? identifier != null : !IDENTIFIER_EDEFAULT.equals(identifier);
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS:
				return ownedConfigurations != null && !ownedConfigurations.isEmpty();
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__HINT:
				return HINT_EDEFAULT == null ? hint != null : !HINT_EDEFAULT.equals(hint);
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__KIND:
				return KIND_EDEFAULT == null ? kind != null : !KIND_EDEFAULT.equals(kind);
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__ICON_ENTRY:
				return iconEntry != null;
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNED_ADVICE:
				return !getOwnedAdvice().isEmpty();
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNING_SET:
				return getOwningSet() != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == IdentifiedConfiguration.class) {
			switch (derivedFeatureID) {
				case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__IDENTIFIER: return ElementTypesConfigurationsPackage.IDENTIFIED_CONFIGURATION__IDENTIFIER;
				default: return -1;
			}
		}
		if (baseClass == NamedConfiguration.class) {
			switch (derivedFeatureID) {
				case ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__NAME: return ElementTypesConfigurationsPackage.NAMED_CONFIGURATION__NAME;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == IdentifiedConfiguration.class) {
			switch (baseFeatureID) {
				case ElementTypesConfigurationsPackage.IDENTIFIED_CONFIGURATION__IDENTIFIER: return ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__IDENTIFIER;
				default: return -1;
			}
		}
		if (baseClass == NamedConfiguration.class) {
			switch (baseFeatureID) {
				case ElementTypesConfigurationsPackage.NAMED_CONFIGURATION__NAME: return ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__NAME;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (identifier: ");
		result.append(identifier);
		result.append(", name: ");
		result.append(name);
		result.append(", hint: ");
		result.append(hint);
		result.append(", kind: ");
		result.append(kind);
		result.append(')');
		return result.toString();
	}

} //ElementTypeConfigurationImpl
