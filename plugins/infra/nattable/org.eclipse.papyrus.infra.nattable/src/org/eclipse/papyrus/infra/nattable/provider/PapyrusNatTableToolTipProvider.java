/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
package org.eclipse.papyrus.infra.nattable.provider;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.tooltip.NatTableContentTooltip;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableproblem.Problem;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.Constants;
import org.eclipse.papyrus.infra.nattable.utils.LabelProviderCellContextElementWrapper;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.infra.services.decoration.DecorationService;
import org.eclipse.papyrus.infra.services.decoration.util.Decoration;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 *
 * @author Vincent Lorenzo
 *         This class provides the tooltip to display in the table
 * 
 *         This class allows to display as tooltip :
 *         <ul>
 *         <li>the text and image when there is Problem associated to a cell located in the body area</li>
 *         <li>the text and image when there is decoration associated to the object represented by a cell located in a header area</li>
 *         </ul>
 */
public class PapyrusNatTableToolTipProvider extends NatTableContentTooltip {

	/**
	 * the shared images registry
	 */
	private final ISharedImages sharedImage = PlatformUI.getWorkbench().getSharedImages();

	/**
	 * the wrapper used to get the text and image from the table label provider
	 */
	private final LabelProviderCellContextElementWrapper wrapper = new LabelProviderCellContextElementWrapper();

	/**
	 *
	 * Constructor.
	 *
	 * @param natTable
	 *            the nattable
	 * @param tooltipRegions
	 *            the region on which this tooltip provider will be applied
	 */
	public PapyrusNatTableToolTipProvider(final NatTable natTable, final String... tooltipRegions) {
		super(natTable, tooltipRegions);
	}

	/**
	 * 
	 * @param cell
	 *            a cell
	 * @return
	 * 		return <code>true</code> if the cell is in a header region
	 */
	protected boolean isInHeaderRegion(final ILayerCell cell) {
		LabelStack labels = cell.getConfigLabels();
		return labels.hasLabel(GridRegion.ROW_HEADER) || labels.hasLabel(GridRegion.COLUMN_HEADER);
	}

	/**
	 * 
	 * @param cell
	 *            a cell
	 * @return
	 * 		<code>true</code> if the cell is in the body region
	 */
	protected boolean isInBodyRegion(final ILayerCell cell) {
		LabelStack labels = cell.getConfigLabels();
		return labels.hasLabel(GridRegion.BODY);
	}

	/**
	 *
	 * @see org.eclipse.jface.window.DefaultToolTip#getImage(org.eclipse.swt.widgets.Event)
	 *
	 * @param event
	 * @return
	 */
	@Override
	protected Image getImage(final Event event) {
		ILayerCell cell = getCell(event);
		if (cell == null) {
			return null;
		}
		// we return the error image
		if (isInBodyRegion(cell) && isCellWithError(cell)) {
			return this.sharedImage.getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
		}

		// we return the image of the object with its decorator
		if (isInHeaderRegion(cell) && isCellWithDecorationMarker(cell)) {
			LabelProviderService serv = natTable.getConfigRegistry().getConfigAttribute(NattableConfigAttributes.LABEL_PROVIDER_SERVICE_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.LABEL_PROVIDER_SERVICE_ID);

			wrapper.setCell(cell);
			wrapper.setConfigRegistry(natTable.getConfigRegistry());
			wrapper.setObject(cell.getDataValue());
			// we return the image with the decoration
			return serv.getLabelProvider(Constants.TABLE_LABEL_PROVIDER_CONTEXT, wrapper).getImage(wrapper);
		}
		return super.getImage(event);
	}

