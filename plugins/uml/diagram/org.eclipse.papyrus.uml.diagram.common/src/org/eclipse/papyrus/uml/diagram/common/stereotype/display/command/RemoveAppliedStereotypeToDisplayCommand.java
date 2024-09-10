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
package org.eclipse.papyrus.uml.diagram.common.stereotype.display.command;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayCommandExecution;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayUtil;
import org.eclipse.uml2.uml.Stereotype;

/**
 * The Class RemoveAppliedStereotypeToDisplayCommand used to set the list of applied stereotype to
 * display
 */
public class RemoveAppliedStereotypeToDisplayCommand extends RecordingCommand {


	private View view;
	private TransactionalEditingDomain domain;
	private Stereotype stereotype;

	private static StereotypeDisplayUtil helper = StereotypeDisplayUtil.getInstance();
	private static StereotypeDisplayCommandExecution commandHelper = StereotypeDisplayCommandExecution.getInstance();




	/**
	 * Instantiates a new sets the applied stereotype to display command.
	 * 
	 * @param domain
	 *            the domain
	 * @param object
	 *            the object
	 * @param stereotypeList
	 *            the stereotype list
	 */

	public RemoveAppliedStereotypeToDisplayCommand(TransactionalEditingDomain domain, View view,
			Stereotype stereotype) {
		super(domain, "Hide Stereotype Label");
		this.domain = domain;
		this.view = view;
		this.stereotype = stereotype;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doExecute() {
		View label = helper.getStereotypeLabel(view, stereotype);
		commandHelper.setUserVisibility(domain, label, false);
	}
}
