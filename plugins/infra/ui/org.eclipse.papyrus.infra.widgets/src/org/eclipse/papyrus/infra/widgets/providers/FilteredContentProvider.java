/*****************************************************************************
 * Copyright (c) 2011, 2016 CEA LIST, ALL4TEC, and others.
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
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Bug 500869 - implement expand/collapse buttons
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.providers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.editors.StringWithClearEditor;
import org.eclipse.papyrus.infra.widgets.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;


/**
 * A generic implementation for a IGraphicalContentProvider.
 * This class doesn't provide any element, and should be extended.
 *
 * It implements a filter for List or Tree elements, based on the label
 * provided by the viewer's label provider (Or Object#toString() if the viewer
 * doesn't have a label provider).
 *
 * A Text widget is added before the display control to insert the filter
 * pattern. An element is matched if at least one of these conditions is
 * matched :
 * - The element's name matches the pattern
 * - One of the element's children matches the pattern
 * - One of the element's parent matches the pattern
 *
 * The elements' hierarchy is obtained via the viewer's ContentProvider.
 *
 * @author Camille Letavernier
 */
public class FilteredContentProvider extends EncapsulatedContentProvider {

	protected StructuredViewer viewer;

	private StringWithClearEditor filterPattern;

	public static final String BASE_PATTERN = "*"; //$NON-NLS-1$

	protected boolean showIfHasVisibleParent = false;

	/** The default profile icon path. */
	private static final String ICONS_EXPAND_ALL = "/icons/expandAll.png";//$NON-NLS-1$

	/** The default profile icon path. */
	private static final String ICONS_COLLAPSE_ALL = "/icons/collapseAll.png";//$NON-NLS-1$

	/** the expand button */
	private ToolItem buttonExpand;

	/** the collapse button */
	private ToolItem buttonCollapse;

	/** The pattern viewer filter. */
	private PatternViewerFilter patternFilter;

	/** the current filter pattern as string. */
	private String currentFilterPattern;

	/** the enable state of buttons collapse and expand. */
	private boolean buttonExpandCollapseEnable = true;

	public FilteredContentProvider(final IStructuredContentProvider encapsulated) {
		super(encapsulated);
	}

	public FilteredContentProvider() {
		super();
	}


	@Override
	public void inputChanged(final Viewer viewer, final Object oldInput, final Object newInput) {
		super.inputChanged(viewer, oldInput, newInput);
		if (viewer instanceof StructuredViewer) {
			this.viewer = (StructuredViewer) viewer;
			checkPatterns();
		}
	}

	@Override
	public void createBefore(final Composite parent) {
		super.createBefore(parent);

		Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(composite);
		createFilterPattern(composite);

		findAndRevealFirstMatchingItem();

		// Create case sensitive checkbox
		createCaseSensitiveButton(composite);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.providers.IGraphicalContentProvider#createViewerToolbar(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createViewerToolbar(final Composite parent) {
		// Create case sensitive checkbox
		if (!isFlat()) {
			createExpandCollapseButtons(parent);
		}
	}

