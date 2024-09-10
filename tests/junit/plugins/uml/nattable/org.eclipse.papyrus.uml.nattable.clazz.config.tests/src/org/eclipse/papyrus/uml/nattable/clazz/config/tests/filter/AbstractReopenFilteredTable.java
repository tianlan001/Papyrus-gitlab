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

package org.eclipse.papyrus.uml.nattable.clazz.config.tests.filter;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.ITreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.FillingConfigurationUtils;
import org.eclipse.papyrus.infra.nattable.utils.StyleUtils;
import org.eclipse.papyrus.infra.nattable.utils.TableClipboardUtils;
import org.eclipse.papyrus.infra.tools.util.FileUtils;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.uml.nattable.clazz.config.tests.Activator;
import org.eclipse.ui.IEditorPart;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * 
 * Abstract test class to reopen filtered table
 */
public abstract class AbstractReopenFilteredTable extends AbstractPapyrusTest {

	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();

	/**
	 * the nattable editor
	 */
	private NatTableEditor editor;

	/**
	 * the tree table manager
	 */
	private ITreeNattableModelManager manager;

	/**
	 * the resource path
	 */
	private static final String RESOURCES_PATH = "resources/filter/bug469284";//$NON-NLS-1$

	/**
	 * load the table editor
	 */
	@Before
	public void init() {
		// TODO : a java annotation similar to diagram could be used to activate the table before the JUnit test
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


	@Test
	public void test() {
		fixture.flushDisplayEvents();
		List<?> rows = manager.getRowElementsList();
		Assert.assertTrue(rows.size() > 0); // could be empty it all the table is filtered

		manager.selectAll();
		((NattableModelManager) manager).copyToClipboard();
		String clipboard = getClipboardContent();
		Assert.assertNotNull(clipboard);

		String str = getWantedString(getResultFileName());

		// we check than the contents of the clipboard (so the displayed table) is the same than the wanted result
		Assert.assertEquals(str, clipboard);
	}

	/**
	 * this test is used to check the test
	 */
	@Test
	public void checkTestConsitency() {
		String className = getClass().getSimpleName();
		URI modelUri = fixture.getModel().eResource().getURI();
		URI uri = modelUri.trimFileExtension();

		// we check than the className is the same than the tested file
		Assert.assertEquals("The java class doesn't test the wanted papyrus model", className, uri.lastSegment());

		checkFillingConfigurationAndHiddenCategoryForTestConsistency(manager.getTable(), className);
	}



	/**
	 * @param table
	 *            the tested table
	 * @param the
	 *            simple Class name : should be something like than : className_Empty/H1/V1_H1/V1_H1/V1, where Empty than the roots of the table are filled by DnD, H1 : means 1 category hidden and V1 means 1 category visible
	 * 
	 */
	public void checkFillingConfigurationAndHiddenCategoryForTestConsistency(Table table, String simpleClassName) {
		int index = simpleClassName.indexOf(FileUtils.UNDERSCORE);
		simpleClassName = simpleClassName.substring(index + 1);
		String[] result = simpleClassName.split(FileUtils.UNDERSCORE); // $NON-NLS-1$
		Assert.assertTrue(result.length == 3);
		for (int depth = 0; depth < 3; depth++) {
			String current = result[depth];
			// filled by DnD
			if (current.equals("Empty")) { //$NON-NLS-1$
				// no configuration
				Assert.assertTrue("We must not have filling configuration for depth==0", FillingConfigurationUtils.getAllTreeFillingConfigurationForDepth(table, 0).size() == 0); //$NON-NLS-1$
				// we can't hide a depth for which we don't have category

				Assert.assertTrue("The depth 0 can't be hidden", StyleUtils.isHiddenDepth(table, 0) == false);//$NON-NLS-1$
			} else {
				Assert.assertEquals(2, current.length());
				char visibility = current.charAt(0);
				char nbCategoriesForTheDepth = current.charAt(1);
				switch (visibility) {
				case 'H':
					Assert.assertTrue(NLS.bind("The depth {0} must be hidden", depth), true == StyleUtils.isHiddenDepth(table, depth));//$NON-NLS-1$
					break;
				case 'V':
					Assert.assertTrue(NLS.bind("The depth {0} must be visible", depth), false == StyleUtils.isHiddenDepth(table, depth));//$NON-NLS-1$
					break;
				default:
					Assert.assertTrue("Not supported case", false); //$NON-NLS-1$
				}
				// we check that we have the wanted number of filling categories
				List<TreeFillingConfiguration> confs = FillingConfigurationUtils.getAllTreeFillingConfigurationForDepth(table, depth);
				int nbConfig = confs.size();
				int wantedConfig = Integer.parseInt(String.valueOf(nbCategoriesForTheDepth));
				Assert.assertEquals(wantedConfig, nbConfig);
			}
		}
	}

	/**
	 * 
	 * @return
	 * 		the name of the file which contains the wanted contents of the clipboard after the copy to clipboard
	 */
	private String getResultFileName() {
		URI uri = manager.getTable().eResource().getURI();
		uri = uri.trimFileExtension();
		StringBuffer buffer = new StringBuffer(uri.lastSegment());
		buffer.append(FileUtils.DOT_STRING);
		buffer.append(FileUtils.TEXT_EXTENSION);
		return buffer.toString();
	}

	/**
	 * @return
	 */
	private String getSourcePath() {
		return RESOURCES_PATH;
	}

	/**
	 * 
	 * @param fileName
	 *            a file name
	 * @return
	 * 		the text stored in the file associated to this test
	 */
	protected String getWantedString(String fileName) {
		return FileUtils.getStringFromPlatformFile(Activator.PLUGIN_ID, getSourcePath(), fileName, FileUtils.getSystemPropertyLineSeparator());// $NON-NLS-1$
	}


	/**
	 * 
	 * @return
	 * 		the clipboard contents
	 */
	protected String getClipboardContent() {
		String clipboard = TableClipboardUtils.getClipboardContentsAsString();
		return clipboard;
	}

}
