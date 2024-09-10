/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Pauline DEVILLE (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.canonical;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.canonical.DefaultUMLVisualChildrenStrategy;

/**
 * This Strategy managed the constraint display (Bug 381704)
 *
 * @since 3.4
 */
public class ConstraintVisualChildrenStrategy extends DefaultUMLVisualChildrenStrategy {

	/**
	 * Default constructor.
	 */
	public ConstraintVisualChildrenStrategy() {
		super();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.canonical.DefaultUMLVisualChildrenStrategy#getCanonicalChildren(org.eclipse.gef.EditPart, org.eclipse.gmf.runtime.notation.View)
	 */
	@Override
	public List<? extends View> getCanonicalChildren(final EditPart editPart, final View view) {
		List<? extends View> result = super.getCanonicalChildren(editPart, view);
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.canonical.DefaultUMLVisualChildrenStrategy#getCanonicalEdges(org.eclipse.gef.EditPart, org.eclipse.gmf.runtime.notation.View)
	 */
	@Override
	public List<? extends View> getCanonicalEdges(final EditPart editPart, final View view) {
		List<? extends View> result = super.getCanonicalEdges(editPart, view);
		result.removeIf(v -> v.getType().equals("Action_LocalPreconditionEdge")); //$NON-NLS-1$
		result.removeIf(v -> v.getType().equals("Action_LocalPostconditionEdge")); //$NON-NLS-1$
		return result;
	}

}
