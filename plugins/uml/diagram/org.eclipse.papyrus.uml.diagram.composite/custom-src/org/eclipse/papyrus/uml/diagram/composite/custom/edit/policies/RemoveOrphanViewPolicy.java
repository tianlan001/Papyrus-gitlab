/*****************************************************************************
 * Copyright (c) 2009-2017 CEA LIST.
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
 *  Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *  Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 528334
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.composite.custom.edit.policies;

import org.eclipse.gmf.runtime.notation.BasicCompartment;
import org.eclipse.gmf.runtime.notation.DecorationNode;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.OrphanViewPolicy;

/**
 * <pre>
 * This policy is used to suppress orphan node view in GMF view.
 * The policy to remove orphan connection is more complex.
 *
 * See RemoveOrphanViewPolicy in Class Diagram
 * </pre>
 * 
 * @deprecated since 3.1. Useless. Helper Advices remove views.
 */
public class RemoveOrphanViewPolicy extends OrphanViewPolicy {

	/**
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.editpolicies.OrphanViewPolicy#isOrphaned(org.eclipse.gmf.runtime.notation.View)
	 *
	 * @param view
	 * @return true is the view is Orphaned
	 */
	@Override
	protected boolean isOrphaned(View view) {
		// Always treat Compartment and Decoration as not orphaned nodes
		if ((view instanceof BasicCompartment) || (view instanceof DecorationNode)) {
			return false;
		}
		return !view.isSetElement();
	}
}
