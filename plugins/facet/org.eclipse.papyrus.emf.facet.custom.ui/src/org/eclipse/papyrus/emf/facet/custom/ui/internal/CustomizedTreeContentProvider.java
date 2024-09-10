/*******************************************************************************
 * Copyright (c) 2012, 2017 Mia-Software, CEA, Christian W. Damus, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Nicolas Bros (Mia-Software) - Bug 379683 - customizable Tree content provider
 *    Gregoire Dupe (Mia-Software) - Bug 385292 - [CustomizedTreeContentProvider] StackOverFlow when refreshing a TreeViewer with ICustomizedTreeContentProvider
 *    Gregoire Dupe (Mia-Software) - Bug 386387 - [CustomizedTreeContentProvider] The TreeElements are not preserved between two calls to getElements()
 *    Christian W. Damus (CEA) - bug 430700
 *    Christian W. Damus (CEA) - bug 440795
 *    Christian W. Damus (CEA) - bug 441857
 *    Sebastien Gabel (Esterel Technologies) - Bug 438931 - Non deterministic order of the facet references defined in custom file
 *    Christian W. Damus - bugs 451683, 509742
 *
 *******************************************************************************/
package org.eclipse.papyrus.emf.facet.custom.ui.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.papyrus.emf.facet.custom.core.ICustomizationManager;
import org.eclipse.papyrus.emf.facet.custom.core.exception.CustomizationException;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.custom.Customization;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.EAttributeTreeElement;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.EObjectTreeElement;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.EReferenceTreeElement;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.TreeElement;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.TreeproxyFactory;
import org.eclipse.papyrus.emf.facet.custom.ui.IContentPropertiesHandler;
import org.eclipse.papyrus.emf.facet.custom.ui.IContentPropertiesHandlerFactory;
import org.eclipse.papyrus.emf.facet.custom.ui.ICustomizedContentProvider;
import org.eclipse.papyrus.emf.facet.custom.ui.ICustomizedTreeContentProvider;
import org.eclipse.papyrus.emf.facet.efacet.core.FacetUtils;
import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager;
import org.eclipse.papyrus.emf.facet.efacet.core.exception.FacetManagerException;
import org.eclipse.papyrus.emf.facet.util.core.Logger;

@SuppressWarnings("deprecation")
// @SuppressWarnings("deprecation") : Bug 380229 - [deprecated] ICustomizedContentProvider
public class CustomizedTreeContentProvider implements ICustomizedTreeContentProvider, ICustomizedContentProvider {

	private static final Comparator<EStructuralFeature> BY_RANK = CustomizedTreeContentProvider::compareRanks;

	private final ICustomizationManager customManager;
	private final IContentPropertiesHandler contentHandler;
	private Object[] rootElements;

	private final Map<EObjectCacheElement, TreeElement> cache;

	private class EObjectCacheElement {

		// Not null
		private final EObject element;

		// May be null
		private final TreeElement parent;

		public EObjectCacheElement(EObject element, TreeElement parent) {
			this.element = element;
			this.parent = parent;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((element == null) ? 0 : element.hashCode());
			result = prime * result + ((parent == null) ? 0 : parent.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof EObjectCacheElement)) {
				return false;
			}
			EObjectCacheElement other = (EObjectCacheElement) obj;
			if (!getOuterType().equals(other.getOuterType())) {
				return false;
			}
			if (element == null) {
				if (other.element != null) {
					return false;
				}
			} else if (!element.equals(other.element)) {
				return false;
			}
			if (parent == null) {
				if (other.parent != null) {
					return false;
				}
			} else if (!parent.equals(other.parent)) {
				return false;
			}
			return true;
		}

