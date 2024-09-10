/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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

package org.eclipse.papyrus.infra.nattable.fillhandle.command;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.fillhandle.command.FillHandlePasteCommand;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer.MoveDirectionEnum;

/**
 * The papyrus fill handle paste command which allow to manage the increment or decrement action and the prefi or suffix for the string series.
 */
public class PapyrusFillHandlePasteCommand extends FillHandlePasteCommand {

	/**
	 * Boolean to determinate if this an increment action.
	 */
	protected boolean isIncrement;

	/**
	 * Boolean to determinate if this is a prefix or suffix modification for string series.
	 */
	protected boolean isPrefix;

	/**
	 * Create a FillHandlePasteCommand that triggers a copy operation.
	 *
	 * @param configRegistry
	 *            The {@link IConfigRegistry} needed to dynamically read
	 *            configurations on command handling, e.g. editable state.
	 */
	public PapyrusFillHandlePasteCommand(final IConfigRegistry configRegistry) {
		this(FillHandleOperation.COPY, null, configRegistry, false, true);
	}

	/**
	 * Create a FillHandlePasteCommand.
	 *
	 * @param operation
	 *            The {@link FillHandleOperation} that should be triggered.
	 * @param direction
	 *            The direction in which the fill handle was dragged. Necessary
	 *            for the series operation to calculate the values.
	 * @param configRegistry
	 *            The {@link IConfigRegistry} needed to dynamically read
	 *            configurations on command handling, e.g. editable state.
	 * @param isIncrement
	 *            Boolean to determinate if this an increment action.
	 * @param isPrefix
	 *            Boolean to determinate if this is a prefix or suffix modification for string series.
	 */
	public PapyrusFillHandlePasteCommand(
			final FillHandleOperation operation, final MoveDirectionEnum direction, final IConfigRegistry configRegistry, final boolean isIncrement, final boolean isPrefix) {
		super(operation, direction, configRegistry);
		this.isIncrement = isIncrement;
		this.isPrefix = isPrefix;
	}

	/**
	 * Return <code>true</code> if this is an increment, <code>false</code> otherwise.
	 * 
	 * @return the isIncrement
	 */
	public boolean isIncrement() {
		return isIncrement;
	}

	/**
	 * Return <code>true</code> if this is a prefix series, <code>false</code> otherwise.
	 * 
	 * @return the isIncrement
	 */
	public boolean isPrefix() {
		return isPrefix;
	}



}
