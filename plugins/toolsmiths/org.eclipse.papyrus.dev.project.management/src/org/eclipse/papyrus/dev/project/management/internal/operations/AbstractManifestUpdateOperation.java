/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrus.dev.project.management.internal.operations;

import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IManifestEditor;

abstract class AbstractManifestUpdateOperation extends AbstractOperation {

	private Map<? extends IFile, ? extends IManifestEditor> manifests;

	public AbstractManifestUpdateOperation(String label, Map<? extends IFile, ? extends IManifestEditor> manifests) {
		super(label);

		this.manifests = manifests;
	}

	@Override
	public final IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		try {
			return doExecute(monitor, manifests);
		} finally {
			// Don't need these any longer
			manifests = null;
		}
	}

	protected abstract IStatus doExecute(IProgressMonitor monitor, Map<? extends IFile, ? extends IManifestEditor> manifests) throws ExecutionException;

	@Override
	public boolean canUndo() {
		return false;
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return Status.CANCEL_STATUS;
	}

	@Override
	public boolean canRedo() {
		return false;
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return Status.CANCEL_STATUS;
	}
}
