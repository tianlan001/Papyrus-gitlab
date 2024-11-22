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
import java.util.Optional;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.tools.TargetingTool;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditDomain;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.AbstractDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.CommonDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.DeleteServices;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.reorder.SequenceDiagramReorderElementSwitch;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.reorder.SequenceDiagramSemanticReorderHelper;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.utils.SequenceDiagramUMLHelper;
import org.eclipse.papyrus.uml.domain.services.EMFUtils;
import org.eclipse.papyrus.uml.domain.services.labels.ElementDefaultNameProvider;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.common.tools.api.util.ReflectionHelper;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.DNode;
import org.eclipse.sirius.diagram.sequence.business.api.util.Range;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.ISequenceElementAccessor;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.ISequenceEvent;
import org.eclipse.sirius.diagram.sequence.description.ObservationPointMapping;
import org.eclipse.sirius.diagram.sequence.ordering.EventEnd;
import org.eclipse.sirius.diagram.ui.business.api.view.SiriusGMFHelper;
import org.eclipse.sirius.ext.gmf.runtime.editparts.GraphicalHelper;
import org.eclipse.sirius.ui.business.api.session.IEditingSession;
import org.eclipse.sirius.ui.business.api.session.SessionUIManager;
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
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
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
	public EObject createLifeline(Element parent, DSemanticDecorator parentView, EObject predecessor) {
		CommonDiagramServices commonDiagramServices = new CommonDiagramServices();
		EObject result = commonDiagramServices.createElement(parent, UML.getLifeline().getName(), UML.getInteraction_Lifeline().getName(), parentView);
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
	public Message initializeMessage(Message message, MessageSort type, MessageOccurrenceSpecification sendEvent, MessageOccurrenceSpecification receiveEvent, NamedElement source,
			NamedElement target, EventEnd startingEndPredecessor, EventEnd finishingEndPredecessor) {
		setDefaultName(message);
		message.setMessageSort(type);
		initializeMessageEvent(message, sendEvent, UML.getMessage_SendEvent(), source);
		initializeMessageEvent(message, receiveEvent, UML.getMessage_ReceiveEvent(), target);
		orderService.createStartingEnd(message);
		orderService.createFinishingEnd(message);

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
	private void initializeMessageEvent(Message message, MessageOccurrenceSpecification event, EReference eventReference, NamedElement eventTarget) {
		event.setName(message.getName() + eventReference.getName());
		event.setMessage(message);
		message.eSet(eventReference, event);
		event.setCovered(umlHelper.getCoveredLifeline(eventTarget));
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
		setDefaultName(execution);
		execution.getCovereds().add(umlHelper.getCoveredLifeline(parent));

		initializeExecutionEvent(execution, UML.getExecutionSpecification_Start());
		initializeExecutionEvent(execution, UML.getExecutionSpecification_Finish());

		orderService.createStartingEnd(execution);
		orderService.createFinishingEnd(execution);

		updateElementOrderWithEvents(execution, startingEndPredecessor, finishingEndPredecessor);

		return execution;
	}

	private ExecutionOccurrenceSpecification initializeExecutionEvent(ExecutionSpecification execution, EReference eventReference) {
		ExecutionOccurrenceSpecification result = UML.getUMLFactory().createExecutionOccurrenceSpecification();
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
	 * Returns {@code true} if a {@link TimeObservation} can be created on the provided {@code parent}.
	 *
	 * @param parent
	 *            the element to check
	 * @return {@code true} if a {@link TimeObservation} can be created on the provided {@code parent}
	 */
	public boolean canCreateTimeObservation(Element parent) {
		boolean result = false;
		if (parent instanceof ExecutionSpecification || parent instanceof Message) {
			result = umlHelper.getTimeObservationFromEvent(umlHelper.getSemanticStart(parent)).isEmpty()
					|| umlHelper.getTimeObservationFromEvent(umlHelper.getSemanticFinish(parent)).isEmpty();
		}
		return result;
	}

	/**
	 * Creates a {@link TimeObservation} on the provided {@code parent}.
	 *
	 * @param parent
	 *            the element on which to create a {@link TimeObservation}
	 * @param parentView
	 *            the graphical view representing the {@code parent}
	 * @param diagram
	 *            the diagram
	 * @return the created {@link TimeObservation}, or {@code null} if the creation failed
	 * @see #canCreateTimeObservation(Element)
	 */
	public EObject createTimeObservation(Element parent, DSemanticDecorator parentView, DDiagram diagram) {
		InteractionFragment start = umlHelper.getSemanticStart(parent);
		InteractionFragment finish = umlHelper.getSemanticFinish(parent);

		List<TimeObservation> startTimeObservations = umlHelper.getTimeObservationFromEvent(start);
		List<TimeObservation> finishTimeObservations = umlHelper.getTimeObservationFromEvent(finish);

		// Check which side of the element has been clicked, and try to create a TimeObservation on the
		// corresponding end. If there is already a TimeObservation on it, try to add it on the other side.
		// Do nothing if the element already has the maximum number of TimeObservations attached to it.

		Element event = null;
		if (isActiveCreationToolInvokedOnStartSide(parent, parentView, diagram)) {
			if (startTimeObservations.isEmpty()) {
				event = start;
			} else if (finishTimeObservations.isEmpty()) {
				event = finish;
			}
		} else // on finish side
			if (finishTimeObservations.isEmpty()) {
				event = finish;
			} else if (startTimeObservations.isEmpty()) {
				event = start;
			}

		if (event == null) { // already created.
			return null;
		}

		return createTimeObservationWithEvent(event, parentView);
	}

	/**
	 * Returns {@code true} if the active creation tool has been invoked on the <i>start</i> side of the provided {@code parent}.
	 * <p>
	 * The <i>start</i> side of an element is the graphical half of the element that is closer to the its starting end than its
	 * finishing end. For example, it corresponds to the upper half of an execution, or the left half of a message that goes
	 * left-to-right.
	 * </p>
	 * <p>
	 * This method is typically used to define click-dependent creation tool behavior. For example, it allows to
	 * create {@link TimeObservation}s on the appropriate end of the parent element, relative to the position of
	 * the click on it.
	 * </p>
	 * <p>
	 * Note that this method returns {@code true} by default if the position of the active tool cannot be retrieved.
	 * </p>
	 *
	 * @param parent
	 *            the element that has been clicked on
	 * @param parentView
	 *            the graphical view representing the {@code parent}
	 * @param diagram
	 *            the diagram
	 * @return {@code true} if the active creation tool has been invoked on the start side of {@code parent}, {@code false} otherwise
	 *
	 * @see #getActiveCreationToolClickLocation(DDiagram)
	 */
	@SuppressWarnings("restriction")
	private boolean isActiveCreationToolInvokedOnStartSide(Element parent, DSemanticDecorator parentView, DDiagram diagram) {
		// Default to true if we cannot compute which side of the element is the closest.
		// This method relies on internal APIs to access the range of the elements on the diagram. This should be
		// handled internally by Sirius and exposed as tool variables.
		boolean result = true;
		Optional<Point> optClickLocation = getActiveCreationToolClickLocation(diagram);
		if (optClickLocation.isPresent() && parentView instanceof DDiagramElement diagramElementParentView) {
			Point clickLocation = optClickLocation.get();

			@SuppressWarnings({ "deprecation", "removal" })
			ISequenceEvent sequenceEvent = ISequenceElementAccessor.getISequenceEvent(SiriusGMFHelper.getGmfView(diagramElementParentView)).get();
			if (diagramElementParentView instanceof DNode && parent instanceof ExecutionSpecification) {
				Range verticalRange = sequenceEvent.getVerticalRange();
				result = clickLocation.y() <= ((verticalRange.getLowerBound() + verticalRange.getUpperBound()) / 2);
			} else if (diagramElementParentView instanceof DEdge) {
				Rectangle logicalBounds = sequenceEvent.getProperLogicalBounds();
				if (logicalBounds.width() > 0) {
					result = clickLocation.x() <= (logicalBounds.x() + logicalBounds.width() / 2);
				} else {
					result = clickLocation.x() >= (logicalBounds.x() + logicalBounds.width() / 2);
				}
			}
		}
		return result;
	}

	/**
	 * Returns the click location of the active creation tool.
	 * <p>
	 * This method is typically used to define click-dependent creation tool behavior. For example, it allows to
	 * create {@link TimeObservation}s on the appropriate end of the parent element, relative to the position of
	 * the click on it.
	 * </p>
	 *
	 * @param diagram
	 *            the diagram on which the tool is invoked
	 * @return the location of the active creation tool if it exists, or {@link Optional#empty()} otherwise
	 */
	private Optional<Point> getActiveCreationToolClickLocation(DDiagram diagram) {
		// This method relies on reflection and internal APIs to access the click location of the active tool.
		// This should be handled internally by Sirius: the location should be available in the tool's variables.
		Optional<Point> result = Optional.empty();

		IEditingSession uiSession = SessionUIManager.INSTANCE.getUISession(Session.of(diagram).get());
		if (uiSession.getEditor(diagram) instanceof DiagramEditor editor
				&& editor.getDiagramEditDomain() instanceof DiagramEditDomain editDomain
				&& editDomain.getActiveTool() instanceof TargetingTool tool) {
			if (ReflectionHelper.getFieldValueWithoutException(tool, "targetRequest").orElse(null) instanceof CreateRequest createRequest //$NON-NLS-1$
					&& ReflectionHelper.getFieldValueWithoutException(tool, "targetEditPart").orElse(null) instanceof EditPart editPart) { //$NON-NLS-1$
				// Create a copy: we don't want to change the location when computing the logical point.
				Point clickLocation = createRequest.getLocation().getCopy();
				GraphicalHelper.screen2logical(clickLocation, (IGraphicalEditPart) editPart);
				result = Optional.of(clickLocation);
			}
		}

		return result;
	}

	/**
	 * Creates a {@link TimeObservation} and sets its event to {@code event}.
	 *
	 * @param event
	 *            the event to set for the {@link TimeObservation}
	 * @param parentView
	 *            the graphical element representing the parent of the {@link TimeObservation} to create
	 * @return the created {@link TimeObservation}
	 */
	private TimeObservation createTimeObservationWithEvent(Element event, DSemanticDecorator parentView) {
		TimeObservation result = null;
		if (event instanceof NamedElement namedEvent) {
			Package ancestorPackage = EMFUtils.getAncestor(Package.class, event);
			CommonDiagramServices commonDiagramServices = new CommonDiagramServices();
			// We can use the CommonDiagramServices to create TimeObservations: they aren't directly affected by
			// reorders, and are always created at the right location.
			result = (TimeObservation) commonDiagramServices.createElement(ancestorPackage, UML.getTimeObservation().getName(), UML.getPackage_PackagedElement().getName(), parentView);
			result.setEvent(namedEvent);
		}
		return result;
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
		if (element instanceof EAnnotation eAnnotation) {
			// Do not delete EAnnotations if they aren't handled below (this is the case for EAnnotation
			// representing ends without observations).
			elementToDelete = null;
			if (elementView instanceof DNode dNode) {
				if (dNode.getMapping() instanceof ObservationPointMapping mapping
						&& Objects.equals(mapping.getName(), "SD_TimeObservation")) { //$NON-NLS-1$
					// Check the mapping name, we can't rely on the domain type (EAnnotation)
					elementToDelete = umlHelper.getTimeObservationFromEnd(eAnnotation).orElse(null);
				}
			}
		}
		boolean result = false;
		if (elementToDelete != null) {
			result = deleteServices.delete(elementToDelete);
		}
		return result;
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
	 * @param startingPreviousEnd
	 *            the predecessor of the fragment's starting end
	 * @param finishPreviousEnd
	 *            the predecessor of the fragment's finishing end
	 *
	 * @see SequenceDiagramReorderElementSwitch
	 */
	public <T extends Element> T updateElementOrderWithEvents(T element, EventEnd startingPreviousEnd, EventEnd finishPreviousEnd) {
		final EAnnotation startPrevious = getSemanticEnd(startingPreviousEnd);
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
		if (eventEnd != null
				&& eventEnd.getSemanticEnd() instanceof EAnnotation result) {
			return result;
		}
		return null;
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
