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
 *
 *		CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.rendering;

import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.editpolicies.GraphicalEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.gmf.util.GMFUnsafe;
import org.eclipse.papyrus.infra.gmfdiag.common.Activator;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.IPapyrusEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.service.shape.NotificationManager;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.InducedRepresentation;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;

/**
 * this edit policy can be apply only on {@link IPapyrusEditPart} in order to
 * access to primary figure. the primary figure has to be a {@link IPapyrusNodeUMLElementFigure}.
 * 
 * it creates the compartment displaying shapes for an element by reading the expansion model
 * see #Req org.eclipse.papyrus.infra.gmfdiag.expansion.Req_011
 */
public class InducedRepresentationCreationEditPolicy extends GraphicalEditPolicy {

	/** constant for this edit policy role */
	public static final String INDUCED_REPRESENTATION_CREATOR_EDITPOLICY = "InducedRepresentationCreationEditPolicy"; //$NON-NLS-1$

	/** manager for notifications: should the compartment react to the notification? */
	protected NotificationManager notificationManager;

	protected DiagramExpansionsRegistry diagramExpansionRegistry;

	/**
	 * Creates a new AppliedStereotype display edit policy
	 */
	public InducedRepresentationCreationEditPolicy() {
		super();
		this.diagramExpansionRegistry = DiagramExpansionSingleton.getInstance().getDiagramExpansionRegistry();
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
		updateAddedCompartment();

	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public void deactivate() {
		// retrieve the view and the element managed by the edit part
		View view = getView();
		if (view == null) {
			return;
		}
		super.deactivate();
	}

	/**
	 * Returns the view controlled by the host edit part
	 *
	 * @return the view controlled by the host edit part
	 */
	protected View getView() {
		return (View) getHost().getModel();
	}



	/**
	 * the goal of this method is to execute the a command to create a notation node for a compartment of stereotype
	 *
	 * @param editPart
	 *            the editpart owner of the new compartment
	 * @param appliedstereotype
	 *            the stereotype application
	 */
	protected void executeShapeCompartmentCreation(final IGraphicalEditPart editPart, HashMap<String, View> existedDynamicCompartment, List<String> wantedChildreenID, ChildrenListRepresentation listRepresentation) {
		for (String wantedID : wantedChildreenID) {
			if (existedDynamicCompartment.get(wantedID) == null) {
				if (listRepresentation.IDMap.get(wantedID) instanceof InducedRepresentation) {
					try {
						TransactionalEditingDomain domain = getEditingDomain(editPart);

						CreateInducedRepresentationViewCommand command = new CreateInducedRepresentationViewCommand(domain,
								"view Creation",
								wantedID,
								"view Creation",
								editPart.getNotationView(),
								true,
								editPart.getDiagramPreferencesHint());
						// This should not change the command stack, as this transaction will only manipulate transient views. Create a transaction manually, if needed
						GMFUnsafe.write(domain, command);
					} catch (Exception e) {
						Activator.log.error(e);
					}
				}
			}
		}


	}

	/**
	 * Returns the editing domain for the given edit Part
	 *
	 * @param editPart
	 *            the edit part from which editing domain is searched
	 * @return the editing domain
	 */
	protected TransactionalEditingDomain getEditingDomain(IGraphicalEditPart editPart) {
		return editPart.getEditingDomain();
	}


	protected String getDiagramType(View currentView) {
		Diagram diagram = currentView.getDiagram();
		String currentDiagramType = null;
		ViewPrototype viewPrototype = org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramUtils.getPrototype(diagram);
		if (viewPrototype != null) {
			currentDiagramType = viewPrototype.getLabel();
		} else {
			currentDiagramType = diagram.getType();
		}
		return currentDiagramType;
	}

	/**
	 * this method creates a node for the compartment of stereotype if it does not exist.
	 *
	 * @param stereotypeApplication
	 *            the stereotype application
	 */
	public void updateAddedCompartment() {
		final IGraphicalEditPart editPart = (IGraphicalEditPart) getHost();
		final View view = editPart.getNotationView();
		String diagramType = getDiagramType(view);
		ChildrenListRepresentation listRepresentation = diagramExpansionRegistry.mapChildreen.get(diagramType);
		if (listRepresentation == null) {
			return;
		}
		List<String> childreenID = listRepresentation.parentChildrenRelation.get(view.getType());
		if (childreenID == null) {
			return;
		}
		// Look for the node for the shape compartment
		HashMap<String, View> dynamicCompartments = getAddedCompartmentView(view, childreenID);
		// it does not exist
		if (dynamicCompartments.size() < childreenID.size()) {

			executeShapeCompartmentCreation(editPart, dynamicCompartments, childreenID, listRepresentation);
		}
	}

	/**
	 * Returns the view corresponding to the shape compartment
	 *
	 * @param view
	 * @return
	 */
	private HashMap<String, View> getAddedCompartmentView(View view, List<String> childreenID) {

		HashMap<String, View> dynamicCompartments = new HashMap<String, View>();
		for (Object child : view.getChildren()) {
			if (child instanceof View && childreenID.contains(((View) child).getType())) {
				dynamicCompartments.put((((View) child).getType()), (View) child);
			}
		}
		return dynamicCompartments;
	}

}
