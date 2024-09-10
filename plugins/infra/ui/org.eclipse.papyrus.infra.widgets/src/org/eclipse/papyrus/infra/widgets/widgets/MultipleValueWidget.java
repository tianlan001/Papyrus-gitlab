/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.widgets.widgets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory;
import org.eclipse.papyrus.infra.widgets.editors.IElementSelectionListener;
import org.eclipse.papyrus.infra.widgets.editors.IElementSelector;
import org.eclipse.papyrus.infra.widgets.messages.Messages;
import org.eclipse.papyrus.infra.widgets.providers.CollectionContentProvider;
import org.eclipse.papyrus.infra.widgets.util.ValueUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.services.IDisposable;

/**
 * The widget for the simple multi value (without selection widget).
 *
 * @since 3.3
 */
public class MultipleValueWidget implements ISelectionChangedListener, IDoubleClickListener, IElementSelectionListener, SelectionListener, IDisposable {

	/**
	 * The object selector.
	 */
	protected IElementSelector selector;

	/**
	 * The up/down buttons section.
	 */
	protected Composite rightButtonsSection;

	/**
	 * The listViewer for chosen elements.
	 */
	protected TreeViewer selectedElementsViewer;

	/**
	 * The list for chosen elements.
	 */
	protected Tree selectedElements;

	/**
	 * The create action button.
	 */
	protected Button create;

	/**
	 * The delete action button.
	 */
	protected Button delete;

	/**
	 * the up action button.
	 */
	protected Button up;

	/**
	 * the down action button.
	 */
	protected Button down;

	/**
	 * The label provider for the listViewer of chosen elements.
	 */
	protected ILabelProvider labelProvider;

	/**
	 * The currently chosen elements.
	 */
	protected final Collection<Object> allElements;

	/**
	 * Indicates if the values should be unique (according to Object.equals()).
	 */
	protected boolean unique;

	/**
	 * Indicates if the list is ordered.
	 */
	protected boolean ordered;

	/**
	 * The factory for creating new elements.
	 */
	protected ReferenceValueFactory factory;

	/**
	 * The model element being edited (if any), to which elements are to be added or removed.
	 */
	protected Object contextElement;

	/**
	 * The list of newly created objects.
	 */
	protected Set<Object> newObjects = new HashSet<>();


	/**
	 * The maximum number of values selected.
	 */
	protected int upperBound;

	/**
	 * the initial selection.
	 */
	protected List<?> initialSelection;

