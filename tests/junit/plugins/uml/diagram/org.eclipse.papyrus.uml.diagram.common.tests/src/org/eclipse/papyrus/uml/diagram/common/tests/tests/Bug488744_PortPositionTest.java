/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.tests.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.NodeEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IPapyrusNodeFigure;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.DiagramUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test the port position (inside/outside/online) for port in composite diagram and in component diagram.
 */
@PluginResource("/resources/488744/model.di")
public class Bug488744_PortPositionTest extends AbstractPapyrusTest {

	/** The model set fixture. */
	@Rule
	public final PapyrusEditorFixture modelSetFixture = new PapyrusEditorFixture();

	/** The component diagram. */
	private Diagram ComponentDiagram = null;

	/** The composite diagram. */
	private Diagram CompositeDiagram = null;

	/**
	 * Initialize tests.
	 */
	@Before
	public void init() {

		ComponentDiagram = DiagramUtils.getNotationDiagram((ModelSet) modelSetFixture.getResourceSet(), "Component Diagram"); //$NON-NLS-1$
		assertNotNull(ComponentDiagram);
		CompositeDiagram = DiagramUtils.getNotationDiagram((ModelSet) modelSetFixture.getResourceSet(), "Composite Diagram"); //$NON-NLS-1$
		assertNotNull(CompositeDiagram);
	}

	/**
	 * Test the port position on 3 different parent.
	 */
	@Test
	public void testPortPosition() {

		IGraphicalEditPart parent = getEditPart(ComponentDiagram, "Component"); //$NON-NLS-1$
		assertNotNull(parent);
		Rectangle parentBounds = parent.getFigure().getBounds();
		Rectangle bounds = null;

		// Test for the component diagram
		IGraphicalEditPart inlinePort = getEditPart(ComponentDiagram, "onlinePort"); //$NON-NLS-1$
		assertNotNull(inlinePort);
		bounds = inlinePort.getFigure().getBounds();
		assertTrue("port is not online", bounds.x == parentBounds.x - bounds.width / 2); //$NON-NLS-1$

		IGraphicalEditPart outsidePort = getEditPart(ComponentDiagram, "outsidePort"); //$NON-NLS-1$
		assertNotNull(outsidePort);
		bounds = outsidePort.getFigure().getBounds();
		assertTrue("port is not outside", bounds.x == parentBounds.x - bounds.width + 1); //$NON-NLS-1$

		IGraphicalEditPart insidePort = getEditPart(ComponentDiagram, "insidePort"); //$NON-NLS-1$
		assertNotNull(insidePort);
		bounds = insidePort.getFigure().getBounds();
		assertTrue("port is not inside", bounds.x == parentBounds.x); //$NON-NLS-1$


		// Test for the composite diagram
		// Port on top node
		parent = getEditPart(CompositeDiagram, "Class"); //$NON-NLS-1$
		assertNotNull(parent);
		parentBounds = parent.getFigure().getBounds();

		IGraphicalEditPart inlinePort1 = getEditPart(CompositeDiagram, "onlinePort1"); //$NON-NLS-1$
		assertNotNull(inlinePort1);
		bounds = inlinePort1.getFigure().getBounds();
		assertTrue("port is not online", bounds.x == parentBounds.x - bounds.width / 2); //$NON-NLS-1$

		IGraphicalEditPart outsidePort1 = getEditPart(CompositeDiagram, "outsidePort1"); //$NON-NLS-1$
		assertNotNull(outsidePort1);
		bounds = outsidePort1.getFigure().getBounds();
		assertTrue("port is not outside", bounds.x == parentBounds.x - bounds.width + 1); //$NON-NLS-1$

		IGraphicalEditPart insidePort1 = getEditPart(CompositeDiagram, "insidePort1"); //$NON-NLS-1$
		assertNotNull(insidePort1);
		bounds = insidePort1.getFigure().getBounds();
		assertTrue("port is not inside", bounds.x == parentBounds.x); //$NON-NLS-1$

		// port on child node
		parent = getEditPart(CompositeDiagram, "Attribute"); //$NON-NLS-1$
		assertNotNull(parent);
		parentBounds = parent.getFigure().getBounds();

		IGraphicalEditPart inlinePort2 = getEditPart(CompositeDiagram, "onlinePort2"); //$NON-NLS-1$
		assertNotNull(inlinePort2);
		bounds = inlinePort2.getFigure().getBounds();
		assertTrue("port is not online", bounds.x == parentBounds.x - bounds.width / 2); //$NON-NLS-1$

		IGraphicalEditPart outsidePort2 = getEditPart(CompositeDiagram, "outsidePort2"); //$NON-NLS-1$
		assertNotNull(outsidePort2);
		bounds = outsidePort2.getFigure().getBounds();
		assertTrue("port is not outside", bounds.x == parentBounds.x - bounds.width + 1); //$NON-NLS-1$

		IGraphicalEditPart insidePort2 = getEditPart(CompositeDiagram, "insidePort2"); //$NON-NLS-1$
		assertNotNull(insidePort2);
		bounds = insidePort2.getFigure().getBounds();
		assertTrue("port is not inside", bounds.x == parentBounds.x); //$NON-NLS-1$

	}

