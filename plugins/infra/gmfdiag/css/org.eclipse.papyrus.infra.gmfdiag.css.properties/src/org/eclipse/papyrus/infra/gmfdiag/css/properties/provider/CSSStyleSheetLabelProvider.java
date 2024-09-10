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
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css.properties.provider;

import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.EmbeddedStyleSheet;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.StyleSheetReference;
import org.eclipse.papyrus.infra.ui.emf.providers.EMFLabelProvider;


public class CSSStyleSheetLabelProvider extends EMFLabelProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof StyleSheetReference) {
			return ((StyleSheetReference) element).getPath();
		} else if (element instanceof EmbeddedStyleSheet) {
			EmbeddedStyleSheet styleSheet = (EmbeddedStyleSheet) element;
			if (styleSheet.getLabel() == null || "".equals(styleSheet.getLabel())) {
				return "EmbeddedStyleSheet";
			}
			return styleSheet.getLabel();
		}
		return super.getText(element);
	}
}
