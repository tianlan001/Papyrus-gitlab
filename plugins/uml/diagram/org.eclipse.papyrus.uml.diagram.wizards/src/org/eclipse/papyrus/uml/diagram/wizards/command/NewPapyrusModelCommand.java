/*****************************************************************************
 * Copyright (c) 2010, 2013 CEA LIST.
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
 *  Tatiana Fesenko (CEA LIST) - Initial API and implementation
 *  Christian W. Damus (CEA) - Support creating models in repositories (CDO)
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.wizards.command;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.papyrus.infra.core.resource.ModelSet;

/**
 * The Command to create a new Papyrus Model.
 *
 * @deprecated
 *             see org.eclipse.papyrus.infra.emf.commands.CreateModelInModelSetCommand
 */
@Deprecated(since = "4.0.100")
public class NewPapyrusModelCommand extends RecordingCommand {

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
	public NewPapyrusModelCommand(ModelSet modelSet, URI newURI) {
		super(modelSet.getTransactionalEditingDomain());
		myModelSet = modelSet;
		myURI = newURI;
	}

	@Override
	protected void doExecute() {
		myModelSet.createModels(myURI);
	}

}
