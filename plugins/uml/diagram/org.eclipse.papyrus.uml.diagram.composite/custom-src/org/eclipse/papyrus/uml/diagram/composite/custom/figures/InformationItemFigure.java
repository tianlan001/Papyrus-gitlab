/*****************************************************************************
 * Copyright (c) 2009-2011 CEA LIST.
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
 *  Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.composite.custom.figures;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.uml.diagram.common.figure.node.RoundedCompartmentFigure;

/**
 * Figure for Composite representation of InformationItem.
 */
public class InformationItemFigure extends RoundedCompartmentFigure {

	/** Value of the textual tag added to the figure. */
	private static String TAG_LABEL = "Information"; //$NON-NLS-1$

	/** The List of Compartment */
	private final static List<String> NO_COMPARTMENT = new ArrayList<String>();

	/**
	 * Creates a new InformationItemFigure.
	 */
	public InformationItemFigure() {
		super(NO_COMPARTMENT, TAG_LABEL);
	}
}
