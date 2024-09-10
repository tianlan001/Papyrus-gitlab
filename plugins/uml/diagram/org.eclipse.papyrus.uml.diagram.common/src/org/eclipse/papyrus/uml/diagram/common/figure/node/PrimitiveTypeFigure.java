/*****************************************************************************
 * Copyright (c) 2008, 2017 CEA LIST.
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
 *  CEA List - initial API and implementation
 *  Nizar GUEDIDI (CEA LIST) - Bug 345889 - [ClassDiagram] Compartment for properties and operations are not implemented yet for primitive type
 *  Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 515409
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.figure.node;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;

/**
 * Represents a Primitive Type
 */
public class PrimitiveTypeFigure extends RoundedCompartmentFigure {

	/** Attribute Compartment */
	private final static String ATTRIBUTE_COMPARTMENT = "attributeCompartment";

	/** Operation Compartment */
	private final static String OPERATION_COMPARTMENT = "operationCompartment";

	/** List of compartment */
	private final static List<String> COMPARTMENT = new ArrayList<String>() {

		private static final long serialVersionUID = -6373359195626265958L;

		{
			add(ATTRIBUTE_COMPARTMENT);
			add(OPERATION_COMPARTMENT);
		}
	};

	/**
	 * Default Constructor
	 */
	public PrimitiveTypeFigure() {
		super(COMPARTMENT, "Primitive");
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
