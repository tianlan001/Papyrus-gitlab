/*****************************************************************************
 * Copyright (c) 2013, 2015 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 431618
 *  Christian W. Damus - bug 464647
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.tests.drop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.Element;
import org.junit.Assert;
import org.junit.Before;

/**
 * Abstract class to test nodes
 */
public abstract class AbstractDropNodeTest extends org.eclipse.papyrus.uml.diagram.tests.canonical.AbstractPapyrusTestCase {

	/** <code>true</code> if semantic tests should be run on graphical manipulation */
	private boolean testSemantic;

	/**
	 * @see org.eclipse.papyrus.diagram.clazz.test.canonical.AbstractPapyrusTestCase#setUp()
	 *
	 * @throws Exception
	 */
	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		testSemantic = isSemanticTest();

	}

	/**
	 * Returns <code>true</code> if semantic tests should be also performed
	 *
	 * @return <code>true</code> if semantic tests should be also performed
	 */
	protected boolean isSemanticTest() {
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.diagram.clazz.test.canonical.AbstractPapyrusTestCase#getRootView()
	 *
	 * @return
	 */
	@Override
	protected abstract View getRootView();

	/**
	 * Returns the container edit part
	 */
	protected abstract IGraphicalEditPart getContainerEditPart();

	/**
	 * Test to drop node.
	 *
	 */
	public void testToDropNode(IElementType type, EClass eClass, boolean mustSuceed) {
		// Precondition for the drag node test: There must be an element in of the desired type in the semantic model.
		createNodeOptionally(type, getContainerEditPart());

		// The element can be dropped several time in the diagrams
		testDrop(type, eClass, 1, 1, 1);

		// undo the drop
		undoOnUIThread();
	}

	/**
	 * Test drop.
	 *
	 * @param type
	 *            the type
	 */
	public void testDrop(IElementType type, EClass eClass, int expectedGraphicalChildren, int expectedSemanticChildren, int addedGraphicalChildren) {


		// DROP
		assertEquals(DROP + INITIALIZATION_TEST, expectedGraphicalChildren, getRootView().getChildren().size());
		if (testSemantic) {
			assertEquals(DROP + INITIALIZATION_TEST, expectedSemanticChildren, getRootSemanticModel().getOwnedElements().size());
		}
		DropObjectsRequest dropObjectsRequest = new DropObjectsRequest();
		ArrayList<Element> list = new ArrayList<Element>();
		for (Element element : getRootSemanticModel().getOwnedElements()) {
			if (element != null && element.eClass().equals(eClass)) {
				list.add(element);
			}
		}
		dropObjectsRequest.setObjects(list);
		dropObjectsRequest.setLocation(new Point(40, 40));
		Command command = getContainerEditPart().getCommand(dropObjectsRequest);
		assertNotNull(DROP + COMMAND_NULL, command);
		assertTrue(DROP + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue(DROP + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute());
		// execute the drop
		executeOnUIThread(command);
		Assert.assertEquals(DROP + TEST_THE_EXECUTION, expectedGraphicalChildren + addedGraphicalChildren, getRootView().getChildren().size());
		if (testSemantic) {
			Assert.assertEquals(DROP + TEST_THE_EXECUTION, expectedSemanticChildren, getRootSemanticModel().getOwnedElements().size());
		}
		// undo the drop
		undoOnUIThread();
		Assert.assertEquals(DROP + TEST_THE_UNDO, expectedGraphicalChildren, getRootView().getChildren().size());
		if (testSemantic) {
			Assert.assertEquals(DROP + TEST_THE_UNDO, expectedSemanticChildren, getRootSemanticModel().getOwnedElements().size());
		}
		// redo the drop
		redoOnUIThread();
		Assert.assertEquals(DROP + TEST_THE_REDO, expectedGraphicalChildren + addedGraphicalChildren, getRootView().getChildren().size());
		if (testSemantic) {
			assertTrue(DROP + TEST_THE_REDO, getRootSemanticModel().getOwnedElements().size() != 0);
		}
	}

}
