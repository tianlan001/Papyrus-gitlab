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
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.junit.framework.runner.Scenario;
import org.eclipse.papyrus.junit.framework.runner.ScenarioRunner;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * Test suite for master/slave notation synchronization overrides.
 */
@RunWith(ScenarioRunner.class)
@PluginResource("models/sync/sync-test.di")
@ActiveDiagram("master")
public class NotationSyncOverrideTest {

	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	final PapyrusEditorFixture editor = new PapyrusEditorFixture();

	@Rule
	public final TestRule sync = TestSyncFixture.compose(editor);

	private Package rootPackage;

	private Class class2;

	private DiagramEditPart master;

	private GraphicalEditPart class2EP;

	public NotationSyncOverrideTest() {
		super();
	}

	@Scenario({ "move", "override", "undo", "redo" })
	public void overrideSyncNodeLocation() {
		// Open the slave diagram and return to the master
		DiagramEditPart slave = activate("slave2");
		editor.activateDiagram(master);

		// Compute and actuate a resize of Class2 in the master diagram
		final Rectangle oldBounds = getBounds(class2EP);
		final Rectangle newBounds = oldBounds.getTranslated(20, 20);

		editor.move(class2EP, newBounds.getLocation());

		GraphicalEditPart class2Slave = (GraphicalEditPart) editor.requireEditPart(slave, class2);

		if (verificationPoint()) {
			assertThat(getBounds(class2Slave), is(newBounds));
		}

		// Now, in the slave diagram, move its class2
		editor.activateDiagram(slave);

		final Rectangle override = newBounds.getTranslated(-40, 40);
		editor.move(class2Slave, override.getLocation());

		// And move the shape in the master diagram again
		editor.activateDiagram(master);
		editor.move(class2EP, oldBounds.getLocation());
		editor.activateDiagram(slave);

		if (verificationPoint()) {
			assertThat(getBounds(class2EP), is(oldBounds)); // The master is, indeed, moved
			assertThat(getBounds(class2Slave), is(override)); // But the slave is overridden
		}

		editor.undo(); // Undo master move
		editor.undo(); // Undo slave override
		editor.undo(); // Undo master move before that

		if (verificationPoint()) {
			assertThat(getBounds(class2Slave), is(oldBounds));
		}

		editor.redo(); // Redo first master move
		editor.redo(); // Redo slave override
		editor.redo(); // Redo master move

		if (verificationPoint()) {
			assertThat(getBounds(class2Slave), is(override));
		}
	}

	@Scenario({ "move", "override", "undo", "redo" })
	public void overrideSyncNodeSize() {
		// Open the slave diagram and return to the master
		DiagramEditPart slave = activate("slave2");
		editor.activateDiagram(master);

		// Compute and actuate a move of Class2 in the master diagram
		final Rectangle oldBounds = getBounds(class2EP);
		final Rectangle newBounds = oldBounds.getResized(oldBounds.width / 2, oldBounds.height / 2);

		editor.resize(class2EP, newBounds.getSize());

		GraphicalEditPart class2Slave = (GraphicalEditPart) editor.requireEditPart(slave, class2);

		if (verificationPoint()) {
			assertThat(getBounds(class2Slave), is(newBounds));
		}

		// Now, in the slave diagram, resize its class2
		editor.activateDiagram(slave);

		final Rectangle override = newBounds.getResized(-oldBounds.width / 3, -oldBounds.height / 3);
		editor.resize(class2Slave, override.getSize());

		// And resize the shape in the master diagram again
		editor.activateDiagram(master);
		editor.resize(class2EP, oldBounds.getSize());
		editor.activateDiagram(slave);

		if (verificationPoint()) {
			assertThat(getBounds(class2EP), is(oldBounds)); // The master is, indeed, resized
			assertThat(getBounds(class2Slave), is(override)); // But the slave is overridden
		}

		editor.undo(); // Undo master resize
		editor.undo(); // Undo slave override
		editor.undo(); // Undo master resize before that

		if (verificationPoint()) {
			assertThat(getBounds(class2Slave), is(oldBounds));
		}

		editor.redo(); // Redo first master resize
		editor.redo(); // Redo slave override
		editor.redo(); // Redo master resize

		if (verificationPoint()) {
			assertThat(getBounds(class2Slave), is(override));
		}
	}

	//
	// Test framework
	//

	@Before
	public void gatherReferences() {
		rootPackage = editor.getModel();

		class2 = (Class) rootPackage.getOwnedType("Class2");

		master = editor.getActiveDiagramEditor().getDiagramEditPart();
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
