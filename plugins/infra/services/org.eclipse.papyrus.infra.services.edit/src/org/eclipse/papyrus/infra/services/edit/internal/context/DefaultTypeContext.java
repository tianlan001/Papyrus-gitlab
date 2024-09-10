/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 * 		Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.edit.internal.context;

import org.eclipse.gmf.runtime.emf.type.core.ClientContextManager;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.edit.messages.Messages;

/**
 * <pre>
 * A wrapper for the default {@link IClientContext} for Papyrus
 *
 * This class stores this context ID and provides a convenient API to
 * get the default {@link IClientContext} from the {@link ClientContextManager}.
 * </pre>
 */
public class DefaultTypeContext {

	/** The ID of Papyrus {@link IClientContext} (declared in plugin.xml, shall not be modified) */
	public static final String ID = "org.eclipse.papyrus.infra.services.edit.TypeContext"; //$NON-NLS-1$

	/**
	 * Retrieve Papyrus {@link IClientContext} from its ID.
	 *
	 * @return the tool context
	 * @throws Exception
	 *             if the context is not found
	 */
	public static IClientContext getContext() throws ServiceException {

		IClientContext context = ClientContextManager.getInstance().getClientContext(ID);
		if (context == null) {
			throw new ServiceException(NLS.bind(Messages.TypeContext_ContextNotFound, ID));
		}

		return context;
	}

}
