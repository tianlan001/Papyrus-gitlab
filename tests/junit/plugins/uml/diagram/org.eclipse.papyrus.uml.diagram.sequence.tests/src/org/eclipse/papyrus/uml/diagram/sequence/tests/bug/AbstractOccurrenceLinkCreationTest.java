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

import java.util.Random;
import java.util.Set;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.uml.diagram.sequence.anchors.CenterAnchor;
import org.eclipse.papyrus.uml.diagram.sequence.anchors.ConnectionSourceAnchor;
import org.eclipse.papyrus.uml.diagram.sequence.anchors.ConnectionTargetAnchor;
import org.eclipse.papyrus.uml.diagram.sequence.anchors.NodeBottomAnchor;
import org.eclipse.papyrus.uml.diagram.sequence.anchors.NodeTopAnchor;
import org.eclipse.uml2.uml.ActionExecutionSpecification;
import org.eclipse.uml2.uml.BehaviorExecutionSpecification;
import org.eclipse.uml2.uml.DestructionOccurrenceSpecification;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.NamedElement;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public abstract class AbstractOccurrenceLinkCreationTest<T extends NamedElement> extends AbstractPapyrusTest {

	@Rule
	public final PapyrusEditorFixture editor = new PapyrusEditorFixture();

	IGraphicalEditPart exec1, exec2, exec3, destruction1, destruction2;
	ConnectionNodeEditPart message3, message4;
	ConnectionNodeEditPart[] links;

	private Class<T> umlClass;

	private String paletteToolId;

	private Class<? extends Connection> figureClass;

	public AbstractOccurrenceLinkCreationTest(Class<T> umlClass, String paletteToolId, Class<? extends Connection> figureClass) {
		this.umlClass = umlClass;
		this.paletteToolId = paletteToolId;
		this.figureClass = figureClass;
	}

	@Before
	public void getElements() {
		exec1 = (IGraphicalEditPart) editor.findEditPart("ActionExecutionSpecification1", ActionExecutionSpecification.class);
		exec2 = (IGraphicalEditPart) editor.findEditPart("BehaviorExecutionSpecification2", BehaviorExecutionSpecification.class);
		exec3 = (IGraphicalEditPart) editor.findEditPart("ActionExecutionSpecification3", ActionExecutionSpecification.class);
		destruction1 = (IGraphicalEditPart) editor.findEditPart("Message1ReceiveDestroyEvent", DestructionOccurrenceSpecification.class);
		destruction2 = (IGraphicalEditPart) editor.findEditPart("Message2ReceiveDestroyEvent", DestructionOccurrenceSpecification.class);

		message3 = (ConnectionNodeEditPart) editor.findEditPart("Message3", Message.class);
		message4 = (ConnectionNodeEditPart) editor.findEditPart("Message4", Message.class);
	}

	@Test
	public void testLinkCreationOnMessageSourceToTarget() throws Exception {
		editor.getActiveDiagramEditor().getDiagramGraphicalViewer().reveal(message4);

		Point message3Source = findSourcePoint(message3);
		Point message4Target = findTargetPoint(message4);

		editor.createLink(paletteToolId, message3, message4, message3Source, message4Target);

		Message umlMessage3 = (Message) EMFHelper.getEObject(message3);
		Message umlMessage4 = (Message) EMFHelper.getEObject(message4);

		// There's no T in the model before we create the link, so just find the only one
		Set<T> allInstances = EMFHelper.allInstances(umlMessage3.eResource(), umlClass);
		T newLink = allInstances.iterator().next();

		EditPart newLinkEditPart = editor.findEditPart(newLink);
		Assert.assertThat(newLinkEditPart, IsInstanceOf.instanceOf(ConnectionNodeEditPart.class));
		ConnectionNodeEditPart newConnection = (ConnectionNodeEditPart) newLinkEditPart;

		Element source = getSemanticSource(newLink);
		Element target = getSemanticTarget(newLink);

		Assert.assertEquals(umlMessage3.getSendEvent(), source);
		Assert.assertEquals(umlMessage4.getReceiveEvent(), target);
		Assert.assertThat(newConnection.getConnectionFigure(), IsInstanceOf.instanceOf(figureClass));
		Assert.assertThat(newConnection.getConnectionFigure().getSourceAnchor(), IsInstanceOf.instanceOf(ConnectionSourceAnchor.class));
		Assert.assertThat(newConnection.getConnectionFigure().getTargetAnchor(), IsInstanceOf.instanceOf(ConnectionTargetAnchor.class));
	}

	@Test
	public void testLinkCreationOnMessageTargetToSource() throws Exception {
		editor.getActiveDiagramEditor().getDiagramGraphicalViewer().reveal(message4);

		Point message3Target = findTargetPoint(message3);
		Point message4Source = findSourcePoint(message4);

		editor.createLink(paletteToolId, message3, message4, message3Target, message4Source);

		Message umlMessage3 = (Message) EMFHelper.getEObject(message3);
		Message umlMessage4 = (Message) EMFHelper.getEObject(message4);

		// There's no T in the model before we create the link, so just find the only one
		Set<T> allInstances = EMFHelper.allInstances(umlMessage3.eResource(), umlClass);
		T newLink = allInstances.iterator().next();

		EditPart newLinkEditPart = editor.findEditPart(newLink);
		Assert.assertThat(newLinkEditPart, IsInstanceOf.instanceOf(ConnectionNodeEditPart.class));
		ConnectionNodeEditPart newConnection = (ConnectionNodeEditPart) newLinkEditPart;

		Element source = getSemanticSource(newLink);
		Element target = getSemanticTarget(newLink);

		Assert.assertEquals(umlMessage3.getReceiveEvent(), source);
		Assert.assertEquals(umlMessage4.getSendEvent(), target);
		Assert.assertThat(newConnection.getConnectionFigure(), IsInstanceOf.instanceOf(figureClass));
		Assert.assertThat(newConnection.getConnectionFigure().getSourceAnchor(), IsInstanceOf.instanceOf(ConnectionTargetAnchor.class));
		Assert.assertThat(newConnection.getConnectionFigure().getTargetAnchor(), IsInstanceOf.instanceOf(ConnectionSourceAnchor.class));
	}

	@Test
	public void testLinkCreationOnExecSpecStartToFinish() throws Exception {
		editor.getActiveDiagramEditor().getDiagramGraphicalViewer().reveal(exec2);

		Point exec1Start = findStartPoint(exec1);
		Point exec2Finish = findFinishPoint(exec2);

		editor.createLink(paletteToolId, exec1, exec2, exec1Start, exec2Finish);

		ActionExecutionSpecification umlExec1 = (ActionExecutionSpecification) EMFHelper.getEObject(exec1);
		BehaviorExecutionSpecification umlExec2 = (BehaviorExecutionSpecification) EMFHelper.getEObject(exec2);

		// There's no T in the model before we create the link, so just find the only one
		Set<T> allInstances = EMFHelper.allInstances(umlExec1.eResource(), umlClass);
		T newLink = allInstances.iterator().next();

		EditPart newLinkEditPart = editor.findEditPart(newLink);
		Assert.assertThat(newLinkEditPart, IsInstanceOf.instanceOf(ConnectionNodeEditPart.class));
		ConnectionNodeEditPart newConnection = (ConnectionNodeEditPart) newLinkEditPart;

		Element source = getSemanticSource(newLink);
		Element target = getSemanticTarget(newLink);

		Assert.assertEquals(umlExec1.getStart(), source);
		Assert.assertEquals(umlExec2.getFinish(), target);
		Assert.assertThat(newConnection.getConnectionFigure(), IsInstanceOf.instanceOf(figureClass));
		Assert.assertThat(newConnection.getConnectionFigure().getSourceAnchor(), IsInstanceOf.instanceOf(NodeTopAnchor.class));
		Assert.assertThat(newConnection.getConnectionFigure().getTargetAnchor(), IsInstanceOf.instanceOf(NodeBottomAnchor.class));
	}

	@Test
	public void testLinkCreationOnExecSpecFinishToStart() throws Exception {
		editor.getActiveDiagramEditor().getDiagramGraphicalViewer().reveal(exec3);

		Point exec1Finish = findFinishPoint(exec1);
		Point exec3Start = findStartPoint(exec3);

		editor.createLink(paletteToolId, exec1, exec3, exec1Finish, exec3Start);

		ActionExecutionSpecification umlExec1 = (ActionExecutionSpecification) EMFHelper.getEObject(exec1);
		ActionExecutionSpecification umlExec3 = (ActionExecutionSpecification) EMFHelper.getEObject(exec3);

		// There's no T in the model before we create the link, so just find the only one
		Set<T> allInstances = EMFHelper.allInstances(umlExec1.eResource(), umlClass);
		T newLink = allInstances.iterator().next();

		EditPart newLinkEditPart = editor.findEditPart(newLink);
		Assert.assertThat(newLinkEditPart, IsInstanceOf.instanceOf(ConnectionNodeEditPart.class));
		ConnectionNodeEditPart newConnection = (ConnectionNodeEditPart) newLinkEditPart;

		Element source = getSemanticSource(newLink);
		Element target = getSemanticTarget(newLink);

		Assert.assertEquals(umlExec1.getFinish(), source);
		Assert.assertEquals(umlExec3.getStart(), target);
		Assert.assertThat(newConnection.getConnectionFigure(), IsInstanceOf.instanceOf(figureClass));
		Assert.assertThat(newConnection.getConnectionFigure().getSourceAnchor(), IsInstanceOf.instanceOf(NodeBottomAnchor.class));
		Assert.assertThat(newConnection.getConnectionFigure().getTargetAnchor(), IsInstanceOf.instanceOf(NodeTopAnchor.class));
	}

	@Test
	public void testLinkCreationOnDestruction() throws Exception {
		editor.getActiveDiagramEditor().getDiagramGraphicalViewer().reveal(destruction2);

		Point destruction1Any = findAnyPoint(destruction1);
		Point destruction2Any = findAnyPoint(destruction2);

		editor.createLink(paletteToolId, destruction1, destruction2, destruction1Any, destruction2Any);

		DestructionOccurrenceSpecification umlDest1 = (DestructionOccurrenceSpecification) EMFHelper.getEObject(destruction1);
		DestructionOccurrenceSpecification umlDest2 = (DestructionOccurrenceSpecification) EMFHelper.getEObject(destruction2);

		// There's no T in the model before we create the link, so just find the only one
		Set<T> allInstances = EMFHelper.allInstances(umlDest1.eResource(), umlClass);
		T newLink = allInstances.iterator().next();

		EditPart newLinkEditPart = editor.findEditPart(newLink);
		Assert.assertThat(newLinkEditPart, IsInstanceOf.instanceOf(ConnectionNodeEditPart.class));
		ConnectionNodeEditPart newConnection = (ConnectionNodeEditPart) newLinkEditPart;

		Element source = getSemanticSource(newLink);
		Element target = getSemanticTarget(newLink);

		Assert.assertEquals(umlDest1, source);
		Assert.assertEquals(umlDest2, target);
		Assert.assertThat(newConnection.getConnectionFigure(), IsInstanceOf.instanceOf(figureClass));
		Assert.assertThat(newConnection.getConnectionFigure().getSourceAnchor(), IsInstanceOf.instanceOf(CenterAnchor.class));
		Assert.assertThat(newConnection.getConnectionFigure().getTargetAnchor(), IsInstanceOf.instanceOf(CenterAnchor.class));
	}

	protected abstract Element getSemanticSource(T link);

	protected abstract Element getSemanticTarget(T link);

	private Point findSourcePoint(ConnectionNodeEditPart editPart) {
		PolylineConnection connection = (PolylineConnection) editPart.getConnectionFigure();
		Point start = connection.getStart().getCopy();
		Point end = connection.getEnd().getCopy();

		connection.translateToAbsolute(start);
		connection.translateToAbsolute(end);

		// Messages are straight lines, so just shift the X/Y slightly towards the source side
		int newX = (int) (0.75 * start.x() + 0.25 * end.x());
		int newY = (int) (0.75 * start.y() + 0.25 * end.y());

		return new Point(newX, newY);
	}

	private Point findTargetPoint(ConnectionNodeEditPart editPart) {
		PolylineConnection connection = (PolylineConnection) editPart.getConnectionFigure();
		Point end = connection.getEnd().getCopy();
		Point start = connection.getStart().getCopy();

		connection.translateToAbsolute(start);
		connection.translateToAbsolute(end);

		// Messages are straight lines, so just shift the X/Y slightly towards the target side
		int newX = (int) (0.75 * end.x() + 0.25 * start.x());
		int newY = (int) (0.75 * end.y() + 0.25 * start.y());

		return new Point(newX, newY);
	}

	private Point findStartPoint(IGraphicalEditPart editPart) {
		IFigure figure = editPart.getFigure();
		Rectangle bounds = figure.getBounds();

		Point top = bounds.getTop().getCopy();
		Point bottom = bounds.getBottom().getCopy();
		figure.translateToAbsolute(top);
		figure.translateToAbsolute(bottom);

		int newX = top.x() + 7; // Slightly shift to the right (arbitrary small value)
		int newY = (int) (0.75 * top.y() + 0.25 * bottom.y()); // Shift to the bottom (We just need to be on the upper half of the ExecSpec to target the start event)

		return new Point(newX, newY);
	}

	private Point findFinishPoint(IGraphicalEditPart editPart) {
		IFigure figure = editPart.getFigure();
		Rectangle bounds = figure.getBounds();

		Point top = bounds.getTop().getCopy();
		Point bottom = bounds.getBottom().getCopy();
		figure.translateToAbsolute(top);
		figure.translateToAbsolute(bottom);

		int newX = bottom.x() - 4; // Slightly shift to the left (arbitrary value between -10 and 10)
		int newY = (int) (0.25 * top.y() + 0.75 * bottom.y()); // Shift to the top (We just need to be on the lower half of the ExecSpec to target the finish event)

		return new Point(newX, newY);
	}

	private Point findAnyPoint(IGraphicalEditPart editPart) {
		IFigure figure = editPart.getFigure();
		Rectangle bounds = figure.getBounds();

		Point topLeft = bounds.getTopLeft().getCopy();
		Point bottomRight = bounds.getBottomRight().getCopy();

		Random r = new Random();
		int x = topLeft.x() + r.nextInt(bottomRight.x() - topLeft.x());
		int y = topLeft.y() + r.nextInt(bottomRight.y() - topLeft.y());

		return new Point(x, y);
	}



}

