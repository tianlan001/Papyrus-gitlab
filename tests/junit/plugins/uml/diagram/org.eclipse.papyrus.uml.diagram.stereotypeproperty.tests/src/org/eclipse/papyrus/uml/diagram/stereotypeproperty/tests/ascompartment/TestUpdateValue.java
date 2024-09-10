/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Francois Le Fevre  francois.le-fevre@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.stereotypeproperty.tests.ascompartment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Iterator;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.draw2d.ui.text.TextFlowEx;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.clazz.CreateClassDiagramCommand;
import org.eclipse.papyrus.uml.diagram.common.figure.node.EditingFlowPage;
import org.eclipse.papyrus.uml.diagram.stereotype.edition.editpart.AppliedStereotypeCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.stereotype.edition.editpart.AppliedStereotypeMultilinePropertyEditPart;
import org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractPapyrusTestCase;
import org.eclipse.uml2.uml.NamedElement;
import org.junit.Rule;
import org.junit.Test;


/**
 * Open a model with a stereotype that is apply on the class with the display as compartment.
 * Observe the value of the stereotype.
 * Change the value.
 * The display must have changed.
 *
 * Verify requirements org.eclipse.papyrus.uml.diagram.stereotype.edition.REQ_006
 * - Update the display of applied stereotype properties when the affected values have changed
 */
@PluginResource({ TestUpdateValue.MODEL_DI })
public class TestUpdateValue extends AbstractPapyrusTestCase {
	/** name of the test project */
	public final String PROJECT_NAME = "TestUpdateValueProject";

	/** name of the test model */
	public final String FILE_NAME = "TestUpdateValue.di";

	final static String MODEL_DI = "resource/ReopenFileWithString.di"; //$NON-NLS-1$

	final static String STEREOTYPEDCLASS = "StereotypedClass"; //$NON-NLS-1$
	final static String CLASS1 = "Class1"; //$NON-NLS-1$
	final static String STEREOTYPEDLINK = "StereotypedLink"; //$NON-NLS-1$

	final static String PROFILE = "testProfile"; //$NON-NLS-1$

	private IMultiDiagramEditor editor;

	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();


	@Test
	@ActiveDiagram("ClassDiagram")
	public void testUpdateValue() {

		editor = fixture.getEditor(MODEL_DI);
		fixture.openDiagram("ClassDiagram"); //$NON-NLS-1$
		// Check initial model
		// test the node class edipart
		final NamedElement stereotypedClass = fixture.getModel().getOwnedMember(STEREOTYPEDCLASS);
		EditPart stereotypedClassEditPart = fixture.findEditPart(stereotypedClass);
		stereotypedClassEditPart.refresh();
		// look for the the editpart for the comment
		AppliedStereotypeCompartmentEditPart appliedStereotypeCompartmentEditPart = null;
		for (Iterator<?> iterator = stereotypedClassEditPart.getChildren().iterator(); iterator.hasNext();) {
			Object child = iterator.next();
			if (child instanceof AppliedStereotypeCompartmentEditPart) {
				appliedStereotypeCompartmentEditPart = (AppliedStereotypeCompartmentEditPart) child;
			}
		}
		assertNotNull("The applied Stereotype as Compartment must be alive", appliedStereotypeCompartmentEditPart);


		assertEquals(" 1 Stererotype  property must be displayed in the comment ", 1, appliedStereotypeCompartmentEditPart.getChildren().size());

		{
			AppliedStereotypeMultilinePropertyEditPart property = (AppliedStereotypeMultilinePropertyEditPart) appliedStereotypeCompartmentEditPart.getChildren().get(0);
			EditingFlowPage editingFlowPage = (EditingFlowPage) property.getFigure();
			TextFlowEx textFlowEx = (TextFlowEx) editingFlowPage.getChildren().get(0);
			String text = "" + textFlowEx.getText();
			assertEquals(" the display of the stereotype must be correct ", "testString ", text);

		}
		{// test the udpate
			RecordingCommand updateValue = new RecordingCommand(fixture.getEditingDomain()) {

				@Override
				protected void doExecute() {
					stereotypedClass.setValue(stereotypedClass.getAppliedStereotype("testProfile::Stereotype1"), "testString", "TestMyInputString");

				}
			};
			fixture.getEditingDomain().getCommandStack().execute(updateValue);
			stereotypedClassEditPart.refresh();
			AppliedStereotypeMultilinePropertyEditPart property = (AppliedStereotypeMultilinePropertyEditPart) appliedStereotypeCompartmentEditPart.getChildren().get(0);
			EditingFlowPage editingFlowPage = (EditingFlowPage) property.getFigure();
			TextFlowEx textFlowEx = (TextFlowEx) editingFlowPage.getChildren().get(0);
			String text = "" + textFlowEx.getText();
			assertEquals(" the display of the stereotype must be correct ", "testString=TestMyInputString ", text);


		}


	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractPapyrusTestCase#getDiagramCommandCreation()
	 *
	 * @return
	 */
	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateClassDiagramCommand();
	}


	/**
	 * @see org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractPapyrusTestCase#getProjectName()
	 *
	 * @return
	 */
	@Override
	protected String getProjectName() {
		return PROJECT_NAME;
	}


	/**
	 * @see org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractPapyrusTestCase#getFileName()
	 *
	 * @return
	 */
	@Override
	protected String getFileName() {
		return FILE_NAME;
	}

}
