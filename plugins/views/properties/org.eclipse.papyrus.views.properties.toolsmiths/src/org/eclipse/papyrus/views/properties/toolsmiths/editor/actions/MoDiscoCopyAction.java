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
import org.eclipse.emf.edit.ui.action.CopyAction;
import org.eclipse.papyrus.views.properties.toolsmiths.util.ActionUtil;

/**
 * An adapter for the EMF "Copy" Action, compatible with the
 * MoDisco customizable content provider.
 *
 * @author Camille Letavernier
 */
public class MoDiscoCopyAction extends CopyAction {

	/**
	 * Constructor.
	 */
	public MoDiscoCopyAction() {
	}

	/**
	 * Constructor.
	 *
	 * @param domain
	 *            The Editing domain on which the action will be executed
	 */
	public MoDiscoCopyAction(EditingDomain domain) {
		super(domain);
	}

	@Override
	public Command createCommand(Collection<?> selection) {
		Collection<?> newSelection = ActionUtil.getAdaptedSelection(selection);
		return super.createCommand(newSelection);
	}

}
