/*****************************************************************************
 * Copyright (c) 2018 Christian W. Damus, CEA LIST, and others.
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

package org.eclipse.papyrus.uml.diagram.sequence.tests.bug;

import static org.eclipse.papyrus.uml.diagram.sequence.tests.bug.TimeElementCreationTest.getGeometry;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.requests.BendpointRequest;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.papyrus.infra.types.core.registries.ElementTypeSetConfigurationRegistry;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.sequence.tests.bug.TimeElementCreationTest.What;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.TimeConstraint;
import org.eclipse.uml2.uml.TimeObservation;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Regression tests for moving (with undo) of {@link TimeObservation}s and
 * {@link TimeConstraint}s in the sequence diagram editor.
 *
 * @author Christian W. Damus
 * @see <a href="http://eclip.se/537571">bug 537571</a>
 */
@PluginResource("resource/bugs/bug537571-times.di")
@ActiveDiagram("scenario")
@RunWith(Parameterized.class)
public class TimeElementMoveTest extends AbstractPapyrusTest {
	private static final boolean SEND = true;
	private static final boolean RECV = true;

	@Rule
	public final PapyrusEditorFixture editor = new PapyrusEditorFixture();

	private final What what;

	static {
		// Kick the modeled element types registry that is needed by the tests
		ElementTypeSetConfigurationRegistry.getInstance();
	}

	/**
	 * Initializes me.
	 *
	 * @param what
	 *            what to create
	 */
	public TimeElementMoveTest(What what) {
		super();

		this.what = what;
	}

	@Test
	public void moveOnMessageSendOnLifeline() {
		EditPart lifelineEP = editor.findEditPart("foo", Lifeline.class);
		EditPart messageEP = editor.findEditPart("message1", Message.class);
		PointList points = getGeometry(messageEP, PointList.class);

		EditPart timeEP = editor.createShape(lifelineEP, what.getElementType(),
				points.getFirstPoint(), null);

		moveMessageAssertion(messageEP, timeEP, SEND);
	}

	private void moveMessageAssertion(EditPart messageEP, EditPart timeEP, boolean sourceEnd) {
		PointList messageGeom = getGeometry(messageEP, PointList.class);
		int messageY = (sourceEnd ? messageGeom.getFirstPoint() : messageGeom.getLastPoint()).y();
		Rectangle original = getGeometry(timeEP, Rectangle.class);
		Rectangle expected = original.getCopy();
		expected.setY(messageY - 20);

		move(messageEP, 0, -20);

		assertThat("Not moved correctly", getGeometry(timeEP, Rectangle.class), equalGeometry(expected));

		expected.translate(0, 20);
		editor.undo();

		assertThat("Not tracked on undo", getGeometry(timeEP, Rectangle.class), equalGeometry(original));
	}

	@Test
	public void moveOnMessageReceiveOnExecution() {
		EditPart execEP = editor.findEditPart("exec2", ExecutionSpecification.class);
		EditPart messageEP = editor.findEditPart("message4", Message.class);
		Rectangle bounds = getGeometry(execEP, Rectangle.class);
		PointList points = getGeometry(messageEP, PointList.class);

		EditPart timeEP = editor.createShape(execEP, what.getElementType(),
				new Point(bounds.getTop().x(), points.getLastPoint().y()), null);

		moveMessageAssertion(messageEP, timeEP, RECV);
	}

	//
	// Test framework
	//

	@Parameters(name = "{0}")
	public static Iterable<Object[]> parameters() {
		return TimeElementCreationTest.parameters();
	}

	@Before
	public void maximizeEditor() {
		IEditorPart editor = this.editor.getEditor();
		IWorkbenchPage page = editor.getSite().getPage();
		page.setPartState(page.getReference(editor), IWorkbenchPage.STATE_MAXIMIZED);
		this.editor.flushDisplayEvents();
	}

	/**
	 * Work around the absence of an {@code equals} method in the {@link PointList} class.
	 *
	 * @param geometry
	 *            a geometry to test for equality with an actual observed geometry
	 * @return the geometry matcher
	 */
	static Matcher<Object> equalGeometry(Object geometry) {
		return new CustomTypeSafeMatcher<Object>("equals " + geometry) {
			@Override
			protected boolean matchesSafely(Object item) {

				return ((item instanceof PointList) && (geometry instanceof PointList))
						? Arrays.equals(((PointList) item).toIntArray(),
								((PointList) geometry).toIntArray())
						: Objects.equals(item, geometry);
			}
		};
	}

	void move(EditPart editPart, int deltaX, int deltaY) {
		Request move;
		if (editPart instanceof ConnectionEditPart) {
			ConnectionEditPart connection = (ConnectionEditPart) editPart;
			Point mid = getGeometry(connection, PointList.class).getMidpoint();
			mid.translate(deltaX, deltaY);

			// Messages are moved by the gesture that creates bendpoints
			BendpointRequest bendpoint = new BendpointRequest();
			bendpoint.setType(RequestConstants.REQ_CREATE_BENDPOINT);
			bendpoint.setSource(connection);
			bendpoint.setLocation(mid);
			move = bendpoint;
		} else {
			ChangeBoundsRequest bounds = new ChangeBoundsRequest(RequestConstants.REQ_MOVE);
			bounds.setEditParts(editPart);
			bounds.setConstrainedMove(false);
			bounds.setMoveDelta(new Point(deltaX, deltaY));
			move = bounds;
		}

		EditPart target = editPart.getTargetEditPart(move);
		editor.execute(Optional.ofNullable(target).orElse(editPart).getCommand(move));
	}
}
