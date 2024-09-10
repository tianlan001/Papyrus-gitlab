/**
 * Copyright (c) 2014, 2017 CEA LIST.
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
 *  Ansgar Radermacher, ansgar.radermacher@cea.fr, Bug 527291 - avoid unwanted region creation
 */
package org.eclipse.papyrus.uml.diagram.statemachine.custom.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.commands.SetPropertyCommand;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.SemanticAdapter;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.SideAffixedNodesCreationEditPolicy;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.commands.CustomFirstRegionInCompositeStateCreateElementCommand;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part.CustomStateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.helpers.Zone;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.RegionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.providers.UMLElementTypes;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Vertex;

public class CustomStateCreationEditPolicy extends SideAffixedNodesCreationEditPolicy {

	/**
	 * constants for changing the visibility attribute of a view
	 */
	public static final String NOTATION_VIEW_VISIBLE = "notation.View.visible"; //$NON-NLS-1$
	public static final String VISIBILITY = "Visibility"; //$NON-NLS-1$

	/**
	 * Default location for new vertex, if called from popup-bar
	 */
	public static final Rectangle defaultLocation = new Rectangle(20, 20, -1, -1);

	/**
	 * No specific ZONE
	 */
	public static final String dropLocation = Zone.NONE;

	/**
	 * Vertical offset for region compartment within composite state (cannot be calculated from host figure, since
	 * the region compartment has not been created when the position calculation is done.
	 */
	public static final int REGION_OFFSET = 20;

	@Override
	public Command getCommand(Request request) {
		if (understandsRequest(request)) {
			if (request instanceof CreateViewRequest) {
				// used by popup-bar assistant
				CreateViewRequest createViewRequest = (CreateViewRequest) request;
				for (ViewDescriptor vd : createViewRequest.getViewDescriptors()) {
					IElementType elementType = vd.getElementAdapter().getAdapter(IElementType.class);
					if (elementType != null) {
						Command cmd = getCustomCreateCommand(request, null, elementType, vd.getSemanticHint());
						if (cmd != null) {
							return cmd;
						}
					}
				}
			} else if (request instanceof CreateUnspecifiedTypeRequest) {
				// used by palette
				CreateUnspecifiedTypeRequest unspecReq = (CreateUnspecifiedTypeRequest) request;
				for (Object elementTypeObj : unspecReq.getElementTypes()) {
					if (elementTypeObj instanceof IHintedType) {
						IHintedType hintedType = (IHintedType) elementTypeObj;
						CreateRequest createRequest = unspecReq.getRequestForType(hintedType);
						Command cmd = getCustomCreateCommand(request, createRequest, hintedType, hintedType.getSemanticHint());
						if (cmd != null) {
							return cmd;
						}
					}
				}
			} else if (request instanceof ChangeBoundsRequest) {
				ChangeBoundsRequest changeBoundsRequest = (ChangeBoundsRequest) request;
				Point mouseLocation = changeBoundsRequest.getLocation();
				DropObjectsRequest dropRequest = new DropObjectsRequest();
				dropRequest.setLocation(mouseLocation);
				List<View> list = new ArrayList<>();
				for (Object epObj : changeBoundsRequest.getEditParts()) {
					if (epObj instanceof RegionEditPart) {
						EditPart ep = (EditPart) epObj;
						View regionToDrag = (View) ep.getModel();
						list.add(regionToDrag);
					}
				}
				dropRequest.setObjects(list);
				return getHost().getCommand(dropRequest);
			}
			return super.getCommand(request);
		}
		return null;
	}

	/**
	 * Create a vertex (sub-state) within the region that is retrieved from the passed adaptable during
	 * execution. This command is used in the context that the region has not been created yet, i.e. when
	 * the first addition of a vertex implies creating the region first. This requires the use of the
	 * adaptable and does not allow the use of the standard creation commands.
	 * This command is used only in composition with region creation in CustomStateCreationEditPolicy
	 */
	public static class CustomVertexCreateElementCommand extends AbstractTransactionalCommand {

		IAdaptable adaptable;

		CreateViewRequest.ViewDescriptor viewDescriptor;

