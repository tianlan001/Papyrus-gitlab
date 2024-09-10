/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.timing.custom.edit.policies;

import org.eclipse.papyrus.uml.diagram.common.editpolicies.UMLTextSelectionEditPolicy;
import org.eclipse.papyrus.uml.diagram.timing.custom.figures.LifelineVerticalLabel;

/** Shows feedback for the selection of a Lifeline name vertical label. */
public class CustomLifelineNameEditPolicy extends UMLTextSelectionEditPolicy {

	@Override
	protected void showPrimarySelection() {
		final LifelineVerticalLabel label = (LifelineVerticalLabel) getHostFigure();
		label.setSelected(true);
		label.setFocus(true);
	}

	@Override
	protected void showSelection() {
		final LifelineVerticalLabel label = (LifelineVerticalLabel) getHostFigure();
		label.setSelected(true);
		label.setFocus(false);
	}

	@Override
	protected void hideSelection() {
		final LifelineVerticalLabel label = (LifelineVerticalLabel) getHostFigure();
		label.setSelected(false);
		label.setFocus(false);
	}

	@Override
	protected void showFocus() {
		final LifelineVerticalLabel label = (LifelineVerticalLabel) getHostFigure();
		label.setFocus(true);
	}

	@Override
	protected void hideFocus() {
		final LifelineVerticalLabel label = (LifelineVerticalLabel) getHostFigure();
		label.setFocus(false);
	}
}
