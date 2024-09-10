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
 * Model/profile. Model applies a stereotype, but does not set the value of a required stereotype attribute.
 */
public class TestStereotypeValidation extends AbstractValidationEditorTest {

	public static final String MODEL_NAME = "simple"; //$NON-NLS-1$

	public static final String PROFILE_NAME = "simple.profile"; //$NON-NLS-1$

	public static final String CLASS1 = "Class1"; //$NON-NLS-1$
	protected Class class1;

	@Before
	public void initModelForValidationTest() throws Exception {
		createProject(PROJECT_PREFIX + MODEL_NAME);
		copyModel(PROFILE_NAME, Activator.getDefault().getBundle());
		initModel(MODEL_NAME, Activator.getDefault().getBundle());

		Model model = (Model) getRootUMLModel();

		class1 = (Class) model.getPackagedElement(CLASS1);
		Assert.assertNotNull(String.format(CAN_NOT_FIND_ELEMENT, CLASS1, model), class1);

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
	 * Failing validation for stereotype whose required attribute is unset
	 */
	@FailingTest
	@Test
	public void validateUnsetStereotypeAttribute() throws Exception {
		// get the diagnostic and check for the given capsule
		List<Diagnostic> diagnostics = filterDiagnosticsByElement(globalDiagnostic.getChildren(), class1);
		Assert.assertEquals(Messages.TestStereotypeValidation_UnsetRequiredAttribute, 1, diagnostics.size());
	}
}
