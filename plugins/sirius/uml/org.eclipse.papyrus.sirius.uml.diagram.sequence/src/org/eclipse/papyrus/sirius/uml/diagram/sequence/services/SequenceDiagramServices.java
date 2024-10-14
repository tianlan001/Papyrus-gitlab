/*****************************************************************************
 * Copyright (c) 2024 CEA LIST.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.sequence.services;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.CommonDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.Activator;
import org.eclipse.sirius.diagram.sequence.ordering.EventEnd;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.ActionExecutionSpecification;
import org.eclipse.uml2.uml.BehaviorExecutionSpecification;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Services for the "Sequence" diagram.
 * 
 * @author <a href="mailto:gwendal.daniel@obeosoft.com>Gwendal Daniel</a>
 */
public class SequenceDiagramServices {

	private final static String CREATE_ERROR_NO_PARENT = "Unable to create a {0} with no parent"; //$NON-NLS-1$

	/**
	 * Creates a new semantic {@link Lifeline}, initializes and creates a view.
	 * <p>
	 * The new element is placed after (i.e. on the right) of the provided {@code predecessor}. The element is placed first if {@code predecessor} is {@code null}.
	 * 
	 * @param parent
	 *            the semantic parent
	 * @param parentView
	 *            the view of the semantic parent
	 * @param predecessor
	 *            the element preceding the lifeline to create (i.e. on the left)
	 * @return a new instance or {@code null} if the creation failed
	 */
	public EObject createLifeline(Element parent, DSemanticDecorator parentView, EObject predecessor) {
		EObject result = null;
		if (parent == null) {
			Activator.log.warn(MessageFormat.format(CREATE_ERROR_NO_PARENT, UMLPackage.eINSTANCE.getLifeline().getName()));
		} else {
			CommonDiagramServices commonDiagramServices = new CommonDiagramServices();
			result = commonDiagramServices.createElement(parent, UMLPackage.eINSTANCE.getLifeline().getName(), UMLPackage.eINSTANCE.getInteraction_Lifeline().getName(), parentView);
			SequenceDiagramReorderServices reorderServices = new SequenceDiagramReorderServices();
			reorderServices.reorderLifeline(parent, result, predecessor);
		}
		return result;
	}

	/**
	 * Creates a new semantic <i>synchronous</i> {@link Message}, initializes and creates a view.
	 * <p>
	 * The new message connects {@code source} and {@code target}, and is placed after (i.e. below) the provided {@startingEndPredecessor} and {@code finishingEndPredecessor}.
	 * 
	 * @param parent
	 *            the semantic parent
	 * @param source
	 *            the source element of the message
	 * @param target
	 *            the target element of the message
	 * @param startingEndPredecessor
	 *            the {@link EventEnd} preceding the starting end of the message
	 * @param finishingEndPredecessor
	 *            the {@link EventEnd} preceding the finishing end of the message
	 * @param parentView
	 *            the view of the semantic parent
	 * @return a new instance or {@code null} if the creation failed
	 */
	public EObject createSynchronousMessage(Element parent, Element source, Element target, EObject startingEndPredecessor, EObject finishingEndPredecessor, DSemanticDecorator parentView) {
		return this.createMessage(parent, MessageType.COMPLETE_SYNCH_CALL, source, target, startingEndPredecessor, finishingEndPredecessor, parentView);
	}

	/**
	 * Creates a new semantic <i>asynchronous</i> {@link Message}, initializes and creates a view.
	 * <p>
	 * The new message connects {@code source} and {@code target}, and is placed after (i.e. below) the provided {@code startingEndPredecessor} and {@code finishingEndPredecessor}.
	 * 
	 * @param parent
	 *            the semantic parent
	 * @param source
	 *            the source element of the message
	 * @param target
	 *            the target element of the message
	 * @param startingEndPredecessor
	 *            the {@link EventEnd} preceding the starting end of the message
	 * @param finishingEndPredecessor
	 *            the {@link EventEnd} preceding the finishing end of the message
	 * @param parentView
	 *            the view of the semantic parent
	 * @return a new instance or {@code null} if the creation failed
	 */
	public EObject createAsynchronousMessage(Element parent, Element source, Element target, EObject startingEndPredecessor, EObject finishingEndPredecessor, DSemanticDecorator parentView) {
		return this.createMessage(parent, MessageType.COMPLETE_ASYNCH_CALL, source, target, startingEndPredecessor, finishingEndPredecessor, parentView);
	}

