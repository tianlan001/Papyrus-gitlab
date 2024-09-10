/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
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
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.example.core.sashwindows.fulleditor.msgpage;

import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IComponentModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * Description of the first page
 * 
 * @author dumoulin
 */

public class MessagePartModel implements IComponentModel {

	private String title;

	private String msg;

	static private int count = 0;


	/**
	 * 
	 */
	public MessagePartModel(String msg) {
		title = "newMsg " + count++;
		this.msg = msg;
	}

	/**
	 * @param title
	 */
	public MessagePartModel(String title, String msg) {
		this.title = title;
		this.msg = msg;
	}

	/**
	 * Return the control to be shown. {@inheritDoc}
	 */
	public Composite createPartControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		FillLayout layout = new FillLayout();
		composite.setLayout(layout);
		StyledText text;

		text = new StyledText(composite, SWT.H_SCROLL | SWT.V_SCROLL);
		text.setEditable(false);

		text.setText("  " + msg + " - " + getTabTitle());
		return composite;
	}

	public Image getTabIcon() {
		return null;
	}

	public String getTabTitle() {
		return title;
	}

	/**
	 * Return this. In this implementation, the rawModel and the IEditorModel are the same.
	 * 
	 */
	public Object getRawModel() {
		return this;
	}

	@Override
	public void dispose() {
		// Pass
	}

}
