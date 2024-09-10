/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.toolsmiths.storage.actions.workspace;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.views.properties.toolsmiths.storage.actions.workspace.WorkspaceContextCopyAction;
import org.eclipse.papyrus.views.properties.toolsmiths.storage.actions.workspace.WorkspaceContextDeleteAction;
import org.eclipse.papyrus.views.properties.toolsmiths.storage.actions.workspace.WorkspaceContextEditAction;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.views.properties.toolsmiths.storage.actions.AbstractContextStorageActionProvider;
import org.eclipse.papyrus.views.properties.toolsmiths.storage.actions.IContextCopyAction;
import org.eclipse.papyrus.views.properties.toolsmiths.storage.actions.IContextDeleteAction;
import org.eclipse.papyrus.views.properties.toolsmiths.storage.actions.IContextEditAction;


/**
 * This is the WorkspaceContextStorageActionProvider type. Enjoy.
 */
public class WorkspaceContextStorageActionProvider extends AbstractContextStorageActionProvider {

	public WorkspaceContextStorageActionProvider() {
		super();
	}

	@Override
	public boolean providesFor(Context context) {
		Resource resource = context.eResource();
		return (resource != null) && resource.getURI().isPlatformResource() || resource.getURI().isFile();
	}

	@Override
	protected IContextCopyAction createContextCopyAction() {
		return new WorkspaceContextCopyAction();
	}

	@Override
	protected IContextEditAction createContextEditAction() {
		return new WorkspaceContextEditAction();
	}

	@Override
	protected IContextDeleteAction createContextDeleteAction() {
		return new WorkspaceContextDeleteAction();
	}

}
