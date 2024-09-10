/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.xtext.valuespecification.celleditor;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.nebula.widgets.nattable.data.convert.IDisplayConverter;
import org.eclipse.nebula.widgets.nattable.edit.editor.ICellEditor;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.TextPainter;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.nattable.celleditor.config.AbstractCellEditorConfiguration;
import org.eclipse.papyrus.infra.nattable.manager.table.ITableAxisElementProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.uml2.uml.ValueSpecification;

/**
 * The configuration for the ConnectorEnd cell editor
 */
public class ValueSpecificationCellEditorConfiguration extends AbstractCellEditorConfiguration {

	/**
	 * The identifier of xtext cell editor for the ValueSpecification.
	 */
	private static final String XTEXT_VALUE_SPECIFICATION_CELL_EDITOR_IDENTIFIER = "XTEXT_ValueSpecification_Cell_Editor"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.config.IAxisCellEditorConfiguration#getDisplayConvert(org.eclipse.papyrus.infra.nattable.model.nattable.Table, java.lang.Object, org.eclipse.jface.viewers.ILabelProvider)
	 */
	@Override
	public IDisplayConverter getDisplayConvert(Table table, Object axisElement, ILabelProvider provider) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.config.IAxisCellEditorConfiguration#getCellPainter(org.eclipse.papyrus.infra.nattable.model.nattable.Table, java.lang.Object)
	 */
	@Override
	public ICellPainter getCellPainter(Table table, Object axisElement) {
		return new TextPainter();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.config.IAxisCellEditorConfiguration#getICellEditor(org.eclipse.papyrus.infra.nattable.model.nattable.Table, java.lang.Object, org.eclipse.papyrus.infra.nattable.manager.table.ITableAxisElementProvider)
	 */
	@Override
	public ICellEditor getICellEditor(Table table, Object axisElement, ITableAxisElementProvider elementProvider) {
		return new ValueSpecificationCellEditor(table, axisElement, elementProvider);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.config.IAxisCellEditorConfiguration#getDisplayMode(org.eclipse.papyrus.infra.nattable.model.nattable.Table, java.lang.Object)
	 */
	@Override
	public String getDisplayMode(Table table, Object axisElement) {
		return DisplayMode.EDIT;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.config.IAxisCellEditorConfiguration#getEditorConfigId()
	 */
	@Override
	public String getEditorConfigId() {
		return XTEXT_VALUE_SPECIFICATION_CELL_EDITOR_IDENTIFIER;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.celleditor.config.IAxisCellEditorConfiguration#handles(org.eclipse.papyrus.infra.nattable.model.nattable.Table, java.lang.Object)
	 */
	@Override
	public boolean handles(final Table table, final Object object) {
		boolean result = false;
		if (object instanceof EStructuralFeature) {
			result = ((EStructuralFeature) object).getEType().getInstanceClassName().equals(ValueSpecification.class.getName());
		}
		return result;
	}

}
