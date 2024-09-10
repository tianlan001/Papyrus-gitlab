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
package org.eclipse.papyrus.infra.core.architecture;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Description Language</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A description language (from ISO 42010) represents in Papyrus a modeling language (e.g., UML, SysML). It has a unique id' that corresponds to that of an ''IClientContext from GMF. It references a metamodel, a set of profiles, a set of element type set configurations, a creation command (creates a model of this language), a conversion command (converts a model to this language), and and icon. It also has an optional extension prefix for its models. It can also contain a set of representation kinds (which can be either diagrams or tables) and a set of viewpoints.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureDescriptionLanguage#getRepresentationKinds <em>Representation Kinds</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureDescriptionLanguage#getMetamodel <em>Metamodel</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureDescriptionLanguage#getProfiles <em>Profiles</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureDescriptionLanguage#getTreeViewerConfigurations <em>Tree Viewer Configurations</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage#getArchitectureDescriptionLanguage()
 * @model
 * @generated
 */
public interface ArchitectureDescriptionLanguage extends ArchitectureContext {
	/**
	 * Returns the value of the '<em><b>Representation Kinds</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.core.architecture.RepresentationKind}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.core.architecture.RepresentationKind#getLanguage <em>Language</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Representation Kinds</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The set of representation kinds defined by the language
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Representation Kinds</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage#getArchitectureDescriptionLanguage_RepresentationKinds()
	 * @see org.eclipse.papyrus.infra.core.architecture.RepresentationKind#getLanguage
	 * @model opposite="language" containment="true"
	 * @generated
	 */
	EList<RepresentationKind> getRepresentationKinds();

	/**
	 * Returns the value of the '<em><b>Metamodel</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metamodel</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The metamodel used by the language
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Metamodel</em>' reference.
	 * @see #setMetamodel(EPackage)
	 * @see org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage#getArchitectureDescriptionLanguage_Metamodel()
	 * @model required="true"
	 * @generated
	 */
	EPackage getMetamodel();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureDescriptionLanguage#getMetamodel <em>Metamodel</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Metamodel</em>' reference.
	 * @see #getMetamodel()
	 * @generated
	 */
	void setMetamodel(EPackage value);

	/**
	 * Returns the value of the '<em><b>Profiles</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EPackage}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Profiles</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The set of UML profiles used by the language
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Profiles</em>' reference list.
	 * @see org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage#getArchitectureDescriptionLanguage_Profiles()
	 * @model
	 * @generated
	 */
	EList<EPackage> getProfiles();

	/**
	 * Returns the value of the '<em><b>Tree Viewer Configurations</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.core.architecture.TreeViewerConfiguration}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The set of TreeViewer configuration to apply for the ADL.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Tree Viewer Configurations</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage#getArchitectureDescriptionLanguage_TreeViewerConfigurations()
	 * @model containment="true"
	 * @generated
	 */
	EList<TreeViewerConfiguration> getTreeViewerConfigurations();

} // ArchitectureDescriptionLanguage
