/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.services.controlmode.internal.handler;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;

import org.eclipse.core.commands.State;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.emf.resource.ShardResourceHelper;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeManager;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * The current "sub-model" toggle state of an element.
 * 
 * @see ToggleSubmodelHandler
 */
public class SubmodelState extends State implements ISelectionListener {

	private ISelectionService selectionService = null;

	private Reference<EObject> selectedElement;

	/**
	 * Initializes me.
	 */
	public SubmodelState() {
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

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		update(selection);
	}

	private void update(ISelection selection) {
		// Default state is not canonical
		boolean state = false;

		selectedElement = null;

		if (selection instanceof IStructuredSelection) {
			EObject element = ((List<?>) ((IStructuredSelection) selection).toList()).stream()
					.map(EMFHelper::getEObject)
					.filter(Objects::nonNull)
					.findFirst().orElse(null);
			if (element != null) {
				selectedElement = new WeakReference<>(element);
				state = ControlModeManager.getInstance().canCreateSubmodel(element);
			}
		}

		// Fires notification if changed from previous state
		setValue(state);
	}

	// I am a computed value, actually
	@Override
	public Object getValue() {
		boolean result = false;

		EObject element = (selectedElement == null) ? null : selectedElement.get();
		if (element != null) {
			result = isIndependentSubmodel(element);
		}

		return result;
	}

	static boolean isIndependentSubmodel(EObject object) {
		boolean result = false;

		try (ShardResourceHelper helper = new ShardResourceHelper(object)) {
			result = !helper.isShard();
		}

		return result;
	}
}
