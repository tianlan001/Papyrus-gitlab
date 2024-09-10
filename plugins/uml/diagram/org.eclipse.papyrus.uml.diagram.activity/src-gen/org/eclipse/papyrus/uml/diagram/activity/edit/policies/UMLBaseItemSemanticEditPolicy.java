/*****************************************************************************
 * Copyright (c) 2009 Atos Origin.
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
 *   Atos Origin - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.edit.helpers.GeneratedEditHelperBase;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLDiagramEditorPlugin;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLVisualIDRegistry;
import org.eclipse.papyrus.uml.diagram.activity.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AbstractBaseItemSemanticEditPolicy;
import org.eclipse.papyrus.uml.tools.utils.ObjectFlowUtil;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.ControlFlow;
import org.eclipse.uml2.uml.DecisionNode;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExceptionHandler;
import org.eclipse.uml2.uml.ExecutableNode;
import org.eclipse.uml2.uml.FinalNode;
import org.eclipse.uml2.uml.ForkNode;
import org.eclipse.uml2.uml.InitialNode;
import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.JoinNode;
import org.eclipse.uml2.uml.MergeNode;
import org.eclipse.uml2.uml.ObjectFlow;
import org.eclipse.uml2.uml.ObjectNode;
import org.eclipse.uml2.uml.OutputPin;
import org.eclipse.uml2.uml.StructuredActivityNode;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @generated
 */
