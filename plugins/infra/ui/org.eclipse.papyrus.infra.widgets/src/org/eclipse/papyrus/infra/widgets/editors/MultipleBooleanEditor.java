/*****************************************************************************
 * Copyright (c) 2017 CEA, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Pierre GAUTIER (CEA LIST) - Initial API and implementation
 *	Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 521902, Bug 526304
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.editors;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.papyrus.infra.widgets.selectors.BooleanSelector;
import org.eclipse.papyrus.infra.widgets.util.Constants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * An editor for multivalued Boolean attributes
 *
 * @since 3.1
 */
public class MultipleBooleanEditor extends MultipleStringEditor<BooleanSelector> {

	/** Proposals for boolean */
	private final String[] booleanProposals = new String[] { "true", "false" }; //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * Constructs an Editor for multiple boolean values.
	 * The widget is a List, with controls to move values up/down, add values and remove values.
	 *
	 * @param parent
	 *            The Composite in which this editor is created
	 * @param style
	 *            The List's style
	 */
	public MultipleBooleanEditor(final Composite parent, final int style) {
		super(parent, style, new BooleanSelector());
	}

	/**
	 * Constructs an Editor for multiple boolean values.
	 * The widget is a List, with controls to move values up/down, add values and remove values.
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
	public MultipleBooleanEditor(final Composite parent, final boolean directCreation, boolean directCreationWithTreeViewer, final int style) {
		super(parent, style, new BooleanSelector(), directCreation, directCreationWithTreeViewer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CellEditor createCellEditor(Object element) {
		return new ComboBoxCellEditor(((TreeViewer) getViewer()).getTree(), booleanProposals, SWT.READ_ONLY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object getEditingValue(Object object) {
		List<String> booleans = Arrays.asList(booleanProposals);
		if (object == null || object.equals(Constants.EMPTY_STRING)) {
			return 0;
		}
		return booleans.indexOf(object.toString());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object getValueToSet(Object element, Object value) {
		Object newValue = value;

		if (element instanceof Boolean) {
			if (value.equals(0)) {
				newValue = true;
			} else {
				newValue = false;
			}
		}

		return newValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object getDefaultValue() {
		return true;
	}

}
