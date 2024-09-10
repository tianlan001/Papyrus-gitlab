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
package org.eclipse.papyrus.infra.widgets.providers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.papyrus.infra.widgets.strategy.ProviderBasedBrowseStrategy;
import org.eclipse.papyrus.infra.widgets.strategy.StrategyBasedContentProvider;
import org.eclipse.papyrus.infra.widgets.strategy.TreeBrowseStrategy;
import org.eclipse.papyrus.infra.widgets.strategy.WorkspaceRevealStrategy;
import org.eclipse.swt.widgets.Composite;


/**
 * A Workspace content provider with search possibilities and filters (By file name and by file extension)
 *
 * @author Camille Letavernier
 */
public class WorkspaceContentProvider extends EncapsulatedContentProvider {

	private Map<String, String> extensionFilters = new LinkedHashMap<String, String>();

	private PatternViewerFilter extensionViewerFilter = new PatternViewerFilter();

	private ISelectionChangedListener extensionFiltersListener = new ISelectionChangedListener() {

		@Override
		public void selectionChanged(SelectionChangedEvent event) {

			String patternString = "*";
			if (!event.getSelection().isEmpty()) {
				patternString = (String) ((IStructuredSelection) event.getSelection()).getFirstElement();
			}
			extensionViewerFilter.setPattern(patternString);
			viewer.refresh();
		}
	};

	protected StructuredViewer viewer;

	/**
	 * Constructor
	 */
	public WorkspaceContentProvider() {
		super();
		this.encapsulated = encapsulateProvider(getSemanticProvider());
		extensionFilters.put("*", "All (*)");
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		super.inputChanged(viewer, oldInput, newInput);

		if (viewer instanceof StructuredViewer) {
			this.viewer = (StructuredViewer) viewer;
			addViewerFilter(this.viewer, extensionViewerFilter);
		}
	}

	protected static IHierarchicContentProvider getSemanticProvider() {
		return new SemanticWorkspaceContentProvider();
	}

	public void setExtensionFilters(Map<String, String> extensionFilters) {
		this.extensionFilters = extensionFilters;
	}

	public void addExtensionFilter(String pattern, String label) {
		this.extensionFilters.put(pattern, label);
	}

	protected IStructuredContentProvider encapsulateProvider(IHierarchicContentProvider provider) {
		TreeBrowseStrategy browseStrategy = new ProviderBasedBrowseStrategy(provider);
		TreeBrowseStrategy revealStrategy = new WorkspaceRevealStrategy(provider);
		StrategyBasedContentProvider strategyProvider = new StrategyBasedContentProvider(browseStrategy, revealStrategy);


		EncapsulatedContentProvider graphicalProvider = new ExtensionFilteredContentProvider(strategyProvider);

		return graphicalProvider;
	}

	private class ExtensionFilteredContentProvider extends FilteredContentProvider {

		public ExtensionFilteredContentProvider(IHierarchicContentProvider semanticProvider) {
			super(semanticProvider);
		}

		@Override
		public void createAfter(Composite parent) {
			super.createAfter(parent);
			ComboViewer extensionFiltersViewer = new ComboViewer(parent);

			extensionFiltersViewer.setContentProvider(CollectionContentProvider.instance);
			extensionFiltersViewer.setLabelProvider(new LabelProvider() {

				@Override
				public String getText(Object element) {
					if (extensionFilters.containsKey(element)) {
						return extensionFilters.get(element);
					} else {
						return super.getText(element);
					}
				}
			});

			extensionFiltersViewer.addSelectionChangedListener(extensionFiltersListener);

			extensionFiltersViewer.setInput(extensionFilters.keySet());
			if (!extensionFilters.isEmpty()) {
				extensionFiltersViewer.setSelection(new StructuredSelection(extensionFilters.keySet().iterator().next()));
			}
		}
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider#setFlat(boolean)
	 */
	@Override
	public void setFlat(final boolean isFlat) {
		super.setFlat(isFlat);
		if (null != viewer) {
			ViewerFilter[] filters = viewer.getFilters();
			for (int i = 0; i < filters.length; i++) {
				if (filters[i] instanceof PatternViewerFilter) {
					((PatternViewerFilter) filters[i]).clearCache();
				}
			}
			viewer.refresh();
		}
	}
}
