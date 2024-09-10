/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.clazz.config.tests.sort;

import java.util.Collections;

import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.TreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.tree.CollapseAndExpandActionsEnum;
import org.eclipse.papyrus.infra.nattable.utils.TableClipboardUtils;
import org.eclipse.papyrus.infra.tools.util.FileUtils;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.ActiveTable;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.uml.nattable.clazz.config.tests.Activator;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * This abstract test allows to check a sorted table is well sorted after opening it!
 *
 */
public abstract class AbstractOpenTableTest extends AbstractPapyrusTest {

	private static final String RESOURCES_PATH = "resources/sort/";//$NON-NLS-1$

	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();

	@ActiveTable("ClassTreeTable")
	@Test
	public void openTable() {
		final TreeNattableModelManager manager = (TreeNattableModelManager) fixture.getActiveTableManager();
		manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, Collections.emptyList());
		fixture.flushDisplayEvents();

		manager.selectAll();
		((NattableModelManager) manager).copyToClipboard();
		fixture.flushDisplayEvents();

		String str = getWantedString(getResultFileName());
		String clipboard = getClipboardContent();
		Assert.assertNotNull(clipboard);

		// we check than the contents of the clipboard (so the displayed table) is the same than the wanted result
		Assert.assertEquals(str, clipboard);
	}

	/**
	 *
	 * @return
	 *         the clipboard contents
	 */
	private String getClipboardContent() {
		return TableClipboardUtils.getClipboardContentsAsString();
	}

	/**
	 *
	 * @return
	 *         the name of the result file
	 */
	private String getResultFileName() {
		return fixture.getModelResource().getURI().trimFileExtension().appendFileExtension("txt").lastSegment(); //$NON-NLS-1$
	}

	/**
	 *
	 * @param fileName
	 *            a file name
	 * @return
	 *         the text stored in the file associated to this test
	 */
	protected String getWantedString(String fileName) {
		return FileUtils.getStringFromPlatformFile(Activator.PLUGIN_ID, RESOURCES_PATH, fileName, FileUtils.getSystemPropertyLineSeparator());// $NON-NLS-1$
	}
}
