/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
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
 *    Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.profile.custom.figure;

import org.eclipse.papyrus.uml.diagram.common.figure.node.ClassFigure;

/**
 * Figure for representation of Stereotype
 */
public class StereotypeFigure extends ClassFigure {

	/**
	 * the tag
	 */
	private static String TAG_LABEL = "Stereotype"; //$NON-NLS-1$

	/**
	 *
	 * Constructor.
	 *
	 */
	public StereotypeFigure() {
		super(TAG_LABEL);

	}
}
