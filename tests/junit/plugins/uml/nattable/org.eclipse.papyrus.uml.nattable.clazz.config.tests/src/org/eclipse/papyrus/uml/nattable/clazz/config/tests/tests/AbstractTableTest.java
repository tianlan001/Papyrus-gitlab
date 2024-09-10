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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.TreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.menu.MenuUtils;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.tree.CollapseAndExpandActionsEnum;
import org.eclipse.papyrus.infra.nattable.utils.FillingConfigurationUtils;
import org.eclipse.papyrus.infra.nattable.utils.StyleUtils;
import org.eclipse.papyrus.infra.nattable.utils.TableClipboardUtils;
import org.eclipse.papyrus.infra.nattable.utils.TableEditingDomainUtils;
import org.eclipse.papyrus.infra.tools.util.FileUtils;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.ActiveTable;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.uml.nattable.clazz.config.tests.Activator;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Vincent Lorenzo
 *
 */
public abstract class AbstractTableTest extends AbstractPapyrusTest {

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
	 *
	 *
	 */
	protected void startTest() {
		this.manager = (TreeNattableModelManager) fixture.getActiveTableManager();
		this.natTable = (NatTable) this.manager.getAdapter(NatTable.class);
		manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		fixture.flushDisplayEvents();
		registerNattableWidgetInEclipseContext(manager, new LabelStack(GridRegion.BODY));
	}

	/**
	 * This method register the nattable widget in the eclipse context, to be able to get is in the the setEnable(Objet) of the handlers
	 *
	 * @see org.eclipse.papyrus.infra.nattable.handler.AbstractTableHandler.getCurrentNattableModelManager()
	 * @param manager
	 *            the nattable manager
	 */
	protected void registerNattableWidgetInEclipseContext(INattableModelManager manager, LabelStack regionLabels) {
		MenuUtils.registerNatTableWidgetInEclipseContext(manager, regionLabels);
	}

	/**
	 * this test is used to check the test
	 */
	@Test
	@ActiveTable("ClassTreeTable")
	public void checkTestConsistency() {
		startTest();
		String className = getClass().getSimpleName();
		URI modelUri = fixture.getModel().eResource().getURI();
		URI uri = modelUri.trimFileExtension();

		// we check than the className is the same than the tested file
		Assert.assertEquals("The java class doesn't test the wanted papyrus model", className, uri.lastSegment()); //$NON-NLS-1$

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
		// we expect a name like CLassName_H1_V1_H1_Tests or CLassName_H1_V1_H1
		int index = simpleClassName.indexOf(FileUtils.UNDERSCORE);
		simpleClassName = simpleClassName.substring(index + 1);
		String[] result = simpleClassName.split(FileUtils.UNDERSCORE); // $NON-NLS-1$
		if (simpleClassName.endsWith("_Test")) { //$NON-NLS-1$
			Assert.assertTrue(result.length == 4);
		} else {
			Assert.assertTrue(result.length == 3);
		}
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
	 * @param fileName
	 *            a file name
	 * @return
	 *         the text stored in the file associated to this test
	 */
	protected String getWantedString(String fileName) {
		return FileUtils.getStringFromPlatformFile(Activator.PLUGIN_ID, getSourcePath(), fileName, FileUtils.getSystemPropertyLineSeparator());// $NON-NLS-1$
	}

	/**
	 *
	 * @return
	 *         the source path to use to load files
	 *
	 *         must be overridden by sub classes
	 */
	protected String getSourcePath() {
		return null;
	}

	/**
	 *
	 * @return
	 *         the clipboard contents
	 */
	protected String getClipboardContent() {
		String clipboard = TableClipboardUtils.getClipboardContentsAsString();
		return clipboard;
	}

	/**
	 *
	 * @return
	 *         the editing domain of the context of the table
	 */
	protected TransactionalEditingDomain getTableContextEditingDomain() {
		return TableEditingDomainUtils.getTableContextEditingDomain(this.manager.getTable());
	}

	/**
	 *
	 * @return
	 *         the editing domain of the table
	 */
	protected TransactionalEditingDomain getTableEditingDomain() {
		return TableEditingDomainUtils.getTableEditingDomain(this.manager.getTable());
	}

	/**
	 *
	 * @param resultFileName
	 *            the name of the file containing the wanted result
	 */
	protected void compareCurrentDisplayToWantedDisplay(String resultFileName) {
		fixture.flushDisplayEvents();
		manager.selectAll();
		((NattableModelManager) manager).copyToClipboard();
		fixture.flushDisplayEvents();

		String clipboard = getClipboardContent();
		Assert.assertNotNull(clipboard);

		String str = getWantedString(resultFileName);

		// we check than the contents of the clipboard (so the displayed table) is the same than the wanted result
		Assert.assertEquals(str, clipboard);
	}

	/**
	 * Method to hide all categories in the tree table manager.
	 *
	 * @param treeManager
	 *            The tree table manager
	 */
	protected void hideAllCategories(final TreeNattableModelManager treeManager) {
		final Table table = treeManager.getTable();
		final int maxDepth = FillingConfigurationUtils.getMaxDepthForTree(table);
		final List<Integer> toHide = new ArrayList<>();
		int start = 0;
		if (!FillingConfigurationUtils.hasTreeFillingConfigurationForDepth(table, 0)) {
			start = 1;
		}
		for (int i = start; i <= maxDepth; i++) {
			toHide.add(Integer.valueOf(i));
		}
		treeManager.hideShowCategories(toHide, null);
	}
}
