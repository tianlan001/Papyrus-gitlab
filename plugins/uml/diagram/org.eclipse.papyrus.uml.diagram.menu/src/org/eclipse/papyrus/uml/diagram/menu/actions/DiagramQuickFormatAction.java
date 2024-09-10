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
package org.eclipse.papyrus.uml.diagram.menu.actions;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.papyrus.commands.wrappers.EMFtoGEFCommandWrapper;
import org.eclipse.papyrus.infra.ui.menu.NameNormalizationCommand;

public class DiagramQuickFormatAction extends AbstractGraphicalParametricOnSelectedElementsAction {

	public DiagramQuickFormatAction(String parameter , List<EObject> selectedElements) {
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
			DiagramNameNormalizationCommand myCmd = new DiagramNameNormalizationCommand(domain, igEP, parameter);
			EMFtoGEFCommandWrapper commandWrapper=new EMFtoGEFCommandWrapper(myCmd);
			cc.add(commandWrapper);
		}
		
		
		if (!cc.isEmpty() && cc.canExecute()) {
			return cc;
		}
		return UnexecutableCommand.INSTANCE;
	}
}
