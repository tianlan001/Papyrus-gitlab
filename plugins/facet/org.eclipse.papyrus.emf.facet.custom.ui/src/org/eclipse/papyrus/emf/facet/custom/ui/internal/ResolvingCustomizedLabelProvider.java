/*******************************************************************************
 * Copyright (c) 2012 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Nicolas Bros (Mia-Software) - Bug 379683 - customizable Tree content provider
 *    Mickael ADAM (ALL4TEC) - mickael.adam@all4tec.net - Bug 500219 - implementation of getStyledText
 *    
 *******************************************************************************/
package org.eclipse.papyrus.emf.facet.custom.ui.internal;

import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.papyrus.emf.facet.custom.core.ICustomizationManager;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.EAttributeTreeElement;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.EObjectTreeElement;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.EReferenceTreeElement;
import org.eclipse.papyrus.emf.facet.custom.ui.CustomizedContentProviderUtils;
import org.eclipse.papyrus.emf.facet.custom.ui.ICustomizedLabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

public class ResolvingCustomizedLabelProvider implements ICustomizedLabelProvider {

	private final ICustomizedLabelProvider delegate;

	public ResolvingCustomizedLabelProvider(final ICustomizedLabelProvider delegate) {
		super();
		this.delegate = delegate;
	}

	public Image getImage(final Object element) {
		Image image;
		if (element instanceof EReferenceTreeElement) {
			final EReferenceTreeElement treeElement = (EReferenceTreeElement) element;
			final EObjectTreeElement parent = treeElement.getParent();
			image = this.delegate.getImage(parent.getEObject(), treeElement.getEReference());
		} else if (element instanceof EAttributeTreeElement) {
			final EAttributeTreeElement treeElement = (EAttributeTreeElement) element;
			final EObjectTreeElement parent = (EObjectTreeElement) treeElement.getParent();
			image = this.delegate.getImage(parent.getEObject(), treeElement.getEAttribute());
		}

		else {
			image = this.delegate.getImage(CustomizedContentProviderUtils.resolve(element));
		}
		return image;
	}

