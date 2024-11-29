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
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
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
public class SequenceDiagramObservationServices {

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
	 * Returns {@code true} if a {@link TimeObservation} can be created on the provided {@code parent}.
	 *
	 * @param parent
	 *            the element to check
	 * @return {@code true} if a {@link TimeObservation} can be created on the provided {@code parent}
	 */
	public static boolean canCreateTimeObservation(DSemanticDecorator view) {
		// boolean result = false;
		// if (parent instanceof ExecutionSpecification || parent instanceof Message) {
		// result = umlHelper.getTimeObservationFromEvent(umlHelper.getSemanticStart(parent)).isEmpty()
		// || umlHelper.getTimeObservationFromEvent(umlHelper.getSemanticFinish(parent)).isEmpty();
		// }
		// return result;
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
		result.getFirstEvents().add(Boolean.TRUE);
		result.getConstrainedElements().add(targetFragment);
		result.getFirstEvents().add(Boolean.FALSE);
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
		return ORDER_SERVICES.findOccurrenceEnd(getDurationEnd(element, true));
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
	public static EAnnotation getDurationConstraintTarget(DurationConstraint element) {
		return ORDER_SERVICES.findOccurrenceEnd(getDurationEnd(element, false));
	}

	private static OccurrenceSpecification getDurationEnd(DurationConstraint element, boolean start) {
		// Warning:
		// There is an issue in
		// org.eclipse.papyrus.uml.domain.services.create.ElementConfigurer$ElementInitializerImpl#caseDurationConstraint(DurationConstraint)
		// The container is added 'constrainedElements'
		// From Spec, there should only 1 or 2 elements indicating which end to use.

		List<OccurrenceSpecification> ends = element.getConstrainedElements().stream()
				.filter(OccurrenceSpecification.class::isInstance)
				.map(OccurrenceSpecification.class::cast)
				.toList();
		OccurrenceSpecification result = null;

		// For a well-formed duration, only 2 OccurrenceSpecification are expressed.
		if (ends.size() == 2 && element.getFirstEvents().size() == 2) {
			// In theory, element.firstEvents are exclusive.
			// So the information is duplicated in the MOF.
			// A flag was enough.
			@SuppressWarnings("boxing") // protected by EMF.
			boolean firstEvent = element.getFirstEvents().get(0);

			if (start == firstEvent) {
				result = ends.get(0);
			} else {
				result = ends.get(1);
			}
		}
		return result;
	}

}
