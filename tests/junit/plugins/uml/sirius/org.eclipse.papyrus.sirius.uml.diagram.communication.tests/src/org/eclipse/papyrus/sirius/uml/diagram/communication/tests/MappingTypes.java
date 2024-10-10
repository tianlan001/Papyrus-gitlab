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
package org.eclipse.papyrus.sirius.uml.diagram.communication.tests;

/**
 * This class provides mapping types for Sirius Communication Diagram odesign.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class MappingTypes {

	private MappingTypes() {
		// to prevent instantiation
	}

	/*----------------------------------------------The Node mapping IDs----------------------------------------------*/

	public static final String COMMENT_NODE_TYPE = "COD_Comment"; //$NON-NLS-1$

	public static final String CONSTRAINT_NODE_TYPE = "COD_Constraint"; //$NON-NLS-1$

	public static final String DURATION_OBSERVATION_NODE_TYPE = "COD_DurationObservation"; //$NON-NLS-1$

	public static final String INTERACTION_NODE_TYPE = "COD_Interaction"; //$NON-NLS-1$

	public static final String INTERACTION_NODE_COD_COMPARTMENTS_TYPE = "COD_InteractionContentCompartment"; //$NON-NLS-1$

	public static final String LIFELINE_NODE_TYPE = "COD_Lifeline"; //$NON-NLS-1$

	public static final String TIME_OBSERVATION_NODE_TYPE = "COD_TimeObservation"; //$NON-NLS-1$

	// /*----------------------------------------------The Edge mapping IDs----------------------------------------------*/

	public static final String MESSAGE_EDGE_TYPE = "COD_Message"; //$NON-NLS-1$

}
