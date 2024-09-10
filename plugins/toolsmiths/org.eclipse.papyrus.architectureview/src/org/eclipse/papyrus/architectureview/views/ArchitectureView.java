/*****************************************************************************
 * Copyright (c) 2019, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   Christian W. Damus - bug 570097
 *
 *****************************************************************************/

package org.eclipse.papyrus.architectureview.views;

import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.papyrus.architectureview.Activator;
import org.eclipse.papyrus.architectureview.providers.ArchiectureViewLabelProvider;
import org.eclipse.papyrus.architectureview.providers.ArchitectureViewContentProvider;
import org.eclipse.papyrus.infra.architecture.ArchitectureDomainManager;
import org.eclipse.papyrus.infra.core.architecture.ADElement;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

/**
 * This view shows the available architectures in the current instance.
 */
public class ArchitectureView extends ViewPart {

	/**
	 * The tree viewer.
	 */
	private TreeViewer viewer;

	private ArchitectureDomainManager.Listener architectureListener = this::domainManagerChanged;

	/**
	 * Constructor.
	 */
	public ArchitectureView() {
		super();
	}

	/**
	 * Create the main tree control
	 *
	 * @param parent
	 *            The parent composite.
	 * @return Tree The created tree.
	 */
	protected Tree createTree(final Composite parent) {
		final Tree tree = new Tree(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI | SWT.FULL_SELECTION);
		tree.setLinesVisible(true);
		tree.setHeaderVisible(true);
		return tree;
	}

	/**
	 * Return the viewer.
	 *
	 * @return TreeViewer
	 */
	protected TreeViewer getViewer() {
		return viewer;
	}

