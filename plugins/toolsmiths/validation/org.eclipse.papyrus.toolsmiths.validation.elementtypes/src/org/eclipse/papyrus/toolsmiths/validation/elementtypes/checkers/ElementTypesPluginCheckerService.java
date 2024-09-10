/*****************************************************************************
 * Copyright (c) 2019, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   Christian W. Damus - bug 573245
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.elementtypes.checkers;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.papyrus.toolsmiths.validation.elementtypes.Activator;

/**
 * This allows to check an element types plug-in (extensions, builds, dependencies).
 *
 * @deprecated Since the 2.1 release of the bundle, the plug-in validation menu action is no longer defined and this class is obsolete.
 * @see <a href="https://eclip.se/573251">bug 573251</a> to watch for the removal of this API in a future release
 */
@Deprecated(since = "2.1", forRemoval = true)
public class ElementTypesPluginCheckerService {

	/**
	 * This allows to check the element types plug-in.
	 *
	 * @param project
	 *            The current project to check.
	 * @param monitor
	 *            An {@link IProgressMonitor}, or <code>null</code>
	 */
	public static void checkElementTypesPlugin(final IProject project, IProgressMonitor monitor) {
		try {
			project.build(IncrementalProjectBuilder.FULL_BUILD, monitor);
		} catch (CoreException e) {
			Activator.log.log(e.getStatus());
		}
	}

}
