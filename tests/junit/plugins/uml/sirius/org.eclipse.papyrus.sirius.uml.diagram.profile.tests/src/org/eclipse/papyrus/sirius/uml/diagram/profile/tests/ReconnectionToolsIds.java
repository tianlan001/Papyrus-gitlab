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
package org.eclipse.papyrus.sirius.uml.diagram.profile.tests;

/**
 * This class provides reconnection tools ids for Sirius Profile Diagram odesign
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
public class ReconnectionToolsIds {

	private ReconnectionToolsIds() {
		// to prevent instantiation
	}

	/*----------------------------------------------The Edge reconnection target tools IDs----------------------------------------------*/

	public static final String RECONNECT_TARGET__ASSOCIATION__TOOL = "ReconnectAssociationTarget"; //$NON-NLS-1$

	public static final String RECONNECT_TARGET__EXTENSION__TOOL = "ReconnectExtensionTarget"; //$NON-NLS-1$

	public static final String RECONNECT_TARGET__GENERALIZATION__TOOL = "ReconnectGeneralizationTarget"; //$NON-NLS-1$



	/*----------------------------------------------The Edge reconnection source tools IDs----------------------------------------------*/

	public static final String RECONNECT_SOURCE__ASSOCIATION__TOOL = "ReconnectAssociationSource"; //$NON-NLS-1$

	public static final String RECONNECT_SOURCE__EXTENSION__TOOL = "ReconnectExtensionSource"; //$NON-NLS-1$

	public static final String RECONNECT_SOURCE__GENERALIZATION__TOOL = "ReconnectGeneralizationSource"; //$NON-NLS-1$



}

