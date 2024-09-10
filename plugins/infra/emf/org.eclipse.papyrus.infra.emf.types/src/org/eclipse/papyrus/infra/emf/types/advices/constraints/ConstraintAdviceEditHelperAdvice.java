/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.types.advices.constraints;

import java.util.Iterator;

import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint;
import org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdviceConfiguration;
import org.eclipse.papyrus.infra.types.core.AbstractConfiguredEditHelperAdvice;

/**
 * Implementation of the modeled {@link ConstraintAdviceConfiguration}.
 */
public class ConstraintAdviceEditHelperAdvice extends AbstractConfiguredEditHelperAdvice<ConstraintAdviceConfiguration> {

	public ConstraintAdviceEditHelperAdvice() {
		super();
	}

	@Override
	public boolean approveRequest(IEditCommandRequest request) {
		PermissionResult result = PermissionResult.NONE;

		for (Iterator<AdviceConstraint> iter = getConfiguration().getConstraints().iterator(); iter.hasNext() && !result.isDetermined();) {
			if (!iter.next().approveRequest(request)) {
				result = PermissionResult.DENIED;
			}
		}

		return !result.isDenied();
	}

}
