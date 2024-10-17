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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.CommonDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.DeleteServices;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.Activator;
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
 * Services for the "Sequence" diagram.
 * 
 * @author <a href="mailto:gwendal.daniel@obeosoft.com>Gwendal Daniel</a>
 */
public class SequenceDiagramServices {

	private final static String CREATE_ERROR_NO_PARENT = "Unable to create a {0} with no parent"; //$NON-NLS-1$

	private final static String ORDERING_ERROR_UNKNOWN_TYPE = "Cannot set ordering end for {0}"; //$NON-NLS-1$

	public static final String START_ANNOTATION_SOURCE = "org.eclipse.papyrus.sirius.uml.diagram.sequence.start"; //$NON-NLS-1$

	public static final String END_ANNOTATION_SOURCE = "org.eclipse.papyrus.sirius.uml.diagram.sequence.end"; //$NON-NLS-1$

	public static final String ORDERING_ANNOTATION_SOURCE = "org.eclipse.papyrus.sirius.uml.diagram.sequence.end"; //$NON-NLS-1$

	/**
	 * The service used to reorder elements.
	 * <p>
	 * This service is used after element creation to move them at the correct location (graphically and semantically),
	 * as well as to handle reorder tools.
	 * </p>
	 */
	private SequenceDiagramReorderServices reorderServices = new SequenceDiagramReorderServices();

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
			reorderServices.reorderLifeline(parent, result, predecessor);
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
			Interaction rootInteraction = message.getInteraction();
			EAnnotation orderingAnnotation = getOrCreateOrderingAnnotation(rootInteraction);
			if (message.getSendEvent() instanceof InteractionFragment sendEventFragment) {
				orderingAnnotation.getContents().add(this.createStartAnnotation(sendEventFragment, result));
			} else {
				Activator.log.warn(MessageFormat.format(ORDERING_ERROR_UNKNOWN_TYPE, message.getSendEvent()));
			}
			if (message.getReceiveEvent() instanceof InteractionFragment receiveEventFragment) {
				orderingAnnotation.getContents().add(this.createEndAnnotation(receiveEventFragment, result));
			} else {
				Activator.log.warn(MessageFormat.format(ORDERING_ERROR_UNKNOWN_TYPE, message.getReceiveEvent()));
			}
			EAnnotation startingEndPredecessorSemanticElement = getSemanticEnd(startingEndPredecessor);
			EAnnotation finishingEndPredecessorSemanticElement = getSemanticEnd(finishingEndPredecessor);
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

			Interaction rootInteraction = reorderServices.getOwningInteraction(result);
			EAnnotation orderingAnnotation = getOrCreateOrderingAnnotation(rootInteraction);
			orderingAnnotation.getContents().add(this.createStartAnnotation(result.getStart(), result));
			orderingAnnotation.getContents().add(this.createEndAnnotation(result.getFinish(), result));

			EAnnotation semanticStartingEndPredecessor = getSemanticEnd(startingEndPredecessor);
			EAnnotation semanticFinishingEndPredecessor = getSemanticEnd(finishingEndPredecessor);
			reorderServices.reorderFragment(result, semanticStartingEndPredecessor, semanticFinishingEndPredecessor);
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

			Interaction rootInteraction = reorderServices.getOwningInteraction(result);
			EAnnotation orderingAnnotation = this.getOrCreateOrderingAnnotation(rootInteraction);
			orderingAnnotation.getContents().add(this.createStartAnnotation(start, result));
			orderingAnnotation.getContents().add(this.createEndAnnotation(finish, result));

			EAnnotation semanticStartingEndPredecessor = getSemanticEnd(startingEndPredecessor);
			EAnnotation semanticFinishingEndPredecessor = getSemanticEnd(finishingEndPredecessor);
			reorderServices.reorderFragment(result, semanticStartingEndPredecessor, semanticFinishingEndPredecessor);
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
		Interaction rootInteraction = reorderServices.getOwningInteraction(combinedFragment);
		EAnnotation orderingAnnotation = this.getOrCreateOrderingAnnotation(rootInteraction);
		orderingAnnotation.getContents().add(this.createStartAnnotation(combinedFragment));

		combinedFragment.getCovereds().addAll(coveredLifelines);
		InteractionOperand interactionOperand = (InteractionOperand) commonDiagramServices.createElement(combinedFragment, UMLPackage.eINSTANCE.getInteractionOperand().getName(), UMLPackage.eINSTANCE.getCombinedFragment_Operand().getName(), parentView);
		interactionOperand.getCovereds().addAll(coveredLifelines);
		orderingAnnotation.getContents().add(this.createStartAnnotation(interactionOperand));
		// There is no end annotation for InteractionOperand. It is defined by either its containing combined fragment
		// end, or the start of the next InteractionOperand in the CombinedFragment.
		orderingAnnotation.getContents().add(this.createEndAnnotation(combinedFragment));

