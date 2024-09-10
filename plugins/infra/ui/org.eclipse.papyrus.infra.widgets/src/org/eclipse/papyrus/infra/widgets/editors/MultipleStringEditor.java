/*****************************************************************************
 * Copyright (c) 2010, 2017, 2018 CEA LIST.
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
 *  Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 521902, Bug 526304
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 517190
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.editors;

import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.FocusCellOwnerDrawHighlighter;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.TreeViewerEditor;
import org.eclipse.jface.viewers.TreeViewerFocusCellManager;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.widgets.creation.StringEditionFactory;
import org.eclipse.papyrus.infra.widgets.messages.Messages;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.TreeCollectionContentProvider;
import org.eclipse.papyrus.infra.widgets.selectors.StandardSelector;
import org.eclipse.papyrus.infra.widgets.selectors.StringSelector;
import org.eclipse.papyrus.infra.widgets.util.Constants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;


/**
 * An editor for multivalued String attributes
 *
 * @author Camille Letavernier
 * @param <T>
 *
 */
public class MultipleStringEditor<T extends StringSelector> extends MultipleValueEditor<IElementSelector> {

	/**
	 * Constructs an Editor for multiple String values
	 * The widget is a List, with controls to move values up/down, add values
	 * and remove values.
	 *
	 * @param parent
	 *            The Composite in which this editor is created
	 * @param style
	 *            The List's style
	 */
	public MultipleStringEditor(Composite parent, int style) {
		super(parent, style, new StringSelector());
		init();
	}

	/**
	 * Constructs an Editor for multiple String values
	 * The widget is a List, with controls to move values up/down, add values
	 * and remove values.
	 *
	 * @param parent
	 *            The Composite in which this editor is created
	 * @param directCreation
	 *            Indicates if the creation and modification are directed.
	 * @param directCreationWithTreeViewer
	 *            Indicates if the creation and modification are directed on the TreeViewer.
	 * @param style
	 *            The List's style
	 *
	 * @since 3.1
	 */
	public MultipleStringEditor(Composite parent, boolean directCreation, boolean directCreatiuonWithTreeViewer, int style) {
		super(parent, style, new StringSelector());
		setDirectCreation(directCreation);
		setDirectCreationWithTreeViewer(directCreatiuonWithTreeViewer);
		init();
	}

	public MultipleStringEditor(Composite parent, int style, boolean multiline) {
		super(parent, style, new StringSelector(multiline), true, false, null);
		init();
	}

	/**
	 * Constructs an Editor for multiple String values
	 * The widget is a List, with controls to move values up/down, add values
	 * and remove values.
	 *
	 * @param parent
	 *            The Composite in which this editor is created
	 * @param style
	 *            The List's style
	 * @param ordered
	 *            Indicates if the values should be ordered. If true, the up/down controls will be activated
	 * @param unique
	 *            Indicates if the values should be unique.
	 */
	public MultipleStringEditor(Composite parent, int style, boolean ordered, boolean unique) {
		super(parent, style, new StringSelector(), ordered, unique, null);
		init();
	}

	/**
	 * Constructs an Editor for multiple String values
	 * The widget is a List, with controls to move values up/down, add values
	 * and remove values.
	 *
	 * @param parent
	 *            The Composite in which this editor is created
	 * @param style
	 *            The List's style
	 * @param ordered
	 *            Indicates if the values should be ordered. If true, the up/down controls will be activated
	 * @param unique
	 *            Indicates if the values should be unique.
	 * @param label
	 *            The editor's label
	 */
	public MultipleStringEditor(Composite parent, int style, boolean ordered, boolean unique, String label) {
		super(parent, style, new StringSelector(), ordered, unique, label);
		init();
	}

	/**
	 * Constructs an Editor for multiple String values
	 * The widget is a List, with controls to move values up/down, add values
	 * and remove values.
	 *
	 * @param parent
	 *            The Composite in which this editor is created
	 * @param style
	 *            The List's style
	 * @param selector
	 *            The Element selector for the dialog's left-pane. Used to select values or enter new ones.
	 * @param ordered
	 *            Indicates if the values should be ordered. If true, the up/down controls will be activated
	 * @param unique
	 *            Indicates if the values should be unique.
	 * @param label
	 *            The editor's label
	 * @since 3.0
	 */
	public MultipleStringEditor(Composite parent, int style, T selector, boolean ordered, boolean unique, String label) {
		super(parent, style, selector, ordered, unique, label);
		init();
	}

