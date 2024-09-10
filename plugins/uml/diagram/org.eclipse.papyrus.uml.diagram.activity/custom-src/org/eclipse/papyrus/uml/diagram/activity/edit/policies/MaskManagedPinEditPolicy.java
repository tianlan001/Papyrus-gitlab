/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) remi.schnekenburger@cea.fr - Initial API and implementation
 *  Nizar GUEDIDI (CEA LIST) - Update getUMLElement()
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.edit.policies;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.papyrus.uml.diagram.activity.helper.PinLabelHelper;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AbstractMaskManagedEditPolicy;
import org.eclipse.papyrus.uml.tools.utils.ICustomAppearance;
import org.eclipse.uml2.uml.Pin;

public class MaskManagedPinEditPolicy extends AbstractMaskManagedEditPolicy {

	public MaskManagedPinEditPolicy() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<String> getDefaultDisplayValue() {
		return Arrays.asList(ICustomAppearance.DISP_NAME);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> getMasks() {
		return PinLabelHelper.getInstance().getMasks();
	}

	/**
	 * {@inheritedDoc}
	 */
	@Override
	public Pin getUMLElement() {
		return PinLabelHelper.getInstance().getUMLElement((GraphicalEditPart) getHost());
	}

	/**
	 * {@inheritedDoc}
	 */
	@Override
	public void refreshDisplay() {
		PinLabelHelper.getInstance().refreshEditPartDisplay((GraphicalEditPart) getHost());
	}
}
