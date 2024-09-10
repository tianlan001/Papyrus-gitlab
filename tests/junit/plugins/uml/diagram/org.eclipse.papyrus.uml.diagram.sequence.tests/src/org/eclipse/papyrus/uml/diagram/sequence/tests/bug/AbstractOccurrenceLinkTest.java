/*****************************************************************************
 * Copyright (c) 2018 CEA LIST, EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.sequence.tests.bug;

import java.util.Arrays;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.uml.diagram.sequence.anchors.CenterAnchor;
import org.eclipse.papyrus.uml.diagram.sequence.anchors.ConnectionSourceAnchor;
import org.eclipse.papyrus.uml.diagram.sequence.anchors.ConnectionTargetAnchor;
import org.eclipse.papyrus.uml.diagram.sequence.anchors.NodeBottomAnchor;
import org.eclipse.papyrus.uml.diagram.sequence.anchors.NodeTopAnchor;
import org.eclipse.papyrus.uml.diagram.sequence.edit.policies.UpdateWeakReferenceForExecSpecEditPolicy;
import org.eclipse.uml2.uml.ActionExecutionSpecification;
import org.eclipse.uml2.uml.DestructionOccurrenceSpecification;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.OccurrenceSpecification;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * <p>
 * Base test class for DurationLinks (DurationConstraint and DurationObservation) and GeneralOrdering
 * </p>
 * <p>
 * These links are really similar in that they use the same Anchors representing
 * {@link OccurrenceSpecification}s, thus mostly behave the same. The only differences
 * are semantic, and they use a different figure.
 * </p>
 */
public abstract class AbstractOccurrenceLinkTest<T extends NamedElement> extends AbstractPapyrusTest {

	@Rule
	public final PapyrusEditorFixture editor = new PapyrusEditorFixture();

	IGraphicalEditPart lifeline1, lifeline3, exec1, exec3, destruction;
	ConnectionNodeEditPart message1, message4;
	ConnectionNodeEditPart[] links;

	private String[] linkNames;

	private Class<T> umlClass;

	private Class<? extends Connection> connectionFigure;

	public AbstractOccurrenceLinkTest(String[] linkNames, Class<T> umlClass, Class<? extends Connection> connectionFigure) {
		Assert.assertTrue("Test setup error: there should be at least 7 links defined for this test class", linkNames.length >= 7);
		this.linkNames = linkNames;
		this.umlClass = umlClass;
		this.connectionFigure = connectionFigure;
	}

	@Before
	public void getElements() {
		lifeline1 = (IGraphicalEditPart) editor.findEditPart("Lifeline1", Lifeline.class);
		lifeline3 = (IGraphicalEditPart) editor.findEditPart("Lifeline3", Lifeline.class);
		exec1 = (IGraphicalEditPart) editor.findEditPart("ActionExecutionSpecification1", ActionExecutionSpecification.class);
		exec3 = (IGraphicalEditPart) editor.findEditPart("ActionExecutionSpecification3", ActionExecutionSpecification.class);
		destruction = (IGraphicalEditPart) editor.findEditPart("Message5ReceiveDestroyEvent", DestructionOccurrenceSpecification.class);

		message1 = (ConnectionNodeEditPart) editor.findEditPart("Message1", Message.class);
		message4 = (ConnectionNodeEditPart) editor.findEditPart("Message4", Message.class);

		links = Arrays.stream(linkNames).map(name -> editor.findEditPart(name, umlClass))
				.map(ConnectionNodeEditPart.class::cast)
				.toArray(ConnectionNodeEditPart[]::new);
	}

	@Test
	public void testLinksAnchors() {
		checkFixedAnchors(links[0], NodeTopAnchor.class, NodeTopAnchor.class);
		checkFixedAnchors(links[1], ConnectionSourceAnchor.class, ConnectionTargetAnchor.class);
		checkFixedAnchors(links[2], NodeTopAnchor.class, ConnectionTargetAnchor.class);
		checkFixedAnchors(links[3], NodeBottomAnchor.class, NodeBottomAnchor.class);
		checkFixedAnchors(links[4], ConnectionTargetAnchor.class, ConnectionSourceAnchor.class);
		checkFixedAnchors(links[5], ConnectionSourceAnchor.class, CenterAnchor.class);
		checkFixedAnchors(links[6], NodeBottomAnchor.class, ConnectionTargetAnchor.class);
	}

