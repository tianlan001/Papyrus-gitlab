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
import org.eclipse.uml2.uml.Package;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for UML validation rules. It tests
 * - Model with UML warning (identifiers of same name in package)
 * - Model with non-integer multiplicity on attribute (0..MAX)
 */
public class TestUMLValidationRules extends AbstractValidationEditorTest {

	public static final String MODEL_NAME = "UMLValidationTests"; //$NON-NLS-1$

	public static final String PKG_NAME_CLASH = "nameclash"; //$NON-NLS-1$

	public static final String CLASS1 = "Class1"; //$NON-NLS-1$
	protected Class class1;

	public static final String PKG_MULTIPLICITY = "multiplicity"; //$NON-NLS-1$
	public static final String NON_STANDARD_MULTIPLICITY = "NonStandardMultiplicity"; //$NON-NLS-1$
	protected Class nonStandardMultiplicity;

	@Before
	public void initModelForValidationTest() throws Exception {
		initModel(PROJECT_PREFIX + MODEL_NAME, MODEL_NAME, Activator.getDefault().getBundle());

		// validate the new model

		Assert.assertNotNull("RootModel is null", getRootUMLModel()); //$NON-NLS-1$
		Model model = (Model) getRootUMLModel();

		Package nameclash = (Package) model.getPackagedElement(PKG_NAME_CLASH);
		Assert.assertNotNull(String.format(CAN_NOT_FIND_ELEMENT, PKG_NAME_CLASH, model), nameclash);
		class1 = (Class) nameclash.getPackagedElement(CLASS1);
		Assert.assertNotNull(String.format(CAN_NOT_FIND_ELEMENT, CLASS1, model), class1);

		Package multiplicty = (Package) model.getPackagedElement(PKG_MULTIPLICITY);
		Assert.assertNotNull(String.format(CAN_NOT_FIND_ELEMENT, PKG_MULTIPLICITY, model), multiplicty);
		nonStandardMultiplicity = (Class) multiplicty.getPackagedElement(NON_STANDARD_MULTIPLICITY);
		Assert.assertNotNull(String.format(CAN_NOT_FIND_ELEMENT, NON_STANDARD_MULTIPLICITY, model), nonStandardMultiplicity);

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
	 * Simple failing validation for IsActiveEntityRule
	 */
	@FailingTest
	@Test
	public void validateNameClashDetection() throws Exception {
		// get the diagnostic and check for the given class
		List<Diagnostic> diagnostics = filterDiagnosticsByElement(globalDiagnostic.getChildren(), class1);
		Assert.assertEquals(String.format(Messages.TestUMLValidationRules_DetectNameClash, class1), 1, diagnostics.size());
	}

	/**
	 * Simple valid validation for IsActiveEntityRule
	 */
	@FailingTest
	@Test
	public void validateNonStandardMultiplicity() throws Exception {
		// get the diagnostic and check for the given class
		List<Diagnostic> diagnostics = filterDiagnosticsByElement(globalDiagnostic.getChildren(), nonStandardMultiplicity);
		Assert.assertEquals(String.format(Messages.TestUMLValidationRules_NonStandardMultiplicityMayNotImplyError, nonStandardMultiplicity), 0, diagnostics.size());
	}

}
