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
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AbstractHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AxisIndexStyle;
import org.eclipse.papyrus.infra.nattable.utils.HeaderAxisConfigurationManagementUtils;
import org.eclipse.papyrus.infra.ui.util.EclipseCommandUtils;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.TableUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.RadioState;
import org.eclipse.uml2.uml.Model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * This abstract class allow to define the tests for the change axis index style.
 */
@PluginResource("resources/bugs/bug473155/AxisChangeIndex.di")
public abstract class AbstractAxisChangeIndexTest extends AbstractPapyrusTest {

	/**
	 * The table name.
	 */
	private static final String TABLE_NAME = "GenericTable0"; //$NON-NLS-1$

	/**
	 * The first alphabetic index.
	 */
	protected static final String INDEX_A = "A"; //$NON-NLS-1$

	/**
	 * The second alphabetic index.
	 */
	protected static final String INDEX_B = "B"; //$NON-NLS-1$

	/**
	 * The third alphabetic index.
	 */
	protected static final String INDEX_C = "C"; //$NON-NLS-1$

	/**
	 * The first numeric index.
	 */
	protected static final int INDEX_0 = 0;

	/**
	 * The sexond numeric index.
	 */
	protected static final int INDEX_1 = 1;

	/**
	 * The third numeric index.
	 */
	protected static final int INDEX_2 = 2;


	/**
	 * The papyrus fixture.
	 */
	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();

	/**
	 * Constructor.
	 */
	public AbstractAxisChangeIndexTest() {
		super();
	}

	/**
	 * This allow to initialize the tests.
	 */
	@Before
	public void init() {
		final Model model = (Model) fixture.getModel();
		Assert.assertNotNull("RootModel is null", model); //$NON-NLS-1$

		// Get the table and open it
		Table mainTable = TableUtils.getNotationFirstTable(fixture.getModelSet(), TABLE_NAME);
		fixture.getPageManager().openPage(mainTable);
		fixture.flushDisplayEvents();
	}

	/**
	 * The test for the change index of the column.
	 * 
	 * @throws Exception
	 *             The exception.
	 */
	@Test
	public void testChangeIndexInitialColumn() throws Exception {
		// Open the table and get the manager
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
		checkAlphabeticColumn(currentManager);

		// Get the command service
		ICommandService commandService = EclipseCommandUtils.getCommandService();
		Assert.assertNotNull(commandService);

		// Get the command and the handler
		Command cmd = commandService.getCommand("org.eclipse.papyrus.infra.nattable.column.index.style"); //$NON-NLS-1$
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
		checkNumericColumn(currentManager);

		// Put the alphabetic parameter
		parameter.clear();
		parameter.put(RadioState.PARAMETER_ID, AxisIndexStyle.ALPHABETIC.getName()); // $NON-NLS-1$

		// Execute the command with the correct parameter and check its result
		event = new ExecutionEvent(cmd, parameter, null, null);
		fixture.flushDisplayEvents();
		res = cmd.executeWithChecks(event);
		Assert.assertTrue(res instanceof IStatus);
		iStatus = (IStatus) res;
		Assert.assertTrue("Returned status is not OK", iStatus.isOK()); //$NON-NLS-1$

		// check the initial data
		checkAlphabeticColumn(currentManager);
	}

	/**
	 * The test for the change index of the row.
	 * 
	 * @throws Exception
	 *             The exception.
	 */
	@Test
	public void testChangeIndexInitialRow() throws Exception {
		// Open the page and get the manager
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
		checkNumericRow(currentManager);

		// Get the command service
		ICommandService commandService = EclipseCommandUtils.getCommandService();
		Assert.assertNotNull(commandService);

		// Get the command and the handler
		Command cmd = commandService.getCommand("org.eclipse.papyrus.infra.nattable.row.index.style"); //$NON-NLS-1$
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
		checkAlphabeticRow(currentManager);

		// Put the alphabetic parameter
		parameter.clear();
		parameter.put(RadioState.PARAMETER_ID, AxisIndexStyle.NUMERIC.getName());

		// Execute the command with the correct parameter and check its result
		event = new ExecutionEvent(cmd, parameter, null, null);
		fixture.flushDisplayEvents();
		res = cmd.executeWithChecks(event);
		Assert.assertTrue(res instanceof IStatus);
		iStatus = (IStatus) res;
		Assert.assertTrue("Returned status is not OK", iStatus.isOK()); //$NON-NLS-1$

		// check the initial data
		checkNumericRow(currentManager);
	}

	/**
	 * This allow to check the alphabetic column.
	 * 
	 * @param currentManager
	 *            The nattable model manager.
	 */
	public void checkAlphabeticColumn(final INattableModelManager currentManager) {
		// Check the index style
		AbstractHeaderAxisConfiguration headerConfiguration = HeaderAxisConfigurationManagementUtils.getColumnAbstractHeaderAxisInTable(currentManager.getTable());
		if (null == headerConfiguration) {
			headerConfiguration = HeaderAxisConfigurationManagementUtils.getColumnAbstractHeaderAxisInTableConfiguration(currentManager.getTable());
		}
		Assert.assertEquals("This is not the alphabetic style", headerConfiguration.getIndexStyle(), AxisIndexStyle.ALPHABETIC); //$NON-NLS-1$

		// Check the data
		final AbstractLayer columnHeaderLayerIndex = ((NattableModelManager) currentManager).getColumnHeaderLayerStack().getColumnIndexDataLayer();
		Assert.assertTrue("The column layer is not a data lyer", columnHeaderLayerIndex instanceof DataLayer); //$NON-NLS-1$
		Assert.assertEquals("The first column index is not corresponding to 'A'", INDEX_A, ((DataLayer) columnHeaderLayerIndex).getDataValueByPosition(0, 0)); //$NON-NLS-1$
		Assert.assertEquals("The second column index is not corresponding to 'B'", INDEX_B, ((DataLayer) columnHeaderLayerIndex).getDataValueByPosition(1, 0)); //$NON-NLS-1$
		Assert.assertEquals("The third column index is not corresponding to 'C'", INDEX_C, ((DataLayer) columnHeaderLayerIndex).getDataValueByPosition(2, 0)); //$NON-NLS-1$
	}

	/**
	 * This allow to check the numeric column.
	 * 
	 * @param currentManager
	 *            The nattable model manager.
	 */
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
		Assert.assertEquals("The third column index is not corresponding to '2'", INDEX_2, ((DataLayer) columnHeaderLayerIndex).getDataValueByPosition(2, 0)); //$NON-NLS-1$
	}

	/**
	 * This allow to check the numeric row.
	 * 
	 * @param currentManager
	 *            The nattable model manager.
	 */
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
	}

	/**
	 * This allow to check the alphabetic row
	 * 
	 * @param currentManager
	 *            The nattable model manager.
	 */
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
	}
}
