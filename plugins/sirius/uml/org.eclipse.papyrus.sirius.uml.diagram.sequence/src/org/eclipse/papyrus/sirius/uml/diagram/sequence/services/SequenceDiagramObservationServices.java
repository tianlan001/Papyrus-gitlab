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

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.CommonDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.ViewpointHelpers;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.utils.SequenceDiagramUMLHelper;
import org.eclipse.papyrus.uml.domain.services.UMLHelper;
import org.eclipse.sirius.diagram.sequence.description.ObservationPointMapping;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.DurationConstraint;
import org.eclipse.uml2.uml.DurationObservation;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.OccurrenceSpecification;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.TimeConstraint;
import org.eclipse.uml2.uml.TimeObservation;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Generic Services for the Sequence Diagram.
 * <p>
 * This class offers service to display and edit Observation related elements for
 * Sequence diagrams to used by the VSM.
 * </p>
 *
 * @author <a href="mailto:nicolas.peransin@obeo.fr>Nicolas Peransin (Obeo)</a>
 */
public final class SequenceDiagramObservationServices {

	/*
	 * Warning: UML Ecore is bugged for DurationObservation and DurationConstraint.
	 * FirstEvents { unique, !ordered } is not conform with UML specification.
	 * As workaround, constraint targets ends instead of elements and 'firstEvents'
	 * property.
	 *
	 * @see DurationConstraint#getFirstEvents()
	 *
	 * @see DurationOservation#getFirstEvents()
	 */

	/**
	 * Shortcut for Reflective EMF element of UML.
	 */
	private static final UMLPackage UML = UMLPackage.eINSTANCE;

	/**
	 * The UML helper used to manipulate the semantic model associated to the Sequence Diagram.
	 */
	private static final SequenceDiagramUMLHelper UML_HELPER = new SequenceDiagramUMLHelper();

	/**
	 * The order service used to create and manage ends order.
	 */
	private static final SequenceDiagramOrderServices ORDER_SERVICES = new SequenceDiagramOrderServices();

	/** Common services. */
	private static final CommonDiagramServices COMMONS = new CommonDiagramServices();

	// No creation allows for utilities class
	private SequenceDiagramObservationServices() {
	}

	/**
	 * Evaluates if a {@link TimeObservation} can be created at the location of provided {@code view}.
	 * <p>
	 * See {@link
	 * SequenceDiagramSemanticCandidatesServices#getImplicitTimeObservationCandidates(Interaction)
	 * } for details on place where a Time-related element can be created.
	 * </p>
	 *
	 * @param view
	 *            the user-selected view
	 * @return {@code true} if a {@link TimeObservation} can be created on the provided {@code parent}
	 */
	public static boolean canCreateTimeElement(DSemanticDecorator view) {
		boolean end = view.getTarget() instanceof EAnnotation;
		return end &&
				ViewpointHelpers.isMapping(view, ObservationPointMapping.class, ViewpointHelpers.OBSERVABLE_END_ID);
	}

	/**
	 * Creates a {@link TimeObservation} on the provided {@code end}.
	 *
	 * @param end
	 *            the element on which to create a {@link TimeObservation}
	 * @param parentView
	 *            the graphical view representing the {@code parent}
	 * @return the created {@link TimeObservation}, or {@code null} if the creation failed
	 * @see #canCreateTimeElement(Element)
	 */
	public static TimeObservation createTimeObservation(EAnnotation end, DSemanticDecorator parentView) {
		return createTimeElement(end, event -> {
			Namespace container = UMLHelper.getPackagedContainer(event);

			// We can use the CommonDiagramServices to create TimeObservations: they aren't directly affected by
			// reorders, and are always created at the right location.
			TimeObservation result = (TimeObservation) COMMONS.createElement(container,
					UML.getTimeObservation().getName(), "packagedElement", //$NON-NLS-1$
					parentView);
			result.setEvent(event);
			return result;
		});
	}

