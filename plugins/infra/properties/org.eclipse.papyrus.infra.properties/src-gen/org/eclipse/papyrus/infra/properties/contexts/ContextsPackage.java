/*****************************************************************************
 * Copyright (c) 2011, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - add prototype reference to Context (CDO)
 *   Christian W. Damus - bugs 482927, 573986
 *   Vincent Lorenzo - Bug 520271
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.contexts;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.papyrus.infra.constraints.ConstraintsPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.infra.properties.contexts.ContextsFactory
 * @model kind="package"
 * @generated
 */
public interface ContextsPackage extends EPackage {

	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "contexts";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/properties/contexts/0.9";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "contexts";

	/**
	 * The package content type ID.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eCONTENT_TYPE = "org.eclipse.papyrus.infra.properties.context"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ContextsPackage eINSTANCE = org.eclipse.papyrus.infra.properties.contexts.impl.ContextsPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.properties.contexts.impl.ContextImpl <em>Context</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.properties.contexts.impl.ContextImpl
	 * @see org.eclipse.papyrus.infra.properties.contexts.impl.ContextsPackageImpl#getContext()
	 * @generated
	 */
	int CONTEXT = 0;

	/**
	 * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT__EANNOTATIONS = EcorePackage.EMODEL_ELEMENT__EANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT__NAME = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Dependencies</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT__DEPENDENCIES = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Tabs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT__TABS = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Views</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT__VIEWS = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Data Contexts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT__DATA_CONTEXTS = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Prototype</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT__PROTOTYPE = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT__LABEL = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>User Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT__USER_LABEL = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Context</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_FEATURE_COUNT = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The operation id for the '<em>Get EAnnotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT___GET_EANNOTATION__STRING = EcorePackage.EMODEL_ELEMENT___GET_EANNOTATION__STRING;

	/**
	 * The number of operations of the '<em>Context</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTEXT_OPERATION_COUNT = EcorePackage.EMODEL_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.properties.contexts.impl.TabImpl <em>Tab</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.properties.contexts.impl.TabImpl
	 * @see org.eclipse.papyrus.infra.properties.contexts.impl.ContextsPackageImpl#getTab()
	 * @generated
	 */
	int TAB = 1;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAB__LABEL = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAB__ID = 1;

	/**
	 * The feature id for the '<em><b>Category</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAB__CATEGORY = 2;

	/**
	 * The feature id for the '<em><b>Image</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAB__IMAGE = 3;

	/**
	 * The feature id for the '<em><b>After Tab</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAB__AFTER_TAB = 4;

	/**
	 * The feature id for the '<em><b>Sections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAB__SECTIONS = 5;

	/**
	 * The feature id for the '<em><b>All Sections</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAB__ALL_SECTIONS = 6;

	/**
	 * The feature id for the '<em><b>Indented</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAB__INDENTED = 7;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAB__PRIORITY = 8;

	/**
	 * The number of structural features of the '<em>Tab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAB_FEATURE_COUNT = 9;

	/**
	 * The number of operations of the '<em>Tab</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TAB_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.properties.contexts.impl.ViewImpl <em>View</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.properties.contexts.impl.ViewImpl
	 * @see org.eclipse.papyrus.infra.properties.contexts.impl.ContextsPackageImpl#getView()
	 * @generated
	 */
	int VIEW = 10;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.properties.contexts.impl.AbstractSectionImpl <em>Abstract Section</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.properties.contexts.impl.AbstractSectionImpl
	 * @see org.eclipse.papyrus.infra.properties.contexts.impl.ContextsPackageImpl#getAbstractSection()
	 * @generated
	 */
	int ABSTRACT_SECTION = 3;

	/**
	 * The feature id for the '<em><b>Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SECTION__CONSTRAINTS = ConstraintsPackage.DISPLAY_UNIT__CONSTRAINTS;

	/**
	 * The feature id for the '<em><b>Element Multiplicity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SECTION__ELEMENT_MULTIPLICITY = ConstraintsPackage.DISPLAY_UNIT__ELEMENT_MULTIPLICITY;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SECTION__ANNOTATIONS = ConstraintsPackage.DISPLAY_UNIT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SECTION__NAME = ConstraintsPackage.DISPLAY_UNIT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Tab</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SECTION__TAB = ConstraintsPackage.DISPLAY_UNIT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Abstract Section</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SECTION_FEATURE_COUNT = ConstraintsPackage.DISPLAY_UNIT_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Annotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SECTION___GET_ANNOTATION__STRING = ConstraintsPackage.DISPLAY_UNIT_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Abstract Section</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SECTION_OPERATION_COUNT = ConstraintsPackage.DISPLAY_UNIT_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.properties.contexts.impl.SectionImpl <em>Section</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.properties.contexts.impl.SectionImpl
	 * @see org.eclipse.papyrus.infra.properties.contexts.impl.ContextsPackageImpl#getSection()
	 * @generated
	 */
	int SECTION = 2;

