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
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.editor;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.papyrus.infra.core.editor.BackboneException;
import org.eclipse.papyrus.infra.ui.multidiagram.actionbarcontributor.CoreComposedActionBarContributor;
import org.eclipse.ui.IEditorActionBarContributor;


/**
 * EditorActionBarContributor suitable to Papyrus multi editor.
 * This ActionBarContributor is composed of ActionBarContributor described in extension.
 * It also implements interfaces requested by some well knowns Papyrus nested
 * editors (EMF, GMF, ...)
 *
 * @author dumoulin
 *
 */
public class PapyrusActionBarContributor extends CoreComposedActionBarContributor implements IMenuListener {


	public PapyrusActionBarContributor() throws BackboneException {
		super();
	}

	/**
	 * Methods requested by EMF nested diagrams.
	 * Propagate the call to the currently active contributor.
	 *
	 * @see org.eclipse.jface.action.IMenuListener#menuAboutToShow(org.eclipse.jface.action.IMenuManager)
	 * @param manager
	 *
	 */
	@Override
	public void menuAboutToShow(IMenuManager manager) {
		IEditorActionBarContributor contributor = getActiveContributor();
		if (contributor != this && contributor instanceof IMenuListener) {
			((IMenuListener) contributor).menuAboutToShow(manager);
			return;
		}

	}

}
