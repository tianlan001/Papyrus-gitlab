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

import java.util.List;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.AbstractDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.CommonDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.DeleteServices;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.ViewpointHelpers;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.reorder.SequenceDiagramReorderElementSwitch;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.reorder.SequenceDiagramSemanticReorderHelper;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.utils.SequenceDiagramUMLHelper;
import org.eclipse.papyrus.uml.domain.services.labels.ElementDefaultNameProvider;
import org.eclipse.sirius.diagram.sequence.description.ObservationPointMapping;
import org.eclipse.sirius.diagram.sequence.ordering.EventEnd;
import org.eclipse.sirius.diagram.sequence.ordering.OrderingFactory;
import org.eclipse.sirius.diagram.sequence.ordering.SingleEventEnd;
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
import org.eclipse.uml2.uml.InteractionUse;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.StateInvariant;
import org.eclipse.uml2.uml.TimeObservation;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Generic Services for the Sequence Diagram.
 * <p>
 * This class provides services to:
 * <ul>
 * <li>Initialize semantic elements and position them on the diagram</li>
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
 * <p>
 * This class <i>initializes</i> semantic elements but do not create them. This is handled by the VSM, and is required by the Sequence
 * Diagram framework to correctly position created elements. As a result, the Sequence Diagram VSM differs from other VSM as it contains
 * both {@code CreateInstance} operations as well as {@code ChangeContext}s.
 * </p>
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com>Gwendal Daniel</a>
 */
public class SequenceDiagramServices extends AbstractDiagramServices {

	/**
	 * Shortcut for Reflective EMF element of UML.
	 */
	private static final UMLPackage UML = UMLPackage.eINSTANCE;

	/**
	 * The order service used to create and manage ends order.
	 */
	private final SequenceDiagramOrderServices orderService = new SequenceDiagramOrderServices();

	/**
	 * Provider of default name for UML elements.
	 */
	private final ElementDefaultNameProvider nameProvider = new ElementDefaultNameProvider();

	/**
	 * Service to delete elements.
	 */
	private final DeleteServices deleteServices = new DeleteServices();

	/**
	 * Helper to reorder ends.
	 */
	private final SequenceDiagramSemanticReorderHelper reorderHelper = new SequenceDiagramSemanticReorderHelper();

	/**
	 * The UML helper used to manipulate the semantic model associated to the Sequence Diagram.
	 */
	private final SequenceDiagramUMLHelper umlHelper = new SequenceDiagramUMLHelper();


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
	public Lifeline createLifeline(Interaction parent, DSemanticDecorator parentView, Lifeline predecessor) {
		CommonDiagramServices commonDiagramServices = new CommonDiagramServices();
		Lifeline result = (Lifeline) commonDiagramServices.createElement(parent, UML.getLifeline().getName(), UML.getInteraction_Lifeline().getName(), parentView);
		reorderHelper.reorderLifeline(result, predecessor);
		return result;
	}

	/**
	 * Service used to check if an object can be created under the specified container.
	 *
	 * @param container
	 *            the container that should contains the new object to create.
	 * @param objectToCreate
	 *            the EClass defining the type of the object to create .
	 * @param containmentReferenceName
	 *            the name of the containment reference to use to attach the new element to the model
	 * @return <code>true</code> if the object can be created; <code>false</code> otherwise.
	 */
	public boolean canCreateSD(EObject container, EClass objectToCreate, String containmentReferenceName) {
		return super.canCreate(container, objectToCreate, containmentReferenceName);
	}

	/**
	 * Initializes a semantic {@link Message}.
	 * <p>
	 * Default name is provided and send and receive {@link MessageOccurrenceSpecification} are created.
	 * </p>
	 * <p>
	 * The initialized elements are moved between {@code startingEndPredecessor} and {@code finishingEndPredecessor}.
	 * </p>
	 *
	 * @param message
	 *            the {@link Message} to initialize
	 * @param type
	 *            the type of the {@link Message} to set
	 * @param sendEvent
	 *            the sendEvent {@link MessageOccurrenceSpecification} to initialize
	 * @param receiveEvent
	 *            the receiveEvent {@link MessageOccurrenceSpecification} to initialize
	 * @param source
	 *            the source element of the message
	 * @param target
	 *            the target element of the message
	 * @param startingEndPredecessor
	 *            the graphical predecessor of the message's starting end
	 * @param finishingEndPredecessor
	 *            the graphical predecessor of the message's finishing end
	 * @return the initialized {@link Message}
	 */
	public Message initializeMessage(Message message, MessageSort type, NamedElement source,
			NamedElement target, EventEnd startingEndPredecessor, EventEnd finishingEndPredecessor) {
		setDefaultName(message);
		message.setMessageSort(type);
		if (source != null) {
			initializeMessageEvent(message, UML.getMessage_SendEvent(), source);
			orderService.createStartingEnd(message);
		}
		if (target != null) {
			initializeMessageEvent(message, UML.getMessage_ReceiveEvent(), target);
			orderService.createFinishingEnd(message);
		}
		return updateElementOrderWithEvents(message, startingEndPredecessor, finishingEndPredecessor);
	}