	/**
	 * Create buttons to collapse and expand treeViewer.
	 */
	protected void createExpandCollapseButtons(final Composite parent) {

		ToolBar Toolbar = new ToolBar(parent, SWT.NONE);
		buttonExpand = new ToolItem(Toolbar, SWT.NONE);
		buttonExpand.setImage(Activator.getDefault().getImage(ICONS_EXPAND_ALL));
		buttonExpand.setToolTipText(Messages.FilteredContentProvider_ExpandAllTooltip);
		buttonExpand.setEnabled(buttonExpandCollapseEnable);
		buttonExpand.addSelectionListener(new SelectionAdapter() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				ISelection selection = viewer.getSelection();
				// If there are selected element
				if (selection instanceof StructuredSelection && !selection.isEmpty()) {
					// For each element
					for (Object object : ((StructuredSelection) selection).toArray()) {
						((AbstractTreeViewer) viewer).expandToLevel(object, Activator.getMaxLevelToExpandValue());
					}
				} else {
					// or expand all
					((AbstractTreeViewer) viewer).expandToLevel(Activator.getMaxLevelToExpandValue());
				}
			}
		});

		buttonCollapse = new ToolItem(Toolbar, SWT.NONE);
		buttonCollapse.setImage(Activator.getDefault().getImage(ICONS_COLLAPSE_ALL));
		buttonCollapse.setToolTipText(Messages.FilteredContentProvider_CollapseAllTooltip);
		buttonCollapse.setEnabled(buttonExpandCollapseEnable);
		buttonCollapse.addSelectionListener(new SelectionAdapter() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				ISelection selection = ((AbstractTreeViewer) viewer).getSelection();
				// If there are selected element
				if (selection instanceof StructuredSelection && !selection.isEmpty()) {
					// expand each selected element
					for (Object object : ((StructuredSelection) selection).toArray()) {
						((AbstractTreeViewer) viewer).collapseToLevel(object, AbstractTreeViewer.ALL_LEVELS);
					}

				} else {
					// or collapse all
					((AbstractTreeViewer) viewer).collapseAll();
				}
			}
		});
	}

	/**
	 * @param composite
	 */
	protected void createFilterPattern(Composite composite) {
		filterPattern = new StringWithClearEditor(composite, SWT.BORDER);

		filterPattern.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		filterPattern.setValidateOnDelay(Activator.getValidationDelay());
		filterPattern.setValidateOnDelay(Activator.isFilterValidateOnDelay());

		if (Activator.isStereotypeDelimitersReplaced()) {
			filterPattern.addStringToReplace(Activator.ST_LEFT_BEFORE, Activator.ST_LEFT);
			filterPattern.addStringToReplace(Activator.ST_RIGHT_BEFORE, Activator.ST_RIGHT);
		} else {
			filterPattern.clearStringToReplace();
		}

		currentFilterPattern = "*";//$NON-NLS-1$
		patternFilter = getViewerFilter();
		patternFilter.setPattern(currentFilterPattern);
		filterPattern.addCommitListener(editor -> {

			String filterPattern = (String) ((StringWithClearEditor) editor).getValue();
			((PatternViewerFilter) patternFilter).setPattern(filterPattern);

			checkPatterns();

			if (!("".equals(filterPattern) || currentFilterPattern.equals(filterPattern))) { //$NON-NLS-1$
				findAndRevealFirstMatchingItem();
				currentFilterPattern = filterPattern;
			}
		});
	}


	/**
	 * Traverses to the first leaf item in the viewer tree and reveals it.
	 * <p>
	 * If this method is called after the filter has been updated and the viewer has been refreshed,
	 * this will reveal and return the first item that matches the filter. Note that this only works
	 * if {@link #viewer} is an instance of {@link TreeViewer}.
	 * </p>
	 * 
	 * @return the semantic element of the revealed item, or <code>null</code> if it could not be found.
	 */
	protected Object findAndRevealFirstMatchingItem() {
		Object result = null;
		if (viewer instanceof TreeViewer) {
			// start to search from first root element
			final Tree tree = ((TreeViewer) viewer).getTree();
			if (tree.getItems().length > 0) {
				result = revealFirstLeaf(tree.getItem(0), 15);
			}
		}
		return result;
	}

	private Object revealFirstLeaf(final TreeItem item, final int maxSearchDepth) {
		Object result = null;
		if (1 <= maxSearchDepth && viewer instanceof TreeViewer) {

			// reveal to current item
			final TreeViewer treeViewer = (TreeViewer) viewer;
			final Object[] itemDataArray = Collections.singletonList(item.getData()).toArray();
			treeViewer.setExpandedElements(itemDataArray);

			if (item.getItems().length > 0) {
				// continue with children of current item
				result = revealFirstLeaf(item.getItem(0), maxSearchDepth - 1);
			} else {
				// leaf item found: reveal and return it
				result = item.getData();
				treeViewer.setSelection(new StructuredSelection(result));
			}
		}
		return result;
	}

	/**
	 * create the Case sensitive checkBox.
	 * 
	 * @param parent
	 *            The parent {@link Composite}.
	 */
	protected void createCaseSensitiveButton(final Composite parent) {
		// Create the checkbox button
		Button checkBoxCaseSensitive = new Button(parent, SWT.CHECK);
		checkBoxCaseSensitive.setText(Messages.FilteredContentProvider_CaseSensitiveLabel);
		checkBoxCaseSensitive.setToolTipText(Messages.FilteredContentProvider_CaseSensitiveTooltip);
		checkBoxCaseSensitive.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				if (patternFilter instanceof PatternViewerFilter) {
					((PatternViewerFilter) patternFilter).setIgnoreCase(!((Button) event.getSource()).getSelection());
				}
				viewer.refresh();
			}
		});
	}

	/**
	 * Create the pattern viewer filter.
	 */
	protected PatternViewerFilter getViewerFilter() {
		PatternViewerFilter filter = new PatternViewerFilter();
		filter.setStrict(false);
		filter.setPattern(BASE_PATTERN);
		filter.setShowIfHasVisibleParent(showIfHasVisibleParent);
		return filter;
	}

	/**
	 * Checks Patterns filter.
	 */
	protected void checkPatterns() {
		if (viewer instanceof TreeViewer && null != viewer.getInput()) {
			List<ViewerFilter> filtersAsList = Arrays.asList(viewer.getFilters());
			if (null != patternFilter && !filtersAsList.contains(patternFilter)) {
				viewer.addFilter(patternFilter);
			}
			viewer.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider#setFlat(boolean)
	 */
	@Override
	public void setFlat(final boolean flat) {
		super.setFlat(flat);
		if (patternFilter instanceof PatternViewerFilter) {
			((PatternViewerFilter) patternFilter).clearCache();
			viewer.refresh();
		}
		if (null != buttonCollapse && null != buttonExpand) {
			buttonExpandCollapseEnable = !flat;
			buttonCollapse.setEnabled(buttonExpandCollapseEnable);
			buttonExpand.setEnabled(buttonExpandCollapseEnable);
		}
	}

}
