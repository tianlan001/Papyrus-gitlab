/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
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
 *
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.custom.helper;

import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICompositeCommand;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.CreateCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.appearance.helper.UMLVisualInformationPapyrusConstant;
import org.eclipse.papyrus.uml.diagram.clazz.custom.command.CustomDropAppliedStereotypeCommand;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ContainmentLinkEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ModelEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ModelEditPartTN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.NestedClassForClassEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.PackageEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.PackageEditPartCN;
import org.eclipse.papyrus.uml.diagram.clazz.edit.policies.UMLBaseItemSemanticEditPolicy;
import org.eclipse.papyrus.uml.diagram.clazz.part.UMLVisualIDRegistry;
import org.eclipse.papyrus.uml.diagram.common.helper.ElementHelper;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Stereotype;

public class ContainmentHelper extends ElementHelper {

	protected static final String CREATE_CONTAINMENT = "Create Containment";

	public static final String CONTAINMENT_CIRCLE_POSITION = "ContainmentCirclePosition";

	/** The Constant CONNECTION_VIEW. */
	public static final String KEY_CONNECTION_VIEW = "connection_view"; //$NON-NLS-1$

	/**
	 * Instantiates a new containment class helper.
	 *
	 * @param editDomain
	 *            the edit domain
	 */
	public ContainmentHelper(TransactionalEditingDomain editDomain) {
		this.editDomain = editDomain;
	}



	/**
	 * Delete all incoming link that go this node
	 *
	 * @param cc
	 *            a composite command in which the behavior of deletion
	 * @param node
	 *            where would like to remove all incoming containment link can not be null
	 */
	public void deleteIncomingContainmentLinksFor(CompositeCommand cc, View node) {
		for (Object incomingLink : node.getTargetEdges()) {
			Edge nextConnector = (Edge) incomingLink;
			View nextConnectorSource = nextConnector.getSource();
			if (isContainmentLink(nextConnector)) {
				cc.add(new DeleteCommand(getEditingDomain(), nextConnector));
				/* The containment circle node is deleted only if any other link is connected */
				if (isContainmentCircle(nextConnectorSource) && nextConnectorSource.getSourceEdges().size() == 1) {
					// Delete the containment circle
					cc.add(new DeleteCommand(getEditingDomain(), nextConnectorSource));
				}
			}
		}
	}

	/**
	 * Delete all outgoing link that come from this node
	 *
	 * @param cc
	 *            a composite command in which the behavior of deletion
	 * @param node
	 *            where would like to remove all outgoing containment link can not be null
	 */
	public void deleteOutgoingContainmentLinksFor(CompositeCommand cc, View node) {
		for (Object nextChild : node.getVisibleChildren()) {
			View circle = (View) nextChild;
			if (isContainmentCircle(circle)) {
				for (Object next : circle.getSourceEdges()) {
					Edge outgoingLink = (Edge) next;
					if (ContainmentHelper.isContainmentLink(outgoingLink)) {
						cc.add(new DeleteCommand(getEditingDomain(), outgoingLink));
					}
				}
			}
		}
	}

	/**
	 * DragDrop the contained class from the modelexplorer to the diagram and from the compartment to the diagram.
	 *
	 * @param droppedElement
	 *            the semantic class
	 * @param viewer
	 *            the viewer
	 * @param diagramPreferencesHint
	 *            the diagram preferences hint
	 * @param location
	 *            the location
	 * @param containerView
	 *            the container view
	 *
	 * @return the command
	 */
	public Command outlineDropContainedClass(PackageableElement droppedElement, EditPartViewer viewer, PreferencesHint diagramPreferencesHint, Point location, View containerView) {
		return dropElementToDiagram(droppedElement, viewer, diagramPreferencesHint, location, containerView);
	}

