/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.search.ui.providers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.papyrus.views.search.results.AbstractResultEntry;
import org.eclipse.papyrus.views.search.utils.MatchUtils;
import org.eclipse.search.ui.text.AbstractTextSearchResult;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Stereotype;

/**
 *
 * Content provider used by the filter dialog to list available types in a search result
 *
 */
public class FilterTypeContentProvider implements ITreeContentProvider {

	public void dispose() {

	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

	public Object[] getElements(Object inputElement) {
		Set<Object> results = new HashSet<Object>();

		if (inputElement instanceof AbstractTextSearchResult) {
			Collection<AbstractResultEntry> matches = MatchUtils.getMatches((AbstractTextSearchResult) inputElement, Element.class, false);
			Set<Stereotype> stereotypesToAdd = new HashSet<Stereotype>();
			
			for (AbstractResultEntry match : matches) {
				// Add meta-classes
				results.add(((EObject) match.elementToCheckFilterFor()).eClass());
				
				// Add stereotypes
				for (Stereotype appliedStereotype : ((Element) match.elementToCheckFilterFor()).getAppliedStereotypes()) {
					boolean exists = false;

					for (Stereotype existingStereotype : stereotypesToAdd) {
						if (EcoreUtil.getURI(existingStereotype).equals(EcoreUtil.getURI(appliedStereotype))) {
							exists = true;
							break;
						}
					}
					
					if (!exists) {
						stereotypesToAdd.add(appliedStereotype);
					}
				}
			}
			
			results.addAll(stereotypesToAdd);
		}

		return results.toArray();
	}

	public Object[] getChildren(Object parentElement) {
		Set<Object> results = new HashSet<Object>();

		return results.toArray();
	}

	public Object getParent(Object element) {
		return null;
	}

	public boolean hasChildren(Object element) {

		return false;
	}

}
