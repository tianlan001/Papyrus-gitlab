/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
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
 * 
 * 		Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.edit.tests.edit.helper;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelper;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.papyrus.infra.services.edit.commands.IConfigureCommandFactory;

public class EPackageEditHelper extends AbstractEditHelper {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getConfigureCommand(ConfigureRequest req) {
		if (req.getParameter(IConfigureCommandFactory.CONFIGURE_COMMAND_FACTORY_ID) != null) {
			IConfigureCommandFactory factory = (IConfigureCommandFactory) req.getParameter(IConfigureCommandFactory.CONFIGURE_COMMAND_FACTORY_ID);
			return factory.create(req);
		}
		return super.getConfigureCommand(req);
	}
}
