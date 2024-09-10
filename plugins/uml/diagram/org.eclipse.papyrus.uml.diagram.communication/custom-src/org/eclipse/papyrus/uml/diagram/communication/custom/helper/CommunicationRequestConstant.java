/*****************************************************************************
 * Copyright (c) 2010, 2015 CEA, Christian W. Damus, and others
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
 *   Atos Origin - Initial API and implementation
 *  Saadia DHOUIB (CEA LIST) saadia.dhouib@cea.fr - adapted from sequence diagram
 *   Christian W. Damus - bug 468175
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.communication.custom.helper;

/**
 * Constants used in the communication diagram
 */
public interface CommunicationRequestConstant {

	/**
	 * A constant representing the container of an interactionFragment. It can
	 * be an Interaction or an InteractionOperand
	 */
	public static final String INTERACTIONFRAGMENT_CONTAINER = "InteractionFragment Container"; //$NON-NLS-1$

	/**
	*
	*/
	public static final String SOURCE_MODEL_CONTAINER = "Source model container"; //$NON-NLS-1$

	/**
	*
	*/
	public static final String TARGET_MODEL_CONTAINER = "Target model container"; //$NON-NLS-1$

	/** A request parameter indicating the connectable element that a lifeline represents. */
	public static final String REPRESENTS = "Lifeline::represents"; //$NON-NLS-1$
}
