/*****************************************************************************
 * Copyright (c) 2013-2014, 2019 Cedric Dumoulin, CEA, and others.
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
 *  Christian W. Damus (CEA) - bug 433371
 *  Céline Janssens (ALL4TEC) celine.janssens@all4tec.net - Bug 415638
 *  Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Bug 440754
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 550569
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.internal;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.IOpenable;
import org.eclipse.papyrus.infra.core.sasheditor.utils.PageAddValidatorManager;
import org.eclipse.papyrus.infra.core.sashwindows.di.PageRef;
import org.eclipse.papyrus.infra.core.sashwindows.di.SashWindowsMngr;
import org.eclipse.papyrus.infra.core.sashwindows.di.TabFolder;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.BasicPageManagerImpl;
import org.eclipse.swt.widgets.Display;


/**
 * This class provide high level method to manage the Sash pages through its DI
 * implementation.
 *
 * @author cedric dumoulin
 *
 */
public class PageManagerImpl extends BasicPageManagerImpl {

	/**
	 * An object used to get the current folder, or to ask to set the
	 * active page.
	 * This is usually backuped by the SashWindowContainer. <br>
	 */
	private ICurrentFolderAndPageMngr folderAndPageMngr;

	private ContentChangedEventProvider contentChangedEventProvider;

	/**
	 * Constructor.
	 * Use a default {@link ICurrentFolderAndPageMngr} that alwayrs use the first
	 * folder as the current folder. Futhermore, the default implementation doesn't
	 * allow to set the active folder. <br>
	 * Suitable for tests
	 *
	 * @param diSashModel
	 *            The model onto which operation of this class act.
	 * @param contentChangedEventProvider
	 *            A class listening on changes on the internal model and delivering events to registered listeners. This implementation
	 *            need the class in order to deactivate event triggering during the operations.
	 *
	 */
	protected PageManagerImpl(SashWindowsMngr diSashModel, ContentChangedEventProvider contentChangedEventProvider) {
		super(diSashModel);

		this.contentChangedEventProvider = contentChangedEventProvider;
		this.folderAndPageMngr = new DefaultCurrentFolderAndPageMngr(diSashModel);
	}

	/**
	 * Constructor.
	 *
	 * @param diSashModel
	 *            the di sash model
	 * @param contentChangedEventProvider
	 *            the content changed event provider
	 * @param folderAndPageMngr
	 *            the folder and page mngr
	 */
	protected PageManagerImpl(SashWindowsMngr diSashModel, ContentChangedEventProvider contentChangedEventProvider, ICurrentFolderAndPageMngr folderAndPageMngr) {
		super(diSashModel);

		this.contentChangedEventProvider = contentChangedEventProvider;
		this.folderAndPageMngr = folderAndPageMngr;
	}

	@Override
	protected TabFolder getCurrentFolder() {
		return folderAndPageMngr.getCurrentFolder();
	}

	@Override
	protected boolean isLegacyMode() {
		// We are never in legacy mode even if there is a page list
		return false;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.core.sashwindows.di.service.BasicPageManagerImpl#canAddPage(java.lang.Object)
	 */
	@Override
	protected boolean canAddPage(final Object pageIdentifier) {
		return PageAddValidatorManager.getInstance().isValid(pageIdentifier);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.core.sashwindows.di.service.BasicPageManagerImpl#canAddPage(org.eclipse.papyrus.infra.core.sashwindows.di.PageRef)
	 */
	@Override
	protected boolean canAddPage(final PageRef page) {
		return PageAddValidatorManager.getInstance().isValid(page);
	}

	/**
	 * @since 2.0
	 */
	@Override
	protected boolean isPage(EObject content) {
		return Platform.getAdapterManager().getAdapter(content, IOpenable.class) != null;
	}

	@Override
	public void closePage(Object pageIdentifier) {
		// Suppress event notifications
		doExecute(() -> super.closePage(pageIdentifier));
	}

	@Override
	public void closeAllOpenedPages() {
		// Suppress event notifications
		doExecute(() -> super.closeAllOpenedPages());
	}

	@Override
	public void closeOtherPages(Object pageIdentifier) {
		// Suppress event notifications
		doExecute(() -> super.closeOtherPages(pageIdentifier));
	}

	@Override
	public void selectPage(final Object pageIdentifier) {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				folderAndPageMngr.setActivePage(pageIdentifier);
			}
		});
	}

	/**
	 * Sets the current folder and page mngr.
	 *
	 * @param currentFolderAndPageMngr
	 *            the new current folder and page mngr
	 */
	public void setCurrentFolderAndPageMngr(ICurrentFolderAndPageMngr currentFolderAndPageMngr) {
		this.folderAndPageMngr = currentFolderAndPageMngr;

	}

	@Override
	protected <T> T doExecute(SashModelOperation<T> sashModelOperation) {
		T result;

		final boolean deliver = contentChangedEventProvider.isDeliver();

		contentChangedEventProvider.setDeliver(false);
		try {
			result = super.doExecute(sashModelOperation);
		} finally {
			contentChangedEventProvider.setDeliver(deliver);
		}

		return result;
	}

	private void doExecute(Runnable sashModelOperation) {
		doExecute(ignored -> {
			sashModelOperation.run();
			return ignored;
		});
	}

}