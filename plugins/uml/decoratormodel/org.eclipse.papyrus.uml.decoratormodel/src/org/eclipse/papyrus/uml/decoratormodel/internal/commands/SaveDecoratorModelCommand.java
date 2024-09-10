/*****************************************************************************
 * Copyright (c) 2014 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.decoratormodel.internal.commands;

import java.util.Set;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.AbstractCommand.NonDirtying;
import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.uml.decoratormodel.internal.messages.Messages;

import com.google.common.collect.Sets;

/**
 * A command that ensures the next save of the {@link ModelSet} will save the decorator model
 * potentially created (if not already existing) by some previous {@link SeparateProfileApplicationsCommand}.
 */
public class SaveDecoratorModelCommand extends AbstractCommand implements NonDirtying {
	private final ModelSet modelSet;
	private final URI resourceURIWithoutExtension;
	private Set<URI> rescued;

	public SaveDecoratorModelCommand(ModelSet modelSet, URI resourceURI) {
		super(Messages.SaveDecoratorModelCommand_0);

		this.modelSet = modelSet;
		this.resourceURIWithoutExtension = resourceURI.trimFileExtension();
	}

	@Override
	protected boolean prepare() {
		rescued = Sets.newHashSet();
		for (URI next : modelSet.getResourcesToDeleteOnSave()) {
			if (next.trimFileExtension().equals(resourceURIWithoutExtension)) {
				rescued.add(next);
			}
		}

		return true;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public void execute() {
		modelSet.getResourcesToDeleteOnSave().removeAll(rescued);
	}

	@Override
	public void undo() {
		modelSet.getResourcesToDeleteOnSave().addAll(rescued);
	}

	@Override
	public void redo() {
		execute();
	}

}
