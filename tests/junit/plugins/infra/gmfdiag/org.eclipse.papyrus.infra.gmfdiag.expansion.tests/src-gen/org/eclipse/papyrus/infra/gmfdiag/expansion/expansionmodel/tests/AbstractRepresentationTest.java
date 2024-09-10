/**
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
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
package org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.tests;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.diagnosticWithMessage;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.DiagramExpansion;
import org.eclipse.papyrus.junit.utils.rules.ProjectFixture;

import junit.framework.TestCase;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Abstract Representation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation#validate(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Validate</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class AbstractRepresentationTest extends TestCase {

	private final PrivateFixture project = new PrivateFixture();

	/**
	 * The fixture for this Abstract Representation test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected AbstractRepresentation fixture = null;

	/**
	 * Constructs a new Abstract Representation test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public AbstractRepresentationTest(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		// This is not a JUnit 4 test
		project.createProject("org.eclipse.papyrus.infra.gmfdiag.expansion.tests.project");

		project.copyFolder(getClass(), "resources/org.eclipse.papyrus.infra.gmfdiag.expansion.tests.project");
		ResourceSet rset = new ResourceSetImpl();
		Resource testResource = rset.getResource(URI.createPlatformResourceURI(project.getFile("model/test.expansionmodel").getFullPath().toPortableString(), true), true);
		DiagramExpansion root = (DiagramExpansion) testResource.getContents().get(0);

		setFixture(findFixture(root));
	}

	@Override
	protected void tearDown() throws Exception {
		project.deleteProject();

		super.tearDown();
	}

	protected abstract AbstractRepresentation findFixture(DiagramExpansion expansionModel);

	/**
	 * Sets the fixture for this Abstract Representation test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void setFixture(AbstractRepresentation fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Abstract Representation test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected AbstractRepresentation getFixture() {
		return fixture;
	}

	protected DiagnosticChain diagnosticChain() {
		return new BasicDiagnostic();
	}

	protected Map<Object, Object> validationContext() {
		return new HashMap<>();
	}

	protected Diagnostic getDiagnostic(DiagnosticChain chain) {
		return ((BasicDiagnostic) chain).getChildren().stream()
				.filter(d -> d.getSeverity() >= Diagnostic.WARNING)
				.findAny()
				.orElse(Diagnostic.OK_INSTANCE);
	}

	@SuppressWarnings("rawtypes") // The model was generated with raw Map type
	protected Diagnostic validate(BiPredicate<DiagnosticChain, Map> validationMethod) {
		DiagnosticChain diagnostics = diagnosticChain();
		boolean validationResult = validationMethod.test(diagnostics, validationContext());
		Diagnostic result = getDiagnostic(diagnostics);

		assertThat("Validation method returned wrong result for diagnostics reported.", validationResult, is(result.getSeverity() == Diagnostic.OK));
		return result;
	}

	/**
	 * Tests the '{@link org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation#validate(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map) <em>Validate</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.AbstractRepresentation#validate(org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 * @generated NOT
	 */
	public void testValidate__DiagnosticChain_Map() {
		Diagnostic diag = validate(getFixture()::validate);
		assertThat("Validation failed for edit part and factory that exist.", diag.getSeverity(), is(Diagnostic.OK));

		getFixture().setEditPartQualifiedName("org.eclipse.papyrus.infra.gmfdiag.expansion.tests.project.internal.NoSuchEditPart");
		diag = validate(getFixture()::validate);
		assertThat(diag, diagnosticWithMessage(containsString("references an edit part that does not exist")));

		getFixture().setEditPartQualifiedName("org.eclipse.papyrus.infra.gmfdiag.expansion.tests.project.internal.TestEditPart");
		diag = validate(getFixture()::validate);
		assertThat("Validation failed for edit part that exists in the class path.", diag.getSeverity(), is(Diagnostic.OK));

		getFixture().setViewFactory("org.eclipse.papyrus.infra.gmfdiag.expansion.tests.project.internal.NoSuchViewFactory");
		diag = validate(getFixture()::validate);
		assertThat(diag, diagnosticWithMessage(containsString("references a view factory that does not exist")));

		getFixture().setViewFactory("org.eclipse.papyrus.infra.gmfdiag.expansion.tests.project.internal.TestViewFactory");
		diag = validate(getFixture()::validate);
		assertThat("Validation failed for view factory that exists in the class path.", diag.getSeverity(), is(Diagnostic.OK));
	}

	//
	// Nested types
	//

	private static final class PrivateFixture extends ProjectFixture {
		PrivateFixture() {
			super();
		}

		@Override
		protected void createProject(String name) throws CoreException {
			super.createProject(name);
		}

		@Override
		protected void deleteProject() {
			super.deleteProject();
		}
	}

} // AbstractRepresentationTest
