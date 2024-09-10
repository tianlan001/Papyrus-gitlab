/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.widgets.editors;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory;
import org.eclipse.papyrus.infra.widgets.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

/**
 * This class allow to define a Tree Reference Value Editor.
 */
public abstract class TreeReferenceValueEditor extends AbstractValueEditor implements SelectionListener {

	/**
	 * The Button used to create a new instance
	 */
	protected Button createInstanceButton;

	/**
	 * The Button used to unset the current value
	 */
	protected Button unsetButton;

	/**
	 * The label provider used to display the values in both the label and the
	 * selection dialog
	 */
	protected ILabelProvider labelProvider;

	/**
	 * The current value for this editor
	 */
	protected Object value;

	/**
	 * The factory used to create or edit objects directly from this editor
	 */
	protected ReferenceValueFactory valueFactory;

	/**
	 * TreeViewer to show
	 */
	protected TreeViewer treeViewer;

	/**
	 * The tree of the TreeViewer.
	 */
	protected Tree tree;

	/**
	 * The parent composite of the tree viewer.
	 */
	protected Composite compositeTree;

	/**
	 * Listener to modify IObservableValue when modelProperty changed.
	 */
	private IChangeListener changeListener = new IChangeListener() {

		@Override
		public void handleChange(ChangeEvent event) {
			setWidgetObservable(createWidgetObservable(modelProperty));
			checkCreateInstanceButton();
			setValueRootContentProvider();
			if (null != treeViewer) {
				treeViewer.refresh();
				if (null != tree && null != tree.getTopItem()) {
					treeViewer.expandToLevel(tree.getTopItem().getData(), 10);
				}
			}
		}
	};

	/**
	 *
	 * Constructs a new ReferenceDialog in the given parent Composite. The style
	 * will be applied to the CLabel displaying the current value.
	 *
	 * @param parent
	 *            The parent composite.
	 * @param style
	 *            The style for the created widget.
	 */
	public TreeReferenceValueEditor(final Composite parent, final int style) {
		super(parent, style);

		((GridLayout) getLayout()).numColumns += 1;

		// Create a parent composite to set the margin
		Composite gridComposite = factory.createComposite(this);
		gridComposite.setLayout(new GridLayout());
		gridComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		compositeTree = factory.createComposite(gridComposite);
		final TreeColumnLayout treeColumnLayout = new TreeColumnLayout();
		compositeTree.setLayout(treeColumnLayout);
		final GridData compositeTreeGridData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		compositeTreeGridData.minimumHeight = 150;
		compositeTree.setLayoutData(compositeTreeGridData);

		treeViewer = new TreeViewer(compositeTree, SWT.MULTI | SWT.NO_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);
		tree = treeViewer.getTree();
		tree.setLinesVisible(true);
		tree.setHeaderVisible(true);

		final TreeViewerColumn columnProperty = new TreeViewerColumn(treeViewer, SWT.LEFT);
		columnProperty.getColumn().setAlignment(SWT.LEFT);
		columnProperty.getColumn().setText(Messages.TreeReferenceValueEditor_NameColumnProperty); 
		treeColumnLayout.setColumnData(columnProperty.getColumn(), new ColumnWeightData(100));
		final TreeViewerColumn columnValue = new TreeViewerColumn(treeViewer, SWT.RIGHT);
		columnValue.getColumn().setAlignment(SWT.LEFT);
		columnValue.getColumn().setText(Messages.TreeReferenceValueEditor_NameColumnValue); 
		treeColumnLayout.setColumnData(columnValue.getColumn(), new ColumnWeightData(200));

		columnValue.setEditingSupport(createEditingSupport());

		createButtons();
	}

	/**
	 * Create the buttons.
	 */
	protected void createButtons() {
		((GridLayout) getLayout()).numColumns += 1;

		// Create a parent composite to set add the buttons on the top
		final Composite gridComposite = factory.createComposite(this);
		gridComposite.setLayout(new GridLayout(2, true));
		gridComposite.setLayoutData(new GridData(SWT.NONE, SWT.BEGINNING, false, false));

		createInstanceButton = factory.createButton(gridComposite, null, SWT.PUSH);
		createInstanceButton.setImage(Activator.getDefault().getImage("/icons/Add_12x12.gif")); //$NON-NLS-1$
		createInstanceButton.setToolTipText(Messages.ReferenceDialog_CreateANewObject);
		createInstanceButton.addSelectionListener(this);

		unsetButton = factory.createButton(gridComposite, null, SWT.PUSH);
		unsetButton.setImage(Activator.getDefault().getImage("/icons/Delete_12x12.gif")); //$NON-NLS-1$
		unsetButton.setToolTipText(Messages.ReferenceDialog_UnsetValue);
		unsetButton.addSelectionListener(this);

		if (null != treeViewer && treeViewer.getSelection() != null && !treeViewer.getSelection().isEmpty()) {
			unsetButton.setEnabled(true);
		} else {
			unsetButton.setEnabled(false);
		}
	}

