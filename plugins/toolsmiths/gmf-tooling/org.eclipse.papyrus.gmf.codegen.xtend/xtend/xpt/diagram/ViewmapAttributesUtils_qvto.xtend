/*******************************************************************************
 * Copyright (c) 2006 - 2020 Borland Software Corporation, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Dmitry Stadnik (Borland) - initial API and implementation
 * 	  Michael Golubev (Montages) - #386838 - migrate to Xtend2
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package xpt.diagram

import org.eclipse.papyrus.gmf.codegen.gmfgen.DefaultSizeAttributes
import org.eclipse.papyrus.gmf.codegen.gmfgen.LabelOffsetAttributes
import org.eclipse.papyrus.gmf.codegen.gmfgen.ResizeConstraints
import org.eclipse.papyrus.gmf.codegen.gmfgen.StyleAttributes
import org.eclipse.papyrus.gmf.codegen.gmfgen.Viewmap
import org.eclipse.papyrus.gmf.codegen.gmfgen.ViewmapLayoutType

@com.google.inject.Singleton class ViewmapAttributesUtils_qvto {

	def boolean isStoringChildPositions(ViewmapLayoutType layoutType) {
		return ViewmapLayoutType::XY_LAYOUT == layoutType
	}

	def boolean isFixedFont(Viewmap xptSelf) {
		return xptSelf !== null && xptSelf.attributes.filter(typeof(StyleAttributes)).exists[fixedFont]
	}

	def boolean isFixedForeground(Viewmap xptSelf) {
		return xptSelf !== null && xptSelf.attributes.filter(typeof(StyleAttributes)).exists[fixedForeground]
	}

	def boolean isFixedBackground(Viewmap xptSelf) {
		return xptSelf !== null && xptSelf.attributes.filter(typeof(StyleAttributes)).exists[fixedBackground]
	}

	def boolean canUseShapeStyle(Viewmap xptSelf) {
		return xptSelf !== null && !(xptSelf.isFixedFont() || xptSelf.isFixedForeground() || xptSelf.isFixedBackground());
	}

	def ResizeConstraints getResizeConstraints(Viewmap viewmap) {
		if(viewmap === null) return null;
		return viewmap.attributes.filter(typeof(ResizeConstraints)).head
	}

	def DefaultSizeAttributes getDefaultSizeAttributes(Viewmap viewmap) {
		if(viewmap === null) return null;
		return viewmap.attributes.filter(typeof(DefaultSizeAttributes)).head
	}

	def LabelOffsetAttributes getLabelOffsetAttributes(Viewmap viewmap) {
		if(viewmap === null) return null;
		return viewmap.attributes.filter(typeof(LabelOffsetAttributes)).head
	}

	def int defaultSizeWidth(Viewmap viewmap, int defaultValue) {
		var result = getDefaultSizeAttributes(viewmap)
		return if(result === null) defaultValue else result.width
	}

	def int defaultSizeHeight(Viewmap viewmap, int defaultValue) {
		var result = getDefaultSizeAttributes(viewmap)
		return if(result === null) defaultValue else result.height
	}

	def int labelOffsetX(Viewmap viewmap, int defaultValue) {
		var result = getLabelOffsetAttributes(viewmap)
		return if(result === null) defaultValue else result.x
	}

	def int labelOffsetY(Viewmap viewmap, int defaultValue) {
		var result = getLabelOffsetAttributes(viewmap)
		return if(result === null) defaultValue else result.y
	}

}
