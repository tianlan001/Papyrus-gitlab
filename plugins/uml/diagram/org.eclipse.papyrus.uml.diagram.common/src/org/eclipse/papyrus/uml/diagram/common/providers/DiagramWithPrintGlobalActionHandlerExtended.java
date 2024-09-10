/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 		Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.providers;

import org.eclipse.gef.RootEditPart;
import org.eclipse.gmf.runtime.common.ui.services.action.global.IGlobalActionContext;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramRootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IDiagramPreferenceSupport;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.diagram.ui.printing.internal.util.SWTDiagramPrinter;
import org.eclipse.gmf.runtime.diagram.ui.printing.providers.DiagramWithPrintGlobalActionHandler;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Class that implements the <code>IGlobalActionHandler</code>
 * interface and provides a command for <code>GlobalActionId.PRINT</code>.
 *
 * @since 3.0
 */
public class DiagramWithPrintGlobalActionHandlerExtended extends DiagramWithPrintGlobalActionHandler {

	/**
	 * Constructor.
	 *
	 */
	public DiagramWithPrintGlobalActionHandlerExtended() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPrint(final IGlobalActionContext cntxt) {
		CustomPrintActionHelper.doRun((IEditorPart) cntxt.getActivePart(),
				new SWTDiagramPrinter(getPreferencesHint((IEditorPart) cntxt.getActivePart()), getMapMode(cntxt)));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected IMapMode getMapMode(final IGlobalActionContext globalActionContext) {
		IWorkbenchPart part = globalActionContext.getActivePart();
		IMapMode mapMode = MapModeUtil.getMapMode();
		if (part instanceof IDiagramWorkbenchPart) {
			RootEditPart rootEditPart = ((IDiagramWorkbenchPart) part).getDiagramGraphicalViewer().getRootEditPart();
			if (rootEditPart instanceof DiagramRootEditPart) {
				mapMode = ((DiagramRootEditPart) part).getMapMode();
			}
		}

		return mapMode;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected PreferencesHint getPreferencesHint(final IEditorPart editorPart) {
		PreferencesHint preferencesHint = checkDiagramWorkenchPart(editorPart);
		if (null == preferencesHint) {
			if (editorPart instanceof IMultiDiagramEditor) {
				IEditorPart activeEditor = ((IMultiDiagramEditor) editorPart).getActiveEditor();
				preferencesHint = checkDiagramWorkenchPart(activeEditor);
			}
		}
		if (null == preferencesHint) {
			preferencesHint = PreferencesHint.USE_DEFAULTS;
		}
		return preferencesHint;
	}

	/**
	 * Get PreferencesInt of a IDiagramPreferenceSupport.
	 *
	 * @param editorPart
	 *            The IEditorPart.
	 * @return The PreferencesHint.
	 */
	private PreferencesHint checkDiagramWorkenchPart(final IEditorPart editorPart) {
		PreferencesHint preferencesHint = null;
		if (editorPart instanceof IDiagramWorkbenchPart) {
			RootEditPart rootEditPart = ((IDiagramWorkbenchPart) editorPart).getDiagramGraphicalViewer().getRootEditPart();
			if (rootEditPart instanceof IDiagramPreferenceSupport) {
				preferencesHint = ((IDiagramPreferenceSupport) rootEditPart).getPreferencesHint();
			}
		}

		return preferencesHint;
	}

}