	/**
	 * Creates a new semantic <i>reply</i> {@link Message}, initializes and creates a view.
	 * <p>
	 * The new message connects {@code source} and {@code target}, and is placed after (i.e. below) the provided {@code startingEndPredecessor} and {@code finishingEndPredecessor}.
	 * 
	 * @param parent
	 *            the semantic parent
	 * @param source
	 *            the source element of the message
	 * @param target
	 *            the target element of the message
	 * @param startingEndPredecessor
	 *            the {@link EventEnd} preceding the starting end of the message
	 * @param finishingEndPredecessor
	 *            the {@link EventEnd} preceding the finishing end of the message
	 * @param parentView
	 *            the view of the semantic parent
	 * @return a new instance or {@code null} if the creation failed
	 */
	public EObject createReplyMessage(Element parent, Element source, Element target, EObject startingEndPredecessor, EObject finishingEndPredecessor, DSemanticDecorator parentView) {
		return this.createMessage(parent, MessageType.COMPLETE_REPLY, source, target, startingEndPredecessor, finishingEndPredecessor, parentView);
	}

	private EObject createMessage(Element parent, MessageType type, Element source, Element target, EObject startingEndPredecessor, EObject finishingEndPredecessor, DSemanticDecorator parentView) {
		EObject result = null;
		if (parent == null) {
			Activator.log.warn(MessageFormat.format(CREATE_ERROR_NO_PARENT, UMLPackage.eINSTANCE.getMessage().getName()));
		} else {
			CommonDiagramServices commonDiagramServices = new CommonDiagramServices();
			result = commonDiagramServices.createElement(parent, UMLPackage.eINSTANCE.getMessage().getName(), UMLPackage.eINSTANCE.getInteraction_Message().getName(), parentView);
			Message message = (Message) result;
			if (MessageType.COMPLETE_SYNCH_CALL.equals(type)) {
				message.setMessageSort(MessageSort.SYNCH_CALL_LITERAL);
				createSendEvent(message, source, parentView);
				createReceiveEvent(message, target, parentView);
			} else if (MessageType.COMPLETE_REPLY.equals(type)) {
				message.setMessageSort(MessageSort.REPLY_LITERAL);
				createSendEvent(message, source, parentView);
				createReceiveEvent(message, target, parentView);
			} else if (MessageType.COMPLETE_ASYNCH_CALL.equals(type)) {
				message.setMessageSort(MessageSort.ASYNCH_CALL_LITERAL);
				createSendEvent(message, source, parentView);
				createReceiveEvent(message, target, parentView);
			}
			SequenceDiagramReorderServices reorderServices = new SequenceDiagramReorderServices();
			EObject startingEndPredecessorSemanticElement = getSemanticEnd((EventEnd) startingEndPredecessor);
			EObject finishingEndPredecessorSemanticElement = getSemanticEnd((EventEnd) finishingEndPredecessor);
			reorderServices.reorderMessage(message, startingEndPredecessorSemanticElement, finishingEndPredecessorSemanticElement);
		}
		return result;
	}

	private void createSendEvent(Message message, Element source, DSemanticDecorator parentView) {
		if (source instanceof Lifeline || source instanceof ExecutionSpecification) {
			MessageOccurrenceSpecification sendEvent = UMLFactory.eINSTANCE.createMessageOccurrenceSpecification();
			message.getInteraction().getFragments().add(sendEvent);
			sendEvent.setName(message.getName() + "SendEvent"); //$NON-NLS-1$
			sendEvent.setMessage(message);
			message.setSendEvent(sendEvent);
			if (source instanceof Lifeline) {
				sendEvent.setCovered((Lifeline) source);
			} else if (source instanceof ExecutionSpecification) {
				sendEvent.setCovered(((ExecutionSpecification) source).getCovereds().get(0));
			}
		}
	}

