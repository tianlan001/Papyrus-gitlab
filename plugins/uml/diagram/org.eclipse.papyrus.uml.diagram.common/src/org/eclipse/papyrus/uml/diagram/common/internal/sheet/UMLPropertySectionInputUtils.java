/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.internal.sheet;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.ui.views.properties.IPropertySource;

/**
 * This class provides a method to convert a selected element into a valid input for the PropertySheet
 *
 * @since 3.3.200
 *
 */
public class UMLPropertySectionInputUtils {


	/**
	 * Modify/unwrap selection.
	 *
	 * @param selected
	 *            a selected element
	 * @return
	 *         a valid object to use for the property sheet
	 */
	public static final Object transformSelection(final Object selected) {
		if (selected == null) {
			return null;
		}
		if (selected instanceof IAdaptable) {
			IAdaptable adaptable = (IAdaptable) selected;

			Object adapter = adaptable.getAdapter(IPropertySource.class);
			if (adapter != null) {
				// This is a terminal transformation
				return adapter;
			}
			adapter = adaptable.getAdapter(EObject.class);
			if (adapter != null) {
				// This is a terminal transformation
				return adapter;
			}
			adapter = adaptable.getAdapter(View.class);
			if (adapter instanceof View) {
				// This is an intermediate transformation
				return transformSelection(adapter);
			}
		}
		if (selected instanceof EditPart) {
			// This is an intermediate transformation
			Object model = ((EditPart) selected).getModel();
			return transformSelection(model);
		}
		if (selected instanceof View) {
			// This is a terminal transformation
			return ViewUtil.resolveSemanticElement((View) selected);
		}

		EObject elem = EMFHelper.getEObject(selected);
		if (elem != null) {
			return elem;
		}

		return selected;
	}

}
