/**
 * Copyright (c) 2013 CEA LIST.
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
 * 	Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 */
package org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.NattablewrapperFactory
 * @model kind="package"
 * @generated
 */
public interface NattablewrapperPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "nattablewrapper"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/nattable/model/table/nattablewrapper"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "nattablewrapper"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	NattablewrapperPackage eINSTANCE = org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.impl.NattablewrapperPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.IWrapper <em>IWrapper</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.IWrapper
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.impl.NattablewrapperPackageImpl#getIWrapper()
	 * @generated
	 */
	int IWRAPPER = 0;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IWRAPPER__EANNOTATIONS = NattablestylePackage.STYLED_ELEMENT__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Styles</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IWRAPPER__STYLES = NattablestylePackage.STYLED_ELEMENT__STYLES;

	/**
	 * The number of structural features of the '<em>IWrapper</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IWRAPPER_FEATURE_COUNT = NattablestylePackage.STYLED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get EAnnotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IWRAPPER___GET_EANNOTATION__STRING = NattablestylePackage.STYLED_ELEMENT___GET_EANNOTATION__STRING;

	/**
	 * The operation id for the '<em>Get Named Style</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IWRAPPER___GET_NAMED_STYLE__ECLASS_STRING = NattablestylePackage.STYLED_ELEMENT___GET_NAMED_STYLE__ECLASS_STRING;

	/**
	 * The operation id for the '<em>Get Style</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IWRAPPER___GET_STYLE__ECLASS = NattablestylePackage.STYLED_ELEMENT___GET_STYLE__ECLASS;

	/**
	 * The operation id for the '<em>Create Style</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IWRAPPER___CREATE_STYLE__ECLASS = NattablestylePackage.STYLED_ELEMENT___CREATE_STYLE__ECLASS;

	/**
	 * The operation id for the '<em>Get Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IWRAPPER___GET_ELEMENT = NattablestylePackage.STYLED_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>IWrapper</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IWRAPPER_OPERATION_COUNT = NattablestylePackage.STYLED_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.impl.EObjectWrapperImpl <em>EObject Wrapper</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.impl.EObjectWrapperImpl
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.impl.NattablewrapperPackageImpl#getEObjectWrapper()
	 * @generated
	 */
	int EOBJECT_WRAPPER = 1;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_WRAPPER__EANNOTATIONS = IWRAPPER__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Styles</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_WRAPPER__STYLES = IWRAPPER__STYLES;

	/**
	 * The feature id for the '<em><b>Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_WRAPPER__ELEMENT = IWRAPPER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>EObject Wrapper</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_WRAPPER_FEATURE_COUNT = IWRAPPER_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get EAnnotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_WRAPPER___GET_EANNOTATION__STRING = IWRAPPER___GET_EANNOTATION__STRING;

	/**
	 * The operation id for the '<em>Get Named Style</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_WRAPPER___GET_NAMED_STYLE__ECLASS_STRING = IWRAPPER___GET_NAMED_STYLE__ECLASS_STRING;

	/**
	 * The operation id for the '<em>Get Style</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_WRAPPER___GET_STYLE__ECLASS = IWRAPPER___GET_STYLE__ECLASS;

	/**
	 * The operation id for the '<em>Create Style</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_WRAPPER___CREATE_STYLE__ECLASS = IWRAPPER___CREATE_STYLE__ECLASS;

	/**
	 * The operation id for the '<em>Get Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_WRAPPER___GET_ELEMENT = IWRAPPER___GET_ELEMENT;

	/**
	 * The number of operations of the '<em>EObject Wrapper</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EOBJECT_WRAPPER_OPERATION_COUNT = IWRAPPER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.impl.IdWrapperImpl <em>Id Wrapper</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.impl.IdWrapperImpl
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.impl.NattablewrapperPackageImpl#getIdWrapper()
	 * @generated
	 */
	int ID_WRAPPER = 2;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID_WRAPPER__EANNOTATIONS = IWRAPPER__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Styles</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID_WRAPPER__STYLES = IWRAPPER__STYLES;

	/**
	 * The feature id for the '<em><b>Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID_WRAPPER__ELEMENT = IWRAPPER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Id Wrapper</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID_WRAPPER_FEATURE_COUNT = IWRAPPER_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get EAnnotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID_WRAPPER___GET_EANNOTATION__STRING = IWRAPPER___GET_EANNOTATION__STRING;

	/**
	 * The operation id for the '<em>Get Named Style</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID_WRAPPER___GET_NAMED_STYLE__ECLASS_STRING = IWRAPPER___GET_NAMED_STYLE__ECLASS_STRING;

	/**
	 * The operation id for the '<em>Get Style</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID_WRAPPER___GET_STYLE__ECLASS = IWRAPPER___GET_STYLE__ECLASS;

	/**
	 * The operation id for the '<em>Create Style</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID_WRAPPER___CREATE_STYLE__ECLASS = IWRAPPER___CREATE_STYLE__ECLASS;

	/**
	 * The operation id for the '<em>Get Element</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID_WRAPPER___GET_ELEMENT = IWRAPPER___GET_ELEMENT;

	/**
	 * The number of operations of the '<em>Id Wrapper</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ID_WRAPPER_OPERATION_COUNT = IWRAPPER_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.IWrapper <em>IWrapper</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IWrapper</em>'.
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.IWrapper
	 * @generated
	 */
	EClass getIWrapper();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.IWrapper#getElement() <em>Get Element</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Element</em>' operation.
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.IWrapper#getElement()
	 * @generated
	 */
	EOperation getIWrapper__GetElement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.EObjectWrapper <em>EObject Wrapper</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EObject Wrapper</em>'.
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.EObjectWrapper
	 * @generated
	 */
	EClass getEObjectWrapper();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.EObjectWrapper#getElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Element</em>'.
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.EObjectWrapper#getElement()
	 * @see #getEObjectWrapper()
	 * @generated
	 */
	EReference getEObjectWrapper_Element();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.IdWrapper <em>Id Wrapper</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Id Wrapper</em>'.
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.IdWrapper
	 * @generated
	 */
	EClass getIdWrapper();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.IdWrapper#getElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Element</em>'.
	 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.IdWrapper#getElement()
	 * @see #getIdWrapper()
	 * @generated
	 */
	EAttribute getIdWrapper_Element();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	NattablewrapperFactory getNattablewrapperFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.IWrapper <em>IWrapper</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.IWrapper
		 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.impl.NattablewrapperPackageImpl#getIWrapper()
		 * @generated
		 */
		EClass IWRAPPER = eINSTANCE.getIWrapper();

		/**
		 * The meta object literal for the '<em><b>Get Element</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation IWRAPPER___GET_ELEMENT = eINSTANCE.getIWrapper__GetElement();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.impl.EObjectWrapperImpl <em>EObject Wrapper</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.impl.EObjectWrapperImpl
		 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.impl.NattablewrapperPackageImpl#getEObjectWrapper()
		 * @generated
		 */
		EClass EOBJECT_WRAPPER = eINSTANCE.getEObjectWrapper();

		/**
		 * The meta object literal for the '<em><b>Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EOBJECT_WRAPPER__ELEMENT = eINSTANCE.getEObjectWrapper_Element();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.impl.IdWrapperImpl <em>Id Wrapper</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.impl.IdWrapperImpl
		 * @see org.eclipse.papyrus.infra.nattable.model.nattable.nattablewrapper.impl.NattablewrapperPackageImpl#getIdWrapper()
		 * @generated
		 */
		EClass ID_WRAPPER = eINSTANCE.getIdWrapper();

		/**
		 * The meta object literal for the '<em><b>Element</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ID_WRAPPER__ELEMENT = eINSTANCE.getIdWrapper_Element();

	}

} //NattablewrapperPackage