	/**
	 * Test the port position on 3 different parent for resized port.
	 */
	@Test
	public void testResizedPortPosition() {

		Rectangle bounds = null;

		// Test for the composite diagram
		// Port on top node
		IGraphicalEditPart parent = getEditPart(CompositeDiagram, "ClassForResizedPort"); //$NON-NLS-1$
		assertNotNull(parent);
		Rectangle parentBounds = parent.getFigure().getBounds();

		IGraphicalEditPart inlinePort1 = getEditPart(CompositeDiagram, "onlinePort1Resized"); //$NON-NLS-1$
		assertNotNull(inlinePort1);
		bounds = inlinePort1.getFigure().getBounds();
		assertTrue("port is not online", bounds.x == parentBounds.x - bounds.width / 2); //$NON-NLS-1$

		IGraphicalEditPart outsidePort1 = getEditPart(CompositeDiagram, "outsidePort1Resized"); //$NON-NLS-1$
		assertNotNull(outsidePort1);
		bounds = outsidePort1.getFigure().getBounds();
		assertTrue("port is not outside", bounds.x == parentBounds.x - bounds.width + 1); //$NON-NLS-1$

		IGraphicalEditPart insidePort1 = getEditPart(CompositeDiagram, "insidePort1Resized"); //$NON-NLS-1$
		assertNotNull(insidePort1);
		bounds = insidePort1.getFigure().getBounds();
		assertTrue("port is not inside", bounds.x == parentBounds.x); //$NON-NLS-1$

		// Port on top
		IGraphicalEditPart topInlinePort1 = getEditPart(CompositeDiagram, "topPortOnlineResized"); //$NON-NLS-1$
		assertNotNull(topInlinePort1);
		bounds = topInlinePort1.getFigure().getBounds();
		assertTrue("port is not online", bounds.y == parentBounds.y - bounds.height / 2); //$NON-NLS-1$

		// TOFIX ouside port is not resized: reside port have a offset of one pixel. When corrected resized the port on model (See bug 493303)
		IGraphicalEditPart topOutsidePort1 = getEditPart(CompositeDiagram, "topPortOutsideResized"); //$NON-NLS-1$
		assertNotNull(topOutsidePort1);
		bounds = topOutsidePort1.getFigure().getBounds();
		assertTrue("port is not outside", bounds.y == parentBounds.y - bounds.height + 1); //$NON-NLS-1$

		IGraphicalEditPart topInsidePort1 = getEditPart(CompositeDiagram, "topPortInsideResized"); //$NON-NLS-1$
		assertNotNull(topInsidePort1);
		bounds = topInsidePort1.getFigure().getBounds();
		assertTrue("port is not inside", bounds.y == parentBounds.y); //$NON-NLS-1$

		// port on child node
		parent = getEditPart(CompositeDiagram, "AttributeForResizedPort"); //$NON-NLS-1$
		assertNotNull(parent);
		parentBounds = parent.getFigure().getBounds();

		IGraphicalEditPart inlinePort2 = getEditPart(CompositeDiagram, "onlinePort2Resized"); //$NON-NLS-1$
		assertNotNull(inlinePort2);
		bounds = inlinePort2.getFigure().getBounds();
		assertTrue("port is not online", bounds.x == parentBounds.x - bounds.width / 2); //$NON-NLS-1$

		IGraphicalEditPart outsidePort2 = getEditPart(CompositeDiagram, "outsidePort2Resized"); //$NON-NLS-1$
		assertNotNull(outsidePort2);
		bounds = outsidePort2.getFigure().getBounds();
		assertTrue("port is not outside", bounds.x == parentBounds.x - bounds.width + 1); //$NON-NLS-1$

		IGraphicalEditPart insidePort2 = getEditPart(CompositeDiagram, "insidePort2Resized"); //$NON-NLS-1$
		assertNotNull(insidePort2);
		bounds = insidePort2.getFigure().getBounds();
		assertTrue("port is not inside", bounds.x == parentBounds.x); //$NON-NLS-1$
	}

