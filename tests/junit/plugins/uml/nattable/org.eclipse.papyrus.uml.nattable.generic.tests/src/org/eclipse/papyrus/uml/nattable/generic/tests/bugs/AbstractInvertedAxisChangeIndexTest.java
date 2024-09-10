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
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.generic.tests.bugs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.grid.layer.RowHeaderLayer;
import org.eclipse.nebula.widgets.nattable.layer.AbstractLayer;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;
import org.eclipse.papyrus.infra.nattable.menu.MenuUtils;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AbstractHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AxisIndexStyle;
import org.eclipse.papyrus.infra.nattable.utils.HeaderAxisConfigurationManagementUtils;
import org.eclipse.papyrus.infra.ui.util.EclipseCommandUtils;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.RadioState;
import org.junit.Assert;
import org.junit.Test;

/**
 * This abstract class allow to define the tests for the change axis index style when the axis are inverted.
 */
public abstract class AbstractInvertedAxisChangeIndexTest extends AbstractAxisChangeIndexTest {
	
	/**
	 * Constructor.
	 */
	public AbstractInvertedAxisChangeIndexTest() {
		super();
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.generic.tests.bugs.AbstractAxisChangeIndexTest#testChangeIndexInitialColumn()
	 *
	 * @throws Exception
	 */
	@Override
	@Test
	public void testChangeIndexInitialColumn() throws Exception {
		// Try to execute a resize command to check the re-creation
		IPageManager pageManager = fixture.getPageManager();
		List<Object> pages = pageManager.allPages();
		pageManager.openPage(pages.get(0));
		IEditorPart part = fixture.getEditor().getActiveEditor();
		Assert.assertTrue(part instanceof NatTableEditor);
		NatTableEditor editor = (NatTableEditor) part;
		INattableModelManager currentManager = (INattableModelManager) editor.getAdapter(INattableModelManager.class);
		MenuUtils.registerNatTableWidgetInEclipseContext(currentManager, new LabelStack(GridRegion.COLUMN_HEADER));
		Assert.assertTrue(currentManager instanceof INattableModelManager);

		// check the initial data
		checkNumericColumn(currentManager);

		// Get the command service
		ICommandService commandService = EclipseCommandUtils.getCommandService();
		Assert.assertNotNull(commandService);

		// Get the command and the handler
		Command cmd = commandService.getCommand("org.eclipse.papyrus.infra.nattable.column.index.style"); //$NON-NLS-1$
		IHandler handler = cmd.getHandler();
		Assert.assertTrue(handler.isEnabled());

		// Put the numeric parameter
		Map<String, String> parameter = new HashMap<String, String>(1);
		parameter.put(RadioState.PARAMETER_ID, AxisIndexStyle.ALPHABETIC.getName()); // $NON-NLS-1$

		// Execute the command with the correct parameter and check its result
		ExecutionEvent event = new ExecutionEvent(cmd, parameter, null, null);
		fixture.flushDisplayEvents();
		Object res = cmd.executeWithChecks(event);
		Assert.assertTrue(res instanceof IStatus);
		IStatus iStatus = (IStatus) res;
		Assert.assertTrue("Returned status is not OK", iStatus.isOK()); //$NON-NLS-1$

		// Check the numeric column data
		checkAlphabeticColumn(currentManager);

		// Put the alphabetic parameter
		parameter.clear();
		parameter.put(RadioState.PARAMETER_ID, AxisIndexStyle.NUMERIC.getName()); // $NON-NLS-1$

		// Execute the command with the correct parameter and check its result
		event = new ExecutionEvent(cmd, parameter, null, null);
		fixture.flushDisplayEvents();
		res = cmd.executeWithChecks(event);
		Assert.assertTrue(res instanceof IStatus);
		iStatus = (IStatus) res;
		Assert.assertTrue("Returned status is not OK", iStatus.isOK()); //$NON-NLS-1$

		// check the initial data
		checkNumericColumn(currentManager);
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.generic.tests.bugs.AbstractAxisChangeIndexTest#testChangeIndexInitialRow()
	 *
	 * @throws Exception
	 */
	@Override
	@Test
	public void testChangeIndexInitialRow() throws Exception {
		// Try to execute a resize command to check the re-creation
		IPageManager pageManager = fixture.getPageManager();
		List<Object> pages = pageManager.allPages();
		pageManager.openPage(pages.get(0));
		IEditorPart part = fixture.getEditor().getActiveEditor();
		Assert.assertTrue(part instanceof NatTableEditor);
		NatTableEditor editor = (NatTableEditor) part;
		INattableModelManager currentManager = (INattableModelManager) editor.getAdapter(INattableModelManager.class);
		Assert.assertTrue(currentManager instanceof INattableModelManager);
		MenuUtils.registerNatTableWidgetInEclipseContext(currentManager, new LabelStack(GridRegion.ROW_HEADER));
		// check the initial data
		checkAlphabeticRow(currentManager);

		// Get the command service
		ICommandService commandService = EclipseCommandUtils.getCommandService();
		Assert.assertNotNull(commandService);

		// Get the command and the handler
		Command cmd = commandService.getCommand("org.eclipse.papyrus.infra.nattable.row.index.style"); //$NON-NLS-1$
		IHandler handler = cmd.getHandler();
		Assert.assertTrue(handler.isEnabled());

		// Put the numeric parameter
		Map<String, String> parameter = new HashMap<String, String>(1);
		parameter.put(RadioState.PARAMETER_ID, AxisIndexStyle.NUMERIC.getName()); // $NON-NLS-1$

		// Execute the command with the correct parameter and check its result
		ExecutionEvent event = new ExecutionEvent(cmd, parameter, null, null);
		fixture.flushDisplayEvents();
		Object res = cmd.executeWithChecks(event);
		Assert.assertTrue(res instanceof IStatus);
		IStatus iStatus = (IStatus) res;
		Assert.assertTrue("Returned status is not OK", iStatus.isOK()); //$NON-NLS-1$

		// Check the numeric column data
		checkNumericRow(currentManager);

		// Put the alphabetic parameter
		parameter.clear();
		parameter.put(RadioState.PARAMETER_ID, AxisIndexStyle.ALPHABETIC.getName());

		// Execute the command with the correct parameter and check its result
		event = new ExecutionEvent(cmd, parameter, null, null);
		fixture.flushDisplayEvents();
		res = cmd.executeWithChecks(event);
		Assert.assertTrue(res instanceof IStatus);
		iStatus = (IStatus) res;
		Assert.assertTrue("Returned status is not OK", iStatus.isOK()); //$NON-NLS-1$

		// check the initial data
		checkAlphabeticRow(currentManager);
	}
	
	/**
	 * @see org.eclipse.papyrus.uml.nattable.generic.tests.bugs.AbstractAxisChangeIndexTest#checkAlphabeticColumn(org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 *
	 * @param currentManager
	 */
	@Override
	public void checkAlphabeticColumn(final INattableModelManager currentManager) {
		// Check the index style
		AbstractHeaderAxisConfiguration headerConfiguration = HeaderAxisConfigurationManagementUtils.getColumnAbstractHeaderAxisInTable(currentManager.getTable());
		if (null == headerConfiguration) {
			headerConfiguration = HeaderAxisConfigurationManagementUtils.getColumnAbstractHeaderAxisInTableConfiguration(currentManager.getTable());
		}
		Assert.assertEquals("This is not the alphabetic style", headerConfiguration.getIndexStyle(), AxisIndexStyle.ALPHABETIC); //$NON-NLS-1$

		// Check the data
		final AbstractLayer columnHeaderLayerIndex = ((NattableModelManager) currentManager).getColumnHeaderLayerStack().getColumnIndexDataLayer();
		Assert.assertTrue("The column layer is not a data layer", columnHeaderLayerIndex instanceof DataLayer); //$NON-NLS-1$
		Assert.assertEquals("The first column index is not corresponding to 'A'", INDEX_A, ((DataLayer) columnHeaderLayerIndex).getDataValueByPosition(0, 0)); //$NON-NLS-1$
		Assert.assertEquals("The second column index is not corresponding to 'B'", INDEX_B, ((DataLayer) columnHeaderLayerIndex).getDataValueByPosition(1, 0)); //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.papyrus.uml.nattable.generic.tests.bugs.AbstractAxisChangeIndexTest#checkNumericColumn(org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 *
	 * @param currentManager
	 */
	@Override
	public void checkNumericColumn(final INattableModelManager currentManager) {
		// Check the index style
		AbstractHeaderAxisConfiguration headerConfiguration = HeaderAxisConfigurationManagementUtils.getColumnAbstractHeaderAxisInTable(currentManager.getTable());
		if (null == headerConfiguration) {
			headerConfiguration = HeaderAxisConfigurationManagementUtils.getColumnAbstractHeaderAxisInTableConfiguration(currentManager.getTable());
		}
		Assert.assertEquals("This is not the numeric style", headerConfiguration.getIndexStyle(), AxisIndexStyle.NUMERIC); //$NON-NLS-1$

		// Check the data
		final AbstractLayer columnHeaderLayerIndex = ((NattableModelManager) currentManager).getColumnHeaderLayerStack().getColumnIndexDataLayer();
		Assert.assertTrue("The column layer is not a data layer", columnHeaderLayerIndex instanceof DataLayer); //$NON-NLS-1$
		Assert.assertEquals("The first column index is not corresponding to '0'", INDEX_0, ((DataLayer) columnHeaderLayerIndex).getDataValueByPosition(0, 0)); //$NON-NLS-1$
		Assert.assertEquals("The second column index is not corresponding to '1'", INDEX_1, ((DataLayer) columnHeaderLayerIndex).getDataValueByPosition(1, 0)); //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.papyrus.uml.nattable.generic.tests.bugs.AbstractAxisChangeIndexTest#checkNumericRow(org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 *
	 * @param currentManager
	 */
	@Override
	public void checkNumericRow(final INattableModelManager currentManager) {
		// Check the index style
		AbstractHeaderAxisConfiguration headerConfiguration = HeaderAxisConfigurationManagementUtils.getRowAbstractHeaderAxisInTable(currentManager.getTable());
		if (null == headerConfiguration) {
			headerConfiguration = HeaderAxisConfigurationManagementUtils.getRowAbstractHeaderAxisInTableConfiguration(currentManager.getTable());
		}
		Assert.assertEquals("This is not the numeric style", headerConfiguration.getIndexStyle(), AxisIndexStyle.NUMERIC); //$NON-NLS-1$

		// check the data
		final AbstractLayer rowHeaderLayerIndex = ((NattableModelManager) currentManager).getRowHeaderLayerStack().getRowHeaderLayerIndex();
		Assert.assertTrue("The row layer is not a row header layer", rowHeaderLayerIndex instanceof RowHeaderLayer); //$NON-NLS-1$
		Assert.assertEquals("The first row index is not corresponding to '0'", INDEX_0, ((RowHeaderLayer) rowHeaderLayerIndex).getDataValueByPosition(0, 0)); //$NON-NLS-1$
		Assert.assertEquals("The second row index is not corresponding to '1'", INDEX_1, ((RowHeaderLayer) rowHeaderLayerIndex).getDataValueByPosition(0, 1)); //$NON-NLS-1$
		Assert.assertEquals("The third row index is not corresponding to '2'", INDEX_2, ((RowHeaderLayer) rowHeaderLayerIndex).getDataValueByPosition(0, 2)); //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.papyrus.uml.nattable.generic.tests.bugs.AbstractAxisChangeIndexTest#checkAlphabeticRow(org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager)
	 *
	 * @param currentManager
	 */
	@Override
	public void checkAlphabeticRow(final INattableModelManager currentManager) {
		// Check the index style
		AbstractHeaderAxisConfiguration headerConfiguration = HeaderAxisConfigurationManagementUtils.getRowAbstractHeaderAxisInTable(currentManager.getTable());
		if (null == headerConfiguration) {
			headerConfiguration = HeaderAxisConfigurationManagementUtils.getRowAbstractHeaderAxisInTableConfiguration(currentManager.getTable());
		}
		Assert.assertEquals("This is not the alphabetic style", headerConfiguration.getIndexStyle(), AxisIndexStyle.ALPHABETIC); //$NON-NLS-1$

		// check the data
		final AbstractLayer rowHeaderLayerIndex = ((NattableModelManager) currentManager).getRowHeaderLayerStack().getRowHeaderLayerIndex();
		Assert.assertTrue("The row layer is not a row header layer", rowHeaderLayerIndex instanceof RowHeaderLayer); //$NON-NLS-1$
		Assert.assertEquals("The first row index is not corresponding to 'A'", INDEX_A, ((RowHeaderLayer) rowHeaderLayerIndex).getDataValueByPosition(0, 0)); //$NON-NLS-1$
		Assert.assertEquals("The second row index is not corresponding to 'B'", INDEX_B, ((RowHeaderLayer) rowHeaderLayerIndex).getDataValueByPosition(0, 1)); //$NON-NLS-1$
		Assert.assertEquals("The third row index is not corresponding to 'C'", INDEX_C, ((RowHeaderLayer) rowHeaderLayerIndex).getDataValueByPosition(0, 2)); //$NON-NLS-1$
	}

}
