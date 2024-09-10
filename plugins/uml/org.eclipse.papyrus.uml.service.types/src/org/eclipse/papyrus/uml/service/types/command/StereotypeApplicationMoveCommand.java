/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Benoit Maggi (CEA LIST) - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.command;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.uml2.uml.Element;

/**
 * A Command to move all stereotype applications applied to an UML Element
 *  from a Resource to an other Resource
 */
public class StereotypeApplicationMoveCommand extends AbstractTransactionalCommand {

	 Element element;
	 Resource source;
	 Resource target;
	 
	/**
	 * Constructor.
	 *
	 * @param request
	 */
	public StereotypeApplicationMoveCommand(TransactionalEditingDomain domain,Element element, Resource source, Resource target) {
		super(domain, "Move stereotype application", null);
		this.element = element;
		this.source = source;
		this.target = target;
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 *
	 * @param monitor
	 * @param info
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		if (element != null && target != null) {
			EList<EObject> stereotypeApplications = element.getStereotypeApplications();
			EList<EObject> targetList = target.getContents();
			for (EObject next : stereotypeApplications) {
				if (next.eResource() == source) {
					targetList.add(next);
				}
			}			
		}
		return CommandResult.newOKCommandResult();
	}	
	
	
}
