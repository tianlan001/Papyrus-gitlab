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
 *  Remi Schnekenburger (CEA LIST) remi.schnekenburger@cea.fr - Initial API and implementation
 *  Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Bug 455311 : Refactor Stereotypes Display
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editpolicies;

import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.uml2.uml.Usage;

/**
 * Specific edit policy for label displaying stereotypes and their properties
 * for edges representing UML elements.
 * <p>
 * It also displays the tag for the links, for example "use" for {@link Usage}.
 *
 */
public abstract class AppliedStereotypeLabelDisplayEditPolicy extends AbstractAppliedStereotypeDisplayEditPolicy {

	/** constant for this edit policy role */
	public static final String STEREOTYPE_LABEL_POLICY = "AppliedStereotypeLabelDisplayEditPolicy";

	/** tag displayed for the UML element */
	public String tag;

	/**
	 * Creates a new AppliedStereotypeLabelDisplayEditPolicy, with the specified
	 * tag for the element.
	 *
	 * @param tag
	 *            the tag for element, for example "use" for {@link Usage}.
	 */
	public AppliedStereotypeLabelDisplayEditPolicy(String tag) {
		super();
		this.tag = Activator.ST_LEFT + tag + Activator.ST_RIGHT;
	}

	/**
	 * Creates a new AppliedStereotypeLabelDisplayEditPolicy, with no tag for
	 * the element.
	 */
	public AppliedStereotypeLabelDisplayEditPolicy() {
		super();
		this.tag = "";
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void refreshDisplay() {
		refreshStereotypeDisplay();
	}

	/**
	 * Refreshes the stereotype display
	 */
	protected abstract void refreshStereotypeDisplay();

	/**
	 * Get the Stereotype text to display into the Label.
	 * 
	 * @return The stereotype name Label to display according to the depth
	 */
	public String stereotypesToDisplay() {
		return helper.getStereotypeTextToDisplay(hostView);
	}



}
