/*****************************************************************************
 * Copyright (c) 2018 Maged Elaasar, CEA LIST, and others
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
 *  Maged Elaasar - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.commands;

import java.util.Collections;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.papyrus.infra.architecture.commands.IModelConversionCommand;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;

/**
 * A base class for model conversion commands.
 */
public abstract class AbstractModelConversionCommand implements IModelConversionCommand {

	/**
	 * @see org.eclipse.papyrus.infra.ui.extension.commands.IModelConversionCommand#convertModel(org.eclipse.papyrus.infra.core.utils.DiResourceSet)
	 *
	 * @param diResourceSet
	 */
	@Override
	public final void convertModel(final ModelSet modelSet) {
		TransactionalEditingDomain editingDomain = modelSet.getTransactionalEditingDomain();
		AbstractTransactionalCommand command = new AbstractTransactionalCommand(editingDomain, "Convert model", Collections.emptyList()) {
			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				doConvertModel(modelSet);
				return CommandResult.newOKCommandResult();
			}
		};
		editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(command));
	}

	/**
	 * Perform the model conversion logic
	 *
	 * @param modelSet
	 *            the model resource set
	 */
	public abstract void doConvertModel(final ModelSet modelSet);

}
