/*****************************************************************************
 * Copyright (c) 2018 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.ui.widgets;

import org.eclipse.swt.widgets.Composite;

/**
 * A Widget for selecting Files on the workspace or on the file system.
 * The widgets only edits String values : it uses the path of the files
 *
 * Adapted from {@link StringFileSelector}
 *
 * @author Vincent Lorenzo
 * @since 3.3
 */
public class StringDirectorySelector extends AbstractPropertyEditor {

	/**
	 * The StringFileSelector widget used by this property editor
	 */
	protected org.eclipse.papyrus.infra.widgets.editors.StringDirectorySelector selector;

	/**
	 * Enables the "browse workspace" feature
	 */
	protected boolean allowWorkspace = true;

	/**
	 * Enables the "browse file system" feature
	 */
	protected boolean allowFileSystem = true;

	/**
	 *
	 * Constructor.
	 *
	 * @param parent
	 * @param style
	 */
	public StringDirectorySelector(Composite parent, int style) {
		selector = createSelector(parent, style);
		super.setEditor(selector);
	}

	/**
	 * Creates the selector
	 *
	 * @param parent
	 *            The composite in which the widget will be displayed
	 * @param style
	 *            The style for the widget
	 * @return the selector
	 */
	protected org.eclipse.papyrus.infra.widgets.editors.StringDirectorySelector createSelector(Composite parent, int style) {
		return new org.eclipse.papyrus.infra.widgets.editors.StringDirectorySelector(parent, style);
	}

	/**
	 * Indicates whether the editor should allow browsing the workspace or not
	 *
	 * @param allowWorkspace
	 */
	public void setAllowWorkspace(boolean allowWorkspace) {
		this.allowWorkspace = allowWorkspace;
		selector.setAllowWorkspace(allowWorkspace);
	}

	/**
	 *
	 * @return true if the editor can browse the workspace
	 */
	public boolean getAllowWorkspace() {
		return allowWorkspace;
	}

	/**
	 * Indicates whether the editor should allow browsing the file system or not
	 *
	 * @param allowFileSystem
	 */
	public void setAllowFileSystem(boolean allowFileSystem) {
		this.allowFileSystem = allowFileSystem;
		selector.setAllowFileSystem(allowFileSystem);
	}

	/**
	 *
	 * @return true if the editor can browse the fileSystem
	 */
	public boolean getAllowFileSystem() {
		return allowFileSystem;
	}
}
