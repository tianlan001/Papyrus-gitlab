/*****************************************************************************
 * Copyright (c) 2008 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.figure.node;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;

/**
 * Represents a datatype
 */
public class DataTypeFigure extends RoundedCompartmentFigure {

	/** Attribute Compartment */
	private final static String ATTRIBUTE_COMPARTMENT = "attributeCompartment";

	/** Operation Compartment */
	private final static String OPERATION_COMPARTMENT = "operationCompartment";

	/** List of compartment */
	private final static List<String> COMPARTMENT = new ArrayList<String>() {

		private static final long serialVersionUID = -2841645169151618170L;
		{
			add(ATTRIBUTE_COMPARTMENT);
			add(OPERATION_COMPARTMENT);
		}
	};

	/**
	 * Default Constructor
	 */
	public DataTypeFigure() {
		super(COMPARTMENT, "DataType");
	}

	/**
	 * Get the attribute's compartment figure
	 *
	 * @return
	 */
	public IFigure getAttributeCompartmentFigure() {
		return getCompartment(ATTRIBUTE_COMPARTMENT);
	}

	/**
	 * Get the operation's compartment figure
	 *
	 * @return
	 */
	public IFigure getOperationCompartmentFigure() {
		return getCompartment(OPERATION_COMPARTMENT);
	}
}
