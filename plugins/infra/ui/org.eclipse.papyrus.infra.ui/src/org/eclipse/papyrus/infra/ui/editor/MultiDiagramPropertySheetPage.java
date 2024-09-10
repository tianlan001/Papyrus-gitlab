/*****************************************************************************
 * Copyright (c) 2014, 2015 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 469188
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.editor;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.infra.core.operation.DelegatingUndoContext;
import org.eclipse.papyrus.infra.core.sasheditor.editor.ISashWindowsContainer;
import org.eclipse.papyrus.infra.tools.util.PlatformHelper;
import org.eclipse.papyrus.infra.ui.editor.reload.EditorReloadEvent;
import org.eclipse.papyrus.infra.ui.editor.reload.IEditorReloadListener;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.operations.RedoActionHandler;
import org.eclipse.ui.operations.UndoActionHandler;
import org.eclipse.ui.part.IShowInSource;
import org.eclipse.ui.part.ShowInContext;
import org.eclipse.ui.views.properties.PropertyShowInContext;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;


/**
 * A specialized property-sheet page that knows how to restore the Property Sheet view's input from the workbench part
 * that most recently gave it its input, after a {@link CoreMultiDiagramEditor} has been re-loaded.
 */
class MultiDiagramPropertySheetPage extends TabbedPropertySheetPage implements IEditorReloadListener {

	private final CoreMultiDiagramEditor multiDiagramEditor;

	private UndoActionHandler undo;
	private RedoActionHandler redo;
	private DelegatingUndoContext undoContext;

	public MultiDiagramPropertySheetPage(CoreMultiDiagramEditor editor) {
		super(editor);

		this.multiDiagramEditor = editor;
		IReloadableEditor.Adapter.getAdapter(editor).addEditorReloadListener(this);
	}

	@Override
	public void dispose() {
		IReloadableEditor.Adapter.getAdapter(multiDiagramEditor).removeEditorReloadListener(this);

		if (undo != null) {
			undo.dispose();
		}
		if (redo != null) {
			redo.dispose();
		}

		super.dispose();
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (selection.isEmpty() && (part instanceof IMultiDiagramEditor)) {
			// Perhaps the user selected a page such as the Welcome Page that
			// isn't an editor and so doesn't have a selection
			IMultiDiagramEditor editor = (IMultiDiagramEditor) part;
			ISashWindowsContainer sash = editor.getAdapter(ISashWindowsContainer.class);
			if (sash != null) {
				if ((sash.getActiveEditor() == null) && (sash.getActiveSashWindowsPage() != null)) {
					// Yep, that's the case, here. So show the properties of the input
					// resource (usually a DI file)
					IEditorInput input = editor.getEditorInput();
					Object newSelection;
					if (input instanceof IFileEditorInput) {
						newSelection = ((IFileEditorInput) input).getFile();
					} else if (input instanceof IStorageEditorInput) {
						IStorageEditorInput storageInput = (IStorageEditorInput) input;
						try {
							newSelection = storageInput.getStorage();
						} catch (CoreException e) {
							newSelection = storageInput;
						}
					} else if (input instanceof IURIEditorInput) {
						newSelection = ((IURIEditorInput) input).getURI();
					} else if (input instanceof URIEditorInput) {
						newSelection = ((URIEditorInput) input).getURI();
					} else {
						newSelection = null;
					}

					if (newSelection != null) {
						selection = new StructuredSelection(newSelection);
					}
				}
			}
		}

		super.selectionChanged(part, selection);
	}

	@Override
	public void editorAboutToReload(EditorReloadEvent event) {
		Object propertySheet = getSite().getService(IViewPart.class);
		if (propertySheet instanceof IShowInSource) {
			ShowInContext context = ((IShowInSource) propertySheet).getShowInContext();

			if (context instanceof PropertyShowInContext) {
				IWorkbenchPart inputPart = ((PropertyShowInContext) context).getPart();
				if (inputPart != null) {
					event.putContext(inputPart);
				}
			}
		}
	}

	@Override
	public void editorReloaded(EditorReloadEvent event) {
		final IWorkbenchPart inputPart = (IWorkbenchPart) event.getContext();
		if (inputPart != null) {
			final Object propertySheet = getSite().getService(IViewPart.class);
			if (propertySheet instanceof IPartListener) {
				// Kick it with this part
				((IPartListener) propertySheet).partActivated(inputPart);

				// And again later to get its new selection (we don't know when its selection may be restored relative to us)
				getSite().getShell().getDisplay().asyncExec(new Runnable() {

					@Override
					public void run() {
						ISelectionProvider selectionProvider = inputPart.getSite().getSelectionProvider();
						if (selectionProvider != null) {
							((ISelectionListener) propertySheet).selectionChanged(inputPart, selectionProvider.getSelection());
						}
					}
				});
			}
		}

		// The editor will have a new undo context (because it will have a new editing domain)
		if (undoContext != null) {
			undoContext.setDelegate(PlatformHelper.getAdapter(multiDiagramEditor, IUndoContext.class));
		}
	}

	@Override
	public void setActionBars(IActionBars actionBars) {
		super.setActionBars(actionBars);

		undoContext = new DelegatingUndoContext();
		undoContext.setDelegate(PlatformHelper.getAdapter(multiDiagramEditor, IUndoContext.class));

		undo = new UndoActionHandler(multiDiagramEditor.getSite(), undoContext);
		redo = new RedoActionHandler(multiDiagramEditor.getSite(), undoContext);

		actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(), undo);
		actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(), redo);
	}


}