		private CustomizedTreeContentProvider getOuterType() {
			return CustomizedTreeContentProvider.this;
		}
	}

	public CustomizedTreeContentProvider(final ICustomizationManager customManager) {
		this.customManager = customManager;
		this.contentHandler = IContentPropertiesHandlerFactory.DEFAULT.createIContentPropertiesHandler(customManager);
		this.cache = new HashMap<EObjectCacheElement, TreeElement>();
	}

	protected Object[] getRootElements(final Object inputElement) {
		Object[] result;
		if (inputElement == null) {
			result = new Object[0];
		} else if (inputElement instanceof EObject) {
			result = new EObject[] { (EObject) inputElement };
		} else if (inputElement instanceof Collection<?>) {
			result = ((Collection<?>) inputElement).toArray();
		} else if (inputElement instanceof EObject[]) {
			result = (EObject[]) inputElement;
		} else {
			throw new IllegalArgumentException("Unhandled input element type: " + inputElement.getClass().getSimpleName()); //$NON-NLS-1$
		}
		return result;
	}

	@Override
	public Object[] getElements(final Object inputElement) {
		// Reconcile the (possibly changed) list of root elements with our tree element proxies

		final Object[] elements = getRootElements(inputElement);
		final List<Object> elementList = new ArrayList<Object>();

		// Index the existing elements
		final Map<Object, Object> index = new IdentityHashMap<Object, Object>(elements.length + 1);
		if (this.rootElements != null) {
			for (Object next : this.rootElements) {
				if (next instanceof EObjectTreeElement) {
					// Maps to its tree-element proxy
					index.put(((EObjectTreeElement) next).getEObject(), next);
				} else {
					// Maps to itself
					index.put(next, next);
				}
			}
		}

		for (final Object element : elements) {
			if (!isVisible(element)) {
				continue;
			}

			Object existing = index.get(element);
			if (existing != null) {
				// Just add it
				elementList.add(existing);
			} else if (element instanceof EObject) {
				final EObjectTreeElement eObjectProxy = getEObjectProxy(element, null);
				elementList.add(eObjectProxy);
			} else {
				elementList.add(element);
			}
		}

		this.rootElements = elementList.toArray();
		return this.rootElements;
	}

	protected static EObjectTreeElement createEObjectProxy(final Object element, final TreeElement parent) {
		final EObject eObject = (EObject) element;
		final EObjectTreeElement eObjectProxy = TreeproxyFactory.eINSTANCE.createEObjectTreeElement();
		eObjectProxy.setEObject(eObject);
		eObjectProxy.setParent(parent);
		return eObjectProxy;
	}

	@Override
	public Object[] getChildren(final Object parentElement) {
		Object[] result;
		if (parentElement == null) {
			result = new Object[0];
		} else if (parentElement instanceof EObjectTreeElement) {
			final EObjectTreeElement eObjectProxy = (EObjectTreeElement) parentElement;
			result = getChildren(eObjectProxy);
		} else if (parentElement instanceof EAttributeTreeElement) {
			final EAttributeTreeElement attributeProxy = (EAttributeTreeElement) parentElement;
			result = getChildren(attributeProxy);
		} else if (parentElement instanceof EReferenceTreeElement) {
			final EReferenceTreeElement referenceProxy = (EReferenceTreeElement) parentElement;
			result = getChildren(referenceProxy);
		} else {
			Logger.logError("Unknown element in tree: " + parentElement.getClass(), Activator.getDefault()); //$NON-NLS-1$
			result = new Object[0];
		}
		return result;
	}

	public Object[] getChildren(final EObjectTreeElement treeElement) {
		List<EStructuralFeature> facetFeatures;
		try {
			facetFeatures = FacetUtils.getETypedElements(treeElement.getEObject(), EStructuralFeature.class, customManager.getFacetManager());
		} catch (FacetManagerException e) {
			facetFeatures = Collections.emptyList();
			Logger.logError(e, Activator.getDefault());
		}

		// Where is the zero rank at which we insert native model features?
		int zeroPoint = getZeroRank(facetFeatures);
		List<EStructuralFeature> priorityFeatures = facetFeatures.subList(0, zeroPoint);
		List<EStructuralFeature> otherFeatures = facetFeatures.subList(zeroPoint, facetFeatures.size());

		final Collection<TreeElement> children = new ArrayList<TreeElement>();
		createAttributes(treeElement, priorityFeatures, otherFeatures, children);
		createReferences(treeElement, priorityFeatures, otherFeatures, children);
		return children.toArray();
	}

	/**
	 * Finds the index in a rank-sorted list of facet-features of the first feature defined
	 * by a facet-set of rank zero or greater (facet-sets that are not customizations are
	 * deemed to have rank zero).
	 * 
	 * @param facetFeatures
	 *            a list of facet features
	 * @return the index of the first feature of non-negative rank, or the size of the
	 *         list if all have negative rank (or {@code 0} if the list is empty)
	 */
	private int getZeroRank(List<EStructuralFeature> facetFeatures) {
		// The actual ordering is suspectible to user intervention, so it is possible
		// that the binary search will not find the best insertion point because the
		// list may not actually be sorted. But, it will find something and hopefully
		// that will be near the middle-ish
		int search = Collections.binarySearch(facetFeatures, null, BY_RANK);

		// We know that 'null' is not in the list, so the search result is the insertion point
		return -(1 + search);
	}

	static int compareRanks(EStructuralFeature o1, EStructuralFeature o2) {
		// the 'null' key (by definition the insertion point) has a magic
		// rank between -1 and 0
		if (o1 == null) {
			return (rank(o2) >= 0) ? +1 : -1;
		} else if (o2 == null) {
			return (rank(o1) < 0) ? -1 : +1;
		} else {
			return rank(o1) - rank(o2);
		}
	}

	static int rank(EStructuralFeature eStructuralFeature) {
		EObject eClass = eStructuralFeature.eContainer();
		EObject ePackage = eClass.eContainer();

		return (ePackage instanceof Customization)
				? ((Customization) ePackage).getRank()
				: 0;
	}

	public Object[] getChildren(final EAttributeTreeElement attributeProxy) {
		final List<Object> children = new ArrayList<Object>();
		final EAttribute eAttribute = attributeProxy.getEAttribute();
		if (eAttribute.isMany()) {
			final TreeElement parent = attributeProxy.getParent();
			if (!(parent instanceof EObjectTreeElement)) {
				throw new IllegalStateException("An attribute should appear only under a model element"); //$NON-NLS-1$
			}
			final EObjectTreeElement parentProxy = (EObjectTreeElement) parent;
			final EObject eObject = parentProxy.getEObject();

			try {
				final IFacetManager facetManager = this.customManager.getFacetManager();
				final List<Object> result = facetManager.getOrInvokeMultiValued(eObject, eAttribute, null);
				for (final Object object : result) {
					if (object instanceof EObject) {
						final EObject childEObject = (EObject) object;
						children.add(getEObjectProxy(childEObject, attributeProxy));
					}
					children.add(object);
				}
			} catch (final FacetManagerException e) {
				Logger.logError(e, Activator.getDefault());
			}
		}
		return children.toArray();
	}

	public Object[] getChildren(final EReferenceTreeElement referenceProxy) {
		List<? extends Object> result;
		final EReference eReference = referenceProxy.getEReference();
		final TreeElement parent = referenceProxy.getParent();
		if (!(parent instanceof EObjectTreeElement)) {
			throw new IllegalStateException("A reference should appear only under a model element"); //$NON-NLS-1$
		}
		final EObjectTreeElement parentProxy = (EObjectTreeElement) parent;
		final EObject eObject = parentProxy.getEObject();
		if (eReference.isMany()) {
			result = getMultiValuedReferenceChildren(eReference, eObject, referenceProxy);
		} else {
			final Object child = getSingleValuedReferenceChild(eReference, eObject, referenceProxy);
			if (child == null) {
				result = Collections.emptyList();
			} else {
				result = Collections.singletonList(child);
			}
		}
		return result.toArray();
	}

	private TreeElement getSingleValuedReferenceChild(final EReference eReference,
			final EObject eObject, final EReferenceTreeElement parent) {
		TreeElement child = null;
		if (parent.getReferedEObjectTE() == null || parent.getReferedEObjectTE().size() == 0) {
			try {
				final IFacetManager facetManager = this.customManager
						.getFacetManager();
				final EObject referedEObject = facetManager.getOrInvoke(
						eObject, eReference, EObject.class);
				if (referedEObject != null) {
					child = getEObjectProxy(referedEObject, parent);
				}
			} catch (final FacetManagerException e) {
				Logger.logError(e, Activator.getDefault());
			}
		} else {
			child = parent.getReferedEObjectTE().get(0);
		}
		return child;
	}

	private List<EObjectTreeElement> getMultiValuedReferenceChildren(
			final EReference eReference, final EObject eObject,
			final EReferenceTreeElement parent) {
		List<EObjectTreeElement> children = new ArrayList<EObjectTreeElement>();
		if (parent.getReferedEObjectTE() == null || parent.getReferedEObjectTE().size() == 0) {
			try {
				final IFacetManager facetManager = this.customManager
						.getFacetManager();
				final List<Object> result = facetManager
						.getOrInvokeMultiValued(eObject, eReference, null);
				for (final Object object : result) {
					if (object instanceof EObject) {
						final EObject childEObject = (EObject) object;
						children.add(getEObjectProxy(childEObject, parent));
					}
				}
			} catch (final FacetManagerException e) {
				Logger.logError(e, Activator.getDefault());
			}
		} else {
			children = parent.getReferedEObjectTE();
		}
		return children;
	}

	@Override
	public Object getParent(final Object element) {
		Object result = null;
		if (element instanceof TreeElement) {
			// It's a TreeElement, which means we've already displayed it.
			// Finding its parent will be easy
			final TreeElement treeElement = (TreeElement) element;
			result = treeElement.getParent();
		} else if (element instanceof EObject) {
			// It's an EObject, meaning we don't know whether it's already
			// been displayed. Delegate to the #parent custom query
			EObject modelElement = (EObject) element;
			result = customGetParent(modelElement);
		}
		return result;
	}

	/**
	 * Computes the parent TreeElement for a raw model element
	 * 
	 * @param modelElement
	 * @return
	 */
	private TreeElement customGetParent(final EObject modelElement) {
		// The custom EObject/Facet parent path, without references
		List<EObject> customParentPath = getModelElementPath(modelElement);

		// The custom EObject/Facet parent path, including references
		List<TreeElement> fullElementPath = getFullElementPath(customParentPath);

		// Build the full TreeElement path and return the leaf element
		return getParentTreeElementForPath(fullElementPath);
	}

	/**
	 * Returns the (customized) hierarchy of model elements until
	 * the given element (Included).
	 * 
	 * This may be different from the semantic container path
	 * if the tree structure has been customized (e.g. via custom
	 * facet references)
	 * 
	 * This path is the "natural path", i.e. it is unique (Even when the
	 * content provider actually represents a cyclic graph). It is however
	 * not necessarily the shortest path.
	 */
	private List<EObject> getModelElementPath(final EObject modelElement) {

		// Ordered from child to parent
		Collection<EObject> customParentPath = new LinkedHashSet<>();
		customParentPath.add(modelElement);

		EObject currentModelElement = modelElement;
		try {
			while (currentModelElement != null) {
				EObject parent = customManager.getCustomValueOf(currentModelElement, contentHandler.getParent(), EObject.class);
				if (parent != null) {
					if (customParentPath.contains(parent)) {
						String message = "Cyclic path detected when computing the hierarchy for " + modelElement;
						message += "\nPath: "+customParentPath;
						message += "This may indicate an inconsistency in the facet/custom implementation(s) of the #parent query";
						Logger.logError(message, Activator.getDefault());
						break;
					}
					customParentPath.add(parent);
				}
				currentModelElement = parent;
			}
		} catch (CustomizationException ex) {
			Logger.logError(ex, Activator.getDefault());
		}

		List<EObject> parentPath = new ArrayList<>(customParentPath);

		// Order from parent to child
		Collections.reverse(parentPath);
		return parentPath;
	}

	/**
	 * Builds and returns the complete list of TreeElements corresponding
	 * to the given model element path. The resulting path will include the
	 * necessary EReferences (Which may include FacetReferences).
	 */
	private List<TreeElement> getFullElementPath(Collection<EObject> customParentPath) {
		List<TreeElement> treePath = new ArrayList<>(); // Contains EObjects and EReferences
		TreeElement currentPathElement = null;
		EObject currentSemanticParent = null;
		
		for (EObject semanticElement : customParentPath) {

			if (currentPathElement == null) {
				// Root element
				EObjectCacheElement cacheElement = new EObjectCacheElement(semanticElement, null);
				currentPathElement = cache.get(cacheElement);
				if (currentPathElement == null) {
					currentPathElement = Arrays.stream(getElements(null)) //
							.filter(EObjectTreeElement.class::isInstance) //
							.map(EObjectTreeElement.class::cast) //
							.filter(tree -> tree.getEObject() == semanticElement) //
							.findAny().orElse(null);
				}
			} else {

				TreeElement nextElement = Arrays.stream(getChildren(currentPathElement)) //
						.filter(EObjectTreeElement.class::isInstance)//
						.map(EObjectTreeElement.class::cast) //
						.filter(tree -> tree.getEObject() == semanticElement) //
						.findAny() //
						.orElse(null);
				if (nextElement == null) {
					// Try references that may contain this element
					List<EReference> references = getVisibleReferences(currentSemanticParent);
					for (EReference reference : references) {
						if (reference.getEType().isInstance(semanticElement)) {
							// Potential match

							EObjectCacheElement cacheElement = new EObjectCacheElement(reference, currentPathElement);
							TreeElement cachedElement = cache.get(cacheElement);
							if (cachedElement == null || false == cachedElement instanceof EReferenceTreeElement) {
								// Since we called getChildren() earlier, this shouldn't happen
								System.err.println("Reference is visible but wasn't found");
								continue;
							}

							EReferenceTreeElement cachedRef = (EReferenceTreeElement) cachedElement;

							List<EObjectTreeElement> children;
							if (reference.getUpperBound() == 1) {
								children = Collections.singletonList(getSingleValuedReferenceChild(reference, currentSemanticParent, cachedElement));
							} else {
								children = getMultiValuedReferenceChildren(reference, currentSemanticParent, cachedRef);
							}

							nextElement = children.stream() //
									.filter(tree -> tree.getEObject() == semanticElement) //
									.findAny() //
									.orElse(null);

							if (nextElement != null) {
								treePath.add(cachedRef);
								break;
							}
						}
					}
				}

				currentPathElement = nextElement;
			}

			currentSemanticParent = semanticElement;
			if (currentPathElement != null) {
				treePath.add(currentPathElement);
			}
		}

		return treePath;
	}

	private TreeElement getParentTreeElementForPath(List<TreeElement> fullElementPath) {
		if (fullElementPath.size() < 2) {
			return null;
		}
		return fullElementPath.get(fullElementPath.size() - 2);
	}

	@Override
	public boolean hasChildren(final Object element) {
		return getChildren(element).length > 0;
	}

	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		if (oldInput != newInput) {
			cache.clear();
		}
	}

	@Override
	public ICustomizationManager getCustomizationManager() {
		return this.customManager;
	}

	public boolean isVisible(final Object object) {
		Boolean result = Boolean.TRUE;
		if (object instanceof EObject) {
			final EObject eObject = (EObject) object;
			try {
				result = this.customManager.getCustomValueOf(eObject, this.contentHandler.getIsVisible(), Boolean.class);
			} catch (final CustomizationException e) {
				Logger.logError(e, Activator.getDefault());
			}
		}
		return result.booleanValue();
	}

	private boolean collapseLink(final Object object, final EReference eTypedElement) {
		Boolean result = Boolean.TRUE;
		if (object instanceof EObject) {
			final EObject eObject = (EObject) object;
			try {
				result = this.customManager.getCustomValueOf(eObject, eTypedElement, this.contentHandler.getCollapseLink(), Boolean.class);
			} catch (final CustomizationException e) {
				Logger.logError(e, Activator.getDefault());
			}
		}
		return result.booleanValue();
	}



	private void createReferences(final EObjectTreeElement treeElement,
			Collection<EStructuralFeature> priorityFacetFeatures, Collection<EStructuralFeature> otherFacetFeatures,
			Collection<TreeElement> children) {
		final EObject eObject = treeElement.getEObject();

		// High-priority (negative rank) features, first
		if (!priorityFacetFeatures.isEmpty()) {
			for (EStructuralFeature next : priorityFacetFeatures) {
				if (next instanceof EReference) {
					createReference(treeElement, eObject, (EReference) next, children);
				}
			}
		}

		// Filtered native model references have implicit zero rank
		for (EReference next : getVisibleReferences(eObject)) {
			createReference(treeElement, eObject, next, children);
		}

		// Lower priority (positive rank) features follow
		if (!otherFacetFeatures.isEmpty()) {
			for (EStructuralFeature next : otherFacetFeatures) {
				if (next instanceof EReference) {
					createReference(treeElement, eObject, (EReference) next, children);
				}
			}
		}
	}

	private void createReference(EObjectTreeElement treeElement, EObject eObject, EReference eReference, Collection<TreeElement> children) {
		if (collapseLink(eObject, eReference)) {
			if (eReference.getUpperBound() != 1) {
				collectMultiValuedReferenceChildren(eReference, eObject, treeElement, children);
			} else {
				EObjectTreeElement child = getSingleValuedReferenceChild(eReference, eObject, treeElement);
				if (child != null) {
					children.add(child);
				}
			}
		} else {
			children.add(getEReferenceProxy(eReference, treeElement));
		}
	}

	private void createAttributes(final EObjectTreeElement treeElement,
			Collection<EStructuralFeature> priorityFacetFeatures, Collection<EStructuralFeature> otherFacetFeatures,
			Collection<TreeElement> children) {

		final EObject eObject = treeElement.getEObject();

		// High-priority (negative rank) features, first
		if (!priorityFacetFeatures.isEmpty()) {
			for (EStructuralFeature next : priorityFacetFeatures) {
				if (next instanceof EAttribute) {
					createAttribute(treeElement, eObject, (EAttribute) next, children);
				}
			}
		}

		// Filtered native model attributes have implicit zero rank
		for (EAttribute next : getVisibleAttributes(eObject)) {
			createAttribute(treeElement, eObject, next, children);
		}

		// Lower priority (positive rank) features follow
		if (!otherFacetFeatures.isEmpty()) {
			for (EStructuralFeature next : otherFacetFeatures) {
				if (next instanceof EAttribute) {
					createAttribute(treeElement, eObject, (EAttribute) next, children);
				}
			}
		}
	}

	private List<EAttribute> getVisibleAttributes(EObject eObject) {
		List<EAttribute> result = null;
		try {
			result = this.customManager.getCustomValuesOf(eObject, this.contentHandler.getVisibleAttributes(), EAttribute.class);
		} catch (final CustomizationException e) {
			Logger.logError(e, Activator.getDefault());
		}

		return result == null ? Collections.emptyList() : result;
	}

	private List<EReference> getVisibleReferences(EObject eObject) {
		List<EReference> result = null;
		try {
			result = this.customManager.getCustomValuesOf(eObject, this.contentHandler.getVisibleReferences(), EReference.class);
		} catch (final CustomizationException e) {
			Logger.logError(e, Activator.getDefault());
		}

		return result == null ? Collections.emptyList() : result;
	}

	private void createAttribute(EObjectTreeElement treeElement, EObject eObject, EAttribute eAttribute, Collection<? super TreeElement> children) {
		TreeElement eAttributeTreeElement = getEAttributeProxy(eAttribute, treeElement);
		if (eAttributeTreeElement != null) {
			children.add(eAttributeTreeElement);
		}
	}


	private EReferenceTreeElement createReferenceProxy(final EReference reference, final EObjectTreeElement parent) {
		final EReferenceTreeElement referenceProxy = TreeproxyFactory.eINSTANCE.createEReferenceTreeElement();
		referenceProxy.setEReference(reference);
		referenceProxy.setParent(parent);
		return referenceProxy;
	}

	private EAttributeTreeElement createAttributeProxy(final EAttribute attribute, final EObjectTreeElement parent) {
		final EAttributeTreeElement attributeProxy = TreeproxyFactory.eINSTANCE.createEAttributeTreeElement();
		attributeProxy.setEAttribute(attribute);
		attributeProxy.setParent(parent);
		return attributeProxy;
	}

	private EObjectTreeElement getSingleValuedReferenceChild(final EReference eReference, final EObject eObject, final TreeElement parent) {
		EObjectTreeElement child = null;
		try {
			final IFacetManager facetManager = this.customManager.getFacetManager();
			final Object result = facetManager.getOrInvoke(eObject, eReference, null);
			if (result instanceof EObject) {
				final EObject childEObject = (EObject) result;
				child = getEObjectProxy(childEObject, parent);
			}
		} catch (final FacetManagerException e) {
			Logger.logError(e, Activator.getDefault());
		}
		return child;
	}

	private void collectMultiValuedReferenceChildren(final EReference eReference, final EObject eObject, final TreeElement parent, Collection<TreeElement> children) {
		try {
			final IFacetManager facetManager = this.customManager.getFacetManager();
			final List<Object> result = facetManager.getOrInvokeMultiValued(eObject, eReference, null);
			for (final Object object : result) {
				if (object instanceof EObject) {
					final EObject childEObject = (EObject) object;
					children.add(getEObjectProxy(childEObject, parent));
				}
			}
		} catch (final FacetManagerException e) {
			Logger.logError(e, Activator.getDefault());
		}
	}

	protected EObjectTreeElement getEObjectProxy(final Object element, final TreeElement parent) {
		EObjectCacheElement cacheElement = new EObjectCacheElement((EObject) element, parent);
		if (!(cache.containsKey(cacheElement))) {
			cache.put(cacheElement, createEObjectProxy(element, parent));
		}
		return (EObjectTreeElement) cache.get(cacheElement);
	}

	protected EAttributeTreeElement getEAttributeProxy(final Object element, final TreeElement parent) {
		EObjectCacheElement cacheElement = new EObjectCacheElement((EObject) element, parent);
		if (!(cache.containsKey(cacheElement))) {
			cache.put(cacheElement, createAttributeProxy((EAttribute) element, (EObjectTreeElement) parent));
		}
		return (EAttributeTreeElement) cache.get(cacheElement);
	}

	protected EReferenceTreeElement getEReferenceProxy(final Object element, final EObjectTreeElement parent) {
		EObjectCacheElement cacheElement = new EObjectCacheElement((EObject) element, parent);
		if (!(cache.containsKey(cacheElement))) {
			cache.put(cacheElement, createReferenceProxy((EReference) element, parent));
		}
		return (EReferenceTreeElement) cache.get(cacheElement);
	}

	@Override
	public void dispose() {
		cache.clear();
		rootElements = null;
	}
}
