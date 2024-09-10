/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *  Camille Letavernier (camille.letavernier@cea.fr) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.validator;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrus.infra.widgets.Activator;

/**
 * An abstract implementation of the IValidator interface
 *
 * @author Camille Letavernier
 *
 */
public abstract class AbstractValidator implements IValidator {

	protected String pluginId;

	public void setPluginID(String pluginId) {
		this.pluginId = pluginId;
	}

	protected IStatus error(String message) {
		return new Status(IStatus.ERROR, getPluginId(), message);
	}

	public String getPluginId() {
		if (pluginId == null) {
			return Activator.PLUGIN_ID;
		} else {
			return pluginId;
		}
	}


}
