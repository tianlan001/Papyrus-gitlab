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
import org.eclipse.papyrus.infra.properties.contexts.Context;


/**
 * A provider of actions to the Properties View customization dialog on the <tt>org.eclipse.papyrus.customization.properties.contextStorage</tt> extension point to implement {@linkplain IContextCopyAction copying}, {@linkplain IContextEditAction editing},
 * and {@linkplain IContextDeleteAction deletion} of {@link Context} to the corresponding storage
 * provider.
 *
 * @see IContextCopyAction
 * @see IContextEditAction
 * @see IContextDeleteAction
 */
public interface IContextStorageActionProvider {

	IContextStorageActionProvider READ_ONLY = new ReadOnlyContextStorageActionProvider();

	/**
	 * Queries whether I provide edit and delete actions for the a {@code context}.
	 *
	 * @param context
	 *            a context selected by the user to act on
	 *
	 * @return whether I provide actions for it
	 */
	boolean providesFor(Context context);

	IContextCopyAction getContextCopyAction();

	IContextEditAction getContextEditAction();

	IContextDeleteAction getContextDeleteAction();

	//
	// Nested types
	//

	class ReadOnlyContextStorageActionProvider implements IContextStorageActionProvider {

		@Override
		public boolean providesFor(Context context) {
			// I am the final fall-back that provides no editing capability, so I always return false
			return false;
		}

		@Override
		public IContextCopyAction getContextCopyAction() {
			// I am not actually provided on the extension point, so this isn't needed
			return null;
		}

		@Override
		public IContextEditAction getContextEditAction() {
			// I can't edit anything, so this isn't needed
			return null;
		}

		@Override
		public IContextDeleteAction getContextDeleteAction() {
			// I can't edit anything, so this isn't needed
			return null;
		}
	}
}
