/**
 * Copyright (c) 2014, 2016 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bugs 477384, 495087
 */
package org.eclipse.papyrus.uml.diagram.statemachine.custom.policies;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.adapter.SemanticAdapter;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.commands.CustomFirstRegionInCompositeStateCreateElementCommand;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.helpers.Zone;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.RegionCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.RegionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.providers.UMLElementTypes;

import com.google.common.collect.Iterables;

public class CustomStateCompartmentCreationEditPolicy extends CreationEditPolicy {
	IFigure sizeOnDropFeedback = null;
	String dropLocation = Zone.NONE;

	@Override
	public Command getCommand(Request request) {
		TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost()).getEditingDomain();
		CompositeTransactionalCommand cc = new CompositeTransactionalCommand(editingDomain, DiagramUIMessages.AddCommand_Label);
		if (understandsRequest(request)) {
			if (request instanceof CreateViewRequest) {
				// used by popup-bar assistant
				CreateViewRequest createViewRequest = (CreateViewRequest) request;
				for (ViewDescriptor vd : createViewRequest.getViewDescriptors()) {
					if (vd.getSemanticHint().equals(((IHintedType) UMLElementTypes.Region_Shape).getSemanticHint())) {
						// starting point is the state compartment on which mouse was moving
						View stateCompartmentView = (View) getHost().getModel();
						// get and adaptable for it, to pass on to commands
						IAdaptable adaptableForStateCompartmentView = new SemanticAdapter(null, stateCompartmentView);
						// do the whole job
						CustomFirstRegionInCompositeStateCreateElementCommand createNewRegion = new CustomFirstRegionInCompositeStateCreateElementCommand(adaptableForStateCompartmentView, null, ((IGraphicalEditPart) getHost()).getDiagramPreferencesHint(),
								editingDomain, DiagramUIMessages.CreateCommand_Label, dropLocation);
						cc.compose(createNewRegion);
						return new ICommandProxy(cc.reduce());
					}
				}
			} else if (request instanceof CreateUnspecifiedTypeRequest) {
				// used by palette
				CreateUnspecifiedTypeRequest unspecReq = (CreateUnspecifiedTypeRequest) request;
				for (Iterator<?> iter = unspecReq.getElementTypes().iterator(); iter.hasNext();) {
					IElementType elementType = (IElementType) iter.next();
					if (((IHintedType) elementType).getSemanticHint().equals(((IHintedType) UMLElementTypes.Region_Shape).getSemanticHint())) {
						// starting point is the state compartment on which mouse was moving
						View stateCompartmentView = (View) getHost().getModel();
						// get and adaptable for it, to pass on to commands
						IAdaptable adaptableForStateCompartmentView = new SemanticAdapter(null, stateCompartmentView);
						// do the whole job
						CustomFirstRegionInCompositeStateCreateElementCommand createNewRegion = new CustomFirstRegionInCompositeStateCreateElementCommand(adaptableForStateCompartmentView, null, ((IGraphicalEditPart) getHost()).getDiagramPreferencesHint(),
								editingDomain, DiagramUIMessages.CreateCommand_Label, dropLocation);
						cc.compose(createNewRegion);
						return new ICommandProxy(cc.reduce());
					}
				}
			} else if (request instanceof ChangeBoundsRequest) {
				ChangeBoundsRequest changeBoundsRequest = (ChangeBoundsRequest) request;
				Point mouseLocation = changeBoundsRequest.getLocation();
				DropObjectsRequest dropRequest = new DropObjectsRequest();
				dropRequest.setLocation(mouseLocation);
				List<View> list = new ArrayList<>();
				Iterator<?> it = changeBoundsRequest.getEditParts().iterator();
				while (it.hasNext()) {
					Object next = it.next();
					if (next instanceof EditPart) {
						EditPart ep = (EditPart) next;
						if (ep instanceof RegionEditPart) {
							View regionToDrag = (View) ep.getModel();
							list.add(regionToDrag);
						}
					}
				}
				dropRequest.setObjects(list);
				return getHost().getCommand(dropRequest);
			}
			return super.getCommand(request);
		}
		return null;
	}

	@Override
	protected Command getCreateCommand(CreateViewRequest request) {
		TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost()).getEditingDomain();
		CompositeTransactionalCommand cc = null;

		for (CreateViewRequest.ViewDescriptor descriptor : request.getViewDescriptors()) {
			if (((IHintedType) UMLElementTypes.Region_Shape).getSemanticHint().equals(descriptor.getSemanticHint())) {
				// Creating a region view in this compartment
				IAdaptable compartment = new SemanticAdapter(null, getHost().getModel());

				// Create the view with a reasonable initial layout
				CustomFirstRegionInCompositeStateCreateElementCommand command = new CustomFirstRegionInCompositeStateCreateElementCommand(
						compartment, descriptor.getElementAdapter(),
						((IGraphicalEditPart) getHost()).getDiagramPreferencesHint(),
						editingDomain, DiagramUIMessages.CreateCommand_Label, dropLocation);

				if (cc == null) {
					cc = new CompositeTransactionalCommand(editingDomain, DiagramUIMessages.AddCommand_Label);
					cc.compose(command);
				}
			}
		}

		// It's all-or-nothing: if anything besides regions were being created (which
		// would be an odd sort of a compound request), then only the regions will be
		return (cc != null)
				? new ICommandProxy(cc.reduce())
				: super.getCreateCommand(request);
	}

	@Override
	public EditPart getTargetEditPart(Request request) {
		if (request instanceof CreateUnspecifiedTypeRequest) {
			CreateUnspecifiedTypeRequest createUnspecifiedTypeRequest = (CreateUnspecifiedTypeRequest) request;
			if (understandsRequest(request)) {
				List<?> elementTypes = createUnspecifiedTypeRequest.getElementTypes();
				// Treat the case where only one element type is listed
				// Only take EntryPoint or ExitPoint element type into account
				if ((elementTypes.size() == 1) && (((IElementType) (elementTypes.get(0)) == UMLElementTypes.ConnectionPointReference_Shape))) {
					// If the target is a compartment replace by its parent edit part
					if ((getHost() instanceof ShapeCompartmentEditPart)) {
						return getHost().getParent();
					}
				}
			}
		} else if (request instanceof CreateViewRequest) {
			CreateViewRequest create = (CreateViewRequest) request;
			// If we are creating any regions, and there are already existing regions, redirect
			// to the last existing region
			for (CreateViewRequest.ViewDescriptor descriptor : create.getViewDescriptors()) {
				if (((IHintedType) UMLElementTypes.Region_Shape).getSemanticHint().equals(descriptor.getSemanticHint())) {
					RegionEditPart existingRegion = Iterables.getLast(Iterables.filter(getHost().getChildren(), RegionEditPart.class), null);
					if (existingRegion != null) {
						RegionCompartmentEditPart compartment = Iterables.getFirst(Iterables.filter(existingRegion.getChildren(), RegionCompartmentEditPart.class), null);
						if (compartment != null) {
							return compartment.getTargetEditPart(request);
						}
					}
				}
			}
		}
		return super.getTargetEditPart(request);
	}
}