	/**
	 * This method can be called in two cases:
	 * - a drop from the model explorer
	 * - a drop from the owner class to outside of this class
	 * when the contained link is not created in this case.
	 *
	 *
	 * @param droppedElement
	 *            the dropped element <B> cannot be null</B>
	 * @param viewer
	 *            the viewer <B> cannot be null</B>
	 * @param diagramPreferencesHint
	 *            the diagram preferences hint <B> cannot be null</B>
	 * @param location
	 *            the location <B> cannot be null</B>
	 * @param containerView
	 *            the container view <B> cannot be null</B>
	 * @return the command
	 */
	public Command dropElementToDiagram(PackageableElement droppedElement, EditPartViewer viewer, PreferencesHint diagramPreferencesHint, Point location, View containerView) {
		// 0 what is the context of this call
		// - drop from the model explorer
		// - drop intra diagram form its container to outside of this class
		// - the edit part of a the dropped element already exist?
		EditPart droppedElementEditPart = findEditPartFor(viewer.getEditPartRegistry(), droppedElement);
		// Is is contained into a class ?
		Element owner = droppedElement.getOwner();
		// the container editpart is the the class that can contained the dropped element.
		// if it is not null we are in the context of drop intra diagram
		GraphicalEditPart containerEditpart = null;
		if (droppedElementEditPart != null) {
			GraphicalEditPart parentEP = (GraphicalEditPart) droppedElementEditPart.getParent();
			if (parentEP.resolveSemanticElement().equals(owner)) {
				containerEditpart = parentEP;
			}
		}
		CompositeCommand cc = new CompositeCommand("drop");
		// in the context of a drop from the model explorer -> create only a view for this element
		if (containerEditpart == null) {
			dropElementToDiagram(cc, droppedElement, diagramPreferencesHint, location, containerView);
		} else {
			// in the case of a drop intra diagram, remove view from the container and it into the diagram
			if (canHaveContainmentLink(droppedElementEditPart)) {
				cc.add(new DeleteCommand(getEditingDomain(), (View) droppedElementEditPart.getModel()));
				dropElementToDiagram(cc, droppedElement, diagramPreferencesHint, location, containerView);
			}
		}
		return new ICommandProxy(cc);
	}

	/**
	 * dropped the dropped element into container view at a good location,
	 *
	 * @param cc
	 *            the composite command, where the behavior will be add <B> cannot be null</B>
	 * @param droppedElement
	 *            the semantic element to drop <B> cannot be null</B>
	 * @param diagramPreferencesHint
	 *            <B> cannot be null</B>
	 * @param location
	 *            location of the dropped element <B> cannot be null</B>
	 * @param containerView
	 *            the container that will contain the view of the dropped element <B> cannot be null</B>
	 */
	protected void dropElementToDiagram(CompositeCommand cc, PackageableElement droppedElement, PreferencesHint diagramPreferencesHint, Point location, View containerView) {
		ViewDescriptor droppedElementDescriptor = new ViewDescriptor(new EObjectAdapter(droppedElement), Node.class, null, ViewUtil.APPEND, true, diagramPreferencesHint);
		CreateCommand containedNodeCreationCommand = new CreateCommand(this.editDomain, droppedElementDescriptor, containerView);
		cc.add(containedNodeCreationCommand);
		cc.add(new SetBoundsCommand(getEditingDomain(), "move", (IAdaptable) containedNodeCreationCommand.getCommandResult().getReturnValue(), new Point(location.x, location.y - 100)));
		addStereotypeLabelToDroppedElement(cc, droppedElement, (IAdaptable) containedNodeCreationCommand.getCommandResult().getReturnValue());
	}

	/**
	 * TO DO: to investigate about the use of this code
	 * this method is used to display applied stereotype of the dropped element
	 *
	 * @param cc
	 *            the command where the behavior will be add
	 * @param droppedElement
	 *            the dropped element <B> cannot be null</B>
	 * @param createdEditPartAdapter
	 *            a wrapper that contained the created view of the dropped element
	 */
	protected void addStereotypeLabelToDroppedElement(CompositeCommand cc, PackageableElement droppedElement, IAdaptable createdEditPartAdapter) {
		if (droppedElement.getAppliedStereotypes().isEmpty()) {
			return;
		}
		EList<Stereotype> stereotypeAppliedList = droppedElement.getAppliedStereotypes();
		Iterator<Stereotype> stereotypeAppliedIterator = stereotypeAppliedList.iterator();
		while (stereotypeAppliedIterator.hasNext()) {
			Stereotype stereotype = stereotypeAppliedIterator.next();
			String profileApplied = "\"" + stereotype.getProfile() + "\"::";
			cc.add(new CustomDropAppliedStereotypeCommand(this.editDomain, createdEditPartAdapter, profileApplied, UMLVisualInformationPapyrusConstant.STEREOTYPE_COMPARTMENT_LOCATION));
		}
	}

