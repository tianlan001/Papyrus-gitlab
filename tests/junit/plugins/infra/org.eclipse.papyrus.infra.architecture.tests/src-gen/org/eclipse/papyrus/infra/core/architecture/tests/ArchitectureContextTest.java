/**
 *  Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 * 
 *  SPDX-License-Identifier: EPL-2.0
 * 
 *  Contributors:
 *  Christian W. Damus - Initial API and implementation
 */
package org.eclipse.papyrus.infra.core.architecture.tests;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.diagnosticWithMessage;
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.function.Predicate;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureFactory;
import org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage;
import org.eclipse.papyrus.infra.core.architecture.util.ArchitectureValidator;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Context</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#isExtension() <em>Extension</em>}</li>
 * </ul>
 * </p>
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#ceationCommandClassExists(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Ceation Command Class Exists</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#conversionCommandClassExists(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Conversion Command Class Exists</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#isConsistentWith(org.eclipse.papyrus.infra.core.architecture.ArchitectureContext) <em>Is Consistent With</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#contextExtensionsAreConsistent(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Context Extensions Are Consistent</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#contextGeneralizationIsConsistent(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Context Generalization Is Consistent</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#creationCommandClassRequired(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Creation Command Class Required</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#allExtendedContexts() <em>All Extended Contexts</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#allGeneralContexts() <em>All General Contexts</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#extensionCycle(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Extension Cycle</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#generalizationCycle(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Generalization Cycle</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#generalNotExtended(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>General Not Extended</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public abstract class ArchitectureContextTest extends ADElementTest {

	/**
	 * Constructs a new Context test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArchitectureContextTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Context test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected ArchitectureContext getFixture() {
		return (ArchitectureContext)fixture;
	}

	protected ArchitectureContext createSameKindAsSelf(String name) {
		ArchitectureContext result = (ArchitectureContext) ArchitectureFactory.eINSTANCE.create(getFixture().eClass());
		result.setName(name);
		return result;
	}
	
	protected ArchitectureContext createDifferentKindAsSelf(String name) {
		EClass otherKind = ArchitecturePackage.eINSTANCE.getEClassifiers().stream()
				.filter(Predicate.not(getFixture().eClass()::equals))
				.filter(EClass.class::isInstance).map(EClass.class::cast)
				.filter(Predicate.not(EClass::isAbstract))
				.filter(eclass -> ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT.isSuperTypeOf(eclass))
				.findAny().get();
		ArchitectureContext result = (ArchitectureContext) ArchitectureFactory.eINSTANCE.create(otherKind);
		result.setName(name);
		return result;
	}
	
	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#isExtension() <em>Extension</em>}' feature getter.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#isExtension()
	 * @generated NOT
	 */
	public void testIsExtension() {
		assertThat(getFixture().isExtension(), is(false));
		
		getFixture().getExtendedContexts().add(createSameKindAsSelf("Extended"));
		assertThat(getFixture().isExtension(), is(true));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#ceationCommandClassExists(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Ceation Command Class Exists</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#ceationCommandClassExists(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated NOT
	 */
	public void testCeationCommandClassExists__DiagnosticChain_Map() {
		// This is tested by org.eclipse.papyrus.toolsmiths.validation.architecture.tests in the required JDT context
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#conversionCommandClassExists(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Conversion Command Class Exists</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#conversionCommandClassExists(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated NOT
	 */
	public void testConversionCommandClassExists__DiagnosticChain_Map() {
		// This is tested by org.eclipse.papyrus.toolsmiths.validation.architecture.tests in the required JDT context
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#isConsistentWith(org.eclipse.papyrus.infra.core.architecture.ArchitectureContext) <em>Is Consistent With</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#isConsistentWith(org.eclipse.papyrus.infra.core.architecture.ArchitectureContext)
	 * @generated NOT
	 */
	public void testIsConsistentWith__ArchitectureContext() {
		assertThat(getFixture().isConsistentWith(createSameKindAsSelf("SameKind")), is(true));
		assertThat(getFixture().isConsistentWith(createDifferentKindAsSelf("DifferentKind")), is(false));
		assertThat(getFixture().isConsistentWith(null), is(false));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#contextExtensionsAreConsistent(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Context Extensions Are Consistent</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#contextExtensionsAreConsistent(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated NOT
	 */
	public void testContextExtensionsAreConsistent__DiagnosticChain_Map() {
		assertThat(getFixture().contextExtensionsAreConsistent(null, null), is(true));
		
		ArchitectureContext sameKind = createSameKindAsSelf("SameKind");
		getFixture().getExtendedContexts().add(sameKind);
		assertThat(getFixture().contextExtensionsAreConsistent(null, null), is(true));
		
		ArchitectureContext differentKind = createDifferentKindAsSelf("DifferentKind");
		sameKind.getExtendedContexts().add(differentKind);
		assertThat(getFixture().contextExtensionsAreConsistent(null, null), is(false));
		
		sameKind.getExtendedContexts().add(createDifferentKindAsSelf("Other"));
		
		Diagnostic diagnostic = validate(ArchitectureValidator.ARCHITECTURE_CONTEXT__CONTEXT_EXTENSIONS_ARE_CONSISTENT);
		assertThat(diagnostic, diagnosticWithMessage(both(containsString("'self' cannot extend")).and(
				containsString("'DifferentKind, Other'"))));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#contextGeneralizationIsConsistent(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Context Generalization Is Consistent</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#contextGeneralizationIsConsistent(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated NOT
	 */
	public void testContextGeneralizationIsConsistent__DiagnosticChain_Map() {
		assertThat(getFixture().contextGeneralizationIsConsistent(null, null), is(true));
		
		ArchitectureContext sameKind = createSameKindAsSelf("SameKind");
		getFixture().setGeneralContext(sameKind);
		assertThat(getFixture().contextGeneralizationIsConsistent(null, null), is(true));
		
		ArchitectureContext differentKind = createDifferentKindAsSelf("DifferentKind");
		sameKind.setGeneralContext(differentKind);
		assertThat(getFixture().contextGeneralizationIsConsistent(null, null), is(false));
		
		Diagnostic diagnostic = validate(ArchitectureValidator.ARCHITECTURE_CONTEXT__CONTEXT_GENERALIZATION_IS_CONSISTENT);
		assertThat(diagnostic, diagnosticWithMessage(containsString("'self' cannot specialize 'DifferentKind'")));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#creationCommandClassRequired(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Creation Command Class Required</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#creationCommandClassRequired(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated NOT
	 */
	public void testCreationCommandClassRequired__DiagnosticChain_Map() {
		assertThat(getFixture().creationCommandClassRequired(null, null), is(false));

		// General context doesn't matter; must be able to instantiate this context
		getFixture().setGeneralContext(createSameKindAsSelf("SameKind"));
		assertThat(getFixture().creationCommandClassRequired(null, null), is(false));
		
		Diagnostic diagnostic = validate(ArchitectureValidator.ARCHITECTURE_CONTEXT__CREATION_COMMAND_CLASS_REQUIRED);
		assertThat(diagnostic, diagnosticWithMessage(containsString("creation command class is required in 'self'")));
		
		getFixture().setCreationCommandClass("foo.CreateModelCommand");
		assertThat(getFixture().creationCommandClassRequired(null, null), is(true));
		
		getFixture().getExtendedContexts().add(createSameKindAsSelf("SameKind"));
		assertThat(getFixture().creationCommandClassRequired(null, null), is(true));
		
		getFixture().setCreationCommandClass(null);
		assertThat(getFixture().creationCommandClassRequired(null, null), is(true));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#allExtendedContexts() <em>All Extended Contexts</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#allExtendedContexts()
	 * @generated NOT
	 */
	public void testAllExtendedContexts() {
		ArchitectureContext c1 = createSameKindAsSelf("C1");
		ArchitectureContext c2 = createSameKindAsSelf("C2");
		ArchitectureContext c3 = createSameKindAsSelf("C3");
		
		c1.getExtendedContexts().addAll(List.of(c2,c3));
		
		ArchitectureContext c4 = createSameKindAsSelf("C4");
		ArchitectureContext c5 = createSameKindAsSelf("C5");
		
		c4.getExtendedContexts().add(c5);
		
		getFixture().getExtendedContexts().addAll(List.of(c1, c4));
		
		// Extension is not inherited

		ArchitectureContext c6 = createSameKindAsSelf("C6");
		ArchitectureContext c7 = createSameKindAsSelf("C7");
		c6.getExtendedContexts().add(c7);
		getFixture().setGeneralContext(c6);
		
		// Breadth-first order!
		assertThat(getFixture().allExtendedContexts(), is(List.of(c1, c4, c2, c3, c5)));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#allGeneralContexts() <em>All General Contexts</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#allGeneralContexts()
	 * @generated NOT
	 */
	public void testAllGeneralContexts() {
		ArchitectureContext c1 = createSameKindAsSelf("C1");
		ArchitectureContext c2 = createSameKindAsSelf("C2");
		ArchitectureContext c3 = createSameKindAsSelf("C3");
		ArchitectureContext c4 = createSameKindAsSelf("C4");
		ArchitectureContext c5 = createSameKindAsSelf("C5");
		
		c1.setGeneralContext(c2);
		c2.setGeneralContext(c3);
		c3.setGeneralContext(c4);
		c4.setGeneralContext(c5);
		
		getFixture().setGeneralContext(c1);
		
		assertThat(getFixture().allGeneralContexts(), is(List.of(c1, c2, c3, c4, c5)));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#extensionCycle(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Extension Cycle</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#extensionCycle(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated NOT
	 */
	public void testExtensionCycle__DiagnosticChain_Map() {
		ArchitectureContext c1 = createSameKindAsSelf("C1");
		ArchitectureContext c2 = createSameKindAsSelf("C2");
		ArchitectureContext c3 = createSameKindAsSelf("C3");
		
		assertThat(getFixture().extensionCycle(null, null), is(true));

		getFixture().getExtendedContexts().add(c1);
		assertThat(getFixture().extensionCycle(null, null), is(true));

		c1.getExtendedContexts().addAll(List.of(c2, c3));
		assertThat(getFixture().extensionCycle(null, null), is(true));

		c3.getExtendedContexts().add(getFixture());
		assertThat(getFixture().extensionCycle(null, null), is(false));
		
		Diagnostic diagnostic = validate(ArchitectureValidator.ARCHITECTURE_CONTEXT__EXTENSION_CYCLE);
		assertThat(diagnostic, diagnosticWithMessage(containsString("'self' extends itself")));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#generalizationCycle(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Generalization Cycle</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#generalizationCycle(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated NOT
	 */
	public void testGeneralizationCycle__DiagnosticChain_Map() {
		ArchitectureContext c1 = createSameKindAsSelf("C1");
		ArchitectureContext c2 = createSameKindAsSelf("C2");
		ArchitectureContext c3 = createSameKindAsSelf("C3");
		
		assertThat(getFixture().generalizationCycle(null, null), is(true));

		getFixture().setGeneralContext(c1);
		assertThat(getFixture().generalizationCycle(null, null), is(true));

		c1.setGeneralContext(c2);
		c2.setGeneralContext(c3);
		assertThat(getFixture().generalizationCycle(null, null), is(true));

		c3.setGeneralContext(getFixture());
		assertThat(getFixture().generalizationCycle(null, null), is(false));
		
		Diagnostic diagnostic = validate(ArchitectureValidator.ARCHITECTURE_CONTEXT__GENERALIZATION_CYCLE);
		assertThat(diagnostic, diagnosticWithMessage(containsString("'self' specializes itself")));
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#generalNotExtended(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>General Not Extended</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.core.architecture.ArchitectureContext#generalNotExtended(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated NOT
	 */
	public void testGeneralNotExtended__DiagnosticChain_Map() {
		ArchitectureContext c1 = createSameKindAsSelf("C1");
		ArchitectureContext c2 = createSameKindAsSelf("C2");
		ArchitectureContext c3 = createSameKindAsSelf("C3");
		ArchitectureContext c4 = createSameKindAsSelf("C4");
		ArchitectureContext c5 = createSameKindAsSelf("C5");
		
		assertThat(getFixture().generalNotExtended(null, null), is(true));
		
		c1.setGeneralContext(c2);
		c3.getExtendedContexts().add(c4);
		c4.getExtendedContexts().add(c5);
		getFixture().setGeneralContext(c1);
		getFixture().getExtendedContexts().add(c3);
		assertThat(getFixture().generalNotExtended(null, null), is(true));

		c5.getExtendedContexts().add(c2);
		assertThat(getFixture().generalNotExtended(null, null), is(false));
		
		Diagnostic diagnostic = validate(ArchitectureValidator.ARCHITECTURE_CONTEXT__GENERAL_NOT_EXTENDED);
		assertThat(diagnostic, diagnosticWithMessage(containsString("'self' extends one of its general contexts")));
	}

} //ArchitectureContextTest