	/**
	 * The feature id for the '<em><b>Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECTION__CONSTRAINTS = ABSTRACT_SECTION__CONSTRAINTS;

	/**
	 * The feature id for the '<em><b>Element Multiplicity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECTION__ELEMENT_MULTIPLICITY = ABSTRACT_SECTION__ELEMENT_MULTIPLICITY;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECTION__ANNOTATIONS = ABSTRACT_SECTION__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECTION__NAME = ABSTRACT_SECTION__NAME;

	/**
	 * The feature id for the '<em><b>Tab</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECTION__TAB = ABSTRACT_SECTION__TAB;

	/**
	 * The feature id for the '<em><b>Section File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECTION__SECTION_FILE = ABSTRACT_SECTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Widget</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECTION__WIDGET = ABSTRACT_SECTION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Views</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECTION__VIEWS = ABSTRACT_SECTION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECTION__OWNER = ABSTRACT_SECTION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Section</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECTION_FEATURE_COUNT = ABSTRACT_SECTION_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Get Annotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECTION___GET_ANNOTATION__STRING = ABSTRACT_SECTION___GET_ANNOTATION__STRING;

	/**
	 * The operation id for the '<em>Get Views</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECTION___GET_VIEWS = ABSTRACT_SECTION_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Section</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECTION_OPERATION_COUNT = ABSTRACT_SECTION_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.properties.contexts.impl.AnnotatableImpl <em>Annotatable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.properties.contexts.impl.AnnotatableImpl
	 * @see org.eclipse.papyrus.infra.properties.contexts.impl.ContextsPackageImpl#getAnnotatable()
	 * @generated
	 */
	int ANNOTATABLE = 4;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATABLE__ANNOTATIONS = 0;

	/**
	 * The number of structural features of the '<em>Annotatable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATABLE_FEATURE_COUNT = 1;

	/**
	 * The operation id for the '<em>Get Annotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATABLE___GET_ANNOTATION__STRING = 0;

	/**
	 * The number of operations of the '<em>Annotatable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATABLE_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.properties.contexts.impl.AnnotationImpl <em>Annotation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.properties.contexts.impl.AnnotationImpl
	 * @see org.eclipse.papyrus.infra.properties.contexts.impl.ContextsPackageImpl#getAnnotation()
	 * @generated
	 */
	int ANNOTATION = 5;

	/**
	 * The feature id for the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__SOURCE = 0;

	/**
	 * The feature id for the '<em><b>References</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__REFERENCES = 1;

	/**
	 * The feature id for the '<em><b>Details</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__DETAILS = 2;

	/**
	 * The feature id for the '<em><b>Element</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__ELEMENT = 3;

	/**
	 * The number of structural features of the '<em>Annotation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Annotation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.properties.contexts.impl.DataContextElementImpl <em>Data Context Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.properties.contexts.impl.DataContextElementImpl
	 * @see org.eclipse.papyrus.infra.properties.contexts.impl.ContextsPackageImpl#getDataContextElement()
	 * @generated
	 */
	int DATA_CONTEXT_ELEMENT = 7;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.properties.contexts.impl.PropertyImpl <em>Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.properties.contexts.impl.PropertyImpl
	 * @see org.eclipse.papyrus.infra.properties.contexts.impl.ContextsPackageImpl#getProperty()
	 * @generated
	 */
	int PROPERTY = 6;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__ANNOTATIONS = ANNOTATABLE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__NAME = ANNOTATABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__LABEL = ANNOTATABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__TYPE = ANNOTATABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Context Element</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__CONTEXT_ELEMENT = ANNOTATABLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Multiplicity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__MULTIPLICITY = ANNOTATABLE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__DESCRIPTION = ANNOTATABLE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Redefined Properties</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__REDEFINED_PROPERTIES = ANNOTATABLE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Redefined By Properties</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__REDEFINED_BY_PROPERTIES = ANNOTATABLE_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FEATURE_COUNT = ANNOTATABLE_FEATURE_COUNT + 8;

