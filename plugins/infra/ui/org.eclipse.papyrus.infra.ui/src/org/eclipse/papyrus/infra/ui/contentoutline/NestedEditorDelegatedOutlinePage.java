/*****************************************************************************
 * Copyright (c) 2013, 2014 CEA LIST and other.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 437217
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.contentoutline;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.papyrus.infra.core.Activator;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IEditorPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IPageLifeCycleEventsListener;
import org.eclipse.papyrus.infra.core.sasheditor.editor.ISashWindowsContainer;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.utils.AdapterUtils;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.infra.ui.editor.IReloadableEditor;
import org.eclipse.papyrus.infra.ui.editor.reload.EditorReloadEvent;
import org.eclipse.papyrus.infra.ui.editor.reload.IEditorReloadListener;
import org.eclipse.papyrus.infra.ui.editor.reload.IReloadContextProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.SubActionBars;
import org.eclipse.ui.part.IPageBookViewPage;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.PageSite;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.google.common.collect.Lists;

/**
 * Page for Papyrus outline when active nested editor is a GMF editor
 * 
 * @since 1.2
 */
public class NestedEditorDelegatedOutlinePage extends Page implements IPapyrusContentOutlinePage, IPageLifeCycleEventsListener, IEditorReloadListener {

	/** The editor for which I am a slave. */
	private IMultiDiagramEditor multiEditor;

	/** Sash window container to listen for page changes inside the same editor */
	private ISashWindowsContainer sashWindowsContainer;

	/** Page book in which all outline controls of nested editors will be stored and displayed one by one */
	private PageBook sashEditorPageBook;

	/**
	 * Map from papyrus pages (representing nested editors) to outline page records (key type: <code>org.eclipse.papyrus.infra.core.sasheditor.editor.IPage</code>;
	 * value type: <code>OutlinePageRec</code>).
	 */
	private Map<IPage, OutlinePageRec> mapIPapyrusPageToOutlineRec = new HashMap<IPage, OutlinePageRec>();

	/**
	 * The page rec which provided the current page or <code>null</code>
	 */
	private OutlinePageRec activeRec;

	/**
	 * Default page rec that displays a simple message
	 */
	private OutlinePageRec defaultPageRec;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(IMultiDiagramEditor multiEditor) {
		this.multiEditor = multiEditor;

		internalInit(multiEditor);

		IReloadableEditor.Adapter.getAdapter(multiEditor).addEditorReloadListener(this);
	}

