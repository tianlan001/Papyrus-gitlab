/******************************************************************************
 * Copyright (c) 2021 CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Etienne ALLOGO (ARTAL) - etienne.allogo@artal.fr - initial API and implementation
 *    Etienne ALLOGO (ARTAL) - etienne.allogo@artal.fr - Bug 569174 - Use project or worksapce preference as new line characters
 *****************************************************************************/
package org.eclipse.papyrus.gmf.common.codegen;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;


/**
 * OutputFormatterUtil class provides utility methods to correctly format the files produced when generating diagrams.
 *
 * @author allogo
 * @since 2.0
 */
public class OutputFormatterUtil {

	/**
	 * Gets the default line separator from project or workspace.
	 *
	 * @param project
	 *            the project
	 * @return the default line separator
	 */
	public static String getDefaultLineSeparator(IProject project) {
		if (Platform.isRunning()) {
			// line delimiter in project preference
			IScopeContext[] scopeContext;
			String lineSeparator;
			if (project != null) {
				scopeContext = new IScopeContext[] { new ProjectScope(project) };
				lineSeparator = Platform.getPreferencesService().getString(Platform.PI_RUNTIME, Platform.PREF_LINE_SEPARATOR, null, scopeContext);
				if (lineSeparator != null) {
					return lineSeparator;
				}
			}

			// line delimiter in workspace preference
			scopeContext = new IScopeContext[] { InstanceScope.INSTANCE };
			lineSeparator = Platform.getPreferencesService().getString(Platform.PI_RUNTIME, Platform.PREF_LINE_SEPARATOR, null, scopeContext);
			if (lineSeparator != null) {
				return lineSeparator;
			}
		}
		return System.lineSeparator(); // default old behavior -> from OS default
	}
}