	/**
	 * Initializes a synchronous {@link Message}, its associated behavior and reply.
	 * <p>
	 * Both messages are fully initialized
	 * </p>
	 * <p>
	 * The initialized elements are moved between {@code startingEndPredecessor} and {@code finishingEndPredecessor}.
	 * </p>
	 *
	 * @param message
	 *            the {@link Message} to initialize
	 * @param type
	 *            the type of the {@link Message} to set
	 * @param sendEvent
	 *            the sendEvent {@link MessageOccurrenceSpecification} to initialize
	 * @param receiveEvent
	 *            the receiveEvent {@link MessageOccurrenceSpecification} to initialize
	 * @param source
	 *            the source element of the message
	 * @param target
	 *            the target element of the message
	 * @param startingEndPredecessor
	 *            the graphical predecessor of the message's starting end
	 * @param finishingEndPredecessor
	 *            the graphical predecessor of the message's finishing end
	 * @return the initialized {@link Message}
	 */
	public Message initializeSyncCall(Message invocation, ExecutionSpecification exec, Message reply, NamedElement source,
			NamedElement target, EventEnd startingEndPredecessor, EventEnd finishingEndPredecessor) {
		initializeMessage(invocation, MessageSort.SYNCH_CALL_LITERAL, source, target,
				startingEndPredecessor, finishingEndPredecessor);

		EventEnd invocationFinish = toEventEnd(orderService.getFinishingEnd(invocation));

		initializeMessage(reply, MessageSort.REPLY_LITERAL, target, source,
				invocationFinish, invocationFinish);

		initializeExecutionContent(exec, target);
		exec.setStart((MessageOccurrenceSpecification) invocation.getReceiveEvent());
		exec.setFinish((MessageOccurrenceSpecification) reply.getSendEvent());

		// Insert between invocation and reply.
		// As (exec start == invocation.finish) then predecessor == invocation.start
		EventEnd invocationStart = toEventEnd(orderService.getStartingEnd(invocation));
		updateElementOrderWithEvents(exec, invocationStart, invocationStart);

		return invocation;
	}



	/**
	 * Creates an Sirius end for this end.
	 *
	 * @param end
	 *            semantic end
	 * @return created EventEnd
	 */
	private EventEnd toEventEnd(EAnnotation end) {
		SingleEventEnd result = OrderingFactory.eINSTANCE.createSingleEventEnd();
		result.setSemanticEnd(end);
		result.setStart(false);
		return result;
	}

	/**
	 * Configures the {@code event} {@link MessageOccurrenceSpecification} for {@code message}.
	 *
	 * @param message
	 *            the {@link Message} to add the {@code sendEvent} into
	 * @param event
	 *            the {@link MessageOccurrenceSpecification} to add as event of the message
	 * @param suffix
	 *            the suffix to append to the {@code event} name
	 * @param eventReference
	 *            the reference to use to store the event in the message
	 * @param eventTarget
	 *            the target of the event
	 */
	private MessageOccurrenceSpecification initializeMessageEvent(Message message, EReference eventReference, NamedElement eventTarget) {
		MessageOccurrenceSpecification result = UML.getUMLFactory().createMessageOccurrenceSpecification();
		message.getInteraction().getFragments().add(result);
		result.setName(message.getName() + eventReference.getName());
		result.setMessage(message);
		message.eSet(eventReference, result);
		result.setCovered(umlHelper.getCoveredLifeline(eventTarget));
		return result;
	}

