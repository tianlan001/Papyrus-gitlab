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
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.clazz.config.tests.fillhandle;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.command.ILayerCommand;
import org.eclipse.nebula.widgets.nattable.fillhandle.command.FillHandlePasteCommand.FillHandleOperation;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer.MoveDirectionEnum;
import org.eclipse.papyrus.infra.nattable.fillhandle.command.PapyrusFillHandlePasteCommand;
import org.eclipse.papyrus.infra.nattable.manager.table.TreeNattableModelManager;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.swt.graphics.Rectangle;

/**
 * This allows to manage the fill handle tests for the name column.
 * As showed in the bug 499466, the fill action can not handle correctly the string such as "001_Req".
 * It returns "1_Req", "2_Req" for a down-fill prefix action instead of "001_Req" and "002_Req".
 */
@PluginResource("resources/fillhandle/FillHandleNameBug499466BeginningPrefix_V1_V3_V1_Test.di")
public class FillHandleNameBug499466BeginningPrefix_V1_V3_V1_Test extends AbstractFillHandleTest {

	/**
	 * Constructor.
	 */
	public FillHandleNameBug499466BeginningPrefix_V1_V3_V1_Test() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.fillhandle.AbstractFillHandleTest#selectCellToFillToUp(org.eclipse.papyrus.infra.nattable.manager.table.TreeNattableModelManager)
	 */
	@Override
	protected void selectCellToFillToUp(final TreeNattableModelManager treeManager) {
		treeManager.getBodyLayerStack().getSelectionLayer().setSelectedCell(0, 12);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.fillhandle.AbstractFillHandleTest#selectCellToFillToDown(org.eclipse.papyrus.infra.nattable.manager.table.TreeNattableModelManager)
	 */
	@Override
	protected void selectCellToFillToDown(final TreeNattableModelManager treeManager) {
		treeManager.getBodyLayerStack().getSelectionLayer().setSelectedCell(0, 3);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.fillhandle.AbstractFillHandleTest#setFillHandlerRegionToUp(org.eclipse.papyrus.infra.nattable.manager.table.TreeNattableModelManager)
	 */
	@Override
	protected void setFillHandlerRegionToUp(final TreeNattableModelManager treeManager) {
		treeManager.getBodyLayerStack().getSelectionLayer().setFillHandleRegion(new Rectangle(0, 3, 1, 10));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.fillhandle.AbstractFillHandleTest#setFillHandlerRegionToDown(org.eclipse.papyrus.infra.nattable.manager.table.TreeNattableModelManager)
	 */
	@Override
	protected void setFillHandlerRegionToDown(final TreeNattableModelManager treeManager) {
		treeManager.getBodyLayerStack().getSelectionLayer().setFillHandleRegion(new Rectangle(0, 3, 1, 10));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.fillhandle.AbstractFillHandleTest#getCopyCommand()
	 */
	@Override
	protected ILayerCommand getCopyCommand(final NatTable natTable) {
		return new PapyrusFillHandlePasteCommand(FillHandleOperation.COPY, MoveDirectionEnum.DOWN, natTable.getConfigRegistry(), false, true);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.fillhandle.AbstractFillHandleTest#getIncrementUpCommand(org.eclipse.nebula.widgets.nattable.NatTable)
	 */
	@Override
	protected ILayerCommand getIncrementUpCommand(final NatTable natTable) {
		return new PapyrusFillHandlePasteCommand(FillHandleOperation.SERIES, MoveDirectionEnum.UP, natTable.getConfigRegistry(), true, true);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.fillhandle.AbstractFillHandleTest#getIncrementDownCommand(org.eclipse.nebula.widgets.nattable.NatTable)
	 */
	@Override
	protected ILayerCommand getIncrementDownCommand(final NatTable natTable) {
		return new PapyrusFillHandlePasteCommand(FillHandleOperation.SERIES, MoveDirectionEnum.DOWN, natTable.getConfigRegistry(), true, true);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.fillhandle.AbstractFillHandleTest#getDecrementUpCommand(org.eclipse.nebula.widgets.nattable.NatTable)
	 */
	@Override
	protected ILayerCommand getDecrementUpCommand(final NatTable natTable) {
		return new PapyrusFillHandlePasteCommand(FillHandleOperation.SERIES, MoveDirectionEnum.UP, natTable.getConfigRegistry(), false, true);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.fillhandle.AbstractFillHandleTest#getDecrementDownCommand(org.eclipse.nebula.widgets.nattable.NatTable)
	 */
	@Override
	protected ILayerCommand getDecrementDownCommand(final NatTable natTable) {
		return new PapyrusFillHandlePasteCommand(FillHandleOperation.SERIES, MoveDirectionEnum.DOWN, natTable.getConfigRegistry(), false, true);
	}
}
