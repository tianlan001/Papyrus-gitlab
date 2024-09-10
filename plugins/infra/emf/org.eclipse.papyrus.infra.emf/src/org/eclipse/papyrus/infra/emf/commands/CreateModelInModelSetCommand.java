/*****************************************************************************
 * Copyright (c) 2023 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.emf.commands;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.papyrus.infra.core.resource.ModelSet;

/**
 * The Command to create a new Papyrus Model.
 *
 * @since 4.3
 */
public class CreateModelInModelSetCommand extends RecordingCommand {

	/** The my model set. */
	private final ModelSet myModelSet;

	/** The URI of the model to create. */
	private final URI myURI;

	/**
	 * Instantiates a new new papyrus model command.
	 *
	 * @param modelSet
	 *            the di resource set
	 * @param newURI
	 *            the URI of the new model's principal resource
	 * @param contextId
	 *            the id of the architecture context
	 * @param viewpointIds
	 *            the ids of the architecture viewpoints
	 */
	public CreateModelInModelSetCommand(ModelSet modelSet, URI newURI) {
		super(modelSet.getTransactionalEditingDomain());
		myModelSet = modelSet;
		myURI = newURI;
	}

	/**
	 *
	 * @see org.eclipse.emf.transaction.RecordingCommand#doExecute()
	 *
	 */
	@Override
	protected void doExecute() {
		myModelSet.createModels(myURI);
	}

}
