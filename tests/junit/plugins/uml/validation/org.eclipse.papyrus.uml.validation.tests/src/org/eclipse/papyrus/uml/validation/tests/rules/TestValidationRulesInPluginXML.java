/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.validation.tests.rules;

import java.util.List;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.validation.service.ConstraintRegistry;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.services.validation.commands.ValidateModelCommand;
import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.uml.validation.tests.Activator;
import org.eclipse.papyrus.uml.validation.tests.Messages;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for validation rules. It tests
 * Model with OCL constraints in plugin.xml (EMFv)
 * Model with Java constraints in plugin.xml (EMFv)
 */
public class TestValidationRulesInPluginXML extends AbstractValidationEditorTest {

	public static final String CONSTRAINT_PLUGIN = "org.eclipse.papyrus.uml.validation.tests.genvalidation"; //$NON-NLS-1$

	public static final String CONSTRAINT_ID_ALWAYS_ACTIVE = "profile.AlwaysActive.ConstraintAlwaysActive"; //$NON-NLS-1$

	public static final String MODEL_NAME = "active-tst.rule-in-plugin"; //$NON-NLS-1$

	/**
	 * An active class applying a stereotype with an attached OCL constraint requiring that the class is active
	 */
	public static final String INACTIVE_OCL_NAME = "ActiveOCL"; //$NON-NLS-1$
	protected Class activeOCL;

	/**
	 * A non active class applying a stereotype with an attached OCL constraint requiring that the class is active
	 */
	public static final String ACTIVE_OCL_NAME = "InactiveOCL"; //$NON-NLS-1$
	protected Class inactiveOCL;

	/**
	 * A non active class applying a stereotype with an attached Java constraint requiring that the class is passive
	 */
	public static final String INACTIVE_JAVA_NAME = "InactiveJava"; //$NON-NLS-1$
	protected Class inactiveJava;

	/**
	 * An active class applying a stereotype with an attached Java constraint requiring that the class is passive
	 */
	public static final String ACTIVE_JAVA_NAME = "ActiveJava"; //$NON-NLS-1$
	protected Class activeJava;

	@Before
	public void initModelForValidationTest() throws Exception {
		initModel(PROJECT_PREFIX + MODEL_NAME, MODEL_NAME, Activator.getDefault().getBundle());

		Model model = (Model) getRootUMLModel();

		inactiveOCL = (Class) model.getPackagedElement(INACTIVE_OCL_NAME);
		Assert.assertNotNull(String.format(CAN_NOT_FIND_ELEMENT, INACTIVE_OCL_NAME, model), inactiveOCL);

		activeOCL = (Class) model.getPackagedElement(ACTIVE_OCL_NAME);
		Assert.assertNotNull(String.format(CAN_NOT_FIND_ELEMENT, ACTIVE_OCL_NAME, model), activeOCL);

		inactiveJava = (Class) model.getPackagedElement(INACTIVE_JAVA_NAME);
		Assert.assertNotNull(String.format(CAN_NOT_FIND_ELEMENT, INACTIVE_JAVA_NAME, model), inactiveJava);

		activeJava = (Class) model.getPackagedElement(ACTIVE_JAVA_NAME);
		Assert.assertNotNull(String.format(CAN_NOT_FIND_ELEMENT, ACTIVE_JAVA_NAME, model), activeJava);

		final EditingDomain domain = TransactionUtil.getEditingDomain(model);
		final ValidateModelCommand validateModelCommand = new ValidateModelCommand(model);
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				domain.getCommandStack().execute(GMFtoEMFCommandWrapper.wrap(validateModelCommand));
			}
		});

		// check that the constraint exists
		ConstraintRegistry instance = ConstraintRegistry.getInstance();
		IConstraintDescriptor descriptor = instance.getDescriptor(CONSTRAINT_PLUGIN, CONSTRAINT_ID_ALWAYS_ACTIVE);
		Assert.assertNotNull(Messages.TestValidationRulesInPluginXML_ConstraintIsMissing, descriptor);

		globalDiagnostic = validateModelCommand.getDiagnostic();
	}

	/**
	 * Failing validation for IsActiveEntityRule (OCL rule, on inactive class)
	 */
	@FailingTest
	@Test
	public void validateIsActiveOCLRule_inactiveClass() throws Exception {
		// get the diagnostic and check for the given class
		List<Diagnostic> diagnostics = filterDiagnosticsByElement(globalDiagnostic.getChildren(), inactiveOCL);
		Assert.assertEquals(String.format(Messages.TestValidationRulesInPluginXML_IsActiveShouldTriggerIssue, inactiveOCL), 1, diagnostics.size());
	}

	/**
	 * Successful validation for IsActiveEntityRule (OCL rule, on active class)
	 */
	@FailingTest
	@Test
	public void validateIsActiveOCLRule_activeClass() throws Exception {
		// get the diagnostic and check for the given class
		List<Diagnostic> diagnostics = filterDiagnosticsByElement(globalDiagnostic.getChildren(), activeOCL);
		Assert.assertEquals(String.format(Messages.TestValidationRulesInPluginXML_IsActiveShouldNotTriggerIssue, activeOCL), 0, diagnostics.size());
	}

	/**
	 * Successful validation for IsPassiveEntityRule (Java rule, on inactive class)
	 */
	@FailingTest
	@Test
	public void validateIsPassiveJavaRule_inactiveClass() throws Exception {
		// get the diagnostic and check for the given class
		List<Diagnostic> diagnostics = filterDiagnosticsByElement(globalDiagnostic.getChildren(), inactiveJava);
		Assert.assertEquals(String.format(Messages.TestValidationRulesInPluginXML_IsPassiveShouldTriggerIssue, inactiveJava), 0, diagnostics.size());
	}

	/**
	 * Failing validation for IsPassiveEntityRule (Java rule, on active class)
	 */
	@FailingTest
	@Test
	public void validateIsPassiveJavaRule_activeClass() throws Exception {
		// get the diagnostic and check for the given class
		List<Diagnostic> diagnostics = filterDiagnosticsByElement(globalDiagnostic.getChildren(), activeJava);
		Assert.assertEquals(String.format(Messages.TestValidationRulesInPluginXML_IsPassiveShouldNotTriggerIssue, activeJava), 1, diagnostics.size());
	}
}