	/**
	 * Constructs an Editor for multiple Integer values
	 * The widget is a List, with controls to move values up/down, add values
	 * and remove values.
	 *
	 * @param parent
	 *            The Composite in which this editor is created
	 * @param style
	 *            The List's style
	 * @param selector
	 *            The Element selector for the dialog's left-pane. Used to select values or enter new ones.
	 * @since 3.0
	 */
	public MultipleStringEditor(Composite parent, int style, T selector) {
		super(parent, style, selector);
		init();
	}

	/**
	 * Constructs an Editor for multiple Integer values
	 * The widget is a List, with controls to move values up/down, add values
	 * and remove values.
	 *
	 * @param parent
	 *            The Composite in which this editor is created
	 * @param style
	 *            The List's style
	 * @param selector
	 *            The Element selector for the dialog's left-pane. Used to select values or enter new ones.
	 * @param directCreation
	 *            Indicates if the creation and modification are directed.
	 * @param directCreationWithTreeViewer
	 *            Indicates if the creation and modification are directed on the TreeViewer.
	 * @since 3.1
	 */
	public MultipleStringEditor(Composite parent, int style, T selector, boolean directCreation, boolean directCreationWithTreeViewer) {
		super(parent, style, selector);
		setDirectCreation(directCreation);
		setDirectCreationWithTreeViewer(directCreationWithTreeViewer);
		init();
	}

	/**
	 * Constructs an Editor for multiple String values
	 * The widget is a List, with controls to move values up/down, add values
	 * and remove values.
	 *
	 * @param parent
	 *            The Composite in which this editor is created
	 * @param style
	 *            The List's style
	 * @param selector
	 *            The Element selector for the dialog's left-pane. Used to select values or enter new ones.
	 * @param label
	 *            The editor's label
	 * @since 3.0
	 */
	public MultipleStringEditor(Composite parent, int style, T selector, String label) {
		super(parent, style, selector, label);
		init();
	}

