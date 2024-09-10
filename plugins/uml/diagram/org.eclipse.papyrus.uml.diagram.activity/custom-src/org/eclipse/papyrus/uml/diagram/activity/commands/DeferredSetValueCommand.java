/*****************************************************************************
 * Copyright (c) 2011 Atos.
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
 *   Arthur Daussy (Atos) - Initial API and implementation
 *   Arthur Daussy - 371712 : 372745: [ActivityDiagram] Major refactoring group framework
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.commands;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.diagram.activity.activitygroup.request.SetDeferredRequest;

public class DeferredSetValueCommand extends org.eclipse.gmf.runtime.emf.type.core.commands.DeferredSetValueCommand {

	protected SetDeferredRequest request;

	protected EObject elementToEdit;

	public DeferredSetValueCommand(SetDeferredRequest request) {
		super(request);
		this.request = request;
	}

	@Override
	protected EObject getElementToEdit() {
		if (elementToEdit == null) {
			Object adapter = request.getElementToSet().getAdapter(EObject.class);
			if (adapter instanceof EObject) {
				elementToEdit = (EObject) adapter;
			}
		}
		return elementToEdit;
	}
}
