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
package org.eclipse.papyrus.infra.gmfdiag.css.configuration.handler;

import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.papyrus.infra.gmfdiag.css.configuration.helper.XtextStylesheetHelper;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.StyleSheet;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.StyleSheetReference;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.AttributeSelector;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.CSSFactory;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.css_declaration;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.ruleset;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.stylesheet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;


public class CreateStyleHandler extends AbstractStyleHandler {

	@Override
	protected AbstractStyleDialog createStyleDialog(Shell shell, Map<css_declaration, Boolean> declarations, Map<AttributeSelector, Boolean> conditions, String selectorName, View context) {
		return new StyleCreationDialog(shell, conditions, declarations, selectorName, context);
	}

	@Override
	protected ruleset getRuleset(AbstractStyleDialog dialog) {
		return CSSFactory.eINSTANCE.createruleset();
	}

	@Override
	protected stylesheet getStyleSheet(AbstractStyleDialog dialog, View contextView) {
		StyleSheet styleSheet = ((StyleCreationDialog) dialog).getStyleSheet();

		if (styleSheet == null) {
			MessageDialog.open(MessageDialog.ERROR, dialog.getShell(), "Stylesheet error", "Invalid stylesheet", SWT.NONE);
			return null;
		}

		Resource resource;

		if (styleSheet instanceof StyleSheetReference) {
			resource = XtextStylesheetHelper.loadStylesheet((StyleSheetReference) styleSheet, null, contextView, dialog.getShell());
			if (resource == null) {
				return null;
			}
		} else {
			MessageDialog.open(MessageDialog.ERROR, dialog.getShell(), "Stylesheet error", "Embedded stylesheets are not yet supported", SWT.NONE);
			return null;
		}

		stylesheet xtextStylesheet;

		if (resource.getContents().isEmpty()) {
			xtextStylesheet = CSSFactory.eINSTANCE.createstylesheet();
			setCharset(xtextStylesheet, "UTF-8");
			resource.getContents().add(xtextStylesheet);
		} else {
			xtextStylesheet = (stylesheet) resource.getContents().get(0);
		}

		return xtextStylesheet;
	}

}
