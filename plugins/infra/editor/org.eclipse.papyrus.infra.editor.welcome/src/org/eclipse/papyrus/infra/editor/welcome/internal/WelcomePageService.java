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

package org.eclipse.papyrus.infra.editor.welcome.internal;

import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.stream.StreamSupport.stream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Spliterator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.sasheditor.SashModelUtils;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.utils.IPageUtils;
import org.eclipse.papyrus.infra.core.sasheditor.editor.DefaultPageLifeCycleEventListener;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IComponentPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IEditorPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IPageLifeCycleEventsListener;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IPageVisitor;
import org.eclipse.papyrus.infra.core.sasheditor.editor.ISashWindowsContainer;
import org.eclipse.papyrus.infra.core.sashwindows.di.PageRef;
import org.eclipse.papyrus.infra.core.sashwindows.di.SashModel;
import org.eclipse.papyrus.infra.core.sashwindows.di.SashWindowsMngr;
import org.eclipse.papyrus.infra.core.sashwindows.di.TabFolder;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.sashwindows.di.util.PageRemovalValidator;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.TransactionHelper;
import org.eclipse.papyrus.infra.editor.welcome.IWelcomePageService;
import org.eclipse.papyrus.infra.editor.welcome.Welcome;
import org.eclipse.papyrus.infra.editor.welcome.WelcomePackage;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.tools.util.PlatformHelper;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.infra.ui.services.EditorLifecycleEventListener;
import org.eclipse.papyrus.infra.ui.services.EditorLifecycleManager;
import org.eclipse.swt.widgets.Display;

/**
 * Default implementation of the <em>Welcome Page</em> service.
 */
public class WelcomePageService implements IWelcomePageService {

	private ServicesRegistry services;
	private ModelSet modelSet;
	private ISashWindowsContainer sashContainer;
	private IPageManager pageManager;
	private EditorLifecycleManager editorManager;

	private WelcomeModelManager welcomeManager;

	private IPageLifeCycleEventsListener sashListener;
	private EditorLifecycleEventListener editorListener;

	private IPage welcomePage;

	public WelcomePageService() {
		super();
	}

	@Override
	public void init(ServicesRegistry servicesRegistry) throws ServiceException {
		this.services = servicesRegistry;
	}

	@Override
	public final ServicesRegistry getOwner() {
		return services;
	}

	@Override
	public void startService() throws ServiceException {
		welcomeManager = Activator.getDefault().getWelcomeModelManager();
		modelSet = services.getService(ModelSet.class);
		welcomeManager.connect(modelSet);
		welcomeManager.onWelcomeChanged(modelSet, this::handleWelcomeChanged);

		pageManager = services.getService(IPageManager.class);

		editorManager = services.getService(EditorLifecycleManager.class);
		editorListener = new EditorListener();
		editorManager.addEditorLifecycleEventsListener(editorListener);

		installPageRemovalValidator();
	}

	@Override
	public void disposeService() throws ServiceException {
		uninstallPageRemovalValidator();

		if (welcomeManager != null) {
			welcomeManager.disconnect(modelSet);
			welcomeManager = null;
		}

		modelSet = null;
		pageManager = null;

		if (editorManager != null) {
			editorManager.removeEditorLifecycleEventsListener(editorListener);
			editorManager = null;
		}
	}

	@Override
	public boolean canCloseWelcomePage() {
		return (welcomePage != null) && (getOpenPageCount() > 1);
	}

	public int getOpenPageCount() {
		class PageCounter implements IPageVisitor {
			int count = 0;

			@Override
			public void accept(IEditorPage page) {
				count++;
			}

			@Override
			public void accept(IComponentPage page) {
				count++;
			}
		}

		PageCounter counter = new PageCounter();

		sashContainer.visit(counter);

		return counter.count;
	}

	@Override
	public void openWelcomePage() {
		if (pageManager != null) {
			if (welcomePage == null) {
				pageManager.openPage(getModel());
			} else {
				pageManager.selectPage(getModel());
			}
		}
	}

	@Override
	public void resetWelcomePage() {
		getWelcomePage().ifPresent(WelcomePage::reset);
	}

	Optional<WelcomePage> getWelcomePage() {
		return Optional.ofNullable(PlatformHelper.getAdapter(welcomePage, WelcomePage.class));
	}

	@Override
	public void saveWelcomePageAsDefault() throws CoreException {
		try {
			welcomeManager.createDefaultWelcomeResource(getWelcome());
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Failed to set default welcome page", e));
		}
	}

	void handleWelcomeChanged(Welcome welcome) {
		getWelcomePage().ifPresent(WelcomePage::layout);
	}

	void checkWelcomePage() {
		Display.getDefault().asyncExec(() -> {
			// Check that the editor hasn't been disposed in the mean-time
			if ((sashContainer != null) && !sashContainer.isDisposed()) {
				if (getOpenPageCount() <= 0) {
					openWelcomePage();
				}

				getWelcomePage().ifPresent(WelcomePage::fireCanCloseChanged);
			}
		});
	}

	@Override
	public Welcome getWelcome() {
		Welcome override = getWelcomeOverride();
		return (override != null) ? override : welcomeManager.getWelcome(modelSet);
	}

	@Override
	public Resource getWelcomeResource() {
		Welcome override = getWelcomeOverride();
		return (override != null) ? override.eResource() : welcomeManager.getWelcomeResource(modelSet);
	}

	/** Get the welcome model stored in the editor's sash resource, if any. */
	private Welcome getWelcomeOverride() {
		Resource sashResource = SashModelUtils.getSashModel(modelSet).getResource();
		return (sashResource == null)
				? null
				: (Welcome) EcoreUtil.getObjectByType(sashResource.getContents(), WelcomePackage.Literals.WELCOME);
	}

