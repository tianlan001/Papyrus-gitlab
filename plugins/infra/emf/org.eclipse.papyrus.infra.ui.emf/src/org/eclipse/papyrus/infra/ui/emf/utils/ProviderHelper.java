/*****************************************************************************
 * Copyright (c) 2012, 2016 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 485220
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.emf.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.papyrus.emf.facet.custom.core.ICustomizationManager;
import org.eclipse.papyrus.infra.core.language.ILanguageService;
import org.eclipse.papyrus.infra.core.resource.IModel;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.emf.utils.HistoryUtil;
import org.eclipse.papyrus.infra.ui.emf.providers.EMFGraphicalContentProvider;
import org.eclipse.papyrus.infra.ui.emf.providers.strategy.ContainmentBrowseStrategy;
import org.eclipse.papyrus.infra.ui.emf.providers.strategy.SemanticEMFContentProvider;
import org.eclipse.papyrus.infra.ui.internal.emf.Activator;
import org.eclipse.papyrus.infra.ui.providers.ISemanticContentProviderFactory;
import org.eclipse.papyrus.infra.widgets.strategy.ProviderBasedBrowseStrategy;
import org.eclipse.papyrus.infra.widgets.strategy.StrategyBasedContentProvider;
import org.eclipse.papyrus.infra.widgets.strategy.TreeBrowseStrategy;


public class ProviderHelper {

	/**
	 * Obtains the customization manager used by Papyrus's EMFFacet-based content providers.
	 * 
	 * @return the customization manager
	 */
	public static ICustomizationManager getCustomizationManager() {
		return Activator.getDefault().getCustomizationManager();
	}

	/**
	 * Encapsulates the given content provider in a higher-level content provider
	 * The returned provider uses two different strategies to display and search
	 * elements, and adds a pattern filter and an History
	 *
	 * @param provider
	 *            The ContentProvider to encapsulate
	 * @return
	 */
	public static EMFGraphicalContentProvider encapsulateProvider(ITreeContentProvider provider, ResourceSet resourceSet, String historyId) {
		TreeBrowseStrategy browseStrategy = new ProviderBasedBrowseStrategy(provider);
		TreeBrowseStrategy revealStrategy = new ContainmentBrowseStrategy(provider);
		IStructuredContentProvider strategyProvider = new StrategyBasedContentProvider(browseStrategy, revealStrategy);
		EMFGraphicalContentProvider graphicalProvider = new EMFGraphicalContentProvider(strategyProvider, resourceSet, historyId);

		return graphicalProvider;
	}

	/**
	 * Encapsulates the given content provider in a higher-level content provider
	 * The returned provider uses two different strategies to display and search
	 * elements, and adds a pattern filter and an History
	 *
	 * @param provider
	 *            The ContentProvider to encapsulate
	 * @return
	 */
	public static EMFGraphicalContentProvider encapsulateProvider(ITreeContentProvider provider, EObject editedEObject, EStructuralFeature feature) {
		ResourceSet rs = editedEObject == null ? null : editedEObject.eResource() == null ? null : editedEObject.eResource().getResourceSet();
		return encapsulateProvider(provider, rs, HistoryUtil.getHistoryID(editedEObject, feature));
	}

	/**
	 * Obtain the best available semantic content provider factory for a given resource set.
	 * 
	 * @param resourceSet
	 *            a resource set
	 * @return the best available semantic content provider factory (never {@code null})
	 * 
	 * @see #getContentProvider(ResourceSet)
	 */
	public static ISemanticContentProviderFactory getContentProviderFactory(ResourceSet resourceSet) {
		Collection<? extends IModel> models = (resourceSet instanceof ModelSet)
				? ILanguageService.getLanguageModels((ModelSet) resourceSet)
				: Collections.emptyList();
		return models.stream()
				.map(m -> m.getAdapter(ISemanticContentProviderFactory.class))
				.filter(Objects::nonNull)
				.reduce(ISemanticContentProviderFactory::compose)
				.orElse(SemanticEMFContentProvider::new);
	}

	/**
	 * Obtain the best available semantic content provider for a given resource set.
	 * 
	 * @param resourceSet
	 *            a resource set
	 * @return the best available semantic content provider factory (never {@code null})
	 * 
	 * @see #getContentProviderFactory(ResourceSet)
	 */
	public static ITreeContentProvider getContentProvider(ResourceSet resourceSet) {
		return getContentProviderFactory(resourceSet).createSemanticContentProvider(resourceSet);
	}

	/**
	 * <p>
	 * Returns a collection of objects to reveal, corresponding to a collection of model elements.
	 * </p>
	 * <p>
	 * This method takes custom structure into account, and will wrap natural model elements into
	 * viewer-specific representation elements (Such as Facet TreeElements).
	 * </p>
	 * 
	 * @param modelElements
	 * @param contentProvider
	 * @param providerInput
	 *            The input used to get the root elements from the {@code ITreeContentProvider}
	 * @return
	 * 
	 * @since 2.1
	 */
	/*
	 * Implementation note:
	 * 
	 * ITreeContentProvider#getParent can give us the path until the parent of the element to reveal.
	 * However, we also need the element itself. So we first retrieve all parents, then for each parent,
	 * filter their children to find the ones corresponding to the model elements we need to reveal.
	 * 
	 * This method works well if (and only if) the ITreeContentProvider#getParent method is properly implemented,
	 * and returns a Wrapper Parent Element when passing a Semantic Model Element (Which is now the case for the EMF Facet provider)
	 * It also works well for providers that are not wrapping model elements... by simply returning a list equivalent to its argument.
	 */
	public static List<Object> findObjectsToReveal(Collection<EObject> modelElements, ITreeContentProvider contentProvider, Object providerInput) {
		List<Object> toReveal = new ArrayList<>();
		ITreeContentProvider provider = (ITreeContentProvider) contentProvider;
		Map<Object, Collection<EObject>> allParents = new HashMap<>();
		for (EObject modelElement : modelElements) {
			Object parent = provider.getParent(modelElement);
			if (parent != null) {
				if (!allParents.containsKey(parent)) {
					allParents.put(parent, new HashSet<>());
				}
				allParents.get(parent).add(modelElement);
			} else { // element can't be found via getParent.
				// Try to find it in the root elements
				Object providerElement = Arrays.stream(provider.getElements(providerInput)) //
						.filter(element -> EMFHelper.getEObject(element) == modelElement) //
						.findFirst().orElse(modelElement); //If we can't find it, use the raw model element

				toReveal.add(providerElement);
			}
		}

		for (Map.Entry<Object, Collection<EObject>> entry : allParents.entrySet()) {
			Object[] children = provider.getChildren(entry.getKey());
			Collection<EObject> semanticToReveal = entry.getValue();
			Arrays.stream(children) //
					.filter(child -> semanticToReveal.contains(EMFHelper.getEObject(child))) //
					.forEach(toReveal::add);
		}

		return toReveal;
	}

	/**
	 * <p>
	 * Reveals and selects the given model element in the specified viewer.
	 * </p>
	 * 
	 * <p>
	 * This methods takes custom structure into account (Thus will also work with custom EMF Facet providers).
	 * </p>
	 * 
	 * @param modelElement
	 * @param viewer
	 * @since 2.1
	 */
	public static void selectReveal(Collection<EObject> modelElements, StructuredViewer viewer) {
		IContentProvider contentProvider = viewer.getContentProvider();
		if (contentProvider instanceof ITreeContentProvider) {
			List<Object> toReveal = findObjectsToReveal(modelElements, (ITreeContentProvider) contentProvider, viewer.getInput());
			viewer.setSelection(new StructuredSelection(toReveal), true);
		} else {
			viewer.setSelection(new StructuredSelection(new ArrayList<>(modelElements)), true);
		}
	}

}
