/*****************************************************************************
 * Copyright (c) 2021 CEA LIST.
 *
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
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.properties.ui.widgets;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.properties.ui.modelelement.DataSource;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.swt.widgets.Composite;

/**
 * Adapted code from StringFileSelector
 *
 * This widget allows to select a workspace file for String field
 * The String field is read-only.
 * The file selection can be restricted to the current project or displayed all workspace project using the property showOnlyCurrentProject
 * The files to show can be filtered by their extension
 *
 * @since 4.1
 *
 */
public class StringWorkspaceFileSelector extends AbstractPropertyEditor {

	/**
	 * The StringFileSelector widget used by this property editor
	 */
	protected org.eclipse.papyrus.infra.widgets.editors.StringWorkspaceFileSelector selector;

	/**
	 * The filtered extensions
	 * This should be a 1-1 mapping with {@link #filterNames}
	 */
	protected String[] filterExtensions;

	/**
	 * The name of the filters.
	 * This should be a 1-1 mapping with {@link #filterExtensions}
	 */
	protected String[] filterNames;

	/**
	 * if <code>true</code> the Browse workspace action will show only the current project
	 */
	protected boolean showOnlyCurrentProject = false;;

	/**
	 *
	 * Constructor.
	 *
	 * @param parent
	 * @param style
	 */
	public StringWorkspaceFileSelector(Composite parent, int style) {
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
	protected org.eclipse.papyrus.infra.widgets.editors.StringWorkspaceFileSelector createSelector(Composite parent, int style) {
		return new org.eclipse.papyrus.infra.widgets.editors.StringWorkspaceFileSelector(parent, style);
	}

	/**
	 *
	 * @param filterExtensions
	 */
	public void setFilterExtensions(String[] filterExtensions) {
		this.filterExtensions = filterExtensions;
		checkFilters();
	}

	/**
	 *
	 * @return
	 *         the filter extension
	 */
	public String[] getFilterExtensions() {
		return this.filterExtensions;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.properties.ui.widgets.AbstractPropertyEditor#setProperty(java.lang.String)
	 *
	 * @param path
	 */
	@Override
	public void setProperty(String path) {
		super.setProperty(path);
		selector.setProjectName(getCurrentProjectName());
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.properties.ui.widgets.AbstractPropertyEditor#setInput(org.eclipse.papyrus.infra.properties.ui.modelelement.DataSource)
	 *
	 * @param input
	 */
	@Override
	public void setInput(DataSource input) {
		super.setInput(input);
		selector.setProjectName(getCurrentProjectName());
	}

	/**
	 *
	 * @param filterNames
	 */
	public void setFilterNames(String[] filterNames) {
		this.filterNames = filterNames;
		checkFilters();
	}

	/**
	 *
	 * @return
	 *         the name of the filters
	 */
	public String[] getFilterNames() {
		return this.filterNames;
	}

	/**
	 * Checks if the filters are valid
	 */
	protected void checkFilters() {
		if (filterExtensions != null && filterNames != null) {
			selector.setFilters(filterExtensions, filterNames);
		}
	}

	/**
	 *
	 * @param showOnlyCurrentProject
	 *            if <code>true</code> the Browse action will only show the current project
	 */
	public void setShowOnlyCurrentProject(final boolean showOnlyCurrentProject) {
		this.showOnlyCurrentProject = showOnlyCurrentProject;
		this.selector.setShowOnlyCurrentProject(showOnlyCurrentProject);
	}

	/**
	 *
	 * @return
	 *         the name of the current project
	 */
	protected final String getCurrentProjectName() {
		String currentProjectName = null;
		if (input != null && propertyPath != null) {
			ModelElement modelElement = input.getModelElement(propertyPath);
			if (modelElement instanceof EMFModelElement) {
				final EObject source = ((EMFModelElement) modelElement).getSource();
				final URI uri = source.eResource().getURI();
				if (uri.isPlatformResource() && uri.segmentCount() >= 2) {
					currentProjectName = uri.segment(1);
				}
			}
		}
		return currentProjectName;
	}

	/**
	 *
	 * @return
	 *         boolean indicating if we want to show only the current project in the Browse action
	 */
	public boolean getShowOnlyCurrentProject() {
		return this.showOnlyCurrentProject;
	}

}