	/**
	 * The operation id for the '<em>Get Annotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY___GET_ANNOTATION__STRING = ANNOTATABLE___GET_ANNOTATION__STRING;

	/**
	 * The number of operations of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_OPERATION_COUNT = ANNOTATABLE_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONTEXT_ELEMENT__ANNOTATIONS = ANNOTATABLE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONTEXT_ELEMENT__NAME = ANNOTATABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Package</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONTEXT_ELEMENT__PACKAGE = ANNOTATABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Supertypes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONTEXT_ELEMENT__SUPERTYPES = ANNOTATABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONTEXT_ELEMENT__PROPERTIES = ANNOTATABLE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Data Context Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONTEXT_ELEMENT_FEATURE_COUNT = ANNOTATABLE_FEATURE_COUNT + 4;

	/**
	 * The operation id for the '<em>Get Annotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONTEXT_ELEMENT___GET_ANNOTATION__STRING = ANNOTATABLE___GET_ANNOTATION__STRING;

	/**
	 * The number of operations of the '<em>Data Context Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONTEXT_ELEMENT_OPERATION_COUNT = ANNOTATABLE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.properties.contexts.impl.UnknownPropertyImpl <em>Unknown Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.properties.contexts.impl.UnknownPropertyImpl
	 * @see org.eclipse.papyrus.infra.properties.contexts.impl.ContextsPackageImpl#getUnknownProperty()
	 * @generated
	 */
	int UNKNOWN_PROPERTY = 9;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.properties.contexts.impl.DataContextPackageImpl <em>Data Context Package</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.properties.contexts.impl.DataContextPackageImpl
	 * @see org.eclipse.papyrus.infra.properties.contexts.impl.ContextsPackageImpl#getDataContextPackage()
	 * @generated
	 */
	int DATA_CONTEXT_PACKAGE = 8;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONTEXT_PACKAGE__ANNOTATIONS = DATA_CONTEXT_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONTEXT_PACKAGE__NAME = DATA_CONTEXT_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Package</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONTEXT_PACKAGE__PACKAGE = DATA_CONTEXT_ELEMENT__PACKAGE;

	/**
	 * The feature id for the '<em><b>Supertypes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONTEXT_PACKAGE__SUPERTYPES = DATA_CONTEXT_ELEMENT__SUPERTYPES;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONTEXT_PACKAGE__PROPERTIES = DATA_CONTEXT_ELEMENT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONTEXT_PACKAGE__ELEMENTS = DATA_CONTEXT_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Data Context Package</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONTEXT_PACKAGE_FEATURE_COUNT = DATA_CONTEXT_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Get Annotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONTEXT_PACKAGE___GET_ANNOTATION__STRING = DATA_CONTEXT_ELEMENT___GET_ANNOTATION__STRING;

	/**
	 * The number of operations of the '<em>Data Context Package</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONTEXT_PACKAGE_OPERATION_COUNT = DATA_CONTEXT_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_PROPERTY__ANNOTATIONS = PROPERTY__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_PROPERTY__NAME = PROPERTY__NAME;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_PROPERTY__LABEL = PROPERTY__LABEL;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_PROPERTY__TYPE = PROPERTY__TYPE;

	/**
	 * The feature id for the '<em><b>Context Element</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_PROPERTY__CONTEXT_ELEMENT = PROPERTY__CONTEXT_ELEMENT;

	/**
	 * The feature id for the '<em><b>Multiplicity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_PROPERTY__MULTIPLICITY = PROPERTY__MULTIPLICITY;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_PROPERTY__DESCRIPTION = PROPERTY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Redefined Properties</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_PROPERTY__REDEFINED_PROPERTIES = PROPERTY__REDEFINED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Redefined By Properties</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_PROPERTY__REDEFINED_BY_PROPERTIES = PROPERTY__REDEFINED_BY_PROPERTIES;

	/**
	 * The number of structural features of the '<em>Unknown Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_PROPERTY_FEATURE_COUNT = PROPERTY_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Get Annotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_PROPERTY___GET_ANNOTATION__STRING = PROPERTY___GET_ANNOTATION__STRING;

	/**
	 * The number of operations of the '<em>Unknown Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNKNOWN_PROPERTY_OPERATION_COUNT = PROPERTY_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__CONSTRAINTS = ConstraintsPackage.DISPLAY_UNIT__CONSTRAINTS;

	/**
	 * The feature id for the '<em><b>Element Multiplicity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__ELEMENT_MULTIPLICITY = ConstraintsPackage.DISPLAY_UNIT__ELEMENT_MULTIPLICITY;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__ANNOTATIONS = ConstraintsPackage.DISPLAY_UNIT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__NAME = ConstraintsPackage.DISPLAY_UNIT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Sections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__SECTIONS = ConstraintsPackage.DISPLAY_UNIT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Context</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__CONTEXT = ConstraintsPackage.DISPLAY_UNIT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Automatic Context</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__AUTOMATIC_CONTEXT = ConstraintsPackage.DISPLAY_UNIT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Datacontexts</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW__DATACONTEXTS = ConstraintsPackage.DISPLAY_UNIT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>View</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_FEATURE_COUNT = ConstraintsPackage.DISPLAY_UNIT_FEATURE_COUNT + 6;

	/**
	 * The operation id for the '<em>Get Annotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW___GET_ANNOTATION__STRING = ConstraintsPackage.DISPLAY_UNIT_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>View</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIEW_OPERATION_COUNT = ConstraintsPackage.DISPLAY_UNIT_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.properties.contexts.impl.DataContextRootImpl <em>Data Context Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.properties.contexts.impl.DataContextRootImpl
	 * @see org.eclipse.papyrus.infra.properties.contexts.impl.ContextsPackageImpl#getDataContextRoot()
	 * @generated
	 */
	int DATA_CONTEXT_ROOT = 11;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONTEXT_ROOT__ANNOTATIONS = DATA_CONTEXT_PACKAGE__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONTEXT_ROOT__NAME = DATA_CONTEXT_PACKAGE__NAME;

