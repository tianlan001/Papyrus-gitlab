/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
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

import org.eclipse.emf.edit.ui.action.ValidateAction;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.views.properties.toolsmiths.util.ActionUtil;

/**
 * An adapter for the EMF "Validate" Action, compatible with the
 * MoDisco customizable content provider.
 *
 * @author Camille Letavernier
 */
public class ValidationAction extends ValidateAction {

	@Override
	public boolean updateSelection(IStructuredSelection selection) {
		return super.updateSelection((IStructuredSelection) ActionUtil.getAdaptedSelection(selection));
	}
}
