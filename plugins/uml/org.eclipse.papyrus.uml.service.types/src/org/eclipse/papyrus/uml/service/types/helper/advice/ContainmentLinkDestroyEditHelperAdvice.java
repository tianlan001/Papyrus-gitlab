/*****************************************************************************
 * Copyright (c) 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Pauline DEVILLE (CEA LIST) - pauline.deville@cea.fr - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.helper.advice;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.commands.MoveElementsCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientReferenceRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRequest;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.infra.services.edit.utils.RequestParameterConstants;
import org.eclipse.papyrus.infra.types.core.utils.ElementTypeRegistryUtils;
import org.eclipse.papyrus.uml.service.types.Activator;
import org.eclipse.papyrus.uml.service.types.element.UMLDIElementTypes;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrus.uml.service.types.utils.ElementUtil;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * This helper provides the command to remove and reorient a ContainmentLink
 *
 * @since 5.2
 */
public class ContainmentLinkDestroyEditHelperAdvice extends AbstractReferenceDeleteRelationshipEditHelperAdvice {

	/**
	 * @see org.eclipse.papyrus.uml.service.types.helper.advice.AbstractReferenceDeleteRelationshipEditHelperAdvice#checkSourceEdgeToFindConnectorToDestroy()
	 *
	 * @return
	 */
	@Override
	protected boolean checkSourceEdgeToFindConnectorToDestroy() {
		return true;
	}

	/**
	 * @see org.eclipse.papyrus.uml.service.types.helper.advice.AbstractReferenceDeleteRelationshipEditHelperAdvice#checkTargetEdgeToFindConnectorToDestroy()
	 *
	 * @return
	 */
	@Override
	protected boolean checkTargetEdgeToFindConnectorToDestroy() {
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.uml.service.types.helper.advice.AbstractReferenceDeleteRelationshipEditHelperAdvice#getFeatureElementTypeToEReferenceMap()
	 *
	 * @return
	 */
	@Override
	protected Map<String, EReference> getFeatureElementTypeToEReferenceMap() {
		return Collections.singletonMap(UMLElementTypes.ELEMENT_OWNEDELEMENT.getId(), UMLPackage.eINSTANCE.getElement_OwnedElement());
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterDestroyReferenceCommand(org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getAfterDestroyReferenceCommand(DestroyReferenceRequest request) {
		Object affectedView = request.getParameter(RequestParameterConstants.AFFECTED_VIEW);
		if (affectedView instanceof Connector && isViewElementTypeTypeOf((View) affectedView, UMLDIElementTypes.ELEMENT_CONTAINMENT_EDGE)) {
			return getReparentCommand(request.getReferencedObject(), (Connector) affectedView);
		}

		return super.getAfterDestroyReferenceCommand(request);
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterReorientReferenceRelationshipCommand(org.eclipse.gmf.runtime.emf.type.core.requests.ReorientReferenceRelationshipRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getAfterReorientReferenceRelationshipCommand(ReorientReferenceRelationshipRequest request) {
		Object graphicalEdge = request.getParameter(RequestParameterConstants.GRAPHICAL_RECONNECTED_EDGE);
		if (graphicalEdge instanceof Connector && isViewElementTypeTypeOf((View) graphicalEdge, UMLDIElementTypes.ELEMENT_CONTAINMENT_EDGE)) {
			if (ReorientRequest.REORIENT_TARGET == request.getDirection()) {
				return getReparentCommand(request.getOldRelationshipEnd(), (Connector) graphicalEdge);
			}
		}
		return super.getAfterReorientReferenceRelationshipCommand(request);
	}

	/**
	 * This method create the command to reparent the element to the graphical container of the containmentLink target
	 *
	 * @param elementToReparent
	 *            the lement to reparent
	 * @param containmentLink
	 *            the containmentLink used to determine the new container
	 * @return the command to reparent the element
	 */
	private ICommand getReparentCommand(EObject elementToReparent, Connector containmentLink) {
		EObject targetContainer = containmentLink.getTarget().eContainer();
		if (targetContainer instanceof View) {
			EObject newContainer = ((View) targetContainer).getElement();
			MoveRequest moveRequest = new MoveRequest(newContainer, elementToReparent);
			MoveElementsCommand command = new MoveElementsCommand(moveRequest);
			return command;
		}
		return null;
	}

	/**
	 * This method check if one elementType of the given view correspond to the expectedType
	 *
	 * @param view
	 * @param expectedType
	 * @return true if one elementType of the given view correspond to the expectedType, false otherwise
	 */
	private boolean isViewElementTypeTypeOf(View view, IHintedType expectedType) {
		boolean isTypeOf = false;
		IClientContext context;
		try {
			context = TypeContext.getContext(view);
			List<IElementType> elementTypes = ElementTypeRegistryUtils.getElementTypesBySemanticHint(view.getType(), context.getId());
			for (IElementType iElementType : elementTypes) {
				isTypeOf |= ElementUtil.isTypeOf(iElementType, expectedType);
			}
		} catch (ServiceException e) {
			Activator.log.error(e);
		}
		return isTypeOf;
	}

}
