/*****************************************************************************
 * Copyright (c) 2018 CEA LIST, EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper.advice;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.GetEditContextCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.AbstractEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.GetEditContextRequest;
import org.eclipse.papyrus.uml.service.types.utils.SequenceRequestConstant;
import org.eclipse.uml2.uml.DurationConstraint;
import org.eclipse.uml2.uml.DurationObservation;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.GeneralOrdering;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.OccurrenceSpecification;

/**
 * <p>
 * Abstract advice for links connecting {@link OccurrenceSpecification}
 * ({@link DurationConstraint}, {@link DurationObservation}, {@link GeneralOrdering})
 * </p>
 */
public abstract class AbstractOccurrenceLinkEditHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * <p>
	 * Retrieve the Source Element from a request (Either a {@link CreateRelationshipRequest}, or a
	 * related request, e.g. a {@link ConfigureRequest} occurring after a {@link CreateRelationshipRequest}).
	 * </p>
	 *
	 * <p>
	 * The source element may correspond to the element on which the relationship is created,
	 * or a specific occurrence specification (For example, when creating a link on an {@link ExecutionSpecification},
	 * this method may return the specific start or finish {@link OccurrenceSpecification} instead of the {@link ExecutionSpecification}).
	 * </p>
	 *
	 * @param request
	 *            The Edit Request (Typically a {@link CreateRelationshipRequest} or {@link ConfigureRequest})
	 * @return
	 * 		The source element for this request, or <code>null</code> if no such element can be found
	 */
	protected Element getSourceElement(AbstractEditCommandRequest request) {
		if (request instanceof CreateRelationshipRequest) {
			return getSourceElement((CreateRelationshipRequest) request);
		}
		return getSourceOrTarget(request.getParameter(CreateRelationshipRequest.SOURCE), request.getParameter(SequenceRequestConstant.SOURCE_OCCURRENCE));
	}

	/**
	 * <p>
	 * Retrieve the Target Element from a request (Either a {@link CreateRelationshipRequest}, or a
	 * related request, e.g. a {@link ConfigureRequest} occurring after a {@link CreateRelationshipRequest}).
	 * </p>
	 *
	 * <p>
	 * The target element may correspond to the element on which the relationship is created,
	 * or a specific occurrence specification (For example, when creating a link on an {@link ExecutionSpecification},
	 * this method may return the specific start or finish {@link OccurrenceSpecification} instead of the {@link ExecutionSpecification}).
	 * </p>
	 *
	 * @param request
	 *            The Edit Request (Typically a {@link CreateRelationshipRequest} or {@link ConfigureRequest})
	 * @return
	 * 		The target element for this request, or <code>null</code> if no such element can be found
	 */
	protected Element getTargetElement(AbstractEditCommandRequest request) {
		if (request instanceof CreateRelationshipRequest) {
			return getTargetElement((CreateRelationshipRequest) request);
		}
		return getSourceOrTarget(request.getParameter(CreateRelationshipRequest.TARGET), request.getParameter(SequenceRequestConstant.TARGET_OCCURRENCE));
	}

	/**
	 * <p>
	 * Retrieve the Source Element from a {@link CreateRelationshipRequest}
	 * </p>
	 *
	 * <p>
	 * The source element may correspond to the element on which the relationship is created,
	 * or a specific occurrence specification (For example, when creating a link on an {@link ExecutionSpecification},
	 * this method may return the specific start or finish {@link OccurrenceSpecification} instead of the {@link ExecutionSpecification}).
	 * </p>
	 *
	 * @param request
	 *            The Edit Request (Typically a {@link CreateRelationshipRequest} or {@link ConfigureRequest})
	 * @return
	 * 		The source element for this request, or <code>null</code> if no such element can be found
	 */
	protected Element getSourceElement(CreateRelationshipRequest request) {
		return getSourceOrTarget(request.getSource(), request.getParameter(SequenceRequestConstant.SOURCE_OCCURRENCE));
	}

	/**
	 * <p>
	 * Retrieve the Target Element from a {@link CreateRelationshipRequest}
	 * </p>
	 *
	 * <p>
	 * The target element may correspond to the element on which the relationship is created,
	 * or a specific occurrence specification (For example, when creating a link on an {@link ExecutionSpecification},
	 * this method may return the specific start or finish {@link OccurrenceSpecification} instead of the {@link ExecutionSpecification}).
	 * </p>
	 *
	 * @param request
	 *            The Edit Request (Typically a {@link CreateRelationshipRequest} or {@link ConfigureRequest})
	 * @return
	 * 		The source element for this request, or <code>null</code> if no such element can be found
	 */
	protected Element getTargetElement(CreateRelationshipRequest request) {
		return getSourceOrTarget(request.getTarget(), request.getParameter(SequenceRequestConstant.TARGET_OCCURRENCE));
	}

	/**
	 * <p>
	 * For Links connecting {@link OccurrenceSpecification}s, the {@link CreateRelationshipRequest} will typically contain two distinct parameters
	 * for their source, and two for their target. The first one will represent the source/target visual element,
	 * and the second will represent the real event ({@link OccurrenceSpecification}); typically the start/finish
	 * {@link OccurrenceSpecification} of the graphical element.
	 * </p>
	 * <p>
	 * This method will take these two values (Which may or may not be valid {@link Element} or {@link OccurrenceSpecification},
	 * thus the {@link Object} type), and will return the one that should be used as the source/target of the new Link
	 * </p>
	 *
	 * @param rawElement
	 *            The element that is the source or target of a {@link CreateRelationshipRequest}. (Typically the UML Element
	 *            represented in a Diagram, i.e. an {@link ExecutionSpecification} or a {@link Message}. It may also be
	 *            an {@link OccurrenceSpecification}, e.g. if the Link is created directly from the Model Explorer).
	 * @param occurrenceParam
	 *            The specific {@link OccurrenceSpecification} referenced by the {@link CreateRelationshipRequest}. Typical values
	 *            may be <code>null</code> (The request doesn't specify which event should be used), an {@link OccurrenceSpecification}
	 *            referenced by the <code>rawElement</code> (Start/Finish occurrence, or Send/Receive event), or the same object as
	 *            <code>rawElement</code>.
	 * @return
	 * 		The source or target semantic element for a Link (Which may be different from
	 *         the graphical element, since {@link OccurrenceSpecification} are not displayed on diagrams)
	 *
	 * @see CreateRelationshipRequest#getSource()
	 * @see CreateRelationshipRequest#getTarget()
	 * @see SequenceRequestConstant#SOURCE_OCCURRENCE
	 * @see SequenceRequestConstant#TARGET_OCCURRENCE
	 * @see CreateRelationshipRequest#SOURCE
	 * @see CreateRelationshipRequest#TARGET
	 */
	protected Element getSourceOrTarget(Object rawElement, Object occurrenceParam) {
		OccurrenceSpecification occurrence;
		if (occurrenceParam instanceof OccurrenceSpecification) {
			occurrence = (OccurrenceSpecification) occurrenceParam;
		} else if (rawElement instanceof Element) {
			return (Element) rawElement;
		} else {
			return null;
		}

		Element element;
		if (rawElement instanceof Element) {
			element = (Element) rawElement;
		} else {
			element = null;
		}

		// If both the element and occurrence are set, make sure the occurrence belongs to the element
		// That's required, because the occurrence parameter might have been added to the request
		// when its owner was hovered, and then it wasn't removed when we hovered a different element.
		if (element instanceof ExecutionSpecification) {
			ExecutionSpecification execSpec = (ExecutionSpecification) element;
			if (execSpec.getStart() == occurrence || execSpec.getFinish() == occurrence) {
				return occurrence;
			}
		} else if (element instanceof Message) {
			Message message = (Message) element;
			if (message.getSendEvent() == occurrence || message.getReceiveEvent() == occurrence) {
				return occurrence;
			}
		} else if (element == occurrence) {
			return occurrence;
		}

		return element;
	}

	@Override
	protected ICommand getBeforeEditContextCommand(GetEditContextRequest request) {
		if (request.getEditCommandRequest() instanceof CreateRelationshipRequest) {
			CreateRelationshipRequest createRequest = (CreateRelationshipRequest) request.getEditCommandRequest();
			EObject source = getSourceElement(createRequest);
			EObject target = getTargetElement(createRequest);
			if (createRequest.getSource() != null && !isValid(source)) {
				return UnexecutableCommand.INSTANCE;
			}
			if (createRequest.getTarget() != null && !isValid(target)) {
				return UnexecutableCommand.INSTANCE;
			}
			if (source != null && target != null) {
				Object editContext = request.getEditContext();
				if (editContext instanceof Element) {
					Element creationContainer = getCreationContainer((Element) editContext);
					if (creationContainer != null) {
						GetEditContextCommand command = new GetEditContextCommand(request);
						command.setEditContext(creationContainer);
						return command;
					}
				}
			}
		}
		return super.getBeforeEditContextCommand(request);
	}

	protected boolean isValid(EObject sourceOrTarget) {
		return sourceOrTarget instanceof OccurrenceSpecification;
	}

	protected abstract Element getCreationContainer(Element targetElement);

}
