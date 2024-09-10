/*****************************************************************************
 * Copyright (c) 2010, 2017, 2018 CEA LIST, Esterel Technologies SAS and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Adapted code from MultipleValueSelectorDialog
 *  Sebastien Gabel (Esterel Technologies SAS) - Bug 526302
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 517190
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.editors;

import org.eclipse.papyrus.infra.widgets.util.ValueUtils;
import org.eclipse.papyrus.infra.widgets.widgets.MultipleValueSelectionWidget;
import org.eclipse.swt.widgets.Shell;

/**
 * Object Chooser. Defines a standard popup for selecting
 * multiple values. If this dialog is used to select or create model
 * elements to be added to or removed from some element that is being
 * edited, then it is important to {@linkplain #setContextElement(Object) set that contextual element} in this dialog.
 *
 * @author Vincent Lorenzo
 *
 * @see #setContextElement(Object)
 *
 */
public class MultipleValueSelectionDialog extends MultipleValueDialog {

	/**
	 * Constructor.
	 *
	 * @param parentShell
	 *            The shell in which this dialog should be opened
	 * @param selector
	 *            The element selector used by this dialog
	 */
	public MultipleValueSelectionDialog(Shell parentShell, IElementSelector selector) {
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
	public MultipleValueSelectionDialog(Shell parentShell, IElementSelector selector, String title) {
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
	public MultipleValueSelectionDialog(Shell parentShell, IElementSelector selector, boolean unique) {
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
	 * @param ordered
	 *            <code>true</code> if the edited feature is ordered
	 */
	public MultipleValueSelectionDialog(Shell parentShell, IElementSelector selector, String title, boolean unique, boolean ordered) {
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
	 * @param ordered
	 *            <code>true</code> if the edited feature is ordered
	 * @param upperBound
	 *            The maximum number of values selected.
	 */
	public MultipleValueSelectionDialog(Shell parentShell, IElementSelector selector, String title, boolean unique, boolean ordered, int upperBound) {
		super(parentShell, selector, title, unique, ordered, upperBound);
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
	@Override
	protected MultipleValueSelectionWidget createWidget(IElementSelector selector, boolean unique, boolean ordered, int upperBound) {
		return new MultipleValueSelectionWidget(selector, unique, ordered, upperBound);
	}
	
}
