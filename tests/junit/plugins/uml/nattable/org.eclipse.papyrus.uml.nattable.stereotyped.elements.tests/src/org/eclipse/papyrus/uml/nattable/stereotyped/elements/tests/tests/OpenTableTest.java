/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and Others.
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
 *    Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.stereotyped.elements.tests.tests;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.commands.OpenDiagramCommand;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.GenericUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.ui.IEditorPart;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * This allows to test the table opening
 */
@PluginResource("/resources/openTest.di")
public class OpenTableTest extends AbstractPapyrusTest {
	
	/**
	 * The papyrus fixture.
	 */
	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();

	/**
	 * This test allows to be sure that we doesn't break existing table model
	 * 
	 * @throws Exception The caught exception.
	 */
	@Test
	public void testOpenExistingTable() throws Exception {
		IPageManager pageManager = fixture.getServiceRegistry().getService(IPageManager.class);
		Assert.assertEquals(2, pageManager.allPages().size());
		fixture.flushDisplayEvents();
		Resource notationResource = NotationUtils.getNotationModel(fixture.getServiceRegistry().getService(ModelSet.class)).getResource();
		Table requirementTable = (Table)notationResource.getContents().get(0);
		TransactionalEditingDomain editingDomain = fixture.getServiceRegistry().getService(TransactionalEditingDomain.class);
		editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(new OpenDiagramCommand(editingDomain, requirementTable)));
		IEditorPart tableEditor = fixture.getActiveTableEditor();
		Assert.assertTrue(tableEditor instanceof NatTableEditor);
		INattableModelManager manager = (INattableModelManager)tableEditor.getAdapter(INattableModelManager.class);
		Assert.assertNotNull(manager);
		Assert.assertEquals(AllTests.REQUIREMENT_TABLE_ID, manager.getTable().getTableConfiguration().getType());
	}

	/**
	 * End of the test.
	 */
	@AfterClass
	public static void endOfTest() {
		GenericUtils.closeAllEditors();
	}
}