		EAnnotation semanticStartingEndPredecessor = getSemanticEnd(startingEndPredecessor);
		EAnnotation semanticFinishingEndPredecessor = getSemanticEnd(finishingEndPredecessor);
		reorderServices.reorderFragment(combinedFragment, semanticStartingEndPredecessor, semanticFinishingEndPredecessor);
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
			EAnnotation orderingAnnotation = getOrCreateOrderingAnnotation(reorderServices.getOwningInteraction(parent));
			orderingAnnotation.getContents().add(this.createStartAnnotation(result));
			EAnnotation semanticStartingEndPredecessor = getSemanticEnd(startingEndPredecessor);
			EAnnotation semanticFinishingEndPredecessor = getSemanticEnd(finishingEndPredecessor);
			reorderServices.reorderFragment(result, semanticStartingEndPredecessor, semanticFinishingEndPredecessor);
		} else {
			Activator.log.warn("Cannot create an {0} on the provided parent ({1})", InteractionOperand.class.getSimpleName(), parent); //$NON-NLS-1$
			result = null;
		}
		return result;
	}

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
	 * @see SequenceDiagramReorderServices#reorderFragment(InteractionFragment, EAnnotation, EAnnotation)
	 */
	public void graphicalReorderElement(Element element, EventEnd startingEndPredecessor, EventEnd finishingEndPredecessor) {
		final EAnnotation semanticStartingEndPredecessor = getSemanticEnd(startingEndPredecessor);
		final EAnnotation semanticFinishingEndPredecessor = getSemanticEnd(finishingEndPredecessor);
		if (element instanceof InteractionFragment fragment) {
			reorderServices.reorderFragment(fragment, semanticStartingEndPredecessor, semanticFinishingEndPredecessor);
		} else if (element instanceof Message message) {
			reorderServices.reorderMessage(message, semanticStartingEndPredecessor, semanticFinishingEndPredecessor);
		}
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
		List<ExecutionSpecification> result = new ArrayList<>();
		for (InteractionFragment fragment : interaction.getFragments()) {
			if (fragment instanceof ExecutionSpecification executionSpecification) {
				result.add(executionSpecification);
			} else if (fragment instanceof CombinedFragment combinedFragment) {
				result.addAll(this.getExecutionSpecificationCandidates(combinedFragment));
			}
		}
		return result.stream()
				.filter(executionSpecification -> executionSpecification.getCovereds().contains(lifeline))
				.toList();
	}

	/**
	 * Returns the {@link ExecutionSpecification}s in the provided {@code combinedFragment}.
	 * 
	 * @param combinedFragment
	 *            the {@link CombinedFragment} to search into
	 * @return the list of {@link ExecutionSpecification}
	 */
	private Collection<ExecutionSpecification> getExecutionSpecificationCandidates(CombinedFragment combinedFragment) {
		List<ExecutionSpecification> result = new ArrayList<>();
		for (InteractionOperand operand : combinedFragment.getOperands()) {
			for (InteractionFragment fragment : operand.getFragments()) {
				if (fragment instanceof ExecutionSpecification executionSpecification) {
					result.add(executionSpecification);
				} else if (fragment instanceof CombinedFragment childCombinedFragment) {
					result.addAll(this.getExecutionSpecificationCandidates(childCombinedFragment));
				}
			}
		}
		return result;
	}

	/**
	 * Returns the {@link CombinedFragment} contained in the provided {@code interaction}.
	 * 
	 * @param interaction
	 *            the {@link Interaction} to search into
	 * @return the {@link CombinedFragment} contained in the provided {@code interaction}
	 */
	public Collection<CombinedFragment> getCombinedFragmentCandidates(Interaction interaction) {
		List<CombinedFragment> result = new ArrayList<>();
		for (InteractionFragment fragment : interaction.getFragments()) {
			if (fragment instanceof CombinedFragment combinedFragment) {
				result.add(combinedFragment);
				result.addAll(this.getCombinedFragmentCandidates(combinedFragment));
			}
		}
		return result;
	}

	/**
	 * Returns the {@link CombinedFragment} contained in the provided {@code combinedFragment}.
	 * 
	 * @param combinedFragment
	 *            the {@link CombinedFragment} to search into
	 * @return the {@link CombinedFragment} contained in the provided {@code combinedFragment}
	 */
	private Collection<CombinedFragment> getCombinedFragmentCandidates(CombinedFragment combinedFragment) {
		List<CombinedFragment> result = new ArrayList<>();
		for (InteractionOperand operand : combinedFragment.getOperands()) {
			for (InteractionFragment fragment : operand.getFragments()) {
				if (fragment instanceof CombinedFragment childCombinedFragment) {
					result.add(childCombinedFragment);
					result.addAll(this.getCombinedFragmentCandidates(childCombinedFragment));
				}
			}
		}
		return result;
	}

	/**
	 * Returns the {@link InteractionOperand} contained in the provided {@code combinedFragment}.
	 * 
	 * @param combinedFragment
	 *            the {@link CombinedFragment} to search into
	 * @return the {@link InteractionOperand} contained in the provided {@code combinedFragment}
	 */
	public Collection<InteractionOperand> getInteractionOperandCandidates(CombinedFragment combinedFragment) {
		return combinedFragment.getOperands();
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

	/**
	 * Gets the {@link Interaction}'s {@link EAnnotation} containing the general ordering of the Sequence Diagram.
	 * <p>
	 * Sequence Diagram graphical ordering is stored in an {@link EAnnotation} on the root {@link Interaction} of the diagram.
	 * This annotation contains itself the {@link EAnnotation}s representing the ordered starting/finishing ends of the
	 * graphical elements.
	 * </p>
	 * <p>
	 * This method creates the {@link EAnnotation} if it does not exist.
	 * </p>
	 * 
	 * @param interaction
	 *            the {@link Interaction} to retrieve the ordering from
	 * @return the ordering {@link EAnnotation}
	 */
	private EAnnotation getOrCreateOrderingAnnotation(Interaction interaction) {
		EAnnotation orderingAnnotation = interaction.getEAnnotation(ORDERING_ANNOTATION_SOURCE);
		if (orderingAnnotation == null) {
			orderingAnnotation = interaction.createEAnnotation(ORDERING_ANNOTATION_SOURCE);
		}
		return orderingAnnotation;
	}

	/**
	 * Creates a <i>starting end</i> {@link EAnnotation} for the given {@code fragment}.
	 * <p>
	 * The provided {@code fragment} is stored in {@link EAnnotation#getReferences()}.
	 * </p>
	 * 
	 * @param fragment
	 *            the {@link InteractionFragment}
	 * @return the created {@link EAnnotation}
	 * 
	 * @see #createAnnotationWithReference(InteractionFragment, Element, String)
	 */
	private EAnnotation createStartAnnotation(InteractionFragment fragment) {
		return this.createStartAnnotation(fragment, null);
	}

	/**
	 * Creates a <i>starting end</i> {@link EAnnotation} for the given {@code fragment} and {@code baseElement}.
	 * 
	 * @param fragment
	 *            the {@link InteractionFragment}
	 * @param baseElement
	 *            the base element of the fragment
	 * @return the created {@link EAnnotation}
	 * 
	 * @see #createAnnotationWithReference(InteractionFragment, Element, String)
	 */
	private EAnnotation createStartAnnotation(InteractionFragment fragment, Element baseElement) {
		return this.createAnnotationWithReference(fragment, baseElement, START_ANNOTATION_SOURCE);
	}

	/**
	 * Creates a <i>finishing end</i> {@link EAnnotation} for the given {@code fragment}.
	 * 
	 * @param fragment
	 *            the {@link InteractionFragment}
	 * @return the created {@link EAnnotation}
	 * 
	 * @see #createAnnotationWithReference(InteractionFragment, Element, String)
	 */
	private EAnnotation createEndAnnotation(InteractionFragment fragment) {
		return this.createEndAnnotation(fragment, null);
	}

	/**
	 * Creates a <i>finishing end</i> {@link EAnnotation} for the given {@code fragment} and {@code baseElement}.
	 * 
	 * @param fragment
	 *            the {@link InteractionFragment}
	 * @param baseElement
	 *            the base element of the fragment
	 * @return the created {@link EAnnotation}
	 * 
	 * @see #createAnnotationWithReference(InteractionFragment, Element, String)
	 */
	private EAnnotation createEndAnnotation(InteractionFragment fragment, Element baseElement) {
		return this.createAnnotationWithReference(fragment, baseElement, END_ANNOTATION_SOURCE);
	}

	/**
	 * Creates a {@code source} {@link EAnnotation} for the given {@code fragment} and {@code baseElement}.
	 * <p>
	 * This method is typically used to create an ordering end for an element that already has a semantic start (e.g. an
	 * execution). In this case, the semantic start is stored as the first reference in the annotation, and the base element
	 * is stored as the second element.
	 * </p>
	 * <p>
	 * The provided {@code [fragment, baseElement]} are stored in {@link EAnnotation#getReferences()}.
	 * </p>
	 * 
	 * @param fragment
	 *            the {@link InteractionFragment}
	 * @param baseElement
	 *            the base element of the fragment
	 * @param source
	 *            the source of the {@link EAnnotation}
	 * @return the created {@link EAnnotation}
	 */
	private EAnnotation createAnnotationWithReference(InteractionFragment fragment, Element baseElement, String source) {
		Objects.requireNonNull(fragment);
		Objects.requireNonNull(source);
		EAnnotation annotation = EcoreFactory.eINSTANCE.createEAnnotation();
		annotation.setSource(source);
		annotation.getReferences().add(fragment);
		if (baseElement != null) {
			annotation.getReferences().add(baseElement);
		}
		return annotation;
	}

	// Copied from org.eclipse.papyrus.uml.service.types.element.UMLElementTypes to avoid a dependency to papyrus.uml.service
	private static enum MessageType {
		COMPLETE_SYNCH_CALL, COMPLETE_ASYNCH_CALL, COMPLETE_REPLY
	}

}