	@Test
	public void testLinksDisplay() {
		for (ConnectionNodeEditPart link : links) {
			Assert.assertThat(link.getFigure(), IsInstanceOf.instanceOf(connectionFigure));
		}

		// Check the initial anchors. Note that this test is fragile, since the anchors
		// depend on the start/finish position of ExecSpecs, Messages and the position of
		// destruction events.
		// If any of these elements change, the assertions will fail, even if the Link
		// anchor itself is correct.
		// Anchors are aligned on the grid (Or in the middle of two points on the grid, e.g. for the X location
		// on an ExecutionSpecification).
		// It seems that message anchors are not always perfectly aligned on the grid (Values "301", "401")
		checkPosition(links[0], 130, 100, 410, 160);
		checkPosition(links[1], 140, 180, 400, 180);
		checkPosition(links[2], 420, 180, 140, 240);
		checkPosition(links[3], 420, 280, 410, 301);
		checkPosition(links[4], 410, 340, 410, 400);
		checkPosition(links[5], 410, 400, 690, 500);
		checkPosition(links[6], 130, 401, 410, 580);
	}

	@Test
	public void testAnchorUpdateOnMessages() {
		// Move a message
		ReconnectRequest request = new ReconnectRequest(RequestConstants.REQ_RECONNECT_SOURCE);
		request.setConnectionEditPart(message1);

		Point sourceLocation = ((PolylineConnection) message1.getConnectionFigure()).getStart().getCopy();
		sourceLocation.setY(sourceLocation.y() - 40);
		// sourceLocation.setX(sourceLocation.x() + 3); // Move slightly to the right, to make sure we hover the lifeline and not the exec spec
		request.setLocation(sourceLocation);

		EditPart targetEditPart = lifeline1.getTargetEditPart(request);
		request.setTargetEditPart(targetEditPart);

		editor.execute(targetEditPart.getCommand(request));

		// Check that the duration link follows
		/**
		 * FIXME: newStartX should be 140, but the Message Reconnect is incorrect, and the DurationLink simply follows
		 * The Message Start Anchor is moved to the left side of the ExecSpec, instead of the right side.
		 * When the MessageReconnect bug is fixed, this should be changed back to 140
		 */
		int newStartX = 120;
		checkPosition(links[1], newStartX, 140, 400, 180);
	}

	@Test
	public void testAnchorUpdateOnExecSpec() {
		// Move an ExecutionSpecification
		ChangeBoundsRequest request = new ChangeBoundsRequest(RequestConstants.REQ_MOVE);
		request.setEditParts(exec1);
		request.setMoveDelta(new Point(0, 40));

		Point center = exec1.getFigure().getBounds().getCenter().getCopy();
		center.setY(center.y() + 40);
		request.setLocation(center); // Probably doesn't matter

		// By default, messages located below an ExecSpec will move with it. We don't want that here.
		disableMoveMessages(request, exec1);

		EditPart targetPart = exec1.getTargetEditPart(request);
		editor.execute(targetPart.getCommand(request));

		// Check that the duration links (1 and 7) follow
		checkPosition(links[0], 130, 140, 410, 160);
		checkPosition(links[6], 130, 440, 410, 580);
	}

	private void disableMoveMessages(ChangeBoundsRequest request, IGraphicalEditPart editPart) {
		// Unfortunately, there is no request constant to specify this; this is done via a Keyboard
		// listener installed on a specific EditPolicy, so we have to fake the Shift key-press
		EditPolicy editPolicy = editPart.getEditPolicy(UpdateWeakReferenceForExecSpecEditPolicy.UDPATE_WEAK_REFERENCE_FOR_EXECSPEC);
		((UpdateWeakReferenceForExecSpecEditPolicy) editPolicy).setKeyPressState(true);
	}

