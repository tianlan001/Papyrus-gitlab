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
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Bug 455311 Stereotype Display
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.stereotype.display.command;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.notation.View;

/**
 * This Command set a view Transcient.
 *
 *
 * @author Céline JANSSENS
 *
 */
public class UnsetPersistentViewCommand extends RecordingCommand {

	protected View view;

	protected final static String LABEL_COMMAND = "Unset Persistency"; //$NON-NLS-1$

	/**
	 *
	 * Constructor.
	 *
	 * @param domain
	 *            Transactional Domain
	 * @param view
	 *            The view to make persistent
	 */
	public UnsetPersistentViewCommand(TransactionalEditingDomain domain, View view) {

		super(domain, LABEL_COMMAND);
		this.view = view;
	}


	/**
	 * @see org.eclipse.emf.transaction.RecordingCommand#doExecute()
	 *
	 */
	@Override
	protected void doExecute() {
		makeViewTranscient(view);

	}

	/**
	 * Recursive method that makes a view and all its ancestors persistent.
	 *
	 * @param view
	 *            The view to make persistent ( cannot be {@code null})
	 */
	@SuppressWarnings("unchecked")
	protected void makeViewTranscient(final View view) {
		if (view != null) {
			if (view.eContainer() != null && view.eContainer() instanceof View) {

				// Move the view from the Transient List to the Persistent Children list
				((View) view.eContainer()).getTransientChildren().add(view);
				((View) view.eContainer()).getPersistedChildren().remove(view);

			}
		}
	}
}