	/**
	 * The feature id for the '<em><b>Package</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONTEXT_ROOT__PACKAGE = DATA_CONTEXT_PACKAGE__PACKAGE;

	/**
	 * The feature id for the '<em><b>Supertypes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONTEXT_ROOT__SUPERTYPES = DATA_CONTEXT_PACKAGE__SUPERTYPES;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONTEXT_ROOT__PROPERTIES = DATA_CONTEXT_PACKAGE__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONTEXT_ROOT__ELEMENTS = DATA_CONTEXT_PACKAGE__ELEMENTS;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONTEXT_ROOT__LABEL = DATA_CONTEXT_PACKAGE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Model Element Factory</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONTEXT_ROOT__MODEL_ELEMENT_FACTORY = DATA_CONTEXT_PACKAGE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Data Context Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONTEXT_ROOT_FEATURE_COUNT = DATA_CONTEXT_PACKAGE_FEATURE_COUNT + 2;


	/**
	 * The operation id for the '<em>Get Annotation</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONTEXT_ROOT___GET_ANNOTATION__STRING = DATA_CONTEXT_PACKAGE___GET_ANNOTATION__STRING;

	/**
	 * The number of operations of the '<em>Data Context Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CONTEXT_ROOT_OPERATION_COUNT = DATA_CONTEXT_PACKAGE_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.properties.contexts.Context <em>Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Context</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Context
	 * @generated
	 */
	EClass getContext();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.properties.contexts.Context#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Context#getName()
	 * @see #getContext()
	 * @generated
	 */
	EAttribute getContext_Name();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.infra.properties.contexts.Context#getDependencies <em>Dependencies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Dependencies</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Context#getDependencies()
	 * @see #getContext()
	 * @generated
	 */
	EReference getContext_Dependencies();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.properties.contexts.Context#getTabs <em>Tabs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tabs</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Context#getTabs()
	 * @see #getContext()
	 * @generated
	 */
	EReference getContext_Tabs();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.properties.contexts.Context#getViews <em>Views</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Views</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Context#getViews()
	 * @see #getContext()
	 * @generated
	 */
	EReference getContext_Views();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.properties.contexts.Context#getDataContexts <em>Data Contexts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Data Contexts</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Context#getDataContexts()
	 * @see #getContext()
	 * @generated
	 */
	EReference getContext_DataContexts();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.properties.contexts.Context#getPrototype <em>Prototype</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Prototype</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Context#getPrototype()
	 * @see #getContext()
	 * @generated
	 */
	EReference getContext_Prototype();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.properties.contexts.Context#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Context#getLabel()
	 * @see #getContext()
	 * @generated
	 */
	EAttribute getContext_Label();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.properties.contexts.Context#getUserLabel <em>User Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>User Label</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Context#getUserLabel()
	 * @see #getContext()
	 * @generated
	 */
	EAttribute getContext_UserLabel();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.properties.contexts.Tab <em>Tab</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Tab</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Tab
	 * @generated
	 */
	EClass getTab();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.properties.contexts.Tab#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Tab#getLabel()
	 * @see #getTab()
	 * @generated
	 */
	EAttribute getTab_Label();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.properties.contexts.Tab#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Tab#getId()
	 * @see #getTab()
	 * @generated
	 */
	EAttribute getTab_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.properties.contexts.Tab#getCategory <em>Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Category</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Tab#getCategory()
	 * @see #getTab()
	 * @generated
	 */
	EAttribute getTab_Category();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.properties.contexts.Tab#getImage <em>Image</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Image</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Tab#getImage()
	 * @see #getTab()
	 * @generated
	 */
	EAttribute getTab_Image();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.properties.contexts.Tab#getAfterTab <em>After Tab</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>After Tab</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Tab#getAfterTab()
	 * @see #getTab()
	 * @generated
	 */
	EReference getTab_AfterTab();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.infra.properties.contexts.Tab#getSections <em>Sections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Sections</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Tab#getSections()
	 * @see #getTab()
	 * @generated
	 */
	EReference getTab_Sections();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.properties.contexts.Tab#getAllSections <em>All Sections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>All Sections</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Tab#getAllSections()
	 * @see #getTab()
	 * @generated
	 */
	EReference getTab_AllSections();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.properties.contexts.Tab#isIndented <em>Indented</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Indented</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Tab#isIndented()
	 * @see #getTab()
	 * @generated
	 */
	EAttribute getTab_Indented();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.properties.contexts.Tab#getPriority <em>Priority</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Priority</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Tab#getPriority()
	 * @see #getTab()
	 * @generated
	 */
	EAttribute getTab_Priority();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.properties.contexts.View <em>View</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>View</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.View
	 * @generated
	 */
	EClass getView();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.properties.contexts.View#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.View#getName()
	 * @see #getView()
	 * @generated
	 */
	EAttribute getView_Name();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.infra.properties.contexts.View#getSections <em>Sections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Sections</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.View#getSections()
	 * @see #getView()
	 * @generated
	 */
	EReference getView_Sections();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.papyrus.infra.properties.contexts.View#getContext <em>Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Context</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.View#getContext()
	 * @see #getView()
	 * @generated
	 */
	EReference getView_Context();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.properties.contexts.View#isAutomaticContext <em>Automatic Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Automatic Context</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.View#isAutomaticContext()
	 * @see #getView()
	 * @generated
	 */
	EAttribute getView_AutomaticContext();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.infra.properties.contexts.View#getDatacontexts <em>Datacontexts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Datacontexts</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.View#getDatacontexts()
	 * @see #getView()
	 * @generated
	 */
	EReference getView_Datacontexts();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.properties.contexts.AbstractSection <em>Abstract Section</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Section</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.AbstractSection
	 * @generated
	 */
	EClass getAbstractSection();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.properties.contexts.AbstractSection#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.AbstractSection#getName()
	 * @see #getAbstractSection()
	 * @generated
	 */
	EAttribute getAbstractSection_Name();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.papyrus.infra.properties.contexts.AbstractSection#getTab <em>Tab</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Tab</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.AbstractSection#getTab()
	 * @see #getAbstractSection()
	 * @generated
	 */
	EReference getAbstractSection_Tab();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.properties.contexts.Annotatable <em>Annotatable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotatable</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Annotatable
	 * @generated
	 */
	EClass getAnnotatable();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.properties.contexts.Annotatable#getAnnotations <em>Annotations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Annotations</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Annotatable#getAnnotations()
	 * @see #getAnnotatable()
	 * @generated
	 */
	EReference getAnnotatable_Annotations();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.properties.contexts.Annotatable#getAnnotation(java.lang.String) <em>Get Annotation</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Annotation</em>' operation.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Annotatable#getAnnotation(java.lang.String)
	 * @generated
	 */
	EOperation getAnnotatable__GetAnnotation__String();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.properties.contexts.Annotation <em>Annotation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotation</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Annotation
	 * @generated
	 */
	EClass getAnnotation();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.properties.contexts.Annotation#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Annotation#getSource()
	 * @see #getAnnotation()
	 * @generated
	 */
	EAttribute getAnnotation_Source();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.infra.properties.contexts.Annotation#getReferences <em>References</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>References</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Annotation#getReferences()
	 * @see #getAnnotation()
	 * @generated
	 */
	EReference getAnnotation_References();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.papyrus.infra.properties.contexts.Annotation#getDetails <em>Details</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Details</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Annotation#getDetails()
	 * @see #getAnnotation()
	 * @generated
	 */
	EReference getAnnotation_Details();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.papyrus.infra.properties.contexts.Annotation#getElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Element</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Annotation#getElement()
	 * @see #getAnnotation()
	 * @generated
	 */
	EReference getAnnotation_Element();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.properties.contexts.Section <em>Section</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Section</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Section
	 * @generated
	 */
	EClass getSection();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.properties.contexts.Section#getSectionFile <em>Section File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Section File</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Section#getSectionFile()
	 * @see #getSection()
	 * @generated
	 */
	EAttribute getSection_SectionFile();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.properties.contexts.Section#getWidget <em>Widget</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Widget</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Section#getWidget()
	 * @see #getSection()
	 * @generated
	 */
	EReference getSection_Widget();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.infra.properties.contexts.Section#getViews <em>Views</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Views</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Section#getViews()
	 * @see #getSection()
	 * @generated
	 */
	EReference getSection_Views();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.properties.contexts.Section#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Owner</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Section#getOwner()
	 * @see #getSection()
	 * @generated
	 */
	EReference getSection_Owner();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.properties.contexts.Section#getViews() <em>Get Views</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Get Views</em>' operation.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Section#getViews()
	 * @generated
	 */
	EOperation getSection__GetViews();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.properties.contexts.DataContextElement <em>Data Context Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Context Element</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.DataContextElement
	 * @generated
	 */
	EClass getDataContextElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.properties.contexts.DataContextElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.DataContextElement#getName()
	 * @see #getDataContextElement()
	 * @generated
	 */
	EAttribute getDataContextElement_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.properties.contexts.DataContextElement#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.DataContextElement#getProperties()
	 * @see #getDataContextElement()
	 * @generated
	 */
	EReference getDataContextElement_Properties();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.papyrus.infra.properties.contexts.DataContextElement#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Package</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.DataContextElement#getPackage()
	 * @see #getDataContextElement()
	 * @generated
	 */
	EReference getDataContextElement_Package();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.infra.properties.contexts.DataContextElement#getSupertypes <em>Supertypes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Supertypes</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.DataContextElement#getSupertypes()
	 * @see #getDataContextElement()
	 * @generated
	 */
	EReference getDataContextElement_Supertypes();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.properties.contexts.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Property
	 * @generated
	 */
	EClass getProperty();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.properties.contexts.Property#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Property#getName()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.properties.contexts.Property#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Property#getLabel()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Label();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.properties.contexts.Property#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Property#getType()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Type();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.papyrus.infra.properties.contexts.Property#getContextElement <em>Context Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Context Element</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Property#getContextElement()
	 * @see #getProperty()
	 * @generated
	 */
	EReference getProperty_ContextElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.properties.contexts.Property#getMultiplicity <em>Multiplicity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Multiplicity</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Property#getMultiplicity()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Multiplicity();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.properties.contexts.Property#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Property#getDescription()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Description();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.infra.properties.contexts.Property#getRedefinedProperties <em>Redefined Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Redefined Properties</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Property#getRedefinedProperties()
	 * @see #getProperty()
	 * @generated
	 */
	EReference getProperty_RedefinedProperties();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.infra.properties.contexts.Property#getRedefinedByProperties <em>Redefined By Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Redefined By Properties</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.Property#getRedefinedByProperties()
	 * @see #getProperty()
	 * @generated
	 */
	EReference getProperty_RedefinedByProperties();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.properties.contexts.UnknownProperty <em>Unknown Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unknown Property</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.UnknownProperty
	 * @generated
	 */
	EClass getUnknownProperty();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.properties.contexts.DataContextPackage <em>Data Context Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Context Package</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.DataContextPackage
	 * @generated
	 */
	EClass getDataContextPackage();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.properties.contexts.DataContextPackage#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Elements</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.DataContextPackage#getElements()
	 * @see #getDataContextPackage()
	 * @generated
	 */
	EReference getDataContextPackage_Elements();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.properties.contexts.DataContextRoot <em>Data Context Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Context Root</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.DataContextRoot
	 * @generated
	 */
	EClass getDataContextRoot();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.properties.contexts.DataContextRoot#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.DataContextRoot#getLabel()
	 * @see #getDataContextRoot()
	 * @generated
	 */
	EAttribute getDataContextRoot_Label();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.properties.contexts.DataContextRoot#getModelElementFactory <em>Model Element Factory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Model Element Factory</em>'.
	 * @see org.eclipse.papyrus.infra.properties.contexts.DataContextRoot#getModelElementFactory()
	 * @see #getDataContextRoot()
	 * @generated
	 */
	EReference getDataContextRoot_ModelElementFactory();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ContextsFactory getContextsFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.properties.contexts.impl.ContextImpl <em>Context</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.properties.contexts.impl.ContextImpl
		 * @see org.eclipse.papyrus.infra.properties.contexts.impl.ContextsPackageImpl#getContext()
		 * @generated
		 */
		EClass CONTEXT = eINSTANCE.getContext();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTEXT__NAME = eINSTANCE.getContext_Name();