	/**
	 * Test the port position on corner.
	 */
	@Test
	public void testPortPositionOnCorner() {

		Rectangle portBounds = null;

		IGraphicalEditPart parent = getEditPart(ComponentDiagram, "Component4CornerPosition"); //$NON-NLS-1$
		assertNotNull(parent);
		Rectangle parentBounds = parent.getFigure().getBounds();

		IGraphicalEditPart nwPort = getEditPart(ComponentDiagram, "NWPort"); //$NON-NLS-1$
		assertNotNull(nwPort);
		portBounds = nwPort.getFigure().getBounds();
		assertTrue("Port is not well placed in corner", portBounds.equals(new Rectangle(new Point(parentBounds.x - portBounds.width / 2, parentBounds.y - portBounds.height / 2), portBounds.getSize()))); //$NON-NLS-1$

		IGraphicalEditPart nePort = getEditPart(ComponentDiagram, "NEPort"); //$NON-NLS-1$
		assertNotNull(nePort);
		portBounds = nePort.getFigure().getBounds();
		assertTrue("Port is not well placed in corner", portBounds.equals(new Rectangle(new Point(parentBounds.x + parentBounds.width - portBounds.width / 2, parentBounds.y - portBounds.height / 2), portBounds.getSize()))); //$NON-NLS-1$

		IGraphicalEditPart sePort = getEditPart(ComponentDiagram, "SEPort"); //$NON-NLS-1$
		assertNotNull(nwPort);
		portBounds = sePort.getFigure().getBounds();
		assertTrue("Port is not well placed in corner", portBounds.equals(new Rectangle(new Point(parentBounds.x + parentBounds.width - portBounds.width / 2, parentBounds.y + parentBounds.height - portBounds.height / 2), portBounds.getSize()))); //$NON-NLS-1$

		IGraphicalEditPart swPort = getEditPart(ComponentDiagram, "SWPort"); //$NON-NLS-1$
		assertNotNull(swPort);
		portBounds = swPort.getFigure().getBounds();
		assertTrue("Port is not well placed in corner", portBounds.equals(new Rectangle(new Point(parentBounds.x - portBounds.width / 2, parentBounds.y + parentBounds.height - portBounds.height / 2), portBounds.getSize()))); //$NON-NLS-1$
	}

	/**
	 * Test the port position on package top.
	 */
	@Test
	public void testPortPositionOnPackageTop() {
		IGraphicalEditPart parent = getEditPart(ComponentDiagram, "PackagedComponent"); //$NON-NLS-1$
		assertTrue(parent instanceof NodeEditPart);
		IPapyrusNodeFigure figure = ((NodeEditPart) parent).getPrimaryShape();
		assertTrue(figure instanceof IRoundedRectangleFigure);
		Rectangle packageHeaderBounds = ((IRoundedRectangleFigure) figure).getPackageHeader();

		assertTrue("Package header must not be empty", !packageHeaderBounds.isEmpty()); //$NON-NLS-1$

		IGraphicalEditPart port = getEditPart(ComponentDiagram, "PortOnPackage"); //$NON-NLS-1$
		assertNotNull(port);
		Rectangle portBounds = port.getFigure().getBounds();

		assertTrue("Port figure on package is misplaced", portBounds.equals(portBounds.x, figure.getBounds().y + packageHeaderBounds.height - portBounds.height / 2, portBounds.width, portBounds.height)); //$NON-NLS-1$
	}

	/**
	 * Test the port position on oval.
	 */
	@Test
	public void testPortPositionOnOval() {
		IGraphicalEditPart parent = getEditPart(ComponentDiagram, "OvalComponent"); //$NON-NLS-1$
		assertTrue(parent instanceof NodeEditPart);
		IPapyrusNodeFigure parentFigure = ((NodeEditPart) parent).getPrimaryShape();
		assertTrue(parentFigure instanceof IRoundedRectangleFigure);
		Rectangle parentBounds = parentFigure.getBounds();

		IGraphicalEditPart port = getEditPart(ComponentDiagram, "PortOnOval"); //$NON-NLS-1$
		assertNotNull(port);
		Rectangle portBounds = port.getFigure().getBounds();

		assertTrue("Port figure on oval is misplaced", portBounds.getTopRight().x < parentBounds.getTopRight().x && portBounds.getTopRight().x < parentBounds.getTopRight().x); //$NON-NLS-1$
	}

	/**
	 * Gets the edits the part.
	 *
	 * @param diagram
	 *            The diagram where to look.
	 *
	 * @param semanticElement
	 *            The semantic element.
	 * @return the edits the part
	 */
	private IGraphicalEditPart getEditPart(Diagram diagram, String semanticElement) {

		modelSetFixture.getPageManager().openPage(diagram);

		View diagramView = DiagramUtils.findShape(diagram, semanticElement);
		if (null == diagramView) {
			diagramView = DiagramUtils.findEdge(diagram, semanticElement);
		}
		if (null == diagramView) {
			Iterator<?> iterator = diagram.eAllContents();
			while (null == diagramView && iterator.hasNext()) {
				Object object = iterator.next();
				if (object instanceof View) {
					diagramView = DiagramUtils.findShape((View) object, semanticElement);
					if (null == diagramView) {
						diagramView = DiagramUtils.findEdge((View) object, semanticElement);
					}
				}
			}
		}

		assertNotNull(diagramView);

		IGraphicalEditPart semanticEP = DiagramUtils.findEditPartforView(modelSetFixture.getEditor(), diagramView, IGraphicalEditPart.class);
		assertNotNull(semanticEP);

		return semanticEP;
	}

}
