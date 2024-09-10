/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.clipboard.tests;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.eclipse.papyrus.junit.framework.classification.FailingTest;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.junit.Assert;
import org.junit.Test;


/**
 * 
 * @author Vincent Lorenzo
 *         This tests classes tests provides clipboard reading and wrintg tests into the clipboard
 */
public class ClipboardTests extends AbstractPapyrusTest{

	/**
	 * the string used to fill the clipboard. We don't use \n or \r\n, because it seems me than the implements AWT/SWT changes them and it is not the goal of these tests
	 */
	public static final String CLIPBOARD_CONTENTS = "Class1\tClass2_Class3\tClass4"; //$NON-NLS-1$


	@Test
	public void fillSWT_readSWT() {
		StringBuffer testedString = new StringBuffer(CLIPBOARD_CONTENTS);
		testedString.append("test1"); //$NON-NLS-1$
		SWTClipboardUtils.fillClipboard(testedString.toString());
		String read = SWTClipboardUtils.getClipboardContents();
		Assert.assertNotNull(read);
		Assert.assertEquals(testedString.toString(), read);
	}

	/**
	 * 467554: filling clipboard with SWT and reading it with AWT doesn't work on Linux
	 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=467554
	 * 
	 * @throws UnsupportedFlavorException
	 * @throws IOException
	 */
	@FailingTest
	@Test
	public void fillSWT_readAWT() throws UnsupportedFlavorException, IOException {
		StringBuffer testedString = new StringBuffer(CLIPBOARD_CONTENTS);
		testedString.append("test2"); //$NON-NLS-1$
		SWTClipboardUtils.fillClipboard(testedString.toString());
		String read = AWTClipboardUtils.getClipboardContents();
		Assert.assertNotNull(read);
		Assert.assertEquals(testedString.toString(), read);
	}

	@Test
	public void fillAWT_readAWT() throws UnsupportedFlavorException, IOException {
		StringBuffer testedString = new StringBuffer(CLIPBOARD_CONTENTS);
		testedString.append("test3"); //$NON-NLS-1$
		AWTClipboardUtils.fillClipboard(testedString.toString());
		String read = AWTClipboardUtils.getClipboardContents();
		Assert.assertNotNull(read);
		Assert.assertEquals(testedString.toString(), read);
	}

	@Test
	public void fillAWT_readSWT() {
		StringBuffer testedString = new StringBuffer(CLIPBOARD_CONTENTS);
		testedString.append("test4"); //$NON-NLS-1$
		AWTClipboardUtils.fillClipboard(testedString.toString());
		String read = SWTClipboardUtils.getClipboardContents();
		Assert.assertNotNull(read);
		Assert.assertEquals(testedString.toString(), read);
	}

}