	/**
	 * Constructor.
	 *
	 * @param selector
	 *            The element selector used by this dialog
	 */
	public MultipleValueWidget(IElementSelector selector) {
		this(selector, false, false);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param selector
	 *            The element selector used by this dialog
	 * @param unique
	 *            True if the values returned by this dialog should be unique
	 */
	public MultipleValueWidget(IElementSelector selector, boolean unique) {
		this(selector, unique, false);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param selector
	 *            The element selector used by this dialog
	 * @param unique
	 *            True if the values returned by this dialog should be unique
	 */
	public MultipleValueWidget(IElementSelector selector, boolean unique, boolean ordered) {
		this(selector, unique, false, ValueUtils.MANY);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param selector
	 *            The element selector used by this dialog
	 * @param unique
	 *            True if the values returned by this dialog should be unique
	 * @param upperBound
	 *            The maximum number of values selected.
	 */
	public MultipleValueWidget(IElementSelector selector, boolean unique, boolean ordered, int upperBound) {
		Assert.isNotNull(selector, "The element selector should be defined"); //$NON-NLS-1$
		this.selector = selector;
		allElements = unique ? new LinkedHashSet<>() : new LinkedList<>();
		this.unique = unique;
		this.ordered = ordered;
		this.upperBound = upperBound;
		selector.addElementSelectionListener(this);
		init();
	}

	/**
	 * initialize the widget if required
	 */
	protected void init() {
		// nothing to do
	}

	/**
	 * Create the contents of the dialog
	 *
	 * @return
	 * 		the the composite which encapsulate all the sub composite
	 */
	public final Composite create(Composite composite) {
		Composite parent = createContents(composite);
		updateControls();
		return parent;
	}

	/**
	 * Create the contents of the dialog
	 *
	 * @return
	 * 		the the composite which encapsulate all the sub composite
	 */
	protected Composite createContents(Composite parent) {
		Object parentLayout = parent.getLayout();
		GridLayout layout = null;
		if (parentLayout instanceof GridLayout) {
			layout = (GridLayout) parentLayout;
		} else {
			parent = new Composite(parent, SWT.NONE);
			layout = new GridLayout();
			parent.setLayout(layout);
		}

		createSections(parent);

		allElements.clear();
		allElements.addAll(getInitialElementSelections());
		return parent;
	}

	/**
	 * This allows to create the sections of the dialog.
	 *
	 * @param parent
	 *            The parent composite.
	 */
	protected void createSections(final Composite parent) {
		final Composite selectedPane = new Composite(parent, SWT.NONE);
		selectedPane.setLayout(new GridLayout(2, false));
		selectedPane.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createListSection(selectedPane);
		createRightButtonsSection(selectedPane);
	}


	public void setInitialSelections(List<?> selectedElements) {
		this.initialSelection = selectedElements;
		allElements.clear();
		allElements.addAll(getInitialElementSelections());
	}

	/**
	 * @return
	 */
	private Collection<? extends Object> getInitialElementSelections() {
		if (this.initialSelection == null) {
			return Collections.emptyList();
		}
		return this.initialSelection;
	}

	/**
	 * Creates the list displaying the currently selected elements
	 *
	 * @param parent
	 *            The composite in which the section is created
	 */
	protected void createListSection(Composite parent) {

		selectedElements = new Tree(parent, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		// selectedElements.addSelectionListener(this);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		selectedElements.setLayoutData(data);
		selectedElementsViewer = new TreeViewer(selectedElements);

		selectedElementsViewer.addSelectionChangedListener(this);
		selectedElementsViewer.addDoubleClickListener(this);

		selectedElementsViewer.setContentProvider(createListSectionContentProvider());

		if (labelProvider != null) {
			selectedElementsViewer.setLabelProvider(labelProvider);
		}

		selectedElementsViewer.setInput(allElements);
		selector.setSelectedElements(allElements.toArray());
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.wizard.pages.MultipleValueSelectionWizard#createListSectionContentProvider()
	 *
	 * @return
	 * 		the content provider to use in the list section (right part)
	 */
	protected IContentProvider createListSectionContentProvider() {
		return CollectionContentProvider.instance;
	}

	/**
	 * Creates the up/down controls section
	 *
	 * @param parent
	 *            The composite in which the section is created
	 */
	protected void createRightButtonsSection(Composite parent) {
		rightButtonsSection = new Composite(parent, SWT.NONE);
		rightButtonsSection.setLayout(new GridLayout(1, true));

		up = new Button(rightButtonsSection, SWT.PUSH);
		up.setImage(Activator.getDefault().getImage("/icons/Up_12x12.gif")); //$NON-NLS-1$
		up.addSelectionListener(this);
		up.setToolTipText(Messages.MultipleValueEditor_MoveSelectedElementsUp);

		down = new Button(rightButtonsSection, SWT.PUSH);
		down.setImage(Activator.getDefault().getImage("/icons/Down_12x12.gif")); //$NON-NLS-1$
		down.addSelectionListener(this);
		down.setToolTipText(Messages.MultipleValueEditor_MoveSelectedElementsDown);

		create = new Button(rightButtonsSection, SWT.PUSH);
		create.setImage(Activator.getDefault().getImage("/icons/Add_12x12.gif")); //$NON-NLS-1$
		create.addSelectionListener(this);
		create.setToolTipText(Messages.MultipleValueSelectorDialog_CreateNewElement);

		delete = new Button(rightButtonsSection, SWT.PUSH);
		delete.setImage(Activator.getDefault().getImage("/icons/Delete_12x12.gif")); //$NON-NLS-1$
		delete.addSelectionListener(this);
		delete.setToolTipText(Messages.MultipleValueSelectorDialog_DeleteNewElement);
		delete.setEnabled(false);
	}

	/**
	 * Sets the label provider used to display the selected elements
	 *
	 * @param labelProvider
	 */
	public void setLabelProvider(ILabelProvider labelProvider) {
		this.labelProvider = labelProvider;
	}

	/**
	 * {@inheritDoc} Handles the events on one of the control buttons
	 *
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 *
	 * @param e
	 *            The event that occurred
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		if (e.widget == up) {
			upAction();
		} else if (e.widget == down) {
			downAction();
		} else if (e.widget == create) {
			createAction();
		} else if (e.widget == delete) {
			deleteAction();
		}

		updateControls();
	}

	/**
	 * Sets the {@link ReferenceValueFactory} for this editor. The {@link ReferenceValueFactory} is used to create
	 * new instances and edit existing ones.
	 *
	 * @param factory
	 *            The {@link ReferenceValueFactory} to be used by this editor
	 */
	public void setFactory(ReferenceValueFactory factory) {
		this.factory = factory;
		updateControls();
	}

	/**
	 * Handles the "Up" action
	 */
	protected void upAction() {
		IStructuredSelection selection = (IStructuredSelection) selectedElementsViewer.getSelection();

		// We need a list to move objects. LinkedHashSet can't do that
		java.util.List<Object> list = new LinkedList<>(allElements);
		for (Object o : selection.toArray()) {
			int oldIndex = list.indexOf(o);
			if (oldIndex > 0) {
				move(list, oldIndex, oldIndex - 1);
			}
		}

		allElements.clear();
		allElements.addAll(list);

		IStructuredSelection selectionCopy = new StructuredSelection(selection.toArray());
		selectedElementsViewer.setSelection(selectionCopy);
		selectedElementsViewer.refresh();
	}

	/**
	 * Handles the "Down" action
	 */
	protected void downAction() {
		IStructuredSelection selection = (IStructuredSelection) selectedElementsViewer.getSelection();

		// We need a list to move objects. LinkedHashSet can't do that
		java.util.List<Object> list = new LinkedList<>(allElements);

		int maxIndex = list.size() - 1;

		Object[] selectionArray = selection.toArray();
		for (int i = selectionArray.length - 1; i >= 0; i--) {
			Object o = selectionArray[i];
			int oldIndex = list.indexOf(o);
			if (oldIndex < maxIndex) {
				move(list, oldIndex, oldIndex + 1);
			}
		}

		allElements.clear();
		allElements.addAll(list);

		IStructuredSelection selectionCopy = new StructuredSelection(selection.toArray());
		selectedElementsViewer.setSelection(selectionCopy);
		selectedElementsViewer.refresh();
	}

	/**
	 * Handles the "Create" action
	 */
	protected void createAction() {
		if (factory == null) {
			return;
		}

		Object newObject;

		try {
			newObject = factory.createObject(this.create, this.contextElement);
		} catch (OperationCanceledException e) {
			// The user cancelled and we rolled back pending model changes
			newObject = null;
		}

		if (newObject == null) {
			return;
		}

		newObjects.add(newObject);
		selector.newObjectCreated(newObject);

		Object[] createdObjects = new Object[] { newObject };
		addElements(createdObjects);

		selector.setSelectedElements(allElements.toArray());
	}


	/**
	 * Moves an element from oldIndex to newIndex
	 *
	 * @param list
	 *            The list in which to move the object
	 * @param oldIndex
	 * @param newIndex
	 */
	private void move(java.util.List<Object> list, int oldIndex, int newIndex) {
		int size = list.size();

		if (oldIndex < 0 || oldIndex >= size) {
			throw new IndexOutOfBoundsException("oldIndex: " + oldIndex + ", size:" + size); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (newIndex < 0 || newIndex >= size) {
			throw new IndexOutOfBoundsException("newIndex: " + newIndex + ", size:" + size); //$NON-NLS-1$ //$NON-NLS-2$
		}
		Object element = list.remove(oldIndex);
		list.add(newIndex, element);
	}

	/**
	 * Handles the "Remove" action
	 */
	protected void removeAction() {
		if (canRemove()) {
			IStructuredSelection selection = (IStructuredSelection) selectedElementsViewer.getSelection();
			if (selection.isEmpty()) {
				return;
			}

			for (Object element : selection.toArray()) {
				allElements.remove(element);
			}

			selector.setSelectedElements(allElements.toArray());
			selectedElementsViewer.setSelection(null);
			selectedElementsViewer.refresh();
		}
	}

	/**
	 * Handles the "Delete" action
	 */
	protected void deleteAction() {
		// nothing to do here
		ISelection selection = selectedElementsViewer.getSelection();
		if (selection instanceof IStructuredSelection) {
			final Iterator<?> selectedObjects = ((IStructuredSelection) selection).iterator();
			List<Object> removedObjects = new ArrayList<>();
			while (selectedObjects.hasNext()) {
				Object selectedObject = selectedObjects.next();
				if (newObjects.contains(selectedObject)) {
					newObjects.remove(selectedObject);
				}
				removedObjects.add(selectedObject);
			}

			removeElements(removedObjects.toArray());
			selector.setSelectedElements(allElements.toArray());
		}
	}

	/**
	 * Adds the specified elements to the currently selected elements (For
	 * "Add" and "Add all" actions)
	 *
	 * @param elements
	 *            The elements to be added
	 */
	@Override
	public void addElements(final Object[] elements) {
		if (null != elements && elements.length > 0) {
			allElements.addAll(Arrays.asList(elements));
			selectedElementsViewer.refresh();
		}
	}

	/**
	 * Adds the specified elements to the currently selected elements (For
	 * "Remove" and "Remove all" actions)
	 *
	 * @param elements
	 *            The elements to be added
	 */
	public void removeElements(final Object[] elements) {
		if (null != elements && elements.length > 0) {
			allElements.removeAll(Arrays.asList(elements));
			selectedElementsViewer.refresh();
		}
	}

	public List<Object> getSelection() {
		if (factory != null) {
			java.util.List<Object> objectsToValidate = new LinkedList<>();
			for (Object object : newObjects) {
				if (allElements.contains(object)) {
					objectsToValidate.add(object);
				}
			}
			factory.validateObjects(objectsToValidate);
			selector.clearTemporaryElements();
		}
		return new LinkedList<>(allElements);
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// Nothing (see #doubleClick())
	}

	/**
	 * Indicates if the selected values should be unique (According to Object.equals())
	 *
	 * @param unique
	 */
	public void setUnique(boolean unique) {
		this.unique = unique;
		updateControls();
	}

	/**
	 * Indicates if the selected elements should be ordered
	 *
	 * @param ordered
	 */
	public void setOrdered(boolean ordered) {
		this.ordered = ordered;
		updateControls();
	}

	public void updateControls() {
		updateControl(up, ordered && canMove(true));
		updateControl(down, ordered && canMove(false));
		updateControl(create, this.factory != null && this.factory.canCreateObject());
		updateControl(delete, canDelete());
	}

	/**
	 * @return
	 */
	protected boolean canDelete() {
		return true;
	}

	/**
	 *
	 * @param up
	 *            if <code>true</code> we try to move the element to the up, if <code>false</code> we try to move the element to the down
	 * @return
	 * 		<code>true</code> if the element can be moved
	 */
	protected boolean canMove(boolean up) {
		return null != selectedElementsViewer && null != selectedElementsViewer.getSelection() && !selectedElementsViewer.getSelection().isEmpty();
	}

	/**
	 *
	 * @return
	 * 		<code>true</code> if we can add elements
	 */
	protected boolean canAdd() {
		/* Disable the bouton 'add' if the upperBound is reached */
		boolean canAdd = true;
		if (this.upperBound != ValueUtils.MANY) {
			if (allElements.size() >= this.upperBound) {
				canAdd = false;
			}
		}
		return canAdd;
	}

	protected void updateControl(Control control, boolean enabled) {
		if (control != null) {
			control.setEnabled(enabled);
		}
	}

	public void setSelector(IElementSelector selector) {
		if (null != this.selector) {
			this.selector.removeElementSelectionListener(this);
		}
		this.selector = selector;
		this.selector.addElementSelectionListener(this);
	}

	/**
	 * Set the maximum number of values selected.
	 *
	 * @param upperBound
	 */
	public void setUpperBound(int upperBound) {
		this.upperBound = upperBound;
	}

	/**
	 * Sets the optional context of the element that is being edited, in which others will be added and removed.
	 *
	 * @param contextElement
	 *            the model element that is being edited
	 */
	public void setContextElement(Object contextElement) {
		this.contextElement = contextElement;
	}

	/**
	 * Queries the optional context of the element that is being edited, in which others will be added and removed.
	 *
	 * @return the model element that is being edited
	 */
	public Object getContextElement() {
		return contextElement;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Handles double click event on the right-panel tree viewer {@link #selectedElementsViewer}
	 *
	 */
	@Override
	public void doubleClick(DoubleClickEvent event) {
		removeAction();
	}

	/**
	 *
	 * @return
	 * 		<code>true</code> if the selected elements can be removed (moved from right to left)
	 */
	protected boolean canRemove() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Handles selection change event on the right-panel tree viewer {@link #selectedElementsViewer}
	 */
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		updateControls();
	}

	/**
	 * @see org.eclipse.ui.services.IDisposable#dispose()
	 *
	 */
	@Override
	public void dispose() {
		if (null != this.selector) {
			this.selector.removeElementSelectionListener(this);
		}
	}

	/**
	 *
	 * @return
	 * 		the initial selection
	 */
	protected List<?> getInitialSelection() {
		return this.initialSelection;
	}
}
