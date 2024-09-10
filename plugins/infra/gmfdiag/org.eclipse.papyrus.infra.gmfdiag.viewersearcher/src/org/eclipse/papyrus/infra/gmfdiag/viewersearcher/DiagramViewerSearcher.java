/*****************************************************************************
 * Copyright (c) 2013, 2014 CEA LIST and others.
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
 *  Christian W. Damus (CEA) - bug 426732
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.viewersearcher;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramUtils;
import org.eclipse.papyrus.infra.services.viewersearch.impl.AbstractViewerSearcher;

/**
 *
 * Contributes to the viewersearchservice by providing the ability to search viewers in GMF diagrams
 *
 */
public class DiagramViewerSearcher extends AbstractViewerSearcher {

	/**
	 *
	 * @see org.eclipse.papyrus.infra.services.viewersearch.impl.AbstractViewerSearcher#getViewers(java.util.Collection, java.util.Collection)
	 *
	 * @param modelElements
	 * @param modelSets
	 * @return
	 */
	@Override
	public Map<Object, Map<Object, Object>> getViewers(Collection<Object> modelElements, Collection<ModelSet> modelSets) {

		Map<Object, Map<Object, Object>> results = new HashMap<Object, Map<Object, Object>>();

		for (ModelSet modelSet : modelSets) {

			Map<EObject, Collection<Setting>> references = crossReference(modelElements, modelSet);

			for (Object semanticElement : references.keySet()) {
				for (Setting setting : references.get(semanticElement)) {
					if (setting.getEObject() instanceof View) {
						// Diagram diagram = ((View)setting.getEObject()).getDiagram();
						Map<Object, Object> viewMappings;

						if (results.containsKey(modelSet)) {
							viewMappings = results.get(modelSet);
						} else {
							viewMappings = new HashMap<Object, Object>();
						}
						viewMappings.put(setting.getEObject(), semanticElement);

						results.put(modelSet, viewMappings);
					}
				}
			}
		}

		return results;
	}

	private Map<EObject, Collection<Setting>> crossReference(Collection<?> modelElements, ModelSet modelSet) {
		Map<EObject, Collection<Setting>> result;

		final ECrossReferenceAdapter xrefs = ECrossReferenceAdapter.getCrossReferenceAdapter(modelSet);
		if (xrefs == null) {
			// one-off usage cross referencer
			result = EcoreUtil.UsageCrossReferencer.findAll(modelElements, modelSet);
		} else {
			result = new HashMap<EObject, Collection<Setting>>();
			for (Object next : modelElements) {
				if (next instanceof EObject) {
					EObject eObject = (EObject) next;
					result.put(eObject, xrefs.getInverseReferences(eObject));
				}
			}
		}

		return result;
	}

	/**
	 * @see org.eclipse.papyrus.infra.services.viewersearch.impl.AbstractViewerSearcher#getViewersInCurrentModel(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, boolean, boolean)
	 *
	 * @param element
	 * @param container
	 * @param pagesOnly
	 * @param openPagesOnly
	 * @return
	 */
	@Override
	public List<Object> getViewersInCurrentModel(EObject element, EObject container, boolean pagesOnly, boolean openPagesOnly) {
		List<Object> objectsToSelect = new LinkedList<Object>();
		
		if (element == null && container == null) {
			return objectsToSelect;
		}

		IPageManager pageManager;
		try {
			if (element == null) {
				pageManager = ServiceUtilsForEObject.getInstance().getServiceRegistry(container).getService(IPageManager.class);
			} else {
				pageManager = ServiceUtilsForEObject.getInstance().getServiceRegistry(element).getService(IPageManager.class);
			}
		} catch (Exception ex) {
			return objectsToSelect;
		}
		
		try {
			for (Object page : pageManager.allPages()) {
				if (openPagesOnly && !pageManager.isOpen(page)) {
					continue;
				}
				
				if (page instanceof Diagram) {
					// Container checks
					if (container != null) {
						EObject owner = DiagramUtils.getOwner((Diagram) page);
						if (!owner.equals(container)) {
							continue;
						}
					}
					
					if (element == null) {
						objectsToSelect.add(page);
						continue;
					}
					
					try {
						TreeIterator<EObject> allViews = ((Diagram) page).eAllContents();
						while (allViews.hasNext()) {
							EObject next = allViews.next();
							if (!(next instanceof View)) {
								allViews.prune();
								continue;
							}

							View nextView = (View) next;
							if (element.equals(nextView.getElement())) {
								if (pagesOnly) {
									objectsToSelect.add(page);
								} else {
									objectsToSelect.add(nextView);
								}
								break;
							}
						}
					} catch (Exception e) {
						return objectsToSelect;
					}
				}
			}
		} catch (Exception e) {
			return objectsToSelect;
		}
		
		return objectsToSelect;
	}
}
