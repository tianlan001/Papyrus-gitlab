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

package org.eclipse.papyrus.infra.ui.internal.commands;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.commands.ToggleState;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.sasheditor.SashModel;
import org.eclipse.papyrus.infra.core.resource.sasheditor.SashModelUtils;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * The boolean toggle state of the private page layout storage menu item.
 */
public class PageLayoutStorageState extends ToggleState implements IPartListener, PropertyChangeListener {

	private IPartService partService = null;

	private Reference<IMultiDiagramEditor> activeEditor;

	public PageLayoutStorageState() {
		super();

		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

		if (window != null) {
			partService = window.getPartService();
			if (partService != null) {
				partService.addPartListener(this);
				update(partService.getActivePart());
			}
		}
	}

	@Override
	public void dispose() {
		if (partService != null) {
			partService.removePartListener(this);
		}

		super.dispose();
	}

	@Override
	public void partDeactivated(IWorkbenchPart part) {
		if ((activeEditor != null) && (activeEditor.get() == part)) {
			update(null);
		}
	}

	@Override
	public void partActivated(IWorkbenchPart part) {
		update(part);
	}

	private void update(IWorkbenchPart part) {
		// Default state is private storage
		boolean state = true;

		unhookSashModelListener();

		activeEditor = null;

		if (part instanceof IMultiDiagramEditor) {
			IMultiDiagramEditor editor = (IMultiDiagramEditor) part;
			activeEditor = new WeakReference<>(editor);
			state = isPrivateLayout(editor);
		}

		hookSashModelListener();

		// Fires notification if changed from previous state
		setValue(state);
	}

	// I am a computed value, actually
	@Override
	public Object getValue() {
		IMultiDiagramEditor editor = (activeEditor == null) ? null : activeEditor.get();
		return (editor != null) ? isPrivateLayout(editor) : super.getValue();
	}

	boolean isPrivateLayout(IMultiDiagramEditor editor) {
		ModelSet modelSet = (ModelSet) editor.getAdapter(EditingDomain.class).getResourceSet();
		SashModel sashModel = SashModelUtils.getSashModel(modelSet);

		// The default is private layout
		return (sashModel == null) || !sashModel.isLegacyMode();
	}

	@Override
	public void partBroughtToTop(IWorkbenchPart part) {
		// Pass
	}

	@Override
	public void partClosed(IWorkbenchPart part) {
		// Pass
	}

	@Override
	public void partOpened(IWorkbenchPart part) {
		// Pass
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getSource() instanceof SashModel) {
			switch (evt.getPropertyName()) {
			case SashModel.PROPERTY_LEGACY_MODE:
				setValue(!(Boolean) evt.getNewValue());
				break;
			}
		}
	}

	private SashModel getSashModel() {
		SashModel result = null;

		if (activeEditor != null) {
			IMultiDiagramEditor editor = activeEditor.get();
			if (editor != null) {
				result = SashModelUtils.getSashModel(editor.getServicesRegistry());
			}
		}

		return result;
	}

	private void unhookSashModelListener() {
		SashModel sash = getSashModel();
		if (sash != null) {
			sash.removePropertyChangeListener(SashModel.PROPERTY_LEGACY_MODE, this);
		}
	}

	private void hookSashModelListener() {
		SashModel sash = getSashModel();
		if (sash != null) {
			sash.addPropertyChangeListener(SashModel.PROPERTY_LEGACY_MODE, this);
		}
	}
}
