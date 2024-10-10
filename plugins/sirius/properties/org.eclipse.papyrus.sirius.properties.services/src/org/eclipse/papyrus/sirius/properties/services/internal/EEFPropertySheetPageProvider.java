/*****************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *  Obeo - Other enhancements
 *****************************************************************************/
package org.eclipse.papyrus.sirius.properties.services.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.infra.ui.editor.CoreMultiDiagramEditor;
import org.eclipse.papyrus.views.modelexplorer.ModelExplorerPageBookView;
import org.eclipse.papyrus.views.properties.services.IPropertySheetPageProvider;
import org.eclipse.ui.views.properties.IPropertySheetPage;

/**
 * Provide EEFPropertySheetPage.
 */
public class EEFPropertySheetPageProvider implements IPropertySheetPageProvider {

	/**
	 * ID of the view matching with Sirius Id to display default tabs (Semantic, appearance, style...) on sirius diagrams
	 */
	private static final String DIAGRAM_VIEW_ID = "org.eclipse.sirius.diagram.ui"; //$NON-NLS-1$

	/**
	 * ID of the view matching with ModelExplorer Id to display advanced tab.
	 */
	private static final String TREE_OUTLINE_VIEW_ID = "TreeOutlinePage";//$NON-NLS-1$

	/**
	 * @see org.eclipse.papyrus.views.properties.services.IPropertySheetPageProvider#getRendererID()
	 *
	 * @return
	 */
	@Override
	public String getRendererID() {
		return "EEF"; //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.papyrus.views.properties.services.IPropertySheetPageProvider#provides(java.lang.Object, java.lang.String)
	 *
	 * @param realContributor
	 *            the caller wanting a Property View (the Papyrus editor, or the ModelExplorer for example
	 *
	 * @param viewID
	 *            the id of the caller
	 * @return <code>true</code> if propertySheetPage should be displayed, <code>false</code> otherwise.
	 */
	@Override
	public boolean provides(final Object realContributor, final String viewID) {
		// TODO VLO : find a better way for that ?
		if (realContributor instanceof CoreMultiDiagramEditor || realContributor instanceof ModelExplorerPageBookView) {
			return true;
		}
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.views.properties.services.IPropertySheetPageProvider#createPropertyPage(java.lang.Object, java.lang.String)
	 *
	 * @param realContributor
	 *            the caller wanting a Property View (the Papyrus editor, or the ModelExplorer for example
	 *
	 * @param viewID
	 *            the id of the caller
	 * @return the property sheet page to display in Properties view.
	 */
	@Override
	public IPropertySheetPage createPropertyPage(final Object realContributor, final String viewID) {
		if (provides(realContributor, viewID)) {
			List<String> localViewIDs = new ArrayList<>();
			if (realContributor instanceof CoreMultiDiagramEditor) {
				localViewIDs.add(TREE_OUTLINE_VIEW_ID);
				localViewIDs.add(DIAGRAM_VIEW_ID);
			} else if (realContributor instanceof ModelExplorerPageBookView) {
				localViewIDs.add(TREE_OUTLINE_VIEW_ID);
			} else {
				localViewIDs.add(viewID);
			}
			return new PapyrusEEFTabbedPropertySheetPage(new CustomSiriusContributorWrapper(realContributor, localViewIDs));
		}
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.views.properties.services.IPropertySheetPageProvider#dispose(org.eclipse.ui.views.properties.IPropertySheetPage)
	 *
	 * @param propertySheetPage
	 */
	@Override
	public void dispose(final IPropertySheetPage propertySheetPage) {
		// nothing to do for EEF
	}

}
