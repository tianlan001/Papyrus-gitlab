/*****************************************************************************
 * Copyright (c) 2018 CEA LIST.
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
 * 	Ansgar Radermacher (CEA LIST) ansgar.radermacher@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.textedit.tests.editor;

import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.definition.IDirectEditorExtensionPoint;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.utils.DirectEditorsUtil;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.ValueSpecification;
import org.junit.Rule;
import org.junit.Test;

/**
 * The whether the "right" editor is used when opening an editor on a selected element
 */
@SuppressWarnings("nls")
@PluginResource("resources/opaqueExpressionTest.di")
public class UseRightEditorTest {

	@Rule
	/** The model set fixture. */
	public final PapyrusEditorFixture modelSetFixture = new PapyrusEditorFixture();

	public static final String CLASS1 = "Class1";

	public static final String CONSTRAINT_OCL = "ConstraintOCL";

	public static final String CONSTRAINT_CPP = "ConstraintCPP";

	@Test
	public void noMetaclassAndNoContextTest() {
		Package model = modelSetFixture.getModel();
		assertThat("RootElement must exist", model != null);

		Class class1 = (Class) model.getPackagedElement(CLASS1);
		assertThat(String.format("Model must contain a class named %s", CLASS1), class1 != null);

		Constraint constraintOCL = class1.getOwnedRule(CONSTRAINT_OCL);
		assertThat(String.format("%s must contain a constraint called %s", CLASS1, CONSTRAINT_OCL), constraintOCL != null);

		Constraint constraintCPP = class1.getOwnedRule(CONSTRAINT_CPP);
		assertThat(String.format("%s must contain a constraint called %s", CLASS1, CONSTRAINT_CPP), constraintCPP != null);

		IDirectEditorExtensionPoint oclExtension =
				DirectEditorsUtil.getDefaultDirectEditorExtension(constraintOCL);

		assertThat(String.format("editor extension for %s must be non null", CONSTRAINT_OCL), oclExtension != null);
		assertThat(String.format("configuration for %s must be non null", CONSTRAINT_OCL), oclExtension.getDirectEditorConfiguration() != null);
		assertThat(String.format("language for constraint %s must be OCL", CONSTRAINT_OCL), oclExtension.getDirectEditorConfiguration().getLanguage().equals("Essential OCL constraint editor"));

		IDirectEditorExtensionPoint cppExtension =
				DirectEditorsUtil.getDefaultDirectEditorExtension(constraintCPP);

		assertThat(String.format("editor extension for %s must be non null", CONSTRAINT_CPP), cppExtension != null);
		assertThat(String.format("configuration for %s must be non null", CONSTRAINT_CPP), cppExtension.getDirectEditorConfiguration() != null);
		assertThat(String.format("language for constraint %s must be CPP", CONSTRAINT_CPP), cppExtension.getDirectEditorConfiguration().getLanguage().equals("Textual editor for constraint edition with C++"));

		// obtain constraint specification (opaque expression)
		ValueSpecification specificationOCL = constraintOCL.getSpecification();
		assertThat(String.format("specification of constraint %s must be an opaque expression", CONSTRAINT_OCL), specificationOCL instanceof OpaqueExpression);
		IDirectEditorExtensionPoint oclExtensionOE =
				DirectEditorsUtil.getDefaultDirectEditorExtension(specificationOCL);

		// same tests as above, but for opaque expression
		assertThat(String.format("editor extension for %s must be non null", CONSTRAINT_OCL), oclExtensionOE != null);
		assertThat(String.format("configuration for %s must be non null", CONSTRAINT_OCL), oclExtensionOE.getDirectEditorConfiguration() != null);
		assertThat(String.format("language for constraint %s must be OCL", CONSTRAINT_OCL), oclExtensionOE.getDirectEditorConfiguration().getLanguage().equals("Essential OCL constraint editor"));

		ValueSpecification specificationCPP = constraintCPP.getSpecification();
		assertThat(String.format("specification of constraint %s must be an opaque expression", CONSTRAINT_CPP), specificationCPP instanceof OpaqueExpression);

		IDirectEditorExtensionPoint cppExtensionOE =
				DirectEditorsUtil.getDefaultDirectEditorExtension(specificationCPP);

		assertThat(String.format("editor extension for %s must be non null", CONSTRAINT_CPP), cppExtensionOE != null);
		assertThat(String.format("configuration for %s must be non null", CONSTRAINT_CPP), cppExtensionOE.getDirectEditorConfiguration() != null);
		assertThat(String.format("language for constraint %s must be CPP", CONSTRAINT_CPP), cppExtensionOE.getDirectEditorConfiguration().getLanguage().equals("Textual editor for constraint edition with C++"));
	}
}