	/**
	 * Constructs an Editor for multiple String values
	 * The widget is a List, with controls to move values up/down, add values
	 * and remove values.
	 *
	 * @param parent
	 *            The Composite in which this editor is created
	 * @param style
	 *            The List's style
	 * @param label
	 *            The editor's label
	 */
	public MultipleStringEditor(Composite parent, int style, String label) {
		super(parent, style, new StringSelector(), label);
		init();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractMultipleValueEditor#createMultipleValueDialog(org.eclipse.swt.widgets.Composite, org.eclipse.papyrus.infra.widgets.editors.IElementSelector, boolean, boolean, java.lang.String)
	 */
	@Override
	protected MultipleValueDialog createMultipleValueDialog(Composite parent, IElementSelector selector, boolean ordered, boolean unique, String label) {
		return new MultipleValueDialog(parent.getShell(), selector, label, unique, ordered);
	}

	private void init() {
		setFactory(new StringEditionFactory());
	}

	public void setContentProvider(final IStaticContentProvider provider) {
		StandardSelector selector = new StandardSelector(StringCombo::new) {

			@Override
			public void createControls(Composite parent) {
				super.createControls(parent);
				((StringCombo) editor).setProviders(provider, null);
			}
		};
		setSelector(selector);
	}

	/**
	 * {@inheritDoc}
	 *
	 */
	@Override
	public void updateButtons() {
		super.updateButtons();
		edit.setVisible(!isDirectCreationWithTreeViewer());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @since 3.1
	 */
	@Override
	protected void directCreateObject(Object context) {
		if (isDirectCreationWithTreeViewer()) {
			if (referenceFactory != null && referenceFactory.canCreateObject()) {
				getOperationExecutor(context).execute(new Callable<Object>() {

					@Override
					public Object call() {
						Object result = getDefaultValue();
						if (result != null) {
							modelProperty.add(result);
							commit();
						}
						return result;
					}
				}, NLS.bind(Messages.MultipleValueEditor_addOperation, labelText));

				editNewElement();
			}
		} else {
			super.directCreateObject(context);
		}
	}

	/**
	 * Edit the latest element of the TreeViewer.
	 *
	 * @since 3.1
	 */
	protected void editNewElement() {
		Object data = tree.getData();
		if (data instanceof List<?>) {
			int size = ((List) data).size();
			if (0 != size) {
				Object object = ((List) data).get(size - 1);
				if (null != object) {
					treeViewer.editElement(object, 0);
				}
			}
		}
	}

	/**
	 * Returns the default value for each new String value.
	 *
	 * @since 3.1
	 */
	protected Object getDefaultValue() {
		return Constants.DEFAULT_STRING_VALUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite compositeTree = factory.createComposite(parent);
		final TreeColumnLayout treeColumnLayout = new TreeColumnLayout();
		compositeTree.setLayout(treeColumnLayout);

		tree = new Tree(compositeTree, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);

		tree.addSelectionListener(this);

		treeViewer = new TreeViewer(tree);
		treeViewer.setContentProvider(TreeCollectionContentProvider.instance);

		TreeViewerColumn viewerColumn = createTreeViewerColumn(treeViewer, Constants.COLUMN_NAME_VALUE);
		treeColumnLayout.setColumnData(viewerColumn.getColumn(), new ColumnWeightData(100));


		TreeViewerFocusCellManager focusCellManager = new TreeViewerFocusCellManager(
				treeViewer, new FocusCellOwnerDrawHighlighter(treeViewer));
		ColumnViewerEditorActivationStrategy actSupport = new ColumnViewerEditorActivationStrategy(treeViewer) {
			@Override
			protected boolean isEditorActivationEvent(
					ColumnViewerEditorActivationEvent event) {
				return event.eventType == ColumnViewerEditorActivationEvent.TRAVERSAL
						|| event.eventType == ColumnViewerEditorActivationEvent.MOUSE_DOUBLE_CLICK_SELECTION
						|| (event.eventType == ColumnViewerEditorActivationEvent.KEY_PRESSED && event.keyCode == SWT.F2)
						|| event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC;
			}
		};

		int feature = ColumnViewerEditor.TABBING_HORIZONTAL
				| ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR
				| ColumnViewerEditor.TABBING_VERTICAL
				| ColumnViewerEditor.KEYBOARD_ACTIVATION;

		TreeViewerEditor.create(treeViewer, focusCellManager, actSupport, feature);

		viewerColumn.setEditingSupport(new EditingSupport(treeViewer) {

			@Override
			protected void setValue(Object element, Object value) {
				Object newValue = getValueToSet(element, value);

				final int index = modelProperty.indexOf(element);

				if ((element != newValue) && (newValue != null)) {
					modelProperty.set(index, newValue);
				}

				commit();
			}

			@Override
			protected Object getValue(Object element) {
				return getEditingValue(element);
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				return createCellEditor(element);
			}

			@Override
			protected boolean canEdit(Object element) {
				return isDirectCreationWithTreeViewer();
			}
		});

		return compositeTree;
	}

	/**
	 * This allows to create a tree viewer column in the tree viewer.
	 *
	 * @param viewer
	 *            the tree viewer.
	 * @param title
	 *            The title of the column.
	 * @return The created tree viewer column.
	 * @since 3.1
	 */
	protected TreeViewerColumn createTreeViewerColumn(final TreeViewer viewer, final String title) {
		final TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer, SWT.BORDER);
		final TreeColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setResizable(true);
		column.setMoveable(false);
		return viewerColumn;
	}

	/**
	 * Returns the CellEditor for the editing support.
	 *
	 * @since 3.1
	 */
	protected CellEditor createCellEditor(final Object element) {
		return new TextCellEditor(tree);
	}

	/**
	 * Returns the value editing on the editing support.
	 *
	 * @since 3.1
	 */
	protected Object getEditingValue(final Object element) {
		return element.toString();
	}

	/**
	 * Returns the value to set on the editing support.
	 *
	 * @since 3.1
	 */
	protected Object getValueToSet(final Object element, final Object value) {
		return value;
	}
}
