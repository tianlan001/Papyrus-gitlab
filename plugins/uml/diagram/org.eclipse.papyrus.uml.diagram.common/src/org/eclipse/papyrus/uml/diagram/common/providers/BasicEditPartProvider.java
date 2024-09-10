/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
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
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.providers;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.AbstractEditPartProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.CreateGraphicEditPartOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.IEditPartOperation;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.Activator;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IPapyrusNodeFigure;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.RoundedRectangleNodePlateFigure;
import org.eclipse.papyrus.uml.diagram.common.editparts.NamedElementEditPart;
import org.eclipse.papyrus.uml.diagram.common.editparts.UMLConnectionNodeEditPart;
import org.eclipse.papyrus.uml.diagram.common.figure.edge.DashedEdgeFigure;
import org.eclipse.papyrus.uml.diagram.common.figure.node.NodeNamedElementFigure;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.NamedElement;

/**
 * 
 * This class is a generic EditpartProvider that is enable to associate controler to new notation element by reading an expansion model
 * See Requirement #Req org.eclipse.papyrus.infra.gmfdiag.expansion.Req_010
 * This edipart provider is not used because the all diagram have the priority lowest.
 * In order to use it. The priority of all diagrams editpart provider must low and this one lowest.
 *
 */
public class BasicEditPartProvider extends AbstractEditPartProvider {

	/**
	 * 
	 */
	private static final String DEBUG_PREFIX = "[EXPANSION_DIAGRAM]";
	private static final boolean DEBUG_EXPANSION = "true".equalsIgnoreCase(Platform.getDebugOption(
			"org.eclipse.papyrus.infra.gmfdiag.common/debug/expansion"));
	/** Map containing node view types supported by this provider */
	protected Map<String, Class<?>> nodeMap = new HashMap<String, Class<?>>();

	/** Map containing edge view types supported by this provider */
	protected Map<String, Class<?>> edgeMap = new HashMap<String, Class<?>>();


	/** Default constructor */
	public BasicEditPartProvider() {
		super();

	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.services.editpart.AbstractEditPartProvider#provides(org.eclipse.gmf.runtime.common.core.service.IOperation)
	 *
	 * @param operation
	 * @return
	 */
	@Override
	public boolean provides(IOperation operation) {
		if (operation instanceof CreateGraphicEditPartOperation) {

			if (operation instanceof CreateGraphicEditPartOperation) {
				View newView = ((IEditPartOperation) operation).getView();
				if (newView == null) {
					return false;
				}
				// detect if the editor is inside Papyrus, it provides always
				try {
					if (ServiceUtilsForEObject.getInstance().getServiceRegistry(newView) == null) {
						return false;
					}
				} catch (ServiceException e) {
					Activator.log.error(e);
					;
					return false;
				}
				String graphicalType = newView.getType();
				if (DEBUG_EXPANSION) {
					Activator.log.debug(DEBUG_PREFIX + this.getClass().getName() + " view appears with the type " + graphicalType);
				}
				return true;

			}
		}
		return super.provides(operation);
	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.services.editpart.AbstractEditPartProvider#createGraphicEditPart(org.eclipse.gmf.runtime.notation.View)
	 *
	 * @param view
	 * @return
	 */
	@Override
	public IGraphicalEditPart createGraphicEditPart(View view) {
		String graphicalType = view.getType();
		if (DEBUG_EXPANSION) {
			Activator.log.debug(DEBUG_PREFIX + this.getClass().getName() + " view appears with the type " + graphicalType);
		}
		IGraphicalEditPart graphicEditPart = null;
		if (view.eContainer() instanceof Diagram) {
			if (view instanceof Node && (view.getElement() instanceof NamedElement)) {
				graphicEditPart = new NamedElementEditPart(view) {
					protected NodeNamedElementFigure primaryShape;

					protected void handleNotificationEvent(Notification event) {
						if (resolveSemanticElement() != null) {
							primaryShape.setName(UMLLabelInternationalization.getInstance().getLabel(((NamedElement) getNotationView().getElement())));
						}
						super.handleNotificationEvent(event);

					}

					@Override
					public IPapyrusNodeFigure getPrimaryShape() {
						return (NodeNamedElementFigure) primaryShape;
					}

					@Override
					protected IFigure createNodePlate() {
						RoundedRectangleNodePlateFigure result = new RoundedRectangleNodePlateFigure(100, 50);
						return result;
					}

					@Override
					protected IFigure createNodeShape() {
						return primaryShape = new NodeNamedElementFigure();
					}

					@Override
					protected IFigure setupContentPane(IFigure nodeShape) {
						if (nodeShape.getLayoutManager() == null) {
							ConstrainedToolbarLayout layout = new ConstrainedToolbarLayout();
							layout.setSpacing(5);
							nodeShape.setLayoutManager(layout);
						}
						return nodeShape;
					}

				};


			}

			else if (view instanceof Edge && (view.getElement() instanceof NamedElement)) {
				graphicEditPart = new UMLConnectionNodeEditPart(view) {

					/**
					 * Creates figure for this edit part.
					 *
					 * Body of this method does not depend on settings in generation model
					 * so you may safely remove <i>generated</i> tag and modify it.
					 *
					 */
					@Override
					protected Connection createConnectionFigure() {
						return new DashedEdgeFigure();
					}

					protected void handleNotificationEvent(Notification event) {
						if (resolveSemanticElement() != null) {

							getPrimaryShape().getAppliedStereotypeLabel().setBorder(new LineBorder(new Color(Display.getDefault(), new RGB(0, 255, 0))));
							getPrimaryShape().getNameLabel().setBorder(new LineBorder(new Color(Display.getDefault(), new RGB(255, 0, 0))));
							getPrimaryShape().getNameLabel().setText(UMLLabelInternationalization.getInstance().getLabel((NamedElement) getNotationView().getElement()));
						}
						super.handleNotificationEvent(event);

					}

					@Override
					public DashedEdgeFigure getPrimaryShape() {
						return (DashedEdgeFigure) getFigure();
					}

				};
			}
		}
		if ((!(view instanceof Diagram)) && (graphicEditPart == null)) {
			// an dummy editpart
			return new GraphicalEditPart(view) {

				protected IFigure createFigure() {
					return new Figure();
				}

				protected void addNotationalListeners() {
					// no need for Listeners
				}

				protected void addSemanticListeners() {
					// no need for Listeners
				}
			};
		}

		return graphicEditPart;

	}


}
