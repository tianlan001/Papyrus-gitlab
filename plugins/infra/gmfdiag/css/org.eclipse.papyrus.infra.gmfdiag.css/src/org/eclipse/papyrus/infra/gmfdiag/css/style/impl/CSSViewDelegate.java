/*****************************************************************************
 * Copyright (c) 2013, 2015 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bug 433206
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css.style.impl;

import java.util.Arrays;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.gmf.runtime.notation.BooleanValueStyle;
import org.eclipse.gmf.runtime.notation.CanonicalStyle;
import org.eclipse.gmf.runtime.notation.DoubleValueStyle;
import org.eclipse.gmf.runtime.notation.IntValueStyle;
import org.eclipse.gmf.runtime.notation.NamedStyle;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.StringListValueStyle;
import org.eclipse.gmf.runtime.notation.StringValueStyle;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.css.Activator;
import org.eclipse.papyrus.infra.gmfdiag.css.engine.ExtendedCSSEngine;
import org.eclipse.papyrus.infra.gmfdiag.css.helper.ParserHelper;
import org.eclipse.papyrus.infra.gmfdiag.css.notation.CSSStyles;
import org.eclipse.papyrus.infra.gmfdiag.css.resource.CSSNotationResource;
import org.eclipse.papyrus.infra.gmfdiag.css.style.CSSView;
import org.w3c.dom.css.CSSValue;

public class CSSViewDelegate implements CSSView {

	private View view;

	private ExtendedCSSEngine engine;

	public CSSViewDelegate(View view, ExtendedCSSEngine engine) {
		this.view = view;
		this.engine = engine;
	}

	@Override
	public boolean isCSSVisible() {
		CSSValue cssValue = engine.retrievePropertyValue(view, "visible");
		if (cssValue == null) {
			Object defaultValue = NotationPackage.eINSTANCE.getView_Visible().getDefaultValue();
			return (Boolean) defaultValue;
		}

		return (Boolean) engine.convert(cssValue, Boolean.class, null);
	}

	private boolean lookupStyle = false;

	@Override
	public NamedStyle getCSSNamedStyle(EClass eClass, String name) {
		if (!CSSNotationResource.isCSSEnabled(view.eResource())) {
			return null;
		}

		if (!NotationPackage.eINSTANCE.getNamedStyle().isSuperTypeOf(eClass)) {
			return null;
		}

		// /////////////////////////////////////////////
		// This method may call getNamedStyle() to retrieve the applied CSS Styles. Prevent overflow
		if (CSSStyles.RESERVED_KEYWORDS.contains(name)) {
			return null;
		}

		CSSValue cssValue;
		synchronized (this) {
			if (lookupStyle) {
				return null;
			}

			try {
				lookupStyle = true;
				cssValue = engine.retrievePropertyValue(view, name);
				if (cssValue == null) {
					return null;
				}
			} finally {
				lookupStyle = false;
			}
		}
		// /////////////////////////////////////////////

		try {
			switch (eClass.getClassifierID()) {
			case NotationPackage.BOOLEAN_VALUE_STYLE:

				Boolean booleanValue = (Boolean) engine.convert(cssValue, Boolean.class, null);
				BooleanValueStyle booleanStyle = NotationFactory.eINSTANCE.createBooleanValueStyle();

				booleanStyle.setBooleanValue(booleanValue);
				booleanStyle.setName(name);

				return booleanStyle;

			case NotationPackage.STRING_VALUE_STYLE:

				String stringValue = (String) engine.convert(cssValue, String.class, null);
				StringValueStyle stringStyle = NotationFactory.eINSTANCE.createStringValueStyle();

				stringStyle.setName(name);
				stringStyle.setStringValue(stringValue);

				return stringStyle;

			case NotationPackage.INT_VALUE_STYLE:

				Integer intValue = (Integer) engine.convert(cssValue, Integer.class, null);
				IntValueStyle intStyle = NotationFactory.eINSTANCE.createIntValueStyle();

				intStyle.setName(name);
				intStyle.setIntValue(intValue);

				return intStyle;
			case NotationPackage.DOUBLE_VALUE_STYLE:

				Double doubleValue = (Double) engine.convert(cssValue, Double.class, null);
				DoubleValueStyle doubleStyle = NotationFactory.eINSTANCE.createDoubleValueStyle();

				doubleStyle.setName(name);
				doubleStyle.setDoubleValue(doubleValue);

				return doubleStyle;
			case NotationPackage.STRING_LIST_VALUE_STYLE:
				String[] values = ParserHelper.parseValues(engine, cssValue);

				StringListValueStyle stringListStyle = NotationFactory.eINSTANCE.createStringListValueStyle();
				stringListStyle.setName(name);
				stringListStyle.getStringListValue().addAll(Arrays.asList(values));

				return stringListStyle;
			default:
				return null;
			}
		} catch (Exception ex) {
			Activator.log.error(ex);
			return null;
		}
	}

	/**
	 * For now, we support delegation of single-attribute styles:
	 * <ul>
	 * <li>{@link CanonicalStyle}</li>
	 * </ul>
	 */
	@Override
	public Style getCSSStyle(EClass eClass) {
		if (NotationPackage.Literals.NAMED_STYLE.isSuperTypeOf(eClass)) {
			// Should use getCSSNamedStyle for these
			return null;
		}

		if (!NotationPackage.Literals.STYLE.isSuperTypeOf(eClass)) {
			// Not even a style. Sheesh
			return null;
		}

		// Gate on supported single-attribute styles
		switch (eClass.getClassifierID()) {
		case NotationPackage.CANONICAL_STYLE:
			break;
		default:
			return null;
		}

		// There is exactly one feature in this style (that's why we're here)
		final String attrName = eClass.getEStructuralFeatures().get(0).getName();

		// /////////////////////////////////////////////
		// This method may call getNamedStyle() to retrieve the applied CSS Styles. Prevent overflow
		CSSValue cssValue;
		synchronized (this) {
			if (lookupStyle) {
				return null;
			}

			try {
				lookupStyle = true;
				cssValue = engine.retrievePropertyValue(view, attrName);
				if (cssValue == null) {
					return null;
				}
			} finally {
				lookupStyle = false;
			}
		}
		// /////////////////////////////////////////////

		Style result = null;

		try {
			switch (eClass.getClassifierID()) {
			case NotationPackage.CANONICAL_STYLE:
				Boolean booleanValue = (Boolean) engine.convert(cssValue, Boolean.class, null);
				CanonicalStyle canonicalStyle = NotationFactory.eINSTANCE.createCanonicalStyle();

				canonicalStyle.setCanonical(booleanValue);

				result = canonicalStyle;
				break;
			default:
				break;
			}
		} catch (Exception e) {
			Activator.log.error(e);
		}

		return result;
	}
}
