/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *
 *		CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.rendering;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.notation.View;

/**
 * Command to create the compartment displaying shapes for an element
 * see #Req org.eclipse.papyrus.infra.gmfdiag.expansion.Req_011
 */
public class CreateInducedRepresentationViewCommand extends RecordingCommand {

	/** owner of the compartment view to create */
	private View owner;

	/** boolean that indicates if the comaprtment has to be visible or not */
	private boolean isVisible;

	private String visualHint;

	private PreferencesHint preferenceHint;

	/**
	 * Creates a new CreateShapeCompartmentViewCommand.
	 *
	 * @param domain
	 *            editing domain used to manipulate model
	 * @param label
	 *            the label of the command
	 * @param description
	 *            description of the command
	 */
	public CreateInducedRepresentationViewCommand(TransactionalEditingDomain domain, String label,String visualHint, String description, View owner, boolean isVisible,PreferencesHint preferenceHint) {
		super(domain, label, description);
		this.owner = owner;
		this.setVisible(isVisible);
		this.visualHint= visualHint;
		this.preferenceHint= preferenceHint;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doExecute() {
		ViewService.createNode(owner,owner.getElement(), visualHint, preferenceHint);
		
		//BasicCompartmentViewFactory compartmentViewFactory= new BasicCompartmentViewFactory();
		//compartmentViewFactory.createView(new SemanticAdapter(owner.getElement(), null), owner, visualHint, -1, true,preferenceHint );
	}


	/**
	 * Sets the visiblity of the created compartment
	 *
	 * @param isVisible
	 *            <code>true</code> if the compartment should be visible
	 */
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
}