		IElementType elementType;

		/**
		 * @since 3.0
		 */
		public CustomVertexCreateElementCommand(TransactionalEditingDomain domain, ViewDescriptor viewDescriptor, IElementType elementType, IAdaptable adaptable, String label) {
			super(domain, label, null);
			this.adaptable = adaptable;
			this.viewDescriptor = viewDescriptor;
			this.elementType = elementType;
			// make sure the return object is available even before executing/undoing/redoing
			setResult(CommandResult.newOKCommandResult(viewDescriptor));
		}

		/**
		 * @since 3.0
		 */
		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
			// adapt the view at execution time
			View regionView = adaptable.getAdapter(View.class);
			View compartment = (View) regionView.getChildren().get(0);

			CreateElementRequest createElementRequest = new CreateElementRequest(regionView.getElement(), elementType);
			ICommand createElementCommand = elementType.getEditCommand(createElementRequest);

			if (createElementCommand != null && createElementCommand.canExecute()) {
				createElementCommand.execute(monitor, info);
			}

			Vertex newVertex = (Vertex) createElementRequest.getNewElement();

			View view = ViewService.getInstance().createView(
					viewDescriptor.getViewKind(),
					new SemanticAdapter(newVertex, null),
					compartment,
					viewDescriptor.getSemanticHint(),
					viewDescriptor.getIndex(),
					viewDescriptor.isPersisted(),
					viewDescriptor.getPreferencesHint());
			Assert.isNotNull(view, "failed to create a view"); //$NON-NLS-1$
			viewDescriptor.setView(view);

