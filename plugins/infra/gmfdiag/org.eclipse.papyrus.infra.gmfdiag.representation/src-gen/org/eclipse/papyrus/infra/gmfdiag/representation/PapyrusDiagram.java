/**
 * Copyright (c) 2017, 2021 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bugs 539694, 573890
 *  
 * 
 */
package org.eclipse.papyrus.infra.gmfdiag.representation;

import java.util.Map;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteConfiguration;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Papyrus Diagram</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A representation kind that depicts elements form a UML model as a diagram
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram#getCustomStyle <em>Custom Style</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram#getChildRules <em>Child Rules</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram#getPaletteRules <em>Palette Rules</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram#getAssistantRules <em>Assistant Rules</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram#getCreationCommandClass <em>Creation Command Class</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram#getPalettes <em>Palettes</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.gmfdiag.representation.RepresentationPackage#getPapyrusDiagram()
 * @model
 * @generated
 */
public interface PapyrusDiagram extends PapyrusRepresentationKind {
	/**
	 * Returns the value of the '<em><b>Custom Style</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Custom Style</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The custom style for the diagram specified using a platform plugin URI of a CSS file
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Custom Style</em>' attribute.
	 * @see #setCustomStyle(String)
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.RepresentationPackage#getPapyrusDiagram_CustomStyle()
	 * @model
	 * @generated
	 */
	String getCustomStyle();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram#getCustomStyle <em>Custom Style</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Custom Style</em>' attribute.
	 * @see #getCustomStyle()
	 * @generated
	 */
	void setCustomStyle(String value);

	/**
	 * Returns the value of the '<em><b>Child Rules</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.gmfdiag.representation.ChildRule}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The set of rules that determine which model elements can be added as child of the origin through this diagram
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Child Rules</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.RepresentationPackage#getPapyrusDiagram_ChildRules()
	 * @model containment="true"
	 * @generated
	 */
	EList<ChildRule> getChildRules();

	/**
	 * Returns the value of the '<em><b>Palette Rules</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.gmfdiag.representation.PaletteRule}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * These set of rules that determine which palette elements should be available for this diagram
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Palette Rules</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.RepresentationPackage#getPapyrusDiagram_PaletteRules()
	 * @model containment="true"
	 * @generated
	 */
	EList<PaletteRule> getPaletteRules();

	/**
	 * Returns the value of the '<em><b>Assistant Rules</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.gmfdiag.representation.AssistantRule}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The set of rules that determine which modeling assistants should be presented in this diagram
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Assistant Rules</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.RepresentationPackage#getPapyrusDiagram_AssistantRules()
	 * @model containment="true"
	 * @generated
	 */
	EList<AssistantRule> getAssistantRules();

	/**
	 * Returns the value of the '<em><b>Creation Command Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Creation Command Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The fully qualified name of a command that creates the diagram. The command must implement the org.eclipse.papyrus.commands.ICreationCommand inteface
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Creation Command Class</em>' attribute.
	 * @see #setCreationCommandClass(String)
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.RepresentationPackage#getPapyrusDiagram_CreationCommandClass()
	 * @model required="true"
	 *        annotation="http://www.eclipse.org/papyrus/infra/core/architecture classConstraint='bundleclass://org.eclipse.papyrus.infra.gmfdiag.commands/org.eclipse.papyrus.commands.ICreationCommand' requiredBundles='org.eclipse.papyrus.infra.gmfdiag.common'"
	 * @generated
	 */
	String getCreationCommandClass();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.gmfdiag.representation.PapyrusDiagram#getCreationCommandClass <em>Creation Command Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Creation Command Class</em>' attribute.
	 * @see #getCreationCommandClass()
	 * @generated
	 */
	void setCreationCommandClass(String value);

	/**
	 * Returns the value of the '<em><b>Palettes</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteConfiguration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Palettes</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A set of palette configurations used by the diagram (typically found in .paletteconfiguration files)
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Palettes</em>' reference list.
	 * @see org.eclipse.papyrus.infra.gmfdiag.representation.RepresentationPackage#getPapyrusDiagram_Palettes()
	 * @model
	 * @generated
	 */
	EList<PaletteConfiguration> getPalettes();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model diagnosticsUnique="false" contextUnique="false"
	 * @generated
	 */
	boolean ceationCommandClassExists(DiagnosticChain diagnostics, Map<Object, Object> context);

} // PapyrusDiagram
