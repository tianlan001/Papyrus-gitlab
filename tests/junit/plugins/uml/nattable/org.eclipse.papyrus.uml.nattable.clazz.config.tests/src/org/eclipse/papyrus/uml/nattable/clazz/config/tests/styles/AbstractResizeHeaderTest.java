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

package org.eclipse.papyrus.uml.nattable.clazz.config.tests.styles;

import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.AbstractNattableWidgetManager;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.TreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AbstractHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.HeaderAxisConfigurationManagementUtils;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.TableUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.Model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * This allows to manage the resize of header tests.
 */
public abstract class AbstractResizeHeaderTest extends AbstractPapyrusTest {

	/**
	 * The table name.
	 */
	protected static final String TABLE_NAME = "ClassTreeTable"; //$NON-NLS-1$

	/**
	 * The string used on template name style to replace for rows colum header.
	 */
	protected static final String NAME_STYLE_TEMPLATE_TO_REPLACE = "..."; //$NON-NLS-1$

	/**
	 * The papyrus fixture.
	 */
	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();

	/**
	 * The current model.
	 */
	public Model model = null;

	/**
	 * The nattable model manager.
	 */
	public INattableModelManager currentManager = null;

	/**
	 * Constructor.
	 */
	public AbstractResizeHeaderTest() {
		super();
	}

	/**
	 * Initialize.
	 */
	@Before
	public void init() {
		// Get the model
		model = (Model) fixture.getModel();
		Assert.assertNotNull("RootModel is null", model); //$NON-NLS-1$

		// Get the table and open it
		Table mainTable = TableUtils.getNotationFirstTable(fixture.getModelSet(), TABLE_NAME);
		fixture.getPageManager().openPage(mainTable);
		fixture.flushDisplayEvents();

		// Get the table and its manager
		final IPageManager pageManager = fixture.getPageManager();
		final List<Object> pages = pageManager.allPages();
		pageManager.openPage(pages.get(0));
		final IEditorPart part = fixture.getEditor().getActiveEditor();
		Assert.assertTrue(part instanceof NatTableEditor);
		final NatTableEditor editor = (NatTableEditor) part;
		currentManager = (INattableModelManager) editor.getAdapter(INattableModelManager.class);
		Assert.assertTrue("The table manage must be a tree table manager", currentManager instanceof TreeNattableModelManager); //$NON-NLS-1$
	}

	/**
	 * This allows to test the resize of the column header.
	 * 
	 * @throws Exception
	 *             The exception
	 */
	@Test
	public void testResizeColumnHeader() throws Exception {
		DataLayer tableColumnIndexHeaderLayer = ((AbstractNattableWidgetManager) currentManager).getColumnHeaderLayerStack().getColumnIndexDataLayer();
		DataLayer tableColumnLabelHeaderLayer = ((AbstractNattableWidgetManager) currentManager).getColumnHeaderLayerStack().getColumnLabelDataLayer();

		// Check the initial column header height
		checkInitialColumnHeaderHeight(tableColumnIndexHeaderLayer, tableColumnLabelHeaderLayer);

		// Resize the row header
		resizeColumnheader(tableColumnIndexHeaderLayer, tableColumnLabelHeaderLayer);

		// Save the project
		IEditorPart part = fixture.getEditor().getActiveEditor();
		part.doSave(new NullProgressMonitor());

		AbstractHeaderAxisConfiguration columnHeader = HeaderAxisConfigurationManagementUtils.getColumnAbstractHeaderAxisConfigurationUsedInTable(currentManager.getTable());

		// Check the modified height
		checkModifiedColumnHeaderHeight(tableColumnIndexHeaderLayer, tableColumnLabelHeaderLayer, columnHeader);

		// Close the table
		final Table mainTable = TableUtils.getNotationFirstTable(fixture.getModelSet(), TABLE_NAME);
		fixture.getPageManager().closePage(mainTable);
		fixture.flushDisplayEvents();

		// Reopen the table and check its height
		init();

		// Re-get the values after the reopen
		tableColumnIndexHeaderLayer = ((AbstractNattableWidgetManager) currentManager).getColumnHeaderLayerStack().getColumnIndexDataLayer();
		tableColumnLabelHeaderLayer = ((AbstractNattableWidgetManager) currentManager).getColumnHeaderLayerStack().getColumnLabelDataLayer();
		columnHeader = HeaderAxisConfigurationManagementUtils.getColumnAbstractHeaderAxisConfigurationUsedInTable(currentManager.getTable());

		// Check one more time the modified height after the reopen
		checkModifiedColumnHeaderHeight(tableColumnIndexHeaderLayer, tableColumnLabelHeaderLayer, columnHeader);
	}

