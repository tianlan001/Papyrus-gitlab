/*
 * Copyright (c) 2014 CEA and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus (CEA) - Initial API and implementation
 *
 */
package org.eclipse.papyrus.uml.modelrepair.internal.stereotypes;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrus.uml.modelrepair.validation.IProfileSwitchPrecondition;
import org.eclipse.papyrus.uml.modelrepair.validation.ProfileSwitchContext;


/**
 * This is the StereotypeApplicationPrecondition type. Enjoy.
 */
public class StereotypeApplicationPrecondition implements IProfileSwitchPrecondition {

	public StereotypeApplicationPrecondition() {
		super();
	}

	public IStatus validateProfileSwitch(ProfileSwitchContext ctx) {
		IStatus result = Status.OK_STATUS;

		StereotypeApplicationRepairSnippet snippet = StereotypeApplicationRepairSnippet.getInstance(ctx.getModelSet());
		if (snippet != null) {
			result = snippet.repair(ctx.getModelSet());
		}

		return result;
	}

}
