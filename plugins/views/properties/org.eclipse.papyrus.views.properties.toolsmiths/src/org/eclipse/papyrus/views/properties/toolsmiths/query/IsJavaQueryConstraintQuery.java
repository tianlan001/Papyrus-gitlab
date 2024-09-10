/*****************************************************************************
 * Copyright (c) 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.views.properties.toolsmiths.query;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.constraints.SimpleConstraint;
import org.eclipse.papyrus.infra.constraints.constraints.JavaQuery;
import org.eclipse.papyrus.infra.constraints.constraints.JavaQueryConstraint;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;

/**
 * <p>
 * A {@link JavaQuery} to test if a {@link SimpleConstraint} descriptor
 * represents a {@link JavaQueryConstraint}.
 * </p>
 */
public class IsJavaQueryConstraintQuery implements JavaQuery {

	@Override
	public boolean match(Object selection) {
		EObject selectedEObject = EMFHelper.getEObject(selection);
		if (!(selectedEObject instanceof SimpleConstraint)) {
			return false;
		}
		SimpleConstraint source = (SimpleConstraint) selectedEObject;
		return QueryUtil.matches(source, JavaQueryConstraint.class);
	}

}
