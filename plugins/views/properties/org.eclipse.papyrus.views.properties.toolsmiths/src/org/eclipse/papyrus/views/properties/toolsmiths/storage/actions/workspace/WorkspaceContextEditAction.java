/*****************************************************************************
 * Copyright (c) 2010, 2015 CEA LIST, Christian W. Damus, and others.
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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.views.properties.toolsmiths.messages.Messages;
import org.eclipse.papyrus.views.properties.toolsmiths.storage.actions.IContextEditAction;
import org.eclipse.papyrus.views.properties.toolsmiths.util.ProjectUtil;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

/**
 * An action to edit an existing context.
 * The context must not be read-only, which means it should not have been
 * registered via a plug-in extension
 *
 * @author Camille Letavernier
 */
public class WorkspaceContextEditAction implements IContextEditAction {

	@Override
	public String getToolTip() {
		return Messages.WorkspaceContextEditAction_0;
	}

	/**
	 * Opens an Eclipse Editor to edit the given context.
	 *
	 * @param context
	 *            The context to edit
	 * @throws Exception
	 *             If the context cannot be edited
	 */
	@Override
	public void openEditor(final Context context, IProgressMonitor monitor) throws CoreException {
		SubMonitor sub = SubMonitor.convert(monitor, Messages.WorkspaceContextEditAction_1 + context.getUserLabel(), IProgressMonitor.UNKNOWN);

		try {
			runOpenEditor(context);
		} finally {
			sub.done();
		}
	}

	protected void runOpenEditor(Context context) throws CoreException {
		IFile contextFile = ProjectUtil.getContextFile(context);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new FileEditorInput(contextFile), "org.eclipse.papyrus.customization.properties.UIEditor", true); //$NON-NLS-1$ ;
	}
}
