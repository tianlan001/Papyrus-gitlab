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
 *  Christian W. Damus - bugs 539694, 570486
 *  
 * 
 */
package org.eclipse.papyrus.infra.core.architecture;

import java.util.Map;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Context</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * The superclass of architectural description languages or architecture frameworks. It defines a collection of viewpoints on models.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#getViewpoints <em>Viewpoints</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#getDefaultViewpoints <em>Default Viewpoints</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#getElementTypes <em>Element Types</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#getDomain <em>Domain</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#getExtensionPrefix <em>Extension Prefix</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#getCreationCommandClass <em>Creation Command Class</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#getConversionCommandClass <em>Conversion Command Class</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#getGeneralContext <em>General Context</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#getExtendedContexts <em>Extended Contexts</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#isExtension <em>Extension</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage#getArchitectureContext()
 * @model abstract="true"
 * @generated
 */
public interface ArchitectureContext extends ADElement {
	/**
	 * Returns the value of the '<em><b>Viewpoints</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.core.architecture.ArchitectureViewpoint}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureViewpoint#getContext <em>Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Viewpoints</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The set of viewpoints defined by the context
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Viewpoints</em>' containment reference list.
	 * @see org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage#getArchitectureContext_Viewpoints()
	 * @see org.eclipse.papyrus.infra.core.architecture.ArchitectureViewpoint#getContext
	 * @model opposite="context" containment="true"
	 * @generated
	 */
	EList<ArchitectureViewpoint> getViewpoints();

	/**
	 * Returns the value of the '<em><b>Default Viewpoints</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.core.architecture.ArchitectureViewpoint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Viewpoints</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The set of viewpoints that are selected for the context by default
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Default Viewpoints</em>' reference list.
	 * @see org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage#getArchitectureContext_DefaultViewpoints()
	 * @model
	 * @generated
	 */
	EList<ArchitectureViewpoint> getDefaultViewpoints();

	/**
	 * Returns the value of the '<em><b>Element Types</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element Types</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The set of element type set configurations that are used by the context (typically found in .elementtypesconfigurations resources)
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Element Types</em>' reference list.
	 * @see org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage#getArchitectureContext_ElementTypes()
	 * @model
	 * @generated
	 */
	EList<ElementTypeSetConfiguration> getElementTypes();

	/**
	 * Returns the value of the '<em><b>Extension Prefix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extension Prefix</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The prefix of the file extension of the UML models applying this context (e.g., <Name>.<prefixExtension>.uml)
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Extension Prefix</em>' attribute.
	 * @see #setExtensionPrefix(String)
	 * @see org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage#getArchitectureContext_ExtensionPrefix()
	 * @model
	 * @generated
	 */
	String getExtensionPrefix();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#getExtensionPrefix <em>Extension Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extension Prefix</em>' attribute.
	 * @see #getExtensionPrefix()
	 * @generated
	 */
	void setExtensionPrefix(String value);

	/**
	 * Returns the value of the '<em><b>Creation Command Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Creation Command Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The fully qualified name of a command that creates models applying this context. The command must implement the org.eclipse.papyrus.infra.architecture.commands.IModelCreationCommand inteface
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Creation Command Class</em>' attribute.
	 * @see #setCreationCommandClass(String)
	 * @see org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage#getArchitectureContext_CreationCommandClass()
	 * @model annotation="http://www.eclipse.org/papyrus/infra/core/architecture classConstraint='bundleclass://org.eclipse.papyrus.infra.architecture/org.eclipse.papyrus.infra.architecture.commands.IModelCreationCommand'"
	 * @generated
	 */
	String getCreationCommandClass();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#getCreationCommandClass <em>Creation Command Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Creation Command Class</em>' attribute.
	 * @see #getCreationCommandClass()
	 * @generated
	 */
	void setCreationCommandClass(String value);

	/**
	 * Returns the value of the '<em><b>Conversion Command Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Conversion Command Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The fully qualified name of a command that refactors models that switched to this context. The command must implement the org.eclipse.papyrus.infra.architecture.commands.IModelConversionCommand inteface
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Conversion Command Class</em>' attribute.
	 * @see #setConversionCommandClass(String)
	 * @see org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage#getArchitectureContext_ConversionCommandClass()
	 * @model annotation="http://www.eclipse.org/papyrus/infra/core/architecture classConstraint='bundleclass://org.eclipse.papyrus.infra.architecture/org.eclipse.papyrus.infra.architecture.commands.IModelConversionCommand'"
	 * @generated
	 */
	String getConversionCommandClass();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#getConversionCommandClass <em>Conversion Command Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Conversion Command Class</em>' attribute.
	 * @see #getConversionCommandClass()
	 * @generated
	 */
	void setConversionCommandClass(String value);

