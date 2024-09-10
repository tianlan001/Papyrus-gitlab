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
 *   Atos - Initial API and implementation
 *   Arthur Daussy
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.figures;

import org.eclipse.draw2d.ImageFigure;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLDiagramEditorPlugin;

/**
 * External icon which look like a Z which represent external icon for Interruptible Edge or Exception Handler
 */
public class InterruptibleEdgeExternalIconFigure extends ImageFigure {

	public InterruptibleEdgeExternalIconFigure() {
		super(org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(UMLDiagramEditorPlugin.ID, "icons/InterruptibleEdgeIcon.png"), 0);
	}
}