	/**
	 *
	 * @see org.eclipse.nebula.widgets.nattable.tooltip.NatTableContentTooltip#getText(org.eclipse.swt.widgets.Event)
	 *
	 * @param event
	 * @return
	 * 		the text to display in the tooltip
	 *
	 */
	@Override
	protected String getText(final Event event) {
		ILayerCell cell = getCell(event);
		if (cell == null) {
			return null;
		}

		// we return the text of the error
		if (isInBodyRegion(cell) && isCellWithError(cell)) {
			return getProblemTooltip(cell.getDataValue());
		}

		// we return the text of the decoration
		if (isInHeaderRegion(cell) && isCellWithDecorationMarker(cell)) {
			DecorationService serv = natTable.getConfigRegistry().getConfigAttribute(NattableConfigAttributes.DECORATION_SERVICE_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.DECORATION_SERVICE_ID);
			Object value = cell.getDataValue();
			if (value != null) {
				Object element = AxisUtils.getRepresentedElement(value);
				if (null != element) {
					return Decoration.getMessageFromDecorations(serv, element);
				}
			}
		}
		return super.getText(event);
	}

	/**
	 * 
	 * @param cell
	 *            a cell
	 * @return
	 * 		<code>true</code> if there are decoration marked to display
	 */
	protected boolean isCellWithDecorationMarker(final ILayerCell cell) {
		if (cell == null) {
			return false;
		}
		final DecorationService serv = natTable.getConfigRegistry().getConfigAttribute(NattableConfigAttributes.DECORATION_SERVICE_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.DECORATION_SERVICE_ID);
		final Object value = cell.getDataValue();
		if (value != null) {
			final Object representedValue = AxisUtils.getRepresentedElement(value);
			if (representedValue != null) {
				return serv.getDecorations(representedValue, true).size() > 0;
			}
		}
		return false;
	}

	/**
	 *
	 * @param value
	 * @return
	 */
	protected String getProblemTooltip(final Object value) {
		ProblemLabelProvider provider = new ProblemLabelProvider();
		if (value instanceof Problem) {
			return provider.getTooltipText((EObject) value);
		} else if (value instanceof Collection<?>) {
			final StringBuilder builder = new StringBuilder();
			final Iterator<?> iter = ((Collection<?>) value).iterator();
			while (iter.hasNext()) {
				final Object current = iter.next();
				if (current instanceof Problem) {
					builder.append(provider.getTooltipText((EObject) current));
				}
				if (iter.hasNext()) {
					builder.append("\n"); //$NON-NLS-1$
				}
			}
			return builder.toString();
		}
		return null;
	}

	/**
	 *
	 * @param event
	 *            an event
	 * @return
	 * 		the cell for this event
	 */
	protected ILayerCell getCell(final Event event) {
		int col = this.natTable.getColumnPositionByX(event.x);
		int row = this.natTable.getRowPositionByY(event.y);
		return this.natTable.getCellByPosition(col, row);
	}

	/**
	 *
	 * @param event
	 *            an event
	 * @return
	 * 		<code>true</code> if the cell have a problem
	 */
	protected boolean isCellWithError(final ILayerCell cell) {
		boolean hasError = false;
		if (cell != null) {
			final Object value = cell.getDataValue();
			if (value instanceof Problem) {
				hasError = true;
			} else if (value instanceof Collection<?>) {
				final Iterator<?> iter = ((Collection<?>) value).iterator();
				while (!hasError && iter.hasNext()) {
					hasError = iter.next() instanceof Problem;
				}
			}
		}
		return hasError;
	}

	/**
	 *
	 * @see org.eclipse.nebula.widgets.nattable.tooltip.NatTableContentTooltip#shouldCreateToolTip(org.eclipse.swt.widgets.Event)
	 *
	 * @param event
	 * @return
	 */
	@Override
	protected boolean shouldCreateToolTip(final Event event) {
		final ILayerCell cell = getCell(event);
		if (cell == null) {
			return false;
		}
		if (isCellWithError(cell)) {
			return true;
		}
		if (isInHeaderRegion(getCell(event)) && isCellWithDecorationMarker(cell)) {
			return true;
		}
		if (!isDisplayingFullCellText(event)) {
			return true;
		}
		return false;
	}

	/**
	 *
	 * @param event
	 *            an event
	 * @return
	 * 		<code>true</code> if the cell size allow to display the full text
	 */
	protected boolean isDisplayingFullCellText(final Event event) {
		return true;// TODO : display a tooltip when the full text is not displayed in the cell
	}
}
