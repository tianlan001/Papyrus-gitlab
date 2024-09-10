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
 * Model with OCL constraints embedded in profile
 */
public class TestValidationRulesInProfile extends AbstractValidationEditorTest {

	public static final String CONSTRAINT_ID = "isActiveEntity"; //$NON-NLS-1$

	public static final String PROFILE_NAME = "active.rule-in-definition.profile"; //$NON-NLS-1$

	public static final String MODEL_NAME = "active-tst.rule-in-definition"; //$NON-NLS-1$

	/**
	 * A non active class applying a stereotype with an attached OCL constraint requiring that the class is active
	 */
	public static final String INACTIVE_NAME = "Inactive"; //$NON-NLS-1$
	protected Class active;

	/**
	 * An active class applying a stereotype with an attached OCL constraint requiring that the class is active
	 */
	public static final String ACTIVE_NAME = "Active"; //$NON-NLS-1$
	protected Class inactive;

	/**
	 * An active class applying a stereotype with a malformed OCL constraint attached
	 */
	public static final String ACTIVE_MALFORMED_NAME = "ActiveMalformed"; //$NON-NLS-1$
	protected Class activeMalformed;

	@Before
	public void initModelForValidationTest() throws Exception {
		createProject(PROJECT_PREFIX + MODEL_NAME);
		copyModel(PROFILE_NAME, Activator.getDefault().getBundle());
		initModel(MODEL_NAME, Activator.getDefault().getBundle());


		// validate the new model
		Model model = (Model) getRootUMLModel();

		active = (Class) model.getPackagedElement(ACTIVE_NAME);
		Assert.assertNotNull(String.format(CAN_NOT_FIND_ELEMENT, ACTIVE_NAME, model), active);

		inactive = (Class) model.getPackagedElement(INACTIVE_NAME);
		Assert.assertNotNull(String.format(CAN_NOT_FIND_ELEMENT, INACTIVE_NAME, model), inactive);

		activeMalformed = (Class) model.getPackagedElement(ACTIVE_MALFORMED_NAME);
		Assert.assertNotNull(String.format(CAN_NOT_FIND_ELEMENT, ACTIVE_MALFORMED_NAME, model), activeMalformed);

		final EditingDomain domain = TransactionUtil.getEditingDomain(model);
		final ValidateModelCommand validateModelCommand = new ValidateModelCommand(model);
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				domain.getCommandStack().execute(GMFtoEMFCommandWrapper.wrap(validateModelCommand));
			}
		});

		globalDiagnostic = validateModelCommand.getDiagnostic();
	}

	/**
	 * No errors on active class
	 */
	@FailingTest
	@Test
	public void validateIsActiveRule_ActiveCapsule() throws Exception {
		// get the diagnostic and check for the given class
		List<Diagnostic> diagnostics = filterDiagnosticsByElement(globalDiagnostic.getChildren(), active);
		Assert.assertEquals(String.format(Messages.TestValidationRulesInProfile_IsActiveShouldNotTriggerIssue, active), 0, diagnostics.size());
	}

	/**
	 * One warning on inactive class
	 */
	@FailingTest
	public void validateIsActiveRule_notActiveCapsule() throws Exception {
		// get the diagnostic and check for the given class
		List<Diagnostic> diagnostics = filterDiagnosticsByElement(globalDiagnostic.getChildren(), inactive);
		Assert.assertEquals(String.format(Messages.TestValidationRulesInProfile_IsActiveShouldTriggerIssue, inactive), 1, diagnostics.size());
	}

	/**
	 * One error on malformed class
	 */
	@FailingTest
	@Test
	public void validateMalformedActiveRule_notActiveCapsule() throws Exception {
		// get the diagnostic and check for the given class
		List<Diagnostic> diagnostics = filterDiagnosticsByElement(globalDiagnostic.getChildren(), activeMalformed);
		Assert.assertEquals(String.format(Messages.TestValidationRulesInProfile_IsActiveMalformedShouldTriggerIssue, activeMalformed), 1, diagnostics.size());
	}
}
