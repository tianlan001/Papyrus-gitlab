/**
 * Copyright (c) 2016 CEA LIST.
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
 *  
 * 
 */
package org.eclipse.papyrus.infra.architecture.representation;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage;

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
 * @see org.eclipse.papyrus.infra.architecture.representation.RepresentationFactory
 * @model kind="package"
 * @generated
 */
public interface RepresentationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "representation";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/infra/core/architecture/representation";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "representation";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RepresentationPackage eINSTANCE = org.eclipse.papyrus.infra.architecture.representation.impl.RepresentationPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.architecture.representation.impl.PapyrusRepresentationKindImpl <em>Papyrus Representation Kind</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.architecture.representation.impl.PapyrusRepresentationKindImpl
	 * @see org.eclipse.papyrus.infra.architecture.representation.impl.RepresentationPackageImpl#getPapyrusRepresentationKind()
	 * @generated
	 */
	int PAPYRUS_REPRESENTATION_KIND = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_REPRESENTATION_KIND__ID = ArchitecturePackage.REPRESENTATION_KIND__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_REPRESENTATION_KIND__NAME = ArchitecturePackage.REPRESENTATION_KIND__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_REPRESENTATION_KIND__DESCRIPTION = ArchitecturePackage.REPRESENTATION_KIND__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_REPRESENTATION_KIND__QUALIFIED_NAME = ArchitecturePackage.REPRESENTATION_KIND__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_REPRESENTATION_KIND__ICON = ArchitecturePackage.REPRESENTATION_KIND__ICON;

	/**
	 * The feature id for the '<em><b>Language</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_REPRESENTATION_KIND__LANGUAGE = ArchitecturePackage.REPRESENTATION_KIND__LANGUAGE;

	/**
	 * The feature id for the '<em><b>Concerns</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_REPRESENTATION_KIND__CONCERNS = ArchitecturePackage.REPRESENTATION_KIND__CONCERNS;

	/**
	 * The feature id for the '<em><b>Grayed Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_REPRESENTATION_KIND__GRAYED_ICON = ArchitecturePackage.REPRESENTATION_KIND__GRAYED_ICON;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_REPRESENTATION_KIND__PARENT = ArchitecturePackage.REPRESENTATION_KIND_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Model Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_REPRESENTATION_KIND__MODEL_RULES = ArchitecturePackage.REPRESENTATION_KIND_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Owning Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_REPRESENTATION_KIND__OWNING_RULES = ArchitecturePackage.REPRESENTATION_KIND_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Implementation ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_REPRESENTATION_KIND__IMPLEMENTATION_ID = ArchitecturePackage.REPRESENTATION_KIND_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Papyrus Representation Kind</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_REPRESENTATION_KIND_FEATURE_COUNT = ArchitecturePackage.REPRESENTATION_KIND_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Papyrus Representation Kind</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_REPRESENTATION_KIND_OPERATION_COUNT = ArchitecturePackage.REPRESENTATION_KIND_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.architecture.representation.impl.RuleImpl <em>Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.architecture.representation.impl.RuleImpl
	 * @see org.eclipse.papyrus.infra.architecture.representation.impl.RepresentationPackageImpl#getRule()
	 * @generated
	 */
	int RULE = 1;

	/**
	 * The feature id for the '<em><b>Permit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE__PERMIT = 0;

	/**
	 * The number of structural features of the '<em>Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.architecture.representation.impl.ModelRuleImpl <em>Model Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.architecture.representation.impl.ModelRuleImpl
	 * @see org.eclipse.papyrus.infra.architecture.representation.impl.RepresentationPackageImpl#getModelRule()
	 * @generated
	 */
	int MODEL_RULE = 2;

	/**
	 * The feature id for the '<em><b>Permit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_RULE__PERMIT = RULE__PERMIT;

	/**
	 * The feature id for the '<em><b>Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_RULE__CONSTRAINTS = RULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Element Multiplicity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_RULE__ELEMENT_MULTIPLICITY = RULE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_RULE__ELEMENT = RULE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Stereotypes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_RULE__STEREOTYPES = RULE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Multiplicity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_RULE__MULTIPLICITY = RULE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Model Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_RULE_FEATURE_COUNT = RULE_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>Model Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_RULE_OPERATION_COUNT = RULE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.architecture.representation.impl.OwningRuleImpl <em>Owning Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.architecture.representation.impl.OwningRuleImpl
	 * @see org.eclipse.papyrus.infra.architecture.representation.impl.RepresentationPackageImpl#getOwningRule()
	 * @generated
	 */
	int OWNING_RULE = 3;

	/**
	 * The feature id for the '<em><b>Permit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OWNING_RULE__PERMIT = RULE__PERMIT;

	/**
	 * The feature id for the '<em><b>Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OWNING_RULE__ELEMENT = RULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Stereotypes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OWNING_RULE__STEREOTYPES = RULE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Multiplicity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OWNING_RULE__MULTIPLICITY = RULE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>New Model Path</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OWNING_RULE__NEW_MODEL_PATH = RULE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Select Diagram Root</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OWNING_RULE__SELECT_DIAGRAM_ROOT = RULE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Owning Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OWNING_RULE_FEATURE_COUNT = RULE_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>Owning Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OWNING_RULE_OPERATION_COUNT = RULE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.architecture.representation.impl.ModelAutoCreateImpl <em>Model Auto Create</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.architecture.representation.impl.ModelAutoCreateImpl
	 * @see org.eclipse.papyrus.infra.architecture.representation.impl.RepresentationPackageImpl#getModelAutoCreate()
	 * @generated
	 */
	int MODEL_AUTO_CREATE = 4;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_AUTO_CREATE__FEATURE = 0;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_AUTO_CREATE__ORIGIN = 1;

	/**
	 * The feature id for the '<em><b>Creation Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_AUTO_CREATE__CREATION_TYPE = 2;

	/**
	 * The number of structural features of the '<em>Model Auto Create</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_AUTO_CREATE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Model Auto Create</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_AUTO_CREATE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.architecture.representation.impl.RootAutoSelectImpl <em>Root Auto Select</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.architecture.representation.impl.RootAutoSelectImpl
	 * @see org.eclipse.papyrus.infra.architecture.representation.impl.RepresentationPackageImpl#getRootAutoSelect()
	 * @generated
	 */
	int ROOT_AUTO_SELECT = 5;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_AUTO_SELECT__FEATURE = 0;

	/**
	 * The number of structural features of the '<em>Root Auto Select</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_AUTO_SELECT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Root Auto Select</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROOT_AUTO_SELECT_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind <em>Papyrus Representation Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Papyrus Representation Kind</em>'.
	 * @see org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind
	 * @generated
	 */
	EClass getPapyrusRepresentationKind();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind#getParent()
	 * @see #getPapyrusRepresentationKind()
	 * @generated
	 */
	EReference getPapyrusRepresentationKind_Parent();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind#getModelRules <em>Model Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Model Rules</em>'.
	 * @see org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind#getModelRules()
	 * @see #getPapyrusRepresentationKind()
	 * @generated
	 */
	EReference getPapyrusRepresentationKind_ModelRules();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind#getOwningRules <em>Owning Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Owning Rules</em>'.
	 * @see org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind#getOwningRules()
	 * @see #getPapyrusRepresentationKind()
	 * @generated
	 */
	EReference getPapyrusRepresentationKind_OwningRules();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind#getImplementationID <em>Implementation ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Implementation ID</em>'.
	 * @see org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind#getImplementationID()
	 * @see #getPapyrusRepresentationKind()
	 * @generated
	 */
	EAttribute getPapyrusRepresentationKind_ImplementationID();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.architecture.representation.Rule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule</em>'.
	 * @see org.eclipse.papyrus.infra.architecture.representation.Rule
	 * @generated
	 */
	EClass getRule();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.architecture.representation.Rule#isPermit <em>Permit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Permit</em>'.
	 * @see org.eclipse.papyrus.infra.architecture.representation.Rule#isPermit()
	 * @see #getRule()
	 * @generated
	 */
	EAttribute getRule_Permit();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.architecture.representation.ModelRule <em>Model Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model Rule</em>'.
	 * @see org.eclipse.papyrus.infra.architecture.representation.ModelRule
	 * @generated
	 */
	EClass getModelRule();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.architecture.representation.ModelRule#getElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Element</em>'.
	 * @see org.eclipse.papyrus.infra.architecture.representation.ModelRule#getElement()
	 * @see #getModelRule()
	 * @generated
	 */
	EReference getModelRule_Element();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.infra.architecture.representation.ModelRule#getStereotypes <em>Stereotypes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Stereotypes</em>'.
	 * @see org.eclipse.papyrus.infra.architecture.representation.ModelRule#getStereotypes()
	 * @see #getModelRule()
	 * @generated
	 */
	EReference getModelRule_Stereotypes();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.architecture.representation.ModelRule#getMultiplicity <em>Multiplicity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Multiplicity</em>'.
	 * @see org.eclipse.papyrus.infra.architecture.representation.ModelRule#getMultiplicity()
	 * @see #getModelRule()
	 * @generated
	 */
	EAttribute getModelRule_Multiplicity();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.architecture.representation.OwningRule <em>Owning Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Owning Rule</em>'.
	 * @see org.eclipse.papyrus.infra.architecture.representation.OwningRule
	 * @generated
	 */
	EClass getOwningRule();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.architecture.representation.OwningRule#getElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Element</em>'.
	 * @see org.eclipse.papyrus.infra.architecture.representation.OwningRule#getElement()
	 * @see #getOwningRule()
	 * @generated
	 */
	EReference getOwningRule_Element();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.infra.architecture.representation.OwningRule#getStereotypes <em>Stereotypes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Stereotypes</em>'.
	 * @see org.eclipse.papyrus.infra.architecture.representation.OwningRule#getStereotypes()
	 * @see #getOwningRule()
	 * @generated
	 */
	EReference getOwningRule_Stereotypes();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.architecture.representation.OwningRule#getMultiplicity <em>Multiplicity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Multiplicity</em>'.
	 * @see org.eclipse.papyrus.infra.architecture.representation.OwningRule#getMultiplicity()
	 * @see #getOwningRule()
	 * @generated
	 */
	EAttribute getOwningRule_Multiplicity();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.architecture.representation.OwningRule#getNewModelPath <em>New Model Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>New Model Path</em>'.
	 * @see org.eclipse.papyrus.infra.architecture.representation.OwningRule#getNewModelPath()
	 * @see #getOwningRule()
	 * @generated
	 */
	EReference getOwningRule_NewModelPath();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.architecture.representation.OwningRule#getSelectDiagramRoot <em>Select Diagram Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Select Diagram Root</em>'.
	 * @see org.eclipse.papyrus.infra.architecture.representation.OwningRule#getSelectDiagramRoot()
	 * @see #getOwningRule()
	 * @generated
	 */
	EReference getOwningRule_SelectDiagramRoot();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.architecture.representation.ModelAutoCreate <em>Model Auto Create</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model Auto Create</em>'.
	 * @see org.eclipse.papyrus.infra.architecture.representation.ModelAutoCreate
	 * @generated
	 */
	EClass getModelAutoCreate();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.architecture.representation.ModelAutoCreate#getFeature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Feature</em>'.
	 * @see org.eclipse.papyrus.infra.architecture.representation.ModelAutoCreate#getFeature()
	 * @see #getModelAutoCreate()
	 * @generated
	 */
	EReference getModelAutoCreate_Feature();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.architecture.representation.ModelAutoCreate#getOrigin <em>Origin</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Origin</em>'.
	 * @see org.eclipse.papyrus.infra.architecture.representation.ModelAutoCreate#getOrigin()
	 * @see #getModelAutoCreate()
	 * @generated
	 */
	EReference getModelAutoCreate_Origin();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.architecture.representation.ModelAutoCreate#getCreationType <em>Creation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Creation Type</em>'.
	 * @see org.eclipse.papyrus.infra.architecture.representation.ModelAutoCreate#getCreationType()
	 * @see #getModelAutoCreate()
	 * @generated
	 */
	EAttribute getModelAutoCreate_CreationType();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.architecture.representation.RootAutoSelect <em>Root Auto Select</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Root Auto Select</em>'.
	 * @see org.eclipse.papyrus.infra.architecture.representation.RootAutoSelect
	 * @generated
	 */
	EClass getRootAutoSelect();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.architecture.representation.RootAutoSelect#getFeature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Feature</em>'.
	 * @see org.eclipse.papyrus.infra.architecture.representation.RootAutoSelect#getFeature()
	 * @see #getRootAutoSelect()
	 * @generated
	 */
	EReference getRootAutoSelect_Feature();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	RepresentationFactory getRepresentationFactory();

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
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.architecture.representation.impl.PapyrusRepresentationKindImpl <em>Papyrus Representation Kind</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.architecture.representation.impl.PapyrusRepresentationKindImpl
		 * @see org.eclipse.papyrus.infra.architecture.representation.impl.RepresentationPackageImpl#getPapyrusRepresentationKind()
		 * @generated
		 */
		EClass PAPYRUS_REPRESENTATION_KIND = eINSTANCE.getPapyrusRepresentationKind();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAPYRUS_REPRESENTATION_KIND__PARENT = eINSTANCE.getPapyrusRepresentationKind_Parent();

		/**
		 * The meta object literal for the '<em><b>Model Rules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAPYRUS_REPRESENTATION_KIND__MODEL_RULES = eINSTANCE.getPapyrusRepresentationKind_ModelRules();

		/**
		 * The meta object literal for the '<em><b>Owning Rules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAPYRUS_REPRESENTATION_KIND__OWNING_RULES = eINSTANCE.getPapyrusRepresentationKind_OwningRules();

		/**
		 * The meta object literal for the '<em><b>Implementation ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAPYRUS_REPRESENTATION_KIND__IMPLEMENTATION_ID = eINSTANCE.getPapyrusRepresentationKind_ImplementationID();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.architecture.representation.impl.RuleImpl <em>Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.architecture.representation.impl.RuleImpl
		 * @see org.eclipse.papyrus.infra.architecture.representation.impl.RepresentationPackageImpl#getRule()
		 * @generated
		 */
		EClass RULE = eINSTANCE.getRule();

		/**
		 * The meta object literal for the '<em><b>Permit</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE__PERMIT = eINSTANCE.getRule_Permit();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.architecture.representation.impl.ModelRuleImpl <em>Model Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.architecture.representation.impl.ModelRuleImpl
		 * @see org.eclipse.papyrus.infra.architecture.representation.impl.RepresentationPackageImpl#getModelRule()
		 * @generated
		 */
		EClass MODEL_RULE = eINSTANCE.getModelRule();

		/**
		 * The meta object literal for the '<em><b>Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL_RULE__ELEMENT = eINSTANCE.getModelRule_Element();

		/**
		 * The meta object literal for the '<em><b>Stereotypes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL_RULE__STEREOTYPES = eINSTANCE.getModelRule_Stereotypes();

		/**
		 * The meta object literal for the '<em><b>Multiplicity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL_RULE__MULTIPLICITY = eINSTANCE.getModelRule_Multiplicity();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.architecture.representation.impl.OwningRuleImpl <em>Owning Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.architecture.representation.impl.OwningRuleImpl
		 * @see org.eclipse.papyrus.infra.architecture.representation.impl.RepresentationPackageImpl#getOwningRule()
		 * @generated
		 */
		EClass OWNING_RULE = eINSTANCE.getOwningRule();

		/**
		 * The meta object literal for the '<em><b>Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OWNING_RULE__ELEMENT = eINSTANCE.getOwningRule_Element();

		/**
		 * The meta object literal for the '<em><b>Stereotypes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OWNING_RULE__STEREOTYPES = eINSTANCE.getOwningRule_Stereotypes();

		/**
		 * The meta object literal for the '<em><b>Multiplicity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OWNING_RULE__MULTIPLICITY = eINSTANCE.getOwningRule_Multiplicity();

		/**
		 * The meta object literal for the '<em><b>New Model Path</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OWNING_RULE__NEW_MODEL_PATH = eINSTANCE.getOwningRule_NewModelPath();

		/**
		 * The meta object literal for the '<em><b>Select Diagram Root</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OWNING_RULE__SELECT_DIAGRAM_ROOT = eINSTANCE.getOwningRule_SelectDiagramRoot();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.architecture.representation.impl.ModelAutoCreateImpl <em>Model Auto Create</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.architecture.representation.impl.ModelAutoCreateImpl
		 * @see org.eclipse.papyrus.infra.architecture.representation.impl.RepresentationPackageImpl#getModelAutoCreate()
		 * @generated
		 */
		EClass MODEL_AUTO_CREATE = eINSTANCE.getModelAutoCreate();

		/**
		 * The meta object literal for the '<em><b>Feature</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL_AUTO_CREATE__FEATURE = eINSTANCE.getModelAutoCreate_Feature();

		/**
		 * The meta object literal for the '<em><b>Origin</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL_AUTO_CREATE__ORIGIN = eINSTANCE.getModelAutoCreate_Origin();

		/**
		 * The meta object literal for the '<em><b>Creation Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL_AUTO_CREATE__CREATION_TYPE = eINSTANCE.getModelAutoCreate_CreationType();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.architecture.representation.impl.RootAutoSelectImpl <em>Root Auto Select</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.architecture.representation.impl.RootAutoSelectImpl
		 * @see org.eclipse.papyrus.infra.architecture.representation.impl.RepresentationPackageImpl#getRootAutoSelect()
		 * @generated
		 */
		EClass ROOT_AUTO_SELECT = eINSTANCE.getRootAutoSelect();

		/**
		 * The meta object literal for the '<em><b>Feature</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROOT_AUTO_SELECT__FEATURE = eINSTANCE.getRootAutoSelect_Feature();

	}

} //RepresentationPackage
