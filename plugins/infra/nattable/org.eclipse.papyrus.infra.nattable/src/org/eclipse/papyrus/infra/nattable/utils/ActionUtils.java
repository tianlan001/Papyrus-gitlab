/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.utils;

import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IdAxis;

/**
 * @since 6.7
 */
public final class ActionUtils {

	public static final String ACTION_AXIS_PREFIX = "action:/";


	public static final String getAction(final IAxis axis) {
		if (axis instanceof IdAxis) {
			final String action = ((IdAxis) axis).getElement();
			if (action.startsWith(ACTION_AXIS_PREFIX)) {
				return action.replace(ACTION_AXIS_PREFIX, "");
			}
		}
		return "";
	}

	public static final String getActionId(final Object axis) {
		if (axis instanceof IdAxis) {
			return getActionId((IdAxis) axis);
		}
		return "";
	}

	public static final String getActionId(final IAxis axis) {
		if (axis instanceof IdAxis) {
			final String action = ((IdAxis) axis).getElement();
			return action;
		}
		return "";
	}

	public static final boolean isAction(final Object object) {
		if (object instanceof IAxis) {
			return isAction((IAxis) object);
		}
		return false;
	}

	public static final boolean isAction(final IAxis axis) {
		if (axis instanceof IdAxis) {
			final String action = ((IdAxis) axis).getElement();
			return action.startsWith(ACTION_AXIS_PREFIX);
		}
		return false;
	}


}
