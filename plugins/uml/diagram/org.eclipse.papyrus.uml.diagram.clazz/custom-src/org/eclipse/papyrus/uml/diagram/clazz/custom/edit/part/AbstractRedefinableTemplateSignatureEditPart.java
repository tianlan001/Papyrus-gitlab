/*****************************************************************************
 * Copyright (c) 2009, 2021 CEA LIST, ARTAL.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Etienne ALLOGO (ARTAL) etienne.allogo@artal.fr - Bug 569174 : L1.2 Fix border node after cleanup code
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.custom.edit.part;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.editparts.AbstractBorderItemEditPart;

/**
 * this a editpart to force the refresh after change the size of the border item
 */
public abstract class AbstractRedefinableTemplateSignatureEditPart extends AbstractBorderItemEditPart {

	public AbstractRedefinableTemplateSignatureEditPart(View view) {
		super(view);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void refreshBounds() {
		super.refreshBounds();
		// ensure refreshing figures
		getFigure().getParent().getLayoutManager().layout(getFigure().getParent());
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		refreshTransparency();
	}
}
