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

package org.eclipse.papyrus.infra.gmfdiag.common.sync.tests;

import static org.eclipse.papyrus.junit.framework.runner.ScenarioRunner.verificationPoint;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collections;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.junit.framework.runner.Scenario;
import org.eclipse.papyrus.junit.framework.runner.ScenarioRunner;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * Test suite for master/slave notation synchronization.
 */
@RunWith(ScenarioRunner.class)
@PluginResource("models/sync/sync-test.di")
@ActiveDiagram("master")
public class MasterSlaveNotationSyncTest {

	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	final PapyrusEditorFixture editor = new PapyrusEditorFixture();

	@Rule
	public final TestRule sync = TestSyncFixture.compose(editor);

	private Package rootPackage;

	private Class class1;

	private Class class2;

	private Association class1_class2;

	private DiagramEditPart master;

	private GraphicalEditPart class1EP;

	private GraphicalEditPart class2EP;

	public MasterSlaveNotationSyncTest() {
		super();
	}

	@Test
	public void initialSyncOfEmptyDiagram() {
		DiagramEditPart slave = activate("slave1");

		EditPart class1Slave = editor.requireEditPart(slave, class1);
		assertThat(getBounds(class1Slave), is(getBounds(class1EP)));
		EditPart class2Slave = editor.requireEditPart(slave, class2);
		assertThat(getBounds(class2Slave), is(getBounds(class2EP)));

		assertThat(editor.findEditPart(class1Slave, class1_class2), notNullValue());

		// The editor is not marked dirty by this synchronization (it is effected by unprotected writes)
		assertThat(editor.getEditor().isDirty(), is(false));
	}

	@Scenario({ "move", "undo", "redo" })
	public void syncNodeLocation() {
		// Open the slave diagram and return to the master
		DiagramEditPart slave = activate("slave2");
		editor.activateDiagram(master);

		// Compute and actuate a move of Class2 in the master diagram
		final Rectangle oldBounds = getBounds(class2EP);
		final Rectangle newBounds = oldBounds.getTranslated(20, 20);

		editor.move(class2EP, newBounds.getLocation());

		EditPart class2Slave = editor.requireEditPart(slave, class2);

		if (verificationPoint()) {
			assertThat(getBounds(class2Slave), is(newBounds));
		}

		editor.undo();

		if (verificationPoint()) {
			assertThat(getBounds(class2Slave), is(oldBounds));
		}

		editor.redo();

		if (verificationPoint()) {
			assertThat(getBounds(class2Slave), is(newBounds));
		}
	}

	@Scenario({ "resize", "undo", "redo" })
	public void syncNodeSize() {
		// Open the slave diagram and return to the master
		DiagramEditPart slave = activate("slave2");
		editor.activateDiagram(master);

		// Compute and actuate a resize of Class2 in the master diagram
		final Rectangle oldBounds = getBounds(class2EP);
		final Rectangle newBounds = oldBounds.getResized(oldBounds.width / 2, oldBounds.height / 2);

		editor.resize(class2EP, newBounds.getSize());

		EditPart class2Slave = editor.requireEditPart(slave, class2);

		if (verificationPoint()) {
			assertThat(getBounds(class2Slave), is(newBounds));
		}

		editor.undo();

		if (verificationPoint()) {
			assertThat(getBounds(class2Slave), is(oldBounds));
		}

		editor.redo();

		if (verificationPoint()) {
			assertThat(getBounds(class2Slave), is(newBounds));
		}
	}

