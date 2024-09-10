/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.widget;

import java.util.List;

import org.eclipse.nebula.widgets.nattable.style.IStyle;
import org.eclipse.nebula.widgets.nattable.widget.NatCombo;
import org.eclipse.papyrus.infra.nattable.celleditor.IActionCellEditor;
import org.eclipse.papyrus.infra.nattable.celleditor.action.ICellEditorButtonAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * a NatCombo, with a button, inserted by an {@link ICellEditorButtonAction}. So this {@link NatCombo} must be used by a {@link IActionCellEditor}
 *
 * @since 6.6
 */
public class ButtonNatCombo extends NatCombo {

	/**
	 * the button action
	 */
	private ICellEditorButtonAction action;

	/**
	 * Constructor.
	 *
	 * @param parent
	 * @param cellStyle
	 * @param maxVisibleItems
	 * @param style
	 * @param showDropdownFilter
	 */
	public ButtonNatCombo(Composite parent, IStyle cellStyle, int maxVisibleItems, int style, boolean showDropdownFilter, ICellEditorButtonAction action) {
		super(parent, cellStyle, maxVisibleItems, style, showDropdownFilter);
		this.action = action;
		createButton(SWT.NONE);
	}

	/**
	 * Constructor.
	 *
	 * @param parent
	 * @param cellStyle
	 * @param maxVisibleItems
	 * @param style
	 * @param iconImage
	 * @param showDropdownFilter
	 */
	public ButtonNatCombo(Composite parent, IStyle cellStyle, int maxVisibleItems, int style, Image iconImage, boolean showDropdownFilter, ICellEditorButtonAction action) {
		super(parent, cellStyle, maxVisibleItems, style, iconImage, showDropdownFilter);
		this.action = action;
		createButton(SWT.NONE);
	}

	/**
	 * Constructor.
	 *
	 * @param parent
	 * @param cellStyle
	 * @param maxVisibleItems
	 * @param style
	 * @param iconImage
	 */
	public ButtonNatCombo(Composite parent, IStyle cellStyle, int maxVisibleItems, int style, Image iconImage, ICellEditorButtonAction action) {
		super(parent, cellStyle, maxVisibleItems, style, iconImage);
		this.action = action;
		createButton(SWT.NONE);
	}

	/**
	 * Constructor.
	 *
	 * @param parent
	 * @param cellStyle
	 * @param maxVisibleItems
	 * @param style
	 */
	public ButtonNatCombo(Composite parent, IStyle cellStyle, int maxVisibleItems, int style, ICellEditorButtonAction action) {
		super(parent, cellStyle, maxVisibleItems, style);
		this.action = action;
		createButton(SWT.NONE);
	}

	/**
	 * Constructor.
	 *
	 * @param parent
	 * @param cellStyle
	 * @param style
	 */
	public ButtonNatCombo(Composite parent, IStyle cellStyle, int style, ICellEditorButtonAction action) {
		super(parent, cellStyle, style);
		this.action = action;
		createButton(SWT.NONE);
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.widget.NatCombo#createTextControl(int)
	 *
	 * @param style
	 */
	@Override
	protected void createTextControl(int style) {
		super.createTextControl(style);
	}

	/**
	 *
	 * @param style
	 *            the style for the button
	 */
	private void createButton(int style) {
		if (this.action != null && this.action.isEnabled()) {
			final List<Control> ctrl = this.action.createControl(this, style);
			((GridLayout) getLayout()).numColumns += ctrl.size();
		}
	}

}
