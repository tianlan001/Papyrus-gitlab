/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.views.references.utils;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.services.labelprovider.service.impl.LabelProviderServiceImpl;
import org.eclipse.papyrus.views.references.views.ReferencesView;

/**
 * Class used to display all the references of an item on the 'References' view.
 */
public class HandleReferences {

	/**
	 * Singleton instance of HandleReferences.
	 */
	public static HandleReferences INSTANCE = new HandleReferences();

	/**
	 * The object to search.
	 */
	private EObject objectToSearch;

	/**
	 * The Reference View used to display the result.
	 */
	private ReferencesView referencesView;

	/**
	 * The LabelProviderService.
	 */
	private LabelProviderService labelProviderService;

	/**
	 * Get the LabelProviderService.
	 *
	 * @return The LabelProviderService.
	 */
	public LabelProviderService getLabelProviderService() {
		if (null == labelProviderService) {
			labelProviderService = new LabelProviderServiceImpl();
		}
		return labelProviderService;
	}

	/**
	 * Open the view 'References' and launch the search of the references of the
	 * object.
	 *
	 * @param structuredSelection
	 *            The objects to search.
	 */
	public void openViewAndLaunchSearch(final IStructuredSelection structuredSelection) {
		referencesView = ReferencesViewUtil.openReferencesView();
		if (1 == structuredSelection.size()) {
			final Object firstElement = structuredSelection.getFirstElement();
			if (null != firstElement) {
				this.objectToSearch = EMFHelper.getEObject(firstElement);
			}
		}
		launchFindReferences();
	}

	/**
	 * Get the view 'References' and launch the search of the references of the
	 * object.
	 *
	 * @param objectToSearch
	 *            The object to search.
	 */
	public void getViewAndLaunch(final EObject objectToSearch) {
		referencesView = ReferencesViewUtil.getOpenedReferencesView();
		this.objectToSearch = objectToSearch;
		launchFindReferences();
	}

	/**
	 * Launch the search of the objects parameter.
	 */
	public void launchFindReferences() {
		if (null != referencesView) {
			if (null != this.objectToSearch) {
				ECrossReferenceAdapter crossReferenceAdapter = ECrossReferenceAdapter
						.getCrossReferenceAdapter(this.objectToSearch);
				if (null != crossReferenceAdapter) {
					final Collection<Setting> results = crossReferenceAdapter
							.getNonNavigableInverseReferences(this.objectToSearch);
					referencesView.showResult(results, this.objectToSearch);
				}
			}
		}
	}
}