	/**
	 * Creates a {@link TimeConstraint} on the provided {@code end}.
	 *
	 * @param end
	 *            the element on which to create a {@link TimeConstraint}
	 * @param parentView
	 *            the graphical view representing the {@code parent}
	 * @return the created {@link TimeConstraint}, or {@code null} if the creation failed
	 * @see #canCreateTimeElement(DSemanticDecorator)
	 */
	public static TimeConstraint createTimeConstraint(EAnnotation end, DSemanticDecorator parentView) {
		return createTimeElement(end, event -> {

			Interaction interaction = UML_HELPER.getOwningInteraction(event);
			TimeConstraint result = (TimeConstraint) COMMONS
					.createElement(interaction, UML.getTimeConstraint().getName(),
							UML.getNamespace_OwnedRule().getName(), parentView);
			result.getConstrainedElements().add(event);

			return result;
		});
	}

	/**
	 * Creates an time-related element on the provided {@code end}.
	 * <p>
	 * If an element is already exists the creation is not performed.
	 * </p>
	 *
	 * @param <T>
	 *            type of created element.
	 * @param end
	 *            the element on which to create the element
	 * @param factory
	 *            the function to create semantic element
	 * @return the created {@link TimeObservation}, or {@code null} if the creation failed
	 * @see #canCreateTimeElement(Element)
	 */
	private static <T extends PackageableElement> T createTimeElement(EAnnotation end, Function<NamedElement, T> factory) {
		NamedElement event = ORDER_SERVICES.getEndFragment(end);

		List<PackageableElement> existingElements = SequenceDiagramUMLHelper.getTimeElementsFromEvent(event);
		if (!existingElements.isEmpty()) { // already created.
			return null;
		}

		return factory.apply(event);
	}

	/**
	 * Gets the TimeObservation associated by this end.
	 *
	 * @param end
	 *            annotation used as end
	 * @return related time observation or null
	 */
	public static PackageableElement getAssociatedTimeElement(EAnnotation end) {
		return SequenceDiagramUMLHelper.getTimeElementFromEnd(end).orElse(null);
	}

	/**
	 * Gets the end source of provided duration element.
	 * <p>
	 * A duration element can be {@link DurationConstraint} or {@link DurationObservation}.
	 * </p>
	 * <p>
	 * The constraint must be related to 2 (and only 2) {@link OccurrenceSpecification}s
	 * in the same Interaction.
	 * </p>
	 *
	 * @param element
	 *            a duration constraint.
	 * @return end from interaction
	 */
	public static EAnnotation getDurationElementSource(PackageableElement element) {
		return getEndFromEventPair(getDurationEnds(element), true);
	}

	/**
	 * Gets the end target of provided duration element.
	 * <p>
	 * A duration element can be {@link DurationConstraint} or {@link DurationObservation}.
	 * </p>
	 * <p>
	 * The constraint must be related to 2 (and only 2) {@link OccurrenceSpecification}s
	 * in the same Interaction.
	 * </p>
	 *
	 * @param element
	 *            a duration constraint.
	 * @return end from interaction
	 */
	public static EAnnotation getDurationElementTarget(PackageableElement element) {
		return getEndFromEventPair(getDurationEnds(element), false);
	}

	private static List<? extends NamedElement> getDurationEnds(PackageableElement element) {

		// See warning above on Duration for this filtering.
		List<? extends NamedElement> result = Collections.emptyList();

		if (element instanceof DurationConstraint constraint) {
			// On creation,
			// org.eclipse.papyrus.uml.domain.services.create.ElementConfigurer
			// $ElementInitializerImpl
			// #caseDurationConstraint(DurationConstraint)
			// introduces an additional element (Owning interaction).
			// This is not conform with Specification 2.5 but as expected by the CEA.
			// Following code filter unexpected elements.
			result = constraint.getConstrainedElements().stream()
					.filter(OccurrenceSpecification.class::isInstance)
					.map(OccurrenceSpecification.class::cast)
					.toList();
		} else if (element instanceof DurationObservation observation) {
			result = observation.getEvents();
		}

		return result;
	}

