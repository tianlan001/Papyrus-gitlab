/*****************************************************************************
 * Copyright (c) 2009, 2019 CEA LIST & LIFL
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
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 546686
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.sasheditor.internal;


import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.papyrus.infra.core.sasheditor.Activator;
import org.eclipse.papyrus.infra.core.sasheditor.editor.actionbarcontributor.IMultiPageEditorActionBarContributor;
import org.eclipse.papyrus.infra.core.sasheditor.internal.ActivePageTracker.IActiveEditorChangedListener;
import org.eclipse.papyrus.infra.core.sasheditor.internal.eclipsecopy.MultiPageSelectionProvider;
import org.eclipse.ui.IEditorActionBarContributor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.internal.services.INestable;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;
import org.eclipse.ui.services.IServiceLocator;


/**
 * This class is used to switch the services of a nested editor when the active nested editor change.
 * This class is a {@link IActiveEditorChangedListener} registered to the {@link ActivePageTracker} by the {@link SashWindowsContainer}.
 *
 * When a new Editor is set active, by calling {@link #setActiveEditor(PagePart)}, following actions are
 * performed:
 * <ul>
 * <li>deactivate services: nested site and keybinding</li>
 * <li>An event is sent to the ActionBarContributor if it accept it (by implementing {@link IMultiPageEditorActionBarContributor})</li>
 * <li>Send {@link SelectionChangedEvent} to the main editor. The event contains the current selection of the new active editor.</li>
 * <li>Connect the keybinding service to the new Editor.</li>
 * </ul>
 *
 *
 * @author cedric dumoulin
 *
 */
public class ActiveEditorServicesSwitcher implements IActiveEditorChangedListener {

	/** The currently active editor */
	protected PagePart activeEditor;

	/**
	 * The {@link IEditorSite} of the outerMost editor (the main editor) ...
	 */
	private IEditorSite outerEditorSite;

	/**
	 * The active service locator. This value may be <code>null</code> if there is no selected page, or if the selected page is a control with no
	 * site.
	 */
	private INestable activeServiceLocator;

	/**
	 * Constructor.
	 *
	 * @param outerEditorSite
	 *            {@link IEditorSite} of the main editor.
	 */
	public ActiveEditorServicesSwitcher(IEditorSite outerEditorSite) {
		this.outerEditorSite = outerEditorSite;
	}

	/**
	 * Get the currently active IEditorPart, if any.
	 * Return null if there is no active IeditorPart.
	 * Method check if the active leaf encapsulate an IEditorPart. Return it if true.
	 *
	 * @return The currentlyactive IEditorPart or null.
	 */
	private IEditorPart getActiveIEditorPart() {
		return getIEditorPart(activeEditor);
	}

	/**
	 * Get IEditorPart of the specified PagePart, if any.
	 * Return null if there is no IeditorPart.
	 * Method check if the leaf encapsulate an IEditorPart. Return it if true.
	 *
	 * @return The IEditorPart or null.
	 */
	private IEditorPart getIEditorPart(PagePart pagePart) {
		if (pagePart == null) {
			return null;
		}

		if (pagePart instanceof EditorPart) {
			return ((EditorPart) pagePart).getIEditorPart();
		}
		// not found
		return null;
	}

	/**
	 * Called when the active editor is changed.
	 * Perform requested operations.
	 *
	 * @param oldEditor
	 * @param newEditor
	 */
	@Override
	public void activeEditorChanged(PagePart oldEditor, PagePart newEditor) {
		if (activeEditor == newEditor) {
			return;
		}

		// System.out.println(getClass().getSimpleName() + ".activeEditorChange('" + (newEditor != null ? newEditor.getPageTitle() : "null") + "')");

		activeEditor = newEditor;

		// Set focus
		IPartService partService = getOuterEditorSite().getService(IPartService.class);
		if (newEditor != null && partService.getActivePart() == getOuterEditorSite().getPart()) {
			newEditor.setFocus();
		}

		// Switch services
		deactivateServices(false);
		fireChangeEventToActionBarContributor();
		propagateSelectionChanged();
		activateServices();

		//
		if (newEditor != null) {
			newEditor.setFocus();
		}
	}

