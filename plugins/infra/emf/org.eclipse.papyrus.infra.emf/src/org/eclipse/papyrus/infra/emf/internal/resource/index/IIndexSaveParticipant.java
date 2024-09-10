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

package org.eclipse.papyrus.infra.emf.internal.resource.index;

import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.core.resources.ISaveParticipant;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.papyrus.infra.emf.resource.index.WorkspaceModelIndex;

/**
 * Protocol for an extension of the plug-in's {@link ISaveParticipant}
 * that saves the current state of a {@link WorkspaceModelIndex}.
 */
public interface IIndexSaveParticipant {
	/**
	 * Saves an {@code index} to a file.
	 * 
	 * @param index
	 *            the index to save
	 * @param store
	 *            the output stream on which to save it. The caller may choose to
	 *            {@link OutputStream#close() close} this stream but is not
	 *            required to
	 * 
	 * @throws IOException
	 *             on failure to write to the {@code store}
	 * @throws CoreException
	 *             on failure to save the {@code index}
	 */
	void save(WorkspaceModelIndex<?> index, OutputStream output) throws IOException, CoreException;
}