	private void createReceiveEvent(Message message, Element target, DSemanticDecorator parentView) {
		if (target instanceof Lifeline || target instanceof ExecutionSpecification) {
			MessageOccurrenceSpecification receiveEvent = UMLFactory.eINSTANCE
					.createMessageOccurrenceSpecification();
			message.getInteraction().getFragments().add(receiveEvent);
			receiveEvent.setName(message.getName() + "ReceiveEvent"); //$NON-NLS-1$
			receiveEvent.setMessage(message);
			message.setReceiveEvent(receiveEvent);
			if (target instanceof Lifeline) {
				receiveEvent.setCovered((Lifeline) target);
			} else if (target instanceof ExecutionSpecification) {
				receiveEvent.setCovered(((ExecutionSpecification) target).getCovereds().get(0));
			}
		}
	}

	/**
	 * Creates a new semantic {@link ActionExecutionSpecification}, initialize and create a view.
	 * <p>
	 * The new element is initialized with a <i>start</i> and <i>finish</i> {@link ExecutionOccurrenceSpecification}. See {@link ExecutionSpecification#getStart()} and {@link ExecutionSpecification#getFinish()}. The element is placed after the predecessor
	 * represented by {@code startingEndPredecessor} and {@code finishingEndPredecessor}.
	 * 
	 * 
	 * @param parent
	 *            the semantic element represented by the graphical parent
	 * @param startingEndPredecessor
	 *            the starting end of the created element's predecessor
	 * @param finishingEndPredecessor
	 *            the finishing end of the created element's predecessor
	 * @param parentView
	 *            the graphical container of the created element
	 * @return a new instance or {@code null} if the creation failed
	 */
	public EObject createActionExecutionSpecification(Element parent, EObject startingEndPredecessor, EObject finishingEndPredecessor, DSemanticDecorator parentView) {
		// Non-null, non-EventEnd starting and finishing end predecessors aren't supported by this method.
		if ((startingEndPredecessor != null && !(startingEndPredecessor instanceof EventEnd))
				|| (finishingEndPredecessor != null && !(finishingEndPredecessor instanceof EventEnd))) {
			return null;
		}
		ExecutionSpecification result = null;
		if (parent == null) {
			Activator.log.warn(MessageFormat.format(CREATE_ERROR_NO_PARENT, UMLPackage.eINSTANCE.getActionExecutionSpecification().getName()));
		} else {
			Element container = null;
			Lifeline lifeline = null;
			if (parent instanceof Lifeline) {
				lifeline = (Lifeline) parent;
				container = lifeline.getInteraction();
			}

			CommonDiagramServices commonDiagramServices = new CommonDiagramServices();
			result = (ExecutionSpecification) commonDiagramServices.createElement(container, UMLPackage.eINSTANCE.getActionExecutionSpecification().getName(), UMLPackage.eINSTANCE.getInteraction_Fragment().getName(), parentView);

			// No need to create the ExecutionOccurrenceSpecifications here, they are created by CommonDiagramServices for ActionExecutionSpecifications.
			result.getCovereds().add(lifeline);
			result.getStart().getCovereds().add(lifeline);
			result.getFinish().getCovereds().add(lifeline);
			SequenceDiagramReorderServices reorderServices = new SequenceDiagramReorderServices();
			EObject semanticStartingEndPredecessor = getSemanticEnd((EventEnd) startingEndPredecessor);
			EObject semanticFinishingEndPredecessor = getSemanticEnd((EventEnd) finishingEndPredecessor);
			reorderServices.reorderFragment(result, semanticStartingEndPredecessor, semanticFinishingEndPredecessor);
		}
		return result;
	}

