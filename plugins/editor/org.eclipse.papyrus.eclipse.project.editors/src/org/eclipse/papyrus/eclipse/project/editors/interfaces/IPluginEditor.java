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

package org.eclipse.papyrus.eclipse.project.editors.interfaces;

/**
 * A synthesis of all of the editors required to manipulate the metadata
 * of a plug-in bundle project.
 * 
 * @since 2.0
 */
public interface IPluginEditor extends IPluginProjectEditor, IJavaProjectEditor, IManifestEditor {
	/**
	 * Queries whether the <tt>plugin.xml</tt> file, in particualr, exists.
	 * 
	 * @return whether the plug-in manifest exists
	 */
	boolean pluginManifestExists();
}
