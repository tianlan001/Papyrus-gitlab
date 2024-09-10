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

import java.util.ArrayList;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDescriptionPreferences;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.sasheditor.SashModelUtils;
import org.eclipse.papyrus.infra.core.sasheditor.editor.ISashWindowsContainer;
import org.eclipse.papyrus.infra.core.sashwindows.di.PageRef;
import org.eclipse.papyrus.infra.core.sashwindows.di.SashModel;
import org.eclipse.papyrus.infra.core.sashwindows.di.SashWindowsMngr;
import org.eclipse.papyrus.infra.core.sashwindows.di.TabFolder;
import org.eclipse.papyrus.infra.core.sashwindows.di.util.DiUtils;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;

/**
 * A factory for commands that manipulate the configuration of the sash editor layout.
 */
public class SashLayoutCommandFactory {
	private final IMultiDiagramEditor editor;

	public SashLayoutCommandFactory(IMultiDiagramEditor editor) {
		super();

		this.editor = editor;
	}

	/**
	 * Creates a command that toggles whether the sash model is stored in the private
	 * workspace metadata area or in the shared {@code *.di} file.
	 * 
	 * @return a toggle command for the private layout storage
	 */
	public Command createTogglePrivateLayoutCommand() {
		Command result = UnexecutableCommand.INSTANCE;

		ModelSet modelSet = (ModelSet) editor.getAdapter(EditingDomain.class).getResourceSet();
		org.eclipse.papyrus.infra.core.resource.sasheditor.SashModel sashModel = SashModelUtils.getSashModel(modelSet);
		if (sashModel != null) {
			result = new AbstractToggleCommand("Toggle Private Editor Layout") {
				private Command toggleRestoreActivePage;

				{
					// If we are toggling off private mode, make sure that we stop
					// tracking the active page selection if we were tracking it.
					// And remember that for undo
					if (!sashModel.isLegacyMode()) {
						SashWindowsMngr sash = DiUtils.lookupSashWindowsMngr(sashModel.getResource());
						if ((sash != null) && (sash.getSashModel() != null) && sash.getSashModel().isRestoreActivePage()) {
							toggleRestoreActivePage = createToggleRestoreActivePageCommand();
						}
					}
				}

				@Override
				public void execute() {
					// First, if we need to toggle restoring the active page, do that
					if ((toggleRestoreActivePage != null) && toggleRestoreActivePage.canExecute()) {
						toggleRestoreActivePage.execute();
					}

					SashWindowsMngr toMove = DiUtils.lookupSashWindowsMngr(sashModel.getResource());

					// We don't record changes in the sash model for undo/redo,
					// so we cannot assume that any changes to the current page selections
					// are undoable in the usual way
					if (!sashModel.isLegacyMode()) {
						Resource sashResource = toMove.eResource();
						URI sharedURI = sashModel.getSharedResourceURI();

						// Move the contents into the DI model. If the DI resource isn't loaded,
						// give up because something is seriously wrong in that case
						Resource diResource = modelSet.getResource(sharedURI, false);
						if ((diResource != null) && diResource.isLoaded()) {
							moveContents(sashResource, diResource);

							if (sashResource.getContents().isEmpty()) {
								// Schedule deletion on save
								modelSet.getResourcesToDeleteOnSave().add(sashResource.getURI());
							}
						}
					} else {
						Resource sashResource;
						URI privateURI = sashModel.getPrivateResourceURI();

						// Move the contents into the sash model. If the sash resource isn't loaded
						// or doesn't exist, it will have to be handled
						if (modelSet.getURIConverter().exists(privateURI, null)) {
							sashResource = modelSet.getResource(privateURI, true);
						} else {
							sashResource = modelSet.createResource(privateURI);
						}

						// In case we had marked it for deletion, earlier
						modelSet.getResourcesToDeleteOnSave().remove(privateURI);

						Resource diResource = toMove.eResource();
						moveContents(diResource, sashResource);
					}

					// Re-load from the new resource. Snippets might find this odd, but
					// it would be even more odd for there to be any snippets on this model
					sashModel.loadModel(modelSet.getURIWithoutExtension());
				}
			};
		}

		return result;
	}

	void moveContents(Resource fromResource, Resource toResource) {
		// Safe copy to allow concurrent modifications
		for (EObject root : new ArrayList<>(fromResource.getContents())) {
			if (root instanceof SashWindowsMngr || root instanceof ArchitectureDescriptionPreferences) {
				EObject toReplace = (EObject) EcoreUtil.getObjectByType(toResource.getContents(), root.eClass());
				if (toReplace != null) {
					EcoreUtil.replace(toReplace, root);
				} else {
					// This one is expected always to be first
					if (root instanceof SashWindowsMngr)
						toResource.getContents().add(0, root);
					else
						toResource.getContents().add(root);
				}
			}
		}

	}

	/**
	 * Creates a command that toggles whether the sash model records the currently
	 * active page to restore it on next opening.
	 * 
	 * @return a toggle command for the restore-active-page behaviour
	 */
	public Command createToggleRestoreActivePageCommand() {
		Command result = UnexecutableCommand.INSTANCE;

		ModelSet modelSet = (ModelSet) editor.getAdapter(EditingDomain.class).getResourceSet();
		SashWindowsMngr sashWindows = SashModelUtils.getSashWindowsMngr(modelSet);
		ISashWindowsContainer container = editor.getAdapter(ISashWindowsContainer.class);

		SashModel sashModel = sashWindows.getSashModel();
		if (sashModel != null) {
			// We don't record the tracking of the active page for undo/redo,
			// so we cannot assume that any changes to the current page selections
			// are undoable in the usual way
			result = new AbstractToggleCommand("Toggle Restore Active Page") {

				@Override
				public void execute() {
					boolean oldValue = sashModel.isRestoreActivePage();

					if (oldValue) {
						// Clear each tab folder's selection
						container.getIFolderList().stream()
								.map(f -> f.getRawModel())
								.filter(TabFolder.class::isInstance).map(TabFolder.class::cast)
								.filter(f -> f.getCurrentSelection() != null)
								.forEach(f -> f.setCurrentSelection(null));
					} else {
						// Set each tab folder's selection.
						// The 'visible pages' are the current selection in each folder
						container.getVisiblePages().stream()
								.map(p -> p.getRawModel())
								.filter(PageRef.class::isInstance).map(PageRef.class::cast)
								.filter(p -> p.getParent().getCurrentSelection() != p)
								.forEach(p -> p.getParent().setCurrentSelection(p));
					}

					// The basic toggle
					sashModel.setRestoreActivePage(!oldValue);
				}
			};
		}

		return result;
	}

	//
	// Nested types
	//

	private static abstract class AbstractToggleCommand extends AbstractCommand {

		AbstractToggleCommand(String label) {
			super(label);
		}

		@Override
		protected boolean prepare() {
			// Nothing to prepare
			return true;
		}

		@Override
		public void undo() {
			// It's a toggle, so yeah, just execute again
			execute();
		}

		@Override
		public void redo() {
			execute();
		}
	}
}