	private static EAnnotation getEndFromEventPair(List<? extends NamedElement> values, boolean first) {
		if (values.size() < 2) { // Ill-formed. No value.
			return null;
		}
		int index = 0;
		if (!first) {
			index = 1;
		}
		EAnnotation result = null;
		if (values.get(index) instanceof OccurrenceSpecification occurence) {
			result = ORDER_SERVICES.findOccurrenceEnd(occurence);
		}
		return result;
	}

	/**
	 * Evaluates if it is possible to create an duration element between 2 elements.
	 * <p>
	 * Elements are assumed to belong to the same interaction. Only structural valid elements
	 * must be used: {@link EAnnotation}s attached to {@link OccurrenceSpecification}s,
	 * {@link Message}s and {@link ExecutionSpecification}s.
	 * </p>
	 * <p>
	 * An observation cannot be created if selected elements implies no duration.
	 * <ul>
	 * <li>found or lost Message (no start or stop)</li>
	 * <li>1 single {@link OccurrenceSpecification}</li>
	 * </ul>
	 * </p>
	 *
	 * @param source
	 *            one end of the duration
	 * @param target
	 *            the other end of the duration
	 * @return true if selection implies a duration
	 */
	public static boolean canCreateDurationElement(EObject source, EObject target) {
		boolean result = true; // For most of selection, it is possible
		if (source == target) {
			result = hasDuration(source);
		} else if (source instanceof Message message) { // may have no duration
			result = containsDuration(message, target);
		} else if (target instanceof Message message) {
			result = containsDuration(message, source);
		} // else executions have duration

		return result && areFragmentSiblings(adaptToOccurrence(source), adaptToOccurrence(target));
	}


	/**
	 * Provides an {@link OccurrenceSpecification} for a selected element.
	 * <p>
	 * Only supported case are {@link EAnnotation} end, {@link Message}
	 * and {@link ExecutionSpecification}.
	 * </p>
	 *
	 * @param element
	 *            element from selection
	 * @return the first event of element or the event itself
	 */
	private static OccurrenceSpecification adaptToOccurrence(EObject element) {
		OccurrenceSpecification result = null;
		if (element instanceof OccurrenceSpecification value) {
			result = value;
		} else if (element instanceof ExecutionSpecification exec) {
			result = exec.getStart();
		} else if (element instanceof Message message) {
			result = (OccurrenceSpecification) message.getSendEvent();
		} else if (element instanceof EAnnotation end
				&& ORDER_SERVICES.getEndFragment(end) instanceof OccurrenceSpecification value) {
			result = value;
		}
		return result;
	}

	/**
	 * Evaluates if 2 occurrences belong to the same container of fragment.
	 *
	 * @param source
	 *            first occurrence
	 *
	 * @param target
	 *            second occurrence
	 * @return true if siblings
	 */
	private static boolean areFragmentSiblings(OccurrenceSpecification source,
			OccurrenceSpecification target) {
		return source != null && target != null
				&& source.getEnclosingOperand() == target.getEnclosingOperand();
	}

	private static boolean containsDuration(Message message, EObject other) {
		// Other is different from Message and cannot be null.
		if (other instanceof EAnnotation pseudoEnd
				&& ORDER_SERVICES.getEndFragment(pseudoEnd) instanceof MessageEnd end) {
			return message != end.getMessage() || hasDuration(message);
		}
		// In other case, target is not related to the message.
		// It implies different events.
		return true;
	}

	private static boolean hasDuration(EObject element) {
		return element instanceof ExecutionSpecification
				// A message has duration when send and receive is observable.
				// (not lost/found, not gate)
				|| (element instanceof Message message
						&& message.getSendEvent() instanceof MessageOccurrenceSpecification
						&& message.getReceiveEvent() instanceof MessageOccurrenceSpecification);
	}

