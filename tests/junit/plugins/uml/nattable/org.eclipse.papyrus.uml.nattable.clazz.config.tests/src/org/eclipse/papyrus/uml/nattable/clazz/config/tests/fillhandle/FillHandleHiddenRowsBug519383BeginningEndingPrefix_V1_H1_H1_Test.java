/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, ALL4TEC and others.
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
import org.eclipse.papyrus.junit.utils.rules.ActiveTable;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.swt.graphics.Rectangle;
import org.junit.Test;

/**
 * This test protects the prefix fill actions for table with hidden rows.
 * As showed in the bug 519383, the fill action can not handle correctly when some rows are hiding in the table,
 * as a result of Hide All Categories, Show Level-x Categories or using row filter in the column header.
 */
@PluginResource("resources/fillhandle/FillHandleHiddenRowsBug519383BeginningEndingPrefix_V1_H1_H1_Test.di")
public class FillHandleHiddenRowsBug519383BeginningEndingPrefix_V1_H1_H1_Test extends AbstractFillHandleTest {

	/**
	 * Constructor.
	 */
	public FillHandleHiddenRowsBug519383BeginningEndingPrefix_V1_H1_H1_Test() {
		super();
	}

	/**
	 * <pre>
	 * {@inheritDoc}
	 * Overridden to use RequirementTreeTable as active table instead of the default one.
	 * </pre>
	 */
	@Test
	@ActiveTable("GenericTreeTable")
	@Override
	public void testCopy() throws Exception {
		super.testCopy();
	}

	/**
	 * <pre>
	 * {@inheritDoc}
	 * Overridden to use RequirementTreeTable as active table instead of the default one.
	 * </pre>
	 */
	@Test
	@ActiveTable("GenericTreeTable")
	@Override
	public void testIncrementUp() throws Exception {
		super.testIncrementUp();
	}

	/**
	 * <pre>
	 * {@inheritDoc}
	 * Overridden to use RequirementTreeTable as active table instead of the default one.
	 * </pre>
	 */
	@Test
	@ActiveTable("GenericTreeTable")
	@Override
	public void testIncrementDown() throws Exception {
		super.testIncrementDown();
	}

	/**
	 * <pre>
	 * {@inheritDoc}
	 * Overridden to use RequirementTreeTable as active table instead of the default one.
	 * </pre>
	 */
	@Test
	@ActiveTable("GenericTreeTable")
	@Override
	public void testDecrementUp() throws Exception {
		super.testDecrementUp();
	}

	/**
	 * <pre>
	 * {@inheritDoc}
	 * Overridden to use RequirementTreeTable as active table instead of the default one.
	 * </pre>
	 */
	@Test
	@ActiveTable("GenericTreeTable")
	@Override
	public void testDecrementDown() throws Exception {
		super.testDecrementDown();
	}

	/**
	 * <pre>
	 * {@inheritDoc}
	 * Overridden to use RequirementTreeTable as active table instead of the default one.
	 * </pre>
	 */
	@Override
	@Test
	@ActiveTable("GenericTreeTable")
	public void checkTestConsistency() {
		super.checkTestConsistency();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void selectCellToFillToUp(final TreeNattableModelManager treeManager) {
		treeManager.getBodyLayerStack().getSelectionLayer().setSelectedCell(0, 8);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void selectCellToFillToDown(final TreeNattableModelManager treeManager) {
		treeManager.getBodyLayerStack().getSelectionLayer().setSelectedCell(0, 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setFillHandlerRegionToUp(final TreeNattableModelManager treeManager) {
		treeManager.getBodyLayerStack().getSelectionLayer().setFillHandleRegion(new Rectangle(0, 0, 1, 13));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setFillHandlerRegionToDown(final TreeNattableModelManager treeManager) {
		treeManager.getBodyLayerStack().getSelectionLayer().setFillHandleRegion(new Rectangle(0, 0, 1, 13));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ILayerCommand getCopyCommand(final NatTable natTable) {
		return new PapyrusFillHandlePasteCommand(FillHandleOperation.COPY, MoveDirectionEnum.DOWN, natTable.getConfigRegistry(), false, true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ILayerCommand getIncrementUpCommand(final NatTable natTable) {
		return new PapyrusFillHandlePasteCommand(FillHandleOperation.SERIES, MoveDirectionEnum.UP, natTable.getConfigRegistry(), true, true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ILayerCommand getIncrementDownCommand(final NatTable natTable) {
		return new PapyrusFillHandlePasteCommand(FillHandleOperation.SERIES, MoveDirectionEnum.DOWN, natTable.getConfigRegistry(), true, true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ILayerCommand getDecrementUpCommand(final NatTable natTable) {
		return new PapyrusFillHandlePasteCommand(FillHandleOperation.SERIES, MoveDirectionEnum.UP, natTable.getConfigRegistry(), false, true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ILayerCommand getDecrementDownCommand(final NatTable natTable) {
		return new PapyrusFillHandlePasteCommand(FillHandleOperation.SERIES, MoveDirectionEnum.DOWN, natTable.getConfigRegistry(), false, true);
	}

	/**
	 * <pre>
	 * {@inheritDoc}
	 * Overridden to hide all categories in the table before doing the command.
	 * </pre>
	 */
	@Override
	protected void executeCommand(final TreeNattableModelManager treeManager, final ILayerCommand command, final String resultPostFileName, final boolean isUpDirection) throws Exception {
		// Hiding all categories before executing the command
		hideAllCategories(treeManager);
		super.executeCommand(treeManager, command, resultPostFileName, isUpDirection);
	}
}
