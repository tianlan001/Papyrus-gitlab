/*****************************************************************************
 * Copyright (c) 2010, 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.communication.custom.migration;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.InsertFloatingLabelFromMapCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.reconciler.DiagramReconciler;

/**
 * Communication Diagram Reconciler from 1.0.0 to 1.1.0
 *
 * @since 3.0
 */
public class CommunicationReconciler_1_1_0 extends DiagramReconciler {

	private static final String InteractionEditPart_VISUAL_ID = "8002";
	private static final String InteractionFloatingLabelEditPart_VISUAL_ID = "6013";
	private static final String LifelineEditPartCN_VISUAL_ID = "8001";
	private static final String LifelineFloatingLabelEditPartCN_VISUAL_ID = "6014";

	/**
	 * Gets the reconcile command.
	 *
	 * @param diagram
	 *            the diagram
	 * @return the reconcile command
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.reconciler.DiagramReconciler#getReconcileCommand(org.eclipse.gmf.runtime.notation.Diagram)
	 */
	@Override
	public ICommand getReconcileCommand(Diagram diagram) {
		return new InsertFloatingLabelFromMapCommand(diagram, getFloatingLabelMap());
	}

	/**
	 * Gets the floating label map to add.
	 *
	 * @return the floating label map
	 */
	private Map<String, String> getFloatingLabelMap() {
		Map<String, String> map = new HashMap<>();
		map.put(InteractionEditPart_VISUAL_ID, InteractionFloatingLabelEditPart_VISUAL_ID);
		map.put(LifelineEditPartCN_VISUAL_ID, LifelineFloatingLabelEditPartCN_VISUAL_ID);

		return map;
	}
}
