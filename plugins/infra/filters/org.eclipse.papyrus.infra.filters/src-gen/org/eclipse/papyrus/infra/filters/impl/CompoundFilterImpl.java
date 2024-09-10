/**
 * Copyright (c) 2014, 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 */
package org.eclipse.papyrus.infra.filters.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrus.infra.filters.CompoundFilter;
import org.eclipse.papyrus.infra.filters.Filter;
import org.eclipse.papyrus.infra.filters.FiltersPackage;
import org.eclipse.papyrus.infra.filters.OperatorKind;
import org.eclipse.papyrus.infra.filters.internal.operations.CompoundFilterOperations;
import org.eclipse.uml2.common.util.SubsetSupersetEObjectContainmentEList;
import org.eclipse.uml2.common.util.SubsetSupersetEObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Composite Filter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.filters.impl.CompoundFilterImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.filters.impl.CompoundFilterImpl#getFilters <em>Filter</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.filters.impl.CompoundFilterImpl#getOwnedFilters <em>Owned Filter</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.filters.impl.CompoundFilterImpl#getOperator <em>Operator</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompoundFilterImpl extends MinimalEObjectImpl.Container implements CompoundFilter {
	/**
	 * A set of bit flags representing the values of boolean attributes and whether unsettable features have been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	protected int _flags = 0;

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
	 * The cached value of the '{@link #getFilters() <em>Filter</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getFilters()
	 * @generated
	 * @ordered
	 */
	protected EList<Filter> filters;

	/**
	 * The cached value of the '{@link #getOwnedFilters() <em>Owned Filter</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getOwnedFilters()
	 * @generated
	 * @ordered
	 */
	protected EList<Filter> ownedFilters;

	/**
	 * The default value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
	protected static final OperatorKind OPERATOR_EDEFAULT = OperatorKind.AND;

	/**
	 * The offset of the flags representing the value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	protected static final int OPERATOR_EFLAG_OFFSET = 0;

	/**
	 * The flags representing the default value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	protected static final int OPERATOR_EFLAG_DEFAULT = OPERATOR_EDEFAULT.ordinal() << OPERATOR_EFLAG_OFFSET;

	/**
	 * The array of enumeration values for '{@link OperatorKind Operator Kind}'
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	private static final OperatorKind[] OPERATOR_EFLAG_VALUES = OperatorKind.values();

	/**
	 * The flags representing the value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
	protected static final int OPERATOR_EFLAG = 0x3 << OPERATOR_EFLAG_OFFSET;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected CompoundFilterImpl() {
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
		return FiltersPackage.Literals.COMPOUND_FILTER;
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
		newName = newName == null ? NAME_EDEFAULT : newName;
		String oldName = name;
		name = newName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, FiltersPackage.COMPOUND_FILTER__NAME, oldName, name));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<Filter> getFilters() {
		if (filters == null) {
			filters = new SubsetSupersetEObjectResolvingEList<>(Filter.class, this, FiltersPackage.COMPOUND_FILTER__FILTER, null, FILTER_ESUBSETS);
		}
		return filters;
	}

	/**
	 * The array of subset feature identifiers for the '{@link #getFilters() <em>Filter</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getFilters()
	 * @generated
	 * @ordered
	 */
	protected static final int[] FILTER_ESUBSETS = new int[] { FiltersPackage.COMPOUND_FILTER__OWNED_FILTER };

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Filter getFilter(String name) {
		return getFilter(name, false, null);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Filter getFilter(String name, boolean ignoreCase, EClass eClass) {
		filterLoop: for (Filter filter : getFilters()) {
			if (eClass != null && !eClass.isInstance(filter)) {
				continue filterLoop;
			}
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(filter.getName()) : name.equals(filter.getName()))) {
				continue filterLoop;
			}
			return filter;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<Filter> getOwnedFilters() {
		if (ownedFilters == null) {
			ownedFilters = new SubsetSupersetEObjectContainmentEList<>(Filter.class, this, FiltersPackage.COMPOUND_FILTER__OWNED_FILTER, OWNED_FILTER_ESUPERSETS, null);
		}
		return ownedFilters;
	}

	/**
	 * The array of superset feature identifiers for the '{@link #getOwnedFilters() <em>Owned Filter</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getOwnedFilters()
	 * @generated
	 * @ordered
	 */
	protected static final int[] OWNED_FILTER_ESUPERSETS = new int[] { FiltersPackage.COMPOUND_FILTER__FILTER };

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Filter createOwnedFilter(String name, EClass eClass) {
		Filter newOwnedFilter = (Filter) create(eClass);
		getOwnedFilters().add(newOwnedFilter);
		if (name != null) {
			newOwnedFilter.setName(name);
		}
		return newOwnedFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Filter getOwnedFilter(String name) {
		return getOwnedFilter(name, false, null, false);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Filter getOwnedFilter(String name, boolean ignoreCase, EClass eClass, boolean createOnDemand) {
		ownedFilterLoop: for (Filter ownedFilter : getOwnedFilters()) {
			if (eClass != null && !eClass.isInstance(ownedFilter)) {
				continue ownedFilterLoop;
			}
			if (name != null && !(ignoreCase ? name.equalsIgnoreCase(ownedFilter.getName()) : name.equals(ownedFilter.getName()))) {
				continue ownedFilterLoop;
			}
			return ownedFilter;
		}
		return createOnDemand && eClass != null ? createOwnedFilter(name, eClass) : null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public OperatorKind getOperator() {
		return OPERATOR_EFLAG_VALUES[(_flags & OPERATOR_EFLAG) >>> OPERATOR_EFLAG_OFFSET];
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setOperator(OperatorKind newOperator) {
		OperatorKind oldOperator = OPERATOR_EFLAG_VALUES[(_flags & OPERATOR_EFLAG) >>> OPERATOR_EFLAG_OFFSET];
		if (newOperator == null) {
			newOperator = OPERATOR_EDEFAULT;
		}
		_flags = _flags & ~OPERATOR_EFLAG | newOperator.ordinal() << OPERATOR_EFLAG_OFFSET;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, FiltersPackage.COMPOUND_FILTER__OPERATOR, oldOperator, newOperator));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean matches(Object input) {
		return CompoundFilterOperations.matches(this, input);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean validateAcyclic(DiagnosticChain diagnostics, Map<Object, Object> context) {
		return CompoundFilterOperations.validateAcyclic(this, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case FiltersPackage.COMPOUND_FILTER__OWNED_FILTER:
			return ((InternalEList<?>) getOwnedFilters()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
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
		case FiltersPackage.COMPOUND_FILTER__NAME:
			return getName();
		case FiltersPackage.COMPOUND_FILTER__FILTER:
			return getFilters();
		case FiltersPackage.COMPOUND_FILTER__OWNED_FILTER:
			return getOwnedFilters();
		case FiltersPackage.COMPOUND_FILTER__OPERATOR:
			return getOperator();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case FiltersPackage.COMPOUND_FILTER__NAME:
			setName((String) newValue);
			return;
		case FiltersPackage.COMPOUND_FILTER__FILTER:
			getFilters().clear();
			getFilters().addAll((Collection<? extends Filter>) newValue);
			return;
		case FiltersPackage.COMPOUND_FILTER__OWNED_FILTER:
			getOwnedFilters().clear();
			getOwnedFilters().addAll((Collection<? extends Filter>) newValue);
			return;
		case FiltersPackage.COMPOUND_FILTER__OPERATOR:
			setOperator((OperatorKind) newValue);
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
		case FiltersPackage.COMPOUND_FILTER__NAME:
			setName(NAME_EDEFAULT);
			return;
		case FiltersPackage.COMPOUND_FILTER__FILTER:
			getFilters().clear();
			return;
		case FiltersPackage.COMPOUND_FILTER__OWNED_FILTER:
			getOwnedFilters().clear();
			return;
		case FiltersPackage.COMPOUND_FILTER__OPERATOR:
			setOperator(OPERATOR_EDEFAULT);
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
		case FiltersPackage.COMPOUND_FILTER__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case FiltersPackage.COMPOUND_FILTER__FILTER:
			return filters != null && !filters.isEmpty();
		case FiltersPackage.COMPOUND_FILTER__OWNED_FILTER:
			return ownedFilters != null && !ownedFilters.isEmpty();
		case FiltersPackage.COMPOUND_FILTER__OPERATOR:
			return (_flags & OPERATOR_EFLAG) != OPERATOR_EFLAG_DEFAULT;
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
	@SuppressWarnings("unchecked")
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
		case FiltersPackage.COMPOUND_FILTER___MATCHES__OBJECT:
			return matches(arguments.get(0));
		case FiltersPackage.COMPOUND_FILTER___VALIDATE_ACYCLIC__DIAGNOSTICCHAIN_MAP:
			return validateAcyclic((DiagnosticChain) arguments.get(0), (Map<Object, Object>) arguments.get(1));
		}
		return super.eInvoke(operationID, arguments);
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
		result.append(", operator: "); //$NON-NLS-1$
		result.append(OPERATOR_EFLAG_VALUES[(_flags & OPERATOR_EFLAG) >>> OPERATOR_EFLAG_OFFSET]);
		result.append(')');
		return result.toString();
	}

	/**
	 * Creates a new instance of the specified Ecore class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param eClass
	 *                   The Ecore class of the instance to create.
	 * @return The new instance.
	 * @generated
	 */
	protected EObject create(EClass eClass) {
		return EcoreUtil.create(eClass);
	}

} // CompoundFilterImpl
