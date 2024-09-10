/*****************************************************************************
 * Copyright (c) 2011, 2016 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 485220
 *  
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.plugin;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.ProjectEditors;
import org.xml.sax.SAXException;

/**
 * @deprecated Use the {@link ProjectEditors#getPluginEditor(IProject)} API, instead.
 */
@Deprecated
public class PluginEditor extends org.eclipse.papyrus.eclipse.project.editors.project.PluginEditor {

	public PluginEditor(IProject project) throws CoreException, IOException, SAXException, ParserConfigurationException {
		super(project);

		if (!exists()) {
			create();
		}

		init();
	}
}
