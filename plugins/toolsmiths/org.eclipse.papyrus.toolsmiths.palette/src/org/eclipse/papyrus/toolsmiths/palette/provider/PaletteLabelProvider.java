/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.palette.provider;

import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.toolsmiths.palette.Messages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Label provider for palette tools.
 * <P>
 * We should be using the Palette label provider from GEF, if it was not with visibility "package"...
 *
 * @see org.eclipse.gef.ui.palette.customize.PaletteLabelProvider
 *      </P>
 *
 */
public class PaletteLabelProvider implements ILabelProvider {

	/** the icon for stereotypes */
	private static final String ICONS_STEREOTYPE = "/icons/stereotype.gif"; // $NON-NLS-N$

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addListener(final ILabelProviderListener listener) {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispose() {
		// Do nothing
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image getImage(final Object element) {
		Image iconImage = null;
		if (element instanceof PaletteEntry) {
			ImageDescriptor descriptor = ((PaletteEntry) element).getSmallIcon();
			if (descriptor != null) {
				iconImage = Activator.getDefault().getImage(descriptor);
			}
		} else if (element instanceof Stereotype) {
			iconImage = Activator.getDefault().getImage("org.eclipse.papyrus.customization.palette", ICONS_STEREOTYPE);
		}
		return iconImage;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getText(final Object element) {
		String text = null;
		if (element instanceof PaletteEntry) {
			text = ((PaletteEntry) element).getLabel();
		} else if (element instanceof Stereotype) {
			text = ((Stereotype) element).getName();
		}
		return null != text ? text : Messages.PaletteConfigurationContentPage_UnknownElement;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isLabelProperty(final Object element, final String property) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeListener(final ILabelProviderListener listener) {
		// Do nothing
	}

}