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
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.usecase.dnd.ui;

import org.eclipse.papyrus.uml.diagram.usecase.messages.Messages;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.UseCase;

/**
 * @author Francois Le Fevre
 *
 */
public class UsecaseSelectionDialog extends SelectionDialog {

	private final UseCase sourceUsecase;
	private final Classifier subject;
	private UsecaseSubjectComposite usecaseSubjectComposite;
	private UsecaseOwnerComposite usecaseOwnerComposite;

	/**
	 * Constructor.
	 *
	 * @param parentShell
	 */
	public UsecaseSelectionDialog(Shell parentShell, UseCase sourceUsecase, Classifier subject) {
		super(parentShell);

		this.sourceUsecase = sourceUsecase;
		this.subject = subject;
	}

	@Override
	public void create() {
		setTitle(Messages.UsecaseSelectionDialog_TITLE);
		super.create();

		Composite parent = getDialogArea();

		// Add a select box only if the sourceUsecase do not contains the target subject
		if (!sourceUsecase.getSubjects().contains(subject)) {
			usecaseSubjectComposite = new UsecaseSubjectComposite(parent, computeInitialStatusForSubject());
		}

		if (!sourceUsecase.getOwner().equals(subject)) {
			usecaseOwnerComposite = new UsecaseOwnerComposite(parent, computeInitialStatusForOwner());
		}

		parent.layout();

		getShell().pack();

	}

	/**
	 * @return
	 */
	private boolean computeInitialStatusForOwner() {
		if (sourceUsecase.getOwner().equals(subject)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return
	 */
	private boolean computeInitialStatusForSubject() {
		if (sourceUsecase.getSubjects().contains(subject)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Composite getDialogArea() {
		return (Composite) super.getDialogArea();
	}

	@Override
	public boolean isResizable() {
		return true;
	}

	public boolean isKeepOwner() {
		if (usecaseOwnerComposite != null) {
			return usecaseOwnerComposite.isKeepOwner();
		} else {
			return true;
		}

	}

	public boolean isKeepSubject() {
		if (usecaseSubjectComposite != null) {
			return usecaseSubjectComposite.isKeepSubject();
		} else {
			return true;
		}
	}

}
