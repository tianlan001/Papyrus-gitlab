/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.utils;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.swt.graphics.Rectangle;

/**
 * @author Vincent Lorenzo
 * 
 *         This wrapper allows to store cell value, with its column object and its row object
 *
 */
public class LabelProviderFullCellContextElementWrapper implements ILabelProviderCellContextElementWrapper {

	/**
	 * the value wrapped by the class
	 */
	private Object cellValue;

	/**
	 * the label stack
	 */
	private LabelStack labelStack;

	/**
	 * the row object for this cell
	 */
	private Object rowObject;

	/**
	 * the columnObject for this cell
	 */
	private Object columnObject;

	/**
	 * the config registry
	 */
	private IConfigRegistry configRegistry;


	/**
	 * @see org.eclipse.papyrus.infra.nattable.utils.ILabelProviderContextElementWrapper#getConfigRegistry()
	 *
	 * @return
	 */
	@Override
	public IConfigRegistry getConfigRegistry() {
		return this.configRegistry;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.utils.ILabelProviderContextElementWrapper#getObject()
	 *
	 * @return
	 */
	@Override
	public Object getObject() {
		return this.cellValue;
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell#getOriginColumnPosition()
	 *
	 * @return
	 */
	@Override
	public int getOriginColumnPosition() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell#getOriginRowPosition()
	 *
	 * @return
	 */
	@Override
	public int getOriginRowPosition() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell#getLayer()
	 *
	 * @return
	 */
	@Override
	public ILayer getLayer() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell#getColumnPosition()
	 *
	 * @return
	 */
	@Override
	public int getColumnPosition() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell#getRowPosition()
	 *
	 * @return
	 */
	@Override
	public int getRowPosition() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell#getColumnIndex()
	 *
	 * @return
	 */
	@Override
	public int getColumnIndex() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell#getRowIndex()
	 *
	 * @return
	 */
	@Override
	public int getRowIndex() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell#getColumnSpan()
	 *
	 * @return
	 */
	@Override
	public int getColumnSpan() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell#getRowSpan()
	 *
	 * @return
	 */
	@Override
	public int getRowSpan() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell#isSpannedCell()
	 *
	 * @return
	 */
	@Override
	public boolean isSpannedCell() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell#getDisplayMode()
	 *
	 * @return
	 */
	@Override
	public String getDisplayMode() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell#getConfigLabels()
	 *
	 * @return
	 */
	@Override
	public LabelStack getConfigLabels() {
		return this.labelStack;
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell#getDataValue()
	 *
	 * @return
	 */
	@Override
	public Object getDataValue() {
		return this.cellValue;
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell#getBounds()
	 *
	 * @return
	 */
	@Override
	public Rectangle getBounds() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param cellValue
	 *            the new cell value to wrap
	 */
	public void setDataValue(Object cellValue) {
		this.cellValue = cellValue;
	}

	/**
	 * 
	 * @param labelStack
	 *            the new label stack to use
	 */
	public void setConfigLabels(LabelStack labelStack) {
		this.labelStack = labelStack;
	}

	/**
	 * 
	 * @return
	 *         the row object for this cell
	 */
	public Object getRowObject() {
		return rowObject;
	}

	/**
	 * 
	 * @param rowObject
	 *            the row object for this cell
	 */
	public void setRowObject(Object rowObject) {
		this.rowObject = rowObject;
	}

	/**
	 * 
	 * @return
	 *         the column object for this cell
	 */
	public Object getColumnObject() {
		return columnObject;
	}

	/**
	 * 
	 * @param columnObject
	 *            the column object for this cell
	 */
	public void setColumnObject(Object columnObject) {
		this.columnObject = columnObject;
	}

	/**
	 * 
	 * @param configRegistry
	 *            the config registry to use
	 */
	public void setConfigRegistry(IConfigRegistry configRegistry) {
		this.configRegistry = configRegistry;
	}
}
