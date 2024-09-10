/*****************************************************************************
 * Copyright (c) 2011 Atos Origin.
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
 *   Atos Origin - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.providers;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.gmf.runtime.common.ui.services.action.contributionitem.IPopupMenuContributionPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityDiagramEditPart;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

/**
 * Reduce the scope of the Menu contribution of the Activity Diagram. implements {@link IPopupMenuContributionPolicy}
 *
 * @author adaussy
 *
 */
public class ActivityDiagramContributionPolicyClass implements IPopupMenuContributionPolicy {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean appliesTo(ISelection selection, IConfigurationElement configuration) {
		IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (editorPart instanceof IMultiDiagramEditor) {
			editorPart = ((IMultiDiagramEditor) editorPart).getActiveEditor();
			if (editorPart instanceof DiagramEditor) {
				DiagramEditPart host = ((DiagramEditor) editorPart).getDiagramEditPart();
				return ActivityDiagramEditPart.MODEL_ID.equals(host.getDiagramView().getType());
			}
		}
		return false;
	}
}
