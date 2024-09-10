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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.celleditor.action;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.nebula.widgets.nattable.config.ConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.papyrus.infra.nattable.celleditor.IActionCellEditor;
import org.eclipse.papyrus.infra.nattable.manager.cell.CellManagerFactory;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.infra.nattable.utils.TableEditingDomainUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Abstract class used to declare a button with an associated on a existing cell editor
 *
 * @since 6.6
 */
public abstract class AbstractCellEditorButtonAction implements ICellEditorButtonAction {

	/** The text to use for the button. */
	private String text;

	/** The tooltip to use for the button. */
	private String tooltipText;

	/** the image to set on the button. */
	private Image image;

	/** the initial cell value */
	protected Object originalCanonicalValue;

	protected Composite parent;

	/**
	 * the initial cell editor
	 */
	protected IActionCellEditor initialCellEditor;

	/**
	 * The edited cell
	 */
	protected ILayerCell cell;

	/**
	 * the {@link ConfigRegistry}
	 */
	protected IConfigRegistry configRegistry;


	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.action.ICellEditorButtonAction#configureAction(org.eclipse.nebula.widgets.nattable.edit.editor.ICellEditor, java.lang.Object, org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell,
	 *      org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 *
	 * @param editor
	 * @param originalCanonicalValue
	 * @param cell
	 * @param configRegistry
	 */
	@Override
	public boolean configureAction(final IActionCellEditor editor, final Composite composite, final Object originalCanonicalValue, final ILayerCell cell, final IConfigRegistry configRegistry) {
		this.initialCellEditor = editor;
		this.cell = cell;
		this.parent = composite;
		this.originalCanonicalValue = originalCanonicalValue;
		this.configRegistry = configRegistry;
		return isEnabled();
	}


	/**
	 * Sets the image.
	 *
	 * @param image
	 *            the new image
	 */
	@Override
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * Sets the text.
	 *
	 * @param text
	 *            the new text
	 */
	@Override
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Sets the tooltip text.
	 *
	 * @param tooltipText
	 *            the new tooltip text
	 */
	@Override
	public void setTooltipText(String tooltipText) {
		this.tooltipText = tooltipText;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.action.ICellEditorButtonAction#createControl(org.eclipse.swt.widgets.Composite, int)
	 *
	 * @param parent
	 * @param style
	 * @return
	 */
	@Override
	public List<Control> createControl(Composite parent, int style) {
		final Button button = new Button(parent, style);
		button.setText(getText());
		button.setToolTipText(getTooltipText());
		button.setImage(getImage());
		final GridData buttonGridData = new GridData(SWT.RIGHT, SWT.FILL, false, true);
		button.setLayoutData(buttonGridData);

		button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				runAction(e);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		return Collections.singletonList(button);
	}

	/**
	 * Do the required stuff expected by the button
	 *
	 * @param e
	 *            the event received by the button
	 * @return a int value coding a status return
	 */
	protected abstract int runAction(final SelectionEvent e);

	protected final String getText() {
		return this.text != null ? this.text : ""; //$NON-NLS-1$
	}

	protected final String getTooltipText() {
		return this.tooltipText != null ? this.tooltipText : ""; //$NON-NLS-1$
	}

	/**
	 *
	 * @return
	 *         the image to use for the button
	 */
	protected final Image getImage() {
		return this.image;
	}

	/**
	 *
	 * @return
	 *         the {@link INattableModelManager} or <code>null</code> if not found
	 */
	protected final INattableModelManager getNattableModelManager() {
		if (this.configRegistry != null) {
			return this.configRegistry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, org.eclipse.nebula.widgets.nattable.style.DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
		}
		return null;
	}

	/**
	 *
	 * @return
	 *         <code>true</code> if the cell is editable (using the CellManagerFactory
	 */
	protected final boolean isCellEditable() {
		final INattableModelManager manager = getNattableModelManager();
		if (manager != null) {
			final Object columnElement = manager.getColumnElement(getColumnIndex());
			final Object rowElement = manager.getRowElement(getRowIndex());
			return CellManagerFactory.INSTANCE.isCellEditable(columnElement, rowElement, manager);
		}
		return false;
	}

	/**
	 *
	 * @return
	 *         the current cell value
	 */
	protected final Object getCurrentCellValue() {
		final INattableModelManager manager = getNattableModelManager();
		if (manager != null) {
			return manager.getDataValue(getColumnIndex(), getRowIndex());
		}
		return null;
	}

	/**
	 *
	 * @return
	 *         the row column index
	 */
	protected final int getRowIndex() {
		return this.cell.getRowIndex();
	}

	/**
	 *
	 * @return
	 *         the cell column index
	 */
	protected final int getColumnIndex() {
		return this.cell.getColumnIndex();
	}

	/**
	 *
	 * @return
	 *         the editing domain
	 */
	protected final TransactionalEditingDomain getEditingDomain() {
		final INattableModelManager manager = getNattableModelManager();
		if (manager != null) {
			return TableEditingDomainUtils.getTableContextEditingDomain(manager.getTable());
		}
		return null;
	}

	/**
	 *
	 * @return
	 *         the column element
	 */
	protected final Object getColumnElement() {
		final INattableModelManager manager = getNattableModelManager();
		if (manager != null) {
			return manager.getColumnElement(getColumnIndex());
		}
		return null;

	}

	/**
	 *
	 * @return
	 *         the row element
	 */
	protected final Object getRowElement() {
		final INattableModelManager manager = getNattableModelManager();
		if (manager != null) {
			return manager.getRowElement(getRowIndex());
		}
		return null;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.action.ICellEditorButtonAction#isEnabled()
	 *
	 * @return
	 */
	@Override
	public boolean isEnabled() {
		return isCellEditable();
	}
}