	private void internalInit(IMultiDiagramEditor multiEditor) {
		sashWindowsContainer = multiEditor.getAdapter(ISashWindowsContainer.class);
		sashWindowsContainer.addPageLifeCycleListener(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(IPageSite pageSite) {
		IViewSite viewSite = getViewSite(pageSite);

		DelegatedPageSite delegatedPageSite = new DelegatedPageSite(viewSite, this);
		super.init(delegatedPageSite);
	}

	/**
	 * /**
	 * The <code>PageBookView</code> implementation of this <code>IWorkbenchPart</code> method cleans up all the pages. Subclasses
	 * may extend.
	 */
	@Override
	public void dispose() {
		if (multiEditor != null) {
			IReloadableEditor.Adapter.getAdapter(multiEditor).removeEditorReloadListener(this);
		}

		internalDispose();

		multiEditor = null;

		// Run super.
		super.dispose();
	}

	private void internalDispose() {
		// Deref all of the pages.
		activeRec = null;
		if (defaultPageRec != null) {
			// check for null since the default page may not have
			// been created (ex. perspective never visible)
			defaultPageRec.contentOutlinePage.dispose();
			defaultPageRec.dispose();
			defaultPageRec = null;
		}

		java.util.List<OutlinePageRec> records = new ArrayList<NestedEditorDelegatedOutlinePage.OutlinePageRec>(mapIPapyrusPageToOutlineRec.values());
		Iterator<OutlinePageRec> itr = records.iterator();
		while (itr.hasNext()) {
			OutlinePageRec rec = itr.next();
			removePage(rec);
		}

		// remove listener and all refs to editor
		sashWindowsContainer.removePageLifeCycleListener(this);
	}

	/**
	 * Refreshes the global actions for the active page.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void refreshGlobalActionHandlers() {
		// Clear old actions.
		IActionBars bars = getSite().getActionBars();
		bars.clearGlobalActionHandlers();

		// Set new actions.
		Map newActionHandlers = ((SubActionBars) activeRec.getPageSite().getActionBars()).getGlobalActionHandlers();
		if (newActionHandlers != null) {
			Set<?> keys = newActionHandlers.entrySet();
			Iterator<?> iter = keys.iterator();
			while (iter.hasNext()) {
				Map.Entry<String, IAction> entry = (Map.Entry) iter.next();
				bars.setGlobalActionHandler(entry.getKey(), entry.getValue());
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		// nothing here
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ISelection getSelection() {
		// nothing here
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		// nothing here
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSelection(ISelection selection) {
		// nothing here
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createControl(Composite parent) {
		sashEditorPageBook = new PageBook(parent, SWT.BORDER);

		createContents();
	}

	protected void createContents() {
		// Create the default page rec.
		IContentOutlinePage defaultPage = createDefaultPage(sashEditorPageBook);
		defaultPageRec = new OutlinePageRec(null, defaultPage);
		preparePage(defaultPageRec);

		// Show the initial active page or the default page
		IPage activePage = sashWindowsContainer.getActiveSashWindowsPage();
		if (activePage != null) {
			OutlinePageRec rec = getOutlinePageRec(activePage);
			if (rec == null) {
				rec = createPage(activePage);
			}

			// Show the page, if it was successfully created
			if (rec != null) {
				showOutlinePageRec(rec);
			} else {
				showOutlinePageRec(defaultPageRec);
			}
		} else {
			showOutlinePageRec(defaultPageRec);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Control getControl() {
		return sashEditorPageBook;
	}

	/**
	 * {@inheritDoc}
	 */
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// nothing here
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void pageChanged(IPage newPage) {
		// throw new UnsupportedOperationException("pageChanged not implemented " + newPage);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void pageOpened(IPage page) {
		// Activator.log.debug("Opened");
		// create the new Outline
		// Create a page for the part.
		OutlinePageRec rec = getOutlinePageRec(page);
		if (rec == null) {
			rec = createPage(page);
		}

		// Show the page, if it was successfully created
		if (rec != null) {
			showOutlinePageRec(rec);
		} else {
			showOutlinePageRec(defaultPageRec);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void pageClosed(IPage papyrusPage) {
		// Activator.log.debug("Closed");
		// Update the active part.
		if (activeRec != null && activeRec.papyrusPage == papyrusPage) {
			showOutlinePageRec(defaultPageRec);
		}

		// Find and remove the part page.
		OutlinePageRec rec = getOutlinePageRec(papyrusPage);
		if (rec != null) {
			removePage(rec);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void pageActivated(IPage page) {
		// Activator.log.debug("Activated");
		// Create a page for the partm, if necessary.
		OutlinePageRec rec = getOutlinePageRec(page, true);

		// Show the page, if it was successfully created
		if (rec != null) {
			showOutlinePageRec(rec);
		} else {
			showOutlinePageRec(defaultPageRec);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void pageDeactivated(IPage page) {
		// throw new UnsupportedOperationException("pageDeactivated not implemented " + page);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void pageAboutToBeOpened(IPage page) {
		// throw new UnsupportedOperationException("pageAboutToBeOpened not implemented "+page);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void pageAboutToBeClosed(IPage page) {
		// throw new UnsupportedOperationException("pageAboutToBeClosed not implemented " + page);
	}

	@Override
	public void editorAboutToReload(EditorReloadEvent event) {
		event.putContext(new OutlineContext());

		internalDispose();
	}

	@Override
	public void editorReloaded(EditorReloadEvent event) {
		internalInit(event.getEditor());
		createContents();

		((OutlineContext) event.getContext()).restore();
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// MAINLY INSPIRED FROM PAGE BOOK VIEW
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Creates and returns the default page for this view.
	 *
	 * @param book
	 *            the pagebook control
	 * @return the default page
	 */
	protected IContentOutlinePage createDefaultPage(PageBook book) {
		MessageOutlinePage page = new MessageOutlinePage();
		initPage(page);
		page.createControl(book);
		return page;
	}

	/**
	 * Creates an outline record for a given papyrus Page. Adds it to the pagebook but does not show it.
	 *
	 * @param page
	 *            The nested editor we are created an outline.
	 * @return the created outline page record
	 */
	protected OutlinePageRec createPage(IPage papyrusPage) {
		OutlinePageRec rec = doCreatePage(papyrusPage);
		if (rec != null) {
			mapIPapyrusPageToOutlineRec.put(papyrusPage, rec);
			preparePage(rec);
		}
		return rec;
	}

	/**
	 * Prepares the page in the given page rec for use in this view.
	 *
	 * @param rec
	 */
	protected void preparePage(OutlinePageRec rec) {
		IPageSite site = null;

		if (!doesPageExist(rec.contentOutlinePage)) {
			if (rec.contentOutlinePage instanceof IPageBookViewPage) {
				site = ((IPageBookViewPage) rec.contentOutlinePage).getSite();
				rec.setPageSite(site);
			}
		}
	}

	/**
	 * Initializes the given page with a page site.
	 * <p>
	 * Subclasses should call this method after the page is created but before creating its controls.
	 * </p>
	 * <p>
	 * Subclasses may override
	 * </p>
	 *
	 * @param page
	 *            The page to initialize
	 */
	protected void initPage(IPageBookViewPage page) {
		try {
			IPageSite site = super.getSite();
			// try to create a specific page site for this page
			page.init(new PageSite(getViewSite(site)));
		} catch (PartInitException e) {
			Activator.log.error(e);
		}
	}

	/**
	 * @param site
	 *            the page site from which parent view site is retrieved
	 * @return the retrieved page site
	 */
	protected static IViewSite getViewSite(IPageSite site) {
		if (site instanceof IViewSite) {
			return ((IViewSite) site);
		}
		// no way to get the IViewSite from the page site.
		if (site instanceof PageSite) {
			try {
				Field parentSiteField = PageSite.class.getDeclaredField("parentSite");
				parentSiteField.setAccessible(true);
				Object parentSite = parentSiteField.get(site);
				if (parentSite instanceof IViewSite) {
					return ((IViewSite) parentSite);
				}
			} catch (SecurityException e) {
				Activator.log.error(e);
			} catch (NoSuchFieldException e) {
				Activator.log.error(e);
			} catch (IllegalArgumentException e) {
				Activator.log.error(e);
			} catch (IllegalAccessException e) {
				Activator.log.error(e);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * Method declared on PageBookView.
	 */
	protected OutlinePageRec doCreatePage(IPage papyrusPage) {
		// Try to get an outline page.
		if (papyrusPage instanceof IEditorPage) {
			IEditorPart part = ((IEditorPage) papyrusPage).getIEditorPart();
			Object obj = getAdapter(part, IContentOutlinePage.class, false);
			if (obj instanceof IContentOutlinePage) {
				IContentOutlinePage page = (IContentOutlinePage) obj;
				if (page instanceof IPageBookViewPage) {
					initPage((IPageBookViewPage) page);
				}
				page.createControl(getPageBook());
				return new OutlinePageRec(papyrusPage, page);
			}
		}

		// There is no content outline
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DelegatedPageSite getSite() {
		return (DelegatedPageSite) super.getSite();
	}

	/*
	 * (non-Javadoc)
	 * Method declared on PageBookView.
	 */
	protected void doDestroyPage(IPage papyrusPage, OutlinePageRec rec) {
		IContentOutlinePage contentOutlinePage = rec.contentOutlinePage;
		contentOutlinePage.dispose();
		rec.dispose();
	}

	protected Collection<OutlinePageRec> getAllPages() {
		return mapIPapyrusPageToOutlineRec.values();
	}

	/**
	 * Returns true if the page has already been created.
	 *
	 * @param page
	 *            the page to test
	 * @return true if this page has already been created.
	 */
	protected boolean doesPageExist(IContentOutlinePage page) {
		return mapIPapyrusPageToOutlineRec.containsKey(page);
	}

	/**
	 * Returns the papyrus page which contributed the current outline page to this view.
	 *
	 * @return the page which contributed the current outline page or <code>null</code> if no part contributed the current page
	 */
	protected IPage getCurrentContributingPage() {
		if (activeRec == null) {
			return null;
		}
		return activeRec.papyrusPage;
	}

	/**
	 * Returns the currently visible outline page for this view or <code>null</code> if no page is currently visible.
	 *
	 * @return the currently visible page
	 */
	public IContentOutlinePage getCurrentOutlinePage() {
		if (activeRec == null) {
			return null;
		}
		return activeRec.contentOutlinePage;
	}

	/**
	 * Returns the view site for the given page of this view.
	 *
	 * @param page
	 *            the page
	 * @return the corresponding site, or <code>null</code> if not found
	 */
	protected IPageSite getPageSite(IPage page) {
		OutlinePageRec rec = getOutlinePageRec(page);
		if (rec != null) {
			return rec.getPageSite();
		}
		return null;
	}

	/**
	 * Returns the default page for this view.
	 *
	 * @return the default page
	 */
	public IContentOutlinePage getDefaultOutlinePage() {
		return defaultPageRec.contentOutlinePage;
	}

	/**
	 * Returns the pagebook control for this view.
	 *
	 * @return the pagebook control, or <code>null</code> if not initialized
	 */
	protected PageBook getPageBook() {
		return sashEditorPageBook;
	}

	/**
	 * Returns the page record for the given part.
	 *
	 * @param part
	 *            the part
	 * @return the corresponding page record, or <code>null</code> if not
	 *         found
	 */
	protected OutlinePageRec getOutlinePageRec(IPage papyrusPage) {
		return mapIPapyrusPageToOutlineRec.get(papyrusPage);
	}

	OutlinePageRec getOutlinePageRec(IPage papyrusPage, boolean create) {
		OutlinePageRec result = getOutlinePageRec(papyrusPage);
		if (result == null) {
			result = createPage(papyrusPage);
		}
		return result;
	}

	/**
	 * Returns the page record for the given page of this view.
	 *
	 * @param page
	 *            the page
	 * @return the corresponding page record, or <code>null</code> if not
	 *         found
	 */
	protected OutlinePageRec getPageRec(IContentOutlinePage contentOutlinePage) {
		Iterator<OutlinePageRec> itr = mapIPapyrusPageToOutlineRec.values().iterator();
		while (itr.hasNext()) {
			OutlinePageRec rec = itr.next();
			if (rec.contentOutlinePage == contentOutlinePage) {
				return rec;
			}
		}
		return null;
	}

	/**
	 * Removes a page record.
	 *
	 * @param rec
	 *            the page record to remove
	 */
	protected void removePage(OutlinePageRec rec) {
		mapIPapyrusPageToOutlineRec.remove(rec.papyrusPage);

		Control control = rec.contentOutlinePage.getControl();
		if (control != null && !control.isDisposed()) {
			// Dispose the page's control so pages don't have to do this in their dispose method.
			// The page's control is a child of this view's control so if this view is closed, the page's control will already be disposed.
			control.dispose();
		}

		// Do this before destroying the page, otherwise we won't be able to retrieve the page site (it will be null)
		IPageSite site = rec.getPageSite();
		if (site instanceof PageSite) { // test null pointer and PageSite
			((SubActionBars) ((PageSite) site).getActionBars()).deactivate();
			((SubActionBars) ((PageSite) site).getActionBars()).dispose();
		}

		// Free the page
		doDestroyPage(rec.papyrusPage, rec);
	}

	/*
	 * (non-Javadoc) Method declared on IWorkbenchPart.
	 */
	@Override
	public void setFocus() {
		// first set focus on the page book, in case the page
		// doesn't properly handle setFocus
		if (sashEditorPageBook != null) {
			sashEditorPageBook.setFocus();
		}
		// then set focus on the page, if any
		if (activeRec != null) {
			activeRec.contentOutlinePage.setFocus();
		}
	}

	/**
	 * Shows page contained in the given page record in this view. The page
	 * record must be one from this pagebook view.
	 * <p>
	 * The <code>PageBookView</code> implementation of this method asks the pagebook control to show the given page's control, and records that the given page is now current. Subclasses may extend.
	 * </p>
	 *
	 * @param pageRec
	 *            the page record containing the page to show
	 */
	protected void showOutlinePageRec(OutlinePageRec pageRec) {
		// If already showing do nothing
		if (activeRec == pageRec) {
			return;
		}
		// If the page is the same, just set activeRec to pageRec
		if (activeRec != null && pageRec != null && activeRec.contentOutlinePage == pageRec.contentOutlinePage) {
			activeRec = pageRec;
			return;
		}

		activeRec = pageRec;
		if (activeRec != null) {
			Control pageControl = activeRec.contentOutlinePage.getControl();
			if (pageControl != null && !pageControl.isDisposed()) {
				PageSite pageSite = (PageSite) activeRec.getPageSite();
				// Verify that the page control is not disposed
				// If we are closing, it may have already been disposed
				sashEditorPageBook.showPage(pageControl);
				getSite().setActivePageSite(pageSite);
			}
		}
	}

	/**
	 * If it is possible to adapt the given object to the given type, this
	 * returns the adapter. Performs the following checks:
	 *
	 * <ol>
	 * <li>Returns <code>sourceObject</code> if it is an instance of the adapter type.</li>
	 * <li>If sourceObject implements IAdaptable, it is queried for adapters.</li>
	 * <li>If sourceObject is not an instance of PlatformObject (which would have already done so), the adapter manager is queried for adapters</li>
	 * </ol>
	 *
	 * Otherwise returns null.
	 *
	 * @param sourceObject
	 *            object to adapt, or null
	 * @param adapter
	 *            type to adapt to
	 * @param activatePlugins
	 *            true if IAdapterManager.loadAdapter should be used (may trigger plugin activation)
	 * @return a representation of sourceObject that is assignable to the
	 *         adapter type, or null if no such representation exists
	 */
	public static Object getAdapter(Object sourceObject, Class<?> adapter, boolean activatePlugins) {
		Assert.isNotNull(adapter);
		if (sourceObject == null) {
			return null;
		}
		if (adapter.isInstance(sourceObject)) {
			return sourceObject;
		}

		if (sourceObject instanceof IAdaptable) {
			IAdaptable adaptable = (IAdaptable) sourceObject;

			Object result = adaptable.getAdapter(adapter);
			if (result != null) {
				// Sanity-check
				Assert.isTrue(adapter.isInstance(result));
				return result;
			}
		}

		if (!(sourceObject instanceof PlatformObject)) {
			Object result;
			if (activatePlugins) {
				result = Platform.getAdapterManager().loadAdapter(sourceObject, adapter.getName());
			} else {
				result = Platform.getAdapterManager().getAdapter(sourceObject, adapter);
			}
			if (result != null) {
				return result;
			}
		}

		return null;
	}

	/**
	 * A data structure used to store the information about the editor outline page within the papyrus outline page.
	 */
	protected static class OutlinePageRec {

		public Object subActionBars;

		/** papyrus page: current editor opened as nested editor */
		public IPage papyrusPage;

		/** outline page recorded for the given papyrus page */
		public IContentOutlinePage contentOutlinePage;

		/** page site for the recorded outline page */
		public IPageSite pageSite;

		/**
		 * Creates a new page record initialized to the given papyrus page and outline page.
		 *
		 * @param papyrusPage
		 * @param contentOutlinePage
		 */
		public OutlinePageRec(IPage papyrusPage, IContentOutlinePage contentOutlinePage) {
			this.papyrusPage = papyrusPage;
			this.contentOutlinePage = contentOutlinePage;
		}

		/**
		 * Sets the page site
		 *
		 * @param pageSite
		 *            the page site for the recorded content outline page
		 */
		public void setPageSite(IPageSite pageSite) {
			this.pageSite = pageSite;
		}

		/**
		 * Sets the page site
		 *
		 * @param pageSite
		 *            the page site for the recorded content outline page
		 */
		public IPageSite getPageSite() {
			return this.pageSite;
		}

		/**
		 * Disposes of this page record by <code>null</code>ing its fields.
		 */
		public void dispose() {
			papyrusPage = null;
			contentOutlinePage = null;
			pageSite = null;
		}
	}

	protected static class DelegatedPageSite extends PageSite {

		/** Page site of the active page in the book */
		protected PageSite activePageSite;

		private NestedEditorDelegatedOutlinePage nestedEditorDelegatedOutlinePage;

		/**
		 * Constructor.
		 *
		 * @param parentViewSite
		 * @param nestedEditorDelegatedOutlinePage
		 */
		public DelegatedPageSite(IViewSite parentViewSite, NestedEditorDelegatedOutlinePage nestedEditorDelegatedOutlinePage) {
			super(parentViewSite);
			this.nestedEditorDelegatedOutlinePage = nestedEditorDelegatedOutlinePage;
		}

		/**
		 * Sets the active page site
		 *
		 * @param activePageSite
		 *            the activePageSite to set
		 */
		public void setActivePageSite(PageSite activePageSite) {
			// remove the contribution of the previous active page site
			if (this.activePageSite != null) {
				// update the action bars for the current page
				getActionBars().deactivate();
				getActionBars().clearGlobalActionHandlers();
				getActionBars().updateActionBars();

				activePageSite.deactivate();

			}
			this.activePageSite = activePageSite;
			if (this.activePageSite != null) {
				activePageSite.activate();
				// update the action bars for the current page
				getActionBars().activate();
				getActionBars().updateActionBars();
			}
		}

		/**
		 * Returns the active page site
		 *
		 * @return the active Page Site
		 */
		public PageSite getActivePageSite() {
			return activePageSite;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public SubActionBars getActionBars() {
			if (activePageSite != null) {
				return (SubActionBars) activePageSite.getActionBars();
			}
			return (SubActionBars) super.getActionBars();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void deactivate() {
			// deactivate the action bars of the current active page
			if (activePageSite != null) {
				activePageSite.deactivate();
			}

			// deactivate all subcontributions
			for (OutlinePageRec rec : nestedEditorDelegatedOutlinePage.getAllPages()) {
				IPageSite site = rec.getPageSite();
				IActionBars bars = site.getActionBars();
				if (bars instanceof SubActionBars) {
					SubActionBars subActionBars = (SubActionBars) bars;
					subActionBars.deactivate();
					subActionBars.clearGlobalActionHandlers();
					subActionBars.updateActionBars();
				}
			}
			super.deactivate();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void activate() {

			// here, we have to desactivate all contributions of all pages of this delegating service.
			// when the page site is activated, the pagebookview has already tried to update some action bars, even if it should not
			// so we recompute all the active contributions items here, after desactivating all the contributions.
			for (OutlinePageRec rec : nestedEditorDelegatedOutlinePage.getAllPages()) {
				IPageSite site = rec.getPageSite();
				IActionBars bars = site.getActionBars();
				if (bars instanceof SubActionBars) {
					SubActionBars subActionBars = (SubActionBars) bars;
					subActionBars.deactivate();
					subActionBars.clearGlobalActionHandlers();
					subActionBars.updateActionBars();
				}
			}
			if (this.activePageSite != null) {
				activePageSite.activate();
				// update the action bars for the current page
				getActionBars().activate();
				getActionBars().updateActionBars();
			}
			super.activate();
		}
	}

	protected static class MessageOutlinePage implements IContentOutlinePage, IPageBookViewPage {

		private Text label;

		private IPageSite site;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void createControl(Composite parent) {
			label = new Text(parent, SWT.NONE);
			label.setText("No outline for this editor");
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void dispose() {
			if (label != null && label.isDisposed()) {
				label.dispose();
				label = null;
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Control getControl() {
			return label;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setActionBars(IActionBars actionBars) {
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setFocus() {
			if (label != null && label.isDisposed()) {
				label.setFocus();
			}
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void addSelectionChangedListener(ISelectionChangedListener listener) {
			// nothing here
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public ISelection getSelection() {
			// nothing here
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void removeSelectionChangedListener(ISelectionChangedListener listener) {
			// nothing here
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setSelection(ISelection selection) {
			// nothing here
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public IPageSite getSite() {
			return site;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void init(IPageSite site) throws PartInitException {
			this.site = site;
		}

	}

	private class OutlineContext {

		private List<PageContext> pages = Lists.newArrayListWithCapacity(mapIPapyrusPageToOutlineRec.size());

		OutlineContext() {
			for (OutlinePageRec next : mapIPapyrusPageToOutlineRec.values()) {
				pages.add(new PageContext(next));
			}
		}

		public void restore() {
			for (PageContext next : pages) {
				next.restore();
			}
		}

		//
		// Nested types
		//

		private class PageContext {

			final URI diagramToken;

			final Object context;

			PageContext(OutlinePageRec outlinePage) {
				Object diagram = outlinePage.papyrusPage.getRawModel();
				diagramToken = (diagram instanceof EObject) ? EcoreUtil.getURI((EObject) diagram) : null;

				// Can only sensibly manage restoring the state of the page if we can find it again
				if (diagramToken == null) {
					context = null;
				} else {
					IReloadContextProvider provider = AdapterUtils.adapt(outlinePage.contentOutlinePage, IReloadContextProvider.class, null);
					context = (provider == null) ? null : provider.createReloadContext();
				}
			}

			void restore() {
				if (diagramToken != null) {
					try {
						ModelSet modelSet = multiEditor.getServicesRegistry().getService(ModelSet.class);

						Object diagram = modelSet.getEObject(diagramToken, true);
						if (diagram != null) {
							IPage page = sashWindowsContainer.lookupModelPage(diagram);
							if (page != null) {
								OutlinePageRec outlinePage = getOutlinePageRec(page, true);
								if ((outlinePage != null) && (context != null)) {
									// Restore it. We know it adapts if it provided the reload state in the first place
									AdapterUtils.adapt(outlinePage.contentOutlinePage, IReloadContextProvider.class, null).restore(context);
								}
							}
						}
					} catch (ServiceException e) {
						Activator.log.error(e);
					}
				}
			}
		}
	}
}
