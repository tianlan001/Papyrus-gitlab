/*****************************************************************************
 * Copyright (c) 2013, 2016 CEA LIST, Christian W. Damus, and others.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *		Patrick Tessier (CEA LIST) - Initial API and implementation
 *      Christian W. Damus (CEA) - bug 323802
 *		Christian W. Damus - bug 489457
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.composite.custom.edit.policies;

import java.util.Iterator;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editpolicies.GraphicalEditPolicy;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.diagram.core.listener.DiagramEventBroker;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationListener;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.listenerservice.IPapyrusListener;
import org.eclipse.papyrus.infra.emf.gmf.util.GMFUnsafe;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.papyrus.uml.diagram.composite.custom.edit.command.CreateBehaviorPortCommand;
import org.eclipse.papyrus.uml.diagram.composite.custom.figures.PortFigure;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.BehaviorPortLinkEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.PortEditPart;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * This editpolicy is a listener that listen the feature is_behavior of the port. It launches a synchronous thread to create a notation node and the link.
 * This editpolicy calls explicitly BehaviorPortLocator in order to place the symbol behavior at the good place (inside the composite).
 */
public class BehaviorPortEditPolicy extends GraphicalEditPolicy implements NotificationListener, IPapyrusListener {
	public static final String BEHAVIOR_PORT = "BehaviorPortPolicy"; //$NON-NLS-1$
	private Port port;

	@Override
	public void notifyChanged(Notification notification) {

		// Don't react to notifications for unvisualized ports
		if (UMLPackage.eINSTANCE.getPort_IsBehavior().equals(notification.getFeature())
				&& getHost().isActive() && isActive()) {
			udaptePortBehavior();
		}
	}

	public void udaptePortBehavior() {
		if (!isActive()) {
			return;
		}
		GraphicalEditPart parentEditPart = (GraphicalEditPart) ((GraphicalEditPart) getHost()).getParent();
		ShapeCompartmentEditPart targetEditPart = (parentEditPart == null) ? null : getPossibleCompartment(parentEditPart);
		if (targetEditPart != null) {
			// remove old BehaviorPort presentation
			View behaviorPort = getBehaviorPortNode();
			executeBehaviorPortDeletion(((GraphicalEditPart) getHost()).getEditingDomain(), behaviorPort);
		}

		if (getHost() instanceof PortEditPart) {
			IFigure hostFigure = ((PortEditPart) getHost()).getContentPane();
			if (hostFigure instanceof PortFigure) {
				PortFigure portFigure = (PortFigure) hostFigure;
				if (port.isBehavior()) {
					if (parentEditPart.resolveSemanticElement() instanceof Classifier || targetEditPart != null) {
						portFigure.restoreBehaviorFigure();
					} else {
						portFigure.removeBehavior();
					}
				} else {
					portFigure.removeBehavior();
				}
			}
		}
	}

	/**
	 * Checks if current edit policy is active, e.g. it has been already activated.
	 * 
	 * @return <code>true</code> if the edit policy has been activated. It will be <code>false</code> when ti has been deactivated also.
	 */
	protected boolean isActive() {
		return port != null;
	}

	protected void executeBehaviorPortDeletion(final TransactionalEditingDomain domain, final View behaviorNode) {
		if ((behaviorNode != null) && (TransactionUtil.getEditingDomain(behaviorNode) == domain)) {
			DeleteCommand command = new DeleteCommand(behaviorNode);
			// use to avoid to put it in the command stack
			try {
				GMFUnsafe.write(domain, command);
			} catch (Exception e) {
				Activator.log.error(e);
			}
		}
	}

	/**
	 * return the comment nodes that represent stereotype properties
	 *
	 * @return may be null if nothing is founded
	 */
	protected Node getBehaviorPortNode() {
		View SemanticView = (View) getHost().getModel();

		Edge behaviorPortLink = null;
		@SuppressWarnings("unchecked")
		Iterator<Edge> edgeIterator = SemanticView.getSourceEdges().iterator();
		while (edgeIterator.hasNext()) {
			Edge edge = edgeIterator.next();
			if (BehaviorPortLinkEditPart.VISUAL_ID.equals(edge.getType())) {
				behaviorPortLink = edge;
			}

		}
		if (behaviorPortLink == null) {
			return null;
		}
		return (Node) behaviorPortLink.getTarget();

	}

	protected ShapeCompartmentEditPart getPossibleCompartment(EditPart parent) {
		ShapeCompartmentEditPart found = null;
		int i = 0;
		while (found == null && i < parent.getChildren().size()) {
			if (parent.getChildren().get(i) instanceof ShapeCompartmentEditPart) {
				found = (ShapeCompartmentEditPart) parent.getChildren().get(i);
			}
			i++;
		}
		return found;
	}

	protected void executeBehaviorPortCreation(final EditPart editPart, final EditPart port, final TransactionalEditingDomain domain, final Rectangle position) {
		CreateBehaviorPortCommand command = new CreateBehaviorPortCommand(domain, (View) editPart.getModel(), (View) port.getModel(), position);
		// use to avoid to put it in the command stack
		try {
			GMFUnsafe.write(domain, command);
		} catch (Exception e) {
			Activator.log.error(e);
		}
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public void activate() {
		super.activate();
		// retrieve the view and the element managed by the edit part
		View view = getView();
		if (view == null) {
			return;
		}
		// adds a listener on the view and the element controlled by the
		// editpart
		getDiagramEventBroker().addNotificationListener(view, this);
		port = getUMLElement();
		if (port != null) {
			getDiagramEventBroker().addNotificationListener(port, this);
		}
		udaptePortBehavior();
	}

	@Override
	public void deactivate() {
		// remove notification on element
		getDiagramEventBroker().removeNotificationListener(port, this);
		getDiagramEventBroker().removeNotificationListener(getView(), this);
		// release link to the semantic element
		port = null;
		super.deactivate();
	}

	/**
	 * Gets the diagram event broker from the editing domain.
	 *
	 * @return the diagram event broker
	 */
	protected DiagramEventBroker getDiagramEventBroker() {
		TransactionalEditingDomain theEditingDomain = ((IGraphicalEditPart) getHost()).getEditingDomain();
		if (theEditingDomain != null) {
			return DiagramEventBroker.getInstance(theEditingDomain);
		}
		return null;
	}

	/**
	 * Returns the uml element controlled by the host edit part
	 *
	 * @return the uml element controlled by the host edit part
	 */
	protected Port getUMLElement() {
		EObject element = getView().getElement();
		if (element instanceof Port) {
			return (Port) element;
		}
		return null;
	}

	/**
	 * Returns the view controlled by the host edit part
	 *
	 * @return the view controlled by the host edit part
	 */
	protected View getView() {
		return (View) getHost().getModel();
	}
}
