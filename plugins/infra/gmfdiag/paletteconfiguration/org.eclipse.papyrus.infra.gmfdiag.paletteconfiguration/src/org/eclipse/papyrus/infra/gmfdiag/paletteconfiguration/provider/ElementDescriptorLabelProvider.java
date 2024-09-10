/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, ALL4TEC and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.provider;

import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.ElementDescriptor;
import org.eclipse.swt.graphics.Image;

/**
 * Label Provider for {@link ElementDescriptor}.
 */
public class ElementDescriptorLabelProvider extends LabelProvider implements IStyledLabelProvider {
	private ElementTypeLabelProvider labelProvider;

	/**
	 * Constructor.
	 */
	public ElementDescriptorLabelProvider() {
		labelProvider = new ElementTypeLabelProvider();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider#getStyledText(java.lang.Object)
	 */
	@Override
	public StyledString getStyledText(final Object element) {
		StyledString styledText;
		if (element instanceof ElementDescriptor) {
			styledText = labelProvider.getStyledText(((ElementDescriptor) element).getElementType());
		} else {
			styledText = labelProvider.getStyledText(element);
		}
		return styledText;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(final Object element) {
		return getStyledText(element).getString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(final Object element) {
		Image image;
		if (element instanceof ElementDescriptor) {
			image = labelProvider.getImage(((ElementDescriptor) element).getElementType());
		} else {
			image = labelProvider.getImage(element);
		}
		return image;
	}

}