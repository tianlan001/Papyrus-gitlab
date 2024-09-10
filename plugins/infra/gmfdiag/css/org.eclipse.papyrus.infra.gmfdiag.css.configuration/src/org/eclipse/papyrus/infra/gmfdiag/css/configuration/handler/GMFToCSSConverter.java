/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
 *  Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 479314
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css.configuration.handler;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.notation.GradientStyle;
import org.eclipse.gmf.runtime.notation.datatype.GradientData;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.CSSFactory;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.ColorTok;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.CssTok;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.IdentifierTok;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.IntegerTok;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.NumberTok;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.StringTok;
import org.eclipse.swt.graphics.Color;


public class GMFToCSSConverter {

	public static final GMFToCSSConverter instance = new GMFToCSSConverter();

	private GMFToCSSConverter() {

	}

	public List<CssTok> convert(Color color) {
		ColorTok hexColor = getColor(color);
		return getExpression(hexColor);
	}

	private ColorTok getColor(Color color) {
		ColorTok hexColor = CSSFactory.eINSTANCE.createColorTok();

		String hexString = twoDigitsHexString(color.getRed()) + twoDigitsHexString(color.getGreen()) + twoDigitsHexString(color.getBlue());
		hexColor.setValue("#" + hexString.toUpperCase());
		return hexColor;
	}

	private ColorTok getColor(int color) {
		Color swtColor = FigureUtilities.integerToColor(color);
		ColorTok result = getColor(swtColor);
		swtColor.dispose();
		return result;
	}

	private String twoDigitsHexString(int color) {
		String hexString = Integer.toHexString(color);
		if (hexString.length() < 2) {
			hexString = "0" + hexString;
		}
		return hexString;
	}

	public List<CssTok> convert(GradientData gradient) {
		if (gradient == null) {
			IdentifierTok noGradient = CSSFactory.eINSTANCE.createIdentifierTok();
			noGradient.setName("none");
			return getExpression(noGradient);
		}

		ColorTok gradientColor = getColor(gradient.getGradientColor1());

		IdentifierTok gradientStyle = CSSFactory.eINSTANCE.createIdentifierTok();
		int style = gradient.getGradientStyle();

		if (style == GradientStyle.HORIZONTAL) {
			gradientStyle.setName("horizontal");
		} else {
			gradientStyle.setName("vertical");
		}

		return getExpression(gradientColor, gradientStyle);
	}

	public List<CssTok> convert(String string) {
		StringTok stringValue = CSSFactory.eINSTANCE.createStringTok();
		stringValue.setValue("\"" + string + "\"");
		return getExpression(stringValue);
	}

	/**
	 * @since 2.0
	 */
	public List<CssTok> convert(Double doubleValue) {
		NumberTok numberValue = CSSFactory.eINSTANCE.createNumberTok();
		numberValue.setVal(doubleValue);
		// if (intValue < 0) {
		// UnaryOperator operator = CssFactory.eINSTANCE.createUnaryOperator();
		// operator.setOperator(UNARY.NEG);
		// numberValue.setOp(operator);
		// }

		return getExpression(numberValue);
	}

	public List<CssTok> convert(final Integer intValue) {
		IntegerTok integerValue = CSSFactory.eINSTANCE.createIntegerTok();
		integerValue.setVal(intValue);
		return getExpression(integerValue);
	}

	public List<CssTok> convert(Enumerator enumerated) {
		IdentifierTok literalValue = CSSFactory.eINSTANCE.createIdentifierTok();

		literalValue.setName(enumerated.getName());

		return getExpression(literalValue);
	}

	private List<CssTok> getExpression(CssTok... values) {
		List<CssTok> result = new LinkedList<CssTok>();
		for (CssTok value : values) {
			if (!result.isEmpty()) {
				result.add(CSSFactory.eINSTANCE.createWSTok());
			}
			result.add(value);
		}
		return result;
	}

	public List<CssTok> convert(boolean booleanValue) {
		IdentifierTok nameValue = CSSFactory.eINSTANCE.createIdentifierTok();
		nameValue.setName(booleanValue ? "true" : "false");
		return getExpression(nameValue);
	}
}
