/*****************************************************************************
 * Copyright (c) 2012, 2015 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 480820
 *  Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 479314
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css.provider;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.appearance.helper.VisualInformationPapyrusConstants;
import org.eclipse.papyrus.infra.emf.appearance.style.AnnotationStyleProvider;
import org.eclipse.papyrus.infra.emf.appearance.style.AppearanceStyleProvider;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.NamedStyleProperties;
import org.eclipse.papyrus.infra.gmfdiag.css.engine.ExtendedCSSEngine;
import org.eclipse.papyrus.infra.gmfdiag.css.helper.CSSDOMSemanticElementHelper;
import org.eclipse.papyrus.infra.gmfdiag.css.helper.StringHelper;
import org.w3c.dom.Element;
import org.w3c.dom.css.CSSValue;

/**
 * Standard implementation of Papyrus Custom Style, CSS-based
 *
 * @author Camille Letavernier
 */
@SuppressWarnings("restriction")
public class CSSCustomStyleDelegate implements CustomStyle {

	private View view;

	private ExtendedCSSEngine engine;

	private Element element;

	private static final String NONE = "none"; //$NON-NLS-1$

	private static final String FULL = "full"; //$NON-NLS-1$

	private static int NONE_VALUE = 1000;

	private static int FULL_VALUE = 0;

	private static final AppearanceStyleProvider provider = new AnnotationStyleProvider();

	/**
	 * Constructor
	 *
	 * @param view
	 *            The GMF view on which the custom style is applied
	 * @param engine
	 *            The CSS engine used to handle the custom properties
	 */
	public CSSCustomStyleDelegate(View view, ExtendedCSSEngine engine) {

		this.view = CSSDOMSemanticElementHelper.getInstance().findPrimaryView(view);
		this.engine = engine;
		this.element = engine.getElement(this.view);
	}

	@Override
	public boolean showElementIcon() {
		EAnnotation displayNameLabelIcon = view.getEAnnotation(VisualInformationPapyrusConstants.DISPLAY_NAMELABELICON);
		if (displayNameLabelIcon != null) {
			return provider.showElementIcon(view);
		}

		CSSValue cssValue = engine.retrievePropertyValue(element, NamedStyleProperties.ELEMENT_ICON);
		if (cssValue == null) {
			return false;
		}
		return (Boolean) engine.convert(cssValue, Boolean.class, null);
	}

	@Override
	public int getQualifiedNameDepth() {
		EAnnotation qualifiedNameAnnotation = view.getEAnnotation(VisualInformationPapyrusConstants.QUALIFIED_NAME);
		if (qualifiedNameAnnotation != null) {
			return provider.getQualifiedNameDepth(view);
		}

		CSSValue cssValue = engine.retrievePropertyValue(element, NamedStyleProperties.QUALIFIED_NAME_DEPTH);
		if (cssValue == null) {
			return NONE_VALUE;
		}

		String cssText = cssValue.getCssText();

		if (StringHelper.equals(FULL, cssText)) {
			return FULL_VALUE;
		}

		if (StringHelper.equals(NONE, cssText)) {
			return NONE_VALUE;
		}

		try {
			int value = Integer.parseInt(cssText);
			return (value < 0) ? -value : value;
		} catch (NumberFormatException ex) {
			try {
				Double doubleValue = Double.parseDouble(cssText);
				int intValue = doubleValue.intValue();
				return (intValue < 0) ? -intValue : intValue;
			} catch (NumberFormatException ex2) {
				engine.handleExceptions(ex2);
				return NONE_VALUE;
			}
		}
	}

	@Override
	public boolean showShadow() {
		EAnnotation shadowAnnotation = view.getEAnnotation(VisualInformationPapyrusConstants.SHADOWFIGURE);

		if (shadowAnnotation != null) {
			return provider.showShadow(view);
		}

		CSSValue cssValue = engine.retrievePropertyValue(element, NamedStyleProperties.SHADOW);
		if (cssValue == null) {
			return false;
		}
		return (Boolean) engine.convert(cssValue, Boolean.class, null);
	}

}