	/**
	 * Creates a new semantic {@link BehaviorExecutionSpecification}, initialize and create a view.
	 * <p>
	 * The new element is initialized with a <i>start</i> and <i>finish</i> {@link ExecutionOccurrenceSpecification}. See {@link ExecutionSpecification#getStart()} and {@link ExecutionSpecification#getFinish()}. The element is placed after the precedessor
	 * represented by {@code startingEndPredecessor} and {@code finishingEndPredecessor}.
	 * 
	 * @param parent
	 *            the semantic element represented by the graphical parent
	 * @param startingEndPredecessor
	 *            the starting end of the created element's predecessor
	 * @param finishingEndPredecessor
	 *            the finishing end of the created element's predecessor
	 * @param parentView
	 *            the graphical container of the created element
	 * @return a new instance or {@code null} if the creation failed
	 */
	public EObject createBehaviorExecutionSpecification(Element parent, EObject startingEndPredecessor, EObject finishingEndPredecessor, DSemanticDecorator parentView) {
		// Non-null, non-EventEnd starting and finishing end predecessors aren't supported by this method.
		if ((startingEndPredecessor != null && !(startingEndPredecessor instanceof EventEnd))
				|| (finishingEndPredecessor != null && !(finishingEndPredecessor instanceof EventEnd))) {
			return null;
		}
		ExecutionSpecification result = null;
		if (parent == null) {
			Activator.log.warn(MessageFormat.format(CREATE_ERROR_NO_PARENT, UMLPackage.eINSTANCE.getBehaviorExecutionSpecification().getName()));
		} else {
			Element container = null;
			Lifeline lifeline = null;
			if (parent instanceof Lifeline) {
				lifeline = (Lifeline) parent;
				container = lifeline.getInteraction();
			}
			CommonDiagramServices commonDiagramServices = new CommonDiagramServices();
			result = (ExecutionSpecification) commonDiagramServices.createElement(container, UMLPackage.eINSTANCE.getBehaviorExecutionSpecification().getName(), UMLPackage.eINSTANCE.getInteraction_Fragment().getName(), parentView);

			// Need to create the ExecutionOccurrenceSpecifications here, they aren't created by CommonDiagramServices for BehaviorExecutionSpecifications.
			ExecutionOccurrenceSpecification start = UMLFactory.eINSTANCE.createExecutionOccurrenceSpecification();
			((Interaction) result.getOwner()).getFragments().add(start);
			start.setName(result.getName() + "Start"); //$NON-NLS-1$
			start.setExecution(result);
			result.setStart(start);
			ExecutionOccurrenceSpecification finish = UMLFactory.eINSTANCE.createExecutionOccurrenceSpecification();
			((Interaction) result.getOwner()).getFragments().add(finish);
			finish.setName(result.getName() + "Finish"); //$NON-NLS-1$
			finish.setExecution(result);
			result.setFinish(finish);

			result.getCovereds().add(lifeline);
			result.getStart().getCovereds().add(lifeline);
			result.getFinish().getCovereds().add(lifeline);
			SequenceDiagramReorderServices reorderServices = new SequenceDiagramReorderServices();
			EObject semanticStartingEndPredecessor = getSemanticEnd((EventEnd) startingEndPredecessor);
			EObject semanticFinishingEndPredecessor = getSemanticEnd((EventEnd) finishingEndPredecessor);
			reorderServices.reorderFragment(result, semanticStartingEndPredecessor, semanticFinishingEndPredecessor);
		}
		return result;
	}

	/**
	 * Moves {@code fragment} between {@code startingEndPredecessor} and {@code finishingEndPredecessor}.
	 * <p>
	 * This method operates at the <b>graphical<b> level, see {@link SequenceDiagramReorderServices#reorderFragment(InteractionFragment, EObject, EObject)} to perform a reorder at the semantic level.
	 * 
	 * @param fragment
	 *            the fragment to move
	 * @param startingEndPredecessor
	 *            the predecessor of the fragment's starting end
	 * @param finishingEndPredecessor
	 *            the predecessor of the fragment's finishing end
	 * 
	 * @see SequenceDiagramReorderServices#reorderFragment(InteractionFragment, EObject, EObject)
	 * @see #graphicalReorderMessage(Message, EventEnd, EventEnd)
	 */
	public void graphicalReorderFragment(InteractionFragment fragment, EObject startingEndPredecessor,
			EObject finishingEndPredecessor) {
		// Non-null, non-EventEnd starting and finishing end predecessors aren't supported by this method.
		if ((startingEndPredecessor != null && !(startingEndPredecessor instanceof EventEnd))
				|| (finishingEndPredecessor != null && !(finishingEndPredecessor instanceof EventEnd))) {
			return;
		}
		final EObject semanticStartingEndPredecessor = Optional.ofNullable((EventEnd) startingEndPredecessor).map(EventEnd::getSemanticEnd).orElse(null);
		final EObject semanticFinishingEndPredecessor = Optional.ofNullable((EventEnd) finishingEndPredecessor).map(EventEnd::getSemanticEnd).orElse(null);
		SequenceDiagramReorderServices reorderServices = new SequenceDiagramReorderServices();
		reorderServices.reorderFragment(fragment, semanticStartingEndPredecessor, semanticFinishingEndPredecessor);
	}

