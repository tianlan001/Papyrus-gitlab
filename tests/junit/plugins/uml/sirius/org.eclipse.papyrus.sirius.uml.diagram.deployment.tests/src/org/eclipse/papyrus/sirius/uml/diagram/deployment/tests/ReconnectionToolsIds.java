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
package org.eclipse.papyrus.sirius.uml.diagram.deployment.tests;

/**
 * This class provides reconnection tool IDs for Sirius Deployment Diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class ReconnectionToolsIds {

	private ReconnectionToolsIds() {
		// to prevent instantiation
	}

	/**
	 * Returns the <i>reconnect source</i> tool id from the VSM according to the provided {@code elementTypeName}.
	 *
	 * @param elementTypeName
	 *            the name of the element type to return the tool id from
	 * @return the tool id
	 * @see #getReconnectTargetToolId(String)
	 */
	public static final String getReconnectSourceToolId(String elementTypeName) {
		return String.format("Reconnect%sSource", elementTypeName); //$NON-NLS-1$
	}

	/**
	 * Returns the <i>reconnect target</i> tool id from the VSM according to the provided {@code elementTypeName}.
	 *
	 * @param elementTypeName
	 *            the name of the element type to return the tool id from
	 * @return the tool id
	 * @see #getReconnectSourceToolId(String)
	 */
	public static final String getReconnectTargetToolId(String elementTypeName) {
		return String.format("Reconnect%sTarget", elementTypeName); //$NON-NLS-1$
	}

}
