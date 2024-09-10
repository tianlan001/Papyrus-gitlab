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
 *  Emilien Perico emilien.perico@atosorigin.com - add methods to manage di resource
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.sashwindows.di.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.sashwindows.di.DiFactory;
import org.eclipse.papyrus.infra.core.sashwindows.di.PageRef;
import org.eclipse.papyrus.infra.core.sashwindows.di.SashModel;
import org.eclipse.papyrus.infra.core.sashwindows.di.SashWindowsMngr;
import org.eclipse.papyrus.infra.core.sashwindows.di.TabFolder;
import org.eclipse.papyrus.infra.core.sashwindows.di.Window;
import org.eclipse.papyrus.infra.core.sashwindows.di.exception.SashEditorException;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.BasicPageManagerImpl;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.BasicPageManagerImpl.SashModelOperation;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;


/**
 * Set of utility methods
 *
 * @author dumoulin
 */
public class DiUtils {

	/**
	 * Create a default SashModel with one window and one folder.
	 * Set the current folder.
	 *
	 * @param diResource
	 * @return
	 */
	static public SashModel createDefaultSashModel() {

		// SashModel
		SashModel sashModel = DiFactory.eINSTANCE.createSashModel();
		Window window = DiFactory.eINSTANCE.createWindow();
		sashModel.getWindows().add(window);

		TabFolder folder = DiFactory.eINSTANCE.createTabFolder();
		window.setPanel(folder);
		// Default folder
		sashModel.setCurrentSelectionSilently(folder);

		return sashModel;
	}

	/**
	 * Create a default SashWindowsMngr with one PageLit and one default SashModel.
	 * Set the current folder.
	 *
	 * @param diResource
	 * @return
	 */
	static public SashWindowsMngr createDefaultSashWindowsMngr() {
		SashWindowsMngr model;

		model = DiFactory.eINSTANCE.createSashWindowsMngr();

		// SashModel
		SashModel layout = createDefaultSashModel();

		model.setSashModel(layout);
		return model;
	}

	/**
	 * Lookup for the SashModel object in the resource.
	 *
	 * @param diResource
	 * @return The {@link DiSashModel} or null if not found.
	 */
	static public SashWindowsMngr lookupSashWindowsMngr(Resource diResource) {

		for (Object node : diResource.getContents()) {
			if (node instanceof SashWindowsMngr) {
				return (SashWindowsMngr) node;
			}
		}
		return null;
	}

	/**
	 * Lookup for the current selection TabFolder object in the resource.
	 *
	 * @param diResource
	 * @return The {@link TabFolder} or null if not found.
	 */
	static public TabFolder lookupCurrentTabFolderSelection(Resource diResource) {
		SashWindowsMngr lookupSashWindowsMngr = lookupSashWindowsMngr(diResource);
		if (lookupSashWindowsMngr != null) {
			SashModel sashModel = lookupSashWindowsMngr.getSashModel();
			if (sashModel != null) {
				return sashModel.getCurrentSelection();
			}
		}
		return null;
	}

	/**
	 * Gets the page reference of the specified eObject
	 *
	 * @param diResource
	 * @param eObject
	 *
	 * @return the page ref of eObject, null if not found
	 *         TODO This method use too low level mechanism for its implementation. Consider to move it in a
	 *         more appropriate class. Furthermore, some similar methods already exist. Can't we use them
	 *         instead ?
	 *
	 * @deprecated Should use IPageMngr instead.
	 */
	@Deprecated
	static public PageRef getPageRef(Resource diResource, EObject eObject) {
		SashWindowsMngr windowsMngr = lookupSashWindowsMngr(diResource);
		if (windowsMngr != null && windowsMngr.getPageList() != null) {

			for (PageRef pageRef : windowsMngr.getPageList().getAvailablePage()) {

				EObject emfPageIdentifier = pageRef.getEmfPageIdentifier();
				if (eObject != null && eObject.equals(emfPageIdentifier)) {
					return pageRef;
				}
			}
		}
		return null;
	}

	/**
	 * Adds page to the page list of the sash windows manager
	 *
	 * @param diResource
	 * @param pageRef
	 * @throws SashEditorException
	 *             TODO This method use too low level mechanism for its implementation. Consider to move it in a
	 *             more appropriate class. Furthermore, some similar methods already exist. Can't we use them
	 *             instead ?
	 * @deprecated Should use IPageMngr instead.
	 */
	@Deprecated
	static public void addPageToPageList(Resource diResource, PageRef pageRef) throws SashEditorException {
		// Do nothing. The page list is not used anymore
	}

