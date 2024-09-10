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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.papyrus.infra.properties.contexts.Context;


/**
 * An action that may be contributed to the Properties View customization dialog on the <tt>org.eclipse.papyrus.customization.properties.contextStorage</tt> extension point
 * to support deletion of a {@link Context} from the associated storage.
 */
public interface IContextDeleteAction {

	/**
	 * Queries the (translated) tool tip to show on the "Remove" button in the Properties
	 * customization dialog for {@link Context}s in the storage providers supported by the
	 * extension.
	 *
	 * @return the optional tool tip to show on the "Edit..." button in the customization dialog.
	 *         May be {@code null}
	 */
	String getToolTip();

	/**
	 * Delete a {@code context} from its persistent storage.
	 *
	 * @param context
	 *            The {@link Context} to delete
	 * @param monitor
	 *            A monitor to track the progress of the delete operation. Will not be {@code null}
	 *
	 * @throws CoreException
	 *             On failure to delete the {@code context}
	 */
	void delete(Context context, IProgressMonitor monitor) throws CoreException;

}
