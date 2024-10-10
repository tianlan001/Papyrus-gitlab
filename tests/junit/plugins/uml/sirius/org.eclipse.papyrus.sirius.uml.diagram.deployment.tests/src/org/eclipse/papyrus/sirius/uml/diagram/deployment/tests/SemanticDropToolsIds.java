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
 * This class provides semantic drop tools ids for Sirius Deployment Diagram odesign.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
public class SemanticDropToolsIds {

	private SemanticDropToolsIds() {
		// to prevent instantiation
	}

	/**
	 * Returns the <i>semantic drop</i> tool id from the VSM according to the provided {@code elementTypeName}.
	 *
	 * @param elementTypeName
	 *            the name of the element type to return the toolid from
	 * @return the tool id
	 */
	public static final String getToolId(String elementTypeName) {
		return String.format("Semantic%sDrop", elementTypeName); //$NON-NLS-1$
	}
}