	/**
	 * Adds page to the page list of the sash windows manager.
	 *
	 * @param pageRef
	 * @param windowsMngr
	 * @throws SashEditorException
	 *             TODO This method use too low level mechanism for its implementation. Consider to move it in a
	 *             more appropriate class. Furthermore, some similar methods already exist. Can't we use them
	 *             instead ?
	 *
	 * @deprecated Should use IPageMngr instead.
	 */
	@Deprecated
	static public void addPageToPageList(SashWindowsMngr windowsMngr, PageRef pageRef) throws SashEditorException {
		// Do nothing. The page list is not used anymore
	}

	/**
	 * Adds page to tab folder.
	 *
	 * @param windowsMngr
	 * @param pageRef
	 * @throws SashEditorException
	 *             TODO This method use too low level mechanism for its implementation. Consider to move it in a
	 *             more appropriate class. Furthermore, some similar methods already exist. Can't we use them
	 *             instead ?
	 *
	 * @deprecated Should use IPageMngr instead.
	 */
	@Deprecated
	static public void addPageToTabFolder(SashWindowsMngr windowsMngr, PageRef pageRef) throws SashEditorException {

		// Check parameters
		if (pageRef == null || pageRef.getPageIdentifier() == null) {
			throw new SashEditorException("Unable to add the page to the tab folder: parameters are null"); //$NON-NLS-1$
		}

		SashModel sashModel = windowsMngr.getSashModel();
		if (sashModel == null) {
			throw new SashEditorException("Unable to add the page to the tab folder: can't find SashModel"); //$NON-NLS-1$
		}

		// Get the currently active folder in order to add the page.
		TabFolder tabFolder = sashModel.getCurrentSelection();
		if (tabFolder == null) {
			throw new SashEditorException("Unable to add the page to the tab folder: No active folder"); //$NON-NLS-1$
		}

		tabFolder.addPage(pageRef.getPageIdentifier());
	}

	/**
	 * Obtains a command that will close all of the pages in the given {@code pageManager} that reference the specified {@code pageIdentifier},
	 * regardless of whether they still reference that identifier at the time of execution (this is the "memoization").
	 *
	 * @param domain
	 *            the editing domain in which the command will be executed
	 * @param pageManager
	 *            the page manager for which to construct the command
	 * @param pageIdentifier
	 *            the identifier of the page(s) to be removed
	 *
	 * @return the memoized close-all-pages command, or {@code null} if there are no pages to close
	 * @since 1.2
	 */
	public static Command getMemoizedCloseAllPagesCommand(final TransactionalEditingDomain domain, final IPageManager pageManager, final Object pageIdentifier) {
		Command result = null;

		final BasicPageManagerImpl pageMan = (BasicPageManagerImpl) pageManager;

		final Map<PageRef, TabFolder> pages = execute(pageMan, new SashModelOperation<Map<PageRef, TabFolder>>() {

			@Override
			public Map<PageRef, TabFolder> execute(SashWindowsMngr sashWindowsManager) {
				return new DiSwitch<Map<PageRef, TabFolder>>() {

					private Map<PageRef, TabFolder> pages = new HashMap<PageRef, TabFolder>();

					@Override
					public Map<PageRef, TabFolder> defaultCase(EObject object) {
						for (EObject next : object.eContents()) {
							doSwitch(next);
						}
						return pages;
					}

					@Override
					public Map<PageRef, TabFolder> casePageRef(PageRef object) {
						if (object.getPageIdentifier() == pageIdentifier) {
							pages.put(object, object.getParent());
						}
						return pages;
					}
				}.doSwitch(sashWindowsManager.getSashModel());
			}
		});

		if (!pages.isEmpty()) {
			final SashModelOperation<Void> removeOp = new SashModelOperation<Void>() {

				@Override
				public Void execute(SashWindowsMngr sashWindowsManager) {
					SashModel sashModel = sashWindowsManager.getSashModel();
					for (Map.Entry<PageRef, TabFolder> next : pages.entrySet()) {
						PageRef page = next.getKey();
						TabFolder folder = next.getValue();

						folder.getChildren().remove(page);
						sashModel.removeEmptyFolder(folder);
					}
					return null;
				}
			};

			result = new RecordingCommand(domain, "Remove Editor Page(s)") { //$NON-NLS-1$

				@Override
				protected void doExecute() {
					DiUtils.execute(pageMan, removeOp);
				}
			};
		}

		return result;
	}

	private static <T> T execute(BasicPageManagerImpl pageManager, SashModelOperation<T> sashOperation) {
		try {
			return pageManager.execute(sashOperation);
		} catch (IllegalAccessException e) {
			// Won't happen because this is our own operation
			throw new IllegalAccessError(e.getLocalizedMessage());
		}
	}

}
