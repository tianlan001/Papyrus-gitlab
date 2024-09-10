/*****************************************************************************
 * Copyright (c) 2010, 2021 CEA LIST, EclipseSource and others.
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
 *  Christian W. Damus (CEA) - bug 417409
 *  EclipseSource - Bug 572530
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.ui.xwt;

import java.util.List;

import org.eclipse.papyrus.infra.properties.contexts.Section;
import org.eclipse.papyrus.infra.properties.contexts.Tab;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.properties.internal.ui.Activator;
import org.eclipse.papyrus.infra.properties.ui.renderers.SectionRendererService;
import org.eclipse.papyrus.infra.properties.ui.runtime.DisplayEngine;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.tabbed.AbstractTabDescriptor;
import org.eclipse.ui.views.properties.tabbed.ISectionDescriptor;

/**
 * A Tab descriptor implementation for the TabbedPropertyView.
 * The property view is described by XWT files.
 *
 * @author Camille Letavernier
 */
public class XWTTabDescriptor extends AbstractTabDescriptor {

	private Tab tab;
	private SectionRendererService sectionRendererService;

	/**
	 * Constructor.
	 *
	 * @param tab
	 *            The Tab model object containing the Metadata for the tab
	 * @param sectionRendererService
	 *            The service used to render the Sections
	 */
	public XWTTabDescriptor(Tab tab, SectionRendererService sectionRendererService) {
		this.tab = tab;
		this.sectionRendererService = sectionRendererService;
	}

	/**
	 * Adds a section to this tab
	 *
	 * @param section
	 *            The Section model object
	 * @param view
	 *            The View model object to which the section belongs
	 * @param display
	 *            The display engine that will be used to display the section
	 */
	@SuppressWarnings("unchecked")
	public void addSection(Section section, View view, DisplayEngine display) {
		super.getSectionDescriptors().add(createDescriptor(section, view, display));
	}

	private ISectionDescriptor createDescriptor(Section section, View view, DisplayEngine display) {
		return sectionRendererService.getSectionDescriptor(section, view, display);
	}

	@Override
	public String getCategory() {
		String category = tab.getCategory();
		return category == null ? "" : category; //$NON-NLS-1$
	}

	@Override
	public String getId() {
		return tab.getId();
	}

	@Override
	public String getLabel() {
		return tab.getLabel();
	}

	@Override
	public Image getImage() {
		String imagePath = tab.getImage();

		if (imagePath == null || imagePath.trim().equals("")) { //$NON-NLS-1$
			return null;
		}

		return Activator.getDefault().getImageFromPlugin(imagePath);
	}

	@Override
	public boolean isIndented() {
		return tab.isIndented();
	}

	@Override
	public String getAfterTab() {
		if (tab.getAfterTab() != null) {
			return tab.getAfterTab().getId();
		}
		return super.getAfterTab();
	}

	@Override
	public String toString() {
		return "Tab " + getLabel() + " => " + getSectionDescriptors(); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @return this tab's priority. The tabs should be ordered by ascending
	 *         priority (i.e. the lower the priority int value, the higher
	 *         the actual priority)
	 */
	public int getPriority() {
		return tab.getPriority();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		List<?> sectionDescriptors = getSectionDescriptors();
		result = prime * result + ((tab == null) ? 0 : tab.hashCode());
		result = prime * result + ((sectionDescriptors == null) ? 0 : sectionDescriptors.hashCode());
		return result;
	}

	/**
	 * XWT tab descriptors are equal if they have the same ID and an equal list (in order) of section descriptors.
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result;

		if (this == obj) {
			result = true;
		} else if ((obj == null) || (obj.getClass() != this.getClass())) {
			result = false;
		} else {
			XWTTabDescriptor other = (XWTTabDescriptor) obj;

			result = (other.getId() == null) ? this.getId() == null : (other.getId().equals(this.getId()));
			if (result) {
				result = (other.getSectionDescriptors() == null) ? this.getSectionDescriptors() == null : other.getSectionDescriptors().equals(this.getSectionDescriptors());
			}
		}

		return result;
	}
}
