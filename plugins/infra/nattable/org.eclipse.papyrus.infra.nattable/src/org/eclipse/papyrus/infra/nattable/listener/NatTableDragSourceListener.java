/*****************************************************************************
 * Copyright (c) 2016, 2018 CEA LIST, ALL4TEC and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *  Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - Bug 535120
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.listener;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.ui.util.CellEdgeDetectUtil;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.StringListValueStyle;
import org.eclipse.papyrus.infra.nattable.utils.NamedStyleConstants;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.graphics.Point;

/**
 * A {@link DragSourceListener} for {@link NatTable}.
 * Region where drag is enable is define throw String List Value style in configuration file.
 * List of available region are available in {@link GridRegion}.
 * 
 * @since 3.0
 */
public class NatTableDragSourceListener implements DragSourceListener {

	/** The selection. */
	protected ISelection selection;

	/** The nattable. */
	private NatTable nattable;

	/** The nat table model manager. */
	private INattableModelManager tableManager;

	private Table table;

	/**
	 * Constructor.
	 * 
	 * @param tableManager
	 *            The table manager.
	 * @param nattable
	 *            The NatTable.
	 * @param table
	 *            The table.
	 * @param gridRegion
	 *            the {@link GridRegion} source from the drag start in the nattable.
	 */
	public NatTableDragSourceListener(final INattableModelManager tableManager, final NatTable nattable, Table table) {
		this.tableManager = tableManager;
		this.nattable = nattable;
		this.table = table;
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.swt.dnd.DragSourceListener#dragStart(org.eclipse.swt.dnd.DragSourceEvent)
	 */
	@Override
	public void dragStart(final DragSourceEvent event) {
		event.doit = false;
		for (int i = 0; i < getGridRegions().size() && !event.doit; i++) {

			if (!isResizing(event) && isInsideTheTable(event) && this.nattable.getRegionLabelsByXY(event.x, event.y).hasLabel(getGridRegions().get(i))) {
				event.doit = true;
			}
		}

		if (event.doit) {
			selection = tableManager.getSelectionInTable();
			LocalSelectionTransfer.getTransfer().setSelection(selection);
			LocalSelectionTransfer.getTransfer().setSelectionSetTime(System.currentTimeMillis());
		}
	}

	/**
	 * 
	 * @param event
	 *            the event
	 * @return
	 * 		<code>true</code> if we are in the table
	 */
	private boolean isInsideTheTable(final DragSourceEvent event) {
		//see bug 535120
		return this.nattable.getRegionLabelsByXY(event.x, event.y) != null;
		// alternative method:
		// INattableModelManager manager = this.nattable.getConfigRegistry().getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
		// LocationValue location = manager.getLocationInTheTable(new Point(event.x, event.y));
	}

	/**
	 * @return The grid region where drag is enabled.
	 */
	protected List<String> getGridRegions() {
		List<String> regions = new ArrayList<String>();

		// // Get in the table
		StringListValueStyle values = (StringListValueStyle) table.getNamedStyle(NattablestylePackage.eINSTANCE.getStringListValueStyle(), NamedStyleConstants.DRAG_REGIONS);
		if (null == values) {
			// If not, get in the configuration
			values = (StringListValueStyle) table.getTableConfiguration().getNamedStyle(NattablestylePackage.eINSTANCE.getStringListValueStyle(), NamedStyleConstants.DRAG_REGIONS);
		}

		if (null != values) {
			EList<String> stringListValue = values.getStringListValue();
			for (String region : stringListValue) {
				if (table.isInvertAxis()) {
					if (GridRegion.ROW_HEADER.equals(region)) {
						region = GridRegion.COLUMN_HEADER;
					} else if (GridRegion.COLUMN_HEADER.equals(region)) {
						region = GridRegion.ROW_HEADER;
					}
				}
				regions.add(region);
			}
		}

		return regions;
	}


	/**
	 * @return true if the drag event occurs during a resize.
	 */
	protected boolean isResizing(final DragSourceEvent event) {
		return CellEdgeDetectUtil.getRowPositionToResize(this.nattable, new Point(event.x, event.y)) >= 0
				|| CellEdgeDetectUtil.getColumnPositionToResize(this.nattable, new Point(event.x, event.y)) >= 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.swt.dnd.DragSourceListener#dragSetData(org.eclipse.swt.dnd.DragSourceEvent)
	 */
	@Override
	public void dragSetData(final DragSourceEvent event) {
		if (LocalTransfer.getInstance().isSupportedType(event.dataType)) {
			event.data = selection;
		} else if (LocalSelectionTransfer.getTransfer().isSupportedType(event.dataType)) {
			event.data = LocalSelectionTransfer.getTransfer().getSelection();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.swt.dnd.DragSourceListener#dragFinished(org.eclipse.swt.dnd.DragSourceEvent)
	 */
	@Override
	public void dragFinished(final DragSourceEvent event) {
		selection = null;
		LocalTransfer.getInstance().javaToNative(null, null);
		LocalSelectionTransfer.getTransfer().setSelection(null);
	}
}