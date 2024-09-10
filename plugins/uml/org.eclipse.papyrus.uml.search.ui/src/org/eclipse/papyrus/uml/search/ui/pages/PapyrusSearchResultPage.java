/*****************************************************************************
 * Copyright (c) 2013, 2023 CEA LIST.
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
 *  CEA LIST - Initial API and implementation
 *  Pauline DEVILLE (CEA LIST) <pauline.deville@cea.fr> - Bug 581217
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.search.ui.pages;

import java.util.Set;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.services.navigation.service.NavigationService;
import org.eclipse.papyrus.infra.services.openelement.service.OpenElementService;
import org.eclipse.papyrus.uml.search.ui.Activator;
import org.eclipse.papyrus.uml.search.ui.Messages;
import org.eclipse.papyrus.uml.search.ui.actions.FilterTypesAction;
import org.eclipse.papyrus.uml.search.ui.providers.ResultContentProvider;
import org.eclipse.papyrus.uml.search.ui.providers.ResultLabelProvider;
import org.eclipse.papyrus.views.search.results.AbstractResultEntry;
import org.eclipse.papyrus.views.search.scope.IScopeEntry;
import org.eclipse.papyrus.views.search.utils.MatchUtils;
import org.eclipse.search.ui.IContextMenuConstants;
import org.eclipse.search.ui.text.AbstractTextSearchViewPage;
import org.eclipse.search.ui.text.Match;
import org.eclipse.ui.PartInitException;
import org.eclipse.uml2.uml.Element;

/**
 *
 * Papyrus specific search results page
 *
 * @since 2.0
 *
 */
public class PapyrusSearchResultPage extends AbstractTextSearchViewPage {

	private static final String ID_VIEW_MODELEXPLORER = "org.eclipse.papyrus.views.modelexplorer.navigation.target"; //$NON-NLS-1$

	ResultContentProvider fContentProvider = null;

	private IAction fFiltertypesAction = null;

	private static final int DEFAULT_ELEMENT_LIMIT = 1000;

	public PapyrusSearchResultPage() {

		super(FLAG_LAYOUT_TREE);
		setElementLimit(Integer.valueOf(DEFAULT_ELEMENT_LIMIT));
		fFiltertypesAction = new FilterTypesAction(this);
	}



	@Override
	public Match[] getDisplayedMatches(Object element) {

		Set<AbstractResultEntry> results = MatchUtils.getMatches(this.getInput(), true);

		if (element instanceof AbstractResultEntry) {
			AbstractResultEntry resultEntry = (AbstractResultEntry) element;

			if (results.contains(resultEntry)) {

				return new Match[] { resultEntry };

			}
			return new Match[0];
		} else {
			return super.getDisplayedMatches(element);
		}
	}

	@Override
	public int getDisplayedMatchCount(Object element) {

		if (element instanceof AbstractResultEntry) {
			AbstractResultEntry resultEntry = (AbstractResultEntry) element;

			Set<AbstractResultEntry> results = MatchUtils.getMatches(this.getInput(), true);
			if (results.contains(resultEntry)) {
				return 1;
			}

			return 0;
		} else {
			return super.getDisplayedMatchCount(element);
		}
	}

	@Override
	protected void fillToolbar(IToolBarManager tbm) {
		super.fillToolbar(tbm);
		tbm.appendToGroup(IContextMenuConstants.GROUP_REMOVE_MATCHES, fFiltertypesAction);
	}

	@Override
	protected void handleOpen(OpenEvent event) {
		ISelection selection = event.getSelection();
		if (!selection.isEmpty()) {
			if (selection instanceof IStructuredSelection) {
				Object firstElement = ((IStructuredSelection) selection).getFirstElement();

				if (firstElement instanceof AbstractResultEntry) {
					AbstractResultEntry resultEntry = (AbstractResultEntry) firstElement;
					IScopeEntry scopeEntry = (IScopeEntry) resultEntry.getElement();
					if (scopeEntry != null && scopeEntry.getServicesRegistry() != null) {
						Object source = resultEntry.getSource();
						if (source instanceof Element) { // UML element => use NavigationService to navigate to Model Explorer
							try {
								NavigationService navigationService = ServiceUtilsForEObject.getInstance().getService(NavigationService.class, (Element) source);
								navigationService.navigate(source, ID_VIEW_MODELEXPLORER);
							} catch (ServiceException e) {
								Activator.log.error(e);
							}
						} else { // Anything else => Let the OpenElementService handle it
							try {
								OpenElementService service = scopeEntry.getServicesRegistry().getService(OpenElementService.class);
								resultEntry.openElement(service);
							} catch (ServiceException e) {
								// Activator.log.error(Messages.PapyrusSearchResultPage_0 + resultEntry.elementToOpen(), e);
							} catch (PartInitException e) {
								Activator.log.error(Messages.PapyrusSearchResultPage_1, e);
							}
						}
					}

				}

			}

		}

		// super.handleOpen(event);
	}

	@Override
	protected void elementsChanged(Object[] objects) {
		if (fContentProvider != null) {
			fContentProvider.elementsChanged(objects);
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	protected void evaluateChangedElements(Match[] matches, @SuppressWarnings("rawtypes") Set changedElements) {
		for (int i = 0; i < matches.length; i++) {
			changedElements.add(matches[i]);
		}
	}

	@Override
	protected void clear() {
		if (fContentProvider != null) {
			fContentProvider.clear();
		}
	}

	@Override
	protected void configureTreeViewer(TreeViewer viewer) {
		viewer.setContentProvider(new ResultContentProvider(this, viewer));
		viewer.setLabelProvider(new ResultLabelProvider());
		fContentProvider = (ResultContentProvider) viewer.getContentProvider();

	}

	@Override
	protected void configureTableViewer(TableViewer viewer) {

	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
