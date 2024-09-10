/****************************************************************************
 * Copyright (c) 2008 Atos Origin.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *		Thibault Landre (Atos Origin) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.codegen.actions;

import org.eclipse.papyrus.codegen.PapyrusExecuteTemplatesOperation;
import org.eclipse.papyrus.gmf.internal.codegen.popup.actions.ExecuteTemplatesAction;
import org.eclipse.papyrus.gmf.internal.codegen.popup.actions.ExecuteTemplatesOperation;

/**
 * Define the Papyrus Generate Action
 *
 * @author tlandre
 */
public class PapyrusExecuteTemplateAction extends ExecuteTemplatesAction {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ExecuteTemplatesOperation createOperation() {
		return new PapyrusExecuteTemplatesOperation();
	}
}
