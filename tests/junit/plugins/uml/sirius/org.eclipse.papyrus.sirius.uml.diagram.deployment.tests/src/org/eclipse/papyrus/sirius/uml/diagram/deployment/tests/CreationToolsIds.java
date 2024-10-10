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
 * This class provides creation tools ids for Sirius Deployment Diagram odesign
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
public class CreationToolsIds {

	private CreationToolsIds() {
		// to prevent instantiation
	}

	/**
	 * Provides the VSM Tool Id according to the given element type name.
	 *
	 * @param elementTypeName
	 *            the name of the element type for which we want to retrieve the tool id.
	 * @return the tool id.
	 */
	public static final String getToolId(String elementTypeName) {
		return String.format("Create%sTool", elementTypeName); //$NON-NLS-1$
	}
}