	/**
	 * The action executed when the "create" button is selected Create a new
	 * instance and assign it to this reference.
	 */
	protected void createAction() {
		if (valueFactory != null && valueFactory.canCreateObject()) {
			final Object context = getContextElement();
			getOperationExecutor(context).execute(new Runnable() {

				@Override
				public void run() {
					Object value = valueFactory.createObject(createInstanceButton, context);
					if (null == value) {
						// Cancel the operation
						throw new OperationCanceledException();
					}

					Collection<Object> validatedObjects = valueFactory.validateObjects(Collections.singleton(value));
					if (!validatedObjects.isEmpty()) {
						setValue(validatedObjects.iterator().next());
						if (null != tree) {
							int itemsSize = tree.getItems().length;
							TreeItem item = tree.getItem(itemsSize - 1);
							if (null != item) {
								treeViewer.expandToLevel(item.getData(), TreeViewer.ALL_LEVELS);
							}
						}
						checkCreateInstanceButton();
					}
				}
			}, NLS.bind(Messages.ReferenceDialog_setOperation, labelText));
		}
	}

	/**
	 * Sets the Label provider for this editor If the label provider is null, a
	 * default one will be used. The same label provider is used for both the
	 * editor's label and the selection dialog.
	 *
	 * @param provider
	 *            The label provider
	 */
	public void setLabelProvider(final ILabelProvider provider) {
		if (null == provider) {
			setLabelProvider(new LabelProvider());
			return;
		}
		this.labelProvider = provider;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractValueEditor#getValue()
	 */
	@Override
	public Object getValue() {
		if (null != modelProperty) {
			return modelProperty.getValue();
		}
		return value;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractEditor#getEditableType()
	 */
	@Override
	public Object getEditableType() {
		return Object.class;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractValueEditor#setModelObservable(org.eclipse.core.databinding.observable.value.IObservableValue)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void setModelObservable(final IObservableValue modelProperty) {
		if (null != modelProperty) {
			this.value = modelProperty.getValue();
		}
		super.setModelObservable(modelProperty);
		setWidgetObservable(createWidgetObservable(modelProperty));
		addListeners();
		checkCreateInstanceButton();
	}

	/**
	 * This allows to set the Reference value factory.
	 * 
	 * @param factory
	 *            The reference value factory.
	 */
	public void setValueFactory(final ReferenceValueFactory factory) {
		this.valueFactory = factory;
	}

	/**
	 * Get the Reference value factory.
	 * 
	 * @return The Reference value factory.
	 */
	public ReferenceValueFactory getValueFactory() {
		return valueFactory;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(final SelectionEvent e) {
		Widget widget = e.widget;
		if (widget == createInstanceButton) {
			createAction();
		} else if (widget == unsetButton) {
			unsetAction();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetDefaultSelected(final SelectionEvent e) {
		// Do nothing
	}

	/**
	 * Set value of the modelProperty and create the widget observable.
	 *
	 * @param value
	 *            The new value.
	 */
	@SuppressWarnings("unchecked")
	public void setValue(final Object value) {
		removeListeners();
		this.value = value;
		if (null != modelProperty) {
			modelProperty.setValue(value);
			setWidgetObservable(createWidgetObservable(modelProperty));
			addListeners();
		}

		if (null != treeViewer) {
			IContentProvider treeViewerContentProvider = treeViewer.getContentProvider();
			if (null != treeViewerContentProvider) {
				setValueRootContentProvider();
				treeViewer.refresh();
			}
		}

		commit();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractEditor#isReadOnly()
	 */
	@Override
	public boolean isReadOnly() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractEditor#setReadOnly(boolean)
	 */
	@Override
	public void setReadOnly(final boolean readOnly) {
		// Do Nothing
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.swt.widgets.Widget#dispose()
	 */
	@Override
	public void dispose() {
		removeListeners();
		super.dispose();
	}

	/**
	 * Add all the listeners.
	 */
	protected void addListeners() {
		if (modelProperty != null) {
			modelProperty.addChangeListener(changeListener);
		}
	};

	/**
	 * Remove all the listeners.
	 */
	protected void removeListeners() {
		if (modelProperty != null) {
			modelProperty.removeChangeListener(changeListener);
		}
	};

	/**
	 * Set the value root to the content provider of the tree viewer.
	 */
	public abstract void setValueRootContentProvider();

	/**
	 * Set the providers of the TreeViewer.
	 */
	public abstract void setProvidersTreeViewer();

	/**
	 * Create the Editing Support.
	 *
	 * @return The EditingSupport created.
	 */
	public abstract EditingSupport createEditingSupport();

	/**
	 * This allow to create the widget observable value.
	 *
	 * @param modelProperty
	 *            The current observable value.
	 * @return The created IObservableValue.
	 */
	@SuppressWarnings("rawtypes")
	public abstract IObservableValue createWidgetObservable(final IObservableValue modelProperty);

	/**
	 * Verify if the CreateInstance button should be enabled or not.
	 */
	public abstract void checkCreateInstanceButton();

	/**
	 * Action associated to the Unset button.
	 */
	public abstract void unsetAction();
}
