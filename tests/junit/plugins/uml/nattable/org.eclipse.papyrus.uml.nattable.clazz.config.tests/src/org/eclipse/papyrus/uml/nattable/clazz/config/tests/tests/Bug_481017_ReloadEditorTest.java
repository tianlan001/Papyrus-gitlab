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
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.clazz.config.tests.tests;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.TreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.tree.CollapseAndExpandActionsEnum;
import org.eclipse.papyrus.junit.utils.rules.ActiveTable;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Vincent Lorenzo
 *
 */
@PluginResource("resources/bugs/bug481017/ReloadEditorTest.di")
public class Bug_481017_ReloadEditorTest extends  AbstractOpenTableTest{

	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();

	/**
	 * the nattable editor
	 */
	protected NatTableEditor editor;

	/**
	 * the nattable widget
	 */
	protected NatTable natTable;

	/**
	 * the tree table manager
	 */
	protected TreeNattableModelManager manager;

	/**
	 * this method initialize some field for the test + expand all the table + check the initial state of the table
	 */
	protected void startTest() {
		this.manager = (TreeNattableModelManager) fixture.getActiveTableManager();
		this.natTable = (NatTable) this.manager.getAdapter(NatTable.class);
		manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		fixture.flushDisplayEvents();
	}

	/**
	 * this test check the content and the display of the table after a creation of an element outside of the table
	 */
	@Test
	@ActiveTable("ClassTreeTable")
	public void reloadTableEditorTest() {
		startTest();
		fixture.getActiveTableEditor().reloadNattableModelManager();
	}
}
