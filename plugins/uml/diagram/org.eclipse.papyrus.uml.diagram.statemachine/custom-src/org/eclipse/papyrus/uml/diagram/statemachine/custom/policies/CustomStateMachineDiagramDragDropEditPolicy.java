/**
 * Copyright (c) 2014, 2015 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bug 465416
 */
package org.eclipse.papyrus.uml.diagram.statemachine.custom.policies;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.commands.SetPropertyCommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.CreateCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.CompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.core.util.PackageUtil;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.wrappers.GMFtoGEFCommandWrapper;
import org.eclipse.papyrus.infra.gmfdiag.common.adapter.SemanticAdapter;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.CommonDeferredCreateConnectionViewCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.CommonDiagramDragDropEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.strategy.paste.ShowConstraintContextLink;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.commands.CreateViewCommand;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.commands.CustomCompositeStateSetBoundsCommand;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.commands.CustomCompositeStateWithDefaultRegionCreateNodeCommand;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.commands.CustomFirstRegionInCompositeStateCreateElementCommand;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.commands.CustomRegionCreateElementCommand;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.commands.CustomRegionMoveCommand;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.commands.CustomStateMachineSetBoundsCommand;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.commands.CustomStateMachineWithDefaultRegionCreateNodeCommand;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomRegionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.figures.RegionFigure;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.helpers.StateMachineLinkMappingHelper;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.helpers.Zone;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ConnectionPointReferenceEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.FinalStateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateChoiceEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateDeepHistoryEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateEntryPointEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateExitPointEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateForkEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateInitialEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateJoinEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateJunctionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateShallowHistoryEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateTerminateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.RegionCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.RegionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPartTN;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateMachineCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateMachineEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.TransitionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.part.UMLVisualIDRegistry;
import org.eclipse.papyrus.uml.diagram.statemachine.providers.UMLElementTypes;
import org.eclipse.uml2.uml.ConnectionPointReference;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.FinalState;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.TransitionKind;
import org.eclipse.uml2.uml.Vertex;

public class CustomStateMachineDiagramDragDropEditPolicy extends CommonDiagramDragDropEditPolicy {

	IFigure sizeOnDropFeedback = null;

	String dropLocation = Zone.RIGHT;

	boolean fromOutline = false;

	/**
	 * Instantiates a new state machine diagram drag drop edit policy with the
	 * right link mapping helper
	 */
	public CustomStateMachineDiagramDragDropEditPolicy() {
		super(StateMachineLinkMappingHelper.getInstance());
	}

