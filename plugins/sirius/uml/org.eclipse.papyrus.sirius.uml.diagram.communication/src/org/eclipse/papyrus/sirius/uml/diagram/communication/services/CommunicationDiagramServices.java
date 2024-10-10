/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.communication.services;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.uml.diagram.common.core.services.AbstractDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.CommonDiagramServices;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.DomainBasedEdgeServices;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.MessageSort;
import org.eclipse.uml2.uml.Observation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.UMLFactory;

/**
 * Services for the Communication diagram.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class CommunicationDiagramServices extends AbstractDiagramServices {

	/**
	 * Service used to check if an {@link Observation} can be created under the specified container.
	 * 
	 * @param container
	 *            the container that should contains the new object to create.
	 * @param objectToCreate
	 *            the EClass defining the type of the object to create.
	 * @param containmentReferenceName
	 *            the name ofthe containment reference to use to attach the new element to the model
	 * @return <code>true</code> if the {@link Observation} can be created; <code>false</code> otherwise.
	 */
	public boolean canCreateObservationCOD(EObject container, EClass objectToCreate, String containmentReferenceName) {
		CommonDiagramServices commonDiagramServices = new CommonDiagramServices();
		return commonDiagramServices.canCreate(getPackageContainer(container), objectToCreate, containmentReferenceName);
	}

	/**
	 * This method is used to create an {@link Observation} in the Communication Diagram.
	 * 
	 * @param parent
	 *            the semantic element used to retrieve the nearest parent {@link Package}
	 * @param type
	 *            the type of element to create
	 * @param referenceName
	 *            the name of the containment reference
	 * @param targetView
	 *            the target view
	 * @return a new instance of {@link Observation} or <code>null</code> if the creation failed
	 */
	public EObject createObservationCOD(Element parent, String type, String referenceName, DSemanticDecorator targetView) {
		CommonDiagramServices commonDiagramServices = new CommonDiagramServices();
		return commonDiagramServices.createElement(getPackageContainer(parent), type, referenceName, targetView);
	}

	/**
	 * Used to retrieve {@link Observation} to display according to the semantic context.
	 * <ul>
	 * <li>For a non-{@link Package}, Observations that are contained by its parent Package.</li>
	 * <li>For a {@link Package}, Observations from the Package#packagedElements feature.</li>
	 * </ul>
	 * 
	 * @see org.eclipse.papyrus.uml.service.types.helper.ObservationEditHelper
	 * 
	 * @param semanticContext
	 *            the context in which Observations should be displayed.
	 * @return all Observations that can be displayed in the specified context
	 */
	public Collection<Observation> getObservationCandidates(final EObject semanticContext) {
		Collection<Observation> observations = Collections.emptyList();
		if (semanticContext == null) {
			return observations;
		}
		if (semanticContext instanceof Package) {
			EList<PackageableElement> packageElements = ((Package) semanticContext).getPackagedElements();
			observations = packageElements.stream() //
					.filter(Observation.class::isInstance) //
					.map(Observation.class::cast) //
					.collect(Collectors.toList()); //
		} else {
			observations = getObservationCandidates(getPackageContainer(semanticContext));
		}
		return observations;
	}

	/**
	 * Service used to create a domain base edge.
	 *
	 * @param source
	 *            the semantic source
	 * @param target
	 *            the semantic target
	 * @param type
	 *            the new element type
	 * @param containementReferenceName
	 *            the containment reference name
	 * @param sourceNode
	 *            the source {@link DSemanticDecorator} of the new edge
	 * @param targetNode
	 *            the target {@link DSemanticDecorator} of the new edge
	 * @return a new element or <code>null</code>
	 */
	public EObject createDomainBasedEdgeCOD(EObject source, EObject target, String type, String containementReferenceName, DSemanticDecorator sourceView, DSemanticDecorator targetView) {
		DomainBasedEdgeServices domainBasedEdgeServices = new DomainBasedEdgeServices();
		EObject newEdge = domainBasedEdgeServices.createDomainBasedEdge(source, target, type, containementReferenceName, sourceView, targetView);
		if (newEdge instanceof Message) {
			initializeMessage((Message) newEdge, source, target);
		}
		return newEdge;
	}

	/**
	 * Initialize new Message between two {@link Lifeline} by creating send/receiveEvent.
	 * 
	 * @param message
	 *            the message to initialize,
	 * @param source
	 *            the {@link Lifeline} source of the message,
	 * @param target
	 *            the {@link Lifeline} target of the message,
	 */
	private void initializeMessage(Message message, EObject source, EObject target) {
		message.setMessageSort(MessageSort.ASYNCH_CALL_LITERAL);
		// set Source
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
		// set Target
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
	 * Return the first element in the container hierarchy of the given element which is a {@link Package}.
	 *
	 * @param element
	 *            the starting point
	 * @return the first containing element which is a {@link Package}, or <code>null</code> if none
	 */
	private Package getPackageContainer(final EObject element) {
		while (element != null) {
			if (element instanceof Package) {
				return (Package) element;
			} else {
				return getPackageContainer(element.eContainer());
			}
		}
		return null;
	}
}
