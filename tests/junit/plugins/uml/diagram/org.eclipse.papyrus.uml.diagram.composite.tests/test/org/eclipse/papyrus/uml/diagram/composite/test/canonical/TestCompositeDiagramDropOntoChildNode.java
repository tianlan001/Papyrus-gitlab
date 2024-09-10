/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.composite.test.canonical;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.uml.diagram.composite.CreateCompositeDiagramCommand;
import org.eclipse.papyrus.uml.diagram.composite.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.composite.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.composite.test.ICompositeDiagramTestsConstants;
import org.eclipse.papyrus.uml.diagram.tests.canonical.TestChildNode;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

/**
 * Regression tests for drag-and-drop scenarios in the composite diagram.
 */
public class TestCompositeDiagramDropOntoChildNode extends TestChildNode {

	public TestCompositeDiagramDropOntoChildNode() {
		super();
	}

	@Override
	public DiagramUpdater getDiagramUpdater() {
		return UMLDiagramUpdater.INSTANCE;
	}

	@Override
	protected ICreationCommand getDiagramCommandCreation() {
		return new CreateCompositeDiagramCommand();
	}

	@Override
	protected CreateViewRequest createViewRequestShapeContainer() {
		return CreateViewRequestFactory.getCreateShapeRequest(UMLElementTypes.Class_Shape, getDiagramEditPart().getDiagramPreferencesHint());
	}

	@Override
	protected String getProjectName() {
		return ICompositeDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return ICompositeDiagramTestsConstants.FILE_NAME;
	}


	/**
	 * Test that we cannot drop an association as type of a part.
	 */
	@Test
	public void dropAssociationOntoPart_bug470823() {
		// first, create a part in the diagram
		testToCreateANode(UMLElementTypes.Property_Shape, 0, 0, 1, 1, true, "Attribute", 0);
		EditPart partEditPart = (EditPart) getContainerEditPart().getChildren().get((getContainerEditPart().getChildren().size() - 1));

		// then, create an association between two classes
		final Association[] association = { null };
		getCommandStack().execute(new RecordingCommand(getContainerEditPart().getEditingDomain()) {

			@Override
			protected void doExecute() {
				org.eclipse.uml2.uml.Package package_ = getRootSemanticModel().getNearestPackage();
				Type type1 = package_.createOwnedType("T1", UMLPackage.Literals.CLASS);
				Type type2 = package_.createOwnedType("T2", UMLPackage.Literals.CLASS);
				association[0] = type1.createAssociation(true, AggregationKind.NONE_LITERAL, null, 0, 1, type2,
						true, AggregationKind.NONE_LITERAL, null, 0, 1);
			}
		});

		// now, check that we cannot drop this association onto the part to set it as the part's type
		DropObjectsRequest dropObjectsRequest = new DropObjectsRequest();
		dropObjectsRequest.setObjects(new ArrayList<Association>(Arrays.asList(association)));
		dropObjectsRequest.setLocation(new Point(40, 40));
		Command command = partEditPart.getCommand(dropObjectsRequest);
		if (command != null) {
			assertFalse("Can drop an association onto a part", command.canExecute());
		}
	}

}
