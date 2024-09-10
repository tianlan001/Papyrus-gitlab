/**
 * Copyright (c) 2015 CEA LIST.
 *  
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *  
 * Contributors:
 * 	CEA LIST - Initial API and implementation
 * 
 */
package org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.ExpansionModelFactory
 * @model kind="package"
 * @generated
 */
public interface ExpansionModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "expansionmodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///expansionmodel.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "expansionmodel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ExpansionModelPackage eINSTANCE = org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.ExpansionModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.AbstractRepresentationImpl <em>Abstract Representation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.AbstractRepresentationImpl
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.ExpansionModelPackageImpl#getAbstractRepresentation()
	 * @generated
	 */
	int ABSTRACT_REPRESENTATION = 1;

	/**
	 * The feature id for the '<em><b>Edit Part Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REPRESENTATION__EDIT_PART_QUALIFIED_NAME = 0;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REPRESENTATION__KIND = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REPRESENTATION__NAME = 2;

	/**
	 * The feature id for the '<em><b>View Factory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REPRESENTATION__VIEW_FACTORY = 3;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REPRESENTATION__DESCRIPTION = 4;

	/**
	 * The number of structural features of the '<em>Abstract Representation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REPRESENTATION_FEATURE_COUNT = 5;

	/**
	 * The operation id for the '<em>Validate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REPRESENTATION___VALIDATE__DIAGNOSTICCHAIN_MAP = 0;

	/**
	 * The number of operations of the '<em>Abstract Representation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_REPRESENTATION_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.RepresentationImpl <em>Representation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.RepresentationImpl
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.ExpansionModelPackageImpl#getRepresentation()
	 * @generated
	 */
	int REPRESENTATION = 0;

	/**
	 * The feature id for the '<em><b>Edit Part Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPRESENTATION__EDIT_PART_QUALIFIED_NAME = ABSTRACT_REPRESENTATION__EDIT_PART_QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPRESENTATION__KIND = ABSTRACT_REPRESENTATION__KIND;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPRESENTATION__NAME = ABSTRACT_REPRESENTATION__NAME;

	/**
	 * The feature id for the '<em><b>View Factory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPRESENTATION__VIEW_FACTORY = ABSTRACT_REPRESENTATION__VIEW_FACTORY;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPRESENTATION__DESCRIPTION = ABSTRACT_REPRESENTATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Induced Representations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPRESENTATION__INDUCED_REPRESENTATIONS = ABSTRACT_REPRESENTATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Sub Representations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPRESENTATION__SUB_REPRESENTATIONS = ABSTRACT_REPRESENTATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Graphical Element Type Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPRESENTATION__GRAPHICAL_ELEMENT_TYPE_REF = ABSTRACT_REPRESENTATION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Representation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPRESENTATION_FEATURE_COUNT = ABSTRACT_REPRESENTATION_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Validate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPRESENTATION___VALIDATE__DIAGNOSTICCHAIN_MAP = ABSTRACT_REPRESENTATION___VALIDATE__DIAGNOSTICCHAIN_MAP;

	/**
	 * The number of operations of the '<em>Representation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPRESENTATION_OPERATION_COUNT = ABSTRACT_REPRESENTATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.RepresentationKindImpl <em>Representation Kind</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.RepresentationKindImpl
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.ExpansionModelPackageImpl#getRepresentationKind()
	 * @generated
	 */
	int REPRESENTATION_KIND = 2;

	/**
	 * The feature id for the '<em><b>Edit Part Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPRESENTATION_KIND__EDIT_PART_QUALIFIED_NAME = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPRESENTATION_KIND__NAME = 1;

	/**
	 * The feature id for the '<em><b>View Factory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPRESENTATION_KIND__VIEW_FACTORY = 2;

	/**
	 * The number of structural features of the '<em>Representation Kind</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPRESENTATION_KIND_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Representation Kind</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REPRESENTATION_KIND_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.InducedRepresentationImpl <em>Induced Representation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.InducedRepresentationImpl
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.ExpansionModelPackageImpl#getInducedRepresentation()
	 * @generated
	 */
	int INDUCED_REPRESENTATION = 3;

	/**
	 * The feature id for the '<em><b>Edit Part Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDUCED_REPRESENTATION__EDIT_PART_QUALIFIED_NAME = ABSTRACT_REPRESENTATION__EDIT_PART_QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDUCED_REPRESENTATION__KIND = ABSTRACT_REPRESENTATION__KIND;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDUCED_REPRESENTATION__NAME = ABSTRACT_REPRESENTATION__NAME;

	/**
	 * The feature id for the '<em><b>View Factory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDUCED_REPRESENTATION__VIEW_FACTORY = ABSTRACT_REPRESENTATION__VIEW_FACTORY;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDUCED_REPRESENTATION__DESCRIPTION = ABSTRACT_REPRESENTATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Hint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDUCED_REPRESENTATION__HINT = ABSTRACT_REPRESENTATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Children</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDUCED_REPRESENTATION__CHILDREN = ABSTRACT_REPRESENTATION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Induced Representation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDUCED_REPRESENTATION_FEATURE_COUNT = ABSTRACT_REPRESENTATION_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Validate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDUCED_REPRESENTATION___VALIDATE__DIAGNOSTICCHAIN_MAP = ABSTRACT_REPRESENTATION___VALIDATE__DIAGNOSTICCHAIN_MAP;

	/**
	 * The number of operations of the '<em>Induced Representation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INDUCED_REPRESENTATION_OPERATION_COUNT = ABSTRACT_REPRESENTATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.GraphicalElementLibraryImpl <em>Graphical Element Library</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.GraphicalElementLibraryImpl
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.ExpansionModelPackageImpl#getGraphicalElementLibrary()
	 * @generated
	 */
	int GRAPHICAL_ELEMENT_LIBRARY = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPHICAL_ELEMENT_LIBRARY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Representationkinds</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPHICAL_ELEMENT_LIBRARY__REPRESENTATIONKINDS = 1;

	/**
	 * The feature id for the '<em><b>Representations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPHICAL_ELEMENT_LIBRARY__REPRESENTATIONS = 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPHICAL_ELEMENT_LIBRARY__DESCRIPTION = 3;

	/**
	 * The number of structural features of the '<em>Graphical Element Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPHICAL_ELEMENT_LIBRARY_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Graphical Element Library</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GRAPHICAL_ELEMENT_LIBRARY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.UseContextImpl <em>Use Context</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.UseContextImpl
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.ExpansionModelPackageImpl#getUseContext()
	 * @generated
	 */
	int USE_CONTEXT = 5;

	/**
	 * The feature id for the '<em><b>Diagram Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USE_CONTEXT__DIAGRAM_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Representations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USE_CONTEXT__REPRESENTATIONS = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USE_CONTEXT__NAME = 2;

	/**
	 * The feature id for the '<em><b>Gmft Representations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USE_CONTEXT__GMFT_REPRESENTATIONS = 3;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USE_CONTEXT__DESCRIPTION = 4;

	/**
	 * The number of structural features of the '<em>Use Context</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USE_CONTEXT_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Use Context</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USE_CONTEXT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.GMFT_BasedRepresentationImpl <em>GMFT Based Representation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.GMFT_BasedRepresentationImpl
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.ExpansionModelPackageImpl#getGMFT_BasedRepresentation()
	 * @generated
	 */
	int GMFT_BASED_REPRESENTATION = 6;

	/**
	 * The feature id for the '<em><b>Edit Part Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GMFT_BASED_REPRESENTATION__EDIT_PART_QUALIFIED_NAME = REPRESENTATION__EDIT_PART_QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GMFT_BASED_REPRESENTATION__KIND = REPRESENTATION__KIND;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GMFT_BASED_REPRESENTATION__NAME = REPRESENTATION__NAME;

	/**
	 * The feature id for the '<em><b>View Factory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GMFT_BASED_REPRESENTATION__VIEW_FACTORY = REPRESENTATION__VIEW_FACTORY;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GMFT_BASED_REPRESENTATION__DESCRIPTION = REPRESENTATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Induced Representations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GMFT_BASED_REPRESENTATION__INDUCED_REPRESENTATIONS = REPRESENTATION__INDUCED_REPRESENTATIONS;

	/**
	 * The feature id for the '<em><b>Sub Representations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GMFT_BASED_REPRESENTATION__SUB_REPRESENTATIONS = REPRESENTATION__SUB_REPRESENTATIONS;

	/**
	 * The feature id for the '<em><b>Graphical Element Type Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GMFT_BASED_REPRESENTATION__GRAPHICAL_ELEMENT_TYPE_REF = REPRESENTATION__GRAPHICAL_ELEMENT_TYPE_REF;

	/**
	 * The feature id for the '<em><b>Reused ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GMFT_BASED_REPRESENTATION__REUSED_ID = REPRESENTATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>GMFT Based Representation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GMFT_BASED_REPRESENTATION_FEATURE_COUNT = REPRESENTATION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Validate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GMFT_BASED_REPRESENTATION___VALIDATE__DIAGNOSTICCHAIN_MAP = REPRESENTATION___VALIDATE__DIAGNOSTICCHAIN_MAP;

	/**
	 * The number of operations of the '<em>GMFT Based Representation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GMFT_BASED_REPRESENTATION_OPERATION_COUNT = REPRESENTATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.DiagramExpansionImpl <em>Diagram Expansion</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.DiagramExpansionImpl
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.ExpansionModelPackageImpl#getDiagramExpansion()
	 * @generated
	 */
	int DIAGRAM_EXPANSION = 7;

	/**
	 * The feature id for the '<em><b>Usages</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_EXPANSION__USAGES = 0;

	/**
	 * The feature id for the '<em><b>Libraries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_EXPANSION__LIBRARIES = 1;

	/**
	 * The feature id for the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_EXPANSION__ID = 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_EXPANSION__DESCRIPTION = 3;

	/**
	 * The number of structural features of the '<em>Diagram Expansion</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_EXPANSION_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Diagram Expansion</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_EXPANSION_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.Representation <em>Representation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Representation</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.Representation
	 * @generated
	 */
	EClass getRepresentation();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.Representation#getInducedRepresentations <em>Induced Representations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Induced Representations</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.Representation#getInducedRepresentations()
	 * @see #getRepresentation()
	 * @generated
	 */
	EReference getRepresentation_InducedRepresentations();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.Representation#getSubRepresentations <em>Sub Representations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Sub Representations</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.Representation#getSubRepresentations()
	 * @see #getRepresentation()
	 * @generated
	 */
	EReference getRepresentation_SubRepresentations();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.Representation#getGraphicalElementTypeRef <em>Graphical Element Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Graphical Element Type Ref</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.Representation#getGraphicalElementTypeRef()
	 * @see #getRepresentation()
	 * @generated
	 */
	EReference getRepresentation_GraphicalElementTypeRef();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation <em>Abstract Representation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Representation</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation
	 * @generated
	 */
	EClass getAbstractRepresentation();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation#getEditPartQualifiedName <em>Edit Part Qualified Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Edit Part Qualified Name</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation#getEditPartQualifiedName()
	 * @see #getAbstractRepresentation()
	 * @generated
	 */
	EAttribute getAbstractRepresentation_EditPartQualifiedName();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Kind</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation#getKind()
	 * @see #getAbstractRepresentation()
	 * @generated
	 */
	EReference getAbstractRepresentation_Kind();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation#getName()
	 * @see #getAbstractRepresentation()
	 * @generated
	 */
	EAttribute getAbstractRepresentation_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation#getViewFactory <em>View Factory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>View Factory</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation#getViewFactory()
	 * @see #getAbstractRepresentation()
	 * @generated
	 */
	EAttribute getAbstractRepresentation_ViewFactory();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation#getDescription()
	 * @see #getAbstractRepresentation()
	 * @generated
	 */
	EAttribute getAbstractRepresentation_Description();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation#validate(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Validate</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Validate</em>' operation.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation#validate(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated
	 */
	EOperation getAbstractRepresentation__Validate__DiagnosticChain_Map();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.RepresentationKind <em>Representation Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Representation Kind</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.RepresentationKind
	 * @generated
	 */
	EClass getRepresentationKind();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.RepresentationKind#getEditPartQualifiedName <em>Edit Part Qualified Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Edit Part Qualified Name</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.RepresentationKind#getEditPartQualifiedName()
	 * @see #getRepresentationKind()
	 * @generated
	 */
	EAttribute getRepresentationKind_EditPartQualifiedName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.RepresentationKind#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.RepresentationKind#getName()
	 * @see #getRepresentationKind()
	 * @generated
	 */
	EAttribute getRepresentationKind_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.RepresentationKind#getViewFactory <em>View Factory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>View Factory</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.RepresentationKind#getViewFactory()
	 * @see #getRepresentationKind()
	 * @generated
	 */
	EAttribute getRepresentationKind_ViewFactory();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.InducedRepresentation <em>Induced Representation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Induced Representation</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.InducedRepresentation
	 * @generated
	 */
	EClass getInducedRepresentation();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.InducedRepresentation#getHint <em>Hint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hint</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.InducedRepresentation#getHint()
	 * @see #getInducedRepresentation()
	 * @generated
	 */
	EAttribute getInducedRepresentation_Hint();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.InducedRepresentation#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Children</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.InducedRepresentation#getChildren()
	 * @see #getInducedRepresentation()
	 * @generated
	 */
	EReference getInducedRepresentation_Children();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.GraphicalElementLibrary <em>Graphical Element Library</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Graphical Element Library</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.GraphicalElementLibrary
	 * @generated
	 */
	EClass getGraphicalElementLibrary();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.GraphicalElementLibrary#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.GraphicalElementLibrary#getName()
	 * @see #getGraphicalElementLibrary()
	 * @generated
	 */
	EAttribute getGraphicalElementLibrary_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.GraphicalElementLibrary#getRepresentationkinds <em>Representationkinds</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Representationkinds</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.GraphicalElementLibrary#getRepresentationkinds()
	 * @see #getGraphicalElementLibrary()
	 * @generated
	 */
	EReference getGraphicalElementLibrary_Representationkinds();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.GraphicalElementLibrary#getRepresentations <em>Representations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Representations</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.GraphicalElementLibrary#getRepresentations()
	 * @see #getGraphicalElementLibrary()
	 * @generated
	 */
	EReference getGraphicalElementLibrary_Representations();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.GraphicalElementLibrary#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.GraphicalElementLibrary#getDescription()
	 * @see #getGraphicalElementLibrary()
	 * @generated
	 */
	EAttribute getGraphicalElementLibrary_Description();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.UseContext <em>Use Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Use Context</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.UseContext
	 * @generated
	 */
	EClass getUseContext();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.UseContext#getDiagramType <em>Diagram Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Diagram Type</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.UseContext#getDiagramType()
	 * @see #getUseContext()
	 * @generated
	 */
	EAttribute getUseContext_DiagramType();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.UseContext#getRepresentations <em>Representations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Representations</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.UseContext#getRepresentations()
	 * @see #getUseContext()
	 * @generated
	 */
	EReference getUseContext_Representations();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.UseContext#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.UseContext#getName()
	 * @see #getUseContext()
	 * @generated
	 */
	EAttribute getUseContext_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.UseContext#getGmftRepresentations <em>Gmft Representations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Gmft Representations</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.UseContext#getGmftRepresentations()
	 * @see #getUseContext()
	 * @generated
	 */
	EReference getUseContext_GmftRepresentations();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.UseContext#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.UseContext#getDescription()
	 * @see #getUseContext()
	 * @generated
	 */
	EAttribute getUseContext_Description();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.GMFT_BasedRepresentation <em>GMFT Based Representation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>GMFT Based Representation</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.GMFT_BasedRepresentation
	 * @generated
	 */
	EClass getGMFT_BasedRepresentation();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.GMFT_BasedRepresentation#getReusedID <em>Reused ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Reused ID</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.GMFT_BasedRepresentation#getReusedID()
	 * @see #getGMFT_BasedRepresentation()
	 * @generated
	 */
	EAttribute getGMFT_BasedRepresentation_ReusedID();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.DiagramExpansion <em>Diagram Expansion</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Diagram Expansion</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.DiagramExpansion
	 * @generated
	 */
	EClass getDiagramExpansion();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.DiagramExpansion#getUsages <em>Usages</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Usages</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.DiagramExpansion#getUsages()
	 * @see #getDiagramExpansion()
	 * @generated
	 */
	EReference getDiagramExpansion_Usages();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.DiagramExpansion#getLibraries <em>Libraries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Libraries</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.DiagramExpansion#getLibraries()
	 * @see #getDiagramExpansion()
	 * @generated
	 */
	EReference getDiagramExpansion_Libraries();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.DiagramExpansion#getID <em>ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>ID</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.DiagramExpansion#getID()
	 * @see #getDiagramExpansion()
	 * @generated
	 */
	EAttribute getDiagramExpansion_ID();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.DiagramExpansion#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.DiagramExpansion#getDescription()
	 * @see #getDiagramExpansion()
	 * @generated
	 */
	EAttribute getDiagramExpansion_Description();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ExpansionModelFactory getExpansionModelFactory();

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
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.RepresentationImpl <em>Representation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.RepresentationImpl
		 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.ExpansionModelPackageImpl#getRepresentation()
		 * @generated
		 */
		EClass REPRESENTATION = eINSTANCE.getRepresentation();

		/**
		 * The meta object literal for the '<em><b>Induced Representations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPRESENTATION__INDUCED_REPRESENTATIONS = eINSTANCE.getRepresentation_InducedRepresentations();

		/**
		 * The meta object literal for the '<em><b>Sub Representations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPRESENTATION__SUB_REPRESENTATIONS = eINSTANCE.getRepresentation_SubRepresentations();

		/**
		 * The meta object literal for the '<em><b>Graphical Element Type Ref</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REPRESENTATION__GRAPHICAL_ELEMENT_TYPE_REF = eINSTANCE.getRepresentation_GraphicalElementTypeRef();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.AbstractRepresentationImpl <em>Abstract Representation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.AbstractRepresentationImpl
		 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.ExpansionModelPackageImpl#getAbstractRepresentation()
		 * @generated
		 */
		EClass ABSTRACT_REPRESENTATION = eINSTANCE.getAbstractRepresentation();

		/**
		 * The meta object literal for the '<em><b>Edit Part Qualified Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_REPRESENTATION__EDIT_PART_QUALIFIED_NAME = eINSTANCE.getAbstractRepresentation_EditPartQualifiedName();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_REPRESENTATION__KIND = eINSTANCE.getAbstractRepresentation_Kind();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_REPRESENTATION__NAME = eINSTANCE.getAbstractRepresentation_Name();

		/**
		 * The meta object literal for the '<em><b>View Factory</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_REPRESENTATION__VIEW_FACTORY = eINSTANCE.getAbstractRepresentation_ViewFactory();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_REPRESENTATION__DESCRIPTION = eINSTANCE.getAbstractRepresentation_Description();

		/**
		 * The meta object literal for the '<em><b>Validate</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ABSTRACT_REPRESENTATION___VALIDATE__DIAGNOSTICCHAIN_MAP = eINSTANCE.getAbstractRepresentation__Validate__DiagnosticChain_Map();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.RepresentationKindImpl <em>Representation Kind</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.RepresentationKindImpl
		 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.ExpansionModelPackageImpl#getRepresentationKind()
		 * @generated
		 */
		EClass REPRESENTATION_KIND = eINSTANCE.getRepresentationKind();

		/**
		 * The meta object literal for the '<em><b>Edit Part Qualified Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPRESENTATION_KIND__EDIT_PART_QUALIFIED_NAME = eINSTANCE.getRepresentationKind_EditPartQualifiedName();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPRESENTATION_KIND__NAME = eINSTANCE.getRepresentationKind_Name();

		/**
		 * The meta object literal for the '<em><b>View Factory</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REPRESENTATION_KIND__VIEW_FACTORY = eINSTANCE.getRepresentationKind_ViewFactory();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.InducedRepresentationImpl <em>Induced Representation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.InducedRepresentationImpl
		 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.ExpansionModelPackageImpl#getInducedRepresentation()
		 * @generated
		 */
		EClass INDUCED_REPRESENTATION = eINSTANCE.getInducedRepresentation();

		/**
		 * The meta object literal for the '<em><b>Hint</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INDUCED_REPRESENTATION__HINT = eINSTANCE.getInducedRepresentation_Hint();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INDUCED_REPRESENTATION__CHILDREN = eINSTANCE.getInducedRepresentation_Children();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.GraphicalElementLibraryImpl <em>Graphical Element Library</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.GraphicalElementLibraryImpl
		 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.ExpansionModelPackageImpl#getGraphicalElementLibrary()
		 * @generated
		 */
		EClass GRAPHICAL_ELEMENT_LIBRARY = eINSTANCE.getGraphicalElementLibrary();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRAPHICAL_ELEMENT_LIBRARY__NAME = eINSTANCE.getGraphicalElementLibrary_Name();

		/**
		 * The meta object literal for the '<em><b>Representationkinds</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GRAPHICAL_ELEMENT_LIBRARY__REPRESENTATIONKINDS = eINSTANCE.getGraphicalElementLibrary_Representationkinds();

		/**
		 * The meta object literal for the '<em><b>Representations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GRAPHICAL_ELEMENT_LIBRARY__REPRESENTATIONS = eINSTANCE.getGraphicalElementLibrary_Representations();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GRAPHICAL_ELEMENT_LIBRARY__DESCRIPTION = eINSTANCE.getGraphicalElementLibrary_Description();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.UseContextImpl <em>Use Context</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.UseContextImpl
		 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.ExpansionModelPackageImpl#getUseContext()
		 * @generated
		 */
		EClass USE_CONTEXT = eINSTANCE.getUseContext();

		/**
		 * The meta object literal for the '<em><b>Diagram Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USE_CONTEXT__DIAGRAM_TYPE = eINSTANCE.getUseContext_DiagramType();

		/**
		 * The meta object literal for the '<em><b>Representations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USE_CONTEXT__REPRESENTATIONS = eINSTANCE.getUseContext_Representations();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USE_CONTEXT__NAME = eINSTANCE.getUseContext_Name();

		/**
		 * The meta object literal for the '<em><b>Gmft Representations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USE_CONTEXT__GMFT_REPRESENTATIONS = eINSTANCE.getUseContext_GmftRepresentations();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USE_CONTEXT__DESCRIPTION = eINSTANCE.getUseContext_Description();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.GMFT_BasedRepresentationImpl <em>GMFT Based Representation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.GMFT_BasedRepresentationImpl
		 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.ExpansionModelPackageImpl#getGMFT_BasedRepresentation()
		 * @generated
		 */
		EClass GMFT_BASED_REPRESENTATION = eINSTANCE.getGMFT_BasedRepresentation();

		/**
		 * The meta object literal for the '<em><b>Reused ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GMFT_BASED_REPRESENTATION__REUSED_ID = eINSTANCE.getGMFT_BasedRepresentation_ReusedID();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.DiagramExpansionImpl <em>Diagram Expansion</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.DiagramExpansionImpl
		 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.ExpansionModelPackageImpl#getDiagramExpansion()
		 * @generated
		 */
		EClass DIAGRAM_EXPANSION = eINSTANCE.getDiagramExpansion();

		/**
		 * The meta object literal for the '<em><b>Usages</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIAGRAM_EXPANSION__USAGES = eINSTANCE.getDiagramExpansion_Usages();

		/**
		 * The meta object literal for the '<em><b>Libraries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIAGRAM_EXPANSION__LIBRARIES = eINSTANCE.getDiagramExpansion_Libraries();

		/**
		 * The meta object literal for the '<em><b>ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIAGRAM_EXPANSION__ID = eINSTANCE.getDiagramExpansion_ID();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIAGRAM_EXPANSION__DESCRIPTION = eINSTANCE.getDiagramExpansion_Description();

	}

} //ExpansionModelPackage
