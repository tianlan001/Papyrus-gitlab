/*****************************************************************************
 * Copyright (c) 2022 CEA LIST
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.properties.services.internal;

import org.eclipse.ui.IStartup;

/**
 * 
 * Used to force the register of the EEF Properties by the Activator
 */
public class Startup implements IStartup {

	@Override
	public void earlyStartup() {
		// nothing to do
	}

}
