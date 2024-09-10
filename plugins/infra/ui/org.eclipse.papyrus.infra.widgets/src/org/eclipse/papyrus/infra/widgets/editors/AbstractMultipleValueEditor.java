/*****************************************************************************
 * Copyright (c) 2010, 2017, 2018 CEA LIST, Christian W. Damus, and others.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 515808
 *  Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 521902, Bug 526304
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 517190
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.editors;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.Callable;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory;
import org.eclipse.papyrus.infra.widgets.messages.Messages;
import org.eclipse.papyrus.infra.widgets.util.PapyrusSelectionService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.SelectionDialog;

/**
 * A partial implementation of an editor for multivalued properties that does not
 * specify the kind of control in which the values are presented, but provides
 * the controls for adding/editing/moving/etc.&nbsp;those values.
 *
 * @param <T>
 *            the kind of element selector
 * 
 * @author Camille Letavernier
 * @since 3.0
 */
public abstract class AbstractMultipleValueEditor<T extends IElementSelector> extends AbstractListEditor implements SelectionListener, IChangeListener, DisposeListener {

	public static final int MANY = -1;

	/**
	 * A provider of the selection in the control that presents the values.
	 */
	protected ISelectionProvider selectionProvider;

	private ISelectionChangedListener selectionChangedListener;

	/**
	 * A Composite containing the different control buttons
	 * (Add, remove, ...)
	 */
	protected Composite controlsSection;

	protected Control contentControl;

	/**
	 * The Add control
	 */
	protected Button add;

	/**
	 * The Remove control
	 */
	protected Button remove;

	/**
	 * The Up control
	 */
	protected Button up;

	/**
	 * The Down control
	 */
	protected Button down;

	/**
	 * The edit control
	 */
	protected Button edit;

	/**
	 * The element selector for this editor's dialog
	 */
	protected T selector;

	/**
	 * Indicates whether the underlying is ordered
	 */
	protected boolean ordered;

	/**
	 * Indicates whether the underlying contains unique values
	 */
	protected boolean unique;

	/**
	 * The factory for creating and editing values from
	 * this editor
	 */
	protected ReferenceValueFactory referenceFactory;

	/**
	 * Indicates if this editor is readOnly
	 */
	protected boolean readOnly;

	private boolean directCreation;

	private boolean directCreationWithTreeViewer;

	/**
	 * Indicates the maximum number of values selected.
	 */
	protected int upperBound;


	/**
	 *
	 * Constructor.
	 * 
	 * @param <T>
	 *
	 * @param parent
	 *            The Composite in which this Editor should be displayed
	 * @param selector
	 *            The element selector for this editor's dialog
	 * @param ordered
	 *            Specify if the observed collection is ordered. If true, Up and Down controls are displayed.
	 * @param unique
	 *            Specify if the observed collection values are unique.
	 * @param label
	 *            The label for this editor. If null, the label isn't created.
	 */
	protected AbstractMultipleValueEditor(Composite parent, T selector,
			boolean ordered, boolean unique, String label) {

		this(parent, selector, ordered, unique, label, MANY);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param parent
	 *            The Composite in which this Editor should be displayed
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
	protected AbstractMultipleValueEditor(Composite parent, T selector,
			boolean ordered, boolean unique, String label, int upperBound) {

		super(parent, label);

		this.selector = selector;
		this.ordered = ordered;
		this.unique = unique;
		this.directCreationWithTreeViewer = false;

		setUpperBound(upperBound);
	}

	/**
	 * Creates my controls.
	 */
	protected void createContents() {
		setLayout(new GridLayout(label == null ? 1 : 2, false));

		controlsSection = new Composite(this, SWT.NONE);
		controlsSection.setLayout(new FillLayout());
		controlsSection.setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));

		contentControl = createContents(this);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 2;
		gridData.minimumHeight = 80;
		contentControl.setLayoutData(gridData);

		this.selectionProvider = getSelectionProvider(contentControl);
		PapyrusSelectionService.getInstance().setSelectionProvider(this.selectionProvider);

		this.selectionChangedListener = __ -> updateControls();
		this.selectionProvider.addSelectionChangedListener(selectionChangedListener);

		createListControls();