	/**
	 * Initializes a semantic {@link ExecutionSpecification}.
	 * <p>
	 * This method configures the provided {@code newActionExecutionSpecification}, {@code newStartExecutionOccurrenceSpecification},
	 * and {@code newFinishExecutionOccurrenceSpecification}. In particular, it adds the provided
	 * {@link ExecutionOccurrenceSpecification}s as <i>start</i> and <i>finish</i> of the provided
	 * {@code newActionExecutionSpecification}, initializes their names, and the {@link Lifeline} they cover.
	 * <p>
	 * The initialized elements are moved between {@code startingEndPredecessor} and {@code finishingEndPredecessor}.
	 * </p>
	 * <p>
	 * This method is used to initialize both {@link ActionExecutionSpecification} and {@link BehaviorExecutionSpecification}.
	 * </p>
	 *
	 * @param execution
	 *            the {@link ExecutionSpecification} to initialize
	 * @param start
	 *            the start {@link ExecutionOccurrenceSpecification} to initialize
	 * @param finish
	 *            the finish {@link ExecutionOccurrenceSpecification} to initialize
	 * @param startingEndPredecessor
	 *            the graphical predecessor of the execution's starting end
	 * @param finishingEndPredecessor
	 *            the graphical predecessor of the execution's finishing end
	 * @param parent
	 *            the semantic parent of the {@link ActionExecutionSpecification}
	 * @return the initialized {@link ExecutionSpecification}
	 */
	public ExecutionSpecification initializeExecutionSpecification(ExecutionSpecification execution, EventEnd startingEndPredecessor, EventEnd finishingEndPredecessor, NamedElement parent) {
		initializeExecutionContent(execution, parent);

		initializeExecutionEvent(execution, UML.getExecutionSpecification_Start());
		initializeExecutionEvent(execution, UML.getExecutionSpecification_Finish());

		orderService.createStartingEnd(execution);
		orderService.createFinishingEnd(execution);

		updateElementOrderWithEvents(execution, startingEndPredecessor, finishingEndPredecessor);

		return execution;
	}

	private ExecutionSpecification initializeExecutionContent(ExecutionSpecification execution, NamedElement parent) {
		setDefaultName(execution);
		execution.getCovereds().add(umlHelper.getCoveredLifeline(parent));
		return execution;
	}

	private ExecutionOccurrenceSpecification initializeExecutionEvent(ExecutionSpecification execution, EReference eventReference) {
		ExecutionOccurrenceSpecification result = UML.getUMLFactory().createExecutionOccurrenceSpecification();
		execution.getEnclosingInteraction().getFragments().add(result);
		result.setName(execution.getName() + eventReference.getName());
		result.setExecution(execution);
		execution.eSet(eventReference, result);
		Lifeline owner = execution.getCovereds().get(0);
		result.getCovereds().add(owner);
		return result;
	}

	/**
	 * Initialize a semantic {@link InteractionUse}.
	 * <p>
	 * This method configures the provided {@code interactionUse}. In particular, it sets its name and the {@link Lifeline}s it
	 * covers.
	 * </p>
	 * <p>
	 * The created element is moved between {@code startingEndPredecessor} and {@code finishingEndPredecessor}.
	 * </p>
	 *
	 * @param interactionUse
	 *            the {@link InteractionUse} to initialize
	 * @param startingEndPredecessor
	 *            the graphical predecessor of the interaction use's starting end
	 * @param finishingEndPredecessor
	 *            the graphical predecessor of the interaction use's finishing end
	 * @param coveredLifelines
	 *            the {@link Lifeline}s covered by the {@link InteractionUse} to create
	 * @return the initialized {@link InteractionUse}
	 */
	public InteractionUse initializeInteractionUse(InteractionUse interactionUse, EventEnd startingEndPredecessor, EventEnd finishingEndPredecessor, List<Lifeline> coveredLifelines) {
		setDefaultName(interactionUse);
		interactionUse.getCovereds().addAll(coveredLifelines);
		orderService.createStartingEnd(interactionUse);
		orderService.createFinishingEnd(interactionUse);
		return updateElementOrderWithEvents(interactionUse, startingEndPredecessor, finishingEndPredecessor);
	}