		/**
		 * The meta object literal for the '<em><b>Dependencies</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTEXT__DEPENDENCIES = eINSTANCE.getContext_Dependencies();

		/**
		 * The meta object literal for the '<em><b>Tabs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTEXT__TABS = eINSTANCE.getContext_Tabs();

		/**
		 * The meta object literal for the '<em><b>Views</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTEXT__VIEWS = eINSTANCE.getContext_Views();

		/**
		 * The meta object literal for the '<em><b>Data Contexts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTEXT__DATA_CONTEXTS = eINSTANCE.getContext_DataContexts();

		/**
		 * The meta object literal for the '<em><b>Prototype</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTEXT__PROTOTYPE = eINSTANCE.getContext_Prototype();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTEXT__LABEL = eINSTANCE.getContext_Label();

		/**
		 * The meta object literal for the '<em><b>User Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTEXT__USER_LABEL = eINSTANCE.getContext_UserLabel();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.properties.contexts.impl.TabImpl <em>Tab</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.properties.contexts.impl.TabImpl
		 * @see org.eclipse.papyrus.infra.properties.contexts.impl.ContextsPackageImpl#getTab()
		 * @generated
		 */
		EClass TAB = eINSTANCE.getTab();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TAB__LABEL = eINSTANCE.getTab_Label();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TAB__ID = eINSTANCE.getTab_Id();

