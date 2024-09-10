/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.emf.providers.strategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.ui.emf.utils.ProviderHelper;
import org.eclipse.papyrus.infra.widgets.providers.IAdaptableContentProvider;
import org.eclipse.papyrus.infra.widgets.strategy.ProviderBasedBrowseStrategy;
import org.eclipse.swt.widgets.TreeItem;

/**
 * A TreeBrowseStrategy based on the semantic model.
 * It can also retrieve a semantic element in a MoDisco tree, when the tree structure is close
 * to the semantic one (With optional EReferences).
 *
 * Container1::Container2::EObject1 can be retrieved in a Tree representing
 * Container1::(Reference1)::Container2::(Reference2)::EObject1
 *
 * @author Camille Letavernier
 */
public class ContainmentBrowseStrategy extends ProviderBasedBrowseStrategy<TreeViewer> {

	protected IAdaptableContentProvider adaptableProvider;

	public ContainmentBrowseStrategy(ITreeContentProvider provider) {
		if (!(provider instanceof IAdaptableContentProvider)) {
			throw new IllegalArgumentException("The provider must be an IAdaptableContentProvider");
		}

		setProvider(provider);
		this.adaptableProvider = (IAdaptableContentProvider) super.provider;
	}

	//
	// Elements filtering
	//

	@Override
	protected boolean browseElement(Object containerElement) {
		Object semanticElement = adaptableProvider.getAdaptedValue(containerElement);

		// Only browse Containment references and Facet references
		if (semanticElement instanceof EReference) {
			return ((EReference) semanticElement).isContainment() && !((EReference) semanticElement).isDerived();
		}

		return true;
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (viewer instanceof TreeViewer) {
			this.viewer = (TreeViewer) viewer;
		}
		super.inputChanged(viewer, oldInput, newInput);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void revealSemanticElement(List<?> elementList) {
		List<EObject> eobjects = elementList.stream().filter(EObject.class::isInstance).map(EObject.class::cast).collect(Collectors.toList());
		ProviderHelper.selectReveal(eobjects, viewer);
	}

	public void expandItems(List<Object> treeElementList, TreeItem[] list) {
		// the treeElement has more tan one element
		viewer.getTree().setRedraw(false);
		if (treeElementList.size() > 0) {
			for (int i = 0; i < list.length; i++) {
				if (list[i].getData() != null && list[i].getData().equals(treeElementList.get(0))) {
					if (treeElementList.size() > 1) {// Do no expand the last element
						Object[] toexpand = { treeElementList.get(0) };
						viewer.setExpandedElements(toexpand);
					}
					ArrayList<Object> tmpList = new ArrayList<Object>();
					tmpList.addAll(treeElementList);
					tmpList.remove(tmpList.get(0));
					expandItems(tmpList, list[i].getItems());
				}
			}
		}
		viewer.getTree().setRedraw(true);
	}

	/**
	 * 
	 * @param selection
	 * 
	 * @deprecated since 2.1
	 *             Use {@link ProviderHelper#selectReveal(java.util.Collection, org.eclipse.jface.viewers.StructuredViewer)} instead
	 */
	@Deprecated
	public void selectReveal(ISelection selection) {
		if (viewer != null) {
			viewer.setSelection(selection, true);
		}
	}

	/**
	 * Simple search, based on containment references
	 *
	 * @param eobject
	 * @param objects
	 * @return
	 * 
	 * @deprecated Since 2.1
	 */
	@Deprecated
	protected List<Object> searchDirectContainmentPath(EObject eobject, List<Object> wrappedElements) {
		List<Object> path = new ArrayList<Object>();

		List<EObject> emfPath = EMFHelper.getContainmentPath(eobject);

		for (Object wrappedElement : wrappedElements) {
			EObject element = EMFHelper.getEObject(wrappedElement);

			if (eobject.equals(element)) {
				// We found the leaf element
				return Collections.singletonList(wrappedElement);
			}

			if (browseElementForDirectContainment(emfPath, element)) {
				List<Object> wrappedChildren = Arrays.asList(provider.getChildren(wrappedElement));
				List<Object> childPath = searchDirectContainmentPath(eobject, wrappedChildren);
				if (!childPath.isEmpty()) {
					// We (indirectly) found the leaf element
					path.add(wrappedElement);
					path.addAll(childPath);
					break;
				}
			} // Else: dead end
		}

		return path;
	}

	/**
	 * 
	 * @param emfPath
	 * @param element
	 * @return
	 * 
	 * @deprecated Since 2.1
	 */
	@Deprecated
	protected boolean browseElementForDirectContainment(List<EObject> emfPath, EObject element) {
		if (emfPath.contains(element)) {
			return true;
		}

		if (element instanceof EReference) {
			EReference reference = (EReference) element;
			if (reference.isContainment() && !reference.isDerived()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * look for the path the list of element (from the content provider) to go the eObject
	 *
	 * @param eobject
	 *            that we look for.
	 * @param objects
	 *            a list of elements where eobject can be wrapped.
	 * @return the list of modelElementItem (from the root to the element that wrap the eobject)
	 * 
	 * @deprecated Since 2.1 See {@link ProviderHelper#findObjectsToReveal(java.util.Collection, org.eclipse.jface.viewers.StructuredViewer)}
	 */
	@Deprecated
	protected List<Object> searchPath(EObject eobject, List<Object> objects) {
		// Simple/quick search (Based on containment)
		List<Object> path = searchDirectContainmentPath(eobject, objects);
		if (!path.isEmpty()) {
			return path;
		}

		// Advanced search
		path = new ArrayList<Object>();

		for (Object o : objects) {
			// Search matches in this level
			if (!(o instanceof Diagram)) {
				if (eobject.equals(EMFHelper.getEObject(o))) {
					path.add(o);
					return path;
				}
			}

			// Find childs only for feature container
			for (int i = 0; i < provider.getChildren(o).length; i++) {
				Object treeItem = provider.getChildren(o)[i];

				List<Object> tmppath = new ArrayList<Object>();
				Object element = EMFHelper.getEObject(treeItem);

				if (browseElement(element)) {
					List<Object> childs = new ArrayList<Object>();
					childs.add(treeItem);
					tmppath = searchPath(eobject, childs);
				}

				// if tmppath contains the wrapped eobject we have found the good path
				if (tmppath.size() > 0) {
					Object last = tmppath.get(tmppath.size() - 1);
					EObject lastEObject = EMFHelper.getEObject(last);
					if (eobject.equals(lastEObject)) {
						path.add(o);
						path.addAll(tmppath);
						return path;
					}
				}
			}
		}

		return new ArrayList<Object>();
	}


}
