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
import org.eclipse.uml2.uml.InstanceSpecification;
import org.eclipse.uml2.uml.Model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for validation rules. It tests
 * - Model with OCL constraints on class, verified in instance specification
 * - Model with OCL constraints on redefined attribute of a class.
 */
public class TestOCLConstraintOnInstanceSpec extends AbstractValidationEditorTest {

	public static final String MODEL_NAME = "instanceSpecification"; //$NON-NLS-1$

	public static final String INSTANCE_NAME = "instance"; //$NON-NLS-1$

	/** instance specification with constraint violation */
	protected InstanceSpecification instance;

	@Before
	public void initModelForValidationTest() throws Exception {
		initModel(PROJECT_PREFIX + MODEL_NAME, MODEL_NAME, Activator.getDefault().getBundle());

		Model model = (Model) getRootUMLModel();

		instance = (InstanceSpecification) model.getPackagedElement(INSTANCE_NAME);
		Assert.assertNotNull(String.format(CAN_NOT_FIND_ELEMENT, INSTANCE_NAME, model), instance);

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
	 * Validation needs to create an entry for the violated constraint of the instance specification
	 */
	@FailingTest
	@Test
	public void validateOCLOnInstanceSpec() throws Exception {
		// get the diagnostic and check for the given class
		List<Diagnostic> diagnostics = filterDiagnosticsByElement(globalDiagnostic.getChildren(), instance);
		Assert.assertEquals(Messages.TestOCLConstraintOnInstanceSpec_PriceRule, 1, diagnostics.size());
	}
}
