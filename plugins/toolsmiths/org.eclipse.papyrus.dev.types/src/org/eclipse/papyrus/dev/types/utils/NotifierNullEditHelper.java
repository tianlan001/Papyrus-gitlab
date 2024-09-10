/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.dev.types.utils;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.types.core.notification.AbstractNotifierEditHelper;

/**
 * Edit helper for the NullElementType that considers before and after
 * advice, only.
 */
public class NotifierNullEditHelper
		extends AbstractNotifierEditHelper {

	protected ICommand getInsteadCommand(IEditCommandRequest req) {
		return null;
	}
}