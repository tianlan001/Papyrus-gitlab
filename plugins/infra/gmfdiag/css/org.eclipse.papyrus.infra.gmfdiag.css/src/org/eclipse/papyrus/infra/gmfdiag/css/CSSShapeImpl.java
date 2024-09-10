/*****************************************************************************
 * Copyright (c) 2012, 2016, 2019 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bugs 433206, 436665
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 544547
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.notation.NamedStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.gmf.runtime.notation.impl.ShapeImpl;
import org.eclipse.papyrus.infra.gmfdiag.css.engine.ExtendedCSSEngine;
import org.eclipse.papyrus.infra.gmfdiag.css.notation.CSSDiagram;
import org.eclipse.papyrus.infra.gmfdiag.css.notation.ForceValueHelper;
import org.eclipse.papyrus.infra.gmfdiag.css.provider.CSSCustomStyleDelegate;
import org.eclipse.papyrus.infra.gmfdiag.css.provider.CustomStyle;
import org.eclipse.papyrus.infra.gmfdiag.css.style.CSSShapeStyle;
import org.eclipse.papyrus.infra.gmfdiag.css.style.CSSView;
import org.eclipse.papyrus.infra.gmfdiag.css.style.impl.CSSShapeStyleDelegate;
import org.eclipse.papyrus.infra.gmfdiag.css.style.impl.CSSViewDelegate;

public class CSSShapeImpl extends ShapeImpl implements CSSShapeStyle, CustomStyle, CSSView.Internal {

	protected ExtendedCSSEngine engine;

	private CSSShapeStyle shapeStyle;

	private CSSView cssView;

	private CustomStyle customStyle;

	protected CSSShapeStyle getShapeStyle() {
		if (shapeStyle == null) {
			shapeStyle = new CSSShapeStyleDelegate(this, getEngine());
		}
		return shapeStyle;
	}

	protected CustomStyle getCustomStyle() {
		if (customStyle == null) {
			customStyle = new CSSCustomStyleDelegate(this, getEngine());
		}
		return customStyle;
	}

	protected CSSView getCSSView() {
		if (cssView == null) {
			cssView = new CSSViewDelegate(this, getEngine());
		}
		return cssView;
	}

	@Override
	public void resetCSS() {
		cssView = null;
		engine = null;
	}

	protected ExtendedCSSEngine getEngine() {
		if (engine == null) {
			engine = ((CSSDiagram) getDiagram()).getEngine();
		}
		return engine;
	}

	// ////////////////////////////////////////
	// Forwards accesses to CSS properties //
	// ////////////////////////////////////////


	@Override
	public int getCSSFontColor() {
		int value = super.getFontColor();

		if (ForceValueHelper.isSet(this, NotationPackage.eINSTANCE.getFontStyle_FontColor(), value)) {
			return value;
		} else {
			return getShapeStyle().getCSSFontColor();
		}
	}

	@Override
	public java.lang.String getCSSFontName() {
		java.lang.String value = super.getFontName();

		if (ForceValueHelper.isSet(this, NotationPackage.eINSTANCE.getFontStyle_FontName(), value)) {
			return value;
		} else {
			return getShapeStyle().getCSSFontName();
		}
	}

	@Override
	public int getCSSFontHeight() {
		int value = super.getFontHeight();

		if (ForceValueHelper.isSet(this, NotationPackage.eINSTANCE.getFontStyle_FontHeight(), value)) {
			return value;
		} else {
			return getShapeStyle().getCSSFontHeight();
		}
	}

	@Override
	public boolean isCSSBold() {
		boolean value = super.isBold();

		if (ForceValueHelper.isSet(this, NotationPackage.eINSTANCE.getFontStyle_Bold(), value)) {
			return value;
		} else {
			return getShapeStyle().isCSSBold();
		}
	}

	@Override
	public boolean isCSSItalic() {
		boolean value = super.isItalic();

		if (ForceValueHelper.isSet(this, NotationPackage.eINSTANCE.getFontStyle_Italic(), value)) {
			return value;
		} else {
			return getShapeStyle().isCSSItalic();
		}
	}

	@Override
	public boolean isCSSUnderline() {
		boolean value = super.isUnderline();

		if (ForceValueHelper.isSet(this, NotationPackage.eINSTANCE.getFontStyle_Underline(), value)) {
			return value;
		} else {
			return getShapeStyle().isCSSUnderline();
		}
	}

	@Override
	public boolean isCSSStrikeThrough() {
		boolean value = super.isStrikeThrough();

		if (ForceValueHelper.isSet(this, NotationPackage.eINSTANCE.getFontStyle_StrikeThrough(), value)) {
			return value;
		} else {
			return getShapeStyle().isCSSStrikeThrough();
		}
	}

	@Override
	public boolean isCSSVisible() {
		boolean value = super.isVisible();

		if (ForceValueHelper.isSet(this, NotationPackage.eINSTANCE.getView_Visible(), value)) {
			return value;
		} else {
			return getCSSView().isCSSVisible();
		}
	}

	@Override
	public java.lang.String getCSSDescription() {
		java.lang.String value = super.getDescription();

		if (ForceValueHelper.isSet(this, NotationPackage.eINSTANCE.getDescriptionStyle_Description(), value)) {
			return value;
		} else {
			return getShapeStyle().getCSSDescription();
		}
	}

	@Override
	public int getCSSFillColor() {
		int value = super.getFillColor();

		if (ForceValueHelper.isSet(this, NotationPackage.eINSTANCE.getFillStyle_FillColor(), value)) {
			return value;
		} else {
			return getShapeStyle().getCSSFillColor();
		}
	}

	@Override
	public int getCSSTransparency() {
		int value = super.getTransparency();

		if (ForceValueHelper.isSet(this, NotationPackage.eINSTANCE.getFillStyle_Transparency(), value)) {
			return value;
		} else {
			return getShapeStyle().getCSSTransparency();
		}
	}

	@Override
	public org.eclipse.gmf.runtime.notation.datatype.GradientData getCSSGradient() {
		org.eclipse.gmf.runtime.notation.datatype.GradientData value = super.getGradient();

		if (ForceValueHelper.isSet(this, NotationPackage.eINSTANCE.getFillStyle_Gradient(), value)) {
			return value;
		} else {
			return getShapeStyle().getCSSGradient();
		}
	}

	@Override
	public int getCSSLineColor() {
		int value = super.getLineColor();

		if (ForceValueHelper.isSet(this, NotationPackage.eINSTANCE.getLineStyle_LineColor(), value)) {
			return value;
		} else {
			return getShapeStyle().getCSSLineColor();
		}
	}

	@Override
	public int getCSSLineWidth() {
		int value = super.getLineWidth();

		if (ForceValueHelper.isSet(this, NotationPackage.eINSTANCE.getLineStyle_LineWidth(), value)) {
			return value;
		} else {
			return getShapeStyle().getCSSLineWidth();
		}
	}

	@Override
	public int getCSSRoundedBendpointsRadius() {
		int value = super.getRoundedBendpointsRadius();

		if (ForceValueHelper.isSet(this, NotationPackage.eINSTANCE.getRoundedCornersStyle_RoundedBendpointsRadius(), value)) {
			return value;
		} else {
			return getShapeStyle().getCSSRoundedBendpointsRadius();
		}
	}


	@Override
	public int getFontColor() {
		return getCSSFontColor();
	}

	@Override
	public java.lang.String getFontName() {
		return getCSSFontName();
	}

	@Override
	public int getFontHeight() {
		return getCSSFontHeight();
	}

	@Override
	public boolean isBold() {
		return isCSSBold();
	}

	@Override
	public boolean isItalic() {
		return isCSSItalic();
	}

	@Override
	public boolean isUnderline() {
		return isCSSUnderline();
	}

	@Override
	public boolean isStrikeThrough() {
		return isCSSStrikeThrough();
	}

	@Override
	public java.lang.String getDescription() {
		return getCSSDescription();
	}

	@Override
	public int getFillColor() {
		return getCSSFillColor();
	}

	@Override
	public int getTransparency() {
		return getCSSTransparency();
	}

	@Override
	public org.eclipse.gmf.runtime.notation.datatype.GradientData getGradient() {
		return getCSSGradient();
	}

	@Override
	public int getLineColor() {
		return getCSSLineColor();
	}

	@Override
	public int getLineWidth() {
		return getCSSLineWidth();
	}

	@Override
	public int getRoundedBendpointsRadius() {
		return getCSSRoundedBendpointsRadius();
	}

	@Override
	public boolean isVisible() {
		return isCSSVisible();
	}

	// //////////////////////////////////////////////
	// Implements a setter for each CSS property //
	// //////////////////////////////////////////////

	@Override
	public void setVisible(boolean value) {
		super.setVisible(value);

		EStructuralFeature feature = NotationPackage.eINSTANCE.getView_Visible();
		ForceValueHelper.setValue(this, feature, value);
	}

	@Override
	public void setType(java.lang.String value) {
		super.setType(value);

		EStructuralFeature feature = NotationPackage.eINSTANCE.getView_Type();
		ForceValueHelper.setValue(this, feature, value);
	}

	@Override
	public void setMutable(boolean value) {
		super.setMutable(value);

		EStructuralFeature feature = NotationPackage.eINSTANCE.getView_Mutable();
		ForceValueHelper.setValue(this, feature, value);
	}

	@Override
	public void setFontColor(int value) {
		super.setFontColor(value);

		EStructuralFeature feature = NotationPackage.eINSTANCE.getFontStyle_FontColor();
		ForceValueHelper.setValue(this, feature, value);
	}

	@Override
	public void setFontName(java.lang.String value) {
		super.setFontName(value);

		EStructuralFeature feature = NotationPackage.eINSTANCE.getFontStyle_FontName();
		ForceValueHelper.setValue(this, feature, value);
	}

	@Override
	public void setFontHeight(int value) {
		super.setFontHeight(value);

		EStructuralFeature feature = NotationPackage.eINSTANCE.getFontStyle_FontHeight();
		ForceValueHelper.setValue(this, feature, value);
	}

	@Override
	public void setBold(boolean value) {
		super.setBold(value);

		EStructuralFeature feature = NotationPackage.eINSTANCE.getFontStyle_Bold();
		ForceValueHelper.setValue(this, feature, value);
	}

	@Override
	public void setItalic(boolean value) {
		super.setItalic(value);

		EStructuralFeature feature = NotationPackage.eINSTANCE.getFontStyle_Italic();
		ForceValueHelper.setValue(this, feature, value);
	}

	@Override
	public void setUnderline(boolean value) {
		super.setUnderline(value);

		EStructuralFeature feature = NotationPackage.eINSTANCE.getFontStyle_Underline();
		ForceValueHelper.setValue(this, feature, value);
	}

	@Override
	public void setStrikeThrough(boolean value) {
		super.setStrikeThrough(value);

		EStructuralFeature feature = NotationPackage.eINSTANCE.getFontStyle_StrikeThrough();
		ForceValueHelper.setValue(this, feature, value);
	}

	@Override
	public void setDescription(java.lang.String value) {
		super.setDescription(value);

		EStructuralFeature feature = NotationPackage.eINSTANCE.getDescriptionStyle_Description();
		ForceValueHelper.setValue(this, feature, value);
	}

	@Override
	public void setFillColor(int value) {
		super.setFillColor(value);

		EStructuralFeature feature = NotationPackage.eINSTANCE.getFillStyle_FillColor();
		ForceValueHelper.setValue(this, feature, value);
	}

	@Override
	public void setTransparency(int value) {
		super.setTransparency(value);

		EStructuralFeature feature = NotationPackage.eINSTANCE.getFillStyle_Transparency();
		ForceValueHelper.setValue(this, feature, value);
	}

	@Override
	public void setGradient(org.eclipse.gmf.runtime.notation.datatype.GradientData value) {
		super.setGradient(value);

		EStructuralFeature feature = NotationPackage.eINSTANCE.getFillStyle_Gradient();
		ForceValueHelper.setValue(this, feature, value);
	}

	@Override
	public void setLineColor(int value) {
		super.setLineColor(value);

		EStructuralFeature feature = NotationPackage.eINSTANCE.getLineStyle_LineColor();
		ForceValueHelper.setValue(this, feature, value);
	}

	@Override
	public void setLineWidth(int value) {
		super.setLineWidth(value);

		EStructuralFeature feature = NotationPackage.eINSTANCE.getLineStyle_LineWidth();
		ForceValueHelper.setValue(this, feature, value);
	}

	@Override
	public void setRoundedBendpointsRadius(int value) {
		super.setRoundedBendpointsRadius(value);

		EStructuralFeature feature = NotationPackage.eINSTANCE.getRoundedCornersStyle_RoundedBendpointsRadius();
		ForceValueHelper.setValue(this, feature, value);
	}

	// ////////////////////////////////
	// Implements the unset method //
	// ////////////////////////////////

	@Override
	public void eUnset(int featureId) {
		super.eUnset(featureId);

		EStructuralFeature feature = eClass().getEStructuralFeature(featureId);
		if (feature != null) {
			ForceValueHelper.unsetValue(this, feature);
		}
	}

	// /////////////////////////////////
	// Implements the custom styles //
	// /////////////////////////////////

	@Override
	public boolean showElementIcon() {
		return getCustomStyle().showElementIcon();
	}

	@Override
	public int getQualifiedNameDepth() {
		return getCustomStyle().getQualifiedNameDepth();
	}

	@Override
	public boolean showShadow() {
		return getCustomStyle().showShadow();
	}

	// ////////////////////////////////
	// Implements the getNamedStyle //
	// ////////////////////////////////

	@Override
	public NamedStyle getNamedStyle(EClass eClass, String name) {
		return getCSSNamedStyle(eClass, name);
	}

	@Override
	public NamedStyle getCSSNamedStyle(EClass eClass, String name) {
		NamedStyle userStyle = super.getNamedStyle(eClass, name);
		if (userStyle != null) {
			return userStyle;
		}

		return getCSSView().getCSSNamedStyle(eClass, name);
	}

	// /////////////////////////////////
	// Implements the getStyle method //
	// /////////////////////////////////

	@Override
	public Style getStyle(EClass eClass) {
		return getCSSStyle(eClass);
	}

	@Override
	public Style getCSSStyle(EClass eClass) {
		Style userStyle = super.getStyle(eClass);
		if (userStyle != null) {
			return userStyle;
		}

		return getCSSView().getCSSStyle(eClass);
	}

}
