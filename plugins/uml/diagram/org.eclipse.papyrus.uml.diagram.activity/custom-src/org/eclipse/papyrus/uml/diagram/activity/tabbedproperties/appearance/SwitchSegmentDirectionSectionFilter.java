/*****************************************************************************
 * Copyright (c) 2010 Atos Origin.
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
 *   Atos Origin - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.tabbedproperties.appearance;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ForkNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.JoinNodeEditPart;

/**
 * Filter for the {@link SwitchSegmentDirectionSection} section
 */
public class SwitchSegmentDirectionSectionFilter implements IFilter {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean select(Object object) {
		return object instanceof ForkNodeEditPart || object instanceof JoinNodeEditPart;
	}
}