	/**
	 * Returns the value of the '<em><b>General Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * An {@link ArchitectureContext} of the same kind that this context specializes.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>General Context</em>' reference.
	 * @see #setGeneralContext(ArchitectureContext)
	 * @see org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage#getArchitectureContext_GeneralContext()
	 * @model
	 * @generated
	 */
	ArchitectureContext getGeneralContext();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#getGeneralContext <em>General Context</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>General Context</em>' reference.
	 * @see #getGeneralContext()
	 * @generated
	 */
	void setGeneralContext(ArchitectureContext value);

	/**
	 * Returns the value of the '<em><b>Extended Contexts</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * {@link ArchitectureContext}s to which this context contributes additional definitions.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Extended Contexts</em>' reference list.
	 * @see org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage#getArchitectureContext_ExtendedContexts()
	 * @model
	 * @generated
	 */
	EList<ArchitectureContext> getExtendedContexts();

	/**
	 * Returns the value of the '<em><b>Extension</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Whether this context is an {@linkplain #getExtendedContexts() extension of other contexts}. Extension contexts are not presented in their own right in model creation wizards and other UI components.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Extension</em>' attribute.
	 * @see org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage#getArchitectureContext_Extension()
	 * @model required="true" transient="true" changeable="false" volatile="true"
	 * @generated
	 */
	boolean isExtension();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model diagnosticsUnique="false" contextUnique="false"
	 * @generated
	 */
	boolean ceationCommandClassExists(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model diagnosticsUnique="false" contextUnique="false"
	 * @generated
	 */
	boolean conversionCommandClassExists(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Query whether the context is consistent with some other {@code context}. At the least, this means that it is of the same metaclass: an {@link ArchitectureFramework} is not consistent with an {@link ArchitectureDescriptionLanguage}.
	 * <!-- end-model-doc -->
	 * @model contextRequired="true"
	 * @generated
	 */
	boolean isConsistentWith(ArchitectureContext context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A context must be {@linkplain #isConsistentWith(ArchitectureContext consistent with} all contexts that it extends.
	 * <!-- end-model-doc -->
	 * @model diagnosticsUnique="false" contextUnique="false"
	 * @generated
	 */
	boolean contextExtensionsAreConsistent(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A context must be {@linkplain #isConsistentWith(ArchitectureContext consistent with} all the context that it specializes (if any).
	 * <!-- end-model-doc -->
	 * @model diagnosticsUnique="false" contextUnique="false"
	 * @generated
	 */
	boolean contextGeneralizationIsConsistent(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A context requires a {@linkplain #getCreationCommandClass() creation command class} if it {@linkplain #isExtension() is not an extension} of some other context.
	 * <!-- end-model-doc -->
	 * @model diagnosticsUnique="false" contextUnique="false"
	 * @generated
	 */
	boolean creationCommandClassRequired(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Query the graph of all contexts that this context extends, in breadth-first order from those directly extended.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	EList<ArchitectureContext> allExtendedContexts();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Query the chain of all contexts that this context specializes, from most specific to most general.
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	EList<ArchitectureContext> allGeneralContexts();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A context may not extend itself, neither directly not indirectly.
	 * <!-- end-model-doc -->
	 * @model diagnosticsUnique="false" contextUnique="false"
	 * @generated
	 */
	boolean extensionCycle(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A context may not specialize itself, neither directly not indirectly.
	 * <!-- end-model-doc -->
	 * @model diagnosticsUnique="false" contextUnique="false"
	 * @generated
	 */
	boolean generalizationCycle(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A context must not extend any {@linkplain #allGeneralContexts() context that it specializes}, neither directly nor indirectly.
	 * <!-- end-model-doc -->
	 * @model diagnosticsUnique="false" contextUnique="false"
	 * @generated
	 */
	boolean generalNotExtended(DiagnosticChain diagnostics, Map<Object, Object> context);

	/**
	 * Returns the value of the '<em><b>Domain</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureDomain#getContexts <em>Contexts</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The domain that defines the context
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Domain</em>' container reference.
	 * @see #setDomain(ArchitectureDomain)
	 * @see org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage#getArchitectureContext_Domain()
	 * @see org.eclipse.papyrus.infra.core.architecture.ArchitectureDomain#getContexts
	 * @model opposite="contexts" required="true" transient="false"
	 * @generated
	 */
	ArchitectureDomain getDomain();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#getDomain <em>Domain</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain</em>' container reference.
	 * @see #getDomain()
	 * @generated
	 */
	void setDomain(ArchitectureDomain value);

} // ArchitectureContext
