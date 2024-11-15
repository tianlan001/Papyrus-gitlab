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
import java.util.Objects;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.AbstractDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.CommonDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.DeleteServices;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.reorder.SequenceDiagramReorderElementSwitch;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.reorder.SequenceDiagramSemanticReorderHelper;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.utils.SequenceDiagramUMLHelper;
import org.eclipse.papyrus.uml.domain.services.labels.ElementDefaultNameProvider;
import org.eclipse.sirius.diagram.sequence.ordering.EventEnd;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.ActionExecutionSpecification;
import org.eclipse.uml2.uml.BehaviorExecutionSpecification;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.InteractionUse;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.NamedElement;
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
		Objects.requireNonNull(parent);
		CommonDiagramServices commonDiagramServices = new CommonDiagramServices();
		EObject result = commonDiagramServices.createElement(parent, UMLPackage.eINSTANCE.getLifeline().getName(), UMLPackage.eINSTANCE.getInteraction_Lifeline().getName(), parentView);
		reorderHelper.reorderLifeline(parent, result, predecessor);
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
	 * This method configures the provided {@code newMessage}, {@code newSendEventMessageOccurrenceSpecification},
	 * {@code newReceiveEventMessageOccurrenceSpecification}. In particular, it adds the provided
	 * {@code newSendEventMessageOccurrenceSpecification} and {@code newReceiveEventMessageOccurrenceSpecification} as <i>sendEvent</i>
	 * and <i>receiveEvent</i> or the provided {@code newMessage}, initialize their names, and the {@link Lifeline}s they cover.
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
	public EObject initializeMessage(Message message, MessageSort type, MessageOccurrenceSpecification sendEvent, MessageOccurrenceSpecification receiveEvent, Element source,
			Element target, EventEnd startingEndPredecessor, EventEnd finishingEndPredecessor) {
		this.setDefaultName(message);
		message.setMessageSort(type);
		configureMessageEvent(message, sendEvent, "SendEvent", UMLPackage.eINSTANCE.getMessage_SendEvent(), source); //$NON-NLS-1$
		configureMessageEvent(message, receiveEvent, "ReceiveEvent", UMLPackage.eINSTANCE.getMessage_ReceiveEvent(), target); //$NON-NLS-1$
		this.orderService.createStartingEnd(sendEvent, message);
		this.orderService.createFinishingEnd(receiveEvent, message);

		return updateElementOrderWithEvents(message, startingEndPredecessor, finishingEndPredecessor);
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
	private void configureMessageEvent(Message message, MessageOccurrenceSpecification event, String suffix, EReference eventReference, Element eventTarget) {
		if (eventTarget instanceof Lifeline || eventTarget instanceof ExecutionSpecification) {
			event.setName(message.getName() + suffix);
			event.setMessage(message);
			message.eSet(eventReference, event);
			if (eventTarget instanceof Lifeline lifeline) {
				event.setCovered(lifeline);
			} else if (eventTarget instanceof ExecutionSpecification executionSpecification) {
				event.setCovered(executionSpecification.getCovereds().get(0));
			}
		}
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
	public EObject initializeExecutionSpecification(ExecutionSpecification execution, ExecutionOccurrenceSpecification start, ExecutionOccurrenceSpecification finish, EventEnd startingEndPredecessor, EventEnd finishingEndPredecessor, Element parent) {
		if(parent instanceof Lifeline lifeline) {
			this.setDefaultName(execution);
			execution.getCovereds().add(lifeline);

			start.setName(execution.getName() + "Start"); //$NON-NLS-1$
			start.setExecution(execution);
			start.getCovereds().add(lifeline);
			execution.setStart(start);

			finish.setName(execution.getName() + "Finish"); //$NON-NLS-1$
			finish.setExecution(execution);
			finish.getCovereds().add(lifeline);
			execution.setFinish(finish);

			this.orderService.createStartingEnd(start, execution);
			this.orderService.createFinishingEnd(finish, execution);

			updateElementOrderWithEvents(execution, startingEndPredecessor, finishingEndPredecessor);
		}
		return execution;
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
	public EObject initializeInteractionUse(InteractionUse interactionUse, EventEnd startingEndPredecessor, EventEnd finishingEndPredecessor, List<Lifeline> coveredLifelines) {
		this.setDefaultName(interactionUse);
		interactionUse.getCovereds().addAll(coveredLifelines);
		this.orderService.createStartingEnd(interactionUse);
		this.orderService.createFinishingEnd(interactionUse);

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
	public EObject initializeCombinedFragment(CombinedFragment combinedFragment, InteractionOperand operand, EventEnd startingEndPredecessor, EventEnd finishingEndPredecessor, List<Lifeline> coveredLifelines) {
		this.setDefaultName(combinedFragment);
		this.setDefaultName(operand);
		operand.createGuard("guard"); //$NON-NLS-1$
		this.orderService.createStartingEnd(combinedFragment);
		combinedFragment.getCovereds().addAll(coveredLifelines);
		operand.getCovereds().addAll(coveredLifelines);
		this.orderService.createStartingEnd(operand);
		// There is no end annotation for InteractionOperand. It is defined by either its containing combined fragment
		// end, or the start of the next InteractionOperand in the CombinedFragment.
		this.orderService.createFinishingEnd(combinedFragment);

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
	public EObject initializeInteractionOperand(InteractionOperand operand, EventEnd startingEndPredecessor, EventEnd finishingEndPredecessor, Element parent) {
		if (parent instanceof CombinedFragment combinedFragment) {
			this.setDefaultName(operand);
			operand.createGuard("guard"); //$NON-NLS-1$
			operand.getCovereds().addAll(combinedFragment.getCovereds());
			this.orderService.createStartingEnd(operand);

			updateElementOrderWithEvents(operand, startingEndPredecessor, finishingEndPredecessor);
		}
		return operand;
	}

	/**
	 * Deletes the provided {@code eObject}.
	 *
	 * @param eObject
	 *            element to delete
	 * @return <code>true</code> if the element is removed, <code>false</code> otherwise.
	 */
	public boolean deleteSD(EObject eObject) {
		// Expose this API to VSM keeping limited the number of exposed function.
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
	public <T extends Element> T updateElementOrderWithEvents(T element, EventEnd startingEndPredecessor, EventEnd finishingEndPredecessor) {
		final EAnnotation semanticStartingEndPredecessor = getSemanticEnd(startingEndPredecessor);
		final EAnnotation semanticFinishingEndPredecessor = getSemanticEnd(finishingEndPredecessor);
		new SequenceDiagramReorderElementSwitch(semanticStartingEndPredecessor, semanticFinishingEndPredecessor)
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
	public void updateLifelineOrder(Element container, EObject lifeline, EObject predecessor) {
		reorderHelper.reorderLifeline(container, lifeline, predecessor);
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
		return new SequenceDiagramUMLHelper().getOwningInteraction(element);
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
		return eventEnd != null && eventEnd.getSemanticEnd() instanceof EAnnotation result
				? result
				: null;
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
