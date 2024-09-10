/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 	Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.disable.helper.advices.example.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.papyrus.uml.disable.helper.advices.example.utils.UnregisterHelperAdviceUtils;

/**
 * The handler to disable the automatic naming helper advice.
 */
public class DisableAutomaticNamingHandler extends AbstractHandler {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {

		final String clientContext = "org.eclipse.papyrus.infra.services.edit.TypeContext"; //$NON-NLS-1$
		final String elementTypeSetId = "org.eclipse.papyrus.uml.service.types.UMLElementTypeSet"; //$NON-NLS-1$
		final String adviceIdentifier = "org.eclipse.papyrus.uml.advice.NamedElementNameInitializer"; //$NON-NLS-1$

		UnregisterHelperAdviceUtils.unregisterHelperAdvice(clientContext, elementTypeSetId, adviceIdentifier);

		return null;
	}

}
