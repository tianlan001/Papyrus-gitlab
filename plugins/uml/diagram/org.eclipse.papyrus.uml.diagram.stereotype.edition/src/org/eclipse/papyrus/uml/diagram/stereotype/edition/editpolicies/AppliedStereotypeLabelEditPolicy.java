/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Bug 460356 : Refactor Stereotype Display
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.stereotype.edition.editpolicies;

import org.eclipse.papyrus.uml.diagram.common.editpolicies.AppliedStereotypeNodeLabelDisplayEditPolicy;

/**
 * This Policy is in charge of create and delete the applied Stereotype Label Node into the Notation model.
 * When a stereotype is applied a node associate to the stereotype is created.
 * the created is transient and associated to only one stereotype.
 */
public class AppliedStereotypeLabelEditPolicy extends AppliedStereotypeNodeLabelDisplayEditPolicy {

	/** constant for this edit policy role */
	public static final String STEREOTYPE_LABEL_POLICY = "AppliedStereotypeLabelEditPolicy"; //$NON-NLS-1$

	/**
	 * Creates a new AppliedStereotype display edit policy
	 */
	public AppliedStereotypeLabelEditPolicy() {
		super();
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editpolicies.AbstractAppliedStereotypeDisplayEditPolicy#activate()
	 *
	 */
	@Override
	public void activate() {
		// CEJ Auto-generated method stub
		super.activate();
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editpolicies.AppliedStereotypeNodeLabelDisplayEditPolicy#refreshStereotypeDisplay()
	 *
	 */
	@Override
	protected void refreshStereotypeDisplay() {
		// nothing
	}




}