	public String getText(final Object element) {
		String result;
		if (element instanceof EReferenceTreeElement) {
			final EReferenceTreeElement treeElement = (EReferenceTreeElement) element;
			final EObjectTreeElement parent = treeElement.getParent();
			result = this.delegate.getText(parent.getEObject(), treeElement.getEReference());
		} else if (element instanceof EAttributeTreeElement) {
			final EAttributeTreeElement treeElement = (EAttributeTreeElement) element;
			final EObjectTreeElement parent = (EObjectTreeElement) treeElement.getParent();
			result = this.delegate.getText(parent.getEObject(), treeElement.getEAttribute());
		} else {
			result = this.delegate.getText(CustomizedContentProviderUtils.resolve(element));
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.emf.facet.custom.ui.ICustomizedLabelProvider#getStyledText(java.lang.Object)
	 */
	@Override
	public StyledString getStyledText(final Object element) {
		StyledString result;
		if (delegate instanceof ICustomizedLabelProvider) {
			if (element instanceof EReferenceTreeElement) {
				final EReferenceTreeElement treeElement = (EReferenceTreeElement) element;
				final EObjectTreeElement parent = treeElement.getParent();
				result = this.delegate.getStyledText(parent.getEObject(), treeElement.getEReference());
			} else if (element instanceof EAttributeTreeElement) {
				final EAttributeTreeElement treeElement = (EAttributeTreeElement) element;
				final EObjectTreeElement parent = (EObjectTreeElement) treeElement.getParent();
				result = this.delegate.getStyledText(parent.getEObject(), treeElement.getEAttribute());
			} else {
				result = this.delegate.getStyledText(CustomizedContentProviderUtils.resolve(element));
			}
		} else {
			result = new StyledString(getText(element));
		}
		return result;
	}

	public void addListener(final ILabelProviderListener listener) {
		this.delegate.addListener(listener);
	}

	public void dispose() {
		this.delegate.dispose();
	}

	public boolean isLabelProperty(final Object element, final String property) {
		return this.delegate.isLabelProperty(CustomizedContentProviderUtils.resolve(element), property);
	}

	public void removeListener(final ILabelProviderListener listener) {
		this.delegate.removeListener(listener);
	}

	public Color getForeground(final Object element) {
		return this.delegate.getForeground(CustomizedContentProviderUtils.resolve(element));
	}

	public Color getBackground(final Object element) {
		return this.delegate.getBackground(CustomizedContentProviderUtils.resolve(element));
	}

	public Font getFont(final Object element) {
		return this.delegate.getFont(CustomizedContentProviderUtils.resolve(element));
	}

	public void update(final ViewerCell cell) {
		this.delegate.update(cell);
	}

	public String getToolTipText(final Object element, final ETypedElement eTypedElement) {
		String result;
		if (element instanceof EReferenceTreeElement) {
			final EReferenceTreeElement treeElement = (EReferenceTreeElement) element;
			final EObjectTreeElement parent = treeElement.getParent();
			result = this.delegate.getToolTipText(parent.getEObject(), treeElement.getEReference());
		} else if (element instanceof EAttributeTreeElement) {
			final EAttributeTreeElement treeElement = (EAttributeTreeElement) element;
			final EObjectTreeElement parent = (EObjectTreeElement) treeElement.getParent();
			result = this.delegate.getToolTipText(parent.getEObject(), treeElement.getEAttribute());
		} else {
			result = this.delegate.getToolTipText(CustomizedContentProviderUtils.resolve(element), eTypedElement);
		}
		return result;
	}

	public Image getToolTipImage(final Object element, final ETypedElement eTypedElement) {
		Image result;
		if (element instanceof EReferenceTreeElement) {
			final EReferenceTreeElement treeElement = (EReferenceTreeElement) element;
			final EObjectTreeElement parent = treeElement.getParent();
			result = this.delegate.getToolTipImage(parent.getEObject(), treeElement.getEReference());
		} else if (element instanceof EAttributeTreeElement) {
			final EAttributeTreeElement treeElement = (EAttributeTreeElement) element;
			final EObjectTreeElement parent = (EObjectTreeElement) treeElement.getParent();
			result = this.delegate.getToolTipImage(parent.getEObject(), treeElement.getEAttribute());
		} else {
			result = this.delegate.getToolTipImage(CustomizedContentProviderUtils.resolve(element), eTypedElement);
		}
		return result;
	}

	public Font getToolTipFont(final Object element, final ETypedElement eTypedElement) {
		Font result;
		if (element instanceof EReferenceTreeElement) {
			final EReferenceTreeElement treeElement = (EReferenceTreeElement) element;
			final EObjectTreeElement parent = treeElement.getParent();
			result = this.delegate.getToolTipFont(parent.getEObject(), treeElement.getEReference());
		} else if (element instanceof EAttributeTreeElement) {
			final EAttributeTreeElement treeElement = (EAttributeTreeElement) element;
			final EObjectTreeElement parent = (EObjectTreeElement) treeElement.getParent();
			result = this.delegate.getToolTipFont(parent.getEObject(), treeElement.getEAttribute());
		} else {
			result = this.delegate.getToolTipFont(CustomizedContentProviderUtils.resolve(element), eTypedElement);
		}
		return result;
	}

	public Color getToolTipBackgroundColor(final Object element, final ETypedElement eTypedElement) {
		Color result;
		if (element instanceof EReferenceTreeElement) {
			final EReferenceTreeElement treeElement = (EReferenceTreeElement) element;
			final EObjectTreeElement parent = treeElement.getParent();
			result = this.delegate.getToolTipBackgroundColor(parent.getEObject(), treeElement.getEReference());
		} else if (element instanceof EAttributeTreeElement) {
			final EAttributeTreeElement treeElement = (EAttributeTreeElement) element;
			final EObjectTreeElement parent = (EObjectTreeElement) treeElement.getParent();
			result = this.delegate.getToolTipBackgroundColor(parent.getEObject(), treeElement.getEAttribute());
		} else {
			result = this.delegate.getToolTipBackgroundColor(CustomizedContentProviderUtils.resolve(element), eTypedElement);
		}
		return result;
	}

	public Color getToolTipForegroundColor(final Object element, final ETypedElement eTypedElement) {
		Color result;
		if (element instanceof EReferenceTreeElement) {
			final EReferenceTreeElement treeElement = (EReferenceTreeElement) element;
			final EObjectTreeElement parent = treeElement.getParent();
			result = this.delegate.getToolTipForegroundColor(parent.getEObject(), treeElement.getEReference());
		} else if (element instanceof EAttributeTreeElement) {
			final EAttributeTreeElement treeElement = (EAttributeTreeElement) element;
			final EObjectTreeElement parent = (EObjectTreeElement) treeElement.getParent();
			result = this.delegate.getToolTipForegroundColor(parent.getEObject(), treeElement.getEAttribute());
		} else {
			result = this.delegate.getToolTipForegroundColor(CustomizedContentProviderUtils.resolve(element), eTypedElement);
		}
		return result;
	}

	public Image getToolTipTopLeftOverlay(final Object element, final ETypedElement eTypedElement) {
		Image result;
		if (element instanceof EReferenceTreeElement) {
			final EReferenceTreeElement treeElement = (EReferenceTreeElement) element;
			final EObjectTreeElement parent = treeElement.getParent();
			result = this.delegate.getToolTipTopLeftOverlay(parent.getEObject(), treeElement.getEReference());
		} else if (element instanceof EAttributeTreeElement) {
			final EAttributeTreeElement treeElement = (EAttributeTreeElement) element;
			final EObjectTreeElement parent = (EObjectTreeElement) treeElement.getParent();
			result = this.delegate.getToolTipTopLeftOverlay(parent.getEObject(), treeElement.getEAttribute());
		} else {
			result = this.delegate.getToolTipTopLeftOverlay(CustomizedContentProviderUtils.resolve(element), eTypedElement);
		}
		return result;
	}

	public Image getToolTipTopMiddleOverlay(final Object element, final ETypedElement eTypedElement) {
		Image result;
		if (element instanceof EReferenceTreeElement) {
			final EReferenceTreeElement treeElement = (EReferenceTreeElement) element;
			final EObjectTreeElement parent = treeElement.getParent();
			result = this.delegate.getToolTipTopMiddleOverlay(parent.getEObject(), treeElement.getEReference());
		} else if (element instanceof EAttributeTreeElement) {
			final EAttributeTreeElement treeElement = (EAttributeTreeElement) element;
			final EObjectTreeElement parent = (EObjectTreeElement) treeElement.getParent();
			result = this.delegate.getToolTipTopMiddleOverlay(parent.getEObject(), treeElement.getEAttribute());
		} else {
			result = this.delegate.getToolTipTopMiddleOverlay(CustomizedContentProviderUtils.resolve(element), eTypedElement);
		}
		return result;
	}

	public Image getToolTipTopRightOverlay(final Object element, final ETypedElement eTypedElement) {
		Image result;
		if (element instanceof EReferenceTreeElement) {
			final EReferenceTreeElement treeElement = (EReferenceTreeElement) element;
			final EObjectTreeElement parent = treeElement.getParent();
			result = this.delegate.getToolTipTopRightOverlay(parent.getEObject(), treeElement.getEReference());
		} else if (element instanceof EAttributeTreeElement) {
			final EAttributeTreeElement treeElement = (EAttributeTreeElement) element;
			final EObjectTreeElement parent = (EObjectTreeElement) treeElement.getParent();
			result = this.delegate.getToolTipTopRightOverlay(parent.getEObject(), treeElement.getEAttribute());
		} else {
			result = this.delegate.getToolTipTopRightOverlay(CustomizedContentProviderUtils.resolve(element), eTypedElement);
		}
		return result;
	}

	public Image getToolTipBottomLeftOverlay(final Object element, final ETypedElement eTypedElement) {
		Image result;
		if (element instanceof EReferenceTreeElement) {
			final EReferenceTreeElement treeElement = (EReferenceTreeElement) element;
			final EObjectTreeElement parent = treeElement.getParent();
			result = this.delegate.getToolTipBottomLeftOverlay(parent.getEObject(), treeElement.getEReference());
		} else if (element instanceof EAttributeTreeElement) {
			final EAttributeTreeElement treeElement = (EAttributeTreeElement) element;
			final EObjectTreeElement parent = (EObjectTreeElement) treeElement.getParent();
			result = this.delegate.getToolTipBottomLeftOverlay(parent.getEObject(), treeElement.getEAttribute());
		} else {
			result = this.delegate.getToolTipBottomLeftOverlay(CustomizedContentProviderUtils.resolve(element), eTypedElement);
		}
		return result;
	}

	public Image getToolTipBottomMiddleOverlay(final Object element, final ETypedElement eTypedElement) {
		Image result;
		if (element instanceof EReferenceTreeElement) {
			final EReferenceTreeElement treeElement = (EReferenceTreeElement) element;
			final EObjectTreeElement parent = treeElement.getParent();
			result = this.delegate.getToolTipBottomMiddleOverlay(parent.getEObject(), treeElement.getEReference());
		} else if (element instanceof EAttributeTreeElement) {
			final EAttributeTreeElement treeElement = (EAttributeTreeElement) element;
			final EObjectTreeElement parent = (EObjectTreeElement) treeElement.getParent();
			result = this.delegate.getToolTipBottomMiddleOverlay(parent.getEObject(), treeElement.getEAttribute());
		} else {
			result = this.delegate.getToolTipBottomMiddleOverlay(CustomizedContentProviderUtils.resolve(element), eTypedElement);
		}
		return result;
	}

	public Image getToolTipBottomRightOverlay(final Object element, final ETypedElement eTypedElement) {
		Image result;
		if (element instanceof EReferenceTreeElement) {
			final EReferenceTreeElement treeElement = (EReferenceTreeElement) element;
			final EObjectTreeElement parent = treeElement.getParent();
			result = this.delegate.getToolTipBottomRightOverlay(parent.getEObject(), treeElement.getEReference());
		} else if (element instanceof EAttributeTreeElement) {
			final EAttributeTreeElement treeElement = (EAttributeTreeElement) element;
			final EObjectTreeElement parent = (EObjectTreeElement) treeElement.getParent();
			result = this.delegate.getToolTipBottomRightOverlay(parent.getEObject(), treeElement.getEAttribute());
		} else {
			result = this.delegate.getToolTipBottomRightOverlay(CustomizedContentProviderUtils.resolve(element), eTypedElement);
		}
		return result;
	}

	public int getToolTipTimeDisplayed(final Object element, final ETypedElement eTypedElement) {
		int result;
		if (element instanceof EReferenceTreeElement) {
			final EReferenceTreeElement treeElement = (EReferenceTreeElement) element;
			final EObjectTreeElement parent = treeElement.getParent();
			result = this.delegate.getToolTipTimeDisplayed(parent.getEObject(), treeElement.getEReference());
		} else if (element instanceof EAttributeTreeElement) {
			final EAttributeTreeElement treeElement = (EAttributeTreeElement) element;
			final EObjectTreeElement parent = (EObjectTreeElement) treeElement.getParent();
			result = this.delegate.getToolTipTimeDisplayed(parent.getEObject(), treeElement.getEAttribute());
		} else {
			result = this.delegate.getToolTipTimeDisplayed(CustomizedContentProviderUtils.resolve(element), eTypedElement);
		}
		return result;
	}

	public int getToolTipDisplayDelayTime(final Object element, final ETypedElement eTypedElement) {
		int result;
		if (element instanceof EReferenceTreeElement) {
			final EReferenceTreeElement treeElement = (EReferenceTreeElement) element;
			final EObjectTreeElement parent = treeElement.getParent();
			result = this.delegate.getToolTipDisplayDelayTime(parent.getEObject(), treeElement.getEReference());
		} else if (element instanceof EAttributeTreeElement) {
			final EAttributeTreeElement treeElement = (EAttributeTreeElement) element;
			final EObjectTreeElement parent = (EObjectTreeElement) treeElement.getParent();
			result = this.delegate.getToolTipDisplayDelayTime(parent.getEObject(), treeElement.getEAttribute());
		} else {
			result = this.delegate.getToolTipDisplayDelayTime(CustomizedContentProviderUtils.resolve(element), eTypedElement);
		}
		return result;
	}

	public int getToolTipStyle(final Object element, final ETypedElement eTypedElement) {
		int result;
		if (element instanceof EReferenceTreeElement) {
			final EReferenceTreeElement treeElement = (EReferenceTreeElement) element;
			final EObjectTreeElement parent = treeElement.getParent();
			result = this.delegate.getToolTipStyle(parent.getEObject(), treeElement.getEReference());
		} else if (element instanceof EAttributeTreeElement) {
			final EAttributeTreeElement treeElement = (EAttributeTreeElement) element;
			final EObjectTreeElement parent = (EObjectTreeElement) treeElement.getParent();
			result = this.delegate.getToolTipStyle(parent.getEObject(), treeElement.getEAttribute());
		} else {
			result = this.delegate.getToolTipStyle(CustomizedContentProviderUtils.resolve(element), eTypedElement);
		}
		return result;
	}

	public ICustomizationManager getCustomizationManager() {
		return this.delegate.getCustomizationManager();
	}

	public boolean isUnderlined(final Object element, final ETypedElement eTypedElement) {
		boolean result;
		if (element instanceof EReferenceTreeElement) {
			final EReferenceTreeElement treeElement = (EReferenceTreeElement) element;
			final EObjectTreeElement parent = treeElement.getParent();
			result = this.delegate.isUnderlined(parent.getEObject(), treeElement.getEReference());
		} else if (element instanceof EAttributeTreeElement) {
			final EAttributeTreeElement treeElement = (EAttributeTreeElement) element;
			final EObjectTreeElement parent = (EObjectTreeElement) treeElement.getParent();
			result = this.delegate.isUnderlined(parent.getEObject(), treeElement.getEAttribute());
		} else {
			result = this.delegate.isUnderlined(CustomizedContentProviderUtils.resolve(element), eTypedElement);
		}
		return result;
	}

	public boolean isStruckthrough(final Object element, final ETypedElement eTypedElement) {
		boolean result;
		if (element instanceof EReferenceTreeElement) {
			final EReferenceTreeElement treeElement = (EReferenceTreeElement) element;
			final EObjectTreeElement parent = treeElement.getParent();
			result = this.delegate.isStruckthrough(parent.getEObject(), treeElement.getEReference());
		} else if (element instanceof EAttributeTreeElement) {
			final EAttributeTreeElement treeElement = (EAttributeTreeElement) element;
			final EObjectTreeElement parent = (EObjectTreeElement) treeElement.getParent();
			result = this.delegate.isStruckthrough(parent.getEObject(), treeElement.getEAttribute());
		} else {
			result = this.delegate.isStruckthrough(CustomizedContentProviderUtils.resolve(element), eTypedElement);
		}
		return result;
	}

	public Image getTopLeftOverlay(final Object element, final ETypedElement eTypedElement) {
		Image result;
		if (element instanceof EReferenceTreeElement) {
			final EReferenceTreeElement treeElement = (EReferenceTreeElement) element;
			final EObjectTreeElement parent = treeElement.getParent();
			result = this.delegate.getTopLeftOverlay(parent.getEObject(), treeElement.getEReference());
		} else if (element instanceof EAttributeTreeElement) {
			final EAttributeTreeElement treeElement = (EAttributeTreeElement) element;
			final EObjectTreeElement parent = (EObjectTreeElement) treeElement.getParent();
			result = this.delegate.getTopLeftOverlay(parent.getEObject(), treeElement.getEAttribute());
		} else {
			result = this.delegate.getTopLeftOverlay(CustomizedContentProviderUtils.resolve(element), eTypedElement);
		}
		return result;
	}

	public Image getTopMiddleOverlay(final Object element, final ETypedElement eTypedElement) {
		Image result;
		if (element instanceof EReferenceTreeElement) {
			final EReferenceTreeElement treeElement = (EReferenceTreeElement) element;
			final EObjectTreeElement parent = treeElement.getParent();
			result = this.delegate.getTopMiddleOverlay(parent.getEObject(), treeElement.getEReference());
		} else if (element instanceof EAttributeTreeElement) {
			final EAttributeTreeElement treeElement = (EAttributeTreeElement) element;
			final EObjectTreeElement parent = (EObjectTreeElement) treeElement.getParent();
			result = this.delegate.getTopMiddleOverlay(parent.getEObject(), treeElement.getEAttribute());
		} else {
			result = this.delegate.getTopMiddleOverlay(CustomizedContentProviderUtils.resolve(element), eTypedElement);
		}
		return result;
	}

	public Image getTopRightOverlay(final Object element, final ETypedElement eTypedElement) {
		Image result;
		if (element instanceof EReferenceTreeElement) {
			final EReferenceTreeElement treeElement = (EReferenceTreeElement) element;
			final EObjectTreeElement parent = treeElement.getParent();
			result = this.delegate.getTopRightOverlay(parent.getEObject(), treeElement.getEReference());
		} else if (element instanceof EAttributeTreeElement) {
			final EAttributeTreeElement treeElement = (EAttributeTreeElement) element;
			final EObjectTreeElement parent = (EObjectTreeElement) treeElement.getParent();
			result = this.delegate.getTopRightOverlay(parent.getEObject(), treeElement.getEAttribute());
		} else {
			result = this.delegate.getTopRightOverlay(CustomizedContentProviderUtils.resolve(element), eTypedElement);
		}
		return result;
	}

	public Image getBottomLeftOverlay(final Object element, final ETypedElement eTypedElement) {
		Image result;
		if (element instanceof EReferenceTreeElement) {
			final EReferenceTreeElement treeElement = (EReferenceTreeElement) element;
			final EObjectTreeElement parent = treeElement.getParent();
			result = this.delegate.getBottomLeftOverlay(parent.getEObject(), treeElement.getEReference());
		} else if (element instanceof EAttributeTreeElement) {
			final EAttributeTreeElement treeElement = (EAttributeTreeElement) element;
			final EObjectTreeElement parent = (EObjectTreeElement) treeElement.getParent();
			result = this.delegate.getBottomLeftOverlay(parent.getEObject(), treeElement.getEAttribute());
		} else {
			result = this.delegate.getBottomLeftOverlay(CustomizedContentProviderUtils.resolve(element), eTypedElement);
		}
		return result;
	}

	public Image getBottomMiddleOverlay(final Object element, final ETypedElement eTypedElement) {
		Image result;
		if (element instanceof EReferenceTreeElement) {
			final EReferenceTreeElement treeElement = (EReferenceTreeElement) element;
			final EObjectTreeElement parent = treeElement.getParent();
			result = this.delegate.getBottomMiddleOverlay(parent.getEObject(), treeElement.getEReference());
		} else if (element instanceof EAttributeTreeElement) {
			final EAttributeTreeElement treeElement = (EAttributeTreeElement) element;
			final EObjectTreeElement parent = (EObjectTreeElement) treeElement.getParent();
			result = this.delegate.getBottomMiddleOverlay(parent.getEObject(), treeElement.getEAttribute());
		} else {
			result = this.delegate.getBottomMiddleOverlay(CustomizedContentProviderUtils.resolve(element), eTypedElement);
		}
		return result;
	}

	public Image getBottomRightOverlay(final Object element, final ETypedElement eTypedElement) {
		Image result;
		if (element instanceof EReferenceTreeElement) {
			final EReferenceTreeElement treeElement = (EReferenceTreeElement) element;
			final EObjectTreeElement parent = treeElement.getParent();
			result = this.delegate.getBottomRightOverlay(parent.getEObject(), treeElement.getEReference());
		} else if (element instanceof EAttributeTreeElement) {
			final EAttributeTreeElement treeElement = (EAttributeTreeElement) element;
			final EObjectTreeElement parent = (EObjectTreeElement) treeElement.getParent();
			result = this.delegate.getBottomRightOverlay(parent.getEObject(), treeElement.getEAttribute());
		} else {
			result = this.delegate.getBottomRightOverlay(CustomizedContentProviderUtils.resolve(element), eTypedElement);
		}
		return result;
	}

	public String getText(final Object element, final ETypedElement eTypedElement) {
		String result;
		if (element instanceof EReferenceTreeElement) {
			final EReferenceTreeElement treeElement = (EReferenceTreeElement) element;
			final EObjectTreeElement parent = treeElement.getParent();
			result = this.delegate.getText(parent.getEObject(), treeElement.getEReference());
		} else if (element instanceof EAttributeTreeElement) {
			final EAttributeTreeElement treeElement = (EAttributeTreeElement) element;
			final EObjectTreeElement parent = (EObjectTreeElement) treeElement.getParent();
			result = this.delegate.getText(parent.getEObject(), treeElement.getEAttribute());
		} else {
			result = this.delegate.getText(CustomizedContentProviderUtils.resolve(element), eTypedElement);
		}
		return result;
	}

	public Image getImage(final Object element, final ETypedElement eTypedElement) {
		Image result;
		if (element instanceof EReferenceTreeElement) {
			final EReferenceTreeElement treeElement = (EReferenceTreeElement) element;
			final EObjectTreeElement parent = treeElement.getParent();
			result = this.delegate.getImage(parent.getEObject(), treeElement.getEReference());
		} else if (element instanceof EAttributeTreeElement) {
			final EAttributeTreeElement treeElement = (EAttributeTreeElement) element;
			final EObjectTreeElement parent = (EObjectTreeElement) treeElement.getParent();
			result = this.delegate.getImage(parent.getEObject(), treeElement.getEAttribute());
		} else {
			result = this.delegate.getImage(CustomizedContentProviderUtils.resolve(element), eTypedElement);
		}
		return result;
	}

	public ICustomizedLabelProvider cloneLabelProvider() {
		return new ResolvingCustomizedLabelProvider(this.delegate.cloneLabelProvider());
	}

}
