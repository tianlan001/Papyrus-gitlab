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

package org.eclipse.papyrus.uml.nattable.generic.tests.bugs;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.AbstractNattableWidgetManager;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.BooleanValueStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestyleFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.nattable.utils.NamedStyleConstants;
import org.eclipse.papyrus.infra.nattable.utils.PapyrusTableSizeCalculation;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.TableUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.Model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * This allows to manage the fill columns size tests.
 */
@PluginResource("resources/bugs/bug489720/FillColumnsSize.di") //$NON-NLS-1$
public class FillColumnsSizeTest extends AbstractPapyrusTest {

	/**
	 * The table name.
	 */
	protected static final String TABLE_NAME = "GenericTable"; //$NON-NLS-1$

	/**
	 * The papyrus fixture.
	 */
	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();

	/**
	 * The nattable model manager.
	 */
	protected INattableModelManager currentManager = null;

	/**
	 * The nattable editor.
	 */
	protected NatTableEditor editor;

	/**
	 * Constructor.
	 */
	public FillColumnsSizeTest() {
		super();
	}

	/**
	 * Initialize.
	 */
	@Before
	public void init() {
		// Get the model
		final Model model = (Model) fixture.getModel();
		Assert.assertNotNull("RootModel is null", model); //$NON-NLS-1$

		// Get the table and open it
		final Table mainTable = TableUtils.getNotationFirstTable(fixture.getModelSet(), TABLE_NAME);
		fixture.getPageManager().openPage(mainTable);
		fixture.flushDisplayEvents();

		// Get the table and its manager
		final IPageManager pageManager = fixture.getPageManager();
		final List<Object> pages = pageManager.allPages();
		pageManager.openPage(pages.get(0));
		final IEditorPart part = fixture.getEditor().getActiveEditor();
		Assert.assertTrue(part instanceof NatTableEditor);
		editor = (NatTableEditor) part;
		currentManager = (INattableModelManager) editor.getAdapter(INattableModelManager.class);
	}

	/**
	 * This allows to test the fill columns size.
	 * 
	 * @throws Exception
	 *             The exception
	 */
	@Test
	public void testFillColumnsSize() throws Exception {
		// Set the named style
		final BooleanValueStyle fillStyle = NattablestyleFactory.eINSTANCE.createBooleanValueStyle();
		fillStyle.setName(NamedStyleConstants.FILL_COLUMNS_SIZE);
		fillStyle.setBooleanValue(true);
		final Command addStyle = new AddCommand(editor.getEditingDomain(), currentManager.getTable(), NattablestylePackage.eINSTANCE.getStyledElement_Styles(), Collections.singleton(fillStyle));
		editor.getEditingDomain().getCommandStack().execute(addStyle);

		// refresh the table to display the fill columns
		((NattableModelManager) currentManager).refreshNatTable();
		fixture.flushDisplayEvents();

		/// We need to calculate the needed column width
		final NatTable nattable = currentManager.getAdapter(NatTable.class);
		final int parentSize = nattable.getParent().getSize().x;

		// Get the column width needed
		final int columnSize = PapyrusTableSizeCalculation.getColumnFillWidth((AbstractNattableWidgetManager) currentManager, parentSize);

		Assert.assertEquals("The size of first column have to be equals as needed", columnSize, currentManager.getBodyLayerStack().getBodyDataLayer().getColumnWidthByPosition(0)); //$NON-NLS-1$
		Assert.assertEquals("The size of second column have to be equals as needed", columnSize, currentManager.getBodyLayerStack().getBodyDataLayer().getColumnWidthByPosition(1)); //$NON-NLS-1$
		Assert.assertEquals("The size of third column have to be equals as needed", columnSize, currentManager.getBodyLayerStack().getBodyDataLayer().getColumnWidthByPosition(2)); //$NON-NLS-1$
		Assert.assertEquals("The size of fourth column have to be equals as needed", columnSize, currentManager.getBodyLayerStack().getBodyDataLayer().getColumnWidthByPosition(3)); //$NON-NLS-1$
	}

}