	/**
	 * Moves {@code message} between {@code startingEndPredecessor} and {@code finishingEndPredecessor}.
	 * <p>
	 * This method operates at the <b>graphical</b> level, see {@link SequenceDiagramReorderServices#reorderMessage(Message, EObject, EObject)} to perform a reorder at the semantic level.
	 * 
	 * @param message
	 *            the message to move
	 * @param startingEndPredecessor
	 *            the predecessor of the message's starting end
	 * @param finishingEndPredecessor
	 *            the predecessor of the fragment's finishing end
	 * 
	 * @see SequenceDiagramReorderServices#reorderMessage(Message, EObject, EObject)
	 * @see #graphicalReorderFragment(InteractionFragment, EventEnd, EventEnd)
	 */
	public void graphicalReorderMessage(Message message, EObject startingEndPredecessor, EObject finishingEndPredecessor) {
		// Non-null, non-EventEnd starting and finishing end predecessors aren't supported by this method.
		if ((startingEndPredecessor != null && !(startingEndPredecessor instanceof EventEnd))
				|| (finishingEndPredecessor != null && !(finishingEndPredecessor instanceof EventEnd))) {
			return;
		}
		final EObject semanticStartingEndPredecessor = Optional.ofNullable((EventEnd) startingEndPredecessor).map(EventEnd::getSemanticEnd).orElse(null);
		final EObject semanticFinishingEndPredecessor = Optional.ofNullable((EventEnd) finishingEndPredecessor).map(EventEnd::getSemanticEnd).orElse(null);
		SequenceDiagramReorderServices reorderServices = new SequenceDiagramReorderServices();
		reorderServices.reorderMessage(message, semanticStartingEndPredecessor, semanticFinishingEndPredecessor);
	}

	/**
	 * Returns the {@link ExecutionSpecification}s covering the provided {@code lifeline}
	 * 
	 * @param lifeline
	 *            the lifeline in which we are looking for {@link ExecutionSpecification}
	 * @return the list of {@link ExecutionSpecification}
	 */
	public Collection<ExecutionSpecification> getExecutionSpecificationCandidates(Lifeline lifeline) {
		Interaction interaction = lifeline.getInteraction();
		return interaction.getFragments().stream()
				.filter(ExecutionSpecification.class::isInstance)
				.map(ExecutionSpecification.class::cast)
				.filter(fragment -> fragment.getCovereds().contains(lifeline))
				.collect(Collectors.toList());
	}

	/**
	 * Returns the semantic element corresponding to the provided {@link EventEnd}.
	 * <p>
	 * This method allows to access {@link EventEnd#getSemanticEnd()} in a null-free way: it returns the semantic end if it is defined, or {@code null} if it isn't or if {@code eventEnd} is {@code null}.
	 * 
	 * @param eventEnd
	 *            the event end to retrieve the semantic element from
	 * @return the semantic element if it exists, or {@code null} if it doesn't exist or {@code eventEnd} is {@code null}
	 */
	private EObject getSemanticEnd(EventEnd eventEnd) {
		return Optional.ofNullable(eventEnd)
				.map(EventEnd::getSemanticEnd)
				.orElse(null);
	}

	// Copied from org.eclipse.papyrus.uml.service.types.element.UMLElementTypes to avoid a dependency to papyrus.uml.service
	private static enum MessageType {
		COMPLETE_SYNCH_CALL, COMPLETE_ASYNCH_CALL, COMPLETE_REPLY
	}

}
