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
package org.eclipse.papyrus.sirius.uml.diagram.activity.tests;

/**
 * This class provides reconnection tools ids for Sirius Activity Diagram odesign.
 *
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public final class ReconnectionToolsIds {

	/*----------------------------------------------The Edge reconnection target tools IDs----------------------------------------------*/

	public static final String RECONNECT_TARGET__CONTROL_FLOW__TOOL = "ReconnectControlFlowTarget"; //$NON-NLS-1$

	public static final String RECONNECT_TARGET__LINK__TOOL = "ReconnectLinkTarget"; //$NON-NLS-1$

	public static final String RECONNECT_TARGET__OBJECT_FLOW__TOOL = "ReconnectObjectFlowTarget"; //$NON-NLS-1$

	/*----------------------------------------------The Edge reconnection source tools IDs----------------------------------------------*/

	public static final String RECONNECT_SOURCE__CONTROL_FLOW__TOOL = "ReconnectControlFlowSource"; //$NON-NLS-1$

	public static final String RECONNECT_SOURCE__LINK__TOOL = "ReconnectLinkSource"; //$NON-NLS-1$

	public static final String RECONNECT_SOURCE__OBJECT_FLOW__TOOL = "ReconnectObjectFlowSource"; //$NON-NLS-1$

	private ReconnectionToolsIds() {
		// to prevent instantiation
	}
}
