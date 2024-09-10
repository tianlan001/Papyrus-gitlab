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

import java.util.List;

import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.ITreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;
import org.eclipse.papyrus.infra.nattable.tree.CollapseAndExpandActionsEnum;
import org.eclipse.papyrus.infra.nattable.utils.TableClipboardUtils;
import org.eclipse.papyrus.infra.tools.util.FileUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.nattable.clazz.config.tests.Activator;
import org.eclipse.ui.IEditorPart;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Vincent Lorenzo
 *
 */
@PluginResource("resources/contents_and_expand_tests_resources/ContentsAndExpandWithoutCategories_H0_H1_H2_Tests.di") //$NON-NLS-1$
public class ContentsAndExpandWithoutCategories_H0_H1_H2_Tests {

	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();

	private NatTableEditor editor;

	private ITreeNattableModelManager manager;

	private static final String SOURCE_PATH = "resources/contents_and_expand_tests_resources/";//$NON-NLS-1$


	@Before
	public void init() {
		IPageManager pageManager = fixture.getPageManager();
		List<Object> pages = pageManager.allPages();
		pageManager.openPage(pages.get(0));
		IEditorPart part = fixture.getEditor().getActiveEditor();
		Assert.assertTrue(part instanceof NatTableEditor);
		this.editor = (NatTableEditor) part;
		INattableModelManager m = (INattableModelManager) editor.getAdapter(INattableModelManager.class);
		Assert.assertTrue(m instanceof ITreeNattableModelManager);
		this.manager = (ITreeNattableModelManager) m;
	}

	/**
	 * 
	 */
	@Test
	public void testInitialDisplayedContent() {
		// we check the initial contents
		((NattableModelManager) this.manager).selectAll();
		((NattableModelManager) this.manager).copyToClipboard();
		String clipboard = getClipboardContent();
		Assert.assertNotNull(clipboard);
		String str = getWantedString("ContentsAndExpandContentsAndExpandWithoutCategories_H0_H1_H2_Tests_testInitialDisplayedContent_Result.txt");//$NON-NLS-1$
		Assert.assertEquals(str, clipboard);
	}

	protected String getClipboardContent() {
		String clipboard = TableClipboardUtils.getClipboardContentsAsString();
		return clipboard;
	}

	protected String getWantedString(String fileName) {
		return FileUtils.getStringFromPlatformFile(Activator.PLUGIN_ID, getSourcePath(), fileName, System.getProperty("line.separator"));//$NON-NLS-1$
	}

	/**
	 * @return
	 */
	private String getSourcePath() {
		return SOURCE_PATH;
	}

	@Test
	public void testExpandAll() {
		manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		((NattableModelManager) this.manager).selectAll();
		((NattableModelManager) this.manager).copyToClipboard();
		String clipboard = getClipboardContent();
		Assert.assertNotNull(clipboard);
		String str = getWantedString("ContentsAndExpandContentsAndExpandWithoutCategories_H0_H1_H2_Tests_testExpandAll_Result.txt");//$NON-NLS-1$
		Assert.assertEquals(str, clipboard);
	}

	@Test
	public void testCollapseAll() {
		testExpandAll();
		manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.COLLAPSE_ALL, null);
		((NattableModelManager) this.manager).selectAll();
		((NattableModelManager) this.manager).copyToClipboard();
		String clipboard = getClipboardContent();
		Assert.assertNotNull(clipboard);
		String str = getWantedString("ContentsAndExpandContentsAndExpandWithoutCategories_H0_H1_H2_Tests_testCollapseAll_Result.txt");//$NON-NLS-1$
		Assert.assertEquals(str, clipboard);
	}
}