	@Scenario({ "create", "undo", "redo" })
	public void createNewNode() {
		// Open the slave diagram and return to the master
		DiagramEditPart slave = activate("slave2");
		editor.activateDiagram(master);

		// Create a new Class
		Class newClass = UMLFactory.eINSTANCE.createClass();
		newClass.setName("NewClass");
		editor.execute(AddCommand.create(editor.getEditingDomain(), rootPackage, UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT, newClass));

		// Create a view of the class, as a new command, to ensure that undo/redo
		// doesn't affect the creation of the new class in the model (only the diagram)
		DropObjectsRequest drop = new DropObjectsRequest();
		drop.setObjects(Collections.singletonList(newClass));
		drop.setLocation(new Point(90, 30));
		editor.execute(master.getCommand(drop));

		Rectangle initialBounds = getBounds(editor.requireEditPart(master, newClass));

		if (verificationPoint()) {
			EditPart newClassSlave = editor.requireEditPart(slave, newClass);
			assertThat(getBounds(newClassSlave), is(initialBounds));
		}

		editor.undo();

		if (verificationPoint()) {
			EditPart newClassSlave = editor.findEditPart(slave, newClass);
			assertThat(newClassSlave, nullValue());
		}

		editor.redo();

		if (verificationPoint()) {
			EditPart newClassSlave = editor.requireEditPart(slave, newClass);
			assertThat(getBounds(newClassSlave), is(initialBounds));
		}
	}

	@Test
	public void newNodeOfWrongMetaclassNotSynchronized() {
		// Open the slave diagram and return to the master
		DiagramEditPart slave = activate("slave2");
		editor.activateDiagram(master);

		// Create a new Package
		Package newPackage = UMLFactory.eINSTANCE.createPackage();
		newPackage.setName("NewPackage");
		editor.execute(AddCommand.create(editor.getEditingDomain(), rootPackage, UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT, newPackage));

		// Create a view of the package
		DropObjectsRequest drop = new DropObjectsRequest();
		drop.setObjects(Collections.singletonList(newPackage));
		drop.setLocation(new Point(90, 30));
		editor.execute(master.getCommand(drop));

		EditPart newPackageSlave = editor.findEditPart(slave, newPackage);
		assertThat(newPackageSlave, nullValue());
	}

	@Scenario({ "deleteView", "undo", "redo" })
	public void deleteNode() {
		// Open the slave diagram and return to the master
		DiagramEditPart slave = activate("slave2");
		editor.activateDiagram(master);

		// Hide the view of Class2 in the master diagram
		GroupRequest request = new GroupRequest(RequestConstants.REQ_DELETE);
		editor.execute(class2EP.getCommand(request));

		if (verificationPoint()) {
			assertThat(editor.findEditPart(slave, class2), nullValue());
			assertThat("Class2 deleted from semantic model, also", class2.eResource(), is(editor.getModelResource()));

			// Of course, the connected edges would be removed, anyways
			assertThat(editor.findEditPart(editor.requireEditPart(slave, class1), class1_class2), nullValue());
		}

		editor.undo();

		if (verificationPoint()) {
			EditPart class2Slave = editor.requireEditPart(slave, class2);
			editor.requireEditPart(class2Slave, class1_class2); // connections must also be restored
		}

		editor.redo();

		if (verificationPoint()) {
			assertThat(editor.findEditPart(slave, class2), nullValue());
			assertThat(editor.findEditPart(editor.requireEditPart(slave, class1), class1_class2), nullValue());
		}
	}

	//
	// Test framework
	//

	@Before
	public void gatherReferences() {
		rootPackage = editor.getModel();

		class1 = (Class) rootPackage.getOwnedType("Class1");
		class2 = (Class) rootPackage.getOwnedType("Class2");
		class1_class2 = class1.getOwnedAttribute(null, class2).getAssociation();

		master = editor.getActiveDiagramEditor().getDiagramEditPart();
		class1EP = (GraphicalEditPart) editor.requireEditPart(master, class1);
		class2EP = (GraphicalEditPart) editor.requireEditPart(master, class2);
	}

	DiagramEditPart activate(String diagramName) {
		DiagramEditPart result = editor.getDiagram(diagramName);
		if (result == null) {
			editor.openDiagram(diagramName);
			result = editor.getActiveDiagram();
		} else {
			editor.activateDiagram(diagramName);
		}
		return result;
	}

	Rectangle getBounds(EditPart editPart) {
		GraphicalEditPart graphical = TypeUtils.as(editPart, GraphicalEditPart.class);
		assertThat("Edit-part is not a GraphicalEditPart", graphical, notNullValue());

		return graphical.getFigure().getBounds();
	}
}
