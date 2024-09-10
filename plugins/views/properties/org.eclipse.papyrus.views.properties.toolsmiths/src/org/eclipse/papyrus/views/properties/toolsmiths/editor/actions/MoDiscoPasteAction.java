/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.toolsmiths.editor.actions;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.action.PasteAction;
import org.eclipse.papyrus.views.properties.toolsmiths.util.ActionUtil;

/**
 * An adapter for the EMF "Paste" Action, compatible with the
 * MoDisco customizable content provider.
 *
 * @author Camille Letavernier
 */
public class MoDiscoPasteAction extends PasteAction {

	/**
	 * Constructor.
	 */
	public MoDiscoPasteAction() {
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param domain
	 *            The editing domain on which the command will be called
	 */
	public MoDiscoPasteAction(EditingDomain domain) {
		super(domain);
	}

	@Override
	public Command createCommand(Collection<?> selection) {
		Collection<?> newSelection = ActionUtil.getAdaptedSelection(selection);
		return super.createCommand(newSelection);
	}

}
