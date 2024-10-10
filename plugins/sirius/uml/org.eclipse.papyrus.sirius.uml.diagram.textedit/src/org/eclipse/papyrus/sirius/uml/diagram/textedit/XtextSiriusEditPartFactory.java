/******************************************************************************
 * Copyright (c) 2021 CEA LIST, Artal Technologies
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Yann Binot (Artal Technologies) <yann.binot@artal.fr>
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.textedit;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.sirius.uml.diagram.textedit.editpart.XtextDEdgeNameEditPart;
import org.eclipse.papyrus.sirius.uml.diagram.textedit.editpart.XtextSiriusDNode3EditPart;
import org.eclipse.papyrus.sirius.uml.diagram.textedit.editpart.XtextSiriusDNodeContainerName2EditPart;
import org.eclipse.papyrus.sirius.uml.diagram.textedit.editpart.XtextSiriusDNodeContainerNameEditPart;
import org.eclipse.papyrus.sirius.uml.diagram.textedit.editpart.XtextSiriusDNodeListElementEditPart;
import org.eclipse.papyrus.sirius.uml.diagram.textedit.editpart.XtextSiriusDNodeNameEditPart;
import org.eclipse.sirius.diagram.ui.internal.edit.parts.DEdgeNameEditPart;
import org.eclipse.sirius.diagram.ui.internal.edit.parts.DNode3EditPart;
import org.eclipse.sirius.diagram.ui.internal.edit.parts.DNodeContainerName2EditPart;
import org.eclipse.sirius.diagram.ui.internal.edit.parts.DNodeContainerNameEditPart;
import org.eclipse.sirius.diagram.ui.internal.edit.parts.DNodeListElementEditPart;
import org.eclipse.sirius.diagram.ui.internal.edit.parts.NotationViewIDs;
import org.eclipse.sirius.diagram.ui.internal.edit.parts.SiriusEditPartFactory;
import org.eclipse.sirius.diagram.ui.part.SiriusVisualIDRegistry;

/**
 * A factory for creating ITextAwareEditPart EditPart objects.
 */
@SuppressWarnings("restriction")
public class XtextSiriusEditPartFactory extends SiriusEditPartFactory {

	/**
	 * Creates a new ITextAwareEditPart EditPart object.
	 * This method allows to extends Sirius graphical model elements to integrate Xtext.
	 *
	 * @param context
	 *            the context
	 * @param model
	 *            the model
	 * @return the edits the part
	 * @see org.eclipse.sirius.diagram.ui.internal.edit.parts.SiriusEditPartFactory#createEditPart(org.eclipse.gef.EditPart, java.lang.Object)
	 */

	@Override
	public EditPart createEditPart(EditPart context, Object model) {

		if (model instanceof View) {
			final View view = (View) model;

	        //For other edit part
			switch (SiriusVisualIDRegistry.getVisualID(view)) {
			case NotationViewIDs.DNODE_NAME_EDIT_PART_VISUAL_ID:
			case NotationViewIDs.DNODE_NAME_2_EDIT_PART_VISUAL_ID:
			case NotationViewIDs.DNODE_NAME_3_EDIT_PART_VISUAL_ID:
			case NotationViewIDs.DNODE_NAME_4_EDIT_PART_VISUAL_ID:
				return new XtextSiriusDNodeNameEditPart(view);
			case DNodeContainerName2EditPart.VISUAL_ID:
				return new XtextSiriusDNodeContainerName2EditPart(view);
			case DNodeContainerNameEditPart.VISUAL_ID:
				return new XtextSiriusDNodeContainerNameEditPart(view);
			case DNodeListElementEditPart.VISUAL_ID:
				return new XtextSiriusDNodeListElementEditPart(view);
			case DNode3EditPart.VISUAL_ID:
				return new XtextSiriusDNode3EditPart(view);
			case DEdgeNameEditPart.VISUAL_ID:
				return new XtextDEdgeNameEditPart(view);
			
				
			}
			
		}

		return super.createEditPart(context, model);
	}

}
