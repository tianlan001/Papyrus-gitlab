/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, ALL4TEC and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.providers;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.ReferenceEdgeEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.ReferenceEdgeNameEditPart;
import org.eclipse.papyrus.uml.diagram.common.editparts.StereotypePropertyReferenceEdgeEditPart;

/**
 * {@link EditPartFactory} for Papyrus generic edit parts.
 *
 * @author Mickaël ADAM
 *
 * @since 3.1
 */
public class StereotypePropertyReferenceEdgeEditPartFactory implements EditPartFactory {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart, java.lang.Object)
	 */
	@Override
	public EditPart createEditPart(final EditPart context, final Object model) {
		EditPart editPart = null;
		if (model instanceof View) {
			View view = (View) model;
			switch (view.getType()) {

			case ReferenceEdgeEditPart.VISUAL_ID:
				editPart = new ReferenceEdgeEditPart(view);
				break;
			case StereotypePropertyReferenceEdgeEditPart.VISUAL_ID:
				editPart = new StereotypePropertyReferenceEdgeEditPart(view);
				break;
			case ReferenceEdgeNameEditPart.VISUAL_ID:
				editPart = new ReferenceEdgeNameEditPart(view);
				break;
			}
		}
		return editPart;
	}

}
