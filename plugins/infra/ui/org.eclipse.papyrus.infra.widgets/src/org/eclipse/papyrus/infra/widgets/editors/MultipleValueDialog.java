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
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.editors;

import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.infra.widgets.creation.ReferenceValueFactory;
import org.eclipse.papyrus.infra.widgets.util.ValueUtils;
import org.eclipse.papyrus.infra.widgets.widgets.MultipleValueSelectionWidget;
import org.eclipse.papyrus.infra.widgets.widgets.MultipleValueWidget;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.SelectionDialog;

/**
 * Object Chooser. Defines a standard popup for selecting
 * multiple values. If this dialog is used to select or create model
 * elements to be added to or removed from some element that is being
 * edited, then it is important to {@linkplain #setContextElement(Object) set that contextual element} in this dialog.
 *
 * @see #setContextElement(Object)
 * 
 * @since 3.2
 */
public class MultipleValueDialog extends SelectionDialog {

	/**
	 * the widget providing the contents of the dialog
	 */
	protected MultipleValueWidget widget;

	/**
	 * Constructor.
	 *
	 * @param parentShell
	 *            The shell in which this dialog should be opened
	 * @param selector
	 *            The element selector used by this dialog
	 */
	public MultipleValueDialog(Shell parentShell, IElementSelector selector) {
		this(parentShell, selector, null, false, false);
	}

	/**
	 * Constructor.
	 *
	 * @param parentShell
	 *            The shell in which this dialog should be opened
	 * @param selector
	 *            The element selector used by this dialog
	 * @param title
	 *            The title of this dialog
	 */
	public MultipleValueDialog(Shell parentShell, IElementSelector selector, String title) {
		this(parentShell, selector, title, false, false);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param parentShell
	 *            The shell in which this dialog should be opened
	 * @param selector
	 *            The element selector used by this dialog
	 * @param unique
	 *            True if the values returned by this dialog should be unique
	 */
	public MultipleValueDialog(Shell parentShell, IElementSelector selector, boolean unique) {
		this(parentShell, selector, null, unique, false);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param parentShell
	 *            The shell in which this dialog should be opened
	 * @param selector
	 *            The element selector used by this dialog
	 * @param title
	 *            The title of this dialog
	 * @param unique
	 *            True if the values returned by this dialog should be unique
	 */
	public MultipleValueDialog(Shell parentShell, IElementSelector selector, String title, boolean unique, boolean ordered) {
		this(parentShell, selector, title, unique, ordered, ValueUtils.MANY);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param parentShell
	 *            The shell in which this dialog should be opened
	 * @param selector
	 *            The element selector used by this dialog
	 * @param title
	 *            The title of this dialog
	 * @param unique
	 *            True if the values returned by this dialog should be unique
	 * @param upperBound
	 *            The maximum number of values selected.
	 */
	public MultipleValueDialog(Shell parentShell, IElementSelector selector, String title, boolean unique, boolean ordered, int upperBound) {
		super(parentShell);
		Assert.isNotNull(selector, "The element selector should be defined"); //$NON-NLS-1$
		setHelpAvailable(false);
		setTitle(title);
		this.widget = createWidget(selector, unique, ordered, upperBound);
	}

	/**
	 * 
	 * @param selector
	 *            The element selector used by this dialog
	 * @param unique
	 *            True if the values returned by this dialog should be unique
	 * @param ordered
	 *            <code>true</code> if the edited feature is ordered
	 * @param upperBound
	 *            The maximum number of values selected.
	 */
	protected MultipleValueWidget createWidget(IElementSelector selector, boolean unique, boolean ordered, int upperBound) {
		return new MultipleValueWidget(selector, unique, ordered, upperBound);
	}

	/**
	 *
	 * @see org.eclipse.ui.dialogs.SelectionDialog#configureShell(org.eclipse.swt.widgets.Shell)
	 *
	 * @param shell
	 */
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setImage(org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage("/icons/papyrus.png")); //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.ui.dialogs.SelectionDialog#setInitialElementSelections(java.util.List)
	 *
	 * @param selectedElements
	 */
	@Override
	public void setInitialElementSelections(@SuppressWarnings("rawtypes") List selectedElements) {
		super.setInitialElementSelections(selectedElements);
		widget.setInitialSelections(getInitialElementSelections());
	}


	/**
	 * @see org.eclipse.ui.dialogs.SelectionDialog#setInitialSelections(java.lang.Object[])
	 *
	 * @param selectedElements
	 */
	@Override
	public void setInitialSelections(Object[] selectedElements) {
		super.setInitialSelections(selectedElements);
		widget.setInitialSelections(getInitialElementSelections());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create() {
		super.create();
		createDialogContents();
		getShell().pack();

		this.widget.updateControls();

	}

	/**
	 * Create the contents of the dialog
	 */
	protected void createDialogContents() {
		Composite parent = getDialogArea();
		this.widget.create(parent);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Composite getDialogArea() {
		return (Composite) super.getDialogArea();
	}


	/**
	 * Sets the {@link ReferenceValueFactory} for this editor. The {@link ReferenceValueFactory} is used to create
	 * new instances and edit existing ones.
	 *
	 * @param factory
	 *            The {@link ReferenceValueFactory} to be used by this editor
	 */
	public void setFactory(ReferenceValueFactory factory) {
		this.widget.setFactory(factory);
	}

	/**
	 *
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 *
	 */
	@Override
	protected void okPressed() {
		setResult(this.widget.getSelection());
		super.okPressed();
	}

	/**
	 * Indicates if the selected values should be unique (According to Object.equals())
	 *
	 * @param unique
	 */
	public void setUnique(boolean unique) {
		this.widget.setUnique(unique);
	}

	/**
	 * Indicates if the selected elements should be ordered
	 *
	 * @param ordered
	 */
	public void setOrdered(boolean ordered) {
		this.widget.setOrdered(ordered);
	}

	/**
	 * Set the selector to use
	 *
	 * @param selector
	 *            the selector to use in the dialog
	 */
	public void setSelector(IElementSelector selector) {
		this.widget.setSelector(selector);
	}

	/**
	 * Set the maximum number of values selected.
	 *
	 * @param upperBound
	 */
	public void setUpperBound(int upperBound) {
		this.widget.setUpperBound(upperBound);
	}

	/**
	 * Sets the optional context of the element that is being edited, in which others will be added and removed.
	 *
	 * @param contextElement
	 *            the model element that is being edited
	 */
	public void setContextElement(Object contextElement) {
		this.widget.setContextElement(contextElement);
	}

	/**
	 * Queries the optional context of the element that is being edited, in which others will be added and removed.
	 *
	 * @return the model element that is being edited
	 */
	public Object getContextElement() {
		return this.widget.getContextElement();
	}

	/**
	 *
	 * @see org.eclipse.jface.dialogs.Dialog#close()
	 *
	 * @return
	 */
	@Override
	public boolean close() {
		if (this.widget != null) {
			this.widget.dispose();
		}
		return super.close();
	}

	/**
	 * Sets the label provider used to display the selected elements
	 *
	 * @param labelProvider
	 */
	public void setLabelProvider(ILabelProvider labelProvider) {
		this.widget.setLabelProvider(labelProvider);
	}

	/**
	 * 
	 * @return
	 * 		the widget
	 * @deprecated since 3.2, the field widget is not necessary a MultipleValueSelectionWidget
	 */
	protected MultipleValueSelectionWidget getWidget() {// for backward compatibility and mainly to avoid API break
		if (this.widget instanceof MultipleValueSelectionWidget) {
			return (MultipleValueSelectionWidget) this.widget;
		}
		return null;
	}
}