	/**
	 * Change the current selection of the outermost editor (the main editor).
	 * Send a {@link SelectionChangedEvent} event to the outerProvider. The event contains the current selection
	 * of the new activeEditor.
	 *
	 * @param editor
	 *            The new activeEditor.
	 */
	private void propagateSelectionChanged() {

		// Get the IEditor
		IEditorPart editorPart = getActiveIEditorPart();

		// Propagate the selection change event.
		// Get the selection of the new activeEditor and send an SelectionChangedEvent to the outerProvider (provider of the main
		// editor) with the selection.
		if (editorPart != null) {
			ISelectionProvider selectionProvider = editorPart.getSite().getSelectionProvider();
			if (selectionProvider != null) {
				ISelectionProvider outerProvider = getOuterEditorSite().getSelectionProvider();
				if (outerProvider instanceof MultiPageSelectionProvider) {
					SelectionChangedEvent event = new SelectionChangedEvent(selectionProvider, selectionProvider.getSelection());

					MultiPageSelectionProvider provider = (MultiPageSelectionProvider) outerProvider;
					provider.fireSelectionChanged(event);
					provider.firePostSelectionChanged(event);
				} else {
					Activator.log.warn(this.getClass().getSimpleName()
							+ " did not propogate selection for " //$NON-NLS-1$
							+ editorPart.getTitle());
				}
			}
		}
	}

	/**
	 * Activates services of the active editor: site, keybinding
	 * deactivate old active site.
	 */
	@SuppressWarnings({ "restriction" })
	private void activateServices() {
		// Deactivate old active site
		if (activeServiceLocator != null) {
			activeServiceLocator.deactivate();
			activeServiceLocator = null;
		}

		final IEditorPart editor = getActiveIEditorPart();

		if (editor != null) {
			// It is not needed to manage the keys binding service because the nested binding service is already managed in the parent service currently

			// Activate the services for the new service locator.
			final IServiceLocator serviceLocator = editor.getEditorSite();
			if (serviceLocator instanceof INestable) {
				activeServiceLocator = (INestable) serviceLocator;
				activeServiceLocator.activate();
			}

		}
	}

	/**
	 * Deactivate services: old nested site if any and keybinding service if there is no active editor.
	 * Deactivate the key binding service.
	 * Deactivate it only if there is no editor selected.
	 */
	@SuppressWarnings({ "restriction", "deprecation" })
	private void deactivateServices(boolean immediate) {
		// Deactivate the nested services from the last active service locator.
		if (activeServiceLocator != null) {
			activeServiceLocator.deactivate();
			activeServiceLocator = null;
		}

		// It is no more needed to manage nested keys binding service because this is already done by its parent service
	}

	/**
	 * Send message to the ActionBarContributor, like this it can switch the active editor.
	 */
	private void fireChangeEventToActionBarContributor() {
		IEditorActionBarContributor contributor = getOuterEditorSite().getActionBarContributor();
		if (contributor != null && contributor instanceof IMultiPageEditorActionBarContributor) {
			((IMultiPageEditorActionBarContributor) contributor).setActivePage(getActiveIEditorPart());
		}

		// Ensure compatibility with Eclipse MultiPageEditorActionBarContributor
		else if (contributor != null && contributor instanceof MultiPageEditorActionBarContributor) {
			((MultiPageEditorActionBarContributor) contributor).setActivePage(getActiveIEditorPart());
		}

	}

	/**
	 * Return the MultipageEditorSite
	 *
	 * @return
	 */
	private IEditorSite getOuterEditorSite() {
		return outerEditorSite;
	}


}
