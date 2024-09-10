/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.celleditor;

import org.eclipse.jface.window.Window;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.edit.DialogEditHandler;
import org.eclipse.nebula.widgets.nattable.edit.gui.AbstractDialogCellEditor;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.widget.EditModeEnum;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

/**
 * This class allows to store required information to customize
 * a Button to open a dialog
 *
 * @deprecated since 6.6 (use {@link org.eclipse.papyrus.infra.nattable.celleditor.action.AbstractOpenDialogCellEditorButtonAction} instead)
 */
@Deprecated
public abstract class AbstractOpenDialogCellEditorButtonAction {

	/** The text to use for the button. */
	private String text;

	/** The tooltip to use for the button. */
	private String tooltipText;

	/** the image to set on the button. */
	private Image image;

	/**
	 * the new cell value selected by the user opening a dialog
	 */
	protected Object newValue;

	/**
	 * the column index of the edited cell
	 */
	protected int columnIndex;

	/**
	 * the row index of the edited cell
	 */
	protected int rowIndex;

	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}


	/**
	 * Sets the image.
	 *
	 * @param image
	 *            the new image
	 */
	public void setImage(Image image) {
		this.image = image;
	}


	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}


	/**
	 * Sets the text.
	 *
	 * @param text
	 *            the new text
	 */
	public void setText(String text) {
		this.text = text;
	}


	/**
	 * Gets the tooltip text.
	 *
	 * @return the tooltip text
	 */
	public String getTooltipText() {
		return tooltipText;
	}


	/**
	 * Sets the tooltip text.
	 *
	 * @param tooltipText
	 *            the new tooltip text
	 */
	public void setTooltipText(String tooltipText) {
		this.tooltipText = tooltipText;
	}


	/**
	 *
	 * @return
	 *         the created dialog
	 */
	public abstract AbstractDialogCellEditor createDialogCellEditor();


	/**
	 * Setter for the edited cell location
	 *
	 * @param columnIndex
	 *            the column index of the edited cell
	 * @param rowIndex
	 *            the row index of the edited cell
	 */
	public final void setCellLocation(int columnIndex, int rowIndex) {
		this.columnIndex = columnIndex;
		this.rowIndex = rowIndex;
	}

	/**
	 *
	 * @return
	 *         the value selected by the user
	 */
	public Object getEditorValue() {
		return this.newValue;
	}

	/**
	 *
	 * @param parent
	 *            the parent composite used to open a dialog
	 * @param originalCanonicalValue
	 *            the original value
	 * @param cell
	 *            the cell to edit
	 * @param configRegistry
	 *            the config registry used by the nattable widget
	 * @return
	 *         a IStatus to know if the user cancel the action or validate the action
	 */
	public int openDialog(Composite parent, Object originalCanonicalValue, ILayerCell cell, IConfigRegistry configRegistry) {
		AbstractDialogCellEditor editor = createDialogCellEditor();
		editor.activateCell(parent, originalCanonicalValue, EditModeEnum.DIALOG, new DialogEditHandler(), cell, configRegistry);
		int res = editor.open();
		if (Window.OK == res) {
			this.newValue = editor.getEditorValue();
		}
		return res;
	}
}