		/**
		 * The meta object literal for the '<em><b>Category</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TAB__CATEGORY = eINSTANCE.getTab_Category();

		/**
		 * The meta object literal for the '<em><b>Image</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TAB__IMAGE = eINSTANCE.getTab_Image();

		/**
		 * The meta object literal for the '<em><b>After Tab</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TAB__AFTER_TAB = eINSTANCE.getTab_AfterTab();

		/**
		 * The meta object literal for the '<em><b>Sections</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TAB__SECTIONS = eINSTANCE.getTab_Sections();

		/**
		 * The meta object literal for the '<em><b>All Sections</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TAB__ALL_SECTIONS = eINSTANCE.getTab_AllSections();

		/**
		 * The meta object literal for the '<em><b>Indented</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TAB__INDENTED = eINSTANCE.getTab_Indented();

		/**
		 * The meta object literal for the '<em><b>Priority</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TAB__PRIORITY = eINSTANCE.getTab_Priority();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.properties.contexts.impl.ViewImpl <em>View</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.properties.contexts.impl.ViewImpl
		 * @see org.eclipse.papyrus.infra.properties.contexts.impl.ContextsPackageImpl#getView()
		 * @generated
		 */
		EClass VIEW = eINSTANCE.getView();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VIEW__NAME = eINSTANCE.getView_Name();

