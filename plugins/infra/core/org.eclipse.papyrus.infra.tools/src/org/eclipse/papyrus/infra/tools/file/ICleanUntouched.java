/*******************************************************************************
 * Copyright (c) 2018, 2021 CEA LIST.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     CEA LIST - initial API and implementation
 *     Ansgar Radermacher - Bug 572487
 *******************************************************************************/

package org.eclipse.papyrus.infra.tools.file;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * Manage the deletion of code that has not been access in the code generation
 * process and can be removed.
 *
 * @since 3.0
 * @Deprecated(since="4.2", forRemoval=true) Moved to designer (oep.designer.languages.common.base)
 */
public interface ICleanUntouched {

	/**
	 * Remove files for which no code has been generated. This enables the
	 * removal of old code e.g. after a suppression or renaming of elements
	 * in the model.
	 *
	 * @param folder
	 *            a folder from which cleaning should start
	 * @param monitor
	 *            a progress monitor
	 * @throws CoreException
	 *             a possible exception during file operations
	 */
	public void cleanUntouched(IFolder folder, IProgressMonitor monitor) throws CoreException;
}
