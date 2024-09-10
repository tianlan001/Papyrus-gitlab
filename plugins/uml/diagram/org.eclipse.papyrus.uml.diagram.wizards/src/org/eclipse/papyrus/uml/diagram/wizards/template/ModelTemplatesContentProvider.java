/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *  Tatiana Fesenko (CEA LIST) - Initial API and implementation
 *	Saadia Dhouib (CEA LIST) - Implementation of loading diagrams from template files  (.uml, .di , .notation)
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.wizards.template;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.infra.widgets.providers.UnsetObject;

/**
 * The Class ModelTemplatesContentProvider.
 */
public class ModelTemplatesContentProvider extends AbstractModelTemplateContentProvider {
	// This class is used to populate SelectModelTemplateComposite's singleTemplateCombo

	/**
	 * Gets the elements.
	 *
	 * @param inputElement
	 *            the input element
	 * @return the elements
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		// InputElement is the value of the selected language (radio button) from the SelectDiagramCategoryPage, e.g. "uml"

		if (inputElement instanceof Object[]) {
			// List<ModelTemplateDescription> result = new ArrayList<ModelTemplateDescription>();
			List<Object> result = new ArrayList<Object>();

			for (Object next : (Object[]) inputElement) {
				if (next instanceof String) {
					// Therefore diagramCategory is of the same type
					String diagramCategory = (String) next;
					for (ModelTemplateDescription template : getTemplatesDescription()) {
						// For the QVT transformation, a link to the transformation model was given to the element and therefore it had the corresponding transfoURI
						// The models Templates do not have this information and that was how they were sorted in the tableViewer/comboBox
						// if ((diagramCategory == null || diagramCategory.equals(template.getLanguage())) && template.getTransfoURI() == null) {
						if ((diagramCategory == null || diagramCategory.equals(template.getLanguage())) && template.getDi_path() != null) {
							result.add(template);
						}
					}
				}
			}

			// Empty element to enable the empty selection even after a user selection
			result.add(0, UnsetObject.instance);
			return result.toArray();
		}

		return new Object[0];
	}

}
