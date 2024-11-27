/*****************************************************************************
 * Copyright (c) 2024 CEA LIST.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.sequence.services.utils;

import java.util.Optional;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.tools.TargetingTool;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditDomain;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.common.tools.api.util.ReflectionHelper;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.DNode;
import org.eclipse.sirius.diagram.sequence.business.api.util.Range;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.ISequenceElementAccessor;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.ISequenceEvent;
import org.eclipse.sirius.diagram.ui.business.api.view.SiriusGMFHelper;
import org.eclipse.sirius.ext.gmf.runtime.editparts.GraphicalHelper;
import org.eclipse.sirius.ui.business.api.session.IEditingSession;
import org.eclipse.sirius.ui.business.api.session.SessionUIManager;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.TimeObservation;

/**
 * Helper methods for Sirius Tools.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com>Gwendal Daniel</a>
 */
public final class ToolHelpers {

	// No creation allows for utilities class
	private ToolHelpers() {
	}

	/**
	 * Returns {@code true} if the active creation tool has been invoked on the <i>start</i> side of the provided {@code parent}.
	 * <p>
	 * The <i>start</i> side of an element is the graphical half of the element that is closer to the its starting end than its
	 * finishing end. For example, it corresponds to the upper half of an execution, or the left half of a message that goes
	 * left-to-right.
	 * </p>
	 * <p>
	 * This method is typically used to define click-dependent creation tool behavior. For example, it allows to
	 * create {@link TimeObservation}s on the appropriate end of the parent element, relative to the position of
	 * the click on it.
	 * </p>
	 * <p>
	 * Note that this method returns {@code true} by default if the position of the active tool cannot be retrieved.
	 * </p>
	 *
	 * @param parent
	 *            the element that has been clicked on
	 * @param parentView
	 *            the graphical view representing the {@code parent}
	 * @param diagram
	 *            the diagram
	 * @return {@code true} if the active creation tool has been invoked on the start side of {@code parent}, {@code false} otherwise
	 *
	 * @see #getActiveCreationToolClickLocation(DDiagram)
	 */
	@SuppressWarnings("restriction")
	public static boolean isCreationOnStartSide(Element parent, DSemanticDecorator parentView, DDiagram diagram) {
		// Default to true if we cannot compute which side of the element is the closest.
		// This method relies on internal APIs to access the range of the elements on the diagram. This should be
		// handled internally by Sirius and exposed as tool variables.
		boolean result = true;
		Optional<Point> optClickLocation = getActiveCreationToolClickLocation(diagram);
		if (optClickLocation.isPresent() && parentView instanceof DDiagramElement diagramElementParentView) {
			Point clickLocation = optClickLocation.get();

			@SuppressWarnings({ "deprecation", "removal" })
			ISequenceEvent sequenceEvent = ISequenceElementAccessor.getISequenceEvent(SiriusGMFHelper.getGmfView(diagramElementParentView)).get();
			if (diagramElementParentView instanceof DNode && parent instanceof ExecutionSpecification) {
				Range verticalRange = sequenceEvent.getVerticalRange();
				result = clickLocation.y() <= ((verticalRange.getLowerBound() + verticalRange.getUpperBound()) / 2);
			} else if (diagramElementParentView instanceof DEdge) {
				Rectangle logicalBounds = sequenceEvent.getProperLogicalBounds();
				if (logicalBounds.width() > 0) {
					result = clickLocation.x() <= (logicalBounds.x() + logicalBounds.width() / 2);
				} else {
					result = clickLocation.x() >= (logicalBounds.x() + logicalBounds.width() / 2);
				}
			}
		}
		return result;
	}

	/**
	 * Returns the click location of the active creation tool.
	 * <p>
	 * This method is typically used to define click-dependent creation tool behavior. For example, it allows to
	 * create {@link TimeObservation}s on the appropriate end of the parent element, relative to the position of
	 * the click on it.
	 * </p>
	 *
	 * @param diagram
	 *            the diagram on which the tool is invoked
	 * @return the location of the active creation tool if it exists, or {@link Optional#empty()} otherwise
	 */
	private static Optional<Point> getActiveCreationToolClickLocation(DDiagram diagram) {
		// This method relies on reflection and internal APIs to access the click location of the active tool.
		// This should be handled internally by Sirius: the location should be available in the tool's variables.
		Optional<Point> result = Optional.empty();

		IEditingSession uiSession = SessionUIManager.INSTANCE.getUISession(Session.of(diagram).get());
		if (uiSession.getEditor(diagram) instanceof DiagramEditor editor
				&& editor.getDiagramEditDomain() instanceof DiagramEditDomain editDomain
				&& editDomain.getActiveTool() instanceof TargetingTool tool) {
			if (ReflectionHelper.getFieldValueWithoutException(tool, "targetRequest").orElse(null) instanceof CreateRequest createRequest //$NON-NLS-1$
					&& ReflectionHelper.getFieldValueWithoutException(tool, "targetEditPart").orElse(null) instanceof EditPart editPart) { //$NON-NLS-1$
				// Create a copy: we don't want to change the location when computing the logical point.
				Point clickLocation = createRequest.getLocation().getCopy();
				GraphicalHelper.screen2logical(clickLocation, (IGraphicalEditPart) editPart);
				result = Optional.of(clickLocation);
			}
		}

		return result;
	}

}
