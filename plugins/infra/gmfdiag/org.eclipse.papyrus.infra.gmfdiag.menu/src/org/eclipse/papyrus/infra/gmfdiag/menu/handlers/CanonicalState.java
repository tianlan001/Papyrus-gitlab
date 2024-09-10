/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.menu.handlers;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.jface.commands.ToggleState;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.core.utils.AdapterUtils;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * The canonical state of a command's selection.
 */
public class CanonicalState extends ToggleState implements ISelectionListener {

	private ISelectionService selectionService = null;

	private Reference<IGraphicalEditPart> selectedEditPart;

	public CanonicalState() {
		super();

		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

		if (window != null) {
			selectionService = window.getSelectionService();
			if (selectionService != null) {
				selectionService.addSelectionListener(this);
				update(selectionService.getSelection());
			}
		}
	}

	@Override
	public void dispose() {
		if (selectionService != null) {
			selectionService.removeSelectionListener(this);
		}

		super.dispose();
	}

	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		update(selection);
	}

	private void update(ISelection selection) {
		// Default state is not canonical
		boolean state = false;

		selectedEditPart = null;

		if (selection instanceof IStructuredSelection) {
			IGraphicalEditPart editPart = AdapterUtils.adapt(((IStructuredSelection) selection).getFirstElement(), IGraphicalEditPart.class, null);
			if (editPart != null) {
				selectedEditPart = new WeakReference<IGraphicalEditPart>(editPart);
				state = DiagramEditPartsUtil.isCanonical(editPart);
			}
		}

		// Fires notification if changed from previous state
		setValue(state);
	}

	// I am a computed value, actually
	@Override
	public Object getValue() {
		IGraphicalEditPart editPart = (selectedEditPart == null) ? null : selectedEditPart.get();
		return (editPart != null) ? DiagramEditPartsUtil.isCanonical(editPart) : super.getValue();
	}
}
