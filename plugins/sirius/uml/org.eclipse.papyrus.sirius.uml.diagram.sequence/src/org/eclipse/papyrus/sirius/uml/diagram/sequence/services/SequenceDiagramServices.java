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
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.CommonDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.DeleteServices;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.Activator;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.reorder.SequenceDiagramReorderElementSwitch;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.reorder.SequenceDiagramSemanticReorderHelper;
import org.eclipse.sirius.diagram.sequence.ordering.EventEnd;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.ActionExecutionSpecification;
import org.eclipse.uml2.uml.BehaviorExecutionSpecification;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Generic Services for the Sequence Diagram.
 * <p>
 * This class provides services to:
 * <ul>
 * <li>Create semantic elements and position them on the diagram</li>
 * <li>Delete semantic elements</li>
 * <li>Reorder lifelines</li>
 * <li>Reorder interaction fragments</li>
 * </ul>
 * Note that this class doesn't provide graphical order-related services (e.g. to compute the starting/finishing end of an element),
 * see {@link SequenceDiagramOrderServices}. It also doesn't provide semantic candidate-related services, which are available in
 * {@link SequenceDiagramSemanticCandidatesServices}. Finally, see {@link SequenceDiagramUMLLabelServices} for Sequence Diagram-specific
 * label services.
 * </p>
 * <p>
 * This class is the entry point for reordering services, but note that the reordering logic is defined in the package
 * {@code org.eclipse.papyrus.sirius.uml.diagram.sequence.services.reorder}.
 * </p>
 * 
 * @author <a href="mailto:gwendal.daniel@obeosoft.com>Gwendal Daniel</a>
 */
public class SequenceDiagramServices {

	/**
	 * The error message to log when attempting to create an element on a {@code null} parent.
	 */
	private final static String CREATE_ERROR_NO_PARENT = "Unable to create a {0} with no parent"; //$NON-NLS-1$

	/**
	 * The error message to log when the creation of a graphical ordering end failed.
	 */
	private final static String ORDERING_ERROR_UNKNOWN_TYPE = "Cannot set ordering end for {0}"; //$NON-NLS-1$

	/**
	 * The order service used to create and manage graphical ordering ends.
	 */
	private final SequenceDiagramOrderServices orderService = new SequenceDiagramOrderServices();

	/**
	 * Creates a semantic {@link Lifeline} in the provided {@code parent}.
	 * <p>
	 * The new element is placed after (i.e. on the right) of the provided {@code predecessor}. The element is placed first if {@code predecessor} is {@code null}.
	 * </p>
	 * 
	 * @param parent
	 *            the semantic parent of the {@link Lifeline}
	 * @param parentView
	 *            the view of the semantic parent
	 * @param predecessor
	 *            the element preceding the lifeline to create (i.e. on the left)
	 * @return the created {@link Lifeline}
	 */
	public EObject createLifeline(Element parent, DSemanticDecorator parentView, EObject predecessor) {
		EObject result = null;
		if (parent == null) {
			Activator.log.warn(MessageFormat.format(CREATE_ERROR_NO_PARENT, UMLPackage.eINSTANCE.getLifeline().getName()));
		} else {
			CommonDiagramServices commonDiagramServices = new CommonDiagramServices();
			result = commonDiagramServices.createElement(parent, UMLPackage.eINSTANCE.getLifeline().getName(), UMLPackage.eINSTANCE.getInteraction_Lifeline().getName(), parentView);
			new SequenceDiagramSemanticReorderHelper().reorderLifeline(parent, result, predecessor);
		}
		return result;
	}

	/**
	 * Creates a semantic <i>synchronous</i> {@link Message} in the provided {@code parent}.
	 * 
	 * @param parent
	 *            the semantic parent of the {@link Message}
	 * @param source
	 *            the source element of the message
	 * @param target
	 *            the target element of the message
	 * @param startingEndPredecessor
	 *            the starting end of the created element's predecessor
	 * @param finishingEndPredecessor
	 *            the finishing end of the created element's predecessor
	 * @param parentView
	 *            the graphical container of the created element
	 * @return the created {@link Message}
	 * 
	 * @see #createMessage(Element, MessageType, Element, Element, EObject, EObject, DSemanticDecorator)
	 */
	public EObject createSynchronousMessage(Element parent, Element source, Element target, EventEnd startingEndPredecessor, EventEnd finishingEndPredecessor, DSemanticDecorator parentView) {
		return this.createMessage(parent, MessageType.COMPLETE_SYNCH_CALL, source, target, startingEndPredecessor, finishingEndPredecessor, parentView);
	}

