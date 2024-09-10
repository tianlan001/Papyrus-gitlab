/*****************************************************************************
 * Copyright (c) 2010, 2021 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 573986
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.toolsmiths.editor.actions;

import java.util.Collection;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.action.DeleteAction;
import org.eclipse.papyrus.infra.properties.contexts.command.ContextDeleteCommand;
import org.eclipse.papyrus.views.properties.toolsmiths.util.ActionUtil;

/**
 * An adapter for the EMF "Delete" Action, compatible with the
 * MoDisco customizable content provider.
 *
 * @author Camille Letavernier
 */
public class MoDiscoDeleteAction extends DeleteAction {

	/**
	 * Constructor.
	 *
	 * @param domain
	 *            The editing domain on which the action will be called
	 * @param removeAllReferences
	 *            If true, all references to the deleted object will be removed.
	 *            This causes the whole resourceSet to be loaded, which may
	 *            lead to performance issues.
	 */
	public MoDiscoDeleteAction(EditingDomain domain, boolean removeAllReferences) {
		super(domain, removeAllReferences);
	}

	/**
	 * Constructor. All references to the deleted object will be removed.
	 *
	 * @param domain
	 *            The editing domain on which the action will be called
	 */
	public MoDiscoDeleteAction(EditingDomain domain) {
		super(domain);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param removeAllReferences
	 *            If true, all references to the deleted object will be removed.
	 *            This causes the whole resourceSet to be loaded, which may
	 *            lead to performance issues.
	 */
	public MoDiscoDeleteAction(boolean removeAllReferences) {
		super(removeAllReferences);
	}

	/**
	 * Constructor. All references to the deleted object will be removed.
	 */
	public MoDiscoDeleteAction() {
		super();
	}

	@Override
	public Command createCommand(Collection<?> selection) {
		Collection<?> newSelection = ActionUtil.getAdaptedSelection(selection);
		return removeAllReferences ? ContextDeleteCommand.create(domain, newSelection) : RemoveCommand.create(domain, newSelection);
	}

}
