/*****************************************************************************
 * Copyright (c) 2010, 2017 CEA LIST, ALL4TEC and others.
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *  Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - Bug 519621
 *  Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - Bug 519756
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.IdentityCommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.uml.service.types.command.MessageAsyncReorientCommand;
import org.eclipse.papyrus.uml.service.types.command.MessageCreateReorientCommand;
import org.eclipse.papyrus.uml.service.types.command.MessageDeleteReorientCommand;
import org.eclipse.papyrus.uml.service.types.command.MessageFoundReorientCommand;
import org.eclipse.papyrus.uml.service.types.command.MessageLostReorientCommand;
import org.eclipse.papyrus.uml.service.types.command.MessageReplyReorientCommand;
import org.eclipse.papyrus.uml.service.types.command.MessageSyncReorientCommand;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.papyrus.uml.service.types.utils.ElementUtil;
import org.eclipse.papyrus.uml.service.types.utils.MessageUtils;
import org.eclipse.papyrus.uml.tools.utils.ExecutionSpecificationUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Gate;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.MessageKind;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.OccurrenceSpecification;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * <pre>
 * 
 * This helper provides edit commands for UML {@link Message}.
 * 
 * </pre>
 */
public class MessageEditHelper extends ElementEditHelper {


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getCreateRelationshipCommand(CreateRelationshipRequest request) {

		EObject source = request.getSource();
		EObject target = request.getTarget();
		boolean noSourceOrTarget = (source == null || target == null);
		boolean noSourceAndTarget = (source == null && target == null);
		if (!noSourceAndTarget && !canCreate(source, target, request)) {
			// Abort creation.
			return UnexecutableCommand.INSTANCE;
		}
		if (noSourceOrTarget && !noSourceAndTarget) {
			// The request isn't complete yet. Return the identity command so
			// that the create relationship gesture is enabled.
			return IdentityCommand.INSTANCE;
		}
		return super.getCreateRelationshipCommand(request);
	}

	/**
	 * Test if a message can be created.
	 * 
	 * @param source
	 *            the source of the message
	 * @param target
	 *            the target of the message
	 * @return return true if message can be created
	 */
	private boolean canCreate(final EObject source, final EObject target, final CreateElementRequest request) {
		boolean create = true;
		IElementType elementType = request.getElementType();

		if (null != elementType) {
			// Case of not unique message for a Message End
			if (source instanceof MessageEnd) {
				MessageEnd msgEndSource = (MessageEnd) source;
				create &= (null == msgEndSource.getMessage());
			}

			if (target instanceof MessageEnd) {
				MessageEnd msgEndTarget = (MessageEnd) target;
				create &= (null == msgEndTarget.getMessage());
			}

			// Message Create case
			if (ElementUtil.isTypeOf(elementType, UMLElementTypes.COMPLETE_CREATE_MESSAGE)) {
				create &= canCreateMessageCreate(source, target);
			} else
			// Message Delete case
			if (ElementUtil.isTypeOf(elementType, UMLElementTypes.COMPLETE_DELETE_MESSAGE)) {
				create &= canCreateMessageDelete(source, target);
			} else
			// Message Lost case
			if (ElementUtil.isTypeOf(elementType, UMLElementTypes.LOST_ASYNCH_CALL)) {
				create &= canCreateMessageLost(source, target);
			} else
			// Message Lost case
			if (ElementUtil.isTypeOf(elementType, UMLElementTypes.FOUND_ASYNCH_CALL)) {
				create &= canCreateMessageFound(source, target);
			}
		}

		return create;

	}

	/**
	 * Test if a Message Create can be created.
	 * 
	 * @param source
	 *            the source of the message
	 * @param target
	 *            the target of the message
	 * @param request
	 *            the request
	 * @return return true if message can be created
	 */
	private boolean canCreateMessageCreate(final EObject source, final EObject target) {
		boolean create = true;

		// source and target can't be the same
		create = source != target;

		// check if target is not already created with another create message
		if (create && target instanceof Lifeline) {
			create = !((Lifeline) target).getCoveredBys().stream()
					.filter(MessageEnd.class::isInstance)
					.map(MessageEnd.class::cast)
					.filter(m -> null != m.getMessage()) // filter on receive event
					.filter(m -> null != m.getMessage().getReceiveEvent()) // filter on receive event
					.filter(m -> m.getMessage().getReceiveEvent().equals(m)) // filter on receive event
					.map(m -> m.getMessage())
					.anyMatch(m -> m.getMessageSort() == MessageSort.CREATE_MESSAGE_LITERAL);
		}

		return create;
	}