	/**
	 * Creates a semantic <i>asynchronous</i> {@link Message} in the provided {@code parent}.
	 * 
	 * @param parent
	 *            the semantic parent of the {@link Message}
	 * @param source
	 *            the source element of the message
	 * @param target
	 *            the target element of the message
	 * @param startingEndPredecessor
	 *            the starting end of the created element's predecessor
	 * @param finishingEndPredecessor
	 *            the finishing end of the created element's predecessor
	 * @param parentView
	 *            the graphical container of the created element
	 * @return the created {@link Message}
	 * 
	 * @see #createMessage(Element, MessageType, Element, Element, EObject, EObject, DSemanticDecorator)
	 */
	public EObject createAsynchronousMessage(Element parent, Element source, Element target, EventEnd startingEndPredecessor, EventEnd finishingEndPredecessor, DSemanticDecorator parentView) {
		return this.createMessage(parent, MessageType.COMPLETE_ASYNCH_CALL, source, target, startingEndPredecessor, finishingEndPredecessor, parentView);
	}

	/**
	 * Creates a semantic <i>reply</i> {@link Message} in the provided {@code parent}.
	 * 
	 * @param parent
	 *            the semantic parent of the {@link Message}
	 * @param source
	 *            the source element of the message
	 * @param target
	 *            the target element of the message
	 * @param startingEndPredecessor
	 *            the starting end of the created element's predecessor
	 * @param finishingEndPredecessor
	 *            the finishing end of the created element's predecessor
	 * @param parentView
	 *            the graphical container of the created element
	 * @return the created {@link Message}
	 * 
	 * @see #createMessage(Element, MessageType, Element, Element, EObject, EObject, DSemanticDecorator)
	 */
	public EObject createReplyMessage(Element parent, Element source, Element target, EventEnd startingEndPredecessor, EventEnd finishingEndPredecessor, DSemanticDecorator parentView) {
		return this.createMessage(parent, MessageType.COMPLETE_REPLY, source, target, startingEndPredecessor, finishingEndPredecessor, parentView);
	}


	/**
	 * Creates a semantic {@link Message} in the provided {@code parent}.
	 * <p>
	 * The new element connects {@code source} and {@code target}, and is initialized with a <i>send</i> and <i>receive</i>
	 * {@link MessageOccurrenceSpecification} when appropriate. The created element is moved between {@code startingEndPredecessor} and {@code finishingEndPredecessor}.
	 * 
	 * @param parent
	 *            the semantic parent of the {@link Message}
	 * @param source
	 *            the source element of the message
	 * @param target
	 *            the target element of the message
	 * @param startingEndPredecessor
	 *            the starting end of the created element's predecessor
	 * @param finishingEndPredecessor
	 *            the finishing end of the created element's predecessor
	 * @param parentView
	 *            the graphical container of the created element
	 * @return the created {@link Message}
	 */
	private EObject createMessage(Element parent, MessageType type, Element source, Element target, EventEnd startingEndPredecessor, EventEnd finishingEndPredecessor, DSemanticDecorator parentView) {
		Message result = null;
		if (parent == null) {
			Activator.log.warn(MessageFormat.format(CREATE_ERROR_NO_PARENT, UMLPackage.eINSTANCE.getMessage().getName()));
		} else {
			CommonDiagramServices commonDiagramServices = new CommonDiagramServices();
			result = (Message) commonDiagramServices.createElement(parent, UMLPackage.eINSTANCE.getMessage().getName(), UMLPackage.eINSTANCE.getInteraction_Message().getName(), parentView);
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
			if (message.getSendEvent() instanceof InteractionFragment sendEventFragment) {
				this.orderService.createStartingEnd(sendEventFragment, result);
			} else {
				Activator.log.warn(MessageFormat.format(ORDERING_ERROR_UNKNOWN_TYPE, message.getSendEvent()));
			}
			if (message.getReceiveEvent() instanceof InteractionFragment receiveEventFragment) {
				this.orderService.createFinishingEnd(receiveEventFragment, result);
			} else {
				Activator.log.warn(MessageFormat.format(ORDERING_ERROR_UNKNOWN_TYPE, message.getReceiveEvent()));
			}
			EAnnotation startingEndPredecessorSemanticElement = getSemanticEnd(startingEndPredecessor);
			EAnnotation finishingEndPredecessorSemanticElement = getSemanticEnd(finishingEndPredecessor);
			new SequenceDiagramReorderElementSwitch(startingEndPredecessorSemanticElement, finishingEndPredecessorSemanticElement)
				.doSwitch(message);
		}
		return result;
	}