		/**
		 * The meta object literal for the '<em><b>Sections</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIEW__SECTIONS = eINSTANCE.getView_Sections();

		/**
		 * The meta object literal for the '<em><b>Context</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIEW__CONTEXT = eINSTANCE.getView_Context();

		/**
		 * The meta object literal for the '<em><b>Automatic Context</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VIEW__AUTOMATIC_CONTEXT = eINSTANCE.getView_AutomaticContext();

		/**
		 * The meta object literal for the '<em><b>Datacontexts</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIEW__DATACONTEXTS = eINSTANCE.getView_Datacontexts();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.properties.contexts.impl.AbstractSectionImpl <em>Abstract Section</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.properties.contexts.impl.AbstractSectionImpl
		 * @see org.eclipse.papyrus.infra.properties.contexts.impl.ContextsPackageImpl#getAbstractSection()
		 * @generated
		 */
		EClass ABSTRACT_SECTION = eINSTANCE.getAbstractSection();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_SECTION__NAME = eINSTANCE.getAbstractSection_Name();

		/**
		 * The meta object literal for the '<em><b>Tab</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_SECTION__TAB = eINSTANCE.getAbstractSection_Tab();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.properties.contexts.impl.AnnotatableImpl <em>Annotatable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.properties.contexts.impl.AnnotatableImpl
		 * @see org.eclipse.papyrus.infra.properties.contexts.impl.ContextsPackageImpl#getAnnotatable()
		 * @generated
		 */
		EClass ANNOTATABLE = eINSTANCE.getAnnotatable();

		/**
		 * The meta object literal for the '<em><b>Annotations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANNOTATABLE__ANNOTATIONS = eINSTANCE.getAnnotatable_Annotations();

		/**
		 * The meta object literal for the '<em><b>Get Annotation</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ANNOTATABLE___GET_ANNOTATION__STRING = eINSTANCE.getAnnotatable__GetAnnotation__String();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.properties.contexts.impl.AnnotationImpl <em>Annotation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.properties.contexts.impl.AnnotationImpl
		 * @see org.eclipse.papyrus.infra.properties.contexts.impl.ContextsPackageImpl#getAnnotation()
		 * @generated
		 */
		EClass ANNOTATION = eINSTANCE.getAnnotation();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ANNOTATION__SOURCE = eINSTANCE.getAnnotation_Source();

		/**
		 * The meta object literal for the '<em><b>References</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANNOTATION__REFERENCES = eINSTANCE.getAnnotation_References();

		/**
		 * The meta object literal for the '<em><b>Details</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANNOTATION__DETAILS = eINSTANCE.getAnnotation_Details();

		/**
		 * The meta object literal for the '<em><b>Element</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANNOTATION__ELEMENT = eINSTANCE.getAnnotation_Element();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.properties.contexts.impl.SectionImpl <em>Section</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.properties.contexts.impl.SectionImpl
		 * @see org.eclipse.papyrus.infra.properties.contexts.impl.ContextsPackageImpl#getSection()
		 * @generated
		 */
		EClass SECTION = eINSTANCE.getSection();

