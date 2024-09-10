/*****************************************************************************
 * Copyright (c) 2014-2015, 2020 CEA LIST and others.
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
 *   Camille Letavernier - CEA LIST - Bug 464168 - Use the Context's EditingDomain
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 504077
 *   Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Bug 562619
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.layer;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.nebula.widgets.nattable.data.ISpanningDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.SpanningDataLayer;
import org.eclipse.nebula.widgets.nattable.resize.event.ColumnResizeEvent;
import org.eclipse.papyrus.infra.nattable.manager.refresh.StructuralRefreshConfiguration;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;

/**
 *
 * @author Quentin Le Menez
 * @see org.eclipse.nebula.widgets.nattable.layer.SpanningDataLayer
 *
 *
 */
public class PapyrusSpanningDataLayer extends SpanningDataLayer {

	private TransactionalEditingDomain contextDomain;

	private INattableModelManager manager;

	/**
	 * Constructor.
	 *
	 * @param dataProvider
	 * @param defaultColumnWidth
	 * @param defaultRowHeight
	 */
	public PapyrusSpanningDataLayer(final TransactionalEditingDomain contextDomain, ISpanningDataProvider dataProvider, int defaultColumnWidth, int defaultRowHeight) {
		super(dataProvider, defaultColumnWidth, defaultRowHeight);
		this.contextDomain = contextDomain;
		addConfiguration(new StructuralRefreshConfiguration());
	}

	/**
	 * Constructor.
	 *
	 * @param dataProvider
	 */
	public PapyrusSpanningDataLayer(final TransactionalEditingDomain contextDomain, ISpanningDataProvider dataProvider) {
		super(dataProvider);
		this.contextDomain = contextDomain;
		addConfiguration(new StructuralRefreshConfiguration());
	}


	/**
	 * Constructor.
	 *
	 * @param contextEditingDomain
	 * @param manager
	 * @param spanProvider
	 * @param defaultCellWidth
	 * @param defaultCellHeight
	 */
	public PapyrusSpanningDataLayer(TransactionalEditingDomain contextEditingDomain, INattableModelManager manager,
			ISpanningDataProvider spanProvider, int defaultCellWidth, int defaultCellHeight) {
		super(spanProvider, defaultCellWidth, defaultCellHeight);
		this.contextDomain = contextEditingDomain;
		this.manager = manager;
		addConfiguration(new StructuralRefreshConfiguration());
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.layer.SpanningDataLayer#setDataValue(int, int, java.lang.Object)
	 *
	 * @param columnIndex
	 * @param rowIndex
	 * @param newValue
	 */
	@Override
	public void setDataValue(final int columnIndex, final int rowIndex, final Object newValue) {
		RecordingCommand recordUpdate = new RecordingCommand(this.contextDomain) {

			@Override
			protected void doExecute() {
				// AbstractCellManager's setValue() takes care of the compatibility between the cell and the edit types
				// 469109: [Tree Table] set value problem when categories are hidden
				// https://bugs.eclipse.org/bugs/show_bug.cgi?id=469109
				PapyrusSpanningDataLayer.super.setDataValue(columnIndex, rowIndex, newValue);
			}
		};
		this.contextDomain.getCommandStack().execute(recordUpdate);
	}

	/**
	 * This allows to set the column width with a percentage.
	 *
	 * @param columnPosition
	 *            The column position to modify.
	 * @param width
	 *            The width as percentage.
	 * @param fireEvent
	 *            Boolean to determinate if layer event must be fire.
	 */
	public void setColumnWidthPercentageByPosition(int columnPosition, int width, boolean fireEvent) {
		this.columnWidthConfig.setPercentage(columnPosition, width);
		if (fireEvent) {
			fireLayerEvent(new ColumnResizeEvent(this, columnPosition));
		}
	}
}