	/**
	 *
	 * Checks if is reorient about the containment link.
	 *
	 * @param request
	 *            a connection request
	 * @return true, if is reorient about the containment link
	 */
	public static boolean isReorientContainmentLink(ReconnectRequest request) {
		String visualId = getVisualID(request);
		return ContainmentLinkEditPart.VISUAL_ID.equals(visualId);
	}

	/**
	 * TO DO: to investigate about the use of this code
	 * During the reconnection we need to add information about the view that is reconnected.
	 *
	 * @param request
	 *            the request
	 * @return the reconnect request
	 */
	@SuppressWarnings("unchecked")
	public static ReconnectRequest extendReorientTargetRequest(ReconnectRequest request) {
		Object view = request.getConnectionEditPart().getModel();
		if (view instanceof View) {
			request.getExtendedData().put(ContainmentHelper.KEY_CONNECTION_VIEW, view);
		}
		return request;
	}

	/**
	 * TO DO: to investigate about the use of this code
	 * During the reconnection we need to add information about the view that is reconnected.
	 *
	 * @param request
	 *            the request
	 * @return the reconnect request
	 */
	@SuppressWarnings("unchecked")
	public static ReconnectRequest extendReorientSourceRequest(ReconnectRequest request) {
		Object view = request.getConnectionEditPart().getModel();
		if (view instanceof View) {
			request.getExtendedData().put(ContainmentHelper.KEY_CONNECTION_VIEW, view);
		}
		return request;
	}

	/**
	 * Gets the visual id.
	 *
	 * @param request
	 *            the request
	 * @return the visual id
	 */
	private static String getVisualID(ReconnectRequest request) {
		return (String) request.getExtendedData().get(UMLBaseItemSemanticEditPolicy.VISUAL_ID_KEY);
	}

