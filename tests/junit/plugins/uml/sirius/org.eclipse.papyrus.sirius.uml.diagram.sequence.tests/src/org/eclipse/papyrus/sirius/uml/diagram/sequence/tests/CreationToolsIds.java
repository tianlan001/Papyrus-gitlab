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
package org.eclipse.papyrus.sirius.uml.diagram.sequence.tests;

/**
 * This class provides creation tools ids for Sirius Sequence Diagram odesign.
 * 
 * @author <a href="mailto:gwendal.daniel@obeosoft.com>Gwendal Daniel</a>
 */
public class CreationToolsIds {

	private CreationToolsIds() {
		// to prevent instantiation
	}

	/*----------------------------------------------The Node creation tools IDs----------------------------------------------*/

	public static final String CREATE_LIFELINE_TOOL = "CreateLifelineTool"; //$NON-NLS-1$

	public static final String CREATE_ACTION_EXECUTION_SPECIFICATION_TOOL = "CreateActionExecutionSpecification"; //$NON-NLS-1$

	public static final String CREATE_BEHAVIOR_EXECUTION_SPECIFICATION_TOOL = "CreateBehaviorExecutionSpecification"; //$NON-NLS-1$

	/*----------------------------------------------The Edge creation tools IDs----------------------------------------------*/

	public static final String CREATE_MESSAGE_SYNC_TOOL = "CreateMessageSync"; //$NON-NLS-1$

	public static final String CREATE_MESSAGE_REPLY_TOOL = "CreateMessageReply"; //$NON-NLS-1$
}
