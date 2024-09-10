/*****************************************************************************
 * Copyright (c) 2009-2015 CEA LIST.
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
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.deployment.custom.migration;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.InsertFloatingLabelFromMapCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.reconciler.DiagramReconciler;

/**
 * Deployment Diagram Reconciler from 1.0.0 to 1.1.0
 *
 * @since 3.0
 */
public class DeploymentReconciler_1_1_0 extends DiagramReconciler {

	private final static String ArtifactEditPart_VISUAL_ID = "2006";
	private final static String ArtifactFloatingLabelEditPart_VISUAL_ID = "61";
	private final static String ArtifactEditPartACN_VISUAL_ID = "28";
	private final static String ArtifactFloatingLabelEditPartACN_VISUAL_ID = "60";
	private final static String ArtifactEditPartCN_VISUAL_ID = "25";
	private final static String ArtifactFloatingLabelEditPartCN_VISUAL_ID = "59";

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
		map.put(ArtifactEditPart_VISUAL_ID, ArtifactFloatingLabelEditPart_VISUAL_ID);
		map.put(ArtifactEditPartACN_VISUAL_ID, ArtifactFloatingLabelEditPartACN_VISUAL_ID);
		map.put(ArtifactEditPartCN_VISUAL_ID, ArtifactFloatingLabelEditPartCN_VISUAL_ID);

		return map;
	}
}
