/*****************************************************************************
 * Copyright (c) 2010, 2021 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 573987
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.toolsmiths.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.ui.action.EditingDomainActionBarContributor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.EAttributeTreeElement;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.EObjectTreeElement;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.EReferenceTreeElement;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.EStructuralFeatureTreeElement;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.TreeElement;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;

/**
 * Utility class for Actions.
 *
 * @author Camille Letavernier
 *
 */
public class ActionUtil {

	private final EditingDomainActionBarContributor contributor;
	private final Consumer<? super SelectionChangedEvent> selectionChangedHandler;
	private final BiFunction<? super Collection<?>, ? super ISelection, ? extends Collection<IAction>> newChildActionsGenerator;
	private final BiFunction<? super Collection<?>, ? super ISelection, ? extends Collection<IAction>> newSiblingActionsGenerator;

	private ISelection originalSelection;

	/**
	 * Initialize a utility instance for use in an action bar {@code contributor}.
	 *
	 * @param contributor
	 *            the action-bar contributor that owns me
	 * @param selectionChangedHandler
	 *            the basic selection-change handler of the {@code contributor}. This should usually be its EMF-generated
	 *            implementation of {@link ISelectionChangedListener#selectionChanged(SelectionChangedEvent)}
	 * @param newChildActionsGenerator
	 *            the basic new child actions creation method of the {@code contributor}. This should usually be its
	 *            EMF-generated implementation of {@code generateCreateChildActions(...)}
	 * @param newSiblingActionsGenerator
	 *            the basic new sibling actions creation method of the {@code contributor}. This should usually be its
	 *            EMF-generated implementation of {@code generateCreateSiblingActions(...)}
	 */
	public ActionUtil(EditingDomainActionBarContributor contributor,
			Consumer<? super SelectionChangedEvent> selectionChangedHandler,
			BiFunction<? super Collection<?>, ? super ISelection, ? extends Collection<IAction>> newChildActionsGenerator,
			BiFunction<? super Collection<?>, ? super ISelection, ? extends Collection<IAction>> newSiblingActionsGenerator) {

		super();

		this.contributor = contributor;
		this.selectionChangedHandler = selectionChangedHandler;
		this.newChildActionsGenerator = newChildActionsGenerator;
		this.newSiblingActionsGenerator = newSiblingActionsGenerator;
	}

	/**
	 * EMF can only handle collections of EObjects. However, as the
	 * customization plugin relies a lot on EMF Facet, we often have to handle
	 * objects that can be adapted to EObjects, but are not EObjects
	 * themselves. This method adapts a collections of such objects to their
	 * underlying EObjects, so that EMF can handle them.
	 * Objects that cannot be adapted remain as-is in the collection.
	 *
	 * @param selection
	 *            The collection to adapt
	 * @return
	 *         The adapted selection
	 */
	public static Collection<Object> getAdaptedSelection(Collection<? extends Object> selection) {
		Collection<Object> newSelection = new LinkedList<>();
		for (Object o : selection) {
			EObject semantic = EMFHelper.getEObject(o);
			newSelection.add(semantic == null ? o : semantic);
		}
		return newSelection;
	}

	/**
	 * EMF can only handle ISelection containing EObjects. However, as the
	 * customization plugin relies a lot on EMF Facet, we often have to handle
	 * objects that can be adapted to EObjects, but are not EObjects
	 * themselves. This method adapts a ISelection of such objects to their
	 * underlying EObjects, so that EMF can handle them.
	 * Objects that cannot be adapted remain as-is in the selection.
	 *
	 * @param sourceSelection
	 *            The selection to adapt
	 * @return
	 *         The adapted selection
	 */
	public static ISelection getAdaptedSelection(ISelection sourceSelection) {
		if (sourceSelection instanceof StructuredSelection) {
			StructuredSelection currentSelection = (StructuredSelection) sourceSelection;
			List<Object> newSelection = new LinkedList<>();

			Iterator<?> it = currentSelection.iterator();
			while (it.hasNext()) {
				Object object = it.next();
				EObject eObject = EMFHelper.getEObject(object);
				newSelection.add(eObject == null ? object : eObject);
			}

			StructuredSelection selection = new StructuredSelection(newSelection);
			return selection;
		} else {
			return sourceSelection;
		}
	}

	/**
	 * Get the only element of a {@code selection} if it is a structured selection containing exactly one element.
	 *
	 * @param selection
	 *            a selection
	 * @return its one and only (structured) element
	 */
	public static Object getElement(ISelection selection) {
		Object result = null;

		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structured = (IStructuredSelection) selection;
			if (structured.size() == 1) {
				result = structured.getFirstElement();
			}
		}

