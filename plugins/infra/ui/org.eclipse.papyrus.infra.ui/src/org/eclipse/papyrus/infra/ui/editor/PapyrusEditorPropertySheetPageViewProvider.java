/*****************************************************************************
 * Copyright (c) 2022 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent LORENZO (CEA LIST) - vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.editor;

import org.eclipse.papyrus.views.properties.services.IPropertySheetPageProvider;
import org.eclipse.ui.views.properties.IPropertySheetPage;

/**
 * Papyrus Editor Property Sheet Page Provider using the XWT Renderer
 *
 * @since 3.5
 */
public class PapyrusEditorPropertySheetPageViewProvider implements IPropertySheetPageProvider {

	/**
	 * @see org.eclipse.papyrus.views.properties.services.IPropertySheetPageProvider#getRendererID()
	 *
	 * @return
	 */
	@Override
	public String getRendererID() {
		return "XWT"; //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.papyrus.views.properties.services.IPropertySheetPageProvider#provides(java.lang.Object, java.lang.String)
	 *
	 * @param realContributor
	 * @param viewID
	 * @return
	 */
	@Override
	public boolean provides(final Object realContributor, final String viewID) {
		return realContributor instanceof CoreMultiDiagramEditor;
	}

	/**
	 * @see org.eclipse.papyrus.views.properties.services.IPropertySheetPageProvider#createPropertyPage(java.lang.Object, java.lang.String)
	 *
	 * @param realContributor
	 * @param viewID
	 * @return
	 */
	@Override
	public IPropertySheetPage createPropertyPage(final Object realContributor, final String viewID) {
		if (realContributor instanceof CoreMultiDiagramEditor) {
			return new MultiDiagramPropertySheetPage((CoreMultiDiagramEditor) realContributor);
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
		propertySheetPage.dispose();
	}

}