	/**
	 * Test if a Message Delete can be created.
	 * 
	 * @param source
	 *            the source of the message
	 * @param target
	 *            the target of the message
	 * @param request
	 *            the request
	 * @return return true if message can be created
	 */
	private boolean canCreateMessageDelete(final EObject source, final EObject target) {
		boolean create = true;

		// check if target is not already created with another create message
		if (create && target instanceof Lifeline) {
			create = !((Lifeline) target).getCoveredBys().stream()
					.filter(MessageEnd.class::isInstance)
					.map(MessageEnd.class::cast)
					.filter(m -> null != m.getMessage()) // filter on receive event
					.filter(m -> null != m.getMessage().getReceiveEvent()) // filter on receive event
					.filter(m -> m.getMessage().getReceiveEvent().equals(m)) // filter on receive event
					.map(m -> m.getMessage())
					.anyMatch(m -> m.getMessageSort() == MessageSort.DELETE_MESSAGE_LITERAL);
		}
		return create;
	}

	/**
	 * Test if a Message Lost can be created.
	 * 
	 * @param source
	 *            the source of the message
	 * @param target
	 *            the target of the message
	 * @param request
	 *            the request
	 * @return return true if message can be created
	 */
	private boolean canCreateMessageLost(final EObject source, final EObject target) {
		return (target instanceof Interaction || target == null) && source instanceof Lifeline;
	}

	/**
	 * Test if a Message Found can be created.
	 * 
	 * @param source
	 *            the source of the message
	 * @param target
	 *            the target of the message
	 * @param request
	 *            the request
	 * @return return true if message can be created
	 */
	private boolean canCreateMessageFound(final EObject source, final EObject target) {
		return source instanceof Interaction && (target instanceof Lifeline || target == null);
	}


