/*****************************************************************************
 * Copyright (c) 2008 CEA LIST.
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
 *  Chokri Mraidha (CEA LIST) Chokri.Mraidha@cea.fr - Initial API and implementation
 *  Patrick Tessier (CEA LIST) Patrick.Tessier@cea.fr - modification
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.profile.constraints;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.papyrus.uml.profile.utils.ProfileConstraintUtils;
import org.eclipse.uml2.uml.Property;


public class PropertyHasNameConstraint extends AbstractModelConstraint {

	public PropertyHasNameConstraint() {
		super();
	}

	@Override
	public IStatus validate(IValidationContext ctx) {

		if (ProfileConstraintUtils.isStereotypeProperty(ctx.getTarget())) {
			Property property = (Property) ctx.getTarget();

			if (!property.isSetName()) {
				return ctx.createFailureStatus(ctx.getTarget());
			}

			if ("".equals(property.getName())) {
				return ctx.createFailureStatus(ctx.getTarget());
			}
		}

		return ctx.createSuccessStatus();
	}
}