	/**
	 * Creates the send event {@link MessageOccurrenceSpecification} for {@code message} and set it in the provided {@code source}.
	 * 
	 * @param message
	 *            the {@link Message} to create the send event from
	 * @param source
	 *            the element to attach the created send event to
	 * @param parentView
	 *            the graphical element representing the source
	 */
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

	/**
	 * Creates the receive event {@link MessageOccurrenceSpecification} for {@code message} and set it in the provided {@code target}.
	 * 
	 * @param message
	 *            the {@link Message} to create the send event from
	 * @param target
	 *            the element to attach the created receive event to
	 * @param parentView
	 *            the graphical element representing the target
	 */
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
	 * Creates a semantic {@link ActionExecutionSpecification} in the provided {@code parent}.
	 * <p>
	 * The new element is initialized with a <i>start</i> and <i>finish</i> {@link ExecutionOccurrenceSpecification}.
	 * See {@link ExecutionSpecification#getStart()} and {@link ExecutionSpecification#getFinish()}. The created
	 * element is moved between {@code startingEndPredecessor} and {@code finishingEndPredecessor}.
	 * 
	 * @param parent
	 *            the semantic parent of the {@link ActionExecutionSpecification}
	 * @param startingEndPredecessor
	 *            the starting end of the created element's predecessor
	 * @param finishingEndPredecessor
	 *            the finishing end of the created element's predecessor
	 * @param parentView
	 *            the graphical container of the created element
	 * @return the created {@link ActionExecutionSpecification}
	 */
	public EObject createActionExecutionSpecification(Element parent, EventEnd startingEndPredecessor, EventEnd finishingEndPredecessor, DSemanticDecorator parentView) {
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

			this.orderService.createStartingEnd(result.getStart(), result);
			this.orderService.createFinishingEnd(result.getFinish(), result);

			EAnnotation semanticStartingEndPredecessor = getSemanticEnd(startingEndPredecessor);
			EAnnotation semanticFinishingEndPredecessor = getSemanticEnd(finishingEndPredecessor);
			new SequenceDiagramReorderElementSwitch(semanticStartingEndPredecessor, semanticFinishingEndPredecessor)
					.doSwitch(result);
		}
		return result;
	}