	/**
	 * Creates a {@link DurationConstraint} between the provided {@code source} and {@code target}.
	 * <p>
	 * Duration observation can be created between {@link OccurrenceSpecification} or related-elements
	 * like {@link Message} and {@link ExecutionSpecification}.
	 * </p>
	 * <p>
	 * This method must be called with parameters which validates
	 * {@link #canCreateDurationElement(EObject, EObject)} function.
	 * </p>
	 *
	 * @param source
	 *            one end of a duration
	 * @param target
	 *            the other end of a duration
	 * @param parentView
	 *            the graphical parent
	 * @return the created {@link DurationConstraint}
	 */
	public static DurationConstraint createDurationConstraint(EObject source, EObject target, DSemanticDecorator parentView) {
		return createDuration(source, target, context -> {
			Interaction interaction = UML_HELPER.getOwningInteraction(context);
			return (DurationConstraint) COMMONS
					.createElement(interaction, UML.getDurationConstraint().getName(),
							UML.getNamespace_OwnedRule().getName(), parentView);
		});
	}


	/**
	 * Creates a {@link DurationObservation} between the provided {@code source} and {@code target}.
	 * <p>
	 * Duration observation can be created between {@link OccurrenceSpecification} or related-elements
	 * like {@link Message} and {@link ExecutionSpecification}.
	 * </p>
	 * <p>
	 * This method must be called with parameters which validates
	 * {@link #canCreateDurationElement(EObject, EObject)} function.
	 * </p>
	 *
	 * @param source
	 *            one end of a duration
	 * @param target
	 *            the other end of a duration
	 * @param parentView
	 *            the graphical parent
	 * @return the created {@link DurationObservation}
	 */
	public static DurationObservation createDurationObservation(EObject source, EObject target, DSemanticDecorator view) {
		return createDuration(source, target, context -> {
			Namespace container = UMLHelper.getPackagedContainer(context);
			return (DurationObservation) COMMONS
					.createElement(container,
							UML.getDurationObservation().getName(),
							"packagedElement", //$NON-NLS-1$
							view);
		});
	}

	private static <T extends PackageableElement> T createDuration(EObject source, EObject target, Function<Element, T> factory) {
		DurationEventCandidate sourceSet = createDurationEventCandidate(source);
		DurationEventCandidate targetSet = createDurationEventCandidate(target);
		if (!sourceSet.isValid() || !targetSet.isValid()) {
			return null;
		}

		T result = factory.apply(sourceSet.getContext());

		// Order of cases matter.
		if (!sourceSet.applySingleEvent(result, targetSet)
				&& !sourceSet.applySharedEvent(result, targetSet)) {
			sourceSet.addInEvents(result);
			targetSet.addInEvents(result);
		}
		return result;
	}

	private static DurationEventCandidate createDurationEventCandidate(EObject element) {
		DurationEventCandidate result = null;
		if (element instanceof Message message) {
			result = new DurationEventCandidate(null, message, null);
		} else if (element instanceof ExecutionSpecification execution) {
			result = new DurationEventCandidate(null, null, execution);
		} else if (element instanceof EAnnotation end
				&& ORDER_SERVICES.getEndFragment(end) instanceof OccurrenceSpecification occurrence) {
			Message message = null;
			if (occurrence instanceof MessageOccurrenceSpecification mos) {
				message = mos.getMessage();
			}
			result = new DurationEventCandidate(occurrence, message,
					SequenceDiagramUMLHelper.getAssociatedExecution(occurrence));
		}
		return result;
	}

	/**
	 * Set of candidates to associate to a Duration Element.
	 *
	 * @param event
	 *            event if directly selected
	 * @param message
	 *            selected {@link Message} or related to event
	 * @param execution
	 *            selected {@link ExecutionSpecification} or related to event
	 */
	private record DurationEventCandidate(OccurrenceSpecification event, Message message, ExecutionSpecification execution) {

