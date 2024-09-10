/**
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
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
package org.eclipse.papyrus.infra.emf.types.constraints.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.emf.types.constraints.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class ConstraintAdviceFactoryImpl extends EFactoryImpl implements ConstraintAdviceFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static ConstraintAdviceFactory init() {
		try {
			ConstraintAdviceFactory theConstraintAdviceFactory = (ConstraintAdviceFactory) EPackage.Registry.INSTANCE.getEFactory(ConstraintAdvicePackage.eNS_URI);
			if (theConstraintAdviceFactory != null) {
				return theConstraintAdviceFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ConstraintAdviceFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public ConstraintAdviceFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case ConstraintAdvicePackage.CONSTRAINT_ADVICE_CONFIGURATION:
			return createConstraintAdviceConfiguration();
		case ConstraintAdvicePackage.COMPOSITE_CONSTRAINT:
			return createCompositeConstraint();
		case ConstraintAdvicePackage.REFERENCE_CONSTRAINT:
			return createReferenceConstraint();
		case ConstraintAdvicePackage.ANY_REFERENCE:
			return createAnyReference();
		case ConstraintAdvicePackage.REFERENCE:
			return createReference();
		case ConstraintAdvicePackage.ELEMENT_TYPE_FILTER:
			return createElementTypeFilter();
		case ConstraintAdvicePackage.RELATIONSHIP_CONSTRAINT:
			return createRelationshipConstraint();
		case ConstraintAdvicePackage.END_PERMISSION:
			return createEndPermission();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
		case ConstraintAdvicePackage.REFERENCE_KIND:
			return createReferenceKindFromString(eDataType, initialValue);
		case ConstraintAdvicePackage.ELEMENT_TYPE_RELATIONSHIP_KIND:
			return createElementTypeRelationshipKindFromString(eDataType, initialValue);
		case ConstraintAdvicePackage.END_KIND:
			return createEndKindFromString(eDataType, initialValue);
		case ConstraintAdvicePackage.EDIT_COMMAND_REQUEST:
			return createEditCommandRequestFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
		case ConstraintAdvicePackage.REFERENCE_KIND:
			return convertReferenceKindToString(eDataType, instanceValue);
		case ConstraintAdvicePackage.ELEMENT_TYPE_RELATIONSHIP_KIND:
			return convertElementTypeRelationshipKindToString(eDataType, instanceValue);
		case ConstraintAdvicePackage.END_KIND:
			return convertEndKindToString(eDataType, instanceValue);
		case ConstraintAdvicePackage.EDIT_COMMAND_REQUEST:
			return convertEditCommandRequestToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ConstraintAdviceConfiguration createConstraintAdviceConfiguration() {
		ConstraintAdviceConfigurationImpl constraintAdviceConfiguration = new ConstraintAdviceConfigurationImpl();
		return constraintAdviceConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ReferenceConstraint createReferenceConstraint() {
		ReferenceConstraintImpl referenceConstraint = new ReferenceConstraintImpl();
		return referenceConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public AnyReference createAnyReference() {
		AnyReferenceImpl anyReference = new AnyReferenceImpl();
		return anyReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Reference createReference() {
		ReferenceImpl reference = new ReferenceImpl();
		return reference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ElementTypeFilter createElementTypeFilter() {
		ElementTypeFilterImpl elementTypeFilter = new ElementTypeFilterImpl();
		return elementTypeFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public RelationshipConstraint createRelationshipConstraint() {
		RelationshipConstraintImpl relationshipConstraint = new RelationshipConstraintImpl();
		return relationshipConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EndPermission createEndPermission() {
		EndPermissionImpl endPermission = new EndPermissionImpl();
		return endPermission;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public CompositeConstraint createCompositeConstraint() {
		CompositeConstraintImpl compositeConstraint = new CompositeConstraintImpl();
		return compositeConstraint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public ReferenceKind createReferenceKindFromString(EDataType eDataType, String initialValue) {
		ReferenceKind result = ReferenceKind.get(initialValue);
		if (result == null) {
			throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertReferenceKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public ElementTypeRelationshipKind createElementTypeRelationshipKindFromString(EDataType eDataType, String initialValue) {
		ElementTypeRelationshipKind result = ElementTypeRelationshipKind.get(initialValue);
		if (result == null) {
			throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertElementTypeRelationshipKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public EndKind createEndKindFromString(EDataType eDataType, String initialValue) {
		EndKind result = EndKind.get(initialValue);
		if (result == null) {
			throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		}
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertEndKindToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public IEditCommandRequest createEditCommandRequestFromString(EDataType eDataType, String initialValue) {
		return (IEditCommandRequest) super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public String convertEditCommandRequestToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ConstraintAdvicePackage getConstraintAdvicePackage() {
		return (ConstraintAdvicePackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ConstraintAdvicePackage getPackage() {
		return ConstraintAdvicePackage.eINSTANCE;
	}

} // ConstraintAdviceFactoryImpl
