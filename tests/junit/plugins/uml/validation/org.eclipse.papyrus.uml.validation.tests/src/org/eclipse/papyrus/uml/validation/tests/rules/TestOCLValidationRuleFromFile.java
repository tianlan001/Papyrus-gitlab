/*****************************************************************************
 * Copyright (c) 2014, 2021 CEA LIST, Christian W. Damus, and others.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 *  Ansgar Radermacher (CEA) ansgar.radermacher@cea.fr - Extension to validation test suite
 *  Christian W. Damus - bug 572532
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.validation.tests.rules;

import java.util.List;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.ocl.pivot.internal.resource.OCLAdapter;
import org.eclipse.ocl.xtext.completeocl.utilities.CompleteOCLLoader;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.services.validation.IValidationFilter;
import org.eclipse.papyrus.infra.services.validation.IValidationHook;
import org.eclipse.papyrus.infra.services.validation.commands.ValidateModelCommand;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.uml.validation.tests.Activator;
import org.eclipse.papyrus.uml.validation.tests.Messages;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for validation rules:
 * Verify that custom OCL file contributes to validation, see bug 408215* Sample test for cut in model explorer
 */
public class TestOCLValidationRuleFromFile extends AbstractValidationEditorTest {

	protected static final String EXTRA_UML_VALIDATION_OCL = "ExtraUMLValidation.ocl"; //$NON-NLS-1$

	protected static final String CONSTRAINT_ID = "isActiveEntity"; //$NON-NLS-1$

	protected static final String MODEL_NAME = "UMLValidationTests"; //$NON-NLS-1$

	protected static final String PKG_OCL_FROM_FILE = "oclFromFile"; //$NON-NLS-1$

	protected final static String CLASS_LOWER_CASE = "lowerCaseClass"; //$NON-NLS-1$

	private static volatile boolean testRunning;

	/* class starting with a lower case name (violates constraint in external OCL file) */
	protected Class lowerCaseClass;

	@Before
	public void initModelForValidationTest() throws Exception {
		testRunning = true;
		initModel(PROJECT_PREFIX + MODEL_NAME, MODEL_NAME, Activator.getDefault().getBundle());

		Model model = (Model) getRootUMLModel();
		Package pkg = (Package) model.getPackagedElement(PKG_OCL_FROM_FILE);
		Assert.assertNotNull(String.format(CAN_NOT_FIND_ELEMENT, PKG_OCL_FROM_FILE, model), pkg);

		lowerCaseClass = (Class) pkg.getPackagedElement(CLASS_LOWER_CASE);
		Assert.assertNotNull(String.format(CAN_NOT_FIND_ELEMENT, CLASS_LOWER_CASE, model), lowerCaseClass);

		final ValidateModelCommand validateModelCommand = new ValidateModelCommand(model);

		final EditingDomain domain = TransactionUtil.getEditingDomain(model);
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				domain.getCommandStack().execute(GMFtoEMFCommandWrapper.wrap(validateModelCommand));
			}
		});
		globalDiagnostic = validateModelCommand.getDiagnostic();
	}

	@After
	public void afterTest() {
		testRunning = false;
	}

	/**
	 * Failing validation for rule defined in ExtraUMLValidation
	 */
	@FailingTest
	@Test
	public void validateCamelCaseRule() throws Exception {
		// get the diagnostic and check for the given class
		List<Diagnostic> diagnostics = filterDiagnosticsByElement(globalDiagnostic.getChildren(), lowerCaseClass);
		Assert.assertEquals(String.format(
				Messages.TestOCLValidationRuleFromFile_CamelCaseRule, EXTRA_UML_VALIDATION_OCL),
				1, diagnostics.size());
	}

	//
	// Nested types
	//

	/**
	 * A validation filter that matches while our tests are running.
	 */
	public static final class HookFilter implements IValidationFilter {

		@Override
		public boolean isApplicable(EObject element) {
			return testRunning;
		}

	}

	/**
	 * A validation hook that installs our test OCL resource.
	 */
	public static final class Hook implements IValidationHook {

		@Override
		public void beforeValidation(EObject element) {
			// Registering OCL components
			//
			ResourceSet modelResources = element.eResource().getResourceSet();
			OCLAdapter oclAdapter = OCLAdapter.getAdapter(modelResources);
			CompleteOCLLoader helper = new CompleteOCLLoader(oclAdapter.getEnvironmentFactory()) {
				@Override
				protected boolean error(String primaryMessage, String detailMessage) {
					Assert.fail(Messages.TestOCLValidationRuleFromFile_CanNotGetEnvFactory);
					return false;
				}
			};
			Assert.assertTrue(Messages.TestOCLValidationRuleFromFile_OCLCanNotLoadMM, helper.loadMetamodels());

			// load OCL file
			URI oclURI = URI.createPlatformPluginURI(Activator.PLUGIN_ID + RESOURCES_PATH + EXTRA_UML_VALIDATION_OCL, false);
			try {
				if (!helper.loadDocument(oclURI)) {
					Assert.fail("Can not load OCL document with URI: " + oclURI.path()); //$NON-NLS-1$
				}
			} catch (Throwable e) {
				Assert.fail(String.format("Exception %s during loading of OCL document with URI: %s", e.getMessage(), oclURI.path())); //$NON-NLS-1$
			}
			helper.installPackages();
		}

		@Override
		public void afterValidation(EObject element) {
			// Not needed
		}

	}
}