	Object getModel() {
		return getWelcome();
	}

	static boolean isModel(Object object) {
		return object instanceof Welcome;
	}

	boolean isWelcomePage(IPage page) {
		return isModel(IPageUtils.getRawModel(page));
	}

	void trackActivePage(PageRef pageRef) {
		TabFolder folder = pageRef.getParent();

		if ((folder != null) && (folder.getCurrentSelection() != pageRef)) {
			SashModel sashModel = EMFHelper.getContainer(folder, SashModel.class);
			if ((sashModel != null) && sashModel.isRestoreActivePage()) {
				// track the active page in this folder, but not as an undoable operation
				EditingDomain domain = EMFHelper.resolveEditingDomain(sashModel);

				try {
					TransactionHelper.run(domain, () -> folder.setCurrentSelection(pageRef));
				} catch (Exception e) {
					Activator.log.error("Failed to track page selection", e); //$NON-NLS-1$
				}
			}
		}
	}

	void initializeActivePages() {
		SashModel sashModel = getSashModel();
		if ((sashModel != null) && sashModel.isRestoreActivePage()) {
			// Select all of the remembered pages to make them active
			sashContainer.getIFolderList().stream()
					.filter(f -> f.getRawModel() instanceof TabFolder)
					.forEach(f -> {
						TabFolder tabFolder = (TabFolder) f.getRawModel();
						if (tabFolder.getCurrentSelection() != null) {
							IPage page = sashContainer.lookupModelPage(tabFolder.getCurrentSelection());
							if (page != null) {
								sashContainer.selectPage(page);
							}
						}
					});
		}
	}

	private SashModel getSashModel() {
		// Resource may have been unloaded and removed
		SashWindowsMngr sashMngr = (modelSet == null) ? null : SashModelUtils.getSashWindowsMngr(modelSet);
		return (sashMngr == null) ? null : sashMngr.getSashModel();
	}

	private void installPageRemovalValidator() {
		SashModel sashModel = getSashModel();

		if (sashModel != null) {
			sashModel.eAdapters().add(new CloseValidator());
		}
	}

	private void uninstallPageRemovalValidator() {
		SashModel sashModel = getSashModel();

		if (sashModel != null) {
			sashModel.eAdapters().removeIf(CloseValidator.class::isInstance);
		}
	}

	//
	// Nested types
	//

	private class SashListener extends DefaultPageLifeCycleEventListener {

		@Override
		public void pageOpened(IPage page) {
			if (isWelcomePage(page)) {
				welcomePage = page;
			}

			checkWelcomePage();
		}

		@Override
		public void pageClosed(IPage page) {
			if (page == welcomePage) {
				welcomePage = null;
			}

			checkWelcomePage();
		}

		@Override
		public void pageActivated(IPage page) {
			if (page.getRawModel() instanceof PageRef) {
				PageRef pageRef = (PageRef) page.getRawModel();
				trackActivePage(pageRef);
			}
		}
	}

	private class EditorListener implements EditorLifecycleEventListener {

		@Override
		public void postInit(IMultiDiagramEditor editor) {
			// Pass
		}

		@Override
		public void preDisplay(IMultiDiagramEditor editor) {
			sashContainer = editor.getAdapter(ISashWindowsContainer.class);
			sashListener = new SashListener();
			sashContainer.addPageLifeCycleListener(sashListener);

			welcomePage = IPageUtils.lookupModelPage(sashContainer, getModel());
			if (welcomePage == null) {
				// Maybe we have a left-over override that we're not using?
				welcomePage = IPageUtils.lookupModelPage(sashContainer, welcomeManager.getWelcome(modelSet));
			}
			checkWelcomePage();

			initializeActivePages();
		}

		@Override
		public void postDisplay(IMultiDiagramEditor editor) {
			// Pass
		}

		@Override
		public void beforeClose(IMultiDiagramEditor editor) {
			// By this time, it's already too late to remove the page lifecycle listener, so
			// don't bother (it isn't necessary, anyways, once the sash container is gone)
			sashListener = null;
		}

	}

	private class CloseValidator extends AdapterImpl implements PageRemovalValidator {
		private SashModel sashModel;

		@Override
		public void setTarget(Notifier newTarget) {
			if (newTarget instanceof SashModel) {
				sashModel = (SashModel) newTarget;
			}
		}

		@Override
		public void unsetTarget(Notifier oldTarget) {
			if (oldTarget == sashModel) {
				sashModel = null;
			}
		}

		@Override
		public boolean canRemovePage(PageRef page) {
			Object pageIdentifier = page.getPageIdentifier();
			return (pageIdentifier != getModel()) || canCloseWelcomePage();
		}

		@Override
		public Collection<? extends PageRef> filterRemovablePages(Collection<? extends PageRef> pages) {
			Collection<? extends PageRef> result;
			Optional<? extends PageRef> welcomePage = pages.stream().filter(p -> p.getPageIdentifier() == getModel()).findAny();

			// No problem if the welcome page is not among them
			if (!welcomePage.isPresent()) {
				result = pages;
			} else {
				// If these are all of the pages, then don't close the welcome page
				long allPagesCount = stream(spliteratorUnknownSize(sashModel.eAllContents(), Spliterator.ORDERED), false)
						.filter(PageRef.class::isInstance)
						.count();
				if (allPagesCount > pages.size()) {
					result = pages;
				} else {
					result = new ArrayList<>(pages);
					result.remove(welcomePage.get());
				}
			}

			return result;
		}
	}
}
