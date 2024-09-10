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
package org.eclipse.papyrus.uml.diagram.stereotypeproperty.tests.asbrace;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.figures.BorderedNodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.PapyrusWrappingLabel;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.clazz.CreateClassDiagramCommand;
import org.eclipse.papyrus.uml.diagram.common.figure.node.ClassifierFigure;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayConstant;
import org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractPapyrusTestCase;
import org.eclipse.uml2.uml.NamedElement;
import org.junit.Rule;
import org.junit.Test;


/**
 * Reopen a file in which applied stereotypes are displayed as brace .
 * Verify that all properties fo the applied stereotype are displayed.
 * Verify requirements org.eclipse.papyrus.uml.diagram.stereotype.edition.REQ_001
 * - Stereotype application properties could be displayed as "comment" inside a compartment, or as brace label
 * Verify requirements org.eclipse.papyrus.uml.diagram.stereotype.edition.REQ_013
 * - When the applied stereotype properties are displayed as brace, all this data is display in a label under the name label.
 */
@PluginResource({ TestReopenAsBrace.MODEL_DI })
public class TestReopenAsBrace extends AbstractPapyrusTestCase {
	/** name of the test project */
	public final String PROJECT_NAME = "TestReopenAsBraceProject";

	/** name of the test model */
	public final String FILE_NAME = "TestReopenAsBraceTest.di";

	static final String MODEL_DI = "resource/ReopenFileBrace.di"; //$NON-NLS-1$

	static final String STEREOTYPEDCLASS = "StereotypedClass"; //$NON-NLS-1$
	static final String CLASS1 = "Class1"; //$NON-NLS-1$
	static final String STEREOTYPEDLINK = "StereotypedLink"; //$NON-NLS-1$

	static final String PROFILE = "testProfile"; //$NON-NLS-1$

	private IMultiDiagramEditor editor;

	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();


	@Test
	@ActiveDiagram("ClassDiagram")
	public void testReopenStereotypeAsComment() {
		View appliedStereotypeBraceNotation = null;
		editor = fixture.getEditor(MODEL_DI);
		fixture.openDiagram("ClassDiagram"); //$NON-NLS-1$
		// Check initial model
		{// test the node class edipart
			NamedElement stereotypedClass = fixture.getModel().getOwnedMember(STEREOTYPEDCLASS);
			GraphicalEditPart stereotypedClassEditPart = (GraphicalEditPart) fixture.findEditPart(stereotypedClass);
			// look for the applied stereotype compartment
			View notationClass1 = stereotypedClassEditPart.getNotationView();
			for (int i = 0; i < notationClass1.getChildren().size(); i++) {
				View view = (View) notationClass1.getChildren().get(i);
				if (view.getType().equals(StereotypeDisplayConstant.STEREOTYPE_BRACE_TYPE)) {
					appliedStereotypeBraceNotation = view;
				}
			}
			assertNotNull("notation brace must be not null", appliedStereotypeBraceNotation);
			// look for the the editpart for the comment
			assertTrue("stereotype brace must not be visible", appliedStereotypeBraceNotation.isVisible());
			stereotypedClassEditPart.refresh();
			org.eclipse.papyrus.infra.gmfdiag.common.figure.node.SVGNodePlateFigure nodePlate = (org.eclipse.papyrus.infra.gmfdiag.common.figure.node.SVGNodePlateFigure) ((BorderedNodeFigure) stereotypedClassEditPart.getFigure()).getChildren().get(0);
			// now verify position of each subfigure
			ClassifierFigure class1figure = ((ClassifierFigure) nodePlate.getChildren().get(0));
			assertTrue("the subFigure [1] of the classifier must be a PapyrusWrappingLabel", class1figure.getChildren().get(1) instanceof PapyrusWrappingLabel);
			PapyrusWrappingLabel braceLable = (PapyrusWrappingLabel) class1figure.getChildren().get(1);
			assertEquals("The display of the brace label must be correct",
					"{testReftoStereotype2=[] , testUnaryReftoStereotype2=null , testDataType , testMyPrimituveType , testInt=0 , testMultiString=[] , testRefToMetaclass=null , testRefToEnumeration=EnumerationLiteral1 , testMultiDataType=[] , testMultiprimitiveType=[] , testMultiInt=[] , testMultiEnumeration=[] , testBoolean=false , testMultiBoolean=[] , testString , testReal=0.0 , testMultiReal=[] }",
					braceLable.getText());
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