	protected boolean isReadOnly(EObject eObject) {
		EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(eObject);
		boolean isReadOnly = (eObject.eResource() != null) && (editingDomain.isReadOnly(eObject.eResource()));
		return isReadOnly;
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getBeforeConfigureCommand(org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	public void configureRequest(IEditCommandRequest request) {

		if (request instanceof CreateRelationshipRequest) {
			CreateRelationshipRequest createRequest = (CreateRelationshipRequest) request;

			EObject source = createRequest.getSource();
			EObject target = createRequest.getTarget();

			// If the container is null or containmentFeature doesn't fit with container, try to find one that make sense
			if (createRequest.getContainer() == null || (!createRequest.getContainer().eClass().getEAllReferences().contains(createRequest.getContainmentFeature())) || createRequest.getContainmentFeature() == null) {
				// Propose a container.
				EObject proposedContainer = EMFCoreUtil.getLeastCommonContainer(Arrays.asList(new EObject[] { source, target }), UMLPackage.eINSTANCE.getPackage());

				// If no common container is found try source nearest package
				if (proposedContainer == null) {
					EObject sourcePackage = EMFCoreUtil.getContainer(source, UMLPackage.eINSTANCE.getInteraction());
					if (!isReadOnly(sourcePackage)) {
						proposedContainer = sourcePackage;
					}
				}

				// If no common container is found try target nearest package
				if (proposedContainer == null) {
					EObject targetPackage = EMFCoreUtil.getContainer(target, UMLPackage.eINSTANCE.getInteraction());
					if (!isReadOnly(targetPackage)) {
						proposedContainer = targetPackage;
					}
				}

				createRequest.setContainer(proposedContainer);
			}
			if (source instanceof InteractionFragment && target instanceof InteractionFragment) {
				Interaction sourceInteraction = MessageUtils.getContaining(source, Interaction.class);
				Interaction targetInteraction = MessageUtils.getContaining(target, Interaction.class);

				if (sourceInteraction == targetInteraction && sourceInteraction != null) {
					createRequest.setContainer(sourceInteraction);
					createRequest.setContainmentFeature(UMLPackage.eINSTANCE.getInteraction_Message());
				}
			}
		}
		super.configureRequest(request);
	}

	/**
	 * <pre>
	 * This method build a re-orient command depending on the kind of Message,
	 * and compose this basic command with another which updates the MessageEnd.
	 * </pre>
	 * 
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelper#getReorientRelationshipCommand(org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest)
	 * 
	 * @param req
	 *            the re-orient request
	 * @return the re-orient command
	 */
	@Override
	protected ICommand getReorientRelationshipCommand(ReorientRelationshipRequest req) {
		ICommand reorientCommand = null;

		// Build the initial re-orient command depending on the kind of Message
		Message msgToReorient = (Message) req.getRelationship();

		if (msgToReorient.getMessageKind() == MessageKind.LOST_LITERAL) {
			reorientCommand = new MessageLostReorientCommand(req);

		} else if (msgToReorient.getMessageKind() == MessageKind.FOUND_LITERAL) {
			reorientCommand = new MessageFoundReorientCommand(req);

		} else if (msgToReorient.getMessageSort() == MessageSort.SYNCH_CALL_LITERAL) {
			reorientCommand = new MessageSyncReorientCommand(req);

		} else if (msgToReorient.getMessageSort() == MessageSort.ASYNCH_CALL_LITERAL) {
			reorientCommand = new MessageAsyncReorientCommand(req);

		} else if (msgToReorient.getMessageSort() == MessageSort.ASYNCH_SIGNAL_LITERAL) {
			reorientCommand = new MessageAsyncReorientCommand(req);

		} else if (msgToReorient.getMessageSort() == MessageSort.REPLY_LITERAL) {
			reorientCommand = new MessageReplyReorientCommand(req);

		} else if (msgToReorient.getMessageSort() == MessageSort.CREATE_MESSAGE_LITERAL) {
			if (req.getDirection() == ReorientRelationshipRequest.REORIENT_TARGET) {
				EObject target = req.getNewRelationshipEnd();

				// Get the source
				EObject source = null;
				EObject relationship = req.getRelationship();
				if (relationship instanceof Message) {
					Message message = (Message) relationship;
					MessageEnd sendEvent = message.getSendEvent();
					if (sendEvent instanceof InteractionFragment) {
						InteractionFragment fragment = (InteractionFragment) sendEvent;
						EList<Lifeline> covereds = fragment.getCovereds();
						if (!covereds.isEmpty()) {
							source = covereds.get(0);
						}
					}
				}
				// test if we can create it
				if (canCreateMessageCreate(source, target)) {
					reorientCommand = new MessageCreateReorientCommand(req);
				} else {
					reorientCommand = UnexecutableCommand.INSTANCE;
				}
			} else {
				reorientCommand = new MessageCreateReorientCommand(req);
			}

		} else if (msgToReorient.getMessageSort() == MessageSort.DELETE_MESSAGE_LITERAL) {
			if (req.getDirection() == ReorientRelationshipRequest.REORIENT_TARGET) {
				EObject target = req.getNewRelationshipEnd();

				// Get the source
				EObject source = null;
				EObject relationship = req.getRelationship();
				if (relationship instanceof Message) {
					Message message = (Message) relationship;
					MessageEnd sendEvent = message.getSendEvent();
					if (sendEvent instanceof InteractionFragment) {
						InteractionFragment fragment = (InteractionFragment) sendEvent;
						EList<Lifeline> covereds = fragment.getCovereds();
						if (!covereds.isEmpty()) {
							source = covereds.get(0);
						}
					}
				}
				// test if we can create it
				if (canCreateMessageDelete(source, target)) {
					reorientCommand = new MessageDeleteReorientCommand(req);
				} else {
					reorientCommand = UnexecutableCommand.INSTANCE;
				}
			} else {
				reorientCommand = new MessageDeleteReorientCommand(req);
			}
		}

		// Build the command that will update the message end
		ICommand updateMessageEndCommand = null;

		MessageEnd msgEndToUpdate = null;
		if (req.getDirection() == ReorientRelationshipRequest.REORIENT_SOURCE) {
			msgEndToUpdate = msgToReorient.getSendEvent();
		} else if (req.getDirection() == ReorientRelationshipRequest.REORIENT_TARGET) {
			msgEndToUpdate = msgToReorient.getReceiveEvent();
		}

		updateMessageEndCommand = getUpdateMessageEndCommand(msgEndToUpdate, (Element) req.getOldRelationshipEnd(), (Element) req.getNewRelationshipEnd());

		// Compose both commands
		reorientCommand = CompositeCommand.compose(reorientCommand, updateMessageEndCommand);

		return reorientCommand.reduce();
	}

	/**
	 * <pre>
	 * This method provides a command to update the {@link MessageEnd} concerned by a re-orient request.
	 * </pre>
	 * 
	 * @param messageEnd
	 *            the {@link MessageEnd} concerned by the re-orient command
	 * @param oldElement
	 *            the old {@link Element} graphically attached to the {@link Message}
	 * @param newElement
	 *            the new {@link Element} graphically attached to the {@link Message}
	 * @return the update command
	 */
	private ICommand getUpdateMessageEndCommand(MessageEnd messageEnd, Element oldElement, Element newElement) {

		ICommand updateCommand = null;

		if (messageEnd instanceof MessageOccurrenceSpecification) {
			updateCommand = getUpdateMessageOccurenceSpecificationCommand((MessageOccurrenceSpecification) messageEnd, oldElement, newElement);
		} else if (messageEnd instanceof Gate) { // Gate case is not currently implemented
			updateCommand = getUpdateGateCommand((Gate) messageEnd, oldElement, newElement);
		}

		return updateCommand;
	}

	/**
	 * <pre>
	 * This method provides a command to update the {@link MessageOccurrenceSpecification} concerned by a re-orient request.
	 * </pre>
	 * 
	 * @param messageEnd
	 *            the {@link MessageOccurrenceSpecification} concerned by the re-orient command
	 * @param oldElement
	 *            the old {@link Element} graphically attached to the {@link Message}
	 * @param newElement
	 *            the new {@link Element} graphically attached to the {@link Message}
	 * @return the update command
	 */
	private ICommand getUpdateMessageOccurenceSpecificationCommand(MessageOccurrenceSpecification messageEnd, Element oldElement, Element newElement) {

		ICommand updateCommand = null;

		if (newElement instanceof Lifeline) {
			updateCommand = getUpdateOccurenceSpecificationCommand(messageEnd, (Lifeline) newElement);

		} else if (newElement instanceof ExecutionSpecification) {
			Lifeline lifeline = ExecutionSpecificationUtil.getExecutionSpecificationLifeline((ExecutionSpecification) newElement);
			if (lifeline != null) {
				updateCommand = getUpdateOccurenceSpecificationCommand(messageEnd, lifeline);
			}

		}

		return updateCommand;
	}

	/**
	 * <pre>
	 * This method provides a command to update the {@link OccurrenceSpecification} concerned by a re-orient request.
	 * </pre>
	 * 
	 * @param os
	 *            the {@link OccurrenceSpecification} concerned by the re-orient command
	 * @param newLifeline
	 *            the new {@link Lifeline} graphically covered by the {@link OccurrenceSpecification}
	 * @return the update command
	 */
	private ICommand getUpdateOccurenceSpecificationCommand(OccurrenceSpecification os, Lifeline newLifeline) {

		ICommand updateCommand = null;

		List<Lifeline> covereds = new ArrayList<Lifeline>();
		covereds.add(newLifeline);

		IElementEditService provider = ElementEditServiceUtils.getCommandProvider(os);
		if (provider != null) {

			// Create a command the set the OccurenceSpecification covered EReference with the newLifeline
			SetRequest setCoveredRequest = new SetRequest(os, UMLPackage.eINSTANCE.getInteractionFragment_Covered(), covereds);
			updateCommand = provider.getEditCommand(setCoveredRequest);

		}

		return updateCommand;
	}

	/**
	 * <pre>
	 * This method provides a command to update the {@link Gate} concerned by a re-orient request.
	 * 
	 * TODO : Not currently implemented.
	 * </pre>
	 * 
	 * @param gate
	 *            the {@link Gate} concerned by the re-orient command
	 * @param oldElement
	 *            the old {@link Element} graphically attached to the {@link Message}
	 * @param newElement
	 *            the new {@link Element} graphically attached to the {@link Message}
	 * @return the update command
	 */
	private ICommand getUpdateGateCommand(Gate gate, Element oldElement, Element newElement) {
		return null;
	}
}
