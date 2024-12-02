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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.CommonDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.ViewpointHelpers;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.utils.SequenceDiagramUMLHelper;
import org.eclipse.papyrus.uml.domain.services.EMFUtils;
import org.eclipse.sirius.diagram.sequence.description.ObservationPointMapping;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.DurationConstraint;
import org.eclipse.uml2.uml.DurationObservation;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.OccurrenceSpecification;
import org.eclipse.uml2.uml.Package;
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
	public static boolean canCreateTimeObservation(DSemanticDecorator view) {
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
	 * @see #canCreateTimeObservation(Element)
	 */
	public static EObject createTimeObservation(EAnnotation end, DSemanticDecorator parentView) {
		NamedElement event = ORDER_SERVICES.getEndFragment(end);

		List<TimeObservation> existingObservations = SequenceDiagramUMLHelper.getTimeObservationsFromEvent(event);
		if (!existingObservations.isEmpty()) { // already created.
			return null;
		}

		return createTimeObservationWithEvent(event, parentView);
	}

	/**
	 * Gets the TimeObservation associated by this end.
	 *
	 * @param end
	 *            annotation used as end
	 * @return related time observation or null
	 */
	public static TimeObservation getAssociatedTimeObservation(EAnnotation end) {
		return SequenceDiagramUMLHelper.getTimeObservationFromEnd(end).orElse(null);
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
	private static TimeObservation createTimeObservationWithEvent(NamedElement event, DSemanticDecorator parentView) {
		Package ancestorPackage = EMFUtils.getAncestor(Package.class, event);
		CommonDiagramServices commonDiagramServices = new CommonDiagramServices();
		// We can use the CommonDiagramServices to create TimeObservations: they aren't directly affected by
		// reorders, and are always created at the right location.
		TimeObservation result = (TimeObservation) commonDiagramServices.createElement(ancestorPackage, UML.getTimeObservation().getName(), UML.getPackage_PackagedElement().getName(), parentView);
		result.setEvent(event);
		return result;
	}


	/**
	 * Creates a {@link DurationConstraint} between the provided {@code source} and {@code target}.
	 * <p>
	 * Duration constraints can be created between {@link OccurrenceSpecification}. These elements are represented
	 * by <i>observation point</i> mappings, which are defined on {@link EAnnotation}.
	 * </p>
	 *
	 * @param source
	 *            the source {@link EAnnotation}
	 * @param target
	 *            the target {@link EAnnotation}
	 * @param parentView
	 *            the graphical parent
	 * @return the created {@link DurationConstraint}
	 */
	public static DurationConstraint createDurationConstraint(EAnnotation source, EAnnotation target, DSemanticDecorator parentView) {
		InteractionFragment sourceFragment = ORDER_SERVICES.getEndFragment(source);
		InteractionFragment targetFragment = ORDER_SERVICES.getEndFragment(target);
		Interaction interaction = UML_HELPER.getOwningInteraction(sourceFragment);
		DurationConstraint result = (DurationConstraint) new CommonDiagramServices().createElement(interaction, UML.getDurationConstraint().getName(), UML.getNamespace_OwnedRule().getName(), parentView);
		result.getConstrainedElements().add(sourceFragment);
		result.getConstrainedElements().add(targetFragment);
		// See warning above on DurationConstraint

		return result;
	}

	/**
	 * Gets the end source of provided constraint.
	 * <p>
	 * The constraint must be related to 2 (and only 2) {@link OccurrenceSpecification}s
	 * in the same Interaction.
	 * </p>
	 *
	 * @param element
	 *            a duration constraint.
	 * @return end from interaction
	 */
	public static EAnnotation getDurationConstraintSource(DurationConstraint element) {
		return getDurationEnd(element, true);
	}

	/**
	 * Gets the end target of provided constraint.
	 * <p>
	 * The constraint must be related to 2 (and only 2) {@link OccurrenceSpecification}s
	 * in the same Interaction.
	 * </p>
	 *
	 * @param element
	 *            a duration constraint.
	 * @return end from interaction
	 */
	public static EAnnotation getDurationConstraintTarget(DurationConstraint element) {
		return getDurationEnd(element, false);
	}

	private static EAnnotation getDurationEnd(DurationConstraint element, boolean first) {
		// See warning above on DurationConstraint for this filtering.

		// On creation,
		// org.eclipse.papyrus.uml.domain.services.create.ElementConfigurer
		// $ElementInitializerImpl
		// #caseDurationConstraint(DurationConstraint)
		// introduces an additional element (Owning interaction).
		// This is not conform with Specification 2.5 but as expected by the CEA.
		// Following code filter unexpected elements.
		List<OccurrenceSpecification> ends = element.getConstrainedElements().stream()
				.filter(OccurrenceSpecification.class::isInstance)
				.map(OccurrenceSpecification.class::cast)
				.toList();

		// For a well-formed duration, only 2 OccurrenceSpecification are expressed.
		return getEndFromEventPair(ends, first);
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
	 * Gets the end source of provided observation.
	 *
	 * @param element
	 *            a duration constraint.
	 * @return end from interaction
	 */
	public static EAnnotation getDurationObservationSource(DurationObservation element) {
		return getEndFromEventPair(element.getEvents(), true);
	}

	/**
	 * Gets the end source of provided observation.
	 *
	 * @param element
	 *            a duration constraint.
	 * @return end from interaction
	 */
	public static EAnnotation getDurationObservationTarget(DurationObservation element) {
		return getEndFromEventPair(element.getEvents(), false);
	}

	/**
	 * Evaluates if it is possible to create an duration observation between 2 elements.
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
	public static boolean canCreateDurationObservation(EObject source, EObject target) {
		boolean result = true; // For most of selection, it is possible
		if (source == target) {
			result = hasDuration(source);
		} else if (source instanceof Message message) { // may have no duration
			result = containsDuration(message, target);
		} else if (target instanceof Message message) {
			result = containsDuration(message, source);
		}
		return result;
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
	 * Duration constraints can be created between {@link OccurrenceSpecification}.
	 * These elements are represented
	 * by <i>observation point</i> mappings, which are defined on {@link EAnnotation}.
	 * </p>
	 * <p>
	 * This method must be called with parameters which validates
	 * 'canCreateDurationObservation' function.
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
	public static DurationObservation createDurationObservation(EObject source, EObject target, DSemanticDecorator view) {
		DurationEventCandidate sourceSet = createDurationEventCandidate(source);
		DurationEventCandidate targetSet = createDurationEventCandidate(target);
		if (!sourceSet.isValid() || !targetSet.isValid()) {
			return null;
		}

		Package container = EMFUtils.getAncestor(Package.class, source);
		DurationObservation result = (DurationObservation) new CommonDiagramServices()
				.createElement(container,
						UML.getDurationObservation().getName(),
						UML.getPackage_PackagedElement().getName(),
						view);

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
	 * Set of candidates to associate to a Duration Observation.
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

		void addInEvents(DurationObservation result) {
			NamedElement element = message;
			if (execution != null) {
				element = execution;
			}

			boolean first = event == null || UML_HELPER.getSemanticStart(element) == event;
			addEvent(result, element, first);
		}

		boolean applySingleEvent(DurationObservation value, DurationEventCandidate other) {
			NamedElement single = null;
			// Different parts of same element can be selected.
			if (message != null && message == other.message) {
				single = message;
			} else if (execution != null && execution == other.execution) {
				single = execution;
			}
			if (single == null) {
				return false;
			}
			value.getEvents().add(single);
			// 1 element => no first event.
			return true;
		}

		boolean applySharedEvent(DurationObservation value, DurationEventCandidate other) {
			return applySharedMessageEvent(value, message, other.execution)
					|| applySharedMessageEvent(value, other.message, execution);
		}

	}

	private static boolean applySharedMessageEvent(DurationObservation value, Message message, ExecutionSpecification execution) {
		if (message == null || execution == null) {
			return false;
		}

		boolean result = false;
		// On shared event, pick opposite events.
		// Otherwise no duration to observe.

		if (message.getReceiveEvent() == execution.getStart()) {
			addEvent(value, message, true);
			addEvent(value, execution, false);
			result = true;
		} else if (message.getSendEvent() == execution.getFinish()) {
			addEvent(value, execution, true);
			addEvent(value, message, false);
			result = true;
		}

		return result;
	}

	private static void addEvent(DurationObservation parent, NamedElement event, boolean firstEvent) {
		// See warning above on DurationObservation.
		OccurrenceSpecification end;
		if (firstEvent) {
			end = (OccurrenceSpecification) UML_HELPER.getSemanticStart(event);
		} else {
			end = (OccurrenceSpecification) UML_HELPER.getSemanticFinish(event);
		}
		parent.getEvents().add(end);
	}

}
