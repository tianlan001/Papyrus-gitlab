/*****************************************************************************
 * Copyright (c) 2010, 2014 CEA LIST and others.
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.ui.xwt;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.papyrus.infra.properties.contexts.Section;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.properties.ui.runtime.DisplayEngine;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractSectionDescriptor;
import org.eclipse.ui.views.properties.tabbed.ISection;

/**
 * An XWTSectionDescriptor contains Metadata for the XWTSection.
 * It is similar to the {@link Section} class, but in the TabbedPropertyView context.
 *
 * @author Camille Letavernier
 */
public class XWTSectionDescriptor extends AbstractSectionDescriptor {

	private Section section;

	private View view;

	private DisplayEngine display;

	/**
	 *
	 * Constructs a new XWTSectionDescriptor from the given section and view.
	 * The XWTSections will be displayed in the given DisplayEngine.
	 *
	 * @param section
	 *            The Section model object containing the section metadata
	 * @param view
	 *            The view the section belongs to
	 * @param display
	 *            The display engine that will be used to display the XWTSection
	 */
	public XWTSectionDescriptor(Section section, View view, DisplayEngine display) {
		this.section = section;
		this.view = view;
		this.display = display;
	}

	public String getId() {
		return section.getName();
	}

	public ISection getSectionClass() {
		return new XWTSection(section, view, display);
	}

	public String getTargetTab() {
		return section.getTab().getId();
	}

	@Override
	public String toString() {
		return "Section " + getId(); //$NON-NLS-1$
	}

	@Override
	public boolean appliesTo(IWorkbenchPart part, ISelection selection) {
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + System.identityHashCode(display);
		result = prime * result + System.identityHashCode(section);
		result = prime * result + System.identityHashCode(view);
		return result;
	}

	/**
	 * XWT section descriptors are equal if they have the same (identical) references to the section and view from the property-sheet model
	 * and are associated with the same display engine.
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result;

		if (this == obj) {
			result = true;
		} else if ((obj == null) || (obj.getClass() != this.getClass())) {
			result = false;
		} else {
			XWTSectionDescriptor other = (XWTSectionDescriptor) obj;

			result = (other.section == this.section) && (other.view == this.view) && (other.display == this.display);
		}

		return result;
	}

}
