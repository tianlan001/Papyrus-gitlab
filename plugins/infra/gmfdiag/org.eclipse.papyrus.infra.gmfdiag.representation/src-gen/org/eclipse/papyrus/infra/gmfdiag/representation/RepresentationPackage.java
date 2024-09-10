/**
 * Copyright (c) 2017 CEA LIST.
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
package org.eclipse.papyrus.infra.gmfdiag.representation;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see org.eclipse.papyrus.infra.gmfdiag.representation.RepresentationFactory
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
	String eNAME = "representation"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/infra/gmfdiag/representation"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "gmfdiagrepresentation"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RepresentationPackage eINSTANCE = org.eclipse.papyrus.infra.gmfdiag.representation.impl.RepresentationPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.gmfdiag.representation.impl.PapyrusDiagramImpl <em>Papyrus Diagram</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.impl.PapyrusDiagramImpl
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.impl.RepresentationPackageImpl#getPapyrusDiagram()
	 * @generated
	 */
	int PAPYRUS_DIAGRAM = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_DIAGRAM__ID = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_DIAGRAM__NAME = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_DIAGRAM__DESCRIPTION = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_DIAGRAM__QUALIFIED_NAME = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_DIAGRAM__ICON = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__ICON;

	/**
	 * The feature id for the '<em><b>Language</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_DIAGRAM__LANGUAGE = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__LANGUAGE;

	/**
	 * The feature id for the '<em><b>Concerns</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_DIAGRAM__CONCERNS = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__CONCERNS;

	/**
	 * The feature id for the '<em><b>Grayed Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_DIAGRAM__GRAYED_ICON = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__GRAYED_ICON;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_DIAGRAM__PARENT = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__PARENT;

	/**
	 * The feature id for the '<em><b>Model Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_DIAGRAM__MODEL_RULES = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__MODEL_RULES;

	/**
	 * The feature id for the '<em><b>Owning Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_DIAGRAM__OWNING_RULES = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__OWNING_RULES;

	/**
	 * The feature id for the '<em><b>Implementation ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_DIAGRAM__IMPLEMENTATION_ID = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__IMPLEMENTATION_ID;

	/**
	 * The feature id for the '<em><b>Custom Style</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_DIAGRAM__CUSTOM_STYLE = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Child Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_DIAGRAM__CHILD_RULES = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Palette Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_DIAGRAM__PALETTE_RULES = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Assistant Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_DIAGRAM__ASSISTANT_RULES = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Creation Command Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_DIAGRAM__CREATION_COMMAND_CLASS = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Palettes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_DIAGRAM__PALETTES = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Papyrus Diagram</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_DIAGRAM_FEATURE_COUNT = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND_FEATURE_COUNT + 6;

	/**
	 * The operation id for the '<em>Ceation Command Class Exists</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_DIAGRAM___CEATION_COMMAND_CLASS_EXISTS__DIAGNOSTICCHAIN_MAP = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Papyrus Diagram</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAPYRUS_DIAGRAM_OPERATION_COUNT = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.PAPYRUS_REPRESENTATION_KIND_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.gmfdiag.representation.impl.ChildRuleImpl <em>Child Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.impl.ChildRuleImpl
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.impl.RepresentationPackageImpl#getChildRule()
	 * @generated
	 */
	int CHILD_RULE = 1;

	/**
	 * The feature id for the '<em><b>Permit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_RULE__PERMIT = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.RULE__PERMIT;

	/**
	 * The feature id for the '<em><b>Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_RULE__ELEMENT = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.RULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Stereotypes</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_RULE__STEREOTYPES = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.RULE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_RULE__ORIGIN = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.RULE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Insertion Path</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_RULE__INSERTION_PATH = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.RULE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Child Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_RULE_FEATURE_COUNT = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.RULE_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Child Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHILD_RULE_OPERATION_COUNT = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.RULE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.gmfdiag.representation.impl.PaletteRuleImpl <em>Palette Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.impl.PaletteRuleImpl
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.impl.RepresentationPackageImpl#getPaletteRule()
	 * @generated
	 */
	int PALETTE_RULE = 2;

	/**
	 * The feature id for the '<em><b>Permit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_RULE__PERMIT = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.RULE__PERMIT;

	/**
	 * The feature id for the '<em><b>Element</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_RULE__ELEMENT = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.RULE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Palette Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_RULE_FEATURE_COUNT = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.RULE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Palette Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PALETTE_RULE_OPERATION_COUNT = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.RULE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.gmfdiag.representation.impl.PathElementImpl <em>Path Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.impl.PathElementImpl
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.impl.RepresentationPackageImpl#getPathElement()
	 * @generated
	 */
	int PATH_ELEMENT = 3;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATH_ELEMENT__FEATURE = 0;

	/**
	 * The feature id for the '<em><b>Origin</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATH_ELEMENT__ORIGIN = 1;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATH_ELEMENT__TARGET = 2;

	/**
	 * The number of structural features of the '<em>Path Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATH_ELEMENT_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Path Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATH_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.gmfdiag.representation.impl.AssistantRuleImpl <em>Assistant Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.impl.AssistantRuleImpl
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.impl.RepresentationPackageImpl#getAssistantRule()
	 * @generated
	 */
	int ASSISTANT_RULE = 4;

	/**
	 * The feature id for the '<em><b>Permit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSISTANT_RULE__PERMIT = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.RULE__PERMIT;

	/**
	 * The feature id for the '<em><b>Element Type ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSISTANT_RULE__ELEMENT_TYPE_ID = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.RULE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Assistant Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSISTANT_RULE_FEATURE_COUNT = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.RULE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Matches</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSISTANT_RULE___MATCHES__IELEMENTTYPE = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.RULE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Assistant Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSISTANT_RULE_OPERATION_COUNT = org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage.RULE_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '<em>Element Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.gmf.runtime.emf.type.core.IElementType
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.impl.RepresentationPackageImpl#getElementType()
	 * @generated
	 */
	int ELEMENT_TYPE = 5;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram <em>Papyrus Diagram</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Papyrus Diagram</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram
	 * @generated
	 */
	EClass getPapyrusDiagram();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram#getCustomStyle <em>Custom Style</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Custom Style</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram#getCustomStyle()
	 * @see #getPapyrusDiagram()
	 * @generated
	 */
	EAttribute getPapyrusDiagram_CustomStyle();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram#getChildRules <em>Child Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Child Rules</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram#getChildRules()
	 * @see #getPapyrusDiagram()
	 * @generated
	 */
	EReference getPapyrusDiagram_ChildRules();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram#getPaletteRules <em>Palette Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Palette Rules</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram#getPaletteRules()
	 * @see #getPapyrusDiagram()
	 * @generated
	 */
	EReference getPapyrusDiagram_PaletteRules();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram#getAssistantRules <em>Assistant Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Assistant Rules</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram#getAssistantRules()
	 * @see #getPapyrusDiagram()
	 * @generated
	 */
	EReference getPapyrusDiagram_AssistantRules();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram#getCreationCommandClass <em>Creation Command Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Creation Command Class</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram#getCreationCommandClass()
	 * @see #getPapyrusDiagram()
	 * @generated
	 */
	EAttribute getPapyrusDiagram_CreationCommandClass();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram#getPalettes <em>Palettes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Palettes</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram#getPalettes()
	 * @see #getPapyrusDiagram()
	 * @generated
	 */
	EReference getPapyrusDiagram_Palettes();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram#ceationCommandClassExists(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Ceation Command Class Exists</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Ceation Command Class Exists</em>' operation.
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram#ceationCommandClassExists(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getPapyrusDiagram__CeationCommandClassExists__DiagnosticChain_Map();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.gmfdiag.representation.ChildRule <em>Child Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Child Rule</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.ChildRule
	 * @generated
	 */
	EClass getChildRule();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.gmfdiag.representation.ChildRule#getElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Element</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.ChildRule#getElement()
	 * @see #getChildRule()
	 * @generated
	 */
	EReference getChildRule_Element();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.infra.gmfdiag.representation.ChildRule#getStereotypes <em>Stereotypes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Stereotypes</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.ChildRule#getStereotypes()
	 * @see #getChildRule()
	 * @generated
	 */
	EReference getChildRule_Stereotypes();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.gmfdiag.representation.ChildRule#getOrigin <em>Origin</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Origin</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.ChildRule#getOrigin()
	 * @see #getChildRule()
	 * @generated
	 */
	EReference getChildRule_Origin();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.gmfdiag.representation.ChildRule#getInsertionPath <em>Insertion Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Insertion Path</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.ChildRule#getInsertionPath()
	 * @see #getChildRule()
	 * @generated
	 */
	EReference getChildRule_InsertionPath();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.gmfdiag.representation.PaletteRule <em>Palette Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Palette Rule</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.PaletteRule
	 * @generated
	 */
	EClass getPaletteRule();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.gmfdiag.representation.PaletteRule#getElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Element</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.PaletteRule#getElement()
	 * @see #getPaletteRule()
	 * @generated
	 */
	EAttribute getPaletteRule_Element();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.gmfdiag.representation.PathElement <em>Path Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Path Element</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.PathElement
	 * @generated
	 */
	EClass getPathElement();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.gmfdiag.representation.PathElement#getFeature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Feature</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.PathElement#getFeature()
	 * @see #getPathElement()
	 * @generated
	 */
	EReference getPathElement_Feature();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.gmfdiag.representation.PathElement#getOrigin <em>Origin</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Origin</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.PathElement#getOrigin()
	 * @see #getPathElement()
	 * @generated
	 */
	EReference getPathElement_Origin();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.gmfdiag.representation.PathElement#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.PathElement#getTarget()
	 * @see #getPathElement()
	 * @generated
	 */
	EReference getPathElement_Target();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.gmfdiag.representation.AssistantRule <em>Assistant Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Assistant Rule</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.AssistantRule
	 * @generated
	 */
	EClass getAssistantRule();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.gmfdiag.representation.AssistantRule#getElementTypeID <em>Element Type ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Element Type ID</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.AssistantRule#getElementTypeID()
	 * @see #getAssistantRule()
	 * @generated
	 */
	EAttribute getAssistantRule_ElementTypeID();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.gmfdiag.representation.AssistantRule#matches(org.eclipse.gmf.runtime.emf.type.core.IElementType) <em>Matches</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Matches</em>' operation.
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.AssistantRule#matches(org.eclipse.gmf.runtime.emf.type.core.IElementType)
	 * @generated
	 */
	EOperation getAssistantRule__Matches__IElementType();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.gmf.runtime.emf.type.core.IElementType <em>Element Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Element Type</em>'.
	 * @see org.eclipse.gmf.runtime.emf.type.core.IElementType
	 * @model instanceClass="org.eclipse.gmf.runtime.emf.type.core.IElementType"
	 * @generated
	 */
	EDataType getElementType();

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
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.gmfdiag.representation.impl.PapyrusDiagramImpl <em>Papyrus Diagram</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.gmfdiag.representation.impl.PapyrusDiagramImpl
		 * @see org.eclipse.papyrus.infra.gmfdiag.representation.impl.RepresentationPackageImpl#getPapyrusDiagram()
		 * @generated
		 */
		EClass PAPYRUS_DIAGRAM = eINSTANCE.getPapyrusDiagram();

		/**
		 * The meta object literal for the '<em><b>Custom Style</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAPYRUS_DIAGRAM__CUSTOM_STYLE = eINSTANCE.getPapyrusDiagram_CustomStyle();

		/**
		 * The meta object literal for the '<em><b>Child Rules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAPYRUS_DIAGRAM__CHILD_RULES = eINSTANCE.getPapyrusDiagram_ChildRules();

		/**
		 * The meta object literal for the '<em><b>Palette Rules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAPYRUS_DIAGRAM__PALETTE_RULES = eINSTANCE.getPapyrusDiagram_PaletteRules();

		/**
		 * The meta object literal for the '<em><b>Assistant Rules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAPYRUS_DIAGRAM__ASSISTANT_RULES = eINSTANCE.getPapyrusDiagram_AssistantRules();

		/**
		 * The meta object literal for the '<em><b>Creation Command Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAPYRUS_DIAGRAM__CREATION_COMMAND_CLASS = eINSTANCE.getPapyrusDiagram_CreationCommandClass();

		/**
		 * The meta object literal for the '<em><b>Palettes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAPYRUS_DIAGRAM__PALETTES = eINSTANCE.getPapyrusDiagram_Palettes();

		/**
		 * The meta object literal for the '<em><b>Ceation Command Class Exists</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PAPYRUS_DIAGRAM___CEATION_COMMAND_CLASS_EXISTS__DIAGNOSTICCHAIN_MAP = eINSTANCE.getPapyrusDiagram__CeationCommandClassExists__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.gmfdiag.representation.impl.ChildRuleImpl <em>Child Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.gmfdiag.representation.impl.ChildRuleImpl
		 * @see org.eclipse.papyrus.infra.gmfdiag.representation.impl.RepresentationPackageImpl#getChildRule()
		 * @generated
		 */
		EClass CHILD_RULE = eINSTANCE.getChildRule();

		/**
		 * The meta object literal for the '<em><b>Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHILD_RULE__ELEMENT = eINSTANCE.getChildRule_Element();

		/**
		 * The meta object literal for the '<em><b>Stereotypes</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHILD_RULE__STEREOTYPES = eINSTANCE.getChildRule_Stereotypes();

		/**
		 * The meta object literal for the '<em><b>Origin</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHILD_RULE__ORIGIN = eINSTANCE.getChildRule_Origin();

		/**
		 * The meta object literal for the '<em><b>Insertion Path</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHILD_RULE__INSERTION_PATH = eINSTANCE.getChildRule_InsertionPath();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.gmfdiag.representation.impl.PaletteRuleImpl <em>Palette Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.gmfdiag.representation.impl.PaletteRuleImpl
		 * @see org.eclipse.papyrus.infra.gmfdiag.representation.impl.RepresentationPackageImpl#getPaletteRule()
		 * @generated
		 */
		EClass PALETTE_RULE = eINSTANCE.getPaletteRule();

		/**
		 * The meta object literal for the '<em><b>Element</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PALETTE_RULE__ELEMENT = eINSTANCE.getPaletteRule_Element();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.gmfdiag.representation.impl.PathElementImpl <em>Path Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.gmfdiag.representation.impl.PathElementImpl
		 * @see org.eclipse.papyrus.infra.gmfdiag.representation.impl.RepresentationPackageImpl#getPathElement()
		 * @generated
		 */
		EClass PATH_ELEMENT = eINSTANCE.getPathElement();

		/**
		 * The meta object literal for the '<em><b>Feature</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATH_ELEMENT__FEATURE = eINSTANCE.getPathElement_Feature();

		/**
		 * The meta object literal for the '<em><b>Origin</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATH_ELEMENT__ORIGIN = eINSTANCE.getPathElement_Origin();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PATH_ELEMENT__TARGET = eINSTANCE.getPathElement_Target();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.gmfdiag.representation.impl.AssistantRuleImpl <em>Assistant Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.gmfdiag.representation.impl.AssistantRuleImpl
		 * @see org.eclipse.papyrus.infra.gmfdiag.representation.impl.RepresentationPackageImpl#getAssistantRule()
		 * @generated
		 */
		EClass ASSISTANT_RULE = eINSTANCE.getAssistantRule();

		/**
		 * The meta object literal for the '<em><b>Element Type ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASSISTANT_RULE__ELEMENT_TYPE_ID = eINSTANCE.getAssistantRule_ElementTypeID();

		/**
		 * The meta object literal for the '<em><b>Matches</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ASSISTANT_RULE___MATCHES__IELEMENTTYPE = eINSTANCE.getAssistantRule__Matches__IElementType();

		/**
		 * The meta object literal for the '<em>Element Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.gmf.runtime.emf.type.core.IElementType
		 * @see org.eclipse.papyrus.infra.gmfdiag.representation.impl.RepresentationPackageImpl#getElementType()
		 * @generated
		 */
		EDataType ELEMENT_TYPE = eINSTANCE.getElementType();

	}

} //RepresentationPackage
