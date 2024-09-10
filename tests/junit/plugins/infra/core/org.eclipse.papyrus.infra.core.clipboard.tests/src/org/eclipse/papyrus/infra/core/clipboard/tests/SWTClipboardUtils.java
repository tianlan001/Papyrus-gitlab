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

package org.eclipse.papyrus.infra.core.clipboard.tests;

import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;

public class SWTClipboardUtils {

	 static void fillClipboard(String str) {
		// adapted from CopyDataToClipboard from nattable
		final TextTransfer textTransfer = TextTransfer.getInstance();
		final Clipboard clipboard = new Clipboard(Display.getDefault());
		try {
			clipboard.setContents(new Object[] { str.toString() },
					new Transfer[] { textTransfer });
		} finally {
			clipboard.dispose();
		}

	}

 static String getClipboardContents() {
		// using SWT
		final Display display = Display.getDefault();
		String contents = null;
		final Clipboard clipboard = new Clipboard(display);
		try {

			// we use the text transfert
			final TextTransfer transfer = TextTransfer.getInstance();

			contents = (String) clipboard.getContents(transfer);

			return contents;
		} finally {
			clipboard.dispose();
		}
	}
}
