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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.ui.menu.NameNormalizationCommand;

/**
 * this command is used to create a link "satisfyBy" between requirement and namedElement
 *
 */
public class DiagramNameNormalizationCommand extends NameNormalizationCommand{

	public DiagramNameNormalizationCommand(TransactionalEditingDomain domain, EObject source, String parameter){ 
		super(domain,source, parameter);
	}


	/**
	 * @see org.eclipse.emf.transaction.RecordingCommand#doExecute()
	 *
	 */
	@Override
	protected void doExecute() {
		if(source instanceof Diagram){
			String name = ((Diagram)source).getName();
			String newName = normalizeName(name,parameter);
			((Diagram)source).setName(newName);
		}
	}

}