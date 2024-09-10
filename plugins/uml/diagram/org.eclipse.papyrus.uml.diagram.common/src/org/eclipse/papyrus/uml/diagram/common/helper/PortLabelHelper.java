/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.helper;

import java.util.Collection;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.tools.utils.ICustomAppearance;
import org.eclipse.papyrus.uml.tools.utils.PortUtil;
import org.eclipse.uml2.uml.Port;


public class PortLabelHelper extends PropertyLabelHelper {

	// Einstance
	private static PortLabelHelper labelHelper;

	public static PortLabelHelper getInstance() {
		if (labelHelper == null) {
			labelHelper = new PortLabelHelper();
		}
		return labelHelper;
	}

	protected PortLabelHelper() {
		super();
		masks.put(ICustomAppearance.DISP_CONJUGATED, "Conjugated");
	}

	@Override
	protected String parseString(GraphicalEditPart editPart, Collection<String> maskValues) {
		Port port = getUMLElement(editPart);

		if (port != null) {
			return PortUtil.getCustomLabel(port, maskValues);
		}

		return "";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Port getUMLElement(GraphicalEditPart editPart) {
		if (editPart.getModel() instanceof View) {
			View view = (View) editPart.getModel();
			if (view.getElement() instanceof Port) {
				return (Port) view.getElement();
			}
		}
		return null;
	}

}
