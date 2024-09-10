/*******************************************************************************
 * Copyright (c) 2006, 2014 CEA List and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     CEA List - initial API and implementation
 *     Christian W. Damus (CEA) - bug 392301
 *     Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - use of roundedCompartmentFigure
 *
 *******************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.figure.node;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.swt.graphics.Image;


/**
 * Figure for a package element
 */
public class PackageFigure extends RoundedCompartmentFigure {


	/** The Internal Structure Compartment */
	private final static String PACKAGE_COMPARTMENT = "packageCompartment"; //$NON-NLS-1$

	/** The List of Compartment */
	private final static List<String> COMPARTMENT = new ArrayList<>() {

		private static final long serialVersionUID = 1L;
		{
			add(PACKAGE_COMPARTMENT);
		}
	};

	/**
	 * Default Constructor
	 */
	public PackageFigure() {
		this(null);
	}

	/**
	 * Create a new Classifier figure with the given tag
	 *
	 * @param tagLabel
	 *            a String that will be displayed at the top of the figure
	 */
	public PackageFigure(String tagLabel) {
		super(COMPARTMENT, tagLabel);
	}


	/**
	 * get the gmf container figure of the package
	 *
	 * @return the gmf container
	 */
	public IFigure getGMFPackageableElementContainer() {
		if (getCompartment(PACKAGE_COMPARTMENT).getChildren().size() > 0) {
			return (IFigure) getCompartment(PACKAGE_COMPARTMENT).getChildren().get(0);
		}
		return null;
	}

	public IFigure getPackageableElementFigure() {
		return getCompartment(PACKAGE_COMPARTMENT);
	}

	/** this is a label used to display a specific icon for this element **/
	protected Label iconPackage = null;

	/**
	 * this method is used to display a symbol image for an element package as
	 * triangle for the model
	 *
	 * @param image
	 */
	public void setTagIcon(Image image) {
		iconPackage = new Label();
		iconPackage.setIcon(image);
	}

}
