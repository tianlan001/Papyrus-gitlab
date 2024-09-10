/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
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
 *  Francois Le Fevre (CEA LIST) francois.le-fevre@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.modelexplorer.actions;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.ui.menu.AbstractEMFParametricOnSelectedElementsAction;
import org.eclipse.papyrus.infra.ui.menu.NameNormalizationCommand;

public class UmlQuickFormatAction extends AbstractEMFParametricOnSelectedElementsAction {

	public UmlQuickFormatAction(String parameter , List<EObject> selectedElements) {
		super(parameter, selectedElements);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.uml.diagram.menu.actions.AbstractGraphicalParametricAction#getBuildedCommand()
	 *
	 * @return
	 */
	@Override
	protected Command getBuildedCommand() {
		CompoundCommand cc = new CompoundCommand(NameNormalizationCommand.NAME_ACTION);
		for(EObject igEP : getSelection()){
			final TransactionalEditingDomain domain = getEditingDomain();
			UmlNameNormalizationCommand myCmd = new UmlNameNormalizationCommand(domain, igEP, parameter);
			cc.append(myCmd);
		}
		
		if (!cc.isEmpty() && cc.canExecute()) {
			return cc;
		}
		return UnexecutableCommand.INSTANCE;
	}
	
}