	/**
	 * Creates a semantic {@link BehaviorExecutionSpecification} in the provided {@code parent}.
	 * <p>
	 * The new element is initialized with a <i>start</i> and <i>finish</i> {@link ExecutionOccurrenceSpecification}.
	 * See {@link ExecutionSpecification#getStart()} and {@link ExecutionSpecification#getFinish()}. The created
	 * element is moved between {@code startingEndPredecessor} and {@code finishingEndPredecessor}.
	 * 
	 * @param parent
	 *            the semantic parent of the {@link BehaviorExecutionSpecification}
	 * @param startingEndPredecessor
	 *            the starting end of the created element's predecessor
	 * @param finishingEndPredecessor
	 *            the finishing end of the created element's predecessor
	 * @param parentView
	 *            the graphical container of the created element
	 * @return the created {@link BehaviorExecutionSpecification}
	 */
	public EObject createBehaviorExecutionSpecification(Element parent, EventEnd startingEndPredecessor, EventEnd finishingEndPredecessor, DSemanticDecorator parentView) {
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
			// ExecutionOccurrenceSpecifications aren't created by CommonDiagramServices for BehaviorExecutionSpecifications.
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

			this.orderService.createStartingEnd(start, result);
			this.orderService.createFinishingEnd(finish, result);

			EAnnotation semanticStartingEndPredecessor = getSemanticEnd(startingEndPredecessor);
			EAnnotation semanticFinishingEndPredecessor = getSemanticEnd(finishingEndPredecessor);
			new SequenceDiagramReorderElementSwitch(semanticStartingEndPredecessor, semanticFinishingEndPredecessor)
					.doSwitch(result);
		}
		return result;
	}

	/**
	 * Creates a semantic {@link CombinedFragment} in the provided {@code parent}.
	 * <p>
	 * The created element is moved between {@code startingEndPredecessor} and {@code finishingEndPredecessor}.
	 * </p>
	 * 
	 * @param parent
	 *            the semantic parent of the {@link CombinedFragment}
	 * @param startingEndPredecessor
	 *            the starting end of the created element's predecessor
	 * @param finishingEndPredecessor
	 *            the finishing end of the created element's predecessor
	 * @param coveredLifelines
	 *            the {@link Lifeline}s covered by the {@link CombinedFragment} to create
	 * @param parentView
	 *            the graphical container of the created element
	 * @return the created {@link CombinedFragment}
	 */
	public EObject createCombinedFragment(Element parent, EventEnd startingEndPredecessor, EventEnd finishingEndPredecessor, List<Lifeline> coveredLifelines, DSemanticDecorator parentView) {
		CommonDiagramServices commonDiagramServices = new CommonDiagramServices();
		CombinedFragment combinedFragment = (CombinedFragment) commonDiagramServices.createElement(parent, UMLPackage.eINSTANCE.getCombinedFragment().getName(), UMLPackage.eINSTANCE.getInteraction_Fragment().getName(), parentView);
		this.orderService.createStartingEnd(combinedFragment);

		combinedFragment.getCovereds().addAll(coveredLifelines);
		InteractionOperand interactionOperand = (InteractionOperand) commonDiagramServices.createElement(combinedFragment, UMLPackage.eINSTANCE.getInteractionOperand().getName(), UMLPackage.eINSTANCE.getCombinedFragment_Operand().getName(), parentView);
		interactionOperand.getCovereds().addAll(coveredLifelines);
		this.orderService.createStartingEnd(interactionOperand);
		// There is no end annotation for InteractionOperand. It is defined by either its containing combined fragment
		// end, or the start of the next InteractionOperand in the CombinedFragment.
		this.orderService.createFinishingEnd(combinedFragment);

		EAnnotation semanticStartingEndPredecessor = getSemanticEnd(startingEndPredecessor);
		EAnnotation semanticFinishingEndPredecessor = getSemanticEnd(finishingEndPredecessor);
		new SequenceDiagramReorderElementSwitch(semanticStartingEndPredecessor, semanticFinishingEndPredecessor)
				.doSwitch(combinedFragment);
		return combinedFragment;
	}

	/**
	 * Creates a semantic {@link InteractionOperand} in the provided {@code parent}.
	 * <p>
	 * The created element is moved between {@code startingEndPredecessor} and {@code finishingEndPredecessor}.
	 * </p>
	 * 
	 * @param parent
	 *            the semantic parent of the {@link InteractionOperand}
	 * @param startingEndPredecessor
	 *            the starting end of the created element's predecessor
	 * @param finishingEndPredecessor
	 *            the finishing end of the created element's predecessor
	 * @param parentView
	 *            the graphical container of the created element
	 * @return the created {@link InteractionOperand}
	 */
	public EObject createInteractionOperand(Element parent, EventEnd startingEndPredecessor, EventEnd finishingEndPredecessor, DSemanticDecorator parentView) {
		final InteractionOperand result;
		if (parent instanceof CombinedFragment combinedFragment) {
			CommonDiagramServices commonDiagramServices = new CommonDiagramServices();
			result = (InteractionOperand) commonDiagramServices.createElement(combinedFragment, UMLPackage.eINSTANCE.getInteractionOperand().getName(), UMLPackage.eINSTANCE.getCombinedFragment_Operand().getName(), parentView);
			result.getCovereds().addAll(combinedFragment.getCovereds());
			this.orderService.createStartingEnd(result);
			EAnnotation semanticStartingEndPredecessor = getSemanticEnd(startingEndPredecessor);
			EAnnotation semanticFinishingEndPredecessor = getSemanticEnd(finishingEndPredecessor);
			new SequenceDiagramReorderElementSwitch(semanticStartingEndPredecessor, semanticFinishingEndPredecessor)
					.doSwitch(result);
		} else {
			Activator.log.warn("Cannot create an {0} on the provided parent ({1})", InteractionOperand.class.getSimpleName(), parent); //$NON-NLS-1$
			result = null;
		}
		return result;
	}

	/**
	 * Deletes the provided {@code eObject}.
	 */
	public boolean deleteSD(EObject eObject) {
		DeleteServices deleteServices = new DeleteServices();
		return deleteServices.delete(eObject);
	}

	/**
	 * Moves {@code element} between {@code startingEndPredecessor} and {@code finishingEndPredecessor}.
	 * <p>
	 * This method moves the graphical ends of {@code element} between {@code startingEndPredecessor} and
	 * {@code finishingEndPredecessor}, and update the semantic model to reflect this change.
	 * </p>
	 * 
	 * @param element
	 *            the element to move
	 * @param startingEndPredecessor
	 *            the predecessor of the fragment's starting end
	 * @param finishingEndPredecessor
	 *            the predecessor of the fragment's finishing end
	 * 
	 * @see SequenceDiagramReorderElementSwitch
	 */
	public void graphicalReorderElement(Element element, EventEnd startingEndPredecessor, EventEnd finishingEndPredecessor) {
		final EAnnotation semanticStartingEndPredecessor = getSemanticEnd(startingEndPredecessor);
		final EAnnotation semanticFinishingEndPredecessor = getSemanticEnd(finishingEndPredecessor);
		new SequenceDiagramReorderElementSwitch(semanticStartingEndPredecessor, semanticFinishingEndPredecessor)
				.doSwitch(element);
	}

	/**
	 * Moves the provided {@code lifeline} after {@code predecessor} in {@code container}.
	 * <p>
	 * The lifeline is placed first if {@code predecessor} is {@code null}.
	 * </p>
	 * 
	 * @param container
	 *            the element containing the lifeline
	 * @param lifeline
	 *            the lifeline to move
	 * @param predecessor
	 *            the element preceding the lifeline in the container
	 */
	public void graphicalReorderLifeline(Element container, EObject lifeline, EObject predecessor) {
		new SequenceDiagramSemanticReorderHelper().reorderLifeline(container, lifeline, predecessor);
	}

	/**
	 * Returns the semantic {@link EAnnotation} corresponding to the provided {@link EventEnd}.
	 * <p>
	 * This method returns an {@link EAnnotation} used to represent the graphical ordering of the Sequence Diagram.
	 * It does not return an UML element. The UML element corresponding to an ordering {@link EAnnotation} can be
	 * accessed through {@link EAnnotation#getReferences()}.
	 * </p>
	 * 
	 * @param eventEnd
	 *            the event end to retrieve the semantic element from
	 * @return the semantic element if it exists, or {@code null} if it doesn't exist or {@code eventEnd} is {@code null}
	 */
	private EAnnotation getSemanticEnd(EventEnd eventEnd) {
		return Optional.ofNullable(eventEnd)
				.map(EventEnd::getSemanticEnd)
				.filter(EAnnotation.class::isInstance)
				.map(EAnnotation.class::cast)
				.orElse(null);
	}

	// Copied from org.eclipse.papyrus.uml.service.types.element.UMLElementTypes to avoid a dependency to papyrus.uml.service
	private static enum MessageType {
		COMPLETE_SYNCH_CALL, COMPLETE_ASYNCH_CALL, COMPLETE_REPLY
	}

}
