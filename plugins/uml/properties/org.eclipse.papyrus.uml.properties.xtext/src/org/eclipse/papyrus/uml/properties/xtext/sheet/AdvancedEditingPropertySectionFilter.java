/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.properties.xtext.sheet;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.Activator;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.ICustomDirectEditorConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.IDirectEditorConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.utils.DirectEditorsUtil;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.utils.IDirectEditorsIds;

public class AdvancedEditingPropertySectionFilter implements IFilter {

	public boolean select(Object toTest) {
		EObject semanticElement = null;
		if (toTest instanceof IAdaptable) {
			semanticElement = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
		}
		else if (toTest instanceof GraphicalEditPart) {
			GraphicalEditPart part = (GraphicalEditPart) toTest;
			semanticElement = part.resolveSemanticElement();
		}
		if (semanticElement != null) {
			IPreferenceStore store = Activator.getDefault().getPreferenceStore();
			String key = IDirectEditorsIds.EDITOR_FOR_ELEMENT
					+ semanticElement.eClass().getInstanceClassName();

			String languagePreferred = store.getString(key);

			if (languagePreferred != null && !languagePreferred.equals("")) {
				IDirectEditorConfiguration configuration = DirectEditorsUtil.findEditorConfiguration(
						languagePreferred, semanticElement);
				return configuration instanceof ICustomDirectEditorConfiguration;
			}
		}
		return false;
	}
}