	/**
	 * This allows to resize column header.
	 * 
	 * @param tableRowIndexHeaderLayer
	 *            The index header layer.
	 * @param tableRowLabelHeaderLayer
	 *            The label header layer.
	 */
	protected abstract void resizeColumnheader(final DataLayer tableColumnIndexHeaderLayer, final DataLayer tableColumnLabelHeaderLayer);

	/**
	 * This allows to check the initial column header height of the index and of the label.
	 * 
	 * @param tableColumnIndexHeaderLayer
	 *            The index header layer.
	 * @param tableColumnLabelHeaderLayer
	 *            The label header layer.
	 */
	protected abstract void checkInitialColumnHeaderHeight(final DataLayer tableColumnIndexHeaderLayer, final DataLayer tableColumnLabelHeaderLayer);

	/**
	 * This allows to check the initial column header height of the index and of the label.
	 * 
	 * @param tableColumnIndexHeaderLayer
	 *            The index header layer.
	 * @param tableColumnLabelHeaderLayer
	 *            The label header layer.
	 * @param columnHeader
	 *            The header column axis configuration.
	 */
	protected abstract void checkModifiedColumnHeaderHeight(final DataLayer tableColumnIndexHeaderLayer, final DataLayer tableColumnLabelHeaderLayer, final AbstractHeaderAxisConfiguration columnHeader);

	/**
	 * This allows to test the resize of the row header.
	 * 
	 * @throws Exception
	 *             The exception
	 */
	@Test
	public void testResizeRowsHeader() throws Exception {
		DataLayer tableRowIndexHeaderLayer = ((AbstractNattableWidgetManager) currentManager).getRowHeaderLayerStack().getRowIndexDataLayer();
		DataLayer tableRowLabelHeaderLayer = ((AbstractNattableWidgetManager) currentManager).getRowHeaderLayerStack().getRowLabelDataLayer();

		// Check the initial row header width
		checkInitialRowHeaderWidth(tableRowIndexHeaderLayer, tableRowLabelHeaderLayer);

		// Resize the row header
		resizeRowheader(tableRowIndexHeaderLayer, tableRowLabelHeaderLayer);

		// Save the project
		IEditorPart part = fixture.getEditor().getActiveEditor();
		part.doSave(new NullProgressMonitor());

		AbstractHeaderAxisConfiguration rowHeader = HeaderAxisConfigurationManagementUtils.getRowAbstractHeaderAxisConfigurationUsedInTable(currentManager.getTable());

		// Check the modified width
		checkModifiedRowHeaderWidth(tableRowIndexHeaderLayer, tableRowLabelHeaderLayer, rowHeader);

		// Close the table
		final Table mainTable = TableUtils.getNotationFirstTable(fixture.getModelSet(), TABLE_NAME);
		fixture.getPageManager().closePage(mainTable);
		fixture.flushDisplayEvents();

		// Reopen the table and check its height
		init();

		// Re-get the values after the reopen
		tableRowIndexHeaderLayer = ((AbstractNattableWidgetManager) currentManager).getRowHeaderLayerStack().getRowIndexDataLayer();
		tableRowLabelHeaderLayer = ((AbstractNattableWidgetManager) currentManager).getRowHeaderLayerStack().getRowLabelDataLayer();
		rowHeader = HeaderAxisConfigurationManagementUtils.getRowAbstractHeaderAxisConfigurationUsedInTable(currentManager.getTable());

		// Check one more time the modified width after the reopen
		checkModifiedRowHeaderWidth(tableRowIndexHeaderLayer, tableRowLabelHeaderLayer, rowHeader);
	}

	/**
	 * This allows to resize row header.
	 * 
	 * @param tableRowIndexHeaderLayer
	 *            The index header layer.
	 * @param tableRowLabelHeaderLayer
	 *            The label header layer.
	 */
	protected abstract void resizeRowheader(final DataLayer tableRowIndexHeaderLayer, final DataLayer tableRowLabelHeaderLayer);

	/**
	 * This allows to check the initial row header width of the index and of the label.
	 * 
	 * @param tableRowIndexHeaderLayer
	 *            The index header layer.
	 * @param tableRowLabelHeaderLayer
	 *            The label header layer.
	 */
	protected abstract void checkInitialRowHeaderWidth(final DataLayer tableRowIndexHeaderLayer, final DataLayer tableRowLabelHeaderLayer);

	/**
	 * This allows to check the initial column header height of the index and of the label.
	 * 
	 * @param tableColumnIndexHeaderLayer
	 *            The index header layer.
	 * @param tableColumnLabelHeaderLayer
	 *            The label header layer.
	 * @param rowHeader
	 *            The row header axis configuration.
	 */
	protected abstract void checkModifiedRowHeaderWidth(final DataLayer tableRowIndexHeaderLayer, final DataLayer tableRowLabelHeaderLayer, final AbstractHeaderAxisConfiguration rowHeader);
}