	@Test
	public void testAnchorUpdateOnDestruction() {
		// Move a DestructionOccurrence. Note: it's currently not possible to move the Destruction directly,
		// so we move the lifeline horizontally instead.
		ChangeBoundsRequest request = new ChangeBoundsRequest(RequestConstants.REQ_MOVE);
		request.setEditParts(lifeline3); // Lifeline3 owns a DestructionOccurrenceSpecification
		request.setMoveDelta(new Point(120, 0));

		Point center = lifeline3.getFigure().getBounds().getCenter().getCopy();
		center.setX(center.x() + 120);
		request.setLocation(center); // Probably doesn't matter

		EditPart targetPart = lifeline3.getTargetEditPart(request);
		editor.execute(targetPart.getCommand(request));

		// Check that the duration link follows
		checkPosition(links[5], 410, 400, 810, 500);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testLinkDisappearOnSemanticChange() {
		T link = (T) EMFHelper.getEObject(links[0]);

		// First change doesn't change source/target; the link should remain
		doUnrelatedChange(link);
		Assert.assertTrue("The edit part for " + linkNames[0] + " should still be active", links[0].isActive());

		// Second change modifies the target; the link should be deleted
		doChangeTarget(link);
		checkDeleted(links[0]);
	}

	@Test
	public void testLinkDisappearOnAnchorageChange() {
		editor.delete(message1);
		checkDeleted(links[1]);

		editor.delete(exec3);
		checkDeleted(links[2]);
		checkDeleted(links[3]);

		editor.delete(destruction);
		checkDeleted(links[5]);
		checkDeleted(links[4]); // This is indirect: the deletion of the Destruction removes the Delete Message as well, on which link5 is anchored

		// Other durations are still present & active
		Assert.assertTrue(linkNames[0] + " should still be active", links[0].isActive());
		Assert.assertTrue(linkNames[6] + " should still be active", links[6].isActive());
	}

	// Link deletion shouldn't deleted linked events
	@Test
	public void testLinkDeletion() {
		Message umlMessage1 = (Message) EMFHelper.getEObject(message1);
		Assert.assertNotNull(umlMessage1.eResource());

		for (ConnectionNodeEditPart linkPart : links) {
			@SuppressWarnings("unchecked")
			T link = (T) EMFHelper.getEObject(linkPart);
			Element source = getSourceElement(link);
			Element target = getTargetElement(link);

			Assert.assertNotNull(source);
			Assert.assertNotNull(target);

			editor.delete(linkPart);

			// Check that source/target events are still present in the UML Model
			Assert.assertEquals(umlMessage1.eResource(), source.eResource());
			Assert.assertEquals(umlMessage1.eResource(), target.eResource());
		}

		// Check that the Message/Exec/Destruction edit parts are unaffected
		Assert.assertTrue(message1.isActive());
		Assert.assertTrue(message4.isActive());
		Assert.assertTrue(exec1.isActive());
		Assert.assertTrue(exec3.isActive());
		Assert.assertTrue(destruction.isActive());
	}

	private static void checkDeleted(EditPart editPart) {
		Assert.assertFalse("The edit part should be destroyed", editPart.isActive());
		View view = (View) editPart.getModel();
		Assert.assertNull("The edit part's view should no longer be contained in the model", view.eContainer());
	}

	private static void checkPosition(ConnectionNodeEditPart connection, int startX, int startY, int endX, int endY) {
		PolylineConnection polyline = (PolylineConnection) connection.getConnectionFigure();
		int[] expected = new int[] { startX, startY, endX, endY };

		Point start = absolute(polyline, polyline.getStart());
		Point end = absolute(polyline, polyline.getEnd());
		int[] actual = new int[] { start.x(), start.y(), end.x(), end.y() };

		Assert.assertThat(actual, IsEqual.equalTo(expected));
	}

	private static Point absolute(IFigure reference, Point pointInReference) {
		Point result = pointInReference.getCopy();
		reference.translateToAbsolute(result);
		return result;
	}

	private static void checkFixedAnchors(ConnectionNodeEditPart connection, Class<? extends ConnectionAnchor> sourceAnchor, Class<? extends ConnectionAnchor> targetAnchor) {
		String connectionName = ((NamedElement) EMFHelper.getEObject(connection)).getName();

		Assert.assertThat("Invalid source anchor for " + connectionName, connection.getConnectionFigure().getSourceAnchor(), IsInstanceOf.instanceOf(sourceAnchor));
		Assert.assertThat("Invalid target anchor for " + connectionName, connection.getConnectionFigure().getTargetAnchor(), IsInstanceOf.instanceOf(targetAnchor));
	}

	/**
	 * Execute a semantic change on the given link that doesn't affect
	 * its source or target
	 *
	 * @param linkToChange
	 */
	protected abstract void doUnrelatedChange(T linkToChange);

	/**
	 * Execute a semantic change on the given link that modifies its target
	 *
	 * @param linkToChange
	 */
	protected abstract void doChangeTarget(T linkToChange);

	protected abstract Element getSourceElement(T link);

	protected abstract Element getTargetElement(T link);
}
