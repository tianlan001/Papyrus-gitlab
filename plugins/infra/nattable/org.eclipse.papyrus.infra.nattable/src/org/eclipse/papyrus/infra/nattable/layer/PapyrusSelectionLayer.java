/*****************************************************************************
 * Copyright (c) 2013, 2017 CEA LIST.
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
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 476618
 *  Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 519383
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.layer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.nebula.widgets.nattable.coordinate.Range;
import org.eclipse.nebula.widgets.nattable.copy.command.CopyDataCommandHandler;
import org.eclipse.nebula.widgets.nattable.copy.command.CopyDataToClipboardCommand;
import org.eclipse.nebula.widgets.nattable.layer.IUniqueIndexLayer;
import org.eclipse.nebula.widgets.nattable.selection.ISelectionModel;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.papyrus.infra.nattable.selection.PapyrusSelectColumnCommandHandler;
import org.eclipse.papyrus.infra.nattable.selection.PapyrusSelectRowCommandHandler;
import org.eclipse.swt.graphics.Rectangle;

/**
 * Papyrus selection layer.
 */
public class PapyrusSelectionLayer extends SelectionLayer {

	/**
	 * Constructor.
	 *
	 * @param underlyingLayer
	 *            The underlying layer.
	 * @param useDefaultConfiguration
	 *            Boolean to determinate if the default configuration must be used.
	 */
	public PapyrusSelectionLayer(final IUniqueIndexLayer underlyingLayer, final boolean useDefaultConfiguration) {
		super(underlyingLayer, useDefaultConfiguration);
	}

	/**
	 * Constructor.
	 *
	 * @param underlyingLayer
	 *            The underlying layer.
	 * @param selectionModel
	 *            The selection model.
	 * @param useDefaultConfiguration
	 *            Boolean to determinate if the default configuration must be used.
	 * @param registerDefaultEventHandler
	 *            Boolean to determinate if the default event handler must be registered.
	 */
	public PapyrusSelectionLayer(final IUniqueIndexLayer underlyingLayer, final ISelectionModel selectionModel, final boolean useDefaultConfiguration, final boolean registerDefaultEventHandler) {
		super(underlyingLayer, selectionModel, useDefaultConfiguration, registerDefaultEventHandler);
	}

	/**
	 * Constructor.
	 *
	 * @param underlyingLayer
	 *            The underlying layer.
	 * @param selectionModel
	 *            The selection model.
	 * @param useDefaultConfiguration
	 *            Boolean to determinate if the default configuration must be used.
	 */
	public PapyrusSelectionLayer(final IUniqueIndexLayer underlyingLayer, final ISelectionModel selectionModel, final boolean useDefaultConfiguration) {
		super(underlyingLayer, selectionModel, useDefaultConfiguration);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param underlyingLayer
	 *            The underlying layer.
	 */
	public PapyrusSelectionLayer(final IUniqueIndexLayer underlyingLayer) {
		super(underlyingLayer);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.nebula.widgets.nattable.selection.SelectionLayer#registerCommandHandlers()
	 */
	@Override
	protected void registerCommandHandlers() {

		// Redefine the select row and column command handlers to use the papyrus command handlers
		this.selectRowCommandHandler = new PapyrusSelectRowCommandHandler(this);
		this.selectColumnCommandHandler = new PapyrusSelectColumnCommandHandler(this);

		super.registerCommandHandlers();
		unregisterCommandHandler(CopyDataToClipboardCommand.class);
		final CopyDataCommandHandler handler = new CopyDataCommandHandler(this);
		handler.setCopyFormattedText(true);
		registerCommandHandler(handler);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.selection.SelectionLayer#setLastSelectedRegion(org.eclipse.swt.graphics.Rectangle)
	 */
	@Override
	public void setLastSelectedRegion(Rectangle region) {
		super.setLastSelectedRegion(region);
	}

	/**
	 * This allows to get the selected axis positions managed as range.
	 * 
	 * @param positions
	 *            The positions of axis selected.
	 * @return The selected axis positions managed as range.
	 */
	public List<Range> getRangeSelectedAxis(final Collection<Integer> positions) {
		List<Range> selectedRange = new ArrayList<Range>();

		for (int position : positions) {
			final Iterator<Range> existingRangesIterator = selectedRange.iterator();
			boolean found = false;

			while (!found && existingRangesIterator.hasNext()) {
				final Range existingRange = existingRangesIterator.next();

				if ((existingRange.start - 1) == position) {
					existingRange.start = position;
					found = true;
				} else if ((existingRange.end == position)) {
					existingRange.end = position + 1;
					found = true;
				}
			}

			if (!found) {
				selectedRange.add(new Range(position, position + 1));
			}
		}

		return selectedRange;
	}

	/**
	 * @return The underlying layer of this selection layer
	 * @since 4.0
	 */
	public IUniqueIndexLayer getSelectionUnderlyingLayer() {
		return this.underlyingLayer;
	}
}
