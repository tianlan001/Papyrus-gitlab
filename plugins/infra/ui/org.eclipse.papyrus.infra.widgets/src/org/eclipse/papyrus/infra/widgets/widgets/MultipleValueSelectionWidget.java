/*****************************************************************************
 * Copyright (c) 2014, 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 * Adapted code from Camille Letavernier (CEA LIST) in MultipleValueSelectorDialog
 * Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 517190
 *****************************************************************************/

package org.eclipse.papyrus.infra.widgets.widgets;

import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.editors.IElementSelector;
import org.eclipse.papyrus.infra.widgets.messages.Messages;
import org.eclipse.papyrus.infra.widgets.util.ValueUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;

/**
 * @author Vincent Lorenzo
 *         Class extracted from MultipleValueSelectorDialog
 *
 */
public class MultipleValueSelectionWidget extends MultipleValueWidget {

	/**
	 * The SWT Composite in which the selector is drawn
	 */
	protected Composite selectorSection;

	/**
	 * The add/remove/addAll buttons section
	 */
	protected Composite buttonsSection;

	/**
	 * The add action button
	 */
	protected Button add;

	/**
	 * The remove action button
	 */
	protected Button remove;

	/**
	 * The add all action button
	 */
	protected Button addAll;

	/**
	 * The remove all action button
	 */
	protected Button removeAll;

	/**
	 * Constructor.
	 *
	 * @param selector
	 *            The element selector used by this dialog
	 */
	public MultipleValueSelectionWidget(IElementSelector selector) {
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
	public MultipleValueSelectionWidget(IElementSelector selector, boolean unique) {
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
	public MultipleValueSelectionWidget(IElementSelector selector, boolean unique, boolean ordered) {
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
	public MultipleValueSelectionWidget(IElementSelector selector, boolean unique, boolean ordered, int upperBound) {
		super(selector, unique, ordered, upperBound);
	}

	/**
	 * initialize the widget if required
	 */
	protected void init() {
		// nothing to do
	}

	/**
	 * @see org.eclipse.papyrus.infra.widgets.widgets.MultipleValueWidget#createSections(org.eclipse.swt.widgets.Composite)
	 *
	 * @param parent
	 */
	@Override
	protected void createSections(final Composite parent) {

		Layout parentLayout = parent.getLayout();
		if (parentLayout instanceof GridLayout) {
			GridLayout layout = (GridLayout) parentLayout;
			layout.numColumns = 2;
			layout.makeColumnsEqualWidth = true;

			Composite selectorPane = new Composite(parent, SWT.NONE);
			selectorPane.setLayout(new GridLayout(2, false));
			selectorPane.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

			createSelectorSection(selectorPane);
			createControlsSection(selectorPane);
		}

		super.createSections(parent);
	}
	

	/**
	 * Creates the selector section
	 *
	 * @param parent
	 *            The composite in which the section is created
	 */
	private void createSelectorSection(Composite parent) {
		selectorSection = new Composite(parent, SWT.NONE);
		selectorSection.setLayout(new FillLayout());
		selectorSection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		selector.createControls(selectorSection);
	}

	/**
	 * Creates the main controls section (Add, remove, Add all, remove all)
	 *
	 * @param parent
	 *            The composite in which the section is created
	 */
	private void createControlsSection(Composite parent) {
		buttonsSection = new Composite(parent, SWT.NONE);
		buttonsSection.setLayout(new GridLayout(1, true));

		add = new Button(buttonsSection, SWT.PUSH);
		add.setImage(Activator.getDefault().getImage("/icons/arrow_right.gif")); //$NON-NLS-1$
		add.addSelectionListener(this);
		add.setToolTipText(Messages.MultipleValueSelectorDialog_AddSelectedElements);

		remove = new Button(buttonsSection, SWT.PUSH);
		remove.setImage(Activator.getDefault().getImage("/icons/arrow_left.gif")); //$NON-NLS-1$
		remove.addSelectionListener(this);
		remove.setToolTipText(Messages.MultipleValueEditor_RemoveSelectedElements);

		addAll = new Button(buttonsSection, SWT.PUSH);
		addAll.setImage(Activator.getDefault().getImage("/icons/arrow_double.gif")); //$NON-NLS-1$
		addAll.addSelectionListener(this);
		addAll.setToolTipText(Messages.MultipleValueSelectorDialog_AddAllElements);

		/* Disable the bouton 'addAll' if currently chosen elements is greater than the maximum number of values selected */
		if (this.upperBound != ValueUtils.MANY && allElements.size() > this.upperBound) {
			addAll.setEnabled(false);
		}


		removeAll = new Button(buttonsSection, SWT.PUSH);
		removeAll.setImage(Activator.getDefault().getImage("/icons/arrow_left_double.gif")); //$NON-NLS-1$
		removeAll.addSelectionListener(this);
		removeAll.setToolTipText(Messages.MultipleValueSelectorDialog_RemoveAllElements);
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
		if (e.widget == add) {
			addAction();
		} else if (e.widget == remove) {
			removeAction();
		} else if (e.widget == addAll) {
			addAllAction();
		} else if (e.widget == removeAll) {
			removeAllAction();
		}

		super.widgetSelected(e);
	}


	/**
	 * Handles the "Add" action
	 */
	protected void addAction() {
		Object[] elements = selector.getSelectedElements();
		addElements(elements);
	}

	/**
	 * Handles the "Remove all" action
	 */
	protected void removeAllAction() {
		allElements.clear();
		selector.setSelectedElements(new Object[0]);
		selectedElementsViewer.setSelection(null);
		selectedElementsViewer.refresh();
	}

	/**
	 * Handles the "Add All" action
	 */
	protected void addAllAction() {
		Object[] elements = selector.getAllElements();
		addElements(elements);
	}



	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.widgets.MultipleValueWidget#updateControls()
	 */
	@Override
	public void updateControls() {
		updateControl(add, canAdd());
		super.updateControls();
	}

}