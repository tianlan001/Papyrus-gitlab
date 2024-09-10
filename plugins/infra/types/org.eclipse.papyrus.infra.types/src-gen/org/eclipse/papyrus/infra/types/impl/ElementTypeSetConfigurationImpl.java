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
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;
import org.eclipse.papyrus.infra.types.IdentifiedConfiguration;
import org.eclipse.papyrus.infra.types.NamedConfiguration;
import org.eclipse.papyrus.infra.types.operations.ElementTypeSetConfigurationOperations;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Element Type Set Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.types.impl.ElementTypeSetConfigurationImpl#getIdentifier <em>Identifier</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.types.impl.ElementTypeSetConfigurationImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.types.impl.ElementTypeSetConfigurationImpl#getAdviceBindingsConfigurations <em>Advice Bindings Configurations</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.types.impl.ElementTypeSetConfigurationImpl#getAllAdviceBindings <em>All Advice Bindings</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.types.impl.ElementTypeSetConfigurationImpl#getElementTypeConfigurations <em>Element Type Configurations</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.types.impl.ElementTypeSetConfigurationImpl#getMetamodelNsURI <em>Metamodel Ns URI</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ElementTypeSetConfigurationImpl extends ConfigurationElementImpl implements ElementTypeSetConfiguration {
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
	 * The cached value of the '{@link #getAdviceBindingsConfigurations() <em>Advice Bindings Configurations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdviceBindingsConfigurations()
	 * @generated
	 * @ordered
	 */
	protected EList<AbstractAdviceBindingConfiguration> adviceBindingsConfigurations;

	/**
	 * The cached value of the '{@link #getElementTypeConfigurations() <em>Element Type Configurations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementTypeConfigurations()
	 * @generated
	 * @ordered
	 */
	protected EList<ElementTypeConfiguration> elementTypeConfigurations;

	/**
	 * The default value of the '{@link #getMetamodelNsURI() <em>Metamodel Ns URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetamodelNsURI()
	 * @generated
	 * @ordered
	 */
	protected static final String METAMODEL_NS_URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMetamodelNsURI() <em>Metamodel Ns URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetamodelNsURI()
	 * @generated
	 * @ordered
	 */
	protected String metamodelNsURI = METAMODEL_NS_URI_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ElementTypeSetConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_SET_CONFIGURATION;
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
			eNotify(new ENotificationImpl(this, Notification.SET, ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__IDENTIFIER, oldIdentifier, identifier));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ElementTypeConfiguration> getElementTypeConfigurations() {
		if (elementTypeConfigurations == null) {
			elementTypeConfigurations = new EObjectContainmentWithInverseEList<ElementTypeConfiguration>(ElementTypeConfiguration.class, this, ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__ELEMENT_TYPE_CONFIGURATIONS, ElementTypesConfigurationsPackage.ELEMENT_TYPE_CONFIGURATION__OWNING_SET);
		}
		return elementTypeConfigurations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AbstractAdviceBindingConfiguration> getAdviceBindingsConfigurations() {
		if (adviceBindingsConfigurations == null) {
			adviceBindingsConfigurations = new EObjectContainmentWithInverseEList<AbstractAdviceBindingConfiguration>(AbstractAdviceBindingConfiguration.class, this, ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__ADVICE_BINDINGS_CONFIGURATIONS, ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__OWNING_SET);
		}
		return adviceBindingsConfigurations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AbstractAdviceBindingConfiguration> getAllAdviceBindings() {
		return ElementTypeSetConfigurationOperations.getAllAdviceBindings(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getMetamodelNsURI() {
		return metamodelNsURI;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMetamodelNsURI(String newMetamodelNsURI) {
		String oldMetamodelNsURI = metamodelNsURI;
		metamodelNsURI = newMetamodelNsURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__METAMODEL_NS_URI, oldMetamodelNsURI, metamodelNsURI));
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
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__ADVICE_BINDINGS_CONFIGURATIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getAdviceBindingsConfigurations()).basicAdd(otherEnd, msgs);
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__ELEMENT_TYPE_CONFIGURATIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getElementTypeConfigurations()).basicAdd(otherEnd, msgs);
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
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__ADVICE_BINDINGS_CONFIGURATIONS:
				return ((InternalEList<?>)getAdviceBindingsConfigurations()).basicRemove(otherEnd, msgs);
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__ELEMENT_TYPE_CONFIGURATIONS:
				return ((InternalEList<?>)getElementTypeConfigurations()).basicRemove(otherEnd, msgs);
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
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__IDENTIFIER:
				return getIdentifier();
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__NAME:
				return getName();
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__ADVICE_BINDINGS_CONFIGURATIONS:
				return getAdviceBindingsConfigurations();
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__ALL_ADVICE_BINDINGS:
				return getAllAdviceBindings();
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__ELEMENT_TYPE_CONFIGURATIONS:
				return getElementTypeConfigurations();
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__METAMODEL_NS_URI:
				return getMetamodelNsURI();
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
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__IDENTIFIER:
				setIdentifier((String)newValue);
				return;
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__NAME:
				setName((String)newValue);
				return;
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__ADVICE_BINDINGS_CONFIGURATIONS:
				getAdviceBindingsConfigurations().clear();
				getAdviceBindingsConfigurations().addAll((Collection<? extends AbstractAdviceBindingConfiguration>)newValue);
				return;
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__ELEMENT_TYPE_CONFIGURATIONS:
				getElementTypeConfigurations().clear();
				getElementTypeConfigurations().addAll((Collection<? extends ElementTypeConfiguration>)newValue);
				return;
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__METAMODEL_NS_URI:
				setMetamodelNsURI((String)newValue);
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
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__IDENTIFIER:
				setIdentifier(IDENTIFIER_EDEFAULT);
				return;
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__ADVICE_BINDINGS_CONFIGURATIONS:
				getAdviceBindingsConfigurations().clear();
				return;
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__ELEMENT_TYPE_CONFIGURATIONS:
				getElementTypeConfigurations().clear();
				return;
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__METAMODEL_NS_URI:
				setMetamodelNsURI(METAMODEL_NS_URI_EDEFAULT);
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
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__IDENTIFIER:
				return IDENTIFIER_EDEFAULT == null ? identifier != null : !IDENTIFIER_EDEFAULT.equals(identifier);
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__ADVICE_BINDINGS_CONFIGURATIONS:
				return adviceBindingsConfigurations != null && !adviceBindingsConfigurations.isEmpty();
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__ALL_ADVICE_BINDINGS:
				return !getAllAdviceBindings().isEmpty();
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__ELEMENT_TYPE_CONFIGURATIONS:
				return elementTypeConfigurations != null && !elementTypeConfigurations.isEmpty();
			case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__METAMODEL_NS_URI:
				return METAMODEL_NS_URI_EDEFAULT == null ? metamodelNsURI != null : !METAMODEL_NS_URI_EDEFAULT.equals(metamodelNsURI);
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
				case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__IDENTIFIER: return ElementTypesConfigurationsPackage.IDENTIFIED_CONFIGURATION__IDENTIFIER;
				default: return -1;
			}
		}
		if (baseClass == NamedConfiguration.class) {
			switch (derivedFeatureID) {
				case ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__NAME: return ElementTypesConfigurationsPackage.NAMED_CONFIGURATION__NAME;
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
				case ElementTypesConfigurationsPackage.IDENTIFIED_CONFIGURATION__IDENTIFIER: return ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__IDENTIFIER;
				default: return -1;
			}
		}
		if (baseClass == NamedConfiguration.class) {
			switch (baseFeatureID) {
				case ElementTypesConfigurationsPackage.NAMED_CONFIGURATION__NAME: return ElementTypesConfigurationsPackage.ELEMENT_TYPE_SET_CONFIGURATION__NAME;
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
		result.append(", metamodelNsURI: ");
		result.append(metamodelNsURI);
		result.append(')');
		return result.toString();
	}

} //ElementTypeSetConfigurationImpl
