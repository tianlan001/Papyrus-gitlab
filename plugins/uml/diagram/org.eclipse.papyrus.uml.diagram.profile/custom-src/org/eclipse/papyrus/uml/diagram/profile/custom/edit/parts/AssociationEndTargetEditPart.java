/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Adapted code from Class Diagram
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.profile.custom.edit.parts;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.profile.custom.helper.AssociationEndSourceLabelHelper;
import org.eclipse.papyrus.uml.diagram.profile.edit.parts.AssociationRoleTargetEditPart;
import org.eclipse.ui.views.properties.IPropertySource;


/**
 * The Class AssociationEndTargetEditPart.
 * Ensure the edition of ends in the diagram
 */
public class AssociationEndTargetEditPart extends AssociationRoleTargetEditPart {

	/**
	 * Instantiates a new association end target edit part.
	 *
	 * @param view
	 *            the view
	 */
	public AssociationEndTargetEditPart(View view) {
		super(view);

	}


	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart#resolveSemanticElement()
	 *
	 * @return
	 */

	@Override
	public EObject resolveSemanticElement() {
		return AssociationEndSourceLabelHelper.getInstance().getUMLElement(this);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class key) {
		if (key == IPropertySource.class) {
			return resolveSemanticElement();
		}
		return super.getAdapter(key);
	}
}
