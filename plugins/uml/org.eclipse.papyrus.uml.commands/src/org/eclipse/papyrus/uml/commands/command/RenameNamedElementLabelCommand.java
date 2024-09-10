/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.commands.command;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.papyrus.views.modelexplorer.commands.RenameLabelCommand;
import org.eclipse.uml2.uml.NamedElement;

/**
 * Command to rename a label of a {@link NamedElement}.
 * @since 1.3
 */
public class RenameNamedElementLabelCommand extends RenameLabelCommand {

	/**
	 * Default constructor.
	 *
	 * @param editingDomain
	 *            The editing domain
	 * @param commandLabel
	 *            The command label
	 * @param element
	 *            The element whose label is renamed
	 * @param elementLabel
	 *            The element label
	 * @param dialogTitle
	 *            The dialog title
	 */
	public RenameNamedElementLabelCommand(final TransactionalEditingDomain editingDomain, final String commandLabel, final EObject element, final String elementLabel, final String dialogTitle) {
		super(editingDomain, commandLabel, element, elementLabel, dialogTitle);
	}

	/**
	 * {@inheritDoc}
	 */
	protected void renameLabel(final EObject element, final String newLabel) {
		if (element instanceof NamedElement) {
			UMLLabelInternationalization.getInstance().setLabel((NamedElement) element, newLabel, null);
		}
	}
}
