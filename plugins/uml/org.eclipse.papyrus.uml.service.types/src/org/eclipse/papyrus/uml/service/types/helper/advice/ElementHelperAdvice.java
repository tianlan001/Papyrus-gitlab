/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *		Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 355731
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper.advice;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;

/**
 * <pre>
 * This HelperAdvice prohibits the move command of elements into a proxy element.
 * </pre>
 * @since 3.0
 */
public class ElementHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * <pre>
	 * {@inheritDoc}
	 *
	 * Prohibit the move command of elements into a proxy element such as an unloaded submodel package.
	 * Without doing this, a move command following by a save command could cause the lost of information.
	 * The moved elements do not exist neither in the main model nor in the submodel.
	 * </pre>
	 */
	@Override
	protected ICommand getBeforeMoveCommand(final MoveRequest request) {

		return request.getTargetContainer().eIsProxy() ? UnexecutableCommand.INSTANCE : null;
	}
}