		/**
		 * The meta object literal for the '<em><b>Section File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SECTION__SECTION_FILE = eINSTANCE.getSection_SectionFile();

		/**
		 * The meta object literal for the '<em><b>Widget</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SECTION__WIDGET = eINSTANCE.getSection_Widget();

		/**
		 * The meta object literal for the '<em><b>Views</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SECTION__VIEWS = eINSTANCE.getSection_Views();

		/**
		 * The meta object literal for the '<em><b>Owner</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SECTION__OWNER = eINSTANCE.getSection_Owner();

		/**
		 * The meta object literal for the '<em><b>Get Views</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation SECTION___GET_VIEWS = eINSTANCE.getSection__GetViews();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.properties.contexts.impl.DataContextElementImpl <em>Data Context Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.properties.contexts.impl.DataContextElementImpl
		 * @see org.eclipse.papyrus.infra.properties.contexts.impl.ContextsPackageImpl#getDataContextElement()
		 * @generated
		 */
		EClass DATA_CONTEXT_ELEMENT = eINSTANCE.getDataContextElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_CONTEXT_ELEMENT__NAME = eINSTANCE.getDataContextElement_Name();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATA_CONTEXT_ELEMENT__PROPERTIES = eINSTANCE.getDataContextElement_Properties();

		/**
		 * The meta object literal for the '<em><b>Package</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATA_CONTEXT_ELEMENT__PACKAGE = eINSTANCE.getDataContextElement_Package();

		/**
		 * The meta object literal for the '<em><b>Supertypes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATA_CONTEXT_ELEMENT__SUPERTYPES = eINSTANCE.getDataContextElement_Supertypes();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.properties.contexts.impl.PropertyImpl <em>Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.properties.contexts.impl.PropertyImpl
		 * @see org.eclipse.papyrus.infra.properties.contexts.impl.ContextsPackageImpl#getProperty()
		 * @generated
		 */
		EClass PROPERTY = eINSTANCE.getProperty();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__NAME = eINSTANCE.getProperty_Name();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__LABEL = eINSTANCE.getProperty_Label();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__TYPE = eINSTANCE.getProperty_Type();

		/**
		 * The meta object literal for the '<em><b>Context Element</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY__CONTEXT_ELEMENT = eINSTANCE.getProperty_ContextElement();

		/**
		 * The meta object literal for the '<em><b>Multiplicity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__MULTIPLICITY = eINSTANCE.getProperty_Multiplicity();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__DESCRIPTION = eINSTANCE.getProperty_Description();

		/**
		 * The meta object literal for the '<em><b>Redefined Properties</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY__REDEFINED_PROPERTIES = eINSTANCE.getProperty_RedefinedProperties();

		/**
		 * The meta object literal for the '<em><b>Redefined By Properties</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY__REDEFINED_BY_PROPERTIES = eINSTANCE.getProperty_RedefinedByProperties();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.properties.contexts.impl.UnknownPropertyImpl <em>Unknown Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.properties.contexts.impl.UnknownPropertyImpl
		 * @see org.eclipse.papyrus.infra.properties.contexts.impl.ContextsPackageImpl#getUnknownProperty()
		 * @generated
		 */
		EClass UNKNOWN_PROPERTY = eINSTANCE.getUnknownProperty();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.properties.contexts.impl.DataContextPackageImpl <em>Data Context Package</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.properties.contexts.impl.DataContextPackageImpl
		 * @see org.eclipse.papyrus.infra.properties.contexts.impl.ContextsPackageImpl#getDataContextPackage()
		 * @generated
		 */
		EClass DATA_CONTEXT_PACKAGE = eINSTANCE.getDataContextPackage();

		/**
		 * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATA_CONTEXT_PACKAGE__ELEMENTS = eINSTANCE.getDataContextPackage_Elements();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.properties.contexts.impl.DataContextRootImpl <em>Data Context Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.properties.contexts.impl.DataContextRootImpl
		 * @see org.eclipse.papyrus.infra.properties.contexts.impl.ContextsPackageImpl#getDataContextRoot()
		 * @generated
		 */
		EClass DATA_CONTEXT_ROOT = eINSTANCE.getDataContextRoot();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_CONTEXT_ROOT__LABEL = eINSTANCE.getDataContextRoot_Label();

		/**
		 * The meta object literal for the '<em><b>Model Element Factory</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATA_CONTEXT_ROOT__MODEL_ELEMENT_FACTORY = eINSTANCE.getDataContextRoot_ModelElementFactory();

	}

} // ContextsPackage
