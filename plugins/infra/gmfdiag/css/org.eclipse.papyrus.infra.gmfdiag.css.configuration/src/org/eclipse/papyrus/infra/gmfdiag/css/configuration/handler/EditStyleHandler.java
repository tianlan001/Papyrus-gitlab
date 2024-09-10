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

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.AttributeSelector;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.css_declaration;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.ruleset;
import org.eclipse.papyrus.infra.gmfdiag.css3.cSS.stylesheet;
import org.eclipse.swt.widgets.Shell;


public class EditStyleHandler extends AbstractStyleHandler {

	@Override
	protected AbstractStyleDialog createStyleDialog(Shell shell, Map<css_declaration, Boolean> declarations, Map<AttributeSelector, Boolean> conditions, String selectorName, View context) {
		return new StyleEditionDialog(shell, conditions, declarations, selectorName, context);
	}

	@Override
	protected ruleset getRuleset(AbstractStyleDialog dialog) {
		ruleset ruleset = ((StyleEditionDialog) dialog).getSelectedRuleset();
		ruleset.getSelectors().clear();
		ruleset.getDeclarations().clear();
		return ruleset;
	}

	@Override
	protected stylesheet getStyleSheet(AbstractStyleDialog dialog, View contextView) {
		return ((StyleEditionDialog) dialog).getStylesheet();
	}

}
