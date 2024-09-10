/*****************************************************************************
 * Copyright (c) 2013, 2016 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus (CEA LIST) - Fix leaking of all UML models in search results
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.search.ui.query;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.uml.search.ui.Activator;
import org.eclipse.papyrus.uml.search.ui.results.PapyrusSearchResult;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.uml2.uml.Element;

/**
 *
 * Papyrus specific search query
 *
 */
public abstract class AbstractPapyrusQuery implements ISearchQuery {

	protected SubMonitor progressMonitor;

	/**
	 * Getter for the text query
	 *
	 * @return the the query text
	 */
	public abstract String getSearchQueryText();

	public boolean isCaseSensitive() {
		
		return false;
	}

	public boolean isRegularExpression() {
		
		return false;
	}

	//
	// Nested types
	//

	public static final class Empty extends AbstractPapyrusQuery {
		public static final Empty INSTANCE = new Empty();

		private Empty() {
			super();
		}

		public IStatus run(IProgressMonitor monitor) throws OperationCanceledException {
			return Status.OK_STATUS;
		}

		public String getLabel() {
			return "Empty Papyrus Search";
		}

		public boolean canRerun() {
			return true;
		}

		public boolean canRunInBackground() {
			return true;
		}

		public PapyrusSearchResult getSearchResult() {
			return new PapyrusSearchResult(this);
		}

		@Override
		public String getSearchQueryText() {
			return "";
		}
	}

	/**
	 * Get views in which the UML element is shown
	 * 
	 * @param element
	 * @return
	 */

	protected List<View> getViews(Element element) {
		if (element == null) {
			return null;
		}

		IPageManager pageManager;
		try {
			pageManager = ServiceUtilsForEObject.getInstance().getService(IPageManager.class, element);
		} catch (ServiceException e) {
			Activator.log.error(e);
			return null;
		}

		List<View> viewsToSelect = new LinkedList<View>();

		try {
			for (Object page : pageManager.allPages()) {
				try {
					if (page instanceof View) {
						View view = (View) page;
						TreeIterator<EObject> allViews = view.eAllContents();
						while (allViews.hasNext()) {
							EObject next = allViews.next();
							if (!(next instanceof View)) {
								allViews.prune();
								continue;
							}

							View nextView = (View) next;
							if (element.equals(nextView.getElement())) {
								viewsToSelect.add(nextView);
								allViews.prune();
							}
						}
					}
				} catch (Exception e) {
					Activator.log.error(e);
					return null;
				}
			}
		} catch (Exception e) {
			Activator.log.error(e);
			return null;
		}

		return viewsToSelect;
	}
}
