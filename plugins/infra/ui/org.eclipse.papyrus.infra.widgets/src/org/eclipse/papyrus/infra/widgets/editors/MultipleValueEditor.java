/*****************************************************************************
 * Copyright (c) 2010, 2017 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus (CEA) - bug 402525
 *  Christian W. Damus - bugs 399859, 516526
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - manage buttons visibility and enable. 
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 515808, 522564
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.editors;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.papyrus.infra.widgets.providers.TreeCollectionContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;

/**
 * An editor for multivalued fields.
 *
 * @author Camille Letavernier
 * @param <T>
 *
 */
public class MultipleValueEditor<T extends IElementSelector> extends AbstractMultipleValueEditor<T> {

	private final int style;

	/**
	 * The viewer displaying the current values from
	 * the model
	 */
	protected TreeViewer treeViewer;

	/**
	 * The tree associated to the viewer
	 */
	protected Tree tree;

	/**
	 *
	 * Constructor.
	 * 
	 * @param <T>
	 *
	 * @param parent
	 *            The Composite in which this Editor should be displayed
	 * @param style
	 *            This editor's tree style
	 * @param selector
	 *            The element selector for this editor's dialog
	 * @param ordered
	 *            Specify if the observed collection is ordered. If true, Up and Down controls are displayed.
	 * @param unique
	 *            Specify if the observed collection values are unique.
	 * @param label
	 *            The label for this editor. If null, the label isn't created.
	 */
	public MultipleValueEditor(Composite parent, int style, T selector, boolean ordered, boolean unique, String label) {
		super(parent, selector, ordered, unique, label);

		this.style = style;

		createContents();
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param parent
	 *            The Composite in which this Editor should be displayed
	 * @param style
	 *            This editor's tree style
	 * @param selector
	 *            The element selector for this editor's dialog
	 * @param ordered
	 *            Specify if the observed collection is ordered. If true, Up and Down controls are displayed
	 */
	public MultipleValueEditor(Composite parent, int style, T selector, boolean ordered) {
		this(parent, style, selector, ordered, false, null);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param parent
	 *            The Composite in which this Editor should be displayed
	 * @param style
	 *            This editor's tree style
	 * @param selector
	 *            The element selector for this editor's dialog
	 */
	public MultipleValueEditor(Composite parent, int style, T selector) {
		this(parent, style, selector, false, false, null);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param parent
	 *            The Composite in which this Editor should be displayed
	 * @param style
	 *            This editor's tree style
	 * @param selector
	 *            The element selector for this editor's dialog
	 * @param label
	 *            The label for this Editor
	 */
	public MultipleValueEditor(Composite parent, int style, T selector, String label) {
		this(parent, style, selector, false, false, label);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param parent
	 *            The Composite in which this Editor should be displayed
	 * @param style
	 *            This editor's tree style
	 * @param selector
	 *            The element selector for this editor's dialog
	 * @param ordered
	 *            Specify if the observed collection is ordered. If true, Up and Down controls are displayed.
	 * @param unique
	 *            Specify if the observed collection values are unique.
	 * @param label
	 *            The label for this editor. If null, the label isn't created.
	 * @param upperBound
	 *            The maximum number of values that must appear.
	 */
	public MultipleValueEditor(Composite parent, int style, T selector, boolean ordered, boolean unique, String label, int upperBound) {
		super(parent, selector, ordered, unique, label, upperBound);

		this.style = style;

		createContents();
	}

	@Override
	protected Control createContents(Composite parent) {
		tree = new Tree(parent, style | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);

		tree.addSelectionListener(this);

		treeViewer = new TreeViewer(tree);
		treeViewer.setContentProvider(TreeCollectionContentProvider.instance);

		return tree;
	}

	@Override
	protected ISelectionProvider getSelectionProvider(Control contentControl) {
		return treeViewer;
	}

	@Override
	protected void setInput(IObservableList modelProperty) {
		treeViewer.setInput(modelProperty);
	}

	/**
	 * Sets the label provider for this editor
	 *
	 * @param labelProvider
	 *            The label provider for this editor
	 */
	@Override
	public void setLabelProvider(IBaseLabelProvider labelProvider) {
		treeViewer.setLabelProvider(labelProvider);
	}

	@Override
	public IBaseLabelProvider getLabelProvider() {
		return treeViewer.getLabelProvider();
	}

	/**
	 * Gets the tree viewer associated to this editor
	 *
	 * @return the tree viewer associated to this editor
	 */
	public TreeViewer getViewer() {
		return treeViewer;
	}

	@Override
	public void dispose() {
		if (null != tree && !this.tree.isDisposed()) {
			this.tree.removeSelectionListener(this);
		}
		super.dispose();
	}

	@Override
	public void refreshValue() {
		treeViewer.refresh();
	}

}