	/**
	 * <pre>
	 * Returns the drop command for Affixed nodes (Pseudostate entry/exitPoint, ConnectionPointReference).
	 * </pre>
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param location
	 *            the location to drop the element
	 * @param droppedElement
	 *            the element to drop
	 * @param nodeVISUALID
	 *            the visual identifier of the EditPart of the dropped element
	 * @return the drop command
	 */
	@Override
	protected Command dropAffixedNode(DropObjectsRequest dropRequest, Element droppedElement, String nodeVISUALID) {

		// The dropped element must be a Pseudostate or ConnectionPointReference
		if (!((droppedElement instanceof Pseudostate) || (droppedElement instanceof ConnectionPointReference))) {
			return UnexecutableCommand.INSTANCE;
		}

		if (droppedElement instanceof Pseudostate) {
			Pseudostate ps = (Pseudostate) droppedElement;
			// The dropped element must be an entry or exitPoint
			PseudostateKind kind = ps.getKind();
			if (!(kind.equals(PseudostateKind.ENTRY_POINT_LITERAL) || kind.equals(PseudostateKind.EXIT_POINT_LITERAL))) {
				return UnexecutableCommand.INSTANCE;
			}
		}

		// Manage Element drop in compartment
		Boolean isCompartmentTarget = false; // True if the target is a ShapeCompartmentEditPart
		GraphicalEditPart graphicalParentEditPart = (GraphicalEditPart) getHost();

		// Default drop location
		Point dropLocation = dropRequest.getLocation().getCopy();

		// Detect if the drop target is a compartment
		if (graphicalParentEditPart instanceof RegionCompartmentEditPart) {
			isCompartmentTarget = true;

			RegionFigure regionFigure = ((RegionEditPart) graphicalParentEditPart.getParent()).getPrimaryShape();

			// Replace compartment edit part by its ancestor StateMachineEditPart or StateEditPart
			graphicalParentEditPart = (GraphicalEditPart) graphicalParentEditPart.getParent().getParent().getParent();

		} else if (graphicalParentEditPart instanceof StateCompartmentEditPart) {
			isCompartmentTarget = true;
			// Replace compartment edit part by its ancestor StateMachineEditPart or StateEditPart
			graphicalParentEditPart = (GraphicalEditPart) graphicalParentEditPart.getParent();
		}
		// Manage Element drop in compartment

		EObject graphicalParentObject = graphicalParentEditPart.resolveSemanticElement();

		if ((graphicalParentObject instanceof StateMachine) && (((StateMachine) graphicalParentObject).getConnectionPoints().contains(droppedElement))) {
			// Drop Pseudostate on StateMachine
			if (isCompartmentTarget) {
				return getDropAffixedNodeInCompartmentCommand(nodeVISUALID, dropLocation, droppedElement);
			}
			return new ICommandProxy(getDefaultDropNodeCommand(nodeVISUALID, dropLocation, droppedElement));

		}
		if (graphicalParentObject instanceof State) {
			State state = (State) graphicalParentObject;
			State redefinedState = state.getRedefinedState();
			// also take redefined states into account, see Bug 366415
			EList<Pseudostate> connectionPoints;
			if (redefinedState != null) {
				connectionPoints = new BasicEList<>(state.getConnectionPoints());
				connectionPoints.addAll(redefinedState.getConnectionPoints());
			} else {
				connectionPoints = state.getConnectionPoints();
			}

			if ((droppedElement instanceof Pseudostate) && (connectionPoints.contains(droppedElement)) || (droppedElement instanceof ConnectionPointReference)
					&& (((State) graphicalParentObject).getConnections().contains(droppedElement))) {
				// Drop Pseudostate or ConnectionPointReference on State
				if (isCompartmentTarget) {
					return getDropAffixedNodeInCompartmentCommand(nodeVISUALID, dropLocation, droppedElement);
				}
				return new ICommandProxy(getDefaultDropNodeCommand(nodeVISUALID, dropLocation, droppedElement));
			}
		}

		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * <pre>
	 * Returns the drop command for pseudo states, e.g. initial, choice etc.
	 * Specific command is required in order to handle redefined pseudo states (Bug 366415)
	 * </pre>
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param location
	 *            the location to drop the element
	 * @param droppedElement
	 *            the element to drop
	 * @param nodeVISUALID
	 *            the visual identifier of the EditPart of the dropped element
	 * @return the drop command
	 */
	protected Command dropPseudostate(DropObjectsRequest dropRequest, Element droppedElement, String nodeVISUALID) {
		GraphicalEditPart graphicalParentEditPart = (GraphicalEditPart) getHost();
		EObject graphicalParentObject = graphicalParentEditPart.resolveSemanticElement();
		EObject droppedElemContainer = droppedElement.eContainer();
		Region extendedRegion = null;
		if (graphicalParentObject instanceof org.eclipse.uml2.uml.Region) {
			extendedRegion = ((org.eclipse.uml2.uml.Region) graphicalParentObject).getExtendedRegion();
		}
		if ((graphicalParentObject instanceof org.eclipse.uml2.uml.Region) && (droppedElemContainer == graphicalParentObject || droppedElemContainer == extendedRegion)) {
			Point location = getTranslatedLocation(dropRequest);
			CompositeCommand cc = new CompositeCommand("Drop pseudo state");

			if (graphicalParentEditPart.getParent() instanceof RegionEditPart) {
				RegionFigure regionFigure = ((RegionEditPart) graphicalParentEditPart.getParent()).getPrimaryShape();
				regionFigure.translateToAbsolute(location);
				location.translate(regionFigure.getLocation());
			}
			cc.compose(getDefaultDropNodeCommand(nodeVISUALID, location, droppedElement, dropRequest));

			return new ICommandProxy(cc.reduce());
		}
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * Returns the drop command for StateMachine nodes.
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param location
	 *            the location to drop the element
	 * @param droppedElement
	 *            the element to drop
	 * @param nodeVISUALID
	 *            the visual identifier of the EditPart of the dropped element
	 * @return the drop command
	 */
	protected Command dropRegion(DropObjectsRequest dropRequest, Region droppedElement, String nodeVISUALID) {

		GraphicalEditPart graphicalParentEditPart = (GraphicalEditPart) getHost();
		EObject graphicalParentObject = graphicalParentEditPart.resolveSemanticElement();

		if (graphicalParentObject instanceof Region) {
			Region region = (Region) graphicalParentObject;

			if (((region.getStateMachine() != null) && region.getStateMachine().getRegions().contains(droppedElement) && !region.equals(droppedElement))
					|| ((region.getState() != null) && region.getState().getRegions().contains(droppedElement) && !region.equals(droppedElement))) {
				CompositeCommand cc = new CompositeCommand("Drop");
				// get an adaptable for the dropped region
				IAdaptable adaptableForDroppedRegion = new SemanticAdapter(droppedElement, null);
				// get the existing region view
				View existingRegionView = (View) graphicalParentEditPart.getParent().getModel();
				// get and adaptable for it, to pass on to commands
				IAdaptable adaptableForExistingRegionView = new SemanticAdapter(null, existingRegionView);

				// check whether the dropped region is already shown in the state
				// machine or state compartment
				View compartment = (View) existingRegionView.eContainer();
				View alreadyShown = null;
				Iterator<View> it = compartment.getChildren().iterator();
				while ((alreadyShown == null) && it.hasNext()) {
					View current = it.next();
					if (current.getElement().equals(droppedElement)) {
						alreadyShown = current;
					}
				}
				if (alreadyShown != null) {
					if (fromOutline) {
						return UnexecutableCommand.INSTANCE;
					}

					IAdaptable adaptableForRegionToMove = new SemanticAdapter(null, alreadyShown);

					// specific command to move the already shown region
					CustomRegionMoveCommand moveCommand = new CustomRegionMoveCommand(adaptableForExistingRegionView, adaptableForRegionToMove, ((IGraphicalEditPart) getHost()).getDiagramPreferencesHint(), getEditingDomain(),
							DiagramUIMessages.CreateCommand_Label, dropLocation);
					cc.compose(moveCommand);
				} else {
					// do the whole job
					CustomRegionCreateElementCommand createNewRegion = new CustomRegionCreateElementCommand(adaptableForExistingRegionView, adaptableForDroppedRegion, ((IGraphicalEditPart) getHost()).getDiagramPreferencesHint(), getEditingDomain(),
							DiagramUIMessages.CreateCommand_Label, dropLocation);
					cc.compose(createNewRegion);
				}
				return new ICommandProxy(cc.reduce());
			}
		}

		else if (graphicalParentObject instanceof State) {
			State state = (State) graphicalParentObject;

			if (state.getRegions().contains(droppedElement)) {

				// get the state view
				View stateView = (View) graphicalParentEditPart.getModel();

				// check whether any region is already shown in the state compartment
				if (stateView.getChildren().size() < 2) {
					return UnexecutableCommand.INSTANCE;
				}
				View compartment = ViewUtil.getChildBySemanticHint(stateView, String.valueOf(StateCompartmentEditPart.VISUAL_ID));
				if (!compartment.getChildren().isEmpty()) {
					// then do not allow the drag and drop on state, this forces the drag and drop on an displayed region (see above)
					return UnexecutableCommand.INSTANCE;
				}

				CompositeCommand cc = new CompositeCommand("Drop");
				// get an adaptable for the dropped region
				IAdaptable adaptableForDroppedRegion = new SemanticAdapter(droppedElement, null);
				// get and adaptable for the compartmentView, to pass on to commands
				IAdaptable adaptableForCompartment = new SemanticAdapter(null, compartment);

				// do the whole job
				CustomFirstRegionInCompositeStateCreateElementCommand createNewRegion = new CustomFirstRegionInCompositeStateCreateElementCommand(adaptableForCompartment, adaptableForDroppedRegion, ((IGraphicalEditPart) getHost()).getDiagramPreferencesHint(),
						getEditingDomain(), DiagramUIMessages.CreateCommand_Label, dropLocation);
				SetPropertyCommand showCompartment = new SetPropertyCommand(getEditingDomain(), adaptableForCompartment, "notation.View.visible", "Visibility", true);
				cc.compose(createNewRegion);
				cc.compose(showCompartment);

				return new ICommandProxy(cc.reduce());
			}

		}

		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * Returns the drop command for StateMachine nodes.
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param location
	 *            the location to drop the element
	 * @param droppedElement
	 *            the element to drop
	 * @param nodeVISUALID
	 *            the visual identifier of the EditPart of the dropped element
	 * @return the drop command
	 */
	protected Command dropStateMachine(DropObjectsRequest dropRequest, Point location, StateMachine droppedElement, String nodeVISUALID) {

		GraphicalEditPart graphicalParentEditPart = (GraphicalEditPart) getHost();
		EObject graphicalParentObject = graphicalParentEditPart.resolveSemanticElement();

		if ((graphicalParentObject instanceof org.eclipse.uml2.uml.StateMachine)) {
			CompositeCommand cc = new CompositeCommand("Drop");
			IAdaptable elementAdapter = new EObjectAdapter(droppedElement);

			ViewDescriptor descriptor = new ViewDescriptor(elementAdapter, Node.class, ((IHintedType) getUMLElementType(nodeVISUALID)).getSemanticHint(), ViewUtil.APPEND, true, getDiagramPreferencesHint());

			CreateCommand createStateMachine = new CreateCommand(getEditingDomain(), descriptor, (View) (getHost().getModel()));

			CustomStateMachineWithDefaultRegionCreateNodeCommand createRegion = new CustomStateMachineWithDefaultRegionCreateNodeCommand((IAdaptable) createStateMachine.getCommandResult().getReturnValue(),
					((IGraphicalEditPart) getHost()).getDiagramPreferencesHint(), getEditingDomain(), DiagramUIMessages.CreateCommand_Label, createStateMachine.getAffectedFiles());

			CustomStateMachineSetBoundsCommand setBoundsCommand = new CustomStateMachineSetBoundsCommand(getEditingDomain(), null, descriptor, new Rectangle(location.x, location.y, -1, -1));

			cc.compose(createStateMachine);
			cc.compose(createRegion);
			cc.compose(setBoundsCommand);
			return new ICommandProxy(cc.reduce());
		}

		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * Returns the drop command for State nodes on the canvas.
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param location
	 *            the location to drop the element
	 * @param droppedElement
	 *            the element to drop
	 * @param nodeVISUALID
	 *            the visual identifier of the EditPart of the dropped element
	 * @return the drop command
	 */
	protected Command dropStateEditPartTN(DropObjectsRequest dropRequest, Point location, State semanticElement, String nodeVISUALID) {
		// by default, never allow state to be dropped on the canvas of the diagram.
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * Returns the drop command for State nodes.
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param location
	 *            the location to drop the element
	 * @param droppedElement
	 *            the element to drop
	 * @param nodeVISUALID
	 *            the visual identifier of the EditPart of the dropped element
	 * @return the drop command
	 */
	protected Command dropState(DropObjectsRequest dropRequest, Point location, State droppedElement, String nodeVISUALID) {

		GraphicalEditPart graphicalParentEditPart = (GraphicalEditPart) getHost();
		EObject graphicalParentObject = graphicalParentEditPart.resolveSemanticElement();
		EObject droppedElemContainer = droppedElement.eContainer();
		Region extendedRegion = null;
		if (graphicalParentObject instanceof org.eclipse.uml2.uml.Region) {
			extendedRegion = ((org.eclipse.uml2.uml.Region) graphicalParentObject).getExtendedRegion();
		}
		if ((graphicalParentObject instanceof org.eclipse.uml2.uml.Region) && (droppedElemContainer == graphicalParentObject || droppedElemContainer == extendedRegion)) {
			CompositeCommand cc = new CompositeCommand("Drop");
			IAdaptable elementAdapter = new EObjectAdapter(droppedElement);

			ViewDescriptor descriptor = new ViewDescriptor(elementAdapter, Node.class, ((IHintedType) getUMLElementType(nodeVISUALID)).getSemanticHint(), ViewUtil.APPEND, true, getDiagramPreferencesHint());

			CreateCommand createState = new CreateCommand(getEditingDomain(), descriptor, (View) (getHost().getModel()));

			CustomCompositeStateWithDefaultRegionCreateNodeCommand createRegion = new CustomCompositeStateWithDefaultRegionCreateNodeCommand((IAdaptable) createState.getCommandResult().getReturnValue(),
					((IGraphicalEditPart) getHost()).getDiagramPreferencesHint(), getEditingDomain(), DiagramUIMessages.CreateCommand_Label, createState.getAffectedFiles());

			CustomCompositeStateSetBoundsCommand setBoundsCommand;

			cc.compose(createState);
			cc.compose(createRegion);

			// calculate position relative to graphical parent (passed position is absolute)
			Rectangle parentBounds = graphicalParentEditPart.getFigure().getBounds().getCopy();
			graphicalParentEditPart.getFigure().translateToAbsolute(parentBounds);
			Point relLocation = new Point(location.x - parentBounds.x, location.y - parentBounds.y);

			// take care of the case when a simple state is dropped, then we should provide a reasonable size
			if (droppedElement.getRegions().isEmpty()) {
				// final state has default size 20
				int sizeHint = (droppedElement instanceof FinalState) ? 20 : 60;
				setBoundsCommand = new CustomCompositeStateSetBoundsCommand(getEditingDomain(), null, descriptor, new Rectangle(relLocation.x, relLocation.y, sizeHint, sizeHint), false);
				cc.compose(setBoundsCommand);
			} else {
				setBoundsCommand = new CustomCompositeStateSetBoundsCommand(getEditingDomain(), null, descriptor, new Rectangle(relLocation.x, relLocation.y, -1, -1), true);
				cc.compose(setBoundsCommand);
				// force compartment to be shown
				SetPropertyCommand showCompartment = new SetPropertyCommand(getEditingDomain(), (IAdaptable) createState.getCommandResult().getReturnValue(), "notation.View.visible", "Visibility", true) {

					@Override
					protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {

						View view = getViewAdapter().getAdapter(View.class);
						if ((view != null) && (view.getChildren().size() >= 2) && (view.getChildren().get(1) != null)) {
							ENamedElement namedElement = PackageUtil.getElement((String) getPropertyId());
							if (namedElement instanceof EStructuralFeature) {
								ViewUtil.setStructuralFeatureValue((View) view.getChildren().get(1), (EStructuralFeature) namedElement, getNewValue());
							}
						}
						return CommandResult.newOKCommandResult();
					}

				};
				cc.compose(showCompartment);
			}
			return new ICommandProxy(cc.reduce());
		}

		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * Returns the drop command for Transition links.
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param semanticLink
	 *            the element to drop
	 * @param linkVISUALID
	 *            the visual identifier of the EditPart of the dropped element
	 * @return the drop command
	 */
	protected Command dropTransition(DropObjectsRequest dropRequest, Transition droppedElement, String linkVISUALID) {
		// we restrict drop to be over the owning region
		GraphicalEditPart graphicalParentEditPart = (GraphicalEditPart) getHost();
		EObject graphicalParentObject = graphicalParentEditPart.resolveSemanticElement();
		// might be a state machine instead of a region

		// fix for bug 407737 - check internal transition case before verifying that parent is a region
		// drop is not on owning region. The transition might be a internal transition => drop to internal compartment region
		if (TransitionKind.INTERNAL == droppedElement.getKind().getValue()) {
			return dropInternalTransition(dropRequest, droppedElement, getNodeVisualID(graphicalParentEditPart.getNotationView(), droppedElement));
		}

		if (!(graphicalParentObject instanceof Region)) {
			return UnexecutableCommand.INSTANCE;
		}
		Region region = (Region) graphicalParentObject;
		Region extendedRegion = region.getExtendedRegion();
		// also take redefined (extended) regions into account, see Bug 366415
		EList<Transition> transitions;
		if (extendedRegion != null) {
			transitions = new BasicEList<>(region.getTransitions());
			transitions.addAll(extendedRegion.getTransitions());
		} else {
			transitions = region.getTransitions();
		}
		if (!transitions.contains(droppedElement)) {
			return UnexecutableCommand.INSTANCE;
		}
		Vertex source = droppedElement.getSource();
		Vertex target = droppedElement.getTarget();

		if ((source != null) && (target != null)) {
			// look for editpart
			GraphicalEditPart sourceEditPart = (GraphicalEditPart) lookForEditPart(source);
			GraphicalEditPart targetEditPart = (GraphicalEditPart) lookForEditPart(target);
			// when the vertex are not represented on the diagram, we look for their parents.
			DiagramEditPart diagram = DiagramEditPartsUtil.getDiagramEditPart(getHost());
			// the parents of the vertex, we use them when the VertexEditPart are not on the diagram
			EditPart sourceParent = null;
			EditPart targetParent = null;
			if (sourceEditPart == null || targetEditPart == null) {

				List<IGraphicalEditPart> allEPs = DiagramEditPartsUtil.getAllEditParts(diagram);
				EObject srcParent = source.eContainer();
				EObject tgtParent = target.eContainer();
				if (srcParent instanceof org.eclipse.uml2.uml.Region) {
					extendedRegion = ((org.eclipse.uml2.uml.Region) graphicalParentObject).getExtendedRegion();
				}

				// source or target (or both) have no edit part, i.e. are not in the diagram. Try to resolve their parents
				for (IGraphicalEditPart iGraphicalEditPart : allEPs) {
					EObject object = ViewUtil.resolveSemanticElement((View) iGraphicalEditPart.getModel());// method getHostObject
					if (object instanceof org.eclipse.uml2.uml.Region) {
						extendedRegion = ((org.eclipse.uml2.uml.Region) object).getExtendedRegion();
					} else {
						extendedRegion = null;
					}
					if ((object == srcParent || extendedRegion == srcParent) && !(iGraphicalEditPart instanceof CompartmentEditPart)) {
						sourceParent = iGraphicalEditPart;
					}
					if ((object == tgtParent || extendedRegion == tgtParent) && !(iGraphicalEditPart instanceof CompartmentEditPart)) {
						targetParent = iGraphicalEditPart;
					}
					if (targetParent != null && sourceParent != null) {
						sourceParent = (EditPart) sourceParent.getChildren().get(0);
						targetParent = (EditPart) targetParent.getChildren().get(0);
						break;
					}
				}
				// the parent of the vertex shall be present in the diagram otherwise we do not support drag and drop
				if ((targetParent == null) || (sourceParent == null)) {
					return UnexecutableCommand.INSTANCE;
				}
			}

			return new ICommandProxy(dropBinaryLink(new CompositeCommand("drop Transition"), source, target, //$NON-NLS-1$
					linkVISUALID, dropRequest.getLocation(), droppedElement, sourceParent, targetParent));
		} else {
			return UnexecutableCommand.INSTANCE;
		}
	}

	/**
	 * handle drop of transition with kindTransition set as internal
	 *
	 * @param dropRequest
	 * @param droppedElement
	 * @param nodeVisualID
	 * @return
	 */
	protected Command dropInternalTransition(DropObjectsRequest dropRequest, Transition droppedElement, String nodeVisualID) {
		if (nodeVisualID != null) {
			CompositeCommand cc = new CompositeCommand("Drop Internal Transition");
			;
			ICommand defaultDropNodeCommand = getDefaultDropNodeCommand(nodeVisualID, dropRequest.getLocation().getCopy(), droppedElement, dropRequest);
			cc.compose(defaultDropNodeCommand);
			if (cc != null) {
				cc.reduce();
				if (!cc.isEmpty() && cc.canExecute()) {
					GraphicalEditPart graphicalParentEditPart = (GraphicalEditPart) getHost();
					EditPart compartmentEP = graphicalParentEditPart.getChildBySemanticHint(
							String.valueOf(StateCompartmentEditPart.VISUAL_ID));
					if (compartmentEP != null) {
						// assure visibility of state compartment, otherwise newly added transition is not visible.
						IAdaptable adaptableForCompartment = new SemanticAdapter(null, compartmentEP.getModel());

						SetPropertyCommand showCompartment = new SetPropertyCommand(getEditingDomain(),
								adaptableForCompartment, "notation.View.visible", "Visibility", true); //$NON-NLS-1$ //$NON-NLS-2$
						cc.compose(showCompartment);
					}
					return new ICommandProxy(cc);
				}
			}
		}
		return UnexecutableCommand.INSTANCE;

	}

	/**
	 * the method provides command to create the binary link into the diagram. If the source and the
	 * target views do not exist, these views will be created.
	 *
	 * @param cc
	 *            the composite command that will contain the set of command to create the binary
	 *            link
	 * @param source
	 *            the source the element source of the link
	 * @param target
	 *            the target the element target of the link
	 * @param linkVISUALID
	 *            the link VISUALID used to create the view
	 * @param location
	 *            the location the location where the view will be be created
	 * @param semanticLink
	 *            the semantic link that will be attached to the view
	 * @param sourceParent
	 *            the editPart of the source parent
	 * @param targetParent
	 *            the editPart of the target parent
	 *
	 * @return the composite command
	 */
	public CompositeCommand dropBinaryLink(CompositeCommand cc, Element source, Element target, String linkVISUALID, Point location, Element semanticLink, EditPart sourceParent, EditPart targetParent) {
		// look for editpart
		GraphicalEditPart sourceEditPart = (GraphicalEditPart) lookForEditPart(source);
		GraphicalEditPart targetEditPart = (GraphicalEditPart) lookForEditPart(target);

		// descriptor of the link
		CreateConnectionViewRequest.ConnectionViewDescriptor linkdescriptor = new CreateConnectionViewRequest.ConnectionViewDescriptor(getUMLElementType(linkVISUALID), ((IHintedType) getUMLElementType(linkVISUALID)).getSemanticHint(),
				getDiagramPreferencesHint());

		IAdaptable sourceAdapter = null;
		IAdaptable targetAdapter = null;
		if (sourceEditPart == null) {
			// creation of the node
			ViewDescriptor descriptor = new ViewDescriptor(new EObjectAdapter(source), Node.class, null, ViewUtil.APPEND, true, ((IGraphicalEditPart) getHost()).getDiagramPreferencesHint());

			// get the command and execute it.
			CreateCommand nodeCreationCommand = new CreateCommand(((IGraphicalEditPart) getHost()).getEditingDomain(), descriptor, (View) sourceParent.getModel());
			cc.compose(nodeCreationCommand);
			SetBoundsCommand setBoundsCommand = new SetBoundsCommand(getEditingDomain(), "move", (IAdaptable) nodeCreationCommand.getCommandResult().getReturnValue(), new Point(location.x, location.y + 30)); //$NON-NLS-1$
			cc.compose(setBoundsCommand);

			sourceAdapter = (IAdaptable) nodeCreationCommand.getCommandResult().getReturnValue();
		} else {
			sourceAdapter = new SemanticAdapter(null, sourceEditPart.getModel());
		}
		// additional check to ensure we do not create twice the same node when links are "loops" on the same element
		if ((target != null) && !target.equals(source)) {
			if (targetEditPart == null) {
				// creation of the node
				ViewDescriptor descriptor = new ViewDescriptor(new EObjectAdapter(target), Node.class, null, ViewUtil.APPEND, true, ((IGraphicalEditPart) getHost()).getDiagramPreferencesHint());

				// get the command and execute it.
				CreateCommand nodeCreationCommand = new CreateCommand(((IGraphicalEditPart) getHost()).getEditingDomain(), descriptor, ((View) targetParent.getModel()));
				cc.compose(nodeCreationCommand);
				// take care of the location for the cases when the target is not in the same container as the source
				SetBoundsCommand setBoundsCommand;
				if (!targetParent.equals(sourceParent)) {
					setBoundsCommand = new SetBoundsCommand(getEditingDomain(), "move", (IAdaptable) nodeCreationCommand.getCommandResult().getReturnValue(), new Point(10, 10)); //$NON-NLS-1$
				} else {
					setBoundsCommand = new SetBoundsCommand(getEditingDomain(), "move", (IAdaptable) nodeCreationCommand.getCommandResult().getReturnValue(), new Point(location.x, location.y - 30)); //$NON-NLS-1$
				}
				cc.compose(setBoundsCommand);
				targetAdapter = (IAdaptable) nodeCreationCommand.getCommandResult().getReturnValue();

			} else {
				targetAdapter = new SemanticAdapter(null, targetEditPart.getModel());
			}
		}
		// in case of loop links (see above) we pass on the same adapter for both source and target
		if ((target != null) && target.equals(source)) {
			targetAdapter = sourceAdapter;
		}

		CommonDeferredCreateConnectionViewCommand aLinkCommand = new CommonDeferredCreateConnectionViewCommand(getEditingDomain(), ((IHintedType) getUMLElementType(linkVISUALID)).getSemanticHint(), sourceAdapter, targetAdapter, getViewer(),
				getDiagramPreferencesHint(), linkdescriptor, null);
		aLinkCommand.setElement(semanticLink);
		cc.compose(aLinkCommand);
		return cc;

	}

	@Override
	public void eraseTargetFeedback(Request request) {
		if (sizeOnDropFeedback != null) {
			removeFeedback(sizeOnDropFeedback);
			sizeOnDropFeedback = null;
		}
	}

	/**
	 * <pre>
	 * This method returns the drop command for AffixedNode (Pseudostate, ConnectionPointReference)
	 * in case the node is dropped on a ShapeCompartmentEditPart.
	 * </pre>
	 *
	 * @param nodeVISUALID
	 *            the node visual identifier
	 * @param location
	 *            the drop location
	 * @param droppedObject
	 *            the object to drop
	 * @return a CompositeCommand for Drop
	 */
	@Override
	protected CompoundCommand getDropAffixedNodeInCompartmentCommand(String nodeVISUALID, Point location, EObject droppedObject) {

		CompoundCommand cc = new CompoundCommand("Drop");
		IAdaptable elementAdapter = new EObjectAdapter(droppedObject);

		ViewDescriptor descriptor = new ViewDescriptor(elementAdapter, Node.class, ((IHintedType) getUMLElementType(nodeVISUALID)).getSemanticHint(), ViewUtil.APPEND, true, getDiagramPreferencesHint());
		// Create the command targeting host parent (owner of the ShapeCompartmentEditPart)
		CreateViewCommand createCommand = null;
		if (nodeVISUALID != ConnectionPointReferenceEditPart.VISUAL_ID) {
			createCommand = new CreateViewCommand(getEditingDomain(), descriptor, ((View) (getHost().getParent().getParent().getParent().getModel())));
			cc.add(new ICommandProxy(createCommand));
		} else {
			createCommand = new CreateViewCommand(getEditingDomain(), descriptor, ((View) (getHost().getParent().getModel())));
			cc.add(new ICommandProxy(createCommand));
		}

		SetBoundsCommand setBoundsCommand = new SetBoundsCommand(getEditingDomain(), "move", (IAdaptable) createCommand.getCommandResult().getReturnValue(), location);
		cc.add(new ICommandProxy(setBoundsCommand));

		return cc;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Set<String> getDroppableElementVisualId() {
		Set<String> droppableElementsVisualId = new HashSet<>();
		droppableElementsVisualId.add(StateMachineEditPart.VISUAL_ID);
		droppableElementsVisualId.add(StateEditPartTN.VISUAL_ID);
		droppableElementsVisualId.add(StateEditPart.VISUAL_ID);
		droppableElementsVisualId.add(RegionEditPart.VISUAL_ID);
		droppableElementsVisualId.add(PseudostateEntryPointEditPart.VISUAL_ID);
		droppableElementsVisualId.add(PseudostateExitPointEditPart.VISUAL_ID);
		droppableElementsVisualId.add(ConnectionPointReferenceEditPart.VISUAL_ID);
		droppableElementsVisualId.add(TransitionEditPart.VISUAL_ID);
		// add the following pseudo states to manage addition of redefined elements (Bug 366415)
		droppableElementsVisualId.add(PseudostateInitialEditPart.VISUAL_ID);
		droppableElementsVisualId.add(PseudostateTerminateEditPart.VISUAL_ID);
		droppableElementsVisualId.add(PseudostateChoiceEditPart.VISUAL_ID);
		droppableElementsVisualId.add(PseudostateDeepHistoryEditPart.VISUAL_ID);
		droppableElementsVisualId.add(PseudostateShallowHistoryEditPart.VISUAL_ID);
		droppableElementsVisualId.add(PseudostateForkEditPart.VISUAL_ID);
		droppableElementsVisualId.add(PseudostateJoinEditPart.VISUAL_ID);
		droppableElementsVisualId.add(PseudostateJunctionEditPart.VISUAL_ID);
		// add final state
		droppableElementsVisualId.add(FinalStateEditPart.VISUAL_ID);
		droppableElementsVisualId.add(ConstraintEditPart.VISUAL_ID);

		// droppableElementsVisualId.add(EntryStateBehaviorEditPart.VISUAL_ID);
		return droppableElementsVisualId;
	}

	@Override
	public String getLinkWithClassVisualID(EObject domainElement) {
		return UMLVisualIDRegistry.getLinkWithClassVisualID(domainElement);
	}

	@Override
	public String getNodeVisualID(View containerView, EObject domainElement) {
		if ((domainElement instanceof Region) && (containerView.getElement() instanceof Region)) {
			return RegionEditPart.VISUAL_ID;
		}
		return UMLVisualIDRegistry.getNodeVisualID(containerView, domainElement);
	}

	protected IFigure getSizeOnDropFeedback() {
		if (sizeOnDropFeedback == null) {
			sizeOnDropFeedback = new RectangleFigure();
			FigureUtilities.makeGhostShape((Shape) sizeOnDropFeedback);
			((Shape) sizeOnDropFeedback).setLineStyle(Graphics.LINE_DASHDOT);
			sizeOnDropFeedback.setForegroundColor(ColorConstants.white);
			addFeedback(sizeOnDropFeedback);
		}
		return sizeOnDropFeedback;
	}

	/**
	 * {@inheritedDoc}
	 */
	@Override
	protected Command getSpecificDropCommand(DropObjectsRequest dropRequest, Element semanticElement, String nodeVISUALID, String linkVISUALID) {
		// Retrieve drop location
		Point location = dropRequest.getLocation().getCopy();
		if (linkVISUALID != null) {
			switch (linkVISUALID) {
			case TransitionEditPart.VISUAL_ID:
				return dropTransition(dropRequest, (Transition) semanticElement, linkVISUALID);
			}
		}
		if (nodeVISUALID != null) {
			// Switch test over nodeVISUALID
			switch (nodeVISUALID) {
			case StateMachineEditPart.VISUAL_ID:
				return dropStateMachine(dropRequest, location, (StateMachine) semanticElement, nodeVISUALID);
			case StateEditPartTN.VISUAL_ID:
				return dropStateEditPartTN(dropRequest, location, (State) semanticElement, nodeVISUALID);
			case StateEditPart.VISUAL_ID:
			case FinalStateEditPart.VISUAL_ID:
				return dropState(dropRequest, location, (State) semanticElement, nodeVISUALID);
			case RegionEditPart.VISUAL_ID:
				return dropRegion(dropRequest, (Region) semanticElement, nodeVISUALID);
			case PseudostateEntryPointEditPart.VISUAL_ID:
			case PseudostateExitPointEditPart.VISUAL_ID:
			case ConnectionPointReferenceEditPart.VISUAL_ID:
				return dropAffixedNode(dropRequest, semanticElement, nodeVISUALID);
			case PseudostateInitialEditPart.VISUAL_ID:
			case PseudostateTerminateEditPart.VISUAL_ID:
			case PseudostateChoiceEditPart.VISUAL_ID:
			case PseudostateDeepHistoryEditPart.VISUAL_ID:
			case PseudostateShallowHistoryEditPart.VISUAL_ID:
			case PseudostateForkEditPart.VISUAL_ID:
			case PseudostateJoinEditPart.VISUAL_ID:
			case PseudostateJunctionEditPart.VISUAL_ID:
				return dropPseudostate(dropRequest, semanticElement, nodeVISUALID);
			case ConstraintEditPart.VISUAL_ID:
				return dropConstraint(dropRequest, (Constraint) semanticElement, nodeVISUALID);
			}
		}
		return super.getSpecificDropCommand(dropRequest, semanticElement, nodeVISUALID, linkVISUALID);
	}

	/**
	 * Returns the command to drop the Constraint + the link to attach it to its contrainted elements
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param constraint
	 *            the dropped constraint
	 * @param nodeVISUALID
	 *            the node visual id
	 *
	 * @return the command
	 */
	protected Command dropConstraint(DropObjectsRequest dropRequest, Constraint constraint, String nodeVISUALID) {
		ICommand dropConstraintCommand = getDefaultDropNodeCommand(nodeVISUALID, dropRequest.getLocation(), constraint, dropRequest);
		if (constraint.getContext() != null) {
			ShowConstraintContextLink showConstraintContextLink = new ShowConstraintContextLink(getEditingDomain(), (GraphicalEditPart) getHost(), constraint);
			dropConstraintCommand = dropConstraintCommand.compose(showConstraintContextLink);
		}
		return GMFtoGEFCommandWrapper.wrap(dropConstraintCommand);
	}

	@Override
	public IElementType getUMLElementType(String elementID) {
		return UMLElementTypes.getElementType(elementID);
	}

	@Override
	public void showTargetFeedback(Request request) {
		if ((request instanceof ChangeBoundsRequest) && !REQ_RESIZE.equals(request.getType())) {
			fromOutline = false;
			ChangeBoundsRequest changeBoundsRequest = (ChangeBoundsRequest) request;
			for (Iterator<?> iter = changeBoundsRequest.getEditParts().iterator(); iter.hasNext();) {
				GraphicalEditPart element = (GraphicalEditPart) iter.next();
				if (element instanceof RegionEditPart) {
					CustomRegionEditPart regionEditPart = (CustomRegionEditPart) element;

					View compartment = (View) ((View) element.getModel()).eContainer();
					if (compartment.getChildren().size() == 1) {
						return;
					}

					CustomRegionDragTracker dragTracker = regionEditPart.getRegionDragTracker();
					RegionEditPart targetEP = dragTracker.getTargetRegionEditPart();
					if (targetEP != null) {
						RegionFigure targetFig = targetEP.getPrimaryShape();

						// make a local copy
						Rectangle targetFigBounds = targetFig.getBounds().getCopy();
						// transform the coordinates to absolute
						targetFig.translateToAbsolute(targetFigBounds);
						// retrieve mouse location
						Point mouseLocation = changeBoundsRequest.getLocation();

						// get the drop location, i.e. RIGHT, LEFT, TOP, BOTTOM
						dropLocation = Zone.getZoneFromLocationInRectangleWithAbsoluteCoordinates(mouseLocation, targetFigBounds);

						// perform corresponding change (scaling, translation)
						// on
						// targetFigBounds
						// and updates the graph node drop location property
						if (Zone.isTop(dropLocation)) {
							targetFigBounds.setSize(targetFigBounds.getSize().scale(1.0, 0.5));
						} else if (Zone.isLeft(dropLocation)) {
							targetFigBounds.setSize(targetFigBounds.getSize().scale(0.5, 1.0));
						} else if (Zone.isRight(dropLocation)) {
							targetFigBounds.setSize(targetFigBounds.getSize().scale(0.5, 1.0));
							targetFigBounds.translate(targetFigBounds.width, 0);
						} else if (Zone.isBottom(dropLocation)) {
							targetFigBounds.setSize(targetFigBounds.getSize().scale(1.0, 0.5));
							targetFigBounds.translate(0, targetFigBounds.height);
						}

						getSizeOnDropFeedback().setBounds(new PrecisionRectangle(targetFigBounds));
					}
				}
			}
		}
		if (request instanceof DropObjectsRequest) {
			fromOutline = true;
			DropObjectsRequest dropRequest = (DropObjectsRequest) request;
			for (Iterator<?> iter = dropRequest.getObjects().iterator(); iter.hasNext();) {
				EObject element = (EObject) iter.next();
				if ((element instanceof Region) && (getHost().getParent() instanceof RegionEditPart)) {
					// check whether the dropped region is already shown in the
					// state machine
					View compartment = null;
					if (getHost().getParent().getParent() instanceof StateMachineCompartmentEditPart) {
						compartment = (View) ((StateMachineCompartmentEditPart) getHost().getParent().getParent()).getModel();
					} else if (getHost().getParent().getParent() instanceof StateCompartmentEditPart) {
						compartment = (View) ((StateCompartmentEditPart) getHost().getParent().getParent()).getModel();
					}
					View alreadyShown = null;
					if (compartment != null) {
						Iterator<?> it = compartment.getChildren().iterator();
						while ((alreadyShown == null) && it.hasNext()) {
							Object next = it.next();
							if (next instanceof View) {
								View current = (View) next;
								if (current.getElement().equals(element)) {
									alreadyShown = current;
								}
							}
						}
						if (alreadyShown == null) {
							RegionFigure targetFig = ((RegionEditPart) getHost().getParent()).getPrimaryShape();

							// make a local copy
							Rectangle targetFigBounds = targetFig.getBounds().getCopy();
							// transform the coordinates to absolute
							targetFig.translateToAbsolute(targetFigBounds);
							// retrieve mouse location
							Point mouseLocation = dropRequest.getLocation().getCopy();

							// get the drop location, i.e. RIGHT, LEFT, TOP, BOTTOM
							dropLocation = Zone.getZoneFromLocationInRectangleWithAbsoluteCoordinates(mouseLocation, targetFigBounds);

							// perform corresponding change (scaling, translation)
							// on
							// targetFigBounds
							// and updates the graph node drop location property
							if (Zone.isTop(dropLocation)) {
								targetFigBounds.setSize(targetFigBounds.getSize().scale(1.0, 0.5));
							} else if (Zone.isLeft(dropLocation)) {
								targetFigBounds.setSize(targetFigBounds.getSize().scale(0.5, 1.0));
							} else if (Zone.isRight(dropLocation)) {
								targetFigBounds.setSize(targetFigBounds.getSize().scale(0.5, 1.0));
								targetFigBounds.translate(targetFigBounds.width, 0);
							} else if (Zone.isBottom(dropLocation)) {
								targetFigBounds.setSize(targetFigBounds.getSize().scale(1.0, 0.5));
								targetFigBounds.translate(0, targetFigBounds.height);
							}

							getSizeOnDropFeedback().setBounds(new PrecisionRectangle(targetFigBounds));
						}
					}
				}
			}
		}
	}
}
