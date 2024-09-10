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
package org.eclipse.papyrus.views.properties.toolsmiths.storage.actions;

import org.eclipse.papyrus.views.properties.toolsmiths.storage.actions.IContextCopyAction;
import org.eclipse.papyrus.views.properties.toolsmiths.storage.actions.IContextDeleteAction;
import org.eclipse.papyrus.views.properties.toolsmiths.storage.actions.IContextEditAction;
import org.eclipse.papyrus.views.properties.toolsmiths.storage.actions.IContextStorageActionProvider;

/**
 * This is the AbstractContextStorageActionProvider type. Enjoy.
 */
public abstract class AbstractContextStorageActionProvider implements IContextStorageActionProvider {

	private IContextCopyAction copyAction;

	private IContextEditAction editAction;

	private IContextDeleteAction deleteAction;

	public AbstractContextStorageActionProvider() {
		super();
	}

	@Override
	public IContextCopyAction getContextCopyAction() {
		if (copyAction == null) {
			copyAction = createContextCopyAction();
		}

		return copyAction;
	}

	protected abstract IContextCopyAction createContextCopyAction();

	@Override
	public IContextEditAction getContextEditAction() {
		if (editAction == null) {
			editAction = createContextEditAction();
		}

		return editAction;
	}

	protected abstract IContextEditAction createContextEditAction();

	@Override
	public IContextDeleteAction getContextDeleteAction() {
		if (deleteAction == null) {
			deleteAction = createContextDeleteAction();
		}

		return deleteAction;
	}

	protected abstract IContextDeleteAction createContextDeleteAction();

}