	/**
	 * Initializes a semantic {@link CombinedFragment}.
	 * <p>
	 * This method configures the provided {@code newCombinedFragment} and {@code newInteractionOperand}. In particular, it sets their
	 * names and the {@link Lifeline}s they cover.
	 * <p>
	 * The initialized elements are moved between {@code startingEndPredecessor} and {@code finishingEndPredecessor}.
	 * </p>
	 *
	 * @param combinedFragment
	 *            the {@link CombinedFragment} to initialize
	 * @param operand
	 *            the {@link InteractionOperand} to initialize
	 * @param startingEndPredecessor
	 *            the graphical predecessor of the combined fragment's starting end
	 * @param finishingEndPredecessor
	 *            the graphical predecessor of the combined fragment's finishing end
	 * @param coveredLifelines
	 *            the {@link Lifeline}s covered by the {@link CombinedFragment} to create
	 * @return the initialized {@link CombinedFragment}
	 */
	public CombinedFragment initializeCombinedFragment(CombinedFragment combinedFragment, InteractionOperand operand, EventEnd startingEndPredecessor, EventEnd finishingEndPredecessor, List<Lifeline> coveredLifelines) {
		setDefaultName(combinedFragment);
		orderService.createStartingEnd(combinedFragment);
		combinedFragment.getCovereds().addAll(coveredLifelines);

		initializeOperandContent(operand, combinedFragment);

		// There is no end annotation for InteractionOperand. It is defined by either its containing combined fragment
		// end, or the start of the next InteractionOperand in the CombinedFragment.
		orderService.createFinishingEnd(combinedFragment);

		return updateElementOrderWithEvents(combinedFragment, startingEndPredecessor, finishingEndPredecessor);
	}

	/**
	 * Initializes a semantic {@link InteractionOperand} in the provided {@code parent}.
	 * <p>
	 * This method configures the provided {@code newInteractionOperand}. In particular, it sets its name and the {@link Lifeline}s it
	 * covers.
	 * </p>
	 * <p>
	 * The initialized element is moved between {@code startingEndPredecessor} and {@code finishingEndPredecessor}.
	 * </p>
	 *
	 * @param operand
	 *            the {@link InteractionOperand} to initialize
	 * @param startingEndPredecessor
	 *            the graphical predecessor of the interaction operand's starting end
	 * @param finishingEndPredecessor
	 *            the graphical predecessor of the interaction operand's finishing end
	 * @param parentView
	 *            the graphical container of the created element
	 * @param parent
	 *            the semantic parent of the {@link InteractionOperand}
	 * @return the initialized {@link InteractionOperand}
	 */
	public InteractionOperand initializeInteractionOperand(InteractionOperand operand, EventEnd startingEndPredecessor, EventEnd finishingEndPredecessor, CombinedFragment parent) {
		initializeOperandContent(operand, parent);
		return updateElementOrderWithEvents(operand, startingEndPredecessor, finishingEndPredecessor);
	}

	private InteractionOperand initializeOperandContent(InteractionOperand operand, CombinedFragment parent) {
		setDefaultName(operand);
		operand.createGuard("guard"); //$NON-NLS-1$
		operand.getCovereds().addAll(parent.getCovereds());
		orderService.createStartingEnd(operand);
		return operand;
	}

	/**
	 * Initializes a semantic {@link StateInvariant} in the provided {@code parent}.
	 * <p>
	 * The initialized elements are moved between {@code startingEndPredecessor} and {@code finishingEndPredecessor}.
	 * </p>
	 *
	 * @param parent
	 *            the semantic parent of the {@link StateInvariant}
	 * @param stateInvariant
	 *            the {@link StateInvariant} to initialize
	 * @param startingEndPredecessor
	 *            the graphical predecessor of the execution's starting end
	 * @param finishingEndPredecessor
	 *            the graphical predecessor of the execution's finishing end
	 * @return the initialized {@link StateInvariant}
	 */
	public StateInvariant initializeStateInvariant(StateInvariant stateInvariant, EventEnd startingEndPredecessor, EventEnd finishingEndPredecessor, NamedElement parent) {
		setDefaultName(stateInvariant);
		stateInvariant.getCovereds().add(umlHelper.getCoveredLifeline(parent));

		orderService.createStartingEnd(stateInvariant);
		orderService.createFinishingEnd(stateInvariant);

		updateElementOrderWithEvents(stateInvariant, startingEndPredecessor, finishingEndPredecessor);
		return stateInvariant;
	}