		return result;
	}

	/**
	 * If the {@code selection} contains an <em>EMF Facet</em> tree node, get the object from it
	 * that either the node represents or that owns it if the node is a feature node.
	 *
	 * @param selection
	 *            a selection
	 * @return the EMF object that it represents, or {@code null}
	 *         if the selection is just a regular EMF object or something else
	 */
	public static EObject unwrapTreeNode(ISelection selection) {
		EObject result = null;
		Object firstElement = getElement(selection);
		if (firstElement instanceof TreeElement) {
			result = unwrapTreeNode((TreeElement) firstElement);
		}
		return result;
	}

	private static EObject unwrapTreeNode(TreeElement treeElement) {
		EObject result = null;

		if (treeElement instanceof EObjectTreeElement) {
			result = ((EObjectTreeElement) treeElement).getEObject();
		} else if (treeElement instanceof EStructuralFeatureTreeElement) {
			EStructuralFeatureTreeElement featureElement = (EStructuralFeatureTreeElement) treeElement;
			if (featureElement.getParent() != null) {
				result = unwrapTreeNode(featureElement.getParent());
			}
		}

		return result;
	}

	/**
	 * If the {@code selection} contains an <em>EMF Facet</em> feature node, get the feature from it.
	 *
	 * @param selection
	 *            a selection
	 * @return the EMF structural feature that it represents, or {@code null}
	 *         if the selection is just a regular EMF object or something else
	 */
	public static EStructuralFeature unwrapTreeFeature(ISelection selection) {
		EStructuralFeature result = null;
		Object firstElement = getElement(selection);
		if (firstElement instanceof EStructuralFeatureTreeElement) {
			result = unwrapTreeFeature((EStructuralFeatureTreeElement) firstElement);
		}
		return result;
	}

	private static EStructuralFeature unwrapTreeFeature(EStructuralFeatureTreeElement treeElement) {
		EStructuralFeature result = null;

		if (treeElement instanceof EAttributeTreeElement) {
			result = ((EAttributeTreeElement) treeElement).getEAttribute();
		} else if (treeElement instanceof EReferenceTreeElement) {
			result = ((EReferenceTreeElement) treeElement).getEReference();
		}

		return result;
	}

	/**
	 * If the {@code selection} contains an <em>EMF Facet</em> feature node, get the object that
	 * owns the feature (from this node's parent).
	 *
	 * @param selection
	 *            a selection
	 * @return the EMF object that it owns the feature that the node represents, or {@code null}
	 *         if the selection is just a regular EMF object or something else
	 */
	public static EStructuralFeature unwrapTreeParentFeature(ISelection selection) {
		EStructuralFeature result = null;
		Object firstElement = getElement(selection);
		if (firstElement instanceof EObjectTreeElement) {
			EObjectTreeElement object = (EObjectTreeElement) firstElement;
			if (object.getParent() instanceof EStructuralFeatureTreeElement) {
				result = unwrapTreeFeature((EStructuralFeatureTreeElement) object.getParent());
			}
		}
		return result;
	}

	/**
	 * Handle the selection change on behalf of the owning action bar contributor.
	 *
	 * @param event
	 *            the selection change event received by the action bar contributor
	 */
	public void selectionChanged(SelectionChangedEvent event) {
		originalSelection = event.getSelection();

		try {
			ISelection newSelection = ActionUtil.getAdaptedSelection(event.getSelection());

			SelectionChangedEvent newEvent = new SelectionChangedEvent(event.getSelectionProvider(), newSelection);

			selectionChangedHandler.accept(newEvent);
		} finally {
			originalSelection = null;
		}

	}

	/**
	 * Create new child actions on behalf of the owning action bar contributor.
	 *
	 * @param descriptors
	 *            the new-child descriptors gathered by the base EMF contributor framework
	 * @param the
	 *            selection forward by the EMF contributor framework (which was replaced by this utility)
	 * @return the actions that the contributor should return to the EMF editor
	 */
	public Collection<IAction> createNewChildActions(Collection<?> descriptors, ISelection selection) {
		Collection<IAction> result;
		EObject unwrappedTreeObject = unwrapTreeNode(originalSelection);
		EStructuralFeature unwrappedTreeFeature = unwrapTreeFeature(originalSelection);

		if (unwrappedTreeObject != null && unwrappedTreeFeature != null) {
			descriptors = getEditingDomain().getNewChildDescriptors(unwrappedTreeObject, null);
			descriptors.removeIf(d -> !(d instanceof CommandParameter) || ((CommandParameter) d).getEStructuralFeature() != unwrappedTreeFeature);
			result = newChildActionsGenerator.apply(descriptors, new StructuredSelection(unwrappedTreeObject));
		} else {
			result = newChildActionsGenerator.apply(descriptors, selection);
		}

		return result;
	}

	/**
	 * Create new sibling actions on behalf of the owning action bar contributor.
	 *
	 * @param descriptors
	 *            the new-sibling descriptors gathered by the base EMF contributor framework
	 * @param the
	 *            selection forward by the EMF contributor framework (which was replaced by this utility)
	 * @return the actions that the contributor should return to the EMF editor
	 */
	public Collection<IAction> createNewSiblingActions(Collection<?> descriptors, ISelection selection) {
		Collection<IAction> result;
		EObject unwrappedTreeObject = unwrapTreeNode(originalSelection);
		EStructuralFeature unwrappedTreeFeature = unwrapTreeFeature(originalSelection);
		EStructuralFeature unwrappedTreeParentFeature = unwrapTreeParentFeature(originalSelection);

		if (unwrappedTreeObject != null && unwrappedTreeFeature != null) {
			descriptors = getEditingDomain().getNewChildDescriptors(null, unwrappedTreeObject);
			descriptors.removeIf(d -> !(d instanceof CommandParameter) || ((CommandParameter) d).getEStructuralFeature() != unwrappedTreeFeature);
			result = newSiblingActionsGenerator.apply(descriptors, new StructuredSelection(unwrappedTreeObject));
		} else if (unwrappedTreeObject != null && unwrappedTreeParentFeature != null) {
			descriptors = new ArrayList<>(descriptors);
			descriptors.removeIf(d -> !(d instanceof CommandParameter) || ((CommandParameter) d).getEStructuralFeature() != unwrappedTreeParentFeature);
			result = newSiblingActionsGenerator.apply(descriptors, new StructuredSelection(unwrappedTreeObject));
		} else {
			result = newSiblingActionsGenerator.apply(descriptors, selection);
		}

		return result;
	}

	/**
	 * Get the editor's editing domain from the contributor that owns me.
	 *
	 * @return the editing domain
	 */
	protected EditingDomain getEditingDomain() {
		return ((IEditingDomainProvider) contributor.getActiveEditor()).getEditingDomain();
	}

}
