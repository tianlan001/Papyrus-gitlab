/*****************************************************************************
 * Copyright (c) 2014, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo - Bug 548998
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.ModelsReader;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.Activator;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.utils.TransactionHelper;
import org.eclipse.papyrus.infra.core.sashwindows.di.SashWindowsMngr;

/**
 * Transactional implementation of the PageManager.
 *
 * It runs all commands in write transactions on the editing domain, without using the CommandStack
 *
 * @author Camille Letavernier
 *
 */
public class TransactionalPageManagerImpl extends PageManagerImpl {

	private EditingDomain editingDomain;

	public TransactionalPageManagerImpl(SashWindowsMngr diSashModel, ContentChangedEventProvider contentChangedEventProvider) {
		super(diSashModel, contentChangedEventProvider);

		this.editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(diSashModel);
	}

	public TransactionalPageManagerImpl(SashWindowsMngr diSashModel, ContentChangedEventProvider contentChangedEventProvider, ICurrentFolderAndPageMngr folderAndPageMngr) {
		super(diSashModel, contentChangedEventProvider, folderAndPageMngr);

		this.editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(diSashModel);
	}

	protected EditingDomain getEditingDomain() {
		return editingDomain;
	}

	@Override
	public void removePage(final Object pageIdentifier) {
		try {
			TransactionHelper.run(getEditingDomain(), new Runnable() {

				@Override
				public void run() {
					TransactionalPageManagerImpl.super.removePage(pageIdentifier);
				}
			});
		} catch (Exception ex) {
			Activator.log.error(ex);
		}
	}

	@Override
	public void closePage(final Object pageIdentifier) {
		try {
			TransactionHelper.run(getEditingDomain(), new Runnable() {

				@Override
				public void run() {
					TransactionalPageManagerImpl.super.closePage(pageIdentifier);
				}
			});
		} catch (Exception ex) {
			Activator.log.error(ex);
		}
	}

	@Override
	public void closeAllOpenedPages() {
		try {
			TransactionHelper.run(getEditingDomain(), new Runnable() {

				@Override
				public void run() {
					TransactionalPageManagerImpl.super.closeAllOpenedPages();
				}
			});
		} catch (Exception ex) {
			Activator.log.error(ex);
		}
	}

	@Override
	public void closeOtherPages(final Object pageIdentifier) {
		try {
			TransactionHelper.run(getEditingDomain(), new Runnable() {

				@Override
				public void run() {
					TransactionalPageManagerImpl.super.closeOtherPages(pageIdentifier);
				}
			});
		} catch (Exception ex) {
			Activator.log.error(ex);
		}
	}

	@Override
	public void openPage(final Object pageIdentifier) {
		try {
			TransactionHelper.run(getEditingDomain(), new Runnable() {

				@Override
				public void run() {
					TransactionalPageManagerImpl.super.openPage(pageIdentifier);
				}
			});
		} catch (Exception ex) {
			Activator.log.error(ex);
		}
	}

	@Override
	public void openPage(final Object pageIdentifier, final String editorID) {
		try {
			TransactionHelper.run(getEditingDomain(), new Runnable() {

				@Override
				public void run() {
					TransactionalPageManagerImpl.super.openPage(pageIdentifier, editorID);
				}
			});
		} catch (Exception ex) {
			Activator.log.error(ex);
		}
	}

	@Override
	public void closeAllOpenedPages(final Object pageIdentifier) {
		try {
			TransactionHelper.run(getEditingDomain(), new Runnable() {

				@Override
				public void run() {
					TransactionalPageManagerImpl.super.closeAllOpenedPages(pageIdentifier);
				}
			});
		} catch (Exception ex) {
			Activator.log.error(ex);
		}
	}


	/**
	 * @see org.eclipse.papyrus.infra.core.sashwindows.di.service.BasicPageManagerImpl#allPages()
	 *
	 * @return
	 */
	@Override
	public List<Object> allPages() {
		if (false == isLegacyMode()) {
			final ResourceSet set = this.editingDomain.getResourceSet();
			if (set instanceof ModelSet) {
				final ModelSet modelSet = (ModelSet) set;
				final URI uri = modelSet.getURIWithoutExtension();
				final ModelsReader reader = new ModelsReader();
				List<Object> result = new ArrayList<>();
				for (URI currentURI : reader.getKnownModelURIs(uri)) {
					final String fileExtension = currentURI.fileExtension();
					// this filter on uml is not the best solution, but we currently don't have other way to get all pages properly
					if (!"uml".equals(fileExtension)) {// to avoid to cross all UML contents for nothing (and avoid time consumption) //$NON-NLS-1$
						List<Resource> notationResources = getResources(fileExtension);
						for (Resource notationResource : notationResources) {
							for (EObject content : notationResource.getContents()) {
								if (isPage(content)) {
									result.add(content);
								}
							}
						}
					}
				}
				return result;
			}
		}
		return super.allPages();
	}
}