	/**
	 * Create the columns.
	 */
	protected void createColumns() {
		// Create the listener to sort the columns
		final SelectionListener sortListener = new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final TreeColumn column = (TreeColumn) e.getSource();
				final TreeColumn previousSortColumn = viewer.getTree().getSortColumn();
				int sortDirection = SWT.DOWN;
				if (previousSortColumn != null) {
					int prevSortDirection = viewer.getTree().getSortDirection();
					if (column.equals(previousSortColumn)) {
						sortDirection = prevSortDirection == SWT.UP ? SWT.DOWN : SWT.UP;
						viewer.getTree().setSortDirection(sortDirection);
					} else {
						viewer.getTree().setSortColumn(column);
						viewer.getTree().setSortDirection(SWT.DOWN);
					}
				} else {
					viewer.getTree().setSortColumn(column);
					viewer.getTree().setSortDirection(SWT.DOWN);
				}
				viewer.refresh();
			}
		};

		// Create the columns
		final TreeColumn tcName = new TreeColumn(viewer.getTree(), SWT.LEFT);
		tcName.setText("Name"); //$NON-NLS-1$
		tcName.setWidth(200);
		tcName.addSelectionListener(sortListener);
		final TreeColumn tcId = new TreeColumn(viewer.getTree(), SWT.LEFT);
		tcId.setText("Identifier"); //$NON-NLS-1$
		tcId.setWidth(250);
		tcId.addSelectionListener(sortListener);
		final TreeColumn tcMerged = new TreeColumn(viewer.getTree(), SWT.LEFT);
		tcMerged.setText("Merged"); //$NON-NLS-1$
		tcMerged.setWidth(100);
		tcMerged.addSelectionListener(sortListener);
		final TreeColumn tcFileName = new TreeColumn(viewer.getTree(), SWT.LEFT);
		tcFileName.setText("File Name"); //$NON-NLS-1$
		tcFileName.setWidth(150);
		tcFileName.addSelectionListener(sortListener);
		final TreeColumn tcPlugin = new TreeColumn(viewer.getTree(), SWT.LEFT);
		tcPlugin.setText("Plug-in"); //$NON-NLS-1$
		tcPlugin.setWidth(250);
		tcPlugin.addSelectionListener(sortListener);
		final TreeColumn tcIsValid = new TreeColumn(viewer.getTree(), SWT.LEFT);
		tcIsValid.setText("IsValid"); //$NON-NLS-1$
		tcIsValid.setWidth(100);
		tcIsValid.addSelectionListener(sortListener);

	}

	@Override
	public void createPartControl(final Composite parent) {
		viewer = new TreeViewer(createTree(parent));
		viewer.setContentProvider(getContentProvider());
		viewer.setLabelProvider(getLabelProvider());
		viewer.setComparator(new ViewerComparator() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.jface.viewers.ViewerComparator#compare(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
			 */
			@Override
			public int compare(final Viewer viewer, final Object e1, final Object e2) {
				final TreeViewer treeViewer = (TreeViewer) viewer;
				// Get the sorted column and direction
				final TreeColumn column = treeViewer.getTree().getSortColumn();
				if (null == column) {
					return super.compare(viewer, e1, e2);
				}
				final int sortDirection = treeViewer.getTree().getSortDirection();

				// Calculate the column index of the sorted column
				int columnIndex = -1;
				for (int cpt = 0; cpt < treeViewer.getTree().getColumnCount() && -1 == columnIndex; cpt++) {
					if (treeViewer.getTree().getColumn(cpt).equals(column)) {
						columnIndex = cpt;
					}
				}

				// Compare the labels of values
				final String firstValue = ((ITableLabelProvider) treeViewer.getLabelProvider()).getColumnText(e1, columnIndex);
				final String secondValue = ((ITableLabelProvider) treeViewer.getLabelProvider()).getColumnText(e2, columnIndex);

				return SWT.DOWN == sortDirection ? getComparator().compare(firstValue, secondValue) : getComparator().compare(secondValue, firstValue);
			}
		});
		// Add the double click listener to open the architecture file
		viewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(final DoubleClickEvent event) {
				ISelection selection = event.getSelection();
				if (selection == null || selection.isEmpty()) {
					return;
				}
				if (event.getSelection() instanceof IStructuredSelection) {

					final IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

					// for each selected resource, we try to open this one
					for (final Object selectedObject : ((IStructuredSelection) selection).toList()) {
						if (selectedObject instanceof MergedArchitectureDomain) {
							// Open the architecture editor for the architecture file
							ADElement element = ((MergedArchitectureDomain) selectedObject).getAdapter(ADElement.class);
							Resource resource = element.eResource();
							try {
								page.openEditor(new URIEditorInput(resource.getURI()), "org.eclipse.papyrus.infra.ui.architecture.ArchitectureEditorID", true); //$NON-NLS-1$
							} catch (PartInitException e) {
								Activator.log.error("The selected resource cannot be opened.", e); //$NON-NLS-1$
							}
						} else {
							// If this is not a resource, expand or collapse element
							if (viewer.getExpandedState(selectedObject)) {
								viewer.collapseToLevel(selectedObject, AbstractTreeViewer.ALL_LEVELS);
							} else {
								viewer.expandToLevel(selectedObject, 1);
							}
						}
					}
				}
			}
		});
		createColumns();

		// Populate the viewer
		domainManagerChanged();

		// Listen for updates
		ArchitectureDomainManager.getInstance().addListener(architectureListener);
	}

	/**
	 * This allows to get the label provider for the tree viewer.
	 *
	 * @return The created label provider for the tree viewer.
	 */
	protected ITableLabelProvider getLabelProvider() {
		return new ArchiectureViewLabelProvider();
	}

	/**
	 * This allows to get the content provider for the tree viewer.
	 *
	 * @return The content provider for the tree viewer.
	 */
	protected IContentProvider getContentProvider() {
		return new ArchitectureViewContentProvider();
	}

	@Override
	public void setFocus() {
		final Viewer viewer = getViewer();
		if (viewer != null && !viewer.getControl().isDisposed()) {
			viewer.getControl().setFocus();
		}
	}

	@Override
	public void dispose() {
		ArchitectureDomainManager.getInstance().removeListener(architectureListener);
		super.dispose();
	}

	private void domainManagerChanged() {
		viewer.setInput(ArchitectureDomainManager.getInstance().getMerger().getDomains());
	}

}
