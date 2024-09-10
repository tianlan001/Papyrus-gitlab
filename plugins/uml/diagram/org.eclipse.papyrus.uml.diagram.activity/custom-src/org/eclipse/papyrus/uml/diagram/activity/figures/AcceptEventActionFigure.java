/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) Patrick.Tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.figures;

import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.papyrus.uml.diagram.activity.draw2d.CenteredColumnConstraint;
import org.eclipse.papyrus.uml.diagram.common.figure.node.RoundedCompartmentFigure;
import org.eclipse.papyrus.uml.diagram.common.helper.StereotypeFigureHelper;
import org.eclipse.swt.graphics.Image;


/**
 * add time template, make margin border relative, use StereotypeFigureHelper
 */

public class AcceptEventActionFigure extends RoundedCompartmentFigure {


	/** The helper which handles stereotype aspects */
	private StereotypeFigureHelper stereotypeHelper;

	public AcceptEventActionFigure() {
		createContents();
		// use StereotypeFigureHelper
		stereotypeHelper = new StereotypeFigureHelper(this) {

			@Override
			public Object getStereotypeRectangleConstraint() {
				return new CenteredColumnConstraint(false);
			}
		};
	}

	/**
	 * This method is used to create the content into the figure, in our case this is the label of the acceptEventAction
	 */
	private void createContents() {
	}


	/**
	 * Refresh the layout of the figure
	 */
	protected void refreshLayout() {
	}

	/** The value to display as stereotype display when available */
	String stereotypeDisplayText = null;

	/** The value to display as stereotype display when available */
	Image stereotypeDisplayImg = null;

	/**
	 * Sets the stereotypes for this figure.
	 *
	 * @param stereotypes
	 *            the string representing the stereotypes to be displayed
	 * @param image
	 *            the image representing the stereotypes to be displayed
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.node.IPapyrusUMLElementFigure#setStereotypeDisplay(java.lang.String, org.eclipse.swt.graphics.Image)
	 */
	@Override
	public void setStereotypeDisplay(String stereotypes, Image image) {
		if (!isTemplateForAcceptTimeEventActionUsed()) {
			stereotypeHelper.setStereotypeDisplay(stereotypes, image);
			refreshLayout();
		}
		// record values in case figure changes
		stereotypeDisplayText = stereotypes;
		stereotypeDisplayImg = image;
	}

	/** The value to display as stereotype in brace when available */
	String stereotypePropertiesInBrace = null;

	/**
	 * Sets the stereotypes properties for this figure.
	 *
	 * @param stereotypeProperties
	 *            the string representing the stereotype properties to be displayed
	 */
	@Override
	public void setStereotypePropertiesInBrace(String stereotypeProperties) {
		if (!isTemplateForAcceptTimeEventActionUsed()) {
			stereotypeHelper.setStereotypePropertiesInBrace(stereotypeProperties);
			refreshLayout();
		}
		// record values in case figure changes
		stereotypePropertiesInBrace = stereotypeProperties;
	}

	/** The value to display as stereotype in brace when available */
	String stereotypePropertiesInCompartment = null;

	/**
	 * displays the new string corresponding to the list of stereotypes.
	 *
	 * @param stereotypeProperties
	 *            the string to be displayed.
	 */
	@Override
	public void setStereotypePropertiesInCompartment(String stereotypeProperties) {
		if (!isTemplateForAcceptTimeEventActionUsed()) {
			stereotypeHelper.setStereotypePropertiesInCompartment(stereotypeProperties);
			refreshLayout();
		}
		// record values in case figure changes
		stereotypePropertiesInCompartment = stereotypeProperties;
	}

	private final PointList myTempslateTime = new PointList();


	private boolean useTemplateTime = false;

	/**
	 * Set whether the figure must use the classic concave pentagon template for AcceptEventAction or the hourglass template for
	 * AcceptTimeEventAction
	 *
	 * @param useAcceptTimeEventActionTemplate
	 *            true if the hourglass template must be used, false for default template
	 */
	public void useTemplateForAcceptTimeEventAction(boolean useAcceptTimeEventActionTemplate) {
		if (useAcceptTimeEventActionTemplate) {
			// erase stereotypes representation in block
			stereotypeHelper.setStereotypeDisplay(null, null);
			stereotypeHelper.setStereotypePropertiesInBrace(null);
			stereotypeHelper.setStereotypePropertiesInCompartment(null);
		} else {
			// restore stereotypes representation in block
			stereotypeHelper.setStereotypeDisplay(stereotypeDisplayText, stereotypeDisplayImg);
			stereotypeHelper.setStereotypePropertiesInBrace(stereotypePropertiesInBrace);
			stereotypeHelper.setStereotypePropertiesInCompartment(stereotypePropertiesInCompartment);
		}
		useTemplateTime = useAcceptTimeEventActionTemplate;
	}

	/**
	 * Ask whether the figure uses the classic concave pentagon template for AcceptEventAction or the hourglass template for
	 * AcceptTimeEventAction
	 *
	 * @return true if the hourglass template is used, false for default template
	 */
	public boolean isTemplateForAcceptTimeEventActionUsed() {
		return useTemplateTime;
	}

}