public class UMLBaseItemSemanticEditPolicy extends AbstractBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected UMLBaseItemSemanticEditPolicy(IElementType elementType) {
		super(elementType);
	}

	/**
	 * @generated
	 */
	@Override
	protected String getVisualIdFromView(View view) {
		return UMLVisualIDRegistry.getVisualID(view);
	}

	/**
	 * @generated NOT adding the possibility to globally disabling a request
	 */
	@Override
	protected Command getEditHelperCommand(IEditCommandRequest request, Command editPolicyCommand) {
		// disable the request if necessary
		if (requestIsDisabled(request)) {
			return null;
		}
		// return command for reorient, as this has already been computed through the edit helpers
		if (request instanceof ReorientRelationshipRequest) {
			return editPolicyCommand;
		}
		// generated code
		if (editPolicyCommand != null) {
			ICommand command = editPolicyCommand instanceof ICommandProxy
					? ((ICommandProxy) editPolicyCommand).getICommand()
					: new CommandProxy(editPolicyCommand);
			request.setParameter(GeneratedEditHelperBase.EDIT_POLICY_COMMAND, command);
		}
		IElementType requestContextElementType = getContextElementType(request);
		if (requestContextElementType != null) {
			request.setParameter(GeneratedEditHelperBase.CONTEXT_ELEMENT_TYPE, requestContextElementType);
			ICommand command = requestContextElementType.getEditCommand(request);
			request.setParameter(GeneratedEditHelperBase.EDIT_POLICY_COMMAND, null);
			request.setParameter(GeneratedEditHelperBase.CONTEXT_ELEMENT_TYPE, null);
			if (command != null) {
				if (!(command instanceof CompositeTransactionalCommand)) {
					command = new CompositeTransactionalCommand(getEditingDomain(), command.getLabel())
							.compose(command);
				}
				return new ICommandProxy(command);
			}
		}
		return editPolicyCommand;
	}

	/**
	 * Check whether the request should be disabled.
	 *
	 * @param request
	 *            the request to analyze
	 * @return true if the request must not succeed
	 * @generated NOT
	 */
	private boolean requestIsDisabled(IEditCommandRequest request) {
		if (request instanceof MoveRequest) {
			// prevent moving a constraint to another parent, since the representation would not be the same
			for (Object element : ((MoveRequest) request).getElementsToMove().keySet()) {
				if (element instanceof Constraint) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @generated
	 */
	@Override
	protected IElementType getContextElementType(IEditCommandRequest request) {
		IElementType requestContextElementType = UMLElementTypes.getElementType(getVisualID(request));
		return requestContextElementType != null ? requestContextElementType : getBaseElementType();
	}

	/**
	 * @generated
	 */
	@Override
	protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {

		IElementEditService commandService = ElementEditServiceUtils.getCommandProvider(((IGraphicalEditPart) getHost()).resolveSemanticElement());
		if (req.getElementType() != null) {
			commandService = ElementEditServiceUtils.getCommandProvider(req.getElementType(), req.getClientContext());
		}

		if (commandService == null) {
			return UnexecutableCommand.INSTANCE;
		}

		ICommand semanticCommand = commandService.getEditCommand(req);

		if ((semanticCommand != null) && (semanticCommand.canExecute())) {
			return getGEFWrapper(semanticCommand);
		}
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * @generated
	 */
	protected ICommand getSemanticCreationCommand(CreateElementRequest req) {
		IElementEditService commandService = ElementEditServiceUtils.getCommandProvider(req.getContainer());
		if (commandService == null) {
			return org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand.INSTANCE;
		}
		return commandService.getEditCommand(req);
	}

	/**
	 * @generated
	 */
	public static LinkConstraints getLinkConstraints() {
		LinkConstraints cached = UMLDiagramEditorPlugin.getInstance().getLinkConstraints();
		if (cached == null) {
			UMLDiagramEditorPlugin.getInstance().setLinkConstraints(cached = new LinkConstraints());
		}
		return cached;
	}

	/**
	 * @generated
	 */
	public static class LinkConstraints {

		/**
		 * @generated
		 */
		public LinkConstraints() {
			// use static method #getLinkConstraints() to access instance
		}

		/**
		 * @generated
		 */
		public boolean canCreateAction_LocalPreconditionEdge(Action source, Constraint target) {
			if (source != null) {
				if (source.getLocalPreconditions()
						.contains(target)) {
					return false;
				}
				if (source == target) {
					return false;
				}
			}
			return canExistAction_LocalPreconditionEdge(source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateAction_LocalPostconditionEdge(Action source, Constraint target) {
			if (source != null) {
				if (source.getLocalPostconditions()
						.contains(target)) {
					return false;
				}
				if (source == target) {
					return false;
				}
			}
			return canExistAction_LocalPostconditionEdge(source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateObjectFlow_Edge(Activity container, ActivityNode source, ActivityNode target) {
			return canExistObjectFlow_Edge(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateControlFlow_Edge(Activity container, ActivityNode source, ActivityNode target) {
			return canExistControlFlow_Edge(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateExceptionHandler_Edge(ExecutableNode container, ExecutableNode source,
				ObjectNode target) {
			return canExistExceptionHandler_Edge(container, null, source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateComment_AnnotatedElementEdge(Comment source, Element target) {
			if (source != null) {
				if (source.getAnnotatedElements()
						.contains(target)) {
					return false;
				}
			}
			return canExistComment_AnnotatedElementEdge(source, target);
		}

		/**
		 * @generated
		 */
		public boolean canCreateConstraint_ConstrainedElementEdge(Constraint source, Element target) {
			if (source != null) {
				if (source.getConstrainedElements()
						.contains(target)) {
					return false;
				}
			}
			return canExistConstraint_ConstrainedElementEdge(source, target);
		}

		/**
		 * @generated
		 */
		public boolean canExistAction_LocalPreconditionEdge(Action source, Constraint target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistAction_LocalPostconditionEdge(Action source, Constraint target) {
			return true;
		}

		/**
		 * modify the generation to call explicitly custom helper
		 *
		 * @generated
		 */
		public boolean canExistObjectFlow_Edge(Activity container, ObjectFlow linkInstance, ActivityNode source,
				ActivityNode target) {
			try {
				if (!ObjectFlowUtil.canExistObjectFlow(container, linkInstance, source, target)) {
					return false;
				}
				return true;
			} catch (Exception e) {
				UMLDiagramEditorPlugin.getInstance().logError("Link constraint evaluation error", e); //$NON-NLS-1$
				return false;
			}
		}

		/**
		 * @generated
		 */
		public boolean canExistControlFlow_Edge(Activity container, ControlFlow linkInstance, ActivityNode source,
				ActivityNode target) {
			try {
				if (source instanceof ObjectNode) {
					if (!((ObjectNode) source).isControlType()) {
						// rule validateControlFlow_validateObjectNodes
						return false;
					}
				}
				if (source instanceof InputPin) {
					// rule validateInputPin_validateOutgoingEdgesStructuredOnly
					if (source.getOwner() instanceof StructuredActivityNode) {
						if (target != null && !source.getOwner().equals(target.getInStructuredNode())) {
							return false;
						}
					} else {
						return false;
					}
				}
				if (source instanceof FinalNode) {
					// rule validateFinalNode_validateNoOutgoingEdges
					return false;
				}
				if (source instanceof JoinNode) {
					// rule validateJoinNode_validateOneOutgoingEdge
					if (!source.getOutgoings().isEmpty()) {
						return false;
					}
					// rule validateJoinNode_validateIncomingObjectFlow
					ActivityEdge incomingObjectFlow = source.getIncoming(null, true, UMLPackage.eINSTANCE.getObjectFlow());
					if (incomingObjectFlow != null) {
						// the outgoing edge must be an ObjectFlow
						return false;
					}
				}
				if (source instanceof ForkNode) {
					// rule validateForkNode_validateEdges on source Fork node
					ActivityEdge outgoingObjectFlow = source.getOutgoing(null, true, UMLPackage.eINSTANCE.getObjectFlow());
					ActivityEdge incomingObjectFlow = source.getIncoming(null, true, UMLPackage.eINSTANCE.getObjectFlow());
					if (outgoingObjectFlow != null || incomingObjectFlow != null) {
						// there is an ObjectFlow which means there must be no ControlFlow
						return false;
					}
				}
				if (source instanceof MergeNode) {
					// rule validateMergeNode_validateOneOutgoingEdge
					if (!source.getOutgoings().isEmpty()) {
						return false;
					}
					// rule validateMergeNode_validateEdges on source Merge node
					ActivityEdge outgoingObjectFlow = source.getOutgoing(null, true, UMLPackage.eINSTANCE.getObjectFlow());
					ActivityEdge incomingObjectFlow = source.getIncoming(null, true, UMLPackage.eINSTANCE.getObjectFlow());
					if (outgoingObjectFlow != null || incomingObjectFlow != null) {
						// there is an ObjectFlow which means there must be no ControlFlow
						return false;
					}
				}
				if (source instanceof DecisionNode) {
					// rule validateDecisionNode_validateEdges on source Decision node
					ActivityEdge outgoingObjectFlow = source.getOutgoing(null, true, UMLPackage.eINSTANCE.getObjectFlow());
					int incomingObjectFlowNumber = 0;
					for (ActivityEdge incomingEdge : source.getIncomings()) {
						if (incomingEdge instanceof ObjectFlow) {
							incomingObjectFlowNumber++;
						}
					}
					if (outgoingObjectFlow != null || incomingObjectFlowNumber > 1) {
						// there is an ObjectFlow (not intended for decisionInputFlow) which means there must be no ControlFlow
						return false;
					}
				}
				if (target instanceof ObjectNode) {
					if (!((ObjectNode) target).isControlType()) {
						// rule validateControlFlow_validateObjectNodes
						return false;
					}
				}
				if (target instanceof OutputPin) {
					// rule validateOutputPin_validateIncomingEdgesStructuredOnly
					if (target.getOwner() instanceof StructuredActivityNode) {
						if (source != null && !target.getOwner().equals(source.getInStructuredNode())) {
							return false;
						}
					} else {
						return false;
					}
				}
				if (target instanceof InitialNode) {
					// rule validateInitialNode_validateNoIncomingEdges
					return false;
				}
				if (target instanceof ForkNode) {
					// rule validateForkNode_validateOneIncomingEdge
					if (!target.getIncomings().isEmpty()) {
						return false;
					}
					// rule validateForkNode_validateEdges on target Fork node
					ActivityEdge outgoingObjectFlow = target.getOutgoing(null, true, UMLPackage.eINSTANCE.getObjectFlow());
					ActivityEdge incomingObjectFlow = target.getIncoming(null, true, UMLPackage.eINSTANCE.getObjectFlow());
					if (outgoingObjectFlow != null || incomingObjectFlow != null) {
						// there is an ObjectFlow which means there must be no ControlFlow
						return false;
					}
				}
				if (target instanceof MergeNode) {
					// rule validateMergeNode_validateEdges on target Merge node
					ActivityEdge outgoingObjectFlow = target.getOutgoing(null, true, UMLPackage.eINSTANCE.getObjectFlow());
					ActivityEdge incomingObjectFlow = target.getIncoming(null, true, UMLPackage.eINSTANCE.getObjectFlow());
					if (outgoingObjectFlow != null || incomingObjectFlow != null) {
						// there is an ObjectFlow which means there must be no ControlFlow
						return false;
					}
				}
				if (target instanceof DecisionNode) {
					// rule validateDecisionNode_validateIncomingOutgoingEdges
					if (target.getIncomings().size() >= 2) {
						// no more than two incoming edges
						return false;
					}
					// rule validateDecisionNode_validateEdges on target Decision node
					ActivityEdge outgoingObjectFlow = target.getOutgoing(null, true, UMLPackage.eINSTANCE.getObjectFlow());
					ActivityEdge incomingObjectFlow = null;
					for (ActivityEdge incomingEdge : target.getIncomings()) {
						// filter the decision flow
						if (incomingEdge instanceof ObjectFlow && incomingEdge != ((DecisionNode) target).getDecisionInputFlow()) {
							incomingObjectFlow = incomingEdge;
						}
					}
					if (outgoingObjectFlow != null || incomingObjectFlow != null) {
						// there is an ObjectFlow which means there must be no ControlFlow
						return false;
					}
				}
				return true;
			} catch (Exception e) {
				UMLDiagramEditorPlugin.getInstance().logError("Link constraint evaluation error", e); //$NON-NLS-1$
				return false;
			}
		}

		/**
		 * @generated
		 */
		public boolean canExistExceptionHandler_Edge(ExecutableNode container, ExceptionHandler linkInstance,
				ExecutableNode source, ObjectNode target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistComment_AnnotatedElementEdge(Comment source, Element target) {
			return true;
		}

		/**
		 * @generated
		 */
		public boolean canExistConstraint_ConstrainedElementEdge(Constraint source, Element target) {
			return true;
		}
	}
}