	/**
	 * Deletes the provided {@code element}.
	 * <p>
	 * This method performs some Sequence-specific deletion operations, e.g. on {@link TimeObservation} elements that
	 * rely on the graphical end ordering of the diagram.
	 * </p>
	 *
	 * @param element
	 *            the semantic element to delete
	 * @param elementView
	 *            the graphical representation of the element to delete
	 * @return {@code true} if the element is deleted, {@code false} otherwise
	 */
	public boolean deleteSD(EObject element, DSemanticDecorator elementView) {
		EObject elementToDelete = element;
		if (element instanceof EAnnotation end) {
			// Do not delete EAnnotations if they aren't handled below (this is the case for EAnnotation
			// representing ends without observations).
			elementToDelete = null;
			if (ViewpointHelpers.isMapping(elementView, ObservationPointMapping.class, ViewpointHelpers.TIME_END_ID)) {
				// Check the mapping name, we can't rely on the domain type (EAnnotation)
				elementToDelete = SequenceDiagramUMLHelper.getTimeElementFromEnd(end).orElse(null);
			}

		}
		boolean result = false;
		if (elementToDelete != null) {
			result = deleteServices.delete(elementToDelete);
		}
		return result;
	}

	/**
	 * Moves {@code element} between {@code startPreviousEnd} and {@code finishPreviousEnd}.
	 * <p>
	 * This method moves the graphical ends of {@code element} between {@code startPreviousEnd} and
	 * {@code finishPreviousEnd}, and update the semantic model to reflect this change.
	 * </p>
	 *
	 * @param element
	 *            the element to move
	 * @param startPreviousEnd
	 *            the predecessor of the fragment's starting end
	 * @param finishPreviousEnd
	 *            the predecessor of the fragment's finishing end
	 *
	 * @see SequenceDiagramReorderElementSwitch
	 */
	public <T extends Element> T updateElementOrderWithEvents(T element, EventEnd startPreviousEnd, EventEnd finishPreviousEnd) {
		final EAnnotation startPrevious = getSemanticEnd(startPreviousEnd);
		final EAnnotation finishPrevious = getSemanticEnd(finishPreviousEnd);
		new SequenceDiagramReorderElementSwitch(startPrevious, finishPrevious)
				.doSwitch(element);
		return element;
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
	public void updateLifelineOrder(Interaction container, Lifeline lifeline, Lifeline predecessor) {
		reorderHelper.reorderLifeline(lifeline, predecessor);
	}

	/**
	 * Retrieves the owning {@link Interaction} of the provided {@code element}.
	 *
	 * @param element
	 *            the element to retrieve the owning {@link Interaction} from
	 * @return the owning {@link Interaction}
	 *
	 * @see SequenceDiagramUMLHelper#getOwningInteraction(Element)
	 */
	public Interaction getOwningInteraction(Element element) {
		return umlHelper.getOwningInteraction(element);
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
		if (eventEnd != null
				&& eventEnd.getSemanticEnd() instanceof EAnnotation result) {
			return result;
		}
		return null;
	}

	/**
	 * Returns the context where the message is sent.
	 * <p>
	 * If the message is lost, the context is the message it self.
	 * Otherwise, context is receiving execution or lifeline.
	 * </p>
	 *
	 * @param element
	 *            message to get the context from
	 * @return context of sending
	 */
	public Element getMessageSendContext(Message element) {
		return getMessageEndContext(element, element.getSendEvent());
	}

	/**
	 * Returns the context where the message is received.
	 * <p>
	 * If the message is found, the context is the message it self.
	 * Otherwise, context is receiving execution or lifeline.
	 * </p>
	 *
	 * @param element
	 *            message to get the context from
	 * @return context of reception
	 */
	public Element getMessageReceiveContext(Message element) {
		return getMessageEndContext(element, element.getReceiveEvent());
	}

	/**
	 * Returns the context where a message event happens.
	 * <p>
	 * The context is the message it self or and execution or a lifeline.
	 * </p>
	 *
	 * @param element
	 *            message to get the context from
	 * @return context of event
	 */
	private Element getMessageEndContext(Message element, MessageEnd end) {
		if (end instanceof InteractionFragment event) {
			return orderService.findIncludingFragment(event);
		}
		return element;
	}

	/**
	 * Sets the default name of the {@code element}.
	 *
	 * @param element
	 *            the element to set the name of
	 * @see ElementDefaultNameProvider
	 */
	private void setDefaultName(NamedElement element) {
		String name = nameProvider.getDefaultName(element, element.getOwner());
		element.setName(name);
	}
}
