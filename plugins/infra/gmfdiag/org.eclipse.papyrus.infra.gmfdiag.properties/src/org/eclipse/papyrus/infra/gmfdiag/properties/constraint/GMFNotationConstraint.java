/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.properties.constraint;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.constraints.constraints.EMFInstanceOfConstraint;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.NotationHelper;

/**
 * A constraint for the GMF Notation metamodel
 * Retrieves the notation model from the selection,
 * then applies an EMFInstanceOfConstraint on the resulting
 * EObject
 *
 * @author Camille Letavernier
 *
 */
public class GMFNotationConstraint extends EMFInstanceOfConstraint {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean match(Object selection) {
		View view = NotationHelper.findView(selection);

		return view == null ? false : super.match(view);
	}

}