		setLabelProvider(new LabelProvider());
		updateControls();
	}

	protected abstract Control createContents(Composite parent);

	protected abstract ISelectionProvider getSelectionProvider(Control contentControl);

	/**
	 * @deprecated Use/override the {@link #createMultipleValueSelectionDialog(Composite, IElementSelector, boolean, boolean, String)} API, instead.
	 */
	@Deprecated
	protected MultipleValueSelectorDialog createMultipleValueSelectorDialog(Composite parent, IElementSelector selector, boolean ordered, boolean unique, String label) {
		return null;
	}

	/**
	 * Creates the dialog for this editor
	 *
	 * @param parent
	 *            The Composite in which the dialog should be displayed
	 * @param selector
	 *            The element selector for this dialog
	 * @param ordered
	 *            Specify if the observed collection is ordered. If true, Up and Down controls are displayed.
	 * @param unique
	 *            Specify if the observed collection values are unique.
	 * @param label
	 *            The editor's label.
	 * @return The new dialog for this editor
	 * 
	 * @deprecated since 3.3 Use/override the {@link #createMultipleValueDialog(Composite, IElementSelector, boolean, boolean, String)} instead.
	 * 
	 */
	@Deprecated
	protected MultipleValueSelectionDialog createMultipleValueSelectionDialog(Composite parent, IElementSelector selector, boolean ordered, boolean unique, String label) {
		return new MultipleValueSelectionDialog(parent.getShell(), selector, label, unique, ordered);
	}

	/**
	 * Creates the dialog for this editor
	 *
	 * @param parent
	 *            The Composite in which the dialog should be displayed
	 * @param selector
	 *            The element selector for this dialog
	 * @param ordered
	 *            Specify if the observed collection is ordered. If true, Up and Down controls are displayed.
	 * @param unique
	 *            Specify if the observed collection values are unique.
	 * @param label
	 *            The editor's label.
	 * @return The new dialog for this editor
	 * @since 3.3
	 */
	protected MultipleValueDialog createMultipleValueDialog(Composite parent, IElementSelector selector, boolean ordered, boolean unique, String label) {
		return new MultipleValueSelectionDialog(parent.getShell(), selector, label, unique, ordered);
	}

	@Override
	protected GridData getLabelLayoutData() {
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, false);
		return data;
	}

	public void setSelector(T selector) {
		this.selector = selector;
	}

	protected void updateControls() {

		boolean enableAddAction = true;
		if (directCreation) {
			if (referenceFactory == null || !referenceFactory.canCreateObject()) {
				enableAddAction = false;
			}
		}

		add.setEnabled(!readOnly && enableAddAction);
		remove.setEnabled(!readOnly);

		if (ordered) {
			up.setVisible(true);
			down.setVisible(true);
			up.setEnabled(!readOnly);
			down.setEnabled(!readOnly);
		} else {
			up.setVisible(false);
			down.setVisible(false);
		}

		if (edit != null) {
			edit.setEnabled(this.referenceFactory != null && referenceFactory.canEdit() && !readOnly);
		}

		if (modelProperty != null && this.upperBound != MANY) {
			if (modelProperty.size() >= this.upperBound) {
				add.setEnabled(false);
			}
		}

		updateButtons();

	}

	/**
	 * Sets the label provider for this editor
	 *
	 * @param labelProvider
	 *            The label provider for this editor
	 */
	public abstract void setLabelProvider(IBaseLabelProvider labelProvider);

	public abstract IBaseLabelProvider getLabelProvider();

	@Override
	protected void doBinding() {
		// We don't do a real Databinding in this case
		setInput(modelProperty);
		modelProperty.addChangeListener(this);
	}

	/**
	 * Sets the the given model property as the input into whatever is my
	 * content viewer.
	 * 
	 * @param modelProperty
	 *            the model property to present
	 * 
	 * @see #createContentControl(Composite, int)
	 */
	protected abstract void setInput(IObservableList modelProperty);

	/**
	 * @param ordered
	 */
	public void setOrdered(boolean ordered) {
		this.ordered = ordered;

		if (controlsSection != null) {
			updateControls();
		}
	}

	/**
	 * @param unique
	 */
	public void setUnique(boolean unique) {
		this.unique = unique;

		if (controlsSection != null) {
			updateControls();
		}
	}

	/**
	 * Creates the Add/Remove controls,
	 * and the Up/Down controls if the collection is ordered
	 *
	 * @param ordered
	 */
	protected void createListControls() {
		up = createButton(Activator.getDefault().getImage("/icons/Up_12x12.gif"), Messages.MultipleValueEditor_MoveSelectedElementsUp); //$NON-NLS-1$
		down = createButton(Activator.getDefault().getImage("/icons/Down_12x12.gif"), Messages.MultipleValueEditor_MoveSelectedElementsDown); //$NON-NLS-1$
		add = createButton(Activator.getDefault().getImage("/icons/Add_12x12.gif"), Messages.MultipleValueEditor_AddElements); //$NON-NLS-1$
		remove = createButton(Activator.getDefault().getImage("/icons/Delete_12x12.gif"), Messages.MultipleValueEditor_RemoveSelectedElements); //$NON-NLS-1$
		edit = createButton(Activator.getDefault().getImage("/icons/Edit_12x12.gif"), Messages.MultipleValueEditor_EditSelectedValue); //$NON-NLS-1$
	}

	protected Button createButton(Image image, String toolTipText) {
		Button button = new Button(controlsSection, SWT.PUSH);
		button.setImage(image);
		button.addSelectionListener(this);
		button.setToolTipText(toolTipText);
		return button;
	}

	@Override
	public Object getEditableType() {
		return Collection.class;
	}

	/**
	 * Handle events occuring on controls
	 *
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 *
	 * @param e
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		if (e.widget == null || e.widget.isDisposed()) {
			return;
		}
		try {
			if (e.widget == add) {
				if (this.upperBound == MANY || modelProperty.size() < this.upperBound) {
					addAction();
				}
			} else if (e.widget == remove) {
				removeAction();
			} else if (e.widget == up) {
				upAction();
			} else if (e.widget == down) {
				downAction();
			} else if (e.widget == edit) {
				editAction();
			}
		} catch (OperationCanceledException canceledException) {
			// do nothing, this exception occurs whenever one of the actions above
			// gets canceled
		}
		if (!isDisposed() && (controlsSection != null)) {
			updateControls();
		}
	}

	/**
	 * Handle add Action
	 */
	protected void addAction() {
		final Object context = getContextElement();

		if (directCreation) {
			directCreateObject(context);
			return;
		}

		getOperationExecutor(context).execute(new Callable<Object[]>() {

			@Override
			public Object[] call() {
				String dialogLabel = label == null ? null : label.getText();
				SelectionDialog dialog = createSelectionDialog(getParent(), selector, ordered, unique, dialogLabel, context);

				if (modelProperty != null) {
					dialog.setInitialSelections(modelProperty.toArray());
				} else {
					dialog.setInitialSelections(new Object[0]);
				}

				int returnCode = dialog.open();
				if (returnCode == Window.CANCEL) {
					// Clear out the element selector in case we open this dialog again
					selector.clearTemporaryElements();

					// Roll back whatever has been done, so far
					throw new OperationCanceledException();
				}

				modelProperty.clear();

				Object[] result = dialog.getResult();
				if (result != null) {
					modelProperty.addAll(Arrays.asList(result));
					commit();
				}

				return result;
			}
		}, NLS.bind(Messages.MultipleValueEditor_addOperation, labelText));
	}

	@SuppressWarnings("deprecation")
	private SelectionDialog createSelectionDialog(Composite parent, IElementSelector selector, boolean ordered, boolean unique, String label, Object context) {
		SelectionDialog result;

		// These dialogs need a complete label provider
		ILabelProvider labelProvider = Optional.of(getLabelProvider())
				.filter(ILabelProvider.class::isInstance).map(ILabelProvider.class::cast)
				.orElseGet(LabelProvider::new);

		MultipleValueSelectorDialog oldStyle = createMultipleValueSelectorDialog(parent, selector, ordered, unique, label);
		if (oldStyle != null) {
			oldStyle.setLabelProvider(labelProvider);
			oldStyle.setFactory(referenceFactory);
			oldStyle.setUpperBound(upperBound);
			oldStyle.setContextElement(context);
			result = oldStyle;
		} else {
			MultipleValueSelectionDialog newStyle = createMultipleValueSelectionDialog(parent, selector, ordered, unique, label);
			newStyle.setLabelProvider(labelProvider);
			newStyle.setFactory(referenceFactory);
			newStyle.setUpperBound(upperBound);
			newStyle.setContextElement(context);
			result = newStyle;
		}

		return result;
	}

	@Override
	protected void commit() {
		super.commit();

		if (!isDisposed()) {
			refreshValue();
		}
	}

	/**
	 * Handle remove Action
	 */
	protected void removeAction() {
		IStructuredSelection selection = getSelection();
		for (Object value : selection.toArray()) {
			modelProperty.remove(value);
		}
		selectionProvider.setSelection(StructuredSelection.EMPTY);

		commit();
	}

	/**
	 * Handle up Action
	 */
	protected void upAction() {
		IStructuredSelection selection = getSelection();
		for (Object o : selection.toArray()) {
			int oldIndex = modelProperty.indexOf(o);
			if (oldIndex > 0) {
				modelProperty.move(oldIndex, oldIndex - 1);
			}
		}

		IStructuredSelection selectionCopy = new StructuredSelection(selection.toArray());
		selectionProvider.setSelection(selectionCopy);

		commit();
	}

	/**
	 * Handle down Action
	 */
	protected void downAction() {
		IStructuredSelection selection = getSelection();

		int maxIndex = modelProperty.size() - 1;

		Object[] selectionArray = selection.toArray();
		for (int i = selectionArray.length - 1; i >= 0; i--) {
			Object o = selectionArray[i];
			int oldIndex = modelProperty.indexOf(o);
			if (-1 != oldIndex && oldIndex < maxIndex) {
				modelProperty.move(oldIndex, oldIndex + 1);
			}
		}

		IStructuredSelection selectionCopy = new StructuredSelection(selection.toArray());
		selectionProvider.setSelection(selectionCopy);

		commit();
	}

	/**
	 * Handle edit Action
	 */
	protected void editAction() {
		IStructuredSelection selection = getSelection();

		if (selection.size() != 1) {
			return;
		}

		final Object currentValue = selection.getFirstElement();
		final int index = modelProperty.indexOf(currentValue);

		getOperationExecutor(currentValue).execute(new Runnable() {

			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				Object newValue = referenceFactory.edit(AbstractMultipleValueEditor.this.edit, currentValue);

				if ((newValue != currentValue) && (newValue != null)) {
					modelProperty.set(index, newValue);
				}

				commit();
			}
		}, NLS.bind(Messages.MultipleValueEditor_editOperation, labelText));
	}

	/**
	 * Sets the {@link ReferenceValueFactory} for this editor. The {@link ReferenceValueFactory} is used to create
	 * new instances and edit existing ones.
	 *
	 * @param factory
	 *            The {@link ReferenceValueFactory} to be used by this editor
	 */
	public void setFactory(ReferenceValueFactory factory) {
		this.referenceFactory = factory;

		if (controlsSection != null) {
			updateControls();
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		if ((e.widget == contentControl) && (edit != null) && edit.isEnabled()) {
			editAction();
		}
	}

	/**
	 * Gets the control that presents the contents of the multi-valued property.
	 *
	 * @return the content control
	 */
	public Control getContentControl() {
		return contentControl;
	}

	/**
	 * Refreshes the viewer when a change occurs on the ObservableList
	 * TODO : Problem : a change occurring on an element of the list is not sent here
	 * TODO : When undoing a command, the change event is not received (Although it modifies the list itself)
	 *
	 * @see org.eclipse.core.databinding.observable.IChangeListener#handleChange(org.eclipse.core.databinding.observable.ChangeEvent)
	 *
	 * @param event
	 */
	@Override
	public void handleChange(ChangeEvent event) {
		if (!isDisposed()) {
			refreshValue();
		}
	}

	@Override
	public void dispose() {
		if (modelProperty != null) {
			modelProperty.removeChangeListener(this);
		}

		if (selectionProvider != null) {
			selectionProvider.removeSelectionChangedListener(selectionChangedListener);
			selectionChangedListener = null;

			PapyrusSelectionService.getInstance().unsetSelectionProvider(selectionProvider);
			selectionProvider = null;
		}

		if (add != null) {
			add.removeSelectionListener(this);
			add = null;
		}
		if (edit != null) {
			edit.removeSelectionListener(this);
			edit = null;
		}
		if (up != null) {
			up.removeSelectionListener(this);
			up = null;
		}
		if (down != null) {
			down.removeSelectionListener(this);
			down = null;
		}
		if (remove != null) {
			remove.removeSelectionListener(this);
			remove = null;
		}

		super.dispose();
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;

		if (controlsSection != null) {
			updateControls();
		}
	}

	@Override
	public boolean isReadOnly() {
		return readOnly;
	}

	@Override
	public void setLabel(String label) {
		if (this.label == null) {
			setLayout(new GridLayout(2, false));
		}
		super.setLabel(label);
	}

	@Override
	public void setToolTipText(String text) {
		contentControl.setToolTipText(text);
		super.setLabelToolTipText(text);
	}

	@Override
	public void setModelObservable(IObservableList modelProperty) {
		super.setModelObservable(modelProperty);

		if (controlsSection != null) {
			updateControls();
		}
	}

	@Override
	public abstract void refreshValue();

	/**
	 * Sets the direct creation mode.
	 * If direct creation is set to true, the {@link ReferenceValueFactory#createObject(org.eclipse.swt.widgets.Control)} method will be called when
	 * to add button is pressed.
	 * Otherwise, the dialog will be used.
	 *
	 * @param directCreation
	 */
	public void setDirectCreation(boolean directCreation) {
		this.directCreation = directCreation;

		if (controlsSection != null) {
			updateControls();
		}
	}

	/**
	 * Set the direct creation on the TreeViewer.
	 * 
	 * @param directCreationWithTreeViewer
	 * 
	 * @since 3.1
	 */
	public void setDirectCreationWithTreeViewer(boolean directCreationWithTreeViewer) {
		this.directCreationWithTreeViewer = directCreationWithTreeViewer;

		if (controlsSection != null) {
			updateControls();
		}
	}

	/**
	 * Returns the boolean for the direct creation.
	 * 
	 * @return the directCreation value.
	 * 
	 * @since 3.1
	 */
	public boolean isDirectCreation() {
		return directCreation;
	}

	/**
	 * Returns the boolean for the direct creation.
	 * 
	 * @return the directCreation value.
	 * 
	 * @since 3.1
	 */
	public boolean isDirectCreationWithTreeViewer() {
		return directCreationWithTreeViewer;
	}

	/**
	 * Adds a ISelectionChangedListener to this widget
	 *
	 * @param listener
	 */
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		if (selectionProvider != null) {
			selectionProvider.addSelectionChangedListener(listener);
		}
	}

	/**
	 * Removes a ISelectionChangedListener from this widget
	 *
	 * @param listener
	 */
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		if (selectionProvider != null) {
			selectionProvider.removeSelectionChangedListener(listener);
		}
	}

	/**
	 * Obtains the current selection.
	 * 
	 * @return the current selection
	 */
	protected IStructuredSelection getSelection() {
		return Optional.ofNullable(selectionProvider)
				.map(ISelectionProvider::getSelection)
				.filter(IStructuredSelection.class::isInstance)
				.map(IStructuredSelection.class::cast)
				.orElse(StructuredSelection.EMPTY);
	}

	/**
	 * Set the maximum number of values selected.
	 *
	 * @param upperBound
	 */
	public void setUpperBound(int upperBound) {
		this.upperBound = upperBound;
	}

	public void updateButtons() {
		/* Disable the button 'add' if the upperBound is reached */
		if (modelProperty != null && this.upperBound != MANY) {
			if (modelProperty.size() >= this.upperBound) {
				add.setEnabled(false);
			} else {
				add.setEnabled(true);
			}
		}

		// manage enable button according to the selection

		Object selection = getFirstSelection();
		if (null == selection) {
			up.setEnabled(false);
			down.setEnabled(false);
			remove.setEnabled(false);
			if (null != edit) {
				edit.setEnabled(false);
			}
		} else if (null != modelProperty) {
			int index = modelProperty.indexOf(selection);
			if (0 == index || -1 == index) {
				up.setEnabled(false);
			}
			if (modelProperty.size() == index + 1 || -1 == index) {
				down.setEnabled(false);
			}
		}


	}

	private Object getFirstSelection() {
		return getSelection().getFirstElement();
	}

	/**
	 * Create an object with the direct creation.
	 * 
	 * @param context
	 *            The context element.
	 * 
	 * @since 3.1
	 * 
	 */
	protected void directCreateObject(final Object context) {
		if (referenceFactory != null && referenceFactory.canCreateObject()) {
			getOperationExecutor(context).execute(new Callable<Object>() {

				@Override
				public Object call() {
					Object result = referenceFactory.createObject(AbstractMultipleValueEditor.this, context);
					if (result != null) {
						modelProperty.add(result);
						commit();
					}
					return result;
				}
			}, NLS.bind(Messages.MultipleValueEditor_addOperation, labelText));
		}
	}

	@Override
	public void changeColorField() {
		// nothing to do here
	}


}
