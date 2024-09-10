/*****************************************************************************
 * Copyright (c) 2011, 2016 CEA LIST, Christian W. Damus, and others.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 488965
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.hyperlink.object;

import java.net.URL;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.hyperlink.Activator;
import org.eclipse.papyrus.infra.hyperlink.ui.EditorHyperLinkWebShell;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

/**
 * this is an hyperlink to manage web link (it open a web browser when it is selected)
 *
 */
public class HyperLinkWeb extends HyperLinkObject {

	public String getHyperLinkWeb() {
		return (String) super.getObject();
	}

	public void setHyperLinkWeb(String object) {
		super.setObject(object);
	}

	@Override
	public void openLink() {
		try {
			// this is an url
			PlatformUI.getWorkbench().getBrowserSupport().createBrowser(IWorkbenchBrowserSupport.AS_EXTERNAL, "aCustomId", "url", "url").openURL(new URL(this.getHyperLinkWeb())); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		} catch (Exception e) {
			Activator.log.error(e);
		}

	}

	@Override
	public void executeEditMousePressed(Shell parentShell, List<HyperLinkObject> list, EObject amodel) {
		EditorHyperLinkWebShell editor = new EditorHyperLinkWebShell(parentShell);
		editor.setHyperLinkWeb(this);
		editor.open();
		if (editor.getHyperLinkWeb() != null) {
			int index = list.indexOf(this);
			list.remove(this);
			list.add(index, editor.getHyperLinkWeb());
		}
	}

	@Override
	public boolean needsOpenCommand() {
		return false;
	}
}
