/*****************************************************************************
 * Copyright (c) 2014, 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 	Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 521902, Bug 526304
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.editors;


import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.papyrus.infra.widgets.selectors.RealSelector;
import org.eclipse.swt.widgets.Composite;


public class MultipleDoubleEditor extends MultipleStringEditor<RealSelector> {

	/**
	 * Constructs an Editor for multiple double values.
	 * The widget is a List, with controls to move values up/down, add values and remove values.
	 *
	 * @param parent
	 *            The Composite in which this editor is created
	 * @param style
	 *            The List's style
	 */
	public MultipleDoubleEditor(Composite parent, int style) {
		super(parent, style, new RealSelector());
	}

	/**
	 * Constructs an Editor for multiple double values.
	 * The widget is a List, with controls to move values up/down, add values and remove values.
	 *
	 * @param parent
	 *            The Composite in which this editor is created
	 * @param style
	 *            The List's style
	 * @param label
	 *            The editor's label
	 */
	public MultipleDoubleEditor(Composite parent, int style, String label) {
		super(parent, style, new RealSelector(), label);
	}

	/**
	 * Constructs an Editor for multiple double values.
	 * The widget is a List, with controls to move values up/down, add values and remove values.
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
	public MultipleDoubleEditor(Composite parent, int style, boolean ordered, boolean unique, String label) {
		super(parent, style, new RealSelector(), ordered, unique, label);
	}

	/**
	 * Constructs an Editor for multiple double values.
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
	public MultipleDoubleEditor(Composite parent, boolean directCreation, boolean directCreationWithTreeViewer, int style) {
		super(parent, style, new RealSelector(), directCreation, directCreationWithTreeViewer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CellEditor createCellEditor(Object element) {
		return new DoubleCellEditor(tree);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Object getDefaultValue() {
		return new Double(0);
	}

	/**
	 * This cell editor ensures that only Double values are supported.
	 */
	private static class DoubleCellEditor extends TextCellEditor {
		public DoubleCellEditor(Composite composite) {
			super(composite);
			setValidator(new ICellEditorValidator() {
				public String isValid(Object object) {
					if (object instanceof Double) {
						return null;
					} else {
						String string = (String) object;
						try {
							Double.parseDouble(string);
							return null;
						} catch (NumberFormatException exception) {
							return exception.getMessage();
						}
					}
				}
			});
		}

		@Override
		public Object doGetValue() {
			return Double.parseDouble((String) super.doGetValue());
		}

		@Override
		public void doSetValue(Object value) {
			super.doSetValue(value.toString());
		}
	}

}