		boolean isValid() {
			return message != null || execution != null;
		}

		NamedElement getContext() {
			if (message != null) {
				return message;
			}
			return execution;
		}

		void addInEvents(PackageableElement result) {
			NamedElement element = message;
			if (execution != null) {
				element = execution;
			}

			boolean first = event == null || UML_HELPER.getSemanticStart(element) == event;
			addEventEnd(result, element, first);
		}

		boolean applySingleEvent(PackageableElement parent, DurationEventCandidate other) {
			NamedElement value = null;
			// Different parts of same element can be selected.
			if (message != null && message == other.message) {
				value = message;
			} else if (execution != null && execution == other.execution) {
				value = execution;
			}
			if (value == null) {
				return false;
			}
			addEvent(parent, value);

			// 1 element => no first event.
			return true;
		}

		boolean applySharedEvent(PackageableElement value, DurationEventCandidate other) {
			return applySharedMessageEvent(value, message, other.execution)
					|| applySharedMessageEvent(value, other.message, execution);
		}

	}

	private static boolean applySharedMessageEvent(PackageableElement value, Message message, ExecutionSpecification execution) {
		if (message == null || execution == null) {
			return false;
		}

		boolean result = false;
		// On shared event, pick opposite events.
		// Otherwise no duration to observe.

		if (message.getReceiveEvent() == execution.getStart()) {
			addEventEnd(value, message, true);
			addEventEnd(value, execution, false);
			result = true;
		} else if (message.getSendEvent() == execution.getFinish()) {
			addEventEnd(value, execution, true);
			addEventEnd(value, message, false);
			result = true;
		}

		return result;
	}

	private static void addEventEnd(PackageableElement parent, NamedElement event, boolean firstEvent) {
		// See warning above on DurationObservation.
		OccurrenceSpecification end;
		if (firstEvent) {
			end = (OccurrenceSpecification) UML_HELPER.getSemanticStart(event);
		} else {
			end = (OccurrenceSpecification) UML_HELPER.getSemanticFinish(event);
		}
		addEvent(parent, end);
	}

	private static void addEvent(PackageableElement parent, NamedElement event) {
		if (parent instanceof DurationConstraint constraint) {
			constraint.getConstrainedElements().add(event);
		} else if (parent instanceof DurationObservation observation) {
			observation.getEvents().add(event);
		}
	}

	/**
	 * Redirects event references from {@code previous} {@link OccurrenceSpecification} to the {@code next} {@link OccurrenceSpecification}.
	 * <p>
	 * This method must be consistent with the logic used in
	 * {@link org.eclipse.papyrus.uml.domain.services.destroy.ElementDependencyCollector.DestroyDependencyCollectorSwitch#caseOccurrenceSpecification(OccurrenceSpecification)}
	 * </p>
	 *
	 * @param previous
	 *            the fragment for which references must be deleted
	 * @param next
	 *            the fragment for which references must be added
	 */
	public static void replaceFragmentReferences(OccurrenceSpecification previous, OccurrenceSpecification next) {
		List<?> temporals = SequenceDiagramUMLHelper.getTemporalElementsFromEvent(previous);
		temporals.forEach(element -> {
			if (element instanceof TimeObservation timeObs) {
				timeObs.setEvent(next);
			} else {
				List<? super OccurrenceSpecification> refs;
				if (element instanceof DurationObservation durationObs) {
					refs = durationObs.getEvents();
				} else { // Duration or Time Constraints
					refs = ((Constraint) element).getConstrainedElements();
				}
				refs.set(refs.indexOf(previous), next);
			}
		});

		// To replace opposite reference, we need to copy the EList.
		// Otherwise, ConcurrentModificationException will rise.
		List.copyOf(previous.getToBefores()).forEach(ordering -> ordering.setAfter(next));
		List.copyOf(previous.getToAfters()).forEach(ordering -> ordering.setBefore(next));
	}

}
