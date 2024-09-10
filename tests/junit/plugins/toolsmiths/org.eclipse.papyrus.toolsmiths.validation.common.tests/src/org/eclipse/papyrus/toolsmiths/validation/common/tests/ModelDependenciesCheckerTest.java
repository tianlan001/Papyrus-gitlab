/*****************************************************************************
 * Copyright (c) 2020 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common.tests;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.isEmpty;
import static org.eclipse.papyrus.junit.matchers.MoreMatchers.regexMatches;
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.function.Consumer;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.papyrus.infra.core.utils.TransactionHelper;
import org.eclipse.papyrus.junit.matchers.MoreMatchers;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.ModelDependenciesChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.Build;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.OverlayFile;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.TestProject;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.TestProjectFixture;
import org.junit.Rule;
import org.junit.Test;

/**
 * Specific test cases for the {@link ModelDependenciesChecker} class.
 */
@TestProject("org.eclipse.papyrus.toolsmiths.validation.common.example")
@Build(false)
public class ModelDependenciesCheckerTest {

	private static final String BOOKSTORE_PROFILE = "resources/BookStore.profile.uml"; //$NON-NLS-1$

	@Rule
	public final TestProjectFixture project = new TestProjectFixture();

	/**
	 * Verify that a dynamic profile definition does not induce spurious problem reports
	 * of the empty URI (from the EPackage::eFactoryInstance reference) and EPackages
	 * referenced by namespace URI instead of location URI.
	 */
	@Test
	public void noSpuriousDependencyProblemsFromDynamicProfileDefinition() {
		List<Diagnostic> diagnostics = checkModel(BOOKSTORE_PROFILE);

		assertThat(diagnostics, not(hasItem(MoreMatchers.diagnosticWithMessage(containsString("must be declared as required plug-in"))))); //$NON-NLS-1$
		assertThat(diagnostics, not(hasItem(MoreMatchers.diagnosticWithMessage(containsString("Cross-document reference by file URI"))))); //$NON-NLS-1$
		assertThat(diagnostics, not(hasItem(MoreMatchers.diagnosticWithMessage(regexMatches("The URI .* cannot be resolved"))))); //$NON-NLS-1$
	}

	/**
	 * Verify the reporting of cross-document URIs (HREFs) with schemes that do
	 * not imply a bundle name, even if the authority looks like one.
	 */
	@Test
	@OverlayFile(value = "bug569357/BookStore-weirdHREF.profile.uml", path = BOOKSTORE_PROFILE)
	public void unresolvedNonBundleDeployedURI() {
		List<Diagnostic> diagnostics = checkModel(BOOKSTORE_PROFILE,
				// Pre-resolve the HREF that doesn't imply bundle deployment
				rset -> {
					URI libraryURI = URI.createURI("http://schema.eclipse.org/BookStore"); //$NON-NLS-1$
					Resource library = Resource.Factory.Registry.INSTANCE.getFactory(libraryURI, EcorePackage.eCONTENT_TYPE).createResource(libraryURI);
					library.getContents().add(EcoreFactory.eINSTANCE.createEPackage());
					rset.getResources().add(library);
				});

		assertThat(diagnostics, both(everyItem(MoreMatchers.diagnosticWithMessage(containsString("Suspicious URI: cannot infer bundle name"))))
				.and(not(isEmpty())));
	}

	//
	// Test framework
	//

	List<Diagnostic> checkModel(String path) {
		return checkModel(path, null);
	}

	List<Diagnostic> checkModel(String path, Consumer<? super ResourceSet> doctorResourceSet) {
		Resource profile = project.loadModelResource(path);
		if (doctorResourceSet != null) {
			Runnable doctorIt = () -> doctorResourceSet.accept(profile.getResourceSet());
			TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(profile);
			if (domain == null) {
				doctorIt.run();
			} else {
				try {
					TransactionHelper.run(domain, doctorIt);
				} catch (Exception e) {
					throw new AssertionError("Failed to doctor the resource set in the test.", e);
				}
			}
		}

		ModelDependenciesChecker checker = new ModelDependenciesChecker(project.getProject(), project.getFile(BOOKSTORE_PROFILE), profile);

		BasicDiagnostic diagnostics = new BasicDiagnostic();
		checker.check(diagnostics, null);

		return diagnostics.getChildren();
	}

}
