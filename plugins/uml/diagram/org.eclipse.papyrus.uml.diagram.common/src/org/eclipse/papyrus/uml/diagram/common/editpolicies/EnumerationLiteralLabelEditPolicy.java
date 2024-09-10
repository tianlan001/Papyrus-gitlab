/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Bug 472034
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.editpolicies;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.papyrus.uml.diagram.common.helper.EnumerationLiteralLabelHelper;

/**
 * @author Céline JANSSENS
 *
 */
public class EnumerationLiteralLabelEditPolicy extends AbstractMaskManagedEditPolicy {

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.IMaskManagedLabelEditPolicy#getMasks()
	 *
	 * @return
	 */
	@Override
	public Map<String, String> getMasks() {
		return Collections.emptyMap();
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editpolicies.AbstractMaskManagedEditPolicy#refreshDisplay()
	 *
	 */
	@Override
	public void refreshDisplay() {
		EnumerationLiteralLabelHelper.getInstance().refreshEditPartDisplay((GraphicalEditPart) getHost());
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editpolicies.AbstractMaskManagedEditPolicy#getDefaultDisplayValue()
	 *
	 * @return
	 */
	@Override
	protected Collection<String> getDefaultDisplayValue() {
		return Collections.emptyList();
	}

}
