/**
 *  Copyright (c) 2011 Mia-Software.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  	Gregoire Dupe (Mia-Software) - Bug 361794 - [Restructuring] New customization meta-model
 *      Gregoire Dupe (Mia-Software) - Bug 369987 - [Restructuring][Table] Switch to the new customization and facet framework
 *      Vincent Lorenzo (CEA-LIST)   - Bug 372644 - Create Customizable tooltips for the TreeViewer using a CustomizableLabelProvider
 *      Gregoire Dupe (Mia-Software) - Bug 373078 - API Cleaning
 *      Mickael ADAM (ALL4TEC) - mickael.adam@all4tec.net - Bug 500219 - implementation of IStyledLabelProvider
 */
package org.eclipse.papyrus.emf.facet.custom.ui;

import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.papyrus.emf.facet.custom.core.ICustomizationManager;
import org.eclipse.swt.graphics.Image;

/**
 * This interface allows to use a {@link ILabelProvider}, an {@link IColorProvider} and an {@link IFontProvider} which is implemented and
 * customizable using the EMF Facet customization mechanism.
 *
 * @author Gregoire Dupe
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ICustomizedLabelProvider extends ILabelProvider,
		IColorProvider, IFontProvider, ICustomizedToolTipLabelProvider, IStyledLabelProvider {
	/**
	 * This method returns the customization manager used by the label provider. The
	 * customization stack updates have to be done using this {@link ICustomizationManager}.
	 *
	 * @return the customization manager used by the content provider.
	 */
	ICustomizationManager getCustomizationManager();

	boolean isUnderlined(Object element, ETypedElement eTypedElement);

	boolean isStruckthrough(Object element, ETypedElement eTypedElement);

	Image getTopLeftOverlay(Object element, ETypedElement eTypedElement);

	Image getTopMiddleOverlay(Object element, ETypedElement eTypedElement);

	Image getTopRightOverlay(Object element, ETypedElement eTypedElement);

	Image getBottomLeftOverlay(Object element, ETypedElement eTypedElement);

	Image getBottomMiddleOverlay(Object element, ETypedElement eTypedElement);

	Image getBottomRightOverlay(Object element, ETypedElement eTypedElement);

	String getText(Object element, ETypedElement eTypedElement);

	Image getImage(Object element, ETypedElement eTypedElement);

	/**
	 * @since 0.2
	 */
	ICustomizedLabelProvider cloneLabelProvider();

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider#getStyledText(java.lang.Object)
	 * 
	 * @since 3.0
	 */
	@Override
	default StyledString getStyledText(final Object element) {
		return new StyledString(getText(element));
	}

	/**
	 * Returns the styled text label for the given element.
	 * 
	 * @since 3.0
	 */
	default StyledString getStyledText(final Object element, final ETypedElement eTypedElement) {
		return new StyledString(getText(element, eTypedElement));
	}

}
