/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Thibault Le Ouay (Sherpa Engineering) t.leouay@sherpa-eng.com  - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.wizards.command;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;


public class CopyModelCommand extends RecordingCommand {

	private Resource inResource;

	private List<EObject> outObjects;

	public CopyModelCommand(TransactionalEditingDomain domain, Resource inResource, List<EObject> outObjects) {
		super(domain);
		this.inResource = inResource;
		this.outObjects = outObjects;
	}

	@Override
	protected void doExecute() {
		inResource.getContents().addAll(outObjects);


	}

}
