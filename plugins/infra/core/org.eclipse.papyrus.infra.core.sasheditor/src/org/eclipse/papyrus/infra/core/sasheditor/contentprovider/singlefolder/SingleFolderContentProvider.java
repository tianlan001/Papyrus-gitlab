/*****************************************************************************
 * Copyright (c) 2009 CEA LIST & LIFL
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.sasheditor.contentprovider.singlefolder;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IAbstractPanelModel;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IContentChangedListener;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IContentChangedListener.ContentEvent;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IContentChangedProvider;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IPageModel;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.ISashWindowsContentProvider;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.ITabFolderModel;

/**
 * A simple implementation of providers allowing only one single folder containing tabs.
 * The tabs can be added and removed.
 *
 * @author dumoulin
 */
public class SingleFolderContentProvider implements ISashWindowsContentProvider, IContentChangedProvider {

	/** The currently selected tab folder */
	private SingleFolderModel currentTabFolder;

	/** */
	private ContentChangeListenerManager contentChangedListenerManager = new ContentChangeListenerManager();

	/**
	 * Constructor.
	 */
	public SingleFolderContentProvider() {
		currentTabFolder = new SingleFolderModel(this);
	}

	/**
	 * Add a listener listening on content changed. This listener will be
	 * notified each time the content change.
	 *
	 * @param listener
	 */
	@Override
	public void addListener(IContentChangedListener listener) {
		contentChangedListenerManager.addContentChangedListener(listener);
	}

	/**
	 * Add a listener listening on content changed. This listener will be
	 * notified each time the content change.
	 *
	 * @param listener
	 */
	@Override
	public void removeListener(IContentChangedListener listener) {
		contentChangedListenerManager.removeContentChangedListener(listener);
	}

	/**
	 * Add a listener listening on content changed. This listener will be
	 * notified each time the content change.
	 *
	 * @param listener
	 */
	protected void firePropertyChanged(ContentEvent event) {
		contentChangedListenerManager.fireContentChanged(event);
	}

	/**
	 * Add the page which should be an IPageModel instance. {@inheritDoc}
	 */
	@Override
	public void addPage(Object newModel) {
		addPage((IPageModel) newModel);
	}

	/**
	 * Add the page which should be an IPageModel instance. {@inheritDoc}
	 */
	@Override
	public void addPage(Object newModel, int index) {
		addPage(index, (IPageModel) newModel);
	}

	public void addPage(IPageModel newModel) {
		currentTabFolder.addItem(newModel);

	}

	public void addPage(int index, IPageModel newModel) {
		currentTabFolder.addItem(index, newModel);
	}

	/**
	 * Do nothing because this implementation doesn't allows folders. {@inheritDoc}
	 */
	@Override
	public void createFolder(ITabFolderModel tabFolder, int tabIndex, ITabFolderModel targetFolder, int side) {
		// Nothing
	}

	/**
	 * Get the root used as root to be shown in the editor. {@inheritDoc}
	 */
	@Override
	public SingleFolderModel getRootModel() {
		return currentTabFolder;
	}

	/**
	 * Create the interface used to access the rootModel. {@inheritDoc}
	 */
	@Override
	public IAbstractPanelModel createChildSashModel(Object root) {
		// The root object should be of type IAbstractPanelModel.
		// This is normally the object returned by getRootPanel
		return (IAbstractPanelModel) root;
	}

	/**
	 * Remove the specified page which should be an instance of IPageModel. {@inheritDoc}
	 */
	@Override
	public void removePage(Object page) {

		removePage((IPageModel) page);
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public void removePage(int index) {
		currentTabFolder.removeTab(index);
	}

	public void removePage(IPageModel tabItem) {
		currentTabFolder.removeTab(tabItem);
	}

	/**
	 * Remove the specified tab. As we have only one folder, this should be from the currentFolder {@inheritDoc}
	 */
	@Override
	public void removePage(ITabFolderModel parentFolder, int tabIndex) {
		removePage(tabIndex);
	}

	/**
	 * Return the currently selected TabFolder.
	 *
	 * @return
	 */
	public ITabFolderModel getCurrentTabFolder() {
		return currentTabFolder;
	}

	/**
	 * Do nothing as we can't have other folders.
	 *
	 * @see org.eclipse.papyrus.infra.core.sasheditor.contentprovider.ISashWindowsContentProvider#setCurrentFolder(java.lang.Object)
	 *
	 * @param rawModel
	 */
	@Override
	public void setCurrentFolder(Object rawModel) {
		// Nothing
	}

	/**
	 * A class managing a list of listeners.
	 *
	 * @author dumoulin
	 */
	protected class ContentChangeListenerManager {

		private List<IContentChangedListener> listeners;

		/**
		 * Add a listener listening on content changed. This listener will be
		 * notified each time the content change.
		 *
		 * @param listener
		 */
		public void addContentChangedListener(IContentChangedListener listener) {
			if (listeners == null) {
				createListeners();
			}

			// Check if already exists.
			if (listeners.contains(listener)) {
				return;
			}

			listeners.add(listener);
		}

		/**
		 * Add a listener listening on content changed. This listener will be
		 * notified each time the content change.
		 *
		 * @param listener
		 */
		public void removeContentChangedListener(IContentChangedListener listener) {
			if (listeners == null) {
				return;
			}

			listeners.remove(listener);
		}

		/**
		 * Create the list of listeners.
		 */
		private void createListeners() {
			if (listeners == null) {
				listeners = new ArrayList<IContentChangedListener>();
			}

		}

		/**
		 * Fire the changed event.
		 *
		 * @param event
		 */
		public void fireContentChanged(ContentEvent event) {
			if (listeners == null) {
				return;
			}

			for (IContentChangedListener listener : listeners) {
				listener.contentChanged(event);
			}
		}
	}

	/**
	 * Move a tab inside the folder. {@inheritDoc}
	 */
	@Override
	public void movePage(ITabFolderModel folderModel, int oldIndex, int newIndex) {
		currentTabFolder.moveTab(oldIndex, newIndex);

	}

	/**
	 * Move a tab from folder to folder.
	 * As this implementation has only one folder, this can't happen. Do nothing. {@inheritDoc}
	 */
	@Override
	public void movePage(ITabFolderModel srcFolderModel, int sourceIndex, ITabFolderModel targetFolderModel, int targetIndex) {
		return;
	}

}
