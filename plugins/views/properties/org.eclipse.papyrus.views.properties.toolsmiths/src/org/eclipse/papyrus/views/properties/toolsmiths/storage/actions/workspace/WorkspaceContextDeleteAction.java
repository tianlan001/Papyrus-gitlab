/*****************************************************************************
 * Copyright (c) 2011, 2015 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - Factor out workspace storage for pluggable storage providers (CDO)
 *  Christian W. Damus - bug 482927
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.toolsmiths.storage.actions.workspace;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.internal.ui.runtime.IInternalConfigurationManager;
import org.eclipse.papyrus.views.properties.toolsmiths.messages.Messages;
import org.eclipse.papyrus.views.properties.toolsmiths.storage.actions.IContextDeleteAction;

/**
 * An action to delete an existing context. This action cannot be undone.
 * If you simply want to disable an existing context, see {@link IInternalConfigurationManager#disableContext(Context, boolean)}
 *
 * @author Camille Letavernier
 */
public class WorkspaceContextDeleteAction implements IContextDeleteAction {

	@Override
	public String getToolTip() {
		return Messages.WorkspaceContextDeleteAction_0;
	}

	/**
	 * Deletes the given context.
	 *
	 * @param context
	 *            The context to delete
	 */
	@Override
	public void delete(final Context context, IProgressMonitor monitor) throws CoreException {
		final File directory = new File(context.eResource().getURI().toFileString()).getParentFile();

		SubMonitor sub = SubMonitor.convert(monitor, Messages.WorkspaceContextDeleteAction_1 + context.getUserLabel(), IProgressMonitor.UNKNOWN);
		try {
			delete(directory);
		} finally {
			sub.done();
		}
	}

	/**
	 * Recursively deletes a file or directory
	 *
	 * @param file
	 *            The file or directory to delete recusively
	 */
	private void delete(File file) {
		if (file.isDirectory()) {
			for (File subFile : file.listFiles()) {
				delete(subFile);
			}

		}
		file.delete();
	}
}
