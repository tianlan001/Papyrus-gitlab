/*****************************************************************************
 * Copyright (c) 2017, 2018 CEA, Christian W. Damus, and others
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 530201
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.utils;

import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.OccurrenceSpecification;

/**
 * Constants used in the sequence diagram
 *
 * @since 3.0
 */
public interface SequenceRequestConstant {

	/** A constant representing the container of an interactionFragment. It can be an Interaction or an InteractionOperand */
	public static final String INTERACTIONFRAGMENT_CONTAINER = "InteractionFragment Container"; //$NON-NLS-1$

	/** The occurrence specification(s) which are the nearest from a creation request */
	public static final String NEAREST_OCCURRENCE_SPECIFICATION = "Nearest occurrence specification"; //$NON-NLS-1$

	/**
	 * Add in the request that a element cover another element.
	 *
	 * @deprecated Since version 3.1, use the {@link #COVERED_LIFELINES}, instead.
	 * @see RequestParameterUtils#getCoveredLifelines(IEditCommandRequest)
	 * @see RequestParameterUtils#setCoveredLifelines(IEditCommandRequest, Iterable)
	 */
	@Deprecated
	public static final String COVERED = "Element Covers this one"; //$NON-NLS-1$

	/**
	 * Hint providing a collection of covered lifelines ({@code {@literal Collection<Lifeline>}}).
	 *
	 * @since 4.0
	 * @see RequestParameterUtils#getCoveredLifelines(IEditCommandRequest)
	 * @see RequestParameterUtils#setCoveredLifelines(IEditCommandRequest, Iterable)
	 */
	public static final String COVERED_LIFELINES = "PAPYRUS_SEQD_COVERED_LIFELINES"; //$NON-NLS-1$

	/** when you create a message, sometimes you create after another event **/
	public static final String PREVIOUS_EVENT = "previous event"; //$NON-NLS-1$
	/** when you create a message, sometimes you create after another event for the target **/
	public static final String SECOND_PREVIOUS_EVENT = "second previous event"; //$NON-NLS-1$

	public static final String MESSAGE_SENTEVENT_REPLACE_EXECUTIONEVENT = "MESSAGE_SENTEVENT_REPLACE_EXECUTIONEVENT";
	public static final String MESSAGE_RECEIVEEVENT_REPLACE_EXECUTIONEVENT = "MESSAGE_RECEIVEEVENT_REPLACE_EXECUTIONEVENT";

	/** the start of Execution specification can be replace by an event of a message */
	public static final String REPLACE_EXECUTION_SPECIFICATION_START = "REPLACE_EXECUTION_SPECIFICATION_START"; //$NON-NLS-1$
	/** the finish of Execution specification can be replace by an event of a message */
	public static final String REPLACE_EXECUTION_SPECIFICATION_FINISH = "REPLACE_EXECUTION_SPECIFICATION_FINISH"; //$NON-NLS-1$

	/**
	 * <p>
	 * When creating a Relationship to an {@link ExecutionSpecification} or a {@link Message},
	 * this parameter represents the target {@link OccurrenceSpecification}. It may be the start or finish
	 * occurrence of an {@link ExecutionSpecification}, or the send or receive event of a {@link Message}.
	 * </p>
	 *
	 * <p>
	 * If this parameter is set, the source of the relationship should reference the {@link OccurrenceSpecification} rather than the original {@link Message} or {@link ExecutionSpecification}.
	 * </p>
	 */
	public static final String SOURCE_OCCURRENCE = "Source Occurrence";

	/**
	 * <p>
	 * When creating a Relationship to an {@link ExecutionSpecification} or a {@link Message},
	 * this parameter represents the source {@link OccurrenceSpecification}. It may be the start or finish
	 * occurrence of an {@link ExecutionSpecification}, or the send or receive event of a {@link Message}.
	 * </p>
	 *
	 * <p>
	 * If this parameter is set, the target of the relationship should reference the {@link OccurrenceSpecification} rather than the original {@link Message} or {@link ExecutionSpecification}.
	 * </p>
	 */
	public static final String TARGET_OCCURRENCE = "Target Occurrence";

}