			return CommandResult.newOKCommandResult(viewDescriptor);
		}
	}

	/**
	 * @since 3.0
	 */
	public Command getCustomCreateCommand(Request request, CreateRequest createReq, IElementType elementType, String semanticHint) {
		TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost()).getEditingDomain();
		CompositeTransactionalCommand cc = new CompositeTransactionalCommand(editingDomain, DiagramUIMessages.AddCommand_Label);

		// starting point is the state node on which mouse was moving
		View stateView = (View) getHost().getModel();
		View stateCompartmentView = CustomStateEditPart.getStateCompartmentView(stateView);

		View existingRegionView = null;
		// State already has a region
		if (!stateCompartmentView.getChildren().isEmpty()) {
			// treat specific case of popup assistant on state, but command is effectively for one of its regions
			if (stateCompartmentView.getChildren().size() == 1) {
				existingRegionView = (View) stateCompartmentView.getChildren().get(0);
			} else {
				// let super class handle it
				return null;
			}
		}

		if (semanticHint.equals(((IHintedType) UMLElementTypes.Region_Shape).getSemanticHint())) {

			// get and adaptable for it, to pass on to commands
			IAdaptable adaptableForStateCompartmentView = new SemanticAdapter(null, stateCompartmentView);

			// do the whole job
			CustomFirstRegionInCompositeStateCreateElementCommand createNewRegion = new CustomFirstRegionInCompositeStateCreateElementCommand(adaptableForStateCompartmentView, null, ((IGraphicalEditPart) getHost()).getDiagramPreferencesHint(),
					editingDomain, DiagramUIMessages.CreateCommand_Label, dropLocation);
			SetPropertyCommand showCompartment = new SetPropertyCommand(editingDomain, adaptableForStateCompartmentView, NOTATION_VIEW_VISIBLE, VISIBILITY, true);
			cc.compose(showCompartment);
			cc.compose(createNewRegion);
			return new ICommandProxy(cc.reduce());
		} else if (semanticHint.equals(((IHintedType) UMLElementTypes.Pseudostate_InitialShape).getSemanticHint())
				|| semanticHint.equals(((IHintedType) UMLElementTypes.Pseudostate_JoinShape).getSemanticHint())
				|| semanticHint.equals(((IHintedType) UMLElementTypes.Pseudostate_ForkShape).getSemanticHint())
				|| semanticHint.equals(((IHintedType) UMLElementTypes.Pseudostate_ChoiceShape).getSemanticHint())
				|| semanticHint.equals(((IHintedType) UMLElementTypes.Pseudostate_JunctionShape).getSemanticHint())
				|| semanticHint.equals(((IHintedType) UMLElementTypes.Pseudostate_ShallowHistoryShape).getSemanticHint())
				|| semanticHint.equals(((IHintedType) UMLElementTypes.Pseudostate_DeepHistoryShape).getSemanticHint())
				|| semanticHint.equals(((IHintedType) UMLElementTypes.Pseudostate_TerminateShape).getSemanticHint())
				|| semanticHint.equals(((IHintedType) UMLElementTypes.State_Shape).getSemanticHint())
				|| semanticHint.equals(((IHintedType) UMLElementTypes.FinalState_Shape).getSemanticHint())) {

			// get an adaptable for it, to pass on to commands
			IAdaptable adaptableForStateCompartmentView = new SemanticAdapter(null, stateCompartmentView);

			ViewDescriptor viewDescriptor;
			if (existingRegionView == null) {
				CustomFirstRegionInCompositeStateCreateElementCommand createNewRegion = new CustomFirstRegionInCompositeStateCreateElementCommand(adaptableForStateCompartmentView, null, ((IGraphicalEditPart) getHost()).getDiagramPreferencesHint(),
						editingDomain, DiagramUIMessages.CreateCommand_Label, dropLocation);
				EList<Region> regions = ((State) stateView.getElement()).getRegions();
				if (!regions.isEmpty()) {
					// the view does not contain any region, but the model has already at least one. Use the first
					// existing region.
					createNewRegion.useExistingRegion(regions.get(0));
				}

				SetPropertyCommand showCompartment = new SetPropertyCommand(editingDomain, adaptableForStateCompartmentView, NOTATION_VIEW_VISIBLE, VISIBILITY, true);

				viewDescriptor = new ViewDescriptor(
						(IAdaptable) createNewRegion.getCommandResult().getReturnValue(),
						Node.class,
						semanticHint,
						((IGraphicalEditPart) getHost()).getDiagramPreferencesHint());
				CustomVertexCreateElementCommand createVertex = new CustomVertexCreateElementCommand(editingDomain, viewDescriptor, elementType, (IAdaptable) createNewRegion.getCommandResult().getReturnValue(), DiagramUIMessages.CreateCommand_Label);

				cc.compose(showCompartment);
				cc.compose(createNewRegion);
				cc.compose(createVertex);
			} else {
				// region view exists, only create vertex within
				viewDescriptor = new ViewDescriptor(
						null, Node.class, semanticHint,
						((IGraphicalEditPart) getHost()).getDiagramPreferencesHint());
				IAdaptable adapter = new SemanticAdapter(null, existingRegionView);
				CustomVertexCreateElementCommand createVertex = new CustomVertexCreateElementCommand(editingDomain, viewDescriptor, elementType, adapter, DiagramUIMessages.CreateCommand_Label);
				cc.compose(createVertex);
			}

			if (request instanceof CreateRequest) {
				CreateRequest req = (CreateRequest) request;

				// Retrieve parent location
				Point parentLocation = getHostFigure().getBounds().getLocation().getCopy();

				// Compute relative creation location
				Point requestedLocation = req.getLocation().getCopy();
				// normally, shape would be relative to region (which has not been created yet). Since we
				// calculate relative to the hosting composite state, we use the REGION_OFFSET below to
				// take this into account.
				getHostFigure().translateToRelative(requestedLocation);
				Rectangle proposedBounds = new Rectangle(requestedLocation.x, requestedLocation.y - REGION_OFFSET, -1, -1);

				// Convert the calculated preferred bounds as relative to parent location
				Rectangle creationBounds;
				if (request instanceof CreateUnspecifiedTypeRequest) {
					creationBounds = proposedBounds.getTranslated(parentLocation.getNegated());
				} else {
					// call from pop-up bar (coordinates from request are not useful)
					creationBounds = defaultLocation;
				}
				cc.compose(new SetBoundsCommand(editingDomain, DiagramUIMessages.SetLocationCommand_Label_Resize, viewDescriptor, creationBounds));
			}
			return new ICommandProxy(cc.reduce());
		}
		return null;
	}
}