	/**
	 * Checks if is containment link.
	 *
	 * @param edge
	 *            the edge
	 * @return true, if is containment link
	 */
	public static boolean isContainmentLink(Edge edge) {
		return ContainmentLinkEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getVisualID(edge));
	}

	/**
	 * Checks if is containment node.
	 *
	 * @param view
	 *            the notation view
	 * @return true, if is containment link
	 */
	public static boolean isContainmentCircle(View view) {
		return false;
	}

	/**
	 * checks if the node contains several outgoing link.
	 *
	 * @param containmentCircle
	 *            the node that show a contaiment link
	 * @return true if the coantaiment node is connected several link
	 */
	private static boolean circleHasOtherLinks(View containmentCircle) {
		return containmentCircle.getSourceEdges().size() > 1;
	}

	/**
	 * Delete incoming containment link command.
	 *
	 * @param editingDomain
	 *            the editing domain
	 * @param incomingLink
	 *            the incoming link
	 * @return the i undoable operation
	 */
	public static IUndoableOperation deleteIncomingContainmentLinkCommand(TransactionalEditingDomain editingDomain, Edge incomingLink) {
		CompositeTransactionalCommand cmd = new CompositeTransactionalCommand(editingDomain, "Delete Incoming Containment Link");
		cmd.add(new DeleteCommand(editingDomain, incomingLink));
		View containmentCircle = incomingLink.getSource();
		if (!circleHasOtherLinks(containmentCircle)) {
			cmd.add(new DeleteCommand(editingDomain, containmentCircle));
		}
		return cmd;
	}

	/**
	 * Adds the delete incoming containment link view commands.
	 *
	 * @param editingDomain
	 *            the editing domain
	 * @param targetNode
	 *            the target node
	 * @param cmd
	 *            the cmd
	 */
	public static void addDeleteIncomingContainmentLinkViewCommands(TransactionalEditingDomain editingDomain, View targetNode, ICompositeCommand cmd) {
		for (Object next : targetNode.getTargetEdges()) {
			Edge incomingLink = (Edge) next;
			if (ContainmentHelper.isContainmentLink(incomingLink)) {
				cmd.add(new DeleteCommand(editingDomain, incomingLink));
				View containmentCircle = incomingLink.getSource();
				if (!circleHasOtherLinks(containmentCircle)) {
					cmd.add(new DeleteCommand(editingDomain, containmentCircle));
				}
			}
		}
	}

	/**
	 * Checks for incoming containment link.
	 *
	 * @param targetNode
	 *            the target node
	 * @return true, if successful
	 */
	public static boolean hasIncomingContainmentLink(View targetNode) {
		for (Object next : targetNode.getTargetEdges()) {
			Edge incomingLink = (Edge) next;
			if (ContainmentHelper.isContainmentLink(incomingLink)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks for outgoing containment link.
	 *
	 * @param targetNode
	 *            the target node
	 * @return true, if successful
	 */
	public static boolean hasOutgoingContainmentLink(View targetNode) {
		for (Object next : targetNode.getVisibleChildren()) {
			View node = (View) next;
			if (isContainmentCircle(node)) {
				for (Object nextLink : node.getSourceEdges()) {
					Edge outgoingLink = (Edge) nextLink;
					if (ContainmentHelper.isContainmentLink(outgoingLink)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Adds the destroy outgoing containment links command.
	 *
	 * @param editingDomain
	 *            the editing domain
	 * @param sourceNode
	 *            the source node
	 * @param cmd
	 *
	 *            the cmd
	 */
	public static void addDeleteOutgoingContainmentLinkViewCommands(TransactionalEditingDomain editingDomain, View sourceNode, ICompositeCommand cmd) {
		for (Object child : sourceNode.getVisibleChildren()) {
			if (ContainmentHelper.isContainmentCircle((View) child)) {
				View circle = (View) child;
				cmd.add(new DeleteCommand(editingDomain, circle));
				for (Object next : circle.getSourceEdges()) {
					Edge outgoingLink = (Edge) next;
					if (ContainmentHelper.isContainmentLink(outgoingLink)) {
						cmd.add(new DeleteCommand(editingDomain, outgoingLink));
						// delete target view as it actually contained by the source object and will be deleted
						cmd.add(new DeleteCommand(editingDomain, outgoingLink.getTarget()));
						addDeleteOutgoingContainmentLinkViewCommands(editingDomain, outgoingLink.getTarget(), cmd);
					}
				}
			}
		}
	}

	/**
	 * look for a editpart from the semantic element
	 *
	 * @param editPartRegistry
	 *            the map of editpart
	 * @param droppedElement
	 *            the semantic element
	 * @return can return null if nothing is found
	 */
	public EditPart findEditPartFor(Map<?, ?> editPartRegistry, Element droppedElement) {
		for (Object next : editPartRegistry.values()) {
			EditPart currentEditPart = (EditPart) next;
			if (canHaveContainmentLink(currentEditPart)) {
				View currentView = (View) currentEditPart.getModel();
				if (droppedElement.equals(currentView.getElement())) {
					return currentEditPart;
				}
			}
		}
		return null;
	}

	private boolean canHaveContainmentLink(EditPart currentEditPart) {
		return currentEditPart instanceof ClassEditPart || currentEditPart instanceof PackageEditPartCN || currentEditPart instanceof PackageEditPart || currentEditPart instanceof ModelEditPartTN || currentEditPart instanceof NestedClassForClassEditPart
				|| currentEditPart instanceof ModelEditPartCN;
	}

	/**
	 * Move.
	 *
	 * @param objectToMove
	 *            the object to move
	 * @param to
	 *            the to
	 * @return true, if successful
	 */
	public boolean move(EObject objectToMove, EObject to) {
		if (objectToMove instanceof org.eclipse.uml2.uml.Package) {
			return movePackage((org.eclipse.uml2.uml.Package) objectToMove, to);
		} else if (objectToMove instanceof org.eclipse.uml2.uml.Class) {
			return moveClass((org.eclipse.uml2.uml.Class) objectToMove, to);
		}
		return false;
	}

	/**
	 * Move package.
	 *
	 * @param pakkage
	 *            the pakkage
	 * @param to
	 *            the to
	 * @return true, if successful
	 */
	private boolean movePackage(org.eclipse.uml2.uml.Package pakkage, EObject to) {
		Element from = pakkage.getOwner();
		if (to instanceof org.eclipse.uml2.uml.Package && from instanceof org.eclipse.uml2.uml.Package) {
			doMovePackage(pakkage, (org.eclipse.uml2.uml.Package) from, (org.eclipse.uml2.uml.Package) to);
			return true;
		}
		return false;
	}

	/**
	 * Move class.
	 *
	 * @param clazz
	 *            the clazz
	 * @param to
	 *            the to
	 * @return true, if successful
	 */
	private boolean moveClass(org.eclipse.uml2.uml.Class clazz, EObject to) {
		Element from = clazz.getOwner();
		if (from instanceof org.eclipse.uml2.uml.Class) {
			org.eclipse.uml2.uml.Class fromClazz = (org.eclipse.uml2.uml.Class) from;
			if (to instanceof org.eclipse.uml2.uml.Class) {
				doMoveClass(clazz, fromClazz, (org.eclipse.uml2.uml.Class) to);
				return true;
			} else if (to instanceof org.eclipse.uml2.uml.Package) {
				doMoveClass(clazz, fromClazz, (org.eclipse.uml2.uml.Package) to);
				return true;
			}
		}
		if (from instanceof org.eclipse.uml2.uml.Package) {
			org.eclipse.uml2.uml.Package fromPackage = (org.eclipse.uml2.uml.Package) from;
			if (to instanceof org.eclipse.uml2.uml.Class) {
				doMoveClass(clazz, fromPackage, (org.eclipse.uml2.uml.Class) to);
				return true;
			} else if (to instanceof org.eclipse.uml2.uml.Package) {
				doMoveClass(clazz, fromPackage, (org.eclipse.uml2.uml.Package) to);
				return true;
			}
		}
		return false;
	}

	/**
	 * Do move package.
	 *
	 * @param pakkage
	 *            the pakkage
	 * @param from
	 *            the from
	 * @param to
	 *            the to
	 */
	private void doMovePackage(org.eclipse.uml2.uml.Package pakkage, org.eclipse.uml2.uml.Package from, org.eclipse.uml2.uml.Package to) {
		from.getNestedPackages().remove(pakkage);
		to.getNestedPackages().add(pakkage);
	}

	/**
	 * Do move class.
	 *
	 * @param clazz
	 *            the clazz
	 * @param from
	 *            the from
	 * @param to
	 *            the to
	 */
	private void doMoveClass(org.eclipse.uml2.uml.Class clazz, org.eclipse.uml2.uml.Package from, org.eclipse.uml2.uml.Package to) {
		from.getPackagedElements().remove(clazz);
		to.getPackagedElements().add(clazz);
	}

	/**
	 * Do move class.
	 *
	 * @param clazz
	 *            the clazz
	 * @param from
	 *            the from
	 * @param to
	 *            the to
	 */
	private void doMoveClass(org.eclipse.uml2.uml.Class clazz, org.eclipse.uml2.uml.Package from, org.eclipse.uml2.uml.Class to) {
		from.getPackagedElements().remove(clazz);
		to.getNestedClassifiers().add(clazz);
	}

	/**
	 * Do move class.
	 *
	 * @param clazz
	 *            the clazz
	 * @param from
	 *            the from
	 * @param to
	 *            the to
	 */
	private void doMoveClass(org.eclipse.uml2.uml.Class clazz, org.eclipse.uml2.uml.Class from, org.eclipse.uml2.uml.Package to) {
		from.getNestedClassifiers().remove(clazz);
		to.getPackagedElements().add(clazz);
	}

	/**
	 * Do move class.
	 *
	 * @param clazz
	 *            the clazz
	 * @param from
	 *            the from
	 * @param to
	 *            the to
	 */
	private void doMoveClass(org.eclipse.uml2.uml.Class clazz, org.eclipse.uml2.uml.Class from, org.eclipse.uml2.uml.Class to) {
		from.getNestedClassifiers().remove(clazz);
		to.getNestedClassifiers().add(clazz);
	}
}
