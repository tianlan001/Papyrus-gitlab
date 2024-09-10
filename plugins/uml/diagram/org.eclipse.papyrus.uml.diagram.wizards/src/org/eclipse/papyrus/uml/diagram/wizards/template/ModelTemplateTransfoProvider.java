/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Thibault Le Ouay (Sherpa Engineering) t.leouay@sherpa-eng.com  - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.wizards.template;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Thibault
 *
 */
public class ModelTemplateTransfoProvider extends AbstractModelTemplateContentProvider {
	// This class is used to populate SelectModelTemplateComposite's templateTableViewer

	/**
	 * @see org.eclipse.papyrus.uml.diagram.wizards.template.ModelTemplatesContentProvider#getElements(java.lang.Object)
	 *
	 * @param inputElement
	 * @return
	 */

	@Override
	public Object[] getElements(Object inputElement) {
		// InputElement is the value of the selected language (radio button) from the SelectDiagramCategoryPage, e.g. "uml"

		if (inputElement instanceof Object[]) {
			List<ModelTemplateDescription> result = new ArrayList<ModelTemplateDescription>();

			for (Object next : (Object[]) inputElement) {
				if (next instanceof String) {
					// Therefore diagramCategory is of the same type
					String diagramCategory = (String) next;
					for (ModelTemplateDescription template : getTemplatesDescription()) {

						// For the QVT transformation, a link to the transformation model was given to the element and therefore it had the corresponding transfoURI
						// The models Templates do not have this information and that was how they were sorted in the tableViewer/comboBox
						// if ((diagramCategory == null || diagramCategory.equals(template.getLanguage())) && template.getTransfoURI() != null) {
						if ((diagramCategory == null || diagramCategory.equals(template.getLanguage())) && template.getDi_path() == null) {
							result.add(template);
						}
					}
				}
			}
			return result.toArray();
		}

		return new Object[0];
	}
}
